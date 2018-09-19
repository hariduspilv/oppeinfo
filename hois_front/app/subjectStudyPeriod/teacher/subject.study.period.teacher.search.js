'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherSearchController', 
  function ($scope, $route, QueryUtils, DataUtils, ArrayUtils, USER_ROLES, AuthService) {
    $scope.schoolId = $route.current.locals.auth.school.id;

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);
    $scope.currentNavItem = 'teachers';

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/teachers/searchByTeacher.xls'};

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
