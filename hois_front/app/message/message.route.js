'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/messages/sent', {
        templateUrl: 'message/message.sent.html',
        controller: 'messageSentController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TEADE]
        }
      }).when('/messages/automatic/sent', {
        templateUrl: 'message/message.sent.html',
        controller: 'messageAutomaticSentController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE]
        }
      }).when('/messages/received', {
        templateUrl: 'message/message.received.html',
        controller: 'messageReceivedController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TEADE]
        }
      }).when('/message/:id/view', {
        templateUrl: 'message/message.view.html',
        controller: 'messageViewController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TEADE]
        }
      }).when('/message/:id/respond', {
        templateUrl: 'message/message.respond.html',
        controller: 'messageRespondController',
        controllerAs: 'controller',
        resolve: {translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TEADE]
        }
      }).when('/message/new', {
        templateUrl: 'message/message.new.html',
        controller: 'messageNewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TEADE]
        }
      });
}]);