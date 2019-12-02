'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/directives/coordinators', {
      templateUrl: 'directive/coordinator.search.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
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
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI]
      }
    })
    .when('/directives/new', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASKKIRI) !== -1;
        }
      }
    })
    .when('/directives/:id/edit', {
      templateUrl: 'directive/directive.edit.html',
      controller: 'DirectiveEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI]
      }
    })
    .when('/directives/:id/view', {
      templateUrl: 'directive/directive.view.html',
      controller: 'DirectiveViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, authorizedRoles) {
          return Session.roleCode === 'ROLL_T' || Session.roleCode === 'ROLL_L' || (['ROLL_A', 'ROLL_J', 'ROLL_O'].indexOf(Session.roleCode) !== -1 &&
            authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI) !== -1);
        }
      }
    })
    .when('/directives/coordinators/new', {
      templateUrl: 'directive/coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_DOKALLKIRI]
      }
    })
    .when('/directives/coordinators/:id/edit', {
      templateUrl: 'directive/coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_DOKALLKIRI]
      }
    });
}]);
