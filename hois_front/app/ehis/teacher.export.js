'use strict';

angular.module('hitsaOis').controller('TeacherExportController', ['$route', '$scope', 'message', 'QueryUtils',
  function ($route, $scope, message, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    $scope.higher = $route.current.locals.higher;
    var exportUrl = $scope.higher ? '/teachers/exportToEhis/higher' : '/teachers/exportToEhis/vocational';

    $scope.teacher = {};
    $scope.teacher.allDates = false;

    $scope.exportTeachers = function() {
      if($scope.teacherExportForm.$valid) {
        QueryUtils.endpoint(exportUrl).post($scope.teacher).$promise.then(function(result) {
          message.info(result && result.length > 0 ? 'ehis.messages.exportFinished' : 
            ($scope.auth.higher ? 'ehis.messages.noTeachersFoundHigher' : 'ehis.messages.noTeachersFoundVocational'));
          $scope.result = result;
        }).catch(angular.noop);
      } else {
        message.error('main.messages.form-has-errors');
      }
    };
  }
]);
