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
        currentStudyYear: function(QueryUtils) {
          return QueryUtils.endpoint('/school/studyYear/current').get().$promise;
        },
        studyPeriods: function(QueryUtils) {
          return QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise;
        }
      }
    });
}]);