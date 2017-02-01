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
      template: '<md-select ng-model-options="{ trackBy: !!modelValueAttr ? \'$value\' : \'$value.code\' }" required="{{angular.isDefined(scope.required)}}"><md-option ng-repeat="(code, option) in optionsByCode" ng-value="!!modelValueAttr ? option[modelValueAttr] : option" ng-hide="option.hide" ' +
      'aria-label="{{option[$root.currentLanguageNameField()]}}">{{option[$root.currentLanguageNameField()]}}</md-option></md-select>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {
        value: "=ngModel",
        required:'@',
        modelValueAttr: '@',
        mainClassifierCode: '@',
        connectMainClassifierCode: '@',
        connectClassifierCodes: '=',
        criteria: '=',
        filterValues: '@', //model of array of classifiers (or other objects which contain classifier, then byProperty must be defined )
        byProperty: '@',
        showOnlyValues: '@', //model of array of classifiers
        watchModel: '@',
        searchFromConnect: '@',
        selectFirstValue: '@'
      },
      link: function postLink(scope) {
        var optionsByCode = {};
        var params = { order: scope.$root.currentLanguageNameField()};

        if(angular.isDefined(scope.mainClassifierCode)) {
          params.mainClassCode = scope.mainClassifierCode;
        } else if(angular.isArray(scope.connectClassifierCodes)) {
          params.connectClassifierCodes = scope.connectClassifierCodes.toString();
        } else {
          // do not make query if main classifier code is undefined
          return;
        }

        if(angular.isObject(scope.criteria)) {
          angular.extend(params, scope.criteria);
        }

        if(params.connectClassifierCodes) {
          ClassifierConnect.queryAll({connectClassifierCodes: params.connectClassifierCodes}, function(classifierConnectResult) {
              classifierConnectResult.forEach(function(result) {
                var option = {code: result.classifier.code, nameEt: result.classifier.nameEt, nameEn: result.classifier.nameEn, labelRu: result.classifier.labelRu};
                optionsByCode[option.code] = option;
              });
              scope.optionsByCode = optionsByCode;
          });
        }

        var postLoad = function() {
          var deselectHiddenValue = function() {
            if(angular.isObject(scope.value) && angular.isString(scope.value.code) && scope.optionsByCode[scope.value.code].hide) {
              scope.value = undefined;
            } else if(scope.modelValueAttr === 'code' && angular.isString(scope.value) && scope.optionsByCode[scope.value].hide) {
              scope.value = undefined;
            }
          };

          var doOptionsFilering = function() {
            if(angular.isDefined(scope.showOnlyValues)) {
              scope.$parent.$watchCollection(scope.showOnlyValues, function(showOnlyValues) {
                var showOptions = [];
                showOnlyValues.forEach(function(it) {
                  if (angular.isDefined(scope.byProperty)) {
                    if (angular.isString(it[scope.byProperty].code)) {
                      showOptions.push(it[scope.byProperty].code);
                    }
                  } else {
                    if (angular.isString(it.code)) {
                      showOptions.push(it.code);
                    }
                  }
                });
                if (showOptions.length > 0) {
                  for(var key in scope.optionsByCode) {
                    scope.optionsByCode[key].hide = (showOptions.indexOf(key) === -1);
                  }
                }
              });
            }

            if(angular.isDefined(scope.filterValues)) {
              scope.$parent.$watchCollection(scope.filterValues, function(filterValues) {
                if (angular.isArray(filterValues)) {
                  filterValues.forEach(function(it) {
                    if (angular.isDefined(scope.byProperty)) {
                      if (angular.isString(it[scope.byProperty].code)) {
                        scope.optionsByCode[it[scope.byProperty].code].hide = true;
                      }
                    } else {
                      if (angular.isString(it.code)) {
                        scope.optionsByCode[it.code].hide = true;
                      }
                    }
                  });
                }
              });
            }
            deselectHiddenValue();
          };
          doOptionsFilering();

          if(angular.isDefined(scope.watchModel)) {
            scope.$parent.$watch(scope.watchModel, function(newValue) {
              if(!angular.isDefined(newValue) || newValue === null) {
                scope.value = undefined;
              } else if(!!newValue) {
                  var params = {
                    mainClassifierCode: scope.connectMainClassifierCode
                  };

                  if(angular.isDefined(scope.searchFromConnect)){
                    params.connectClassifierCode = newValue.code;
                  } else {
                    params.classifierCode = newValue.code;
                  }

                  ClassifierConnect.queryAll(params, function(result) {
                    var showOptions = [];
                    result.forEach(function(classifierConnect) {
                      var code = !angular.isDefined(scope.searchFromConnect) ? classifierConnect.connectClassifier.code : classifierConnect.classifier.code;
                      showOptions.push(code);
                    });

                    if (showOptions.length > 0) {
                      for(var key in scope.optionsByCode) {
                        scope.optionsByCode[key].hide = (showOptions.indexOf(key) === -1);
                      }
                    }

                    if(angular.isDefined(scope.selectFirstValue) && showOptions.length > 0) {
                      if (angular.isDefined(scope.modelValueAttr)) {
                        scope.value = optionsByCode[showOptions[0]][scope.modelValueAttr];
                      } else {
                        scope.value = optionsByCode[showOptions[0]];
                      }
                    }
                    deselectHiddenValue();
                  });
              }
            });
          }
        };

        if(params.mainClassCode) {
          Classifier.queryAll(params, function(arrayResult) {
            arrayResult.forEach(function(classifier) {
              var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu};
              optionsByCode[option.code] = option;
            });
            scope.optionsByCode = optionsByCode;
            postLoad();
          });
        }
      }
    };
  });
