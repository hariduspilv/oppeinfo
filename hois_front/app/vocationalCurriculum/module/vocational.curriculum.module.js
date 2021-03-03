'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleController', function ($scope, $route, QueryUtils, dialogService, message, ArrayUtils, $location, $rootScope, USER_ROLES) {
    /****************
     *    Enums     *
     ****************/
    $scope.moduleTypes = Object.freeze({
      NEW_MODULE: 0,
      BASE_MODULE: 1
    });

    /****************
     *Initial values*
     ****************/
    var id = $route.current.params.id;
    var baseUrl = '/curriculumModule';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var viewMode = $route.current.$$route.originalPath.indexOf("view") !== -1;
    var previousValuesTheme = {};
    var previousValuesOutcome = {};
    var AUTOCOMPLETE_ITEM_SIZE = 32;
    var AUTOCOMPLETE_CONTAINER_MAX_ITEM = 7;
    $scope.auth = $route.current.locals.auth;
    $scope.hasBaseModuleRight = ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL);
    $scope.curriculumId = $route.current.params.curriculum;
    $scope.occupationsSelected = {};
    $scope.specialitiesSelected = {};

    $scope.formState = {
      studyFields: QueryUtils.endpoint('/curriculum/studyFields/'+$scope.curriculumId).query()
    };

    $scope.competences = [];
    $scope.curriculum = QueryUtils.endpoint(baseUrl + '/curriculumMin').get({id: $scope.curriculumId});
    QueryUtils.endpoint(baseUrl + '/competences/curriculum/' + $scope.curriculumId).query().$promise.then(function(response){
      $scope.competences = response;
      if($scope.vocationalCurriculumModuleForm) {
        $scope.vocationalCurriculumModuleForm.$setPristine();
      }
    });
    
    $scope.possibleTypes = QueryUtils.endpoint(baseUrl + '/types').query({module: id, curriculum: $scope.curriculumId});
    $scope.moduleType = $scope.moduleTypes.NEW_MODULE;
    $scope.baseModuleUsed = false;

    var initial = {
      curriculum: $scope.curriculumId,
      occupations: [],
      competences: [],
      outcomes: [],
      practice: false
    }

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    /****************
     *Main functions*
     ****************/
    function get() {
      if (id) {
        Endpoint.get({id: id}).$promise.then(dtoToModel);
      } else {
        $scope.module = new Endpoint(initial);
        QueryUtils.endpoint(baseUrl + '/curriculum/' + $scope.curriculumId).search().$promise.then(function(response){
          $scope.module.curriculumOccupations = response.occupations;
          $scope.module.occupation = response.occupation;
          $scope.module.canHaveOccupations = response.canHaveOccupations;
        });
      }
    }
    
    function update() {
      $scope.module.$update().then(function(response){
        message.info('main.messages.update.success');
        dtoToModel(response);
        $scope.vocationalCurriculumModuleForm.$setPristine();
      });
    }

    function create() {
      $scope.module.$save().then(function(response){
        message.info('main.messages.create.success');
        $location.path('/vocationalCurriculum/' + $scope.module.curriculum + '/module/' + response.id + '/edit');
      });
    }

    function moduleToDto() {
      $scope.module.outcomes.forEach(function(outcome){
        outcome.orderNr = $scope.module.outcomes.indexOf(outcome);
      });
      mapOccupationsToModule($scope, $scope.module);
    }
    
    function dtoToModel(response) {
      $scope.module = response;
      if ($scope.module.baseModule) {
        $scope.baseModuleUsed = true;
        $scope.moduleType = $scope.moduleTypes.BASE_MODULE;
        $scope.baseModule = $scope.module.baseModule;
      }
      $scope.module.outcomes.forEach(function(outcome){
        if(!angular.isDefined(outcome.orderNr)) {
          outcome.orderNr = $scope.module.outcomes.indexOf(outcome);
        }
      });
      $scope.module.outcomes.sort(function(el1, el2){
        return el1.orderNr - el2.orderNr;
      });
      if (angular.isArray($scope.module.occupations)) {
        $scope.module.occupations.forEach(function(occupation) {
          if(occupation.indexOf('KUTSE') === 0 || occupation.indexOf('OSAKUTSE') === 0) {
            $scope.occupationsSelected[occupation] = true;
          } else if(occupation.indexOf('SPETSKUTSE') === 0) {
            $scope.specialitiesSelected[occupation] = true;
          }
        });
      }
    }
    
    function mapOccupationsToModule(submittedDialogScope, curriculumModule) {
      curriculumModule.occupations = [];

      curriculumModule.curriculumOccupations.forEach(function(it) {
        if (submittedDialogScope.occupationsSelected[it.occupation] === true) {
          curriculumModule.occupations.push(it.occupation);
        }

        if (angular.isArray(it.partOccupations)) {
          it.partOccupations.forEach(function(partOccupation) {
            if (submittedDialogScope.occupationsSelected[partOccupation] === true) {
              curriculumModule.occupations.push(partOccupation);
            }
          });
        }

        if (angular.isArray(it.specialities)) {
          it.specialities.forEach(function(speciality) {
            if (submittedDialogScope.specialitiesSelected[speciality] === true) {
              curriculumModule.occupations.push(speciality);
            }
          });
        }
      });
    }

    function validationPassed() {
      if (viewMode && id) {
        return true;
      }
      $scope.vocationalCurriculumModuleForm.$setSubmitted();
      if(!$scope.vocationalCurriculumModuleForm.$valid) {
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
    
    function anySpecAdded(specialities) {
      if(!angular.isArray(specialities)) {
          return false;
      }
      for(var i = 0; i < specialities.length; i++) {
          if($scope.specialitiesSelected[specialities[i]]) {
              return true;
          }
      }
      return false;
    }

    function anyPartOccupationAdded(partOccupations) {
      if(!angular.isArray(partOccupations)) {
          return false;
      }
      for(var i = 0; i < partOccupations.length; i++) {
          if($scope.occupationsSelected[partOccupations[i]]) {
              return true;
          }
      }
      return false;
    }

    function clearReplaceForm(force) {
      if (angular.isDefined($scope.replaceBaseModuleForm) || force) {
        $scope.baseModuleNameTemplate = "";
        $scope.baseModuleTemplate = undefined;
        $scope.replaceBaseModuleForm = undefined;
        $scope.replaceModuleForm.showNotEnoughOutcomesError = false;
        $scope.replaceModuleForm.showOneToOneOutcomeError = false;
        $scope.replaceModuleForm.showOneToOneThemeError = false;
        previousValuesTheme = {};
        previousValuesOutcome = {};
      }
    }

    /*****************
     *Scope functions*
     *****************/
    $scope.save = function() {
      if(!validationPassed()) {
        return;
      }
      moduleToDto();
      if($scope.module.id) {
        update();
      } else {
        create();
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'module.messages.prompt.delete'}, function() {
        $scope.module.$delete().then(function() {
          message.info('main.messages.delete.success');
          $rootScope.back('#/vocationalCurriculum/' + $scope.module.curriculum + '/edit')
        });
      });
    };
    
    $scope.$watch('module.module', function(newValue, oldValue) {
      if (newValue !== oldValue && angular.isDefined($scope.curriculum) && $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        if (oldValue === 'KUTSEMOODUL_Y' || oldValue === 'KUTSEMOODUL_P' ||
          ($scope.module.id && !$scope.module.basicDataCanBeEdited && oldValue === 'KUTSEMOODUL_L')) {
          dialogService.confirmDialog({prompt: 'curriculum.prompt.confirmModuleTypeChange'}, function() {}, function() {
            $scope.module.module = oldValue;
          });
        }
      }
    });
    

    /****************
     *   Outcomes   *
     ****************/

    $scope.isEditingOutcome = false;
    $scope.editedOutcome = null;
    $scope.addOutcome = function() {
      if (angular.isString($scope.outcomeEt) && $scope.outcomeEt !== '') {
          $scope.module.outcomes.push({outcomeEt: $scope.outcomeEt, outcomeEn: $scope.outcomeEn, order: $scope.module.outcomes.length});
          $scope.outcomeEt = undefined;
          $scope.outcomeEn = undefined;
        }
    };

    $scope.swap = function(index1, index2) {
      var temp = $scope.module.outcomes[index1];
      $scope.module.outcomes[index1] = $scope.module.outcomes[index2];
      $scope.module.outcomes[index2] = temp;
      $scope.vocationalCurriculumModuleForm.$setDirty();
    };

    $scope.editOutcome = function(outcome) {
      $scope.isEditingOutcome = true;
      $scope.editedOutcome = outcome;
      $scope.outcomeEt = outcome.outcomeEt;
      $scope.outcomeEn = outcome.outcomeEn;
    };

    $scope.saveOutcome = function() {
      if (angular.isString($scope.outcomeEt) && $scope.outcomeEt !== '') {
          $scope.isEditingOutcome = false;

          $scope.editedOutcome.outcomeEt = $scope.outcomeEt;
          $scope.editedOutcome.outcomeEn = $scope.outcomeEn;

          $scope.outcomeEt = undefined;
          $scope.outcomeEn = undefined;
        }
    };

    $scope.removeFromArray = function(array, item) {
      dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
          ArrayUtils.remove(array, item);
          $scope.vocationalCurriculumModuleForm.$setDirty();
      });
    };

    $scope.radioChange = function () {
      $scope.module.baseModule = null;
    }

    $scope.getBaseModule = function (baseModule) {
      if (angular.isDefined(baseModule) && !$scope.baseModuleUsed) {
        QueryUtils.endpoint("/basemodule/cm").get({id: baseModule.id}).$promise.then(function (response) {
          $scope.module.baseModule = response;
          $scope.module.credits = response.credits;
          $scope.module.nameEt = response.nameEt;
          $scope.module.nameEn = response.nameEn;
          $scope.module.objectivesEt = response.objectivesEt;
          $scope.module.objectivesEn = response.objectivesEn;
          $scope.module.outcomes = response.outcomes;
          $scope.module.outcomes.forEach(function(outcome){
            outcome.id = null;
            if(!angular.isDefined(outcome.orderNr)) {
              outcome.orderNr = $scope.module.outcomes.indexOf(outcome);
            }
          });
          $scope.module.outcomes.sort(function(el1, el2){
            return el1.orderNr - el2.orderNr;
          });
          $scope.module.assessmentsEt = response.assessmentsEt;
          $scope.module.assessmentsEn = response.assessmentsEn;
        });
      }
    }

    $scope.getReplaceForm = function (bModule) {
      clearReplaceForm();
      if (angular.isDefined(bModule)) {
        QueryUtils.endpoint("/basemodule/replace/" + bModule.id + "/" + $scope.module.id).get().$promise.then(function (response) {
          if (response.outcomes) {
            $scope.replaceModuleForm.showNotEnoughOutcomesError = false;
            $scope.replaceBaseModuleForm = response;
            $scope.replaceBaseModuleForm.connections = {};
            $scope.replaceBaseModuleForm.baseModuleThemes.forEach(function (theme) {
              theme.selection = {};
              $scope.replaceBaseModuleForm.occupationModules.forEach(function (module) {
                theme.selection[module.id] = {
                  selected: false
                };
              });
            });
            $scope.replaceBaseModuleForm.occupationModules.forEach(function (oModule) {
              oModule.enabled = angular.isDefined($scope.replaceBaseModuleForm.themes[oModule.id]);
            });
            if (angular.equals($scope.replaceBaseModuleForm.themes, {})) {
              $scope.replaceBaseModuleForm.themes = undefined;
            }
          } else {
            clearReplaceForm();
            $scope.replaceModuleForm.showNotEnoughOutcomesError = true;
          }
        });
      }
    }

    $scope.switchView = function (show) {
      $scope.showReplaceMenu = show;
      clearReplaceForm(true);
    }

    $scope.replace = function () {
      if (!validationReplaceFormPassed()) {
        return;
      }
      var query = {};
      query.baseModule = $scope.replaceBaseModuleForm.baseModule.id;
      query.curriculumModule = $scope.replaceBaseModuleForm.curriculumModule.id;
      query.curriculumVersionOModules = $scope.replaceBaseModuleForm.occupationModules.filter(function (r) {
        return r.enabled;
      }).map(function (r) { return r.id; });
      query.themeReferences = {};
      query.curriculumVersionOModules.forEach(function (oModule) {
        query.themeReferences[oModule] = {};
        $scope.replaceBaseModuleForm.themes[oModule].forEach(function (theme) {
          query.themeReferences[oModule][theme.id] = $scope.replaceBaseModuleForm.connections[theme.id].id;
        });
      });
      query.outcomeReferences = {};
      $scope.replaceBaseModuleForm.outcomes.forEach(function (outcome) {
        query.outcomeReferences[outcome.id] = $scope.replaceBaseModuleForm.connectionsOutcome[outcome.id].id;
      });
      QueryUtils.endpoint("/basemodule/replace").save(query).$promise.then(function () {
        message.info("basemodule.operation.replace.success");
        $scope.showReplaceMenu = false;
        get();
      }).catch(function () {
        message.error("basemodule.operation.replace.fail");
      });
    }

    $scope.selectChange = function (moduleId, themeId, bTheme) {
      if (bTheme.selection[moduleId].selected) {
        if (previousValuesTheme[themeId] && previousValuesTheme[themeId].selection[moduleId]) {
          previousValuesTheme[themeId].selection[moduleId].selected = false;
        }
        delete previousValuesTheme[themeId];
        delete $scope.replaceBaseModuleForm.connections[themeId];
        $scope.replaceModuleForm.showOneToOneThemeError = true;
        return;
      }
      if (previousValuesTheme[themeId] && previousValuesTheme[themeId].selection[moduleId]) {
        previousValuesTheme[themeId].selection[moduleId].selected = false;
      }
      previousValuesTheme[themeId] = bTheme;
      previousValuesTheme[themeId].selection[moduleId].selected = true;
      $scope.replaceModuleForm.showOneToOneThemeError = false;
    };

    $scope.selectChangeOutcome = function (outcomeId, bOutcome) {
      if (bOutcome.selected) {
        if (previousValuesOutcome[outcomeId]) {
          previousValuesOutcome[outcomeId].selected = false;
        }
        delete previousValuesOutcome[outcomeId];
        delete $scope.replaceBaseModuleForm.connectionsOutcome[outcomeId];
        $scope.replaceModuleForm.showOneToOneOutcomeError = true;
        return;
      }
      if (previousValuesOutcome[outcomeId]) {
        previousValuesOutcome[outcomeId].selected = false;
      }
      previousValuesOutcome[outcomeId] = bOutcome;
      previousValuesOutcome[outcomeId].selected = true;
      $scope.replaceModuleForm.showOneToOneOutcomeError = false;
    }
    
    $scope.searchBaseModule = function (name) {
      return QueryUtils.endpoint("/autocomplete/basemodules").query({
        name: name,
        notExpired: true
      }, function (response) {
        // HITSAOIS-413 39. After choosing element from big list the size goes wrong for container.
        document.querySelector("md-virtual-repeat-container.md-autocomplete-suggestions-container")
          .style.setProperty("min-height", (AUTOCOMPLETE_ITEM_SIZE * (response.length > AUTOCOMPLETE_CONTAINER_MAX_ITEM ? AUTOCOMPLETE_CONTAINER_MAX_ITEM : response.length)) + "px", "important");
      }).$promise;
    }

    $scope.releaseModule = function () {
      dialogService.confirmDialog({prompt: 'basemodule.operation.release.message'}, function() {
        QueryUtils.endpoint("/basemodule/release/" + $scope.module.id).get().$promise.then(function () {
          $scope.module.baseModule = null;
          $scope.baseModuleUsed = false;
          $scope.baseModule = undefined;
          $scope.moduleType = $scope.moduleTypes.NEW_MODULE;
          message.info("basemodule.operation.release.success");
        }).catch(function () {
          message.error("basemodule.operation.release.fail");
        });
      });
    };

    function occupationsAreValid() {
      if(!$scope.module || !$scope.module.curriculumOccupations) {
        return true;
      }
      var occupations = $scope.module.curriculumOccupations;
      // module cannot be both in occupation and its speciality
      for(var i = 0; i < occupations.length; i++) {
          var code = occupations[i].occupation;
          var selected = $scope.occupationsSelected[code];
          if(!selected) {
              continue;
          }
          var specialities = occupations[i].specialities;
          if(specialities) {
              for(var k = 0; k < specialities.length; k++) {
                  if($scope.specialitiesSelected[specialities[k]]) {
                      return null;
                  }
              }
          }
        }
        return true;
    }

    $scope.occupationsValid = occupationsAreValid();

    $scope.validateOccupations = function() {
          $scope.occupationsValid = occupationsAreValid();
    };

    get();
  });
  
