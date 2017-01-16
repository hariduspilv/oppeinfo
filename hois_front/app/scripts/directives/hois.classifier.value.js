'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierValue
 * @description
 * # hoisClassifierValue
 */
angular.module('hitsaOis')
  .directive('hoisClassifierValue', function (Classifier) {
    return {
      template:'<span>{{value}}</span>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {},
      link: function postLink(scope, element, attrs, ngModelControllers) {
        var ngModelController = ngModelControllers[0];
        ngModelController.$render = function() {
          if(!!ngModelController.$modelValue) {
            var params = { code: ngModelController.$modelValue.code, mainClassCode: attrs.mainClassifierCode};
            Classifier.queryAll(params, function(result) {
              result.forEach(function(classifier) {
                scope.value = classifier[scope.$root.currentLanguageNameField()];
              });
            });
          }
        };
      }
    };
  });
