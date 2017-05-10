'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSearchController', ['$scope', '$sessionStorage', 'QueryUtils', function ($scope, $sessionStorage, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods', {order: 's.' + $scope.currentLanguageNameField()});
    $scope.loadData();
    QueryUtils.endpoint('/autocomplete/studyPeriods').query(function(result) {
        $scope.studyPeriods = result;
    });
    $scope.showTeachers = function(row, bool) {
        var name = "";
        if( row.teachers) {
            name = row.teachers.join("; ");
            var MAX_INITIAL_LENGTH = 30;
            if(!bool && name.length > MAX_INITIAL_LENGTH) {
                name = name.substring(0, MAX_INITIAL_LENGTH) + "...";
            }
        }
        return name;
    };



}]).controller('SubjectStudyPeriodEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'dialogService', 'message', '$location', '$q', '$rootScope', function ($scope, QueryUtils, ArrayUtils, $route, dialogService, message, $location, $q, $rootScope) {

    var baseUrl = '/subjectStudyPeriods';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId) : null;
    var studentGroupId = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId) : null;
    var subjectId = $route.current.params.subjectId ? parseInt($route.current.params.subjectId) : null;

    if(id) {
        Endpoint.get({id: id}).$promise.then(function(response){
            $scope.record = response;
            if(!$scope.record.groupProportion) {
                $scope.record.groupProportion = 'PAEVIK_GRUPI_JAOTUS_1';
            }
            $scope.disableSubject = true;
        });
    } else {
        var initialObject = {
            groupProportion: 'PAEVIK_GRUPI_JAOTUS_1', 
            studyPeriod: studyPeriodId ? studyPeriodId : null, 
            subject: subjectId, teachers: [], 
            studentGroups: studentGroupId ? [studentGroupId] : null
        };
        $scope.record = new Endpoint(initialObject);
        $scope.disableSubject = subjectId !== null;
    }

    if(studentGroupId) {
        $scope.$watch('record.studentGroups', function(newValue, oldValue) {
                if(!ArrayUtils.includes(newValue, studentGroupId)) {
                    $scope.record.studentGroups = oldValue;
                    message.error('subjectStudyPeriod.error.groupCannotBeRemoved');
                }
            }
        );
    }


    QueryUtils.endpoint('/autocomplete/studyPeriods').query(function(result) {
        $scope.studyPeriods = result;
    });

    // QueryUtils.endpoint('/autocomplete/studentgroups').query(function(result) {
    //     $scope.studentGroups = result;
    // });

    QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list').query(function(result) {
        $scope.studentGroups = result.map(function(el){
            var newEl = el;
            newEl.nameEt = el.code;
            newEl.nameEn = el.code;
            return newEl;
        });
    });

    QueryUtils.endpoint('/autocomplete/subjects').get(function(result) {
        $scope.subjects = result.content;
    });

    $scope.addTeacher = function(teacher) {
        if(teacher && !isTeacherAdded(teacher)) {
            $scope.record.teachers.push({
                teacherId: teacher.id,
                name: teacher.nameEt,
                isSignatory: false
            });
        } else if (teacher && isTeacherAdded(teacher)) {
            message.error('subjectStudyPeriodTeacher.teacherAlreadyAdded');
        }
        $scope.teacher = undefined;
    };


    $scope.querySearch = function (text) {
        var url = '/subjectStudyPeriods/teachers';
        var lookup = QueryUtils.endpoint(url);
        var deferred = $q.defer();
        lookup.search({
            name: text
        }, function (data) {
            deferred.$$resolve(data.content);
        });
        return deferred.promise;
    };

    function isTeacherAdded(teacher) {
        return $scope.record.teachers.filter(function(t){return t.teacherId === teacher.id;}).length > 0;
    }

    $scope.removeTeacher = function(teacher) {
        ArrayUtils.remove($scope.record.teachers, teacher);
    };

    $scope.save = function() {
        if(!$scope.subjectStudyPeriodEditForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }
        if(!$scope.record.teachers || $scope.record.teachers.length === 0) {
            message.error('subjectStudyPeriod.error.teacherNotAdded');
            return;
        }
        if($scope.record.id) {
            $scope.record.$update().then(message.updateSuccess);
        } else {
            $scope.record.$save().then(function(){
                message.updateSuccess();
                if(studentGroupId && studyPeriodId) {
                    $rootScope.replaceLastUrl("#/subjectStudyPeriods/" + studentGroupId + "/" + studyPeriodId + "/edit", function(lastUrl){
                        return lastUrl.indexOf("new") !== -1;
                    });
                }
            });
        }
    };

    $scope.delete = function() {
       dialogService.confirmDialog({prompt: 'subjectStudyPeriodTeacher.deleteconfirm'}, function() {
         $scope.record.$delete().then(function() {
           message.info('main.messages.delete.success');
            $rootScope.back(baseUrl);
         });
      });
    };
}]).controller('SubjectStudyPeriodStudentGroupSearchController', ['$scope', 'QueryUtils', 'ArrayUtils', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, DataUtils) {

    QueryUtils.createQueryForm($scope, '/subjectStudyPeriods/studentGroups', {order: 'id'});

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
}]).controller('SubjectStudyPeriodNewController', ['$scope', 'QueryUtils', 'ArrayUtils', 'DataUtils', 'Classifier', '$translate', '$route', 'message', 'dialogService', '$rootScope', function ($scope, QueryUtils, ArrayUtils, DataUtils, Classifier, $translate, $route, message, dialogService, $rootScope) {

    $scope.record = {
        subjectStudyPeriodDtos: []
    };
    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var studentGroup = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId) : null;
    $scope.isEditing = studentGroup === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/container');

    if(studentGroup) {
        $scope.record = Endpoint.search({studyPeriod: studyPeriodId, studentGroup: studentGroup, subjectStudyPeriodDtos: []});
    } else {
        $scope.record = new Endpoint({studyPeriod: studyPeriodId, subjectStudyPeriodDtos: []});
    }

    if(studentGroup) {
        $scope.backUrlRef = "/subjectStudyPeriods/" + studentGroup + "/" + studyPeriodId + "/edit";
        QueryUtils.endpoint('/studentgroups/' + studentGroup).get(function(result) {
            $scope.studentGroup = result;
            $scope.studentGroup.nameEt = result.code;
            $scope.course = $scope.studentGroup.course.toString();
            getCurriculum();
        });
    } else {
        $scope.backUrlRef = "/subjectStudyPeriods/" + studyPeriodId + "/new";

        QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/editform/' + studyPeriodId).query(function(result) {
            $scope.studentGroups = result;
        });
    }

    function getCurriculum() {
        QueryUtils.endpoint('/subjectStudyPeriods/curriculum/' +  $scope.studentGroup.curriculum.id).get(function(result) {
            $scope.curriculum = result;
            getCurriculumStudyPeriod();
        });
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

    function setCurrentStudyPeriod() {
        $scope.record.studyPeriod = studyPeriodId ? studyPeriodId : DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        DataUtils.sortStudyYearsOrPeriods(response);
        setCurrentStudyPeriod();
    });

    function getCurriculumStudyPeriod() {
        var sp = $scope.curriculum.studyPeriod;
        var years = Math.floor(sp / 12);
        var months = sp % 12;
        var strings = ['subjectStudyPeriod.years', 'subjectStudyPeriod.months'];
        $translate(strings).then(function (value) {
            $scope.curriculumStudyPeriod = years + value[strings[0]] + ' ' + months + value[strings[1]];
        });
    }

    $scope.$watch('record.studentGroup', function() {
            if($scope.studentGroups && $scope.record.studentGroup) {
                selectStudentGroup();
            }
        }
    );


    function filterEmptyCapacities() {
        var ssps = $scope.record.subjectStudyPeriodDtos;
        for(var i = 0; i < ssps.length; i++) {
            ssps[i].capacities = ssps[i].capacities.filter(function(el){
                return el.hours !== undefined && el.hours !== null;
            });
        }
    }


    // capacities sums (try another optimal solution)

    function countHours(sum2, val) {
         var newVal = val && val.hours ? val.hours : 0;
         return sum2 += newVal;
    }

    $scope.capacitiesSumBySsp = function(ssp) {
        return ssp.capacities.reduce(countHours, 0);
    };

    function filterByCapacityType(type) {
        return function(el) {
            return el.capacityType === type;
        };
    }

    function getCapacitiesBySubject(subjectId) {
        return $scope.record.subjectStudyPeriodDtos.filter(function(el){
            return el.subject === subjectId;
        });
    }

    function capacitiesSumByType(type, ssps) {
        var sum = 0;
        for(var i = 0; i < ssps.length; i++) {
            sum += ssps[i].capacities.filter(filterByCapacityType(type)).reduce(countHours, 0);
        }
        return sum;
    }

    $scope.capacitiesSumBySubjectAndType = function(subjectId, type) {
        if(!$scope.record || !$scope.record.subjectStudyPeriodDtos) {
            return 0;
        }
        var ssps = getCapacitiesBySubject(subjectId);
        return capacitiesSumByType(type, ssps);
    };

    $scope.capacitiesSumByType = function(type) {
        if(!$scope.record || !$scope.record.subjectStudyPeriodDtos) {
            return 0;
        }
        var ssps = $scope.record.subjectStudyPeriodDtos;
        return capacitiesSumByType(type, ssps);
    };

    function capacitiesSumBySsps(ssps) {
        var sum = 0;
        for(var i = 0; i < ssps.length; i++) {
            sum += $scope.capacitiesSumBySsp(ssps[i]);
        }
        return sum;
    }

    $scope.capacitiesSumOverall = function() {
        if(!$scope.record || !$scope.record.subjectStudyPeriodDtos) {
            return 0;
        }
        var ssps = $scope.record.subjectStudyPeriodDtos;
        return capacitiesSumBySsps(ssps);
    };

    $scope.capacitiesSumBySubject = function(subjectId) {
        if(!$scope.record || !$scope.record.subjectStudyPeriodDtos) {
            return 0;
        }
        var ssps = getCapacitiesBySubject(subjectId);
        return capacitiesSumBySsps(ssps);
    };

    // subbject study period plans' capacities

    $scope.getSubjectsCapacityByType = function(subjectId, typeCode) {
        if(!$scope.record || !$scope.record.subjectStudyPeriodPlans) {
            return 0;
        }
        var plan = $scope.record.subjectStudyPeriodPlans.find(function(el){
            return el.subject === subjectId;
        });
        if(!plan) {
            return 0;
        }
        var capacity = plan.capacities.find(function(el){
            return el.capacityType === typeCode;
        });
        if(!capacity) {
            return 0;
        }
        return capacity.hours? capacity.hours : 0;
    };


    $scope.getSubjectsTotalCapacity = function(subjectId) {
        if(!$scope.record || !$scope.record.subjectStudyPeriodPlans || !$scope.capacityTypes) {
            return 0;
        }
        var sum = 0;
        for(var i = 0; i < $scope.capacityTypes.length; i++) {
            sum += $scope.getSubjectsCapacityByType(subjectId, $scope.capacityTypes[i].code);
        }
        return sum;
    };



