'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisSchoolDepartmentSelect
 * @description
 * # hoisSchoolSelect
 */
angular.module('hitsaOis').directive('hoisSchoolDepartmentSelect', function (QueryUtils) {
  return {
    template: '<md-select>'+
      '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>'+
      '<md-option ng-repeat="option in options" ng-value="option.id"'+
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-option></md-select>',
    restrict: 'E',
    replace: true,
    scope: {
      excludedId: '@',
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

      var filterValues = function() {
        if(scope.excludedIdValue) {
          scope.options = scope.optionValues.filter(function(i) { return i.id !== scope.excludedIdValue; });
        } else {
          scope.options = scope.optionValues;
        }
      };
      QueryUtils.endpoint('/autocomplete/schooldepartments').search({}, function(result) {
        scope.optionValues = result.content.sort(function(a, b) {
          var aProp = a.nameEt, bProp = b.nameEt;
          if (aProp < bProp) {
            return -1;
          }
          return aProp > bProp ? 1 : 0;
        });
        filterValues();
      });
      if(angular.isDefined(scope.excludedId)) {
        scope.$parent.$watch(scope.excludedId, function(excludedId) {
          scope.excludedIdValue = excludedId;
          if(scope.optionValues !== undefined) {
            filterValues();
          }
        });
      }
    }
  };
});
