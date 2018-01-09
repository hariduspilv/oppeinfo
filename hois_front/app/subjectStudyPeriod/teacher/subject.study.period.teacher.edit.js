'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'message', 'Classifier', 'SspCapacities', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, $route, message, Classifier, SspCapacities, DataUtils) {
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
      $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(setCurrentStudyPeriod);
    }

    if(teacher) {
        $scope.record = Endpoint.search({studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []});
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
            Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
              $scope.capacityTypes = response;
              $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
            });
        });
        $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();
    }

    function loadTeachers() {
      if($scope.record.studyPeriod) {
        $scope.teachers = QueryUtils.endpoint('/subjectStudyPeriods/teachers/list/limited/' + $scope.record.studyPeriod).query();
      }
    }

    $scope.$watch('record.studyPeriod', loadTeachers);

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
        $scope.capacitiesUtil.filterEmptyCapacities();
        $scope.record.$put().then(function(response){
            message.updateSuccess();
            $scope.record = response;
            $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
            $scope.subjectStudyPeriodTeacherEditForm.$setPristine();
        });
    };
}]);
