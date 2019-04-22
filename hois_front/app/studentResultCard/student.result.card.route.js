'use strict';

angular.module('hitsaOis').config(function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentResultCards', {
      templateUrl: 'studentResultCard/student.result.card.list.html',
      controller: 'StudentResultCardListController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.school.vocational === true && Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    }).when('/studentResultCards/:id/view', {
      templateUrl: 'studentResultCard/student.result.card.view.html',
      controller: 'StudentResultCardController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.school.vocational === true && Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR) !== -1;
        }
      },
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      }
    });
  });