'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherEditController', ['$rootScope', '$route', '$scope', 'ArrayUtils', 'DataUtils', 'SspCapacities', 'QueryUtils', 'dialogService', 'message', function ($rootScope, $route, $scope, ArrayUtils, DataUtils, SspCapacities, QueryUtils, dialogService, message) {
    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var teacher = $route.current.params.teacherId ? parseInt($route.current.params.teacherId, 10) : null;
    $scope.isNew = teacher === null && studyPeriodId === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');
    $scope.record = {};

    function setCurrentStudyPeriod() {
      if(!$scope.record.studyPeriod) {
        $scope.record.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
        studyPeriodId = $scope.record.studyPeriod;
      }
    }

    if(studyPeriodId) {
      $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
    } else {
      $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(setCurrentStudyPeriod);
      $scope.studyPeriods.$promise.then(function (response) {
        response.forEach(function (studyPeriod) {
          studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
        });
      });
    }

    if(teacher) {
        $scope.container = {studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []};
        $scope.record = Endpoint.search($scope.container);
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
            $scope.capacityTypes = response.capacityTypes;
            $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes, true);
            $scope.$broadcast('refreshFixedColumns');
            $scope.$broadcast('refreshFixedTableHeight');
        });
        $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();
        $scope.formState = {xlsUrl: 'subjectStudyPeriods/teachers/subjectstudyperiodteacher.xls'};
    }

    function loadTeachers() {
      if($scope.record.studyPeriod) {
        $scope.teachers = QueryUtils.endpoint('/subjectStudyPeriods/teachers/list/limited/' + $scope.record.studyPeriod).query();
      }
    }

    $scope.$watch('record.studyPeriod', loadTeachers);

    $scope.sspTeacherCapacities = function (subjectStudyPeriod) {
      var sspTeacher = subjectStudyPeriod.teachers.filter(function (it) {
        return it.teacherId === teacher;
      })[0];
      return sspTeacher.capacities;
    };

    function isValid() {
      $scope.subjectStudyPeriodTeacherEditForm.$setSubmitted();
      if(!$scope.subjectStudyPeriodTeacherEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      var subjects = $scope.record.subjects;
      if(ArrayUtils.isEmpty(subjects) || ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
          message.error('subjectStudyPeriod.error.noDataForSaving');
          return;
      }
      return true;
    }

    $scope.save = function() {
      if(!isValid()) {
        return;
      }

      var wrongSubjects = $scope.record.subjects.filter(function(el){
        return !$scope.capacitiesUtil.subjectsLoadValid(el.id, $scope.capacityTypes);
      });
      if (!ArrayUtils.isEmpty(wrongSubjects)) {
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
      $scope.capacitiesUtil.filterEmptyCapacities();
      $scope.record.$put().then(function(response) {
          message.updateSuccess();
          $scope.record = response;
          $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes,true);
          $scope.subjectStudyPeriodTeacherEditForm.$setPristine();
          $scope.$broadcast('refreshFixedColumns');
          $scope.$broadcast('refreshFixedTableHeight');
      });
    }
}]);
