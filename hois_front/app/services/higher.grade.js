'use strict';

angular.module('hitsaOis').constant('HigherGrade', {
  'KORGHINDAMINE_5': 'KORGHINDAMINE_5',
  'KORGHINDAMINE_4': 'KORGHINDAMINE_4',
  'KORGHINDAMINE_3': 'KORGHINDAMINE_3',
  'KORGHINDAMINE_2': 'KORGHINDAMINE_2',
  'KORGHINDAMINE_1': 'KORGHINDAMINE_1',
  'KORGHINDAMINE_0': 'KORGHINDAMINE_0',
  'KORGHINDAMINE_A': 'KORGHINDAMINE_A',
  'KORGHINDAMINE_M': 'KORGHINDAMINE_M',
  'KORGHINDAMINE_MI': 'KORGHINDAMINE_MI'
}).factory('HigherGradeUtil', function (HigherGrade, ArrayUtils) {

  var POSITIVE_GRADES = [HigherGrade.KORGHINDAMINE_1, HigherGrade.KORGHINDAMINE_2, HigherGrade.KORGHINDAMINE_3,
    HigherGrade.KORGHINDAMINE_4, HigherGrade.KORGHINDAMINE_5, HigherGrade.KORGHINDAMINE_A];

  var DISTINCTIVE_GRADES = [HigherGrade.KORGHINDAMINE_0, HigherGrade.KORGHINDAMINE_1, HigherGrade.KORGHINDAMINE_2, 
    HigherGrade.KORGHINDAMINE_3, HigherGrade.KORGHINDAMINE_4, HigherGrade.KORGHINDAMINE_5];

  return {
    isPositive: function(gradeCode) {
      return ArrayUtils.includes(POSITIVE_GRADES, gradeCode);
    },
    isDistinctive: function(gradeCode) {
      return ArrayUtils.includes(DISTINCTIVE_GRADES, gradeCode);
    },
  };
});