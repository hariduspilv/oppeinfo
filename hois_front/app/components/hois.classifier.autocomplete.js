'use strict';

angular.module('hitsaOis')
  .directive('hoisClassifierAutocomplete', function ($rootScope, $translate, $q, Classifier) {

    return {
      templateUrl: 'components/hois.chip.autocomplete.html',
      restrict: 'E',
      require: 'ngModel',
      scope: {
        maxChips: '@',
        ngModel: '=',
        label: '@',
        mainClassCode: '@'
      },
      link: function postLink(scope) {
        if (!angular.isArray(scope.ngModel)) {
          scope.ngModel = [];
        }
        // TODO when classifier cache is done try to remove($digest() iterations reached)
        var cache = {};

        scope.transformChip = function(chip) {
          // If it is an object, it's already a known chip
          if (angular.isObject(chip)) {
            cache[chip.code] = chip;
            return chip.code;
          }
        };

        scope.getChipText = function (chip) {
          return $rootScope.currentLanguageNameField(cache[chip]);
        };

        scope.search = function (text) {
          var deferred = $q.defer();
          var fieldName = $rootScope.currentLanguageNameVariable();
          var callback = function (data) {
            var filteredContent = data.filter(function (item) {
              return item[fieldName].includes(text);
            });
            deferred.resolve(filteredContent);
          };

          Classifier.queryForDropdown({mainClassCode: scope.mainClassCode, sort: fieldName}, callback);
          return deferred.promise;

        };
      }
    };
  });
