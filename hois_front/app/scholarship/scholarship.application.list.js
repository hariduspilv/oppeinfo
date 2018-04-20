'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationController', ['Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', '$q', '$sessionStorage', 'AuthService', 'USER_ROLES',
  function (Classifier, $scope, $location, message, QueryUtils, $route, DataUtils, $q, $sessionStorage, AuthService, USER_ROLES) {
    var baseUrl = '/scholarships';
    $scope.criteria = {};
    $scope.formState = {};
    $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    var stipend = $route.current.locals.params.stipend;
    $scope.canManage = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);

    $scope.selected = {};

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    var clMapper = Classifier.valuemapper({
      status: 'STIPTOETUS_STAATUS',
      type: 'STIPTOETUS',
      compensationFrequency: 'STIPTOETUS_HYVITAMINE',
      compensationReason: 'STIPTOETUS_HYVITAMINE_POHJUS'
    });

    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $q.all(promises).then(function () {
      if ($scope.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      if (!('_menu' in $route.current.params)) {
        $scope.fromStorage = function (key) {
          return JSON.parse($sessionStorage[key] || '{}');
        };
        var storedCriteria = $scope.fromStorage($route.current.originalPath);
        if (angular.isNumber(storedCriteria.page)) {
          storedCriteria.page = storedCriteria.page + 1;
        }
        angular.extend($scope.criteria, storedCriteria);
      }
    });

    $scope.toStorage = function(key, criteria) {
      $sessionStorage[key] = JSON.stringify(criteria);
    };

    $scope.reloadTable = function () {
      $scope.applicationSearchForm.$setSubmitted();
      if (!$scope.applicationSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      QueryUtils.endpoint(baseUrl + '/applications').search($scope.criteria, function (result) {
        if (angular.isArray(result.applications)) {
          if (result.applications.length < 1) {
            message.info('main.messages.error.notFound');
            $scope.applications = [];
            $scope.allowedCount = 0;
          } else {
            $scope.applications = clMapper.objectmapper(result.applications);
            $scope.allowedCount = result.allowedCount;
          }
        } else {
          message.info('main.messages.error.notFound');
          $scope.applications = [];
          $scope.allowedCount = 0;
        }
        $scope.submittedType = $scope.criteria.type;
        $scope.toStorage($route.current.originalPath, $scope.criteria);
      });
    };

    $scope.updateAllApplicationCheckBoxes = function (value) {
      $scope.applications.forEach(function (app, index) {
        if (index < $scope.allowedCount) {
          $scope.selected[app.id] = value;
        }
      });
    };

    $scope.clearCriteria = function () {
      $scope.criteria = {};
    };

    $scope.isNumber = angular.isNumber;

    function chosenApplications() {
      return $scope.applications.filter(function (it) {
        return $scope.selected[it.id];
      }).map(function (it) {
        return it.id;
      });
    }

    function allApplications() {
      return $scope.applications.map(function (it) {
        return it.id;
      });
    }

    $scope.accept = function () {
      var applications = chosenApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/acceptApplications').put(applications, $scope.reloadTable);
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.annul = function () {
      var applications = chosenApplications();
      if (applications.length > 0) {
        $location.path('/scholarships/applications/annul').search({
          ids: applications,
          stipend: stipend
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.reject = function () {
      var applications = chosenApplications();
      if (applications.length > 0) {
        $location.path('/scholarships/applications/reject').search({
          ids: applications,
          stipend: stipend
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.refreshResults = function () {
      var applications = allApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/refreshResults').put(applications, $scope.reloadTable);
      }
    };

    $scope.checkComplies = function () {
      var applications = allApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/checkComplies').save(applications, function (result) {
          $scope.applications.forEach(function (application) {
            application.nonCompliant = !result[application.id];
          });
        });
      }
    };

    var previousType;
    $scope.resetCurriculum = function () {
      if (previousType === 'STIPTOETUS_POHI') {
        $scope.criteria.curriculum = [];
      }
      previousType = $scope.criteria.type;
    };
  }
]).controller('ScholarshipRejectionController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'ArrayUtils',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, ArrayUtils) {
    var baseUrl = '/scholarships';
    //if this form is currently being used for stipend or grant
    $scope.stipend = $route.current.params.stipend;
    QueryUtils.endpoint(baseUrl + '/studentProfilesRejection').query({
      id: $route.current.params.ids
    }, function (result) {
      if (angular.isArray(result)) {
        if (result.length < 1) {
          message.info('main.messages.error.notFound');
        } else {
          $scope.rejections = result;
        }
      }
    });

    $scope.reject = function () {
      QueryUtils.endpoint(baseUrl + '/rejectApplications').put({
        applications: $scope.rejections
      }, function () {
        message.info('main.messages.update.success');
        $location.path('/scholarships/applications/' + ($scope.stipend ? 'scholarships' : 'grants'));
      });
    };

    $scope.annul = function () {
      QueryUtils.endpoint(baseUrl + '/annulApplications').put({
        applications: $scope.rejections
      }, function () {
        message.info('main.messages.update.success');
        $location.path('/scholarships/applications/' + ($scope.stipend ? 'scholarships' : 'grants'));
      });
    };

    $scope.removeFromArray = ArrayUtils.remove;
  }
]);
