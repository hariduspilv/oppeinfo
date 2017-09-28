'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};
  $routeProvider
    .when('/persons', {
      templateUrl: 'persons/persons.search.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        clMapping: function() { return {role: 'ROLL'}; },
        params: function() { return {order: 'p.lastname,p.firstname'}; },
        url: function () { return '/users'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/new', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/persons/:person/users/new', {
      templateUrl: 'persons/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/persons/:person/users/:user/edit', {
      templateUrl: 'persons/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/persons/:person/users/:user', {
      templateUrl: 'persons/users.view.html',
      controller: 'UsersViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/persons/:id/edit', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/persons/:id', {
      templateUrl: 'persons/persons.view.html',
      controller: 'PersonsViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    });
}]);
