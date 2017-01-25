'use strict';


angular.module('hitsaOis')
  .factory('Classifier', function ($resource, config, QueryUtils) {

    function Classifier(args) {

      this.code = args.code || null;
      this.value = args.value || null;
      this.value2 = args.value2 || null;
      this.nameEt = args.nameEt || null;
      this.nameEn = args.nameEn || null;
      this.nameRu = args.nameRu || null;
      this.mainClassCode = args.mainClassCode || null;
      this.description = args.description || null;
      this.changedDate = args.changedDate || null;
      this.validFrom = args.validFrom || null;
      this.validThru = args.validThru || null;
      this.extraval1 = args.extraval1 || null;
      this.extraval2 = args.extraval2 || null;
      this.ehisValue = args.ehisValue || null;
      this.vocational = args.vocational || false;
      this.higher = args.higher || false;


      this.save = function () {
          return $resource(config.apiUrl+'/classifier/:code', {code: '@code'}).update(this);
      };

      this.create = function() {
          return $resource(config.apiUrl+'/classifier').save(this);
      };

      this.delete = function () {
        return $resource(config.apiUrl+'/classifier/:code', {code: '@code'}).delete(this);
      };
    }

    Classifier.get = function(code) {
      var resource = $resource(config.apiUrl+'/classifier/:code');
      return resource.get({code: code});
    };

    /*
     For sorting add order element in params ie to order by name desc {order: '-name'} and for asc {order: 'name'}
     */
    Classifier.query = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/classifier');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.get(queryParams, successCallback);
    };

    Classifier.queryAll = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/classifier/all');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.query(queryParams, successCallback);
    };

    Classifier.queryForAutocomplete = function(params, successCallback) {
      var resource = $resource(config.apiUrl + '/classifier/getPossibleParentClassifiers');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.query(queryParams, successCallback);
    };

    Classifier.queryForDropdown = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/classifier/dropdown');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.query(queryParams, successCallback);
    };

    Classifier.getChildren = function(code) {
      return $resource(config.apiUrl+'/classifier/children/:code').query({code: code});
    };

    Classifier.getPossibleConnections = function(code) {
      var resource = $resource(config.apiUrl+'/classifier/connections/:code');
      return resource.query({code: code});
    };

    Classifier.getParents = function(code) {
      var resource = $resource(config.apiUrl+'/classifier/parents/:code');
      return resource.query({code: code});
    };

    Classifier.getParentsWithMainClass = function(parentsMainClassCode, code) {
      var resource = $resource(config.apiUrl+'/classifier/parents/:parentsMainClassCode/:code');
      return resource.query({parentsMainClassCode: parentsMainClassCode, code: code});
    };

    Classifier.toMap = function(array) {
      return array.reduce(function(acc, item) { acc[item.code] = item; return acc; }, {});
    };

    Classifier.getSelectedCodes = function(defs) {
      // create list of selected codes based on model.selected values
      var selected = [];
      for(var rowNo = 0, rowCnt = defs.length;rowNo < rowCnt;rowNo++) {
        var item = defs[rowNo];
        if(item._selected) {
          selected.push(item.code);
        }
      }
      return selected;
    };

    Classifier.setSelectedCodes = function(defs, selected) {
      for(var rowNo = 0, rowCnt = defs.length;rowNo < rowCnt;rowNo++) {
        var item = defs[rowNo];
        item._selected = selected.indexOf(item.code) !== -1;
      }
    };

    return Classifier;
  });
