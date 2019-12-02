'use strict';

angular.module('hitsaOis').controller('FinalThesisListController', function ($scope, $route, $location, $q, QueryUtils, DataUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.criteria = {};
  $scope.noFinalThesisModule = false;
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
  } else if ($scope.auth.isStudent()) {
    QueryUtils.endpoint(endpoint + '/studentFinalThesis').get().$promise.then(function (result) {
      if (result.finalThesisRequired) {
        if (result.finalThesis) {
          $location.url(endpoint + '/' + result.finalThesis + '/view?_noback');
        } else {
          $location.url(endpoint + '/new');
        }
      } else {
        $scope.noFinalThesisModule = true;
      }
    });
  }
});