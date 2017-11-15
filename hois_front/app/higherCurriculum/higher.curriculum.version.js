'use strict';

angular.module('hitsaOis')
  .controller('HigherCurriculumVersionController', function ($scope, Curriculum, dialogService, ArrayUtils, message, $route, $location, QueryUtils, $translate, $rootScope, $routeParams, DataUtils, config, Session) {

    var baseUrl = '/curriculum';
    $scope.curriculum = $route.current.locals.curriculum;
    $scope.formState = {
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
      strictValidation: false
    };
    var SpecialityEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/speciality');
    var SubjectEndpoint = QueryUtils.endpoint('/higherModule/subject/');
    var ModuleCreditsEndpoint = QueryUtils.endpoint('/higherModule/credits/');

    var Endpoint = QueryUtils.endpoint('/curriculumVersion');
    var id = $route.current.params.versionId;

    $scope.VERSION_STATUS = Curriculum.VERSION_STATUS;

    var initialVersion = {
      status: $scope.VERSION_STATUS.S,
      validFrom: new Date(),
      code: $scope.curriculum.code + "/" + new Date().getFullYear(),
      curriculum: $scope.curriculum.id,
      modules: [],
      canChange: true
    };

    if(id) {
      $scope.version = Endpoint.get({id: id}).$promise.then(function(response){
        $scope.version = response;
        dtoToModel();
        $rootScope.removeLastUrlFromHistory(function(lastUrl){
          return lastUrl && lastUrl.indexOf('#/higherCurriculum/' + $scope.version.curriculum + '/version/') !== -1;
        });
      });
    } else {
      $scope.version = angular.extend(initialVersion);
    }

    function dtoToModel() {
      DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
      $scope.formState.curriculumPdfUrl = config.apiUrl + baseUrl + '/print/' + $scope.version.id + '/curriculum.pdf';
      $scope.formState.curriculumXmlUrl = config.apiUrl + baseUrl + '/xml/' + $scope.version.id + '/curriculum.version.xml';
    }



    // --- Dialog Windows

    $scope.openAddSpecialtyDialog = function () {
      var DialogController = function (scope) {
        scope.maxCredits = $scope.curriculum.credits ? $scope.curriculum.credits : 0;
        scope.isNew = true;
      };
      dialogService.showDialog('higherCurriculum/higher.curriculum.specialty.add.dialog.html', DialogController,
        function (submitScope) {
          var data = submitScope.data;
          if (!data.occupation) {
            data.occupationEt = undefined;
            data.occupationEn = undefined;
          }
          var spec = new SpecialityEndpoint(data);
          spec.curriculum = $scope.curriculum.id;
          spec.$save().then(function (responses) {
            message.info('main.messages.create.success');
            $scope.curriculum.specialities.push(responses);
          });
        });
    };

    function setAdmissionYearsSelelction() {
      $scope.admissionYears = [];
      var currentYear = new Date().getFullYear();
      for (var year = currentYear - 10; year <= currentYear + 2; year++) {
        $scope.admissionYears.push(year);
      }
    }
    setAdmissionYearsSelelction();

    $scope.curriculumVersionCodeUniqueQuery = {
      id: $route.current.params.versionId,
      url: '/curriculumVersion/unique/code'
    };

    $scope.save = function () {
      $scope.formState.strictValidation = false;
      setTimeout(save, 0);
    };


    function validationPassed(messages) {
      $scope.higherCurriculumVersionForm.$setSubmitted();

      if (!versionFormIsValid()) {
        var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
        message.error(errorMessage);
        return false;
      }
      return true;
    }

    function save(messages) {

      if (!validationPassed(messages)) {
        return;
      }
      var curriculumVersion = new Endpoint($scope.version);

      if (angular.isDefined($scope.version.id)) {
        curriculumVersion.$update().then(function (response) {
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          $scope.version = response;
          DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
          $scope.higherCurriculumVersionForm.$setPristine();
        });
      } else {
        curriculumVersion.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/edit');
        });
      }
    }

    $scope.saveAndConfirm = function() {
      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.verify' : 'curriculum.statuschange.version.prompt.verify',
        updateSuccess: 'curriculum.statuschange.version.success.verified'
      };
      var SaveAndConfirmEndpoint = QueryUtils.endpoint("/curriculumVersion/saveAndConfirm");
      $scope.formState.strictValidation = true;
      changeStatus(SaveAndConfirmEndpoint, messages);
    };

    $scope.confirm = function() {
      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.verify' : 'curriculum.statuschange.version.prompt.verify',
        updateSuccess: 'curriculum.statuschange.version.success.verified'
      };
      var ConfirmEndpoint = QueryUtils.endpoint("/curriculumVersion/confirm");
      $scope.formState.strictValidation = true;
      changeStatus(ConfirmEndpoint, messages);
    };

    $scope.setStatusClosed = function() {

      var messages = {
        prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.version.prompt.close' : 'curriculum.statuschange.version.prompt.close',
        updateSuccess: 'curriculum.statuschange.version.success.closed'
      };
      var ClosingEndpoint = QueryUtils.endpoint("/curriculumVersion/close");
      $scope.formState.strictValidation = false;
      changeStatus(ClosingEndpoint, messages);
    };

    function changeStatus(StatusChangeEndpoint, messages) {
      if (!validationPassed(messages)) {
        return;
      }
      setTimeout(function(){
        dialogService.confirmDialog({ prompt: messages.prompt }, function () {
            new StatusChangeEndpoint($scope.version).$update().then(function(response){
              var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
              message.info(updateSuccess);
              $scope.version = response;
              DataUtils.convertStringToDates($scope.version, ["validFrom", "validThru"]);
              $scope.version.curriculum = $scope.curriculum.id;
              $scope.formState.strictValidation = false;
              goToReadOnlyForm(response);
              $scope.higherCurriculumVersionForm.$setPristine();
            });
        });
      }, 0);
    }

    function goToReadOnlyForm(response) {
      if(!$scope.formState.readOnly) {
        $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/view');
      }
    }

    $scope.goToEditForm = function () {
      if (!$scope.curriculum) {
        return;
      }
      if ($scope.version.status === Curriculum.VERSION_STATUS.K) {
        dialogService.confirmDialog({
          prompt: 'curriculum.statuschange.version.prompt.editAccepted',
        }, function () {
          $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/edit');
        });
      } else {
        $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + $scope.version.id + '/edit');
      }
    };

    function versionFormIsValid() {
      return $scope.higherCurriculumVersionForm.$valid && ($scope.versionIsValid() || !$scope.strictValidation());
    }

    $scope.strictValidation = function () {
      return $scope.version.status === Curriculum.VERSION_STATUS.K || $scope.formState.strictValidation;
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'curriculum.version.deleteconfirm' }, function () {
        var curriculumVersion = new Endpoint($scope.version);
        curriculumVersion.$delete().then(function () {
          message.info('main.messages.delete.success');
          $rootScope.back('#/higherCurriculum/' + $scope.version.curriculum + '/view');
        });
      });
    };

    $scope.copy = function() {
      var CopyEndpoint = QueryUtils.endpoint('/curriculumVersion/copy');
      new CopyEndpoint({id: id}).$update().then(function(response){
        message.info('main.messages.create.success');
        $location.path('/higherCurriculum/' + $scope.version.curriculum + '/version/' + response.id + '/edit');
      });
    };

    // version form validation

    $scope.versionIsValid = function () {
      return $scope.allSpecialitiesValid() && $scope.allMinorSpecialitiesValid();
    };

    function getModulesBySpeciality(specialityId) {
      return $scope.version.modules.filter(function (el) {
        return !el.minorSpeciality && ArrayUtils.includes(el.curriculumSpecialities, specialityId);
      });
    }

    $scope.allMinorSpecialitiesValid = function () {
      var minorSpecs = $scope.version.modules.filter(function (el) {
        return el.minorSpeciality;
      });
      for (var i = 0; i < minorSpecs.length; i++) {
        if (!$scope.moduleValid(minorSpecs[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.allSpecialitiesValid = function () {
      if (ArrayUtils.isEmpty($scope.version.specialitiesReferenceNumbers) || ArrayUtils.isEmpty($scope.version.modules)) {
        return false;
      }
      var specs = $scope.version.specialitiesReferenceNumbers;
      for (var i = 0; i < specs.length; i++) {
        if (!$scope.specialityValid(specs[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.specialityValid = function (specialityId) {
      var modules = getModulesBySpeciality(specialityId);
      if (ArrayUtils.isEmpty(modules)) {
        return false;
      }
      for (var i = 0; i < modules.length; i++) {
        if (!$scope.moduleValid(modules[i])) {
          return false;
        }
      }
      return true;
    };

    $scope.moduleValid = function (module1) {
      return $scope.moduleHasSubjects(module1);
    };

    $scope.anyModuleAdded = function (version) {
      return version.modules.filter(function (el) { return !el.minorSpeciality; }).length > 0;
    };

    $scope.moduleMustHaveSubjects = function(module) {
      return module.type !== 'KORGMOODUL_V';
    };

    $scope.moduleHasSubjects = function (module1) {
      if (module1.minorSpeciality) {
        return module1.subjects && module1.subjects.length > 0;
      } else {
        return !$scope.moduleMustHaveSubjects(module1) || module1.subjects && module1.subjects.length > 0;
      }
    };


    // modules and specialities

    function moduleHasSpeciality(m) {
      return m.minorSpeciality || (m.curriculumSpecialities && m.curriculumSpecialities.length > 0);
    }

    $scope.filterModulesBySpeciality = function (spec) {
      return function (module1) {
        return ArrayUtils.includes(module1.curriculumSpecialities, spec.referenceNumber);
      };
    };

    $scope.filterSpecialities = function (spec) {
      if (!$scope.curriculum || !$scope.version || !$scope.version.specialitiesReferenceNumbers) {
        return false;
      }
      return ArrayUtils.includes($scope.version.specialitiesReferenceNumbers, spec.referenceNumber);
    };

    function updateModuleCredits(module1) {
      ModuleCreditsEndpoint.get({id: module1.id}).$promise.then(function(response){
        module1.totalCredits = response.totalCredits;
        module1.compulsoryStudyCredits = response.compulsoryStudyCredits;
      });
    }

    $scope.deleteSubject = function (module1, subject) {
      dialogService.confirmDialog({ prompt: 'curriculum.itemDeleteConfirm' }, function () {
        new SubjectEndpoint(subject).$delete().then(function(response){
          message.info('main.messages.delete.success');
          ArrayUtils.remove(module1.subjects, subject);
          updateModuleCredits(module1);
        });
      });
    };

    $scope.$watchCollection('version.specialitiesReferenceNumbers', function(newValues, oldValues){
      if(specialityRemoved(newValues, oldValues)) {
        var removedSpecialities = getRemovedSpecialities(newValues, oldValues);
        for(var i = 0; i < removedSpecialities.length; i++) {
          if(getModulesBySpeciality(removedSpecialities[i]).length > 0) {
            message.error("curriculum.error.specAddedToModule");
            $scope.version.specialitiesReferenceNumbers = oldValues;
            return;
          }
        }
      }
    });

    function specialityRemoved(newValues, oldValues) {
      return !angular.isDefined(newValues) && angular.isDefined(oldValues) ||
      angular.isDefined(newValues) && angular.isDefined(oldValues) && newValues.length < oldValues.length;
    }

    function getRemovedSpecialities(newValues, oldValues) {
      if(!angular.isDefined(newValues)) {
        return oldValues;
      }
      var removedSpecialities = [];
      for(var i = 0; i < oldValues.length; i++) {
        if(!ArrayUtils.contains(newValues, oldValues[i])) {
          removedSpecialities.push(oldValues[i]);
        }
      }
      return removedSpecialities;
    }

    // dialog windows

    $scope.openAddModuleDialog = function (editingModule) {

      if($scope.higherCurriculumVersionForm.$dirty) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          openAddModuleDialog(editingModule);
        });
      } else {
        openAddModuleDialog(editingModule);
      }
    };

    function openAddModuleDialog (editingModule) {

      var url = '';
      if(!editingModule) {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/module/new';
      } else if ($scope.formState.readOnly || !$scope.version.canChange) {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/module/' + editingModule.id + '/view';
      } else {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/module/' + editingModule.id + '/edit';
      }
      $location.path(url);
    }

    $scope.openAddMinorSpecialtyDialog = function (editingModule) {

      if($scope.higherCurriculumVersionForm.$dirty) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          openAddMinorSpecialtyDialog(editingModule);
        });
      } else {
        openAddMinorSpecialtyDialog(editingModule);
      }
    };

    function openAddMinorSpecialtyDialog (editingModule) {

      var url = '';
      if(!editingModule) {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/minorSpeciality/new';
      } else if ($scope.formState.readOnly || !$scope.version.canChange) {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/minorSpeciality/' + editingModule.id + '/view';
      } else {
        url = '/higherCurriculum/'+ $scope.curriculum.id + '/version/' + $scope.version.id + '/minorSpeciality/' + editingModule.id + '/edit';
      }
      $location.path(url);
    }

  });
