'use strict';

angular.module('hitsaOis').controller('GeneralTimetableController', ['$scope', 'QueryUtils',
  function ($scope, QueryUtils) {
    $scope.timetables = QueryUtils.endpoint('/timetables/generalTimetables/').query();

    $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
      $scope.timetableId = timetableId;
      $scope.studyPeriodId = studyPeriodId;
      $scope.timetablesByCurriculums = QueryUtils.endpoint('/timetables/:id/view').search({id: timetableId});
    };
  }
]);