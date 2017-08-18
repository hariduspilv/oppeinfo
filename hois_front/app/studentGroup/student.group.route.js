'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentgroups', {
      templateUrl: 'studentGroup/student.group.list.html',
      controller: 'StudentGroupSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/studentgroups/new', {
      templateUrl: 'studentGroup/student.group.edit.html',
      controller: 'StudentGroupEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/studentgroups/:id/edit', {
      templateUrl: 'studentGroup/student.group.edit.html',
      controller: 'StudentGroupEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/studentgroups/:id/view', {
      templateUrl: 'studentGroup/student.group.view.html',
      controller: 'StudentGroupEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
