'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisFreeForms
 * @description
 * # hoisFreeForms
 */
angular.module('hitsaOis')
  .directive('hoisFreeForms', function () {
    return {
      template: '<span class="middle-text label">' +
        '<span ng-if="value && value.length > 0">{{value.join(", ")}}</span>' +
        '<span class="invalid" ng-if="!(value && value.length > 0)">{{"document.noFreeForms" | translate}}</span>' +
      '</span>',
      restrict: 'E',
      replace: true,
      scope: {
        value: '='
      }
    };
  });
