'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]};

  $routeProvider
    .when('/reports/students/students', {
      templateUrl: 'report/student.html',
      controller: 'ReportStudentController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/students/statistics', {
      templateUrl: 'report/student.statistics.html',
      controller: 'ReportStudentStatisticsController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/students/statistics/byperiod', {
        templateUrl: 'report/student.statistics.byperiod.html',
        controller: 'ReportStudentStatisticsByperiodController',
        controllerAs: 'controller',
        data: authorizedRoles,
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        }
    }).when('/reports/curriculums/completion', {
      templateUrl: 'report/curriculums.completion.html',
      controller: 'ReportCurriculumsCompletionController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/load/higher', {
      templateUrl: 'report/teachers.load.higher.html',
      controller: 'ReportTeacherLoadHigherController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/load/vocational', {
      templateUrl: 'report/teachers.load.vocational.html',
      controller: 'ReportTeacherLoadVocationalController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    });
});
