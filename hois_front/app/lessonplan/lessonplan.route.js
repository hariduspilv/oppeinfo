'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/lessonplans/vocational', {
      templateUrl: 'lessonplan/vocational.search.html',
      controller: 'LessonplanSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles, ArrayUtils) {
          return Session.roleCode === 'ROLL_A' && ArrayUtils.intersect(roles,
              [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA]);
        }, 
        currentNavItem: 'lessonplan.vocational'
      }
    })
    .when('/lessonplans/vocational/byteacher', {
      templateUrl: 'lessonplan/vocational.teacher.search.html',
      controller: 'LessonplanTeacherSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN,
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA
        ],
        currentNavItem: 'lessonplan.vocational-byteacher'
      }
    })
    .when('/lessonplans/vocational/:id/edit', {
      templateUrl: 'lessonplan/vocational.edit.html',
      controller: 'LessonplanEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN], currentNavItem: 'lessonplan.vocational'
      }
    })
    .when('/lessonplans/vocational/byteacher/:id/:studyYear', {
      templateUrl: 'lessonplan/vocational.teacher.view.html',
      controller: 'LessonplanTeacherViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN,
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA
        ]
      }
    })
    .when('/lessonplans/journals/new', {
      templateUrl: 'lessonplan/journal.edit.html',
      controller: 'LessonplanJournalEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
      }
    })
    .when('/lessonplans/journals/:id/edit', {
      templateUrl: 'lessonplan/journal.edit.html',
      controller: 'LessonplanJournalEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
      }
    })
    .when('/lessonplans/events', {
      templateUrl: 'lessonplan/event.search.html',
      controller: 'LessonplanEventSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS]
      }
    });
}]);
