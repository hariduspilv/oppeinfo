'use strict';

angular.module('hitsaOis').controller('RtipSyncController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    $scope.rtipCriteria = {};
    $scope.kutseregisterCriteria = {};

    $scope.rtipSync = function() {
      QueryUtils.endpoint('/logs/rtip/sync').post();
    };

    $scope.rtipZemploees = function() {
      $scope.rtipSyncForm.$setSubmitted();
      if(!$scope.rtipSyncForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }

      QueryUtils.endpoint('/logs/rtip/zemploees').post($scope.rtipCriteria);
    };

    $scope.occupationStandards = function() {
      $scope.kutseregisterSyncForm.$setSubmitted();
      if(!$scope.kutseregisterSyncForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }

      QueryUtils.endpoint('/logs/kutseregister/sync').post($scope.kutseregisterCriteria);
    };
  }
]);
