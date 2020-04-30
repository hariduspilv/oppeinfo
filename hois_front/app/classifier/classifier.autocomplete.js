'use strict';

angular.module('hitsaOis')
  .factory('classifierAutocomplete', function ($translate, config, $resource) {
    var factory = {};
    var resource = $resource(config.apiUrl+'/classifier/getPossibleParentClassifiers');

    factory.searchByName = function (queryText, queryMainClassCode, additionalParams) {
      if(!queryText || queryText.length < 1) {
        return [];
      }
      var query = {mainClassCode: queryMainClassCode, name: queryText, language: $translate.use() === 'en' ? 'EN' : 'ET'};
      if (angular.isDefined(additionalParams)) {
        angular.extend(query, additionalParams);
      }
      return resource.query(query).$promise;
    };

    return factory;
  });
