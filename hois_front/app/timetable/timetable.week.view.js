(function () {
  'use strict';
  var TIME_QUOTIENT = 15;
  var DAY_START_TIME = "7:00";
  var DAY_END_TIME = "23:00";
  var ROW_HEIGHT = 22;
  var TOP = 50;

  function getPreviousAndNextWeek(scope) {
    if (scope.shownWeek) {
      scope.weekIndex = scope.shownWeek.weekNr;

      scope.previousWeekIndex = scope.weeks[scope.weekIndex - 1] ? scope.weekIndex - 1 : null;
      scope.nextWeekIndex = scope.weeks[scope.weekIndex + 1] ? scope.weekIndex + 1 : null;
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

      scope.weekColumns[weekDay].events.push({
        nameEt: event.nameEt,
        nameEn: event.nameEn,
        timeStart: event.timeStart,
        timeEnd: event.timeEnd,
        rowHeight: rowHeight,
        top: top,
        rooms: event.rooms,
        id: event.id,
        maxEl: 1,
        teachers: event.teachers,
        studentGroups: event.studentGroups,
        public: event.publicEvent,
        journalId: event.journalId,
        subjectStudyPeriodId: event.subjectStudyPeriodId,
        showStudyMaterials: event.showStudyMaterials,
        left:0,
        width:100
      });
    }

    overlappingEvents(scope, timetableEvents);
  }

  function sortTimetableEventsByDays(scope, timetableEvents) {
    var eventsByDays = [];
    for (var i = 0; i < 7; i++) {
      eventsByDays.push([]);
    }

    timetableEvents.forEach(function (event) {
      var weekDay = moment(event.date).isoWeekday() - 1;
      eventsByDays[weekDay].push(event);
    });
    return eventsByDays;
  }

  function timetableDayMinutes() {
    var minutes = [];
    /*var minutesInTimetableDay = moment.duration(moment(DAY_END_TIME, "HH:mm").diff(moment(DAY_START_TIME, "HH:mm"))).asMinutes();

    for (var i = 0; i <= minutesInTimetableDay; i++) {
      var minute = moment(DAY_START_TIME, "HH:mm").add(i, 'm');
      var minuteString = moment(minute).format("HH:mm");
      minutes[minuteString] = [];
    }*/
    for(var i=7; i < 23; i++)
    {
      for(var j=0; j < 60; j++)
      {
        var minuteString=(i < 10 ? "0"+i:i)+":"+(j < 10 ? "0"+j:j);
        minutes[minuteString] = [];
      }
    }
    return minutes;
  }

  function overlappingEvents(scope, timetableEvents) {
    var minutes;
    var timetableEventsByDays = sortTimetableEventsByDays(scope, timetableEvents);
    for(var w = 0; w < timetableEventsByDays.length; w++)
    {
      minutes = timetableDayMinutes();
      for (var i = 0; i < timetableEventsByDays[w].length; i++) {
        var event = timetableEventsByDays[w][i];
        var eventIsOverTime = moment(event.timeEnd, "HH:mm").add(-1, 'm').format("HH:mm");
        var eventTakesPlace = false;

        for (var minute in minutes) {
          if (event.timeStart === minute) {
            eventTakesPlace = true;
          } else if (eventIsOverTime === minute) {
            eventTakesPlace = false;
          }

          if (eventTakesPlace) {
            minutes[minute].push(event.id);
          }
        }
      }

      //kui minutid sätitud, vaatame nüüd objektid üle
      for (var minute in minutes) {
        for(var x=0; x < minutes[minute].length; x++)
        {
          for(var t=0; t < scope.weekColumns[w].events.length; t++)
          {
            if(minutes[minute][x]===scope.weekColumns[w].events[t].id && scope.weekColumns[w].events[t].maxEl < minutes[minute].length)
            {
              scope.weekColumns[w].events[t].maxEl= minutes[minute].length;
              scope.weekColumns[w].events[t].left=x*Math.floor(100/minutes[minute].length);
              scope.weekColumns[w].events[t].width=Math.floor(100/minutes[minute].length);
            }
          }
        }
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

  function getDirectRouteParameters(scope, route, routeTypeId) {
    scope.directRoute = false;

    if (routeTypeId) {
      scope.directRoute = true;
      scope.$parent.typeId = routeTypeId;

      if (route.current.params.weekIndex) {
        scope.weekIndex = route.current.params.weekIndex;
      }
    }
  }

  function changeTimetableParameters(scope, typeId, weekIndex) {
    scope.typeId = typeId;
    scope.weekIndex = weekIndex;
    scope.shownWeek = scope.weeks[weekIndex];
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
      $scope.timetableSearch = true;

      $scope.weeks = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query();

      $scope.$watch('searchResultEvents', function () {
        if ($scope.searchResultEvents) {
          setWeekCriteria();
          $scope.shownEvents = $scope.searchResultEvents;

          $scope.weeks.$promise.then(function () {
            if (!$scope.weekIndex) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      });

      $scope.previousWeek = function (typeId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekIndex);
        getPreviousAndNextWeek($scope);
        searchTimetableWeek();
        $scope.$parent.shownWeek = $scope.shownWeek;
      };

      $scope.nextWeek = function (typeId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekIndex);
        getPreviousAndNextWeek($scope);
        searchTimetableWeek();
        $scope.$parent.shownWeek = $scope.shownWeek;
      };

      $scope.generateCalendar = function () {
        if ($scope.shownWeek) {
          QueryUtils.endpoint('/timetableevents/timetableSearch/calendar').search({
            room: $scope.weekCriteria.room,
            teachers: $scope.weekCriteria.teachers,
            studentGroups: $scope.weekCriteria.studentGroups,
            journalOrSubjectId: $scope.weekCriteria.subject,
            from: $scope.weeks[0].start,
            thru: $scope.weeks[$scope.weeks.length - 1].end,
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
        $scope.timetableEvents = null;
        QueryUtils.loadingWheel($scope, true);
        if ($scope.shownWeek) {
          QueryUtils.endpoint('/timetableevents/timetableSearch').query({
              room: $scope.weekCriteria.room,
              teachers: $scope.weekCriteria.teachers,
              studentGroups: $scope.weekCriteria.studentGroups,
              journalOrSubjectId: $scope.weekCriteria.subject,
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              QueryUtils.loadingWheel($scope, false);
              $scope.shownEvents = result;
              showWeekTimetable();
            });
        }
      }

      function showWeekTimetable() {
        if ($scope.shownWeek && $scope.shownEvents) {
          fillTimetable($scope, $scope.shownEvents);
        }
      }
    }
  ]).controller('GroupTimetableViewController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.timetableType = "group";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.auth = $route.current.locals.auth;

      $scope.schoolId = $scope.auth === undefined ? $route.current.params.schoolId : $scope.auth.school.id;
      var timetableEventsEndpoint = '/timetableevents/timetableByGroup/' + $scope.schoolId;
      var timetableEventsCalendarEndpoint = '/timetableevents/timetableByGroup/calendar/' + $scope.schoolId;

      $scope.weeks = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query();

      if ($route.current.params.groupId) {
        getDirectRouteParameters($scope, $route, $route.current.params.groupId);
      }

      $scope.$watchGroup(['$parent.typeId'], function () {
        if ($scope.$parent.typeId) {
          $scope.typeId = $scope.$parent.typeId;
          $scope.weeks.$promise.then(function () {
            if (!$scope.weekIndex) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      });

      $scope.previousWeek = function (typeId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.emptyParentVariable = function () {
        $scope.$parent.typeId = null;
      };

      $scope.generateCalendar = function () {
        if ($scope.shownWeek && $scope.$parent.typeId) {
          QueryUtils.endpoint(timetableEventsCalendarEndpoint).search({
            studentGroups: [$scope.$parent.typeId],
            from: $scope.weeks[0].start,
            thru: $scope.weeks[$scope.weeks.length - 1].end,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };
      function showWeekTimetable() {
        $scope.timetableEvents = null;
        QueryUtils.loadingWheel($scope, true);
        if ($scope.shownWeek) {
          QueryUtils.endpoint(timetableEventsEndpoint).search({
              studentGroups: [$scope.$parent.typeId],
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              QueryUtils.loadingWheel($scope, false);
              $scope.shownStudyPeriods = result.studyPeriods;
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
      $scope.auth = $route.current.locals.auth;

      $scope.schoolId = $scope.auth === undefined ? $route.current.params.schoolId : $scope.auth.school.id;
      var timetableEventsEndpoint = '/timetableevents/timetableByTeacher/' + $scope.schoolId;
      var timetableEventsCalendarEndpoint = '/timetableevents/timetableByTeacher/calendar/' + $scope.schoolId;

      $scope.weeks = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query();
      
      if ($route.current.params.teacherId) {
        getDirectRouteParameters($scope, $route, $route.current.params.teacherId);
      }

      $scope.$watchGroup(['$parent.typeId'], function () {
        if ($scope.$parent.typeId) {
          $scope.typeId = $scope.$parent.typeId;
          $scope.weeks.$promise.then(function () {
            if (!$scope.weekIndex) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      });

      $scope.previousWeek = function (typeId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.emptyParentVariable = function () {
        $scope.$parent.typeId = null;
      };

      $scope.generateCalendar = function () {
        if ($scope.shownWeek && $scope.$parent.typeId) {
          QueryUtils.endpoint(timetableEventsCalendarEndpoint).search({
            teachers: [$scope.$parent.typeId],
            from: $scope.weeks[0].start,
            thru: $scope.weeks[$scope.weeks.length - 1].end,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        $scope.timetableEvents = null;
        QueryUtils.loadingWheel($scope, true);
        if ($scope.shownWeek) {
          QueryUtils.endpoint(timetableEventsEndpoint).search({
              teachers: [$scope.$parent.typeId],
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              QueryUtils.loadingWheel($scope, false);
              $scope.shownStudyPeriods = result.studyPeriods;
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
      $scope.auth = $route.current.locals.auth;

      $scope.schoolId = $scope.auth === undefined ? $route.current.params.schoolId : $scope.auth.school.id;
      var timetableEventsEndpoint = '/timetableevents/timetableByRoom/' + $scope.schoolId;
      var timetableEventsCalendarEndpoint = '/timetableevents/timetableByRoom/calendar/' + $scope.schoolId;

      $scope.weeks = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query();

      if ($route.current.params.roomId) {
        getDirectRouteParameters($scope, $route, $route.current.params.roomId);
      }

      $scope.$watchGroup(['$parent.typeId'], function () {
        if ($scope.$parent.typeId) {
          $scope.typeId = $scope.$parent.typeId;
          $scope.weeks.$promise.then(function () {
            if (!$scope.weekIndex) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      });

      $scope.previousWeek = function (typeId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.emptyParentVariable = function () {
        $scope.$parent.typeId = null;
      };

      $scope.generateCalendar = function () {
        if ($scope.shownWeek && $scope.$parent.typeId) {
          QueryUtils.endpoint(timetableEventsCalendarEndpoint).search({
            room: $scope.$parent.typeId,
            from: $scope.weeks[0].start,
            thru: $scope.weeks[$scope.weeks.length - 1].end,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        $scope.timetableEvents = null;
        QueryUtils.loadingWheel($scope, true);
        if ($scope.shownWeek) {
          QueryUtils.endpoint(timetableEventsEndpoint).search({
              room: $scope.$parent.typeId,
              from: $scope.shownWeek.start,
              thru: $scope.shownWeek.end
            })
            .$promise.then(function (result) {
              QueryUtils.loadingWheel($scope, false);
              $scope.shownStudyPeriods = result.studyPeriods;
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
      $scope.schoolId = $scope.auth.school.id;

      $scope.weeks = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query();

      $scope.$watchGroup(['$parent.typeId'], function () {
        if (($scope.auth.isStudent() || $scope.auth.isParent() || $scope.auth.isAdmin()) && $scope.$parent.typeId) {
          $scope.typeId = $scope.$parent.typeId;
          $scope.weeks.$promise.then(function () {
            if (!$scope.weekIndex) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      });

      $scope.previousWeek = function (typeId, previousWeekIndex) {
        changeTimetableParameters($scope, typeId, previousWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.nextWeek = function (typeId, nextWeekIndex) {
        changeTimetableParameters($scope, typeId, nextWeekIndex);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.emptyParentVariable = function () {
        $scope.$parent.typeId = null;
      };

      $scope.generateCalendar = function () {
        if ($scope.shownWeek && $scope.$parent.typeId) {
          QueryUtils.endpoint('/timetableevents/timetableByStudent/calendar').search({
            student: $scope.typeId,
            higher: $scope.auth.higher,
            vocational: $scope.auth.vocational,
            from: $scope.weeks[0].start,
            thru: $scope.weeks[$scope.weeks.length - 1].end,
            lang: $scope.currentLanguage()
          }).$promise.then(function (result) {
            saveCalendar(result);
          });
        }
      };

      function showWeekTimetable() {
        $scope.timetableEvents = null;
        QueryUtils.loadingWheel($scope, true);
        if ($scope.shownWeek) {
          QueryUtils.endpoint('/timetableevents/timetableByStudent').search({
            student: $scope.typeId,
            from: $scope.shownWeek.start,
            thru: $scope.shownWeek.end,
            higher: $scope.auth.higher,
            vocational: $scope.auth.vocational
          }).$promise.then(function (result) {
            QueryUtils.loadingWheel($scope, false);
            $scope.shownStudyPeriods = result.studyPeriods;
            fillTimetable($scope, result.timetableEvents);
          });
        }
      }
    }
  ]);
}());
