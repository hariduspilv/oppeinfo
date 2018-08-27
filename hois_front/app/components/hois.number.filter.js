'use strict';

angular.module('hitsaOis').filter('hoisNumber', function ($filter) {
  return function (input, decimals) {
    if (input % 1) {
      return $filter('number')(input, decimals);
    }
    return $filter('number')(input, 0);
  };
});