'use strict';

angular.module('hitsaOis').controller('ModuleProtocolNewController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    selectedStudents: [],
    teachers: QueryUtils.endpoint('/moduleProtocols/teachers').query()
  };
  var clMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });

  $scope.curriculumVersionChange = function () {
    $scope.formState.curriculumVersionOccupationModule = undefined;
    $scope.formState.curriculumVersionOccupationModules = QueryUtils.endpoint('/moduleProtocols/occupationModules/' + $scope.formState.curriculumVersion).query();
  };

  $scope.curriculumVersionOccupationModuleChange = function () {
    var query = QueryUtils.endpoint('/moduleProtocols/occupationModule/' + $scope.formState.curriculumVersionOccupationModule).get();
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

  $scope.journalResultsView = function (journalResult) {
    return journalResult.map(function (it) { return it.value; }).join(' / ');
  };

  $scope.selectedStudentsLength = function () {
    return $scope.formState.selectedStudents.length;
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint('/moduleProtocols');
  $scope.submit = function () {
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
        $location.path('/moduleProtocols/' + result.id + '/edit');
      });
  };
});