'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionController', function (message, $scope, $route, DataUtils, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.saisAdmission = {};
  function entityToForm(saisAdmission) {
    DataUtils.convertStringToDates(saisAdmission, ["periodStart", "periodEnd"]);
    angular.extend($scope.saisAdmission, saisAdmission);
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.revertArchived = function() {
    QueryUtils.endpoint('/saisAdmissions/deArchive/' + $route.current.params.id).save().$promise.then(function(result) {
      if (result.archived) {
        message.error('reception.archive.error');
      } else {
        $scope.saisAdmission = result;
        message.info('reception.archive.revertedArchived');
      }
    });
  };

}).controller('ReceptionSaisAdmissionListController', ['USER_ROLES', 'AuthService', '$q', '$route', '$scope', 'Classifier', 'QueryUtils', function (USER_ROLES, AuthService, $q, $route, $scope, Classifier, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.archiveButton = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_VASTUVOTT);
  var clMapping = $route.current.locals.clMapping;
  var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
  QueryUtils.createQueryForm($scope, $route.current.locals.url, $route.current.locals.params, clMapper ? clMapper.objectmapper : undefined);
  if(clMapper) {
    $q.all(clMapper.promises).then($scope.loadData);
  } else {
    $scope.loadData();
  }

}]);
