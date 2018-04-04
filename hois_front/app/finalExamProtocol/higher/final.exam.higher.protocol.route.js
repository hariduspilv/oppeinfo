'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/finalExamHigherProtocols', {
      templateUrl: 'finalExamProtocol/higher/final.exam.higher.protocol.list.html',
      controller: 'FinalExamHigherProtocolListController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamHigherProtocols/new', {
      templateUrl: 'finalExamProtocol/higher/final.exam.higher.protocol.new.html',
      controller: 'FinalExamHigherProtocolNewController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
				auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamHigherProtocols/:id/edit', {
      templateUrl: 'finalExamProtocol/higher/final.exam.higher.protocol.edit.html',
      controller: 'FinalExamHigherProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalExamHigherProtocols').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL]
      }
    })
    .when('/finalExamHigherProtocols/:id/view', {
      templateUrl: 'finalExamProtocol/higher/final.exam.higher.protocol.view.html',
      controller: 'FinalExamHigherProtocolEditController',
      controllerAs: 'controller',
      resolve: {
				translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/finalExamHigherProtocols').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL]
      }
    });
}]);
