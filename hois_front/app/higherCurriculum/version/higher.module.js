'use strict';

angular.module('hitsaOis')
  .controller('HigherModuleController', function ($scope, $route, QueryUtils, dialogService, message, ArrayUtils, $location, $rootScope) {

    var id = $route.current.params.id;
    var versionId = $route.current.params.versionId;
    var curriculumId = $route.current.params.curriculumId;
    var baseUrl = '/higherModule';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    $scope.studyYears = [];
    $scope.backToEditForm = '#/higherCurriculum/' + curriculumId + '/version/' + versionId + '/edit';
    $scope.backToViewForm = '#/higherCurriculum/' + curriculumId + '/version/' + versionId + '/view';
    $scope.schoolModuleGrades = !!($route.current.locals.auth.school || {}).hmodules;

    function moduleMustHaveSubjects(module) {
      return module.type !== 'KORGMOODUL_V';
    };

    $scope.formState = {
      moduleMustHaveSubjects: !$scope.data || moduleMustHaveSubjects(scope.data),
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
    };

    var initial = {
      electiveModules: [],
      specialitiesReferenceNumbers: [],
      subjects: [],
      minorSpeciality: false,
      optionalStudyCredits: 0,
      totalCredits: 0,
      compulsoryStudyCredits: 0,
      electiveModulesNumber: 0,
      curriculumVersion: versionId
    };

    if (id) {
      Endpoint.get({ id: id }).$promise.then(afterLoad);
    } else {
      QueryUtils.endpoint(baseUrl + "/curriculumYears").get({id: curriculumId}, function (response) {
        fillStudyYears(response.years);
      });
      $scope.data = new Endpoint(angular.extend({}, initial));
      setTypes();
    }

    function afterLoad(response) {
      $scope.data = new Endpoint(response);
      fillStudyYears(response.studyYears);
      setTypes();
      $scope.getSubjects();
    }

    function fillStudyYears(years) {
      $scope.studyYears = [];
      if (typeof years === 'number') {
        for (var i = 0; i < years; i++) {
          $scope.studyYears.push(i + 1);
        }
      }
    }

    function setTypes() {
      var url = baseUrl + '/versionHmoduleTypes/' + versionId + ($scope.data.id ? "/" + $scope.data.id : "");
      QueryUtils.endpoint(url).query().$promise.then(function (response) {
        $scope.moduleTypes = response;
        $scope.data.typeObject = $scope.moduleTypes.find(function (el) {
          if ($scope.data.type !== 'KORGMOODUL_M') {
            return el.code === $scope.data.type;
          } else {
            return el.code === null && el.nameEn === $scope.data.typeNameEn && el.nameEt === $scope.data.typeNameEt;
          }
        });
        if($scope.higherModuleForm) {
            $scope.higherModuleForm.$setPristine();
        }
      });
    }

    QueryUtils.endpoint(baseUrl + '/version/' + versionId + '/specialities').query().$promise.then(function(response){
      $scope.specialities = response;
    });

    $scope.getSubjects = function() {
      if(ArrayUtils.isEmpty($scope.data.specialitiesReferenceNumbers)) {
        $scope.subjects = [];
        return;
      }

      QueryUtils.endpoint(baseUrl + '/possibleSubjects').query({
        module: id,
        curriculumVersion: versionId,
        specialities: $scope.data.specialitiesReferenceNumbers
      }).$promise.then(function(response){
        $scope.subjects = response;
      });
    };

    $scope.searchSubjects = function (text) {
      if (!text) {
        return [];
      }
      var regExp = new RegExp('^.*' + text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\$&').replace("%", ".*").toUpperCase() + '.*$');
      return ($scope.subjects || []).filter(function (subject) {
        return $scope.subjectNotAdded(subject) && regExp.test(subject.code + " - " + $scope.currentLanguageNameField(subject).toUpperCase());
      });
    };

    $scope.$watch('data.typeObject', function (newValue) {
      if (newValue && newValue.code === 'KORGMOODUL_V') {
        $scope.formState.moduleMustHaveSubjects = false;
        clearFreeModulesProperties();
      } else if (newValue) {
        $scope.formState.moduleMustHaveSubjects = true;
      }
    });

    function clearFreeModulesProperties() {
      $scope.data.subjects = [];
      $scope.data.electiveModules = [];
      $scope.data.compulsoryStudyCredits = 0;
      $scope.data.electiveModulesNumber = 0;
      $scope.data.totalCredits = $scope.data.optionalStudyCredits;
    }

    $scope.addElectiveModule = function () {
      var newElectiveModule = {
        nameEt: $scope.optionalNameEt,
        nameEn: $scope.optionalNameEn,
        referenceNumber: getReferenceNumber($scope.data.electiveModules)
      };
      $scope.data.electiveModules.push(newElectiveModule);
      $scope.optionalNameEt = undefined;
      $scope.optionalNameEn = undefined;
    };
    $scope.editingElectiveModule = false;
    var editedElectiveModule = null;

    $scope.editElectiveModule = function (electiveModule) {
      editedElectiveModule = electiveModule;
      $scope.editingElectiveModule = true;
      $scope.optionalNameEt = electiveModule.nameEt;
      $scope.optionalNameEn = electiveModule.nameEn;
    };

    $scope.finishEditElectiveModule = function () {
      $scope.editingElectiveModule = false;
      editedElectiveModule.nameEt = $scope.optionalNameEt;
      editedElectiveModule.nameEn = $scope.optionalNameEn;
      $scope.optionalNameEt = undefined;
      $scope.optionalNameEn = undefined;
    };


    $scope.deleteElectiveModule = function (electiveModule) {
      $scope.data.subjects.forEach(function (e) {
        if (e.electiveModule === electiveModule.referenceNumber) {
          e.electiveModule = undefined;
        }
      });
      ArrayUtils.remove($scope.data.electiveModules, electiveModule);
      $scope.higherModuleForm.$setDirty();
    };

    $scope.subjectsValid = function() {
      return !$scope.formState.moduleMustHaveSubjects || ($scope.data.subjects && $scope.data.subjects.length > 0);
    }

    $scope.removeSubject = function (subject) {
      ArrayUtils.remove($scope.data.subjects, subject);
      $scope.setCompulsoryStudyCredits();
      $scope.higherModuleForm.$setDirty();
    };

    $scope.addSubject = function () {
      if (!$scope.data.selectedSubject) {
        return;
      }
      var newSubject = angular.copy($scope.data.selectedSubject);
      newSubject.optional = false;
      $scope.data.subjects.push(newSubject);
      $scope.data.selectedSubject = undefined;
      $scope.setCompulsoryStudyCredits();
      $scope.higherModuleForm.$setDirty();
    };

    $scope.setCompulsoryStudyCredits = function (subject) {
      $scope.setCompulsoryAndTotalStudyCredits($scope.data);
      if (subject && !subject.optional) {
        subject.electiveModule = undefined;
      }
    };

    $scope.setCompulsoryAndTotalStudyCredits = function (module1) {
      module1.compulsoryStudyCredits = 0;
      module1.subjects.forEach(function (e) {
        if (!e.optional) {
          module1.compulsoryStudyCredits = Math.round((module1.compulsoryStudyCredits + e.credits) * 100) / 100;
        }
      });
      module1.totalCredits = Math.round((module1.compulsoryStudyCredits + module1.optionalStudyCredits) * 100) / 100;
    };

    $scope.setTotalCredits = function() {
      $scope.data.totalCredits = Math.round(($scope.data.optionalStudyCredits + $scope.data.compulsoryStudyCredits) * 100) / 100;
    };

    function validationPassed() {
      $scope.higherModuleForm.$setSubmitted();
      if (!$scope.higherModuleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      } else if (!$scope.subjectsValid()) {
        message.error('curriculum.error.noSubject');
        return false;
      }
      return true;
    }

    function setModuleType() {
      var data = $scope.data;
      if (data.typeObject.code !== 'KORGMOODUL_M' && data.typeObject.code !== null) {
        data.typeNameEt = null;
        data.typeNameEn = null;
        data.type = data.typeObject.code;
        // user selected module type saved before
      } else if (data.typeObject.code === null) {
        data.type = 'KORGMOODUL_M';
        data.typeNameEt = data.typeObject.nameEt;
        data.typeNameEn = data.typeObject.nameEn;
        // user created new module type
      } else {
        data.type = 'KORGMOODUL_M';
      }
    }

    $scope.save = function (response) {
      if (!validationPassed()) {
        return;
      }
      setModuleType();
      if (id) {
        $scope.data.$update().then(function (response) {
          message.info('main.messages.update.success');
          $scope.higherModuleForm.$setPristine();
          afterLoad(response);
        });
      } else {
        $scope.data.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path('/higherCurriculum/' + curriculumId + '/version/' + versionId + '/module/' + response.id + '/edit');
        });
      }
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'curriculum.moduleDeleteConfirm' }, function () {
        new Endpoint($scope.data).$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url('/higherCurriculum/' + curriculumId + '/version/' + versionId + '/edit?_noback');
        });
      });
    };

    $scope.subjectNotAdded = function (subject) {
      if(!$scope.data || ArrayUtils.isEmpty($scope.data.subjects)) {
        return true;
      }
      var added = $scope.data.subjects.map(function (s) {
        return s.subjectId;
      });
      return !ArrayUtils.includes(added, subject.subjectId);
    };

    function getReferenceNumber(list) {
      var existingReferenceNumbers = list.map(function (el) {
        return el.referenceNumber;
      });
      var rand;
      while (true) {
        rand = - Math.floor((Math.random() * 1000) + 1);
        if (!ArrayUtils.includes(existingReferenceNumbers, rand)) {
          break;
        }
      }
      return rand;
    }

  });
