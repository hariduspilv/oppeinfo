'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
  var $route = $routeProvider.$get[$routeProvider.$get.length-1]({$on:function(){}}); // regex

  $routeProvider
    .when('/schoolBoard/:schoolId', {
      templateUrl: 'schoolBoard/school.board.html',
      controller: 'SchoolBoardController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/:type', {
      templateUrl: 'schoolBoard/school.board.timetable.type.selection.html',
      controller: 'SchoolBoardTimetableTypeSelectionController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/:type/:typeId', {
      templateUrl: 'schoolBoard/school.board.timetable.html',
      controller: 'SchoolBoardTimetableController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/currentEvents', {
      templateUrl: 'schoolBoard/school.board.current.events.html',
      controller: 'SchoolBoardCurrentEventsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/roomsWithCurrentEvents', {
      templateUrl: 'schoolBoard/school.board.room.selection.html',
      controller: 'SchoolBoardRoomsWithCurrentEventsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/currentEvents/:roomId', {
      templateUrl: 'schoolBoard/school.board.room.current.events.html',
      controller: 'SchoolBoardCurrentEventsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/schoolBoard/:schoolId/freeRooms/:type', {
      templateUrl: 'schoolBoard/school.board.free.rooms.html',
      controller: 'SchoolBoardFreeRoomsController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
  ;

  // Regex for paths
  $route.routes['/schoolBoard/:schoolId/:type'].regexp = /^\/schoolBoard\/(\d+|[A-Za-z0-9_+=-]{1,50})\/(group|teacher|room)$/;
  $route.routes['/schoolBoard/:schoolId/:type/:typeId'].regexp = /^\/schoolBoard\/(\d+|[A-Za-z0-9_+=-]{1,50})\/(group|teacher|room)\/(\d+)$/;
  $route.routes['/schoolBoard/:schoolId/freeRooms/:type'].regexp = /^\/schoolBoard\/(\d+|[A-Za-z0-9_+=-]{1,50})\/freeRooms\/(current|wholeDay)$/;
}]);
