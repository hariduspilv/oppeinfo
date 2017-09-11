'use strict';

angular.module('hitsaOis').directive('oisDrag', function() {
  return {
    restrict: 'A',
    link: function(scope, element) {
      element.prop('draggable', true);
      element.on('dragstart', function(event) {
        if(event.target.attributes.getNamedItem('old-event-id') !== null) {
          event.dataTransfer.setData('oldEventId', event.target.attributes.getNamedItem('old-event-id').value);
        }
        if(event.target.attributes.getNamedItem('capacity-type') !== null) {
          event.dataTransfer.setData('capacityType', event.target.attributes.getNamedItem('capacity-type').value);
        }
        event.dataTransfer.setData('journalId', event.target.attributes.getNamedItem('journal-id').value);
        event.dataTransfer.setData('elementId', event.target.id);
      });
    }
  };
});

angular.module('hitsaOis').directive('oisDrop', function() {
  return {
    restrict : 'A',
    scope : { dropFn: '&', param2: '&', index: '&', oldEventId: '&' },
    link : function(scope, element, attrs) {
      element.on('dragover', function(event) {
        if(attrs.oisDrop === "true" && this.childElementCount === 0) {
          event.preventDefault();
        }
      });
      element.on('drop', function(event) {
        if(attrs.oisDrop === "true") {  
          event.preventDefault();
          var data = {};
          data.id = event.dataTransfer.getData("elementId");
          data.journalId = event.dataTransfer.getData("journalId");
          data.oldEventId = event.dataTransfer.getData("oldEventId");
          data.capacityType = event.dataTransfer.getData("capacityType");
          data.index = event.target.attributes.getNamedItem('index-value').value;
          event.target.appendChild(document.getElementById(data.id));
          scope.dropFn({data: data});
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
