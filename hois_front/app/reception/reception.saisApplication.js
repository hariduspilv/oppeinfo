'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationController', function (message, $location, $scope, $route, DataUtils, dialogService, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.saisApplication = {};
  $scope.getAddress = DataUtils.formatAddress;
  var Endpoint = QueryUtils.endpoint('/saisApplications');

  function entityToForm(saisApplication) {
    DataUtils.convertStringToDates(saisApplication, ["submitted", "saisChanged"]);
    angular.extend($scope.saisApplication, saisApplication);
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.confirm = function () {
    dialogService.confirmDialog({prompt: 'main.menu.reception.saisApplication.delete'}, function() {
      var application = new Endpoint($scope.saisApplication);
      application.$delete().then(function() {
        message.info('main.messages.delete.success');
        $location.url('/reception/saisApplication/search');
      });
    });
  };
});
