'use strict';

angular.module('hitsaOis').directive('oisDrag', function () {
  return {
    restrict: 'A',
    link: function (scope, element, attr) {
      attr.$observe('oisDrag', function (draggable) {
        element.prop('draggable', draggable === "true");
      });
      element.on('dragstart', function (event) {
        if (event.target.attributes.getNamedItem('old-event-id') !== null) {
          event.dataTransfer.setData('oldEventId', event.target.attributes.getNamedItem('old-event-id').value);
        }
        if (event.target.attributes.getNamedItem('capacity-type') !== null) {
          event.dataTransfer.setData('capacityType', event.target.attributes.getNamedItem('capacity-type').value);
        }
        if (event.target.attributes.getNamedItem('journal-id') !== null) {
          event.dataTransfer.setData('journalId', event.target.attributes.getNamedItem('journal-id').value);
        }
        event.dataTransfer.setData('elementId', event.target.id);
      });
    }
  };
});

angular.module('hitsaOis').directive('oisDrop', function () {
  return {
    restrict: 'A',
    scope: {
      dropFn: '&',
      indexValue: '@',
    },
    link: function (scope, element, attrs) {
      element.on('dragover', function (event) {
        if (attrs.oisDrop === "true") {
          event.preventDefault();
        }
      });
      element.on('drop', function (event) {
        if (attrs.oisDrop === "true") {
          event.preventDefault();
          var data = {};
          data.id = event.dataTransfer.getData("elementId");
          data.journalId = event.dataTransfer.getData("journalId");
          data.oldEventId = event.dataTransfer.getData("oldEventId");
          data.capacityType = event.dataTransfer.getData("capacityType");
          data.index = scope.indexValue;
          event.target.appendChild(document.getElementById(data.id));
          scope.dropFn({
            data: data
          });
        }
      });
      element.on('dragenter', function () {
        if (attrs.oisDrop === "true") {
          this.classList.add("highlight-drop-area");
        }
      });
      element.on('dragleave', function () {
        if (attrs.oisDrop === "true") {
          this.classList.remove("highlight-drop-area");
        }
      });
      element.bind('error', function () {
        if (attrs.onErrorSrc === '') {
          attrs.ngHide = true;
        } else if (attrs.src !== attrs.onErrorSrc) {
          attrs.$set('src', attrs.onErrorSrc);
        }
      });
    }
  };
});

angular.module('hitsaOis').directive('oisHigherDrop', function () {
  return {
    restrict: 'A',
    scope: {
      dropFn: '&',
    },
    link: function (scope, element, attrs) {
      element.on('dragover', function (event) {
        if (attrs.oisHigherDrop === "true" && this.childElementCount === 0) {
          event.preventDefault();
        }
      });
      element.on('drop', function (event) {
        if (attrs.oisHigherDrop === "true") {
          event.preventDefault();
          var data = {};
          data.id = event.dataTransfer.getData("elementId");
          data.journalId = event.dataTransfer.getData("journalId");
          data.oldEventId = event.dataTransfer.getData("oldEventId");
          data.capacityType = event.dataTransfer.getData("capacityType");
          event.target.appendChild(document.getElementById(data.id).cloneNode(true));
          scope.dropFn({
            data: data
          });
        }
      });
      element.on('dragenter', function () {
        if (attrs.oisHigherDrop === "true") {
          this.classList.add("highlight-drop-area");
        }
      });
      element.on('dragleave', function () {
        if (attrs.oisHigherDrop === "true") {
          this.classList.remove("highlight-drop-area");
        }
      });
      element.bind('error', function () {
        if (attrs.onErrorSrc === '') {
          attrs.ngHide = true;
        } else if (attrs.src !== attrs.onErrorSrc) {
          attrs.$set('src', attrs.onErrorSrc);
        }
      });
    }
  };
});
