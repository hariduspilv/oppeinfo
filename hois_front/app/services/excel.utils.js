'use strict';

angular.module('hitsaOis').factory('ExcelUtils', function (QueryUtils, $http) {
    var factory = {};

    /**
     * Max timeout set by browser is 2 min
     * Polling to be added
     */
    factory.get = function(url, scope, name) {
        QueryUtils.loadingWheel(scope, true);
        $http.get(url, {
            responseType: 'arraybuffer'
        }).then(function(res) {
            QueryUtils.loadingWheel(scope, false);
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
        });
    };
    return factory;
  });