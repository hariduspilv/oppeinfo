'use strict';

angular.module('hitsaOis').controller('HigherProtocolSearchController', ['$scope', '$route', '$q', '$timeout', 'QueryUtils', 'DataUtils', 'Classifier', 'message', 'USER_ROLES',
  function ($scope, $route, $q, $timeout, QueryUtils, DataUtils, Classifier, message, USER_ROLES) {
    $scope.auth = $route.current.locals.auth;
    $scope.search = {};
    $scope.criteria = {};
    var baseUrl = "/higherProtocols";

    var clMapper = Classifier.valuemapper({status: 'PROTOKOLL_STAATUS', protocolType: 'PROTOKOLLI_LIIK'});
    QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'}, clMapper.objectmapper);
    DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });
    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $scope.formState = {canCreate: $scope.auth.authorizedRoles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL) !== -1};

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.higherProtocolSearchForm.$setSubmitted();
      if(!$scope.higherProtocolSearchForm.$valid) {
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
        $scope.canCreate = false;
        message.error('studyYear.studyPeriod.missing');
      }
    });
  }
]);
