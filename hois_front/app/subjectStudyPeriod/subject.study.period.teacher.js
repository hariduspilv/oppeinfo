'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherSearchController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', function ($scope, $sessionStorage, QueryUtils, DataUtils) {
    $scope.currentNavItem = 'teachers';

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/teachers', {order: 'id'});

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

}]).controller('SubjectStudyPeriodTeacherEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'message', 'Classifier', 'SspCapacities', function ($scope, QueryUtils, ArrayUtils, $route, message, Classifier, SspCapacities) {
    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var teacher = $route.current.params.teacherId ? parseInt($route.current.params.teacherId) : null;
    $scope.isNew = teacher === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
    });

    if(teacher) {
        $scope.record = Endpoint.search({studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []});
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
        });
        QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get(function(result) {
            $scope.teacher = result;
        });
    } else {
        $scope.record = new Endpoint({studyPeriod: studyPeriodId, subjectStudyPeriodDtos: []});
        QueryUtils.endpoint('/subjectStudyPeriods/teachers/list/limited/' + studyPeriodId).query(function(result) {
            $scope.teachers = result;
        });
    }

    $scope.save = function() {
        var subjects = $scope.record.subjects;
        if(ArrayUtils.isEmpty(subjects) || ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
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
}]).controller('SubjectStudyPeriodTeacherViewController', ['$scope', 'QueryUtils', '$route', 'Classifier', 'SspCapacities', function ($scope, QueryUtils, $route, Classifier, SspCapacities) {
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
