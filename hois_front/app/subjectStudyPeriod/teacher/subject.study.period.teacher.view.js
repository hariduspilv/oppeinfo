'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherViewController', ['$scope', 'QueryUtils', '$route', 'Classifier', 'SspCapacities', function ($scope, QueryUtils, $route, Classifier, SspCapacities) {
    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var teacher = $route.current.params.teacherId;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });
    QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
    });
    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });

    QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get(function(result) {
        $scope.teacher = result;
    });
}]);
