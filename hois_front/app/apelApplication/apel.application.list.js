'use strict';

angular.module('hitsaOis').controller('ApelApplicationListController', function ($scope, $route, QueryUtils, Classifier, $q) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({
    status: 'VOTA_STAATUS'
  });

  QueryUtils.createQueryForm($scope, '/apelApplications', {
    order: 'student_lastname, student_firstname, inserted desc'
  });
  $q.all(clMapper.promises).then($scope.loadData);

  if ($scope.auth.isStudent()) {
    $scope.afterLoadData = function (resultData) {
      $scope.tabledata.content = resultData.content;
      $scope.tabledata.totalElements = resultData.totalElements;
    };
  }

  if ($scope.criteria && !$scope.criteria.status && !$scope.auth.isStudent()) {
    $scope.criteria.status = ["VOTA_STAATUS_E"];
  }

  $scope.$watch('criteria.studentObject', function () {
    $scope.criteria.student = $scope.criteria.studentObject ? $scope.criteria.studentObject.id : null;
  });

  $scope.clearCriteria = function () {
    $scope.criteria = {};
  };
});
