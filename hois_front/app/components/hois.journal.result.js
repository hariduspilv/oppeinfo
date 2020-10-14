'use strict';

angular.module('hitsaOis').directive('hoisJournalResult', function (VocationalGradeUtil) {
    return {
      template:
        '<div layout="row" layout-align="space-between center" ng-class="{journalFinalResult: finalResult}">' +
          '<span ng-if="value.grade">' +
            '<span ng-class="{badResult: !gradeUtil.isPositive(value.grade)}">{{value.grade | hoisVocationalGrade}}</span><span ng-if="value.verbalGrade">,&nbsp;</span>' +
          '</span>' +
          '<hois-journal-verbal-result value="value" limit="limit"></hois-journal-verbal-result>' +
        '</div>',
      restrict: 'E',
      replace: true,
      scope: {
        value: '=',
        finalResult: '=',
        limit: '='
      },
      link: function postLink(scope) {
        scope.gradeUtil = VocationalGradeUtil;
      }
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
});
