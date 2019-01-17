'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/moduleProtocols', {
        templateUrl: 'protocol/module.protocol.list.html',
        controller: 'ModuleProtocolListController',
        controllerAs: 'moduleProtocolListController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL) !== -1;
          }
        }
      })
      .when('/moduleProtocols/new', {
        templateUrl: 'protocol/module.protocol.new.html',
        controller: 'ModuleProtocolNewController',
        controllerAs: 'moduleProtocolNewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_MOODULPROTOKOLL) !== -1;
          }
        }
      })
      .when('/moduleProtocols/:id/:action', {
        templateUrl: function(urlAttrs) {
          return urlAttrs.action === 'edit' ? 'protocol/module.protocol.edit.html' : 'protocol/module.protocol.view.html';
        },
        controller: 'ModuleProtocolController',
        controllerAs: 'moduleProtocolController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/moduleProtocols').get({id: $route.current.params.id}).$promise;
          },
          isView: function ($route){
            return $route.current.params.action === 'view';
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_MOODULPROTOKOLL]
        }
      });
}]);
