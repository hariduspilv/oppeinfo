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

  angular.module('hitsaOis').factory('GeneralTimetableUtils', ['$sessionStorage',
    function ($sessionStorage) {
      var GeneralTimetableUtil = function () {
        var self = this;
        var stateKey = 'timetableState';

        this.getCurrentWeekIndex = function (weeks) {
          var currentTime = moment();

          for (var i = 0; i <= weeks.length - 1; i++) {
            var weekStartDate = moment(weeks[i].start).startOf('day')
            var weekEndDate = moment(weeks[i].end).endOf('day')

            if (moment(currentTime).isBetween(weekStartDate, weekEndDate, null, '[]')) {
              return i;
            }
          }

          // if current week doesn't exist, then return first week index if current date is before first week start date
          // else return last week index
          if (weeks && weeks.length > 0) {
            return currentTime < moment(weeks[0].start).startOf('day') ? 0 : weeks.length - 1;
          }
        }
        
        this.loadState = function(schoolId) {
          var storage = $sessionStorage[stateKey] || '{}';
          var schoolStates = JSON.parse(storage);
          return schoolStates[schoolId] || {};
        }

        this.changeState = function(newState, schoolId) {
          var schoolState = {};
          schoolState[schoolId] = angular.extend(self.loadState(schoolId), newState);
          $sessionStorage[stateKey] = JSON.stringify(angular.extend(JSON.parse($sessionStorage[stateKey] || '{}'), schoolState));
        }

      }
      return GeneralTimetableUtil;
    }
  ]).controller('GeneralTimetableSearchController', ['$scope', '$route', 'message', 'QueryUtils', 'GeneralTimetableUtils',
    function ($scope, $route, message, QueryUtils, GeneralTimetableUtils) {
      $scope.currentNavItem = "search";
      $scope.generalTimetableUtils = new GeneralTimetableUtils();      
      $scope.auth = $route.current.locals.auth;
      $scope.formState = {};
      
      if ($route.current.params.schoolId) {
        $scope.schoolId = $route.current.params.schoolId  
      } else {
        if ($scope.auth) {
          $scope.schoolId = $scope.auth.school.id;
        } else {
          $location.url('/timetables');
        }
      }

      QueryUtils.endpoint('/timetableevents/timetableSearch/searchFormData/' + $scope.schoolId).search().$promise.then(function (result) {
        $scope.formState.teachers = result.teachers ? result.teachers : [];
        $scope.formState.studentGroups = result.studentGroups ? result.studentGroups : [];
        $scope.formState.rooms = result.rooms ? result.rooms : [];
        $scope.formState.subjects = result.subjects ? result.subjects : [];
      });

      function saveCriteria() {
        $scope.generalTimetableUtils.changeState({
          roomObject: $scope.criteria.roomObject,
          teacherObject: $scope.criteria.teacherObject,
          studentGroupObject: $scope.criteria.studentGroupObject,
          subjectObject: $scope.criteria.subjectObject
        }, $scope.schoolId);
      }

      function loadCriteria() {
        angular.extend($scope.criteria, $scope.generalTimetableUtils.loadState($scope.schoolId));
      }

      QueryUtils.endpoint('/timetables/timetableStudyYearWeeks/' + $scope.schoolId).query().$promise.then(function (weeks) {
        loadCriteria();
        setCriteria();
        $scope.weeks = weeks;
        var shownWeekIndex = angular.isDefined($scope.criteria.weekIndex) ? $scope.criteria.weekIndex : $scope.generalTimetableUtils.getCurrentWeekIndex($scope.weeks);
        $scope.shownWeek = $scope.weeks[shownWeekIndex];
        $scope.search();
      });

      $scope.search = function() {
        if ($scope.shownWeek) {
          if ($scope.criteria.room || $scope.criteria.teachers || $scope.criteria.studentGroups || $scope.criteria.subject) {
            saveCriteria();
            QueryUtils.endpoint('/timetableevents/timetableSearch/' + $scope.schoolId).query(
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

      $scope.clearSearchParameters = function() {
        $scope.criteria = {};
        $scope.teacherSearchText = null;
        $scope.studentGroupSearchText = null;
        $scope.roomSearchText = null;
        $scope.subjectSearchText = null;
      };

      $scope.parameterSearch = function (searchText, formData) {
        searchText = (searchText || '').toUpperCase();
        return formData.filter(function (it) { return $scope.currentLanguageNameField(it).toUpperCase().indexOf(searchText) !== -1; });
      };

      function setCriteria() {
        $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
        $scope.criteria.teachers = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.id : null;
        $scope.criteria.studentGroups = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
        $scope.criteria.subject = $scope.criteria.subjectObject ? $scope.criteria.subjectObject.id : null;
      }

      $scope.$watch('criteria.roomObject', function() {
        $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
      });

      $scope.$watch('criteria.teacherObject', function() {
        $scope.criteria.teachers = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.id : null;
      });

      $scope.$watch('criteria.studentGroupObject', function() {
        $scope.criteria.studentGroups = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
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

      function saveGroupId(groupId) {
        $scope.generalTimetableUtils.changeState({ groupId: groupId }, $scope.schoolId);
      }
    
      function loadGroupId() {
        var state = $scope.generalTimetableUtils.loadState($scope.schoolId);
        if (state.groupId) {
          $scope.typeId = state.groupId;
        }
      }

      loadGroupId();
    
      $scope.getStudentGroups = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return $scope.groupTimetables.filter(function (it) { return it.groupCode.toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.showGroupWeek = function (groupId) {
        $scope.typeId = groupId;
        saveGroupId(groupId);
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

      function saveTeacherId(teacherId) {
        $scope.generalTimetableUtils.changeState({ teacherId: teacherId }, $scope.schoolId);
      }
    
      function loadTeacherId() {
        var state = $scope.generalTimetableUtils.loadState($scope.schoolId);
        if (state.teacherId) {
          $scope.typeId = state.teacherId;
        }
      }

      loadTeacherId();
    
      $scope.getTeachers = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return $scope.timetablesByTeachers.filter(function (it) { return (it.firstname + ' ' + it.lastname).toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.showTeacherWeek = function (teacherId) {
        $scope.typeId = teacherId;
        saveTeacherId(teacherId);
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
    
          QueryUtils.endpoint(roomPeriodTimetablesEndpoint).query().$promise.then(function (result) {
            $scope.timetablesByRooms = result;
          });
        }

        function saveRoomId(roomId) {
          $scope.generalTimetableUtils.changeState({ roomId: roomId }, $scope.schoolId);
        }
      
        function loadRoomId() {
          var state = $scope.generalTimetableUtils.loadState($scope.schoolId);
          if (state.roomId) {
            $scope.typeId = state.roomId;
          }
        }
  
        loadRoomId();
      
        $scope.getRooms = function (searchText) {
          searchText = (searchText || '').toUpperCase();
          return $scope.timetablesByRooms.filter(function (it) { return it.code.toUpperCase().indexOf(searchText) !== -1; });
        };

        $scope.showRoomWeek = function (roomId) {
          $scope.typeId = roomId;
          saveRoomId(roomId);
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
