'use strict';

angular.module('hitsaOis').directive('unique', function(config, $http) {
  return {
    restrict: 'A',
    scope: {
      unique: '='
    },
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$asyncValidators.unique = function(modelValue, viewValue) {
        scope.unique.paramValue = viewValue;
        return $http({
          url: config.apiUrl + scope.unique.url,
          method: "GET",
          params: scope.unique
        }).then(function(response) {
          ctrl.$setValidity('notUnique', response.data);
          return response.data;
        });
      };
    }
  };
});
