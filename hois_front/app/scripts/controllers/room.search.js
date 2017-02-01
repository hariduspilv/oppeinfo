'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/rooms/search', {
      templateUrl: 'views/room.search.html',
      controller: 'RoomSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('RoomSearchController', ['$scope', 'Classifier', 'QueryUtils', function ($scope, Classifier, QueryUtils) {
  $scope.equipmentDefs = Classifier.queryForDropdown({mainClassCode: 'SEADMED'});

  QueryUtils.createQueryForm($scope, '/buildings/searchrooms', {order: 'name'}, function(rows) {
    // set up equipment column
    rows.forEach(function(room) {
      room.equipment = room.roomEquipment.map(function(e) { return {equipment: $scope.equipmentDefs[e.equipment], equipmentCount: e.equipmentCount}; });
    });
  });

  $scope.equipmentDefs.$promise.then(function() {
    $scope.equipmentDefs = Classifier.toMap($scope.equipmentDefs);
    $scope.loadData();
  });
}]);
