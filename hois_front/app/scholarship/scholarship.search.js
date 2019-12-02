'use strict';

angular.module('hitsaOis').controller('ScholarshipSearchController', ['$route', '$scope', '$q', 'AuthService', 'Classifier', 'ScholarshipUtils', 'QueryUtils', 'USER_ROLES',
  function ($route, $scope, $q, AuthService, Classifier, ScholarshipUtils, QueryUtils, USER_ROLES) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    var baseUrl = '/scholarships';
    $scope.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    var clMapper = Classifier.valuemapper({type: 'STIPTOETUS'});
    $scope.formState = {};

    QueryUtils.createQueryForm($scope, baseUrl, {order: 'id', allowedStipendTypes: $scope.allowedStipendTypes}, clMapper.objectmapper);
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.allowedStipendTypes = $scope.allowedStipendTypes;
    };

    if($route.current.locals.params.scholarship) {
      $scope.formState.typeIsScholarship = true;
      $scope.scholarshipType = "scholarship";
    } else if ($route.current.locals.params.grant) {
      $scope.scholarshipType = "grant";
    } else if ($route.current.locals.params.scholarshipType) {
      $scope.scholarshipType = $route.current.locals.params.scholarshipType;
    }

    var unbindStudyPeriodWatch = $scope.$watch('criteria.studyPeriod', function(value) {
      if (angular.isNumber(value)) {
        unbindStudyPeriodWatch();
        $q.all(clMapper.promises).then($scope.loadData);
      }
    });

    $scope.changeStipend = function(row) {
      ScholarshipUtils.changeStipend(row.id, row.type.code, row.isOpen);
    };
  }
]);
