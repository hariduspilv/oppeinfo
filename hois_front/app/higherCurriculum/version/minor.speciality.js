'use strict';

angular.module('hitsaOis')
  .controller('MinorSpecialityController', function ($scope, $route, QueryUtils, dialogService, message, ArrayUtils, $location, $rootScope) {

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

    $scope.formState = {
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
    };

    var initial = {
      electiveModules: [],
      specialitiesReferenceNumbers: [],
      subjects: [],
      minorSpeciality: true,
      optionalStudyCredits: 0,
      totalCredits: 0,
      compulsoryStudyCredits: 0,
      electiveModulesNumber: 0,
      type: 'KORGMOODUL_M',
      curriculumVersion: versionId
    };

    if (id) {
      Endpoint.get({ id: id }).$promise.then(afterLoad);
    } else {
      QueryUtils.endpoint(baseUrl + "/curriculumYears").get({id: curriculumId}, function (response) {
        fillStudyYears(response.years);
      });
      $scope.data = new Endpoint(angular.extend({}, initial));
    }

    function afterLoad(response) {
      $scope.data = new Endpoint(response);
      fillStudyYears(response.studyYears);
    }

    function fillStudyYears(years) {
      $scope.studyYears = [];
      if (typeof years === 'number') {
        for (var i = 0; i < years; i++) {
          $scope.studyYears.push(i + 1);
        }
      }
    }

    function getSubjects () {
      QueryUtils.endpoint(baseUrl + '/minorSpeciality/possibleSubjects').query({
        curriculumVersion: versionId
      }).$promise.then(function(response){
        $scope.subjects = response;
      });
    };

    getSubjects();

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
      $scope.minorSpecialityForm.$setDirty();
    };

    function subjectsValid() {
      return $scope.data.subjects.length > 0;
    }

    $scope.removeSubject = function (subject) {
      ArrayUtils.remove($scope.data.subjects, subject);
      $scope.setCompulsoryStudyCredits();
      $scope.minorSpecialityForm.$setDirty();
    };

    $scope.addSubject = function () {
      var newSubject = angular.copy($scope.data.selectedSubject);
      newSubject.optional = false;
      $scope.data.subjects.push(newSubject);
      $scope.data.selectedSubject = undefined;
      $scope.setCompulsoryStudyCredits();
      $scope.minorSpecialityForm.$setDirty();
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
          module1.compulsoryStudyCredits += e.credits;
        }
      });
      module1.totalCredits = module1.compulsoryStudyCredits + module1.optionalStudyCredits;
    };

    function validationPassed() {
      $scope.minorSpecialityForm.$setSubmitted();
      if (!$scope.minorSpecialityForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      } else if (!subjectsValid()) {
        message.error('curriculum.error.noSubject');
        return false;
      }
      return true;
    }

    $scope.save = function (response) {
      if (!validationPassed()) {
        return;
      }
      if (id) {
        $scope.data.$update().then(function (response) {
          message.info('main.messages.update.success');
          $scope.minorSpecialityForm.$setPristine();
          afterLoad(response);
        });
      } else {
        $scope.data.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.path('/higherCurriculum/' + curriculumId + '/version/' + versionId + '/minorSpeciality/' + response.id + '/edit');
        });
      }
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'curriculum.minorSpecialtyDeleteConfirm' }, function () {
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
