'use strict';

angular.module('hitsaOis').constant('GRADING_SCHEMA_TYPE', {
  BASIC: 'basic',
  SECONDARY: 'secondary',
  VOCATIONAL: 'vocational',
  HIGHER: 'higher'
}).factory('GradingSchema', ['$q', 'GRADING_SCHEMA_TYPE', 'Classifier', 'HigherGradeUtil', 'QueryUtils',
  function ($q, GRADING_SCHEMA_TYPE, Classifier, HigherGradeUtil, QueryUtils) {
    var type, classifierGrades, schoolGradingSchemas;

    function GradingSchema(gradingSchemaType, schoolId) {
      type = gradingSchemaType;
      var gradesClassCode = GRADING_SCHEMA_TYPE.HIGHER === gradingSchemaType ? 'KORGHINDAMINE' : 'KUTSEHINDAMINE';
      classifierGrades = Classifier.queryForDropdown({ mainClassCode: gradesClassCode });

      if (angular.isDefined(schoolId)) {
        schoolGradingSchemas = QueryUtils.endpoint('/public/gradingSchemas').query({ schoolId: schoolId, type: gradingSchemaType });
      } else {
        schoolGradingSchemas = QueryUtils.endpoint('/gradingSchema/typeSchemas').query({ type: gradingSchemaType });
      }
      var promises = [classifierGrades.$promise, schoolGradingSchemas.$promise];

      return {
        promises: promises,
        existsSchoolGradingSchema: function () {
          return existsSchoolGradingSchema();
        },
        existsValidSchoolGradingSchema: function (studyYearId) {
          return existsValidSchoolGradingSchema(studyYearId);
        },
        validSchoolGradingSchema: function (studyYearId) {
          return validSchoolGradingSchema(studyYearId);
        },
        gradeSelection: function (studyYearId, isGradeCheck) {
          return gradeSelection(studyYearId, isGradeCheck);
        },
        gradeMapper: function (selection, gradeProperties) {
          return gradeMapper(selection, gradeProperties);
        },
        analogGetter: function (selection) {
          return analogGetter(selection);
        }
      };
    }

    function existsSchoolGradingSchema() {
      return schoolGradingSchemas.length > 0;
    }

    function existsValidSchoolGradingSchema(studyYearId) {
      if (angular.isDefined(studyYearId)) {
        if (validSchoolGradingSchema(studyYearId) !== null) {
          return true;
        }
      }
      return false;
    }

    function validSchoolGradingSchema(studyYearId) {
      if (angular.isDefined(studyYearId)) {
        for (var i = 0; i < schoolGradingSchemas.length; i++) {
          if (schoolGradingSchemas[i].studyYears.indexOf(studyYearId) !== -1) {
            return schoolGradingSchemas[i];
          }
        }
      }
      return null;
    }

    // if isGradeCheck = true then school grading schema grades are only valid if
    // grading schema is not verbal or is also assessed by grades, important for journals
    function gradeSelection(studyYearId, isGradeCheck) {
      var schoolGradingSchemaGradesValid = false;
      var selection = [];
      schoolGradingSchemas.forEach(function (schoolGradingSchema) {
        var validSchoolGradingSchema = schoolGradingSchema.studyYears.indexOf(studyYearId) !== -1 &&
          (!isGradeCheck || !schoolGradingSchema.isVerbal || schoolGradingSchema.isGrade);
        schoolGradingSchemaGradesValid = schoolGradingSchemaGradesValid || validSchoolGradingSchema;
        schoolGradingSchema.gradingSchemaRows.forEach(function (row) {
          selection.push({
            code: row.gradeReal,
            gradingSchemaRowId: row.id,
            value: null,
            value2: null,
            extraval1: null,
            extraval2: null,
            nameEt: row.grade,
            nameEn: row.gradeEn,
            valid: validSchoolGradingSchema ? row.isValid : false
          });
        });
      });

      classifierGrades.forEach(function (grade) {
        selection.push({
          code: grade.code,
          gradingSchemaRowId: null,
          value: grade.value,
          value2: grade.value2,
          extraval1: grade.extraval1,
          extraval2: grade.extraval2,
          nameEt: grade.nameEt,
          nameEn: grade.nameEn,
          valid: !schoolGradingSchemaGradesValid ? Classifier.isValid(grade) : false
        });
      });
      return selection.sort(gradeComparator);
    }

    function gradeMapper(selection, gradeProperties) {
      var classifierGradesMap = [];
      var schoolGradingSchemaRowsMap = [];
      selection.forEach(function (grade) {
        if (grade.gradingSchemaRowId) {
          schoolGradingSchemaRowsMap[grade.gradingSchemaRowId] = grade;
        } else {
          classifierGradesMap[grade.code] = grade;
        }
      });

      function valuegetter(gradeProperty) {
        if (angular.isArray(gradeProperty)) {
          return gradeProperty.map(function (it) { return grade(it); });
        }
        return grade(gradeProperty);
      }

      function grade(gradeProperty) {
        if (gradeProperty) {
          if (gradeProperty.gradingSchemaRowId) {
            return schoolGradingSchemaRowsMap[gradeProperty.gradingSchemaRowId];
          } else {
            return classifierGradesMap[gradeProperty.code];
          }
        }
        return null;
      }

      var objectmapper = function(object) {
        // if object is array, iterate over its elements
        if (angular.isArray(object)) {
          return object.map(objectmapper);
        }
        (gradeProperties || []).forEach(function (key) {
          if (object.hasOwnProperty(key)) {
            object[key] = valuegetter(object[key]);
          }
        });
        return object;
      };
      return {objectmapper: objectmapper};
    }

    function analogGetter(selection) {
      var validGrades = selection.filter(function (grade) { return grade.valid; }).sort(gradeComparator);
      if (type !== GRADING_SCHEMA_TYPE.HIGHER) {
        // sorted reverse to get the highest possible grade that matches given code, KORGHINDAMINE already sorted like that
        validGrades.reverse();
      }

      var get = function(code) {
        var analog = validGrades.find(function (grade) {
          return grade.code === code;
        });
        return angular.isDefined(analog) ? analog : null;
      };
      return {get: get};
    }

    function gradeComparator(a, b) {
      if (type === GRADING_SCHEMA_TYPE.HIGHER) {
        return a.valid - b.valid || HigherGradeUtil.gradeComparator(a, b) ||
          b.nameEt.localeCompare(a.nameEt, undefined, {numeric: true, sensitivity: 'base'});
      }
      return a.valid - b.valid || a.code.localeCompare(b.code) ||
        a.nameEt.localeCompare(b.nameEt, undefined, {numeric: true,  sensitivity: 'base'});
    }

    return GradingSchema;
  }
]);
