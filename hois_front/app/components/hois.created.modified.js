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
      templateUrl: 'components/hois.created.modified.html'
    };
  });
