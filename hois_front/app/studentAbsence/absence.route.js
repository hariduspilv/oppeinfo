'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/absences', {
      templateUrl: 'studentAbsence/absence.search.html',
      controller: 'StudentAbsenceController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/groupAbsences/:today?', {
      templateUrl: 'studentAbsence/absence.group.html',
      controller: 'StudentGroupAbsenceController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    });
}]);
