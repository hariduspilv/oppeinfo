'use strict';

angular.module('hitsaOis').controller('FinalVocationalProtocolListController', 
function ($scope, $route, QueryUtils, DataUtils, Classifier, $q, dialogService, message, $location) {
  $scope.auth = $route.current.locals.auth;
  $scope.criteria = {};
  $scope.criteria.status = 'PROTOKOLL_STAATUS_S';
  var endpoint = '/finalVocationalProtocols';

  $scope.load = function() {
    if (!$scope.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  }

  function canCreateProtocol() {
    return ($scope.auth.isTeacher() || $scope.auth.isAdmin()) && $scope.auth.authorizedRoles.indexOf("ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL") !== -1;
  }

  $scope.formState = {
    canCreateProtocol: canCreateProtocol()
  };

  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, endpoint, {order: '14 desc'}, clMapper.objectmapper);

  if (!angular.isDefined($scope.criteria.status)) {
    $scope.criteria.status = 'PROTOKOLL_STAATUS_S';
  }

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });

  $scope.$watch('criteria.moduleObject', function() {
      $scope.criteria.module = $scope.criteria.moduleObject ? $scope.criteria.moduleObject.id : null;
    }
  );

});