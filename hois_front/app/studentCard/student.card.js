'use strict';

angular.module('hitsaOis').controller('StudentCardSearchController', ['$scope', '$q', 'QueryUtils', 'config', '$httpParamSerializer', '$route', 'dialogService', 'message', 'FormUtils',
  function ($scope, $q, QueryUtils, config, $httpParamSerializer, $route, dialogService, message, FormUtils) {
    $scope.currentNavItem = 'student.card.search';

    var auth = $route.current.locals.auth;
    $scope.canEdit = auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_PILET') !== -1;

    QueryUtils.createQueryForm($scope, '/studentCards', {order: 'p.lastname, p.firstname'});

    $scope.copiedCriteria = {};
    var _loadData = $scope.loadData;
    $scope.loadData = function () {
      FormUtils.withValidForm($scope.searchForm, function () {
        angular.copy($scope.criteria, $scope.copiedCriteria);
        _loadData();
      });
    };

    $q.all().then($scope.loadData);

    $scope.orderUrl = function(operation) {
      return config.apiUrl + '/studentCards/' + operation + '.zip?' + $httpParamSerializer($scope.copiedCriteria);
    };

    $scope.excelUrl = function() {
      return config.apiUrl + '/studentCards/excel.xlsx?' + $httpParamSerializer($scope.copiedCriteria);
    };

    $scope.orderRepetition = function (row) {
      dialogService.confirmDialog({prompt: 'student.card.operation.orderRepetition.prompt'}, function() {
        QueryUtils.endpoint("/studentCards/orderRepetition/" + row.studentId).save({}, function(result) {
          message.info('student.card.operation.orderRepetition.success');
          for (var key in result) {
            row[key] = result[key];
          }
        });
      });
    };
  }
]).controller('StudentCardManagementController', ['$scope', '$q', 'QueryUtils', 'dialogService', 'message', '$route', 'FormUtils', '$timeout',
  function ($scope, $q, QueryUtils, dialogService, message, $route, FormUtils, $timeout) {
    $scope.currentNavItem = 'student.card.management';

    var auth = $route.current.locals.auth;
    $scope.canEdit = auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_PILET') !== -1;

    QueryUtils.createQueryForm($scope, '/studentCards', {order: 'p.lastname, p.firstname'}, function () {
      if (angular.isDefined($scope.cards) && $scope.cards.$dirty === true ) {
        $scope.cards.$setPristine();
      }
    }, true);
    angular.extend($scope.criteria, {size: 100});

    $scope.copiedCriteria = {};
    var _loadData = $scope.loadData;
    $scope.loadData = function () {
      FormUtils.withValidForm($scope.searchForm, function () {
        angular.copy($scope.criteria, $scope.copiedCriteria);
        _loadData();
      });
    };

    $q.all().then($scope.loadData);

    var ignoreWindow = false;
    $scope.changePage = function () {
      if (ignoreWindow) {
        return;
      }
      if (angular.isDefined($scope.cards) && $scope.cards.$dirty === true ) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          $scope.loadData();
        }, function () {
          ignoreWindow = true;
          angular.copy($scope.copiedCriteria, $scope.criteria);
          $timeout(function () {
            ignoreWindow = false;
          }, 50);
        });
      } else {
        $scope.loadData();
      }
    };

    $scope.save = function () {
      FormUtils.withValidForm($scope.cards, function () {
        QueryUtils.endpoint("/studentCards").save($scope.tabledata.content.filter(function (r) {
          return r.dirty;
        })).$promise.then(function () {
          message.info("main.messages.update.success");
          $scope.loadData();
        }).catch($scope.loadData);
      });
    };
  }
]);