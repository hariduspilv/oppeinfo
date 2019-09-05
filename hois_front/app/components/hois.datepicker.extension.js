'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisNavbarOverflowed
 * @description
 * 
 * mdDatepicker does not support allowInvalid function
 * AngularJS puts a parser validation for any new filter.
 * Input field has "date" type which makes it impossible to put any not Date object.
 * We need to call clear to clear in case of invalid date/object type.
 * !!! Scope is not isolated so it takes the scope of parent. Be careful with ngIf.
 */
angular.module('hitsaOis').directive('hoisDatepickerExtension', function () {
  return {
    restrict: 'A',
    require: ['^mdDatepicker'],
    link: {
      post: function(scope, element, attrs, controllers) {
        var mdDatepicker = controllers[0];

        if (mdDatepicker) {
          var controller = {
            clear: function () {
              if (!(mdDatepicker.date instanceof Date && !isNaN(mdDatepicker.date))) {
                mdDatepicker.ngModelCtrl.$$setModelValue(undefined);
              }
            }
          }
          
          var previousControllerName = undefined;
          attrs.$observe('hdController', function(newValue) {
            if (previousControllerName === newValue || !newValue) {
              return;
            }
            previousControllerName = newValue;
            if (angular.isDefined(scope[newValue]) && angular.isArray(scope[newValue])) {
              scope[newValue].push(controller);
            } else {
              scope[newValue] = [controller];
            }
          });
        }
      }
    }
  };
});