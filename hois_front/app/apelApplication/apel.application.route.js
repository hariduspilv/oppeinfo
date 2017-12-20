'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/apelApplication', {
        templateUrl: 'apelApplication/apel.application.list.html',
        controller: 'ApelApplicationListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelApplication/new', {
        templateUrl: 'apelApplication/apel.application.edit.html',
        controller: 'ApelApplicationEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelApplication/:id/edit', {
        templateUrl: 'apelApplication/apel.application.edit.html',
        controller: 'ApelApplicationEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/apelApplications').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelApplication/:id/view', {
        templateUrl: 'apelApplication/apel.application.view.html',
        controller: 'ApelApplicationViewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/apelApplications').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelSchools', {
        templateUrl: 'apelApplication/apel.school.list.html',
        controller: 'ApelSchoolListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelSchools/:id/edit', {
        templateUrl: 'apelApplication/apel.school.edit.html',
        controller: 'ApelSchoolEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      })
      .when('/apelSchools/new', {
        templateUrl: 'apelApplication/apel.school.edit.html',
        controller: 'ApelSchoolEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA]
        }
      });
}]);