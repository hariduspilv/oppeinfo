'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectStudyPeriodPlans', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.search.html',
        controller: 'subjectStudyPeriodPlanSearchController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.roleCode === 'ROLL_A' && Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KOORM) !== -1;
          }
        }
      })
      .when('/subjectStudyPeriodPlans/:subjectId/:studyPeriodId/new', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.edit.html',
        controller: 'subjectStudyPeriodPlanNewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM) !== -1;
          }
        }
      }).when('/subjectStudyPeriodPlans/:id/edit', {
        templateUrl: 'subjectStudyPeriodPlan/subject.study.period.plan.edit.html',
        controller: 'subjectStudyPeriodPlanNewController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM]
        }
      });
}]);
