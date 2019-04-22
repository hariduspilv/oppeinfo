'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practice/application/myData', {
      templateUrl: 'practiceApplication/practice.application.student.html',
      controller: 'PracticeApplicationStudentController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/applications', {
      templateUrl: 'practiceApplication/practice.application.list.html',
      controller: 'PracticeApplicationSearchController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAAVALDUS],
        currentNavItem: 'practice.application'
      }
    })
    .when('/practice/applicationPeriods', {
      templateUrl: 'practiceApplication/practice.application.period.list.html',
      controller: 'PracticeApplicationPeriodSearchController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAAVALDUS],
        currentNavItem: 'practice.application-period'
      }
    });
}]);
