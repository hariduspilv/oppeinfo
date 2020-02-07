'use strict';

angular.module('hitsaOis').controller('StudentEhisController', ['$scope', '$route', 'message', 'QueryUtils',
  'dialogService', '$filter', '$translate', 'busyHandler', 'PollingService', 'POLLING_STATUS',
  function ($scope, $route, message, QueryUtils, dialogService, $filter, $translate, busyHandler, PollingService, POLLING_STATUS) {

  $scope.auth = $route.current.locals.auth;

  var COURSE_CHANGE = 'COURSE_CHANGE';
  var CURRICULA_FULFILMENT = 'CURRICULA_FULFILMENT';
  var DORMITORY = 'DORMITORY';
  var FOREIGN_STUDY = 'FOREIGN_STUDY';
  var GRADUATION = 'GRADUATION';
  var VOTA = 'VOTA';
  var SPECIAL_NEEDS = 'SPECIAL_NEEDS';
  var GUEST_STUDENTS = 'GUEST_STUDENTS';

  $scope.dataTypes = [
    {type: COURSE_CHANGE, translate: $translate.instant('ehis.student.COURSE_CHANGE')},
    {type: CURRICULA_FULFILMENT, translate: $translate.instant('ehis.student.CURRICULA_FULFILMENT')},
    {type: DORMITORY, translate: $translate.instant('ehis.student.DORMITORY')},
    {type: GRADUATION, translate: $translate.instant('ehis.student.GRADUATION')},
    {type: VOTA, translate: $translate.instant('ehis.student.VOTA')},
    {type: SPECIAL_NEEDS, translate: $translate.instant('ehis.student.SPECIAL_NEEDS')},
  ];

  if ($scope.auth.higher) {
    $scope.dataTypes.push({type: GUEST_STUDENTS, translate: $translate.instant('ehis.student.GUEST_STUDENTS')});
    $scope.dataTypes.push({type: FOREIGN_STUDY, translate: $translate.instant('ehis.student.FOREIGN_STUDY')});
  }

  $scope.displayDates = [COURSE_CHANGE, DORMITORY, FOREIGN_STUDY, GRADUATION, VOTA, SPECIAL_NEEDS, GUEST_STUDENTS];
  $scope.criteria = {from: new Date(), thru: new Date()};

  $scope.dataTypeChanged = function() {
    $scope.result = [];
    setMinDate();
  };
  
  function setMinDate() {
    switch ($scope.criteria.dataType) {
      case GUEST_STUDENTS:
        // 01.09.2013 min date
        $scope.minDate = new Date(2013, 8, 1);
        break;
      case FOREIGN_STUDY:
        // 01.04.2014 min date
        $scope.minDate = new Date(2014, 3, 1);
        break;
      default:
        $scope.minDate = undefined;
    }
  }

  function processResult(result) {
    if ($scope.criteria.dataType === 'VOTA') {
      var flatResult = [];
      result.forEach(function(row) {
        if (angular.isArray(row.records) && row.records.length > 0) {
          flatResult.push(angular.extend({}, row, row.records[0]));
          row.records.forEach(function(record, idx) {
            if (idx > 0) {
              flatResult.push(record);
            }
          });
        } else {
          flatResult.push(row);
        }
      });
      return flatResult;
    }
    return result;
  }

  function send() {
    if ($scope.cancelledBy) {
      $scope.cancelledBy = null;
    }
    QueryUtils.loadingWheel($scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
    PollingService.sendRequest({
      url: '/students/ehisStudentExport',
      data: $scope.criteria,
      pollUrl: '/students/ehisStudentExportStatus',
      successCallback: function (pollResult) {
        message.info(pollResult && pollResult.result.length > 0 ? 'ehis.messages.exportFinished' : 'ehis.messages.nostudentsfound');
        $scope.result = processResult(pollResult.result);
        busyHandler.setProgress(100);
        QueryUtils.loadingWheel($scope, false);
      },
      failCallback: function (pollResult) {
        if (pollResult) {
          message.error('ehis.messages.taskStatus.' + pollResult.status, {error: pollResult.error});
          if ((pollResult.status === POLLING_STATUS.CANCELLED || pollResult.status === POLLING_STATUS.INTERRUPTED) && pollResult.result) {
            $scope.cancelledBy = pollResult.cancelledBy;
            $scope.result = processResult(pollResult.result);
          }
        }
        QueryUtils.loadingWheel($scope, false);
      },
      updateProgress: function (pollResult) {
        if (pollResult) {
          busyHandler.setProgress(Math.round(pollResult.progress * 100));
          if (pollResult.message) {
            busyHandler.setText($translate.instant(pollResult.message));
          }
        }
      }
    });
  }

  $scope.exportStudents = function() {
    if($scope.studentExportForm.$valid) {
      QueryUtils.endpoint('/students/ehisStudentExportCheck').get($scope.criteria).$promise.then(function(checkResult) {
        if (checkResult && checkResult.user) { // if has any of these parameters (user or type) then exists
          dialogService.confirmDialog({
            prompt: checkResult.from ? 'ehis.messages.overlappedRequestDateRange' : 'ehis.messages.overlappedRequest',
            user: checkResult.user,
            type: $translate.instant('ehis.student.' + checkResult.type),
            from: $filter('hoisDate')(checkResult.from),
            thru: $filter('hoisDate')(checkResult.thru)
          }, function() {
            send();
          });
        } else {
          send();
        }
      }).catch(angular.noop);
    } else {
      message.error('main.messages.form-has-errors');
    }
  };
}]);
