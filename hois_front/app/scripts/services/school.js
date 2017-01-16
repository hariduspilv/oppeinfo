'use strict';

/**
 * @ngdoc service
 * @name hitsaOis.School
 * @description
 * # School
 * Service in the hitsaOis.
 */
angular.module('hitsaOis').factory('School', function ($resource, QueryUtils) {

  function School() {
  }

  School.getAll = function () {
    return QueryUtils.endpoint('/school/all').query();
  };

  return School;
});
