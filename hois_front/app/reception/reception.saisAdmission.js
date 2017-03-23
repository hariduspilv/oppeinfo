'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionController', function ($scope, $route, DataUtils) {

  $scope.saisAdmission = {};
  function entityToForm(saisAdmission) {
    DataUtils.convertStringToDates(saisAdmission, ["periodStart", "periodEnd"]);
    angular.extend($scope.saisAdmission, saisAdmission);
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

});
