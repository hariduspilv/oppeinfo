'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/finalExamVocationalProtocols', {
      templateUrl: 'finalExamProtocol/vocational/final.exam.vocational.protocol.list.html',
      controller: 'FinalExamVocationalProtocolListController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamVocationalProtocols/new', {
      templateUrl: 'finalExamProtocol/vocational/final.exam.vocational.protocol.new.html',
      controller: 'FinalExamVocationalProtocolNewController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamVocationalProtocols/:id/edit', {
      templateUrl: 'finalExamProtocol/vocational/final.exam.vocational.protocol.edit.html',
      controller: 'FinalExamVocationalProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalExamVocationalProtocols').get({id: $route.current.params.id}).$promise;
        },
        isView: function ($route){
          return false
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamVocationalProtocols/:id/view', {
      templateUrl: 'finalExamProtocol/vocational/final.exam.vocational.protocol.view.html',
      controller: 'FinalExamVocationalProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalExamVocationalProtocols').get({id: $route.current.params.id}).$promise;
        },
        isView: function ($route){
          return true
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    });
}]);
