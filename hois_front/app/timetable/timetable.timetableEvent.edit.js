'use strict';

angular.module('hitsaOis').controller('TimetableEventEditController', ['$location', '$route', '$scope', 'USER_ROLES', 'AuthService' , 'DataUtils', 'QueryUtils', 'dialogService', 'message',
  function ($location, $route, $scope, USER_ROLES, AuthService, DataUtils, QueryUtils, dialogService, message) {
    $scope.auth = $route.current.locals.auth;
    var baseUrl = '/timetableevents';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var params = $route.current.params;
    var id = params.id;
    $scope.eventsPerm = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS);
    $scope.canCreatePersonalEvents = ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS);

    var autofill = {
      roomId: params.room,
      date: params.date ? new Date(params.date) : undefined,
      start: params.startTime ? params.startTime : undefined,
      end: params.endTime ? params.endTime : undefined
    };

    $scope.directiveControllers = [];

    function afterLoad(result) {
      if (!result.canEdit && !$route.current.locals.isView) {
        // if editing permission is lost after saving don't show nopermission message and just redirect
        if (angular.equals({}, $scope.timetableEvent)) {
          message.error("main.messages.error.nopermission");
        }
        $scope.back("#/");
      }
      result.startTime = new Date(result.startTime);
      result.endTime = new Date(result.endTime);
      $scope.timetableEvent = result;
    }

    if (id) {
      $scope.timetableEvent = {};
      Endpoint.get({ id: id }, afterLoad);
    } else {
      $scope.timetableEvent = new Endpoint();
      $scope.timetableEvent.isSingleEvent = true;
      if (!$scope.eventsPerm) {
        $scope.timetableEvent.isPersonal = true;
      }

      $scope.timetableEvent.date = autofill.date;
      $scope.timetableEvent.startTime = autofill.start;
      $scope.timetableEvent.endTime = autofill.end;
      
      if (autofill.roomId) {
        // Same parameters as for autocomplete directive in edit.html. Only added `id`.
        QueryUtils.endpoint('/autocomplete/rooms').search({
          id: autofill.roomId,
          isStudy: true,
          occupied: true,
          date: $scope.timetableEvent ? $scope.timetableEvent.date : undefined,
          startTime: $scope.timetableEvent ? $scope.timetableEvent.startTime : undefined,
          endTime: $scope.timetableEvent ? $scope.timetableEvent.endTime : undefined,
          repeatCode: $scope.timetableEvent ? $scope.timetableEvent.repeatCode : undefined,
          weekAmount: $scope.timetableEvent ? $scope.timetableEvent.weekAmount : undefined
        }, function (result) {
          var foundRoom = result.content[0];
          if (foundRoom) {
            $scope.timetableEvent.room = foundRoom;
          }
        });
      }
      
      if ($scope.auth.isTeacher()) {
        $scope.timetableEvent.teachers = [];
        $scope.timetableEvent.teachers.push({teacher: {id:$scope.auth.teacher, nameEt: $scope.auth.fullname, nameEn: $scope.auth.fullname, nameRu: $scope.auth.fullname}});
      }
    }

    $scope.personalEventChanged = function () {
      if ($scope.timetableEvent.isPersonal) {
        $scope.directiveControllers.forEach(function (c) {
          c.clear();
        });
      }
    };

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
          dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts($scope, $scope.auth.higher, result), function () {
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
          filtered.push(teacher.teacher.id);
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
        $scope.removedJournalTeacherIds = {};
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

    $scope.sortTeachers = function (teacher) {
      return $scope.currentLanguageNameField(teacher.teacher);
    };

    $scope.addTeacher = function () {
      if (!angular.isArray($scope.timetableEvent.teachers)) {
        $scope.timetableEvent.teachers = [];
      }
      if ($scope.timetableEvent.teachers.some(function (teacher) {
          return teacher.teacher.id === $scope.timetableEvent.teacher.id;
        })) {
        message.error($scope.auth.higher ? 'timetable.timetableEvent.error.duplicateTeacherHigher' : 'timetable.timetableEvent.error.duplicateTeacherVocational');
        return;
      }
      $scope.timetableEvent.teachers.push({
        id: $scope.removedJournalTeacherIds ? $scope.removedJournalTeacherIds[$scope.timetableEvent.teacher.id] : null,
        teacher: $scope.timetableEvent.teacher
      });
      $scope.timetableEvent.teacher = undefined;
    };

    $scope.deleteTeacher = function (teacher) {
      var index = $scope.timetableEvent.teachers.indexOf(teacher);
      if (index !== -1) {
        $scope.timetableEvent.teachers.splice(index, 1);

        if (!$scope.removedJournalTeacherIds) {
          $scope.removedJournalTeacherIds = {};
        }
        $scope.removedJournalTeacherIds[teacher.teacher.id] = teacher.id;
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
