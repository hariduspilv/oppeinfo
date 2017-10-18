(function () {
  'use strict';

  function getCurrentWeekIndex(weeks) {
    var currentTime = moment();

    for (var i = 0; i <= weeks.length - 1; i++) {
      var weekStartDate = moment(weeks[i].start).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
      var weekEndDate = moment(weeks[i].end).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });

      if (moment(currentTime).isBetween(weekStartDate, weekEndDate)) {
        return i;
      }
    }
  }

  function getCurrentWeekTimetables(scope, timetables, weeks) {
    var currentWeekIndex = getCurrentWeekIndex(weeks);
    
    if (currentWeekIndex) {
      scope.shownWeek = weeks[currentWeekIndex];
      var timetableIndex = scope.shownWeek.timetableIndex;
      var timetableId = scope.shownWeek.timetableId;

      scope.showTimetablesByCurriculums(timetableId, timetables[timetableIndex].studyPeriodId);
      scope.shownPeriodIndex = getShownTimetableIndex(timetables, scope.timetableId);
    }
  }

  function getShownTimetableIndex(timetables, timetableId) {
    for (var i = 0; i <= timetables.length - 1; i++) {
      if (timetables[i].id === timetableId) {
        return i;
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

  angular.module('hitsaOis').controller('GeneralTimetableByGroupController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.currentNavItem = "studentGroup";
      $scope.auth = $route.current.locals.auth;

      $scope.$watch('timetableId', function() {
        if ($scope.timetables) {
          $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
          $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
        }
      });

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        $scope.weeks = getTimetablesWeeks(result);
        getCurrentWeekTimetables($scope, result, $scope.weeks);
      });

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownStudyPeriodId = studyPeriodId;
        $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, timetableId);

        $scope.timetablesByCurriculums = QueryUtils.endpoint('/timetables/groupPeriodTimetables/').query(
          {studyPeriodId: $scope.shownStudyPeriodId, timetableId: $scope.shownTimetableId });
      };

      $scope.showGroupTimetable = function (studyPeriodId, groupId, timetableId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownTypeId = groupId;
        $scope.shownStudyPeriodId = studyPeriodId;
      };
    }
  ]).controller('GeneralTimetableByTeacherController', ['$scope', '$route', 'QueryUtils',
    function ($scope, $route, QueryUtils) {
      $scope.currentNavItem = "teacher";
      $scope.auth = $route.current.locals.auth;

      $scope.$watch('timetableId', function() {
        if ($scope.timetables) {
          $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
          $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
        }
      });

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        $scope.weeks = getTimetablesWeeks(result);
        getCurrentWeekTimetables($scope, result, $scope.weeks);
      });

      $scope.showTeacherTimetable = function (studyPeriodId, teacherId, timetableId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownTypeId = teacherId;
        $scope.shownStudyPeriodId = studyPeriodId;
      };

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownStudyPeriodId = studyPeriodId;
        $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, timetableId);

        $scope.timetablesByTeachers = QueryUtils.endpoint('/timetables/teacherPeriodTimetables/').query(
          {studyPeriodId: $scope.shownStudyPeriodId, timetableId: $scope.shownTimetableId });
      };
    }
  ]).controller('GeneralTimetableByRoomController', ['$scope', '$route', 'QueryUtils',
      function ($scope, $route, QueryUtils) {
        $scope.currentNavItem = "room";
        $scope.auth = $route.current.locals.auth;

        $scope.$watch('timetableId', function() {
          if ($scope.timetables) {
            $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
            $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
            $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
          }
        });

        QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
          $scope.timetables = result;
          $scope.weeks = getTimetablesWeeks(result);
          getCurrentWeekTimetables($scope, result, $scope.weeks);
        });

        $scope.showRoomTimetable = function (studyPeriodId, roomId, timetableId) {
          $scope.shownTimetableId = timetableId;
          $scope.shownTypeId = roomId;
          $scope.shownStudyPeriodId = studyPeriodId;
        };

        $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
          $scope.shownTimetableId = timetableId;
          $scope.shownStudyPeriodId = studyPeriodId;
          $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, timetableId);

          $scope.timetablesByRooms = QueryUtils.endpoint('/timetables/roomPeriodTimetables/').query(
            {studyPeriodId: $scope.shownStudyPeriodId, timetableId: $scope.shownTimetableId });
        };
      }
    ]).controller('GeneralTimetableByStudentController', ['$scope', '$route', 'QueryUtils',
      function ($scope, $route, QueryUtils) {
        $scope.currentNavItem = "student";
        $scope.auth = $route.current.locals.auth;
        var studentId = $route.current.locals.auth.student;

        $scope.$watch('timetableId', function() {
          if ($scope.timetables) {
            $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
            $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          }
        });

        QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
          $scope.timetables = result;
          $scope.weeks = getTimetablesWeeks(result);

          $scope.shownWeekIndex = getCurrentWeekIndex($scope.weeks);
          if ($scope.shownWeekIndex) {
            var timetableIndex = $scope.weeks[$scope.shownWeekIndex].timetableIndex;
            $scope.shownTimetableId = result[timetableIndex].id;
            $scope.shownStudyPeriodId = result[timetableIndex].studyPeriodId;
            $scope.shownTimetableIndex = timetableIndex;
          }
          $scope.shownTypeId = studentId;
        });

        $scope.showTimetable = function (timetableId, studyPeriodId, index) {
          $scope.shownTimetableId = timetableId;
          $scope.shownStudyPeriodId = studyPeriodId;
          $scope.shownTimetableIndex = index;
        };
      }
    ]);
}());
