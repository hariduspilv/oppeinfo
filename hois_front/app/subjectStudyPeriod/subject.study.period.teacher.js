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
        DataUtils.sortStudyYearsOrPeriods(response);
        setCurrentStudyPeriod();
    });

    $scope.$watch('criteria.studyPeriod', function() {
            if($scope.studyPeriods && !$scope.criteria.studyPeriod) {
                setCurrentStudyPeriod();
            }
        }
    );

    $scope.$watch('criteria.teacherObject', function() {
            if($scope.criteria.teacherObject) {
                $scope.criteria.teacher = $scope.criteria.teacherObject.id;
            }
        }
    );

}]).controller('SubjectStudyPeriodTeacherEditController', ['$scope', 'DataUtils','QueryUtils', 'ArrayUtils', '$route', 'dialogService', 'message', '$location', '$q', '$rootScope', 'Classifier',  'SspCapacities', function ($scope, DataUtils, QueryUtils, ArrayUtils, $route, dialogService, message, $location, $q, $rootScope, Classifier, SspCapacities) {
    $scope.record = {
        subjectStudyPeriodDtos: []
    };
    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var teacher = $route.current.params.teacherId ? parseInt($route.current.params.teacherId) : null;
    $scope.isNew = teacher === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    function setCurrentStudyPeriod() {
        $scope.record.studyPeriod = studyPeriodId ? studyPeriodId : DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        DataUtils.sortStudyYearsOrPeriods(response);
        setCurrentStudyPeriod();
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
        QueryUtils.endpoint('/subjectStudyPeriods/teachers/list/new/' + studyPeriodId).query(function(result) {
            $scope.teachers = result;
        });
    }

    $scope.save = function() {
        var subjects = $scope.record.subjects;
        if(ArrayUtils.isEmpty(subjects) || ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
            message.error('subjectStudyPeriod.error.noDataForSaving');
            return;
        }
        // var wrongSubjects = $scope.record.subjects.filter(function(el){
        //     return !$scope.subjectsLoadValid(el.id);
        // });
        // if(!ArrayUtils.isEmpty(wrongSubjects)) {
        //     var subjectsNames = wrongSubjects.map(function(el){
        //         return $rootScope.currentLanguageNameField(el);
        //     }).join(", ");
        //     dialogService.confirmDialog({
        //         prompt: 'subjectStudyPeriod.error.subjectLoad', 
        //         subject: subjectsNames
        //     }, save);
        //     return;
        // }
        save();
    };

    function save() {
        $scope.capacitiesUtil.filterEmptyCapacities();        
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
        });
    }


}]);
