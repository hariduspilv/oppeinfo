'use strict';

angular.module('hitsaOis').controller('StudentAbsenceController',
['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', 'message', function ($scope, $sessionStorage, QueryUtils, DataUtils, message) {
    QueryUtils.createQueryForm($scope, '/absences', {order: '-sa.inserted'});
    DataUtils.convertStringToDates($scope.criteria, ['validFrom', 'validThru']);
    $scope.loadData();
    var AcceptEndpoint = QueryUtils.endpoint('/absences/accept');

    $scope.accept = function(absence) {
      new AcceptEndpoint(absence).$update().then(function(response){
        absence.isAccepted = response.isAccepted;
        absence.acceptor = response.acceptor; //TODO: value in table not shown
        message.info('absence.accepted');
      });
    };

    $scope.showAll = function() {
      $scope.criteria.showAll = !$scope.criteria.showAll;
      $scope.loadData();
    };
}]);
