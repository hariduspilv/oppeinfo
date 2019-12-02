'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/midtermResult', {
      templateUrl: 'midtermResult/midtermResult.search.html',
      controller: 'MidtermResultSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode === 'ROLL_T';
        }
      }
    }).when('/midtermResult/:id', {
      templateUrl: 'midtermResult/midtermResult.view.html',
      controller: 'MidtermResultViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode === 'ROLL_T';
        }
      }
    });
}]);
