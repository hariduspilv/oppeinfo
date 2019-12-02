'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studyYearScheduleLegend', {
        templateUrl: 'studyYearSchedule/study.year.schedule.legend.html',
        controller: 'studyYearScheduleLegendController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return ['ROLL_A', 'ROLL_J'].indexOf(Session.roleCode) !== -1 && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK) !== -1;
          }
        }
      }).when('/studyYearSchedule', {
        templateUrl: 'studyYearSchedule/study.year.schedule.html',
        controller: 'studyYearScheduleController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return !Session.hasSchoolRole || roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK) !== -1;
          }
        }
      });
}]);