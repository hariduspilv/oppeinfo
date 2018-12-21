'use strict';

angular.module('hitsaOis').controller('ModuleProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    selectedStudents: []
  };

  if($scope.auth.isTeacher()) {
    $scope.formState.teacher = $scope.auth.teacher;
  }

  var clMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });

  $scope.$watch('formState.teacherObject', function () {
    $scope.formState.teacher = $scope.formState.teacherObject ? $scope.formState.teacherObject.id : null;
  });

  $scope.$watch('formState.curriculumVersionObject', function () {
    $scope.formState.curriculumVersion = $scope.formState.curriculumVersionObject ? $scope.formState.curriculumVersionObject.id : null;
    $scope.formState.curriculumVersionOccupationModule = undefined;
    if ($scope.formState.curriculumVersion) {
      $scope.formState.curriculumVersionOccupationModules = QueryUtils.endpoint('/moduleProtocols/occupationModules/' + $scope.formState.curriculumVersion).query();
    }
  });

  $scope.curriculumVersionOccupationModuleChange = function () {
    var query = QueryUtils.endpoint('/moduleProtocols/occupationModule/' + $scope.formState.studyYear + '/' + $scope.formState.curriculumVersionOccupationModule).get();
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
      if (result.teacher) {
        $scope.formState.teacherObject = result.teacher;
      }
    });
  };

  $scope.journalResultsView = function (journalResult) {
    return journalResult.map(function (it) { return it.value; }).join(' / ');
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint('/moduleProtocols');
  $scope.submit = function () {
    if(!$scope.moduleProtocolNewForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.moduleProtocolNewForm.$setSubmitted();
      return false;
    }

    if (ArrayUtils.isEmpty($scope.formState.selectedStudents)) {
      message.error("moduleProtocol.error.noStudents");
      return;
    }

    var entity = {};
    entity.protocolVdata = {
      curriculumVersionOccupationModule: $scope.formState.curriculumVersionOccupationModule,
      curriculumVersion: $scope.formState.curriculumVersion,
      studyYear: $scope.formState.studyYear,
      teacher: $scope.formState.teacher
    };
    entity.protocolStudents = $scope.formState.selectedStudents.map(function(it) {return {studentId: it};});

    new ModuleProtocolEndpoint(entity)
      .$save().then(function (result) {
        message.info('main.messages.create.success');
        $location.url('/moduleProtocols/' + result.id + '/edit?_noback');
      });
  };
});
