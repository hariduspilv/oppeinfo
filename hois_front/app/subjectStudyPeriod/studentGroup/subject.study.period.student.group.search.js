'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupSearchController', 
function ($scope, $route, QueryUtils, ArrayUtils, DataUtils, USER_ROLES, AuthService, message) {
    $scope.schoolId = $route.current.locals.auth.school.id;

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);

    $scope.currentNavItem = 'studentGroups';

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/studentGroups/searchByStudentGroup.xls'};

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/studentGroups', {order: 'id'});

    function setCurrentStudyPeriod() {
        if($scope.criteria && !$scope.criteria.studyPeriod) {
            $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        }
        $scope.loadData();
    }

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(setCurrentStudyPeriod);
    $scope.studyPeriods.$promise.then(function (response) {
        response.forEach(function (studyPeriod) {
            studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
    });

    $scope.load = function() {
        if (!$scope.searchForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
        } else {
          $scope.loadData();
        }
      };

    $scope.directiveControllers = [];
    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
        clearCriteria();
        $scope.directiveControllers.forEach(function (c) {
            c.clear();
        });
    }
    
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
