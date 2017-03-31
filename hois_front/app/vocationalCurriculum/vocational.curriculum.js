'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    message, oisFileService, QueryUtils, $route, DataUtils, $location, Curriculum, $q) {
      var clMapper = Classifier.valuemapper({occupations: 'KUTSE', partOccupations: 'OSAKUTSE', specialities: 'SPETSKUTSE'});

      var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
      var mapDtoToModel = function(response, scope) {

        var promises = [];
        var curriculum = angular.extend({}, response);
        DataUtils.convertStringToDates(curriculum, ["validFrom", "validThru", "approval"]);

        if (angular.isArray(response.jointPartners) && response.jointPartners.length > 0) {
          curriculum.supervisor = response.jointPartners[0].supervisor;
          curriculum.jointMentor = response.jointPartners[0].ehisSchool;
          curriculum.contractEt = response.jointPartners[0].contractEt;
          curriculum.contractEn = response.jointPartners[0].contractEn;
        }


        if (angular.isArray(response.occupations)) {
            response.occupations.forEach(function(it) {
              var code = it.occupation;
              var promise = Classifier.get(code).$promise.then(function(classifier) {
                it.occupation = classifier;
              });
              promises.push(promise);


              var partOccupationsPromise = ClassifierConnect.queryAll({connectClassifierCode: code}, function(result) {
                var partOccupations = [];
                result.forEach(function(classifierConnect) {
                  if(classifierConnect.classifier.mainClassCode === 'OSAKUTSE') {
                    partOccupations.push(classifierConnect.classifier);
                  }
                });
                it.partOccupations = partOccupations;
              }).$promise;
              promises.push(partOccupationsPromise);

              if (angular.isArray(it.specialities)) {
                var specialityPromises = [];
                it.specialities.forEach(function(specialityCode){
                  specialityPromises.push(Classifier.get(specialityCode).$promise);
                });
                var specialitiesPromise = $q.all(specialityPromises).then(function(specialities){
                  it.specialities = specialities;
                });

                promises.push(specialitiesPromise);
              }
            });
        }


        curriculum.studyPeriodMonths = response.studyPeriod % 12;
        curriculum.studyPeriodYears = Math.floor(response.studyPeriod / 12);

        if (angular.isString(response.iscedClass)) {
          var deferred = $q.defer();

          ClassifierConnect.queryAll({classifierCode: response.iscedClass}, function(result) {
            result.forEach(function(it) {
              if(it.mainClassifierCode === "ISCED_SUUN") {
                curriculum.fieldOfStudy = it.connectClassifier.code;
              }
            });

            ClassifierConnect.queryAll({classifierCode: curriculum.fieldOfStudy}, function(result) {
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
          if (angular.isString(it.module)) {
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
          }
        });
      }

      $q.all(promises).then(function() {
        angular.extend(scope.curriculum, curriculum);
      });

    };/*mapDtoToModel*/

    var mapModelToDto = function(curriculumModel) {
      var dto = angular.extend({}, curriculumModel);
      if (angular.isArray(dto.occupations)) {
        dto.occupations.forEach(function(it) {
          if (angular.isObject(it.occupation)) {
            it.occupation = it.occupation.code;
            delete it.partOccupations;
          }
          if (angular.isArray(it.specialities)) {
            it.specialities = it.specialities.map(function(speciality){
              return speciality.code;
            });
          }
        });
      }

      if (angular.isArray(dto.modules)) {
        dto.modules.forEach(function(it){
          it.module = it.module.code;
          if(angular.isArray(it.occupations)) {
            it.occupations = it.occupations.map(function(occupation){
              return occupation.code;
            });
          }
        });
      }
      return dto;
    };

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
      abroad: false,
      occupation: false
    };
    $scope.curriculum = angular.extend({}, initialCurriculumScope);
    $scope.validation = {};


    $scope.schoolOrigStudyLevel = [];
    QueryUtils.endpoint('/school/studyLevels').get().$promise.then(function(response){
        response.studyLevels.forEach(function(it) {
            if(it.charAt(it.length - 3) < '5') {
                $scope.schoolOrigStudyLevel.push({code: it});
            }
        });
    });

    $scope.codeUniqueQuery = {
      paramName: 'code',
      url: '/curriculum/unique',
      id: angular.isDefined($route.current.locals.entity) ? $route.current.locals.entity.id : undefined
    };

    $scope.merCodeUniqueQuery = {
      id: $scope.codeUniqueQuery.id,
      paramName: 'merCode',
      countOnlyValid: true,
      url: $scope.codeUniqueQuery.url
    };

    //edit
    if (angular.isDefined($route.current.locals.entity)) {
      mapDtoToModel($route.current.locals.entity, $scope);
    }

    $scope.draftOptions = {};
    Classifier.queryForDropdown({mainClassCode: 'OPPEKAVA_LOOMISE_VIIS'}, function(response) {
      response.forEach(function(it) {
        $scope.draftOptions[it.code] = it;
      });
    });


    var sanitizeDraftEntity = function(draftEntity) {
      ['id', 'version', 'changed', 'changedBy', 'inserted', 'insertedBy', 'status', 'ehisStatus', 'ehisChanged', 'files',
      'joint', 'jointMentor', 'jointPartners', 'specialities', 'higher', 'versions', 'merCode', 'code', 'occupation',
      'approvalDokNr', 'approval', 'validFrom', 'validThru'].forEach(function(property) {
        delete draftEntity[property];
      });
      if (angular.isObject(draftEntity.stateCurrClass)) {
        draftEntity.stateCurrClass = draftEntity.stateCurrClass.code;
      }
    };

    $scope.draftSelected = function() {
      if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showDialog('vocationalCurriculum/state.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.selectedStateCurriculumOccupations = {};
          dialogScope.selectedStateCurriculumPartOccupations = {};
          dialogScope.selectedStateCurriculumSpecialities = {};
          QueryUtils.endpoint('/stateCurriculum/all').query({sort: $scope.currentLanguageNameField()+',asc', expired: false, status: ['OPPEKAVA_STAATUS_K']}).$promise
            .then(function(response) {
              dialogScope.stateCurriculums = response;
            });
          dialogScope.stateCurriculumSelected = function(stateCurriculumId) {
            if (angular.isNumber(stateCurriculumId)) {
              QueryUtils.endpoint('/stateCurriculum').get({id: stateCurriculumId}).$promise
              .then(function(response) {
                clMapper.objectmapper(response).$promise
                  .then(function(result) {
                    var occupations = [];
                    if (angular.isArray(result.occupations)) {
                      result.occupations.forEach(function(occupationClassifier) {
                        var occupation = {occupation: occupationClassifier};
                        loadPartOccupationsAndSpecialities(occupation);
                        occupations.push(occupation);
                      });
                    }
                    dialogScope.stateCurriculumOccupations = occupations;
                  });
                dialogScope.stateCurriculum = response;
              });
            }
          };

          dialogScope.selectedSpecialitiesCount = {};
          dialogScope.occupationSelected = function(code) {
            var check = dialogScope.selectedStateCurriculumOccupations[code] === true;
            dialogScope.stateCurriculumOccupations.forEach(function(it) {
              if (it.occupation.code === code) {
                it.partOccupations.forEach(function(it) {
                  dialogScope.selectedStateCurriculumPartOccupations[it.code] = check;
                });

                if (angular.isArray(it.specialities) && it.specialities.length > 0) {
                  dialogScope.selectedSpecialitiesCount[code] = check ? 0 : undefined;
                  it.specialities.forEach(function(it) {
                    dialogScope.selectedStateCurriculumSpecialities[it.code] = false;
                  });
                }
              }
            });
          };
          dialogScope.specialitySelected = function(occupationCode, code) {
            var check = dialogScope.selectedStateCurriculumSpecialities[code] === true;
            if (angular.isNumber(dialogScope.selectedSpecialitiesCount[occupationCode])) {
              var value = check ? 1 : -1;
              dialogScope.selectedSpecialitiesCount[occupationCode]+=value;
            }
          };
        },
        function(submittedDialogScope) {
          var occupations = [];

          if (angular.isArray(submittedDialogScope.stateCurriculumOccupations)) {
            $scope.curriculum.occupation = false;

            submittedDialogScope.stateCurriculumOccupations.forEach(function(occupation) {
              var addedOccupation;
              if(submittedDialogScope.selectedStateCurriculumOccupations[occupation.occupation.code] === true) {
                $scope.curriculum.occupation = true;
                addedOccupation = {occupation: occupation.occupation.code, specialities: []};
                occupations.push(addedOccupation);
              }

              if (angular.isArray(occupation.partOccupations)) {
                occupation.partOccupations.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumPartOccupations[it.code] === true && !addedOccupation) {
                    addedOccupation = {occupation: occupation.occupation.code, specialities: []};
                    occupations.push(addedOccupation);
                  }
                });
              }

              if (angular.isArray(occupation.specialities)) {
                occupation.specialities.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumSpecialities[it.code] === true) {
                    if (!addedOccupation) {
                      addedOccupation = {occupation: occupation.occupation.code, specialities: []};
                      occupations.push(addedOccupation);
                    }
                    addedOccupation.specialities.push(it.code);
                  }
                });
              }
            });
          }
          submittedDialogScope.stateCurriculum.occupations = occupations;
          var modules = [];
          if (angular.isArray(submittedDialogScope.stateCurriculum.modules)) {
            submittedDialogScope.stateCurriculum.modules.forEach(function(it) {
              delete it.id;
              delete it.version;
              it.occupations = it.moduleOccupations;
              if (angular.isArray(it.occupations) && it.occupations.length > 0) {
                it.occupations.forEach(function(occupation) {
                  if (submittedDialogScope.selectedStateCurriculumOccupations[occupation] ||
                    submittedDialogScope.selectedStateCurriculumPartOccupations[occupation] ||
                    submittedDialogScope.selectedStateCurriculumSpecialities[occupation]) {
                    modules.push(it);
                  }
                });
              } else {
                modules.push(it);
              }
            });
          }
          submittedDialogScope.stateCurriculum.modules = modules;


          sanitizeDraftEntity(submittedDialogScope.stateCurriculum);
          ['objectivesEt', 'objectivesEn'].forEach(function(property) {
            delete submittedDialogScope.stateCurriculum[property];
          });
          submittedDialogScope.stateCurriculum.draft = $scope.curriculum.draft;
          submittedDialogScope.stateCurriculum.status = 'OPPEKAVA_STAATUS_K';
          mapDtoToModel(submittedDialogScope.stateCurriculum, $scope);
        }, function() {
          $scope.curriculum.draft = undefined;
        });

      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showDialog('vocationalCurriculum/school.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.curriculumSelected = [];

        var query = {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt', school: $scope.currentUser.school.id, isVocational: true};

        dialogScope.loadData = function() {
          dialogScope.tabledata = Curriculum.query(angular.extend(query, dialogScope.criteria), null);
        };
        dialogScope.loadData();

        },
        function(submittedDialogScope) {
          QueryUtils.endpoint('/curriculum').get({id: submittedDialogScope.curriculumSelected[0].id}).$promise
              .then(function(response) {
                if (angular.isArray(response.occupations)) {
                  response.occupations.forEach(function(it) {
                    delete it.id;
                    delete it.version;
                  });
                }

                if (angular.isArray(response.modules)) {
                  response.modules.forEach(function(it) {
                    delete it.id;
                    delete it.version;
                    if (angular.isArray(it.outcomes)) {
                      it.outcomes.forEach(function(it) {
                        delete it.id;
                        delete it.version;
                      });
                    }
                  });
                }

                sanitizeDraftEntity(response);
                response.draft = $scope.curriculum.draft;
                response.status = 'OPPEKAVA_STAATUS_K';
                mapDtoToModel(response, $scope);
              });
        }, function() {
          $scope.curriculum.draft = undefined;
        });
      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA') {
        $scope.curriculum.occupations = [];
        $scope.curriculum.occupation = false;
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');
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
                modulesToRemove.push(it);
                break;
              }
            }
          }
        });

        modulesToRemove.forEach(function(it) {
          $scope.curriculum.modules.splice($scope.curriculum.modules.indexOf(it), 1);
        });

      }
    };

    $scope.occupationChanged = function() {
      $scope.curriculum.occupations = [];
      if (angular.isArray($scope.curriculum.versions)) {
        $scope.curriculum.versions.forEach(function(version) {
          version.occupationModules = [];
        });
      }
      updateModules();
    };


    var loadPartOccupationsAndSpecialities = function(object) {
        if(angular.isDefined(object.occupation)) {
          ClassifierConnect.queryAll({connectClassifierCode: object.occupation.code}, function(result) {
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

            object.specialities = specialities;
            object.partOccupations = partOccupations;
          });
      }
    };

    $scope.openOccupationDialog = function(curriculumOccupation) {
      dialogService.showDialog('vocationalCurriculum/occupation.add.dialog.html',
        function(dialogScope) {
          dialogScope.$watch('occupation', function (oldValue, newValue) {
            if (angular.isDefined(curriculumOccupation) && oldValue.code !== newValue.code) {
              dialogScope.selectedSpecialities = {};
            }
            loadPartOccupationsAndSpecialities(dialogScope);
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
        dialogService.showDialog('vocationalCurriculum/partOccupation.add.dialog.html',
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
      dialogService.showDialog('vocationalCurriculum/module.add.dialog.html',
      function(dialogScope) {
          dialogScope.occupations = $scope.curriculum.occupations;
          dialogScope.occupationsSelected = {};
          dialogScope.partOccupationsSelected = {};
          dialogScope.specialitiesSelected = {};
          dialogScope.competences = [];
          dialogScope.module = {
            outcomes: [],
            occupations: []
          };
          if ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
            dialogScope.filteredModuleValues = ['KUTSEMOODUL_P', 'KUTSEMOODUL_Y'];
          }

          if (angular.isDefined(curriculumModule)) {
            angular.extend(dialogScope.module, curriculumModule);
            if (angular.isArray(curriculumModule.occupations)) {
              curriculumModule.occupations.forEach(function(occupation) {
                if(occupation.code.indexOf('KUTSE') === 0 || occupation.code.indexOf('OSAKUTSE') === 0) {
                  dialogScope.occupationsSelected[occupation.code] = true;
                } else if(occupation.code.indexOf('SPETSKUTSE') === 0) {
                  dialogScope.specialitiesSelected[occupation.code] = true;
                }
              });
            }
          }

          dialogScope.addOutcome = function() {
            if (angular.isString(dialogScope.outcomeEt) && dialogScope.outcomeEt !== '') {
                dialogScope.module.outcomes.push({outcomeEt: dialogScope.outcomeEt, outcomeEn: dialogScope.outcomeEn});
                dialogScope.outcomeEt = undefined;
                dialogScope.outcomeEn = undefined;
              }
          };

          var occupationsSelected =  dialogScope.occupations.map(function(it){return it.occupation.code;});
          if (angular.isArray(occupationsSelected) && occupationsSelected.length > 0) {
            var loadedCompetences = {};
            ClassifierConnect.queryAll({connectClassifierCodes: dialogScope.occupations.map(function(it){return it.occupation.code;})}, function(result) {
              result.forEach(function(it) {
                loadedCompetences[it.classifier.code] = it.classifier;
              });
              for(var p in loadedCompetences) {
                if (loadedCompetences.hasOwnProperty(p)) {
                  dialogScope.competences.push(loadedCompetences[p]);
                }
              }
            });
          }
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

            if (angular.isArray(it.partOccupations)) {
              it.partOccupations.forEach(function(partOccupation) {
                if (submittedDialogScope.occupationsSelected[partOccupation.code] === true) {
                  curriculumModule.occupations.push(partOccupation);
                }
              });
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
    };

    var renderModules = function() {
      if (angular.isArray($scope.curriculum.modules)) {
        var modulesView = Curriculum.modulesViewData($scope.curriculum.modules);
        $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
        $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;

        $scope.validation.mainModuleCount = 0;
        $scope.validation.voluntaryModuleCount = 0;
        $scope.curriculum.modules.forEach(function(it) {
          if (it.module.code === "KUTSEMOODUL_P") {
            $scope.validation.mainModuleCount++;
          } else if (it.module.code === "KUTSEMOODUL_V") {
            $scope.validation.voluntaryModuleCount++;
          }
        });

        var creditsPerOccupation = {};
        for (var occupation in $scope.occupationModuleTypesModules) {
          if (!angular.isDefined(creditsPerOccupation[occupation])) {
            creditsPerOccupation[occupation] = 0;
            if (angular.isDefined($scope.occupationModuleTypesModules[occupation].moduleTypes)) {
              for (var moduleType in $scope.occupationModuleTypesModules[occupation].moduleTypes) {
                if (angular.isArray($scope.occupationModuleTypesModules[occupation].moduleTypes[moduleType].modules)) {
                  for(var i in $scope.occupationModuleTypesModules[occupation].moduleTypes[moduleType].modules) {
                    creditsPerOccupation[occupation] += $scope.occupationModuleTypesModules[occupation].moduleTypes[moduleType].modules[i].credits;
                  }
                }
              }
            }
          }
        }
        $scope.creditsPerOccupation = creditsPerOccupation;

      }
    };
    $scope.$watchCollection('curriculum.modules', renderModules);

    $scope.openAddFileDialog = function() {
      dialogService.showDialog('vocationalCurriculum/file.add.dialog.html', null, function(submittedDialogScope) {
        var data = submittedDialogScope.data;
        data.sendEhis = false;
        data.ehis = false;
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
      var jointPartner = {abroad: $scope.curriculum.abroad};
      if (jointPartner.abroad) {
        jointPartner.nameEt = $scope.curriculum.jointPartnerForeignNameEt;
        jointPartner.nameEn = $scope.curriculum.jointPartnerForeignNameEn;
      } else {
        jointPartner.ehisSchool = $scope.curriculum.jointPartnerEhisSchool;
      }
      $scope.curriculum.jointPartners.push(jointPartner);
      $scope.curriculum.jointPartnerForeignNameEt = undefined;
      $scope.curriculum.jointPartnerForeignNameEn = undefined;
    };

    $scope.validation.jointPartnersLength = 0;
    $scope.$watchCollection('curriculum.jointPartners', function() {
      var selectedJointPartners = [];
      if (angular.isDefined($scope.currentUser)) {
        selectedJointPartners.push($scope.currentUser.school.ehisSchool);
      }
      if (angular.isArray($scope.curriculum.jointPartners)) {
        $scope.validation.jointPartnersLength = $scope.curriculum.jointPartners.length;
        $scope.curriculum.jointPartners.forEach(function(it) {
          if(!it.abroad) {
            selectedJointPartners.push(it.ehisSchool);
          }
        });
      }
      $scope.selectedJointPartners = selectedJointPartners;
    });

    $scope.curriculumJointChange = function() {
      if ($scope.curriculum.joint === true) {
        $scope.curriculum.abroad = false;
        $scope.selectedJointPartners = [$scope.currentUser.school.ehisSchool];
      } else {
        $scope.clearJointPartnersFields();
        $scope.curriculum.jointMentor = undefined;
        $scope.curriculum.jointPartners = [];
      }
    };

    $scope.removejointPartner = function(item) {
      $scope.removeFromArray($scope.curriculum.jointPartners, item);
    };

    var addSharedInformationToJointPartners = function () {
      $scope.curriculum.jointPartners.forEach(function(it) {
        it.contractEt = $scope.curriculum.contractEt;
        it.contractEn = $scope.curriculum.contractEn;
        it.supervisor = $scope.curriculum.supervisor;
      });
    };

    $scope.clearJointPartnersFields = function() {
        $scope.curriculum.jointPartnerForeignNameEt = undefined;
        $scope.curriculum.jointPartnerForeignNameEn = undefined;
        $scope.curriculum.jointPartnerEhisSchool = undefined;
    };

    $scope.save = function() {
      $scope.vocationalCurriculumForm.$setSubmitted();
      if($scope.vocationalCurriculumForm.$valid) {
        //before save collect some data;
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

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.deleteconfirm'}, function() {
        var curriculum = new CurriculumEndpoint(mapModelToDto($scope.curriculum));
        curriculum.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/curriculum');
        });
      });
    };

  });
