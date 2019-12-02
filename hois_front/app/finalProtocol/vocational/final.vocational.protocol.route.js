'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/finalVocationalProtocols', {
      templateUrl: 'finalProtocol/vocational/final.vocational.protocol.list.html',
      controller: 'FinalVocationalProtocolListController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL]
      }
    })
    .when('/finalVocationalProtocols/:moduleType/new', {
      templateUrl: 'finalProtocol/vocational/final.vocational.protocol.new.html',
      controller: 'FinalVocationalProtocolNewController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPMOODULPROTOKOLL]
      }
    })
    .when('/finalVocationalProtocols/:id/edit', {
      templateUrl: 'finalProtocol/vocational/final.vocational.protocol.edit.html',
      controller: 'FinalVocationalProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalVocationalProtocols').get({id: $route.current.params.id}).$promise;
        },
        isView: function () {
          return false;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPMOODULPROTOKOLL]
      }
    })
    .when('/finalVocationalProtocols/:id/view', {
      templateUrl: 'finalProtocol/vocational/final.vocational.protocol.view.html',
      controller: 'FinalVocationalProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalVocationalProtocols').get({id: $route.current.params.id}).$promise;
        },
        isView: function () {
          return true;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL]
      }
    });
}]);
