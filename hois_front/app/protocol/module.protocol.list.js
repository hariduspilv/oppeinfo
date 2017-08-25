'use strict';

angular.module('hitsaOis').controller('ModuleProtocolListController', function ($scope, $route, QueryUtils, DataUtils, Classifier, $q) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  $scope.criteria = {};
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, '/moduleProtocols', {}, clMapper.objectmapper);

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });


  $scope.$watchCollection("search.module", function(value) {
    $scope.criteria.module = angular.isArray(value) ? value.map(function(it) {return it.id;}) : value;
  });

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = {};
  };

  $scope.curriculumVersionModule = function(row) {
    var result = '';
    result += row.curriculumVersionOccupationModules.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    result += ', ';
    result += row.curriculumVersions.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    return result;
  };
});
