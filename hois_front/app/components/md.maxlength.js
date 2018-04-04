'use strict';

angular.module('hitsaOis')
  .directive('mdMaxlength', function () {
    return {
      restrict: 'A',
      require: ['ngModel', '^mdInputContainer'],
      link: {
        post: function(scope, element, attrs) {
          var originalSet = attrs.$set;
          attrs.$set = function (key, value, writeAttr, attrName) {
            if (key !== 'ngTrim') {
              originalSet.call(attrs, key, value, writeAttr, attrName);
            }
          };
        }
      }
    };
  });
