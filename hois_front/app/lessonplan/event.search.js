'use strict';

angular.module('hitsaOis').controller('LessonplanEventSearchController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {
  $scope.formState = {};
  var baseUrl = '/timetableevents';
  
  
  QueryUtils.endpoint(baseUrl+'/searchFormData').search().$promise.then(function(result) {
    $scope.formState.studentGroups =  result.studentGroups;
    $scope.formState.studyPeriods = result.studyPeriods;
    $scope.formState.teachers = result.teachers;
    $scope.criteria.singleEvent = true;
  });


  $scope.clearCriteria = function() {
    $scope.criteria = {};
    $scope.criteria.singleEvent = true;
  }
}
]);
