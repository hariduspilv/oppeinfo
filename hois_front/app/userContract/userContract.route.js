'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/userContract', {
      templateUrl: 'userContract/userContract.settings.html',
      controller: 'UserContractSettingsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TINGIMUS]
      }
    }).when('/userContract/users', {
      templateUrl: 'userContract/userContract.users.html',
      controller: 'UserContractStudentsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TINGIMUS]
      }
    });
});
