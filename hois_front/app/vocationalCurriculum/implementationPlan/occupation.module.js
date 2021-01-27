'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumVersionOccupationModuleController',
  function ($scope, $route, QueryUtils, Classifier, message, $location, ArrayUtils, dialogService, $rootScope, USER_ROLES) {
    /****************
     *    Enums     *
     ****************/
    $scope.viewModes = Object.freeze({
      NEW: 0,
      REPLACE: 1,
      MAIN: 2
    });
    $scope.startModes = Object.freeze({
      BASE_MODULE: 0,
      COPY_MODULE: 1,
      INDEPENDENT: 2
    });

    /****************
     *Initial values*
     ****************/
    var id = $route.current.params.occupationModule;
    var curriculum = $route.current.params.curriculum;
    var curriculumVersion = $route.current.params.version;
    var curriculumModule = $route.current.params.curriculumModule;
    var autocompleteTeacher = QueryUtils.endpoint("/basemodule/teachers");
    var baseUrl = '/occupationModule';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var ThemeEndpoint = QueryUtils.endpoint(baseUrl + '/theme');
    var HOURS_PER_EKAP = 26;
    var initial = {
      curriculumModule: curriculumModule,
      curriculumVersion: curriculumVersion,
      assessment: 'KUTSEHINDAMISVIIS_E',
      yearCapacities: [ {credits: 0, studyYearNumber: 1} ],
      themes: [],
      capacities: []
    };
    /****************
     * Scope values *
     ****************/
    //$scope.replaceModuleForm = {};
    $scope.auth = $route.current.locals.auth;
    $scope.hasBaseModuleRight = ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL);
    $scope.viewMode = $scope.viewModes.MAIN;
    $scope.startMode = $scope.startModes.INDEPENDENT;
    $scope.formState = {
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
    };
    if ($scope.auth.school !== null) {
      $scope.capacities = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: false });
    }
    $scope.curriculumVersion = QueryUtils.endpoint(baseUrl + "/curriculumVersion").get({id: curriculumVersion});
    $scope.backToEditForm = '#/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/edit';
    $scope.backToViewForm = '#/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/view';

    /****************
     *Inner  methods*
     ****************/

    /****************
     *    Getter    *
     ****************/
    function get() {
      if (id) {
        $scope.occupationModule = Endpoint.get({ id: id }).$promise.then(function (response) {
          dtoToModel(response);
          $scope.viewMode = $scope.viewModes.MAIN;
          getCurriculumModule();
        });
      } else {
        dtoToModel(new Endpoint(initial));
        getCurriculumModule();
      }
    }

    function getCurriculumModule() {
      QueryUtils.endpoint(baseUrl + '/curriculumModule').get({ id: curriculumModule }).$promise.then(function(response){
        $scope.curriculumModule = response;
        if ($scope.curriculumModule.baseModule && !$scope.occupationModule.id) {
          $scope.viewMode = $scope.viewModes.NEW;
        }
        if(!id) {
          $scope.occupationModule.assessmentsEt = response.assessmentsEt;
        }
        var years = Math.floor(((response.studyPeriod || 0) + 11) / 12);
        $scope.studyYears = Array.apply(null, {length: years}).map(function(it, index) { return index + 1; });
        if(!id) {
          $scope.occupationModule.yearCapacities = $scope.studyYears.map(function(it) { return {credits: 0, studyYearNumber: it}; });
        } else {
          filterAndSortYearCapacities();
        }
        getModuleReplaceForm();
      });
    }

    /**
     * Gets a form for replacement this occupation module by base module
     */
    function getModuleReplaceForm() {
      if ($scope.curriculumModule.baseModule && $scope.auth.isAdmin() && ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA)) {
        QueryUtils.endpoint(
          "/basemodule/replace/" + $scope.curriculumModule.baseModule.id +
          "/" + $scope.curriculumModule.id +
          (id ? "/" + id : "")
        ).get().$promise.then(function (response) {
          $scope.replaceForm = response;
          $scope.replaceForm.connections = {};
          $scope.replaceForm.baseModuleThemes.forEach(function (theme) {
            theme.selection = {};
            response.occupationModules.forEach(function (module) {
              theme.selection[module.id] = {
                selected: false
              };
            });
          });
        });
      }
    }

    /****************
     *Main functions*
     ****************/
    function update() {
      $scope.occupationModule.$update().then(function (response) {
        message.info('main.messages.update.success');
        dtoToModel(response);
        $scope.occupationModuleForm.$setPristine();
      }).catch(angular.noop);
    }

    function create() {
      $scope.occupationModule.$save().then(function (response) {
        message.info('main.messages.create.success');
        $location.path('/occupationModule/' + curriculum + '/' + curriculumVersion + '/' + curriculumModule + '/' + response.id + '/edit');
      }).catch(angular.noop);
    }

    function save() {
      if ($scope.occupationModule.id) {
        update();
      } else {
        create();
      }
    }

    function generate() {
      dialogService.confirmDialog({prompt: 'basemodule.operation.createNewBaseModule.message'}, function() {
        QueryUtils.endpoint("/basemodule/generate/" + $scope.curriculumModule.id + "/" + $scope.occupationModule.id).get().$promise.then(function () {
          message.info('main.messages.create.success');
          get();
        });
      });
    }

    function validationPassed() {
      $scope.occupationModuleForm.$setSubmitted();
      if (!$scope.occupationModuleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    function validationReplaceFormPassed() {
      $scope.replaceModuleForm.$setSubmitted();
      if (!$scope.replaceModuleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    /**
     * 1) Get capacity types. They are set on new occupational module and new module themes
     * 2) sort year capacities
     */
    function dtoToModel(response) {
      $scope.occupationModule = new Endpoint(response);
      if ($scope.occupationModule.baseModule) {
        $scope.startMode = $scope.startModes.BASE_MODULE;
      }
      if ($scope.occupationModule.supervisor) {
        $scope.occupationModule.supervisor = $scope.occupationModule.supervisor.trim();
      }
      if ($scope.occupationModule.totalGradeDescription) {
        $scope.occupationModule.totalGradeDescription = $scope.occupationModule.totalGradeDescription.trim();
      }
      if ($scope.occupationModule.requirementsEt) {
        $scope.occupationModule.requirementsEt = $scope.occupationModule.requirementsEt.trim();
      }
      if(!$scope.occupationModule.id) {
        $scope.capacities.$promise.then(function () {
          $scope.occupationModule.capacities = $scope.capacities.map(classifierToCapacity);
        });
      }
      filterAndSortYearCapacities();
    }

    /**********************
     *Additional functions*
     **********************/

    /**
     * Copies data from the connected base module to occupation module.
     * NB! Ignores themes.
     *
     * @param {Funciton} callback function which is called in the end
     */
    function copyBaseModuleData(callback) {
      QueryUtils.endpoint("/basemodule/cm/" + $scope.curriculumModule.baseModule.id).get().$promise.then(function (response) {
        $scope.occupationModule.requirementsEt = response.cvRequirementsEt;
        $scope.occupationModule.assessmentsEt = response.cvAssessmentsEt;
        $scope.occupationModule.learningMethodsEt = response.cvLearningMethodsEt;
        $scope.occupationModule.assessmentMethodsEt = response.cvAssessmentMethodsEt;
        $scope.occupationModule.assessment = response.cvAssessment;
        $scope.occupationModule.totalGradeDescription = response.cvTotalGradeDescription;
        $scope.occupationModule.passDescription = response.cvPassDescription;
        $scope.occupationModule.grade3Description = response.cvGrade3Description;
        $scope.occupationModule.grade4Description = response.cvGrade4Description;
        $scope.occupationModule.grade5Description = response.cvGrade5Description;
        $scope.occupationModule.independentStudyEt = response.cvIndependentStudyEt;
        $scope.occupationModule.studyMaterials = response.cvStudyMaterials;
        $scope.occupationModule.teacher = response.teacher;
        $scope.occupationModule.supervisor = response.teacher[$scope.currentLanguageNameField()];
        $scope.occupationModule.capacities = response.capacities.map(function (r) {
          return {
            capacityType: r.capacityType,
            contact: r.contact,
            hours: r.hours,
            nameEn: r.nameEn,
            nameEt: r.nameEt
          }
        })
        callback();
      })
    }

    function filterAndSortYearCapacities() {
      if($scope.studyYears && $scope.occupationModule && $scope.occupationModule.yearCapacities) {
        var studyYears = $scope.studyYears.length;
        $scope.occupationModule.yearCapacities = $scope.occupationModule.yearCapacities.filter(function(yc) {
          return yc.studyYearNumber <= studyYears;
        }).sort(function(yc1, yc2) {
          return yc1.studyYearNumber - yc2.studyYearNumber;
        });
      }
    }

    function capacitiesMatch() {
      var capacities = $scope.occupationModule.capacities.reduce(function(sum, c){
        return sum + (c.hours ? c.hours : 0);
      }, 0);
      return capacities / HOURS_PER_EKAP === $scope.curriculumModule.credits;
    }

    function yearCapacitiesMatch() {
      var credits = $scope.occupationModule.yearCapacities.reduce(function(sum, c){
        return sum + c.credits;
      }, 0);
      return credits === $scope.curriculumModule.credits;
    }

    function classifierToCapacity(c) {
      return {
        capacityType: c.code,
        nameEt: c.nameEt,
        nameEn: c.nameEn
      };
    }

    function getNewCapacities() {
      Endpoint.get({id: id}).$promise.then(function(response){
        $scope.occupationModule.capacities = response.capacities;
        $scope.occupationModule.yearCapacities = response.yearCapacities;
        filterAndSortYearCapacities();
      });
    }

    function uniqueCapacityArray(arr) {
      var a = arr.concat();
      for (var i = 0; i < a.length; ++i) {
          for (var j = i+1; j < a.length; ++j) {
              if (a[i].capacityType === a[j].capacityType) {
                  a.splice(j--, 1);
              }
          }
      }
      return a;
    };

    /*****************
     *Scope functions*
     *****************/

    var previousValues = {};
    /**
     * Removes the mark that this theme for this module has been selected and adds a new mark for selected one
     *
     * @param {Number} moduleId occupation module id
     * @param {Nubmer} themeId occupation theme id
     * @param {Object} bTheme base module theme
     */
    $scope.selectChange = function (moduleId, themeId, bTheme) {
      if (bTheme.selection[moduleId].selected) {
        if (previousValues[themeId] && previousValues[themeId].selection[moduleId]) {
          previousValues[themeId].selection[moduleId].selected = false;
        }
        delete previousValues[themeId];
        delete $scope.replaceForm.connections[themeId];
        $scope.replaceForm.showOneToOneThemeError = true;
        return;
      }
      if (previousValues[themeId] && previousValues[themeId].selection[moduleId]) {
        previousValues[themeId].selection[moduleId].selected = false;
      }
      previousValues[themeId] = bTheme;
      previousValues[themeId].selection[moduleId].selected = true;
      $scope.replaceForm.showOneToOneThemeError = false;
    };

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    $scope.save = function () {
      if (!validationPassed()) {
        return;
      }
      if(!capacitiesMatch() && !yearCapacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.capacitiesAndYearCapacitiesMismatch'}, save);
      } else if(!capacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.occupationModuleCapacitiesMismatch'}, save);
      } else if (!yearCapacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.occupationModuleYearCapacitiesMismatch'}, save);
      } else {
        save();
      }
    };

    $scope.hasThemes = function() {
      return $scope.occupationModule && !ArrayUtils.isEmpty($scope.occupationModule.themes);
    }

    $scope.deleteTheme = function(theme) {
      dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteTheme'}, function() {
        new ThemeEndpoint(theme).$delete().then(function() {
          message.info('main.messages.delete.success');
          ArrayUtils.remove($scope.occupationModule.themes, theme);
          getNewCapacities();
          getModuleReplaceForm();
        });
      });
    };

    $scope.createBaseModule = function () {
      if (!validationPassed()) {
        return;
      }
      if ($scope.occupationModule.id) {
        if ($scope.occupationModuleForm.$dirty) {
          dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
            $scope.occupationModule.$get({id: $scope.occupationModule.id}).then(generate);
          });
        } else {
          generate();
        }
      }
    }

    $scope.release = function () {
      dialogService.confirmDialog({prompt: 'basemodule.operation.release.message'}, function() {
        QueryUtils.endpoint("/basemodule/releaseMin/" + $scope.occupationModule.id).get().$promise.then(function () {
          message.info("basemodule.operation.release.success");
          get();
        }).catch(function () {message.error("basemodule.operation.release.fail");});
      });
    }

    /**
     * Used when there is a new occupation module and curriculum module has a base module connection
     */
    $scope.continue = function () {
      switch ($scope.startMode) {
        case $scope.startModes.BASE_MODULE:
          $scope.occupationModule.baseModule = $scope.curriculumModule.baseModule.id;
          copyBaseModuleData(save);
          break;
        case $scope.startModes.COPY_MODULE:
          $scope.occupationModule.baseModule = $scope.curriculumModule.baseModule.id;
          $scope.occupationModule.copy = true;
          copyBaseModuleData(save);
          break;
        default:
          break;
      }
      $scope.viewMode = $scope.viewModes.MAIN;
    }

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteModule'}, function() {
        new Endpoint($scope.occupationModule).$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/edit?_noback');
        }).catch(angular.noop);
      });
    };

    /**
     * Replaces the current occupation module with data from the base module.
     */
    $scope.replace = function () {
      if (!validationReplaceFormPassed()) {
        return;
      }
      var query = {};
      query.baseModule = $scope.replaceForm.baseModule.id;
      query.curriculumModule = $scope.replaceForm.curriculumModule.id;
      query.curriculumVersionOModules = [$scope.occupationModule.id];
      query.themeReferences = {};
      $scope.replaceForm.occupationModules.forEach(function (oModule) {
        query.themeReferences[oModule.id] = {};
        $scope.replaceForm.themes[oModule.id].forEach(function (theme) {
          query.themeReferences[oModule.id][theme.id] = $scope.replaceForm.connections[theme.id].id;
        })
      });
      QueryUtils.endpoint("/basemodule/replace").save(query).$promise.then(function () {
        message.info("basemodule.operation.replace.success");
        $scope.viewMode = $scope.viewModes.MAIN;
        get();
      }).catch(function () {message.error("basemodule.operation.replace.fail")});
    }

    $scope.searchTeacher = function (text) {
      $scope.occupationModuleForm.$setDirty();
      return autocompleteTeacher.query({
        name: text
      }).$promise;
    };

    $scope.openAddThemeDialog = function (occupationModuleTheme) {

      var form = $scope.formState.readOnly ? 'vocationalCurriculum/implementationPlan/occupation.module.theme.view.dialog.html' : 'vocationalCurriculum/implementationPlan/occupation.module.theme.edit.dialog.html';

      dialogService.showDialog(form,
        function (dialogScope) {

          // If there is no value before or after being `propotion` filled automatically hours/credits
          // are changed then it will recalculate.
          var oldCalculatedProportion;
          var occupationModule = $scope.occupationModule;
          dialogScope.hasBaseModule = !(angular.isUndefined($scope.occupationModule.baseModule) || $scope.occupationModule.baseModule === null);

          if (!angular.isArray(occupationModule.themes)) {
            occupationModule.themes = [];
          }
          if (!angular.isDefined(occupationModuleTheme)) {
            dialogScope.occupationModuleTheme = {
              capacities: $scope.capacities.map(classifierToCapacity),
              assessment: 'KUTSEHINDAMISVIIS_E',
              module: $scope.occupationModule.id
            };
          } else {
            occupationModuleTheme.capacities = uniqueCapacityArray(occupationModuleTheme.capacities.concat($scope.capacities.map(classifierToCapacity)));
            dialogScope.occupationModuleTheme = angular.copy(occupationModuleTheme);
          }
          dialogScope.outcomes = $scope.curriculumModule.outcomes;
          dialogScope.setDefaultHours = function () {
            if (angular.isNumber(dialogScope.occupationModuleTheme.credits)) {
              dialogScope.occupationModuleTheme.hours = Math.round(HOURS_PER_EKAP * dialogScope.occupationModuleTheme.credits);
              dialogScope.dialogForm.hours.$setDirty();
              var proportion = Math.round((dialogScope.occupationModuleTheme.credits / occupationModule.credits) * 100);
              if (!dialogScope.occupationModuleTheme.proportion || oldCalculatedProportion === dialogScope.occupationModuleTheme.proportion) {
                oldCalculatedProportion = proportion;
                dialogScope.occupationModuleTheme.proportion = proportion;
              }
            }
          };
          dialogScope.studyYears = $scope.studyYears;
          dialogScope.assessments = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMISVIIS'});

          dialogScope.setDefaultCredits = function () {
            dialogScope.occupationModuleTheme.credits = Math.round((dialogScope.occupationModuleTheme.hours / HOURS_PER_EKAP) * 100) / 100;
            dialogScope.dialogForm.credits.$setDirty();
            var proportion = Math.round((dialogScope.occupationModuleTheme.credits / occupationModule.credits) * 100);
            if (!dialogScope.occupationModuleTheme.proportion || oldCalculatedProportion === dialogScope.occupationModuleTheme.proportion) {
              oldCalculatedProportion = proportion;
              dialogScope.occupationModuleTheme.proportion = proportion;
            }
          };

          dialogScope.getOutcomesByIds = function (arr) {
            if (!angular.isArray(arr)) return [];
            return dialogScope.outcomes.filter(function (outcome) {
              for (var i = 0; i < arr.length; i++) {
                if (arr[i] === outcome.id) {
                  return true;
                }
              }
              return false;
            });
          };

          dialogScope.joinOutcomes = function (arr, seperator) {
            if (!arr || angular.isUndefined(seperator)) return '';
            return arr.map(function (r) {
                return $scope.currentLanguage() === 'en' ? r.outcomeEn : r.outcomeEt
            }).join(seperator);
          };

          dialogScope.outcomesChanged = function () {
            if (dialogScope.occupationModuleTheme.outcomes.length === 0) {
              dialogScope.occupationModuleTheme.moduleOutcomes = false;
            }
          };

          dialogScope.saveTheme = function () {
            var submittedDialogScope = dialogScope;
            if (!validateTheme(submittedDialogScope)) {
              return;
            }
            if(angular.isDefined(occupationModuleTheme)) {
              new ThemeEndpoint(submittedDialogScope.occupationModuleTheme).$update().then(function(response){
                message.info('main.messages.update.success');
                ArrayUtils.remove(occupationModule.themes, occupationModuleTheme);
                occupationModule.themes.push(response);
                getNewCapacities();
                dialogScope.cancel();
                getModuleReplaceForm();
              });
            } else {
              new ThemeEndpoint(submittedDialogScope.occupationModuleTheme).$save().then(function(response){
                message.info('main.messages.create.success');
                occupationModule.themes.push(response);
                getNewCapacities();
                dialogScope.cancel();
                getModuleReplaceForm();
              });
            }
          };

          function validateTheme(submittedDialogScope) {
            submittedDialogScope.dialogForm.$setSubmitted();
            if (!submittedDialogScope.dialogForm.$valid) {
              message.error('main.messages.form-has-errors');
              return false;
            }
            if (submittedDialogScope.occupationModuleTheme.hours !== Math.round(HOURS_PER_EKAP * submittedDialogScope.occupationModuleTheme.credits) &&
              submittedDialogScope.occupationModuleTheme.credits !== Math.round((submittedDialogScope.occupationModuleTheme.hours / HOURS_PER_EKAP) * 10) / 10) {
              message.error('curriculum.error.themeCreditsAndHoursMismatch');
              return false;
            }
            if (!proportionsMatch()) {
              message.error('curriculum.error.themeCapacitiesAndHoursMismatch');
              return false;
            }
            return true;
          }

          function proportionsMatch() {
            return dialogScope.occupationModuleTheme.hours === dialogScope.occupationModuleTheme.capacities.reduce(function (sum, value) {
              if (angular.isDefined(value.hours)) {
                return sum += value.hours;
              } else {
                return sum;
              }
            }, 0);
          }

        }, function () {
          // saved in dialogScope.saveTheme()
        });
    };

    get();
  });
