'use strict';

angular.module('hitsaOis').controller('StudentEhisController', ['$scope', 'message', 'QueryUtils', function ($scope, message, QueryUtils) {

  var CURRICULA_FULFILMENT = 'CURRICULA_FULFILMENT';
  var FOREIGN_STUDY = 'FOREIGN_STUDY';
  var GRADUATION = 'GRADUATION';
  var VOTA = 'VOTA';

  $scope.dataTypes = [CURRICULA_FULFILMENT, FOREIGN_STUDY, GRADUATION, VOTA];
  $scope.displayDates = [FOREIGN_STUDY, GRADUATION, VOTA];
  $scope.criteria = {from: new Date(), thru: new Date()};

  $scope.exportStudents = function() {
    if($scope.studentExportForm.$valid) {
      QueryUtils.endpoint('/students/ehisStudentExport').save($scope.criteria).$promise.then(function(result) {
        message.info('ehis.messages.exportFinished');
        $scope.result = result;
      });
    } else {
      message.error('main.messages.form-has-errors');
    }
  };
}
]);
