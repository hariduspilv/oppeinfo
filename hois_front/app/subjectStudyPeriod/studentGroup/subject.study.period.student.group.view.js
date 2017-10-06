'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupViewController', ['$scope', 'QueryUtils', 'Classifier', '$route', 'SspCapacities', function ($scope, QueryUtils, Classifier, $route, SspCapacities) {

    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var studentGroup = parseInt($route.current.params.studentGroupId);
    $scope.isEditing = studentGroup === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/container');

    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, studentGroup: studentGroup, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });
    QueryUtils.endpoint('/studentgroups/' + studentGroup).get(function(response) {
        $scope.studentGroup = response;
        $scope.studentGroup.nameEt = response.code;
        $scope.course = $scope.studentGroup.course.toString();
        getCurriculum();
    });

    function getCurriculum() {
        QueryUtils.endpoint('/subjectStudyPeriods/curriculum/' +  $scope.studentGroup.curriculum.id).get(function(response) {
            $scope.curriculum = response;
            getCurriculumStudyPeriod();
        });
    }

    function getCurriculumStudyPeriod() {
        var sp = $scope.curriculum.studyPeriod;
        $scope.curriculumStudyPeriod = {
            years: Math.floor(sp / 12),
            months: sp % 12
        };
    }

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
    });

}]);
