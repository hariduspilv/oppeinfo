'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/practice/studentgroup/contracts', {
      templateUrl: 'studentgroupContract/studentgroup.contract.list.html',
      controller: 'StudentgroupContractController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LEPING]
      }
    })
    .when('/practice/studentgroup/contracts/:studentGroupId', {
      templateUrl: 'studentgroupContract/studentgroup.contract.list.html',
      controller: 'StudentgroupContractController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LEPING]
      }
    })
    .when('/practice/studentgroup/ekis', {
      templateUrl: 'studentgroupContract/studentgroup.ekis.contract.list.html',
      controller: 'StudentgroupEkisContractController',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); } ,
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING]
      }
    });
}]);