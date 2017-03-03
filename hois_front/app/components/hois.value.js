'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisValue
 * @description
 * # hoisValue
 *
 * This element must be inside md-input-container for label to properly work
 */
angular.module('hitsaOis')
  .directive('hoisValue', function () {
    return {
      template:'<div class="md-body-1">{{value}}</div>',
      restrict: 'E',
      replace: true,
      scope: {
        value: '='
      },
      link: function postLink(scope, element) {
        element[0].parentElement.classList.add("md-input-has-value");
      }
    };
  });
