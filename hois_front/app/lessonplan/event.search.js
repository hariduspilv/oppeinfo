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
    $scope.loadData();
  });


  $scope.clearCriteria = function() {
    $scope.criteria = {};
    $scope.criteria.singleEvent = true;
  };
  
  $scope.$watch('criteria.roomObject', function() {
    $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
  });
  
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'});
}
]);
