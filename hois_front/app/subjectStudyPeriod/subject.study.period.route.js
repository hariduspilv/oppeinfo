'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectStudyPeriods', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.search.html',
        controller: 'SubjectStudyPeriodSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK]
        }
      }).when('/subjectStudyPeriod/:id/edit', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.edit.html',
        controller: 'SubjectStudyPeriodEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK]
        }
      }).when('/subjectStudyPeriod/:id/view', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.view.html',
        controller: 'SubjectStudyPeriodViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK]
        }
      }).when('/subjectStudyPeriod/new', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.edit.html',
        controller: 'SubjectStudyPeriodEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK]
        }
      }).when('/subjectStudyPeriods/studentGroups', {
        templateUrl: 'subjectStudyPeriod/studentGroup/subject.study.period.student.group.search.html',
        controller: 'SubjectStudyPeriodStudentGroupSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/studentGroups/new', {
        templateUrl: 'subjectStudyPeriod/studentGroup/subject.study.period.student.group.edit.html',
        controller: 'SubjectStudyPeriodStudentGroupEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/studentGroups/:studentGroupId/:studyPeriodId/edit', {
        templateUrl: 'subjectStudyPeriod/studentGroup/subject.study.period.student.group.edit.html',
        controller: 'SubjectStudyPeriodStudentGroupEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/studentGroups/:studentGroupId/:studyPeriodId/view', {
        templateUrl: 'subjectStudyPeriod/studentGroup/subject.study.period.student.group.view.html',
        controller: 'SubjectStudyPeriodStudentGroupViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/teachers', {
        templateUrl: 'subjectStudyPeriod/teacher/subject.study.period.teacher.search.html',
        controller: 'SubjectStudyPeriodTeacherSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/teachers/new', {
        templateUrl: 'subjectStudyPeriod/teacher/subject.study.period.teacher.edit.html',
        controller: 'SubjectStudyPeriodTeacherEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/teachers/:teacherId/:studyPeriodId/edit', {
        templateUrl: 'subjectStudyPeriod/teacher/subject.study.period.teacher.edit.html',
        controller: 'SubjectStudyPeriodTeacherEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      })
      .when('/subjectStudyPeriods/teachers/:teacherId/:studyPeriodId/view', {
        templateUrl: 'subjectStudyPeriod/teacher/subject.study.period.teacher.view.html',
        controller: 'SubjectStudyPeriodTeacherViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      })
      .when('/subjectStudyPeriods/subjects', {
        templateUrl: 'subjectStudyPeriod/subject/subject.study.period.subject.search.html',
        controller: 'SubjectStudyPeriodSubjectSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/subjects/new', {
        templateUrl: 'subjectStudyPeriod/subject/subject.study.period.subject.edit.html',
        controller: 'SubjectStudyPeriodSubjectEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/subjects/:subjectId/:studyPeriodId/edit', {
        templateUrl: 'subjectStudyPeriod/subject/subject.study.period.subject.edit.html',
        controller: 'SubjectStudyPeriodSubjectEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      }).when('/subjectStudyPeriods/subjects/:subjectId/:studyPeriodId/view', {
        templateUrl: 'subjectStudyPeriod/subject/subject.study.period.subject.view.html',
        controller: 'SubjectStudyPeriodSubjectViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN]
        }
      });
}]);
