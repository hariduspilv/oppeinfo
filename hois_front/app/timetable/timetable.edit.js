'use strict';

angular.module('hitsaOis').controller('TimetableEditController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location',
  function ($scope, message, QueryUtils, DataUtils, $route, $location) {
    var id = $route.current.params.id;
    $scope.formState = {};
    var baseUrl = '/timetables';
    var blockedDates = [];
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.lastDateForCurrentRange = Date.now();
    $scope.firstDateForCurrentRange = Date.now();
    if (id) {
      $scope.timetable = Endpoint.get({id: id});
      $scope.timetable.$promise.then(function () {
        $scope.formState.studyYears = $scope.timetable.studyYears;
        $scope.allStudyPeriods = $scope.timetable.studyPeriods;
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.timetable.studyPeriods);
        $scope.timetable.studyYear = sy.id;
        $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
          return t.studyYear === $scope.timetable.studyYear;
        });
        $scope.timetable.studyPeriod = currentStudyPeriod.id;
        $scope.timetable.studyYears = null;
        $scope.timetable.currentStudyPeriod = null;
        $scope.timetable.studyPeriods = null;
        
      });
    } else {
      $scope.timetable = new Endpoint();
      $scope.timetable.code = $route.current.params.type;
      $scope.timetable.startDate = $route.current.params.from ? new Date($route.current.params.from) : null;
      $scope.timetable.endDate = $route.current.params.thru ? new Date($route.current.params.thru) : null;
      QueryUtils.endpoint(baseUrl + '/managementSearchFormData').search().$promise.then(function (result) {
        $scope.formState.studyYears = result.studyYears;
        $scope.allStudyPeriods = result.studyPeriods;
        if (!$scope.timetable.studyYear) {
          var sy, currentStudyPeriod;
          if ($scope.timetable.startDate) {
            DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
            currentStudyPeriod = DataUtils.getStudyYearOrPeriodAt($scope.timetable.startDate, result.studyPeriods);
          } else {
            sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
            currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod(result.studyPeriods);
          }
          if (sy && $scope.allStudyPeriods) {
            $scope.timetable.studyYear = sy.id;
            $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
              return t.studyYear === $scope.timetable.studyYear;
            });
            $scope.timetable.studyPeriod = currentStudyPeriod.id;
          }
        }
      });
    }
    $scope.studyYearChanged = function () {
      if (!studyPeriodBelongsToStudyYear($scope.timetable.studyYear, $scope.timetable.studyPeriod)) {
        $scope.timetable.studyPeriod = null; 
      }
    };

    function studyPeriodBelongsToStudyYear(studyYearId, studyPeriodId) {
      for (var i = 0; i < $scope.formState.studyPeriods.length; i++) {
        var studyPeriod = $scope.formState.studyPeriods[i];
        if (studyPeriod.id === studyPeriodId && studyPeriod.studyYear === studyPeriodId) {
          return true;
        }
      }
      return false;
    } 

    $scope.$watch('timetable.startDate', function () {
      var startDate = $scope.timetable.startDate;
      if (startDate === undefined) {
        $scope.lastDateForCurrentRange = Date.now();
      } else {
        blockedDates.sort(function(a, b) {
          return a-b;
        });
        for (var i = 0; i < blockedDates.length; i++) {
          if (blockedDates[i] >= startDate.getTime()) {
            $scope.lastDateForCurrentRange = blockedDates[i];
            break;
          }
        }
        if(angular.isObject(startDate) && $scope.lastDateForCurrentRange < startDate.getTime()) {
          var spEndDate = new Date( getCurrentStudyPeriod().endDate);
          $scope.lastDateForCurrentRange = spEndDate.setDate(spEndDate.getDate() + 1);
        }
      }
      $scope.setEndDateMinDate();
    });

    $scope.$watch('timetable.endDate', function () {
      var endDate = $scope.timetable.endDate;
      blockedDates = blockedDates.sort().reverse();
      if (endDate === undefined) {
        var currentStudyPeriod = getCurrentStudyPeriod();
        if (currentStudyPeriod !== undefined) {
          $scope.firstDateForCurrentRange = currentStudyPeriod.startDate;
        }
      } else {
        for (var i = 0; i < blockedDates.length; i++) {
          if (blockedDates[i] <= endDate.getTime()) {
            $scope.firstDateForCurrentRange = blockedDates[i];
            break;
          }
        }
      }
      blockedDates = blockedDates.sort();
    });

    function setBlockedDates(result) {
      blockedDates = [];
      for (var i = 0; i < result.length; i++) {
        for (var j = 0; j < result[i].dates.length; j++) {
          var currentDate = new Date(result[i].dates[j]);
          currentDate = currentDate.setHours(0, 0, 0, 0);
          blockedDates.push(currentDate);
        }
      }
      blockedDates = blockedDates.sort();
    }

    function getCurrentStudyPeriod() {
      if ($scope.allStudyPeriods !== undefined) {
        return $scope.allStudyPeriods.filter(function (obj) {
          return obj.id === $scope.timetable.studyPeriod;
        })[0];
      }
    }

    function setBlockedDateRange() {
      if ($scope.timetable.studyPeriod !== undefined) {
        var params = 'studyPeriod=' + $scope.timetable.studyPeriod + '&code=' + $scope.timetable.code;
        if (id !== undefined) {
          params += '&currentTimetable=' + id;
        }
        QueryUtils.endpoint(baseUrl + '/blockedDatesForPeriod?' + params).query(setBlockedDates);
        var currentStudyPeriod = getCurrentStudyPeriod();
        var spStartDate = new Date(currentStudyPeriod.startDate);
        var spEndDate = new Date(currentStudyPeriod.endDate);
        $scope.firstDateForCurrentRange = spStartDate.setDate(currentStudyPeriod.startDate.getDate() - 1);
        $scope.lastDateForCurrentRange = spEndDate.setDate(currentStudyPeriod.endDate.getDate() + 1);
        $scope.studyPeriodStartDate = new Date(currentStudyPeriod.startDate.getTime());
        $scope.studyPeriodEndDate = new Date(currentStudyPeriod.endDate.getTime());
      }
      $scope.setEndDateMinDate();
    }

    $scope.$watch('timetable.studyPeriod', function () {
      if ($scope.timetable.studyPeriod) {
        setBlockedDateRange();
      }
    });

    $scope.blockedFromDatesPredicate = function (date) {
      if (blockedDates.length !== 0 && blockedDates.indexOf(date.getTime()) !== -1 || date.getTime() < $scope.firstDateForCurrentRange) {
        return false;
      }
      return true;
    };

    $scope.setEndDateMinDate = function() {
      $scope.endDateMinDate = $scope.timetable.startDate ? $scope.timetable.startDate : $scope.studyPeriodStartDate;
    };

    $scope.blockedThruDatesPredicate = function (date) {
      if (blockedDates.length !== 0 && blockedDates.indexOf(date.getTime()) !== -1 || date.getTime() > $scope.lastDateForCurrentRange) {
        return false;
      }
      return true;
    };

    $scope.$watch('timetable.studyYear', function () {
      if ($scope.timetable.studyYear && $scope.allStudyPeriods) {
        $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
          return t.studyYear === $scope.timetable.studyYear;
        });
      }
    });

    $scope.save = function () {
      $scope.timetableForm.$setSubmitted();
      /*if (!$scope.timetableForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }*/
      if (angular.isDefined(id)) {
        $scope.timetable.$update({id: id}).then(function () {
          message.info('main.messages.update.success');
        });
        $location.url('/timetable/' + id + '/view');
      } else {
        $scope.timetable.$save().then(function (result) {
          message.info('timetable.timetableManagement.created');
          $location.url('/timetable/' + result.id + '/view');
        });
      }
    };
  }
]).controller('TimetableViewController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier) {
    $scope.timetableId = $route.current.params.id;
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;
    var clMapper = Classifier.valuemapper({curriculumStudyLevelCode: 'OPPEASTE'});
    QueryUtils.endpoint('/timetables/:id/view').search({id: $scope.timetableId}, function (result) {
      clMapper.objectmapper(result.curriculums);
      $scope.timetable = result;
      $scope.timetable.endDate = new Date($scope.timetable.endDate);
      $scope.timetable.startDate = new Date($scope.timetable.startDate);
    });

    $scope.confirm = function() {
      QueryUtils.endpoint('/timetables/' + $scope.timetableId + '/confirm').put({}, function() {
        $route.reload();    
      });
    };

    $scope.publicize = function() {
      QueryUtils.endpoint('/timetables/' + $scope.timetableId + '/publicize').put({}, function() {
        $route.reload();    
      });
    };
  }
]);
