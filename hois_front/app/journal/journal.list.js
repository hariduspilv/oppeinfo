'use strict';

angular.module('hitsaOis').controller('JournalListController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  var clMapper = Classifier.valuemapper({ status: 'PAEVIK_STAATUS' });
  var promises = clMapper.promises;

  $scope.formState = {};

  QueryUtils.endpoint('/journals/canConfirmAll').search().$promise.then(function(response){
    $scope.formState.canConfirmAll = response.canConfirmAll;
  });

  $scope.confirmAll = function() {
    dialogService.confirmDialog({prompt: 'journal.prompt.confirmAll'}, function() {
      QueryUtils.endpoint('/journals/confirmAll').put().$promise.then(function(response){
        message.info('journal.messages.allConfirmed', {numberOfConfirmedJournals: response.numberOfConfirmedJournals});
        $scope.loadData();
      });
    });
  };

  $scope.criteriaDefer = $q.defer();
  var order = $scope.currentLanguage() === 'et' ? '2, 5, 3' : '2, 6, 3';
  QueryUtils.createQueryForm($scope, '/journals', {order: order}, clMapper.objectmapper);
  $scope.$watch('criteria.studyYear', function (studyYearId) {
    if (angular.isNumber(studyYearId)) {
      $q.all(promises).then(function () {
        $scope.loadData();
        $scope.criteriaDefer.resolve();
      });
    }
  });

  $scope.$watch("criteria.teacherObject", function (value) {
    $scope.criteria.teacher = angular.isObject(value) ? value.id : value;
  });

  $scope.$watch('criteria.moduleObject', function() {
    $scope.criteria.module = $scope.criteria.moduleObject ? $scope.criteria.moduleObject.id : null;
  });

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = { module: [] };
  };
});
