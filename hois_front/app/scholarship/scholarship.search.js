'use strict';

angular.module('hitsaOis').controller('ScholarshipSearchController', ['$route', '$scope', '$q', 'AuthService', 'Classifier', 'ScholarshipUtils', 'QueryUtils', 'USER_ROLES',
  function ($route, $scope, $q, AuthService, Classifier, ScholarshipUtils, QueryUtils, USER_ROLES) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    var baseUrl = '/scholarships';
    $scope.auth = $route.current.locals.auth;
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

    if ($scope.scholarshipType === 'drGrant') {
      $scope.currentNavItem = 'scholarship.drStipend';
    } else if ($scope.scholarshipType === 'scholarship') {
      $scope.currentNavItem = 'scholarship.stipend';
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
]).controller('ScholarshipOthersController', ['$scope', 'QueryUtils', 'message', '$route',
  function ($scope, QueryUtils, message, $route) {
    var baseUrl = '/scholarships/noApplication';
    var endpoint = new QueryUtils.endpoint(baseUrl);
    $scope.auth = $route.current.locals.auth;
    $scope.currentNavItem = 'scholarship.others';
    $scope.save = saveAllowedEhisScholarshipTypes;

    $scope.ehisScholarships = ['EHIS_STIPENDIUM_11', 'EHIS_STIPENDIUM_12', 'EHIS_STIPENDIUM_13', 'EHIS_STIPENDIUM_15',
                               'EHIS_STIPENDIUM_16', 'EHIS_STIPENDIUM_6', 'EHIS_STIPENDIUM_9'];

    $scope.allowedEhisTypes = {};
    $scope.promise = endpoint.get(mapResult).$promise;

    function saveAllowedEhisScholarshipTypes() {
      var req = {
        allowedEhisTypes: []
      };
      for (var code in $scope.allowedEhisTypes) {
        if ($scope.allowedEhisTypes[code]) {
          req.allowedEhisTypes.push(code);
        }
      }
      $scope.promise = endpoint.update(req, function (result) {
        message.info('main.messages.update.success');
        mapResult(result);
      }).$promise;
    }

    function mapResult(result) {
      $scope.allowedEhisTypes = {};
      if (angular.isArray(result.allowedEhisTypes)) {
        for (var i = 0; i < result.allowedEhisTypes.length; i++) {
          $scope.allowedEhisTypes[result.allowedEhisTypes[i]] = true;
        }
      }
    }
  }
]);
