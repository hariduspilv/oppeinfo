'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/documents', {
      templateUrl: 'document/diploma.search.html',
      controller: 'DiplomaSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE]
      }
    })
    .when('/documents/diplomas', {
      templateUrl: 'document/diploma.html',
      controller: 'DiplomaController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: true}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE]
      }
    })
    .when('/documents/diplomas/vocational', {
      templateUrl: 'document/diploma.html',
      controller: 'DiplomaController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: false}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE]
      }
    })
    .when('/documents/supplements', {
      templateUrl: 'document/supplement.search.html',
      controller: 'SupplementSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: true}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE]
      }
    })
    .when('/documents/supplements/vocational', {
      templateUrl: 'document/supplement.search.html',
      controller: 'SupplementSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: false}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE]
      }
    })
    .when('/documents/supplements/:id', {
      templateUrl: 'document/supplement.html',
      controller: 'SupplementController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: true}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE]
      }
    })
    .when('/documents/supplements/vocational/:id', {
      templateUrl: 'document/supplement.vocational.html',
      controller: 'SupplementController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {isHigher: false}; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE]
      }
    });
}]);
