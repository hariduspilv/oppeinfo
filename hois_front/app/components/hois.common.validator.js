'use strict';

angular.module('hitsaOis').directive('hoisCommonValidator', function() {
  return {
    require: 'ngModel',
    scope: {
      validationMethodReference: "&hoisCommonValidator"
    },
    link: function(scope, elm, attrs, ctrl) {
        ctrl.$validators.hoisCommonValidation = function(modelValue) {
            
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
