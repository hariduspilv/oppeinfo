'use strict';

angular.module('hitsaOis').controller('FinalExamHigherProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  var endpoint = '/finalExamHigherProtocols';

  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
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

  $scope.studyPeriodChange = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.subjectChange = function () {
    var query = QueryUtils.endpoint(endpoint + '/subject/' + $scope.formState.subject).get();
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
      if (result.teacher && !$scope.formState.teacher) {
        $scope.formState.teacher = result.teacher.id;
      }
    });
  };

  function getSubjectStudyPeriod(subjectId) {
    for (var i = 0; $scope.formState.subjects.length; i++) {
      if ($scope.formState.subjects[i].id === subjectId) {
        return $scope.formState.subjects[i].subjectStudyPeriod;
      }
    }
  }

  var FinalExamProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.submit = function () {
    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("finalExamProtocol.error.noStudents");
      return;
    }

    var entity = {};
    entity.subject = $scope.formState.subject;
    entity.subjectStudyPeriod = getSubjectStudyPeriod($scope.formState.subject);
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
