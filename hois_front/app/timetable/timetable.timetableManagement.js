'use strict';

angular.module('hitsaOis').controller('TimetableManagementController', ['$scope', 'message', 'QueryUtils', 'DataUtils', 'Classifier', '$q',
  function ($scope, message, QueryUtils, DataUtils, Classifier, $q) {
  $scope.formState = {};
  var baseUrl = '/timetables';
  
  QueryUtils.endpoint(baseUrl+'/managementSearchFormData').search().$promise.then(function(result) {
    $scope.formState.studyYears =  result.studyYears;
    $scope.allStudyPeriods = result.studyPeriods;
    if(!$scope.criteria.studyYear) {
      var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
      if(sy) {
        $scope.criteria.studyYear = sy.id;
        $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function(t) { return t.studyYear === $scope.criteria.studyYear;});
        $scope.criteria.studyPeriod = result.currentStudyPeriod;
      }
    }
    $scope.higher = result.higher;
    $scope.vocational = result.vocational;
    if(!$scope.higher || !$scope.vocational) {
      if($scope.higher) {
        $scope.criteria.type = 'TUNNIPLAAN_LIIK_H';
      }
    }
    $scope.loadData();
  });
  
  $scope.$watch('criteria.studyYear', function() {
    if($scope.criteria.studyYear !== undefined && $scope.allStudyPeriods) {
      $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function(t) { return t.studyYear === $scope.criteria.studyYear;});
    }
  });
  
  var clMapper = Classifier.valuemapper({ status: 'TUNNIPLAAN_STAATUS' });
  QueryUtils.createQueryForm($scope, baseUrl + "/searchTimetableForManagement", {order: 'start_date'}, clMapper.objectmapper);
}
]);
