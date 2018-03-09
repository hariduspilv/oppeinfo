'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider.when('/examTimes', {
    templateUrl: 'exam/search.html',
    controller: 'ExamSearchController',
    controllerAs: 'controller',
    resolve: {
      translationLoaded: function($translate) { return $translate.onReady(); },
      auth: function (AuthResolver) { return AuthResolver.resolve(); }
    },
    data: {
      authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_EKSAM]
    }
  })
  .when('/examRegistration', {
    templateUrl: 'exam/registration.student.html',
    controller: 'ExamStudentController',
    controllerAs: 'controller',
    resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
    data: {
      authorizedRoles: function(Session) { return Session.roleCode === 'ROLL_T'; }
    }
  });
}]);
