'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherSearchController',
  function ($scope, $route, QueryUtils, DataUtils, ArrayUtils, USER_ROLES, AuthService, message, $q) {
    $scope.schoolId = $route.current.locals.auth.school.id;

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM);
    $scope.currentNavItem = 'teachers';
    $scope.auth = $route.current.locals.auth;

    $scope.formState = {
      xlsUrl: 'subjectStudyPeriods/teachers/searchByTeacher.xls',
      xlsUrlSummary: "subjectStudyPeriods/studentGroups/workloadsummary.xls"
    };

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/teachers', {order: 'p.lastname,p.firstname'}, function () {
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

    function filterStudyPeriods(studyPeriod) {
      return !$scope.criteria.studyYear || studyPeriod.studyYear.id === $scope.criteria.studyYear;
    }

    $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
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

    $scope.$watchCollection('criteria.teacherObject', function() {
            $scope.criteria.teacher = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.map(function(item) {return item.id}) : null;
        }
    );
  });
