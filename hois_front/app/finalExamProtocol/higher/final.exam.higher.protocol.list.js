'use strict';

angular.module('hitsaOis').controller('FinalExamHigherProtocolListController', 
  function ($scope, $route, $timeout, $q, QueryUtils, DataUtils, Classifier, dialogService, message, $location, USER_ROLES) {
    $scope.auth = $route.current.locals.auth;
    $scope.search = {};
    $scope.criteria = {};
    var endpoint = '/finalExamHigherProtocols';

    var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
    QueryUtils.createQueryForm($scope, endpoint, {order: 'id'}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $scope.formState = {
      //TODO: õigused
      canCreateProtocol: ($scope.auth.isTeacher() || $scope.auth.isAdmin()) && $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL) !== -1
    };

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.searchForm.$setSubmitted();
      if(!$scope.searchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };

    $q.all(promises).then(function() {
      if($scope.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      if($scope.criteria.studyPeriod) {
        $timeout($scope.loadData);
      } else if($scope.studyPeriods.length === 0) {
        $scope.formState.canCreateProtocol = false;
        message.error('studyYear.studyPeriod.missing');
      }
    });

    $scope.clearSearch = function () {
      $scope.clearCriteria();
      $scope.search = {};
    };

});