'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studyMaterial/higher', {
      templateUrl: 'studyMaterial/study.material.higher.list.html',
      controller: 'StudyMaterialHigherListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/vocational', {
      templateUrl: 'studyMaterial/study.material.vocational.list.html',
      controller: 'StudyMaterialVocationalListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/higher/:subjectStudyPeriodId/edit', {
      templateUrl: 'studyMaterial/study.material.subjectStudyPeriod.edit.html',
      controller: 'StudyMaterialSubjectStudyPeriodController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        subjectStudyPeriod: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $route.current.params.subjectStudyPeriodId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/vocational/:journalId/edit', {
      templateUrl: 'studyMaterial/study.material.journal.edit.html',
      controller: 'StudyMaterialJournalController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        journal: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/journal/' + $route.current.params.journalId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/higher/:subjectStudyPeriodId/new', {
      templateUrl: 'studyMaterial/study.material.higher.edit.html',
      controller: 'StudyMaterialHigherEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        subjectStudyPeriod: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $route.current.params.subjectStudyPeriodId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/vocational/:journalId/new', {
      templateUrl: 'studyMaterial/study.material.vocational.edit.html',
      controller: 'StudyMaterialVocationalEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        journal: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/journal/' + $route.current.params.journalId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/higher/:subjectStudyPeriodId/edit/:materialId', {
      templateUrl: 'studyMaterial/study.material.higher.edit.html',
      controller: 'StudyMaterialHigherEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        subjectStudyPeriod: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $route.current.params.subjectStudyPeriodId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/vocational/:journalId/edit/:materialId', {
      templateUrl: 'studyMaterial/study.material.vocational.edit.html',
      controller: 'StudyMaterialVocationalEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        journal: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/journal/' + $route.current.params.journalId).get().$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL]
      }
    })
    .when('/studyMaterial/higher/:subjectStudyPeriodId/view', {
      templateUrl: 'studyMaterial/study.material.subjectStudyPeriod.view.html',
      controller: 'StudyMaterialSubjectStudyPeriodController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        subjectStudyPeriod: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/subjectStudyPeriod/' + $route.current.params.subjectStudyPeriodId).get().$promise;
        }
      }
    })
    .when('/studyMaterial/vocational/:journalId/view', {
      templateUrl: 'studyMaterial/study.material.journal.view.html',
      controller: 'StudyMaterialJournalController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        journal: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/studyMaterial/journal/' + $route.current.params.journalId).get().$promise;
        }
      }
    })
    .when('/studyMaterial/:schoolId/higher/:subjectStudyPeriodId/view', {
      templateUrl: 'studyMaterial/study.material.subjectStudyPeriod.view.html',
      controller: 'StudyMaterialSubjectStudyPeriodPublicController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        subjectStudyPeriod: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/public/studyMaterial/subjectStudyPeriod/' + $route.current.params.subjectStudyPeriodId).get().$promise;
        }
      }
    })
    .when('/studyMaterial/:schoolId/vocational/:journalId/view', {
      templateUrl: 'studyMaterial/study.material.journal.view.html',
      controller: 'StudyMaterialJournalPublicController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        journal: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/public/studyMaterial/journal/' + $route.current.params.journalId).get().$promise;
        }
      }
    });
}]);
