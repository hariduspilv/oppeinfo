'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierSelect
 * @description
 * # hoisClassifierSelect
 */
angular.module('hitsaOis')
  .directive('hoisClassifierSelect', function (Classifier, ClassifierConnect) {
    return {
      template: '<md-select ng-model-options="{ trackBy: \'$value.code\' }"><md-option ng-repeat="(code, option) in optionsByCode" ng-value="option" ng-hide="option.hide" ' +
      'aria-label="{{option[$root.currentLanguageNameField()]}}">{{option[$root.currentLanguageNameField()]}}</md-option></md-select>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {
        criteria: '='
      },
      link: function postLink(scope, element, attrs, ngModelControllers) {
        var optionsByCode = {};
        var params = { order: scope.$root.currentLanguageNameField()};

        if(typeof attrs.mainClassifierCode !== 'undefined') {
          params.mainClassCode = attrs.mainClassifierCode;
        } else if(typeof attrs.connectClassifierCodes !== 'undefined') {
          params.connectClassifierCodes = attrs.connectClassifierCodes;
        } else {
          // do not make query if main classifier code is undefined
          return;
        }

        if(angular.isObject(scope.criteria)) {
          angular.extend(params, scope.criteria);
        }

        if(params.connectClassifierCodes) {
          ClassifierConnect.queryAll({connectClassifierCodes: attrs.connectClassifierCodes}, function(classifierConnectResult) {
              classifierConnectResult.forEach(function(result) {
                var option = {code: result.classifier.code, nameEt: result.classifier.nameEt, nameEn: result.classifier.nameEn, labelRu: result.classifier.labelRu};
                optionsByCode[option.code] = option;
              });
              scope.optionsByCode = optionsByCode;
          });
        }

        if(params.mainClassCode) {
          Classifier.queryAll(params, function(arrayResult) {
            arrayResult.forEach(function(classifier) {
              var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu};
              optionsByCode[option.code] = option;
            });
            scope.optionsByCode = optionsByCode;
          });

          if(typeof attrs.watchModel !== 'undefined') {
            scope.$parent.$watch(attrs.watchModel, function(newValue) {
              if(!!newValue) {
                  var params = {
                    mainClassifierCode: attrs.connectMainClassifierCode
                  };

                  if(typeof attrs.searchFromConnect === 'undefined'){
                    params.classifierCode = newValue.code;
                  } else {
                    params.connectClassifierCode = newValue.code;
                  }


                  ClassifierConnect.queryAll(params, function(result) {
                    var showOptions = [];
                    result.forEach(function(classifierConnect) {
                      var code = (typeof attrs.searchFromConnect === 'undefined') ? classifierConnect.connectClassifier.code : classifierConnect.classifier.code;
                      showOptions.push(code);
                    });

                    if(typeof attrs.filterValues !== 'undefined') {
                      if(ngModelControllers[0].$modelValue && showOptions.indexOf(ngModelControllers[0].$modelValue.code) === -1){
                        ngModelControllers[0].$setViewValue(null);
                        ngModelControllers[0].$render();
                      }
                      for(var key in scope.optionsByCode) {
                        scope.optionsByCode[key].hide = (showOptions.indexOf(key) === -1);
                      }
                    }

                    if(typeof attrs.selectFirstValue !== 'undefined' && showOptions.length > 0){
                      ngModelControllers[0].$setViewValue(optionsByCode[showOptions[0]]);
                      ngModelControllers[0].$render();
                    }
                  });
              }
            });
          }
        }
      }
    };
  });
