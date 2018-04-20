'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentgroups', {
      templateUrl: 'studentGroup/student.group.list.html',
      controller: 'StudentGroupSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM]
      }
    })
    .when('/studentgroups/new', {
      templateUrl: 'studentGroup/student.group.edit.html',
      controller: 'StudentGroupEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPERYHM]
      }
    })
    .when('/studentgroups/:id/edit', {
      templateUrl: 'studentGroup/student.group.edit.html',
      controller: 'StudentGroupEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPERYHM]
      }
    })
    .when('/studentgroups/:id/view', {
      templateUrl: 'studentGroup/student.group.view.html',
      controller: 'StudentGroupViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.roleCode === 'ROLL_T' || Session.roleCode === 'ROLL_L' || roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM) !== -1;
        }
      }
    });
}]);
