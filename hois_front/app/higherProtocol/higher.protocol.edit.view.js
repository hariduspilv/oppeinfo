'use strict';

angular.module('hitsaOis').controller('HigherProtocolEditViewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message) {
  $scope.auth = $route.current.locals.auth;

  $scope.record = $route.current.locals.entity;


  $scope.save = function() {
    console.log("save");
  };

  $scope.confirmAndSave = function() {
    console.log("confirmAndSave");
  };

});
