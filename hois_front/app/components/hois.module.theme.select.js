'use strict';

angular.module('hitsaOis').directive('hoisModuleThemeSelect', function ($mdSelect, $rootScope) {
  return {
    templateUrl: 'components/hois.module.theme.select.html',
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '=',
      values: '@',
      relatedModel: "="
    },
    link: function postLink(scope, element) {
      scope.options = [];
      scope.filteredOptions = [];

      // HACK: if first option is disabled then select doesn't close after clicking on an option
      // clickListener(mouseEvent) thinks first option was selected
      // is not broken if value is already selected before opening select
      if (!angular.isDefined(element.attr('multiple'))) {
        scope.$watch('ngModel', function () {
          $mdSelect.hide();
        });
      }

      if (angular.isDefined(scope.values)) {
        scope.$parent.$watchCollection(scope.values, function (values) {
          scope.options = sortOptions(values);
          filterOptions();
        });

        scope.$watch('relatedModel', function () {
          if (angular.isDefined(scope.relatedModel)) {
            filterOptions();
          }
        });
      }

      function filterOptions() {
        if (angular.isDefined(scope.relatedModel)) {
          // if related model is module then only modules can be selected
          // if related model is theme then only themes can be selected but modules are still shown
          scope.filteredOptions = angular.copy(scope.options).filter(function (it) {
            if (!scope.relatedModel.isModule) {
              it.disabled = it.isModule;
            }
            return !scope.relatedModel.isModule || (scope.relatedModel.isModule && it.isModule && !it.disabled);
          });

          // remove selected value if it should be not selected anymore
          if (angular.isDefined(scope.ngModel)) {
            var selectedFromOptions = scope.filteredOptions.find(function (it) {
              return it.moduleId === scope.ngModel.moduleId && it.themeId === scope.ngModel.themeId && !it.disabled;
            });
            if (!angular.isDefined(selectedFromOptions)) {
              scope.ngModel = null;
            }
          }
        } else {
          scope.filteredOptions = scope.options;
        }
      }

      var sortOptionsListener = $rootScope.$on('$translateChangeSuccess', function () {
        scope.options = sortOptions(scope.options);
      });
      scope.$on('$destroy', sortOptionsListener);

      function sortOptions(options) {
        return (options || []).sort(function (o1, o2) {
          var o1Theme = o1.themeId !== null ? $rootScope.currentLanguageNameField(o1.theme) : '';
          var o2Theme = o2.themeId !== null ? $rootScope.currentLanguageNameField(o2.theme) : '';
          return $rootScope.currentLanguageNameField(o1.module).hoisLocaleCompare($rootScope.currentLanguageNameField(o2.module)) ||
            o1.moduleId - o2.moduleId || o1Theme.hoisLocaleCompare(o2Theme);
        });
      }
    }
  };
});
