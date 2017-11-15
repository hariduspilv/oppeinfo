'use strict';

angular.module('hitsaOis').controller('ContractEditController', function ($scope, $route, QueryUtils, Classifier, message, $location, dialogService, DataUtils) {
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
    entityToForm(entity);
  } else {
    $scope.formState.isHigher = $scope.auth.school.higher === true && (($scope.auth.school.vocational === true && $route.current.params.higher === true) || $scope.auth.school.vocational === false);
  }

  function loadEnterprises() {
    $scope.formState.enterprisesById = {};
    return QueryUtils.endpoint('/autocomplete/enterprises').query(function (result) {
      result.forEach(function (it) {
        $scope.formState.enterprisesById[it.id] = it;
      });
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

      //in case of when module is set (edit)
      if (angular.isNumber($scope.contract.module)) {
        $scope.moduleChanged($scope.contract.module);
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

  $scope.moduleChanged = function (moduleId) {
    if ($scope.formState.modulesById[moduleId]) {
      $scope.formState.themes = $scope.formState.modulesById[moduleId].themes.map(function (it) { return it.theme; });

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
      });
    });
  };


  var ContractEndpoint = QueryUtils.endpoint('/contracts');
  $scope.save = function (success) {
    $scope.contract.isHigher = $scope.formState.isHigher;
    var contract = new ContractEndpoint($scope.contract);
    if (angular.isDefined($scope.contract.id)) {
      contract.$update().then(function () {
        message.info('main.messages.create.success');
        entityToForm(contract);
        if (angular.isFunction(success)) {
          success();
        }
      });
    } else {
      contract.$save().then(function () {
        message.info('main.messages.create.success');
        $location.url('/contracts/' + contract.id + '/edit?_noback');
      });
    }
  };

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'contract.deleteconfirm' }, function () {
      var contract = new ContractEndpoint($scope.contract);
      contract.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path('/contracts');
      });
    });
  };

  $scope.sendToEkis = function () {
    $scope.contractForm.$setSubmitted();
    if(!$scope.contractForm.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }

    dialogService.confirmDialog({ prompt: 'contract.ekisconfirm' }, function () {
      $scope.save(function () {
        var EkisEndpoint = QueryUtils.endpoint('/contracts/sendToEkis/' + $scope.contract.id);
        new EkisEndpoint().$save().then(function (contract) {
          message.info('contract.messages.sendToEkis.success');
          $location.url('/contracts/' + contract.id + '/view?_noback');
        });
      });
    });
  };
});
