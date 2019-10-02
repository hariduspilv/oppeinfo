(function () {
  'use strict';

  angular.module('hitsaOis').factory('GeneralTimetableUtils', ['DataUtils', 'stateStorageService',
    function (DataUtils, stateStorageService) {
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

        this.getStudyYearId = function (schoolId, studyYears) {
          // if state has studyYear and it is included in given studyYears then return that
          var state = this.loadState(schoolId);

          if (state.studyYearId) {
            var studyYearIds = (studyYears || []).map(function (sy) {
              return sy.id;
            });
            if (studyYearIds.indexOf(state.studyYearId) !== -1) {
              return state.studyYearId;
            }
          }

          var currentSy = DataUtils.getCurrentStudyYearOrPeriod(studyYears);
          return currentSy ? currentSy.id : null;
        };
        
        this.loadState = function(schoolId) {
          return stateStorageService.loadState(schoolId, stateKey);
        }

        this.changeState = function(newState, schoolId) {
          stateStorageService.changeState(schoolId, stateKey, newState);
        }

      }
      return GeneralTimetableUtil;
    }
  ]).controller('GeneralTimetableSearchController', ['$route', '$scope', 'GeneralTimetableUtils', 'QueryUtils', 'message',
    function ($route, $scope, GeneralTimetableUtils, QueryUtils, message) {
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.auth = $route.current.locals.auth;
      $scope.currentNavItem = "search";
      $scope.formState = {};
      $scope.criteria = {};

      if ($route.current.params.schoolId) {
        $scope.schoolId = $route.current.params.schoolId;
      } else {
        if ($scope.auth) {
          $scope.schoolId = $scope.auth.school.id;
        } else {
          $location.url('/timetables');
        }
      }

      if ($scope.schoolId) {
        QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
          $scope.studyYears = result;
          $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
        });
      }

      $scope.$watch('criteria.studyYearId', function () {
        if (angular.isDefined($scope.criteria.studyYearId) && $scope.criteria.studyYearId !== null) {
          QueryUtils.endpoint('/timetableevents/timetableSearch/searchFormData/:schoolId/:studyYearId').search({
            schoolId: $scope.schoolId,
            studyYearId: $scope.criteria.studyYearId
          }).$promise.then(function (result) {
            $scope.formState.teachers = result.teachers ? result.teachers : [];
            $scope.formState.studentGroups = result.studentGroups ? result.studentGroups : [];
            $scope.formState.rooms = result.rooms ? result.rooms : [];
            $scope.formState.subjects = result.subjects ? result.subjects : [];
            setCriteria();
            if (atLeastOneParameterFilled()) {
              search();
            }
          });
        }
      });

      function setCriteria() {
        angular.extend($scope.criteria, $scope.generalTimetableUtils.loadState($scope.schoolId));
        if ($scope.criteria.subjectObject) {
          var studyYearSubject = $scope.formState.subjects.filter(function (it) { return it.id === $scope.criteria.subjectObject.id });
          $scope.criteria.subjectObject = studyYearSubject;
        }
      }

      function saveCriteria() {
        $scope.generalTimetableUtils.changeState({
          roomObject: $scope.criteria.roomObject,
          teacherObject: $scope.criteria.teacherObject,
          studentGroupObject: $scope.criteria.studentGroupObject,
          subjectObject: $scope.criteria.subjectObject
        }, $scope.schoolId);
      }

      $scope.search = function() {
        if (atLeastOneParameterFilled()) {
          search();
        } else {
          message.error('timetable.error.atleastOneParameterMustBeFilled');
        }
      };

      function search() {
        saveCriteria();
        $scope.$broadcast("updateWeekTimetable", { criteria: $scope.criteria });
      }

      function atLeastOneParameterFilled() {
        return $scope.criteria.roomObject != null || $scope.criteria.teacherObject != null ||
          $scope.criteria.studentGroupObject != null || $scope.criteria.subjectObject != null;
      }

      $scope.parameterSearch = function (searchText, formData) {
        searchText = (searchText || '').toUpperCase();
        return (formData || []).filter(function (it) { return $scope.currentLanguageNameField(it).toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.changeStudyYear = function (studyYear) {
        $scope.generalTimetableUtils.changeState({ studyYearId: studyYear.id }, $scope.schoolId);
        $scope.criteria.studyYearId = studyYear.id;
      };

      $scope.clearSearchParameters = function() {
        $scope.criteria = {studyYearId: $scope.criteria.studyYearId};
        $scope.teacherSearchText = null;
        $scope.studentGroupSearchText = null;
        $scope.roomSearchText = null;
        $scope.subjectSearchText = null;
      };

    }
  ]).controller('GeneralTimetableController', ['$route', '$location', '$scope', 'GeneralTimetableUtils', 'QueryUtils',
    function ($route, $location, $scope, GeneralTimetableUtils, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.generalTimetableUtils = new GeneralTimetableUtils();
      $scope.currentNavItem = $route.current.params.type;
      $scope.typeParam = $scope.currentNavItem + 'Id';
      $scope.criteria = {};

      if ($route.current.params.schoolId) {
        $scope.schoolId = $route.current.params.schoolId;
      } else {
        if ($scope.auth) {
          $scope.schoolId = $scope.auth.school.id;
        } else {
          $location.url('/timetables');
        }
      }

      QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
        $scope.studyYears = result;
        $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
      });

      $scope.$watch('criteria.studyYearId', function () {
        if (angular.isDefined($scope.criteria.studyYearId) && $scope.criteria.studyYearId !== null) {
          QueryUtils.endpoint('/timetables/:type/:schoolId/:studyYearId').query({
            type: $scope.currentNavItem,
            schoolId: $scope.schoolId,
            studyYearId: $scope.criteria.studyYearId
          }).$promise.then(function (result) {
            $scope.typeTimetables = result;

            var state = $scope.generalTimetableUtils.loadState($scope.schoolId);
            if (state[$scope.typeParam]) {
              var studyYearTimetable = $scope.typeTimetables.filter(function (it) { return it[$scope.typeParam] === state[$scope.typeParam] });
              $scope.criteria.selectedTimetable = studyYearTimetable ? studyYearTimetable[0] : null;
            }
            $scope.showTypeWeek($scope.criteria.selectedTimetable ? $scope.criteria.selectedTimetable.id : null);
          });
        }
      });

      $scope.changeStudyYear = function (studyYear) {
        $scope.generalTimetableUtils.changeState({ studyYearId: studyYear.id }, $scope.schoolId);
        $scope.criteria.studyYearId = studyYear.id;
      };

      $scope.getStudentGroups = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return ($scope.typeTimetables || []).filter(function (it) { return it.groupCode.toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.getTeachers = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return ($scope.typeTimetables || []).filter(function (it) { return (it.firstname + ' ' + it.lastname).toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.getRooms = function (searchText) {
        searchText = (searchText || '').toUpperCase();
        return ($scope.typeTimetables || []).filter(function (it) { return it.code.toUpperCase().indexOf(searchText) !== -1; });
      };

      $scope.showTypeWeek = function (typeId) {
        if (angular.isDefined(typeId)) {
          var newState = {};
          newState[$scope.typeParam] = typeId;
          $scope.criteria[$scope.typeParam] = typeId;
          $scope.generalTimetableUtils.changeState(newState, $scope.schoolId);
          $scope.$broadcast("updateWeekTimetable", { criteria: $scope.criteria });
        }
      };

    }
  ]).controller('PersonalGeneralTimetableController', ['$route', '$scope', 'GeneralTimetableUtils', 'QueryUtils',
      function ($route, $scope, GeneralTimetableUtils, QueryUtils) {
        $scope.auth = $route.current.locals.auth;
        $scope.generalTimetableUtils = new GeneralTimetableUtils();
        $scope.currentNavItem = 'personal';
        $scope.criteria = {};

        $scope.schoolId = $scope.auth.school.id;
        QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
          $scope.studyYears = result;
          $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
        });

        if ($scope.auth.isStudent() || $scope.auth.isParent()) {
          $scope.typeParam = 'studentId';
          $scope.criteria[$scope.typeParam] = $scope.auth.student;
        } else if ($scope.auth.isTeacher()) {
          $scope.typeParam = 'teacherId';
          $scope.criteria[$scope.typeParam] = $scope.auth.teacher;
        }

        $scope.$watch('criteria.studyYearId', function () {
          if (angular.isDefined($scope.criteria.studyYearId)) {
            $scope.showPersonalWeek();
          }
        });

        $scope.changeStudyYear = function (studyYear) {
          $scope.generalTimetableUtils.changeState({ studyYearId: studyYear.id }, $scope.schoolId);
          $scope.criteria.studyYearId = studyYear.id;
        };  

        $scope.showPersonalWeek = function () {
          var newState = {};
          newState[$scope.typeParam] = $scope.criteria[$scope.typeParam];
          $scope.generalTimetableUtils.changeState(newState, $scope.schoolId);
          $scope.$broadcast("updateWeekTimetable", { criteria: $scope.criteria });
        };

      }
    ]).controller('GeneralTimetableSchoolListController', ['$scope', 'School', '$location',
      function ($scope, School, $location) {
        $scope.schools = School.getSchoolsWithLogo();

        $scope.openSchoolGeneralTimetable = function (schoolId) {
          $location.path('timetable/' + schoolId +  '/generalTimetable/group/');
        }
      }
    ]);
}());
