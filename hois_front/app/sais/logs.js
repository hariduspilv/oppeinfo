'use strict';

angular.module('hitsaOis').controller('SaisLogsController', ['$mdDialog', '$scope', 'QueryUtils',
  function ($mdDialog, $scope, QueryUtils) {

    $scope.messageTypes = ['laeKorgharidus', 'oisOppekava', 'oisOppekavaStaatus'];

    var baseUrl = '/logs/sais';
    QueryUtils.createQueryForm($scope, baseUrl, {order: '-inserted'});
    $scope.loadData();

    $scope.logentry = function(row) {
      $mdDialog.show({
        controller: function($scope) {
          var initialLength = 1000;
          $scope.requestLength = $scope.responseLength = initialLength;
          $scope.cancel = $mdDialog.hide;

          $scope.showAll = function(request) {
            if(request) {
              $scope.requestLength = undefined;
            } else {
              $scope.responseLength = undefined;
            }
          };

          $scope.record = QueryUtils.endpoint(baseUrl).get({id: row.id, messageType: row.wsName}, function(res) {
            if(!res.request || res.request.length < initialLength) {
              $scope.showAll(true);
            }
            if(!res.response || res.response.length < initialLength) {
              $scope.showAll(false);
            }
          });
        },
        templateUrl: 'sais/logentry.view.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
