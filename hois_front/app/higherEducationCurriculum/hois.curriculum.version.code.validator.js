'use strict';

angular.module('hitsaOis').directive('hoisCurriculumVersionCodeValidator', function(config, $http) {
  return {
    restrict: 'A',
    scope: {
      unique: '=uniqueQuery',
      validationMethodReference: "&uniqueFunction"
    },
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$asyncValidators.unique = function(modelValue, viewValue) {

        scope.unique.paramValue = viewValue;
        return $http({
          url: config.apiUrl + scope.unique.url,
          method: "GET",
          params: scope.unique
        }).then(function(response) {
          ctrl.$setValidity('hoisQueryValidation', response.data);
          return response.data;
        });
      };
        ctrl.$validators.hoisFunctionValidation = function(modelValue) {
            
            if (ctrl.$isEmpty(modelValue)) {
                return true;
            }
            if(!scope.validationMethodReference() || !angular.isFunction(scope.validationMethodReference())) {
                throw "Attribute parameter must be a function, which takes a field's modelValue as input parameter";
            }
            return scope.validationMethodReference()(modelValue);
        };
    }
  };
});