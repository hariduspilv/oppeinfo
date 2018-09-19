'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherViewController', 
  function ($scope, QueryUtils, $route, Classifier, SspCapacities, USER_ROLES, AuthService) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);
    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var teacher = $route.current.params.teacherId;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');
    
    $scope.formState = {xlsUrl: 'subjectStudyPeriods/teachers/subjectstudyperiodteacher.xls'};
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});

    $scope.container = {studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []};
    $scope.record = Endpoint.search($scope.container);
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
        $scope.capacityTypes = response.capacityTypes;
    });

    $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();
  });
