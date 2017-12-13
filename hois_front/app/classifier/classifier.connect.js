'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.ClassifierConnect
 * @description
 * # ClassifierConnect
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis').factory('ClassifierConnect', ['QueryUtils',
  function (QueryUtils) {

    function ClassifierConnect(args) {
      this.classifierCode = args.classifierCode;
      this.connectClassifierCode = args.connectClassifierCode;
    }

    ClassifierConnect.query = function(params, successCallback) {
      var queryParams = QueryUtils.getQueryParams(params);
      return QueryUtils.endpoint('/classifierConnect').search(queryParams, successCallback);
    };

    ClassifierConnect.queryAll = function(params, successCallback) {
      var queryParams = QueryUtils.getQueryParams(params);
      return QueryUtils.endpoint('/classifierConnect/all').query(queryParams, successCallback);
    };

    ClassifierConnect.sendListOfParents = function(classifier, parents) {
        return QueryUtils.endpoint('/classifierConnect/changeParents/' + classifier.code).save(parents);
    };

    return ClassifierConnect;
  }
]);
