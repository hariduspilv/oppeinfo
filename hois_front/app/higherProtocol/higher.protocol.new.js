'use strict';

angular.module('hitsaOis').controller('HigherProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = "/higherProtocols";

  $scope.record = {
    protocolType: 'PROTOKOLLI_LIIK_P',
    students: []
  };

  $scope.subjectStudyPeriods = QueryUtils.endpoint(baseUrl + "/subjectStudyPeriods").query();

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
    if (ArrayUtils.isEmpty($scope.record.students)) {
      message.error("higherProtocol.error.noStudents");
      return;
    }

    new HigherProtocolEndpoint($scope.record).$save().then(function (response) {
      message.info('main.messages.create.success');
      $location.path(baseUrl + '/' + response.id + '/edit');
    });
  };
});