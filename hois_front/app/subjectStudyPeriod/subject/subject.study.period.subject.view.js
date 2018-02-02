'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectViewController', 
  function ($scope, QueryUtils, $route, Classifier, SspCapacities, USER_ROLES, AuthService) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);

    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var subject = parseInt($route.current.params.subjectId, 10);
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/subjects/container');

    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});

    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });
    $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get();
  });
