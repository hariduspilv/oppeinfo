'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/academicCalendar/:schoolId?', {
      templateUrl: 'academicCalendar/academic.calendar.view.html',
      controller: 'AcademicCalendarController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      }
    })
    .when('/academicCalendars', {
      templateUrl: 'academicCalendar/academic.calendar.school.list.html',
      controller: 'AcademicCalendarSchoolListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function ($translate) { return $translate.onReady(); } }
    });
});
