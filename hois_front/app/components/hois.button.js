'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisButton
 * @description
 * # hoisButton
 * 
 */
angular.module('hitsaOis').directive('hoisButton', function (QueryUtils, $q) {
  return {
    template: '<md-button class="{{hbClass}}" ng-click="click()">{{hbLabel}}</md-button>',
    restrict: 'E',
    scope: {
      hbClick: "&?",
      hbLabel: "@",
      hbClass: "@",
      hbLoading: "@",
      hbPromise: "@"
    },
    link: function postLink(scope, el, attr) {
      if (angular.isUndefined(scope.hbClass)) { // Set class from hoisButton to mdButton
        scope.hbClass = attr.class;
      }
      if (angular.isDefined(scope.hbLoading) && angular.isDefined(scope.hbClick)) {
        scope.click = function () {
          QueryUtils.loadingWheel(scope, true);
          var deferred = $q.defer();
          var promise = scope.hbClick();
          if (angular.isDefined(scope.hbPromise)) {
            promise.then(function(r) { deferred.resolve(r); }).catch(function(r) { deferred.resolve(r); });
          } else {
            deferred.resolve();
          }
          deferred.promise.then(function() {
            QueryUtils.loadingWheel(scope, false);
          });
        };
      } else {
        scope.click = scope.hbClick;
      }
    }
  };
});
