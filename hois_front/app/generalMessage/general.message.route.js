'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function canEdit(Session, roles) {
    return roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_YLDTEADE) || Session.roleCode === 'ROLL_P';
  }

  function canView(Session, roles) {
    return roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_YLDTEADE) || Session.roleCode === 'ROLL_P';
  }

  $routeProvider
    .when('/generalmessages', {
      templateUrl: 'generalMessage/general.message.list.html',
      controller: 'GeneralMessageSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: canView
      }
    })
    .when('/generalmessages/new', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: canEdit
      }
    })
    .when('/generalmessages/:id/edit', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: canEdit
      }
    })
    .when('/generalmessages/:id/view', {
      templateUrl: 'generalMessage/general.message.view.html',
      controller: 'GeneralMessageViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: canView
      }
    });
}]);
