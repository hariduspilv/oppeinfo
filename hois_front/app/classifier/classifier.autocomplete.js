'use strict';

angular.module('hitsaOis')
  .factory('classifierAutocomplete', function ($translate, config, $resource) {
    var factory = {};
    var resource = $resource(config.apiUrl+'/classifier/getPossibleParentClassifiers');

    factory.searchByName = function (queryText, queryMainClassCode) {
      if(!queryText || queryText.length < 3) {
        return [];
      }
      var query = {mainClassCode: queryMainClassCode, name: queryText, language: $translate.use() === 'en' ? 'EN' : 'ET'};
      return resource.query(query).$promise;
    };

    return factory;
  });
