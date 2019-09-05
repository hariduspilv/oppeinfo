'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function canViewContract(authUser, authRoles) {
    return authRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LEPING) !== -1 || authUser.roleCode === "ROLL_L" || authUser.roleCode === 'ROLL_O';
  }

  $routeProvider
    .when('/contracts', {
        templateUrl: 'contract/contract.list.html',
        controller: 'ContractListController',
        controllerAs: 'contractListController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LEPING]
        }
      })
      .when('/contracts/new', {
        templateUrl: 'contract/contract.edit.html',
        controller: 'ContractEditController',
        controllerAs: 'contractEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING]
        }
      })
      .when('/contracts/:studentId/new', {
        templateUrl: 'contract/contract.edit.html',
        controller: 'ContractEditController',
        controllerAs: 'contractEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING]
        }
      })
      .when('/contracts/new/:studentGroupId', {
        templateUrl: 'contract/contract.edit.html',
        controller: 'ContractEditController',
        controllerAs: 'contractEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING]
        }
      })
      .when('/contracts/:id/edit', {
        templateUrl: 'contract/contract.edit.html',
        controller: 'ContractEditController',
        controllerAs: 'contractEditController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/contracts').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING]
        }
      })
      .when('/contracts/:id/view', {
        templateUrl: 'contract/contract.view.html',
        controller: 'ContractViewController',
        controllerAs: 'contractViewController',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/contracts').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: canViewContract
        }
      });
}]);
