'use strict';

angular.module('hitsaOis').controller('PracticeJournalEditController', function ($scope, $route, QueryUtils, Classifier, message, $location, dialogService, DataUtils) {
  var CREDITS_TO_HOURS_MULTIPLIER = 26;

  $scope.auth = $route.current.locals.auth;
  $scope.practiceJournal = {};
  $scope.formState = {};

  function entityToForm(entity) {
    DataUtils.convertObjectToIdentifier(entity, ['module', 'theme', 'teacher']);
    $scope.practiceJournal = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  var clMapper = Classifier.valuemapper({ studyForm: 'OPPEVORM' });
  $scope.$watch('practiceJournal.student', function (student) {
    if (angular.isObject(student)) {
      QueryUtils.endpoint('/students/' + student.id).search(function (result) {
        $scope.formState.student = clMapper.objectmapper(result);
      });

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
  });

  $scope.moduleChanged = function (moduleId) {
    if ($scope.formState.modulesById[moduleId]) {
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
    }
  };

  var PracticeJournalEndpoint = QueryUtils.endpoint('/practiceJournals');
  $scope.save = function () {
    var practiceJournal = new PracticeJournalEndpoint($scope.practiceJournal);
    if (angular.isDefined($scope.practiceJournal.id)) {
      practiceJournal.$update().then(function () {
        message.info('main.messages.create.success');
        entityToForm(practiceJournal);
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
