'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationController', function ($scope, $route, DataUtils) {

  $scope.saisApplication = {};

  function entityToForm(saisApplication) {
    DataUtils.convertStringToDates(saisApplication, ["submitted", "saisChanged"]);
    angular.extend($scope.saisApplication, saisApplication);
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

});
