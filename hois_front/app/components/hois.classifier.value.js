'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierValue
 * @description
 * # hoisClassifierValue
 *
 * This element must be inside md-input-container for label to properly work
 */
angular.module('hitsaOis')
  .directive('hoisClassifierValue', function (Classifier) {
    return {
      template:'<div class="hois-classifier-value">{{value}}</div>',
      restrict: 'E',
      replace: true,
      scope: {
        ngModel: '=',
        mainClassifierCode: '@',
        mainClassifierCodes: '@'
      },
      link: function postLink(scope, element) {
        element[0].parentElement.classList.add("md-input-has-value");
        var classifiervalues;
        scope.$watch('ngModel', function(newVal) {
          var params = null;
          if(angular.isObject(newVal)) {
            if (angular.isString(newVal[scope.$root.currentLanguageNameField()])) {
              scope.value = newVal[scope.$root.currentLanguageNameField()];
            } else {
              params = { code: newVal.code};
            }
          } else if(angular.isString(newVal)) {
            params = { code: newVal};
          } else {
            scope.value = undefined;
          }

          if(params !== null) {
            var setter = function(values) {
              var selected = values.filter(function(i) { return i.code === params.code; });
              if(selected.length > 0) {
                scope.value = selected[0][scope.$root.currentLanguageNameField()];
              }
            };

            if(classifiervalues === undefined) {
              Classifier.queryForDropdown({mainClassCode: scope.mainClassifierCode, mainClassCodes: scope.mainClassifierCodes}, function(result) {
                classifiervalues = result;
                setter(result);
              });
            } else {
              setter(classifiervalues);
            }
          }
        });
      }
    };
  });
