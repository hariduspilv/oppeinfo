'use strict';

angular.module('hitsaOis').controller('AcademicCalendarController', ['$scope', '$route', 'QueryUtils',
  function($scope, $route, QueryUtils) {
    $scope.auth = $route.current.locals.auth;

    $scope.schoolSpecific = angular.isUndefined($route.current.params.schoolId);
    $scope.schoolId = ($scope.auth === undefined || !$scope.auth.school) ? $route.current.params.schoolId : $scope.auth.school.id;
    $scope.data = QueryUtils.endpoint('/academicCalendar/' + $scope.schoolId).search();
  }
]).controller('AcademicCalendarSchoolListController', ['$scope', 'School', '$location',
  function($scope, School, $location) {
    $scope.schools = School.getSchoolsWithLogo();

    $scope.openSchoolAcademicCalendar = function (schoolId) {
      $location.path('academicCalendar/' + schoolId);
    };
  }
]);
