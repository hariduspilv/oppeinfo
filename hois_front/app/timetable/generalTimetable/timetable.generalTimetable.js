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
        };

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

      var directRoute = angular.isDefined($route.current.params.schoolId);
      var schoolAuthenticatedUser = false;
      if (directRoute) {
        $scope.schoolId = parseInt($route.current.params.schoolId, 10);
      }
      if (angular.isDefined($scope.auth) && angular.isDefined($scope.auth.school) && $scope.auth.school !== null) {
        if (angular.isUndefined($scope.schoolId)) {
          $scope.schoolId = $scope.auth.school.id;
          schoolAuthenticatedUser = true;
        } else {
          schoolAuthenticatedUser = $scope.auth.school.id === $scope.schoolId;
        }
      }

      if ($route.current.params.schoolId) {
        if (!schoolAuthenticatedUser) {
          QueryUtils.endpoint('/public/schoolSettings').get({schoolId: $scope.schoolId}).$promise.then(function (result) {
            $scope.isForbidden = result.isTimetableNotPublic;
          });
        } else {
          $scope.isForbidden = false;
        }
      } else {
        $location.url('/timetables');
      }

      if ($scope.schoolId) {
        QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
          $scope.studyYears = result;
          $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
          $scope.generalTimetableUtils.changeState({ studyYearId: $scope.criteria.studyYearId }, $scope.schoolId);
        });
      }

      $scope.$watch('criteria.studyYearId', function () {
        if (angular.isDefined($scope.criteria.studyYearId) && $scope.criteria.studyYearId !== null) {
          QueryUtils.endpoint('/timetableevents/timetableSearch/:schoolId/searchFormData/:studyYearId').search({
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

      var directRoute = angular.isDefined($route.current.params.schoolId);
      var schoolAuthenticatedUser = false;
      if (directRoute) {
        $scope.schoolId = parseInt($route.current.params.schoolId, 10);
      }
      if (angular.isDefined($scope.auth) && angular.isDefined($scope.auth.school) && $scope.auth.school !== null) {
        if (angular.isUndefined($scope.schoolId)) {
          $scope.schoolId = $scope.auth.school.id;
          schoolAuthenticatedUser = true;
        } else {
          schoolAuthenticatedUser = $scope.auth.school.id === $scope.schoolId;
        }
      }

      if (angular.isDefined($scope.schoolId)) {
        if (!schoolAuthenticatedUser) {
          QueryUtils.endpoint('/public/schoolSettings').get({schoolId: $scope.schoolId}).$promise.then(function (result) {
            $scope.isForbidden = result.isTimetableNotPublic;
          });
        } else {
          $scope.isForbidden = false;
        }
      } else {
        $location.url('/timetables');
      }

      if (angular.isDefined($scope.auth) && $scope.auth.isAdmin()) {
        QueryUtils.endpoint('/schoolBoard/:schoolId').get({schoolId: $scope.schoolId}).$promise.then(function (result) {
          $scope.schoolBoardUrl = result.url;
          console.log($scope.schoolBoardUrl);
        });
      }

      QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
        $scope.studyYears = result;
        $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
        $scope.generalTimetableUtils.changeState({ studyYearId: $scope.criteria.studyYearId }, $scope.schoolId);
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

        var encodedPerson = $route.current.params.encodedPerson;
        if (encodedPerson) {
          QueryUtils.endpoint('/timetables/timetableStudyYears/person').query({encodedPerson: encodedPerson}).$promise.then(function (result) {
            $scope.studyYears = result;
            $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
            $scope.generalTimetableUtils.changeState({ studyYearId: $scope.criteria.studyYearId }, $scope.schoolId);
          });
        } else {
          $scope.schoolId = $scope.auth.school.id;
          QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
            $scope.studyYears = result;
            $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
            $scope.generalTimetableUtils.changeState({ studyYearId: $scope.criteria.studyYearId }, $scope.schoolId);
          });
        }

        if (encodedPerson) {
          $scope.typeParam = 'personalId';
          $scope.criteria[$scope.typeParam] = encodedPerson;
          $scope.currentNavItem = "";
        } else if ($scope.auth.isStudent() || $scope.auth.isParent()) {
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
          if (!encodedPerson) {
            var newState = {};
            newState[$scope.typeParam] = $scope.criteria[$scope.typeParam];
            $scope.generalTimetableUtils.changeState(newState, $scope.schoolId);
          }
          $scope.$broadcast("updateWeekTimetable", { criteria: $scope.criteria });
        };

      }
    ]).controller('GeneralTimetableSchoolListController', ['$scope', 'School', '$location',
      function ($scope, School, $location) {
        $scope.schools = School.getSchoolsWithLogo();

        $scope.openSchoolGeneralTimetable = function (schoolId) {
          $location.path('timetable/' + schoolId + '/generalTimetable/group');
        }
      }
    ]);
}());
