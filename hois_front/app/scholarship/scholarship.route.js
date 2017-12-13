'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/scholarships/new', {
      templateUrl: 'scholarship/scholarship.edit.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/scholarships/:id/edit', {
      templateUrl: 'scholarship/scholarship.edit.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/scholarships/:id/view', {
      templateUrl: 'scholarship/scholarship.view.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/scholarships', {
      templateUrl: 'scholarship/scholarship.search.html',
      controller: 'ScholarshipSearchController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/scholarships/myData/scholarships', {
      templateUrl: 'scholarship/scholarship.scholarship.student.html',
      controller: 'StudentScholarshipController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR]
      }
    })
    .when('/scholarships/myData/grants', {
      templateUrl: 'scholarship/scholarship.grants.student.html',
      controller: 'StudentScholarshipController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR]
      }
    })
    .when('/scholarships/myData/drGrants', {
      templateUrl: 'scholarship/scholarship.grants.student.html',
      controller: 'StudentScholarshipController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR]
      }
    })
    .when('/scholarships/:id/application', {
      templateUrl: 'scholarship/scholarship.application.edit.html',
      controller: 'StudentScholarshipApplicationController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR]
      }
    })
    .when('/scholarships/applications/grants', {
      templateUrl: 'scholarship/scholarship.application.list.grants.html',
      controller: 'ScholarshipApplicationController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params : function() {
          return {allowedStipendTypes: ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU']};
        }
      }
    })
    .when('/scholarships/applications/scholarships', {
      templateUrl: 'scholarship/scholarship.application.list.scholarships.html',
      controller: 'ScholarshipApplicationController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      }
    });
}]);
