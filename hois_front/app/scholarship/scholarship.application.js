'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationRankingController', ['$location', '$route', '$scope', '$sessionStorage', '$q', 'AuthService', 'Classifier', 'USER_ROLES', 'QueryUtils', 'dialogService', 'message',
  function ($location, $route, $scope, $sessionStorage, $q, AuthService, Classifier, USER_ROLES, QueryUtils, dialogService, message) {
    var baseUrl = '/scholarships';
    $scope.currentNavItem = 'ranking';
    $scope.criteria = {};
    $scope.formState = {};
    $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
    $scope.scholarshipType = $route.current.locals.params.scholarshipType;
    var stipend = $route.current.locals.params.stipend;
    $scope.auth = $route.current.locals.auth;
    $scope.canManage = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    $scope.isStudentGroupTeacher = $scope.auth.isTeacher() && $scope.auth.teacherGroupIds.length > 0;

    $scope.applicationsTable = {
      showSelect: !$scope.auth.isLeadingTeacher()
    };
    $scope.selected = {};

    var clMapper = Classifier.valuemapper({
      status: 'STIPTOETUS_STAATUS',
      type: 'STIPTOETUS',
      compensationFrequency: 'STIPTOETUS_HYVITAMINE',
      compensationReason: 'STIPTOETUS_HYVITAMINE_POHJUS'
    });

    var promises = clMapper.promises;

    $q.all(promises).then(function () {
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
      QueryUtils.endpoint(baseUrl + '/applications/ranking').search($scope.criteria, function (result) {
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
        return $scope.selected[it.id] && !it.hasDirective;
      }).map(function (it) {
        return it.id;
      });
    }

    function allApplicationsWithoutDirective() {
      return $scope.applications.filter(function (it) {
        return !it.hasDirective;
      }).map(function (it) {
        return it.id;
      });
    }

    $scope.accept = function () {
      var applications = chosenApplications();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/acceptApplications').put(applications, $scope.reloadTable).$promise.then(function () {
          message.info('stipend.messages.applicationAccepted');
        });
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
        QueryUtils.endpoint(baseUrl + '/teacherConfirmApplications/' + action)
          .put(applications, $scope.reloadTable).$promise.then(function () {
            if (action === 'yes') {
              message.info('stipend.messages.applicationConfirmed');
            } else if (action === 'no') {
              message.info('stipend.messages.applicationUnconfirmed');
            }
          });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.refreshResults = function () {
      var applications = allApplicationsWithoutDirective();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/refreshResults').put(applications, $scope.reloadTable).$promise.then(function () {
          message.info('stipend.messages.resultsRefreshed');
        });
      } else {
        message.error('stipend.messages.error.noApplicationsWithoutDirectives');
      }
    };

    $scope.checkComplies = function () {
      var applications = allApplicationsWithoutDirective();
      if (applications.length > 0) {
        QueryUtils.endpoint(baseUrl + '/checkComplies').save(applications, function (result) {
          $scope.applications.forEach(function (application) {
            if (result[application.id]) {
              application.nonCompliant = result[application.id].fullyComplies === false;
              application.termCompliance = result[application.id];
            }
          });
        }).$promise.then(function () {
          message.info('stipend.messages.compliesChecked');
        });
      } else {
        message.error('stipend.messages.error.noApplicationsWithoutDirectives');
      }
    };

    $scope.scholarshipTermCompliances = function (application) {
      dialogService.showDialog('scholarship/templates/scholarship.term.compliance.dialog.html', function (dialogScope) {
        dialogScope.stipend = application;
        dialogScope.stipend.nameEt = application.termNameEt;
      });
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
]).controller('ScholarshipRejectionController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'ArrayUtils', 'FormUtils',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, ArrayUtils, FormUtils) {
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
      FormUtils.withValidForm($scope.rejectForm, function() {
        QueryUtils.endpoint(baseUrl + '/rejectApplications').put({
          applications: $scope.rejections
        }, function () {
          message.info('stipend.messages.applicationRejected');
          $location.path('/scholarships/applications/ranking/' + $scope.scholarshipType);
        });
      });
    };

    $scope.annul = function () {
      QueryUtils.endpoint(baseUrl + '/annulApplications').put({
        applications: $scope.rejections
      }, function () {
        message.info('stipend.messages.applicationAnnulled');
        $location.path('/scholarships/applications/ranking/' + $scope.scholarshipType);
      });
    };

    $scope.removeFromArray = ArrayUtils.remove;
  }
]).controller('ScholarshipDecisionController', ['$scope', '$location', '$q', 'message', 'QueryUtils', '$route', 'Classifier',
function ($scope, $location, $q, message, QueryUtils, $route, Classifier) {
  var baseUrl = '/scholarships';
  $scope.scholarshipType = $route.current.params.type;
  $scope.formState = {};
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
    $scope.formState.committees = QueryUtils.endpoint(baseUrl + "/committees").query({
      validDate: new Date().withoutTime(),
      id: result.committeeId
    });
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
  function getApplicationIds(ids) {
    return angular.isArray(ids) ? ids : [ids];
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
      applicationIds: getApplicationIds($route.current.params.ids)
    });
    QueryUtils.endpoint(baseUrl + '/decide').save($scope.decision, function () {
      message.info('main.messages.update.success');
      $location.path('/scholarships/applications/ranking/' + $scope.scholarshipType);
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
        $scope.back('/scholarships/applications/ranking/' + $scope.scholarshipType);
      }).catch(angular.noop);
    });
  };
}
]);
