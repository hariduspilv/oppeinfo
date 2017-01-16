'use strict';

/**
 * NB! in terminology of this controller by (Classifier) occupations.children I mean its osakutsed and spetskutsed
 * TODO: Valid StateCurriculum name validation!
 * TODO: Suboccupations validation
 */
angular.module('hitsaOis')
  .controller('StateCurriculumController', function ($http, $route, $scope, StateCurriculum, message, Classifier, $location, classifierAutocomplete, stateCurriculumOccupationValidator) {

    StateCurriculum.editableModule = undefined;

// ---------- get current data (needed for moving between forms)

    function getStateCurriculum() {
      if(StateCurriculum.current) {
        if(StateCurriculum.cameFromModuleForm && reallyCameFrommodulesForm()) {
          $scope.stateCurriculum = StateCurriculum.current;
          $scope.areOccupationsValid = stateCurriculumOccupationValidator.validate($scope.stateCurriculum.occupations);
        } else {
          StateCurriculum.current = undefined;
          getStateCurriculum();
        }
      } else if($route.current.params.id) {
        getEditableStateCurriculum();
      } else {
        initializeNewStateCurriculum();
        $scope.areOccupationsValid = false;
      }
      StateCurriculum.cameFromModuleForm = false;
    }
    getStateCurriculum();

    function reallyCameFrommodulesForm() {
      if($route.current.params.id && StateCurriculum.current.id) {
        return $route.current.params.id === StateCurriculum.current.id.toString();
      } else if ($route.current.params.id && !StateCurriculum.current.id ||
      !$route.current.params.id && StateCurriculum.current.id) {
        return false;
      } else {
        return true;
      }
    }

    function getEditableStateCurriculum() {
      StateCurriculum.get($route.current.params.id).$promise.then(function(response) {
        $scope.stateCurriculum = response;
        setDates();
        createListsOfModulesAndOccupations();
        StateCurriculum.current = $scope.stateCurriculum;
        $scope.areOccupationsValid = stateCurriculumOccupationValidator.validate($scope.stateCurriculum.occupations);
        fillCascadeDropdowns();
        //console.log("State Curriculum from DB");
        //console.log($scope.stateCurriculum);
      });
    }

    function setDates() {
      if($scope.stateCurriculum.validThru) {
        $scope.stateCurriculum.validThru = new Date($scope.stateCurriculum.validThru);
      }
      if($scope.stateCurriculum.validFrom) {
        $scope.stateCurriculum.validFrom = new Date($scope.stateCurriculum.validFrom);
      }
    }

    function fillCascadeDropdowns() {
      Classifier.getParentsWithMainClass('ISCED_SUUN', $scope.stateCurriculum.iscedClass.code).$promise.then(function(response) {
        $scope.stateCurriculum.iscedSuun = response[0].code;
        getOsakutsedAndSpetsialiseerumised();
        return response[0];
      }).then(function(result){
          Classifier.getParentsWithMainClass('ISCED_VALD', result.code).$promise.then(function(response) {
          $scope.stateCurriculum.iscedVald = response[0].code;
          $scope.getClassifierSuuns();
          $scope.getClassifierGroups();
        });
      });
    }

    function getOsakutsedAndSpetsialiseerumised() {
        for(var i = 0; i < $scope.stateCurriculum.occupations.length; i++) {
          getChildren($scope.stateCurriculum.occupations[i].occupation);
        }
    }

    function createListsOfModulesAndOccupations() {
      if(!$scope.stateCurriculum.modules) {
        $scope.stateCurriculum.modules = [];
      }
      if(!$scope.stateCurriculum.occupations) {
        $scope.stateCurriculum.occupations = [];
      }
    }

    function initializeNewStateCurriculum() {
        StateCurriculum.current = {};
        StateCurriculum.current.occupations = [];
        StateCurriculum.current.modules = [];
        StateCurriculum.current.validFrom = new Date();
        $scope.stateCurriculum = StateCurriculum.current;
        getStatuses().then(function(){
          setDefaultStatus();
        });
    }

    function getStatuses() {
      return Classifier.get('OPPEKAVA_STAATUS').$promise.then(function(response) {
        $scope.statuses = response.children;
        fillStatuses();
        });
    }
    getStatuses();

    //TODO: move "Kinnita", "Sulge", and "Muuda" to et.json
    function fillStatuses() {
      for(var i = 0; i < $scope.statuses.length; i++) {
        if($scope.statuses[i].code === 'OPPEKAVA_STAATUS_S') {
          $scope.statuses[i].change = 'Muuda';
        } else if ($scope.statuses[i].code === 'OPPEKAVA_STAATUS_K') {
          $scope.statuses[i].change = 'Kinnita';
        } else if ($scope.statuses[i].code === 'OPPEKAVA_STAATUS_C') {
          $scope.statuses[i].change = 'Sulge';
        }
      }
    }

    // Method is overcomplicated for current reqiurements,
    // but let it be
    $scope.filterStatuses = function(statusClassifier) {

      var sis = ['OPPEKAVA_STAATUS_K'];
      var kinn = ['OPPEKAVA_STAATUS_C'];

      if(!$scope.stateCurriculum || !$scope.stateCurriculum.status || !statusClassifier) {
        return false;
      }
      var status = statusClassifier.code;
      var currentStatus = $scope.stateCurriculum.status.code;

      if(currentStatus === 'OPPEKAVA_STAATUS_S') {
        return sis.indexOf(status) !== -1;
      } else if(currentStatus === 'OPPEKAVA_STAATUS_K') {
        return kinn.indexOf(status) !== -1;
      } else {
        return false;
      }
    };

    function setDefaultStatus() {
      if(!$scope.stateCurriculum.status) {
        $scope.stateCurriculum.status = $scope.statuses.filter(function(e) { return e.code === 'OPPEKAVA_STAATUS_S'; })[0];
      }
    }

    $scope.setStatus = function(status) {
      $scope.stateCurriculum.status = status;
    };

    $scope.saveForm = function(){
      StateCurriculum.current = $scope.stateCurriculum;
    };

    // -----------saving to db

    function create() {

      $scope.stateCurriculumForm.$setSubmitted();

      //console.log("state curriculum to create...");
      //console.log($scope.stateCurriculum);

      StateCurriculum.create($scope.stateCurriculum).then(function(response) {
        message.info('main.messages.create.success');
        //console.log("Created!");
        //console.log(response);
        $scope.stateCurriculum = response.data;
        setDates();
        createListsOfModulesAndOccupations();
        fillCascadeDropdowns();
      });
    }

    function update() {
      //console.log("updating...");
      //console.log($scope.stateCurriculum);
      StateCurriculum.update($scope.stateCurriculum).then(function() {
        message.info('main.messages.update.success');
        //console.log("Updated!");
        //console.log(response);
      });
    }

    $scope.validate = function() {
      $scope.stateCurriculumForm.$setSubmitted();
      //console.log('form is valid: '+ $scope.stateCurriculumForm.$valid);
      if($scope.stateCurriculumForm.$valid) {
        if($scope.stateCurriculum.id) {
          update();
        } else {
          create();
        }
      } else {
        //console.log("form invalid!");
      }
    };



    $scope.delete = function() {
      StateCurriculum.current = undefined;
      StateCurriculum.delete($scope.stateCurriculum.id).then(function() {
        $location.path('/stateCurriculum');
      });
    };

    $scope.abort = function() {
      StateCurriculum.current = undefined;
      $location.path('/stateCurriculum');
    };



// ------------dropdowns and autocomplete
// TODO: when 2nd select (õppekava, or oppesuund) is changed, 3rd one sets to null but not shown as incorrect!

    $scope.getClassifierValds = function() {
      var query = {
        mainClassCode: 'ISCED_VALD'
      };
      Classifier.queryForDropdown(query).$promise.then(function(response) {
        $scope.listOfClassifierValds = response;
      });
    };
    $scope.getClassifierValds();

    $scope.resetAndGetClassifierSuuns = function() {
      $scope.stateCurriculum.iscedClassCode = undefined;
      $scope.stateCurriculum.iscedClass = undefined;
      $scope.stateCurriculum.iscedSuun = undefined;
      $scope.getClassifierSuuns();
    };

    $scope.getClassifierSuuns = function() {
      if($scope.stateCurriculum && $scope.stateCurriculum.iscedVald) {
        Classifier.getChildren($scope.stateCurriculum.iscedVald).$promise.then(function(response) {
          $scope.listOfClassifierSuuns = response;
        });
      }
    };
    $scope.getClassifierSuuns();

    $scope.getClassifierGroups = function() {
      if($scope.stateCurriculum && $scope.stateCurriculum.iscedSuun) {
        Classifier.getChildren($scope.stateCurriculum.iscedSuun).$promise.then(function(response) {
          $scope.listOfClassifierGroups = response;
        });
      }
    };
    $scope.getClassifierGroups();

    $scope.querySearch = function(queryText) {
      return classifierAutocomplete.searchByName(queryText, 'KUTSE');
    };

    $scope.addOcupation = function(item) {
      if(item) {
        if(occupationNotAlreadyAdded(item)) {
          getChildren(item);
          var occupation = {};
          occupation.occupation = item;
          $scope.stateCurriculum.occupations.push(occupation);
        } else {
          //console.log("item already added");
        }
      }
      $scope.areOccupationsValid = stateCurriculumOccupationValidator.validate($scope.stateCurriculum.occupations);
      $scope.autocompleteTouched = true;
    };

    function occupationNotAlreadyAdded(item) {
        return $scope.stateCurriculum.occupations.filter(function(e) { return e.occupation.code === item.code; }).length === 0;
    }

    function getChildren(item) {
        Classifier.getChildren(item.code).$promise.then(function(response) {
          item.children = response;
        });
    }

    $scope.removeOcupation = function(occupation) {
      $scope.stateCurriculum.occupations.splice($scope.stateCurriculum.occupations.indexOf(occupation), 1);
      $scope.areOccupationsValid = stateCurriculumOccupationValidator.validate($scope.stateCurriculum.occupations);
      removeOccupationFromModules(occupation.occupation);
      removeOccupationsSuboccupations(occupation);
    };

    function removeOccupationFromModules(occupation) {
      for(var i = $scope.stateCurriculum.modules.length - 1; i >= 0 ; i--) {
        if($scope.stateCurriculum.modules[i].moduleOccupations) {
             for(var j = $scope.stateCurriculum.modules[i].moduleOccupations.length - 1; j >= 0; j--) {
               if($scope.stateCurriculum.modules[i].moduleOccupations[j].occupation.code === occupation.code) {
                 //console.log(occupation.nameEt + " removed from " + $scope.stateCurriculum.modules[i].nameEt);
                 $scope.stateCurriculum.modules[i].moduleOccupations.splice(j, 1);
                 // remove module with no occupations left
                 if($scope.stateCurriculum.modules[i].moduleOccupations.length === 0) {
                   //console.log("module removed");
                   $scope.stateCurriculum.modules.splice(i, 1);
                 }
               }
             }
        }
      }
    }

    function removeOccupationsSuboccupations(occupation) {
      for(var i = occupation.occupation.children.length - 1; i >= 0; i--) {
        if(occupation.occupation.children[i].mainClassCode === 'OSAKUTSE' || occupation.occupation.children[i].mainClassCode === 'SPETSKUTSE') {
          //console.log("suboccupation to remove: " + occupation.occupation.children[i].nameEt);
          removeOccupationFromModules(occupation.occupation.children[i]);
        }
      }
    }

    function getListOfStateCurrClassifiers() {
      var query = {
        mainClassCode: 'EHIS_ROK'
      };
      Classifier.queryForDropdown(query).$promise.then(function(response) {
        $scope.listOfStateCurrClassifiers = response;
      });
    }
    getListOfStateCurrClassifiers();

    $scope.hasOsakutsed = function(children) {
      if(children) {
        for(var i = 0; i < children.length; i++) {
          if(children[i].mainClassCode === 'OSAKUTSE') {
            return true;
          }
        }
        return false;
      }
    };

    $scope.hasSpetsialiseerumised = function(children) {
      if(children) {
        for(var i = 0; i < children.length; i++) {
          if(children[i].mainClassCode === 'SPETSKUTSE') {
            return true;
          }
        }
        return false;
      }
    };







    // ------ display modules

    $scope.filterModules = function(occupation, moduleTypeCode) {
      return function(module1){
        for(var j = 0; j < module1.moduleOccupations.length; j++) {
          if(module1.moduleType.code === moduleTypeCode &&
          occupation.code === module1.moduleOccupations[j].occupation.code) {
            return true;
          }
        }
        return false;
      };
    };

    $scope.removeModule = function(occupation, module1) {
      for(var i = 0; i < module1.moduleOccupations.length; i++) {
        if(module1.moduleOccupations[i].occupation.code === occupation.code) {
          module1.moduleOccupations.splice(i, 1);
          $scope.areOccupationsValid = stateCurriculumOccupationValidator.validate($scope.stateCurriculum.occupations);
          break;
        }
      }
      if(module1.moduleOccupations.length === 0) {
        deleteModule(module1);
      }
    };

    function deleteModule(module1) {
      $scope.stateCurriculum.modules.splice($scope.stateCurriculum.modules.indexOf(module1), 1);
    }

    $scope.editModule = function(editableModule) {
      StateCurriculum.editableModule = editableModule;
    };




    // -----------Validation
// TODO: add suboccupations validation
    $scope.autocompleteTouched = false;


    $scope.hasAnyOccupation = function() {
      return stateCurriculumOccupationValidator.hasAnyOccupation($scope.stateCurriculum.occupations);
    };

    $scope.hasNoPmodule = function(occupation) {
      return stateCurriculumOccupationValidator.dontHavePmodule(occupation);
    };

    $scope.nameEtUniqueQuery = {
      id: $route.current.params.id,
      paramName: 'nameEt',
      url: '/stateCurriculum/unique'
    };

    $scope.nameEnUniqueQuery = {
      id: $scope.nameEtUniqueQuery.id,
      paramName: 'nameEn',
      url: $scope.nameEtUniqueQuery.url
    };
  });
