'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherSearchController', 
  function ($scope, QueryUtils, DataUtils, ArrayUtils, USER_ROLES, AuthService) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);
    $scope.currentNavItem = 'teachers';

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/teachers', {order: 'id'});

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

    $scope.$watch('criteria.teacherObject', function() {
            $scope.criteria.teacher = $scope.criteria.teacherObject ? $scope.criteria.teacherObject.id : null;
        }
    );
  });
