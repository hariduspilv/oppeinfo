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
      template:'<div><div layout="row" class="hois-collapse"><div flex layout="row" layout-align="start center"><div class="hois-collapse-header" flex="90" ng-transclude="header">{{label}}</div></div><div flex="10"><md-button ng-click="expandCollapse()" class="hois-collapse-button"><md-icon md-font-set="material-icons md-dark">{{expanded ? "keyboard_arrow_up" : "keyboard_arrow_down"}}</md-icon></md-button></div></div><div ng-show="expanded" ng-transclude></div></div>',
      restrict: 'E',
      replace: true,
      transclude: {
        header: '?hoisCollapsableHeader'
      },
      scope: {
        label: '=',
        expanded: '=?'
      },
      link: function postLink(scope) {
        scope.expanded = angular.isDefined(scope.expanded) ? scope.expanded : true;
        scope.expandCollapse = function() {
          scope.expanded = !scope.expanded;
        };
      }
    };
  });
