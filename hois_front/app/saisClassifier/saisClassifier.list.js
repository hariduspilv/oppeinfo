'use strict';

angular.module('hitsaOis').controller('SaisClassifierListController',
  ['$scope', 'message', 'QueryUtils', 'PollingService',
  function ($scope, message, QueryUtils, PollingService) {

    $scope.saisImport = function() {
      QueryUtils.loadingWheel($scope, true);
      PollingService.sendRequest({
        url: '/saisClassifier/saisImport',
        pollUrl: '/saisClassifier/saisImportStatus',
        successCallback: function (pollResult) {
          message.info('saisClassifier.importFinished');
          QueryUtils.loadingWheel($scope, false);
          $scope.loadData();
        },
        failCallback: function (pollResult) {
          if (pollResult) {
            message.error('main.async.taskStatus.' + pollResult.status, {error: pollResult.error});
          }
          QueryUtils.loadingWheel($scope, false);
          $scope.loadData();
        },
        updateProgress: function (pollResult) {
        }
      });
    };

    QueryUtils.createQueryForm($scope, '/saisClassifier', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
    $scope.loadData();
  }
]);
