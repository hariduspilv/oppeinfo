'use strict';


angular.module('hitsaOis').controller('ApplicationStudentController', function ($scope, $route, QueryUtils, ArrayUtils, message, $location) {
  $scope.auth = $route.current.locals.auth;
  var studentId = $route.current.locals.auth.student;
  if ($scope.auth.isGuestStudent() && !ArrayUtils.includes(['AVALDUS_LIIK_MUU'], $route.current.params.type)) {
    message.error('main.messages.error.nopermission');
    $location.path('');
    return;
  }

  $scope.studentApplication = {
    type: $route.current.params.type,
    student: {id: studentId},
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  };

  if ($scope.auth.isParent()) {
    QueryUtils.endpoint('/students').get({ id: studentId }, function (result) {
      $scope.student = {
        nameEt: result.person.fullname + " (" + result.person.idcode + ")"
      }
    });
  }

});
