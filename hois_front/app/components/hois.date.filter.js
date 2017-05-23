'use strict';

angular.module('hitsaOis').filter('hoisDate', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yyyy');
  };
}).filter('hoisDateTime', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yyyy HH:mm:ss');
  };
}).filter('hoisTime', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'HH:mm:ss');
  };
});
