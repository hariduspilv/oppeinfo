'use strict';

angular.module('hitsaOis').controller('TimetableEventEditController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService) {
    $scope.auth = $route.current.locals.auth;
    var baseUrl = '/timetableevents';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    function afterLoad(result) {
      result.startTime = new Date(result.startTime);
      result.endTime = new Date(result.endTime);
      $scope.timetableEvent = result;
    }

    if (id) {
      $scope.timetableEvent = Endpoint.get({
        id: id
      });
      $scope.timetableEvent.$promise.then(afterLoad);
    } else {
      $scope.timetableEvent = new Endpoint();

      if ($scope.auth.isTeacher()) {
        $scope.timetableEvent.teachers = [];
        $scope.timetableEvent.teachers.push({id:$scope.auth.teacher, nameEt: $scope.auth.fullname, nameEn: $scope.auth.fullname, nameRu: $scope.auth.fullname});
      }
    }

    $scope.save = function () {
      $scope.eventForm.$setSubmitted();
      if (!$scope.eventForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      if ($scope.timetableEvent.startTime > $scope.timetableEvent.endTime) {
        message.error('timetable.timetableEvent.error.endIsEarlierThanStart');
        return;
      }

      var occupiedQuery = timetableTimeOccupiedQuery();
      QueryUtils.endpoint(baseUrl + '/timetableTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
        if(result.occupied) {
          dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
            saveEvent();
          });
        } else {
          saveEvent();
        }
      });
    };

    function timetableTimeOccupiedQuery() {
      var occupiedQuery = {};
      if ($scope.timetableEvent.rooms) {
        occupiedQuery.rooms = $scope.timetableEvent.rooms.reduce(function (filtered, room) {
          filtered.push(room.id);
          return filtered;
        }, []);
      }
      if ($scope.timetableEvent.teachers) {
        occupiedQuery.teachers = $scope.timetableEvent.teachers.reduce(function (filtered, teacher) {
          filtered.push(teacher.id);
          return filtered;
        }, []);
      }
      if ($scope.timetableEvent.studentGroups) {
        occupiedQuery.studentGroups = $scope.timetableEvent.studentGroups.reduce(function (filtered, studentGroup) {
          filtered.push(studentGroup.id);
          return filtered;
        }, []);
      }
      var date = new Date($scope.timetableEvent.date);
      var startTime = $scope.timetableEvent.startTime;
      var endTime = $scope.timetableEvent.endTime;

      occupiedQuery.startTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), startTime.getHours(), startTime.getMinutes());
      occupiedQuery.endTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), endTime.getHours(), endTime.getMinutes());
      occupiedQuery.timetableEventId = $scope.timetableEvent.id;
      occupiedQuery.repeatCode = $scope.timetableEvent.repeatCode;
      occupiedQuery.weekAmount = $scope.timetableEvent.weekAmount;

      return occupiedQuery;
    }

    function saveEvent() {
      if (id) {
        $scope.timetableEvent.$update().then(afterLoad).then(message.updateSuccess);
        $scope.eventForm.$setPristine();
      } else {
        $scope.timetableEvent.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url('/lessonplans/events?_menu');
        });
      }
    }
    
    $scope.delete = function () {
      dialogService.confirmDialog({
        prompt: 'timetable.deleteConfirm',
        accept: 'main.yes',
        cancel: 'main.no'
      }, function () {
        $scope.timetableEvent.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/events?_noback');
        }).catch(angular.noop);
      });
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
        message.error('timetable.timetableEvent.error.duplicateTeacher');
        return;
      }
      $scope.timetableEvent.teachers.push($scope.timetableEvent.teacher);
      $scope.timetableEvent.teacher = undefined;
    };

    $scope.deleteTeacher = function (teacher) {
      var index = $scope.timetableEvent.teachers.indexOf(teacher);
      if (index !== -1) {
        $scope.timetableEvent.teachers.splice(index, 1);
        $scope.eventForm.$setDirty();
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
        message.error('timetable.timetableEvent.error.duplicateRoom');
        return;
      }
      $scope.timetableEvent.rooms.push($scope.timetableEvent.room);
      $scope.timetableEvent.room = undefined;
    };

    $scope.deleteRoom = function (room) {
      var index = $scope.timetableEvent.rooms.indexOf(room);
      if (index !== -1) {
        $scope.timetableEvent.rooms.splice(index, 1);
        $scope.eventForm.$setDirty();
      }
    };

    $scope.$watch('timetableEvent.studentGroup', function () {
      if (angular.isDefined($scope.timetableEvent.studentGroup) && $scope.timetableEvent.studentGroup !== null) {
        $scope.addStudentGroup();
      }
    });

    $scope.addStudentGroup = function () {
      if (!angular.isArray($scope.timetableEvent.studentGroups)) {
        $scope.timetableEvent.studentGroups = [];
      }
      if ($scope.timetableEvent.studentGroups.some(function (studentGroup) {
          return studentGroup.id === $scope.timetableEvent.studentGroup.id;
        })) {
        message.error('timetable.timetableEvent.error.duplicateStudentGroup');
        return;
      }
      $scope.timetableEvent.studentGroups.push($scope.timetableEvent.studentGroup);
      $scope.timetableEvent.studentGroup = undefined;
    };

    $scope.deleteStudentGroup = function (studentGroup) {
      var index = $scope.timetableEvent.studentGroups.indexOf(studentGroup);
      if (index !== -1) {
        $scope.timetableEvent.studentGroups.splice(index, 1);
        $scope.eventForm.$setDirty();
      }
    };
  }
]);
