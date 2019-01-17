'use strict';

angular.module('hitsaOis').controller('ContractEditController', function ($location, $scope, $route, dialogService, message, Classifier, DataUtils, FormUtils, QueryUtils) {
  var CREDITS_TO_HOURS_MULTIPLIER = 26;

  $scope.auth = $route.current.locals.auth;
  $scope.contract = {};
  $scope.formState = {};

  function entityToForm(entity) {
    $scope.formState.isHigher = angular.isObject(entity.subject);
    DataUtils.convertObjectToIdentifier(entity, ['module', 'theme', 'enterprise', 'teacher', 'contractCoordinator', 'subject']);
    $scope.contract = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entity.$promise.then(function(response) {
      if (response.canEdit === false) {
        message.error("main.messages.error.nopermission");
        $scope.back("#/");
      }
    });
  }
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  } else {
    $scope.formState.isHigher = $scope.auth.school.higher === true && (($scope.auth.school.vocational === true && $route.current.params.higher === true) || $scope.auth.school.vocational === false);
  }

  function loadEnterprises() {
    $scope.formState.enterprisesById = {};
    return QueryUtils.endpoint('/autocomplete/enterprises').query(function (result) {
      $scope.formState.enterprisesById = result.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
      $scope.formState.enterprises = result;
    });
  }
  loadEnterprises();

  var clMapper = Classifier.valuemapper({ studyForm: 'OPPEVORM' });
  $scope.$watch('contract.student', function (student) {
    if (angular.isObject(student)) {
      QueryUtils.endpoint('/students/' + student.id).search(function (result) {
        $scope.formState.student = clMapper.objectmapper(result);
      });

      if ($scope.formState.isHigher) {
        loadStudentPracticeSubjects(student);
      } else {
        loadStudentPracticeModules(student);
      }
    }
  });

  function loadStudentPracticeModules(student) {
    QueryUtils.endpoint('/contracts/studentPracticeModules/' + student.id).query(function (result) {
      $scope.formState.modulesById = {};
      $scope.formState.themesById = {};
      result.forEach(function (it) {
        $scope.formState.modulesById[it.module.id] = it;
        it.themes.forEach(function (it) {
          $scope.formState.themesById[it.theme.id] = it;
        });
      });
      $scope.formState.modules = result.map(function (it) { return it.module; });
      if (angular.isNumber($scope.contract.module)) {
        updateModuleThemes($scope.contract.module);
      }
    });
  }

  function loadStudentPracticeSubjects(student) {
    QueryUtils.endpoint('/contracts/studentPracticeSubjects/' + student.id).query(function (result) {
      $scope.formState.subjectsById = {};
      result.forEach(function (it) {
        $scope.formState.subjectsById[it.id] = it;
      });
      $scope.formState.subjects = result;
    });
  }

  function updateModuleThemes(moduleId) {
    var module = $scope.formState.modulesById[moduleId];
    if (module) {
      $scope.formState.themes = module.themes.map(function (it) { return it.theme; });
    }
  }

  function setModuleCredits() {
    if($scope.contract.module && !$scope.contract.theme) {
      $scope.contract.credits = $scope.formState.modulesById[$scope.contract.module].credits;
      $scope.contract.hours = $scope.contract.credits * CREDITS_TO_HOURS_MULTIPLIER;
    }
  }

  $scope.moduleChanged = function (moduleId) {
    if ($scope.formState.modulesById[moduleId]) {
      setModuleCredits();
      updateModuleThemes(moduleId);

      if (!angular.isDefined(entity) && angular.isString($scope.formState.modulesById[moduleId].assessmentMethodsEt)) {
        $scope.contract.practicePlan = $scope.formState.modulesById[moduleId].assessmentMethodsEt;
      }
    }
  };

  $scope.themeChanged = function (themeId) {
    if ($scope.formState.themesById[themeId]) {
      $scope.contract.credits = $scope.formState.themesById[themeId].credits;
      $scope.contract.hours = $scope.contract.credits * CREDITS_TO_HOURS_MULTIPLIER;

      if (!angular.isDefined(entity) && angular.isString($scope.formState.themesById[themeId].subthemes)) {
        $scope.contract.practicePlan = $scope.formState.themesById[themeId].subthemes;
      }
    } else {
      setModuleCredits();
    }
  };

  $scope.subjectChanged = function (subjectId) {
    if ($scope.formState.subjectsById[subjectId]) {
      $scope.contract.credits = $scope.formState.subjectsById[subjectId].credits;
      $scope.contract.hours = $scope.contract.credits * CREDITS_TO_HOURS_MULTIPLIER;

      if (!angular.isDefined(entity) && angular.isString($scope.formState.subjectsById[subjectId].outcomesEt)) {
        $scope.contract.practicePlan = $scope.formState.subjectsById[subjectId].outcomesEt;
      }
    }
  };

  $scope.updateHours = function () {
    if (angular.isNumber($scope.contract.credits)) {
      $scope.contract.hours = DataUtils.creditsToHours($scope.contract.credits);
      $scope.contractForm.hours.$setDirty();
    }
  };
  $scope.updateCredits = function () {
    $scope.contract.credits = DataUtils.hoursToCredits($scope.contract.hours);
    $scope.contractForm.credits.$setDirty();
  };

  $scope.enterpriseChanged = function (enterpriseId) {
    var enterprise = $scope.formState.enterprisesById[enterpriseId];
    $scope.contract.contactPersonName = enterprise.contactPersonName;
    $scope.contract.contactPersonEmail = enterprise.contactPersonEmail;
    $scope.contract.contactPersonPhone = enterprise.contactPersonPhone;
  };

  var EnterpriseEndpoint = QueryUtils.endpoint('/enterprises');
  $scope.addEnterprise = function () {
    dialogService.showDialog('contract/enterprise.add.dialog.html', function (dialogScope) {
      dialogScope.enterprise = {};
    }, function (submittedDialogScope) {
      var enterprise = new EnterpriseEndpoint(submittedDialogScope.enterprise);
      enterprise.$save().then(function (result) {
        message.info('main.messages.create.success');

        loadEnterprises().$promise.then(function () {
          $scope.contract.enterprise = result.id;
          $scope.enterpriseChanged($scope.contract.enterprise);
        });
      }).catch(angular.noop);
    });
  };


  var ContractEndpoint = QueryUtils.endpoint('/contracts');
  $scope.save = function (success) {
    FormUtils.withValidForm($scope.contractForm, function() {
      $scope.contract.isHigher = $scope.formState.isHigher;
      var contract = new ContractEndpoint($scope.contract);
      if (angular.isDefined($scope.contract.id)) {
        contract.$update().then(function () {
          message.updateSuccess();
          entityToForm(contract);
          if (angular.isFunction(success)) {
            success();
          }
          $scope.contractForm.$setPristine();
        }).catch(angular.noop);
      } else {
        contract.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url('/contracts/' + contract.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    });
  };

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'contract.deleteconfirm' }, function () {
      var contract = new ContractEndpoint($scope.contract);
      contract.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path('/contracts');
      }).catch(angular.noop);
    });
  };

  $scope.sendToEkis = function () {
    FormUtils.withValidForm($scope.contractForm, function() {
      QueryUtils.endpoint('/contracts/checkForEkis').get({id: $scope.contract.id}).$promise.then(function(check) {
        dialogService.confirmDialog({ prompt: (check.templateExists ? 'contract.ekisconfirm' : 'contract.ekisconfirmTemplateMissing'), template: check.templateName }, function () {
          $scope.save(function () {
            var EkisEndpoint = QueryUtils.endpoint('/contracts/sendToEkis/' + $scope.contract.id);
            new EkisEndpoint().$save().then(function (contract) {
              message.info('contract.messages.sendToEkis.success');
              $location.url('/contracts/' + contract.id + '/view?_noback');
            }).catch(angular.noop);
          });
        });
      });
    });
  };
});
