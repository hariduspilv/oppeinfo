'use strict';

angular.module('hitsaOis')
  .controller('VocationalCurriculumVersionOccupationModuleController', function ($scope, $route, QueryUtils, Classifier, message, $location, ArrayUtils, dialogService, $rootScope) {

    var id = $route.current.params.occupationModule;
    var curriculum = $route.current.params.curriculum;
    var curriculumVersion = $route.current.params.version;
    var curriculumModule = $route.current.params.curriculumModule;
    var baseUrl = '/occupationModule';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var ThemeEndpoint = QueryUtils.endpoint(baseUrl + '/theme');

    $scope.formState = {
      readOnly: $route.current.$$route.originalPath.indexOf("view") !== -1
    };

    var HOURS_PER_EKAP = 26;

    $rootScope.removeLastUrlFromHistory(function(url){
      return url && url.indexOf("new") !== -1;
    });

    function filterAndSortYearCapacities() {
      if($scope.studyYears && $scope.occupationModule && $scope.occupationModule.yearCapacities) {
        var studyYears = $scope.studyYears.length;
        $scope.occupationModule.yearCapacities = $scope.occupationModule.yearCapacities.filter(function(yc) {
          return yc.studyYearNumber <= studyYears;
        }).sort(function(yc1, yc2) {
          return yc1.studyYearNumber - yc2.studyYearNumber;
        });
      }
    }

    QueryUtils.endpoint(baseUrl + '/curriculumModule').get({ id: curriculumModule }).$promise.then(function(response){
      $scope.curriculumModule = response;
      if(!id) {
        $scope.occupationModule.assessmentsEt = response.assessmentsEt;
      }
      var years = Math.floor(((response.studyPeriod || 0) + 11) / 12);
      $scope.studyYears = Array.apply(null, {length: years}).map(function(it, index) { return index + 1; });
      if(!id) {
        $scope.occupationModule.yearCapacities = $scope.studyYears.map(function(it) { return {credits: 0, studyYearNumber: it}; });
      } else {
        filterAndSortYearCapacities();
      }
    });
    $scope.backToEditForm = '#/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/edit';
    $scope.backToViewForm = '#/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/view';

    $scope.capacities = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: false });

    var initial = {
      curriculumModule: curriculumModule,
      curriculumVersion: curriculumVersion,
      assessment: 'KUTSEHINDAMISVIIS_E',
      yearCapacities: [ {credits: 0, studyYearNumber: 1} ],
      themes: [],
      capacities: []
    };

    if (id) {
      $scope.occupationModule = Endpoint.get({ id: id }).$promise.then(dtoToModel);
    } else {
      dtoToModel(new Endpoint(initial));
    }

    function validationPassed() {
      $scope.occupationModuleForm.$setSubmitted();
      if (!$scope.occupationModuleForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    function update() {
      $scope.occupationModule.$update().then(function (response) {
        message.info('main.messages.update.success');
        dtoToModel(response);
        $scope.occupationModuleForm.$setPristine();
      }).catch(angular.noop);
    }

    function create() {
      $scope.occupationModule.$save().then(function (response) {
        message.info('main.messages.create.success');
        $location.path('/occupationModule/' + curriculum + '/' + curriculumVersion + '/' + curriculumModule + '/' + response.id + '/edit');
      }).catch(angular.noop);
    }

    function capacitiesMatch() {
      var capacities = $scope.occupationModule.capacities.reduce(function(sum, c){
        return sum + (c.hours ? c.hours : 0);
      }, 0);
      return capacities / HOURS_PER_EKAP === $scope.curriculumModule.credits;
    }

    function yearCapacitiesMatch() {
      var credits = $scope.occupationModule.yearCapacities.reduce(function(sum, c){
        return sum + c.credits;
      }, 0);
      return credits === $scope.curriculumModule.credits;
    }

    $scope.save = function () {
      if (!validationPassed()) {
        return;
      }
      if(!capacitiesMatch() && !yearCapacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.capacitiesAndYearCapacitiesMismatch'}, save);
      } else if(!capacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.occupationModuleCapacitiesMismatch'}, save);
      } else if (!yearCapacitiesMatch()) {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.occupationModuleYearCapacitiesMismatch'}, save);
      } else {
        save();
      }
    };

    function save() {
      if ($scope.occupationModule.id) {
        update();
      } else {
        create();
      }
    }

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteModule'}, function() {
        new Endpoint($scope.occupationModule).$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/vocationalCurriculum/' + curriculum + '/moduleImplementationPlan/' + curriculumVersion + '/edit');
        }).catch(angular.noop);
      });
    };

    function classifierToCapacity(c) {
      return {
        capacityType: c.code,
        nameEt: c.nameEt,
        nameEn: c.nameEn
      };
    }

    /**
     * 1) Get capacity types. They are set on new occupational module and new module themes
     * 2) sort year capacities
     */
    function dtoToModel(response) {
      $scope.occupationModule = new Endpoint(response);
      if ($scope.occupationModule.supervisor) {
        $scope.occupationModule.supervisor = $scope.occupationModule.supervisor.trim();
      }
      if ($scope.occupationModule.totalGradeDescription) {
        $scope.occupationModule.totalGradeDescription = $scope.occupationModule.totalGradeDescription.trim();
      }
      if ($scope.occupationModule.requirementsEt) {
        $scope.occupationModule.requirementsEt = $scope.occupationModule.requirementsEt.trim();
      }
      if(!$scope.occupationModule.id) {
        $scope.capacities.$promise.then(function () {
          $scope.occupationModule.capacities = $scope.capacities.map(classifierToCapacity);
        });
      }
      filterAndSortYearCapacities();
    }

    $scope.hasThemes = function() {
      return $scope.occupationModule && !ArrayUtils.isEmpty($scope.occupationModule.themes);
    }

    function getNewCapacities() {
      Endpoint.get({id: id}).$promise.then(function(response){
        $scope.occupationModule.capacities = response.capacities;
        $scope.occupationModule.yearCapacities = response.yearCapacities;
        filterAndSortYearCapacities();
      });
    }


    $scope.deleteTheme = function(theme) {
      dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteTheme'}, function() {
        new ThemeEndpoint(theme).$delete().then(function() {
          message.info('main.messages.delete.success');
          ArrayUtils.remove($scope.occupationModule.themes, theme);
          getNewCapacities();
        });
      });
    };

    $scope.openAddThemeDialog = function (occupationModuleTheme) {

      var form = $scope.formState.readOnly ? 'vocationalCurriculum/implementationPlan/occupation.module.theme.view.dialog.html' : 'vocationalCurriculum/implementationPlan/occupation.module.theme.edit.dialog.html';

      dialogService.showDialog(form,
        function (dialogScope) {

          var occupationModule = $scope.occupationModule;

          if (!angular.isArray(occupationModule.themes)) {
            occupationModule.themes = [];
          }
          if (!angular.isDefined(occupationModuleTheme)) {
            dialogScope.occupationModuleTheme = {
              capacities: $scope.capacities.map(classifierToCapacity),
              assessment: 'KUTSEHINDAMISVIIS_E',
              module: $scope.occupationModule.id
            };
          } else {
            dialogScope.occupationModuleTheme = angular.copy(occupationModuleTheme);
          }
          dialogScope.outcomes = $scope.curriculumModule.outcomes;
          dialogScope.setDefaultHours = function () {
            if (angular.isNumber(dialogScope.occupationModuleTheme.credits)) {
              dialogScope.occupationModuleTheme.hours = Math.round(HOURS_PER_EKAP * dialogScope.occupationModuleTheme.credits);
              dialogScope.dialogForm.hours.$setDirty();
            }
          };
          dialogScope.studyYears = $scope.studyYears;
          dialogScope.assessments = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMISVIIS'});

          dialogScope.setDefaultCredits = function () {
            dialogScope.occupationModuleTheme.credits = Math.round((dialogScope.occupationModuleTheme.hours / HOURS_PER_EKAP) * 10) / 10;
            dialogScope.dialogForm.credits.$setDirty();
          };

          dialogScope.saveTheme = function () {
            var submittedDialogScope = dialogScope;
            if (!validateTheme(submittedDialogScope)) {
              return;
            }
            if(angular.isDefined(occupationModuleTheme)) {
              new ThemeEndpoint(submittedDialogScope.occupationModuleTheme).$update().then(function(response){
                message.info('main.messages.update.success');
                ArrayUtils.remove(occupationModule.themes, occupationModuleTheme);
                occupationModule.themes.push(response);
                getNewCapacities();
                dialogScope.cancel();
              });
            } else {
              new ThemeEndpoint(submittedDialogScope.occupationModuleTheme).$save().then(function(response){
                message.info('main.messages.create.success');
                occupationModule.themes.push(response);
                getNewCapacities();
                dialogScope.cancel();
              });
            }
          };

          function validateTheme(submittedDialogScope) {
            submittedDialogScope.dialogForm.$setSubmitted();
            if (!submittedDialogScope.dialogForm.$valid) {
              message.error('main.messages.form-has-errors');
              return false;
            }
            if (submittedDialogScope.occupationModuleTheme.hours !== Math.round(HOURS_PER_EKAP * submittedDialogScope.occupationModuleTheme.credits) &&
              submittedDialogScope.occupationModuleTheme.credits !== Math.round((submittedDialogScope.occupationModuleTheme.hours / HOURS_PER_EKAP) * 10) / 10) {
              message.error('curriculum.error.themeCreditsAndHoursMismatch');
              return false;
            }
            if (!proportionsMatch()) {
              message.error('curriculum.error.themeCapacitiesAndHoursMismatch');
              return false;
            }
            return true;
          }

          function proportionsMatch() {
            return dialogScope.occupationModuleTheme.hours === dialogScope.occupationModuleTheme.capacities.reduce(function (sum, value) {
              if (angular.isDefined(value.hours)) {
                return sum += value.hours;
              } else {
                return sum;
              }
            }, 0);
          }

        }, function () {
          // saved in dialogScope.saveTheme()
        });
    };

  });
