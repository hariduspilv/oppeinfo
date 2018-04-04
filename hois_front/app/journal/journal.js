'use strict';

angular.module('hitsaOis').controller('JournalController', function ($scope, $route, QueryUtils, ArrayUtils, message, $location) {
  $scope.auth = $route.current.locals.auth;

  function assertPermissionToEdit(entity) {
    if ($route.current.params.action === 'edit' && !entity.canEdit) {
      message.error('main.messages.error.nopermission');
      $location.path('');
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);

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
    QueryUtils.endpoint('/journals/' + entity.id + '/saveEndDate').save({endDate: $scope.journal.endDate}, function() {
      message.info('main.messages.create.success');
      $scope.journalForm.$setPristine();
    });
  };

  $scope.saveMoodleCourseId = function() {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/courseLink').update($scope.journal.moodleCourseId, function() {
      message.info('main.messages.create.success');
      $scope.journalForm.$setPristine();
      $scope.moodleCourseId = $scope.journal.moodleCourseId;
    }).$promise.catch(angular.noop);
  };
});
