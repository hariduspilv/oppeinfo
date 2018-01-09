'use strict';

angular.module('hitsaOis').controller('HigherProtocolSearchController', function ($scope, $route, QueryUtils, DataUtils, Classifier, message, $q, ArrayUtils, USER_ROLES) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = "/higherProtocols";

  var clMapper = Classifier.valuemapper({status: 'PROTOKOLL_STAATUS', protocolType: 'PROTOKOLLI_LIIK'});
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'}, clMapper.objectmapper);
  $q.all(clMapper.promises);
  DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

  var Endpoint = QueryUtils.endpoint(baseUrl);

  function setCurrentStudyPeriod() {
    if($scope.criteria && !ArrayUtils.isEmpty($scope.studyPeriods) && !$scope.criteria.studyPeriod) {
        $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }
  }

  function canCreate() {
    return ($scope.auth.isAdmin() || $scope.auth.isTeacher()) &&
    ArrayUtils.includes($scope.auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL);
  }

  $scope.formState = {
    canCreate: canCreate()
  };


  $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function() {
    setCurrentStudyPeriod();
    $scope.loadData();
  });

  $scope.$watch('criteria.studyPeriod', setCurrentStudyPeriod);

});
