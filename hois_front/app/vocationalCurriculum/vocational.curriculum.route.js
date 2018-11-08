'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function checkRightsToEdit(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
    AuthResolver.resolve().then(function(auth){
      if(!auth.isAdmin() || !ArrayUtils.includes(auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA)) {
        message.error('main.messages.error.nopermission');
        $location.path('');
      }
    });
  }

  $routeProvider
      .when('/vocationalCurriculum/new', {
        templateUrl: 'vocationalCurriculum/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
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
          },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
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
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
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
          },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
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
          },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      })
      .when('/vocationalCurriculum/:id/moduleImplementationPlan/:versionId/view', {
        templateUrl: 'vocationalCurriculum/implementationPlan/implementation.plan.view.html',
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
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
        }
      }).when('/vocationalCurriculum/:curriculum/module/new', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.edit.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/vocationalCurriculum/:curriculum/module/:id/view', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.view.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
        }
      }).when('/vocationalCurriculum/:curriculum/module/:id/edit', {
        templateUrl: 'vocationalCurriculum/module/vocational.curriculum.module.edit.html',
        controller: 'VocationalCurriculumModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/new', {
       templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.edit.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/:occupationModule/edit', {
        templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.edit.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/occupationModule/:curriculum/:version/:curriculumModule/:occupationModule/view', {
        templateUrl: 'vocationalCurriculum/implementationPlan/occupation.module.view.html',
        controller: 'VocationalCurriculumVersionOccupationModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
        }
      });
}]);
