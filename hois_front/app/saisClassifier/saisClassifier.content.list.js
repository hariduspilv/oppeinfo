'use strict';


angular.module('hitsaOis').controller('SaisClassifierContentListController', ['$route', '$scope', 'QueryUtils',
  function ($route, $scope, QueryUtils) {
    var parentCode = $route.current.params.code;

    QueryUtils.createQueryForm($scope, '/saisClassifier/' + parentCode, {order: 'value'});
    $scope.loadData();
  }
]);
