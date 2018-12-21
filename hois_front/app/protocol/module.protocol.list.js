'use strict';

angular.module('hitsaOis').controller('ModuleProtocolListController', function ($scope, $route, QueryUtils, DataUtils, Classifier, $q, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  $scope.criteria = {};
  $scope.directiveControllers = [];

  function canCreateProtocol() {
    return ($scope.auth.isTeacher() || $scope.auth.isAdmin()) && $scope.auth.authorizedRoles.indexOf("ROLE_OIGUS_K_TEEMAOIGUS_MOODULPROTOKOLL") !== -1;
  }

  $scope.load = function() {
    if (!$scope.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };

  $scope.formState = {
    canCreateProtocol: canCreateProtocol()
  };

  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, '/moduleProtocols', {}, clMapper.objectmapper);

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });

  $scope.$watch('criteria.moduleObject', function() {
      $scope.criteria.module = $scope.criteria.moduleObject ? $scope.criteria.moduleObject.id : null;
    }
  );

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = {};
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

  $scope.curriculumVersionModule = function(row) {
    var result = '';
    result += row.curriculumVersions.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    result += ', ';
    result += row.curriculumVersionOccupationModules.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    return result;
  };
});
