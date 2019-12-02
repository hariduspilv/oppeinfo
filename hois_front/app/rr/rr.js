'use strict';

angular.module('hitsaOis').controller('RRChangeLogsController', ['$route', '$scope', 'QueryUtils',
  function ($route, $scope, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var baseUrl = "/logs/rr/changelogs";
    QueryUtils.createQueryForm($scope, baseUrl);
    angular.extend($scope.criteria, {order: "-wrcl.inserted"});
    if (!angular.isObject($scope.criteria.student)) {
      $scope.criteria.student = null;
    }
    $scope.loadData();
  }
]);
