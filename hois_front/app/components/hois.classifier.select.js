(function(){
'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierSelect
 * @description
 * # hoisClassifierSelect
 */
angular.module('hitsaOis')
  .directive('hoisClassifierSelect', function (Classifier, ClassifierConnect, hoisValidDatesFilter) {
    return {
      // ng-hide on md-option causes a problem with being not closed after selecting value. Replaced ng-hide by ng-if.
      // @since 07.01.2019: We need to use ng-hide to have an opportunity to fill a selected value which were placed before.
      // It means that object had a value, but now it should be hidden. Still we need to display value as selected.
      // @since 11.01.2019: `ng-if="!option.hide || (value.code === code || value === code)"` gives an opportunity to select given value before.
      // So if we give a value to `ngModel` outside (`value` inside) then it will be visible.
      template: '<md-select ng-model-options="{ trackBy: !!modelValueAttr ? \'$value\' : \'$value.code\' }" >'+ // md-on-open="queryPromise" this causes some bugs
      '<md-option ng-if="!isHideEmpty && !isMultiple && ((!isRequired && !ngRequired) || isShowEmpty)" md-option-empty></md-option>'+
      '<md-option ng-repeat="(code, option) in optionsByCode" ng-value="!!modelValueAttr ? option[modelValueAttr] : option" ng-if="!option.hide || isSelected(option)" ' +
      'aria-label="{{getLabel(option)}}">{{getLabel(option)}}</md-option></md-select>',
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
        mainClassifierCodes: '@',
        connectMainClassifierCode: '@',
        criteria: '=',
        filterValues: '@', //model of array of classifiers (or other objects which contain classifier, then byProperty must be defined )
        ignorePreselected: '@',
        byProperty: '@',
        hideEmpty: '@',
        showEmpty: '@',
        showOnlyValues: '@', //model of array of classifiers (if value === true, all items are shown)
        watchModel: '@',
        watchMultiple: '@',
        searchFromConnect: '@',
        selectFirstValue: '@',
        defaultValue: '@',
        onlyValid: '@',
        ignoreCodeValidation: '<' // one time binding. Ignores value during validation which happens with onlyValid. Checks by 'code' attribute.
      },
      link: function postLink(scope, element) {
        scope.isMultiple = angular.isDefined(scope.multiple);
        scope.isRequired = angular.isDefined(scope.required);
        scope.isHideEmpty = angular.isDefined(scope.hideEmpty);
        scope.isShowEmpty = angular.isDefined(scope.showEmpty);
        //fix select not showing required visuals if <hois-classifier-select required> is used
        element.attr('required', scope.isRequired);
        
        scope.isSelected = function(option) {
          if (angular.isUndefined(scope.value)) {
            return false;
          }
          if (scope.isMultiple) {
            if (angular.isDefined(scope.modelValueAttr)) {
              if (angular.isArray(scope.value)) {
                return scope.value.filter(function(opt) {
                  return opt === option[scope.modelValueAttr];
                }).length > 0;
              }
              return false;
            } else {
              if (angular.isArray(scope.value)) {
                return scope.value.filter(function(opt) {
                  return opt.code === option.code;
                }).length > 0;
              }
              return false;
            }
          } else {
            if (angular.isDefined(scope.modelValueAttr)) {
              return scope.value === option[scope.modelValueAttr];
            } else {
              return scope.value.code === option.code;
            }
          }
        };

        scope.getLabel = angular.isDefined(scope.onlyValid) ? hoisValidDatesFilter : scope.$root.currentLanguageNameField;

        classifierPostLink(scope, Classifier, ClassifierConnect);
      }
    };
  })

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisClassifierRadio
 * @description
 * # hoisClassifierRadio
 */
  .directive('hoisClassifierRadio', function (Classifier, ClassifierConnect) {
    return {
      template: '<md-radio-group ng-model-options="{ trackBy: !!modelValueAttr ? \'$value\' : \'$value.code\' }"><md-radio-button ng-repeat="(code, option) in optionsByCode" ng-value="!!modelValueAttr ? option[modelValueAttr] : option" ng-hide="option.hide" ' +
      'aria-label="{{$root.currentLanguageNameField(option)}}">{{$root.currentLanguageNameField(option)}}</md-radio-button></md-radio-group>',
      restrict: 'E',
      require: ['ngModel', '^form'],
      replace: true,
      scope: {
        value: "=ngModel",
        required:'@',
        disabled:'@',
        modelValueAttr: '@',
        mainClassifierCode: '@',
        watchModel: '@',
        connectMainClassifierCode: '@',
        searchFromConnect: '@',
        selectFirstValue: '@',
        defaultValue: '@',
        order: '@'
      },
      link: function postLink(scope, element, attrs, ngModelControllers) {
        scope.isRequired = angular.isDefined(scope.required);
        element.attr('required', scope.isRequired);

        scope.isDisabled = angular.isDefined(scope.disabled);
        element.attr('disabled', scope.isDisabled);

        var modelController = ngModelControllers[0];
        var formController = ngModelControllers[1];


        var inputContainer = element[0].parentElement;
        var label = inputContainer.querySelector('label');

        if(inputContainer.tagName === 'MD-INPUT-CONTAINER') {
          if (inputContainer.querySelector('label') !== null) {
            element[0].style.marginTop = "0.5em";
            //inputContainer.classList.add("md-input-focused");
            inputContainer.style.marginTop = "1em";
            inputContainer.style.marginBottom = "0.5em";
            label.style.marginBottom = "2em";
            if (scope.isRequired) {
              label.classList.add("md-required");
            }

            modelController.$viewChangeListeners.push(function() {
              if (angular.isDefined(modelController.$viewValue) && modelController.$viewValue !== null) {
                label.style.marginBottom = "0";
                inputContainer.classList.add("md-input-has-value");
              } else {
                inputContainer.classList.remove("md-input-has-value");
                label.style.marginBottom = "2em";
              }
            });

            scope.form = formController;
            scope.$watch("form.$submitted",function(submitted){
              if(modelController.$valid || submitted === false) {
                inputContainer.classList.remove("md-input-invalid");
              } else {
                inputContainer.classList.add("md-input-invalid");
              }
            });

          }
        }

        classifierPostLink(scope, Classifier, ClassifierConnect);
      }
    };
  });

  function classifierPostLink(scope, Classifier, ClassifierConnect) {

    var optionsByCode = {};
    var params = { order: scope.order ? scope.order : scope.$root.currentLanguageNameField()};

    if(angular.isDefined(scope.mainClassifierCode)) {
      params.mainClassCode = scope.mainClassifierCode;
    }  else if(angular.isString(scope.mainClassifierCodes)) {
      params.mainClassCodes = scope.mainClassifierCodes.replace(/\s/g, '');
    } else {
      // do not make query if main classifier code is undefined
      return;
    }

    if(angular.isObject(scope.criteria)) {
      angular.extend(params, scope.criteria);
    }

    var postLoad = function() {
      var deselectHiddenValue = function() {
        if (angular.isUndefined(scope.ignorePreselected)) {
          if (angular.isArray(scope.value)) {
            scope.value.forEach(function(valueCode, index, array) {
              if(angular.isString(valueCode) && (!angular.isDefined(scope.optionsByCode[valueCode]) || scope.optionsByCode[valueCode].hide)) {
                // Removed making scope.value undefined. (Several values might be there then only 1 value should be hidden)
                array.splice(index, 1);
              }
            });
          } else if(angular.isObject(scope.value) && angular.isString(scope.value.code) &&
            (!angular.isDefined(scope.optionsByCode[scope.value.code]) || scope.optionsByCode[scope.value.code].hide)) {
            scope.value = undefined;
          } else if(scope.modelValueAttr === 'code' && angular.isString(scope.value) &&
            (!angular.isDefined(scope.optionsByCode[scope.value]) || scope.optionsByCode[scope.value].hide)) {
            scope.value = undefined;
          }
        }
      };

      var doOptionsFiltering = function() {
        if(angular.isDefined(scope.showOnlyValues)) {
          scope.$parent.$watchCollection(scope.showOnlyValues, function(changedShowOnlyValues) {
            var showOptions = [];
            if (angular.isArray(changedShowOnlyValues)) {
              changedShowOnlyValues.forEach(function(it) {
                var value = it;
                if (angular.isDefined(scope.byProperty) && angular.isObject(it)) {
                  value = it[scope.byProperty];
                }

                if (angular.isString(value)) {
                  showOptions.push(value);
                } else if(angular.isObject(value) && angular.isString(value.code)) {
                  showOptions.push(value.code);
                }
              });
            }
            // changedShowOnlyValues value true means "show all items"
            for(var key in scope.optionsByCode) {
              var option = scope.optionsByCode[key];
              option.hide = (changedShowOnlyValues !== true && showOptions.indexOf(key) === -1);
              if (angular.isDefined(scope.onlyValid) && option.hide === false && !Classifier.isValid(option)) {
                option.hide = true;
              }
            }
            deselectHiddenValue();
          });
        }

        if(angular.isDefined(scope.filterValues)) {
          scope.$parent.$watchCollection(scope.filterValues, function(changedFilterValues) {
            var hideOptions = [];
            if (angular.isArray(changedFilterValues)) {
              changedFilterValues.forEach(function(it) {
                var value = it;
                if (angular.isDefined(scope.byProperty) && angular.isObject(it)) {
                  value = it[scope.byProperty];
                }

                if (angular.isString(value)) {
                  hideOptions.push(value);
                } else if(angular.isObject(value) && angular.isString(value.code)) {
                  hideOptions.push(value.code);
                }
              });
            }

            for(var key in scope.optionsByCode) {
              var option = scope.optionsByCode[key];
              option.hide = hideOptions.indexOf(key) !== -1;
              if (angular.isDefined(scope.onlyValid) && option.hide === false && !Classifier.isValid(option)) {
                option.hide = true;
              }
            }
            deselectHiddenValue();
          });
        }

        if(angular.isDefined(scope.onlyValid)) {
          for(var key in scope.optionsByCode) {
            var option = scope.optionsByCode[key];
            if (!(angular.isDefined(scope.ignoreCodeValidation) && scope.ignoreCodeValidation === option.code)) {
              option.hide = !Classifier.isValid(option);
            }
          }
        }
        deselectHiddenValue();
      };
      doOptionsFiltering();

      function showAllValues() {
        for(var key in scope.optionsByCode) {
          scope.optionsByCode[key].hide = false;
        }
      }

      function watchCallback(newValue) {
          if(!angular.isDefined(newValue) || newValue === null || newValue === '' || (angular.isArray(newValue) && newValue.length === 0)) {
            scope.value = undefined;
            showAllValues();
          } else  {
              var params = {
                mainClassifierCode: scope.connectMainClassifierCode
              };

              var code = newValue;
              if (angular.isArray(newValue)) {
                code = newValue.join(",");
              } else if(angular.isObject(newValue)) {
                code = newValue.code;
              }

              if(angular.isDefined(scope.searchFromConnect)) {
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

                //removed "showOptions.length > 0" for hiding all values if no connections found?
                if ((angular.isDefined(scope.showOnlyValues) || angular.isDefined(scope.filterValues))) {
                  for(var key in scope.optionsByCode) {
                    scope.optionsByCode[key].hide = (showOptions.indexOf(key) === -1);
                  }
                }

                if(angular.isDefined(scope.selectFirstValue)) {
                  var value = showOptions.length > 0 ? showOptions[0] : (angular.isDefined(scope.defaultValue) ? scope.defaultValue : null);
                  if (value !== null) {
                    if (angular.isDefined(scope.modelValueAttr)) {
                      scope.value = optionsByCode[value][scope.modelValueAttr];
                    } else {
                      scope.value = optionsByCode[value];
                    }
                  }
                }
                deselectHiddenValue();
              });
          }

          if (!angular.isDefined(scope.value) && angular.isDefined(scope.defaultValue)) {
            scope.value = scope.defaultValue;
          }
      }

      if(angular.isDefined(scope.watchMultiple)) {
        scope.$parent.$watchCollection(scope.watchModel, watchCallback);
      } else if(angular.isDefined(scope.watchModel)) {
        scope.$parent.$watch(scope.watchModel, watchCallback);
      }
    };

    if(params.mainClassCode || params.mainClassCodes) {
      var query = Classifier.queryForDropdown(params, function(arrayResult) {
        arrayResult.forEach(function(classifier) {
          var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu,
            validFrom: classifier.validFrom, validThru: classifier.validThru};
          optionsByCode[option.code] = option;
        });
        scope.optionsByCode = optionsByCode;
        postLoad();
      });
     scope.queryPromise = query.$promise;
    }
}
})();
