'use strict';

angular.module('hitsaOis').directive('hoisGradeSelect', function ($route, DataUtils) {
  return {
    templateUrl: 'components/hois.grade.select.html',
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '=',
      ngRequired: '=',
      required: '@',
      values: '@'
    },
    link: function postLink(scope, element) {
      scope.auth = $route.current.locals.auth;
      scope.isRequired = angular.isDefined(scope.required);
      element.attr('required', scope.isRequired);

      scope.grades = [];
      scope.filteredGrades = [];

      function doFilter() {
        scope.filteredGrades = (scope.grades || []).filter(function (it) {
          return it.valid || (scope.ngModel !== null && DataUtils.isSameGrade(it, scope.ngModel));
        });
      }

      function afterLoad(result) {
        scope.grades = result.content;
        doFilter();
      }

      function loadValues() {
        if (angular.isDefined(scope.values)) {
          scope.$parent.$watchCollection(scope.values, function (values) {
            afterLoad({content: values});
          });
        }
      }

      loadValues();
    }
  };
});
