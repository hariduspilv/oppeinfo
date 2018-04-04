'use strict';

angular.module('hitsaOis').controller('StudentAbsenceController',
  ['$scope', '$route', '$timeout', 'QueryUtils', 'DataUtils', 'ArrayUtils', 'dialogService', 'message', function ($scope, $route, $timeout, QueryUtils, DataUtils, ArrayUtils, dialogService, message) {

    QueryUtils.createQueryForm($scope, '/absences', {order: '-sa.inserted', isAccepted: false});
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);
    $scope.criteria.status = 'isNotAccepted';
    $scope.criteria.isAccepted = false;
    $scope.criteria.isRejected = false;

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

    $scope.statusChanged = function () {
      $scope.criteria.isAccepted = null;
      $scope.criteria.isRejected = null;

      switch ($scope.criteria.status) {
        case 'accepted':
          $scope.criteria.isAccepted = true;
          break;
        case 'rejected':
          $scope.criteria.isRejected = true;
          break;
        case 'isNotAccepted':
          $scope.criteria.isAccepted = false;
          $scope.criteria.isRejected = false;
          break;
      }
    };

    var AcceptEndpoint = QueryUtils.endpoint('/absences/accept');
    $scope.accept = function (absence) {
      dialogService.confirmDialog({
        prompt: 'absence.acceptConfirm'
      }, function () {
        new AcceptEndpoint(absence).$update().then(function () {
          ArrayUtils.remove($scope.tabledata.content, absence);
          message.info('absence.accepted');
        });
      });
    };

    var RejectEndpoint = QueryUtils.endpoint('/absences/reject');
    $scope.reject = function (absence) {
      dialogService.confirmDialog({
        prompt: 'absence.rejectConfirm'
      }, function () {
        new RejectEndpoint(absence).$update().then(function () {
          ArrayUtils.remove($scope.tabledata.content, absence);
          message.info('absence.rejected');
        });
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
