'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationSearchController', ['$route', '$scope', '$q', 'USER_ROLES', 'AuthService', 'Classifier', 'ScholarshipUtils', 'QueryUtils', 'message',
  function ($route, $scope, $q, USER_ROLES, AuthService, Classifier, ScholarshipUtils, QueryUtils, message) {
    $scope.auth = $route.current.locals.auth;
    $scope.currentNavItem = 'applications';
    var baseUrl = '/scholarships/applications';
    $scope.scholarshipType = $route.current.params.type;

    var isStudentGroupTeacher = $scope.auth.isTeacher() && $scope.auth.teacherGroupIds.length > 0;
    $scope.criteria = {};
    $scope.formState = {
      canCreateNewApplication: isStudentGroupTeacher || ($scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS)),
      allowedStipendTypes: ScholarshipUtils.getScholarshipGroupTypes($scope.scholarshipType)
    };
    $scope.getScholarshipApplicationPeriod = ScholarshipUtils.applicationPeriod;

    var clMapper = Classifier.valuemapper({
      termType: 'STIPTOETUS',
      applicationStatus: 'STIPTOETUS_STAATUS'
    });

    QueryUtils.createQueryForm($scope, baseUrl, {
      order: "p.lastname, p.firstname",
      scholarshipType: $scope.scholarshipType}, clMapper.objectmapper);

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.applicationSearchForm.$setSubmitted();
      if(!$scope.applicationSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };

    var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
      if (angular.isNumber(value)) {
        unbindStudyYearWatch();
        $q.all(clMapper.promises).then($scope.loadData);
      }
    });

    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.scholarshipType = $scope.scholarshipType;
    };

  }
]);
