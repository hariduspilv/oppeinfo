'use strict';

angular.module('hitsaOis').directive('hoisIdcodeLookup', function ($q, QueryUtils, message) {
  return {
    template: '<input type="text" ng-blur="lookupPerson()" ng-minlength="11" md-maxlength="11">',
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
    link: function postLink(scope, element, attrs, ctrl) {

      function validFormat() {
        ctrl.$setValidity('estonianIdcode', true);
      }
      function invalidFormat() {
        ctrl.$setValidity('estonianIdcode', false);
        message.error('main.messages.error.estonianIdcode');
      }

      scope.lookupPerson = function() {
        var idcode = scope.ngModel;
        if(!scope.ngReadonly && idcode && idcode !== scope.idcode) {
          if (idcode.length === 11) {
            QueryUtils.endpoint('/autocomplete/persons', {search: {method: 'GET'}}).search({idcode: idcode, role: scope.role}).$promise.then(function(response) {
              validFormat();
              scope.idcode = idcode;
              scope.afterLookup({response: response});
            }).catch(function(response) {
              if(response.status === 404) {
                validFormat();
                // allow new lookup
                scope.idcode = undefined;
              }
              if(response.status === 412) {
                invalidFormat();
                // allow new lookup
                scope.idcode = undefined;
              }
              if(angular.isDefined(scope.afterFailedLookup)) {
                scope.afterFailedLookup({response: response});
              }
              return $q.reject(response);
            });
          } else {
            invalidFormat();
          }
        } else if (angular.isDefined(scope.noLookup)) {
          scope.noLookup();
        }
      };
    }
  };
});
