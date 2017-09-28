
'use strict';

angular.module('hitsaOis')
  .factory('ekisService', function(config) {
    var DATE_TIME_FORMAT = "YYYYMMDDHHmmSS";
    var factory = {};

    factory.getContractUrl = function(wdId) {
      return config.ekisUrl + wdId + moment().format(DATE_TIME_FORMAT);
    };

    factory.getCertificateUrl = function(wdId) {
      return config.ekisUrl + wdId + moment().format(DATE_TIME_FORMAT);
    };
    return factory;
  });
