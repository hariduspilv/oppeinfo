'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentCards', {
      templateUrl: 'studentCard/student.card.search.html',
      controller: 'StudentCardSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PILET) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/studentCards/studentCardManagement', {
      templateUrl: 'studentCard/student.card.management.html',
      controller: 'StudentCardManagementController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PILET) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    });
}]);