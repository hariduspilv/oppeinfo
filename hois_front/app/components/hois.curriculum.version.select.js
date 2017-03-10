'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCurriculumVersionSelect
 * @description
 * # hoisCurriculumVersionSelect
 */
angular.module('hitsaOis').directive('hoisCurriculumVersionSelect', function (Curriculum) {
  return {
    template: '<md-select><md-option ng-repeat="option in options" ng-value="option.id"' +
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      value: '=ngModel'
    },
    link: function postLink(scope) {
      Curriculum.queryVersions().$promise.then(function(result) {
        scope.options = result.content;
      });
    }
  };
});
