'use strict';


angular.module('hitsaOis').controller('ApplicationListController', function ($scope, $route, QueryUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS'});
  QueryUtils.createQueryForm($scope, '/applications', {order: '-inserted'}, clMapper.objectmapper);
  $scope.loadData();
});
