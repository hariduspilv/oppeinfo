'use strict';

/**
 * Example of clearing autocomplete fields:
 * (JS)
 * $scope.directiveControllers = [];
 * var clearCriteria = $scope.clearCriteria;
 * $scope.clearCriteria = function () {
 *   clearCriteria();
 *   $scope.directiveControllers.forEach(function (c) {
 *     c.clear();
 *   });
 * }
 * 
 * (HTML)
 * <hois-autocomplete ng-model="criteria.object" ha-attribute="id" ha-controller="directiveControllers" ...></hois-autocomplete>
 */
angular.module('hitsaOis')
  .directive('hoisAutocomplete', function ($rootScope, $translate, $q, QueryUtils) {

    return {
      templateUrl: function (elem, attr) {
        if (angular.isDefined(attr.multiple)) {
          return 'components/hois.chip.autocomplete.html';
        } else if (angular.isDefined(attr.label) && attr.label.length > 0) {
          return 'components/hois.autocomplete.html';
        } else {
          return 'components/hois.autocomplete.nolabel.html';
        }
      },
      restrict: 'E',
      require: 'ngModel',
      scope: {
        maxChips: '@',
        ngModel: '=',
        ngSearchText: '=?',
        label: '@',
        method: '@',
        haAttribute: '@',
        haSearch: "=?",
        display: '@',
        mdMinLength: '@',
        haIgnored: '@',
        multiple: '@',
        ngRequired: '=',
        required: '@', // todo add chip visuals
        ngDisabled: '=',
        disabled: '@',
        mdSelectedItemChange: '&?',
        readonly: '=',
        additionalQueryParams: '=',
        warningParam: '@',
        noPaging: '@',
        url: '@',    // this allows to search from different controllers, not only AutocompleteController
        haController: "=?" // Array. Holds functions of this directive to be used outside as an object in array.
      },
      link: {
        post: function(scope, element, attrs) {
          if (angular.isUndefined(scope.haSearch)) {
            var url =  scope.url ? scope.url : '/autocomplete/' + scope.method;
            var lookup = QueryUtils.endpoint(url);
          }
          var controller = {};
          if (angular.isDefined(scope.haIgnored) && scope.haIgnored === "true") {
            scope.haSearch = [];
          }
          if (angular.isUndefined(scope.haSearch)) {
            scope.haSearch = function (text) {
              var deferred = $q.defer();
              var query = {
                lang: $translate.use().toUpperCase(),
                name: text
              };
              if(scope.additionalQueryParams) {
                  angular.extend(query, scope.additionalQueryParams);
              }

              if(url === '/autocomplete/curriculumversions') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(url === '/autocomplete/studentgroups') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(url === '/autocomplete/curriculumversionomodulesandthemes') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(url === '/autocomplete/enterprises') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(url === '/autocomplete/activeEnterprises') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(url === '/autocomplete/journals') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else if(scope.noPaging === 'true') {
                lookup.query(query, function (data) {
                  deferred.resolve(data);
                });
              } else {
                lookup.search(query, function (data) {
                  deferred.resolve(data.content);
                });
              }

              return deferred.promise;
            };
          }

          scope.isRequired = angular.isDefined(scope.required);
          element.attr('required', scope.isRequired);

          scope.isDisabled = angular.isDefined(scope.disabled);
          element.attr('disabled', scope.isDisabled);

          if (angular.isUndefined(scope.mdMinLength)) {
            scope.mdMinLength = 1;
          }

          function findValue(array, object, arrayKey) {
            var res = array.filter(function(row) {
              return arrayKey ? row[arrayKey] === object : row === object;
            });
            return res.length > 0 ? res[0] : null;
          }

          function findValues(array, objects, arrayKey) {
            return array.filter(function(row) {
              for (var i = 0; i < objects.length; i++) {
                if ((arrayKey && row[arrayKey] === objects[i]) || (angular.isUndefined(arrayKey) && row === objects[i])) {
                  return true;
                }
              }
              return false;
            });
          }

          /**
           * We need to cache some results to be able to put some value in ngHolder instead of attribute which is given.
           * Might be neccessary to put into watchers as well for ngModel value.
           */
          var cached = scope.haSearch();
          if (angular.isDefined(scope.haAttribute)) {
            if (angular.isObject(scope.ngModel) && scope.ngModel.hasOwnProperty(scope.haAttribute)) {
              scope.ngHolder = scope.ngModel;
            } else {
              if (cached && angular.isFunction(cached.then)) {
                cached.then(function(result) {
                  scope.ngHolder = angular.isDefined(scope.multiple) ? findValues(result, scope.ngHolder ? scope.ngHolder : scope.ngModel, scope.haAttribute)
                  : findValue(result, scope.ngHolder ? scope.ngHolder : scope.ngModel, scope.haAttribute);
                });
              } else if (cached && angular.isArray(cached)) {
                scope.ngHolder = angular.isDefined(scope.multiple) ? findValues(cached, scope.ngModel, scope.haAttribute) : findValue(cached, scope.ngModel, scope.haAttribute);
              } else {
                scope.ngHolder = scope.ngModel;
              }
            }
          } else {
            scope.ngHolder = scope.ngModel;
          }
          
          if (angular.isDefined(attrs.multiple) && !angular.isArray(scope.ngHolder)) {
            scope.ngHolder = [];
          }

          function equals(model, holder) {
            if (angular.isDefined(scope.multiple)) {
              if (angular.isDefined(scope.haAttribute)) {
                if (angular.isArray(model) && angular.isArray(holder)) {
                  if (model.length !== holder.length) {
                    return false;
                  } else {
                    return holder.filter(function (obj, idx) {
                      return obj[scope.haAttribute] !== model[idx];
                    }).length === 0;
                  }
                } else {
                  return model === holder;
                }
              } else {
                return angular.equals(model, holder);
              }
            } else {
              if (angular.isDefined(scope.haAttribute)) {
                if (angular.isDefined(model) && angular.isDefined(holder) && model !== null && holder !== null) {
                  return holder[scope.haAttribute] === model;
                } else {
                  return model === holder;
                }
              } else {
                return angular.equals(model, holder);
              }
            }
          }
          
          if (angular.isDefined(scope.multiple)) {
            scope.$watchCollection('ngHolder', function (newValue, oldValue) {
              if (newValue !== oldValue && !equals(scope.ngModel, newValue)) {
                if (angular.isDefined(scope.haAttribute)) {
                  scope.ngModel = [];
                  if (angular.isArray(newValue)) {
                    newValue.forEach(function (val) {
                      scope.ngModel.push(angular.isObject(val) ? val[scope.haAttribute] : val);
                    });
                  }
                } else {
                  scope.ngModel = newValue || [];
                }
              }
            });
            scope.$watchCollection('ngModel', function (newValue) {
              if (!equals(newValue, scope.ngHolder)) {
                if (newValue && newValue.length > 0) {
                  scope.ngHolder = newValue;
                } else {
                  scope.ngHolder = newValue || [];
                }
              }
            });
          } else {
            scope.$watch('ngHolder', function (newValue, oldValue) {
              if (newValue !== oldValue && !equals(scope.ngModel, newValue)) {
                if (angular.isDefined(scope.haAttribute)) {
                  scope.ngModel = newValue ? newValue[scope.haAttribute] : null;
                } else {
                  scope.ngModel = newValue;
                }
              }
            });
            scope.$watch('ngModel', function (newValue, oldValue) {
              if (newValue !== oldValue && !equals(newValue, scope.ngHolder)) {
                if (angular.isDefined(scope.haAttribute)) {
                  if (newValue === undefined || newValue === null) {
                    scope.ngHolder = newValue;
                  } else if (newValue.hasOwnProperty(scope.haAttribute)) {
                    scope.ngHolder = newValue;
                  }
                } else {
                  scope.ngHolder = newValue;
                }
              }
            });
          }

          controller.clear = function () {
            if (angular.isUndefined(scope.multiple)) {
              scope.ngHolder = null;
            } else {
              scope.ngHolder = [];
            }
            scope.ngSearchText = null;
          };

          if (angular.isDefined(scope.haController)) {
            if (angular.isArray(scope.haController)) {
              scope.haController.push(controller);
            } else {
              scope.haController = [controller];
            }
          }

          scope.transformChip = function(chip) {
            // If it is an object, it's already a known chip
            if (angular.isObject(chip)) {
              return chip;
            }
          };

          scope.getChipText = function (chip) {
            if (scope.display) {
              return chip[scope.display];
            }
            return $rootScope.currentLanguageNameField(chip);
          };

          scope.selectedItemChange = function() {
            if(angular.isFunction(scope.mdSelectedItemChange)) {
              if (angular.isFunction(scope.mdSelectedItemChange())) {
                scope.$$postDigest(scope.mdSelectedItemChange());
              } else {
                scope.$$postDigest(scope.mdSelectedItemChange);
              }
            }
          };
        }
      }
    };
  });
