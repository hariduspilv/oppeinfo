'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:oisMultiText
 * @description
 * # oisMultiText
 */
angular.module('hitsaOis')
  .directive('oisMultiText', function () {
    return {
      template:'<div layout="column">'+
        '<div layout="row" flex>'+
          '<div layout="row" flex><input type="text" ng-model="itemValue"></div>'+
          '<div layout="row"><a href="" ng-click="add()"><md-icon md-font-set="material-icons">playlist_add</md-icon></a></div>'+
        '</div>'+
        '<div layout="column">'+
            '<div layout="row" ng-repeat="item in items" flex>'+
              '<div layout="row" layout-align="start center" flex>{{item}}</div>'+
              '<div layout="row">'+
                '<md-button style="left:10px;" class="md-icon-button" ng-click="remove(item)">'+
                  '<md-icon ng-class="\'md-warn md-hue-2\'" md-font-set="material-icons">close</md-icon>'+
                '</md-button>'+
              '</div>'+
            '</div>'+
        '</div>'+
      '</div>',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {},
      link: function postLink(scope, element, attrs, ngModelControllers) {
        scope.items = [];

        scope.add = function() {
          if(!!scope.itemValue && scope.items.indexOf(scope.itemValue) === -1) {
            scope.items.push(scope.itemValue);
            scope.itemValue = '';
            ngModelControllers[0].$setViewValue(scope.items);
            ngModelControllers[0].$render();
          }
        };

        scope.remove = function(element) {
          scope.items.splice(scope.items.indexOf(element), 1);
          ngModelControllers[0].$setViewValue(scope.items);
          ngModelControllers[0].$render();
        };

      }
    };
  });
