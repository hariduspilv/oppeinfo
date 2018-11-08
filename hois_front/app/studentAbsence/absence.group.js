'use strict';

angular.module('hitsaOis').controller('StudentGroupAbsenceController',
  ['$scope', '$route', 'Classifier', 'DataUtils', 'QueryUtils', 'message', 'stateStorageService', function ($scope, $route, Classifier, DataUtils, QueryUtils, message, stateStorageService) {
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

    if (!('_menu' in $route.current.params)) {
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

    $scope.$watch('criteria.studentGroupObject', function () {
      $scope.criteria.studentGroup = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
    });

    function setCurrentStudyWeek() {
      var currentDate = new Date();
      var currentTime = currentDate.getTime();

      for (var weekId in $scope.formState.studyWeeks) {
        var week = $scope.formState.studyWeeks[weekId];
        if (currentTime >= new Date(week.start) && currentTime <= new Date(week.end)) {
          $scope.criteria.studyWeek = week;
          break;
        }
        $scope.criteria.studyWeek = null;
      }
    }

    function getDateStringWithoutTime(dateString) {
      return dateString.slice(0, 10);
    }

    $scope.journalsByDateCount = function (date) {
      var dateString = getDateStringWithoutTime(date);
      return $scope.content.journalsByDates[dateString].length;
    };

    $scope.search = function() {
      $scope.studentGroupAbsenceForm.$setSubmitted();
      if(!$scope.studentGroupAbsenceForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      $scope.criteria.studyWeekStart = $scope.criteria.studyWeek.start;
      $scope.criteria.studyWeekEnd = $scope.criteria.studyWeek.end;
      
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl).get($scope.criteria).$promise.then(function (result) {
        QueryUtils.loadingWheel($scope, false);
        $scope.content = result;
        setJournals(result.journalsByDates);
        setStudentAbsences(result.studentAbsences);
      });
      stateStorageService.changeState(schoolId, stateKey, $scope.criteria);
    };
  
    $scope.clearCriteria = function() {
      $scope.criteria = {};
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
          $scope.studentAbsences[studentId][journalId][date] = {};
        }

        var entryId = absence.journalStudentEntry;
        if (!angular.isDefined($scope.studentAbsences[studentId][journalId][date][entryId])) {
          $scope.studentAbsences[studentId][journalId][date][entryId] = [];
        }
        $scope.studentAbsences[studentId][journalId][date][entryId].push(absence);
      }
    }

    $scope.absenceChanged = function (absence) {
      QueryUtils.endpoint(baseUrl + '/entry/' + absence.journalStudentEntry).put({
        studentGroup: $scope.criteria.studentGroup,
        absence: absence.absence
      });
    };

    $scope.lessonAbsenceChanged = function (absence) {
      QueryUtils.endpoint(baseUrl + '/lesson/' + absence.journalEntryStudentLessonAbsence).put({
        studentGroup: $scope.criteria.studentGroup,
        absence: absence.absence
      });
    };

}]);