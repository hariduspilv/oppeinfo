'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', '$stateProvider', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/applications/student', {
        templateUrl: 'application/student/application.student.list.html',
        controller: 'ApplicationStudentListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/applications/student/new/:type', {
        templateUrl: 'application/student/application.student.new.html',
        controller: 'ApplicationStudentController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
