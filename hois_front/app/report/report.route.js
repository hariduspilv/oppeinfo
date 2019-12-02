'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/reports/students/students', {
      templateUrl: 'report/student.html',
      controller: 'ReportStudentController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/students/statistics', {
      templateUrl: 'report/student.statistics.html',
      controller: 'ReportStudentStatisticsController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/guest/students/statistics', {
      templateUrl: 'report/guest.student.statistics.html',
      controller: 'ReportGuestStudentStatisticsController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/students/statistics/byperiod', {
      templateUrl: 'report/student.statistics.byperiod.html',
      controller: 'ReportStudentStatisticsByperiodController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/curriculums/completion', {
      templateUrl: 'report/curriculums.completion.html',
      controller: 'ReportCurriculumsCompletionController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/load/higher', {
      templateUrl: 'report/teachers.load.higher.html',
      controller: 'ReportTeacherLoadHigherController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/load/vocational', {
      templateUrl: 'report/teachers.load.vocational.html',
      controller: 'ReportTeacherLoadVocationalController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/detailload/vocational', {
      templateUrl: 'report/teachers.detail.load.vocational.html',
      controller: 'ReportTeacherDetailLoadVocationalController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/teachers/detailload/higher', {
      templateUrl: 'report/teachers.detail.load.higher.html',
      controller: 'ReportTeacherDetailLoadHigherController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/vota', {
      templateUrl: 'report/vota.html',
      controller: 'ReportVotaController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING]
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/studentgroupteacher', {
      templateUrl: 'report/studentgroup.teacher.html',
      controller: 'StudentGroupTeacherController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RYHMAJUHATAJA) !== -1;
        }
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    }).when('/reports/scholarships', {
      templateUrl: 'report/scholarship.statistics.html',
      controller: 'ScholarshipStatisticsController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      },
      data: {
        authorizedRoles: function (Session, roles) {
          return ['ROLL_A', 'ROLL_J'].indexOf(Session.roleCode) !== -1 && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PARING) !== -1;
        }
      }
    }).when('/reports/individualcurriculum/statistics', {
      templateUrl: 'report/individual.curriculum.statistics.html',
      controller: 'IndividualCurriculumStatisticsController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.vocational && ((Session.roleCode === 'ROLL_O' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_RYHMAJUHATAJA) !== -1) ||
            (['ROLL_A', 'ROLL_J'].indexOf(Session.roleCode) !== -1 && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_INDIVID) !== -1));
        }
      },
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    });
});
