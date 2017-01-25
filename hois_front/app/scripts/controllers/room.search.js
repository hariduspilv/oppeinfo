'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/rooms/search', {
      templateUrl: 'views/room.search.html',
      controller: 'RoomSearchController',
      controllerAs: 'controller'
    });
  }
).controller('RoomSearchController', function ($scope, Classifier, QueryUtils) {
  $scope.equipmentDefs = Classifier.query({mainClassCode: 'SEADMED'});

  QueryUtils.createQueryForm($scope, '/buildings/searchrooms', {order: 'name'}, function() {
    // set up equipment column
    $scope.tabledata.content.forEach(function(room) {
      room.equipment = room.roomEquipment.map(function(e) { return {equipmentCode: $scope.equipmentDefs[e.equipmentCode], equipmentCount: e.equipmentCount}; });
    });
  });

  $scope.equipmentDefs.$promise.then(function() {
    $scope.equipmentDefs = Classifier.toMap($scope.equipmentDefs.content);
    $scope.loadData();
  });
});
