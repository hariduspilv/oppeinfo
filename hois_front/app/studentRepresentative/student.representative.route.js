'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function checkStudentType(message, $location, AuthResolver) {
    AuthResolver.resolve().then(function (auth) {
      // guest student is not allowed to make representative applications
      if (auth.isGuestStudent()) {
        message.error('main.messages.error.nopermission');
        $location.path('');
      }
    });
  }

  $routeProvider
    .when('/studentrepresentatives/applications', {
      templateUrl: 'studentRepresentative/representative.application.list.html',
      controller: 'StudentRepresentativeApplicationSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS]
      }
    }).when('/studentrepresentatives/applications/new', {
      templateUrl: 'studentRepresentative/representative.application.create.html',
      controller: 'StudentRepresentativeApplicationCreateController',
      controllerAs: 'controller',
      resolve: { 
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        checkAccess: checkStudentType
      }
    });
}]);
