'use strict';

angular.module('hitsaOis').controller('TimetableManagementController', ['$scope', 'message', 'QueryUtils', 'DataUtils', 'Classifier', '$location', 'dialogService',
  function ($scope, message, QueryUtils, DataUtils, Classifier, $location, dialogService) {
    var baseUrl = '/timetables';
    $scope.formState = {xlsDiffUrl: 'timetables/timetableDifference.xls', xlsPlanUrl: 'timetables/timetablePlan.xlsx'};

    QueryUtils.endpoint(baseUrl + '/managementSearchFormData').search().$promise.then(function (result) {
      $scope.formState.studyYears = result.studyYears;
      $scope.allStudyPeriods = result.studyPeriods;
      var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
      if (sy) {
        $scope.criteria.studyYear = sy.id;
        $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
          return t.studyYear === $scope.criteria.studyYear;
        });
        $scope.criteria.studyPeriod = result.currentStudyPeriod;
      }
      $scope.higher = result.higher;
      $scope.vocational = result.vocational;
      if (!$scope.higher || !$scope.vocational) {
        if ($scope.higher) {
          $scope.criteria.type = 'TUNNIPLAAN_LIIK_H';
        }
      }
      $scope.loadData();
    });

    $scope.$watch('criteria.studyYear', function () {
      if ($scope.criteria.studyYear !== undefined && $scope.allStudyPeriods) {
        $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
          return t.studyYear === $scope.criteria.studyYear;
        });
      }
    });

    $scope.copyTimetable = function (rowId) {
      QueryUtils.endpoint(baseUrl + '/getPossibleTargetsForCopy').query({id: rowId}).$promise.then(function (possibleTargets) {
        possibleTargets.forEach(function (element) {
          element.formattedStart = new Date(element.start);
        });
        dialogService.showDialog('timetable/timetable.timetableManagement.copyTimetable.html', function (dialogScope) {
          dialogScope.copyTargets = possibleTargets;
        }, function (submittedDialogScope) {
          var query = {
            start: submittedDialogScope.copyTarget.start,
            higher: submittedDialogScope.copyTarget.isHigher,
            id: submittedDialogScope.copyTarget.id,
            originalTimetable: rowId,
            studyPeriod: submittedDialogScope.copyTarget.studyPeriod
          };
          QueryUtils.endpoint(baseUrl + '/copyTimetable').search(query).$promise.then(function () {
            message.info('main.messages.create.success');
            $scope.loadData();
          });
        });
      });
    };

    $scope.createEvent = function (row) {
      var Endpoint = QueryUtils.endpoint(baseUrl);
      var timetable = new Endpoint();
      timetable.code = row.isHigher ? 'TUNNIPLAAN_LIIK_H' : 'TUNNIPLAAN_LIIK_V';
      timetable.studyPeriod = row.studyPeriod;
      timetable.startDate = row.start;
      timetable.endDate = row.end;
      timetable.$save().then(function (result) {
        $location.url('/timetable/' + result.id + '/view');
      });
    };

    var clMapper = Classifier.valuemapper({status: 'TUNNIPLAAN_STAATUS'});
    QueryUtils.createQueryForm($scope, baseUrl + "/searchTimetableForManagement", {order: 'start_date'}, clMapper.objectmapper);
  }
]);
