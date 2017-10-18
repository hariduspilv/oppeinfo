'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodSubjectEditController', ['$scope','QueryUtils', 'ArrayUtils', '$route', 'message', 'Classifier',  'SspCapacities', 'DataUtils', function ($scope, QueryUtils, ArrayUtils, $route, message, Classifier, SspCapacities, DataUtils) {

    var studyPeriodId = $route.current.params.studyPeriodId ? parseInt($route.current.params.studyPeriodId) : null;
    var subject = $route.current.params.subjectId ? parseInt($route.current.params.subjectId) : null;
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
      $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(setCurrentStudyPeriod);
    }

    if(subject) {
        $scope.record = Endpoint.search({studyPeriod: studyPeriodId, subject: subject, subjectStudyPeriodDtos: []});
        $scope.record.$promise.then(function(response){
            $scope.capacitiesUtil = new SspCapacities(response);
            Classifier.queryForDropdown({mainClassCode: 'MAHT'}, function(response){
              $scope.capacityTypes = response;
              $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
            });
        });
        $scope.subject = QueryUtils.endpoint('/subjectStudyPeriods/subject/' + subject).get();
    }

    function loadSubjects() {
      if($scope.record.studyPeriod) {
        $scope.subjects = QueryUtils.endpoint('/subjectStudyPeriods/subjects/list/limited/' + $scope.record.studyPeriod).query();
      }
    }

    $scope.$watch('record.studyPeriod', loadSubjects);

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
      });
    };
}]);
