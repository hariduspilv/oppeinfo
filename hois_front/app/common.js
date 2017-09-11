'use strict';

angular.module('hitsaOis').controller('SimpleListController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $route, $scope, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;

    var clMapping = $route.current.locals.clMapping;
    var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
    QueryUtils.createQueryForm($scope, $route.current.locals.url, $route.current.locals.params, clMapper ? clMapper.objectmapper : undefined);

    if(clMapper) {
      $q.all(clMapper.promises).then($scope.loadData);
    } else {
      $scope.loadData();
    }
  }
]);
