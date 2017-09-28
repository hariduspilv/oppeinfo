'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentrepresentatives/applications', {
      templateUrl: 'studentRepresentative/representative.application.list.html',
      controller: 'StudentRepresentativeApplicationSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS]
      }
    }).when('/studentrepresentatives/applications/new', {
      templateUrl: 'studentRepresentative/representative.application.create.html',
      controller: 'StudentRepresentativeApplicationCreateController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
