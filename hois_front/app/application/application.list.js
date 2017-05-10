'use strict';

angular.module('hitsaOis').controller('ApplicationListController', function ($scope, $route, $q, QueryUtils, Classifier, DataUtils) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS'});
  QueryUtils.createQueryForm($scope, '/applications', {order: '-inserted'}, clMapper.objectmapper);

  $q.all(clMapper.promises).then(function() {
    $scope.loadData();
  });
});
