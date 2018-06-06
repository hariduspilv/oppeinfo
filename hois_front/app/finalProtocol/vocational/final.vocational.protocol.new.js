'use strict';

angular.module('hitsaOis').controller('FinalVocationalProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  var endpoint = '/finalVocationalProtocols';
  
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    selectedStudents: [],
    curriculumVersions: QueryUtils.endpoint(endpoint + '/curriculumVersions/').query(),
    isFinalThesis: $route.current.params.moduleType === 'thesis' ? true : false
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
    var query = QueryUtils.endpoint(endpoint + '/occupationModule/' + $route.current.params.moduleType + '/' 
      + $scope.formState.studyYear + '/' + $scope.formState.curriculumVersionOccupationModule).get();
    $scope.tabledata = {
      $promise: query.$promise
    };

    query.$promise.then(function (result) {
      $scope.formState.selectedStudents = [];
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

  var FinalProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.submit = function () {
    if(!$scope.finalProtocolNewForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.finalProtocolNewForm.$setSubmitted();
      return false;
    }

    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("finalProtocol.error.noStudents");
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
    entity.isFinalThesis = $scope.formState.isFinalThesis;

    new FinalProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.path(endpoint + '/' + result.id + '/edit');
      }).catch(angular.noop);
  };
  
});
