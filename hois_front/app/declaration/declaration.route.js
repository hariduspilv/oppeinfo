'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/declarations', {
        templateUrl: 'declaration/declaration.search.html',
        controller: 'DeclarationSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      })
      .when('/declarations/:id/view', {
        templateUrl: 'declaration/declaration.view.html',
        controller: 'DeclarationViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      })
      .when('/declaration/current/edit', {
        templateUrl: 'declaration/declaration.edit.html',
        controller: 'DeclarationEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      })
      .when('/declarations/:id/edit', {
        templateUrl: 'declaration/declaration.edit.html',
        controller: 'DeclarationEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      })
    .when('/declaration/current/view', {
        templateUrl: 'declaration/declaration.view.html',
        controller: 'DeclarationViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      }).when('/declarations/previous', {
        templateUrl: 'declaration/declaration.student.search.html',
        controller: 'DeclarationStudentSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      }).when('/declarations/new', {
        templateUrl: 'declaration/declaration.new.html',
        controller: 'DeclarationNewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA]
        }
      });
}]);
