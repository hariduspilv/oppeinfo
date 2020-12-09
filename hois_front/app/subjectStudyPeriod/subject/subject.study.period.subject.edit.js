'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectEditController', ['$route', '$scope', 'ArrayUtils', 'DataUtils', 'SspCapacities', 'QueryUtils', 'dialogService', 'message', function ($route, $scope, ArrayUtils, DataUtils, SspCapacities, QueryUtils, dialogService, message) {

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId, 10) : null;
    var subject = $route.current.params.subjectId ? parseInt($route.current.params.subjectId, 10) : null;
    $scope.isNew = subject === null && studyPeriodId === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/subjects/container');
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

    if(subject) {
        $scope.container = {studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []};
        $scope.record = Endpoint.search($scope.container);
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
            $scope.capacityTypes = response.capacityTypes;
            $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
            $scope.$broadcast('refreshFixedColumns');
            $scope.$broadcast('refreshFixedTableHeight');
        });
        $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get();
        $scope.formState = {xlsUrl: 'subjectStudyPeriods/subjects/subjectstudyperiodsubject.xls'};
    }

    function isValid() {
      $scope.subjectStudyPeriodSubjectEditForm.$setSubmitted();
      if(!$scope.subjectStudyPeriodSubjectEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      if(ArrayUtils.isEmpty($scope.record.subjectStudyPeriodDtos)) {
        message.error('subjectStudyPeriod.error.noDataForSaving');
        return false;
      }
      return true;
    }

    $scope.save = function() {
      if(!isValid()) {
        return;
      }
      $scope.capacitiesUtil.filterEmptyCapacities();
      $scope.record.$put().then(function(response){
        message.updateSuccess();
        $scope.record = response;
        $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
        $scope.subjectStudyPeriodSubjectEditForm.$setPristine();
        $scope.$broadcast('refreshFixedColumns');
        $scope.$broadcast('refreshFixedTableHeight');
      });
    };

    $scope.teacherPlannedLoad = function (teacher) {
      return $scope.capacitiesUtil.teacherPlannedLoad(teacher);
    };

    $scope.teacherCapacities = function (subject) {
      dialogService.showDialog('subjectStudyPeriod/teacher.capacities.tmpl.html', function (dialogScope) {
        dialogScope.subject = subject;
        dialogScope.capacityTypes = $scope.capacityTypes;
        dialogScope.capacitiesUtil = $scope.capacitiesUtil;
      }, function () {
        $scope.save();
      });
    };
}]);
