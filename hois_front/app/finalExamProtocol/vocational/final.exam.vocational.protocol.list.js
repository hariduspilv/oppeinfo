'use strict';

angular.module('hitsaOis').controller('FinalExamVocationalProtocolListController', function ($scope, $route, QueryUtils, DataUtils, Classifier, $q, ArrayUtils, dialogService, message, $location) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  $scope.criteria = {};
  var endpoint = '/finalExamVocationalProtocols';

  function canCreateProtocol() {
    return ($scope.auth.isTeacher() || $scope.auth.isAdmin()) &&  ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL");
  }

  $scope.formState = {
    canCreateProtocol: canCreateProtocol()
  };

  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, endpoint, {}, clMapper.objectmapper);

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
  };

  $scope.curriculumVersionModule = function(row) {
    var result = '';
    result += row.curriculumVersions.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    result += ', ';
    result += row.curriculumVersionOccupationModules.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    return result;
  };
});