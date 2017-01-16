'use strict';

/**
 * NB! in terminology of this controller by (Classifier) occupations.children I mean its osakutsed and spetskutsed 
 * Another term for this is subocccupation
 * 
 * TODO: consider suboccupations in validation!
 */
angular.module('hitsaOis')
  .controller('StateCurriculumModuleController', function ($scope, Classifier, StateCurriculum, $location) {

    if(!StateCurriculum.current) {
        $location.path('/stateCurriculum/new');
    } else {
      //console.log("this state curriculum: ");
      //console.log(StateCurriculum.current);
      StateCurriculum.cameFromModuleForm = true;
    }
    
    function getEditableModule() {
      if(StateCurriculum.editableModule){
        $scope.module = StateCurriculum.editableModule;
      }
    }
    getEditableModule();

    function setMainFormsUrl() {
      $scope.mainFormsUrl = '';
      if(StateCurriculum.current && StateCurriculum.current.id) {
        $scope.mainFormsUrl = '/stateCurriculum/' + StateCurriculum.current.id + '/edit';
      } else {
        $scope.mainFormsUrl = '/stateCurriculum/new';
      }
      //console.log("mainFormsUrl: " + $scope.mainFormsUrl);
    }
    setMainFormsUrl();

    $scope.filterChildren = function(child) {
      return child.mainClassCode === 'OSAKUTSE' || child.mainClassCode === 'SPETSKUTSE';
    };

    // change validation, as children can be selected as well
    $scope.checkOccupationsValidity = function() {
      if(!$scope.occupations) {
        return;
      }
      for(var i = 0; i < $scope.occupations.length; i++) {
        if ($scope.occupations[i].selected) {
          $scope.occupationSelected = true;
          return;
        }
      }
      $scope.occupationSelected = false;
    };

    function getOccupations() {
      $scope.occupations = StateCurriculum.current ? StateCurriculum.current.occupations : undefined;
      //console.log("occupations:");
      //console.log($scope.occupations);
      markSelectedOccupations();
      markSelectedSubOccupations();
      $scope.checkOccupationsValidity();
    }
    getOccupations();

    function markSelectedOccupations() {
      if($scope.occupations) {
        for(var i = 0; i < $scope.occupations.length; i++) {
          $scope.occupations[i].selected = false;
        }
        if($scope.module && $scope.module.moduleOccupations) {
          for(var j = 0; j < $scope.occupations.length; j++) {
            for(var k = 0; k < $scope.module.moduleOccupations.length; k++) {
              if($scope.module.moduleOccupations[k].occupation.code === $scope.occupations[j].occupation.code) {
                $scope.occupations[j].selected = true;
              }
            }
          }
        }
      }
    }

    function markSelectedSubOccupations() {
      if($scope.occupations) {
        for(var i = 0; i < $scope.occupations.length; i++) {
          for(var j = 0; j < $scope.occupations[i].occupation.children.length; j++) {
            $scope.occupations[i].occupation.children[j].selected = false;
          }
        }
        if($scope.module && $scope.module.moduleOccupations) {
          for(var k = 0; k < $scope.occupations.length; k++) {
            for(var m = 0; m < $scope.occupations[k].occupation.children.length; m++) {
              for(var n = 0; n < $scope.module.moduleOccupations.length; n++) {
                if($scope.module.moduleOccupations[n].occupation.code === $scope.occupations[k].occupation.children[m].code) {
                  $scope.occupations[k].occupation.children[m].selected = true;
                }
              }
            }
          }
        }
      }
    }
    
    $scope.goToMainForm = function() {
      StateCurriculum.editableModule = undefined;
      $location.path($scope.mainFormsUrl);
    };
    //TODO: get rid of this function
    function success2() {
    }

    function getModuleTypes() {
      var query = {
        mainClassCode: 'KUTSEMOODUL'
      };
      Classifier.queryForDropdown(query, success2).$promise.then(function(response) {
        $scope.listOfModuleTypes = response;
        $scope.listOfModuleTypes = filterModuleTypes();
      });
    }
    getModuleTypes();

    function filterModuleTypes() {
      return $scope.listOfModuleTypes.filter(function(e) { return e.code === 'KUTSEMOODUL_P' || e.code === 'KUTSEMOODUL_Y'; });
    }

    $scope.save = function() {
      $scope.stateCurriculumModuleForm.$setSubmitted();
      //console.log('form is valid: '+ $scope.stateCurriculumModuleForm.$valid);
      if($scope.stateCurriculumModuleForm.$valid) {
        addOccupations();
        addSubOccupations();
        if(!StateCurriculum.editableModule) {
          StateCurriculum.current.modules.push($scope.module);
        }
        //console.log("new module: ");
        //console.log($scope.module);
        $scope.goToMainForm();
      } else {
        //console.log("form invalid!");
      }
    };

    //TODO: old module occupations are removed and added again. Is it okay?
    function addOccupations() {
      $scope.module.moduleOccupations = [];

      if($scope.occupations) {
        for(var i = 0; i < $scope.occupations.length; i++) {
          if($scope.occupations[i].selected) {
            var newModuleOccupation = {};
            newModuleOccupation.type = 'O';
            newModuleOccupation.occupation = $scope.occupations[i].occupation;
            $scope.module.moduleOccupations.push(newModuleOccupation);
          }
        }
      }
    }
    
    function addSubOccupations() {
      for(var i = 0; i < $scope.occupations.length; i++) {
        for(var j = 0; j < $scope.occupations[i].occupation.children.length; j++) {
          if($scope.occupations[i].occupation.children[j].selected) {
            var newModuleOccupation = {};
            newModuleOccupation.type = 'O';
            newModuleOccupation.occupation = $scope.occupations[i].occupation.children[j];
            $scope.module.moduleOccupations.push(newModuleOccupation);
          }
        }
      }
    }
  });
