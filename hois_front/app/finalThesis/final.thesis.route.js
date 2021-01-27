'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/finalThesis', {
      templateUrl: 'finalThesis/final.thesis.list.html',
      controller: 'FinalThesisListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isEdit: false}; }
        },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA]}
    })
    .when('/finalThesis/new', {
      templateUrl: 'finalThesis/final.thesis.edit.html',
      controller: 'FinalThesisEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isEdit: true}; }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPTEEMA]}
    })
    .when('/finalThesis/:id/edit', {
      templateUrl: 'finalThesis/final.thesis.edit.html',
      controller: 'FinalThesisEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalThesis').get({id: $route.current.params.id}).$promise;
        },
        params: function() { return {isEdit: true}; }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPTEEMA]}
    })
    .when('/finalThesis/:id/view', {
      templateUrl: 'finalThesis/final.thesis.view.html',
      controller: 'FinalThesisEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalThesis').get({id: $route.current.params.id}).$promise;
        },
        params: function() { return {isEdit: false}; }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA]}
    });
}]);
