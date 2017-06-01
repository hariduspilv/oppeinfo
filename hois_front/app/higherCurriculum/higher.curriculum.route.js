'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/higherCurriculum/new', {
        templateUrl: 'higherCurriculum/higher.curriculum.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      })
      .when('/higherCurriculum/:id/edit', {
        templateUrl: 'higherCurriculum/higher.curriculum.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      })
      .when('/higherCurriculum/:id/view', {
        templateUrl: 'higherCurriculum/higher.curriculum.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      }).when('/higherCurriculum/:id/version/new', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },          
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          },
          copy: function($routeParams) {
            return $routeParams.versionCopy;
          }
        },
        data: authorizedRoles
      }).when('/higherCurriculum/:id/version/:versionId/edit', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          },
          copy: function($routeParams) {
            return $routeParams.versionCopy;
          }
        },
        data: authorizedRoles
      }).when('/higherCurriculum/:id/version/:versionId/view', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          },
          copy: function($routeParams) {
            return $routeParams.versionCopy;
          }
        },
        data: authorizedRoles
      });
}]);
