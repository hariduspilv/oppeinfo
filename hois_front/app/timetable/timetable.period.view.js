(function () {
  'use strict';
  var TIME_QUOTIENT = 15;
  var DAY_START_TIME = "7:00";
  var ROW_HEIGHT = 22;
  var TOP = 50;

  function getPreviousAndNextTimetables(scope, QueryUtils) {
    QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
      scope.timetables = result;
      var shownTimetableIndex;
      for (var i = 0; i <= scope.timetables.length - 1; i++) {
        if (scope.timetables[i].id === scope.shownTimetable.id) {
          shownTimetableIndex = i;
        }
      }
      scope.previousTimetableId = scope.timetables[shownTimetableIndex - 1] ? scope.timetables[shownTimetableIndex - 1].id : null;
      scope.nextTimetableId = scope.timetables[shownTimetableIndex + 1] ? scope.timetables[shownTimetableIndex + 1].id : null;
    });
  }

  function setTimetableEvents(scope, timetableEvents) {
    for (var i = 0; i < timetableEvents.length; i++) {
      var event = timetableEvents[i];
      var weekDay = moment(event.date).isoWeekday() - 1;
      var eventDurationMinutes = moment.duration(moment(event.timeEnd, "HH:mm").diff(moment(event.timeStart, "HH:mm"))).asMinutes();
      var rowHeight = ROW_HEIGHT * (eventDurationMinutes / TIME_QUOTIENT);
      var timeFromDayStart = moment.duration(moment(event.timeStart, "HH:mm").diff(moment(DAY_START_TIME, "HH:mm"))).asMinutes();
      var top = ROW_HEIGHT * (timeFromDayStart / TIME_QUOTIENT) + TOP;

      scope.weekColumns[weekDay].events.push({
        name: event.name, timeStart: event.timeStart, timeEnd: event.timeEnd,
        rowHeight: rowHeight, top: top, column: 1, rooms: event.rooms, teachers: event.teachers, studentGroups: event.studentGroups
      });
    }
  }

  function createTimetableTimeColumn(scope) {
    var timeColumn = [];
    timeColumn.push("");
    for (var i = 0; i <= 64; i++) {
      var timeStart = moment(DAY_START_TIME, "HH:mm").add(TIME_QUOTIENT * i, 'm');
      var timeStartString = moment(timeStart).format("HH:mm");

      timeColumn.push(timeStartString);
    }
    scope.timeColumn = timeColumn;
  }

  function createTimetableWeekColumns(scope) {
    var weekColumns = [];
    var week = ['dayMon', 'dayTue', 'dayWed', 'dayThu', 'dayFri', 'daySat', 'daySun'];

    for (var i = 0; i < week.length; i++) {
      weekColumns.push({
        name: week[i], column: i + 1, events: [], date: moment(scope.shownTimetable.startDate).add(i, "days").toDate(),
        today: (moment().format("DD.MM.YYYY") === moment(scope.shownTimetable.startDate).add(i, "days").format("DD.MM.YYYY") ? 1 : 0)
      });
    }
    scope.weekColumns = weekColumns;
  }

  angular.module('hitsaOis').controller('GroupTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "group";
      $scope.typeId = $route.current.params.groupId;
      $scope.studyperiodId = $route.current.params.periodId;
      $scope.timetableId = $route.current.params.timetableId;

      QueryUtils.endpoint('/timetableevents/timetableByGroup').search(
        { studyperiodId: $scope.studyperiodId, studentGroupId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
          $scope.shownTimetable = result.generalTimetable;
          $scope.shownTimetableCurriculum = result.generalTimetableCurriculum;

          createTimetableTimeColumn($scope);
          createTimetableWeekColumns($scope);
          setTimetableEvents($scope, result.timetableEvents);
          getPreviousAndNextTimetables($scope, QueryUtils);
        });
    }
  ]).controller('TeacherTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "teacher";
      $scope.typeId = $route.current.params.teacherId;
      $scope.studyperiodId = $route.current.params.periodId;
      $scope.timetableId = $route.current.params.timetableId;

      QueryUtils.endpoint('/timetableevents/timetableByTeacher').search(
        { studyperiodId: $scope.studyperiodId, teacherId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
          $scope.shownTimetable = result.generalTimetable;
          $scope.shownTeacher = { firstname: result.firstname, lastname: result.lastname };

          createTimetableTimeColumn($scope);
          createTimetableWeekColumns($scope);
          setTimetableEvents($scope, result.timetableEvents);
          getPreviousAndNextTimetables($scope, QueryUtils);
        });
    }
  ]).controller('RoomTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "room";
      $scope.typeId = $route.current.params.roomId;
      $scope.studyperiodId = $route.current.params.periodId;
      $scope.timetableId = $route.current.params.timetableId;

      QueryUtils.endpoint('/timetableevents/timetableByRoom').search(
        { studyperiodId: $scope.studyperiodId, roomId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
          $scope.shownTimetable = result.generalTimetable;
          $scope.shownRoom = { roomCode: result.roomCode, buildingCode: result.buildingCode };

          createTimetableTimeColumn($scope);
          createTimetableWeekColumns($scope);
          setTimetableEvents($scope, result.timetableEvents);
          getPreviousAndNextTimetables($scope, QueryUtils);
        });
    }
  ]);
}());