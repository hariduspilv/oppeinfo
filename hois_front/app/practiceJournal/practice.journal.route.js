'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practiceJournals', {
        templateUrl: 'practiceJournal/practice.journal.list.html',
        controller: 'PracticeJournalListController',
        controllerAs: 'PracticeJournalListController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/practiceJournals/new', {
        templateUrl: 'practiceJournal/practice.journal.edit.html',
        controller: 'PracticeJournalEditController',
        controllerAs: 'PracticeJournalEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/practiceJournals/:id/edit', {
        templateUrl: 'practiceJournal/practice.journal.edit.html',
        controller: 'PracticeJournalEditController',
        controllerAs: 'PracticeJournalEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/practiceJournals').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/practiceJournals/:id/view', {
        templateUrl: 'practiceJournal/practice.journal.view.html',
        controller: 'PracticeJournalEntryController',
        controllerAs: 'PracticeJournalViewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/practiceJournals').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/practiceJournals/:id/entries/edit', {
        templateUrl: 'practiceJournal/practice.journal.entry.html',
        controller: 'PracticeJournalEntryController',
        controllerAs: 'PracticeJournalEntryController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/practiceJournals').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/practiceJournals/supervisor/:uuid', {
        templateUrl: 'practiceJournal/practice.journal.supervisor.entry.html',
        controller: 'PracticeJournalSupervisorEntryController',
        controllerAs: 'PracticeJournalSupervisorEntryController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/practiceJournals/supervisor').get({id: $route.current.params.uuid}).$promise;
          }
        }
      })
      .when('/practiceJournals/student', {
        templateUrl: 'practiceJournal/practice.journal.student.list.html',
        controller: 'PracticeJournalStudentListController',
        controllerAs: 'PracticeJournalStudentListController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
