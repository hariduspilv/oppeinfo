'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupSearchController', ['$scope', 'QueryUtils', 'ArrayUtils', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, DataUtils) {

    $scope.currentNavItem = 'studentGroups';

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/studentGroups', {order: 'id'});

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

    QueryUtils.endpoint('/subjectStudyPeriods/curricula').query().$promise.then(function(response){
        $scope.curricula = response;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list').query(function(result) {
        $scope.studentGroups = result.map(function(el){
            var newEl = el;
            newEl.nameEt = el.code;
            newEl.nameEn = el.code;
            return newEl;
        });
    });

    $scope.filterCurriculums = function(curriculum) {
        return $scope.criteria.department ? ArrayUtils.includes(curriculum.departments, $scope.criteria.department) : true;
    };

    $scope.filterStudentGroups = function(studentGroup) {
        return $scope.criteria.curriculum ? studentGroup.curriculum.id === $scope.criteria.curriculum : true;
    };
}]);
