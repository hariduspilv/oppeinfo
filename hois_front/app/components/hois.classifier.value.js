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
  .directive('hoisClassifierValue', function (Classifier, ArrayUtils, hoisValidDatesFilter) {
    return {
      template:'<div class="hois-classifier-value">{{value}}</div>',
      restrict: 'E',
      replace: true,
      scope: {
        ngModel: '=',
        mainClassifierCode: '@',
        mainClassifierCodes: '@',
        showDate: '@'
      },
      link: function postLink(scope, element) {
        element[0].parentElement.classList.add("md-input-has-value");
        var classifiervalues;
        scope.getLabel = angular.isDefined(scope.showDate) ? hoisValidDatesFilter : scope.$root.currentLanguageNameField;
        scope.$watch('ngModel', function(newVal) {
          var params = null;
          if(angular.isObject(newVal)) {
            if (angular.isString(newVal[scope.$root.currentLanguageNameField()])) {
              scope.value = newVal[scope.$root.currentLanguageNameField()];
            } else if(angular.isArray(newVal) && newVal.length > 0) {
              if(angular.isString(newVal[0])) {
                params = {codes: newVal};
              } else {
                var codes = newVal.map(function(el){
                  return el.code;
                });
                params = {codes: codes};
              }
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
              var selected = values.filter(function(i) {
                if(params.code) {
                  return i.code === params.code;
                } else if(params.codes) {
                  return ArrayUtils.includes(params.codes, i.code);
                } else {
                  return false;
                }
              });
              if(selected.length > 0) {
                var nameStrings = selected.map(scope.getLabel);
                scope.value = nameStrings.join(', ');
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
