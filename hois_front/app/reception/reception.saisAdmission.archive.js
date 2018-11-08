'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionArchiveController', ['message','$route', '$scope', 'QueryUtils', 'dialogService', "$filter",
function (message,$route, $scope, QueryUtils, dialogService, $filter) {
  $scope.auth = $route.current.locals.auth;

  $scope.save = function () {
    dialogService.showDialog('reception/reception.saisAdmission.archive.confirm.html', 
    function(dialogScope) {
      dialogScope.archive = $filter("hoisDate")($scope.archive.endDate);
      dialogScope.submit = function () {
        QueryUtils.endpoint('/saisAdmissions/archive').save($scope.archive).$promise.then(function() {
          message.info('reception.archive.archivedAdmission');
        });
      };
    });
  };

}
]);