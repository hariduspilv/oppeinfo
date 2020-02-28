'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  var $route = $routeProvider.$get[$routeProvider.$get.length-1]({$on:function(){}}); // regex

  function isStudentGroupTeacher(Session) {
    return angular.isArray(Session.teacherGroupIds) && Session.teacherGroupIds.length > 0;
  }

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
        authorizedRoles: function(Session, roles) {
          return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: function(Session, roles) {
          return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR],
        guestStudentForbidden: true
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR],
        guestStudentForbidden: true
      }
    })
    .when('/scholarships/myData/drGrants', {
      templateUrl: 'scholarship/student/scholarship.drGrants.student.html',
      controller: 'StudentScholarshipController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        drGrant: function () {
          return true;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR],
        guestStudentForbidden: true
      }
    })
    .when('/scholarships/:id/application/:studentId?', {
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPUR, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS]
      }
    })
    .when('/scholarships/applications/ranking/grants', {
      templateUrl: 'scholarship/scholarship.application.ranking.grants.html',
      controller: 'ScholarshipApplicationRankingController',
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
            stipend: false,
            scholarshipType: "grants"
          };
        }
      },
      data: {
        authorizedRoles: function(Session) {
          return Session.vocational && (Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1 ||
            isStudentGroupTeacher(Session));
        }
      }
    })
    .when('/scholarships/applications/ranking/drGrants', {
      templateUrl: 'scholarship/scholarship.application.ranking.drGrants.html',
      controller: 'ScholarshipApplicationRankingController',
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
            scholarshipType: "drGrants"
          };
        }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
      }
    })
    .when('/scholarships/applications/ranking/scholarships', {
      templateUrl: 'scholarship/scholarship.application.ranking.scho.html',
      controller: 'ScholarshipApplicationRankingController',
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
            stipend: true,
            scholarshipType: "scholarships"
          };
        }
      },
      data: {
        authorizedRoles: function(Session, roles) {
          return Session.higher && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1;
        }
      }
    })
    .when('/scholarships/applications/:type/annul', {
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
      authorizedRoles: function(Session) {
        return Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS) !== -1 ||
          isStudentGroupTeacher(Session);
      }
    })
    .when('/scholarships/applications/:type/reject', {
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
    .when('/scholarships/decision/:type', {
      templateUrl: 'scholarship/scholarship.decision.edit.html',
      controller: 'ScholarshipDecisionController',
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
    .when('/scholarships/decision/:type/:id', {
      templateUrl: 'scholarship/scholarship.decision.view.html',
      controller: 'ScholarshipDecisionViewController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        translationLoaded: function ($translate) {
          return $translate.onReady();
        }
      },
      authorizedRoles: function(Session) {
        return Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1 ||
          isStudentGroupTeacher(Session);
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
    })
    .when('/scholarships/applications/:type', {
      templateUrl: 'scholarship/scholarship.application.search.html',
      controller: 'ScholarshipApplicationSearchController',
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
        authorizedRoles: function(Session) {
          return Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS) !== -1 ||
            isStudentGroupTeacher(Session);
        }
      }
    })
    .when('/scholarships/applications/:type/new', {
      templateUrl: 'scholarship/scholarship.application.new.html',
      controller: 'ScholarshipApplicationNewController',
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
        authorizedRoles: function(Session) {
          return Session.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS) !== -1 ||
            isStudentGroupTeacher(Session);
        }
      }
    });

    // Regex for paths
    $route.routes['/scholarships/applications/:id'].regexp = /^\/scholarships\/applications\/(\d+)$/;
    $route.routes['/scholarships/applications/:type'].regexp = /^\/scholarships\/applications\/(grants|scholarships|drGrants)$/;
    $route.routes['/scholarships/applications/:type/new'].regexp = /^\/scholarships\/applications\/(grants|scholarships|drGrants)\/new$/;
}]);
