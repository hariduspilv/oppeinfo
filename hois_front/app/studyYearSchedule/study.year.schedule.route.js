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
            return Session.roleCode === 'ROLL_A' && Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK);
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
          authorizedRoles: function(Session) {
            return Session.vocational;
          }
        }
      });
}]);