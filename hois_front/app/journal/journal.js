'use strict';

angular.module('hitsaOis').controller('JournalController', function ($scope, $route, Classifier, DataUtils, QueryUtils, message, $location) {
  $scope.auth = $route.current.locals.auth;

  $scope.capacityTypes = {};
  Classifier.queryForDropdown({ mainClassCode: 'MAHT' }, function (result) {
    $scope.capacityTypes = Classifier.toMap(result);
  });
  
  function assertPermissionToEdit(entity) {
    if ($route.current.params.action === 'edit' && !entity.canEdit) {
      message.error('main.messages.error.nopermission');
      $location.path('');
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);

    DataUtils.convertStringToDates(entity, ['studyYearStartDate', 'studyYearEndDate']);
    $scope.journal = entity;
    if (!angular.isString(entity.endDate)) {
      $scope.journal.endDate = entity.studyYearEndDate;
    }
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.showModuleNames = function(modules) {
    return modules.map(function(it) {return $scope.currentLanguageNameField(it);}).join(", ");
  };

  $scope.saveEndDate = function() {
    $scope.journalForm.$setSubmitted();
    if (!$scope.journalForm.$valid) {
      message.error('main.messages.form-has-errors');
    } else {
      QueryUtils.endpoint('/journals/' + entity.id + '/saveEndDate').save({endDate: $scope.journal.endDate}, function() {
        message.info('main.messages.create.success');
        $scope.journalForm.$setPristine();
      });
    }
  };

  $scope.saveMoodleCourseId = function() {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/courseLink').update($scope.journal.moodleCourseId, function() {
      message.info('main.messages.create.success');
      $scope.journalForm.$setPristine();
      $scope.moodleCourseId = $scope.journal.moodleCourseId;
    }).$promise.catch(angular.noop);
  };

  $scope.saveReview = function () {
    $scope.journalForm.$setSubmitted();
    if (!$scope.journalForm.$valid) {
      message.error('main.messages.form-has-errors');
    } else {
      QueryUtils.endpoint('/journals/' + entity.id + '/journalReview').save(
        { isReviewOk: $scope.journal.isReviewOk, reviewInfo: $scope.journal.reviewInfo }, function (entity) {
        message.info('main.messages.create.success');
        $scope.journalForm.$setPristine();
        entityToForm(entity);
      });
    }
  };
});
