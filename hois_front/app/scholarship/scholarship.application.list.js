'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationController', ['Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', '$q', '$sessionStorage', 'AuthService', 'USER_ROLES',
  function (Classifier, $scope, $location, message, QueryUtils, $route, DataUtils, $q, $sessionStorage, AuthService, USER_ROLES) {
    var baseUrl = '/scholarships';
    $scope.criteria = {};
    $scope.formState = {};
    $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    $scope.scholarshipType = $route.current.locals.params.scholarshipType;
    var stipend = $route.current.locals.params.stipend;
    $scope.auth = $route.current.locals.auth;
    $scope.canManage = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    $scope.isStudentGroupTeacher = $scope.auth.isTeacher() && $scope.auth.teacherGroupIds.length > 0;

    $scope.applicationsTable = {
      showSelect: true
    };
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

    angular.element(document).ready(function() {
      $scope.applicationSearchForm.$setSubmitted();
        if ($scope.applicationSearchForm.$valid) {
          $scope.reloadTable();
        } else {
          window.setTimeout(function() {
            $scope.applicationSearchForm.$setSubmitted();
            if ($scope.applicationSearchForm.$valid) {
              $scope.reloadTable();
            }
          }, 500);
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
        if (index < $scope.allowedCount || !angular.isNumber($scope.allowedCount)) {
          if (app.needsConfirm && $scope.auth.isTeacher()) {
            $scope.selected[app.id] = value;
          } else if (!$scope.auth.isTeacher() && !app.hasDirective){
            $scope.selected[app.id] = value;
          }
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
        $location.path('/scholarships/applications/' + $scope.scholarshipType + '/annul').search({
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
        $location.path('/scholarships/applications/' + $scope.scholarshipType + '/reject').search({
          ids: applications,
          stipend: stipend
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.teacherConfirm = function (action) {
      var applications = chosenApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/teacherConfirmApplications/' + action).put(applications, $scope.reloadTable);
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

    $scope.committeeDecision = function () {
      var applications = chosenApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/decision/canCreate').get({
          ids: applications
        }, function (result) {
          if (result.canCreate) {
            $location.path('/scholarships/decision/' + $scope.scholarshipType).search({
              ids: applications,
              stipend: stipend
            });
          } else {
            message.error('stipend.messages.error.cannotCreateDecision');
          }
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
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
    $scope.scholarshipType = $route.current.params.type;
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
        $location.path('/scholarships/applications/' + $scope.scholarshipType);
      });
    };

    $scope.annul = function () {
      QueryUtils.endpoint(baseUrl + '/annulApplications').put({
        applications: $scope.rejections
      }, function () {
        message.info('main.messages.update.success');
        $location.path('/scholarships/applications/' + $scope.scholarshipType);
      });
    };

    $scope.removeFromArray = ArrayUtils.remove;
  }
]).controller('ScholarshipDecisionController', ['$scope', '$location', '$q', 'message', 'QueryUtils', '$route', 'Classifier',
function ($scope, $location, $q, message, QueryUtils, $route, Classifier) {
  var baseUrl = '/scholarships';
  $scope.scholarshipType = $route.current.params.type;
  $scope.formState = {
    committees: QueryUtils.endpoint(baseUrl + "/committees").query()
  };
  var clMapper = Classifier.valuemapper({
    status: 'STIPTOETUS_STAATUS',
    type: 'STIPTOETUS',
    compensationFrequency: 'STIPTOETUS_HYVITAMINE',
    compensationReason: 'STIPTOETUS_HYVITAMINE_POHJUS'
  });
  QueryUtils.endpoint(baseUrl + '/decision').get({
    ids: $route.current.params.ids
  }, function (result) {
    $scope.formState.committee = result.committeeId;
    $scope.selectedCommitteeChanged();
    $scope.submittedType = result.applications[0].type;
    $q.all(clMapper.promises).then(function () {
      $scope.applications = clMapper.objectmapper(result.applications);
    });
  });
  function getPresentCommitteeMembers(committeeMembers) {
    if (committeeMembers) {
      return committeeMembers.filter(function (member) {
        return member.isPresent;
      }).map(function (member) {
        return member.id;
      });
    }
    return [];
  }

  $scope.selectedCommitteeChanged = function () {
    if ($scope.formState.committee) {
      QueryUtils.endpoint("/committees").get({ id: $scope.formState.committee }, function (committee) {
        $scope.formState.committeeMembers = committee.members;
      });
    }
  };

  $scope.decide = function () {
    $scope.decisionForm.$setSubmitted();
    if (!$scope.decisionForm.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }
    angular.extend($scope.decision, {
      presentCommitteeMembers: getPresentCommitteeMembers($scope.formState.committeeMembers),
      applicationIds: $route.current.params.ids
    });
    QueryUtils.endpoint(baseUrl + '/decide').save($scope.decision, function () {
      message.info('main.messages.update.success');
      $location.path('/scholarships/applications/' + $scope.scholarshipType);
    });
  };
}
]).controller('ScholarshipDecisionViewController', ['$scope', '$location', '$q', 'message', 'QueryUtils', '$route', 'Classifier', 'dialogService',
function ($scope, $location, $q, message, QueryUtils, $route, Classifier, dialogService) {
  var baseUrl = '/scholarships/decision';
  $scope.auth = $route.current.locals.auth;
  $scope.scholarshipType = $route.current.params.type;
  var id = $route.current.params.id;

  var Endpoint = QueryUtils.endpoint(baseUrl);

  var clMapper = Classifier.valuemapper({
    status: 'STIPTOETUS_STAATUS',
    type: 'STIPTOETUS',
    compensationFrequency: 'STIPTOETUS_HYVITAMINE',
    compensationReason: 'STIPTOETUS_HYVITAMINE_POHJUS'
  });

  $scope.decision = Endpoint.get({id: id}, function (result) {
    $scope.decision.committee = result.committeeId;
    $scope.submittedType = result.applications[0].type;
    $q.all(clMapper.promises).then(function () {
      $scope.applications = clMapper.objectmapper(result.applications);
    });
    QueryUtils.endpoint("/committees").get({ id: $scope.decision.committee }, function (committee) {
      $scope.decision.committeeMembers = committee.members;
      if (result.presentCommitteeMembers) {
        $scope.decision.committeeMembers.forEach(function (it) {
          if (result.presentCommitteeMembers.indexOf(it.id) !== -1) {
            it.isPresent = true;
          }
        });
      }
    });
  });

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'stipend.decision.deleteconfirm' }, function () {
      $scope.decision.$delete().then(function() {
        message.info('main.messages.delete.success');
        $scope.back('/scholarships/applications/' + $scope.scholarshipType);
      }).catch(angular.noop);
    });
  };
}
]);
