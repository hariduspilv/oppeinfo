'use strict';

angular.module('hitsaOis').controller('HigherProtocolNewController', function ($route, $location, $scope, ArrayUtils, Classifier, QueryUtils, message) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = "/higherProtocols";

  if ($scope.auth.isTeacher()) {
    $scope.$watch('studyPeriod', function () {
      if (angular.isDefined($scope.studyPeriod)) {
        QueryUtils.endpoint('/higherProtocols/subjectStudyPeriods').query({
          studyPeriodId: $scope.studyPeriod
        }).$promise.then(function (subjectStudyPeriods) {
          $scope.subjectStudyPeriods = subjectStudyPeriods;
        });
      }
    });
  }

  $scope.protocolTypes = {};
  Classifier.queryForDropdown({mainClassCode: 'PROTOKOLLI_LIIK'}, function(response) {
    $scope.protocolTypes = Classifier.toMap(response);
  });

  $scope.record = {
    protocolType: 'PROTOKOLLI_LIIK_P',
    students: []
  };

  $scope.clearForm = function() {
    $scope.record.subjectStudyPeriod = undefined;
    $scope.record.students = [];
    $scope.students = [];
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
