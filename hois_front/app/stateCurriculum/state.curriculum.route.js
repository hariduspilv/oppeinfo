'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/stateCurriculum/new', {
      templateUrl: 'stateCurriculum/state.curriculum.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_RIIKLIKOPPEKAVA]
      }
    })
    .when('/stateCurriculum/:id/edit', {
      templateUrl: 'stateCurriculum/state.curriculum.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_RIIKLIKOPPEKAVA]
      }
    })
    .when('/stateCurriculum/:id/view', {
      templateUrl: 'stateCurriculum/state.curriculum.html',
      controller: 'StateCurriculumController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
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
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA]
      }
    });
}]);
