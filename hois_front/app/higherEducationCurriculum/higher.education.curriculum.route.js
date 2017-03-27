'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/higherEducationCurriculum/new', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      })
      .when('/higherEducationCurriculum/:id/edit', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      })
      .when('/higherEducationCurriculum/:id/view', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: authorizedRoles
      }).when('/higherEducationCurriculum/:id/version/new', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.version.html',
        controller: 'HigherEducationCurriculumVersionController',
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
      }).when('/higherEducationCurriculum/:id/version/:versionId/edit', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.version.html',
        controller: 'HigherEducationCurriculumVersionController',
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
      }).when('/higherEducationCurriculum/:id/version/:versionId/view', {
        templateUrl: 'higherEducationCurriculum/higher.education.curriculum.version.html',
        controller: 'HigherEducationCurriculumVersionController',
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
