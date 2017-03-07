'use strict';

angular.module('hitsaOis')
  .directive('hoisAutocomplete', function ($rootScope, $translate, $q, QueryUtils) {

    return {
      templateUrl: function (elem, attr) {
        if (attr.multiple) {
          return 'components/hois.chip.autocomplete.html';
        } else {
          return 'components/hois.autocomplete.html';
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
        required: '=', // todo add chip visuals
        mdSelectedItemChange: '&?'
      },
      link: {
        post: function(scope, element, attrs) {
          var lookup = QueryUtils.endpoint('/autocomplete/'+scope.method);

          if (attrs.multiple && !angular.isArray(scope.ngModel)) {
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
              scope.$$postDigest(scope.mdSelectedItemChange);
            }
          };

          scope.search = function (text) {
            var deferred = $q.defer();
            lookup.search({
              lang: $translate.use().toUpperCase(),
              name: text
            }, function (data) {
              deferred.$$resolve(data.content);
            });
            return deferred.promise;

          };
        }
      }
    };
  });
