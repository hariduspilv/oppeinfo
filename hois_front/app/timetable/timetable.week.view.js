(function () {
  'use strict';
  var TIME_QUOTIENT = 15;
  var DAY_START_TIME = "7:00";
  var ROW_HEIGHT = 22;
  var TOP = 50;

  function getTimetableById(scope, timetableId) {
    for (var i = 0; i <= scope.timetables.length - 1; i++) {
      if (scope.timetables[i].id === timetableId) {
        return scope.timetables[i];
      }
    }
  }

  function getShownWeekIndex(scope) {
    var weeks = scope.weeks;
    var shownWeekStart = moment();

    if (weeks) {
      for (var i = 0; i <= weeks.length - 1; i++) {
        var weekStartDate = moment(weeks[i].start).set({
          hour: 0,
          minute: 0,
          second: 0,
          millisecond: 0
        });
        var weekEndDate = moment(weeks[i].end).set({
          hour: 0,
          minute: 0,
          second: 0,
          millisecond: 0
        });

        if (moment(shownWeekStart).isBetween(weekStartDate, weekEndDate)) {
          return i;
        }
      }
    }
  }

  function getPreviousAndNextWeek(scope, route) {
    if (scope.shownWeek) {
      scope.weekIndex = scope.shownWeek.weekNr;
    } else {
      if (route.current.params.weekIndex) {
        scope.weekIndex = Number(route.current.params.weekIndex);
        if (scope.weeks) {
          scope.shownWeek = scope.weeks[scope.weekIndex];
        }
      } else {
        scope.weekIndex = getShownWeekIndex(scope);
        if (scope.weeks) {
          if (!scope.weekIndex) {
            //if current week doesn't exist
            scope.weekIndex = scope.generalTimetableUtils.getTimetableFirstWeekIndex(scope.weeks, scope.timetableId);
          }
          scope.shownWeek = scope.weeks[scope.weekIndex];
        }
      }
    }

    if (scope.weeks && scope.weekIndex !== null) {
      scope.previousWeekIndex = scope.weeks[scope.weekIndex - 1] ? scope.weekIndex - 1 : null;
      scope.nextWeekIndex = scope.weeks[scope.weekIndex + 1] ? scope.weekIndex + 1 : null;

      scope.previousWeekTimetableId = scope.weeks[scope.weekIndex - 1] ? scope.weeks[scope.weekIndex - 1].timetableId : null;
      scope.nextWeekTimetableId = scope.weeks[scope.weekIndex + 1] ? scope.weeks[scope.weekIndex + 1].timetableId : null;
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
          nameEt: event.nameEt,
          nameEn: event.nameEn,
          timeStart: event.timeStart,
          timeEnd: event.timeEnd,
          rowHeight: rowHeight,
          top: top,
          column: 1,
          rooms: event.rooms,
          teachers: event.teachers,
          studentGroups: event.studentGroups,
          public: event.publicEvent
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
        name: week[i],
        column: i + 1,
        events: [],
        date: moment(scope.shownWeek.start).add(i, "days").toDate(),
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
      scope.weekIndex = route.current.params.weekIndex;
    } else if (scope.shownWeekIndex !== null) {
      scope.weekIndex = scope.shownWeekIndex;
      if (scope.shownWeek) {
        scope.shownWeek = scope.weeks[scope.weekIndex];
      }
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
    scope.shownTimetableId = timetableId;
    scope.weekIndex = weekIndex;
    scope.shownWeek = scope.weeks[weekIndex];

    scope.$parent.timetableId = timetableId;
    scope.$parent.shownWeekIndex = weekIndex;
    scope.$parent.shownWeek = scope.weeks[weekIndex];
  }

  function getTimetableGenerationParameters(scope) {
    var firstTimetable = scope.timetables[0];
    var lastTimetable = scope.timetables[scope.timetables.length - 1];
    var timetableIds = [];
    timetableIds.push(firstTimetable.id);
    if (firstTimetable.id !== lastTimetable.id) {
      timetableIds.push(lastTimetable.id);
    }
    return {
      startDate: firstTimetable.startDate,
      endDate: lastTimetable.endDate,
      timetableIds: timetableIds,
      lang: scope.currentLanguage()
    };
  }

  function fillTimetable(scope, timetableEvents) {
    createTimetableTimeColumn(scope);
    createTimetableWeekColumns(scope);
    setTimetableEvents(scope, timetableEvents);
  }

  function saveCalendar(calendar) {
    var blob = new Blob([calendar.content], {
      type: 'text/ics'
    });
    var filename = calendar.filename + ".ics";
    saveFile(filename, blob);
  }

  function saveFile(filename, blob) {
    if (window.navigator.msSaveOrOpenBlob) {
      window.navigator.msSaveBlob(blob, filename);
    } else {
      var element = window.document.createElement('a');
      element.href = window.URL.createObjectURL(blob);
      element.download = filename;
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    }
  }

  angular.module('hitsaOis').controller('SearchTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watch('searchResultEvents', function () {
        if ($scope.searchResultEvents) {
          setWeekCriteria();
          $scope.shownEvents = $scope.searchResultEvents;
          getTimetablePeriodParameters($scope, $route, null);
          $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks($scope.timetables);
          $scope.shownTimetable = getTimetableById($scope, $scope.timetableId);
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function (typeId, previousWeekTimetableId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekTimetableId, previousWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        searchTimetableWeek();
      };

      $scope.nextWeek = function (typeId, nextWeekTimetableId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekTimetableId, nextWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        searchTimetableWeek();
      };

      $scope.generateCalendar = function () {
        if ($scope.timetables) {
          var parameters = getTimetableGenerationParameters($scope);
          QueryUtils.endpoint('/timetableevents/timetableSearch/calendar').search({
            room: $scope.weekCriteria.room,
            teachers: $scope.weekCriteria.teachers,
            studentGroups: $scope.weekCriteria.studentGroups,
            journalOrSubjectId: $scope.weekCriteria.subject,
            from: parameters.startDate,
            thru: parameters.endDate,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function setWeekCriteria() {
        $scope.weekCriteria = [];
        $scope.weekCriteria.room = $scope.criteria.room;
        $scope.weekCriteria.teachers = $scope.criteria.teachers;
        $scope.weekCriteria.studentGroups = $scope.criteria.studentGroups;
        $scope.weekCriteria.subject = $scope.criteria.subject;
      }

      function searchTimetableWeek() {
        QueryUtils.endpoint('/timetableevents/timetableSearch').query({
            room: $scope.weekCriteria.room,
            teachers: $scope.weekCriteria.teachers,
            studentGroups: $scope.weekCriteria.studentGroups,
            journalOrSubjectId: $scope.weekCriteria.subject,
            from: $scope.shownWeek.start,
            thru: $scope.shownWeek.end
          })
          .$promise.then(function (result) {
            $scope.shownEvents = result;
            showWeekTimetable();
          });
      }

      function showWeekTimetable() {
        if ($scope.shownEvents) {
          fillTimetable($scope, $scope.shownEvents);
        }
      }
    }
  ]).controller('GroupTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.timetableType = "group";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function () {
        getTimetablePeriodParameters($scope, $route, $route.current.params.groupId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function (typeId, previousWeekTimetableId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekTimetableId, previousWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekTimetableId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekTimetableId, nextWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.generateCalendar = function () {
        if ($scope.timetables) {
          var parameters = getTimetableGenerationParameters($scope);
          QueryUtils.endpoint('/timetableevents/timetableByGroup/calendar').search({
            studentGroups: [$scope.typeId],
            timetables: parameters.timetableIds,
            from: parameters.startDate,
            thru: parameters.endDate,
            lang: parameters.lang
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByGroup').search({
              studentGroups: [$scope.typeId],
              timetables: [$scope.timetableId],
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownTimetableCurriculum = result.generalTimetableCurriculum;
              fillTimetable($scope, result.timetableEvents);
            });
        }
      }
    }
  ]).controller('TeacherTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.timetableType = "teacher";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function () {
        getTimetablePeriodParameters($scope, $route, $route.current.params.teacherId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function (typeId, previousWeekTimetableId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekTimetableId, previousWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekTimetableId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekTimetableId, nextWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.generateCalendar = function () {
        if ($scope.timetables) {
          var parameters = getTimetableGenerationParameters($scope);
          QueryUtils.endpoint('/timetableevents/timetableByTeacher/calendar').search({
            teachers: [$scope.typeId],
            timetables: parameters.timetableIds,
            from: parameters.startDate,
            thru: parameters.endDate,
            lang: parameters.lang
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByTeacher').search({
              teachers: [$scope.typeId],
              timetables: [$scope.timetableId],
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownTeacher = {
                firstname: result.firstname,
                lastname: result.lastname
              };
              fillTimetable($scope, result.timetableEvents);
            });
        }
      }
    }
  ]).controller('RoomTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.timetableType = "room";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'shownStudyPeriodId'], function () {
        getTimetablePeriodParameters($scope, $route, $route.current.params.roomId);
        if (!$scope.timetables) {
          QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
            $scope.timetables = result;
            $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks($scope.timetables);
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          });
        } else {
          getPreviousAndNextWeek($scope, $route);
          showWeekTimetable();
        }
      });

      $scope.previousWeek = function (typeId, previousWeekTimetableId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekTimetableId, previousWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekTimetableId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekTimetableId, nextWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.generateCalendar = function () {
        if ($scope.timetables) {
          var parameters = getTimetableGenerationParameters($scope);
          QueryUtils.endpoint('/timetableevents/timetableByRoom/calendar').search({
            room: $scope.typeId,
            timetables: parameters.timetableIds,
            from: parameters.startDate,
            thru: parameters.endDate,
            lang: parameters.lang
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByRoom').search({
              room: $scope.typeId,
              timetables: [$scope.timetableId],
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              $scope.shownTimetable = result.generalTimetable;
              $scope.shownRoom = {
                roomCode: result.roomCode,
                buildingCode: result.buildingCode
              };
              fillTimetable($scope, result.timetableEvents);
            });
        }
      }
    }
  ]).controller('StudentTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.timetableType = "student";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.auth = $route.current.locals.auth;

      $scope.$watchGroup(['shownTypeId', 'shownTimetableId', 'studyPeriodId'], function () {
        if ($scope.auth.isStudent() || $scope.auth.isParent() || $scope.auth.isAdmin()) {
          getTimetablePeriodParameters($scope, $route, $route.current.params.studentId);
          if (!$scope.timetables) {
            QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
              $scope.timetables = result;
              $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks($scope.timetables);
              getPreviousAndNextWeek($scope, $route);
              showWeekTimetable();
            });
          } else {
            getPreviousAndNextWeek($scope, $route);
            showWeekTimetable();
          }
        }
      });

      $scope.previousWeek = function (typeId, previousWeekTimetableId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekTimetableId, previousWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekTimetableId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekTimetableId, nextWeekIndex);
        getPreviousAndNextWeek($scope, $route);
        showWeekTimetable();
      };

      $scope.generateCalendar = function () {
        if ($scope.timetables) {
          var parameters = getTimetableGenerationParameters($scope);
          QueryUtils.endpoint('/timetableevents/timetableByStudent/calendar').search({
            student: $scope.typeId,
            timetables: parameters.timetablesIds,
            from: parameters.startDate,
            thru: parameters.endDate,
            higher: $scope.auth.higher,
            vocational: $scope.auth.vocational,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        if ($scope.typeId && $scope.timetableId) {
          QueryUtils.endpoint('/timetableevents/timetableByStudent').search({
            student: $scope.typeId,
            timetables: [$scope.timetableId],
            from: $scope.shownWeek.start,
            thru: $scope.shownWeek.end,
            higher: $scope.auth.higher,
            vocational: $scope.auth.vocational
          }).$promise.then(function (result) {
            $scope.shownTimetable = result.generalTimetable;
            fillTimetable($scope, result.timetableEvents);
          });
        }
      }
    }
  ]);
}());
