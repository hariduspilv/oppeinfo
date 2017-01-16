'use strict';

angular.module('hitsaOis')
  .directive('oisInputFile', function () {
    return {
      template: '<input type="file" />',
      restrict: 'E',
      require: ['ngModel'],
      replace: true,
      scope: {},
      link: function postLink(scope, element, attrs, ngModelControllers) {
        var ngModelController = ngModelControllers[0];
        element.bind("change", function() {
          var file = element[0].files[0];
          var reader  = new FileReader();
          reader.addEventListener("load", function () {
            var token = 'base64,';
            file.data = reader.result.substring(reader.result.indexOf(token) + token.length);
            ngModelController.$setViewValue(file);
          }, false);
          reader.readAsDataURL(file);
        });
      }
    };
  });
