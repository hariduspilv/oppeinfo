'use strict';

angular.module('hitsaOis').controller('JournalListController', function ($scope, $route, QueryUtils, Classifier, $q) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  var clMapper = Classifier.valuemapper({status: 'PAEVIK_STAATUS'});
  var promises = clMapper.promises;

  QueryUtils.createQueryForm($scope, '/journals', {}, clMapper.objectmapper);

  $q.all(promises).then(function() {
    $scope.loadData();
  });

  $scope.showModuleNames = function(modules) {
    return modules.map(function(it) {return $scope.currentLanguageNameField(it);}).join(", ");
  };

  $scope.$watch("search.teacher", function(value) {
    $scope.criteria.teacher = angular.isObject(value) ? value.id : value;
  });

  $scope.$watchCollection("search.module", function(value) {
    $scope.criteria.module = angular.isArray(value) ? value.map(function(it) {return it.id;}) : value;
  });

  $scope.clearSearch = function() {
    $scope.clearCriteria();
    $scope.search = {};
  };
});
