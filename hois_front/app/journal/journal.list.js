'use strict';

angular.module('hitsaOis').controller('JournalListController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  var clMapper = Classifier.valuemapper({ status: 'PAEVIK_STAATUS' });
  var promises = clMapper.promises;

  $scope.formState = {};
  $scope.directiveControllers = [];

  $scope.load = function() {
    if (!$scope.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };

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

  
  var order = $scope.currentLanguage() === 'et' ? '2, 5, 3' : '2, 6, 3';
  QueryUtils.createQueryForm($scope, '/journals', {order: order}, clMapper.objectmapper);
  $scope.$watch('criteria.studyYear', function (studyYearId) {
    if (angular.isNumber(studyYearId)) {
      $q.all(promises).then(function () {
        $scope.loadData();
      });
    }
  });
  
  if ($scope.auth.isTeacher()) {
    $scope.criteria.onlyMyJournals = true;
  }

  $scope.$watch("criteria.teacherObject", function (value) {
    $scope.criteria.teacher = angular.isObject(value) ? value.id : value;
  });

  $scope.$watch('criteria.moduleObject', function() {
    $scope.criteria.module = $scope.criteria.moduleObject ? $scope.criteria.moduleObject.id : null;
  });
  

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = { module: [] };
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };
});
