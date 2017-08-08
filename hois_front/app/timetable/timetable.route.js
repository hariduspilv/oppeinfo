'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/timetable/lessonTime/search', {
        templateUrl: 'timetable/timetable.lessonTime.list.html',
        controller: 'TimetableLessonTimeListController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/lessonTime', {
        templateUrl: 'timetable/timetable.lessonTime.html',
        controller: 'TimetableLessonTimeController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/lessonTime/:id/:action', {
        templateUrl: function(urlAttrs) {
          return urlAttrs.action === 'edit' ? 'timetable/timetable.lessonTime.html' : 'timetable/timetable.lessonTime.view.html';
        },
        controller: 'TimetableLessonTimeController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/lessontimes').get({id: $route.current.params.id}).$promise;
          },
          isView: function ($route){
            return $route.current.params.action === 'view';
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/generalTimetableByGroup?_menu', {
        templateUrl: 'timetable/timetable.generalTimetable.byGroup.html',
        controller: 'GeneralTimetableController',
          controllerAs: 'controller',
          resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); } ,
            auth: function (AuthResolver) { return AuthResolver.resolve(); }
          },
          data: {
            authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
          }
        })
      .when('/timetable/timetableManagement', {
        templateUrl: 'timetable/timetable.timetableManagement.html',
        controller: 'TimetableManagementController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/new', {
        templateUrl: 'timetable/timetable.edit.html',
        controller: 'TimetableEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); } ,
          auth: function (AuthResolver) { return AuthResolver.resolve(); } ,
          isCreate: function (){return true;}
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/:id/edit', {
        templateUrl: 'timetable/timetable.edit.html',
        controller: 'TimetableEditController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          auth: function (AuthResolver) { return AuthResolver.resolve(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/timetables').get({id: $route.current.params.id}).$promise;
          },
          isView: function ($route){
            return $route.current.params.action === 'view';
          }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/timetable/:id/view', {
        templateUrl: 'timetable/timetable.view.html',
        controller: 'TimetableViewController',
        controllerAs: 'controller',
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
    .when('/timetable/:id/createPlan/:groupId', {
      templateUrl: 'timetable/timetable.createTimetablePlan.html',
      controller: 'TimetablePlanController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]);
