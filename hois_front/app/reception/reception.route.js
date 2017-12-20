'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/reception/saisAdmission/search', {
        templateUrl: 'reception/reception.saisAdmission.list.html',
        controller: 'SimpleListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          clMapping: function() { return {studyForm: 'OPPEVORM', language: 'OPPEKEEL'}; },
          params: function() { return {order: 'code'}; },
          url: function() { return '/saisAdmissions'; }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT]
        }
      })
      .when('/reception/saisAdmission/:id/view', {
        templateUrl: 'reception/reception.saisAdmission.view.html',
        controller: 'ReceptionSaisAdmissionController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/saisAdmissions').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT]
        }
      })
      .when('/reception/saisAdmission/import', {
        templateUrl: 'reception/reception.saisAdmission.import.html',
        controller: 'ReceptionSaisAdmissionImportController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS]
        }
      })
      .when('/reception/saisApplication/search', {
        templateUrl: 'reception/reception.saisApplication.list.html',
        controller: 'ReceptionSaisApplicationListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT]
        }
      })
      .when('/reception/saisApplication/:id/view', {
        templateUrl: 'reception/reception.saisApplication.view.html',
        controller: 'ReceptionSaisApplicationController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/saisApplications').get({id: $route.current.params.id}).$promise;
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT]
        }
      })
      .when('/reception/saisApplication/import', {
        templateUrl: 'reception/reception.saisApplication.import.html',
        controller: 'ReceptionSaisApplicationImportController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS]
        }
      });
}]);
