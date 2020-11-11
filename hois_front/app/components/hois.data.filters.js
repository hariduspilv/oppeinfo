'use strict';

angular.module('hitsaOis').filter('hoisVocationalGrade', function ($rootScope) {
  return function (grade) {
    if (grade !== null && angular.isDefined(grade)) {
      return grade.gradingSchemaRowId ? $rootScope.currentLanguageNameField(grade) : grade.value;
    }
    return null;
  };
}).filter('hoisHigherGrade', function ($rootScope, HigherGradeUtil) {
  return function (grade, letterGrades) {
    if (grade !== null && angular.isDefined(grade)) {
      if (grade.gradingSchemaRowId) {
        return $rootScope.currentLanguageNameField(grade);
      }

      if (HigherGradeUtil.isDistinctive(grade.code)) {
        return letterGrades ? grade.value2 : grade.value;
      }
      if (letterGrades) {
        return $rootScope.currentLanguageNameField(grade);
      }
      return $rootScope.currentLanguage() === 'en' ? grade.extraval2 : grade.extraval1;
    }
    return null;
  };
}).filter('hoisLimitTo', function ($filter) {
  return function (input, limit, conditional) {
    if (angular.isDefined(conditional)) {
      return input && input.length > limit && conditional ?
        (angular.isArray(input) ? $filter('limitTo')(input, limit).join(', ') + '...' : $filter('limitTo')(input, limit) + '...') :
        (angular.isArray(input) ? input.join(', ') : input);
    }
    return input && input.length > limit ?
    (angular.isArray(input) ? $filter('limitTo')(input, limit).join(', ') + '...' : $filter('limitTo')(input, limit) + '...') :
    (angular.isArray(input) ? input.join(', ') : input);
  };
}).filter('hoisLimitToList', function ($filter) {
  return function (input, limit, conditional) {
    if (!angular.isArray(input)) return;
    if (angular.isDefined(conditional)) {
      return input && input.length > limit && conditional ? $filter('limitTo')(input, limit) : input;
    }
    return input && input.length > limit ? $filter('limitTo')(input, limit) : input;
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
}).filter('hoisUnique', function () {
  return function (array, key) {
    if (!key) {
      return array;
    }
    var result = [];
    var used = {};

    angular.forEach(array, function (item) {
      if (item[key] && !used[item[key]]) {
        used[item[key]] = true;
        result.push(item);
      }
    });

    return result;
  };
}).filter('linkyWithHtml', function($filter) {
  return function(value) {
    var linked = $filter('linky')(value, '_blank');
    return linked.replace(/\&gt;/g, '>').replace(/\&lt;/g, '<')
      .replace(/(<a href=)(\&#34;.*?)(>)(.+?)(<\/a>)(.+?>)(.+?)(<\/a>)/g, '<a href="$4" target="_blank">$7</a>');
  };
});
