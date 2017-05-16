'use strict';

angular.module('hitsaOis').controller('SaisClassifierListController', ['$scope', 'message', 'QueryUtils', '$mdDialog',
  function ($scope, message, QueryUtils, $mdDialog) {

    $scope.saisImport = function() {
      QueryUtils.endpoint('/saisClassifier/saisImport').save().$promise.then(function() {
        message.info('saisClassifier.importFinished');
        $mdDialog.hide();
      }).catch(function() {
        $mdDialog.hide();
      });
      var parentEl = angular.element(document.body);
      $mdDialog.show({
        parent: parentEl,
        template: 
          '<md-dialog style="background-color:transparent;box-shadow:none">' +
          '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait" style="height:120px;">'+
          '<md-progress-circular class="md-hue-2" md-diameter="80px" class="loader"></md-progress-circular>'+
          '</div>' +
          '</md-dialog>'
      });
    };
    QueryUtils.createQueryForm($scope, '/saisClassifier', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
    $scope.loadData();
  }
]);
