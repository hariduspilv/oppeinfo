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
      template:'<div class="md-body-1">{{value}}</div>',
      restrict: 'E',
      replace: true,
      scope: {
        ngModel: '=',
        mainClassifierCode: '@'
      },
      link: function postLink(scope, element) {
        element[0].parentElement.classList.add("md-input-has-value");
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
            Classifier.queryForDropdown({mainClassCode: scope.mainClassifierCode}, function(result) {
              var items = result.filter(function(i) { return i.code === params.code});
              if(items.length > 0) {
                scope.value = items[0][scope.$root.currentLanguageNameField()];
              }
            });
          }
        });
      }
    };
  });
