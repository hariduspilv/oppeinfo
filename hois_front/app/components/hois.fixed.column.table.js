'use strict';

//based on https://technology.cap-hpi.com/blog/fixing-column-headers-using-angular/
angular.module('hitsaOis').directive('fixedColumnTable', function ($timeout, $window) {
  return {
    restrict: 'A',
    scope: {
      fixedColumns: '@',
      resizeTable: '@',
      maxTableHeight: '@',
      searchCriteriaHeight: '@',
      tableClass: '@'
    },
    link: function (scope, element) {
      var SCROLLBAR_WIDTH = 17;
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
        newTableHeight += SCROLLBAR_WIDTH;
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

      var _init = false;
      function activate() {
        var tableClassQuery = angular.isDefined(scope.tableClass) ? 'table.' + scope.tableClass + ' > ' : '';
        applyClasses(tableClassQuery + 'thead > tr', 'cross', 'th');
        applyClasses(tableClassQuery + 'tbody > tr', 'fixed-cell', 'td');
        applyClasses(tableClassQuery + 'tfoot > tr', 'fixed-cell', 'td');

        function updateHeaders() {
          var x = container.scrollLeft;
          var y = container.scrollTop;

          var leftHeaders = [].concat.apply([], container.querySelectorAll(tableClassQuery + 'tbody > tr td.fixed-cell'));
          var crossHeaders = [].concat.apply([], container.querySelectorAll(tableClassQuery + 'thead > tr th.cross'));
          var topHeaders = [].concat.apply([], container.querySelectorAll(tableClassQuery + 'thead > tr th'));
          var footerCrossColumns = [].concat.apply([], container.querySelectorAll(tableClassQuery + 'tfoot > tr td.cross'));
          var footerColumns = [].concat.apply([], container.querySelectorAll(tableClassQuery + 'tfoot > tr td'));

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

          var table = container.querySelectorAll('table');
          var currentTableHeight = angular.isDefined(table[0]) ? table[0].offsetHeight : 0;
          var footerY = y - currentTableHeight + container.offsetHeight - SCROLLBAR_WIDTH;

          //Update the footer column positions when the container is scrolled
          footerColumns.forEach(function (footerColumn) {
            footerColumn.style.transform = translate(0, footerY);
          });

          //Update columns that are part of the footer and the left column
          footerCrossColumns.forEach(function (crossColumn) {
            crossColumn.style.transform = translate(x, footerY);
          });
        }

        updateHeaders();

        function translate(x, y) {
          return 'translate(' + x + 'px, ' + y + 'px)';
        }

        if (!_init) {
          _init = true;
          container.addEventListener('scroll', function () {
            updateHeaders();
          });
          // possible increase in performance if we try to use requestAnimationFrame with `ticking` and passive listener option

          // var supportsPassive = false;
          // try {
          //   var opts = Object.defineProperty({}, 'passive', {
          //     get: function() {
          //       supportsPassive = true;
          //     }
          //   });
          //   window.addEventListener("testPassive", null, opts);
          //   window.removeEventListener("testPassive", null, opts);
          // } catch (e) {}
          //
          // var ticking = false;
          // container.addEventListener('scroll', function () {
          //   if (!ticking) {
          //     window.requestAnimationFrame(function () {
          //       updateHeaders();
          //       ticking = false;
          //     })
          //     ticking = true;
          //   }
          // }, supportsPassive ? { passive: true } : false);
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

      scope.$on('refreshFixedTableHeight', function () {
        $timeout(function () {
          resizeTableHeight();
        }, 0);
      });

    }
  };

});
