'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherEditController', ['$scope', 'QueryUtils', 'ArrayUtils', '$route', 'message', 'Classifier', 'SspCapacities', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, $route, message, Classifier, SspCapacities, DataUtils) {
    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId) : null;
    var teacher = $route.current.params.teacherId ? parseInt($route.current.params.teacherId) : null;
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
      QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId}).$promise.then(function(response) {
        $scope.studyPeriod = response;
      });
    } else {
      QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        $scope.studyPeriods = response;
        setCurrentStudyPeriod();
      });
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
        QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get(function(result) {
            $scope.teacher = result;
        });
        Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
          $scope.capacityTypes = response;
        });
    }

    function loadTeachers() {
      if($scope.record.studyPeriod) {
        QueryUtils.endpoint('/subjectStudyPeriods/teachers/list/limited/' + $scope.record.studyPeriod).query(function(result) {
            $scope.teachers = result;
        });
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
        });
    };
}]);
