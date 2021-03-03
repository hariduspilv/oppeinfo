'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA]};

  $routeProvider
    .when('/teachers', {
      templateUrl: 'teacher/teacher.list.html',
      controller: 'TeacherListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/teachers/new', {
      templateUrl: 'teacher/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA]}
    })
    .when('/teachers/:id/edit', {
      templateUrl: 'teacher/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA]}
    })
    .when('/teachers/:id/editmydata', {
      templateUrl: 'teacher/teacher.edit.mydata.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA]}
    })
    .when('/teachers/:id/continuingEducation', {
      templateUrl: 'teacher/teacher.continuing.education.edit.html',
      controller: 'TeacherContinuingEducationEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/teachers/:id/qualification', {
      templateUrl: 'teacher/teacher.qualification.edit.html',
      controller: 'TeacherQualificationEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }},
      data: authorizedRoles
    })
    .when('/teachers/:id/mobility', {
      templateUrl: 'teacher/teacher.mobility.edit.html',
      controller: 'TeacherMobilityEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/teachers/:id/rtipAbsence', {
      templateUrl: 'teacher/teacher.rtip.absence.view.html',
      controller: 'TeacherRtipAbsenceEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/teachers/myData', {
      templateUrl: 'teacher/teacher.view.html',
      controller: 'TeacherViewController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    })
    .when('/teachers/:id/programs', {
      templateUrl: 'teacher/teacher.programs.html',
      controller: 'TeacherProgramController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    })
    .when('/teachers/:id', {
      templateUrl: 'teacher/teacher.view.html',
      controller: 'TeacherViewController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: authorizedRoles
    })
    .when('/teachers/:id/load', {
      templateUrl: 'teacher/teacher.load.edit.html',
      controller: 'TeacherLoadController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {edit: true}; }
      }
    });
});
