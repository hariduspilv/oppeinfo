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



}]).controller('SubjectStudyPeriodEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'dialogService', 'message', '$q', '$rootScope', function ($scope, QueryUtils, ArrayUtils, $route, dialogService, message, $q, $rootScope) {

    var baseUrl = '/subjectStudyPeriods';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId) : null;
    var studentGroupId = $route.current.params.studentGroupId ? parseInt($route.current.params.studentGroupId) : null;
    var subjectId = $route.current.params.subjectId ? parseInt($route.current.params.subjectId) : null;
    var teacherId = $route.current.params.teacherId ? parseInt($route.current.params.teacherId) : null;

    $scope.hasObligatoryStudentGroup = studentGroupId !== null;
    $scope.obligatoryTeacher = teacherId;

    if(studentGroupId) {
        QueryUtils.endpoint('/studentgroups').get({id: studentGroupId}).$promise.then(function(response){
            $scope.studentGroup = response;
        });
    }

    if(id) {
        Endpoint.get({id: id}).$promise.then(function(response){
            $scope.record = response;
            if(studentGroupId) {
                ArrayUtils.remove($scope.record.studentGroups, studentGroupId);
            }
            if(!$scope.record.groupProportion) {
                $scope.record.groupProportion = 'PAEVIK_GRUPI_JAOTUS_1';
            }
            $scope.disableSubject = true;
        });
    } else {
        var initialObject = {
            groupProportion: 'PAEVIK_GRUPI_JAOTUS_1',
            studyPeriod: studyPeriodId ? studyPeriodId : null,
            subject: subjectId,
            teachers: [],
            studentGroups: []
        };
        $scope.record = new Endpoint(initialObject);
        $scope.disableSubject = subjectId !== null;
        if(teacherId) {
            QueryUtils.endpoint('/subjectStudyPeriods/teacher').get({id: teacherId}).$promise.then(function(response){
                $scope.record.teachers.push({teacherId: response.id, name: response.nameEt});
            });
        }
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query(function(result) {
        $scope.studyPeriods = result;
    });

    QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/list').query(function(result) {
        $scope.studentGroups = result.filter(function(el){
            return studentGroupId ? el.id !== studentGroupId : true;
        }).map(function(el){
            var newEl = el;
            newEl.nameEt = el.code;
            newEl.nameEn = el.code;
            return newEl;
        });
    });

    QueryUtils.endpoint('/subjectStudyPeriods/subjects/list').query(function(result) {
        $scope.subjects = result;
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
        var url = '/subjectStudyPeriods/teachers/page';
        var lookup = QueryUtils.endpoint(url);
        var deferred = $q.defer();
        lookup.search({
            name: text
        }, function (data) {
            deferred.resolve(data.content);
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
        if(studentGroupId) {
            $scope.record.studentGroups.push(studentGroupId);
        }
        if($scope.record.teachers.length === 1) {
            $scope.record.teachers[0].isSignatory = true;
        }
        if($scope.record.id) {
            $scope.record.$update().then(message.updateSuccess);
        } else {
            $scope.record.$save().then(function(){
                message.updateSuccess();
                if(studentGroupId && studyPeriodId) {
                    $rootScope.replaceLastUrl("#/subjectStudyPeriods/studentGroups/" + studentGroupId + "/" + studyPeriodId + "/edit", function(lastUrl){
                        return lastUrl.indexOf("new") !== -1;
                    });
                } else if(teacherId && studyPeriodId) {
                    $rootScope.replaceLastUrl("#/subjectStudyPeriods/teachers/" + teacherId + "/" + studyPeriodId + "/edit", function(lastUrl){
                        return lastUrl.indexOf("new") !== -1;
                    });
                } else if (subjectId && studyPeriodId && !studentGroupId && !teacherId) {
                    $rootScope.replaceLastUrl("#/subjectStudyPeriods/subjects/" + subjectId + "/" + studyPeriodId + "/edit", function(lastUrl){
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
            $rootScope.back('#' + baseUrl);
         });
      });
    };
}]);
