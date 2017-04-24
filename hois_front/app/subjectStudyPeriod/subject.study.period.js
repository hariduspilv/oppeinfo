'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectStudyPeriods', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.search.html',
        controller: 'SubjectStudyPeriodSearchController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
    .when('/subjectStudyPeriod/:id/edit', {
        templateUrl: 'subjectStudyPeriod/subject.study.period.edit.html',
        controller: 'SubjectStudyPeriodEditController',
        controllerAs: 'controller',
        resolve: {
            translationLoaded: function($translate) { return $translate.onReady(); }
        },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]).controller('SubjectStudyPeriodSearchController', ['$scope', '$sessionStorage', 'QueryUtils', function ($scope, $sessionStorage, QueryUtils) {
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



}]).controller('SubjectStudyPeriodEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'dialogService', 'message', '$location', '$q', function ($scope, QueryUtils, ArrayUtils, $route, dialogService, message, $location, $q) {

    var baseUrl = '/subjectStudyPeriods';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    Endpoint.get({id: id}).$promise.then(function(response){
        $scope.record = response;
    });

    QueryUtils.endpoint('/autocomplete/studyPeriods').query(function(result) {
        $scope.studyPeriods = result;
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
        } else if (isTeacherAdded(teacher)) {
            message.error('subjectStudyPeriodTeacher.teacherAlreadyAdded');
        }
        $scope.teacher = undefined;
    };


    $scope.querySearch = function (text) {
        var url = '/autocomplete/teachers';
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
        if(!$scope.subjectStudyPeriodEditForm.$valid && !$scope.record.signatoryIdcode) {
            message.error('main.messages.form-has-errors');
            return;
        }
        $scope.record.$update().then(message.updateSuccess);
    };

    $scope.delete = function() {
       dialogService.confirmDialog({prompt: 'subjectStudyPeriodTeacher.deleteconfirm'}, function() {
         $scope.record.$delete().then(function() {
           message.info('main.messages.delete.success');
             $location.path(baseUrl);
         });
      });
    };
}]);
