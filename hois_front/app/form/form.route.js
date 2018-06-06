'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider.when('/forms', {
    templateUrl: 'form/form.list.html',
    controller: 'FormSearchController',
    controllerAs: 'controller',
    resolve: {
      translationLoaded: function($translate) { return $translate.onReady(); },
      auth: function (AuthResolver) { return AuthResolver.resolve(); }
    },
    data: {
      authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPBLANKETT]
    }
  }).when('/forms/:action', {
    templateUrl: 'form/form.edit.html',
    controller: 'FormEditController',
    controllerAs: 'controller',
    resolve: {
      translationLoaded: function($translate) { return $translate.onReady(); },
      auth: function (AuthResolver) { return AuthResolver.resolve(); }
    },
    data: {
      authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPBLANKETT]
    }
  });
}]);
