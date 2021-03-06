'use strict';

angular.module('hitsaOis').controller('SubjectStudyPeriodTeacherViewController',
  function ($scope, QueryUtils, $route, Classifier, SspCapacities, USER_ROLES, AuthService) {
    $scope.auth = $route.current.locals.auth;
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_KOORM);
    var studyPeriodId = parseInt($route.current.params.studyPeriodId, 10);
    var teacher = $route.current.params.teacherId ? parseInt($route.current.params.teacherId, 10) : null;
    var Endpoint = QueryUtils.endpoint('/subjectStudyPeriods/teachers/container');

    $scope.formState = {xlsUrl: 'subjectStudyPeriods/teachers/subjectstudyperiodteacher.xls'};
    $scope.studyPeriod = QueryUtils.endpoint('/subjectStudyPeriods/studyPeriod').get({id: studyPeriodId});

    $scope.container = {studyPeriod: studyPeriodId, teacher: teacher, subjectStudyPeriodDtos: []};
    $scope.record = Endpoint.search($scope.container);
    $scope.record.$promise.then(function(response){
        $scope.capacitiesUtil = new SspCapacities(response);
        $scope.capacityTypes = response.capacityTypes;
        $scope.capacitiesUtil.addEmptyCapacities($scope.capacityTypes);
        $scope.$broadcast('refreshFixedColumns');
        $scope.$broadcast('refreshFixedTableHeight');
    });

    $scope.teacher = QueryUtils.endpoint('/subjectStudyPeriods/teacher/' + teacher).get();

    $scope.teacherCapacitiesSumBySspAndType = function (ssp, type) {
      return $scope.capacitiesUtil.capacitiesSumBySspAndTeacherAndType(ssp, teacher, type);
    };

    // $scope.sspTeacherCapacities = function (subjectStudyPeriod) {
    //   var sspTeacher = subjectStudyPeriod.teachers.filter(function (it) {
    //     return it.teacherId === teacher;
    //   })[0];
    //   return Object.values(sspTeacher.capacities.reduce(function (acc, cur) {
    //     if (!acc[cur.capacityType]) {
    //       acc[cur.capacityType] = cur;
    //       return acc;
    //     }
    //     var existing = acc[cur.capacityType];
    //     if (!existing.subgroup) {
    //       return acc;
    //     }
    //     if (!cur.subgroup) {
    //       acc[cur.capacityType] = cur;
    //       return acc;
    //     }
    //     if (existing.hours < cur.hours) {
    //       acc[cur.capacityType] = cur;
    //     }
    //     return acc;
    //   }, {}));
    // };
  });
