'use strict';

angular.module('hitsaOis').controller('FinalHigherProtocolListController', 
  function ($scope, $route, $q, QueryUtils, Classifier, USER_ROLES) {
    $scope.auth = $route.current.locals.auth;
    var endpoint = '/finalHigherProtocols';
    var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });

    $scope.formState = {
      canCreateProtocol: ($scope.auth.isTeacher() || $scope.auth.isAdmin()) && $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL) !== -1,
      status: 'PROTOKOLL_STAATUS_S'
    };

    QueryUtils.createQueryForm($scope, endpoint, {order: '14 desc', status: $scope.formState.status}, clMapper.objectmapper);

    var _loadData = $scope.loadData;
    $scope.loadData = function () {
      _loadData();
      $scope.formState.status = $scope.criteria.status;
    };
    
    $q.all(clMapper.promises).then($scope.loadData);

    $scope.$watch('hiddenCriteria.subjectObject', function() {
      $scope.criteria.subject = $scope.hiddenCriteria.subjectObject ? $scope.hiddenCriteria.subjectObject.id : null;
    }
  );
});