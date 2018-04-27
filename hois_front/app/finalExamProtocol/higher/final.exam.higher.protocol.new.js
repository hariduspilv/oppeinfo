'use strict';

angular.module('hitsaOis').controller('FinalExamHigherProtocolNewController', 
function ($scope, $route, $location, $q, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
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
    if ($scope.formState.studyPeriod && $scope.formState.curriculum) {
      $scope.formState.subjects = QueryUtils.endpoint(endpoint + '/subjects/' + $scope.formState.studyPeriod + '/' + $scope.formState.curriculum).query();
    }
  }
  
  $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
  $scope.curriculums = QueryUtils.endpoint(endpoint + '/curriculums').query();
  
  var promises = [];
  promises.push($scope.studyPeriods.$promise);

  $q.all(promises).then(function() {
    $scope.formState.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
  });

  $scope.studyPeriodChanged = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.curriculumChanged = function () {
    $scope.formState.subject = undefined;
    getStudyPeriodSubjects();
  };

  $scope.subjectChanged = function () {
    var query = QueryUtils.endpoint(endpoint + '/subject/' + $scope.formState.subject.id).get();
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
    entity.subjectStudyPeriod = $scope.formState.subject.id;
    entity.protocolType = $scope.formState.protocolType;
    entity.protocolStudents = $scope.formState.selectedStudents.map(function (it) {
      return {
        studentId: it
      };
    });
    entity.curriculum = $scope.formState.curriculum;

    new FinalExamProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.path(endpoint + '/' + result.id + '/edit');
      });
  };
});
