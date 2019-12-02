'use strict';

angular.module('hitsaOis').filter('hoisHigherGrade', function ($rootScope, HigherGradeUtil) {
  return function (grade, letterGrades) {
    if (angular.isDefined(grade)) {
      if (letterGrades && !HigherGradeUtil.isDistinctive(grade.code)) {
        return $rootScope.currentLanguageNameField(grade);
      }
      return letterGrades ? grade.value2 : grade.value;
    }
    return null;
  };
}).filter('hoisLimitTo', function ($filter) {
  return function (input, limit, conditional) {
    if (angular.isDefined(conditional)) {
      return input && input.length > limit && conditional ? $filter('limitTo')(input, limit) + '...' : input;
    }
    return input && input.length > limit ? $filter('limitTo')(input, limit) + '...' : input;
  };
}).filter('hoisNumber', function ($filter) {
  return function (input, decimals) {
    var number = input % 1 ? $filter('number')(input, decimals) : $filter('number')(input, 0);
    return number.split(',').join('');
  };
}).filter('hoisUrl', function () {
  var PROTOCOL_IDENTIFIER_REGEX = /^((http|https|ftp):\/\/)/;

  return function (input) {
    if (!PROTOCOL_IDENTIFIER_REGEX.test(input)) {
      return 'http://' + input;
    }
    return input;
  };
});