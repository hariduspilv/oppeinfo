'use strict';

/**
 * @ngdoc directive
 * @name hitsaOis.directive:hoisCreatedModified
 * @description
 * # hoisCreatedModified
 */
angular.module('hitsaOis')
  .directive('hoisCreatedModified', function () {
    return {
      restrict: 'E',
      scope: {
        object: '='
      },
      templateUrl: 'views/templates/hois.created.modified.html'
    };
  });
