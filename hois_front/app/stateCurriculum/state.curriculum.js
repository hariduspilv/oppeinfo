'use strict';

angular.module('hitsaOis')
  .controller('StateCurriculumController', function ($q, $route, $scope, message, Classifier, $location, classifierAutocomplete, dialogService, QueryUtils, DataUtils, ArrayUtils) {

    var initialStateCurriculum = {
        status: 'OPPEKAVA_STAATUS_S',
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

    function getStateCurriculum() {
      if (id) {
        Endpoint.get({ id: id }).$promise.then(function (response) {
          $scope.stateCurriculum = response;
          setVariablesForExistingStateCurriculum();
        });
      } else {
        $scope.stateCurriculum = new Endpoint(initialStateCurriculum);
      }
    }
    getStateCurriculum();

    function setVariablesForExistingStateCurriculum() {
        DataUtils.convertStringToDates($scope.stateCurriculum, ["validFrom", "validThru"]);
        fillCascadeDropdowns();
        setReadOnly();
        getAllSuboccupations();
    }

    function fillCascadeDropdowns() {
      Classifier.getParentsWithMainClass('ISCED_SUUN', $scope.stateCurriculum.iscedClass).$promise.then(function(response) {
        $scope.iscedSuun = response[0].code;
        return response[0];
      }).then(function(result){
          Classifier.getParentsWithMainClass('ISCED_VALD', result.code).$promise.then(function(response) {
          $scope.iscedVald = response[0].code;
        });
      });
    }

    function setReadOnly() {
        $scope.readOnly = $scope.stateCurriculum.status === "OPPEKAVA_STAATUS_C" ||
        $route.current.$$route.originalPath.indexOf("view") !== -1;
    }
        // ----------- save and delete


    $scope.delete = function() {
        dialogService.confirmDialog({prompt: 'stateCurriculum.deleteconfirm'}, function() {
            $scope.stateCurriculum.$delete().then(function() {
                message.info('main.messages.delete.success');
                $location.path('/stateCurriculum');
            });
        });
    };

    $scope.save = function () {
      if($scope.readOnly) {
         return;
      }
      $scope.stateCurriculumForm.$setSubmitted();

      if (!stateCurriculumFormIsValid()) {
        message.error('main.messages.form-has-errors');
        return;
      }
      createOrUpdate().then(function() {
         setVariablesForExistingStateCurriculum();
         message.info('main.messages.create.success');
      });
    };

    function createOrUpdate() {
        return $scope.stateCurriculum.id ? $scope.stateCurriculum.$update() : $scope.stateCurriculum.$save();
    }


    // validation

    function stateCurriculumFormIsValid() {
        return $scope.stateCurriculumForm.$valid && $scope.stateCurriculum.occupations.length > 0 && allOcupationsHavePModule() && allSubOccupationsHavePModule();
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
            if($scope.stateCurriculum.modules[i].module === 'KUTSEMOODUL_P' &&
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

    //  statuses

    $scope.setStatus = function(newStatus) {
      $scope.stateCurriculum.status = newStatus.code;
    };

    $scope.statuses = [
        {code: 'OPPEKAVA_STAATUS_S', button: 'main.button.status.edit'},
        {code: 'OPPEKAVA_STAATUS_K', button: 'main.button.status.confirm'},
        {code: 'OPPEKAVA_STAATUS_C', button: 'main.button.status.close'}
    ];

    $scope.filterStatusChangeOptions = function(status) {
        if(!$scope.stateCurriculum || !$scope.stateCurriculum.status || !status) {
            return false;
        }
        var currentStatusCode = $scope.stateCurriculum.status;
        var enabledStatusesToChange = {
            'OPPEKAVA_STAATUS_S': ['OPPEKAVA_STAATUS_K'],
            'OPPEKAVA_STAATUS_K': ['OPPEKAVA_STAATUS_S', 'OPPEKAVA_STAATUS_C'],
            'OPPEKAVA_STAATUS_C': []
        };
        return enabledStatusesToChange[currentStatusCode].indexOf(status.code) !== -1;
    };


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
            scope.occupations = $scope.stateCurriculum.occupations;
            $scope.stateCurriculum.occupations.forEach(function(o){
                var list = $scope.subOccupations[o].map(function(e){return e.code;});
                scope.occupations = scope.occupations.concat(list);
            });
            scope.allowedModuleTypes = ['KUTSEMOODUL_P', 'KUTSEMOODUL_Y'];

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
            });
        };
  });
