'use strict';

angular.module('hitsaOis').controller('TeacherExportController', ['$route', '$scope', 'message', 'QueryUtils',
  '$translate', 'busyHandler', 'dialogService', '$filter', 'PollingService', 'POLLING_STATUS',
  function ($route, $scope, message, QueryUtils, $translate, busyHandler, dialogService, $filter, PollingService, POLLING_STATUS) {
    $scope.auth = $route.current.locals.auth;
    $scope.higher = $route.current.locals.higher;
    var exportUrl = $scope.higher ? '/teachers/exportToEhis/higher' : '/teachers/exportToEhis/vocational';

    $scope.teacher = {};
    $scope.teacher.allDates = false;

    function send() {
      if ($scope.cancelledBy) {
        $scope.cancelledBy = null;
      }
      QueryUtils.loadingWheel($scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
      PollingService.sendRequest({
        url: exportUrl,
        data: $scope.teacher,
        pollUrl: '/teachers/ehisTeacherExportStatus',
        successCallback: function (pollResult) {
          message.info(pollResult && pollResult.result.length > 0 ? 'ehis.messages.exportFinished' : 
            ($scope.auth.higher ? 'ehis.messages.noTeachersFoundHigher' : 'ehis.messages.noTeachersFoundVocational'));
          $scope.result = pollResult.result;
          busyHandler.setProgress(100);
          QueryUtils.loadingWheel($scope, false);
        },
        failCallback: function (pollResult) {
          if (pollResult) {
            message.error('ehis.messages.taskStatus.' + pollResult.status, {error: pollResult.error});
            if ((pollResult.status === POLLING_STATUS.CANCELLED || pollResult.status === POLLING_STATUS.INTERRUPTED) && pollResult.result) {
              $scope.cancelledBy = pollResult.cancelledBy;
              $scope.result = pollResult.result;
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
  }
]);
