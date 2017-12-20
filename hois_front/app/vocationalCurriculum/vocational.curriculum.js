'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumController', function ($scope, Classifier, dialogService, ClassifierConnect, ArrayUtils,
    message, oisFileService, QueryUtils, $route, DataUtils, $location, Curriculum, $q, config, $rootScope, Session) {
      var clMapper = Classifier.valuemapper({occupations: 'KUTSE', partOccupations: 'OSAKUTSE', specialities: 'SPETSKUTSE'});
      $scope.school = $route.current.locals.auth ? $route.current.locals.auth.school : null;
      var id = $route.current.params.id;
      var OccupationEndpoint = QueryUtils.endpoint('/curriculumOccupation');


      $scope.maxStydyYears = {max: 100};

      $scope.STATUS = Curriculum.STATUS;
      var CurriculumFileEndpoint;

      $scope.formState = {
         readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
      };

      function getInitialSchoolDepartments() {
        if(id) {
          QueryUtils.endpoint('/curriculum/schoolDepartments/' + id).query().$promise.then(function(response){
            $scope.schoolDepartments = response;
            if($scope.vocationalCurriculumForm) {
              $scope.vocationalCurriculumForm.$setPristine();
            }
          });
        } else {
          getSchoolDepartmentOptions();
        }
      }

      getInitialSchoolDepartments();

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

      $q.all(promises).then(function() {
        angular.extend(scope.curriculum, curriculum);
      });

    };/*mapDtoToModel*/

    var mapModelToDto = function(curriculumModel) {
      var dto = angular.extend({}, curriculumModel);

      curriculumModel.studyPeriodMonths = curriculumModel.studyPeriodMonths ? curriculumModel.studyPeriodMonths : 0;
      dto.studyPeriod = curriculumModel.studyPeriodMonths + 12 * curriculumModel.studyPeriodYears;

      return dto;
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


    $scope.$watchCollection('curriculum.curriculumOccupations', function() {
        $scope.validation.occupationsLength = $scope.curriculum && $scope.curriculum.curriculumOccupations ? $scope.curriculum.curriculumOccupations.length : 0;
        if($scope.curriculum && $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA') {
            $scope.validation.occupationsLength = 1;
        }
    });

    $scope.schoolOrigStudyLevel = [];
    function getAllowedStudyLevels() {
      QueryUtils.endpoint('/curriculum/studyLevels').query({curriculum: id, isHigher: false}).$promise.then(function(response) {
          $scope.schoolOrigStudyLevel = response;
      });
    }
    getAllowedStudyLevels();

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
      angular.extend($scope.curriculum, defaultValues);
    }

    $scope.draftOptions = {};
    Classifier.queryForDropdown({mainClassCode: 'OPPEKAVA_LOOMISE_VIIS'}, function(response) {
      $scope.draftOptions = Classifier.toMap(response);
    });

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

    function setInitialDraft() {
      $scope.curriculum.draft = 'OPPEKAVA_LOOMISE_VIIS_KUTSE';
    }

    $scope.draftSelected = function() {
      if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK') {
        dialogService.showDialog('vocationalCurriculum/dialog/state.curriculum.selection.dialog.html',
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

          var command = {
            id: submittedDialogScope.stateCurriculum.id,
            occupations: []
          };

          if (angular.isArray(submittedDialogScope.stateCurriculumOccupations)) {

            submittedDialogScope.stateCurriculumOccupations.forEach(function(occupation) {
              var addedOccupation;
              if(submittedDialogScope.selectedStateCurriculumOccupations[occupation.occupation.code] === true) {
                addedOccupation = {occupation: occupation.occupation.code, specialities: []};
                command.occupations.push(addedOccupation);
              }

              if (angular.isArray(occupation.partOccupations)) {
                occupation.partOccupations.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumPartOccupations[it.code] === true && !addedOccupation) {
                    var partOccupation = {occupation: it.code};
                    command.occupations.push(partOccupation);
                  }
                });
              }

              if (angular.isArray(occupation.specialities)) {
                occupation.specialities.forEach(function(it) {
                  if(submittedDialogScope.selectedStateCurriculumSpecialities[it.code] === true) {
                    if (!addedOccupation) {
                      addedOccupation = {occupation: occupation.occupation.code, specialities: [], occupationGrant: false};
                      command.occupations.push(addedOccupation);
                    }
                    addedOccupation.specialities.push(it.code);
                  }
                });
              }
            });
          }

          QueryUtils.endpoint('/curriculum/copy/statecurriculum')
          .put(command).$promise.then(function(response){
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+ response.id + '/edit');
          }, setInitialDraft);
        }, setInitialDraft);

      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KOOL') {
        dialogService.showDialog('vocationalCurriculum/dialog/school.curriculum.selection.dialog.html',
        function(dialogScope) {
          dialogScope.curriculumSelected = [];

          QueryUtils.createQueryForm(dialogScope, '/curriculum', {order: $scope.currentLanguageNameField()});
          dialogScope.clearCriteria();
          dialogScope.loadData();
        },
        function(submittedDialogScope) {
          var CopiedCurriculumEndpoint = QueryUtils.endpoint('/curriculum/copy/curriculum');
          var copiedCurriculum = new CopiedCurriculumEndpoint({id: submittedDialogScope.curriculumSelected[0].id});
          copiedCurriculum.$update().then(function(response){
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+ response.id + '/edit');
          }, setInitialDraft);
        }, setInitialDraft);
      } else if($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA') {
        $scope.curriculum.curriculumOccupations = [];
        $scope.curriculum.occupation = false;
      }

      $scope.isDraftEmployerSupportLetter = ($scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_TOOANDJA');

    };

    /**
     * Removes occupations from modules
     */
    var updateModules = function() {
      var occupationCodes = {};
      if (angular.isArray($scope.curriculum.curriculumOccupations)) {
        $scope.curriculum.curriculumOccupations.forEach(function(curriculumOccupation) {
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
            return occupationCodes[o] === true;
          });
        });
      }
    };

    $scope.occupationChanged = function() {
      $scope.curriculum.curriculumOccupations = [];
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
      dialogService.showDialog('vocationalCurriculum/dialog/occupation.add.dialog.html',
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

          if (!angular.isDefined(curriculumOccupation)) {
            curriculumOccupation = {
              curriculum: id
            };
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
          var dto = occupationToDto(curriculumOccupation);
          if(dto.id) {
            new OccupationEndpoint(dto).$update().then(function(response){
              message.info('main.messages.update.success');
              curriculumOccupation = response;
              getModules();
              setFormPristine();
            });
          } else {
            new OccupationEndpoint(dto).$save().then(function(response){
              message.info('main.messages.create.success');
              $scope.curriculum.curriculumOccupations.push(response);
              setFormPristine();
            });
          }
        });
    };

    /**
     * when value of curriculum.occupation field is changed and
     * new occupation or part occupation saved, the field curriculum.occupation
     * should be set to pristine, as its value is saved as well.
     *
     * Without this function back button would not work as expected in this case.
     */
    function setFormPristine() {
      $scope.vocationalCurriculumForm.isOccupation.$setPristine();
      var dirtyField = $scope.vocationalCurriculumForm.$$controls.find(function(e){
        return e.$dirty;
      });
      if(!dirtyField) {
        $scope.vocationalCurriculumForm.$setPristine();
      }
    }

    function occupationToDto(occupation) {
      var dto = angular.copy(occupation);
      dto.occupation = dto.occupation.code;
      if(dto.partOccupations) {
        dto.partOccupations = dto.partOccupations.map(function(p){
          return p.code;
        });
      }
      if(dto.specialities) {
        dto.specialities = dto.specialities.map(function(s){
          return s.code;
        });
      }
      return dto;
    }


    $scope.openPartOccupationsDialog = function(curriculumPartOccupation) {

        dialogService.showDialog('vocationalCurriculum/dialog/partOccupation.add.dialog.html',
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
              readOnly: $scope.formState.readOnly || !$scope.occupationCanBeChanged() || $scope.formState.notEditableBasicData
            };
          },
          function(submittedDialogScope) {
            if (!angular.isDefined(curriculumPartOccupation)) {
              curriculumPartOccupation = {
                  occupationGrant: false,
                  curriculum: id
              };
            }
            curriculumPartOccupation.occupation = submittedDialogScope.partOccupationAquired;

            var dto = occupationToDto(curriculumPartOccupation);
            if(dto.id) {
              new OccupationEndpoint(dto).$update().then(function(response){
                message.info('main.messages.update.success');
                curriculumPartOccupation = response;
                getModules();
                setFormPristine();
              });
            } else {
              new OccupationEndpoint(dto).$save().then(function(response){
                message.info('main.messages.create.success');
                $scope.curriculum.curriculumOccupations.push(response);
                setFormPristine();
              });
            }
          });
    };

    function moduleCannotBeEdited(curriculumModule) {
      return $scope.formState.readOnly ||
             $scope.formState.notEditableBasicData &&
             curriculumModule.module !== 'KUTSEMOODUL_V';
    }

    function openAddModuleDialog(curriculumModule) {
      var url = '';
      if(!curriculumModule) {
        url = '/vocationalCurriculum/'+ $scope.curriculum.id + '/module/new';
      } else if (moduleCannotBeEdited(curriculumModule)) {
        url = '/vocationalCurriculum/'+ $scope.curriculum.id + '/module/' + curriculumModule.id + '/view';
      } else {
        url = '/vocationalCurriculum/'+ $scope.curriculum.id + '/module/' + curriculumModule.id + '/edit';
      }
      $location.path(url);
    }

    $scope.openAddModuleDialog = function(curriculumModule) {
      if(!$scope.formState.readOnly && $scope.vocationalCurriculumForm.$dirty) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          openAddModuleDialog(curriculumModule);
        });
      } else {
        openAddModuleDialog(curriculumModule);
      }
    };

    var renderModules = function() {
      if (angular.isArray($scope.curriculum.modules)) {
        var modulesView = Curriculum.modulesViewData($scope.curriculum.modules);
        $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
        $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;

        $scope.validation.mainModuleCount = 0;
        $scope.curriculum.modules.forEach(function(it) {
          if (it.module === "KUTSEMOODUL_P") {
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
      dialogService.showDialog('curriculum/dialog/file.add.dialog.html', function(scope){
        scope.fileTypeCriteria = {
          vocational: true
        };
      }, function (submitScope) {
        var data = submitScope.data;
        data.sendEhis = false;
        data.ehis = false;
        data.oisFile = oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            var newFile = new CurriculumFileEndpoint(data);
            newFile.$save().then(function(response){
              message.info('main.messages.create.success');
              $scope.curriculum.files.push(response);
            });
        });
      });
    };

    $scope.deleteFile = function(file) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove($scope.curriculum.files, file);
            var deletedFile = new CurriculumFileEndpoint(file);
            deletedFile.$delete().then(function () {
              message.info('main.messages.delete.success');
            });
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
      updateJointPartners();
      getSchoolDepartmentOptions();
    };

    $scope.validation.jointPartnersLength = 0;

    function getMyEhisSchool() {
      if($scope.curriculum.ehisSchool) {
        return $scope.curriculum.ehisSchool;
      } else if(Session.school && Session.school.ehisSchool) {
        return Session.school.ehisSchool;
      }
      return null;
    }

    function updateJointPartners() {
      var selectedJointPartners = [];
      selectedJointPartners.push(getMyEhisSchool());
      if (angular.isArray($scope.curriculum.jointPartners)) {
        $scope.validation.jointPartnersLength = $scope.curriculum.jointPartners.length;
        $scope.curriculum.jointPartners.forEach(function(it) {
          if(!it.abroad) {
            selectedJointPartners.push(it.ehisSchool);
          }
        });
      }
      $scope.selectedJointPartners = selectedJointPartners;
    }

    // $scope.$watchCollection('curriculum.jointPartners', updateJointPartners);

    function getSchoolDepartmentOptions() {
      QueryUtils.endpoint('/curriculum/schoolDepartments').query({ehisShools: $scope.selectedJointPartners, curriculum: id}).$promise.then(function(response){
        $scope.schoolDepartments = response;
        /*
         * Although partners' school departments are not displayed in options,
         * they are not actually removed from the list of
         * curriculum's school department.
         * Code below handles that.
         */
        if($scope.curriculum.schoolDepartments) {
          var schoolDepartmentsIds = response.map(function(d){
            return d.id;
          });
          $scope.curriculum.schoolDepartments = $scope.curriculum.schoolDepartments.filter(function(d){
            return ArrayUtils.includes(schoolDepartmentsIds, d);
          });
        }
      });
    }

    $scope.$watch('curriculum.joint', function(newVal, oldVal){
      if(oldVal && !newVal) {
        removeJointInfo();
        updateJointPartners();
        getSchoolDepartmentOptions();
      } else if (!oldVal && newVal) {
        updateJointPartners();
      }
    });

    function removeJointInfo() {
      $scope.curriculum.abroad = false;
      $scope.curriculum.jointPartners = [];

      $scope.curriculum.supervisor = undefined;
      $scope.curriculum.jointMentor = undefined;
      $scope.curriculum.contractEt = undefined;
      $scope.curriculum.contractEn = undefined;
    }

    $scope.removejointPartner = function(item) {
      dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
        ArrayUtils.remove($scope.curriculum.jointPartners, item);
        $scope.vocationalCurriculumForm.$setDirty();
        updateJointPartners();
        getSchoolDepartmentOptions();
      });
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
        return el.module !== 'KUTSEMOODUL_V';
      });
      var credits = modules.reduce(function(sum, el){
        return sum += el.credits;
      }, 0);
      return $scope.curriculum.optionalStudyCredits + credits === $scope.curriculum.credits;
    }


    function allOccuppationsCreditsValid() {
        var occupations = $scope.curriculum.curriculumOccupations;
        for(var i = 0; i < occupations.length; i++) {
            if(!$scope.isOccutationsCreditsValid(occupations[i])) {
                return false;
            }
        }
        return true;
    }

    function getCreditsOfOccupation(occupationCode) {
        return $scope.curriculum.modules.filter(function(el){
          return el.module !== 'KUTSEMOODUL_V';
        }).filter(function(m){
          var moduleOccupations = m.occupations.map(function(el){
            return el;
          });
          return ArrayUtils.includes(moduleOccupations, occupationCode);
        }).reduce(function(total, val){
                return val.isAdditional ? total : total + val.credits;
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
        var occupations = $scope.curriculum.curriculumOccupations;
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
          return el.module !== 'KUTSEMOODUL_V' && !el.isAdditional;
        });
        for(var i = 0; i < modules.length; i++) {
            var moduleOccupations = modules[i].occupations.map(function(el){
              return el;
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
        var creditsOfRepeatingModules = getCreditsOfRepeatingModules(occupation);
        var bool = occMod + subOccMod + partOccMod +
        $scope.curriculum.optionalStudyCredits - creditsOfRepeatingModules === $scope.curriculum.credits;
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
        if($scope.occupationRequired() && ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations)) {
            message.error('curriculum.error.noOccupation');
            return false;
        } else if(ArrayUtils.isEmpty($scope.curriculum.modules)) {
            message.error('curriculum.error.noModule');
            return false;
        } else if (!ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !$scope.allOccupationsHavePModule()) {
            message.error('curriculum.error.noPModules');
            return false;
        }  else if (!ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !$scope.allModulesHaveOccupation()) {
            message.error('curriculum.error.notAllModulesHaveOccupation');
            return false;
        } else if (ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !anyModuleIsBasic()) {
            message.error('curriculum.error.noBasicModule');
            return false;
            // validate credits
        } else if(ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !emptyModulesCreditsAreValid()) {
            message.error('curriculum.error.modulesCredits');
            return false;
        } else if(!ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !allOccuppationsCreditsValid()) {
            message.error('curriculum.error.occupationsCredits');
            return false;
        } else if(!ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations) && !allSpetsOccupationsValid()) {
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
      ($scope.occupationRequired() || !ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations)) && !$scope.allModulesHaveOccupation();
    };

    function save(messages) {
      if(!validationPassed(messages)) {
        return;
      }

      //before save collect some data;
      addSharedInformationToJointPartners();
      updateJointInfo();

      var curriculum = new CurriculumEndpoint(mapModelToDto($scope.curriculum));

      if (angular.isDefined($scope.curriculum.id)) {
        curriculum.$update().then(function(response) {
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          $scope.vocationalCurriculumForm.$setPristine();
          mapDtoToModel(response, $scope);
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
        new CurriculumEndpoint($scope.curriculum).$delete().then(function() {
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
      dialogService.confirmDialog({ prompt: 'curriculum.ehisconfirm' }, function () {
        var SendToEhisEndpoint = QueryUtils.endpoint("/curriculum/sendToEhis");
        changeStatus(SendToEhisEndpoint, {updateSuccess: 'curriculum.sentToEhis'});
      });
    };

    $scope.updateFromEhis = function() {
      var UpdateFromEhisEndpoint = QueryUtils.endpoint("/curriculum/updateFromEhis");
      changeStatus(UpdateFromEhisEndpoint, {updateSuccess: 'curriculum.message.ehisStatusUpdated'});
    };

    function changeStatus(StatusChangeEndpoint, messages) {
      new StatusChangeEndpoint($scope.curriculum).$update().then(function(response){
        message.info(messages.updateSuccess);
        mapDtoToModel(response, $scope);
        $scope.vocationalCurriculumForm.$setPristine();
        goToReadOnlyForm();
      });
    }

    function goToReadOnlyForm() {
      if(!$scope.formState.readOnly) {
          $location.path('/vocationalCurriculum/' + $scope.curriculum.id +'/view');
      }
    }

    $scope.isOccupationChanged = function() {
        if(!ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations)) {
            var prompt = $scope.curriculum.occupation ? 'curriculum.prompt.hasOccupations' : 'curriculum.prompt.hasSubOccupations';
            $scope.curriculum.occupation = !$scope.curriculum.occupation;
            dialogService.confirmDialog({prompt: prompt}, function() {
                for(var i = 0; i < $scope.curriculum.modules.length; i++) {
                    $scope.curriculum.modules[i].occupations = [];
                }
                $scope.curriculum.curriculumOccupations = [];
                $scope.curriculum.occupation = !$scope.curriculum.occupation;
            });
        }
    };


    Classifier.queryForDropdown({mainClassCode: 'KUTSEMOODUL'}, function(response) {
        $scope.moduleTypes = response;
    });

    $scope.filterEmptyModulesByType = function(typeCode) {
        return function(module1) {
            return module1.module === typeCode && ArrayUtils.isEmpty(module1.occupations);
        };
    };

   $scope.filterModulesByType = function(typeCode, occupationCode) {
        return function(module1) {
            var moduleOccupations = module1.occupations;
            return module1.module === typeCode && ArrayUtils.includes(moduleOccupations, occupationCode);
        };
    };
    //TODO: filter types of modules without occupations, filter occupatios / suboccupations
    $scope.filterTypesWithoutModules = function(occupationCode, modules) {
        return function(type) {
            var thisTypeModules = modules ? modules.filter(function(el){
                var occupations = el.occupations;
                return ArrayUtils.includes(occupations, occupationCode) && el.module === type.code;
            }) : [];
            return !ArrayUtils.isEmpty(thisTypeModules);
        };
    };

    $scope.filterTypesWithoutEmptyModules = function(type) {
        var modules = $scope.curriculum.modules.filter(function(el){
            return ArrayUtils.isEmpty(el.occupations);
        });
        var thisTypeModules = modules ? modules.filter(function(el){
            return el.module === type.code;
        }) : [];
        return !ArrayUtils.isEmpty(thisTypeModules);
    };

    /**
     * After deleting occupations, it should be also deleted from modules
     */
    function getModules() {
      CurriculumEndpoint.get({id: $scope.curriculum.id}).$promise.then(function(response){
        $scope.curriculum.modules = response.modules;
      });
    }

    $scope.removeOccupation = function(occupation) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {

          new OccupationEndpoint(occupation).$delete().then(function(){
            message.info('main.messages.delete.success');
            ArrayUtils.remove($scope.curriculum.curriculumOccupations, occupation);
            getModules();
          });
        });
    };

    $scope.goToVersionNewForm = function(){
        var url = '/vocationalCurriculum/' + $scope.curriculum.id + '/moduleImplementationPlan/new';
        if(!$scope.formState.readOnly && $scope.vocationalCurriculumForm.$dirty) {
            dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
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
        if(!$scope.formState.readOnly && $scope.vocationalCurriculumForm.$dirty) {
            dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    $scope.versionCanBeAdded = function() {
        return $scope.curriculum && $scope.curriculum.canChange && (
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
            return el.module === 'KUTSEMOODUL_P';
        });
    }

    function anyModuleIsBasic() {
        return !ArrayUtils.isEmpty(getBasicModules());
    }

    $scope.modulesValid = function() {
        if(ArrayUtils.isEmpty($scope.curriculum.modules)) {
            return false;
        }
        if(ArrayUtils.isEmpty($scope.curriculum.curriculumOccupations)) {
            return anyModuleIsBasic() && emptyModulesCreditsAreValid();
        }
        return $scope.allModulesHaveOccupation() && $scope.allOccupationsHavePModule() &&
        allOccuppationsCreditsValid() && allSpetsOccupationsValid();
    };

    $scope.allOccupationsHavePModule = function() {
        var occupations = $scope.curriculum.curriculumOccupations;
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
                return el2;
            });
            return el.module === 'KUTSEMOODUL_P' && ArrayUtils.includes(occupations, occupationCode);
        });
    }


    $scope.occupationRequired = function() {
        return $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_KUTSE' || $scope.curriculum.draft === 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK';
    };

    $scope.occupationCanBeChanged = function() {
        return $scope.curriculum.draft !== 'OPPEKAVA_LOOMISE_VIIS_RIIKLIK';
    };

  });
