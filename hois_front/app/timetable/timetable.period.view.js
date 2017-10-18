(function () {
  'use strict';
  var TIME_QUOTIENT = 15;
  var DAY_START_TIME = "7:00";
  var ROW_HEIGHT = 22;
  var TOP = 50;

  function getShownWeekIndex(scope) {
    var weeks = scope.weeks;
    var shownWeekStart = moment();

    if (weeks) {
      for (var i = 0; i <= weeks.length - 1; i++) {
        var weekStartDate = moment(weeks[i].start).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
        var weekEndDate = moment(weeks[i].end).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
  
        if (moment(shownWeekStart).isBetween(weekStartDate, weekEndDate)) {
          return i;
        }
      }
    }
  }

  function getTimetablesWeeks(timetables) {
    var weeks = [];
    var weekNr = 0;

    for (var i = 0; i < timetables.length; i++) {
      var start = new Date(timetables[i].startDate);
      var end = new Date(timetables[i].endDate);
      start.setHours(0, 0, 0, 0);
      end.setHours(0, 0, 0, 0);

      while (start < end) {
        var week = {};
        week.weekNr = weekNr++;
        week.start = start;
        start = new Date(start.getFullYear(), start.getMonth(), start.getDate() + (7 - start.getDay()));
        week.end = start;
        week.timetableIndex = i;
        week.timetableId = timetables[i].id;

        weeks.push(week);
        start = new Date(start.getFullYear(), start.getMonth(), start.getDate() + 1);
      }
    }
    return weeks;
  }

  function getPreviousAndNextWeek(scope, route) {
    if (scope.shownWeek) {
      scope.shownWeekIndex = scope.shownWeek.weekNr;
    } else {
      if (route.current.params.weekIndex) {
        scope.shownWeekIndex = Number(route.current.params.weekIndex);
        if (scope.weeks) {
          scope.shownWeek = scope.weeks[scope.shownWeekIndex];
        }
      } else {
        scope.shownWeekIndex = getShownWeekIndex(scope);
        if (scope.weeks) {
          scope.shownWeek = scope.weeks[scope.shownWeekIndex];
        }
      }
    }

    if (scope.weeks && scope.shownWeekIndex) {
      scope.previousWeekIndex = scope.weeks[scope.shownWeekIndex - 1] ? scope.shownWeekIndex - 1 : null;
      scope.nextWeekIndex = scope.weeks[scope.shownWeekIndex + 1] ? scope.shownWeekIndex + 1 : null;
    
      scope.previousWeekTimetableId = scope.weeks[scope.shownWeekIndex - 1] ? scope.weeks[scope.shownWeekIndex - 1].timetableId : null;
      scope.nextWeekTimetableId = scope.weeks[scope.shownWeekIndex + 1] ? scope.weeks[scope.shownWeekIndex + 1].timetableId : null;
    }
  }

  function setTimetableEvents(scope, timetableEvents) {
    scope.timetableEvents = timetableEvents;

    for (var i = 0; i < timetableEvents.length; i++) {
      var event = timetableEvents[i];
      var weekDay = moment(event.date).isoWeekday() - 1;
      var eventDurationMinutes = moment.duration(moment(event.timeEnd, "HH:mm").diff(moment(event.timeStart, "HH:mm"))).asMinutes();
      var rowHeight = ROW_HEIGHT * (eventDurationMinutes / TIME_QUOTIENT);
      var timeFromDayStart = moment.duration(moment(event.timeStart, "HH:mm").diff(moment(DAY_START_TIME, "HH:mm"))).asMinutes();
      var top = ROW_HEIGHT * (timeFromDayStart / TIME_QUOTIENT) + TOP;

      if (moment(event.date).isBetween(moment(scope.shownWeek.start), moment(scope.shownWeek.end), 'day', '[]')) {
        scope.weekColumns[weekDay].events.push({
          nameEt: event.nameEt, nameEn: event.nameEn, timeStart: event.timeStart, timeEnd: event.timeEnd,
          rowHeight: rowHeight, top: top, column: 1, rooms: event.rooms, teachers: event.teachers, studentGroups: event.studentGroups
        });
      }
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
        name: week[i], column: i + 1, events: [], date: moment(scope.shownWeek.start).add(i, "days").toDate(),
        today: (moment().format("DD.MM.YYYY") === moment(scope.shownWeek.start).add(i, "days").format("DD.MM.YYYY") ? true : false)
      });
    }
    scope.weekColumns = weekColumns;
  }

  function getTimetablePeriodParameters(scope, route, routeTypeId) {
    if (routeTypeId) {
      scope.typeId = routeTypeId;
    } else {
      scope.typeId = scope.shownTypeId;
    }

    if (route.current.params.periodId) {
      scope.studyPeriodId = route.current.params.periodId;
    } else {
      scope.studyPeriodId = scope.shownStudyPeriodId;
    }

    if (route.current.params.timetableId) {
      scope.timetableId = route.current.params.timetableId;
    } else {
      scope.timetableId = scope.shownTimetableId;
    }

    if (route.current.params.weekIndex) {
      scope.shownWeekIndex = route.current.params.weekIndex;
    }

    if (routeTypeId && route.current.params.timetableId && route.current.params.periodId) {
      scope.directRoute = true;
    } else {
      scope.directRoute = false;
    }
  }

  function changeTimetableParameters(scope, typeId, timetableId, weekIndex) {
    scope.typeId = typeId;
    scope.timetableId = timetableId;
    scope.shownWeekIndex = weekIndex;
    scope.shownWeek = scope.weeks[weekIndex];
    scope.$parent.timetableId = timetableId;
  }

  angular.module('hitsaOis').controller('GroupTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "group";

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function() {
        getTimetablePeriodParameters($scope, $route, $route.current.params.groupId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function(typeId, studyPeriodId, previousWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, previousWeekTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function(typeId, studyPeriodId, nextWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, nextWeekTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByGroup').search(
            { studentGroupId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownTimetableCurriculum = result.generalTimetableCurriculum;
    
              createTimetableTimeColumn($scope);
              createTimetableWeekColumns($scope);
              setTimetableEvents($scope, result.timetableEvents);
          });
        }
      }
    }
  ]).controller('TeacherTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "teacher";

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function() {
        getTimetablePeriodParameters($scope, $route, $route.current.params.teacherId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function(typeId, studyPeriodId, previousWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, previousWeekTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function(typeId, studyPeriodId, nextWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, nextWeekTimetableId );
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByTeacher').search(
            { teacherId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownTeacher = { firstname: result.firstname, lastname: result.lastname };

              createTimetableTimeColumn($scope);
              createTimetableWeekColumns($scope);
              setTimetableEvents($scope, result.timetableEvents);
          });
        }
      }
    }
  ]).controller('RoomTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "room";

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function() {
        getTimetablePeriodParameters($scope, $route, $route.current.params.roomId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function(typeId, studyPeriodId, previousWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, previousWeekTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function(typeId, studyPeriodId, nextWeekTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, nextWeekTimetableId );
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByRoom').search(
            { roomId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownRoom = { roomCode: result.roomCode, buildingCode: result.buildingCode };

              createTimetableTimeColumn($scope);
              createTimetableWeekColumns($scope);
              setTimetableEvents($scope, result.timetableEvents);
          });
        }
      }
    }
  ]).controller('StudentTimetableViewController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.timetableType = "student";

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'studyPeriodId'], function() {
        getTimetablePeriodParameters($scope, $route, $route.current.params.studentId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function(typeId, studyPeriodId, previousTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, previousTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function(typeId, studyPeriodId, nextTimetableId) {
        changeTimetableParameters($scope, typeId, studyPeriodId, nextTimetableId);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByStudent').search(
            { studentId: $scope.typeId, timetableId: $scope.timetableId }).$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
      
              createTimetableTimeColumn($scope);
              createTimetableWeekColumns($scope);
              setTimetableEvents($scope, result.timetableEvents);
          });
        }
      }
    }
]);
}());