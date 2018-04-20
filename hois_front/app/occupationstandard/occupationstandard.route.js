'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/occupationstandard', {
      templateUrl: 'occupationstandard/occupationstandard.html',
      controller: 'KutseregisterSyncController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session) {
          return Session.roleCode === 'ROLL_P';
        }
      }
    });
}]);
