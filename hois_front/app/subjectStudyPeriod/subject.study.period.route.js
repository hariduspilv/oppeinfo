'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectStudyPeriods', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.search.html',
        controller: 'SubjectStudyPeriodSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriod/:id/edit', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.edit.html',
        controller: 'SubjectStudyPeriodEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriod/new', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.edit.html',
        controller: 'SubjectStudyPeriodEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/studentGroups', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.student.group.search.html',
        controller: 'SubjectStudyPeriodStudentGroupSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/studentGroups/:studyPeriodId/new', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.student.group.edit.html',
        controller: 'SubjectStudyPeriodStudentGroupEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/studentGroups/:studentGroupId/:studyPeriodId/edit', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.student.group.edit.html',
        controller: 'SubjectStudyPeriodStudentGroupEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/teachers', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.teacher.search.html',
        controller: 'SubjectStudyPeriodTeacherSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/teachers/:studyPeriodId/new', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.teacher.edit.html',
        controller: 'SubjectStudyPeriodTeacherEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriods/teachers/:teacherId/:studyPeriodId/edit', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.teacher.edit.html',
        controller: 'SubjectStudyPeriodTeacherEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);