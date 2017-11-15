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
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/view', {
        templateUrl: 'vocationalCurriculum/vocational.curriculum.view.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/moduleImplementationPlan/new', {
        templateUrl: 'vocationalCurriculum/implementationPlan/implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/moduleImplementationPlan/:versionId/edit', {
        templateUrl: 'vocationalCurriculum/implementationPlan/implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          },
          curriculumVersion: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion').get({id: $route.current.params.versionId}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/vocationalCurriculum/:id/moduleImplementationPlan/:versionId/view', {
        templateUrl: 'vocationalCurriculum/implementationPlan/implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          },
          curriculumVersion: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion').get({id: $route.current.params.versionId}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/vocationalCurriculum/:curriculum/module/new', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.edit.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/vocationalCurriculum/:curriculum/module/:id/view', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.view.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/vocationalCurriculum/:curriculum/module/:id/edit', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.edit.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/new', {
       templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.edit.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/:occupationModule/edit', {
        templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.edit.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/:occupationModule/view', {
        templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.view.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
