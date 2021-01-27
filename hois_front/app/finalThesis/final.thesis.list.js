'use strict';

angular.module('hitsaOis').controller('FinalThesisListController', function ($location, $route, $scope, $q, Classifier, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.criteria = {};
  var endpoint = '/finalThesis';

  var clMapper = Classifier.valuemapper({ status: 'LOPUTOO_STAATUS' });
  QueryUtils.createQueryForm($scope, endpoint, {order: '2'}, clMapper.objectmapper);

  $scope.directiveControllers = [];
  var clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function () {
    clearCriteria();
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

  if ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher() || $scope.auth.isTeacher()) {
    $q.all(clMapper.promises).then($scope.loadData);
  }
});
