'use strict';

angular.module('hitsaOis').controller('TeacherExportController', ['$mdDialog','$route', '$scope', 'message', 'QueryUtils',
  function ($mdDialog, $route, $scope, message, QueryUtils) {
    var higher = $route.current.locals.higher;
    var exportUrl = higher ? '/teachers/exportToEhis/higher' : '/teachers/exportToEhis/vocational';

    $scope.teacher = {};
    $scope.teacher.allDates = false;

    $scope.exportTeachers = function() {
      if($scope.teacherExportForm.$valid) {
        var parentEl = angular.element(document.body);
        $mdDialog.show({
          parent: parentEl,
          template:
            '<md-dialog style="background-color:transparent;box-shadow:none">' +
            '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait" style="height:120px;">'+
            '<md-progress-circular class="md-hue-2" md-diameter="80px" class="loader"></md-progress-circular>'+
            '</div>' +
            '</md-dialog>'
        });
        QueryUtils.endpoint(exportUrl).post($scope.teacher).$promise.then(function(result) {
          message.info('ehis.messages.exportFinished');
          $mdDialog.hide();
          $scope.result = result;
        }).catch($mdDialog.hide);

      } else {
        message.error('main.messages.form-has-errors');
      }
    };
  }
]);
