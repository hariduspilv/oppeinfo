'use strict';

angular.module('hitsaOis').controller('TimetableEventEditController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier',
  function ($scope, message, QueryUtils, DataUtils, $route, $location) {
    var baseUrl = '/timetableevents';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    function afterLoad(result) {
      $scope.timetableEvent = result;
    }

    if (id) {
      $scope.timetableEvent = Endpoint.get({
        id: id
      });
      $scope.timetableEvent.$promise.then(afterLoad);
    } else {
      $scope.timetableEvent = new Endpoint();
    }

    $scope.save = function () {
      if (!$scope.eventForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      if (id) {
        $scope.timetableEvent.$update().then(afterLoad).then(message.updateSuccess);
      } else {
        $scope.eventForm.$setSubmitted();
        $scope.timetableEvent.$save().then(function () {
          message.info('timetable.timetableEvent.created');
          $location.url('/lessonplans/events?_menu');
        });
      }

    };

    $scope.$watch('timetableEvent.teacher', function () {
      if (angular.isDefined($scope.timetableEvent.teacher) && $scope.timetableEvent.teacher !== null) {
        $scope.addTeacher();
      }
    });

    $scope.addTeacher = function () {
      if (!angular.isArray($scope.timetableEvent.teachers)) {
        $scope.timetableEvent.teachers = [];
      }
      if ($scope.timetableEvent.teachers.some(function (teacher) {
          return teacher.id === $scope.timetableEvent.teacher.id;
        })) {
        message.error('timetable.timetableEvent.duplicateTeacher');
        return;
      }
      $scope.timetableEvent.teachers.push($scope.timetableEvent.teacher);
      $scope.timetableEvent.teacher = undefined;
    };

    $scope.deleteTeacher = function (teacher) {
      var index = $scope.timetableEvent.teachers.indexOf(teacher);
      if (index !== -1) {
        $scope.timetableEvent.teachers.splice(index, 1);
      }
    };

    $scope.$watch('timetableEvent.room', function () {
      if (angular.isDefined($scope.timetableEvent.room) && $scope.timetableEvent.room !== null) {
        $scope.addRoom();
      }
    });

    $scope.addRoom = function () {
      if (!angular.isArray($scope.timetableEvent.rooms)) {
        $scope.timetableEvent.rooms = [];
      }
      if ($scope.timetableEvent.rooms.some(function (room) {
          return room.id === $scope.timetableEvent.room.id;
        })) {
        message.error('timetable.timetableEvent.duplicateRoom');
        return;
      }
      $scope.timetableEvent.rooms.push($scope.timetableEvent.room);
      $scope.timetableEvent.room = undefined;
    };

    $scope.deleteRoom = function (room) {
      var index = $scope.timetableEvent.rooms.indexOf(room);
      if (index !== -1) {
        $scope.timetableEvent.rooms.splice(index, 1);
      }
    };
  }
]);
