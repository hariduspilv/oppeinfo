'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.QueryUtils
 * @description
 * # QueryUtils
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis').factory('QueryUtils', ['config', '$resource', '$route', '$translate', 'message', 'resourceErrorHandler', '$sessionStorage',

  function (config, $resource, $route, $translate, message, resourceErrorHandler, $sessionStorage) {

    var defaultPagingParams = {size: 20, page: 1};

    var getQueryParams = function (params) {
      var queryParams = angular.copy(params, {});

      //md-data-table is 1 based, backend is 0 based
      if(angular.isNumber(queryParams.page)) {
        queryParams.page = queryParams.page - 1;
      }

      queryParams.lang = $translate.use().toUpperCase();

      //do not send empty strings as search params
      for (var key in queryParams) {
        if (queryParams.hasOwnProperty(key)) {
          if (queryParams[key] === '') {
            delete queryParams[key];
          }
        }
      }

      if(queryParams.order) {
        //md-data-table order is sign based, but backend wants asc or desc in the value of sort params eg sort=id,asc
        if(queryParams.order[0] === '-') {
          queryParams.sort = params.order.substring(1) + ',desc';
        } else {
          queryParams.sort = params.order + ',asc';
        }
        delete queryParams.order;
      }
      return queryParams;
    };

    var clearQueryParams = function (params) {
      for (var property in params) {
        if (params.hasOwnProperty(property) && property !== 'order' && property !== 'size' && property !== 'page') {
          delete params[property];
        }
      }
    };

    var createQueryForm = function(scope, url, defaultParams, postLoad) {
      scope.criteria = angular.extend({}, defaultPagingParams);

      scope.clearCriteria = function() {
        clearQueryParams(scope.criteria);
      };

      if(scope.fromStorage === undefined) {
        // if caller has not already provided it's own version, define one
        scope.fromStorage = function(key) {
          return JSON.parse($sessionStorage[key] || '{}');
        };
      }

      scope.toStorage = function(key, criteria) {
        $sessionStorage[key] = JSON.stringify(criteria);
      };

      if(!('_menu' in $route.current.params)) {
        var storedCriteria = scope.fromStorage(url);
        if(angular.isNumber(storedCriteria.page)) {
          storedCriteria.page = storedCriteria.page + 1;
        }

        angular.extend(scope.criteria, storedCriteria);
      } else {
        // apply default parameters only when we are not restoring previously used query parameters
        angular.extend(scope.criteria, defaultParams);
      }

      scope.getCriteria = function() {
        return getQueryParams(scope.criteria);
      };

      scope.tabledata = {};

      scope.afterLoadData = function(resultData) {
        scope.tabledata.content = resultData.content;
        scope.tabledata.totalElements = resultData.totalElements;
        if(resultData.content.length === 0) {
          message.info('main.messages.error.notFound');
        }
        if(angular.isFunction(postLoad)) {
          postLoad(resultData.content);
        }
      };

      scope.loadData = function() {
        var query = scope.getCriteria();
        scope.toStorage(url, query);
        scope.tabledata.$promise = endpoint(url).search(query, scope.afterLoadData);
      };
    };

    var endpoint = function(baseUrl) {
      var basePath = config.apiUrl + baseUrl;
      var idPath = basePath + '/:id';
      return $resource(basePath, {}, {
          // crud
          save:   {method: 'POST', interceptor: resourceErrorHandler},
          post:   {method: 'POST', isArray: true, interceptor: resourceErrorHandler},
          get:    {method: 'GET', url: idPath , interceptor: resourceErrorHandler},
          update: {method: 'PUT', url: idPath, params: {id: '@id'}, interceptor: resourceErrorHandler},
          delete: {method: 'DELETE', url: idPath, params: {id: '@id', version: '@version'}, interceptor: resourceErrorHandler},
          // update without id
          put:    {method: 'PUT', interceptor: resourceErrorHandler},
          // search functions
          search: {method: 'GET', interceptor: resourceErrorHandler},
          query:  {method: 'GET', isArray:true, interceptor: resourceErrorHandler},
      });
    };

    return {getQueryParams: getQueryParams, clearQueryParams: clearQueryParams, createQueryForm: createQueryForm, endpoint: endpoint};
}]);
