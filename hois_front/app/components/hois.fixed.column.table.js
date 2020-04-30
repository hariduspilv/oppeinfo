'use strict';

//based on https://technology.cap-hpi.com/blog/fixing-column-headers-using-angular/
angular.module('hitsaOis').directive('fixedColumnTable', function ($timeout, $window) {
  return {
    restrict: 'A',
    scope: {
      fixedColumns: '@',
      resizeTable: '@',
      maxTableHeight: '@',
      searchCriteriaHeight: '@'
    },
    link: function (scope, element) {
      var container = element[0];

      if (angular.isDefined(scope.resizeTable)) {
        var observer = new MutationObserver(function () {
          resizeTableHeight();
        });
        observer.observe(container, {
          childList: true,
          subtree: true
        });

        var resizeTimer;
        angular.element($window).on('resize', function() {
          clearTimeout(resizeTimer);
          resizeTimer = setTimeout(resizeTableHeight, 500);
        });
      }

      function resizeTableHeight() {
        var searchCriteriaHeight = angular.isDefined(scope.searchCriteriaHeight) ? scope.searchCriteriaHeight : 0;

        var tableMaxHeight = 0;
        if (scope.maxTableHeight) {
          tableMaxHeight = scope.maxTableHeight - searchCriteriaHeight;
        } else {
          tableMaxHeight = $window.innerHeight - searchCriteriaHeight;
        }

        var table = container.querySelectorAll('table');
        var currentTableHeight = angular.isDefined(table[0]) ? table[0].offsetHeight : 0;

        var newTableHeight = currentTableHeight > 0 && currentTableHeight < tableMaxHeight ? currentTableHeight : tableMaxHeight;
        newTableHeight += 17; // scrollbar width
        element.css('height', newTableHeight + 'px');
      }

      function applyClasses(selector, newClass, cell) {
        var arrayItems = [].concat.apply([], container.querySelectorAll(selector));
        var currentElement;
        var colspan;
        var rowElement;

        arrayItems.forEach(function (row) {
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

        function updateHeaders() {
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
        }

        updateHeaders();

        function translate(x, y) {
          return 'translate(' + x + 'px, ' + y + 'px)';
        }

        container.addEventListener('scroll', function () {
          updateHeaders();
        });
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

      scope.$on('refreshFixedTableHeight', function () {
        $timeout(function () {
          resizeTableHeight();
        }, 0);
      });

    }
  };

});
