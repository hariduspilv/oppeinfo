'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]};
  $routeProvider
    .when('/users', {
      templateUrl: 'users/users.search.html',
      controller: 'UsersSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    })
    .when('/users/new', {
      templateUrl: 'users/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    })
    .when('/users/:id/edit', {
      templateUrl: 'users/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    });
}]);
