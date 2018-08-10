'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupSearchController', 
function ($scope, $route, QueryUtils, ArrayUtils, DataUtils, USER_ROLES, AuthService) {
    $scope.schoolId = $route.current.locals.auth.school.id;

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);

    $scope.currentNavItem = 'studentGroups';

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/studentGroups', {order: 'id'});

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
        $scope.loadData();
    }

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(setCurrentStudyPeriod);

    $scope.$watch('criteria.studyPeriod', function() {
            if(!ArrayUtils.isEmpty($scope.studyPeriods) && !$scope.criteria.studyPeriod) {
                setCurrentStudyPeriod();
            }
        }
    );

    $scope.$watch('criteria.department', function() {
        if ($scope.criteria.department && $scope.criteria.curriculum) {
            var curriculum = $scope.curricula.find(function (it) { return it.id === $scope.criteria.curriculum; });
            if (curriculum.departments.indexOf($scope.criteria.department) === -1) {
                $scope.criteria.curriculum = undefined;
            }
        }
    });

    $scope.$watch('criteria.curriculum', function() {
        if ($scope.criteria.curriculum && $scope.criteria.studentGroup) {
            var studentGroup = $scope.studentGroups.find(function (it) { return it.id === $scope.criteria.studentGroup; });
            if (studentGroup.curriculum.id !== $scope.criteria.curriculum) {
                $scope.criteria.studentGroup = undefined;
            }
        }
    });

    $scope.curricula = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/curricula').query();

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
});
