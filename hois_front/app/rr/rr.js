'use strict';

angular.module('hitsaOis').controller('RRChangeLogsController', ['$scope', 'QueryUtils',
  function ($scope, QueryUtils) {
    var baseUrl = "/logs/rr/changelogs";
    QueryUtils.createQueryForm($scope, baseUrl);
    angular.extend($scope.criteria, {order: "-wrcl.inserted"});
    if (!angular.isObject($scope.criteria.student)) {
      $scope.criteria.student = null;
    }
    $scope.loadData();
  }
]);
