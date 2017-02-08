'use strict';

function classifierPostLink(scope, Classifier, ClassifierConnect, QueryUtils, $filter) {
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
            if(angular.isObject(scope.value) && angular.isString(scope.value.code) &&
              (!angular.isDefined(scope.optionsByCode[scope.value.code]) || scope.optionsByCode[scope.value.code].hide)) {
              scope.value = undefined;
            } else if(scope.modelValueAttr === 'code' && angular.isString(scope.value) &&
              (!angular.isDefined(scope.optionsByCode[scope.value]) || scope.optionsByCode[scope.value].hide)) {
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
                    if (angular.isObject(it) && angular.isString(it.code)) {
                      showOptions.push(it.code);
                    } else if (angular.isString(it)) {
                      showOptions.push(it);
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

                  var code = angular.isString(newValue) ? newValue : newValue.code;
                  if(angular.isDefined(scope.searchFromConnect)){
                    params.connectClassifierCode = code;
                  } else {
                    params.classifierCode = code;
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
          var paramsCount = Object.keys(params).length;
          var query = paramsCount === 1 || (paramsCount === 2 && params.order) ? Classifier.queryForDropdown : Classifier.queryAll;
          query(params, function(arrayResult) {
            arrayResult.forEach(function(classifier) {
              var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu};
              optionsByCode[option.code] = option;
            });
            scope.optionsByCode = optionsByCode;
            postLoad();
          });
/*          QueryUtils.endpoint('/autocomplete/classifiers').query({mainClassCode: params.mainClassCode}).$promise
            .then(function(response) {
              var sortedResponse = $filter('orderBy')(response, params.order);
              sortedResponse.forEach(function(classifier) {
                var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu};
                optionsByCode[option.code] = option;
              });
              scope.optionsByCode = optionsByCode;
              postLoad();
            });*/
        }
      }

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierSelect
 * @description
 * # hoisClassifierSelect
 */
angular.module('hitsaOis')
  .directive('hoisClassifierSelect', function (Classifier, ClassifierConnect, QueryUtils, $filter) {
    return {
      template: '<md-select ng-model-options="{ trackBy: !!modelValueAttr ? \'$value\' : \'$value.code\' }">'+
      '<md-option ng-if="!isMultiple && !isRequired && !ngRequired" md-option-empty></md-option>'+
      '<md-option ng-repeat="(code, option) in optionsByCode" ng-value="!!modelValueAttr ? option[modelValueAttr] : option" ng-hide="option.hide" ' +
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-option></md-select>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {
        value: "=ngModel",
        required:'@',
        ngRequired:'=',
        multiple:'@',
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
      link: function postLink(scope, element) {
        scope.isMultiple = angular.isDefined(scope.multiple);
        scope.isRequired = angular.isDefined(scope.required);
        //fix select not showing required visuals if <hois-classifier-select required> is used
        element.attr('required', scope.isRequired);

        classifierPostLink(scope, Classifier, ClassifierConnect, QueryUtils, $filter);
      }
    };
  })

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierRadio
 * @description
 * # hoisClassifierRadio
 */
  .directive('hoisClassifierRadio', function (Classifier, ClassifierConnect, QueryUtils, $filter) {
    return {
      template: '<md-radio-group ng-model-options="{ trackBy: !!modelValueAttr ? \'$value\' : \'$value.code\' }"><md-radio-button ng-repeat="(code, option) in optionsByCode" ng-value="!!modelValueAttr ? option[modelValueAttr] : option" ng-hide="option.hide" ' +
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-radio-button></md-radio-group>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {
        value: "=ngModel",
        required:'@',
        disabled:'@',
        modelValueAttr: '@',
        mainClassifierCode: '@',
        connectMainClassifierCode: '@',
      },
      link: function postLink(scope, element) {
        scope.isRequired = angular.isDefined(scope.required);
        element.attr('required', scope.isRequired);

        scope.isDisabled = angular.isDefined(scope.disabled);
        element.attr('disabled', scope.isDisabled);

        classifierPostLink(scope, Classifier, ClassifierConnect, QueryUtils, $filter);
      }
    };
  });
