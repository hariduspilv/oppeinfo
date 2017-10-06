'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectViewController', ['$scope','QueryUtils', '$route', 'Classifier',  'SspCapacities', function ($scope, QueryUtils, $route,  Classifier, SspCapacities) {

    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var subject = parseInt($route.current.params.subjectId);
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/subjects/container');

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
    });

    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });
    QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get(function(result) {
        $scope.subject = result;
    });


}]);
