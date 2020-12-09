'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectSearchController',
  function ($scope, $sessionStorage, QueryUtils, DataUtils, ArrayUtils, USER_ROLES, AuthService, message, $q) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM);

    $scope.currentNavItem = 'subjects';

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/subjects/searchBySubject.xls'};

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/subjects', {order: 's.code,s.' + $scope.currentLanguageNameField()}, function () {
      $scope.periodId = $scope.criteria.studyPeriod;
    });

    function setCurrentStudyPeriod() {
      if($scope.criteria) {
        if (!$scope.criteria.studyYear) {
          $scope.criteria.studyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears).id;
          if (!$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
          }
        }
        $scope.filteredStudyPeriods = $scope.studyPeriods.filter(filterStudyPeriods);
      }
      $scope.loadData();
    }

    $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.studyPeriods.$promise.then(function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });
    $q.all([$scope.studyYears.$promise, $scope.studyPeriods.$promise])
      .then(setCurrentStudyPeriod)
      .then(function () {
        $scope.$watch('criteria.studyYear', function () {
          $scope.filteredStudyPeriods = $scope.studyPeriods.filter(filterStudyPeriods);
          var hasSelected = !$scope.criteria.studyPeriod ||
            !!$scope.filteredStudyPeriods.find(function (it) { return it.id === $scope.criteria.studyPeriod; });
          if (!hasSelected || !$scope.criteria.studyYear) {
            $scope.criteria.studyPeriod = undefined;
          }
        });
      });

    $scope.load = function() {
        if (!$scope.searchForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
        } else {
          $scope.loadData();
        }
      };

    $scope.$watch('hiddenCriteria.teacherObject', function() {
            $scope.criteria.teacher = $scope.hiddenCriteria.teacherObject ? $scope.hiddenCriteria.teacherObject.id : null;
        }
    );

    $scope.$watch('hiddenCriteria.subjectObject', function() {
            $scope.criteria.subject = $scope.hiddenCriteria.subjectObject ? $scope.hiddenCriteria.subjectObject.id : null;
        }
    );

    function filterStudyPeriods(studyPeriod) {
      return !$scope.criteria.studyYear || studyPeriod.studyYear.id === $scope.criteria.studyYear;
    }
  });
