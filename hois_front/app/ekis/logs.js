'use strict';

angular.module('hitsaOis').controller('EkisLogsController', ['$mdDialog', '$scope', 'QueryUtils',
  function ($mdDialog, $scope, QueryUtils) {

    $scope.messageTypes = ['registerDirective', 'deleteDirective', 'registerCertificate', 'registerPracticeContract',
      'enforceContract', 'enforceDirective', 'rejectDirective'];

    var baseUrl = '/logs/ekis';
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
        templateUrl: 'ekis/logentry.view.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
