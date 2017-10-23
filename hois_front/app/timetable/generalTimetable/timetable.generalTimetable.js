(function () {
  'use strict';

  function getCurrentWeekTimetables(scope, timetables, weeks) {
    var currentWeekIndex = scope.generalTimetableUtils.getCurrentWeekIndex(weeks);
    
    if (currentWeekIndex) {
      scope.shownWeek = weeks[currentWeekIndex];
      var timetableIndex = scope.shownWeek.timetableIndex;
      var timetableId = scope.shownWeek.timetableId;
      var studyPeriodId = timetables[timetableIndex].studyPeriodId;

      scope.showTimetablesByCurriculums(timetableId, studyPeriodId);
      scope.shownTimetableIndex = getShownTimetableIndex(timetables, timetableId);
      scope.shownTimetableId = timetableId;
      scope.shownStudyPeriodId = studyPeriodId;
    }
  }

  function getShownTimetableIndex(timetables, timetableId) {
    for (var i = 0; i <= timetables.length - 1; i++) {
      if (timetables[i].id === timetableId) {
        return i;
      }
    }
  }

  function getTimetableFirstWeekIndex(weeks, timetableId) {
    for (var i = 0; i < weeks.length; i++) {
      if (weeks[i].timetableId === timetableId) {
        return i;
      }
    }
  }

  function setIndexes(scope, timetableId) {
    if (isWeekInTimetable(scope, scope.generalTimetableUtils.getCurrentWeekIndex(scope.weeks), timetableId)) {
      scope.shownWeekIndex = scope.generalTimetableUtils.getCurrentWeekIndex(scope.weeks);
      scope.shownTimetableIndex = getShownTimetableIndex(scope.timetables, scope.weeks[scope.shownWeekIndex].timetableId);
    } else {
      scope.shownWeekIndex = getTimetableFirstWeekIndex(scope.weeks, timetableId);
      scope.shownTimetableIndex = getShownTimetableIndex(scope.timetables, scope.weeks[scope.shownWeekIndex].timetableId);
    }
  }

  function isWeekInTimetable(scope, weekIndex, timetableId) {
    var weekTimetablId = scope.weeks[weekIndex].timetableId;
    if (weekTimetablId === timetableId) {
      return true;
    }
    return false;
  }

  angular.module('hitsaOis').factory('GeneralTimetableUtils', [
    function () {
      var GeneralTimetableUtil = function () {
        var self = this;
        
        this.getTimetablesWeeks = function (timetables) {
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
              week.end.setHours(23, 59, 59);
              week.timetableIndex = i;
              week.timetableId = timetables[i].id;

              weeks.push(week);
              start = new Date(start.getFullYear(), start.getMonth(), start.getDate() + 1);
            }
          }
          return weeks;
        };

        this.getCurrentWeekIndex = function (weeks) {
          var currentTime = moment();

          for (var i = 0; i <= weeks.length - 1; i++) {
            var weekStartDate = moment(weeks[i].start).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });
            var weekEndDate = moment(weeks[i].end).set({ hour: 0, minute: 0, second: 0, millisecond: 0 });

            if (moment(currentTime).isBetween(weekStartDate, weekEndDate)) {
              return i;
            }
          }
        }
      }
      return GeneralTimetableUtil;
    }
  ]).controller('GeneralTimetableSearchController', ['$scope', 'message', 'QueryUtils',
    function ($scope, message, QueryUtils) {
      $scope.formState = {};
  
      $scope.clearCriteria = function() {
        $scope.criteria = {};
      };

      $scope.search = function() {
        if ($scope.criteria.room || $scope.criteria.teachers || $scope.criteria.studentGroups) {
          QueryUtils.endpoint('/timetableevents/timetableSearch').query(
            {room: $scope.criteria.room, teachers: $scope.criteria.teachers, studentGroups: $scope.criteria.studentGroups,
            name: $scope.criteria.name, from: $scope.shownWeek.start, thru: $scope.shownWeek.end})
            .$promise.then(function(result) {
              console.log(result);
          });
        }
      };

      $scope.$watch('criteria.roomObject', function() {
        $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
      });

      $scope.$watch('criteria.teacher', function() {
        $scope.criteria.teachers = $scope.criteria.teacher ? $scope.criteria.teacher.id : null;
      });

      $scope.$watch('criteria.studentGroup', function() {
        $scope.criteria.studentGroups = $scope.criteria.studentGroup ? $scope.criteria.studentGroup.id : null;
      });

      $scope.$watch('criteria.nameObject', function() {
        $scope.criteria.name = $scope.criteria.nameObject ? $scope.criteria.nameObject.nameEt : null;
      });
      
      QueryUtils.createQueryForm($scope, '/timetableevents', {order: 'id'});
    }
  ]).controller('GeneralTimetableByGroupController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "studentGroup";
      $scope.auth = $route.current.locals.auth;
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watch('timetableId', function() {
        if ($scope.timetables) {
          $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
          $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
        }
      });

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks(result);
        getCurrentWeekTimetables($scope, result, $scope.weeks);
      });

      $scope.showTimetable = function (timetableId, studyPeriodId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownStudyPeriodId = studyPeriodId;
        setIndexes($scope, timetableId);
        $scope.showTimetablesByCurriculums(timetableId, studyPeriodId);
      };

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.timetablesByCurriculums = QueryUtils.endpoint('/timetables/groupPeriodTimetables/').query(
          {studyPeriodId: studyPeriodId, timetableId: timetableId });
      };

      $scope.showGroupWeek = function (studyPeriodId, groupId, timetableId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownTypeId = groupId;
        $scope.shownStudyPeriodId = studyPeriodId;
      };
    }
  ]).controller('GeneralTimetableByTeacherController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "teacher";
      $scope.auth = $route.current.locals.auth;
      $scope.generalTimetableUtils = new GeneralTimetableUtils();

      $scope.$watch('timetableId', function() {
        if ($scope.timetables) {
          $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
          $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
        }
      });

      QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
        $scope.timetables = result;
        $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks(result);
        getCurrentWeekTimetables($scope, result, $scope.weeks);
      });

      $scope.showTimetable = function (timetableId, studyPeriodId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownStudyPeriodId = studyPeriodId;
        setIndexes($scope, timetableId);
        $scope.showTimetablesByCurriculums(timetableId, studyPeriodId);
      };

      $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
        $scope.timetablesByTeachers = QueryUtils.endpoint('/timetables/teacherPeriodTimetables/').query(
          {studyPeriodId: studyPeriodId, timetableId: timetableId});
      };

      $scope.showTeacherWeek = function (studyPeriodId, teacherId, timetableId) {
        $scope.shownTimetableId = timetableId;
        $scope.shownTypeId = teacherId;
        $scope.shownStudyPeriodId = studyPeriodId;
      };
    }
  ]).controller('GeneralTimetableByRoomController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
      function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
        $scope.currentNavItem = "room";
        $scope.auth = $route.current.locals.auth;
        $scope.generalTimetableUtils = new GeneralTimetableUtils();

        $scope.$watch('timetableId', function() {
          if ($scope.timetables) {
            $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
            $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
            $scope.showTimetablesByCurriculums($scope.timetableId, $scope.shownStudyPeriodId);
          }
        });

        QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
          $scope.timetables = result;
          $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks(result);
          getCurrentWeekTimetables($scope, result, $scope.weeks);
        });

        $scope.showTimetable = function (timetableId, studyPeriodId) {
          $scope.shownTimetableId = timetableId;
          $scope.shownStudyPeriodId = studyPeriodId;
          setIndexes($scope, timetableId);
          $scope.showTimetablesByCurriculums(timetableId, studyPeriodId);
        };

        $scope.showTimetablesByCurriculums = function (timetableId, studyPeriodId) {
          $scope.timetablesByRooms = QueryUtils.endpoint('/timetables/roomPeriodTimetables/').query(
            {studyPeriodId: studyPeriodId, timetableId: timetableId});
        };

        $scope.showRoomWeek = function (studyPeriodId, roomId, timetableId) {
          $scope.shownTimetableId = timetableId;
          $scope.shownTypeId = roomId;
          $scope.shownStudyPeriodId = studyPeriodId;
        };
      }
    ]).controller('GeneralTimetableByStudentController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
      function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
        $scope.currentNavItem = "student";
        $scope.auth = $route.current.locals.auth;
        $scope.generalTimetableUtils = new GeneralTimetableUtils();
        var studentId = $route.current.locals.auth.student;

        $scope.$watch('timetableId', function() {
          if ($scope.timetables) {
            $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
            $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          }
        });

        QueryUtils.endpoint('/timetables/generalTimetables/').query().$promise.then(function (result) {
          $scope.timetables = result;
          $scope.weeks = $scope.generalTimetableUtils.getTimetablesWeeks(result);

          $scope.shownWeekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
          if ($scope.shownWeekIndex) {
            var timetableIndex = $scope.weeks[$scope.shownWeekIndex].timetableIndex;
            $scope.shownTimetableId = result[timetableIndex].id;
            $scope.shownStudyPeriodId = result[timetableIndex].studyPeriodId;
            $scope.shownTimetableIndex = timetableIndex;
          }
          $scope.shownTypeId = studentId;
        });

        $scope.showTimetable = function (timetableId, studyPeriodId) {
          $scope.shownTimetableId = timetableId;
          $scope.shownStudyPeriodId = studyPeriodId;
          setIndexes($scope, timetableId);
        };
      }
    ]);
}());
