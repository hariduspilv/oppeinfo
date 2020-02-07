'use strict';

angular.module('hitsaOis').controller('PracticeJournalEditController',
function ($rootScope, $location, $route, $scope, ArrayUtils, Classifier, DataUtils, QueryUtils, dialogService, message, USER_ROLES) {
  var DAYS_AFTER_CAN_EDIT = 30;

  $scope.auth = $route.current.locals.auth;
  $scope.letterGrades = $scope.auth.school.letterGrades;
  $scope.practiceJournal = {moduleSubjects: [{}]};
  $scope.formState = {};

  function noPermission() {
    message.error('main.messages.error.nopermission');
    $location.path('');
  }

  function assertPermissionToCreate() {
    if (!($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) || $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK) === -1) {
      noPermission();
    }
  }

  function assertPermissionToEdit(entity) {
    if (!(entity.canEdit && ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()))) {
      noPermission();
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);

    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);
    $scope.formState.isHigher = entity.isHigher;
    DataUtils.convertObjectToIdentifier(entity, ['practiceEvaluation']);
    entity.moduleSubjects.forEach(function (it) {
      DataUtils.convertObjectToIdentifier(it, ['module', 'theme', 'subject']);
    });
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
      $scope.formState.themesByModuleId = {};
      result.forEach(function (it) {
        $scope.formState.modulesById[it.module.id] = it;
        it.themes.forEach(function (it) {
          $scope.formState.themesById[it.theme.id] = it;
        });
        $scope.formState.themesByModuleId[it.module.id] = it.themes.map(function (it) { return it.theme; });
      });
      $scope.formState.modules = result.map(function (it) { return it.module; });
      removeUnsuitableModules();
    });
  }

  function removeUnsuitableModules() {
    var modules = $scope.formState.modules.map(function (it) { return it.id; });
    for (var i = $scope.practiceJournal.moduleSubjects.length - 1; i >= 0; i--) {
      if ($scope.practiceJournal.moduleSubjects[i].module && modules.indexOf($scope.practiceJournal.moduleSubjects[i].module) === -1) {
        ArrayUtils.remove($scope.practiceJournal.moduleSubjects, $scope.practiceJournal.moduleSubjects[i]);
      }
    }
  }

  function loadStudentPracticeSubjects(student) {
    QueryUtils.endpoint('/practiceJournals/studentPracticeSubjects/' + student.id).query(function (result) {
      $scope.formState.subjectsById = {};
      result.forEach(function (it) {
        $scope.formState.subjectsById[it.id] = it;
      });
      $scope.formState.subjects = result;
      removeUnsuitableSubjects();
    });
  }

  function removeUnsuitableSubjects() {
    var subjects = $scope.formState.subjects.map(function (it) { return it.id; });
    for (var i = $scope.practiceJournal.moduleSubjects.length - 1; i >= 0; i--) {
      if ($scope.practiceJournal.moduleSubjects[i].subject && subjects.indexOf($scope.practiceJournal.moduleSubjects[i].subject) === -1) {
        ArrayUtils.remove($scope.practiceJournal.moduleSubjects, $scope.practiceJournal.moduleSubjects[i]);
      }
    }
  }

  function setModuleCredits(moduleSubject) {
    if(moduleSubject.module && !moduleSubject.theme) {
      moduleSubject.credits = $scope.formState.modulesById[moduleSubject.module].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);
    }
  }

  $scope.addModuleSubject = function () {
    $scope.practiceJournal.moduleSubjects.push({});
  };

  $scope.removeModuleSubject = function (item) {
    ArrayUtils.remove($scope.practiceJournal.moduleSubjects, item);
  };

  $scope.moduleChanged = function (moduleId, moduleSubject) {
    var module = $scope.formState.modulesById[moduleId];
    if (module) {
      var themeIds = module.themes.map(function (it) { return it.theme.id; });
      if (themeIds.indexOf(moduleSubject.theme) === -1) {
        moduleSubject.theme = null;
      }

      setModuleCredits(moduleSubject);

      if (angular.isString(module.assessmentMethodsEt)) {
        addStringToPracticePlan(module.assessmentMethodsEt);
      }
    }

  };

  $scope.themeChanged = function (themeId, moduleSubject) {
    if ($scope.formState.themesById[themeId]) {
      moduleSubject.credits = $scope.formState.themesById[themeId].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);

      if (angular.isString($scope.formState.themesById[themeId].subthemes)) {
        addStringToPracticePlan($scope.formState.themesById[themeId].subthemes);
      }
    } else {
      setModuleCredits(moduleSubject);
    }
  };

  var addStringToPracticePlan = function(addable) {
    if (angular.isString(addable)) {
      if ($scope.practiceJournal.practicePlan === undefined || $scope.practiceJournal.practicePlan === null) {
        $scope.practiceJournal.practicePlan = addable;
      } else {
        if (($scope.practiceJournal.practicePlan + "\n" + addable).length <= 20000) {
          $scope.practiceJournal.practicePlan += "\n" + addable;
        } else {
          var index = 20000 - ($scope.practiceJournal.practicePlan.length + 2);
          if (index > 0) {
            $scope.practiceJournal.practicePlan += "\n" + addable.substring(0, index);
          }
        }
      }
    }
  };

  $scope.subjectChanged = function (subjectId, moduleSubject) {
    if ($scope.formState.subjectsById[subjectId]) {
      moduleSubject.credits = $scope.formState.subjectsById[subjectId].credits;
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);

      if (angular.isString($scope.formState.subjectsById[subjectId].outcomesEt)) {
        addStringToPracticePlan($scope.formState.subjectsById[subjectId].outcomesEt);
      }
    }
  };

  $scope.updateHours = function (moduleSubject, index) {
    if (angular.isNumber(moduleSubject.credits)) {
      moduleSubject.hours = DataUtils.creditsToHours(moduleSubject.credits);
      $scope.practiceJournalForm['hours[' + index + ']'].$setDirty();
    }
  };
  $scope.updateCredits = function (moduleSubject, index) {
    moduleSubject.credits = DataUtils.hoursToCredits(moduleSubject.hours);
    $scope.practiceJournalForm['credits[' + index + ']'].$setDirty();
  };

  function validationPassed() {
    $scope.practiceJournalForm.$setSubmitted();
    if(!$scope.practiceJournalForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }

    if($scope.practiceJournal.moduleSubjects.length === 0) {
      message.error($scope.formState.isHigher ? 'practiceJournal.messages.atleastOneSubjectRequired' : 'practiceJournal.messages.atleastOneModuleRequired');
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
          $scope.practiceJournalForm.$setPristine();
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
          $location.path('/practiceJournals/' + practiceJournal.id + '/edit');
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
