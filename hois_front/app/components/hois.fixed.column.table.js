'use strict';

//based on https://technology.cap-hpi.com/blog/fixing-column-headers-using-angular/
angular.module('hitsaOis').directive('fixedColumnTable', function ($timeout) {
  return {
    restrict: 'A',
    scope: {
      fixedColumns: "@"
    },
    link: function (scope, element) {

      var container = element[0];

      function applyClasses(selector, newClass, cell) {
        var arrayItems = [].concat.apply([], container.querySelectorAll(selector));
        var currentElement;
        var colspan;
        var rowElement;

        arrayItems.forEach(function (row, i) {
          rowElement = angular.element(row);
          var numFixedColumns = scope.fixedColumns;
          for (var j = 0; j < numFixedColumns; j++) {
            currentElement = angular.element(row).find(cell)[j];
            if (currentElement !== undefined && (!rowElement.hasClass("fix") || rowElement.hasClass("fix") && currentElement.classList.contains("fix"))) {
              currentElement.classList.add(newClass);
              
              if (currentElement.hasAttribute('colspan')) {
                colspan = currentElement.getAttribute('colspan');
                numFixedColumns -= (parseInt(colspan) - 1);
              }
            }
          }
        });
      }

      function activate() {
        applyClasses('thead tr', 'cross', 'th');
        applyClasses('tbody tr', 'fixed-cell', 'td');

        container.addEventListener('scroll', function () {
          var x = container.scrollLeft;
          var y = container.scrollTop;

          var leftHeaders = [].concat.apply([], container.querySelectorAll('tbody td.fixed-cell'));
          var crossHeaders = [].concat.apply([], container.querySelectorAll('thead th.cross'));
          var topHeaders = [].concat.apply([], container.querySelectorAll('thead th'));

          //Update the left header positions when the container is scrolled
          leftHeaders.forEach(function (leftHeader) {
            leftHeader.style.transform = translate(x, 0);
          });


          //Update the top header positions when the container is scrolled
          topHeaders.forEach(function (topHeader) {
            topHeader.style.transform = translate(0, y);
          });

          //Update headers that are part of the header and the left column
          crossHeaders.forEach(function (crossHeader) {
            crossHeader.style.transform = translate(x, y);
          });

        });

        function translate(x, y) {
          return 'translate(' + x + 'px, ' + y + 'px)';
        }
      }

      $timeout(function () {
        activate();
      }, 0);

      scope.$on('refreshFixedColumns', function () {
        $timeout(function () {
          activate();
          container.scrollLeft = 0;
        }, 0);
      });
    }
  };

});
