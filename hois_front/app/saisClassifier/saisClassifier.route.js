'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
  .when('/saisClassifier', {
        templateUrl: 'saisClassifier/saisClassifier.list.html',
        controller: 'SaisClassifierListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR]
        }
      })
      .when('/saisClassifier/:code', {
        templateUrl: 'saisClassifier/saisClassifier.content.list.html',
        controller: 'SimpleListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          params: function() { return {order: 'value'}; },
          url: function($route) { return '/saisClassifier/' + $route.current.params.code; }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      });
}]);
