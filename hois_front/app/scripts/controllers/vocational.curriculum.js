'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, StateCurriculum, Curriculum,
    dialogService, ClassifierConnect, ArrayUtils, $resource, config, message, $http, oisFileService, QueryUtils, $route, DataUtils) {

    var mapResponseToModel = function(response, scope) {
        DataUtils.convertStringToDates(response, ["validFrom", "validThru"]);
        scope.curriculum = angular.extend(scope.curriculum, response);
        scope.curriculum.studyLanguages.forEach(function(it) {
          scope.curriculum.studyLanguageClassifiers.push(it.studyLang);
        });
        scope.curriculum.studyForms.forEach(function(it) {
          scope.curriculum.studyFormClassifiers.push(it.studyForm);
        });
    };

    $scope.removeFromArray = ArrayUtils.remove;

    var initialCurriculumScope = {
      validFrom: new Date(),
      selectedOccupationsPartOccupations: [],
      modules: [],
      status: {code: 'OPPEKAVA_STAATUS_S'},
      files: [],
      jointPartners: [],
      studyLanguageClassifiers: [],
      studyFormClassifiers: [],
      higher: false,
      optionalStudyCredits: 0,
      occupation: false,
      joint: false
    };
    $scope.curriculum = angular.extend({}, initialCurriculumScope);

    //edit
    var id = $route.current.params.id;
    if (angular.isDefined(id)) {
      var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
        CurriculumEndpoint.get({id: id}).$promise.then(function(response) {
        mapResponseToModel(response, $scope);
      });
    }

    $resource(config.apiUrl+'/school/departments').get()
      .$promise.then(function(result) {
          $scope.schoolDepartments = [];
          result.content.forEach(function(it) {
            $scope.schoolDepartments.push({id: it.id, code: it.code, nameEt: it.nameEt, nameEn: it.nameEn});
          });
      });

    var formScope = $scope;
    $scope.draftSelected = function() {
      if($scope.curriculum.draft.code === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showSelectDialog('views/templates/vocational.curriculum.state.curriculum.selection.dialog.html', StateCurriculum, function(selected) {
          var data = selected[0];
          data.stateCurrClass = data.stateCurrClass.code;
          delete data.id;
          formScope.curriculum = angular.extend({draft: formScope.curriculum.draft}, initialCurriculumScope);
          angular.extend(formScope.curriculum, data);
        });

      } else if($scope.curriculum.draft.code === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showSelectDialog('views/templates/vocational.curriculum.school.curriculum.selection.dialog.html', Curriculum, function(selected) {
          var data = selected[0];
          delete data.id;
          formScope.curriculum = angular.extend({draft: formScope.curriculum.draft}, initialCurriculumScope);
          angular.extend(formScope.curriculum, data);
        });
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draftCode === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');
      if($scope.isDraftEmployerSupportLetter) {
        $scope.curriculum.occupation = false;
      }
    };

    $scope.isOccupationChanged = function() {
      $scope.curriculum.selectedOccupationsPartOccupations = [];
    };

    $scope.openAddOccupationPartOccupationsDialog = function() {
      if($scope.curriculum.occupation) {
        var DialogController = function(scope) {
          scope.occupationSelected = function() {
              scope.data.specializations = [];
              scope.data.selectedSpecializations = [];
              scope.data.partOccupations = [];

              scope.selectSpecialization = function(isSelected, specialization) {
                if(!ArrayUtils.contains(scope.data.selectedSpecializations, specialization)){
                  scope.data.selectedSpecializations.push(specialization);
                } else {
                  ArrayUtils.remove(scope.data.selectedSpecializations, specialization);
                }
              };

              ClassifierConnect.query({connectClassifierCode: scope.data.occupation.code}, function(pagedResult) {
                pagedResult.content.forEach(function(classifier) {
                  if(classifier.classifier.mainClassCode === 'SPETSKUTSE') {
                    scope.data.specializations.push(classifier.classifier);
                  }
                  if(classifier.classifier.mainClassCode === 'OSAKUTSE') {
                    scope.data.partOccupations.push(classifier.classifier);
                  }
                });
              });
          };
        };

        dialogService.showDialog('views/templates/vocational.curriculum.occupation.add.dialog.html', DialogController, function(data) {
          var element = {occupation: data.occupation, partOccupations: data.partOccupations,
              specializations: data.selectedSpecializations, schoolHasRighToGiveOccupation: data.schoolHasRightToGiveOccupation};
            $scope.curriculum.selectedOccupationsPartOccupations.push(element);
        });
      } else {
        dialogService.showDialog('views/templates/vocational.curriculum.partOccupation.add.dialog.html', null, function(data) {
          var element = {occupation: data.occupation, partOccupations: [data.partOccupationAquired]};
          $scope.curriculum.selectedOccupationsPartOccupations.push(element);
        });
      }
    };


    $scope.openAddModuleDialog = function() {
      var DialogController = function(scope) {
        scope.data.studyOutcomes = [];
        scope.data.occupations = [];
        scope.occupationsSelected = {};
        scope.partOccupationsSelected = {};
        scope.specializationsSelected = {};

        scope.selectAll = function(isSelected, occupationPartOccupationSpecializeSelected) {
          var occupation = occupationPartOccupationSpecializeSelected.occupation;
          if(angular.isArray(occupationPartOccupationSpecializeSelected.partOccupations)) {
            occupationPartOccupationSpecializeSelected.partOccupations.forEach(function(it) {
              scope.partOccupationsSelected[it.code] = isSelected;
            });
          }
          if(angular.isArray(occupationPartOccupationSpecializeSelected.specializations)) {
            occupationPartOccupationSpecializeSelected.specializations.forEach(function(it) {
              scope.specializationsSelected[it.code] = isSelected;
            });
          }
          scope.occupationsSelected[occupation.code] = isSelected;
        };

        //populate competence select with data
        scope.connectClassifierCodeValuesForCompetenceSelection = [];
        if(angular.isArray($scope.curriculum.selectedOccupationsPartOccupations)) {
          scope.occupationPartOccupationSpecializeSelected = $scope.curriculum.selectedOccupationsPartOccupations;
          scope.occupationPartOccupationSpecializeSelected.forEach(function(it) {
            scope.connectClassifierCodeValuesForCompetenceSelection.push(it.occupation.code);
            if(it.partOccupations){
              it.partOccupations.forEach(function(it) {
                scope.connectClassifierCodeValuesForCompetenceSelection.push(it.code);
              });
            }
            if(it.specializations) {
              it.specializations.forEach(function(it) {
                scope.connectClassifierCodeValuesForCompetenceSelection.push(it.code);
              });
            }
          });
        }


        scope.openAddModuleStudyOutcomeDialog = function() {
          dialogService.showDialog('views/templates/vocational.curriculum.study.outcome.add.dialog.html', null,
          function(data){
            scope.data.studyOutcomes.push(data);
          });
        };
      };

      dialogService.showDialog('views/templates/vocational.curriculum.module.add.dialog.html', DialogController, function(data, scope) {
        data.occupations = [];

        scope.occupationPartOccupationSpecializeSelected.forEach(function(it) {
          if(scope.occupationsSelected[it.occupation.code]) {
            data.occupations.push({occupation: it.occupation});
          }
          it.partOccupations.forEach(function(partOccupation){
            if(scope.partOccupationsSelected[partOccupation.code]) {
              data.occupations.push({occupation: partOccupation});
            }
          });
          it.specializations.forEach(function(specialization){
            if(scope.specializationsSelected[specialization.code]) {
              data.occupations.push({occupation: specialization});
            }
          });
        });
        $scope.curriculum.modules.push(data);
      });
    };

    $scope.$watchCollection('curriculum.modules', function() {
        $scope.occupationModuleTypesModules = {};
        $scope.curriculum.modules.forEach(function(curriculumModule) {
          curriculumModule.occupations.forEach(function(curriculumModuleOccupation) {
            var occupation = curriculumModuleOccupation.occupation;
            if(!angular.isObject($scope.occupationModuleTypesModules[occupation.code])){
              $scope.occupationModuleTypesModules[occupation.code] = {occupation: occupation, moduleTypes: {}};
            }
            if(!angular.isObject($scope.occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code])){
              $scope.occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code] = {moduleType: curriculumModule.module, modules: []};
            }
            $scope.occupationModuleTypesModules[occupation.code].moduleTypes[curriculumModule.module.code].modules
              .push(curriculumModule);
          });
        });
    });

    $scope.openAddFileDialog = function() {
      dialogService.showDialog('views/templates/vocational.curriculum.file.add.dialog.html', null, function(data) {
        data.sendEhis = false;
        data.isEhis = false;
        oisFileService.getFromLfFile(data.file[0], function(file) {
          data.oisFile = file;
          $scope.curriculum.files.push(data);
        });
      });
    };

    $scope.fileUrl = function(oisFile) {
      return oisFileService.getFileUrl(oisFile);
    };

    var addJointPartners = function() {
      $scope.curriculum.jointPartners = [];
      if ($scope.curriculum.jointSchoolIsEstonian && angular.isArray($scope.curriculum.jointPartnersEhisSchools)) {
        $scope.curriculum.jointPartnersEhisSchools.forEach(function(it) {
          $scope.curriculum.jointPartners.push({ehisSchool: it, abroad: false,
            contractEt: $scope.curriculum.contractEt,  contractEn: $scope.curriculum.contractEn, supervisor: $scope.curriculum.supervisor});
        });
      } else if (angular.isArray($scope.curriculum.jointPartnersForeign)){
        $scope.curriculum.jointPartnersForeign.forEach(function(it) {
          $scope.curriculum.jointPartners.push({nameEt: it, nameEn: it, abroad: true,
            contractEt: $scope.curriculum.contractEt,  contractEn: $scope.curriculum.contractEn, supervisor: $scope.curriculum.supervisor});
        });
      }
    };

    $scope.save = function() {
      $scope.vocationalCurriculumForm.$setSubmitted();
      if($scope.vocationalCurriculumForm.$valid) {
        //before save collect some data;
        addJointPartners();
        new Curriculum($scope.curriculum).save().$promise
        .then(function(response) {
          message.info('main.messages.create.success');
          mapResponseToModel(response, $scope);
        })
        .catch(function(){
          message.error('main.messages.create.failure');
        });
      } else {
          //console.log($scope.vocationalCurriculumForm.$error);
      }
    };

  });
