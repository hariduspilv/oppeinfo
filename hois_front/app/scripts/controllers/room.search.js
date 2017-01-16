'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/buildings/searchrooms', {
      templateUrl: 'views/room.search.html',
      controller: 'RoomSearchController',
      controllerAs: 'controller'
    });
  }
).controller('RoomSearchController', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/buildings/searchrooms', {order: 'name'});
  $scope.loadData();
});
