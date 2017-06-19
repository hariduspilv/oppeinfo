'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectSearchController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', function ($scope, $sessionStorage, QueryUtils, DataUtils) {
    $scope.currentNavItem = 'subjects';

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/subjects', {order: 'id'});

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
        $scope.loadData();
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        setCurrentStudyPeriod();
    });

    $scope.$watch('criteria.studyPeriod', function() {
            if($scope.studyPeriods && !$scope.criteria.studyPeriod) {
                setCurrentStudyPeriod();
            }
        }
    );

    $scope.$watch('criteria.teacherObject', function() {
            $scope.criteria.teacher = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.id : null;
        }
    );

    $scope.$watch('criteria.subjectObject', function() {
            $scope.criteria.subject = $scope.criteria.subjectObject ? $scope.criteria.subjectObject.id : null;
        }
    );

}]).controller('SubjectStudyPeriodSubjectEditController', ['$scope','QueryUtils', 'ArrayUtils', '$route', 'message', 'Classifier',  'SspCapacities', function ($scope, QueryUtils, ArrayUtils, $route, message, Classifier, SspCapacities) {

    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var subject = $route.current.params.subjectId ? parseInt($route.current.params.subjectId) : null;
    $scope.isNew = subject === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/subjects/container');

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
    });

    if(subject) {
        $scope.record = Endpoint.search({studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []});
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
        });
        QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get(function(result) {
            $scope.subject = result;
        });
    } else {
        $scope.record = new Endpoint({studyPeriod: studyPeriodId, subjectStudyPeriodDtos: []});
        QueryUtils.endpoint('/subjectStudyPeriods/subjects/list/limited/' + studyPeriodId).query(function(result) {
            $scope.subjects = result;
        });
    }

    $scope.save = function() {
        $scope.subjectStudyPeriodSubjectEditForm.$setSubmitted();
        if(ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
            message.error('subjectStudyPeriod.error.noDataForSaving');
            return;
        }
        $scope.record.studyPeriod = studyPeriodId;
        $scope.capacitiesUtil.filterEmptyCapacities();
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
        });
    };
}]).controller('SubjectStudyPeriodSubjectViewController', ['$scope','QueryUtils', '$route', 'Classifier',  'SspCapacities', function ($scope, QueryUtils, $route,  Classifier, SspCapacities) {

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
