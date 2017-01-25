'use strict';

angular.module('hitsaOis')
  .directive('hoisAutocomplete', function () {

    return {
      templateUrl: 'views/templates/hois.autocomplete.html',
      restrict: 'E',
      require: 'ngModel',
      scope: {
        ngModel: '=',
        label: '@',
        method: '&'
      },
      link: function postLink(scope) {
        if (!angular.isDefined(scope.ngModel)) {
          scope.ngModel = [];
        }

        scope.transformChip = function(chip) {
          // If it is an object, it's already a known chip
          if (angular.isObject(chip)) {
            return chip;
          }
        };

        scope.search = function (text) {
          return scope.method({arg: text});
        };
      }
    };
  });
