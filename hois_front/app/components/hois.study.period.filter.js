'use strict';

angular.module('hitsaOis').filter('hoisStudyPeriod', function ($rootScope) {
  return function (studyPeriod) {
    var result = '';
    if (!angular.isObject(studyPeriod)) {
      return result;
    }
    result = $rootScope.currentLanguageNameField(studyPeriod);
    if (!angular.isObject(studyPeriod.studyYear)) {
      return result;
    }
    return $rootScope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + result;
  };
});
