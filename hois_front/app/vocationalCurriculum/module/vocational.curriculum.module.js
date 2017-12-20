'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleController', function ($scope, $route, QueryUtils, dialogService, message, ArrayUtils, $location, $rootScope, ClassifierConnect) {

    $scope.curriculumId = $route.current.params.curriculum;
    var id = $route.current.params.id;
    var baseUrl = '/curriculumModule';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    var initial = {
      curriculum: $scope.curriculumId,
      occupations: [],
      competences: [],
      outcomes: [],
      practice: false
    }

    $scope.occupationsSelected = {};
    $scope.specialitiesSelected = {};

    $scope.competences = [];

    QueryUtils.endpoint(baseUrl + '/competences/curriculum/' + $scope.curriculumId).query().$promise.then(function(response){
      $scope.competences = response;
      if($scope.vocationalCurriculumModuleForm) {
        $scope.vocationalCurriculumModuleForm.$setPristine();
      }
    });

    function dtoToModel(response) {
      $scope.module = response;
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

    if(id) {
      Endpoint.get({id: id}).$promise.then(dtoToModel);
    } else {
      $scope.module = new Endpoint(initial);
      QueryUtils.endpoint(baseUrl + '/curriculum/' + $scope.curriculumId).search().$promise.then(function(response){
        $scope.module.curriculumOccupations = response.occupations;
        $scope.module.occupation = response.occupation;
        $scope.module.canHaveOccupations = response.canHaveOccupations;
      });
    }

    $scope.possibleTypes = [];
    QueryUtils.endpoint(baseUrl + '/types').query({module: id, curriculum: $scope.curriculumId}).$promise.then(function(response){
      $scope.possibleTypes = response;
    });

    $scope.isEditingOutcome = false;
    $scope.editedOutcome = null;


    $scope.removeFromArray = function(array, item) {
      dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
          ArrayUtils.remove(array, item);
          $scope.vocationalCurriculumModuleForm.$setDirty();
      });
    };

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

    function validationPassed() {
      $scope.vocationalCurriculumModuleForm.$setSubmitted();
      if(!$scope.vocationalCurriculumModuleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
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

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'module.messages.prompt.delete'}, function() {
        $scope.module.$delete().then(function() {
          message.info('main.messages.delete.success');
          $rootScope.back('#/vocationalCurriculum/' + $scope.module.curriculum + '/edit')
        });
      });
    };

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
        // module cannot be both in specialty and partOccupation of the same occupation
        for(var j = 0; j < occupations.length; j++) {
            var specialities2 = occupations[j].specialities;
            var partOccupations = occupations[j].partOccupations;
            if(specialities2 && partOccupations) {
                if(anySpecAdded(specialities2) && anyPartOccupationAdded(partOccupations)) {
                    return null;
                }
            }
        }
        return true;
    }

    $scope.occupationsValid = occupationsAreValid();

    $scope.validateOccupations = function() {
          $scope.occupationsValid = occupationsAreValid();
    };

  });
