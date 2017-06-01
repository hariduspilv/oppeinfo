'use strict';

/**
 * This directive is meant for lf-ng-md-file-input
 */
angular.module('hitsaOis')
  .directive('disallowSameFile', function() {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, element, attrs, ngModelController) {
        var addedFiles = scope[attrs.disallowSameFile];
        scope.$watchCollection(attrs.lfFiles, function(selectedFiles) {
          if (angular.isArray(addedFiles) && angular.isArray(selectedFiles)) {
            var fileAlreadyAdded = false;
            addedFiles.forEach(function(addedFile) {
              if (angular.isObject(addedFile.oisFile)) {
                var fileName = addedFile.oisFile.fname;
                selectedFiles.forEach(function(selectedFile) {
                  if(selectedFile.lfFileName === fileName) {
                    fileAlreadyAdded = true;
                  }
                });
              }
            });
            ngModelController.$setValidity("samefile", !fileAlreadyAdded);
          }
        });
      }
    };
  });
