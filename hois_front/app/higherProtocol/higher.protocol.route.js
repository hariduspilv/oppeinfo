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
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/higherProtocols/:id/:action', {
        templateUrl: function(urlAttrs) {
          return urlAttrs.action === 'edit' ? 'higherProtocol/higher.protocol.edit.html' : 'higherProtocol/higher.protocol.view.html';
        },
        controller: 'HigherProtocolEditViewController',
        controllerAs: 'HigherProtocolEditViewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/higherProtocols').get({id: $route.current.params.id}).$promise;
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
