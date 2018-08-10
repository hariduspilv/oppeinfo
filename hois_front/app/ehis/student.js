'use strict';

angular.module('hitsaOis').controller('StudentEhisController', ['$scope', 'message', 'QueryUtils', function ($scope, message, QueryUtils) {

  var CURRICULA_FULFILMENT = 'CURRICULA_FULFILMENT';
  var FOREIGN_STUDY = 'FOREIGN_STUDY';
  var GRADUATION = 'GRADUATION';
  var VOTA = 'VOTA';

  $scope.dataTypes = [CURRICULA_FULFILMENT, FOREIGN_STUDY, GRADUATION, VOTA];
  $scope.displayDates = [FOREIGN_STUDY, GRADUATION, VOTA];
  $scope.criteria = {from: new Date(), thru: new Date()};

  function processResult(result) {
    if ($scope.criteria.dataType === 'VOTA') {
      var flatResult = [];
      result.forEach(function(row) {
        if (angular.isArray(row.records) && row.records.length > 0) {
          flatResult.push(angular.extend({}, row, row.records[0]));
          row.records.forEach(function(record, idx) {
            if (idx > 0) {
              flatResult.push(record);
            }
          });
        } else {
          flatResult.push(row);
        }
      });
      return flatResult;
    }
    return result;
  }

  $scope.exportStudents = function() {
    if($scope.studentExportForm.$valid) {
      QueryUtils.endpoint('/students/ehisStudentExport').post($scope.criteria).$promise.then(function(result) {
        message.info(result && result.length > 0 ? 'ehis.messages.exportFinished' : 'ehis.messages.nostudentsfound');
        $scope.result = processResult(result);
      }).catch(angular.noop);
    } else {
      message.error('main.messages.form-has-errors');
    }
  };
}
]);
