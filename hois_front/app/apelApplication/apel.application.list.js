'use strict';

angular.module('hitsaOis').controller('ApelApplicationListController', function ($scope, $route, ArrayUtils, QueryUtils, Classifier, $q) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({
    status: 'VOTA_STAATUS'
  });

  QueryUtils.createQueryForm($scope, '/apelApplications', {
    order: 'student_lastname, student_firstname, inserted desc'
  });

  if ($scope.auth.isStudent()) {
    $scope.afterLoadData = function (resultData) {
      $scope.tabledata.content = resultData.content;
      $scope.tabledata.totalElements = resultData.totalElements;
    };
  }

  if (!$scope.criteria.status && $scope.auth.isAdmin()) {
    $scope.criteria.status = ['VOTA_STAATUS_E'];
    if (ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_M_TEEMAOIGUS_VOTAKOM")) {
      $scope.criteria.status.push('VOTA_STAATUS_V');
    }
  }

  $q.all(clMapper.promises).then($scope.loadData);

  $scope.$watch('criteria.studentObject', function () {
    $scope.criteria.student = $scope.criteria.studentObject ? $scope.criteria.studentObject.id : null;
  });

  $scope.$watch('criteria.committeeObject', function () {
    $scope.criteria.committee = $scope.criteria.committeeObject ? $scope.criteria.committeeObject.id : null;
  });

});
