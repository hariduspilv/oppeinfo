'use strict';

angular.module('hitsaOis').controller('EhisLogsController', ['$mdDialog', '$route', '$scope', 'QueryUtils', 'Session',
  function ($mdDialog, $route, $scope, QueryUtils, Session) {
    $scope.auth = $route.current.locals.auth;

	  var school = Session.school || {};
    $scope.messageTypes = ['laeKorgharidus', 'oisOppekava', 'oisOppekavaStaatus'];
    if(school.higher) {
      $scope.messageTypes.push('laeOppejoud');
    }
    if(school.vocational) {
      $scope.messageTypes.push('laePedagoogid');
      $scope.messageTypes.push('innoveAjalugu');
    }
    $scope.messageTypesWithTeacher = ['laeOppejoud', 'laePedagoogid'];

    var baseUrl = '/logs/ehis';
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
        templateUrl: 'ehis/logentry.view.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
