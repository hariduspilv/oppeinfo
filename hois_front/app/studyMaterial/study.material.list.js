'use strict';

angular.module('hitsaOis').controller('StudyMaterialHigherListController', ['$scope', '$route', '$timeout', 'QueryUtils', 'message',
  'USER_ROLES', 'AuthService', 'DataUtils',
  function ($scope, $route, $timeout, QueryUtils, message, USER_ROLES, AuthService, DataUtils) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL);
    $scope.auth = $route.current.locals.auth;

    QueryUtils.createQueryForm($scope, '/studyMaterial/subjectStudyPeriods', {
      order: 'subject_name_et'
    });

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.searchForm.$setSubmitted();
      if(!$scope.searchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };
    
    $scope.directiveControllers = [];
    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      clearCriteria();
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.studyPeriods.$promise.then(function () {
      $scope.studyPeriods.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
      if ($scope.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      $timeout($scope.loadData);
    });
  }]).controller('StudyMaterialVocationalListController', ['$scope', '$route', '$timeout', 'QueryUtils', 'message',
    'USER_ROLES', 'AuthService', 'DataUtils',
    function ($scope, $route, $timeout, QueryUtils, message, USER_ROLES, AuthService, DataUtils) {
      $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL);
      $scope.auth = $route.current.locals.auth;

      QueryUtils.createQueryForm($scope, '/studyMaterial/journals', {
        order: 'journal_name'
      });

      var loadData = $scope.loadData;
      $scope.loadData = function() {
        $scope.searchForm.$setSubmitted();
        if(!$scope.searchForm.$valid) {
          message.error('main.messages.form-has-errors');
        } else {
          loadData();
        }
      };

      $scope.directiveControllers = [];
      var clearCriteria = $scope.clearCriteria;
      $scope.clearCriteria = function () {
        clearCriteria();
        $scope.directiveControllers.forEach(function (c) {
          c.clear();
        });
      };

      $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
      $scope.studyYears.$promise.then(function () {
        if ($scope.studyYears.length > 0 && !$scope.criteria.studyYear) {
          var currentStudyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears);
          $scope.criteria.studyYear = currentStudyYear ? currentStudyYear.id : undefined;
        }
        $timeout($scope.loadData);
      });

      $scope.$watch('criteria.studyYear', function() {
        if ($scope.criteria.studyYear && $scope.criteria.journalObject) {
          $scope.criteria.journalObject = $scope.criteria.journalObject.studyYear === $scope.criteria.studyYear ? $scope.criteria.journalObject : null;
        }
      });

      $scope.$watch('criteria.journalObject', function() {
        $scope.criteria.journal = $scope.criteria.journalObject ? $scope.criteria.journalObject.id : null;
      });
    }]);
