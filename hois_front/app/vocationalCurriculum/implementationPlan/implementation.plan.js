'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleImplementationPlanController',

  function ($scope, Curriculum, Classifier, ClassifierConnect, QueryUtils, DataUtils, message, $location, $route, $q, dialogService, $routeParams, ArrayUtils, $rootScope, $http, config) {
    var curriculumEntity = $route.current.locals.curriculum;
    $scope.initializing = true;
    $scope.auth = $route.current.locals.auth;
    var id = $route.current.params.versionId;
    var Endpoint = QueryUtils.endpoint('/curriculumVersion');

    var moduleTypes = Object.freeze({ // Sorting module types
      KUTSEMOODUL_P: 0,
      KUTSEMOODUL_Y: 1,
      KUTSEMOODUL_L: 2,
      KUTSEMOODUL_V: 3
    });

    var capacitiesData = [];

    var HOURS_PER_EKAP = 26;

    $scope.CURRICULUM_STATUS = Curriculum.STATUS;
    $scope.VERSION_STATUS = Curriculum.VERSION_STATUS;

    $scope.formState = {
        readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
    };

    var entity = {curriculum: curriculumEntity.id, occupationModules: []};
    var initialImplementationPlan = {
      status: Curriculum.VERSION_STATUS.S,
      validFrom: new Date(),
      code: curriculumEntity.code + ' - ' + curriculumEntity.nameEt,
      curriculum: curriculumEntity.id
    };

    if (id) {
      entity = $route.current.locals.curriculumVersion;
      $scope.publicUrl = config.apiUrl + '/public/curriculum/' + curriculumEntity.id + '/' + id +'?format=json';
      $scope.implementationPlan = entity;
      DataUtils.convertStringToDates($scope.implementationPlan, ["validFrom", "validThru"]);

      $rootScope.removeLastUrlFromHistory(function(lastUrl){
        return lastUrl && lastUrl.indexOf('#/vocationalCurriculum/' + $scope.implementationPlan.curriculum + '/moduleImplementationPlan/') !== -1;
      });

    }  else {
      var years = Math.floor(((curriculumEntity.studyPeriod || 0) + 11) / 12);
      initialImplementationPlan.yearCapacities = Array.apply(null, {length: years}).fill(0);
      angular.extend(entity, initialImplementationPlan);
      $scope.implementationPlan = entity;
    }
    $scope.curriculumVersionCodeUniqueQuery = {
      id: id,
      url: '/curriculumVersion/unique/code'
    };

    $scope.curriculumId = curriculumEntity.id;
    $scope.studyForms = curriculumEntity.studyForms;
    $scope.curriculumStudyForms = curriculumEntity.studyForms;

    var admissionYears = [null];
    var currentYear = new Date().getFullYear();
    for(var year = currentYear - 10; year <= currentYear + 2; year++) {
      admissionYears.push(year);
    }
    $scope.admissionYears = admissionYears;

    //$scope.implementationPlanPdfUrl = config.apiUrl + '/curriculum/print/' + id + '/curriculum.pdf';

    $scope.curriculumVersionPdfUrl = config.apiUrl + '/curriculum/print/' + id + '/curriculumVersion.pdf';
    $scope.curriculumVersionModulesPdfUrl = config.apiUrl + '/curriculum/print/' + id + '/curriculumVersionModules.pdf';

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
          occupation.specialities = it.specialities;
          var promise = Classifier.get(it.occupation).$promise.then(function(classifier) {
            angular.extend(occupation, it, {occupation: classifier});
          });
          promises.push(promise);
          //  var partOccupations = [];
          // var partOccupationsPromise = ClassifierConnect.queryAll({connectClassifierCode: it.occupation, classifierMainClassCode: 'OSAKUTSE'}, function(result) {
          //   result.forEach(function(classifierConnect) {
          //       partOccupations.push(classifierConnect.classifier);
          //   });
          //   angular.extend(occupation, {partOccupations: partOccupations});
          // }).$promise;
          // promises.push(partOccupationsPromise);
          occupations.push(occupation);
        });
    }


    // var capacitiesPromise = Classifier.queryForDropdown({mainClassCode: 'MAHT', order: 'nameEt'}, function(response) {
    //   capacitiesData = response.filter(function(el){
    //     return el.vocational;
    //   });
    // }).$promise;
    // promises.push(capacitiesPromise);


    $q.all(promises).then(function() {
      var modulesView = Curriculum.modulesViewData(modules);
      $scope.occupations = occupations;
      $scope.modules = curriculumEntity.modules;
      $scope.isOccupation = curriculumEntity.occupation;
      $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
      $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;
      $scope.initializing = false;
    });

    QueryUtils.endpoint('/curriculumVersion/schoolDepartments/curriculum/' + curriculumEntity.id).query().$promise.then(function(response){
      $scope.schoolDepartments = response;
    });

    $scope.openViewModuleDialog = function(curriculumModule) {
      dialogService.showDialog('vocationalCurriculum/implementationPlan/module.view.dialog.html',
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
              dialogScope.module.occupations = curriculumModule.occupations;
              curriculumModule.occupations.forEach(function(occupation) {
                if (angular.isDefined(occupation)) {
                  if(occupation.indexOf('KUTSE') === 0 || occupation.indexOf('OSAKUTSE') === 0) {
                    dialogScope.occupationsSelected[occupation] = true;
                  } else if(occupation.indexOf('SPETSKUTSE') === 0) {
                    dialogScope.specialitiesSelected[occupation] = true;
                  }
                }
              });
            }
          }
      },
      null);
    };

    function getModulesOccupationModule(curriculumModule) {
      var occupationModule;
      entity.occupationModules.forEach(function(it) {
        if (angular.isDefined(curriculumModule) && it.curriculumModule === curriculumModule.id) {
          occupationModule = it;
        }
      });
      return occupationModule;
    }

    $scope.openAddModuleDataDialog = function(curriculumModule, moduleData) {

      if($scope.vocationalCurriculumModuleImplementationPlanForm.$dirty) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          openAddModuleDataDialog(curriculumModule, moduleData);
        });
      } else {
        openAddModuleDataDialog(curriculumModule, moduleData);
      }
    };

    function openAddModuleDataDialog(curriculumModule, moduleData) {
      moduleData = getModulesOccupationModule(curriculumModule);
      var url = '';
      if(!moduleData) {
        url = '/occupationModule/' + curriculumEntity.id + '/' + $scope.implementationPlan.id + '/' + curriculumModule.id + '/new';
      } else if ($scope.formState.readOnly) {
        url = '/occupationModule/' + curriculumEntity.id + '/' + $scope.implementationPlan.id + '/' + curriculumModule.id + '/' + moduleData.id + '/view';
      } else {
        url = '/occupationModule/' + curriculumEntity.id + '/' + $scope.implementationPlan.id + '/' + curriculumModule.id + '/' + moduleData.id + '/edit';
      }
      $location.path(url);
    }

    $scope.occupationModuleHasBaseModule = function(curriculumModule) {
      for (var i = 0; i < $scope.implementationPlan.occupationModules.length; i++) {
        if($scope.implementationPlan.occupationModules[i].curriculumModule === curriculumModule.id) {
          if ($scope.implementationPlan.occupationModules[i].baseModule) {
            return true;
          } else {
            return false;
          }
        }
      }
      return curriculumModule.baseModule !== null;
    }

    $scope.isOccupationModuleDataSaved = function(curriculumModule) {
      var isSaved = false;
      $scope.implementationPlan.occupationModules.forEach(function(it) {
        if(it.curriculumModule === curriculumModule.id) {
          isSaved = true;
        }
      });
      return isSaved;
    };

    $scope.isModuleValid = function(curriculumModule) {
      return $scope.isOccupationModuleDataSaved(curriculumModule) || curriculumModule.module === 'KUTSEMOODUL_V';
    };

    $scope.copy = function() {
      var CopyEndpoint = QueryUtils.endpoint('/curriculumVersion/copy');
      new CopyEndpoint({id: id}).$update().then(function(response){
        message.info('main.messages.create.success');
        $location.path('/vocationalCurriculum/' + response.curriculum + '/moduleImplementationPlan/' + response.id + '/edit');
      });
    };

    $scope.anyModuleAdded = function() {
        return !ArrayUtils.isEmpty($scope.modules);
    };

    function validationPassed(messages) {
      $scope.vocationalCurriculumModuleImplementationPlanForm.$setSubmitted();
      if(!$scope.vocationalCurriculumModuleImplementationPlanForm.$valid) {
        var errorMessage = messages && messages.errorMessage ? messages.errorMessage : 'main.messages.form-has-errors';
        message.error(errorMessage);
        return false;
      }
      return true;
    }

    $scope.save = function save (messages) {

      if(!validationPassed(messages)) {
        return;
      }
      var curriculumVersion = new Endpoint($scope.implementationPlan);

      if (angular.isDefined($scope.implementationPlan.id)) {
        curriculumVersion.$update().then(function(response) {
          var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
          message.info(updateSuccess);
          $scope.implementationPlan.version = response.version;
          $scope.vocationalCurriculumModuleImplementationPlanForm.$setPristine();
        });
      } else {
        curriculumVersion.$save().then(function(response) {
          message.info('main.messages.create.success');
          $location.path('/vocationalCurriculum/' + curriculumEntity.id + '/moduleImplementationPlan/' + response.id + '/edit');
        });
      }
    }

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteImplementationPlan'}, function() {
        var curriculumVersion = new Endpoint($scope.implementationPlan);
        curriculumVersion.$delete().then(function() {
          message.info('main.messages.delete.success');
          $rootScope.back('#/vocationalCurriculum/'+ curriculumEntity.id + '/view');
        });
      });
    };

    $scope.changeStatusUnderRevision = function () {
      var messages = {
        prompt: "curriculum.statuschange.implementationPlan.prompt.setStatusUnderRevision",
        updateSuccess: 'curriculum.statuschange.implementationPlan.success.opened'
      };
      var setStatusEndpoint = QueryUtils.endpoint('/curriculumVersion/underrevision');
      changeStatus(setStatusEndpoint, messages)
    };

    $scope.saveAndConfirm = function() {
      var messages = {
          prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.implementationPlan.prompt.verify' : 'curriculum.statuschange.implementationPlan.prompt.verify',
          updateSuccess: 'curriculum.statuschange.implementationPlan.success.verified'
      };
      var SaveAndConfirmEndpoint = QueryUtils.endpoint("/curriculumVersion/saveAndConfirm");
      changeStatus(SaveAndConfirmEndpoint, messages, true);
    };

    $scope.confirm = function() {
      var messages = {
          prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.implementationPlan.prompt.verify' : 'curriculum.statuschange.implementationPlan.prompt.verify',
          updateSuccess: 'curriculum.statuschange.implementationPlan.success.verified'
      };
      var ConfirmEndpoint = QueryUtils.endpoint("/curriculumVersion/confirm");
      changeStatus(ConfirmEndpoint, messages, true);
    };

    $scope.setStatusClosed = function() {
      var messages = {
          prompt: $scope.formState.readOnly ? 'curriculum.statuschangeReadOnly.implementationPlan.prompt.close' : 'curriculum.statuschange.implementationPlan.prompt.close',
          updateSuccess: 'curriculum.statuschange.implementationPlan.success.closed'
      };
      var ClosingEndpoint = QueryUtils.endpoint("/curriculumVersion/close");
      changeStatus(ClosingEndpoint, messages, false);
    };

    function checkHoursAndCredits(StatusChangeEndpoint, messages) {
      $http({
        url: config.apiUrl + '/curriculumVersion/valid/hourscredits/' + id,
        method: "GET"
      }).then(function(response) {
        if (!response.data) {
          messages.extraPrompts = [messages.prompt];
          messages.prompt = 'curriculum.error.themeCreditsAndHoursMismatch';
        }
        confirmChangeStatus(StatusChangeEndpoint, messages);
      });
    }

    function changeStatus(StatusChangeEndpoint, messages, checkHours) {
      if (!validationPassed(messages)) {
        return;
      }
      if (checkHours) {
        checkHoursAndCredits(StatusChangeEndpoint, messages);
      } else {
        confirmChangeStatus(StatusChangeEndpoint, messages);
      }
    }

    function confirmChangeStatus(StatusChangeEndpoint, messages) {
      setTimeout(function(){
        dialogService.confirmDialog({ prompt: messages.prompt, extraPrompts: messages.extraPrompts }, function () {
            new StatusChangeEndpoint($scope.implementationPlan).$update().then(function(response){
              var updateSuccess = messages && messages.updateSuccess ? messages.updateSuccess : 'main.messages.create.success';
              message.info(updateSuccess);
              $scope.implementationPlan = response;
              DataUtils.convertStringToDates($scope.implementationPlan, ["validFrom", "validThru"]);
              $scope.implementationPlan.curriculum = curriculumEntity.id;
              goToReadOnlyForm(response);
              $scope.vocationalCurriculumModuleImplementationPlanForm.$setPristine();
            });
        });
      }, 0);
    }

    function goToReadOnlyForm(response) {
      if(!$scope.formState.readOnly) {
        $location.path('/vocationalCurriculum/' + curriculumEntity.id + '/moduleImplementationPlan/' + response.id + '/view');
      }
    }

    $scope.goToEditForm = function() {
        if(!$scope.curriculumId) {
            return;
        }
        var url = '/vocationalCurriculum/' + curriculumEntity.id + '/moduleImplementationPlan/' + $scope.implementationPlan.id + '/edit';
        if($scope.implementationPlan.status === Curriculum.VERSION_STATUS.K) {
            dialogService.confirmDialog({
            prompt: 'curriculum.statuschange.implementationPlan.prompt.editAccepted',
            }, function(){
                $location.path(url);
            });
        } else {
            $location.path(url);
        }
    };

    Classifier.queryForDropdown({mainClassCode: 'KUTSEMOODUL'}, function(response) {
        $scope.moduleTypes = response;
        $scope.moduleTypes.sort(function (a, b) {
          return moduleTypes[a.code] - moduleTypes[b.code];
        });
    });

    $scope.getTotalCredits = function (modules) {
      return modules.reduce(function (sum, module) {
        return sum + module.credits;
      }, 0);
    }

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

    $scope.codeNameUnique = true;
    $scope.$watch('implementationPlan.code', function(){
      if($scope.implementationPlan.code) {
        $http({
          url: config.apiUrl + '/curriculumVersion/unique/code',
          method: "GET",
          params: {
            paramName: 'nameEt',
            paramValue: $scope.implementationPlan.code,
            id: $route.current.params.versionId
          }
        }).then(function(response) {
          $scope.codeNameUnique = response.data;
        });
      } else {
        $scope.codeNameUnique = true;
      }
    });

    $scope.showPrintButton = function() {
      return angular.isDefined($scope.implementationPlan.id) && ($scope.auth.isAdmin() || $scope.implementationPlan.status === $scope.VERSION_STATUS.K &&
        curriculumEntity.status === $scope.CURRICULUM_STATUS.VERIFIED);
    };

    /**
     * Default comparator from orderBy.js
     * 
     * @param {Object} v1 
     * @param {Object} v2 
     */
    function defaultCompare(v1, v2) {
      var result = 0;
      var type1 = v1.type;
      var type2 = v2.type;
  
      if (type1 === type2) {
        var value1 = v1.value;
        var value2 = v2.value;
  
        if (type1 === 'string') {
          // Compare strings case-insensitively
          value1 = value1.toLowerCase();
          value2 = value2.toLowerCase();
        } else if (type1 === 'object') {
          // For basic objects, use the position of the object
          // in the collection instead of the value
          if (angular.isObject(value1)) value1 = v1.index;
          if (angular.isObject(value2)) value2 = v2.index;
        }
  
        if (value1 !== value2) {
          result = value1 < value2 ? -1 : 1;
        }
      } else {
        result = (type1 === 'undefined') ? 1 :
          (type2 === 'undefined') ? -1 :
          (type1 === 'null') ? 1 :
          (type2 === 'null') ? -1 :
          (type1 < type2) ? -1 : 1;
      }
  
      return result;
    }

    $scope.compareUsingLanguage = function (o1, o2) {
      if (angular.isObject(o1) && angular.isObject(o2)) {
        var nameOb1 = $scope.currentLanguageNameField(o1.value);
        var nameOb2 = $scope.currentLanguageNameField(o2.value);
        if (!nameOb1 && !nameOb2) {
          return defaultCompare(o1, o2);
        }
        return defaultCompare({type: typeof nameOb1, value: nameOb1, index: o1.index}, {type: typeof nameOb2, value: nameOb2, index: o2.index});
      }
      return defaultCompare(o1, o2);
    }
  });
