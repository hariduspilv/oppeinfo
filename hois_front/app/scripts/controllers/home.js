'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis')
  .controller('HomeController', function ($scope, School) {
    $scope.schools = School.getAll();
  });
