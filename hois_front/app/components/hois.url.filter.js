'use strict';

angular.module('hitsaOis').filter('hoisUrl', function () {
  var PROTOCOL_IDENTIFIER_REGEX = /^((http|https|ftp):\/\/)/;
  
  return function (input) {
    if (!PROTOCOL_IDENTIFIER_REGEX.test(input)) {
      return 'http://' + input;
    }
    return input;
  };
});