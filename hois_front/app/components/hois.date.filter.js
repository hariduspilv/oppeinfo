'use strict';

angular.module('hitsaOis').filter('hoisDate', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yyyy');
  };
}).filter('hoisDateTime', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yyyy HH:mm:ss');
  };
}).filter('hoisDateTimeMin', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yyyy HH:mm');
  };
}).filter('hoisTime', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'HH:mm:ss');
  };
}).filter('hoisDayMonth', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM');
  };
});