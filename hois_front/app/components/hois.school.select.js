'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisSchoolSelect
 * @description
 * # hoisSchoolSelect
 */
angular.module('hitsaOis').directive('hoisSchoolSelect', function (School) {
  return {
    template: '<md-select>'+
      '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>'+
      '<md-option ng-repeat="option in options" ng-value="option.id"'+
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      multiple:'@',
      ngRequired:'=',
      required:'@',
      value: '=ngModel'
    },
    link: function postLink(scope, element) {
      scope.isMultiple = angular.isDefined(scope.multiple);
      scope.isRequired = angular.isDefined(scope.required);
      //fix select not showing required visuals if <hois-school-select required> is used
      element.attr('required', scope.isRequired);

      scope.options = School.getAll();
    }
  };
});
