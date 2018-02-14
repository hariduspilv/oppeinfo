'use strict';

angular.module('hitsaOis').controller('StudyMaterialHigherListController', ['$scope', '$route', '$timeout', 'QueryUtils', 'message',
  'USER_ROLES', 'AuthService', 'DataUtils',
  function ($scope, $route, $timeout, QueryUtils, message, USER_ROLES, AuthService, DataUtils) {
    $scope.canEdit = true;//AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJALID);
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

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $scope.studyPeriods.$promise.then(function () {
      if ($scope.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      $timeout($scope.loadData);
    });

    $scope.$watch('teacher', function (newTeacher) {
      $scope.criteria.teacher = newTeacher ? newTeacher.id : null;
    });
  }]).controller('StudyMaterialVocationalListController', ['$scope', '$route', '$timeout', 'QueryUtils', 'message',
    'USER_ROLES', 'AuthService',
    function ($scope, $route, $timeout, QueryUtils, message, USER_ROLES, AuthService) {
      $scope.canEdit = true;//AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJALID);
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
  
      $timeout($scope.loadData);

      $scope.$watch('teacher', function (newTeacher) {
        $scope.criteria.teacher = newTeacher ? newTeacher.id : null;
      });
    }]);
