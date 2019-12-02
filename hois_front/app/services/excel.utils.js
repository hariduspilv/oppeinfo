'use strict';
/**
 * TODO: Make async requests globally usable
 */
angular.module('hitsaOis').factory('ExcelUtils', function (QueryUtils, $http, busyHandler, $translate, $timeout, message, $rootScope) {
    var factory = {};

    /**
     * Max timeout set by browser is 2 min
     * Polling to be added
    */
    factory.get = function(url, name) {
        $http.get(url, {
            responseType: 'arraybuffer'
        }).then(function(res) {
            factory.downloadResponse(res, name);
        });
    };
    
    factory.send = function(scope, data, name) {
        QueryUtils.loadingWheel(scope, true, false, $translate.instant('ehis.messages.requestInProgress'), true);
        busyHandler.setProgress(undefined);
        QueryUtils.endpoint('/poll/statistics/excelExport').save2(data).$promise.then(function(result) {
            $timeout(function () {excelExportStatus(scope, result.key, data, name);}, 1000); // give 1 second for initializing a thread
        }).catch(function () {
            QueryUtils.loadingWheel(scope, false);
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
    
    function excelExportStatus(scope, key, data, name) {
        data.key = key;
        QueryUtils.endpoint('/poll/statistics/excelExportStatus').get({key: key}).$promise.then(function (result) {
            // Translate informational message
           busyHandler.setText($translate.instant(result.message));
            if (result.status === 'IN_PROGRESS') {
                $timeout(function () {excelExportStatus(scope, key, data, name);}, 5000);
                } else if (result.status === 'DONE') {
                //message.info(result && result.result.length > 0 ? 'ehis.messages.exportFinished' : 'ehis.messages.nostudentsfound');
                factory.get($rootScope.excel('poll/statistics/pollStatistics', data), name);
                QueryUtils.loadingWheel(scope, false);
            } else {
                message.error('ehis.messages.taskStatus.' + result.status, {error: result.error});
                if ((result.status === 'CANCELLED' || result.status === 'INTERRUPTED') && result.result) {
                    factory.get($rootScope.excel('poll/statistics/pollStatistics', data), name);
                }
                QueryUtils.loadingWheel(scope, false);
            }
        });
    }

    return factory;
  });