'use strict';

angular.module('hitsaOis').controller('ContractViewController', ['$scope', '$location', '$route', 'QueryUtils', 'dialogService', 'ekisService', 'message',
  function ($scope, $location, $route, QueryUtils, dialogService, ekisService, message) {
    $scope.auth = $route.current.locals.auth;
    $scope.contract = $route.current.locals.entity;
    $scope.getEkisContractUrl = ekisService.getContractUrl;

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'contract.deleteconfirm' }, function () {
        var ContractEndpoint = QueryUtils.endpoint('/contracts');
        var contract = new ContractEndpoint($scope.contract);
        contract.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.path('/contracts');
        });
      });
    };
  }
]);
