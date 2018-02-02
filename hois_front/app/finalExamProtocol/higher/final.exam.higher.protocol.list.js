'use strict';

angular.module('hitsaOis').controller('FinalExamHigherProtocolListController', 
function ($scope, $route, QueryUtils, DataUtils, Classifier, $q, ArrayUtils, dialogService, message, $location, USER_ROLES) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  $scope.criteria = {};
  var endpoint = '/finalExamHigherProtocols';

  function canCreateProtocol() {
    return ($scope.auth.isTeacher() || $scope.auth.isAdmin()) &&  ArrayUtils.contains($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL);
  }

  $scope.formState = {
    canCreateProtocol: canCreateProtocol()
  };

  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, endpoint, {order: 'id'}, clMapper.objectmapper);
  $q.all(clMapper.promises);
  DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

  function setCurrentStudyPeriod() {
    if($scope.criteria && !ArrayUtils.isEmpty($scope.studyPeriods) && !$scope.criteria.studyPeriod) {
        $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }
  }

  $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function() {
    setCurrentStudyPeriod();
    $scope.loadData();
  });

  $scope.$watch('criteria.studyPeriod', setCurrentStudyPeriod);

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = {};
  };

});