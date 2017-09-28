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
}]).controller('SubjectStudyPeriodStudentGroupEditController', ['$scope', 'QueryUtils', 'ArrayUtils', 'Classifier', '$route', 'message', 'dialogService', '$rootScope', 'SspCapacities', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, Classifier, $route, message, dialogService, $rootScope, SspCapacities, DataUtils) {

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId) : null;
    var studentGroup = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId) : null;
    $scope.isEditing = studentGroup === null && studyPeriodId === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/container');

    $scope.record = {};

    function setCurrentStudyPeriod() {
      if(!$scope.record.studyPeriod) {
        $scope.record.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        studyPeriodId = $scope.record.studyPeriod;
      }
    }

    if(!studyPeriodId) {
      QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        setCurrentStudyPeriod();
      });
    }

    if(studentGroup) {
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
    }

    function loadStudentGroups() {
      if($scope.record.studyPeriod) {
        QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list/limited/' + $scope.record.studyPeriod).query(function(response) {
          $scope.studentGroups = response;
        });
      }
    }

    $scope.$watch('record.studyPeriod', loadStudentGroups);

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

    function selectStudentGroup() {
        $scope.studentGroup = $scope.studentGroups.find(function(el){
            return el.id === $scope.record.studentGroup;
        });
        if($scope.studentGroup) {
            $scope.course = $scope.studentGroup.course.toString();
            getCurriculum();
        }
    }

    Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
        $scope.capacityTypes = response;
    });

    if(!$scope.isEditing) {
      QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
          $scope.studyPeriod = response;
      });
    }

    $scope.$watch('record.studentGroup', function() {
            if($scope.studentGroups && $scope.record.studentGroup) {
                selectStudentGroup();
            }
        }
    );

    // load validation

    $scope.subjectsLoadValid = function(subjectId) {
        var sspLoadByType, sspsCapacities;
        var ssps = $scope.capacitiesUtil.getCapacitiesBySubject(subjectId);

        var getCapacityByType = function(capacityTypeCode) {
            return function(el) {
                return el.capacityType === capacityTypeCode;
            };
        };

        for (var i = 0; i < $scope.capacityTypes.length; i++) {

            sspsCapacities = $scope.capacitiesUtil.getSubjectsCapacityByType(subjectId, $scope.capacityTypes[i].code);

            for(var j = 0; j < ssps.length; j++) {
                var capacity = ssps[j].capacities.find(getCapacityByType($scope.capacityTypes[i].code));
                sspLoadByType = capacity ? capacity.hours : 0;
                if(sspLoadByType > sspsCapacities) {
                    return false;
                }
            }
        }
        return true;
    };
    /**
     * Old solution where all ssps are considered
     */
    // $scope.subjectsLoadValid = function(subjectId) {
    //     var sspLoadByType, sspsCapacities;
    //     for (var i = 0; i < $scope.capacityTypes.length; i++) {
    //         sspLoadByType = $scope.capacitiesSumBySubjectAndType(subjectId, $scope.capacityTypes[i].code);
    //         sspsCapacities = $scope.getSubjectsCapacityByType(subjectId, $scope.capacityTypes[i].code);
    //         if(sspLoadByType > sspsCapacities) {
    //             return false;
    //         }
    //     }
    //     return true;
    // };

    $scope.save = function() {
        var subjects = $scope.record.subjects;
        if(ArrayUtils.isEmpty(subjects) || ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
            message.error('subjectStudyPeriod.error.noDataForSaving');
            return;
        }
        var wrongSubjects = $scope.record.subjects.filter(function(el){
            return !$scope.subjectsLoadValid(el.id);
        });
        if(!ArrayUtils.isEmpty(wrongSubjects)) {
            var subjectsNames = wrongSubjects.map(function(el){
                return $rootScope.currentLanguageNameField(el);
            }).join(", ");
            dialogService.confirmDialog({
                prompt: 'subjectStudyPeriod.error.subjectLoad',
                subject: subjectsNames
            }, save);
            return;
        }
        save();
    };

    function save() {
        $scope.record.studyPeriod = studyPeriodId;
        $scope.capacitiesUtil.filterEmptyCapacities();
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
        });
    }

}]).controller('SubjectStudyPeriodStudentGroupViewController', ['$scope', 'QueryUtils', 'Classifier', '$route', 'SspCapacities', function ($scope, QueryUtils, Classifier, $route, SspCapacities) {

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
