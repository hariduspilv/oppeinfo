'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/ekis/logs', {
      templateUrl: 'logs/logs.view.html',
      controller: 'LogsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        logType: function() { return 'ekis'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EKIS]
      }
    }).when('/kutseregister/logs', {
      templateUrl: 'logs/logs.view.html',
      controller: 'LogsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        logType: function() { return 'kutseregister'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER]
      }
    }).when('/rtip/logs', {
      templateUrl: 'logs/logs.view.html',
      controller: 'LogsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        logType: function() { return 'rtip'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_RTIP]
      }
    }).when('/sais/logs', {
      templateUrl: 'logs/logs.view.html',
      controller: 'LogsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        logType: function() { return 'sais'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS]
      }
    });
}]);
