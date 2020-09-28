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
}).filter('hoisDayMonthTimeMin', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM HH:mm');
  };
}).filter('hoisTimestamp', function ($filter) {
  return function (input) {
    var formatter = $filter('date');
    var value = formatter(input, 'dd.MM.yyyy HH:mm');
    if (value !== undefined && value !== null && value.lastIndexOf('00:00') === -1) {
      return value;
    }
    return formatter(input, 'dd.MM.yyyy');
  };
}).filter('hoisTimeMin', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'HH:mm');
  };
}).filter('hoisYear', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'yyyy');
  };
}).filter('hoisDateShortYear', function ($filter) {
  return function (input) {
    return $filter('date')(input, 'dd.MM.yy');
  };
}).filter('hoisZonedDate', function () {
  return function (input) {
    return moment.parseZone(input).format('DD.MM.yyyy');
  };
});
