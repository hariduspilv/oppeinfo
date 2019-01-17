'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/ehis/teacher/export/higher', {
        templateUrl: 'ehis/teacher.export.html',
        controller: 'TeacherExportController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); } ,
          higher: function() { return true; }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EHIS) !== -1;
          }
        }
      })
    .when('/ehis/teacher/export/vocational', {
        templateUrl: 'ehis/teacher.export.html',
        controller: 'TeacherExportController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); } ,
          higher: function() { return false; }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EHIS) !== -1;
          }
        }
      })
    .when('/ehis/student/export', {
      templateUrl: 'ehis/student.html',
      controller: 'StudentEhisController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EHIS]
      }
    })
    .when('/ehis/logs', {
      templateUrl: 'ehis/logs.view.html',
      controller: 'EhisLogsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EHIS]
      }
    });
}]);
