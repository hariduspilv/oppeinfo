'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studyYearScheduleLegend', {
        templateUrl: 'studyYearSchedule/studyYearScheduleLegend.html',
        controller: 'studyYearScheduleLegendController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/studyYearSchedule', {
        templateUrl: 'studyYearSchedule/studyYearSchedule.html',
        controller: 'studyYearScheduleController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);