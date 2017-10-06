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

}]);
