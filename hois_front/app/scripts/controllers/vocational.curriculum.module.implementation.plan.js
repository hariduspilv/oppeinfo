'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumModuleImplementationPlanController',

  function ($scope, Curriculum, Classifier, QueryUtils, DataUtils, message, $location, $route, $q) {

    var curriculumEntity = $route.current.locals.curriculum;

    var entity;
    if (angular.isDefined($route.current.params.versionId)) {
      curriculumEntity.versions.forEach(function(it){
        if (it.id.toString() === $route.current.params.versionId) {
          entity = it;
        }
      });

      $scope.implementationPlan = entity;
      DataUtils.convertStringToDates($scope.implementationPlan, ["validFrom", "validThru"]);
    } else {
      $scope.implementationPlan = {
        status: 'OPPEKAVA_VERSIOON_STAATUS_S',
        validFrom: new Date(),
        code: curriculumEntity.code + ' - ' + curriculumEntity.nameEt
      };
    }

    $scope.curriculumId = curriculumEntity.id;
    $scope.studyForms = curriculumEntity.studyForms;
    $scope.schoolDepartments = Curriculum.getSchoolDepartments();
    $scope.curriculumStudyForms = curriculumEntity.studyForms;

    $scope.admissionYears = [];
    var currentYear = new Date().getFullYear();
    for(var year = currentYear - 10; year < currentYear + 2; year++) {
      $scope.admissionYears.push(year);
    }


/*    var promises = [];
    if (angular.isArray(curriculumEntity.modules)) {
      curriculumEntity.modules.forEach(function(it) {
        if (angular.isString(it.module)) {
          var promise = Classifier.get(it.module).$promise.then(function(classifier) {
            it.module = classifier;
          });
          promises.push(promise);
        }


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
      var modulesView = Curriculum.modulesViewData(curriculumEntity.modules);
      $scope.occupationModuleTypesModules = modulesView.occupationModuleTypesModules;
      $scope.modulesWithOutOccupation = modulesView.modulesWithOutOccupation;
    });*/


    $scope.save = function() {

      $scope.vocationalCurriculumModuleImplementationPlanForm.$setSubmitted();
      if($scope.vocationalCurriculumModuleImplementationPlanForm.$valid) {

        if (!angular.isArray(curriculumEntity.versions)) {
          curriculumEntity.versions = [];
        }

        if(!angular.isDefined($scope.implementationPlan.id)) {
          curriculumEntity.versions.push($scope.implementationPlan);
        }

        var CurriculumEndpoint = QueryUtils.endpoint('/curriculum');
        var curriculum = new CurriculumEndpoint(curriculumEntity);

        curriculum.$update().then(function() {
          message.info('main.messages.create.success');
          $location.path('/vocationalCurriculum/'+curriculumEntity.id+'/edit');
        });
      } else {
          console.log($scope.vocationalCurriculumModuleImplementationPlanForm.$error);
      }
    };
  });
