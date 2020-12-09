'use strict';

angular.module('hitsaOis').directive('hoisMockPagination', function ($timeout, ArrayUtils) {
  return {
    templateUrl: 'components/hois.mock.pagination.html',
    restrict: 'E',
    replace: true,
    transclude: {
      table: '?hoisMockPaginationTable'
    },
    scope: {
      tableData: '=',
      searchLabel:'@?',
      searchParam: '@?',
      limitOptions: '=?'
    },
    link: function postLink(scope) {
      if (!scope.tableData.page) {
        scope.tableData.page = 1;
      }
      if (!scope.tableData.size) {
        scope.tableData.size = 20;
      }

      scope.tableData.filteredContent = scope.tableData.content;

      scope.search = function() {
        if (scope.tableData.searchValue !== undefined) {
          scope.tableData.filteredContent = scope.tableData.content.filter(function (it) {
            var value = scope.searchParam.split('.').reduce(function(a, b) {
              return a[b];
            }, it);
            return value.toLowerCase().indexOf(scope.tableData.searchValue.toLowerCase()) !== -1;
          });
        } else {
          scope.tableData.filteredContent = scope.tableData.content;
        }
        scope.paginate();
      };

      scope.paginate = function() {
        scope.tableData.shownContent = scope.tableData.filteredContent.slice((scope.tableData.page - 1) * scope.tableData.size, scope.tableData.page * scope.tableData.size);
      };

      scope.tableData.remove = function (row) {
        if (angular.isDefined(scope.tableData.form)) {
          scope.tableData.form.$setDirty();
        }
        ArrayUtils.remove(scope.tableData.content, row);
        scope.search();
      };

      scope.paginate();
    }
  };
});
