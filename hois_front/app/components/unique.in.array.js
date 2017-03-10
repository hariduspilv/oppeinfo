'use strict';

angular.module('hitsaOis').directive('hoisUniqueIn', function(ArrayUtils) {
  return {
    require: 'ngModel',
    scope: {
      hoisUniqueIn: '='
    },
    link: function(scope, elm, attrs, ctrl) {
        ctrl.$validators.notInArray = function(modelValue, viewValue) {
            if (ctrl.$isEmpty(modelValue) || !scope.hoisUniqueIn || scope.hoisUniqueIn.length === 0) {
                return true;
            }
            return !ArrayUtils.includes(scope.hoisUniqueIn, viewValue);
        };
    }
  };
});
