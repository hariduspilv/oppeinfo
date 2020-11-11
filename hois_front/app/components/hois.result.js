'use strict';

angular.module('hitsaOis').directive('hoisVocationalResult', function (VocationalGradeUtil) {
  return {
    template:
      '<span>' +
        '<span ng-class="{badResult: !ignoreStyles && !gradeUtil.isPositive(value), ' +
          'invalidGradingSchema: !ignoreStyles && (gradeUtil.isPositive(value) && existsGradingSchema && !value.gradingSchemaRowId)}">' +
          '{{value | hoisVocationalGrade}}' +
        '</span>' +
        '<span ng-if="previousResult">&nbsp;*</span>' +
      '</span>',
    restrict: 'E',
    replace: true,
    scope: {
      value: '=',
      existsGradingSchema: '=',
      ignoreStyles: '=',
      previousResult: '='
    },
    link: function postLink(scope) {
      scope.gradeUtil = VocationalGradeUtil;
    }
  };
}).directive('hoisJournalResult', function () {
    return {
      template:
        '<div layout="row" layout-align="none center" ng-class="{journalFinalResult: !ignoreStyles && finalResult}">' +
          '<span ng-if="!value.grade && !value.verbalGrade">-</span>' +
          '<hois-vocational-result ng-if="value.grade" value="value.grade" ignoreStyles="ignoreStyles" ' +
            'exists-grading-schema="existsGradingSchema"></hois-vocational-result><span ng-if="value.grade && value.verbalGrade">,&nbsp;</span>' +
          '<hois-journal-verbal-result value="value" limit="limit"></hois-journal-verbal-result>' +
          '<span ng-if="previousResult">&nbsp;*</span>' +
        '</div>',
      restrict: 'E',
      replace: true,
      scope: {
        value: '=',
        existsGradingSchema: '=',
        finalResult: '=',
        ignoreStyles: '=',
        previousResult: '=',
        limit: '='
      },
      link: function postLink() {}
    };
}).directive('hoisJournalVerbalResult', function () {
  return {
    template:
      '<span layout="row" layout-align="space-between center" ng-style="{\'min-width\': value.verbalGrade.length > limitTo ? \'160px\' : \'auto\'}">' +
        '<span>{{value.verbalGrade | hoisLimitTo: limitTo:!value.showVerbalGrade}}</span>' +
        '<md-icon ng-if="!value.showVerbalGrade && value.verbalGrade.length > limitTo"' +
          'ng-click="value.showVerbalGrade = true" md-font-set="material-icons">expand_more</md-icon>' +
        '<md-icon ng-if="value.showVerbalGrade" ng-click="value.showVerbalGrade = false"' +
          'md-font-set="material-icons">expand_less</md-icon>' +
      '</span>',
    restrict: 'E',
    replace: true,
    scope: {
      value: '=',
      finalResult: '=',
      limit: '='
    },
    link: function postLink(scope) {
      scope.limitTo = angular.isDefined(scope.limit) ? scope.limit : 20;
    }
  };
}).directive('hoisHigherResult', function (HigherGradeUtil) {
  return {
    template:
      '<span>' +
        '<span ng-class="{badResult: !ignoreStyles && !gradeUtil.isPositive(value), ' +
          'invalidGradingSchema: !ignoreStyles && (gradeUtil.isPositive(value) && existsGradingSchema && !value.gradingSchemaRowId)}">' +
          '{{value | hoisHigherGrade: letterGrades}}' +
        '</span>' +
        '<span ng-if="previousResult">&nbsp;*</span>' +
      '</span>',
    restrict: 'E',
    replace: true,
    scope: {
      value: '=',
      existsGradingSchema: '=',
      letterGrades: "=",
      ignoreStyles: '=',
      previousResult: '='
    },
    link: function postLink(scope) {
      scope.gradeUtil = HigherGradeUtil;
    }
  };
});
