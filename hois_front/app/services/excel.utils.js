'use strict';
/**
 * TODO: Make async requests globally usable
 */
angular.module('hitsaOis').factory('ExcelUtils', function (QueryUtils, $http, busyHandler, $translate, $timeout, message, $rootScope, PollingService, POLLING_STATUS) {
    var factory = {};

    /**
     * Max timeout set by browser is 2 min
     * Polling to be added
    */
    factory.get = function(url, name, scope) {
        if (scope) {
            QueryUtils.loadingWheel(scope, true);
        }
        $http.get(url, {
            responseType: 'arraybuffer'
        }).then(function(res) {
            factory.downloadResponse(res, name);
            if (scope) {
                QueryUtils.loadingWheel(scope, false);
            }
        });
    };
    
    factory.send = function(scope, data, name) {
        QueryUtils.loadingWheel(scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
        busyHandler.setProgress(undefined);
        PollingService.sendRequest({
            url: '/poll/statistics/excelExport',
            data: data,
            pollUrl: '/poll/statistics/excelExportStatus',
            successCallback: function (_, key) {
                factory.get($rootScope.excel('poll/statistics/pollStatistics', {key: key}), name);
                QueryUtils.loadingWheel(scope, false);
            },
            failCallback: function (pollResult, key) {
                if (pollResult) {
                    message.error('ehis.messages.taskStatus.' + pollResult.status, {error: pollResult.error});
                    if ((pollResult.status === POLLING_STATUS.CANCELLED || pollResult.status === POLLING_STATUS.INTERRUPTED) && pollResult.result) {
                        factory.get($rootScope.excel('poll/statistics/pollStatistics', {key: key}), name);
                    }
                }
                QueryUtils.loadingWheel(scope, false);
            },
            updateProgress: function (pollResult) {
                // Translate informational message
                if (pollResult && pollResult.message) {
                    busyHandler.setText($translate.instant(pollResult.message));
                }
            }
        });
    };
    
    factory.downloadResponse = function(res, name) {
        var blob = new Blob(
            [res.data], { type: res.headers('Content-Type') }
        );
        if (window.navigator.msSaveOrOpenBlob) { // for IE
            //msSaveBlob only available for IE & Edge
            window.navigator.msSaveBlob(blob, name);
        } else {
            var URL = window.URL || window.MozURL || window.webkitURL || window.MSURL || window.OURL;
            var anchor = document.createElement('a');
            anchor.href = URL.createObjectURL(blob);
            anchor.download = name;
            document.body.appendChild(anchor); //For FF
            anchor.target = '_blank';
            anchor.click();
            //remove the elem
            document.body.removeChild(anchor);
        }
    };

    return factory;
  });