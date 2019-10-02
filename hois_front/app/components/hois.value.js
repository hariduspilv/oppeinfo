'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisValue
 * @description
 * # hoisValue
 *
 * This element must be inside md-input-container for label to properly work
 * 
 * @since 10.09.2019
 * For element in array after split "" (empty string) it will generate div, but nothing will be shown
 * as div usually needs at least a non-breaking space. Because of this it will add &nbsp; ("space").
 */
angular.module('hitsaOis')
  .directive('hoisValue', function () {
    return {
      template:'<div class="md-body-1 hois-value">' +
      '<a ng-if="hrefValue" ng-href="{{hrefValue}}">{{value}}</a>' +
      '<span ng-if="!hrefValue">' +
        '<div ng-if="value.split" ng-repeat="row in value.split(\'\n\') track by $index">{{row.length > 0 ? row : \'&nbsp;\'}}</div>' +
        '<div ng-if="!value.split">{{value}}</div>' +
      '</span>' +
      '</div>',
      restrict: 'E',
      replace: true,
      scope: {
        value: '=',
        hrefValue: '='
      },
      link: function postLink(scope, element) {
        element[0].parentElement.classList.add("md-input-has-value");
      }
    };
  });
