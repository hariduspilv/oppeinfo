(function() {
  'use strict';

  /**
   * @ngdoc directive
   * @name hitsaOis.directive:hoisOptimizedClassifierSelect
   * @description
   * # hoisOptimizedClassifierSelect
   */
  angular.module('hitsaOis')
    .directive('hoisOptimizedClassifierSelect', HoisOptimizedClassifierSelect);

  HoisOptimizedClassifierSelect.$inject = ['Classifier', 'hoisValidDatesFilter'];
  function HoisOptimizedClassifierSelect(Classifier, hoisValidDatesFilter) {
    return {
      templateUrl: 'components/hois.optimized.classifier.select.html',
      restrict: 'E',
      require: ['ngModel', '?ngRequired'],
      replace: true,
      scope: {
        criteria: '=',
        hocsRequired: '=ngRequired',
        ignorePreselected: '@',
        mainClassifierCode: '@',
        onlyValid: '@',
        value: "=ngModel",
      },
      link: {
        pre: preLink,
        post: postLink
      }
    };

    function preLink(scope) {
      var params = { order: scope.order ? scope.order : scope.$root.currentLanguageNameField()};

      if (angular.isDefined(scope.mainClassifierCode)) {
        params.mainClassCode = scope.mainClassifierCode;
      }  else if(angular.isString(scope.mainClassifierCodes)) {
        params.mainClassCodes = scope.mainClassifierCodes.replace(/\s/g, '');
      } else {
        // do not make query if main classifier code is undefined
        return;
      }

      if (angular.isObject(scope.criteria)) {
        angular.extend(params, scope.criteria);
      }

      // scope.optionsByCode = {};
      Classifier.queryForDropdown(params, function(arrayResult) {
        scope.options = [];
        arrayResult.forEach(function (classifier) {
          var option = {code: classifier.code, nameEt: classifier.nameEt, nameEn: classifier.nameEn, labelRu: classifier.labelRu,
            validFrom: classifier.validFrom, validThru: classifier.validThru, hide: false};
          if (angular.isDefined(scope.onlyValid)) {
            option.hide = !Classifier.isValid(option);
          }
          scope.options.push(option);
          // scope.optionsByCode[option.code] = option;
        });
        checkSelectedValue(scope.value);
      });

      scope.$watch('value', checkSelectedValue);

      function checkSelectedValue(value) {
        if (!value || !angular.isArray(scope.options)) {
          return;
        }
        var found = scope.options.find(function (opt) {
          return opt.code === value;
        });
        if (!found) {
          scope.value = null;
          return;
        }
        if (found.hide && !angular.isDefined(scope.ignorePreselected)) {
          scope.value = null;
        }
      }
    }

    function postLink(scope) {
      scope.show = function (option) {
        if (angular.isDefined(scope.ignorePreselected) && scope.value === option.code) {
          return true;
        }
        return !option.hide;
      };
      scope.currentLanguage = scope.$root.currentLanguage;
      scope.currentLanguageNameField = angular.isDefined(scope.onlyValid) ? hoisValidDatesFilter : scope.$root.currentLanguageNameField;
    }
  }
})();
