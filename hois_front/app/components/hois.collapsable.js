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
      template:'<div class="hois-collapse-parent"><div layout="row" class="hois-collapse" ng-style="{background: headerBackgroundColor}"><div flex layout="row" layout-align="start center"><div class="hois-collapse-header" flex ng-transclude="header">{{label}}&nbsp;<a class="md-primary" style="font-size: 13px; font-weight: normal;" ng-click="action()" ng-if="ngif()">{{link}}</a></div></div><div flex="15" flex-sm="20" flex-xs="30"><md-button ng-click="expandCollapse()" class="hois-collapse-button"><md-icon md-font-set="material-icons md-dark">{{expanded ? "keyboard_arrow_up" : "keyboard_arrow_down"}}</md-icon></md-button></div></div><div ng-show="expanded" ng-transclude></div></div>',
      replace: true,
      transclude: {
        header: '?hoisCollapsableHeader'
      },
      scope: {
        label: '=',
        link: '=',
        action: '&',
        ngif: '&',
        expanded: '<',
        headerBackgroundColor: '<'
      },
      link: function postLink(scope) {
        scope.expanded = angular.isDefined(scope.expanded) ? scope.expanded : true;
        scope.expandCollapse = function() {
          scope.expanded = !scope.expanded;
        };
      }
    };
  });
