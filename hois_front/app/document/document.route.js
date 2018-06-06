'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/documents/diplomas', {
      templateUrl: 'document/diploma.html',
      controller: 'DiplomaController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
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
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
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
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE]
      }
    });
}]);
