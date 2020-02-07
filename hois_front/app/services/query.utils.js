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

    var createQueryForm = function(scope, url, defaultParams, postLoad, useLoadingWheel, ignoreStorage, noEmptyMsg) {
      scope.criteria = angular.extend({}, defaultPagingParams);
      scope.hiddenCriteria = {};

      scope.directiveControllers = [];
      scope.clearCriteria = function() {
        clearQueryParams(scope.criteria);
        scope.directiveControllers.forEach(function (c) { 
          c.clear();
        });
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

      if(!('_menu' in $route.current.params) && !ignoreStorage) {
        var storedCriteria = scope.fromStorage(url);
        var storedHiddenCriteria = scope.fromStorage('hidden_' + url);
        if(angular.isNumber(storedCriteria.page)) {
          storedCriteria.page = storedCriteria.page + 1;
        }

        // In case if user has an url which lead to search form then there is no parameters.
        // It should place default parameters instead of empty storedCriteria
        if ($sessionStorage[url] === undefined) {
          angular.extend(scope.criteria, defaultParams);
        } else {
          angular.extend(scope.criteria, storedCriteria);
          angular.extend(scope.hiddenCriteria, storedHiddenCriteria);
        }
      } else {
        // apply default parameters only when we are not restoring previously used query parameters
        angular.extend(scope.criteria, defaultParams);
      }

      scope.getCriteria = function() {
        return getQueryParams(scope.criteria);
      };

      scope.tabledata = {};

      var _afterLoadData = function(resultData) {
        try {
          scope.afterLoadData(resultData);
        } finally {
          if (useLoadingWheel) {
            loadingWheel(scope, false);
          }
        }
      };

      scope.afterLoadData = function(resultData) {
        scope.tabledata.content = resultData.content;
        scope.tabledata.totalElements = resultData.totalElements;
        if(resultData.totalElements === 0 && !noEmptyMsg) {
          message.info('main.messages.error.notFound');
        }
        if(angular.isFunction(postLoad)) {
          postLoad(resultData.content);
        }
      };

      var _errorAfterLoadData = function() {
        try {
          scope.errorAfterLoadData();
        } finally {
          if (useLoadingWheel) {
            loadingWheel(scope, false);
          }
        }
      };

      scope.errorAfterLoadData = function() {
        if (useLoadingWheel) {
          loadingWheel(scope, false);
        }
      };

      scope.loadData = function() {
        if (useLoadingWheel) {
          loadingWheel(scope, true);
        }
        var query = scope.getCriteria();
        if (!ignoreStorage) {
          scope.toStorage(url, query);
          scope.toStorage('hidden_' + url, scope.hiddenCriteria);
        }
        scope.tabledata.$promise = endpoint(url).search(query, _afterLoadData, _errorAfterLoadData);
        return scope.tabledata.$promise;
      };
    };

    var endpoint = function(baseUrl, settings) {
      var basePath = config.apiUrl + baseUrl;
      var idPath = basePath + '/:id';
      return $resource(basePath, {}, settings || {
          // crud, methods ending with 2 are without interceptor
          save:   {method: 'POST', interceptor: resourceErrorHandler},
          save2:  {method: 'POST'},
          post:   {method: 'POST', isArray: true, interceptor: resourceErrorHandler},
          post2:  {method: 'POST', isArray: true},
          get:    {method: 'GET', url: idPath , interceptor: resourceErrorHandler},
          update: {method: 'PUT', url: idPath, params: {id: '@id'}, interceptor: resourceErrorHandler},
          update2:{method: 'PUT', url: idPath, params: {id: '@id'}},
          delete: {method: 'DELETE', url: idPath, params: {id: '@id', version: '@version'}, interceptor: resourceErrorHandler},
          // update without id
          put:    {method: 'PUT', interceptor: resourceErrorHandler},
          put2:   {method: 'PUT'},
          putWithoutLoad:   {method: 'PUT', loading: false, url: idPath, params: {id: '@id'}, interceptor: resourceErrorHandler},
          // search functions
          search: {method: 'GET', interceptor: resourceErrorHandler},
          query:  {method: 'GET', isArray:true, interceptor: resourceErrorHandler},
      });
    };

    /**
     * 
     * @param {Object} scope 
     * @param {Boolean} busy 
     * @param {Boolean} multiple 
     * @param {String} text 
     * @param {Boolean} value 
     * @param {Function} onComplete 
     */
    var loadingWheel = function(scope, busy, multiple, text, value, onComplete) {
      scope.$root.$emit('backendBusy', {busy: busy, isMultiple: multiple, text: text, value: value, onComplete: onComplete});
    };

    return {getQueryParams: getQueryParams, clearQueryParams: clearQueryParams, createQueryForm: createQueryForm,
      endpoint: endpoint, loadingWheel: loadingWheel};
}]).factory('busyHandler', ['$mdDialog',
  function ($mdDialog) {
    var requests = 0, busyShowing = false;
    // Progress should be an object to be able to pass it with 2-way-binding.
    var progress = {value: 0, text: ''};

    function _notBusy() {
      requests--;
      if(requests === 0 && busyShowing) {
        $mdDialog.hide();
      }
    }
    
    function setProgress(value) {
      progress.value = value;
    }

    function setText(text) {
      progress.text = text;
    }

    function _createDefaultDialogObject(params) {
      return {
        parent: angular.element(document.body),
        multiple: params.isMultiple ? true : false,
        onComplete: params.onComplete ? params.onComplete : undefined,
        onRemoving: function() {
          busyShowing = false;
        }
      };
    }

    function handle(params) {
      if(params.busy) {
        requests++;
        if(requests === 1 && !busyShowing) {
          var dialogObj = _createDefaultDialogObject(params);
          angular.extend(dialogObj, {
            template:
              '<md-dialog style="background-color:transparent;box-shadow:none">' +
              '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait" style="height:120px;">' +
              '<md-progress-circular class="md-hue-2 loader" md-diameter="80px"></md-progress-circular>' +
              '</div>' +
              (params.text ? '<h3 style="text-align:center; color: white; text-shadow: -1px -1px 0 #000,' +
              '1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;">' + params.text + '</h3>' : '') +
              '</md-dialog>',
          });
          $mdDialog.show(dialogObj);
          busyShowing = true;
        }
      } else {
        _notBusy();
      }
    }

    function handleWithProgress(params) {
      if(params.busy) {
        requests++;
        if(requests === 1 && !busyShowing) {
          progress.value = 0;
          progress.text = params.text;
          var dialogObj = _createDefaultDialogObject(params);
          // $scope is needed for minification as it will not find a provider.
          angular.extend(dialogObj, {
            controller: ["$scope", function (scope) {
              scope.progress = progress;
            }],
            templateUrl: 'components/progress.wheel.dialog.html'
          });
          $mdDialog.show(dialogObj);
          busyShowing = true;
        }
      } else {
        _notBusy();
      }
    }

    return {
      handle: handle,
      handleWithProgress: handleWithProgress,
      setProgress: setProgress,
      setText: setText
    };
  }
]);

(function() {
  function pad(number) {
    if (number < 10) {
      return '0' + number;
    }
    return number;
  }

  Date.prototype.toISOString = function() {
    return this.getFullYear() +
      '-' + pad(this.getMonth() + 1) + '-' + pad(this.getDate()) +
      'T' + pad(this.getHours()) + ':' + pad(this.getMinutes()) + ':' + pad(this.getSeconds()) +
      '.' + (this.getMilliseconds() / 1000).toFixed(3).slice(2, 5) + 'Z';
  };
}());
