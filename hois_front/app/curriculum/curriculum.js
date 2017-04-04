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

    function Curriculum() {
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

    Curriculum.modulesViewData = function(modules) {
        var occupationModuleTypesModules = {};
        var modulesWithOutOccupation = {};
        modules.forEach(function(curriculumModule) {
            if (angular.isArray(curriculumModule.occupations) && curriculumModule.occupations.length > 0) {
              curriculumModule.occupations.forEach(function(occupation) {

                if(!angular.isObject(occupationModuleTypesModules[occupation.code])){
                  occupationModuleTypesModules[occupation.code] = {occupation: occupation, moduleTypes: {}};
                }
                if(!angular.isObject(occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code])){
                  occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code] = {moduleType: curriculumModule.module, modules: []};
                }
                occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code].modules
                  .push(curriculumModule);
              });
          } else {
            //modules without occupation
            if (!angular.isObject(modulesWithOutOccupation[curriculumModule.module.code])) {
              modulesWithOutOccupation[curriculumModule.module.code] = {moduleType: curriculumModule.module, modules: []};
            }
            modulesWithOutOccupation[curriculumModule.module.code].modules.push(curriculumModule);
          }
        });

        return { occupationModuleTypesModules: occupationModuleTypesModules, modulesWithOutOccupation: modulesWithOutOccupation};
    };

    Curriculum.queryVersions = function() {
      return QueryUtils.endpoint('/autocomplete/curriculumversions').query();
    };

    return Curriculum;
  });
