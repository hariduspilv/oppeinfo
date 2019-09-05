'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/boardingSchools', {
      templateUrl: 'boardingSchool/boarding.school.search.html',
      controller: 'BoardingSchoolSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPILASKODU) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/boardingSchools/management', {
      templateUrl: 'boardingSchool/boarding.school.management.html',
      controller: 'BoardingSchoolManagementController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPILASKODU) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/boardingSchools/rooms', {
      templateUrl: 'boardingSchool/boarding.school.rooms.html',
      controller: 'BoardingSchoolRoomsController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPILASKODU) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    });
}]);