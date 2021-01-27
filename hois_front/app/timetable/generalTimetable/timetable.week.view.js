(function () {
  'use strict';
  var TIME_QUOTIENT = 15;
  var DAY_START_TIME = "7:00";
  var ROW_HEIGHT = 22;
  var TOP = 50;

  function getPreviousAndNextWeek(scope) {
    if (scope.shownWeek) {
      scope.weekIndex = scope.shownWeek.weekNr;

      scope.previousWeekIndex = scope.weeks[scope.weekIndex - 1] ? scope.weekIndex - 1 : undefined;
      scope.nextWeekIndex = scope.weeks[scope.weekIndex + 1] ? scope.weekIndex + 1 : undefined;
    }
  }

  function setTimetableEvents(scope, timetableEvents) {
    scope.timetableEvents = timetableEvents;

    for (var i = 0; i < timetableEvents.length; i++) {
      var event = timetableEvents[i];
      var weekDay = moment.parseZone(event.date).isoWeekday() - 1;
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
        person: event.person,
        teachers: event.teachers,
        studentGroups: event.studentGroups,
        subgroups: event.subgroups,
        students: event.students,
        includesEventStudents: event.includesEventStudents,
        addInfo: event.addInfo,
        public: event.publicEvent,
        journalId: event.journalId,
        subjectStudyPeriodId: event.subjectStudyPeriodId,
        showStudyMaterials: event.showStudyMaterials,
        capacityType: event.capacityType,
        left: 0,
        width: 100,
        isExam: event.isExam
      });
    }

    overlappingEvents(scope, timetableEvents);
  }

  function sortTimetableEventsByDays(timetableEvents) {
    var eventsByDays = [];
    for (var i = 0; i < 7; i++) {
      eventsByDays.push([]);
    }

    timetableEvents.forEach(function (event) {
      var weekDay = moment.parseZone(event.date).isoWeekday() - 1;
      eventsByDays[weekDay].push(event);
    });
    return eventsByDays;
  }

  function timetableDayMinutes() {
    var minutes = [];
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
    var minute, minutes;
    var timetableEventsByDays = sortTimetableEventsByDays(timetableEvents);
    for(var w = 0; w < timetableEventsByDays.length; w++) {
      minutes = timetableDayMinutes();
      for (var i = 0; i < timetableEventsByDays[w].length; i++) {
        var event = timetableEventsByDays[w][i];
        var eventIsOverTime = moment(event.timeEnd, "HH:mm").add(-1, 'm').format("HH:mm");
        var eventTakesPlace = false;

        for (minute in minutes) {
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

      for (minute in minutes) {
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
      var date =  moment.parseZone(scope.shownWeek.start).add(i, "days");
      var isToday = date.isSame(moment().tz('Europe/Tallinn'), 'day');
      weekColumns.push({
        name: week[i],
        column: i + 1,
        events: [],
        date: date.format(),
        today: isToday,
        expanded: isToday
      });
    }
    scope.weekColumns = weekColumns;
  }

  function changeTimetableParameters(scope, weekIndex, changeState) {
    scope.weekIndex = weekIndex;
    scope.shownWeek = scope.weeks[weekIndex];
    if (changeState) {
      scope.generalTimetableUtils.changeState({ weekIndex: weekIndex }, scope.schoolId);
    }
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

  function getCapacityTypes(scope, Classifier) {
    var colors = ['amber-100', 'lime-100', 'teal-100', 'indigo-100', 'green-200', 'deep-orange-100', 'deep-purple-200', 'light-green-300', 'blue-grey-200', 'brown-50'];

    var capacityTypes = Classifier.queryForDropdown({
      mainClassCode: 'MAHT',
      higher: scope.auth && scope.auth.higher ? scope.auth.higher : undefined,
      vocational: scope.auth && scope.auth.vocational ? scope.auth.vocational : undefined
    });

    return capacityTypes.$promise.then(function (result) {
      result.forEach(function (item, index) {
        item.color = colors[index % 9];
      });
      return capacityTypes;
    });
  }

  function getEventColor(capacityTypes, eventCapacityType) {
    for (var i = 0; i < capacityTypes.length; i++) {
      if (capacityTypes[i].code === eventCapacityType) {
        if (capacityTypes[i].extraval1 === null) {
          return '#FFFFFF';
        }
        return capacityTypes[i].extraval1;
      }
    }
    return '#BBDEFB';
  }

  function timetableEventsRequest(scope, QueryUtils, url, fillTimetable) {
    QueryUtils.loadingWheel(scope, true);
    QueryUtils.endpoint(url).search(timetableCriteria(scope, true)).$promise.then(function (result) {
      QueryUtils.loadingWheel(scope, false);
      scope.$parent.personalParam = result.personalParam;
      scope.shownStudyPeriods = result.studyPeriods;
      scope.shownTimetableCurriculum = result.generalTimetableCurriculum;
      scope.isHigher = result.isHigher;
      switch (scope.timetableType) {
        case 'person':
          scope.shownPerson = {
            firstname: result.firstname,
            lastname: result.lastname,
            isHigher: result.isHigher
          };
          scope.schoolId = result.schoolId;
          break;
        case 'student':
          scope.shownStudent = {
            firstname: result.firstname,
            lastname: result.lastname,
            isHigher: result.isHigher
          };
          break;
        case 'teacher':
          scope.shownTeacher = {
            firstname: result.firstname,
            lastname: result.lastname
          };
          break;
        case 'room':
          scope.shownRoom = {
            roomCode: result.roomCode,
            buildingCode: result.buildingCode
          };
          break;
      }

      fillTimetable(scope, result.timetableEvents);
    });
  }


  function timetableCriteria(scope, weekTimetable) {
    var criteria = {};
    switch (scope.timetableType) {
      case 'student':
        criteria.student = scope.criteria.studentId;
        break;
      case 'group':
        criteria.studentGroups = [scope.criteria.groupId];
        break;
      case 'teacher':
        criteria.teachers = [scope.criteria.teacherId];
        break;
      case 'room':
        criteria.room = scope.criteria.roomId;
        break;j
    }

    if (weekTimetable) {
      criteria = angular.extend(criteria, {
        from: scope.shownWeek.start,
        thru: scope.shownWeek.end
      });
    } else {
      criteria = angular.extend(criteria, {
        from: scope.weeks[0].start,
        thru: scope.weeks[scope.weeks.length - 1].end,
        lang: scope.currentLanguage().toUpperCase()
      });
    }
    return criteria;
  }

  angular.module('hitsaOis').controller('TimetableWeekViewController', ['$location', '$q', '$route', '$scope', 'Classifier', 'GeneralTimetableUtils', 'QueryUtils',
    function ($location, $q, $route, $scope, Classifier, GeneralTimetableUtils, QueryUtils) {
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.auth = $route.current.locals.auth;

      $scope.getEventTextColor = function(eventCapacityType) {
        for (var i = 0; i < $scope.capacityTypes.length; i++) {
          if ($scope.capacityTypes[i].code === eventCapacityType) {
            return $scope.capacityTypes[i].extraval2;
          }
        }
        return '#000000';
      }

      if ($route.current.params.schoolId) {
        $scope.schoolId = parseInt($route.current.params.schoolId, 10);
      } else if (angular.isDefined($scope.auth) && angular.isDefined($scope.auth.school) && $scope.auth.school !== null) {
        $scope.schoolId = $scope.auth.school.id;
      }
      var capacityTypesPromise = getCapacityTypes($scope, Classifier).then(function (capacityTypes) {
        $scope.capacityTypes = capacityTypes;
      });

      $scope.encodedPerson = $route.current.params.encodedPerson;
      if ($scope.encodedPerson) {
        $scope.timetableType = 'person';
      } else if ($route.current.params.type) {
        $scope.timetableType = $route.current.params.type;
      } else {
        if ($scope.$parent.currentNavItem === 'personal') {
          if ($scope.auth.isStudent() || $scope.auth.isParent()) {
            $scope.timetableType = 'student';
          } else if ($scope.auth.isTeacher()) {
            $scope.timetableType = 'teacher';
          }
        } else if ($scope.$parent.currentNavItem === 'student.timetable') {
          $scope.timetableType = 'student';
        } else {
          $scope.timetableType = $scope.$parent.currentNavItem;
        }
      }

      $scope.typeParam = $scope.timetableType + 'Id';
      var baseUrl = '/timetableevents/timetableBy' + $scope.timetableType.charAt(0).toUpperCase() +
        $scope.timetableType.slice(1) + '/';
      var timetableEventsEndpoint = baseUrl + ($scope.encodedPerson ? $scope.encodedPerson : $scope.schoolId);
      var timetableEventsCalendarEndpoint = timetableEventsEndpoint + '/calendar';

      if ($route.current.locals.isDirectRoute) {
        $scope.directRoute = true;
        $scope.studyYearId = parseInt($route.current.params.studyYearId, 10);
        if ($route.current.params.weekIndex) {
          $scope.weekIndex = parseInt($route.current.params.weekIndex, 10)
        }

        $scope.criteria = {studyYearId: $scope.studyYearId};
        if (!$scope.encodedPerson) {
          $scope.criteria[$scope.typeParam] = $route.current.params.typeId;
          $scope.typeId = $route.current.params.typeId;
        }

        updateWeekTimetable($scope.criteria);
      } else {
        var state = $scope.generalTimetableUtils.loadState($scope.schoolId);
        $scope.studyYearId = state.studyYearId;
        $scope.weekIndex = state.weekIndex;
      }

      $scope.$on('updateWeekTimetable', function (event, args) {
        if (args.criteria) {
          updateWeekTimetable(args.criteria);
        } else {
          $scope.weeks = null;
          $scope.shownWeek = null;
        }
      });

      function updateWeekTimetable(criteria) {
        $scope.criteria = criteria;
        $scope.typeId = criteria[$scope.typeParam];

        var previousStudyYearId = $scope.studyYearId;
        if ($scope.studyYearId !== criteria.studyYearId) {
          $scope.studyYearId = criteria.studyYearId;
        }

        $scope.weeks = null;
        if ($scope.studyYearId) {
          if ($scope.timetableType === 'person') {
            $scope.weeksPromise = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/:studyYearId/person').query({
              studyYearId: $scope.studyYearId,
              encodedPerson: $scope.encodedPerson
            });
          } else {
            $scope.weeksPromise = QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/:studyYearId').query({
              studyYearId: $scope.studyYearId,
              student: $scope.timetableType === 'student' ? $scope.criteria[$scope.typeParam] : null
            });
          }

          $q.all([capacityTypesPromise, $scope.weeksPromise.$promise]).then(function (result) {
            $scope.weeks = result[1];

            // get weekIndex when it doesn't exist, the week doesn't exists or the week is not of the year
            if (angular.isUndefined($scope.weekIndex) || angular.isUndefined($scope.weeks[$scope.weekIndex]) ||
              (angular.isDefined($scope.weeks[$scope.weekIndex]) && $scope.weeks[$scope.weekIndex].studyYear !== previousStudyYearId)) {
              $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            }
            $scope.shownWeek = $scope.weeks[$scope.weekIndex];
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          });
        }
      }

      $scope.previousWeek = function (previousWeekIndex) {
        changeTimetableParameters($scope, previousWeekIndex, true);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.nextWeek = function (nextWeekIndex) {
        changeTimetableParameters($scope, nextWeekIndex, true);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      };

      $scope.weekSelectChange = function (selectedWeek) {
        if (angular.isDefined(selectedWeek)) {
          if ($scope.directRoute) {
            if ($scope.encodedPerson) {
              $location.url('/timetable/person/' + $scope.encodedPerson + '/' + $scope.studyYearId + '/' + selectedWeek.weekNr);
            } else {
              $location.url('/timetable/' + $scope.schoolId + '/' + $scope.timetableType + '/' +
              $scope.typeId + '/' + $scope.studyYearId + '/' + selectedWeek.weekNr);
            }
          } else {
            changeTimetableParameters($scope, selectedWeek.weekNr, true);
            getPreviousAndNextWeek($scope);
            showWeekTimetable();
          }
        }
      };

      $scope.getEventColor = function (eventCapacityType, event) {
        if (event !== undefined && event.isExam) {
          return '#F8BBD0';
        }
        return getEventColor($scope.capacityTypes, eventCapacityType);
      };

      function showWeekTimetable() {
        $scope.timetableEvents = null;
        if ($scope.shownWeek && ($scope.encodedPerson || $scope.criteria[$scope.typeParam])) {
          timetableEventsRequest($scope, QueryUtils, timetableEventsEndpoint, fillTimetable);
        }
      }

      $scope.generateCalendar = function () {
        QueryUtils.endpoint(timetableEventsCalendarEndpoint).search(timetableCriteria($scope, false)).$promise.then(function (result) {
          saveCalendar(result);
        });
      };

    }
  ]).controller('SchoolBoardTimetableWeekViewController', ['$q', '$rootScope', '$route', '$scope', '$timeout', 'Classifier', 'GeneralTimetableUtils', 'QueryUtils',
    function($q, $rootScope, $route, $scope, $timeout, Classifier, GeneralTimetableUtils, QueryUtils) {
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.schoolId = $route.current.params.schoolId;
      $scope.timetableType = $route.current.params.type;
      $scope.typeId = $route.current.params.typeId;

      $scope.hideLinks = true;
      $scope.hideWeekSelect = true;
      $scope.hideWeekStepButtons = $scope.timetableType !== 'group';

      $scope.typeParam = $scope.timetableType + 'Id';
      $scope.criteria = {};
      $scope.criteria[$scope.typeParam] = $route.current.params.typeId;

      var timetableEventsEndpoint = '/schoolBoard/' + $scope.schoolId + '/timetableBy' +
        $scope.timetableType.charAt(0).toUpperCase() + $scope.timetableType.slice(1);

      var capacityTypesPromise = getCapacityTypes($scope, Classifier).then(function (capacityTypes) {
        $scope.capacityTypes = capacityTypes;
      });
      $scope.weeksPromise = QueryUtils.endpoint('/schoolBoard/:schoolIdentifier/timetableStudyYearWeeks/').query({
        schoolIdentifier: $scope.schoolId
      });

      $q.all([capacityTypesPromise, $scope.weeksPromise.$promise]).then(function (result) {
        $scope.weeks = result[1];
        $scope.weekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
        $scope.shownWeek = $scope.weeks[$scope.weekIndex];
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
      });

      function showWeekTimetable() {
        $scope.timetableEvents = null;
        if ($scope.shownWeek) {
          timetableEventsRequest($scope, QueryUtils, timetableEventsEndpoint, fillTimetable);
        }
      }

      $scope.previousWeek = function (previousWeekIndex) {
        changeTimetableParameters($scope, previousWeekIndex, true);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
        $scope.$emit('resetReturnToSchoolBoard');
      };

      $scope.nextWeek = function (nextWeekIndex) {
        changeTimetableParameters($scope, nextWeekIndex, true);
        getPreviousAndNextWeek($scope);
        showWeekTimetable();
        $scope.$emit('resetReturnToSchoolBoard');
      };

      $scope.getEventColor = function (eventCapacityType, event) {
        if (event !== undefined && event.isExam) {
          return '#F8BBD0';
        }
        return getEventColor($scope.capacityTypes, eventCapacityType);
      };
    }
  ]);
}());
