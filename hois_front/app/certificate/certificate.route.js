'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/certificate', {
        templateUrl: 'certificate/certificate.search.html',
        controller: 'CertificateSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TOEND]
        }
      })
      .when('/certificate/new', {
        templateUrl: 'certificate/certificate.edit.html',
        controller: 'CertificateEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session, roles) {
            return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TOEND) !== -1;
          }
        }
      })
      .when('/certificate/:typeCode/new', {
        templateUrl: 'certificate/certificate.student.new.html',
        controller: 'CertificateStudentOrderController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: function(Session) { return Session.roleCode === 'ROLL_T'; }
        }
      })
      .when('/certificate/:id/edit', {
        templateUrl: 'certificate/certificate.edit.html',
        controller: 'CertificateEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TOEND]
        }
      })
      .when('/certificate/:id/view', {
        templateUrl: 'certificate/certificate.view.html',
        controller: 'CertificateViewController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); },
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TOEND]
        }
      });
}]);
