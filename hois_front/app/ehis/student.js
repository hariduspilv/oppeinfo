'use strict';

angular.module('hitsaOis').controller('StudentEhisController', ['$scope', 'message', 'QueryUtils', '$timeout', 'dialogService', '$filter', '$translate', 'busyHandler',
  function ($scope, message, QueryUtils, $timeout, dialogService, $filter, $translate, busyHandler) {

  var COURSE_CHANGE = 'COURSE_CHANGE';
  var CURRICULA_FULFILMENT = 'CURRICULA_FULFILMENT';
  var DORMITORY = 'DORMITORY';
  var FOREIGN_STUDY = 'FOREIGN_STUDY';
  var GRADUATION = 'GRADUATION';
  var VOTA = 'VOTA';
  var SPECIAL_NEEDS = 'SPECIAL_NEEDS';

  $scope.dataTypes = [
    {type: COURSE_CHANGE, translate: $translate.instant('ehis.student.COURSE_CHANGE')},
    {type: CURRICULA_FULFILMENT, translate: $translate.instant('ehis.student.CURRICULA_FULFILMENT')},
    {type: DORMITORY, translate: $translate.instant('ehis.student.DORMITORY')},
    {type: FOREIGN_STUDY, translate: $translate.instant('ehis.student.FOREIGN_STUDY')},
    {type: GRADUATION, translate: $translate.instant('ehis.student.GRADUATION')},
    {type: VOTA, translate: $translate.instant('ehis.student.VOTA')},
    {type: SPECIAL_NEEDS, translate: $translate.instant('ehis.student.SPECIAL_NEEDS')}
  ];
  $scope.displayDates = [COURSE_CHANGE, DORMITORY, FOREIGN_STUDY, GRADUATION, VOTA, SPECIAL_NEEDS];
  $scope.criteria = {from: new Date(), thru: new Date()};

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
    QueryUtils.loadingWheel($scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
    QueryUtils.endpoint('/students/ehisStudentExport').save2($scope.criteria).$promise.then(function(result) {
      $timeout(function () {pollExportStatus(result.key);}, 1000); // give 1 second for initializing a thread
    }).catch(function () {
      QueryUtils.loadingWheel($scope, false);
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

  function pollExportStatus(key) {
    QueryUtils.endpoint('/students/ehisStudentExportStatus').get({key: key}).$promise.then(function (result) {
      if ($scope.cancelledBy) {
        $scope.cancelledBy = null;
      }
      if (result.status === 'IN_PROGRESS') {
        busyHandler.setProgress(Math.round(result.progress * 100));
        $timeout(function () {pollExportStatus(key);}, 5000);
      } else if (result.status === 'DONE') {
        message.info(result && result.result.length > 0 ? 'ehis.messages.exportFinished' : 'ehis.messages.nostudentsfound');
        $scope.result = processResult(result.result);
        busyHandler.setProgress(100);
        QueryUtils.loadingWheel($scope, false);
      } else {
        message.error('ehis.messages.taskStatus.' + result.status, {error: result.error});
        if ((result.status === 'CANCELLED' || result.status === 'INTERRUPTED') && result.result) {
          $scope.cancelledBy = result.cancelledBy;
          $scope.result = processResult(result.result);
        }
        QueryUtils.loadingWheel($scope, false);
      }
    });
  }
}
]);
