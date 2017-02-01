'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierRadio
 * @description
 * # hoisClassifierRadio
 */
angular.module('hitsaOis')
  .directive('hoisClassifierRadio', function (Classifier, ClassifierConnect) {
    return {
      template: '<md-radio-group ng-model-options="{ trackBy: \'$value.code\' }"><md-radio-button ng-repeat="(code, option) in optionsByCode" ng-value="option"   ' +
      'aria-label="{{option[$root.currentLanguageNameField()]}}">{{option[$root.currentLanguageNameField()]}}</md-radio-button></md-radio-group>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {},
      link: function postLink(scope, element, attrs, ngModelControllers) {
        var optionsByCode = {};
        Classifier.queryForDropdown({mainClassCode: attrs.mainClassifierCode}, function(result) {
          result.forEach(function(classifier) {
            var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu};
            optionsByCode[option.code] = option;
          });
          scope.optionsByCode = optionsByCode;
        });

        //FIX for object selection
        scope.$parent.$watch(attrs.ngModel, function(newValue) {
          if (angular.isObject(newValue)) {
            ngModelControllers[0].$setViewValue(optionsByCode[newValue.code]);
            ngModelControllers[0].$render();
          }
        });

        if(attrs.watchModel !== null) {
          scope.$parent.$watch(attrs.watchModel, function(newValue) {
            if(!!newValue) {
              var params = {
                mainClassifierCode: attrs.connectMainClassifierCode
              };

              if(typeof attrs.searchFromConnect === 'undefined'){
                params.classifierCode = newValue;
              } else {
                params.connectClassifierCode = newValue;
              }

              ClassifierConnect.queryAll(params, function(result) {
                if(typeof attrs.defaultValue !== 'undefined'){
                  ngModelControllers[0].$setViewValue(optionsByCode[attrs.defaultValue]);
                }

                result.forEach(function(result) {
                  var classifier = typeof attrs.searchFromConnect === 'undefined' ? result.connectClassifier : result.classifier;
                  scope.options.forEach(function(option) {
                    if (option.code === classifier.code) {
                      ngModelControllers[0].$setViewValue(option);
                    }
                  });
                });
                ngModelControllers[0].$render();
              });
            }
          });
        }
      }
    };
  });
