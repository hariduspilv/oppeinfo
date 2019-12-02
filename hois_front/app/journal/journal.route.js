'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/journals', {
        templateUrl: 'journal/journal.list.html',
        controller: 'JournalListController',
        controllerAs: 'journalListController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return ['ROLL_A', 'ROLL_J', 'ROLL_O'].indexOf(Session.roleCode) !== -1 && Session.vocational &&
              roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK) !== -1;
          }
        }
      })
      .when('/journal/:id/:action', {
        templateUrl: function(urlAttrs) {
          return urlAttrs.action === 'edit' ? 'journal/journal.html' : 'journal/journal.view.html';
        },
        controller: 'JournalController',
        controllerAs: 'journalController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/journals').get({id: $route.current.params.id}).$promise;
          },
          isView: function ($route){
            return $route.current.params.action === 'view';
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK]
        }
      });
}]);
