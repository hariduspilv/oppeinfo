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

  function isStillValid(date) {
    var todayDate = new Date();
    var validThruDate = new Date(date);
    var today = new Date(todayDate.getFullYear(), todayDate.getMonth(), todayDate.getDate());
    var validThru = new Date(validThruDate.getFullYear(), validThruDate.getMonth(), validThruDate.getDate());
    return today.getTime() <= validThru.getTime();
  }

  $scope.isValid = function(classifier) {
    if(!classifier.validThru) {
      return true;
    }
    return isStillValid(classifier.validThru);
  };

  $scope.loadData();
});
