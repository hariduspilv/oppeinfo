'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherViewController', ['$scope', 'QueryUtils', '$route', 'Classifier', 'SspCapacities',
  function ($scope, QueryUtils, $route, Classifier, SspCapacities) {
    var studyPeriodId = parseInt($route.current.params.studyPeriodId);
    var teacher = $route.current.params.teacherId;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
    $scope.record = Endpoint.search({studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []});
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
    });

    $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();
  }
]);
