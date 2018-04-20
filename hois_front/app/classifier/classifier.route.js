'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
  .when('/classifier', {
        templateUrl: 'classifier/classifier.list.html',
        controller: 'SimpleListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          params: function($translate) { return {order: $translate.use() === 'en' ? 'nameEn' : 'nameEt'}; },
          url: function() { return '/classifier/heads'; }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR]
        }
      })
      .when('/classifier/:code', {
        templateUrl: 'classifier/classifier.content.list.html',
        controller: 'ClassifierContentListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR]
        }
      })
      .when('/classifier/:mainClassCode/new', {
        templateUrl: 'classifier/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KLASSIFIKAATOR]
        }
      })
      .when('/classifier/:mainClassCode/:codeThis/edit', {
        templateUrl: 'classifier/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KLASSIFIKAATOR]
        }
      });
}]);
