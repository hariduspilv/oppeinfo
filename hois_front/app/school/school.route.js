'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/school', {
      templateUrl: 'school/school.list.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function($translate) { return {order: $translate.use() === 'en' ? 'nameEn' : 'nameEt'}; },
        url: function() { return '/school'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS]
      }
    })
    .when('/school/new', {
      templateUrl: 'school/school.edit.html',
      controller: 'SchoolEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEASUTUS]
      }
    })
    .when('/school/:id/edit', {
      templateUrl: 'school/school.edit.html',
      controller: 'SchoolEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEASUTUS]
      }
    })
    .when('/school/:id/view', {
      templateUrl: 'school/school.view.html',
      controller: 'SchoolViewController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS]
      }
    })
    .when('/school/departments', {
      templateUrl: 'school/school.department.list.html',
      controller: 'SchoolDepartmentListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR]
      }
    })
    .when('/school/departments/new', {
      templateUrl: 'school/school.department.edit.html',
      controller: 'SchoolDepartmentEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STRUKTUUR]
      }
    })
    .when('/school/departments/:id/edit', {
      templateUrl: 'school/school.department.edit.html',
      controller: 'SchoolDepartmentEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STRUKTUUR]
      }
    })
    .when('/school/departments/:id/view', {
      templateUrl: 'school/school.department.view.html',
      controller: 'SchoolDepartmentEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR]
      }
    })
    .when('/school/teacheroccupations', {
      templateUrl: 'teacher/teacher.occupation.list.html',
      controller: 'SimpleListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function() { return {order: 'occupationEt'}; },
        url: function() { return '/school/teacheroccupations'; }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPETAJAAMET]
      }
    })
    .when('/school/teacheroccupations/new', {
      templateUrl: 'teacher/teacher.occupation.edit.html',
      controller: 'TeacherOccupationEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJAAMET]
      }
    })
    .when('/school/teacheroccupations/:id/edit', {
      templateUrl: 'teacher/teacher.occupation.edit.html',
      controller: 'TeacherOccupationEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPETAJAAMET]
      }
    })
    .when('/school/studyLevels', {
      templateUrl: 'school/school.study.levels.html',
      controller: 'SchoolStudyLevelsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPETASE]
      }
    });
}]);
