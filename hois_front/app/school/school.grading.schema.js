'use strict';

angular.module('hitsaOis')
  .controller('SchoolGradingSchemaController', ['$route', '$scope', 'GRADING_SCHEMA_TYPE', 'USER_ROLES', 'ArrayUtils', 'AuthService', 'Classifier', 'DataUtils', 'FormUtils', 'HigherGradeUtil', 'QueryUtils', 'dialogService', 'message',
    function ($route, $scope, GRADING_SCHEMA_TYPE, USER_ROLES, ArrayUtils, AuthService, Classifier, DataUtils, FormUtils, HigherGradeUtil, QueryUtils, dialogService, message) {
      $scope.auth = $route.current.locals.auth;
      $scope.formState = { studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query() };
      $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_HINDAMISSYSTEEM);
      var baseUrl = '/gradingSchema';
      var Endpoint = QueryUtils.endpoint(baseUrl);

      var school = $scope.auth.school;
      if (school.higher) {
        $scope.schemaType = GRADING_SCHEMA_TYPE.HIGHER;
      } else if (school.vocational) {
        $scope.schemaType = GRADING_SCHEMA_TYPE.VOCATIONAL;
      } else if (school.secondary) {
        $scope.schemaType = GRADING_SCHEMA_TYPE.SECONDARY;
      } else if (school.basic) {
        $scope.schemaType = GRADING_SCHEMA_TYPE.BASIC;
      } else {
        message.error('schoolGradingSchema.error.studyLevelsMissing');
      }

      var currentStudyYear;
      $scope.formState.studyYears.$promise.then(function (result) {
        $scope.studyYearMap = result.reduce(function(mapped, studyYear) {
          mapped[studyYear.id] = studyYear;
          return mapped;
        }, {});
        currentStudyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        schemaTypeChanges();
      });

      $scope.switchSchemaType = function (type) {
        if (angular.isDefined($scope.formState.gradingSchemaForm) && $scope.formState.gradingSchemaForm.$dirty === true) {
          dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function () {
            $scope.schemaType = type;
            schemaTypeChanges();
          }, function () {
            $scope.currentNavItem = 'educationLevel.' + $scope.schemaType;
          });
        } else {
          $scope.schemaType = type;
          schemaTypeChanges();
        }
      };

      function schemaTypeChanges() {
        if (angular.isDefined($scope.schemaType)) {
          $scope.currentNavItem = 'educationLevel.' + $scope.schemaType;

          if ($scope.schemaType === GRADING_SCHEMA_TYPE.HIGHER) {
            $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' });
            $scope.grades.$promise.then(function () {
              $scope.grades = HigherGradeUtil.orderedGrades($scope.grades);
            });
            $scope.gradeSelectShownValue = function (grade) {
              return HigherGradeUtil.gradeSelectShownValue(grade, $scope.auth.school.letterGrades);
            };
          } else {
            $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
            $scope.gradeSelectShownValue = null;
          }

          loadTypeSchemas().then(function (schemas) {
            var currentGradingSchema;
            if (angular.isDefined(currentStudyYear)) {
              currentGradingSchema = schemas.find(function (schema) {
                return schema.studyYears.indexOf(currentStudyYear.id) !== -1;
              });
            }
            setTypeSchemas(schemas, currentGradingSchema);
          });
        }
      }

      function loadTypeSchemas() {
        return QueryUtils.endpoint(baseUrl + '/typeSchemas').query({ type: $scope.schemaType }).$promise.then(function (result) {
          result.forEach(function (schema) {
            schema.studyYearObjects = [];
            schema.studyYears.forEach(function (studyYearId) {
              schema.studyYearObjects.push($scope.studyYearMap[studyYearId]);
            });
          });
          return result;
        });
      }

      function setTypeSchemas(allGradingSchemas, currentGradingSchema) {
        var currentExists = currentGradingSchema !== null && angular.isDefined(currentGradingSchema);
        $scope.gradingSchema = currentExists ? QueryUtils.endpoint(baseUrl + '/' + currentGradingSchema.id).get() : null;

        var otherSchemas = [];
        var studyYearsInUse = [];
        allGradingSchemas.forEach(function (schema) {
          if (!currentExists || currentGradingSchema.id !== schema.id) {
            otherSchemas.push(schema);
            studyYearsInUse = studyYearsInUse.concat(schema.studyYears);
          }
        });
        $scope.otherGradingSchemas = otherSchemas;
        $scope.formState.studyYearsInUse = studyYearsInUse;
        if ($scope.formState.gradingSchemaForm) {
          $scope.formState.gradingSchemaForm.$setPristine();
        }
      }

      $scope.addGradingSchema = function () {
        if (angular.isDefined($scope.formState.gradingSchemaForm) && $scope.formState.gradingSchemaForm.$dirty === true) {
          dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function () {
            addGradingSchema();
          });
        } else {
          addGradingSchema();
        }
      };

      function addGradingSchema() {
        loadTypeSchemas().then(function (schemas) {
          setTypeSchemas(schemas, null);
          $scope.gradingSchema = {
            isVocational: $scope.schemaType === 'vocational',
            isHigher: $scope.schemaType === 'higher',
            isBasic: $scope.schemaType === 'basic',
            isSecondary: $scope.schemaType === 'secondary',
            isVerbal: false,
            isGrade: false,
            gradingSchemaRows: [ { isValid: true } ]
          };
        });
      }

      $scope.isVerbalChanged = function () {
        if (!$scope.gradingSchema.isVerbal) {
          $scope.gradingSchema.isGrade = false;
        }
      };

      $scope.save = function () {
        FormUtils.withValidForm($scope.formState.gradingSchemaForm, function () {
          var gradingSchema = new Endpoint($scope.gradingSchema);
          if (angular.isDefined($scope.gradingSchema.id)) {
            gradingSchema.$update().then(function (response) {
              $scope.gradingSchema = response;
              $scope.formState.gradingSchemaForm.$setPristine();
              message.info('main.messages.update.success');
            }).catch(angular.noop);
          } else {
            gradingSchema.$save().then(function (response) {
              $scope.gradingSchema = response;
              $scope.formState.gradingSchemaForm.$setPristine();
              message.info('main.messages.create.success');
            }).catch(angular.noop);
          }
        });
      };

      $scope.deleteGradingSchema = function () {
        dialogService.confirmDialog({prompt: 'schoolGradingSchema.messages.deleteGradingSchemaConfirm'}, function() {
          if (angular.isDefined($scope.gradingSchema.id)) {
            var gradingSchema = new Endpoint($scope.gradingSchema);
            gradingSchema.$delete().then(function () {
              loadTypeSchemas().then(function (schemas) {
                setTypeSchemas(schemas, null);
              });
              message.info('main.messages.delete.success');
            }).catch(angular.noop);
          } else {
            $scope.gradingSchema = null;
          }
        });
      };

      $scope.addNewRow = function () {
        $scope.gradingSchema.gradingSchemaRows.push({ isValid: true });
      };

      $scope.removeRow = function (row) {
        dialogService.confirmDialog({prompt: 'schoolGradingSchema.messages.deleteSchemaRowConfirm'}, function() {
          if (angular.isDefined(row.id)) {
            var RowEndpoint = QueryUtils.endpoint(baseUrl + '/' + $scope.gradingSchema.id + '/row');
            new RowEndpoint(row).$delete().then(function() {
              ArrayUtils.remove($scope.gradingSchema.gradingSchemaRows, row);
              message.info('main.messages.delete.success');
            });
          } else {
            ArrayUtils.remove($scope.gradingSchema.gradingSchemaRows, row);
          }
        });
      };

      $scope.openGradingSchemaDialog = function (gradingSchema) {
        dialogService.showDialog('school/school.grading.schema.dialog.html', function (dialogScope) {
          dialogScope.grades = $scope.grades;
          dialogScope.gradingSchema = gradingSchema;
        });
      };

      $scope.editGradingSchema = function (gradingSchema) {
          if (angular.isDefined($scope.formState.gradingSchemaForm) && $scope.formState.gradingSchemaForm.$dirty === true) {
          dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function () {
            loadTypeSchemas().then(function (schemas) {
              setTypeSchemas(schemas, gradingSchema);
            });
          });
        } else {
          loadTypeSchemas().then(function (schemas) {
            setTypeSchemas(schemas, gradingSchema);
          });
        }
      };
    }
  ]);
