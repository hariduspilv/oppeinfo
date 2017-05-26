'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    message, oisFileService, QueryUtils, $route, DataUtils, $location, Curriculum, $q, Session) {
      var clMapper = Classifier.valuemapper({occupations: 'KUTSE', partOccupations: 'OSAKUTSE', specialities: 'SPETSKUTSE'});
    //   $scope.school = Session.school;
      $scope.school = $route.current.locals.auth ? $route.current.locals.auth.school : null;
      $scope.PREVIOUS_ACTION = {
          PROCEED: 'proceed'
      };

      $scope.STATUS = Curriculum.STATUS;

      $scope.formState = {
            readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1,
            sentToEhis: angular.isDefined($route.current.params._sentToEhis)
      };

      var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
      var mapDtoToModel = function(response, scope) {

        var promises = [];
        var curriculum = angular.extend({}, response);
        $scope.currentStatus = curriculum.status;

        $scope.formState.notEditableBasicData = curriculum.status === Curriculum.STATUS.VERIFIED;

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


              var partOccupationsPromise = ClassifierConnect.queryAll({connectClassifierCode: code, classifierMainClassCode: 'OSAKUTSE'}, function(result) {
                var partOccupations = [];
                result.forEach(function(classifierConnect) {
                  partOccupations.push(classifierConnect.classifier);
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

          ClassifierConnect.queryAll({classifierCode: response.iscedClass, connectClassifierMainClassCode: 'ISCED_SUUN'}, function(result) {
            if (result.length > 0) {
                curriculum.fieldOfStudy = result[0].connectClassifier.code;
            }

            ClassifierConnect.queryAll({classifierCode: curriculum.fieldOfStudy, connectClassifierMainClassCode: 'ISCED_VALD'}, function(result) {
              if (result.length === 0) {
                deferred.reject();
              } else {
                  curriculum.areaOfStudy = result[0].connectClassifier.code;
                  deferred.resolve();
              }
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

      curriculumModel.studyPeriodMonths = curriculumModel.studyPeriodMonths ? curriculumModel.studyPeriodMonths : 0;
      dto.studyPeriod = curriculumModel.studyPeriodMonths + 12 * curriculumModel.studyPeriodYears;

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

    $scope.removeFromArray = function(array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
        });
    };

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
      occupation: false,
      studyPeriodMonths: 0,
      draft: 'OPPEKAVA_LOOMISE_VIIS_KUTSE'
    };
    $scope.curriculum = angular.extend({}, initialCurriculumScope);
    $scope.currentStatus = Curriculum.STATUS.ENTERING;
    $scope.validation = {
        occupationsLength: 0
    };


    $scope.$watchCollection('curriculum.occupations', function() {
        $scope.validation.occupationsLength = $scope.curriculum && $scope.curriculum.occupations ? $scope.curriculum.occupations.length : 0;
    });


    $scope.schoolOrigStudyLevel = [];
    QueryUtils.endpoint('/school/studyLevels').search().$promise.then(function(response){
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
          QueryUtils.endpoint('/stateCurriculum/all').query({sort: $scope.currentLanguageNameField()+',asc', expired: false, status: [Curriculum.STATUS.VERIFIED]}).$promise
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
          submittedDialogScope.stateCurriculum.status = Curriculum.STATUS.ENTERING;
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
                response.status = Curriculum.STATUS.VERIFIED;
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

      if(angular.isArray($scope.curriculum.modules)) {
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
          ClassifierConnect.queryAll({connectClassifierCode: object.occupation.code, classifierMainClassCode: ['SPETSKUTSE', 'OSAKUTSE']}, function(result) {
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
          dialogScope.formState = {
            readOnly: $scope.formState.readOnly || $scope.formState.notEditableBasicData
          };
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
          curriculumOccupation.occupationGrant = submittedDialogScope.occupationGrant ? submittedDialogScope.occupationGrant : false;
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

              $q.all([
                    QueryUtils.endpoint('/autocomplete/classifiers/withparents').query({mainClassCode: "OSAKUTSE"}).$promise, 
                    QueryUtils.endpoint('/autocomplete/classifiers/withparents').query({mainClassCode: "KUTSE"}).$promise
              ]).then(function(data){
                  dialogScope.partOccupations = data[0];
                  dialogScope.occupations = data[1].filter(function(el1){
                        for(var i = 0; i < dialogScope.partOccupations.length; i++){
                            if(ArrayUtils.includes(dialogScope.partOccupations[i].parents, el1.code)) {
                                return true;
                            }
                        }
                        return false;
                    });;
                    if(angular.isDefined(curriculumPartOccupation)) {
                        dialogScope.partOccupationAquired = dialogScope.partOccupations.find(function(el){
                            return el.code === curriculumPartOccupation.occupation.code;
                        });
                        dialogScope.occupation = dialogScope.occupations.find(function(el){
                            return ArrayUtils.includes(dialogScope.partOccupationAquired.parents, el.code)
                        });
                    }
              });
            
            dialogScope.filterPartOccupations = function(partOccupation){
                return dialogScope.occupation ? ArrayUtils.includes(partOccupation.parents, dialogScope.occupation.code) : true;
            };


            if (angular.isDefined(curriculumPartOccupation)) {
            //   dialogScope.partOccupationAquired = curriculumPartOccupation.occupation;
            //   ClassifierConnect.queryAll({classifierCode: dialogScope.partOccupationAquired.code, connectClassifierMainClassCode: 'KUTSE'}, function(result) {
            //     if (result.length > 0) {
            //       dialogScope.occupation = result[0].connectClassifier;
            //     }
            //   });
            }
            dialogScope.formState = $scope.formState;
          },
          function(submittedDialogScope) {
            if (!angular.isDefined(curriculumPartOccupation)) {
              curriculumPartOccupation = {
                  occupationGrant: false
              };
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
          dialogScope.isNew = !angular.isDefined(curriculumModule);
          dialogScope.formState = $scope.formState;
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

          dialogScope.delete = function() {
            $scope.removeModule(curriculumModule);
            dialogScope.cancel();
          };
          

          var occupationsSelected =  dialogScope.occupations.map(function(it){return it.occupation.code;});
          if (angular.isArray(occupationsSelected) && occupationsSelected.length > 0) {
            var loadedCompetences = {};
            ClassifierConnect.queryAll({connectClassifierCode: dialogScope.occupations.map(function(it){return it.occupation.code;}), classifierMainClassifierCode: 'KOMPENTENTS'}, function(result) {
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

          function occupationsAreValid() {
              var occupations = $scope.curriculum.occupations;
              // module cannot be both in occupation and its speciality
              for(var i = 0; i < occupations.length; i++) {
                  var code = occupations[i].occupation.code
                  var selected = dialogScope.occupationsSelected[code];
                  if(!selected) {
                      continue;
                  }
                  var specialities = occupations[i].specialities;
                  for(var k = 0; k < specialities.length; k++) {
                      if(dialogScope.specialitiesSelected[specialities[k].code]) {
                          return null;
                      }
                  }
              }
               return true;
          }

          dialogScope.occupationsValid = occupationsAreValid();

          dialogScope.validateOccupations = function() {
               dialogScope.occupationsValid = occupationsAreValid();
          }

      },
      function(submittedDialogScope) {
          if (!angular.isDefined(curriculumModule)) {
            curriculumModule = {
                practice: false
            };
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
          if($scope.curriculum.id) {
                var CurriculumModuleEndpoint = QueryUtils.endpoint('/curriculum/module');
                var savedModule = new CurriculumModuleEndpoint(mapModelToDto($scope.curriculum));
                savedModule.$update().then(function(response) {
                    var curriculum = mapDtoToModel(response, $scope);
                    message.info('main.messages.create.success');
                    $scope.curriculum.modules = $scope.curriculum.modules;
                    $scope.curriculum.occupations = $scope.curriculum.occupations;
                    renderModules();
                });
          } else {
                renderModules();
          }
          
      });
    };




    

    var renderModules = function() {
      if (angular.isArray($scope.curriculum.modules)) {
        var modulesView = Curriculum.modulesViewData($scope.curriculum.modules);
        $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
        $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;

        $scope.validation.mainModuleCount = 0;
        $scope.curriculum.modules.forEach(function(it) {
          if (it.module.code === "KUTSEMOODUL_P") {
            $scope.validation.mainModuleCount++;
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

    $scope.removeModule = function(item) {

        if(item.addedToImplementationPlan) {
            message.error('curriculum.error.curriculumModuleCantBeDeleted');
            return;
        }
        dialogService.confirmDialog({prompt: 'curriculum.moduleDeleteConfirm'}, function() {
            var CurriculumModuleEndpoint = QueryUtils.endpoint('/curriculum/module');
            var deletedModule = new CurriculumModuleEndpoint(item);
            if(item.id) {
                deletedModule.$delete().then(function() {
                    message.info('main.messages.delete.success');
                    $scope.curriculum.modules = $scope.curriculum.modules.filter(function(el){
                        return el.id !== item.id;
                    });
                });
            } else {
                ArrayUtils.remove($scope.curriculum.modules, item);
            }
        });
    };

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

    $scope.getUrl = function(oisFile) {
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

    function allModulesHaveOccupation() {
        return true; //TODO: add validation
    }

    function save(messages) {
      $scope.vocationalCurriculumForm.$setSubmitted();

      if(!$scope.vocationalCurriculumForm.$valid) {
          var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
          message.error(errorMessage);
          return;
      } else if ($scope.curriculum.status === Curriculum.STATUS.PROCEEDING && ArrayUtils.isEmpty($scope.curriculum.versions)) {
          message.error('curriculum.error.noImplementationPlan');
          return;
      } else if ($scope.curriculum.status === Curriculum.STATUS.PROCEEDING && !allModulesHaveOccupation()) {
          message.error('curriculum.error.notAllModulesHaveOccupation');
          return;
      } else {
        //before save collect some data;
        addSharedInformationToJointPartners();

        var curriculum = new CurriculumEndpoint(mapModelToDto($scope.curriculum));

        if (angular.isDefined($scope.curriculum.id)) {
          curriculum.$update().then(function() {
            var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
            message.info(updateSuccess);
            if(curriculum.status !== Curriculum.STATUS.ENTERING) {
                $location.path('/vocationalCurriculum/' + curriculum.id + '/view').search({});
            }
            mapDtoToModel(curriculum, $scope);
          });
        } else {
          curriculum.$save().then(function() {
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+curriculum.id+'/edit');
          });
        }
      } 
    }

    $scope.save = function() {
        $scope.curriculum.status = $scope.currentStatus;
        // setTimeout is needed for validation of ng-required fields
        setTimeout(save, 0);
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

    $scope.goToEditForm = function() {
        if(!$scope.curriculum) {
            return;
        }
        if($scope.curriculum.status === Curriculum.STATUS.VERIFIED) {
            dialogService.confirmDialog({
            prompt: 'curriculum.prompt.editAccepted', 
            }, function(){
                $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit');
            });
        } else {
            $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit');
        }
    };

    $scope.canBeEdited = function (){
        //TODO: add user role check
        var status = $scope.curriculum ? $scope.curriculum.status : null;
        return $scope.formState.readOnly && (status === Curriculum.STATUS.ENTERING || status === Curriculum.STATUS.PROCEEDING || status === Curriculum.STATUS.VERIFIED);
    };

    $scope.getStar = function() {
        return $scope.strictValidation() ? '' : ' *';
    };

    $scope.strictValidation = function() {
        // return $scope.curriculum && ($scope.curriculum.status !== Curriculum.STATUS.ENTERING || $scope.curriculum.status !== STATUS.CLOSED);
        return $scope.curriculum && ($scope.curriculum.status === Curriculum.STATUS.VERIFIED || $scope.curriculum.status === Curriculum.STATUS.PROCEEDING);
    };




        // --- Statuses

    function setStatus(newStatus, messages) {
        dialogService.confirmDialog({prompt: messages.prompt}, function() {
            $scope.curriculum.status = newStatus;
            // setTimeout is needed for validation of ng-required fields
            setTimeout(function(){
                save(messages);
            }, 0);
        });
    }

    $scope.setStatusProceed = function() {
        var messages = {
            prompt: 'curriculum.statuschange.vocational.proceed',
            errorMessage: 'curriculum.error.inputFieldsNotFilledOnProcede',
            updateSuccess: 'curriculum.success.proceed'
        };
        setStatus(Curriculum.STATUS.PROCEEDING, messages);
    };

    $scope.setStatusClosed = function() {
        var messages = {
            prompt: 'curriculum.statuschange.vocational.close'
        };
        setStatus(Curriculum.STATUS.CLOSED, messages);
    };

    $scope.sendToEhis = function() {
        message.info("curriculum.sentToEhis");
        // $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/view?_senttoehis');
        $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/view').search({_sentToEhis: true});
    };

    $scope.updateFromEhis = function() {
        $scope.curriculum.status = Curriculum.STATUS.VERIFIED;
        save({updateSuccess: "curriculum.message.statusUpdated"});
    };

    $scope.isOccupationChanged = function() {
        if(!ArrayUtils.isEmpty($scope.curriculum.occupations)) {
            var prompt = $scope.curriculum.occupation ? 'curriculum.prompt.hasOccupations' : 'curriculum.prompt.hasSubOccupations';
            $scope.curriculum.occupation = !$scope.curriculum.occupation;
            dialogService.confirmDialog({prompt: prompt}, function() {
                // for(var i = 0; i < $scope.curriculum.occupations.length; i++) {
                //     removeOccupationFromModules($scope.curriculum.occupations[i]);
                // }
                for(var i = 0; i < $scope.curriculum.modules.length; i++) {
                    $scope.curriculum.modules[i].occupations = [];
                }
                $scope.curriculum.occupations = [];
                $scope.curriculum.occupation = !$scope.curriculum.occupation;
            });
        }
    };


    Classifier.queryForDropdown({mainClassCode: 'KUTSEMOODUL'}, function(response) {
        $scope.moduleTypes = response;
    });

    $scope.filterEmptyModulesByType = function(typeCode) {
        return function(module1) {
            return module1.module.code === typeCode && ArrayUtils.isEmpty(module1.occupations);
        };
    };

   $scope.filterModulesByType = function(typeCode, occupationCode) {
        return function(module1) {
            var moduleOccupations = module1.occupations.map(function(el){
                return el.code;
            });
            return module1.module.code === typeCode && ArrayUtils.includes(moduleOccupations, occupationCode);
        };
    };
    //TODO: filter types of modules without occupations, filter occupatios / suboccupations
    $scope.filterTypesWithoutModules = function(occupationCode, modules) {
        return function(type) {
            var thisTypeModules = modules ? modules.filter(function(el){
                var occupations = el.occupations.map(function(el2){
                    return el2.code;
                });
                return ArrayUtils.includes(occupations, occupationCode) && el.module.code === type.code;
            }) : [];
            return !ArrayUtils.isEmpty(thisTypeModules);
        };
    };

    $scope.filterTypesWithoutEmptyModules = function(type) {
        var modules = $scope.curriculum.modules.filter(function(el){
            return ArrayUtils.isEmpty(el.occupations);
        });
        var thisTypeModules = modules ? modules.filter(function(el){
            return el.module.code === type.code;
        }) : [];
        return !ArrayUtils.isEmpty(thisTypeModules);
    };

    
    function removeOccupationFromModules(occupation) {
        var modules = $scope.curriculum.modules;
        for(var i = 0; i < modules.length; i++) {
            // filter by occupation 
            modules[i].occupations = modules[i].occupations.filter(function(el){
                return el.code !== occupation.occupation.code;
            });
            // filter by specialty
            modules[i].occupations = modules[i].occupations.filter(function(el){
                var specialties = occupation.specialities.map(function(el2){
                    return el2.code;
                });
                return !ArrayUtils.includes(specialties, el.code);
            });
            //filter by partOccupation
            modules[i].occupations = modules[i].occupations.filter(function(el){
                var partOccupations = occupation.partOccupations.map(function(el2){
                    return el2.code;
                });
                return !ArrayUtils.includes(partOccupations, el.code);
            });
        }
    }

    $scope.removeOccupation = function(occupation) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            removeOccupationFromModules(occupation);
            ArrayUtils.remove($scope.curriculum.occupations, occupation);
        });
    };

    $scope.goToImplementationPlanForm = function(version) {
        if($scope.formState.readOnly || version.status !== Curriculum.VERSION_STATUS.S) {
            $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/view/moduleImplementationPlan/' + version.id + '/view');
        } else {
            $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit/moduleImplementationPlan/' + version.id + '/edit');
        }
    };

  });
