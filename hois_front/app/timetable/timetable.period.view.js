'use strict';

angular.module('hitsaOis').controller('TimetablePeriodViewController', ['$scope', '$route', 'QueryUtils',
  function ($scope, $route, QueryUtils) {
    var TIME_QUOTIENT = 15;
    var DAY_START_TIME = "7:00";
    var ROW_HEIGHT = 16+3+3+1;

    $scope.studyperiodId = $route.current.params.periodId;
    $scope.studentGroupId = $route.current.params.groupId;
    $scope.timetableId = $route.current.params.timetableId;

    QueryUtils.endpoint('/timetableevents/timetableByGroup').search(
      {studyperiodId: $scope.studyperiodId, studentGroupId: $scope.studentGroupId, timetableId: $scope.timetableId}).$promise.then(function(result) {
        $scope.timetable = result.generalTimetable;
        createTimetableTimeColumn();
        createTimetableWeekColumns();
        setTimetableEvents(result.timetableEvents);
    });

    function setTimetableEvents(timetableEvents) {
      for (var i = 0; i < timetableEvents.length; i++) {
        var event = timetableEvents[i];
        var weekDay = moment(event.date).isoWeekday();
        var eventDurationMinutes = moment.duration(moment(event.timeEnd,"HH:mm").diff(moment(event.timeStart,"HH:mm"))).asMinutes();
        var rowHeight = ROW_HEIGHT * (eventDurationMinutes / TIME_QUOTIENT);
        var timeFromDayStart = moment.duration(moment(event.timeStart,"HH:mm").diff(moment(DAY_START_TIME,"HH:mm"))).asMinutes();
        var top = ROW_HEIGHT * (timeFromDayStart / TIME_QUOTIENT)+(46);

        $scope.weekColumns[weekDay].events.push({name: event.name, timeStart: event.timeStart, timeEnd: event.timeEnd,
          rowHeight: rowHeight, top: top, column: 1, rooms: event.rooms, teachers: event.teachers, studentGroup: event.studentGroup});
      }
    }

    function createTimetableTimeColumn() {
      var timeColumn = [];
      timeColumn.push("");
      for (var i = 0; i <= 64; i++) {
        var timeStart = moment(DAY_START_TIME, "HH:mm").add(TIME_QUOTIENT * i, 'm');
        var timeStartString = moment(timeStart).format("HH:mm");

        timeColumn.push(timeStartString);
      }
      $scope.timeColumn = timeColumn;
    }

    function createTimetableWeekColumns() {
      var weekColumns = [];
      var week = ['Esmaspäev', 'Teisipäev', 'Kolmapäev', 'Neljapäev', 'Reede', 'Laupäev', 'Pühapäev'];


      for (var i = 0; i < week.length; i++) {
         weekColumns.push({name: week[i], column: i+1, events: [], date: moment($scope.timetable.startDate).add(i,"days").toDate(),
         today: (moment().format("DD.MM.YYYY")===moment($scope.timetable.startDate).add(i,"days").format("DD.MM.YYYY") ? 1:0)});
      }
      $scope.weekColumns = weekColumns;
    }
  }
]);
