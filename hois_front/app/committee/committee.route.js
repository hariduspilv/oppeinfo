'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/committees/:type', {
        templateUrl: 'committee/committee.search.html',
        controller: 'CommitteeSearchController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON]
        }
      })
      .when('/committees/:type/new', {
        templateUrl: 'committee/committee.edit.html',
        controller: 'CommitteeEditViewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOMISJON]
        }
      })
      .when('/committees/:type/:id/edit', {
        templateUrl: 'committee/committee.edit.html',
        controller: 'CommitteeEditViewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOMISJON]
        }
      })
      .when('/committees/:type/:id/view', {
        templateUrl: 'committee/committee.view.html',
        controller: 'CommitteeEditViewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON]
        }
      });
}]);
