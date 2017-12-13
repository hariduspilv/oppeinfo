'use strict';

angular.module('hitsaOis').controller('StudentAbsenceController',
  ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', 'message', '$route', function ($scope, $sessionStorage, QueryUtils, DataUtils, message, $route) {

    var defaultParams = {
      order: '-sa.inserted',
      studyYear: $route.current.locals.currentStudyYear.currentStudyYear,
      isAccepted: false
    };

    QueryUtils.createQueryForm($scope, '/absences', defaultParams);

    $scope.studyPeriods = $route.current.locals.studyPeriods;

    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);
    $scope.loadData();
    var AcceptEndpoint = QueryUtils.endpoint('/absences/accept');

    $scope.accept = function (absence) {
      new AcceptEndpoint(absence).$update().then(function (response) {
        absence.isAccepted = response.isAccepted;
        absence.canAccept = response.canAccept;
        absence.acceptor = response.acceptor;
        message.info('absence.accepted');
      });
    };

    var initialLoad = true;

    $scope.$watch('criteria.studyYear', function () {
      if (!$scope.criteria.studyYear) {
        $scope.criteria.studyYear = $route.current.locals.currentStudyYear.currentStudyYear;
      }
      if(initialLoad) {
        initialLoad = false;
      } else {
        $scope.criteria.studyPeriod = undefined;
      }
    });

    $scope.filterByStudyYear = function(studyYearId) {
      return function(studyPeriod) {
        return !studyYearId || studyPeriod.studyYear === studyYearId;
      };
    };

  }]);
