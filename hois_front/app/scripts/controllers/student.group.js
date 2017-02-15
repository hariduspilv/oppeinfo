'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentgroups', {
      templateUrl: 'views/student.group.list.html',
      controller: 'StudentGroupSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('StudentGroupSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils', function ($q, $scope, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM'});
  QueryUtils.createQueryForm($scope, '/studentgroups', {order: 'code'}, clMapper.objectmapper);

  $q.all(clMapper.promises).then($scope.loadData);
}]);
