'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

  $routeProvider
    .when('/persons/admin-roles', {
      templateUrl: 'persons/adminRoles/admin.roles.search.html',
      controller: 'AdminRoleSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {order: 'usr.name_et'}; },
        url: function () { return '/users/admin-roles'; }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/admin-roles/new', {
      templateUrl: 'persons/adminRoles/admin.roles.edit.html',
      controller: 'AdminRoleEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/admin-roles/:role/edit', {
      templateUrl: 'persons/adminRoles/admin.roles.edit.html',
      controller: 'AdminRoleEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/admin-roles/:role/view', {
      templateUrl: 'persons/adminRoles/admin.roles.view.html',
      controller: 'AdminRoleViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/teacher-roles', {
      templateUrl: 'persons/teacherRoles/teacher.roles.search.html',
      controller: 'TeacherRoleSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {order: 'name_et'}; },
        url: function () { return '/users/teacher-roles'; }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/teacher-roles/:occupation/new', {
      templateUrl: 'persons/teacherRoles/teacher.roles.edit.html',
      controller: 'TeacherRoleEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/teacher-roles/:role/edit', {
      templateUrl: 'persons/teacherRoles/teacher.roles.edit.html',
      controller: 'TeacherRoleEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons/teacher-roles/:role/view', {
      templateUrl: 'persons/teacherRoles/teacher.roles.view.html',
      controller: 'TeacherRoleViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: function (Session) {
          return Session.roleCode !== 'ROLL_P' && Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA) !== -1;
        }
      }
    })
    .when('/persons', {
      templateUrl: 'persons/persons.search.html',
      controller: 'PersonSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        clMapping: function() { return {role: 'ROLL'}; },
        params: function() { return {order: 'p.lastname,p.firstname'}; },
        url: function () { return '/users'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/new', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/:person/users/new', {
      templateUrl: 'persons/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/:person/users/:user/edit', {
      templateUrl: 'persons/users.edit.html',
      controller: 'UsersEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/:person/users/:user', {
      templateUrl: 'persons/users.view.html',
      controller: 'UsersViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/:id/edit', {
      templateUrl: 'persons/persons.edit.html',
      controller: 'PersonsEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA]
      }
    })
    .when('/persons/:id', {
      templateUrl: 'persons/persons.view.html',
      controller: 'PersonsViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA]
      }
    });
}]);
