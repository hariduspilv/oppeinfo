'use strict';

angular.module('hitsaOis').controller('EhisLogsController', ['$mdDialog', '$scope', 'QueryUtils',
  function ($mdDialog, $scope, QueryUtils) {

    $scope.messageTypes = ['laeKorgharidus', 'laeOppejoud'];

    var baseUrl = '/logs/ehis';
    QueryUtils.createQueryForm($scope, baseUrl, {order: '-inserted'});
    $scope.loadData();

    $scope.logentry = function(row) {
      $mdDialog.show({
        controller: function($scope) {
          $scope.requestLength = $scope.responseLength = 1000;
          $scope.cancel = $mdDialog.hide;
          $scope.record = QueryUtils.endpoint(baseUrl).get({id: row.id, messageType: row.wsName});

          $scope.showAll = function(request) {
            if(request) {
              $scope.requestLength = undefined;
            } else {
              $scope.responseLength = undefined;
            }
          }
        },
        templateUrl: 'ehis/logentry.view.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
