'use strict';

angular.module('hitsaOis').controller('FinalThesisListController', function ($scope, $route, $location, $q, QueryUtils, DataUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.criteria = {};
  $scope.noFinalThesisModule = false;
  var endpoint = '/finalThesis';

  var clMapper = Classifier.valuemapper({ status: 'LOPUTOO_STAATUS' });
  QueryUtils.createQueryForm($scope, endpoint, {order: '2'}, clMapper.objectmapper);

  if ($scope.auth.isTeacher() || $scope.auth.isAdmin()) {
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

  $scope.$watch('criteria.studentGroupObject', function () {
    $scope.criteria.studentGroup = $scope.criteria.studentGroupObject ? $scope.criteria.studentGroupObject.id : null;
  });
});