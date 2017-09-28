'use strict';

angular.module('hitsaOis').directive('hoisIdcodeLookup', function ($q, QueryUtils, message) {
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
      role: '@'
    },
    link: function postLink(scope) {

      scope.lookupPerson = function() {
        var idcode = scope.ngModel;
        if(!scope.ngReadonly && idcode && idcode.length === 11 && idcode !== scope.idcode) {
          QueryUtils.endpoint('/autocomplete/persons', {search: {method: 'GET'}}).search({idcode: idcode, role: scope.role}).$promise.then(function(response) {
            scope.idcode = idcode;
            scope.afterLookup({response: response});
          }).catch(function(response) {
            if(response.status === 404) {
              // allow new lookup
              scope.idcode = undefined;
            }
            if(response.status === 412) {
              // allow new lookup
              scope.idcode = undefined;
              message.error('main.messages.error.estonianIdcode');
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
