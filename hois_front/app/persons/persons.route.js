'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]};
  $routeProvider
    .when('/persons', {
      templateUrl: 'persons/persons.search.html',
      controller: 'UsersSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    })
    .when('/persons/new', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    })
    .when('/persons/:id/edit', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: authorizedRoles
    });
}]);
