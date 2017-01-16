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
      angular.extend(this, args);

      this.save = function() {
        if(angular.isNumber(this.id) && this.id > 0) {
          return $resource(config.apiUrl+'/curriculum/:id').update(this);
        } else {
          return $resource(config.apiUrl+'/curriculum').save(this);
        }
      };
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

    return Curriculum;
  });
