'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/applications', {
        templateUrl: 'application/application.list.html',
        controller: 'SimpleListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          clMapping: function() { return {type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS'}; },
          params: function() { return {order: '-inserted'}; },
          url: function() { return '/applications'; }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/applications/new', {
        templateUrl: 'application/application.html',
        controller: 'ApplicationController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/applications/:id/:action', {
        templateUrl: function(urlAttrs) {
          return urlAttrs.action === 'edit' ? 'application/application.html' : 'application/application.view.html';
        },
        controller: 'ApplicationController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/applications').get({id: $route.current.params.id}).$promise;
          },
          isView: function ($route){
            return $route.current.params.action === 'view';
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]);
