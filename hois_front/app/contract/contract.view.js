'use strict';

angular.module('hitsaOis').controller('ContractViewController', function ($scope, $route, ekisService) {
  $scope.auth = $route.current.locals.auth;
  $scope.contract = $route.current.locals.entity;
  $scope.getEkisContractUrl = ekisService.getContractUrl;
});
