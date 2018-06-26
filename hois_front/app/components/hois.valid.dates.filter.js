'use strict';

angular.module('hitsaOis').filter('hoisValidDates', function ($rootScope, hoisDateFilter) {
  return function (input) {
    var result = $rootScope.currentLanguageNameField(input);
    var validFrom = input.validFrom;
    var validThru = input.validThru;
    if (!validFrom && !validThru) {
      return result;
    }
    function dateOrPlaceholder(date) {
      return date ? hoisDateFilter(date) : '...';
    }
    return result + ' (' + dateOrPlaceholder(validFrom) + ' - ' + dateOrPlaceholder(validThru) + ')';
  };
});
