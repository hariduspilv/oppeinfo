'use strict';

angular.module('hitsaOis').controller('HigherProtocolNewController', function ($route, $location, $scope, ArrayUtils, QueryUtils, message) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = "/higherProtocols";

  $scope.record = {
    protocolType: 'PROTOKOLLI_LIIK_P',
    students: []
  };

  function getStudents() {
    if ($scope.record.protocolType && $scope.record.subjectStudyPeriod) {
      QueryUtils.endpoint(baseUrl + "/students").query($scope.record).$promise.then(function (response) {
        $scope.record.students = [];
        $scope.students = response;
      });
    }
  }

  $scope.$watch('record.subjectStudyPeriod', getStudents);
  $scope.$watch('record.protocolType', getStudents);

  var HigherProtocolEndpoint = QueryUtils.endpoint(baseUrl);
  $scope.submit = function () {
    if(!$scope.higherProtocolNewForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.higherProtocolNewForm.$setSubmitted();
      return false;
    }

    if (ArrayUtils.isEmpty($scope.record.students)) {
      message.error("higherProtocol.error.noStudents");
      return;
    }

    new HigherProtocolEndpoint($scope.record).$save().then(function (response) {
      message.info('main.messages.create.success');
      $location.url(baseUrl + '/' + response.id + '/edit?_noback');
    });
  };
});