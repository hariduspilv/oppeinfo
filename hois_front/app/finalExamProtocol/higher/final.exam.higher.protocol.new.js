'use strict';

angular.module('hitsaOis').controller('FinalExamHigherProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  var endpoint = '/finalExamHigherProtocols';

  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    protocolType: 'PROTOKOLLI_LIIK_P',
    selectedStudents: [],
    subjects: []
  };

  var clMapper = Classifier.valuemapper({
    status: 'OPPURSTAATUS'
  });

  function getStudyPeriodSubjects() {
    $scope.formState.subjects = QueryUtils.endpoint(endpoint + '/subjects/' + $scope.formState.studyPeriod).query();
  }

  QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function (periods) {
    $scope.studyPeriods = periods;
    $scope.formState.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod(periods).id;
    getStudyPeriodSubjects();
  });

  $scope.studyPeriodChanged = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.subjectChanged = function () {
    var query = QueryUtils.endpoint(endpoint + '/subject/' + $scope.formState.subject.subjectStudyPeriod).get();
    $scope.tabledata = {
      $promise: query.$promise
    };

    query.$promise.then(function (result) {
      $scope.formState.selectedStudents = [];
      $scope.tabledata.content = clMapper.objectmapper(result.subjectStudents);
      result.subjectStudents.forEach(function (it) {
        if (it.status.code === 'OPPURSTAATUS_O') {
          $scope.formState.selectedStudents.push(it.studentId);
        }
      });
    });
  };

  var FinalExamProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.submit = function () {
    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("finalExamProtocol.error.noStudents");
      return;
    }

    var entity = {};
    entity.subject = $scope.formState.subject.id;
    entity.subjectStudyPeriod = $scope.formState.subject.subjectStudyPeriod;
    entity.protocolType = $scope.formState.protocolType;
    entity.protocolStudents = $scope.formState.selectedStudents.map(function (it) {
      return {
        studentId: it
      };
    });

    new FinalExamProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.path(endpoint + '/' + result.id + '/edit');
      });
  };
});
