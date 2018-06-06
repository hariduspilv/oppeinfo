'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/finalHigherProtocols', {
      templateUrl: 'finalProtocol/higher/final.higher.protocol.list.html',
      controller: 'FinalHigherProtocolListController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalHigherProtocols/:moduleType/new', {
      templateUrl: 'finalProtocol/higher/final.higher.protocol.new.html',
      controller: 'FinalHigherProtocolNewController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalHigherProtocols/:id/edit', {
      templateUrl: 'finalProtocol/higher/final.higher.protocol.edit.html',
      controller: 'FinalHigherProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalHigherProtocols').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalHigherProtocols/:id/view', {
      templateUrl: 'finalProtocol/higher/final.higher.protocol.view.html',
      controller: 'FinalHigherProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalHigherProtocols').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    });
}]);
