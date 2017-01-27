'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    $resource, config, message, oisFileService, QueryUtils, $route, DataUtils, $location) {
    var Curriculum = QueryUtils.endpoint('/curriculum');
    var mapResponseToModel = function(response, scope) {

        var curriculum = angular.extend({}, response);
        DataUtils.convertStringToDates(curriculum, ["validFrom", "validThru", "approval"]);

        var studyLanguageClassifiers = [];
        response.studyLanguages.forEach(function(it) {
          studyLanguageClassifiers.push(it.studyLang);
        });
        curriculum.studyLanguageClassifiers = studyLanguageClassifiers;

        var studyFormClassifiers = [];
        response.studyForms.forEach(function(it) {
          studyFormClassifiers.push(it.studyForm);
        });
        curriculum.studyFormClassifiers = studyFormClassifiers;

        var schoolDepartments = [];
        response.departments.forEach(function(it) {
          schoolDepartments.push(it.schoolDepartment);
        });
        curriculum.schoolDepartments = schoolDepartments;

        if (angular.isArray(response.jointPartners) && response.jointPartners.length > 0) {
          $scope.curriculum.supervisor = response.jointPartners[0].supervisor;
          $scope.curriculum.contractEt = response.jointPartners[0].contractEt;
          $scope.curriculum.contractEn = response.jointPartners[0].contractEn;
        }


        if (angular.isArray(response.occupations)) {
            var selectedOccupationsPartOccupations = [];
            response.occupations.forEach(function(it) {
              var element = {id: it.id, version: it.version, occupation: it.occupation,
                  specialities: it.specialities, occupationGrant: it.occupationGrant};

              if(response.occupation) {
                if(it.occupation.mainClassCode === 'KUTSE') {
                  ClassifierConnect.queryAll({connectClassifierCode: it.occupation.code}, function(result) {
                      var partOccupations = [];
                      result.forEach(function(it) {
                        if(it.classifier.mainClassCode === 'OSAKUTSE') {
                          partOccupations.push(it.classifier);
                        }
                      });
                      element.partOccupations = partOccupations;
                      selectedOccupationsPartOccupations.push(element);
                    });
                }
              } else {
                ClassifierConnect.queryAll({classifierCode: it.occupation.code}, function(result) {
                  result.forEach(function(it) {
                    element.partOccupations = [element.occupation];
                    element.occupation = it.connectClassifier;
                    selectedOccupationsPartOccupations.push(element);
                  });
                });
              }


            });

            curriculum.selectedOccupationsPartOccupations = selectedOccupationsPartOccupations;


        }


        curriculum.studyPeriodMonths = response.studyPeriod % 12;
        curriculum.studyPeriodYears = response.studyPeriod - (response.studyPeriod % 12) * 12;

        if (angular.isObject(response.iscedClass)) {
          ClassifierConnect.queryAll({classifierCode: response.iscedClass.code}, function(result) {
            result.forEach(function(it) {
              if(it.mainClassifierCode === "ISCED_SUUN") {
                curriculum.fieldOfStudy = it.connectClassifier;
              }
            });

            ClassifierConnect.queryAll({classifierCode: curriculum.fieldOfStudy.code}, function(result) {
              result.forEach(function(it) {
                if(it.mainClassifierCode === "ISCED_VALD") {
                  curriculum.areaOfStudy = it.connectClassifier;
                }
              });
              angular.extend(scope.curriculum, curriculum);
            });
          });
      }
    };


    $scope.removeFromArray = ArrayUtils.remove;

    var initialCurriculumScope = {
      validFrom: new Date(),
      selectedOccupationsPartOccupations: [],
      modules: [],
      occupations: [],
      status: {code: 'OPPEKAVA_STAATUS_S'},
      files: [],
      jointPartners: [],
      studyLanguageClassifiers: [],
      studyFormClassifiers: [],
      higher: false,
      optionalStudyCredits: 0,
      joint: false,
      occupation: false
    };
    $scope.curriculum = angular.extend({}, initialCurriculumScope);

    $scope.schoolOrigStudyLevel = [];
    QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
        response.studyLevels.forEach(function(it) {
            if(it.charAt(it.length - 3) < '5') {
                $scope.schoolOrigStudyLevel.push({code: it});
            }
        });
    });

    //edit
    var id = $route.current.params.id;
    if (angular.isDefined(id)) {
      mapResponseToModel($route.current.locals.entity, $scope);
    }

    $resource(config.apiUrl+'/school/departments').get()
      .$promise.then(function(result) {
          $scope.schoolDepartments = [];
          result.content.forEach(function(it) {
            if(angular.isArray(it.children)) {
              it.children.forEach(function(it) {
                $scope.schoolDepartments.push({id: it.id, code: it.code, nameEt: it.nameEt, nameEn: it.nameEn});
              });
            }
          });
      });

    var formScope = $scope;
    $scope.draftSelected = function() {
      if($scope.curriculum.draft.code === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showSelectDialog('views/templates/vocational.curriculum.state.curriculum.selection.dialog.html', function(selected) {
          var data = selected[0];
          data.stateCurrClass = data.stateCurrClass.code;
          $scope.stateCurriculumId = data.id;
          delete data.id;
          formScope.curriculum = angular.extend({draft: formScope.curriculum.draft}, initialCurriculumScope);
          angular.extend(formScope.curriculum, data);
        }, function() {
          $scope.curriculum.draft = {code: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB'};
        });

      } else if($scope.curriculum.draft.code === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showSelectDialog('views/templates/vocational.curriculum.school.curriculum.selection.dialog.html', function(selected) {
          var data = selected[0];
          delete data.id;
          formScope.curriculum = angular.extend({draft: formScope.curriculum.draft}, initialCurriculumScope);
          angular.extend(formScope.curriculum, data);
        }, function() {
          $scope.curriculum.draft = {code: 'OPPEKAVA_LOOMISE_VIIS_PUUDUB'};
        });
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draft.code === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');
      if($scope.isDraftEmployerSupportLetter) {
        $scope.curriculum.occupation = false;
      }
    };

    $scope.isOccupationChanged = function() {
      $scope.curriculum.selectedOccupationsPartOccupations = [];
    };

    $scope.openAddOccupationPartOccupationsDialog = function(editValues) {
      if($scope.curriculum.occupation) {
        var DialogController = function(scope) {

          var occupationSelected = function() {
              scope.data.selectedSpecialities = {};

              if (angular.isDefined(editValues)) {
                editValues.specialities.forEach(function(it){
                  scope.data.selectedSpecialities[it.speciality.code] = true;
                });
              }

              if(angular.isDefined(scope.data.occupation)) {
                ClassifierConnect.queryAll({connectClassifierCode: scope.data.occupation.code}, function(result) {
                  var specialities = [];
                  var partOccupations = [];
                  result.forEach(function(it) {
                    if(it.classifier.mainClassCode === 'SPETSKUTSE') {
                      specialities.push({speciality: it.classifier});
                    }
                    if(it.classifier.mainClassCode === 'OSAKUTSE') {
                      partOccupations.push(it.classifier);
                    }
                  });
                  scope.data.specialities = specialities;
                  scope.data.partOccupations = partOccupations;
                });
            }
          };
          scope.$watch('data.occupation', function () {
              occupationSelected();
          });

          if (angular.isDefined(editValues)) {
              scope.data.occupation = editValues.occupation;
              scope.data.occupationGrant = editValues.occupationGrant;
              scope.data.id = editValues.id;
              scope.data.version = editValues.version;
            }
        };



        dialogService.showDialog('views/templates/vocational.curriculum.occupation.add.dialog.html', DialogController, function(data) {
          var selectedSpecialities = [];
          data.specialities.forEach(function(occupationSpeciality) {
            if(data.selectedSpecialities[occupationSpeciality.speciality.code]) {
              selectedSpecialities.push(occupationSpeciality);
            }
          });
          if (angular.isDefined(data.id)) {
            $scope.curriculum.selectedOccupationsPartOccupations.forEach(function(it){
              if(it.id === data.id) {
                angular.extend(it, data);
                it.specialities = selectedSpecialities;
              }
            });
          } else {
            var newOccupation = true;
            $scope.curriculum.selectedOccupationsPartOccupations.forEach(function(it){
              if(it.occupation.code === data.occupation.code){
                newOccupation = false;
              }
            });
            if(newOccupation) {
              var element = {occupation: data.occupation, partOccupations: data.partOccupations,
                  specialities: selectedSpecialities, occupationGrant: data.occupationGrant};
                $scope.curriculum.selectedOccupationsPartOccupations.push(element);
            }
          }
        });
      } else {
        var PartOccupationDialogController = function(scope) {
          if (angular.isDefined(editValues)) {
              scope.data.occupation = editValues.occupation;
              scope.data.partOccupationAquired = editValues.partOccupations[0];
              scope.data.id = editValues.id;
              scope.data.version = editValues.version;
            }
        };
        dialogService.showDialog('views/templates/vocational.curriculum.partOccupation.add.dialog.html', PartOccupationDialogController, function(data) {
          if(angular.isDefined(data.id)){
            $scope.curriculum.selectedOccupationsPartOccupations.forEach(function(it){
              if (it.id === data.id) {
                it.occupation = data.occupation;
                it.partOccupations = [data.partOccupationAquired];
              }
            });
          } else {
            var element = {occupation: data.occupation, partOccupations: [data.partOccupationAquired]};
            var newOccupation = true;
            $scope.curriculum.selectedOccupationsPartOccupations.forEach(function(it){
              if (it.partOccupations[0].code === data.partOccupationAquired.code) {
                newOccupation = false;
              }
            });

            if(newOccupation){
              $scope.curriculum.selectedOccupationsPartOccupations.push(element);
            }
          }
        });
      }


    };


    $scope.openAddModuleDialog = function(editValues) {
      var DialogController = function(scope) {

        scope.data.outcomes = [];
        scope.data.occupations = [];
        scope.occupationsSelected = {};
        scope.partOccupationsSelected = {};
        scope.specialitiesSelected = {};

        if (angular.isDefined(editValues)) {
          scope.data = angular.extend({}, editValues);
          if (angular.isArray(editValues.occupations)) {
            editValues.occupations.forEach(function(it) {
              if(it.occupation.mainClassCode === 'OSAKUTSE') {
                scope.partOccupationsSelected[it.occupation.code] = true;
              } else if(it.occupation.mainClassCode === 'KUTSE') {
                scope.occupationsSelected[it.occupation.code] = true;
              } else if(it.occupation.mainClassCode === 'SPETSKUTSE') {
                scope.specialitiesSelected[it.occupation.code] = true;
              }
            });
          }
        }

        scope.selectAll = function(isSelected, occupationPartOccupationSpecializeSelected) {
          var occupation = occupationPartOccupationSpecializeSelected.occupation;
          if(angular.isArray(occupationPartOccupationSpecializeSelected.partOccupations)) {
            occupationPartOccupationSpecializeSelected.partOccupations.forEach(function(it) {
              scope.partOccupationsSelected[it.code] = isSelected;
            });
          }
          if(angular.isArray(occupationPartOccupationSpecializeSelected.specialities)) {
            occupationPartOccupationSpecializeSelected.specialities.forEach(function(it) {
              scope.specialitiesSelected[it.code] = isSelected;
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
            if(it.partOccupations) {
              it.partOccupations.forEach(function(it) {
                scope.connectClassifierCodeValuesForCompetenceSelection.push(it.code);
              });
            }
            if(it.specialities) {
              it.specialities.forEach(function(it) {
                scope.connectClassifierCodeValuesForCompetenceSelection.push(it.code);
              });
            }
          });
        }

        scope.addModuleStudyOutcome = function() {
          scope.data.outcomes.push({outcomeEt: scope.data.outcomeEt, outcomeEn: scope.data.outcomeEn});
          scope.data.outcomeEt = undefined;
          scope.data.outcomeEn = undefined;
        };
      };

      dialogService.showDialog('views/templates/vocational.curriculum.module.add.dialog.html', DialogController, function(data, scope) {
        var occupations = [];
        scope.occupationPartOccupationSpecializeSelected.forEach(function(it) {
          if(scope.occupationsSelected[it.occupation.code]) {
            occupations.push({occupation: it.occupation});
          }
          it.partOccupations.forEach(function(partOccupation){
            if(scope.partOccupationsSelected[partOccupation.code]) {
              occupations.push({occupation: partOccupation});
            }
          });
          it.specialities.forEach(function(speciality){
            if(scope.specialitiesSelected[speciality.code]) {
              occupations.push({occupation: speciality});
            }
          });
        });

        if(angular.isDefined(data.id)) {
          $scope.curriculum.modules.forEach(function(it) {
            if (it.id === data.id) {
              angular.extend(it, data);
              var savedOccupations = {};
              data.occupations.forEach(function(it) {
                  savedOccupations[it.occupation.code] = it;
              });
              var removedOccupations = [];
              occupations.forEach(function(it) {
                if(angular.isObject(savedOccupations[it.occupation.code])) {
                  removedOccupations.push(it);
                  occupations.push(savedOccupations[it.occupation.code]);
                }
              });
              removedOccupations.forEach(function(it) {
                ArrayUtils.remove(occupations, it);
              });
              it.occupations = occupations;
              renderModules();

            }
          });
        } else {
          data.occupations = occupations;
          $scope.curriculum.modules.push(data);
        }
      });
    };
    var renderModules = function() {
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
    };
    $scope.$watchCollection('curriculum.modules', renderModules);

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

    $scope.addJointPartner = function () {
     if(!$scope.curriculum.jointPartnerAbroadSchool && !$scope.curriculum.jointPartnerEhisSchool) {
       return;
     }

      var jointPartner = {abroad: !$scope.curriculum.jointSchoolEstonian};
      if (jointPartner.abroad) {
        jointPartner.nameEt = $scope.curriculum.jointPartnerAbroadSchool;
        jointPartner.nameEn = $scope.curriculum.jointPartnerAbroadSchool;
      } else {
        jointPartner.ehisSchool = $scope.curriculum.jointPartnerEhisSchool;
      }
      $scope.curriculum.jointPartners.push(jointPartner);
      $scope.curriculum.jointPartnerAbroadSchool = undefined;
      $scope.curriculum.jointPartnerEhisSchool = undefined;
    };

    $scope.getAddedJointEhisSchools = function() {
      var ehisSchools = [];
      $scope.curriculum.jointPartners.forEach(function(it) {
        if(!it.abroad) {
          ehisSchools.push(it.ehisSchool);
        }
      });
      return ehisSchools;
    };

    var addSharedInformationToJointPartners = function () {
      $scope.curriculum.jointPartners.forEach(function(it) {
        it.contractEt = $scope.curriculum.contractEt;
        it.contractEn = $scope.curriculum.contractEn;
        it.supervisor = $scope.curriculum.supervisor;
      });
    };

    var addOccupations = function() {
      $scope.curriculum.occupations = [];
      if(angular.isArray($scope.curriculum.selectedOccupationsPartOccupations)) {
        $scope.curriculum.selectedOccupationsPartOccupations.forEach(function(it) {
          if($scope.curriculum.occupation) {
            $scope.curriculum.occupations.push(it);
          } else {
            //part occupation was selected
            var  partOccupation = angular.extend({}, it);
            partOccupation.occupation = it.partOccupations[0];
            $scope.curriculum.occupations.push(partOccupation);
          }

        });
      }
    };

    $scope.save = function() {
      $scope.vocationalCurriculumForm.$setSubmitted();
      if($scope.vocationalCurriculumForm.$valid) {
        //before save collect some data;
        addOccupations();
        addSharedInformationToJointPartners();

        var curriculum = new Curriculum($scope.curriculum);
        if (angular.isDefined($scope.curriculum.id)) {
          curriculum.$update().then(function() {
            message.info('main.messages.create.success');
            mapResponseToModel(curriculum, $scope);
          });
        } else {
          curriculum.$save().then(function() {
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+curriculum.id+'/edit');
          });
        }
      } else {
          console.log($scope.vocationalCurriculumForm.$error);
      }
    };

  });
