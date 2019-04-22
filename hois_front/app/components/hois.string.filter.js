'use strict';

angular.module('hitsaOis').filter('hoisLimitTo', function ($filter) {
  return function (input, limit, conditional) {
    if (angular.isDefined(conditional)) {
      return input && input.length > limit && conditional ? $filter('limitTo')(input, limit) + '...' : input;  
    }
    return input && input.length > limit ? $filter('limitTo')(input, limit) + '...' : input;
  };
});