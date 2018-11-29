'use strict';

angular.module('hitsaOis').controller('FinalHigherProtocolListController', 
  function ($scope, $route, $timeout, $q, QueryUtils, DataUtils, Classifier, dialogService, message, $location, USER_ROLES) {
    $scope.auth = $route.current.locals.auth;
    var endpoint = '/finalHigherProtocols';
    var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
    
    $scope.formState = {
      canCreateProtocol: ($scope.auth.isTeacher() || $scope.auth.isAdmin()) && $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL) !== -1
    };
    
    QueryUtils.createQueryForm($scope, endpoint, {order: '14 desc'}, clMapper.objectmapper);

    if (!angular.isDefined($scope.criteria.status)) {
      $scope.criteria.status = 'PROTOKOLL_STAATUS_S';
    }
    
    $q.all(clMapper.promises).then($scope.loadData);

});