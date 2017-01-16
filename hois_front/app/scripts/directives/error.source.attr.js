'use strict';

angular.module('hitsaOis')
  .directive('onErrorSrc', function() {
    return {
      restrict: 'A',
      scope: {},
      link: function(scope, element, attrs) {
        element.bind('error', function() {
          if (attrs.onErrorSrc === '') {
            attrs.ngHide = true;
          } else if (attrs.src !== attrs.onErrorSrc) {
            attrs.$set('src', attrs.onErrorSrc);
          }
        });
      }
    };
  });
