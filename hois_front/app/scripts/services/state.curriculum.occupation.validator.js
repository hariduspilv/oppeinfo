
'use strict';

angular.module('hitsaOis')
  .factory('stateCurriculumOccupationValidator', function(StateCurriculum) {
    var factory = {};

    factory.validate = function(occupations) {
      if(occupations && occupations.length > 0) {
        for(var i = 0; i < occupations.length; i++) {
          if(factory.dontHavePmodule(occupations[i].occupation)) {
            return false;
          }
        }
        return true;
      }
      return false;
    };

    factory.hasAnyOccupation = function(occupations) {
        return occupations.length > 0;
    };

    factory.dontHavePmodule = function(occupation) {     
       return dontHaveModule(occupation, 'KUTSEMOODUL_P');
    };

    function dontHaveModule(occupation, moduleCode) {
      var modules;
      if(StateCurriculum.current) {
        modules = StateCurriculum.current.modules;
      } else {
        modules = undefined;
      }

      if(modules) {
        for(var i = 0; i < modules.length; i++) {
          for(var j = 0; j < modules[i].moduleOccupations.length; j++) {
            if(modules[i].moduleType.code === moduleCode &&
             occupation.code === modules[i].moduleOccupations[j].occupation.code) {
              return false;
            }
          }
        }
      }
      return true;
    }
    return factory;
  });