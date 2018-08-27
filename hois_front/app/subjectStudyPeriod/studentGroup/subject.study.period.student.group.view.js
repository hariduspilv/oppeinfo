'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodStudentGroupViewController', 
  function ($scope, QueryUtils, Classifier, $route, SspCapacities, USER_ROLES, AuthService) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN);

    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var studentGroup = parseInt($route.current.params.studentGroupId, 10);
    $scope.isEditing = studentGroup === null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/container');

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/studentGroups/subjectstudyperiodstudentgroup.xls'};
    $scope.container = {studyPeriod: studyPeriodId, studentGroup: studentGroup, subjectStudyPeriodDtos: []};
    $scope.record = Endpoint.search($scope.container);
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
      $scope.curriculum = QueryUtils.endpoint('/subjectStudyPeriods/studentGroups/curriculum/' +  $scope.studentGroup.curriculum.id).get(getCurriculumStudyPeriod);
    }

    function getCurriculumStudyPeriod() {
        var sp = $scope.curriculum.studyPeriod;
        $scope.curriculumStudyPeriod = {
            years: Math.floor(sp / 12),
            months: sp % 12
        };
    }

    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});
  });
