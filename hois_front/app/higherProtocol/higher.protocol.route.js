'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/higherProtocols', {
        templateUrl: 'higherProtocol/higher.protocol.search.html',
        controller: 'HigherProtocolSearchController',
        controllerAs: 'HigherProtocolSearchController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL]
        }
      })
      .when('/higherProtocols/new', {
        templateUrl: 'higherProtocol/higher.protocol.new.html',
        controller: 'HigherProtocolNewController',
        controllerAs: 'higherProtocolNewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL]
        }
      })
      .when('/higherProtocols/:id/edit', {
        templateUrl: 'higherProtocol/higher.protocol.edit.html',
        controller: 'HigherProtocolEditViewController',
        controllerAs: 'HigherProtocolEditViewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/higherProtocols').get({id: $route.current.params.id}).$promise;
          },
          isView: function (){
            return false;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL]
        }
      })
      .when('/higherProtocols/:id/view', {
        templateUrl: 'higherProtocol/higher.protocol.view.html',
        controller: 'HigherProtocolEditViewController',
        controllerAs: 'HigherProtocolEditViewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/higherProtocols').get({id: $route.current.params.id}).$promise;
          },
          isView: function (){
            return true;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL]
        }
      });
}]);
