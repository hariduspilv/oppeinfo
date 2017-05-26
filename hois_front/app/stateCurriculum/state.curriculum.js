'use strict';

angular.module('hitsaOis')
  .controller('StateCurriculumController', function ($route, $scope, message, Classifier, $location, classifierAutocomplete, dialogService, QueryUtils, DataUtils, ArrayUtils, Curriculum) {


    $scope.STATUS = Curriculum.STATUS;

    $scope.MODULE_TYPE = {
        BASIC: 'KUTSEMOODUL_P',
        GENERAL: 'KUTSEMOODUL_Y'
    };

    var initialStateCurriculum = {
        status: $scope.STATUS.ENTERING,
        modules: [],
        occupations: [],
        validFrom: new Date()
    };
    var baseUrl = '/stateCurriculum';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    $scope.readOnly = false;
    $scope.removeFromArray = ArrayUtils.remove;
    $scope.subOccupations = {};
    $scope.readOnly = $route.current.$$route.originalPath.indexOf("view") !== -1;

    function getStateCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.stateCurriculum = response;
          setVariablesForExistingStateCurriculum();
        });
      } else {
        $scope.stateCurriculum = new Endpoint(initialStateCurriculum);
        $scope.currentStatus = $scope.STATUS.ENTERING;
      }
    }
    getStateCurriculum();

    function setVariablesForExistingStateCurriculum() {
        DataUtils.convertStringToDates($scope.stateCurriculum, ["validFrom", "validThru"]);
        fillCascadeDropdowns();
        getAllSuboccupations();
        $scope.currentStatus = $scope.stateCurriculum.status;
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

    $scope.save = function () {
        $scope.stateCurriculum.status = $scope.currentStatus;
        // setTimeout is needed for validation of ng-required fields
        setTimeout(save, 0);
    };

    function save() {
        $scope.stateCurriculumForm.$setSubmitted();

        if (!stateCurriculumFormIsValid()) {
            message.error('main.messages.form-has-errors');
            return;
        }
        if($scope.strictValidation()) {
            if(!allOccuppationsValid()) {
                message.error('stateCurriculum.error.occupationsCredids');
                return;
            }
            if(!allSpetsOccupationsValid()) {
                message.error('stateCurriculum.error.spetsOccupationsCredids');
                return;
            }
        }

        if(id) {
            $scope.stateCurriculum.$update().then(function(){
                setVariablesForExistingStateCurriculum();
                message.info('main.messages.create.success');
                $scope.stateCurriculumForm.$setPristine();
                if($scope.stateCurriculum.status === $scope.STATUS.CLOSED) {
                    $location.path('/stateCurriculum/' + $scope.stateCurriculum.id + '/view');
                }
            });
        } else {
            $scope.stateCurriculum.$save().then(function(){
                DataUtils.convertStringToDates($scope.stateCurriculum, ["validFrom", "validThru"]);
                message.updateSuccess();
                $location.path('/stateCurriculum/' + $scope.stateCurriculum.id + '/edit');
            });
        }
    }

    $scope.confirmCurriculum = function() {
        dialogService.confirmDialog({prompt: 'stateCurriculum.confirmconfirm'}, function() {
            $scope.stateCurriculum.status = $scope.STATUS.VERIFIED;
            // setTimeout is needed for validation of ng-required fields
            setTimeout(save, 0);
        });
    };


    $scope.closeCurriculum = function() {
        dialogService.confirmDialog({prompt: 'stateCurriculum.closeconfirm'}, function() {
            $scope.stateCurriculum.status = $scope.STATUS.CLOSED;
            // setTimeout is needed for validation of ng-required fields
            setTimeout(save, 0);
        });
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
        return $scope.stateCurriculum && $scope.stateCurriculum.status === $scope.STATUS.VERIFIED;
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

    $scope.checkValidThru = function() {
        if($scope.stateCurriculum.validThru < $scope.stateCurriculum.validFrom) {
            $scope.stateCurriculum.validThru = undefined;
        }
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
        var modules = $scope.stateCurriculum.modules;
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
                return total + val.credits;
        }, 0);
    }

    function getOccupations(classifierCodes) {
        return classifierCodes.filter(function(o){
            return o.indexOf('KUTSE_') === 0;
        });
    }

