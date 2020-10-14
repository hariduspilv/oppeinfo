'use strict';

angular.module('hitsaOis').controller('AcademicCalendarController', ['$scope', '$route', 'QueryUtils', 'DataUtils',
  function($scope, $route, QueryUtils, DataUtils) {
    $scope.auth = $route.current.locals.auth;
    $scope.formState = {};

    $scope.directRoute = angular.isDefined($route.current.params.schoolId);
    var schoolAuthenticatedUser = false;
    if ($scope.directRoute) {
      $scope.schoolId = parseInt($route.current.params.schoolId, 10);
    }
    if (angular.isDefined($scope.auth) && angular.isDefined($scope.auth.school) && $scope.auth.school !== null) {
      if (angular.isUndefined($scope.schoolId)) {
        $scope.schoolId = $scope.auth.school.id;
        schoolAuthenticatedUser = true;
      } else {
        schoolAuthenticatedUser = $scope.auth.school.id === $scope.schoolId;
      }
    }

    $scope.changeStudyYear = function(studyYear) {
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint((!schoolAuthenticatedUser ? '/public' : '') +
        '/academicCalendar/' + $scope.schoolId + '/' + studyYear.id).search().$promise.then(function(response) {
        $scope.data = response;
        $scope.formState.studyYear = studyYear;
        QueryUtils.loadingWheel($scope, false);
      });
    };

    if (angular.isDefined($scope.schoolId)) {
      QueryUtils.endpoint('/academicCalendar/studyYears/' + $scope.schoolId).query().$promise.then(function (response) {
        $scope.formState.studyYears = response;
        $scope.formState.studyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
      });
      if (!schoolAuthenticatedUser) {
        QueryUtils.endpoint('/public/schoolSettings').get({schoolId: $scope.schoolId}).$promise.then(function (result) {
          $scope.isForbidden = result.isAcademicCalendarNotPublic;
          if (!result.isForbidden) {
            $scope.data = QueryUtils.endpoint('/public/academicCalendar/' + $scope.schoolId).search();
          }
        });
      } else {
        $scope.data = QueryUtils.endpoint('/academicCalendar/' + $scope.schoolId).search();
        $scope.isForbidden = false;
      }
    }
  }
]).controller('AcademicCalendarSchoolListController', ['$scope', 'School', '$location',
  function($scope, School, $location) {
    $scope.schools = School.getSchoolsWithLogo();

    $scope.openSchoolAcademicCalendar = function (schoolId) {
      $location.path('academicCalendar/' + schoolId);
    };
  }
]);
