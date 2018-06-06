(function () {
  'use strict';

  function getLastTimetableWeek(scope) {
    scope.shownTimetableIndex = scope.timetables.length-1;
    scope.shownTimetableId = scope.timetables[scope.shownTimetableIndex].id;
    scope.shownStudyPeriodId = scope.timetables[scope.shownTimetableIndex].studyPeriodId;
    scope.shownWeekIndex = scope.weeks.length-1;
    scope.shownWeek = {
      start: scope.weeks[scope.shownWeekIndex].start,
      end: scope.weeks[scope.shownWeekIndex].end
    }
  }

  angular.module('hitsaOis').factory('GeneralTimetableUtils', [
    function () {
      var GeneralTimetableUtil = function () {
        var self = this;

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
  ]).controller('GeneralTimetableSearchController', ['$scope', '$route', 'message', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, message, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "search";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();      
      $scope.auth = $route.current.locals.auth;
      $scope.schoolId = $scope.auth.school.id;
      $scope.formState = {};
      
      QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query().$promise.then(function (weeks) {
        $scope.weeks = weeks;
        var shownWeekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
        $scope.shownWeek = $scope.weeks[shownWeekIndex];
      });

      $scope.search = function() {
        if ($scope.shownWeek) {
          if ($scope.criteria.room || $scope.criteria.teachers || $scope.criteria.studentGroups || $scope.criteria.subject) {
            QueryUtils.endpoint('/timetableevents/timetableSearch').query(
              {room: $scope.criteria.room, teachers: $scope.criteria.teachers, studentGroups: $scope.criteria.studentGroups,
              journalOrSubjectId: $scope.criteria.subject, from: $scope.shownWeek.start, thru: $scope.shownWeek.end})
              .$promise.then(function(result) {
                $scope.searchResultEvents = result;
            });
          } else {
            $scope.searchResultEvents = null;
          }
        }
      };

      $scope.clearCriteria = function() {
        $scope.criteria = {};
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

      $scope.$watch('criteria.subjectObject', function() {
        $scope.criteria.subject = $scope.criteria.subjectObject ? $scope.criteria.subjectObject.id : null;
      });
      
      QueryUtils.createQueryForm($scope, '/timetableevents', {order: 'id'});
    }
  ]).controller('GeneralTimetableByGroupController', ['$scope', '$location', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $location, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "studentGroup";
      $scope.auth = $route.current.locals.auth;
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.typeId = undefined;

      if ($route.current.params.schoolId) {
        $scope.schoolId = $route.current.params.schoolId  
      } else {
        if ($scope.auth) {
          $scope.schoolId = $scope.auth.school.id;
        } else {
          $location.url('/timetables');
        }
      }

      if ($scope.schoolId) {
        var groupTimetablesEndpoint = '/timetables/group/' + $scope.schoolId;
  
        QueryUtils.endpoint(groupTimetablesEndpoint).query().$promise.then(function (result) {
          $scope.groupTimetables = result;
        });
      }

      $scope.getStudentGroups = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return $scope.groupTimetables.filter(function (it) { return it.groupCode.toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.showGroupWeek = function (groupId) {
        $scope.typeId = groupId;
      };
    }
  ]).controller('GeneralTimetableByTeacherController', ['$scope', '$location', '$route', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $location, $route, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "teacher";
      $scope.auth = $route.current.locals.auth;
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.typeId = undefined;

      if ($route.current.params.schoolId) {
        $scope.schoolId = $route.current.params.schoolId  
      } else {
        if ($scope.auth) {
          $scope.schoolId = $scope.auth.school.id;
        } else {
          $location.url('/timetables');
        }
      }

      if ($scope.schoolId) {
        var teacherPeriodTimetablesEndpoint = '/timetables/teacher/' + $scope.schoolId;

        QueryUtils.endpoint(teacherPeriodTimetablesEndpoint).query().$promise.then(function (result) {
          $scope.timetablesByTeachers = result;
        });
      }

      $scope.getTeachers = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return $scope.timetablesByTeachers.filter(function (it) { return (it.firstname + ' ' + it.lastname).toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.showTeacherWeek = function (teacherId) {
        $scope.typeId = teacherId;
      };
    }
  ]).controller('GeneralTimetableByRoomController', ['$scope', '$location', '$route', 'QueryUtils', 'GeneralTimetableUtils',
      function ($scope, $location, $route, QueryUtils, GeneralTimetableUtils) {
        $scope.currentNavItem = "room";
        $scope.auth = $route.current.locals.auth;
        $scope.generalTimetableUtils = new GeneralTimetableUtils();
        $scope.typeId = undefined;

        if ($route.current.params.schoolId) {
          $scope.schoolId = $route.current.params.schoolId  
        } else {
          if ($scope.auth) {
            $scope.schoolId = $scope.auth.school.id;
          } else {
            $location.url('/timetables');
          }
        }

        if ($scope.schoolId) {
          var roomPeriodTimetablesEndpoint = '/timetables/room/' + $scope.schoolId;
  
          QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query().$promise.then(function (weeks) {
            $scope.weeks = weeks;
            var shownWeekIndex = $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
            $scope.shownWeek = $scope.weeks[shownWeekIndex];
          });
    
          QueryUtils.endpoint(roomPeriodTimetablesEndpoint).query().$promise.then(function (result) {
            $scope.timetablesByRooms = result;
          });
        }

        $scope.getRooms = function (searchText) {
          searchText = (searchText || '').toUpperCase();
          return $scope.timetablesByRooms.filter(function (it) { return it.roomCode.toUpperCase().indexOf(searchText) !== -1; });
        };

        $scope.showRoomWeek = function (roomId) {
          $scope.typeId = roomId;
        };

      }
    ]).controller('PersonalGeneralTimetableController', ['$scope', '$route', 'QueryUtils', 'GeneralTimetableUtils',
      function ($scope, $route, QueryUtils, GeneralTimetableUtils) {
        $scope.currentNavItem = "personal";
        $scope.auth = $route.current.locals.auth;
        $scope.generalTimetableUtils = new GeneralTimetableUtils();

        $scope.schoolId = $scope.auth.school.id;

        if ($scope.auth.isStudent() || $scope.auth.isParent()) {
          var personId = $scope.auth.student;
        } else if ($scope.auth.isTeacher()) {
          var personId = $scope.auth.teacher;
        }
        $scope.typeId = personId;

        $scope.$watch('timetableId', function() {
          if ($scope.timetables) {
            $scope.shownTimetableIndex = getShownTimetableIndex($scope.timetables, $scope.timetableId);
            $scope.shownStudyPeriodId = $scope.timetables[$scope.shownTimetableIndex].studyPeriodId;
          }
        });

      }
    ]).controller('GeneralTimetableSchoolListController', ['$scope', 'School', '$location',
      function ($scope, School, $location) {
        $scope.schools = School.getSchoolsWithLogo();

        $scope.openSchoolGeneralTimetable = function (schoolId) {
          $location.path('timetable/generalTimetableByGroup/' + schoolId);
        }
      }
    ]);
}());
