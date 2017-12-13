'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function checkRightsToEdit(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
    AuthResolver.resolve().then(function(auth){
      if(!auth.isAdmin() ||!ArrayUtils.includes(auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA)) {
        message.error('main.messages.error.nopermission');
        $location.path('');
      }
    });
  }

  $routeProvider
    .when('/higherCurriculum/new', {
        templateUrl: 'higherCurriculum/higher.curriculum.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
        }
      })
      .when('/higherCurriculum/:id/edit', {
        templateUrl: 'higherCurriculum/higher.curriculum.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
        }
      })
      .when('/higherCurriculum/:id/view', {
        templateUrl: 'higherCurriculum/higher.curriculum.view.html',
        controller: 'HigherCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
          }
      }).when('/higherCurriculum/:id/version/new', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
          }
        }).when('/higherCurriculum/:id/version/:versionId/edit', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:id/version/:versionId/view', {
        templateUrl: 'higherCurriculum/higher.curriculum.version.view.html',
        controller: 'HigherCurriculumVersionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          curriculum: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculumVersion/curriculum').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/module/new', {
        templateUrl: 'higherCurriculum/version/higher.module.edit.html',
        controller: 'HigherModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/module/:id/edit', {
        templateUrl: 'higherCurriculum/version/higher.module.edit.html',
        controller: 'HigherModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/module/:id/view', {
        templateUrl: 'higherCurriculum/version/higher.module.view.html',
        controller: 'HigherModuleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/minorSpeciality/new', {
        templateUrl: 'higherCurriculum/version/minor.speciality.edit.html',
        controller: 'MinorSpecialityController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/minorSpeciality/:id/edit', {
        templateUrl: 'higherCurriculum/version/minor.speciality.edit.html',
        controller: 'MinorSpecialityController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          checkAccess: checkRightsToEdit
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA]
        }
      }).when('/higherCurriculum/:curriculumId/version/:versionId/minorSpeciality/:id/view', {
        templateUrl: 'higherCurriculum/version/minor.speciality.view.html',
        controller: 'MinorSpecialityController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
        }
      });
}]);
