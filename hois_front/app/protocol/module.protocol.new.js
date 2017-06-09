'use strict';

angular.module('hitsaOis').controller('ModuleProtocolNewController', function ($scope, $route, QueryUtils, DataUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    selectedStudents: [],
    teachers: QueryUtils.endpoint('/moduleProtocols/teachers').query()
  };
  var clMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  //TODO: vaikimisi on vastaval õppeaastal tunnijaotusplaanis märgitud mooduli vastutaja

  $scope.curriculumVersionChange = function () {
    $scope.formState.curriculumVersionOccupationModule = undefined;
    $scope.formState.curriculumVersionOccupationModules = QueryUtils.endpoint('/moduleProtocols/occupationModules/' + $scope.formState.curriculumVersion).query();
  };

  $scope.curriculumVersionOccupationModuleChange = function () {
    var query = QueryUtils.endpoint('/moduleProtocols/occupationModuleStudents/' + $scope.formState.curriculumVersionOccupationModule).query({});
    $scope.tabledata = {
      $promise: query.$promise
    };

    query.$promise.then(function (result) {
      $scope.tabledata.content = clMapper.objectmapper(result);
      result.forEach(function (it) {
        $scope.formState.selectedStudents.push(it.studentId);
      });
    });
  };

  $scope.journalResultsView = function (journalResult) {
    return journalResult.map(function (it) { return it.value; }).join('/ ');
  };

  $scope.selectedStudentsLength = function() {
    return $scope.formState.selectedStudents.length;
  };

  $scope.submit = function () {
    var data = {
      students: $scope.formState.selectedStudents,
      curriculumVersionOccupationModule: $scope.formState.curriculumVersionOccupationModule,
      curriculumVersion: $scope.formState.curriculumVersion,
      studyYear: $scope.formState.studyYear,
      teacher: $scope.formState.teacher
    };

    QueryUtils.endpoint('/moduleProtocols/create').save(data, function (entity) {
      $scope.protocol = entity;
    });
  };
});
