'use strict';

angular.module('hitsaOis').controller('PracticeJournalEditController', 
function ($scope, $route, QueryUtils, Classifier, message, $location, dialogService, DataUtils, $rootScope, ArrayUtils, USER_ROLES) {
  var CREDITS_TO_HOURS_MULTIPLIER = 26;

  $scope.auth = $route.current.locals.auth;
  $scope.practiceJournal = {};
  $scope.formState = {};

  function canCreate() {
    return $scope.auth.isAdmin() && ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK);
  }

  function noPermission() {
    message.error('main.messages.error.nopermission');
    $location.path('');
  }

  function assertPermissionToCreate() {
    if (!canCreate()) {
      noPermission();
    }
  }

  function assertPermissionToEdit(entity) {
    if (!(entity.canEdit && ($scope.auth.isTeacher() || $scope.auth.isAdmin()))) {
      noPermission();
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);
    
    $scope.formState.isHigher = angular.isObject(entity.subject);
    DataUtils.convertObjectToIdentifier(entity, ['module', 'theme', 'teacher', 'subject']);
    $scope.practiceJournal = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
    $rootScope.removeLastUrlFromHistory(function(lastUrl){
      return lastUrl && lastUrl.indexOf('/practiceJournals/new') !== -1;
    });
  } else {
    assertPermissionToCreate();
    $scope.formState.isHigher = $scope.auth.school.higher === true && (($scope.auth.school.vocational === true && $route.current.params.higher === true) || $scope.auth.school.vocational === false);
  }

  var clMapper = Classifier.valuemapper({ studyForm: 'OPPEVORM' });
  $scope.$watch('practiceJournal.student', function (student) {
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
    QueryUtils.endpoint('/practiceJournals/studentPracticeModules/' + student.id).query(function (result) {
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
      if (angular.isNumber($scope.practiceJournal.module)) {
        $scope.moduleChanged($scope.practiceJournal.module);
      }
    });
  }

  function loadStudentPracticeSubjects(student) {
    QueryUtils.endpoint('/practiceJournals/studentPracticeSubjects/' + student.id).query(function (result) {
      $scope.formState.subjectsById = {};
      result.forEach(function (it) {
        $scope.formState.subjectsById[it.id] = it;
      });
      $scope.formState.subjects = result;
    });
  }

  function setModuleCredits() {
    if($scope.practiceJournal.module && !$scope.practiceJournal.theme) {
      $scope.practiceJournal.credits = $scope.formState.modulesById[$scope.practiceJournal.module].credits;
      $scope.practiceJournal.hours = $scope.practiceJournal.credits * CREDITS_TO_HOURS_MULTIPLIER;
    }
  }

  $scope.moduleChanged = function (moduleId) {
    if ($scope.formState.modulesById[moduleId]) {
      setModuleCredits();
      $scope.formState.themes = $scope.formState.modulesById[moduleId].themes.map(function (it) { return it.theme; });

      if (!angular.isDefined(entity) && angular.isString($scope.formState.modulesById[moduleId].assessmentMethodsEt)) {
        $scope.practiceJournal.practicePlan = $scope.formState.modulesById[moduleId].assessmentMethodsEt;
      }
    }
  };

  $scope.themeChanged = function (themeId) {
    if ($scope.formState.themesById[themeId]) {
      $scope.practiceJournal.credits = $scope.formState.themesById[themeId].credits;
      $scope.practiceJournal.hours = $scope.practiceJournal.credits * CREDITS_TO_HOURS_MULTIPLIER;

      if (!angular.isDefined(entity) && angular.isString($scope.formState.themesById[themeId].subthemes)) {
        $scope.practiceJournal.practicePlan = $scope.formState.themesById[themeId].subthemes;
      }
    } else {
      setModuleCredits();
    }
  };

  $scope.subjectChanged = function (subjectId) {
    if ($scope.formState.subjectsById[subjectId]) {
      $scope.practiceJournal.credits = $scope.formState.subjectsById[subjectId].credits;
      $scope.practiceJournal.hours = $scope.practiceJournal.credits * CREDITS_TO_HOURS_MULTIPLIER;

      if (!angular.isDefined(entity) && angular.isString($scope.formState.subjectsById[subjectId].outcomesEt)) {
        $scope.practiceJournal.practicePlan = $scope.formState.subjectsById[subjectId].outcomesEt;
      }
    }
  };

  var PracticeJournalEndpoint = QueryUtils.endpoint('/practiceJournals');
  $scope.save = function () {
    $scope.practiceJournal.isHigher = $scope.formState.isHigher;
    var practiceJournal = new PracticeJournalEndpoint($scope.practiceJournal);
    if (angular.isDefined($scope.practiceJournal.id)) {
      practiceJournal.$update().then(function () {
        message.info('main.messages.create.success');
        entityToForm(practiceJournal);
        $scope.practiceJournalForm.$setPristine();
      });
    } else {
      practiceJournal.$save().then(function () {
        message.info('main.messages.create.success');
        $location.path('/practiceJournals/' + practiceJournal.id + '/edit');
      });
    }
  };

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'practiceJournal.deleteconfirm' }, function () {
      var practiceJournal = new PracticeJournalEndpoint($scope.practiceJournal);
      practiceJournal.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path('/practiceJournals');
      });
    });
  };

});
