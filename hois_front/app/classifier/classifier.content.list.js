'use strict';


angular.module('hitsaOis').controller('ClassifierContentListController', function ($route, $scope, Classifier, QueryUtils) {
  $scope.mainClassCode = $route.current.params.code;
  QueryUtils.createQueryForm($scope, 'classifier.list', {order: 'value'});

  $scope.mainClass = Classifier.get($scope.mainClassCode);

  $scope.loadData = function () {
    var query = angular.extend({}, $scope.criteria, {mainClassCode: $scope.mainClassCode});
    $scope.toStorage('classifier.list', QueryUtils.getQueryParams(query));
    $scope.tabledata.$promise = Classifier.query(query, $scope.afterLoadData).$promise;
  };

  $scope.isValid = function(record) {
    return (!record.validFrom || new Date(record.validFrom) <= new Date()) && (!record.validThru || new Date(record.validThru) >= new Date());
  };

  $scope.loadData();
});
