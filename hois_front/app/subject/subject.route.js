'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  function checkRightsToEdit(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
    AuthResolver.resolve().then(function(auth){
      if(!auth.isAdmin() || !ArrayUtils.includes(auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINE)) {
        message.error('main.messages.error.nopermission');
        $location.path('');
      }
    });
  }

  $routeProvider
    .when('/subject', {
      templateUrl: 'subject/subject.list.html',
      controller: 'SubjectListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINE]
      }
    })
    .when('/subject/new', {
      templateUrl: 'subject/subject.edit.html',
      controller: 'SubjectEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        checkAccess: checkRightsToEdit
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINE]
      }
    })
    .when('/subject/:id/edit', {
      templateUrl: 'subject/subject.edit.html',
      controller: 'SubjectEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        checkAccess: checkRightsToEdit
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AINE]
      }
    })
    .when('/subject/:id', {
      templateUrl: 'subject/subject.view.html',
      controller: 'SubjectViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_AINE]
      }
    });
}]);
