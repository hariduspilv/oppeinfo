'use strict';

angular.module('hitsaOis').controller('ContractViewController', function ($scope, $route, QueryUtils, Classifier, message, $location, dialogService) {
  $scope.auth = $route.current.locals.auth;
  $scope.contract = $route.current.locals.entity;
});
