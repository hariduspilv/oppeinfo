'use strict';

angular.module('hitsaOis').controller('StudentGroupAbsenceController',
  ['$route', '$scope', '$timeout', 'Classifier', 'DataUtils', 'QueryUtils', 'message', 'stateStorageService', function ($route, $scope, $timeout, Classifier, DataUtils, QueryUtils, message, stateStorageService) {
    $scope.auth = $route.current.locals.auth;
    var baseUrl = '/groupAbsences';
    var schoolId = $route.current.locals.auth.school.id;
    var stateKey = 'groupabsences';

    Classifier.queryForDropdown({ mainClassCode: 'PUUDUMINE' }, function (result) {
      $scope.formState.absenceOptions = result;
    });

    function setCurrentStudyYearAndWeek() {
      if (!$scope.criteria.studyYear) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if (sy) {
          $scope.criteria.studyYear = sy.id;
          $scope.studyYearChanged(true);
        }
      }
    }

    $scope.criteria = {};
    $scope.directiveControllers = [];
    $scope.formState = {
      studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(setCurrentStudyYearAndWeek),
      studyWeeks: []
    };

    $scope.studyYearChanged = function (setCurrentWeek) {
      var studyWeeks = [];
      if (setCurrentWeek) {
        studyWeeks = QueryUtils.endpoint(baseUrl + '/studyYearWeeks/' +  $scope.criteria.studyYear).query(setCurrentStudyWeek);
      } else {
        studyWeeks = QueryUtils.endpoint(baseUrl + '/studyYearWeeks/' +  $scope.criteria.studyYear).query();
      }
      $scope.formState.studyWeeks = studyWeeks;
    };

    $scope.search = function() {
      $scope.studentGroupAbsenceForm.$setSubmitted();
      if(!$scope.studentGroupAbsenceForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      $scope.criteria.studyWeekStart = $scope.criteria.studyWeek ? $scope.criteria.studyWeek.start : null;
      $scope.criteria.studyWeekEnd = $scope.criteria.studyWeek ? $scope.criteria.studyWeek.end : null;

      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl).get($scope.criteria).$promise.then(function (result) {
        QueryUtils.loadingWheel($scope, false);
        $scope.content = result;
        setJournals(result.journalsByDates);
        setStudentAbsences(result.studentAbsences);
        $scope.$broadcast('refreshFixedColumns');
      });
      stateStorageService.changeState(schoolId, stateKey, $scope.criteria);
    };

    $scope.clearCriteria = function() {
      $scope.criteria = {};
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };

    function searchTodaysAbsences() {
      if ($scope.studentGroupAbsenceForm && $scope.studentGroupAbsenceForm.$valid) {
        $scope.search();
      } else {
        $timeout(searchTodaysAbsences);
      }
    }

    if (('today' in $route.current.params)) {
      $scope.criteria.todaysAbsences = true;
      searchTodaysAbsences();
    } else if (!('_menu' in $route.current.params)) {
      $scope.storedCriteria = stateStorageService.loadState(schoolId, stateKey);
      angular.extend($scope.criteria, $scope.storedCriteria);
      $scope.studyYearChanged(false);
      $scope.formState.studyWeeks.$promise.then(function (weeks) {
        for (var week in weeks) {
          if (weeks[week].nr === $scope.criteria.studyWeek.nr) {
            $scope.criteria.studyWeek = weeks[week];
            $scope.studentGroupAbsenceForm.$valid = true;
            $scope.studentGroupAbsenceForm.$invalid = false;
            $scope.search();
            break;
          }
        }
      });
    }

    if ($scope.auth.isTeacher()) {
      QueryUtils.endpoint('/autocomplete/studentgroups').query({
        valid: true,
        higher: false,
        studentGroupTeacherId: $scope.auth.teacher
      }).$promise.then(function (studentGroups) {
        $scope.formState.studentGroups = studentGroups;
        if (!$scope.criteria.studentGroup) {
          $scope.criteria.studentGroup = studentGroups.length === 1 ? studentGroups[0].id : null;
        }
      });
    }

    function setCurrentStudyWeek() {
      var currentDate = new Date();
      var currentTime = currentDate.getTime();

      $scope.criteria.studyWeek = null;
      for (var weekId in $scope.formState.studyWeeks) {
        var week = $scope.formState.studyWeeks[weekId];
        if (currentTime >= new Date(week.start) && currentTime <= new Date(week.end)) {
          $scope.criteria.studyWeek = week;
          break;
        }
      }
    }

    function getDateStringWithoutTime(dateString) {
      return dateString.slice(0, 10);
    }

    $scope.journalsByDateCount = function (date) {
      var dateString = getDateStringWithoutTime(date);
      return $scope.content.journalsByDates[dateString].length;
    };

    function setJournals(journalsByDates) {
      $scope.journals = [];
      for (var date in journalsByDates) {
        var journals = journalsByDates[date];
        for (var journalId in journals) {
          journals[journalId].date = date;
        }
        $scope.journals = $scope.journals.concat(journals);
      }
    }

    function setStudentAbsences(absences) {
      $scope.studentAbsences = {};
      for (var i = 0; i < absences.length; i++) {
        var absence = absences[i];

        var studentId = absence.student;
        if (!angular.isDefined($scope.studentAbsences[studentId])) {
          $scope.studentAbsences[studentId] = {};
        }

        var journalId = absence.journal;
        if (!angular.isDefined($scope.studentAbsences[studentId][journalId])) {
          $scope.studentAbsences[studentId][journalId] = {};
        }

        var date = getDateStringWithoutTime(absence.entryDate);
        if (!angular.isDefined($scope.studentAbsences[studentId][journalId][date])) {
          $scope.studentAbsences[studentId][journalId][date] = [];
        }

        var entry = $scope.studentAbsences[studentId][journalId][date].find(function (entry) {
          return entry.id === absence.journalStudentEntry;
        });

        if (angular.isUndefined(entry)) {
          entry = {
            id: absence.journalStudentEntry,
            startLessonNr: absence.startLessonNr,
            lessons: absence.lessons,
            absences: []
          };
          $scope.studentAbsences[studentId][journalId][date].push(entry);
        }
        entry.absences.push(absence);
      }
    }

    $scope.absenceChanged = function (absence) {
      QueryUtils.endpoint(baseUrl + '/entry/' + absence.journalStudentEntry).put({
        absence: absence.absence
      });
    };

    $scope.lessonAbsenceChanged = function (absence) {
      QueryUtils.endpoint(baseUrl + '/lesson/' + absence.journalEntryStudentLessonAbsence).put({
        absence: absence.absence
      });
    };

}]);
