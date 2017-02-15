'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleImplementationPlanController',

  function ($scope, Curriculum, Classifier, QueryUtils, DataUtils, message, $location, $route, $q, dialogService) {

    var curriculumEntity = $route.current.locals.curriculum;
    $scope.initializing = true;
    var capacitiesData = [];

    var entity = {curriculum: curriculumEntity.id, occupationModules: []};
    if (angular.isDefined($route.current.params.versionId)) {
      curriculumEntity.versions.forEach(function(it){
        if (it.id.toString() === $route.current.params.versionId) {
          angular.extend(entity, it);
        }
      });

      $scope.implementationPlan = entity;
      DataUtils.convertStringToDates($scope.implementationPlan, ["validFrom", "validThru"]);
    } else {
      var initialImplementationPlan = {
        status: 'OPPEKAVA_VERSIOON_STAATUS_S',
        validFrom: new Date(),
        code: curriculumEntity.code + ' - ' + curriculumEntity.nameEt
      };
      angular.extend(entity, initialImplementationPlan);
      $scope.implementationPlan = entity;
    }

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
      $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
      $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;
      $scope.initializing = false;
    });

    $scope.openViewModuleDialog = function(curriculumModule, occupation) {
      dialogService.showDialog('views/vocational_curriculum/module.view.dialog.html',
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
              curriculumModule.occupations.forEach(function(occupation){
                if(occupation.code.indexOf('KUTSE') === 0 || occupation.code.indexOf('OSAKUTSE') === 0) {
                  dialogScope.occupationsSelected[occupation.code] = true;
                } else if(occupation.code.indexOf('SPETSKUTSE') === 0) {
                  dialogScope.specialitiesSelected[occupation.code] = true;
                }
              });
            }
          }


      },
      null);
    }


    var openAddThemeDialog = function(occupationModule) {
      return function(theme) {
        dialogService.showDialog('views/vocational_curriculum/module.data.theme.add.dialog.html',
        function(dialogScope) {
          dialogScope.capacities = capacitiesData;
          //console.log(occupationModule)
          //dialogScope.outcomes = occupationModule.curriculumModule.outcomes;
          if (!angular.isArray(occupationModule.themes)) {
            occupationModule.themes = [];
          }

          if (!angular.isDefined(theme)) {
            dialogScope.theme = {capacities: []};
          } else {
            dialogScope.theme = theme;
          }
        },
        function(submittedDialogScope) {
          if (!angular.isDefined(theme)) {
            occupationModule.themes.push(submittedDialogScope.theme);
          }
          $scope.save();
        });
      }
    }



    $scope.openAddModuleDataDialog = function(curriculumModule) {
      dialogService.showDialog('views/vocational_curriculum/module.data.add.dialog.html',
      function(dialogScope) {

        dialogScope.capacities = capacitiesData;
        var occupationModule;
        entity.occupationModules.forEach(function(it) {
          if (it.curriculumModule === curriculumModule.id) {
            occupationModule = it;
          }
        });

        if (angular.isDefined(occupationModule)) {
          dialogScope.occupationModule = occupationModule;
        } else {
          occupationModule = {curriculumModule: curriculumModule.id, capacities: [], yearCapacities: []};
          dialogScope.occupationModule = occupationModule;
        }

        dialogScope.openAddThemeDialog = openAddThemeDialog(dialogScope.occupationModule);
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

    $scope.save = function() {

      $scope.vocationalCurriculumModuleImplementationPlanForm.$setSubmitted();
      if($scope.vocationalCurriculumModuleImplementationPlanForm.$valid) {

        var CurriculumVersionEndpoint = QueryUtils.endpoint('/curriculum/'+curriculumEntity.id+'/versions');
        var curriculumVersion = new CurriculumVersionEndpoint($scope.implementationPlan);

        if (angular.isDefined($scope.implementationPlan.id)) {
          curriculumVersion.$update().then(function(response) {
            message.info('main.messages.update.success');
            $scope.implementationPlan.version = response.version;
          });
        } else {
          curriculumVersion.$save().then(function(response) {
            message.info('main.messages.create.success');
            $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit/moduleImplementationPlan/' + response.id + '/edit');
          });
        }

      } else {
          console.log($scope.vocationalCurriculumModuleImplementationPlanForm.$error);
      }
    };
  });
