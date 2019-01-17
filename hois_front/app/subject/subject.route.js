'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subject', {
      templateUrl: 'subject/subject.list.html',
      controller: 'SubjectListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINE) !== -1;
        }
      }
    })
    .when('/subject/public', {
      templateUrl: 'subject/subject.list.html',
      controller: 'SubjectListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        params: function () { return { isPublic: true }; }
      }
    })
    .when('/subject/new', {
      templateUrl: 'subject/subject.edit.html',
      controller: 'SubjectEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.roleCode === 'ROLL_A' && Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINE) !== -1;
        }
      }
    })
    .when('/subject/:id/edit', {
      templateUrl: 'subject/subject.edit.html',
      controller: 'SubjectEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINE) !== -1;
        }
      }
    })
    .when('/subject/:id', {
      templateUrl: 'subject/subject.view.html',
      controller: 'SubjectViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINE]
      }
    })
    .when('/subject/public/:id', {
      templateUrl: 'subject/subject.view.html',
      controller: 'SubjectViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        params: function () { return { isPublic: true }; }
      }
    });
}]);
