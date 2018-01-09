'use strict';

angular.module('hitsaOis').controller('KutseregisterSyncController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    $scope.criteria = {};

    $scope.occupationStandards = function() {
      $scope.kutseregisterSyncForm.$setSubmitted();
      if(!$scope.kutseregisterSyncForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }

      QueryUtils.endpoint('/logs/kutseregister/sync').post($scope.criteria, function() {
        message.info('occupationcertificate.importDone');
      });
    };
  }
]);
