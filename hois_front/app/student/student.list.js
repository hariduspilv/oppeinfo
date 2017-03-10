'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/students', {
      templateUrl: 'student/list.html',
      controller: 'StudentSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('StudentSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils', function ($q, $scope, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', status: 'OPPURSTAATUS'});
  QueryUtils.createQueryForm($scope, '/students', {order: 'person.lastname,person.firstname'}, clMapper.objectmapper);

  $q.all(clMapper.promises).then($scope.loadData);
}]);
