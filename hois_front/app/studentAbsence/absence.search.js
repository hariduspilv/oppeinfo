'use strict';

angular.module('hitsaOis').controller('StudentAbsenceController',
  ['$scope', '$route', '$timeout', 'QueryUtils', 'DataUtils', 'message', function ($scope, $route, $timeout, QueryUtils, DataUtils, message) {

    QueryUtils.createQueryForm($scope, '/absences', {order: '-sa.inserted', isAccepted: false});
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.absencesSearchForm.$setSubmitted();
      if(!$scope.absencesSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.studyYears.$promise.then(function() {
      var currentStudyYear = DataUtils.getCurrentStudyYearOrPeriod($scope.studyYears);
      $scope.criteria.studyYear = currentStudyYear ? currentStudyYear.id : undefined;

      if($scope.criteria.studyYear) {
        $timeout($scope.loadData);
      } else if($scope.studyYears.length === 0) {
        message.error('studyYear.missing');
      }
    });

    var AcceptEndpoint = QueryUtils.endpoint('/absences/accept');
    $scope.accept = function (absence) {
      new AcceptEndpoint(absence).$update().then(function (response) {
        absence.isAccepted = response.isAccepted;
        absence.canAccept = response.canAccept;
        absence.acceptor = response.acceptor;
        message.info('absence.accepted');
      });
    };

    $scope.filterByStudyYear = function(studyYearId) {
      return function(studyPeriod) {
        return !studyYearId || studyPeriod.studyYear === studyYearId;
      };
    };

    $scope.studyYearChanged = function() {
      $scope.criteria.studyPeriod = undefined;
    };
  }]);
