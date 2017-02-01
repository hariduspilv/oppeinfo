'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.Curriculum
 * @description
 * # Curriculum
 * Factory in the hitsaOis.
 */
angular.module('hitsaOis')
  .factory('Curriculum', function ($resource, config, QueryUtils) {

    function Curriculum(args) {
    }

    Curriculum.query = function(params, successCallback) {
      var resource = $resource(config.apiUrl+'/curriculum');
      var queryParams = QueryUtils.getQueryParams(params);
      return resource.get(queryParams, successCallback);
    };

    Curriculum.getAreasOfStudyByGroupOfStudy = function(code, successCallback) {
        var resource = $resource(config.apiUrl+'/curriculum/areasOfStudyByGroupOfStudy/' + code);
        return resource.query(successCallback);
    };

    Curriculum.queryVersions = function() {
      return QueryUtils.endpoint('/autocomplete/curriculumversions').get();
    };

    return Curriculum;
  });
