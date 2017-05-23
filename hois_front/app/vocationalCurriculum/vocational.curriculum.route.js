'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
      .when('/vocationalCurriculum/new', {
        templateUrl: 'vocationalCurriculum/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/edit', {
        templateUrl: 'vocationalCurriculum/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/view', {
        templateUrl: 'vocationalCurriculum/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/edit/moduleImplementationPlan/new', {
        templateUrl: 'vocationalCurriculum/module.implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          },
          copy: function($routeParams) {
            return $routeParams.implementationPlanCopy;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/edit/moduleImplementationPlan/:versionId/edit', {
        templateUrl: 'vocationalCurriculum/module.implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/view/moduleImplementationPlan/:versionId/view', {
        templateUrl: 'vocationalCurriculum/module.implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
