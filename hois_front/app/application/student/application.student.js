'use strict';


angular.module('hitsaOis').controller('ApplicationStudentController', function ($scope, $route) {
  var studentId = $route.current.locals.auth.student;

  $scope.studentApplication = {
    type: $route.current.params.type,
    student: {id: studentId},
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  };

});
