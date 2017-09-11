'use strict';

angular.module('hitsaOis').directive('hoisIdcodeLookup', function ($q, QueryUtils, config, $http) {
  return {
    template: '<input type="text" ng-blur="lookupPerson()" md-minlength="11" md-maxlength="11">',
    restrict: 'E',
    replace: true,
    require: "ngModel",
    scope: {
      afterFailedLookup: '&',
      afterLookup: '&',
      noLookup: '&',
      ngModel: '=',
      ngReadonly: '=',
      role: '@',
      validateTeacher: '@'
    },
    link: function postLink(scope, elm, attrs, ctrl) {

      // role === forteacher as is not used as it will break teachers' search autocompletes
      if(angular.isDefined(scope.validateTeacher)) {
        ctrl.$asyncValidators.isTeacherUnique = function(modelValue, viewValue) {
            var uniqueQuery = {
              url: '/teachers/unique',
              paramValue: viewValue
            };
            return $http({
              url: config.apiUrl + uniqueQuery.url,
              method: "GET",
              params: uniqueQuery
            }).then(function(response) {
              ctrl.$setValidity('teacherIdCode', response.data);
              return response.data;
            });
        };
      }

      scope.lookupPerson = function() {
        var idcode = scope.ngModel;
        if(!scope.ngReadonly && idcode && idcode.length === 11 && idcode !== scope.idcode) {
          QueryUtils.endpoint('/autocomplete/persons').search({idcode: idcode, role: scope.role}).$promise.then(function(response) {
            scope.idcode = idcode;
            scope.afterLookup({response: response});
          }).catch(function(response) {
            if(response.status === 404) {
              // allow new lookup
              scope.idcode = undefined;
            }
            if(angular.isDefined(scope.afterFailedLookup)) {
              scope.afterFailedLookup({response: response});
            }
            return $q.reject(response);
          });
        } else if (angular.isDefined(scope.noLookup)) {
          scope.noLookup();
        }
      };
    }
  };
});
