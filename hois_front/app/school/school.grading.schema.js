'use strict';

angular.module('hitsaOis')
  .controller('SchoolGradingSchemaController', ['$q', '$route', '$scope', 'USER_ROLES', 'ArrayUtils', 'AuthService', 'Classifier', 'DataUtils', 'QueryUtils', 'dialogService', 'message',
    function ($q, $route, $scope, USER_ROLES, ArrayUtils, AuthService, Classifier, DataUtils, QueryUtils, dialogService, message) {
      $scope.auth = $route.current.locals.auth;
      $scope.formState = { studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query() };
      var baseUrl = '/gradingSchema';
      var Endpoint = QueryUtils.endpoint(baseUrl);

      var school = $scope.auth.school;
      if (school.higher) {
        $scope.schemaType = 'higher';
      } else if (school.vocational) {
        $scope.schemaType = 'vocational';
      } else if (school.secondary) {
        $scope.schemaType = 'secondary';
      } else if (school.basic) {
        $scope.schemaType = 'basic';
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
          $scope.grades = Classifier.queryForDropdown({ mainClassCode: $scope.schemaType === 'higher' ? 'KORGHINDAMINE' : 'KUTSEHINDAMINE' });

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
      }

      $scope.addGradingSchema = function () {
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
      };

      $scope.isVerbalChanged = function () {
        if (!$scope.gradingSchema.isVerbal) {
          $scope.gradingSchema.isGrade = false;
        }
      };

      $scope.save = function () {
        var gradingSchema = new Endpoint($scope.gradingSchema);
        if (angular.isDefined($scope.gradingSchema.id)) {
          gradingSchema.$update().then(function (response) {
            $scope.gradingSchema = response;
            $scope.formState.gradingSchemaForm.$setPristine();
            message.info('main.messages.update.success');
          });
        } else {
          gradingSchema.$save().then(function (response) {
            $scope.gradingSchema = response;
            $scope.formState.gradingSchemaForm.$setPristine();
            message.info('main.messages.create.success');
          });
        }
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
            });
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

      $scope.openGradingSchemaDialog = function(gradingSchema) {
        dialogService.showDialog('school/school.grading.schema.dialog.html', function (dialogScope) {
          dialogScope.grades = $scope.grades;
          QueryUtils.endpoint(baseUrl + '/' + gradingSchema.id).get().$promise.then(function (result) {
            dialogScope.gradingSchema = result;
            dialogScope.gradingSchema.studyYearObjects = [];
            dialogScope.gradingSchema.studyYears.forEach(function (studyYearId) {
              dialogScope.gradingSchema.studyYearObjects.push($scope.studyYearMap[studyYearId]);
            });
          });
        });
      };

      $scope.editGradingSchema = function (gradingSchema) {
        if (angular.isDefined($scope.formState.gradingSchemaForm) && $scope.formState.gradingSchemaForm.$dirty === true) {
          dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function () {
            loadTypeSchemas().then(function (schemas) {
              setTypeSchemas(schemas, gradingSchema);
            });
            $scope.formState.gradingSchemaForm.$setPristine();
          });
        } else {
          loadTypeSchemas().then(function (schemas) {
            setTypeSchemas(schemas, gradingSchema);
          });
        }
      };
    }
  ]);
