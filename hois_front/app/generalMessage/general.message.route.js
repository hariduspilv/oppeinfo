'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/generalmessages', {
      templateUrl: 'generalMessage/general.message.list.html',
      controller: 'GeneralMessageSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/generalmessages/new', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/generalmessages/:id', {
      templateUrl: 'generalMessage/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
