'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectViewController', 
  function ($scope, QueryUtils, $route, Classifier, SspCapacities, USER_ROLES, AuthService) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);

    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var subject = parseInt($route.current.params.subjectId, 10);
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/subjects/container');

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/subjects/subjectstudyperiodsubject.xls'};
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});

    $scope.container = {studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []};
    $scope.record = Endpoint.search($scope.container);
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
        $scope.capacityTypes = response.capacityTypes;
    });
    $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get();

    $scope.teacherPlannedLoad = function (teacher) {
      return $scope.capacitiesUtil.teacherPlannedLoad(teacher);
    };
  });
