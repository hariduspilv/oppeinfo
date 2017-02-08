'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    message, oisFileService, QueryUtils, $route, DataUtils, $location, Curriculum, $q) {
      var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
      var mapDtoToModel = function(response, scope) {

        var promises = [];
        var curriculum = angular.extend({}, response);
        DataUtils.convertStringToDates(curriculum, ["validFrom", "validThru", "approval"]);

        if (angular.isArray(response.jointPartners) && response.jointPartners.length > 0) {
          $scope.curriculum.supervisor = response.jointPartners[0].supervisor;
          $scope.curriculum.contractEt = response.jointPartners[0].contractEt;
          $scope.curriculum.contractEn = response.jointPartners[0].contractEn;
        }


        if (angular.isArray(response.occupations)) {
            response.occupations.forEach(function(it) {
              var promise = Classifier.get(it.occupation).$promise.then(function(classifier) {
                it.occupation = classifier;
              });
              promises.push(promise);

              if (angular.isArray(it.specialities)) {
                var specialityPromises = [];
                it.specialities.forEach(function(specialityCode){
                  specialityPromises.push(Classifier.get(specialityCode).$promise);
                });
                var specialitiesPromise = $q.all(specialityPromises).then(function(specialities){
                  it.specialities = specialities;
                });

                promises.push($q.all(specialityPromises));
              }
            });
        }


        curriculum.studyPeriodMonths = response.studyPeriod % 12;
        curriculum.studyPeriodYears = Math.floor(response.studyPeriod / 12);

        if (angular.isDefined(response.iscedClass)) {
          var deferred = $q.defer();

          ClassifierConnect.queryAll({classifierCode: response.iscedClass}, function(result) {
            result.forEach(function(it) {
              if(it.mainClassifierCode === "ISCED_SUUN") {
                curriculum.fieldOfStudy = it.connectClassifier.code;
              }
            })

            var innerPromise = ClassifierConnect.queryAll({classifierCode: curriculum.fieldOfStudy}, function(result) {
              result.forEach(function(it) {
                if(it.mainClassifierCode === "ISCED_VALD") {
                  curriculum.areaOfStudy = it.connectClassifier.code;
                  deferred.resolve();
                } else {
                  deferred.reject();
                }
              });
            });

          });
          promises.push(deferred.promise);
      }

      if (angular.isArray(response.modules)) {
        response.modules.forEach(function(it) {
          var promise = Classifier.get(it.module).$promise.then(function(classifier) {
            it.module = classifier;
          });
          promises.push(promise);

          if (angular.isArray(it.occupations)) {
              var occupationPromises = [];
              it.occupations.forEach(function(occupationCode){
                occupationPromises.push(Classifier.get(occupationCode).$promise);
              });
              var occupationsPromise = $q.all(occupationPromises).then(function(occupations){
                it.occupations = occupations;
              });

              promises.push($q.all(occupationsPromise));
          }

        });
      }

      $q.all(promises).then(function() {
        angular.extend(scope.curriculum, curriculum);
      });

    };/*mapDtoToModel*/

    var mapModelToDto = function(curriculumModel) {
      var dto = angular.extend({}, curriculumModel);
      if (angular.isArray(curriculumModel.occupations)) {
        curriculumModel.occupations.forEach(function(it) {
          it.occupation = it.occupation.code;
          if (angular.isArray(it.specialities)) {
            it.specialities = it.specialities.map(function(speciality){
              return speciality.code;
            });
          }
        });
      }

      if (angular.isArray(curriculumModel.modules)) {
        curriculumModel.modules.forEach(function(it){
          it.module = it.module.code;
          if(angular.isArray(it.occupations)) {
            it.occupations = it.occupations.map(function(occupation){
              return occupation.code;
            });
          }
        });
      }
      return dto;
    }

    $scope.removeFromArray = ArrayUtils.remove;

    var initialCurriculumScope = {
      validFrom: new Date(),
      selectedOccupationsPartOccupations: [],
      modules: [],
      occupations: [],
      status: "OPPEKAVA_STAATUS_S",
      consecution: "OPPEKAVA_TYPE_E",
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
    if (angular.isDefined($route.current.locals.entity)) {
      mapDtoToModel($route.current.locals.entity, $scope);
    }

    $scope.schoolDepartmentOptions = Curriculum.getSchoolDepartments();
    $scope.draftOptions = {};
    QueryUtils.endpoint('/autocomplete/classifiers').query({mainClassCode: 'OPPEKAVA_LOOMISE_VIIS'}).$promise.then(function(response) {
      response.forEach(function(it) {
        $scope.draftOptions[it.code] = it;
      });
    });

    var sanitizeDraftEntity = function(draftEntity) {
          delete draftEntity.id;
          delete draftEntity.changed;
          delete draftEntity.changedBy;
          delete draftEntity.inserted;
          delete draftEntity.insertedBy;
          delete draftEntity.status;
          delete draftEntity.origStudyLevel;
          delete draftEntity.files;
          if (angular.isObject(draftEntity.stateCurrClass)) {
            draftEntity.stateCurrClass = draftEntity.stateCurrClass.code;
          }

    };

    $scope.draftSelected = function() {
      if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showDialog('views/templates/vocational.curriculum.state.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.selectedStateCurriculumOccupation = {};
          QueryUtils.endpoint('/stateCurriculum/all').query({sort: $scope.currentLanguageNameField()+',asc'}).$promise
            .then(function(response) {
              dialogScope.stateCurriculums = response;
            });
          dialogScope.stateCurriculumSelected = function(stateCurriculumId) {
            if (angular.isNumber(stateCurriculumId)) {
              QueryUtils.endpoint('/stateCurriculum').get({id: stateCurriculumId}).$promise
              .then(function(response) {
                dialogScope.stateCurriculumOccupations = response.occupations;
                dialogScope.stateCurriculum = response;
              });
            }
          };
        },
        function(dialogScope) {
          var occupations = [];
          if (angular.isArray(dialogScope.stateCurriculum.occupations)) {
            $scope.curriculum.occupation = true;
            dialogScope.stateCurriculum.occupations.forEach(function(it) {
              if(dialogScope.selectedStateCurriculumOccupation[it.id] === true) {
                occupations.push({occupation: it.occupation.code});
              }
            });
          }
          dialogScope.stateCurriculum.occupations = occupations;
          sanitizeDraftEntity(dialogScope.stateCurriculum);
          dialogScope.stateCurriculum.draft = $scope.curriculum.draft;
          mapDtoToModel(dialogScope.stateCurriculum, $scope);
        }, function() {
          $scope.curriculum.draft = 'OPPEKAVA_LOOMISE_VIIS_PUUDUB';
        });

      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showDialog('views/templates/vocational.curriculum.school.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.curriculumSelected = [];

          QueryUtils.createQueryForm(dialogScope, '/curriculum', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt',
            school: $scope.currentUser.school.id, isVocational: false});
          dialogScope.loadData();
        },
        function(dialogScope) {
          QueryUtils.endpoint('/curriculum').get({id: dialogScope.curriculumSelected[0].id}).$promise
              .then(function(response) {
                sanitizeDraftEntity(response);
                response.draft = $scope.curriculum.draft;
                mapDtoToModel(response, $scope);
              });
        }, function() {
          $scope.curriculum.draft = 'OPPEKAVA_LOOMISE_VIIS_PUUDUB';
        });
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');
      if($scope.isDraftEmployerSupportLetter) {
        $scope.curriculum.occupation = false;
      }
    };

    var updateModules = function() {
      var occupationCodes = {};
      if (angular.isArray($scope.curriculum.occupations)) {
        $scope.curriculum.occupations.forEach(function(curriculumOccupation) {
          occupationCodes[curriculumOccupation.occupation.code] = true;
          if (angular.isArray(curriculumOccupation.partOccupations)) {
            curriculumOccupation.partOccupations.forEach(function(it) {
              occupationCodes[it.code] = true;
            });
          }
          if (angular.isArray(curriculumOccupation.specialities)) {
            curriculumOccupation.specialities.forEach(function(it) {
              occupationCodes[it.code] = true;
            });
          }
        });
      }

      if (angular.isArray($scope.curriculum.modules)) {
        var modulesToRemove = [];
        $scope.curriculum.modules.forEach(function(it) {
          if (angular.isArray(it.occupations) && it.occupations.length > 0){
            for(var i = 0; i < it.occupations.length; i++) {
              if (occupationCodes[it.occupations[i].code] !== true) {
                modulesToRemove.push(it)
                break;
              }
            }
          }
        });

        modulesToRemove.forEach(function(it) {
          $scope.curriculum.modules.splice($scope.curriculum.modules.indexOf(it), 1);
        });

      }
    }

    $scope.isOccupationChanged = function() {
      $scope.curriculum.occupations = [];
      updateModules();
    };


    var occupationDialogOccupationSelected = function(dialogScope) {
        if(angular.isDefined(dialogScope.occupation)) {
          ClassifierConnect.queryAll({connectClassifierCode: dialogScope.occupation.code}, function(result) {
            var specialities = [];
            var partOccupations = [];
            result.forEach(function(it) {
              if(it.classifier.mainClassCode === 'SPETSKUTSE') {
                specialities.push(it.classifier);
              }
              if(it.classifier.mainClassCode === 'OSAKUTSE') {
                partOccupations.push(it.classifier);
              }
            });

            dialogScope.specialities = specialities;
            dialogScope.partOccupations = partOccupations;
          });
      }
    };

    $scope.openOccupationDialog = function(curriculumOccupation) {
      dialogService.showDialog('views/templates/vocational.curriculum.occupation.add.dialog.html',
        function(dialogScope) {
          dialogScope.$watch('occupation', function (oldValue, newValue) {
            if (angular.isDefined(curriculumOccupation) && oldValue.code != newValue.code) {
              dialogScope.selectedSpecialities = {};
            }
            occupationDialogOccupationSelected(dialogScope);
          });
          dialogScope.selectedSpecialities = {};

          if (angular.isDefined(curriculumOccupation)) {
            dialogScope.occupation = curriculumOccupation.occupation;//{code: curriculumOccupation.occupation.code};
            dialogScope.occupationGrant = curriculumOccupation.occupationGrant;
            if (angular.isArray(curriculumOccupation.specialities)) {
              curriculumOccupation.specialities.forEach(function(it) {
                dialogScope.selectedSpecialities[it.code] = true;
              });
            }
          }
        },
        function(submittedDialogScope) {
          if (!angular.isDefined(curriculumOccupation)) {
            curriculumOccupation = {};
            $scope.curriculum.occupations.push(curriculumOccupation);
          }
          curriculumOccupation.occupation = submittedDialogScope.occupation;
          curriculumOccupation.occupationGrant = submittedDialogScope.occupationGrant;
          curriculumOccupation.partOccupations = submittedDialogScope.partOccupations;

          curriculumOccupation.specialities = [];
          submittedDialogScope.specialities.forEach(function(it){
            if(submittedDialogScope.selectedSpecialities[it.code] === true) {
              curriculumOccupation.specialities.push(it);
            }
          });
          updateModules();
        });
    };


    $scope.openPartOccupationsDialog = function(curriculumPartOccupation) {
        dialogService.showDialog('views/templates/vocational.curriculum.partOccupation.add.dialog.html',
          function(dialogScope) {
            if (angular.isDefined(curriculumPartOccupation)) {
              dialogScope.partOccupationAquired = curriculumPartOccupation.occupation;
              ClassifierConnect.queryAll({classifierCode: dialogScope.partOccupationAquired.code}, function(result) {
                result.forEach(function(it) {
                  if(it.mainClassifierCode === 'KUTSE') {
                    dialogScope.occupation = it.connectClassifier;
                  }
                });
              });
            }
          },
          function(submittedDialogScope) {
            if (!angular.isDefined(curriculumPartOccupation)) {
              curriculumPartOccupation = {};
              $scope.curriculum.occupations.push(curriculumPartOccupation);
            }
            curriculumPartOccupation.occupation = submittedDialogScope.partOccupationAquired;
            updateModules();
          });
    };

    $scope.openAddModuleDialog = function(curriculumModule) {
      dialogService.showDialog('views/templates/vocational.curriculum.module.add.dialog.html',
      function(dialogScope) {
          dialogScope.occupations = $scope.curriculum.occupations;
          dialogScope.occupationsSelected = {};
          dialogScope.partOccupationsSelected = {};
          dialogScope.specialitiesSelected = {};
          dialogScope.module = {
            outcomes: [],
            occupations: []
          };

          if (angular.isDefined(curriculumModule)) {
            angular.extend(dialogScope.module, curriculumModule);
            if (angular.isArray(curriculumModule.occupations)) {
              curriculumModule.occupations.forEach(function(occupation){
                if(occupation.code.indexOf('KUTSE') === 0 || occupation.code.indexOf('OSAKUTSE') === 0) {
                  dialogScope.occupationsSelected[occupation.code] = true;
                } else if(occupation.code.indexOf('SPETSKUTSE') === 0) {
                  dialogScope.specialitiesSelected[occupation.code] = true;
                }
              });
            }
          }

          dialogScope.addOutcome = function() {
            dialogScope.module.outcomes.push({outcomeEt: dialogScope.outcomeEt, outcomeEn: dialogScope.outcomeEn});
            dialogScope.outcomeEt = undefined;
            dialogScope.outcomeEn = undefined;
          };
      },
      function(submittedDialogScope) {
          if (!angular.isDefined(curriculumModule)) {
            curriculumModule = {};
            $scope.curriculum.modules.push(curriculumModule);
          }
          angular.extend(curriculumModule, submittedDialogScope.module);
          curriculumModule.occupations = [];
          $scope.curriculum.occupations.forEach(function(it) {
            if (submittedDialogScope.occupationsSelected[it.occupation.code] === true) {
              curriculumModule.occupations.push(it.occupation);
            }
            if (angular.isArray(it.specialities)) {
              it.specialities.forEach(function(speciality) {
                if (submittedDialogScope.specialitiesSelected[speciality.code] === true) {
                  curriculumModule.occupations.push(speciality);
                }
              });
            }
          });
          renderModules();
      });
    }

    var renderModules = function() {
      if (angular.isArray($scope.curriculum.modules)) {
        var modulesView = Curriculum.modulesViewData($scope.curriculum.modules);
        $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
        $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;

        //for validation
        $scope.mainModuleCount = 0;
        $scope.voluntaryModuleCount = 0;
        $scope.curriculum.modules.forEach(function(it) {
          if (it.module.code === "KUTSEMOODUL_P") {
            $scope.mainModuleCount++;
          } else if (it.module.code === "KUTSEMOODUL_V") {
            $scope.voluntaryModuleCount++;
          }
        });

      }
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

/*    var addOccupations = function() {
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
    };*/

    $scope.save = function() {
      $scope.vocationalCurriculumForm.$setSubmitted();
      if($scope.vocationalCurriculumForm.$valid) {
        //before save collect some data;
        //addOccupations();
        addSharedInformationToJointPartners();

        var curriculum = new CurriculumEndpoint(mapModelToDto($scope.curriculum));
        if (angular.isDefined($scope.curriculum.id)) {
          curriculum.$update().then(function() {
            message.info('main.messages.create.success');
            mapDtoToModel(curriculum, $scope);
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
