'use strict';

angular.module('hitsaOis')
  .controller('StateCurriculumController', function ($route, $scope, message, Classifier, $location,
    classifierAutocomplete, dialogService, QueryUtils, DataUtils, ArrayUtils, Curriculum, config, $rootScope) {

    $scope.isPublic = $route.current.locals.params && $route.current.locals.params.isPublic;

    $scope.STATUS = Curriculum.STATUS;

    $scope.MODULE_TYPE = {
        BASIC: 'KUTSEMOODUL_P',
        GENERAL: 'KUTSEMOODUL_Y'
    };

    var initialStateCurriculum = {
        status: $scope.STATUS.ENTERING,
        modules: [],
        occupations: [],
        validFrom: new Date(),
        optionalStudyCredits: 0,
        canChange: true
    };
    var baseUrl = '/stateCurriculum';
    var Endpoint = QueryUtils.endpoint($scope.isPublic ? '/public/statecurriculum' : baseUrl);
    var id = $route.current.params.id;
    $scope.removeFromArray = ArrayUtils.remove;
    $scope.subOccupations = {};

    $scope.formState = {
      strictValidation: false,
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
      stateCurriculumPdfUrl: config.apiUrl + baseUrl + '/print/' + id + '/stateCurriculum.pdf'
    };

    $scope.getCurriculumViewUrl = function(curriculumId) {
        return $scope.isPublic ? ('#/curriculum/' + curriculumId) : ('#/vocationalCurriculum/' + curriculumId + '/view');
    };

    function getStateCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.stateCurriculum = response;
          setVariablesForExistingStateCurriculum();

          $rootScope.removeLastUrlFromHistory(function(lastUrl){
            return lastUrl && lastUrl.indexOf('/stateCurriculum/' + id) !== -1;
          });
        });
      } else {
        $scope.stateCurriculum = new Endpoint(initialStateCurriculum);
      }
    }
    getStateCurriculum();

    function setVariablesForExistingStateCurriculum() {
        DataUtils.convertStringToDates($scope.stateCurriculum, ["validFrom", "validThru"]);
        fillCascadeDropdowns();
        getAllSuboccupations();
        $scope.formState.readOnly = $route.current.$$route.originalPath.indexOf("view") !== -1;
    }


    function fillCascadeDropdowns() {
        var iscedClass = $scope.stateCurriculum.iscedClass;
        Classifier.getParentsWithMainClass('ISCED_SUUN', $scope.stateCurriculum.iscedClass).$promise.then(function(response) {
          $scope.iscedSuun = response[0].code;
          return response[0];
      }).then(function(result){
          Classifier.getParentsWithMainClass('ISCED_VALD', result.code).$promise.then(function(response) {
          $scope.iscedVald = response[0].code;
          $scope.stateCurriculum.iscedClass = iscedClass;
        });
      });
    }


        // ----------- save and delete

    $scope.delete = function() {
      if(!ArrayUtils.isEmpty($scope.stateCurriculum.curricula)) {
        message.error("stateCurriculum.error.hasCurricula");
        return;
      }
      dialogService.confirmDialog({prompt: 'stateCurriculum.deleteconfirm'}, function() {
        $scope.stateCurriculum.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/stateCurriculum');
        });
      });
    };

    function validationPassed(messages) {
      $scope.stateCurriculumForm.$setSubmitted();
      if (!stateCurriculumFormIsValid()) {
          message.error(messages.errorMessage);
          return false;
      }
      return true;
    }

    $scope.occupationCheckBeforeSave = function (endpoint, messages) {
      if (!allOccuppationsValid()) {
        dialogService.confirmDialog({
          prompt: 'stateCurriculum.occupationsCreditsConfirmConfirm'
        }, function () {
          $scope.allSpetsOccupationsValidBeforeSave(endpoint, messages);
        }, function () {
          return false;
        });
      } else {
        $scope.allSpetsOccupationsValidBeforeSave(endpoint, messages);
      }
    };

    $scope.allSpetsOccupationsValidBeforeSave = function (endpoint, messages) {
      if (!allSpetsOccupationsValid()) {
        dialogService.confirmDialog({
          prompt: 'stateCurriculum.spetsOccupationsCredidsConfirm'
        }, function () {
          update(endpoint, messages);
        }, function () {
          return false;
        });
      } else {
        update(endpoint, messages);
      }
    };

    function createCurriculum(messages) {
      if(!validationPassed(messages)) {
        return;
      }
      $scope.stateCurriculum.$save().then(function(){
          DataUtils.convertStringToDates($scope.stateCurriculum, ["validFrom", "validThru"]);
          message.info('main.messages.create.success');
          $location.url('/stateCurriculum/' + $scope.stateCurriculum.id + '/edit?_noback');
      });
    }

    function update(endpoint, messages) {
      // setTimeout is needed for validation of ng-required fields
      setTimeout(function(){
        if(!validationPassed(messages)) {
          return;
        }
        endpoint.$update().then(function(response){
          message.info(messages.updateSuccess);
          $scope.stateCurriculum = response;
          setVariablesForExistingStateCurriculum();
          $scope.stateCurriculumForm.$setPristine();

          if(!$scope.formState.readOnly && $scope.stateCurriculum.status !== Curriculum.STATUS.ENTERING) {
            $location.url('/stateCurriculum/' + $scope.stateCurriculum.id + '/view?_noback');
          }
        });
      }, 0);
    }

    $scope.save = function () {
      var messages = {
        errorMessage: 'main.messages.form-has-errors',
        updateSuccess: 'main.messages.update.success'
      };
      if(id) {
        $scope.formState.strictValidation = false;
        update(new Endpoint($scope.stateCurriculum), messages);
      } else {
        createCurriculum(messages);
      }
    };

    function setStatus(endpoint, messages) {
      dialogService.confirmDialog({prompt: messages.prompt}, function() {
        if($scope.formState.strictValidation) {
            $scope.occupationCheckBeforeSave(endpoint, messages);
        } else {
            update(endpoint, messages);
        }
      });
    }



    $scope.confirmAndSave = function() {
      $scope.formState.strictValidation = true;
      var messages = {
        prompt: 'stateCurriculum.prompt.editForm.verify',
        updateSuccess: 'stateCurriculum.statuschange.verified',
        errorMessage: 'stateCurriculum.statuschange.fail.verify'
      };
      var ConfirmEndpoint = QueryUtils.endpoint(baseUrl + '/confirmAndSave');
      setStatus(new ConfirmEndpoint($scope.stateCurriculum), messages);
    };

    $scope.close = function() {
      $scope.formState.strictValidation = false;
      var messages = {
        prompt: $scope.formState.readOnly ? 'stateCurriculum.prompt.viewForm.close' : 'stateCurriculum.prompt.editForm.close',
        updateSuccess: 'stateCurriculum.statuschange.closed',
        errorMessage: $scope.formState.readOnly ? 'stateCurriculum.statuschange.fail.closeReadOnly' : 'stateCurriculum.statuschange.fail.close'
      };
      var url = $scope.formState.readOnly ? baseUrl + '/close' : baseUrl + '/closeAndSave';
      var CloseEndpoint = QueryUtils.endpoint(url);
      setStatus(new CloseEndpoint($scope.stateCurriculum), messages);
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
        return $scope.stateCurriculum && $scope.stateCurriculum.status === $scope.STATUS.VERIFIED || $scope.formState.strictValidation;
    };

    // validation

    function stateCurriculumFormIsValid() {
        return $scope.stateCurriculumForm.$valid && (!$scope.strictValidation() || $scope.stateCurriculum.occupations.length > 0 && allOcupationsHavePModule() && allSubOccupationsHavePModule());
    }

    function allOcupationsHavePModule() {
        for(var i = 0; i < $scope.stateCurriculum.occupations.length; i++) {
            if($scope.hasNoPmodule($scope.stateCurriculum.occupations[i])) {
                return false;
            }
        }
        return true;
    }

    function allSubOccupationsHavePModule() {
        for(var i = 0; i < $scope.subOccupationsList.length; i++) {
            if($scope.hasNoPmodule($scope.subOccupationsList[i].code)) {
                return false;
            }
        }
        return true;
    }

    $scope.hasNoPmodule = function(occupation) {
        if(!$scope.stateCurriculum || !$scope.stateCurriculum.modules) {
            return true;
        }
        for(var i = 0; i < $scope.stateCurriculum.modules.length; i++) {
            if($scope.stateCurriculum.modules[i].module === $scope.MODULE_TYPE.BASIC &&
            ArrayUtils.includes($scope.stateCurriculum.modules[i].moduleOccupations, occupation)) {
                return false;
            }
        }
        return true;
    };

    $scope.nameEtUniqueQuery = {
      id: id,
      paramName: 'nameEt',
      url: '/stateCurriculum/unique'
    };

    $scope.nameEnUniqueQuery = {
      id: $scope.nameEtUniqueQuery.id,
      paramName: 'nameEn',
      url: $scope.nameEtUniqueQuery.url
    };


    // ---------- validate credits

    function getCreditsOfRepeatingModules(occupation) {
        var sum = 0;
        var osakutsed = getOccupationsSubOccupations(occupation, 'OSAKUTSE');
        var modules = $scope.stateCurriculum.modules.filter(function(m){
          return !m.isAdditional;
        });
        for(var i = 0; i < modules.length; i++) {
            var commonPart = ArrayUtils.intersection(modules[i].moduleOccupations, osakutsed);
            if(commonPart.length === 0) {
                continue;
            }
            var addedToOccupation = ArrayUtils.includes(modules[i].moduleOccupations, occupation) ? 1 : 0;
            sum += modules[i].credits * (commonPart.length + addedToOccupation - 1);
        }
        return sum;
    }

    $scope.isOccutationValid = function(occupation) {
        var sum = getCreditsOfOccupationOrSubOccupation(occupation) + $scope.stateCurriculum.optionalStudyCredits;

        var osakutsed = getOccupationsSubOccupations(occupation, 'OSAKUTSE');
        for(var i = 0; i < osakutsed.length; i++) {
            sum += getCreditsOfOccupationOrSubOccupation(osakutsed[i]);
        }
        sum -= getCreditsOfRepeatingModules(occupation);

        var spetskutse = getOccupationsSubOccupations(occupation, 'SPETSKUTSE');
        if(spetskutse[0]) {
            sum += getCreditsOfOccupationOrSubOccupation(spetskutse[0]);
        }

        return $scope.stateCurriculum.credits === sum;
    };

    function allOccuppationsValid() {
        var occupation = getOccupations($scope.stateCurriculum.occupations);
        for(var i = 0; i < occupation.length; i++) {
            if(!$scope.isOccutationValid(occupation[i])) {
                return false;
            }
        }
        return true;
    }

    function allSpetsOccupationsValid() {
        var occupations = $scope.stateCurriculum.occupations;
        for(var i = 0; i < occupations.length; i++) {
            var spetskutsed = getOccupationsSubOccupations(occupations[i], 'SPETSKUTSE');
            for(var j = 0; j < spetskutsed.length; j++) {
                if(!$scope.isSpetsOccupationValid(spetskutsed[j], occupations[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    $scope.isSpetsOccupationValid = function(spetsOccupation, occupation) {
        var occMod = getCreditsOfOccupationOrSubOccupation(occupation);
        var subOccMod = getCreditsOfOccupationOrSubOccupation(spetsOccupation);
        var partOccMod = sumOfPartOccupations(occupation);
        var bool = occMod + subOccMod + partOccMod +
        $scope.stateCurriculum.optionalStudyCredits - getCreditsOfRepeatingModules(occupation) === $scope.stateCurriculum.credits;
        return bool;
    };

    $scope.modulesValid = function() {
      if(!$scope.stateCurriculum) {
          return true;
      }
      return !$scope.strictValidation() || $scope.stateCurriculum.occupations.length !== 0 && $scope.stateCurriculum.modules.length !== 0  &&
      allOccuppationsValid() && allSpetsOccupationsValid();
    };

    function sumOfPartOccupations(occupation) {
        var partOccupations = getOccupationsSubOccupations(occupation, 'OSAKUTSE');
        return partOccupations.reduce(function(sum, val){
            return sum + getCreditsOfOccupationOrSubOccupation(val);
        }, 0);
    }


    function getOccupationsSubOccupations(occupation, mainClassCode) {
        return $scope.subOccupations[occupation] ? $scope.subOccupations[occupation].filter(function(el){
            return el.mainClassCode === mainClassCode;
        }).map(function(el){return el.code;}) : [];
    }

    function getCreditsOfOccupationOrSubOccupation(occupation) {
        return $scope.stateCurriculum.modules.filter(function(m){
            return ArrayUtils.includes(m.moduleOccupations, occupation);
        }).reduce(function(total, val){
                return val.isAdditional ? total : total + val.credits;
        }, 0);
    }

    function getOccupations(classifierCodes) {
        return classifierCodes.filter(function(o){
            return o.indexOf('KUTSE_') === 0;
        });
    }

// ------------ Occupations and suboccupations

    $scope.querySearch = function(queryText) {
      return classifierAutocomplete.searchByName(queryText, 'KUTSE', {valid: true});
    };

    $scope.addOcupation = function(item) {
      if(item && occupationNotAlreadyAdded(item.code)) {
        $scope.stateCurriculum.occupations.push(item.code);
        getSubOccupations(item.code);
        $scope.stateCurriculumForm.$setDirty();
      }
    };

    function getAllSuboccupations() {
        if(!$scope.stateCurriculum.occupations) {
            return;
        }
        for(var i = 0; i < $scope.stateCurriculum.occupations.length; i++) {
          getSubOccupations($scope.stateCurriculum.occupations[i]);
        }
    }


    function getSubOccupations(code) {
        // TODO: optimize Classifier.getChildren() not to return whole classifier
        Classifier.getChildren(code).$promise.then(function(response) {
          $scope.subOccupations[code] = response.filter(function(so){return so.mainClassCode === 'OSAKUTSE' || so.mainClassCode === 'SPETSKUTSE';});
        //   .map(function(so){return so.code;});
        $scope.getSubOccupationsAsList();
        });
    }

    $scope.getSubOccupationsAsList = function() {
        $scope.subOccupationsList = [];
        for(var property in $scope.subOccupations) {
            $scope.subOccupationsList = $scope.subOccupationsList.concat($scope.subOccupations[property]);
        }
    };

    function occupationNotAlreadyAdded(item) {
        return !ArrayUtils.includes($scope.stateCurriculum.occupations, item);
    }

    $scope.removeOcupation = function(occupation) {

      var prompt = occupationBoundWithModules(occupation) ?
      'stateCurriculum.occupationWithModuleDeleteConfirm' : 'stateCurriculum.occupationdeleteconfirm';

      dialogService.confirmDialog({prompt: prompt}, function() {
          $scope.removeFromArray($scope.stateCurriculum.occupations, occupation);
          var removedSubOccupations = $scope.subOccupations[occupation].map(function(o){return o.code;});
          delete $scope.subOccupations[occupation];
          $scope.getSubOccupationsAsList();

          $scope.stateCurriculum.modules.forEach(function(m){
              $scope.removeFromArray(m.moduleOccupations, occupation);
          });
          removedSubOccupations.forEach(function(so){
              $scope.stateCurriculum.modules.forEach(function(m){
                  $scope.removeFromArray(m.moduleOccupations, so);
              });
          });
          deleteModulesWithNoOccupation();
          $scope.stateCurriculumForm.$setDirty();
      });
    };

    function occupationBoundWithModules(occupation) {
      var subOccupations = $scope.subOccupations[occupation].map(function(o){return o.code;});

      return $scope.stateCurriculum.modules.some(function(m){
        return ArrayUtils.includes(m.moduleOccupations, occupation) ||
        ArrayUtils.intersect(subOccupations, m.moduleOccupations);
      });
    }



    $scope.filterSubOccupations = function(occupation, subOccupationType) {
        return function(subOccupation) {
            return $scope.subOccupations[occupation] && $scope.subOccupations[occupation].length !== null && ArrayUtils.includes($scope.subOccupations[occupation], subOccupation) && subOccupation.code.indexOf(subOccupationType) !== -1;
        };
    };









    // ------ Modules

    $scope.filterModules = function(occupation, moduleTypeCode) {
      return function(module1){
          return module1.module === moduleTypeCode && ArrayUtils.includes(module1.moduleOccupations, occupation);
      };
    };

    function deleteModulesWithNoOccupation() {
      $scope.stateCurriculum.modules = $scope.stateCurriculum.modules.filter(function(module){
        return !ArrayUtils.isEmpty(module.moduleOccupations);
      });
    }

    $scope.openViewModuleDialog = function (editingModule) {
      var DialogController = function (scope) {

          scope.data = angular.copy(editingModule);

          scope.occupations = $scope.stateCurriculum.occupations.map(function(stateCurriculumOccupation){

            var subOccupations = $scope.subOccupations[stateCurriculumOccupation];
            subOccupations.forEach(function(s){
              s.selected = isSelected(s.code);
            });
            return {
              code: stateCurriculumOccupation,
              selected: isSelected(stateCurriculumOccupation),
              subOccupations:  subOccupations
            };
          });

          function isSelected(occupationCode) {
            return ArrayUtils.includes(scope.data.moduleOccupations, occupationCode);
          }
      };
      dialogService.showDialog('stateCurriculum/state.curriculum.module.view.dialog.html', DialogController,
          function () {
          });
      };

    $scope.openAddModuleDialog = function (editingModule) {
        var DialogController = function (scope) {
            if (editingModule) {
                scope.data = angular.copy(editingModule);
            } else {
                scope.data = {
                    moduleOccupations: [],
                    outcomes: []
                };
            }

            scope.editing = angular.isDefined(editingModule);
            scope.isEditingOutcome = false;
            scope.editedOutcome = null;

            /**
             * Module cannot be simultaneously in occupation and its spets-occupation
             */
            scope.occupationsSelectedCorrectly = function() {
                if(!scope.data.moduleOccupations) {
                    return true;
                }
                var occupations = getOccupations(scope.data.moduleOccupations);
                for(var i = 0; i < occupations.length; i++) {
                    var spetskutsed = getOccupationsSubOccupations(occupations[i], 'SPETSKUTSE');
                    if(ArrayUtils.intersect(spetskutsed, scope.data.moduleOccupations)) {
                        return false;
                    }
                }
                return true;
            };

            scope.addOutcome = function() {
              if (angular.isString(scope.data.outcomesEt) && scope.data.outcomesEt !== '') {
                  scope.data.outcomes.push({outcomesEt: scope.data.outcomesEt, outcomesEn: scope.data.outcomesEn});
                  scope.data.outcomesEt = undefined;
                  scope.data.outcomesEn = undefined;
                }
            };

            scope.editOutcome = function(outcome) {
              scope.isEditingOutcome = true;
              scope.editedOutcome = outcome;
              scope.data.outcomesEt = outcome.outcomesEt;
              scope.data.outcomesEn = outcome.outcomesEn;
            };


            scope.saveOutcome = function() {
              if (angular.isString(scope.data.outcomesEt) && scope.data.outcomesEt !== '') {
                  scope.isEditingOutcome = false;

                  scope.editedOutcome.outcomesEt = scope.data.outcomesEt;
                  scope.editedOutcome.outcomesEn = scope.data.outcomesEn;

                  scope.data.outcomesEt = undefined;
                  scope.data.outcomesEn = undefined;
                }
            };

            scope.delete = function() {
               dialogService.confirmDialog({prompt: 'curriculum.moduleDeleteConfirm'}, function() {
                 if(scope.data.id) {
                  var ModuleEndpoint = QueryUtils.endpoint('/stateCurriculum/modules');
                  var deletedModule = new ModuleEndpoint(scope.data);
                  deletedModule.$delete().then(function() {
                    message.info('main.messages.delete.success');
                    ArrayUtils.remove($scope.stateCurriculum.modules, editingModule);
                  });
                  } else {
                    ArrayUtils.remove($scope.stateCurriculum.modules, editingModule);
                  }
                  scope.cancel();
                });
            };

             /**
             * Module cannot be simultaneously in osakutse and spetskutse of the same occupation
             */
            scope.suboccupationsSelectedCorrectly = function() {
                if(!scope.data.moduleOccupations) {
                    return true;
                }
                var occupations = getOccupations($scope.stateCurriculum.occupations);
                for(var i = 0; i < occupations.length; i++) {
                    var spetskutsed = getOccupationsSubOccupations(occupations[i], 'SPETSKUTSE');
                    var osakutsed = getOccupationsSubOccupations(occupations[i], 'OSAKUTSE');
                    if(ArrayUtils.intersect(spetskutsed, scope.data.moduleOccupations) &&
                    ArrayUtils.intersect(osakutsed, scope.data.moduleOccupations)) {
                        return false;
                    }
                }
                return true;
            };

            scope.validateOccupations = function() {
                scope.occupationsValid = scope.occupationsSelectedCorrectly() ? true : null;
                scope.suboccupationsValid = true; //scope.suboccupationsSelectedCorrectly() ? true : null;
            };
            scope.validateOccupations();

            scope.changeOccupation = function(occupation) {
              if(occupation.selected) {
                scope.data.moduleOccupations.push(occupation.code);
              } else {
                ArrayUtils.remove(scope.data.moduleOccupations, occupation.code);
              }
              scope.validateOccupations();
            };

            scope.occupations = $scope.stateCurriculum.occupations.map(function(stateCurriculumOccupation){

              var subOccupations = $scope.subOccupations[stateCurriculumOccupation];
              subOccupations.forEach(function(s){
                s.selected = isSelected(s.code);
              });

              return {
                code: stateCurriculumOccupation,
                selected: isSelected(stateCurriculumOccupation),
                subOccupations:  subOccupations
              };
            });

            function isSelected(occupationCode) {
              return ArrayUtils.includes(scope.data.moduleOccupations, occupationCode);
            }

            scope.allowedModuleTypes = [$scope.MODULE_TYPE.BASIC, $scope.MODULE_TYPE.GENERAL];
        };
        dialogService.showDialog('stateCurriculum/state.curriculum.module.add.dialog.html', DialogController,
            function (submitScope) {
            var data = submitScope.data;
                if($scope.stateCurriculum.id) {
                    var ModuleEndpoint = QueryUtils.endpoint('/stateCurriculum/modules');
                    var savedModule = new ModuleEndpoint(data);
                    savedModule.stateCurriculumOccupations = $scope.stateCurriculum.occupations;
                    savedModule.stateCurriculum = $scope.stateCurriculum.id;
                    if(data.id) {
                      savedModule.$update().then(function(response) {
                          message.info('main.messages.update.success');
                          angular.extend(editingModule, response);
                      });
                    } else {
                      savedModule.$save().then(function(response) {
                          message.info('main.messages.create.success');
                          $scope.stateCurriculum.modules.push(response);
                      });
                    }
                } else {
                  if(editingModule) {
                      angular.extend(editingModule, data);
                  } else {
                      $scope.stateCurriculum.modules.push(data);
                  }
                }
            });
        };

      $scope.goToEditForm = function() {
        if(!$scope.stateCurriculum) {
            return;
        }
        if($scope.stateCurriculum.status === $scope.STATUS.VERIFIED) {
            dialogService.confirmDialog({
            prompt: 'stateCurriculum.prompt.viewForm.editConfirmed',
          }, function(){
            $location.path('/stateCurriculum/' + $scope.stateCurriculum.id + '/edit');
            });
        } else {
            $location.path('/stateCurriculum/' + $scope.stateCurriculum.id + '/edit');
        }
      };
  });
