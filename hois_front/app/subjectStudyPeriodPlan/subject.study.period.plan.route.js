'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectStudyPeriodPlans', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.search.html',
        controller: 'subjectStudyPeriodPlanSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINE]
        }
      })
      .when('/subjectStudyPeriodPlans/:subjectId/:studyPeriodId/new', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.edit.html',
        controller: 'subjectStudyPeriodPlanNewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      }).when('/subjectStudyPeriodPlans/:id/edit', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.edit.html',
        controller: 'subjectStudyPeriodPlanNewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);