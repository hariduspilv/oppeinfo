'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/academicCalendar', {
      templateUrl: 'academicCalendar/academic.calendar.view.html',
      controller: 'AcademicCalendarController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AKADKALENDER]
      }
    });
});
