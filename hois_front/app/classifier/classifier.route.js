'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
  .when('/classifier', {
        templateUrl: 'classifier/classifier.list.html',
        controller: 'ClassifierListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      })
      .when('/classifier/:code', {
        templateUrl: 'classifier/classifier.content.list.html',
        controller: 'ClassifierContentListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      })
      .when('/classifier/:mainClassCode/new', {
        templateUrl: 'classifier/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      })
      .when('/classifier/:mainClassCode/:codeThis/edit', {
        templateUrl: 'classifier/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      });
}]);
