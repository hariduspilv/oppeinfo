'use strict';

angular.module('hitsaOis')
  .factory('classifierAutocomplete', function ($translate, config, $resource) {
    var factory = {};

    var query = {
      mainClassCode: '',
      name: '',
      language: ''
    };

    function getClassifiers() {
      var resource = $resource(config.apiUrl+'/classifier/getPossibleParentClassifiers');
      return resource.query(query).$promise;
    }

    factory.searchByName = function (queryText, queryMainClassCode) {
      if(!queryText || queryText.length < 3) {
        return [];
      }
      query.mainClassCode  = queryMainClassCode;
      query.name = queryText;
      query.language = $translate.use() === 'en' ? 'EN' : 'ET';

      return getClassifiers();
    };

    return factory;
  });
