'use strict';

angular.module('hitsaOis').controller('JournalListController', function ($q, $route, $scope, USER_ROLES, AuthService, Classifier, QueryUtils, dialogService, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  var clMapper = Classifier.valuemapper({ status: 'PAEVIK_STAATUS' });
  var promises = clMapper.promises;

  $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query(function (result) {
    $scope.studyYearMap = result.reduce(function(mapped, studyYear) {
      mapped[studyYear.id] = studyYear;
      return mapped;
    }, {});
  });

  $scope.formState = {
    canAddStudents: ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher() || $scope.auth.isTeacher()) &&
      AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK)
  };
  $scope.directiveControllers = [];

  $scope.load = function() {
    $scope.searchForm.$setSubmitted();
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
        $scope.load();
      });
    });
  };

  $scope.addStudents = function() {
    dialogService.confirmDialog({prompt: 'journal.prompt.addStudents', studyYear: $scope.studyYearMap[$scope.criteria.studyYear].nameEt}, function() {
		QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint('/journals/addAllSuitableStudents').get({ studyYearId: $scope.criteria.studyYear }).$promise.then(function (response) {
		  QueryUtils.loadingWheel($scope, false);
        if (response.numberOfAddedStudents > 0) {
          message.info('journal.messages.studentsAdded', {numberOfJournals: response.numberOfJournals,
            numberOfAddedStudents: response.numberOfAddedStudents});
          $scope.load();
        } else {
          message.warn('journal.messages.noStudentsAdded');
        }
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

  if ($scope.auth.isTeacher() &&  $scope.criteria.onlyMyJournals === undefined) {
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
