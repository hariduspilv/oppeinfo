'use strict';


angular.module('hitsaOis').controller('ApplicationStudentController', function ($scope, $route) {
  $scope.auth = $route.current.locals.auth;
  var studentId = $route.current.locals.auth.student;

  $scope.studentApplication = {
    type: $route.current.params.type,
    student: {id: studentId},
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  };

});
