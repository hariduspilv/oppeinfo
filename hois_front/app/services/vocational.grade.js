'use strict';

angular.module('hitsaOis').constant('VocationalGrade', {
  'KUTSEHINDAMINE_5': 'KUTSEHINDAMINE_5',
  'KUTSEHINDAMINE_4': 'KUTSEHINDAMINE_4',
  'KUTSEHINDAMINE_3': 'KUTSEHINDAMINE_3',
  'KUTSEHINDAMINE_2': 'KUTSEHINDAMINE_2',
  'KUTSEHINDAMINE_1': 'KUTSEHINDAMINE_1',
  'KUTSEHINDAMINE_A': 'KUTSEHINDAMINE_A',
  'KUTSEHINDAMINE_MA': 'KUTSEHINDAMINE_MA',
  'KUTSEHINDAMINE_X': 'KUTSEHINDAMINE_X'
}).factory('VocationalGradeUtil', function (VocationalGrade, ArrayUtils) {

  var POSITIVE_GRADES = [VocationalGrade.KUTSEHINDAMINE_A,
    VocationalGrade.KUTSEHINDAMINE_3, VocationalGrade.KUTSEHINDAMINE_4, VocationalGrade.KUTSEHINDAMINE_5];

  var DISTINCTIVE_GRADES = [VocationalGrade.KUTSEHINDAMINE_1, VocationalGrade.KUTSEHINDAMINE_2,
    VocationalGrade.KUTSEHINDAMINE_3, VocationalGrade.KUTSEHINDAMINE_4, VocationalGrade.KUTSEHINDAMINE_5];

  var VOCATIONAL_GRADE_PREFIX = 'KUTSEHINDAMINE_';

  return {
    isPositive: function(gradeCode) {
      return ArrayUtils.includes(POSITIVE_GRADES, gradeCode);
    },
    isDistinctive: function(gradeCode) {
      return ArrayUtils.includes(DISTINCTIVE_GRADES, gradeCode);
    },
    removePrefix: function(gradeCode) {
      return gradeCode.replace(VOCATIONAL_GRADE_PREFIX, '');
    },
    addPrefix: function(gradeValue) {
      return VOCATIONAL_GRADE_PREFIX + gradeValue;
    }
  };
});
