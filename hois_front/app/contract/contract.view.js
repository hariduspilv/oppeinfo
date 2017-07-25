'use strict';

angular.module('hitsaOis').controller('ContractViewController', function ($scope, $route) {
  $scope.auth = $route.current.locals.auth;
  $scope.contract = $route.current.locals.entity;
});
