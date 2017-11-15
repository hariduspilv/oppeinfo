'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
  // TODO: fix this ordering for some reason it used the lower one
    .when('/directives/coordinators', {
      templateUrl: 'directive/coordinator.search.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        params: function() { return {order: 'name'}; },
        url: function() { return '/directives/coordinators'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_DOKALLKIRI]
      }
    })
    .when('/directives', {
      templateUrl: 'directive/list.html',
      controller: 'DirectiveListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        clMapping: function() { return {type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'}; },
        params: function() { return {order: '-inserted'}; },
        url: function() { return '/directives'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI]
      }
    })
    .when('/directives/new', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/:id/edit', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/:id/view', {
      templateUrl: 'directive/directive.view.html',
      controller: 'DirectiveViewController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/coordinators/new', {
      templateUrl: 'directive/coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/coordinators/:id/edit', {
      templateUrl: 'directive/coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
