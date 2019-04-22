'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function checkRightsToEdit(Session, roles) {
    return ['ROLL_P', 'ROLL_V'].indexOf(Session.roleCode) !== -1 && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_RIIKLIKOPPEKAVA) !== -1;
  }

  $routeProvider
    .when('/stateCurriculum/new', {
      templateUrl: 'stateCurriculum/state.curriculum.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: checkRightsToEdit
      }
    })
    .when('/stateCurriculum/:id/edit', {
      templateUrl: 'stateCurriculum/state.curriculum.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: checkRightsToEdit
      }
    })
    .when('/stateCurriculum/:id/view', {
      templateUrl: 'stateCurriculum/state.curriculum.view.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: { 
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
      }
    })
    .when('/stateCurriculum', {
      templateUrl: 'stateCurriculum/state.curriculum.list.html',
      controller: 'StateCurriculumListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/stateCurriculum/public', {
      templateUrl: 'stateCurriculum/state.curriculum.list.html',
      controller: 'StateCurriculumListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        params: function () { return { isPublic: true }; }
      }
    })
    .when('/stateCurriculum/public/:id/view', {
      templateUrl: 'stateCurriculum/state.curriculum.view.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        params: function () { return { isPublic: true }; }
      }
    });
}]);
