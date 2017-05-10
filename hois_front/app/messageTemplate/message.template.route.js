'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/messageTemplate', {
        templateUrl: 'messageTemplate/message.template.search.html',
        controller: 'MessageTemplateListController',
        controllerAs: 'controller',
        resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/messageTemplate/new', {
        templateUrl: 'messageTemplate/message.template.edit.html',
        controller: 'MessageTemplateEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/messageTemplate/:id/edit', {
        templateUrl: 'messageTemplate/message.template.edit.html',
        controller: 'MessageTemplateEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/messageTemplate/:id/view', {
        templateUrl: 'messageTemplate/message.template.edit.html',
        controller: 'MessageTemplateEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
