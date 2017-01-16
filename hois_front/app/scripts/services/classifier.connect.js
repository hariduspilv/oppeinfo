'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.ClassifierConnect
 * @description
 * # ClassifierConnect
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis')
  .factory('ClassifierConnect', function ($resource, config, QueryUtils) {

    function ClassifierConnect(args) {
      this.classifierCode = args.classifierCode;
      this.connectClassifierCode = args.connectClassifierCode;
    }

    ClassifierConnect.query = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/classifierConnect');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.get(queryParams, successCallback);
    };

    ClassifierConnect.queryAll = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/classifierConnect/all');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.query(queryParams, successCallback);
    };

    ClassifierConnect.sendListOfParents = function(classifier, parents) {
        return $resource(config.apiUrl+'/classifierConnect/changeParents/' + classifier.code).save(parents);
    };

    return ClassifierConnect;

  });
