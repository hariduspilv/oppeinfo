'use strict';

angular.module('hitsaOis').controller('FinalExamVocationalProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  var endpoint = '/finalExamVocationalProtocols';
  
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    selectedStudents: [],
    curriculumVersions: QueryUtils.endpoint(endpoint + '/curriculumVersions/').query()
  };

  if ($scope.auth.isTeacher()) {
    $scope.formState.teacher = $scope.auth.teacher;
  }

  var clMapper = Classifier.valuemapper({
    status: 'OPPURSTAATUS'
  });

  $scope.curriculumVersionChange = function () {
    $scope.formState.curriculumVersionOccupationModule = undefined;
    $scope.formState.curriculumVersionOccupationModules = QueryUtils.endpoint(endpoint + '/occupationModules/' + $scope.formState.curriculumVersion).query();
  };

  $scope.curriculumVersionOccupationModuleChange = function () {
    var query = QueryUtils.endpoint(endpoint + '/occupationModule/' + $scope.formState.curriculumVersionOccupationModule).get();
    $scope.tabledata = {
      $promise: query.$promise
    };

    query.$promise.then(function (result) {
      $scope.tabledata.content = clMapper.objectmapper(result.occupationModuleStudents);
      result.occupationModuleStudents.forEach(function (it) {
        if (it.status.code === 'OPPURSTAATUS_O') {
          $scope.formState.selectedStudents.push(it.studentId);
        }
      });
      if (result.teacher && !$scope.formState.teacher) {
        $scope.formState.teacher = result.teacher.id;
      }
    });
  };

  var FinalExamProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.submit = function () {
    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("finalExamProtocol.error.noStudents");
      return;
    }

    var entity = {};
    entity.protocolVdata = {
      curriculumVersionOccupationModule: $scope.formState.curriculumVersionOccupationModule,
      curriculumVersion: $scope.formState.curriculumVersion,
      studyYear: $scope.formState.studyYear,
      teacher: $scope.formState.teacher
    };
    entity.protocolStudents = $scope.formState.selectedStudents.map(function (it) { return { studentId: it};
    });

    new FinalExamProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.path(endpoint + '/' + result.id + '/edit');
      });
  };
  
});
