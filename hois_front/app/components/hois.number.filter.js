'use strict';

angular.module('hitsaOis').filter('hoisNumber', function ($filter) {
  return function (input, decimals) {
    var number = input % 1 ? $filter('number')(input, decimals) : $filter('number')(input, 0);
    return number.split(',').join('');
  };
});