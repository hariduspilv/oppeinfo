'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/generalmessages', {
      templateUrl: 'generalMessage/general.message.list.html',
      controller: 'GeneralMessageSearchController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE]
      }
    })
    .when('/generalmessages/new', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE]
      }
    })
    .when('/generalmessages/:id/edit', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE]
      }
    })
    .when('/generalmessages/:id/view', {
      templateUrl: 'generalMessage/general.message.view.html',
      controller: 'GeneralMessageViewController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE]
      }
    });
}]);
