'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/lessonplans/vocational', {
      templateUrl: 'lessonplan/vocational.search.html',
      controller: 'LessonplanSearchController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); }},
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A], currentNavItem: 'lessonplan.vocational'
      }
    })
    .when('/lessonplans/vocational/byteacher', {
      templateUrl: 'lessonplan/vocational.teacher.search.html',
      controller: 'LessonplanSearchController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); }},
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A], currentNavItem: 'lessonplan.vocational-byteacher'
      }
    })
    .when('/lessonplans/vocational/:id/edit', {
      templateUrl: 'lessonplan/vocational.edit.html',
      controller: 'LessonplanEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); }},
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A], currentNavItem: 'lessonplan.vocational'
      }
    })
    .when('/lessonplans/journals/new', {
      templateUrl: 'lessonplan/journal.edit.html',
      controller: 'LessonplanJournalEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); }},
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/lessonplans/journals/:id/edit', {
      templateUrl: 'lessonplan/journal.edit.html',
      controller: 'LessonplanJournalEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); }},
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
