'use strict';

angular.module('hitsaOis').controller('LogsController', ['$mdDialog', '$route', '$scope', 'QueryUtils',
  function ($mdDialog, $route, $scope, QueryUtils) {
    $scope.auth = $route.current.locals.auth;

    var messageTypes = {
      ekis: ['registerDirective', 'deleteDirective', 'registerCertificate', 'registerPracticeContract', 'cancelPracticeContract','enforceContract', 'rejectContract', 'enforceDirective', 'rejectDirective'],
      kutseregister: [$scope.auth.roleCode === 'ROLL_P' ? 'kutseregister.muutunudKutsestandardid' : 'kutseregister.kutsetunnistus'],
      rtip: ['sap.Z_EMPLOEES', 'sap.tootajaPohiandmed'],
      sais: ['sais2.AllAdmissionsExport', 'sais2.AllApplicationsExport', 'sais2.AllApplicationsExportv2'],
      rr: ['rr.RR434']
    };

    $scope.convertType = {
      'sais2.AllApplicationsExportv2': 'sais2.AllApplicationsExport'
    };

    var logType = $route.current.locals.logType, messagePrefix = 'logs.' + logType + '.';
    $scope.logType = logType;
    $scope.messagePrefix = messagePrefix;
    $scope.messageTypes = (messageTypes[logType] || []).reduce(function (acc, it) {
      var item = !!$scope.convertType[it] ? $scope.convertType[it] : it;
      if (acc.indexOf(item) === -1) {
        acc.push(item);
      }
      return acc;
    }, []);

    var baseUrl = '/logs/' + logType;
    QueryUtils.createQueryForm($scope, baseUrl, {order: '-inserted'});
    $scope.loadData();

    $scope.logentry = function(row) {
      var convertType = $scope.convertType;
      $mdDialog.show({
        controller: function($scope) {
          var initialLength = 1000;
          $scope.requestLength = $scope.responseLength = initialLength;
          $scope.cancel = $mdDialog.hide;
          $scope.logType = logType;
          $scope.messagePrefix = messagePrefix;
          $scope.convertType = convertType;

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
        templateUrl: 'logs/logentry.view.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]);
