'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    message, oisFileService, QueryUtils, $route, DataUtils, $location, Curriculum, $q, config, $rootScope, Session) {
      var clMapper = Classifier.valuemapper({occupations: 'KUTSE', partOccupations: 'OSAKUTSE', specialities: 'SPETSKUTSE'});
      $scope.school = $route.current.locals.auth ? $route.current.locals.auth.school : null;
      $scope.auth = $route.current.locals.auth;

      $scope.maxStydyYears = {max: 100};

      $scope.STATUS = Curriculum.STATUS;
      var CurriculumFileEndpoint;

      $scope.formState = {
         readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
      };

      function blockButtons() {
        $scope.formState.waitingResponse = true;
      }

      function releaseButtons() {
        $scope.formState.waitingResponse = false;
      }

      /**
       * When crud query fails, this converts dto back to model and releases buttons after that
       */
      function convertBackToModel(curriculum) {
        mapDtoToModel(curriculum, $scope);
      }

      var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
      var mapDtoToModel = function(response, scope) {

        $scope.formState.strictValidation = false;
        var promises = [];
        var curriculum = angular.extend({}, response);
        $scope.isDraftEmployerSupportLetter = curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA';

        if(curriculum.jointPartners && curriculum.jointPartners.length === 1 &&
        !(curriculum.jointPartners[0].ehisSchool || curriculum.jointPartners[0].nameEt || curriculum.jointPartners[0].nameEn)) {
            curriculum.jointPartners = [];
        }

        $scope.formState.notEditableBasicData = curriculum.status === Curriculum.STATUS.VERIFIED;
        $scope.formState.sentToEhis = curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_A' || curriculum.ehisStatus === 'OPPEKAVA_EHIS_STAATUS_M';

        DataUtils.convertStringToDates(curriculum, ["validFrom", "validThru", "approval"]);

        if (angular.isArray(response.jointPartners) && response.jointPartners.length > 0) {
          curriculum.supervisor = response.jointPartners[0].supervisor;
        //   curriculum.jointMentor = response.jointPartners[0].ehisSchool;
          curriculum.contractEt = response.jointPartners[0].contractEt;
          curriculum.contractEn = response.jointPartners[0].contractEn;
        }


        if (angular.isArray(response.occupations)) {
            response.occupations.forEach(function(it) {
              var code = angular.isString(it.occupation) ? it.occupation : it.occupation.code;
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
        releaseButtons();
      });

    };/*mapDtoToModel*/

    var mapModelToDto = function(curriculumModel) {
      var dto = angular.extend({}, curriculumModel);

      curriculumModel.studyPeriodMonths = curriculumModel.studyPeriodMonths ? curriculumModel.studyPeriodMonths : 0;
      dto.studyPeriod = curriculumModel.studyPeriodMonths + 12 * curriculumModel.studyPeriodYears;

      if (angular.isArray(dto.occupations)) {
        curriculumOccupationsToDto(dto.occupations);
      }

      if (angular.isArray(dto.modules)) {
        dto.modules.forEach(function(it){
          moduleModelToDto(it);
        });
      }
      return dto;
    };

    function curriculumOccupationsToDto(occupations){
      occupations.forEach(function(it) {
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

    function moduleModelToDto(curriculumModule) {
      curriculumModule.module = curriculumModule.module.code;
      if(angular.isArray(curriculumModule.occupations)) {
        curriculumModule.occupations = curriculumModule.occupations.map(function(occupation){
          return occupation.code;
        });
      }
    }

    $scope.removeFromArray = function(array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
        });
    };

    var initialCurriculumScope = {
      selectedOccupationsPartOccupations: [],
      modules: [],
      occupations: [],
      files: [],
      jointPartners: [],
      studyLanguageClassifiers: [],
      studyFormClassifiers: [],
      abroad: false
    };
    /**
     * Setting values from defaultValues into initialCurriculumScope caused error
     * when saving curriculum with validThru date in past
     */
    var defaultValues = {
        validFrom: new Date(),
        status: Curriculum.STATUS.ENTERING,
        consecution: "OPPEKAVA_TYPE_E",
        optionalStudyCredits: 0,
        studyPeriodMonths: 0,
        draft: 'OPPEKAVA_LOOMISE_VIIS_KUTSE',
        higher: false,
        joint: false,
        abroad: false,
        occupation: false
    };

    $scope.curriculum = angular.extend({}, initialCurriculumScope);
    $scope.validation = {
        occupationsLength: 0
    };


    $scope.$watchCollection('curriculum.occupations', function() {
        $scope.validation.occupationsLength = $scope.curriculum && $scope.curriculum.occupations ? $scope.curriculum.occupations.length : 0;
        if($scope.curriculum && $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA') {
            $scope.validation.occupationsLength = 1;
        }
    });


    $scope.schoolOrigStudyLevel = [];
    QueryUtils.endpoint('/school/studyLevels').search().$promise.then(function(response){
        response.studyLevels.forEach(function(it) {
            if(it.charAt(it.length - 3) < '5') {
                $scope.schoolOrigStudyLevel.push({code: it});
            }
        });
    });

    var uniqueQueriesId = angular.isDefined($route.current.locals.entity) ? $route.current.locals.entity.id : undefined;

    $scope.codeUniqueQuery = {
      url: '/curriculum/unique/code',
      id: uniqueQueriesId
    };

    $scope.merCodeUniqueQuery = {
      url: '/curriculum/unique/merCode',
      id: uniqueQueriesId
    };

    //edit
    if (angular.isDefined($route.current.locals.entity)) {
      blockButtons();
      mapDtoToModel($route.current.locals.entity, $scope);
      CurriculumFileEndpoint = QueryUtils.endpoint('/curriculum/' + $route.current.locals.entity.id + '/file');

      $rootScope.removeLastUrlFromHistory(function(lastUrl){
        // return lastUrl.indexOf('#/vocationalCurriculum/' + $route.current.locals.entity.id + '/view') !== -1;
        return lastUrl === '#/vocationalCurriculum/' + $route.current.locals.entity.id + '/view' ||
               lastUrl === '#/vocationalCurriculum/' + $route.current.locals.entity.id + '/edit' ||
               lastUrl === '#/vocationalCurriculum/new';
      });
    } else {
      // create
      releaseButtons();
      angular.extend($scope.curriculum, defaultValues);
    }

    $scope.draftOptions = {};
    Classifier.queryForDropdown({mainClassCode: 'OPPEKAVA_LOOMISE_VIIS'}, function(response) {
      $scope.draftOptions = Classifier.toMap(response);
    });


    var sanitizeDraftEntity = function(draftEntity) {
      ['id', 'version', 'changed', 'changedBy', 'inserted', 'insertedBy', 'status', 'ehisStatus', 'ehisChanged', 'files',
      'jointMentor', 'jointPartners', 'specialities', 'higher', 'versions', 'merCode', 'code',
      'approvalDokNr', 'approval', 'validFrom', 'validThru'].forEach(function(property) {
        delete draftEntity[property];
      });
      if (angular.isObject(draftEntity.stateCurrClass)) {
        draftEntity.stateCurrClass = draftEntity.stateCurrClass.code;
      }
    };


    function countOccupationsAndPartOccupationsSelected(dialogScope) {
        dialogScope.occupationsCount = 0;
        // count occupations
        var stateCurriculumOccupations = dialogScope.stateCurriculumOccupations;
        stateCurriculumOccupations.forEach(function(el){
          if(dialogScope.selectedStateCurriculumOccupations[el.occupation.code]) {
            dialogScope.occupationsCount++;
          }
        });
        // count partoccupations
        dialogScope.partOccupationsCount = 0;
        stateCurriculumOccupations.forEach(function(el){
            el.partOccupations.forEach(function(it) {
                if(dialogScope.selectedStateCurriculumPartOccupations[it.code]) {
                    dialogScope.partOccupationsCount++;
                }
            });
        });
        // sum
        dialogScope.occupationsAndPartOccupationsCount = dialogScope.occupationsCount + dialogScope.partOccupationsCount;
    }

    $scope.draftSelected = function() {
      if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showDialog('vocationalCurriculum/state.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.selectedStateCurriculumOccupations = {};
          dialogScope.selectedStateCurriculumPartOccupations = {};
          dialogScope.selectedStateCurriculumSpecialities = {};
          QueryUtils.endpoint('/stateCurriculum/all').query({sort: $scope.currentLanguageNameField()+',asc', status: [Curriculum.STATUS.VERIFIED]}).$promise
            .then(function(response) {
              dialogScope.stateCurriculums = response;
            });
          dialogScope.stateCurriculumSelected = function(stateCurriculumId) {
            dialogScope.selectedStateCurriculumOccupations = {};
            dialogScope.selectedStateCurriculumPartOccupations = {};
            dialogScope.selectedStateCurriculumSpecialities = {};

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
            dialogScope.occupationsCount = 0;
            dialogScope.partOccupationsCount = 0;
            dialogScope.occupationsAndPartOccupationsCount = 0;
          };

          dialogScope.countOccupationsAndPartOccupationsSelected = function() {
              countOccupationsAndPartOccupationsSelected(dialogScope);
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
            countOccupationsAndPartOccupationsSelected(dialogScope);
          };
          dialogScope.specialitySelected = function(occupationCode, code) {
            var check = dialogScope.selectedStateCurriculumSpecialities[code] === true;
            if (angular.isNumber(dialogScope.selectedSpecialitiesCount[occupationCode])) {
              var value = check ? 1 : -1;
              dialogScope.selectedSpecialitiesCount[occupationCode] += value;
            }
          };
        },
        function(submittedDialogScope) {
          var occupations = [];
          $scope.curriculum.stateCurriculum = submittedDialogScope.stateCurriculum.id;

          if (angular.isArray(submittedDialogScope.stateCurriculumOccupations)) {
            $scope.curriculum.occupation = false;

            submittedDialogScope.stateCurriculumOccupations.forEach(function(occupation) {
              var addedOccupation;
              if(submittedDialogScope.selectedStateCurriculumOccupations[occupation.occupation.code] === true) {
                $scope.curriculum.occupation = true;
                addedOccupation = {occupation: occupation.occupation.code, specialities: [], occupationGrant: false};
                occupations.push(addedOccupation);
              }

              if (angular.isArray(occupation.partOccupations)) {
                occupation.partOccupations.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumPartOccupations[it.code] === true && !addedOccupation) {
                    // code below prevented multiple partOccupations of the same occupation to be added to curriculum
                    // addedOccupation = {occupation: occupation.occupation.code, specialities: []};
                    // addedOccupation = {
                    //     occupation: it.code,
                    //     occupationGrant: false
                    // };
                    // occupations.push(addedOccupation);
                    var partOccupation = {
                        occupation: it.code,
                        occupationGrant: false,
                        specialities: []
                    };
                    occupations.push(partOccupation);
                  }
                });
              }

              if (angular.isArray(occupation.specialities)) {
                occupation.specialities.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumSpecialities[it.code] === true) {
                    if (!addedOccupation) {
                      addedOccupation = {occupation: occupation.occupation.code, specialities: [], occupationGrant: false};
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
              it.practice = false;
              it.occupations = it.moduleOccupations;
              if (angular.isArray(it.occupations) && it.occupations.length > 0) {
                for(var i = 0; i < it.occupations.length; i++) {
                  if (submittedDialogScope.selectedStateCurriculumOccupations[it.occupations[i]] ||
                    submittedDialogScope.selectedStateCurriculumPartOccupations[it.occupations[i]] ||
                    submittedDialogScope.selectedStateCurriculumSpecialities[it.occupations[i]]) {
                    modules.push(it);
                    break;
                  }
                }
              } else {
                modules.push(it);
              }

              // if (angular.isArray(it.occupations) && it.occupations.length > 0) {
              //   it.occupations.forEach(function(occupation) {
              //     if (submittedDialogScope.selectedStateCurriculumOccupations[occupation] ||
              //       submittedDialogScope.selectedStateCurriculumPartOccupations[occupation] ||
              //       submittedDialogScope.selectedStateCurriculumSpecialities[occupation]) {
              //       modules.push(it);
              //     }
              //   });
              // } else {
              //   modules.push(it);
              // }


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
          $scope.curriculum.draft = 'OPPEKAVA_LOOMISE_VIIS_KUTSE';
        });

      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showDialog('vocationalCurriculum/school.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.curriculumSelected = [];

          QueryUtils.createQueryForm(dialogScope, '/curriculum', {order: $scope.currentLanguageNameField()});
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
                response.status = $scope.curriculum.status;
                mapDtoToModel(response, $scope);
              });
        }, function() {
          $scope.curriculum.draft = 'OPPEKAVA_LOOMISE_VIIS_KUTSE';
        });
      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA') {
        $scope.curriculum.occupations = [];
        $scope.curriculum.occupation = false;
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');

    };

    /**
     * Removes occupations from modules
     */
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
        $scope.curriculum.modules.forEach(function(m) {
          m.occupations = m.occupations.filter(function(o){
            return occupationCodes[o.code] === true;
          });
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
            readOnly: $scope.formState.readOnly || $scope.formState.notEditableBasicData,
            limitedEditing: !$scope.occupationCanBeChanged()
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
          $scope.vocationalCurriculumForm.$setDirty();

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
                    });
                    if(angular.isDefined(curriculumPartOccupation)) {
                        dialogScope.partOccupationAquired = dialogScope.partOccupations.find(function(el){
                            return el.code === curriculumPartOccupation.occupation.code;
                        });
                        dialogScope.occupation = dialogScope.occupations.find(function(el){
                            return ArrayUtils.includes(dialogScope.partOccupationAquired.parents, el.code);
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
            dialogScope.formState = {
              readOnly: $scope.formState.readOnly || !$scope.occupationCanBeChanged()
            };
          },
          function(submittedDialogScope) {
            $scope.vocationalCurriculumForm.$setDirty();
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
          dialogScope.formState = {
            readOnly: $scope.formState.readOnly,
            isNew: !angular.isDefined(curriculumModule)
          };
          dialogScope.filteredModuleValues = [];
          if ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
            dialogScope.filteredModuleValues = ['KUTSEMOODUL_P', 'KUTSEMOODUL_Y'];
          }

          dialogScope.swap = function(index1, index2) {
            var temp = dialogScope.module.outcomes[index1];
            dialogScope.module.outcomes[index1] = dialogScope.module.outcomes[index2];
            dialogScope.module.outcomes[index2] = temp;
          };

          if (angular.isDefined(curriculumModule)) {
            dialogScope.module = angular.copy(curriculumModule);
            if (angular.isArray(curriculumModule.occupations)) {
              curriculumModule.occupations.forEach(function(occupation) {
                if(occupation.code.indexOf('KUTSE') === 0 || occupation.code.indexOf('OSAKUTSE') === 0) {
                  dialogScope.occupationsSelected[occupation.code] = true;
                } else if(occupation.code.indexOf('SPETSKUTSE') === 0) {
                  dialogScope.specialitiesSelected[occupation.code] = true;
                }
              });
            }
            dialogScope.formState.readOnly = $scope.formState.readOnly ||
                                            $scope.formState.notEditableBasicData &&
                                            curriculumModule.module.code !== 'KUTSEMOODUL_V';
            if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK' && ArrayUtils.includes(dialogScope.filteredModuleValues, curriculumModule.module.code)) {
                ArrayUtils.remove(dialogScope.filteredModuleValues, curriculumModule.module.code);
            }
            //FIXME: exception occurs when trying to open module
            //when creating curriculum from state curriculum

            // quick fix:
            if(!dialogScope.module.outcomes) {
              dialogScope.module.outcomes = [];
            }

            dialogScope.module.outcomes.forEach(function(outcome){
              if(!angular.isDefined(outcome.orderNr)) {
                outcome.orderNr = dialogScope.module.outcomes.indexOf(outcome);
              }
            });
            dialogScope.module.outcomes.sort(function(el1, el2){
              return el1.orderNr - el2.orderNr;
            });
          }
          if ($scope.formState.notEditableBasicData){
            dialogScope.formState.typePreselected = true;
          }
          if($scope.formState.notEditableBasicData && !angular.isDefined(curriculumModule)) {
              dialogScope.filteredModuleValues = ['KUTSEMOODUL_L', 'KUTSEMOODUL_P', 'KUTSEMOODUL_Y'];
          }
          dialogScope.isEditingOutcome = false;
          dialogScope.editedOutcome = null;

          dialogScope.addOutcome = function() {
            if (angular.isString(dialogScope.outcomeEt) && dialogScope.outcomeEt !== '') {
                dialogScope.module.outcomes.push({outcomeEt: dialogScope.outcomeEt, outcomeEn: dialogScope.outcomeEn, order: dialogScope.module.outcomes.length});
                dialogScope.outcomeEt = undefined;
                dialogScope.outcomeEn = undefined;
              }
          };

          dialogScope.editOutcome = function(outcome) {
            dialogScope.isEditingOutcome = true;
            dialogScope.editedOutcome = outcome;
            dialogScope.outcomeEt = outcome.outcomeEt;
            dialogScope.outcomeEn = outcome.outcomeEn;
          };

          dialogScope.saveOutcome = function() {
            if (angular.isString(dialogScope.outcomeEt) && dialogScope.outcomeEt !== '') {
                dialogScope.isEditingOutcome = false;

                dialogScope.editedOutcome.outcomeEt = dialogScope.outcomeEt;
                dialogScope.editedOutcome.outcomeEn = dialogScope.outcomeEn;

                dialogScope.outcomeEt = undefined;
                dialogScope.outcomeEn = undefined;
              }
          };

          dialogScope.delete = function() {

            if(curriculumModule.addedToImplementationPlan) {
                message.error('curriculum.error.curriculumModuleCantBeDeleted');
                return;
            }

            dialogService.confirmDialog({prompt: 'curriculum.moduleDeleteConfirm'}, function() {
              var CurriculumModuleEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + "/module");
              if(dialogScope.module.id) {
              new CurriculumModuleEndpoint(dialogScope.module).$delete().then(function() {
                message.info('main.messages.delete.success');
                ArrayUtils.remove($scope.curriculum.modules, curriculumModule);
              });
              } else {
                ArrayUtils.remove($scope.curriculum.modules, curriculumModule);
              }
              dialogScope.cancel();
            });
          };

          var occupationsSelected =  dialogScope.occupations.map(function(it){return it.occupation.code;});
          if (angular.isArray(occupationsSelected) && occupationsSelected.length > 0) {
            var loadedCompetences = {};
            ClassifierConnect.queryAll({connectClassifierCode: dialogScope.occupations.map(function(it){return it.occupation.code;}), classifierMainClassCode: 'KOMPETENTS'}, function(result) {
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

          function anySpecAdded(specialities) {
              if(!angular.isArray(specialities)) {
                  return false;
              }
              for(var i = 0; i < specialities.length; i++) {
                  if(dialogScope.specialitiesSelected[specialities[i].code]) {
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
                  if(dialogScope.occupationsSelected[partOccupations[i].code]) {
                      return true;
                  }
              }
              return false;
          }

          function occupationsAreValid() {
              var occupations = $scope.curriculum.occupations;
              // module cannot be both in occupation and its speciality
              for(var i = 0; i < occupations.length; i++) {
                  var code = occupations[i].occupation.code;
                  var selected = dialogScope.occupationsSelected[code];
                  if(!selected) {
                      continue;
                  }
                  var specialities = occupations[i].specialities;
                  if(specialities) {
                     for(var k = 0; k < specialities.length; k++) {
                          if(dialogScope.specialitiesSelected[specialities[k].code]) {
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

          dialogScope.occupationsValid = occupationsAreValid();

          dialogScope.validateOccupations = function() {
               dialogScope.occupationsValid = occupationsAreValid();
          };

      },
      function(submittedDialogScope) {
        if (!angular.isDefined(curriculumModule)) {
          submittedDialogScope.module.practice = false;
        }
        mapOccupationsToModule(submittedDialogScope, submittedDialogScope.module);

        submittedDialogScope.module.outcomes.forEach(function(outcome){
          outcome.orderNr = submittedDialogScope.module.outcomes.indexOf(outcome);
        });

        if($scope.curriculum.id) {
          var CurriculumModuleEndpoint = QueryUtils.endpoint('/curriculum/' + $scope.curriculum.id + '/module');
          moduleModelToDto(submittedDialogScope.module);
          curriculumOccupationsToDto($scope.curriculum.occupations);
          var savedItem = new CurriculumModuleEndpoint({
            id: submittedDialogScope.module.id,
            curriculumModule: angular.copy(submittedDialogScope.module),
            curriculumOccupations: $scope.curriculum.occupations
          });
          if(submittedDialogScope.module.id) {
            savedItem.$update().then(function(response) {
              message.info('main.messages.update.success');
              ArrayUtils.remove($scope.curriculum.modules, curriculumModule);
              afterModuleSaved(response);
            });
          } else {
            savedItem.$save().then(function(response) {
              message.info('main.messages.create.success');
              afterModuleSaved(response);
            });
          }
        } else {
          if(curriculumModule) {
            curriculumModule = angular.copy(submittedDialogScope.module);
          } else {
            $scope.curriculum.modules.push(submittedDialogScope.module);
          }
          renderModules();
        }
      });
    };

    function afterModuleSaved(response){
      $scope.curriculum.modules.push(response.curriculumModule);
      $scope.curriculum.occupations = response.curriculumOccupations;
      mapDtoToModel($scope.curriculum, $scope);
      renderModules();
    }

    function mapOccupationsToModule(submittedDialogScope, curriculumModule) {
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
    }





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

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('vocationalCurriculum/file.add.dialog.html', function(scope){
        scope.fileTypeCriteria = {
          vocational: true
        };
      }, function (submitScope) {
        var data = submitScope.data;
        data.sendEhis = false;
        data.ehis = false;
        data.oisFile = oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            if(CurriculumFileEndpoint) {
              var newFile = new CurriculumFileEndpoint(data);
              newFile.$save().then(function(response){
                message.info('main.messages.create.success');
                $scope.curriculum.files.push(response);
              });
            } else {
              $scope.curriculum.files.push(data);
            }
        });
      });
    };

    $scope.deleteFile = function(file) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.files, file);
            if(CurriculumFileEndpoint) {
              var deletedFile = new CurriculumFileEndpoint(file);
              deletedFile.$delete().then(function () {
                message.info('main.messages.delete.success');
              });
            }
        });
    };

    $scope.getUrl = oisFileService.getUrl;

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
      if (angular.isDefined(Session.school)) {
        selectedJointPartners.push(Session.school.ehisSchool);
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

    $scope.filterEmptyJointPartners = function(jointPartner) {
        return jointPartner.ehisSchool || jointPartner.nameEt || jointPartner.nameEn;
    };

   function jointInfoAddedButNotPartners() {
        return ArrayUtils.isEmpty($scope.curriculum.jointPartners) && (
            angular.isDefined($scope.curriculum.supervisor) ||
            angular.isDefined($scope.curriculum.contractEt) ||
            angular.isDefined($scope.curriculum.contractEn)
        );
    }

    function updateJointInfo() {
      if($scope.curriculum.joint) {
          addSharedInformationToJointPartners();
          if(jointInfoAddedButNotPartners()) {
              var jointPartner = {
                abroad: $scope.curriculum.abroad,
                supervisor: $scope.curriculum.supervisor,
                contractEt: $scope.curriculum.contractEt,
                contractEn: $scope.curriculum.contractEn
              };
              $scope.curriculum.jointPartners.push(jointPartner);
          }
      } else {
          $scope.curriculum.jointMentor = undefined;
          $scope.curriculum.jointPartners = [];
      }
    }

    function emptyModulesCreditsAreValid() {
      var modules = $scope.curriculum.modules.filter(function(el){
        return el.module.code !== 'KUTSEMOODUL_V';
      });
      var credits = modules.reduce(function(sum, el){
        return sum += el.credits;
      }, 0);
      return $scope.curriculum.optionalStudyCredits + credits === $scope.curriculum.credits;
    }


    function allOccuppationsCreditsValid() {
        var occupations = $scope.curriculum.occupations;
        for(var i = 0; i < occupations.length; i++) {
            if(!$scope.isOccutationsCreditsValid(occupations[i])) {
                return false;
            }
        }
        return true;
    }

    function getCreditsOfOccupation(occupationCode) {
        return $scope.curriculum.modules.filter(function(el){
          return el.module.code !== 'KUTSEMOODUL_V';
        }).filter(function(m){
          var moduleOccupations = m.occupations.map(function(el){
            return el.code;
          });
          return ArrayUtils.includes(moduleOccupations, occupationCode);
        }).reduce(function(total, val){
                return total + val.credits;
        }, 0);
    }

    function getPartOccupationsCredits(occupation) {
      if(ArrayUtils.isEmpty(occupation.partOccupations)) {
        return 0;
      }
      var sum = 0;
      var partOccupations = occupation.partOccupations;
      for(var i = 0; i < partOccupations.length; i++) {
        sum += getCreditsOfOccupation(partOccupations[i].code);
      }
      return sum;
    }

    function allSpetsOccupationsValid() {
        var occupations = $scope.curriculum.occupations;
        for(var i = 0; i < occupations.length; i++) {
          if(!ArrayUtils.isEmpty(occupations[i]).specialities) {
            var specialities = occupations[i].specialities;
            for(var j = 0; j < specialities.length; j++) {
                if(!$scope.isSpetsOccupationValid(specialities[j], occupations[i])) {
                    return false;
                }
            }
          }
        }
        return true;
    }

    function getCreditsOfRepeatingModules(occupation) {
        if(ArrayUtils.isEmpty(occupation.partOccupations)) {
          return 0;
        }
        var sum = 0;
        var partOccupations = occupation.partOccupations.map(function(el){
          return el.code;
        });
        var modules = $scope.curriculum.modules.filter(function(el){
          return el.module.code !== 'KUTSEMOODUL_V';
        });
        for(var i = 0; i < modules.length; i++) {
            var moduleOccupations = modules[i].occupations.map(function(el){
              return el.code;
            });
            var commonPart = ArrayUtils.intersection(moduleOccupations, partOccupations);
            if(commonPart.length === 0) {
                continue;
            }
            var addedToOccupation = ArrayUtils.includes(moduleOccupations, occupation.occupation.code) ? 1 : 0;
            sum += modules[i].credits * (commonPart.length + addedToOccupation - 1);
        }
        return sum;
    }

    $scope.isSpetsOccupationValid = function(speciality, occupation) {
        var occMod = getCreditsOfOccupation(occupation.occupation.code);
        var subOccMod = getCreditsOfOccupation(speciality.code);
        var partOccMod = getPartOccupationsCredits(occupation);
        var bool = occMod + subOccMod + partOccMod +
        $scope.curriculum.optionalStudyCredits - getCreditsOfRepeatingModules(occupation) === $scope.curriculum.credits;
        return bool;
    };

    $scope.isOccutationsCreditsValid = function(occupation) {
        var sum = getCreditsOfOccupation(occupation.occupation.code);

        var partOccupationsCredits = getPartOccupationsCredits(occupation);
        sum += partOccupationsCredits;
        if(!ArrayUtils.isEmpty(occupation.specialities)) {
          sum += getCreditsOfOccupation(occupation.specialities[0].code);
        }
        sum -= getCreditsOfRepeatingModules(occupation);
        return $scope.curriculum.credits - $scope.curriculum.optionalStudyCredits === sum;
    };

   $scope.hasVerifiedVersions = function() {
      var versions = $scope.curriculum.versions;
      if(ArrayUtils.isEmpty(versions)) {
        return false;
      }
      return !ArrayUtils.isEmpty(versions.filter(function(el){
        return el.status === Curriculum.VERSION_STATUS.K;
      }));
    };

    function validationPassed(messages) {
      $scope.vocationalCurriculumForm.$setSubmitted();

      if(!$scope.vocationalCurriculumForm.$valid) {
          var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
          message.error(errorMessage);
          return false;
      }
      if ($scope.strictValidation()) {
        if($scope.occupationRequired() && ArrayUtils.isEmpty($scope.curriculum.occupations)) {
            message.error('curriculum.error.noOccupation');
            return false;
        } else if(ArrayUtils.isEmpty($scope.curriculum.modules)) {
            message.error('curriculum.error.noModule');
            return false;
        } else if (!ArrayUtils.isEmpty($scope.curriculum.occupations) && !$scope.allOccupationsHavePModule()) {
            message.error('curriculum.error.noPModules');
            return false;
        }  else if (!ArrayUtils.isEmpty($scope.curriculum.occupations) && !$scope.allModulesHaveOccupation()) {
            message.error('curriculum.error.notAllModulesHaveOccupation');
            return false;
        } else if (ArrayUtils.isEmpty($scope.curriculum.occupations) && !anyModuleIsBasic()) {
            message.error('curriculum.error.noBasicModule');
            return false;
            // validate credits
        } else if(ArrayUtils.isEmpty($scope.curriculum.occupations) && !emptyModulesCreditsAreValid()) {
            message.error('curriculum.error.modulesCredits');
            return false;
        } else if(!ArrayUtils.isEmpty($scope.curriculum.occupations) && !allOccuppationsCreditsValid()) {
            message.error('curriculum.error.occupationsCredits');
            return false;
        } else if(!ArrayUtils.isEmpty($scope.curriculum.occupations) && !allSpetsOccupationsValid()) {
            message.error('curriculum.error.spetsOccupationsCredits');
            return false;
        } else if (!$scope.hasVerifiedVersions()) {
            message.error('curriculum.error.noImplementationPlan');
            return false;
        }
      }
      return true;
    }

    $scope.notAllModulesHaveOccupation = function() {
      return $scope.vocationalCurriculumForm.$submitted && $scope.strictValidation() &&
      ($scope.occupationRequired() || !ArrayUtils.isEmpty($scope.curriculum.occupations)) && !$scope.allModulesHaveOccupation();
    }

    function save(messages) {
      if(!validationPassed(messages)) {
        return;
      }

      //before save collect some data;
      addSharedInformationToJointPartners();
      updateJointInfo();

      blockButtons();
      var curriculum = new CurriculumEndpoint(mapModelToDto($scope.curriculum));

      if (angular.isDefined($scope.curriculum.id)) {
        curriculum.$update().then(function() {
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          $scope.vocationalCurriculumForm.$setPristine();
          mapDtoToModel(curriculum, $scope);
        }, function(){
          convertBackToModel(curriculum);
        });
      } else {
        curriculum.newFiles = curriculum.files;
        curriculum.files = undefined;
        curriculum.$save().then(function() {
          message.info('main.messages.create.success');
          // $location.path('/vocationalCurriculum/'+curriculum.id+'/edit').search({_noback: true});
          $scope.vocationalCurriculumForm.$setPristine();
          $location.path('/vocationalCurriculum/'+ curriculum.id + '/edit');
        }, function(){
          convertBackToModel(curriculum);
        });
      }
    }

    $scope.save = function() {
        $scope.formState.strictValidation = false;
        // setTimeout is needed for validation of ng-required fields
        setTimeout(save, 0);
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.deleteconfirm'}, function() {
        blockButtons();
        new CurriculumEndpoint($scope.curriculum).$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/curriculum');
        }, releaseButtons);
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
                // $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit').search({_noback: true});
                $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit');
            });
        } else {
            // $location.path('/vocationalCurriculum/' + $scope.curriculum.id + '/edit').search({_noback: true});
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
        return $scope.curriculum && ($scope.curriculum.status === Curriculum.STATUS.VERIFIED ||
          $scope.curriculum.status === Curriculum.STATUS.PROCEEDING || $scope.formState.strictValidation);
    };




        // --- Statuses
    $scope.saveAndProceed = function() {
      var messages = {
          prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.vocational.proceed' : 'curriculum.statuschange.vocational.proceed',
          errorMessage: 'curriculum.error.inputFieldsNotFilledOnProcede',
          updateSuccess: 'curriculum.success.proceed'
      };
      $scope.formState.strictValidation = true;

      dialogService.confirmDialog({prompt: messages.prompt}, function() {
        // setTimeout is needed for validation of ng-required fields
        setTimeout(function(){

          if(!validationPassed(messages)) {
            return;
          }
          mapModelToDto($scope.curriculum, $scope);
          var SaveAndProceedEndpoint = QueryUtils.endpoint('/curriculum/saveAndProceed');
          changeStatus(SaveAndProceedEndpoint, messages);
        }, 0);
      });
    };

    $scope.setStatusClosed = function() {

      var messages = {
          prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.vocational.close' : 'curriculum.statuschange.vocational.close',
          updateSuccess: 'curriculum.success.closed'
      };

      dialogService.confirmDialog({prompt: messages.prompt}, function() {
        var ClosingEndpoint = QueryUtils.endpoint("/curriculum/close");
        changeStatus(ClosingEndpoint, messages);
      });
    };

    $scope.sendToEhis = function() {
      var SendToEhisEndpoint = QueryUtils.endpoint("/curriculum/sendToEhis");
      changeStatus(SendToEhisEndpoint, {updateSuccess: 'curriculum.sentToEhis'});
    };

    $scope.updateFromEhis = function() {
      var UpdateFromEhisEndpoint = QueryUtils.endpoint("/curriculum/updateFromEhis");
      changeStatus(UpdateFromEhisEndpoint, {updateSuccess: 'curriculum.message.ehisStatusUpdated'});
    };

    function updateStatus(response) {
      $scope.curriculum.status = response.status;
      $scope.curriculum.changedBy = response.changedBy;
      $scope.curriculum.changed = response.changed;
      $scope.curriculum.ehisChanged = response.ehisChanged;
      $scope.curriculum.ehisStatus = response.ehisStatus;
    }

    function changeStatus(StatusChangeEndpoint, messages) {
      blockButtons();
      new StatusChangeEndpoint($scope.curriculum).$update().then(function(response){
        message.info(messages.updateSuccess);
        updateStatus(response);
        $scope.vocationalCurriculumForm.$setPristine();
        goToReadOnlyForm();
      }, releaseButtons);
    }

    function goToReadOnlyForm() {
      if(!$scope.formState.readOnly) {
          $location.path('/vocationalCurriculum/' + $scope.curriculum.id +'/view');
      }
    }

    $scope.isOccupationChanged = function() {
        if(!ArrayUtils.isEmpty($scope.curriculum.occupations)) {
            var prompt = $scope.curriculum.occupation ? 'curriculum.prompt.hasOccupations' : 'curriculum.prompt.hasSubOccupations';
            $scope.curriculum.occupation = !$scope.curriculum.occupation;
            dialogService.confirmDialog({prompt: prompt}, function() {
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

    function filterByOccupation(occupation){
        return function(el) {
            return el.code !== occupation.occupation.code;
        };
    }

    function filterBySpeciality(occupation) {
        return function(el) {
            var specialties = occupation.specialities.map(function(el2){
                return el2.code;
            });
            return !ArrayUtils.includes(specialties, el.code);
        };
    }

    function filterByPartOccupation(occupation) {
        return function(el) {
            var partOccupations = occupation.partOccupations.map(function(el2){
                return el2.code;
            });
            return !ArrayUtils.includes(partOccupations, el.code);
        };
    }

    function removeOccupationFromModules(occupation) {
        var modules = $scope.curriculum.modules;
        for(var i = 0; i < modules.length; i++) {
            modules[i].occupations = modules[i].occupations.filter(filterByOccupation(occupation));
            modules[i].occupations = modules[i].occupations.filter(filterBySpeciality(occupation));
            modules[i].occupations = modules[i].occupations.filter(filterByPartOccupation(occupation));
        }
    }

    $scope.removeOccupation = function(occupation) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
          $scope.vocationalCurriculumForm.$setDirty();
          removeOccupationFromModules(occupation);
          ArrayUtils.remove($scope.curriculum.occupations, occupation);
        });
    };

    $scope.goToVersionNewForm = function(){
        var url = '/vocationalCurriculum/' + $scope.curriculum.id + '/moduleImplementationPlan/new';
        if(!$scope.formState.readOnly) {
            dialogService.confirmDialog({prompt: 'curriculum.prompt.goToVersionForm'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.goToImplementationPlanForm = function(version) {
        var url = $scope.formState.readOnly || version.status !== Curriculum.VERSION_STATUS.S ?
        '/vocationalCurriculum/' + $scope.curriculum.id + '/moduleImplementationPlan/' + version.id + '/view' :
        '/vocationalCurriculum/' + $scope.curriculum.id + '/moduleImplementationPlan/' + version.id + '/edit';
        if(!$scope.formState.readOnly) {
            dialogService.confirmDialog({prompt: 'curriculum.prompt.goToVersionForm'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.versionCanBeAdded = function() {
        return $scope.curriculum && (
            $scope.curriculum.status === Curriculum.STATUS.ENTERING && !$scope.formState.readOnly ||
            $scope.curriculum.status === Curriculum.STATUS.PROCEEDING ||
            $scope.curriculum.status === Curriculum.STATUS.VERIFIED
        );
    };


    $scope.allModulesHaveOccupation = function() {
        var emptyModules = $scope.getModulesWithoutOccupation();
        return ArrayUtils.isEmpty(emptyModules);
    };

    $scope.getModulesWithoutOccupation = function() {
        return $scope.curriculum.modules.filter(function(el){
            return ArrayUtils.isEmpty(el.occupations);
        });
    };

    function getBasicModules() {
        return $scope.curriculum.modules.filter(function(el){
            return el.module.code === 'KUTSEMOODUL_P';
        });
    }

    function anyModuleIsBasic() {
        return !ArrayUtils.isEmpty(getBasicModules());
    }

    $scope.modulesValid = function() {
        if(ArrayUtils.isEmpty($scope.curriculum.modules)) {
            return false;
        }
        if(ArrayUtils.isEmpty($scope.curriculum.occupations)) {
            return anyModuleIsBasic() && emptyModulesCreditsAreValid();
        }
        return $scope.allModulesHaveOccupation() && $scope.allOccupationsHavePModule() &&
        allOccuppationsCreditsValid() && allSpetsOccupationsValid();
    };

    $scope.allOccupationsHavePModule = function() {
        var occupations = $scope.curriculum.occupations;
        for (var i = 0; i < occupations.length; i++) {
            if(!$scope.occupationHasPModule(occupations[i].occupation.code)) {
                return false;
            }
            var specialities = occupations[i].specialities;
            if(!ArrayUtils.isEmpty(specialities)) {
                for (var j = 0; j < specialities.length; j++) {
                    if(!$scope.occupationHasPModule(specialities[j].code)) {
                        return false;
                    }
                }
            }
            var partOccupations = occupations[i].partOccupations;
            if(!ArrayUtils.isEmpty(partOccupations)) {
                for (var k = 0; k < partOccupations.length; k++) {
                    if(!$scope.occupationHasPModule(partOccupations[k].code)) {
                        return false;
                    }
                }
            }
        }
        return true;
    };

    $scope.occupationHasPModule = function(occupationCode){
        return !ArrayUtils.isEmpty(getPModulesByOccupation(occupationCode));
    };

    function getPModulesByOccupation(occupationCode) {
        return $scope.curriculum.modules.filter(function(el){
            var occupations = el.occupations.map(function(el2){
                return el2.code;
            });
            return el.module.code === 'KUTSEMOODUL_P' && ArrayUtils.includes(occupations, occupationCode);
        });
    }


    $scope.occupationRequired = function() {
        return $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KUTSE' || $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK';
    };

    $scope.occupationCanBeChanged = function() {
        return $scope.curriculum.draft !== 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK';
    };
  });
