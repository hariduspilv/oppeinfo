'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  var authorizedRoles = {authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR]};

  $routeProvider
    .when('/students/:id/main', {
      templateUrl: 'student/view.main.html',
      controller: 'StudentViewMainController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/:id/results', {
      templateUrl: 'student/view.results.html',
      controller: 'StudentViewResultsController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    }).when('/students/:id/documents', {
      templateUrl: 'student/view.documents.html',
      controller: 'StudentViewDocumentsController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/:id/timetable', {
      templateUrl: 'student/view.timetable.html',
      controller: 'StudentViewTimetableController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/:id/edit', {
      templateUrl: 'student/edit.main.html',
      controller: 'StudentEditController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/:id/absences', {
      templateUrl: 'student/view.absences.html',
      controller: 'StudentAbsencesController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students', {
      templateUrl: 'student/list.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function(Session) {
          return Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR) !== -1 && Session.roleCode !== 'ROLL_T';
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        clMapping: function() { return {studyForm: 'OPPEVORM', status: 'OPPURSTAATUS'}; },
        params: function() { return {order: 'person.lastname,person.firstname'}; },
        url: function() { return '/students'; },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    }).when('/students/myData', {
      templateUrl: 'student/view.main.html',
      controller: 'StudentViewMainController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/myResults', {
      templateUrl: 'student/view.results.html',
      controller: 'StudentViewResultsController',
      controllerAs: 'controller',
      data: authorizedRoles,
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      }
    }).when('/students/journals', {
      templateUrl: 'student/student.journal.list.html',
      controller: 'StudentJournalListController',
      controllerAs: 'StudentJournalListController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR,
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK
        ]
      }
    }).when('/students/study', {
      templateUrl: 'student/student.journal.study.html',
      controller: 'StudentJournalStudyController',
      controllerAs: 'StudentJournalStudyController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR,
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK
        ]
      }
    }).when('/students/tasks', {
      templateUrl: 'student/student.journal.tasks.html',
      controller: 'StudentJournalTasksController',
      controllerAs: 'StudentJournalTasksController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR,
          USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK
        ]
      }
    }).when('/students/specialities', {
      templateUrl: 'student/speciality/student.speciality.list.html',
      controller: 'StudentSpecialitySearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.school.higher === true && Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPUR) !== -1;
        }
      }
    });
});
