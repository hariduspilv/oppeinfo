'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
  // TODO: fix this ordering for some reason it used the lower one
    .when('/directives/coordinators', {
      templateUrl: 'directive/coordinator.search.html',
      controller: 'DirectiveCoordinatorSearchController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives', {
      templateUrl: 'directive/list.html',
      controller: 'DirectiveSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/new', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/:id/edit', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/:id/view', {
      templateUrl: 'directive/directive.view.html',
      controller: 'DirectiveViewController',
      controllerAs: 'controller',
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