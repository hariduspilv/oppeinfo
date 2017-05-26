'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleImplementationPlanController',

  function ($scope, Curriculum, Classifier, ClassifierConnect, QueryUtils, DataUtils, message, $location, $route, $q, dialogService, $routeParams, ArrayUtils) {
    var curriculumEntity = $route.current.locals.curriculum;
    $scope.initializing = true;
    var capacitiesData = [];

    $scope.VERSION_STATUS = Curriculum.VERSION_STATUS;

    $scope.formState = {
        readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
    };

    var entity = {curriculum: curriculumEntity.id, occupationModules: []};
    var initialImplementationPlan = {
      status: Curriculum.VERSION_STATUS.S,
      validFrom: new Date(),
      code: curriculumEntity.code + ' - ' + curriculumEntity.nameEt
    };

    if (angular.isDefined($route.current.params.versionId)) {
      curriculumEntity.versions.forEach(function(it){
        if (it.id.toString() === $route.current.params.versionId) {
          angular.extend(entity, it);
        }
      });

      $scope.implementationPlan = entity;
      DataUtils.convertStringToDates($scope.implementationPlan, ["validFrom", "validThru"]);
    } else if (angular.isDefined($route.current.locals.copy)) {
      angular.extend(entity, initialImplementationPlan, $route.current.locals.copy);
      $scope.implementationPlan = entity;
    } else {
      angular.extend(entity, initialImplementationPlan);
      $scope.implementationPlan = entity;
    }
    $scope.currentStatus = $scope.implementationPlan.status;

    $scope.codeUniqueQuery = {
      fk: curriculumEntity.id,
      paramName: 'code',
      url: '/curriculum/version//unique',
      id: angular.isDefined($scope.implementationPlan.id) ? $scope.implementationPlan.id : undefined
    };

    $scope.curriculumId = curriculumEntity.id;
    $scope.studyForms = curriculumEntity.studyForms;
    $scope.curriculumStudyForms = curriculumEntity.studyForms;

    var admissionYears = [];
    var currentYear = new Date().getFullYear();
    for(var year = currentYear - 10; year <= currentYear + 2; year++) {
      admissionYears.push(year);
    }
    $scope.admissionYears = admissionYears;


    var promises = [];
    if (angular.isArray(curriculumEntity.modules)) {
      var modules = [];
      curriculumEntity.modules.forEach(function(it) {
        var module = {};
        if (angular.isString(it.module)) {
          var promise = Classifier.get(it.module).$promise.then(function(classifier) {
            angular.extend(module, it, {module: classifier});
          });
          promises.push(promise);
        }


        if (angular.isArray(it.occupations)) {
            var occupationPromises = [];
            it.occupations.forEach(function(occupationCode){
              occupationPromises.push(Classifier.get(occupationCode).$promise);
            });
            var occupationsPromise = $q.all(occupationPromises).then(function(occupations){
              angular.extend(module, {occupations: occupations});
            });
            promises.push($q.all(occupationsPromise));
        }
        modules.push(module);

      });
    }

    var occupations = [];
    if (angular.isArray(curriculumEntity.occupations)) {
        curriculumEntity.occupations.forEach(function(it) {
          var occupation = {};
          var promise = Classifier.get(it.occupation).$promise.then(function(classifier) {
            angular.extend(occupation, it, {occupation: classifier});
          });
          promises.push(promise);
           var partOccupations = [];
          var partOccupationsPromise = ClassifierConnect.queryAll({connectClassifierCode: it.occupation, classifierMainClassCode: 'OSAKUTSE'}, function(result) {
            result.forEach(function(classifierConnect) {
                partOccupations.push(classifierConnect.classifier);
            });
            angular.extend(occupation, {partOccupations: partOccupations});
          }).$promise;
          promises.push(partOccupationsPromise);

          if (angular.isArray(it.specialities)) {
            var specialityPromises = [];
            it.specialities.forEach(function(specialityCode){
              specialityPromises.push(Classifier.get(specialityCode).$promise);
            });
            var specialitiesPromise = $q.all(specialityPromises).then(function(specialities){
              angular.extend(occupation, {specialities: specialities});
            });
            specialityPromises.push(specialitiesPromise);

            promises.push($q.all(specialityPromises));
          }
          occupations.push(occupation);
        });
    }

    var capacitiesPromise = Classifier.queryForDropdown({mainClassCode: 'MAHT', order: 'nameEt'}, function(response) {
      capacitiesData = response;
    }).$promise;
    promises.push(capacitiesPromise);


    $q.all(promises).then(function() {
      var modulesView = Curriculum.modulesViewData(modules);
      $scope.occupations = occupations;
      $scope.modules = curriculumEntity.modules;
      $scope.isOccupation = curriculumEntity.occupation;
      
      $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
      $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;
      $scope.initializing = false;
    });

    $scope.openViewModuleDialog = function(curriculumModule) {
      dialogService.showDialog('vocationalCurriculum/module.view.dialog.html',
      function(dialogScope) {
          dialogScope.occupations = occupations;
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
              curriculumModule.occupations.forEach(function(occupation) {
                if (angular.isDefined(occupation.code)) {
                  if(occupation.code.indexOf('KUTSE') === 0 || occupation.code.indexOf('OSAKUTSE') === 0) {
                    dialogScope.occupationsSelected[occupation.code] = true;
                  } else if(occupation.code.indexOf('SPETSKUTSE') === 0) {
                    dialogScope.specialitiesSelected[occupation.code] = true;
                  }
                }
              });
            }
          }
      },
      null);
    };


    var openAddThemeDialog = function(occupationModule, curriculumModule) {
      return function(occupationModuleTheme) {
        dialogService.showDialog('vocationalCurriculum/module.data.theme.add.dialog.html',
        function(dialogScope) {
          dialogScope.capacities = capacitiesData;
          if (!angular.isArray(occupationModule.themes)) {
            occupationModule.themes = [];
          }

          if (!angular.isDefined(occupationModuleTheme)) {
            dialogScope.occupationModuleTheme = {capacities: []};
          } else {
            dialogScope.occupationModuleTheme = occupationModuleTheme;
          }

          dialogScope.setDefaultHours = function() {
            if (angular.isNumber(dialogScope.occupationModuleTheme.credits)) {
              dialogScope.occupationModuleTheme.hours = 26 * dialogScope.occupationModuleTheme.credits;
            }
          };
        },
        function(submittedDialogScope) {
          if (!angular.isDefined(occupationModuleTheme)) {
            occupationModule.themes.push(submittedDialogScope.occupationModuleTheme);
          }
          $scope.openAddModuleDataDialog(curriculumModule, occupationModule);
        });
      };
    };


    var calculateYearCapacities = function(themes, yearCapacities) {
      var creditsPerYearNumber = [];
      if (angular.isArray(themes)) {
        themes.forEach(function(theme) {
          if (angular.isNumber(theme.studyYearNumber)) {
            if (!angular.isDefined(creditsPerYearNumber[theme.studyYearNumber])) {
              creditsPerYearNumber[theme.studyYearNumber] = 0;
            }
            creditsPerYearNumber[theme.studyYearNumber] += theme.credits;
          }
        });
      }
      if (yearCapacities.length === 0) {
        creditsPerYearNumber.forEach(function(credits, studyYearNumber) {
          var index = studyYearNumber-1;
          var capacity = {calculated: true, credits: credits, studyYearNumber: studyYearNumber};
          yearCapacities[index] = capacity;
        });
      } else {
        yearCapacities.forEach(function(it) {
          if (angular.isDefined(it.studyYearNumber) && angular.isDefined(creditsPerYearNumber[it.studyYearNumber])) {
            it.credits = creditsPerYearNumber[it.studyYearNumber];
            it.calculated = true;
          } else {
            it.calculated = false;
          }
        });
      }
    };

    $scope.openAddModuleDataDialog = function(curriculumModule, moduleData) {
      dialogService.showDialog('vocationalCurriculum/module.data.add.dialog.html',
      function(dialogScope) {
        dialogScope.capacities = capacitiesData;
        dialogScope.formState = $scope.formState;
        var occupationModule;
        entity.occupationModules.forEach(function(it) {
          if (angular.isDefined(curriculumModule) && it.curriculumModule === curriculumModule.id) {
            occupationModule = it;
          }
        });

        if (angular.isDefined(occupationModule)) {
          dialogScope.occupationModule = occupationModule;
        } else {
          occupationModule = {curriculumModule: curriculumModule.id, capacities: [], yearCapacities: []};
          if (angular.isDefined(moduleData)) {
            angular.extend(occupationModule, moduleData);
          }
          dialogScope.occupationModule = occupationModule;
        }

        dialogScope.openAddThemeDialog = openAddThemeDialog(dialogScope.occupationModule, curriculumModule);
        dialogScope.occupationModule.yearCapacities.sort(function(a, b) {
          return a.studyYearNumber - b.studyYearNumber;
        });
        calculateYearCapacities(dialogScope.occupationModule.themes, dialogScope.occupationModule.yearCapacities);
      },
      function(submittedDialogScope) {
        var isUpdate = false;
        $scope.implementationPlan.occupationModules.forEach(function(it) {
          if(it.curriculumModule === submittedDialogScope.occupationModule.curriculumModule) {
            isUpdate = true;
          }
        });
        if(!isUpdate) {
          $scope.implementationPlan.occupationModules.push(submittedDialogScope.occupationModule);
        }

        $scope.save();
      });
    };



    $scope.isOccupationModuleDataSaved = function(curriculumModule) {
      var isSaved = false;
      $scope.implementationPlan.occupationModules.forEach(function(it) {
        if(it.curriculumModule === curriculumModule.id && angular.isNumber(it.id)) {
          isSaved = true;
        }
      });
      return isSaved;
    };

    $scope.copy = function() {
      var copy = angular.merge({}, $scope.implementationPlan);
      ['id', 'version', 'changed', 'changedBy', 'inserted', 'insertedBy', 'status', 'validFrom', 'validThru']
      .forEach(function(property) {
        delete copy[property];
      });

      if (angular.isArray(copy.occupationModules)) {
        copy.occupationModules.forEach(function(occupationModule) {
          delete occupationModule.id;
          delete occupationModule.version;
          if (angular.isArray(occupationModule.themes)) {
            occupationModule.themes.forEach(function(theme) {
              delete theme.id;
              delete theme.version;
              if (angular.isArray(theme.capacities)) {
                theme.capacities.forEach(function(capacity) {
                  delete capacity.id;
                  delete capacity.version;
                });
              }
            });
          }

          if (angular.isArray(occupationModule.yearCapacities)) {
            occupationModule.yearCapacities.forEach(function(yearCapacity) {
              delete yearCapacity.id;
              delete yearCapacity.version;
            });
          }
        });
      }

      $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit/moduleImplementationPlan/new');
      $routeParams.implementationPlanCopy = copy;
      $route.current.locals.implementationPlanCopy = copy;
      $route.current.params.implementationPlanCopy = copy;
    };

    $scope.save = function () {
        $scope.implementationPlan.status = $scope.currentStatus;
        setTimeout(save, 0);
    };

    function save (messages) {

      $scope.vocationalCurriculumModuleImplementationPlanForm.$setSubmitted();
      if($scope.vocationalCurriculumModuleImplementationPlanForm.$valid) {

        var CurriculumVersionEndpoint = QueryUtils.endpoint('/curriculum/'+curriculumEntity.id+'/versions');
        var curriculumVersion = new CurriculumVersionEndpoint($scope.implementationPlan);

        if (angular.isDefined($scope.implementationPlan.id)) {
          curriculumVersion.$update().then(function(response) {
            var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
            message.info(updateSuccess);
            $scope.implementationPlan.version = response.version;

            var status = response.status;
            if($scope.currentStatus !== status && (status === Curriculum.VERSION_STATUS.K || status === Curriculum.VERSION_STATUS.C)) {
                $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/view/moduleImplementationPlan/' + response.id + '/view');
            }
            $scope.currentStatus = status;
          });
        } else {
          curriculumVersion.$save().then(function(response) {
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit/moduleImplementationPlan/' + response.id + '/edit');
          });
        }

      } else {
          var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
          message.error(errorMessage);
      }
    };

    function setStatus(newStatus, messages) {
        dialogService.confirmDialog({prompt: messages.prompt}, function() {
            $scope.implementationPlan.status = newStatus;
            // setTimeout is needed for validation of ng-required fields
            setTimeout(function(){
                save(messages);
            }, 0);
        });
    }

    $scope.setStatusVerified = function() {
        var messages = {
            prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.implementationPlan.prompt.verify' : 'curriculum.statuschange.implementationPlan.prompt.verify',
            updateSuccess: 'curriculum.statuschange.implementationPlan.success.verified'
        };
        setStatus(Curriculum.VERSION_STATUS.K, messages);
    };

    $scope.setStatusClosed = function() {
        var messages = {
            prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.implementationPlan.prompt.close' : 'curriculum.statuschange.implementationPlan.prompt.close',
            updateSuccess: 'curriculum.statuschange.implementationPlan.success.closed'
        };
        setStatus(Curriculum.VERSION_STATUS.C, messages);
    };

    $scope.goToEditForm = function() {
        if(!$scope.curriculumId) {
            return;
        }
        if($scope.version.status === Curriculum.VERSION_STATUS.K) {
            dialogService.confirmDialog({
            prompt: 'curriculum.statuschange.implementationPlan.prompt.editAccepted', 
            }, function(){
                $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit/moduleImplementationPlan/' + $scope.implementationPlan.id + '/edit');
            });
        } else {
            $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit/moduleImplementationPlan/' + $scope.implementationPlan.id + '/edit');
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
        var modules = $scope.modules ? $scope.modules.filter(function(el){
            return ArrayUtils.isEmpty(el.occupations);
        }) : [];
        var thisTypeModules = modules ? modules.filter(function(el){
            return el.module === type.code;
        }) : [];
        return !ArrayUtils.isEmpty(thisTypeModules);
    };
  });
