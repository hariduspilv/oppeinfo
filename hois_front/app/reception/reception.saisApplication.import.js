'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationImportController',
  function ($scope, Classifier, QueryUtils, message, PollingService, POLLING_STATUS, $translate, busyHandler) {
  $scope.statusList = Classifier.queryForDropdown({mainClassCode: 'SAIS_AVALDUSESTAATUS', order: 'code'});
  $scope.statusList.$promise.then(function() {
    Classifier.setSelectedCodes($scope.statusList, ['SAIS_AVALDUSESTAATUS_T']);
  });

  var clMapper = Classifier.valuemapper({applicationStatus: 'SAIS_AVALDUSESTAATUS'});
  $scope.importApplications = function() {
    if($scope.applicationImportForm.$valid) {
      var selectedCodes = Classifier.getSelectedCodes($scope.statusList);
      $scope.criteria.status = selectedCodes.length > 0 ? selectedCodes : undefined;

      QueryUtils.loadingWheel($scope, true, false,
        $translate.instant('reception.application.requestInProgress'), true);
      PollingService.sendRequest({
        url: '/saisApplications/importSais',
        data: $scope.criteria,
        pollUrl: '/saisApplications/importSaisStatus',
        successCallback: function (pollResult) {
          clMapper.objectmapper(pollResult.result.successful);
          $scope.failed = pollResult.result.failed;
          $scope.successful = pollResult.result.successful;
          $scope.result = true;
          message.info('reception.application.importFinished');
          busyHandler.setProgress(100);
          QueryUtils.loadingWheel($scope, false);
        },
        failCallback: function (pollResult) {
          if (pollResult) {
            message.error('main.async.taskStatus.' + pollResult.status, {error: pollResult.error});
            if ((pollResult.status === POLLING_STATUS.CANCELLED || pollResult.status === POLLING_STATUS.INTERRUPTED) && pollResult.result) {
              $scope.failed = pollResult.result.failed;
              $scope.successful = pollResult.result.successful;
              $scope.result = true;
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
    } else {
      message.error('reception.application.form-has-errors');
    }
  };

});
