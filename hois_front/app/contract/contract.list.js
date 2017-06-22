'use strict';

angular.module('hitsaOis').controller('ContractListController', function ($scope, $route, QueryUtils, DataUtils, Classifier, $q) {
  $scope.auth = $route.current.locals.auth;

  var clMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
  QueryUtils.createQueryForm($scope, '/contracts', {}, clMapper.objectmapper);
  $q.all(clMapper.promises).then($scope.loadData);
});
