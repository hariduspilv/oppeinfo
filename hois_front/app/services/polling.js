'use strict';

angular
  .module('hitsaOis')
  .constant('POLLING_STATUS', {
    DONE: "DONE",
    IN_QUEUE: "IN_QUEUE",
    IN_PROGRESS: "IN_PROGRESS",
    CANCELLED: "CANCELLED",
    INTERRUPTED: "INTERRUPTED",
    EXCEPTION: "EXCEPTION",
    NOT_FOUND: "NOT_FOUND"
  })
  .service('PollingService', PollingService);

PollingService.$inject = ['POLLING_STATUS', 'QueryUtils', '$timeout', 'resourceErrorHandler'];
function PollingService(POLLING_STATUS, QueryUtils, $timeout, resourceErrorHandler) {

  /**
   * 
   * @param {string} config.url
   * @param {Object} config.data
   * @param {string} config.pollUrl
   * @param {Function} config.successCallback
   * @param {Function} config.failCallback
   * @param {Function} config.updateCallback
   */
  this.sendRequest = function (config) {
    QueryUtils.endpoint(config.url).save2(config.data).$promise.then(function(result) {
      $timeout(function () {
        _pollStatus(config, result.key);
      }, 1000); // give 1 second for initializing data
    }).catch(function (error) {
      resourceErrorHandler.responseError(error);
      if (config.failCallback) {
        config.failCallback();
      }
    });
  };

  function _pollStatus(config, key) {
    QueryUtils.endpoint(config.pollUrl).get({key: key}).$promise.then(function (pollResult) {
      if (config.updateProgress) {
        config.updateProgress(pollResult, key);
      }
      if (pollResult.status === POLLING_STATUS.IN_PROGRESS || pollResult.status === POLLING_STATUS.IN_QUEUE) {
        $timeout(function () {
          _pollStatus(config, key);
        }, 5000);
      } else if (pollResult.status === POLLING_STATUS.DONE) {
        if (config.successCallback) {
          config.successCallback(pollResult, key);
        }
      } else {
        if (config.failCallback) {
          config.failCallback(pollResult, key);
        }
      }
    });
  }
}