'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherViewController', 
  function ($scope, QueryUtils, $route, Classifier, SspCapacities, USER_ROLES, AuthService) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);
    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var teacher = $route.current.params.teacherId;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });

    $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();
  });
