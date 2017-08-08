'use strict';

angular.module('hitsaOis').directive('oisDrag', function() {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      element.prop('draggable', true);
      element.on('dragstart', function(event) {
        event.dataTransfer.setData('text', event.target.id);
      });
    }
  };
});

angular.module('hitsaOis').directive('oisDrop', function() {
  return {
    restrict : 'A',
    scope : { dropFn: '&', param2: '&', index: '&' },
    link : function(scope, element, attrs) {
      element.on('dragover', function(event) {
        if(attrs.oisDrop === "true") {
          event.preventDefault();
        }
      });
      element.on('drop', function(event) {
        if(attrs.oisDrop === "true") {
          event.preventDefault();
          var data = event.dataTransfer.getData("text");
          event.target.appendChild(document.getElementById(data));
          scope.dropFn({param1: data, param2: data, index: data});
        }
      });
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
