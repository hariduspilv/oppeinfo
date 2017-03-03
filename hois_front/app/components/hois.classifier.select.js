(function(){
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
        connectMainClassifierCode: '@',
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
    var params = { order: scope.$root.currentLanguageNameField()};

    if(angular.isDefined(scope.mainClassifierCode)) {
      params.mainClassCode = scope.mainClassifierCode;
    }  else {
      // do not make query if main classifier code is undefined
      return;
    }

    if(angular.isObject(scope.criteria)) {
      angular.extend(params, scope.criteria);
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
            for(var key in scope.optionsByCode) {
              scope.optionsByCode[key].hide = (showOptions.indexOf(key) === -1);
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
              scope.optionsByCode[key].hide = (hideOptions.indexOf(key) !== -1);
            }
            deselectHiddenValue();
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
})();
