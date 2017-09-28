'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/teachers', {
      templateUrl: 'teacher/teacher.list.html',
      controller: 'TeacherListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }},
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA]
        }
    })
    .when('/teachers/new', {
      templateUrl: 'teacher/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); } },
      data: authorizedRoles
    })
    .when('/teachers/:id/edit', {
      templateUrl: 'teacher/teacher.edit.html',
      controller: 'TeacherEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }},
      data: authorizedRoles
    })
    .when('/teachers/myData', {
      templateUrl: 'teacher/teacher.view.html',
      controller: 'TeacherViewController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA
        ]
      },
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
        auth: function (AuthResolver) { return AuthResolver.resolve(); }},
      data: authorizedRoles
    });
});