// ------------ Occupations and suboccupations

    $scope.querySearch = function(queryText) {
      return classifierAutocomplete.searchByName(queryText, 'KUTSE');
    };

    $scope.addOcupation = function(item) {
      if(item && occupationNotAlreadyAdded(item.code)) {
        $scope.stateCurriculum.occupations.push(item.code);
        getSubOccupations(item.code);
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
        dialogService.confirmDialog({prompt: 'stateCurriculum.occupationdeleteconfirm'}, function() {
            $scope.removeFromArray($scope.stateCurriculum.occupations, occupation);
            var removedSubOccupations = $scope.subOccupations[occupation].map(function(o){return o.code;});
            delete $scope.subOccupations[occupation];
            $scope.getSubOccupationsAsList();

            $scope.stateCurriculum.modules.forEach(function(m){
                $scope.removeFromArray(m.moduleOccupations, occupation);
                $scope.stateCurriculum.modules.forEach(function(m){
                    deleteModuleWithNoOccupations(m);
                });
            });
            removedSubOccupations.forEach(function(so){
                $scope.stateCurriculum.modules.forEach(function(m){
                    $scope.removeFromArray(m.moduleOccupations, so);
                });
                $scope.stateCurriculum.modules.forEach(function(m){
                    deleteModuleWithNoOccupations(m);
                });
            });
        });
    };

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

    $scope.removeModule = function(occupation, module1) {
        $scope.removeFromArray( module1.moduleOccupations, occupation);
        deleteModuleWithNoOccupations(module1);
    };

    function deleteModuleWithNoOccupations(module1) {
        if(!module1.moduleOccupations ||  module1.moduleOccupations.length === 0) {
            $scope.removeFromArray($scope.stateCurriculum.modules, module1);
        }
    }

    $scope.openAddModuleDialog = function (editingModule) {
        var DialogController = function (scope) {
            if (editingModule) {
                scope.data = angular.extend({}, editingModule);
            } else {
                scope.data = {
                    moduleOccupations: []
                };
            }

            scope.editing = angular.isDefined(editingModule);

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

            scope.delete = function() {
               dialogService.confirmDialog({prompt: 'curriculum.moduleDeleteConfirm'}, function() {
                   ArrayUtils.remove($scope.stateCurriculum.modules, editingModule);
                    if(scope.data.id) {
                         var ModuleEndpoint = QueryUtils.endpoint('/stateCurriculum/modules');
                         var deletedModule = new ModuleEndpoint($scope.stateCurriculum);
                         deletedModule.$update().then(function(response) {
                                message.info('main.messages.delete.success');
                                $scope.stateCurriculum.modules = response.modules;
                        });
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
                scope.suboccupationsValid = scope.suboccupationsSelectedCorrectly() ? true : null;
            };
            scope.validateOccupations();

            scope.readOnly = $scope.readOnly;
            scope.occupations = $scope.stateCurriculum.occupations;
            $scope.stateCurriculum.occupations.forEach(function(o){
                var list = $scope.subOccupations[o].map(function(e){return e.code;});
                scope.occupations = scope.occupations.concat(list);
            });
            scope.allowedModuleTypes = [$scope.MODULE_TYPE.BASIC, $scope.MODULE_TYPE.GENERAL];

            scope.filterOccupations = function(mainClassCode) {
                return function (occupaionCode) {
                    return occupaionCode.indexOf(mainClassCode) === 0;
                };
            };
        };
        dialogService.showDialog('stateCurriculum/state.curriculum.module.add.dialog.html', DialogController,
            function (submitScope) {
            var data = submitScope.data;
                if(editingModule) {
                    angular.extend(editingModule, data);
                } else {
                    $scope.stateCurriculum.modules.push(data);
                }
                if($scope.stateCurriculum.id) {
                    var ModuleEndpoint = QueryUtils.endpoint('/stateCurriculum/modules');
                    var savedModule = new ModuleEndpoint($scope.stateCurriculum);
                    savedModule.$update().then(function(response) {
                        message.info('main.messages.create.success');
                        $scope.stateCurriculum.modules = response.modules;
                        $scope.stateCurriculum.occupations = response.occupations;
                    });
                }
            });
        };
  });
