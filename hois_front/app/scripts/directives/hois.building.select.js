'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisSchoolDepartmentSelect
 * @description
 * # hoisSchoolSelect
 */
angular.module('hitsaOis').directive('hoisBuildingSelect', function (QueryUtils) {
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
      //fix select not showing required visuals if <hois-school-department-select required> is used
      element.attr('required', scope.isRequired);

      QueryUtils.endpoint('/autocomplete/buildings').query({}, function(result) {
        scope.options = result.sort(function(a, b) {
          var aProp = a.nameEt, bProp = b.nameEt;
          if (aProp < bProp) {
            return -1;
          }
          return aProp > bProp ? 1 : 0;
        });
      });
    }
  };
});