// ------------------------------edit capacities
    $scope.getCapacity = function(ssp, type) {
        var capacity = ssp.capacities.find(function(el){
            return el.capacityType === type;
        });
        if(!capacity) {
            capacity = {
                capacityType: type
            };
            ssp.capacities.push(capacity);
        }
        return capacity;
    };

    $scope.setCapacityEditable = function(ssp, type) {
        var capacity = $scope.getCapacity(ssp, type);
        capacity.editable = true;
    };

    $scope.unsetCapacityEditable = function(ssp, type) {
        var capacity = $scope.getCapacity(ssp, type);
        capacity.editable = false;
    };


    // load validation 

    $scope.teachersLoadOk = function(ssp) {
       return ssp.teachers.reduce(function(sum, val){
           return sum += val.scheduleLoad;
       }, 0) >=  $scope.capacitiesSumBySsp(ssp);
    };

    $scope.subjectsLoadValid = function(subjectId) {
        var sspLoadByType, sspsCapacities; 

        for (var i = 0; i < $scope.capacityTypes.length; i++) {
            sspLoadByType = $scope.capacitiesSumBySubjectAndType(subjectId, $scope.capacityTypes[i].code);
            sspsCapacities = $scope.getSubjectsCapacityByType(subjectId, $scope.capacityTypes[i].code);
            if(sspLoadByType > sspsCapacities) {
                return false;
            }
        }
        return true;
    };

    /**
     * FIXME: Logic of saving is not good enough. User can select YES for one subject, but there can be others with not matching loads
     */
    $scope.save = function() {
        var subjects = $scope.record.subjects;
        for(var i = 0; i < subjects.length; i++) {
            if(!$scope.subjectsLoadValid(subjects[i].id)) {
               dialogService.confirmDialog({prompt: 'subjectStudyPeriod.error.subjectLoad', 
               subject: $rootScope.currentLanguageNameField(subjects[i])}, save);
               return;
            }
        }
        save();
    };

    function save() {
        filterEmptyCapacities();
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
        });
    }

}]);
