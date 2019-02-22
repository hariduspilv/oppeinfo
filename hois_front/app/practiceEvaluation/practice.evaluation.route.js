'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practice/evaluation', {
      templateUrl: 'practiceEvaluation/practice.evaluation.list.html',
      controller: 'PracticeEvaluationSearchController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRHINDAMISVORM]
      }
    })
    .when('/practice/evaluation/new', {
      templateUrl: 'practiceEvaluation/practice.evaluation.edit.html',
      controller: 'PracticeEvaluationEditController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRHINDAMISVORM]
      }
    })
    .when('/practice/evaluation/:id/edit', {
      templateUrl: 'practiceEvaluation/practice.evaluation.edit.html',
      controller: 'PracticeEvaluationEditController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRHINDAMISVORM]
      }
    })
    .when('/practice/evaluation/:id/view', {
      templateUrl: 'practiceEvaluation/practice.evaluation.view.html',
      controller: 'PracticeEvaluationViewController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRHINDAMISVORM]
      }
    });
}]);
