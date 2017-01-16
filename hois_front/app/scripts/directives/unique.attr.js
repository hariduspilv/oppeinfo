'use strict';

angular.module('hitsaOis').directive('unique', function(config, $http) {
  return {
    restrict: 'A',
    scope: {},
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {

      ctrl.$asyncValidators.unique = function(modelValue, viewValue) {

        // console.log("attrs.unique");
        // console.log(attrs.unique);
        var data = JSON.parse(attrs.unique);
        data.paramValue = viewValue;
        return $http({
          url: config.apiUrl + data.url,
          method: "GET",
          params: data
        }).then(function(response) {
          ctrl.$setValidity('notUnique', response.data);
          return response.data;
        });
      };
    }
  };
});
