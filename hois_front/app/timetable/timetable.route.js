'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/timetable/lessonTime/search', {
      templateUrl: 'timetable/timetable.lessonTime.list.html',
      controller: 'TimetableLessonTimeListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG]
      }
    })
    .when('/timetable/lessonTime', {
      templateUrl: 'timetable/timetable.lessonTime.html',
      controller: 'TimetableLessonTimeController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG]
      }
    })
    .when('/timetable/lessonTime/:id/view', {
      templateUrl: 'timetable/timetable.lessonTime.view.html',
      controller: 'TimetableLessonTimeController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        entity: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/lessontimes').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG]
      }
    })
    .when('/timetable/lessonTime/:id/edit', {
      templateUrl: 'timetable/timetable.lessonTime.html',
      controller: 'TimetableLessonTimeController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        entity: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/lessontimes').get({id: $route.current.params.id}).$promise;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNDAEG]
      }
    })
    .when('/timetable/:schoolId?/generalTimetable/:type', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.html',
      controller: 'GeneralTimetableController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      }
    })
    .when('/timetable/personalGeneralTimetable', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.personal.html',
      controller: 'PersonalGeneralTimetableController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:schoolId?/searchGeneralTimetable', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.search.html',
      controller: 'GeneralTimetableSearchController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      }
    })
    .when('/timetable/timetableManagement', {
      templateUrl: 'timetable/timetable.timetableManagement.html',
      controller: 'TimetableManagementController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/new', {
      templateUrl: 'timetable/timetable.edit.html',
      controller: 'TimetableEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        isCreate: function () {
          return true;
        }
      },
      data: {
        authorizedRoles: function (Session, roles) {
          return Session.roleCode === 'ROLL_A' && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN) !== -1;
        }
      }
    })
    .when('/timetable/:id/edit', {
      templateUrl: 'timetable/timetable.edit.html',
      controller: 'TimetableEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        entity: function (QueryUtils, $route) {
          return QueryUtils.endpoint('/timetables').get({id: $route.current.params.id}).$promise;
        },
        isView: function ($route) {
          return $route.current.params.action === 'view';
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/view', {
      templateUrl: 'timetable/timetable.view.html',
      controller: 'TimetableViewController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createVocationalPlan/:groupId', {
      templateUrl: 'timetable/timetable.createVocationalPlan.html',
      controller: 'VocationalTimetablePlanController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createHigherPlan/group/:groupId', {
      templateUrl: 'timetable/timetable.createHigherPlan.html',
      controller: 'HigherTimetablePlanController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createHigherPlan/pair/:pairId', {
      templateUrl: 'timetable/timetable.createHigherPlan.html',
      controller: 'HigherTimetablePlanController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createHigherPlan', {
      templateUrl: 'timetable/timetable.createHigherPlan.html',
      controller: 'HigherTimetablePlanController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:schoolId/:type/:typeId/:studyYearId/:weekIndex?', {
      templateUrl: 'timetable/generalTimetable/timetable.week.view.html',
      controller: 'TimetableWeekViewController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
    })
    .when('/timetable/:schoolId/student/:studyYearId/:studentId', {
      templateUrl: 'timetable/generalTimetable/timetable.week.view.html',
      controller: 'StudentTimetableViewController',
      controllerAs: 'controller',
      resolve: {
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetables', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.school.list.html',
      controller: 'GeneralTimetableSchoolListController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
    })
    .when('/timetable/timetableEvent/new', {
      templateUrl: 'timetable/timetable.timetableEvent.edit.html',
      controller: 'TimetableEventEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        isCreate: function () {
          return true;
        }
      },
      data: {
        authorizedRoles: function (Session, roles, ArrayUtils) {
          return (Session.roleCode === 'ROLL_A' || Session.roleCode === 'ROLL_O') && ArrayUtils.intersect(roles, [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIPLAAN, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS]);
        }
      }
    })
    .when('/timetable/timetableEvent/:id/edit', {
      templateUrl: 'timetable/timetable.timetableEvent.edit.html',
      controller: 'TimetableEventEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        },
        isCreate: function () {
          return true;
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIPLAAN, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS]
      }
    })
    .when('/timetable/timetableEvent/:id/view', {
      templateUrl: 'timetable/timetable.timetableEvent.view.html',
      controller: 'TimetableEventEditController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function ($translate) {
          return $translate.onReady();
        },
        auth: function (AuthResolver) {
          return AuthResolver.resolve();
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS]
      }
    });
}]);
