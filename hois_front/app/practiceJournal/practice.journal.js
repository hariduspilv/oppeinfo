'use strict';

angular.module('hitsaOis').controller('PracticeJournalEditController', 
function ($scope, $rootScope, $location, $route, QueryUtils, Classifier, message, dialogService, DataUtils, USER_ROLES) {
  var CREDITS_TO_HOURS_MULTIPLIER = 26;
  var DAYS_AFTER_CAN_EDIT = 30;

  $scope.auth = $route.current.locals.auth;
  $scope.practiceJournal = {};
  $scope.formState = {};

  function noPermission() {
    message.error('main.messages.error.nopermission');
    $location.path('');
  }

  function assertPermissionToCreate() {
    if (!$scope.auth.isAdmin() || $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK) === -1) {
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
    
    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);
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
      if (angular.isNumber($scope.practiceJournal.module)) {
        updateModuleThemes($scope.practiceJournal.module);
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

  function updateModuleThemes(moduleId) {
    var module = $scope.formState.modulesById[moduleId];
    if (module) {
      $scope.formState.themes = module.themes.map(function (it) { return it.theme; });
    }
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
      updateModuleThemes(moduleId);

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

  $scope.updateHours = function () {
    if (angular.isNumber($scope.practiceJournal.credits)) {
      $scope.practiceJournal.hours = DataUtils.creditsToHours($scope.practiceJournal.credits);
      $scope.practiceJournalForm.hours.$setDirty();
    }
  };
  $scope.updateCredits = function () {
    $scope.practiceJournal.credits = DataUtils.hoursToCredits($scope.practiceJournal.hours);
    $scope.practiceJournalForm.credits.$setDirty();
  };

  function validationPassed() {
    $scope.practiceJournalForm.$setSubmitted();
    if(!$scope.practiceJournalForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }
    return true;
  }

  function isBeforeDaysAfterCanEdit(practiceJournal) {
    var now = moment();
    var endDate = moment(practiceJournal.endDate);

    return moment.duration(now.diff(endDate)).asDays() <= DAYS_AFTER_CAN_EDIT;
  }

  var PracticeJournalEndpoint = QueryUtils.endpoint('/practiceJournals');
  $scope.save = function () {
    if(!validationPassed()) {
      return false;
    }
    isBeforeDaysAfterCanEdit($scope.practiceJournal);

    $scope.practiceJournal.isHigher = $scope.formState.isHigher;
    var practiceJournal = new PracticeJournalEndpoint($scope.practiceJournal);
    if (angular.isDefined($scope.practiceJournal.id)) {
      if (!isBeforeDaysAfterCanEdit($scope.practiceJournal)) {        
        dialogService.confirmDialog({prompt: 'practiceJournal.prompt.isAfterDaysAfterCanEditSave'}, function () {
          practiceJournal.$update().then(function () {
            message.info('main.messages.update.success'); 
            $location.path('/practiceJournals');
          });
        });
      } else {
        practiceJournal.$update().then(function () {
          message.info('main.messages.update.success');
          entityToForm(practiceJournal);
          $location.path('/practiceJournals/' + practiceJournal.id + '/edit');
        });
      }
    } else {
      if (!isBeforeDaysAfterCanEdit($scope.practiceJournal)) {        
        dialogService.confirmDialog({prompt: 'practiceJournal.prompt.isAfterDaysAfterCanEditSave'}, function () {
          practiceJournal.$save().then(function () {
            message.info('main.messages.create.success');
            $location.path('/practiceJournals');
          });
        });
      } else {
        practiceJournal.$save().then(function () {
          message.info('main.messages.create.success');
          entityToForm(practiceJournal);
          $scope.practiceJournalForm.$setPristine();
        });
      }
    }
  };

  $scope.confirm = function () {
    if(!validationPassed()) {
      return false;
    }
    $scope.practiceJournal.isHigher = $scope.formState.isHigher;
    if (!isBeforeDaysAfterCanEdit($scope.practiceJournal)) {        
      dialogService.confirmDialog({prompt: 'practiceJournal.prompt.isAfterDaysAfterCanEditConfirm'}, function () {
        confirmPracticeJournal(true);
      });
    } else {
      dialogService.confirmDialog({prompt: 'practiceJournal.prompt.confirmConfirm'}, function () {
        confirmPracticeJournal(false);
      });
    }
  };

  function confirmPracticeJournal(sendBackToList) {
    QueryUtils.endpoint('/practiceJournals/' + $scope.practiceJournal.id + '/confirm/').put($scope.practiceJournal, function (practiceJournal) {
      message.info('practiceJournal.messages.confirmed');
      if (sendBackToList) {
        $location.path('/practiceJournals');
      } else {
        entityToForm(practiceJournal);
        $scope.practiceJournalForm.$setPristine();
      }
    });
  }

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
