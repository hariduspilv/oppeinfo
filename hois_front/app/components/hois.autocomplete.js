'use strict';

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
        label: '@',
        method: '@',
        multiple: '@',
        ngRequired: '=',
        required: '@', // todo add chip visuals
        ngDisabled: '=',
        disabled: '@',
        mdSelectedItemChange: '&?',
        readonly: '=',
        additionalQueryParams: '=',
        noPaging: '@',
        url: '@'    // this allows to search from different controllers, not only AutocompleteController
      },
      link: {
        post: function(scope, element, attrs) {
          var url =  scope.url ? scope.url : '/autocomplete/' + scope.method;
          var lookup = QueryUtils.endpoint(url);

          scope.isRequired = angular.isDefined(scope.required);
          element.attr('required', scope.isRequired);

          scope.isDisabled = angular.isDefined(scope.disabled);
          element.attr('disabled', scope.isDisabled);

          if (angular.isDefined(attrs.multiple) && !angular.isArray(scope.ngModel)) {
            scope.ngModel = [];
          }

          scope.transformChip = function(chip) {
            // If it is an object, it's already a known chip
            if (angular.isObject(chip)) {
              return chip;
            }
          };

          scope.getChipText = function (chip) {
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

          scope.search = function (text) {
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
      }
    };
  });
