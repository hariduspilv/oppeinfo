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
    .when('/timetable/lessonTime/:id/:action', {
      templateUrl: function (urlAttrs) {
        return urlAttrs.action === 'edit' ? 'timetable/timetable.lessonTime.html' : 'timetable/timetable.lessonTime.view.html';
      },
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
        },
        isView: function ($route) {
          return $route.current.params.action === 'view';
        }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG]
      }
    })
    .when('/timetable/generalTimetableByGroup/:schoolId?', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.byGroup.html',
      controller: 'GeneralTimetableByGroupController',
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
    .when('/timetable/generalTimetableByTeacher/:schoolId?', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.byTeacher.html',
      controller: 'GeneralTimetableByTeacherController',
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
    .when('/timetable/generalTimetableByRoom/:schoolId?', {
      templateUrl: 'timetable/generalTimetable/timetable.generalTimetable.byRoom.html',
      controller: 'GeneralTimetableByRoomController',
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
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
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createVocationalPlan/:groupId', {
      templateUrl: 'timetable/timetable.createVocationalPlan.html',
      controller: 'VocationalTimetablePlanController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createHigherPlan/:groupId', {
      templateUrl: 'timetable/timetable.createHigherPlan.html',
      controller: 'HigherTimetablePlanController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/:id/createHigherPlan', {
      templateUrl: 'timetable/timetable.createHigherPlan.html',
      controller: 'HigherTimetablePlanController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    })
    .when('/timetable/group/:schoolId/:periodId/:groupId/:timetableId/:weekIndex?', {
      templateUrl: 'timetable/timetable.week.view.html',
      controller: 'GroupTimetableViewController',
      controllerAs: 'controller'
    })
    .when('/timetable/teacher/:schoolId/:periodId/:teacherId/:timetableId/:weekIndex?', {
      templateUrl: 'timetable/timetable.week.view.html',
      controller: 'TeacherTimetableViewController',
      controllerAs: 'controller'
    })
    .when('/timetable/room/:schoolId/:periodId/:roomId/:timetableId/:weekIndex?', {
      templateUrl: 'timetable/timetable.week.view.html',
      controller: 'RoomTimetableViewController',
      controllerAs: 'controller'
    })
    .when('/timetable/student/:schoolId/:periodId/:studentId/:timetableId', {
      templateUrl: 'timetable/timetable.week.view.html',
      controller: 'StudentTimetableViewController',
      controllerAs: 'controller',
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
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
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN]
      }
    });
}]);
