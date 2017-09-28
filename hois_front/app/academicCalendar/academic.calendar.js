'use strict';

angular.module('hitsaOis').controller('AcademicCalendarController', ['$scope', 'QueryUtils',
  function($scope, QueryUtils) {
      $scope.data = QueryUtils.endpoint('/academicCalendar').search();
  }
]);
