'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectSearchController', 
  function ($scope, $sessionStorage, QueryUtils, DataUtils, ArrayUtils, USER_ROLES, AuthService, message) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM);

    $scope.currentNavItem = 'subjects';

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/subjects/searchBySubject.xls'};

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/subjects', {order: 'id'}, function () {
      $scope.periodId = $scope.criteria.studyPeriod;
    });

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
        $scope.loadData();
    }

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(setCurrentStudyPeriod);
    $scope.studyPeriods.$promise.then(function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
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

    $scope.$watch('criteria.teacherObject', function() {
            $scope.criteria.teacher = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.id : null;
        }
    );

    $scope.$watch('criteria.subjectObject', function() {
            $scope.criteria.subject = $scope.criteria.subjectObject ? $scope.criteria.subjectObject.id : null;
        }
    );
  });
