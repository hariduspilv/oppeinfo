'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
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
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
    });
}]);
