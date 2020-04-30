'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCollapsable
 * @description
 * # hoisCollapsable
 *
 * This element must be inside md-input-container for label to properly work
 *
 * For custom header use hois-collapsable-header element under hois-collapsable
 */
angular.module('hitsaOis')
  .directive('hoisCollapsable', function () {
    return {
      templateUrl: 'components/hois.collapsable.html',
      replace: true,
      transclude: {
        header: '?hoisCollapsableHeader'
      },
      scope: {
        label: '=',
        link: '=',
        action: '&',
        expandCollapseAction: '&',
        ngif: '&',
        expanded: '=?',
        headerBackgroundColor: '<',
        hideCollapseButton: '@' //used in student curriculum fulfillment
      },
      link: function postLink(scope) {
        scope.expanded = angular.isDefined(scope.expanded) ? scope.expanded : true;
        scope.expandCollapse = function() {
          if (angular.isFunction(scope.expandCollapseAction)) {
            scope.expandCollapseAction();
          }
          scope.expanded = !scope.expanded;
        };
      }
    };
  });
