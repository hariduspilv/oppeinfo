'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/scholarships/grant/new', {
      templateUrl: 'scholarship/scholarship.edit.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'],
            typeIsScholarship: false
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/scholarship/new', {
      templateUrl: 'scholarship/scholarship.edit.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_TULEMUS', 'STIPTOETUS_ERIALA', 'STIPTOETUS_MUU'],
            typeIsScholarship: true
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/drGrant/new', {
      templateUrl: 'scholarship/scholarship.edit.html',
      controller: 'ScholarshipEditController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_DOKTOR'],
            typeIsScholarship: true
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/:id/view', {
      templateUrl: 'scholarship/scholarship.view.html',
      controller: 'ScholarshipViewController',
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
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
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_DOKTOR'],
            scholarshipType: "drGrant"
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/grants', {
      templateUrl: 'scholarship/scholarship.search.html',
      controller: 'ScholarshipSearchController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes : ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'],
            grant : true
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/scholarships', {
      templateUrl: 'scholarship/scholarship.search.html',
      controller: 'ScholarshipSearchController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_ERIALA', 'STIPTOETUS_TULEMUS', 'STIPTOETUS_MUU'],
            scholarship : true
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/myData/scholarships', {
      templateUrl: 'scholarship/student/scholarship.scholarship.student.html',
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
      templateUrl: 'scholarship/student/scholarship.grants.student.html',
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
      templateUrl: 'scholarship/student/scholarship.grants.student.html',
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
      templateUrl: 'scholarship/student/scholarship.application.edit.html',
      controller: 'StudentScholarshipApplicationEditController',
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
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'],
            stipend: false
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/applications/scholarships', {
      templateUrl: 'scholarship/scholarship.application.list.scho.html',
      controller: 'ScholarshipApplicationController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            allowedStipendTypes: ['STIPTOETUS_TULEMUS', 'STIPTOETUS_ERIALA', 'STIPTOETUS_MUU'],
            stipend: true
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/applications/annul', {
      templateUrl: 'scholarship/scholarship.application.annul.edit.html',
      controller: 'ScholarshipRejectionController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            type: 'annulApplications'
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/applications/reject', {
      templateUrl: 'scholarship/scholarship.application.reject.edit.html',
      controller: 'ScholarshipRejectionController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        params: function () {
          return {
            type: 'rejectApplications'
          };
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/applications/:id', {
      templateUrl: 'scholarship/student/scholarship.application.edit.html',
      controller: 'StudentScholarshipApplicationViewController',
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
    });
}]);
