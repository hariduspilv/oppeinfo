'use strict';

angular.module('hitsaOis')
  .factory('stateStorageService', function ($sessionStorage) {
    var stateStorageService = {};

    stateStorageService.loadState = function (schoolId, stateKey) {
      var storage = $sessionStorage[stateKey] || '{}';
      var schoolStates = JSON.parse(storage);
      return schoolStates[schoolId] || {};
    };

    stateStorageService.changeState = function (schoolId, stateKey, newState) {
      var schoolState = {};
      schoolState[schoolId] = angular.extend(stateStorageService.loadState(schoolId, stateKey), newState);
      $sessionStorage[stateKey] = JSON.stringify(angular.extend(JSON.parse($sessionStorage[stateKey] || '{}'), schoolState));
    };

    return stateStorageService;
  });
