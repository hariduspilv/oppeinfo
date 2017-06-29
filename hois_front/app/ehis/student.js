'use strict';

angular.module('hitsaOis').controller('StudentEhisController', function ($scope, message, QueryUtils, $mdDialog) {

  var CURRICULA_FULFILMENT = 'CURRICULA_FULFILMENT';
  //var FOREIGN_STUDY = 'FOREIGN_STUDY';
  var GRADUATION = 'GRADUATION';
  //var VOTA = 'VOTA';

  $scope.dataTypes = [
    CURRICULA_FULFILMENT,
    //FOREIGN_STUDY,
    GRADUATION/*,
    VOTA*/
  ];

  $scope.displayDates = [
    //FOREIGN_STUDY,
    GRADUATION/*,
    VOTA*/
  ];

  $scope.criteria = {
    from: new Date(),
    thru: new Date()
  };

  $scope.exportStudents = function() {
    if($scope.studentExportForm.$valid) {
      QueryUtils.endpoint('/students/ehisStudentExport').save($scope.criteria).$promise.then(function(result) {
        message.info('ehis.messages.exportFinished');
        $mdDialog.hide();
        $scope.result = result;
      }).catch(function() {
        $mdDialog.hide();
      });

    } else {
      message.error('main.messages.form-has-errors');
    }
    var parentEl = angular.element(document.body);
    $mdDialog.show({
      parent: parentEl,
      templateUrl: 'ehis/progress.dialog.html'
    });
  };
});
