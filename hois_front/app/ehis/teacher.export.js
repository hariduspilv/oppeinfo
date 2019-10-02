'use strict';

angular.module('hitsaOis').controller('TeacherExportController', ['$route', '$scope', 'message', 'QueryUtils', '$timeout', '$translate', 'busyHandler', 'dialogService', '$filter',
  function ($route, $scope, message, QueryUtils, $timeout, $translate, busyHandler, dialogService, $filter) {
    $scope.auth = $route.current.locals.auth;
    $scope.higher = $route.current.locals.higher;
    var exportUrl = $scope.higher ? '/teachers/exportToEhis/higher' : '/teachers/exportToEhis/vocational';

    $scope.teacher = {};
    $scope.teacher.allDates = false;

    function send() {
      QueryUtils.loadingWheel($scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
      QueryUtils.endpoint(exportUrl).save2($scope.teacher).$promise.then(function(result) {
        $timeout(function () {pollExportStatus(result.key);}, 1000); // give 1 second for initializing a thread
      }).catch(function () {
        QueryUtils.loadingWheel($scope, false);
      });
    }
  
    $scope.exportTeachers = function() {
      if($scope.teacherExportForm.$valid) {
        QueryUtils.endpoint('/teachers/ehisTeacherExportCheck').get($scope.criteria).$promise.then(function(checkResult) {
          if (checkResult && checkResult.user) {
            dialogService.confirmDialog({
              prompt: checkResult.from ? 'ehis.messages.overlappedRequestDateRangeTeacher' : 'ehis.messages.overlappedRequestTeacher',
              user: checkResult.user,
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
      QueryUtils.endpoint('/teachers/ehisTeacherExportStatus').get({key: key}).$promise.then(function (result) {
        if ($scope.cancelledBy) {
          $scope.cancelledBy = null;
        }
        if (result.status === 'IN_PROGRESS') {
          busyHandler.setProgress(Math.round(result.progress * 100));
          $timeout(function () {pollExportStatus(key);}, 5000);
        } else if (result.status === 'DONE') {
          message.info(result && result.result.length > 0 ? 'ehis.messages.exportFinished' : 
            ($scope.auth.higher ? 'ehis.messages.noTeachersFoundHigher' : 'ehis.messages.noTeachersFoundVocational'));
          $scope.result = result.result;
          busyHandler.setProgress(100);
          QueryUtils.loadingWheel($scope, false);
        } else {
          message.error('ehis.messages.taskStatus.' + result.status, {error: result.error});
          if ((result.status === 'CANCELLED' || result.status === 'INTERRUPTED') && result.result) {
            $scope.cancelledBy = result.cancelledBy;
            $scope.result = result.result;
          }
          QueryUtils.loadingWheel($scope, false);
        }
      });
    }
  }
]);
