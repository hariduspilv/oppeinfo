'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practice/student/statistics', {
      templateUrl: 'practiceStatistics/practice.student.list.html',
      controller: 'PracticeStudentListController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRSTATISTIKA]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    .when('/practice/contract/statistics', {
      templateUrl: 'practiceStatistics/practice.contract.list.html',
      controller: 'PracticeContractListController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRSTATISTIKA]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    })
    /**.when('/practice/studyYear/statistics', {
      templateUrl: 'practiceStatistics/practice.studyYear.list.html',
      controller: 'PracticeStudyYearListController',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PRSTATISTIKA]
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    });
    */
}]);
