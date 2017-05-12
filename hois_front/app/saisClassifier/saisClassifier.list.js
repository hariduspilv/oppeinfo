'use strict';

angular.module('hitsaOis').controller('SaisClassifierListController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    $scope.saisImport = function() {
      QueryUtils.endpoint('/saisClassifier/saisImport').save().$promise.then(function() {
        message.info('sais.importStarted');
      });
    };

    QueryUtils.createQueryForm($scope, '/saisClassifier', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
    $scope.loadData();
  }
]);
