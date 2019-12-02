'use strict';

angular.module('hitsaOis').controller('PracticeApplicationStudentController', ['$scope', '$q', 'Classifier', 'QueryUtils', 'dialogService', 'FormUtils', 'message',
  function ($scope, $q, Classifier, QueryUtils, dialogService, FormUtils, message) {
    var baseUrl = '/practiceApplication';
    var clMapper = Classifier.valuemapper({
      status: 'PR_TAOTLUS'
    });

    function loadOpenAdmissions() {
      QueryUtils.endpoint(baseUrl + '/openAdmissions').query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.openAdmissions = result;
      });
    }

    function loadPassedAdmissions() {
      QueryUtils.endpoint(baseUrl + '/passedAdmissions').query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.passedAdmissions = result;
      });
    }

    $q.all(clMapper.promises).then(function () {
      loadOpenAdmissions();
      loadPassedAdmissions();
    });

    /**
     * True if:
     *    (Without `status` OR `status` is "Tühistatud")
     *    AND
     *    (
     *      (Not strict OR `places` is null)
     *      OR
     *      `submittedApplications` < `places`
     *    )
     */
    $scope.canApply = function (admission) {
      return (!admission.status || 'PR_TAOTLUS_C' === admission.status.code) && ((!admission.isStrict || admission.places === null) || admission.submittedApplications < admission.places);
    };

    $scope.apply = function (admission) {
      dialogService.showDialog('practiceApplication/practice.application.apply.dialog.html', function () {
      }, function (submittedDialogScope) {
        FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
          var ApplicationEndpoint = QueryUtils.endpoint('/practiceApplication/apply/' + admission.id);
          var applicationEndpoint = new ApplicationEndpoint(submittedDialogScope.application);
          applicationEndpoint.$save().then(function () {
            message.info('practiceApplication.messages.applicationSubmitted');
            loadOpenAdmissions();
          }).catch(angular.noop);
        });
      });
    };

    $scope.annul = function (admission) {
      dialogService.confirmDialog({prompt: 'practiceApplication.prompt.annulConfirm'}, function() {
        var ApplicationEndpoint = QueryUtils.endpoint('/practiceApplication/annul/' + admission.id);
        var applicationEndpoint = new ApplicationEndpoint();
        applicationEndpoint.$save().then(function () {
          message.info('practiceApplication.messages.applicationAnnulled');
          loadOpenAdmissions();
        }).catch(angular.noop);
      });
    };
  }
]).controller('PracticeApplicationSearchController', ['$q', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'FormUtils', '$route',
function ($q, $scope, dialogService, message, Classifier, QueryUtils, FormUtils, $route) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = '/practiceApplication';

  $scope.formState = {status: 'PR_TAOTLUS_E'};
  $scope.currentNavItem = $route.current.$$route.data.currentNavItem;
  var clMapper = Classifier.valuemapper({status: 'PR_TAOTLUS'});
  QueryUtils.createQueryForm($scope, baseUrl + '/applications', {order: 'p.lastname,p.firstname', status: $scope.formState.status}, clMapper.objectmapper);

  var refreshApplications = $scope.loadData;
  $scope.loadData = function() {
    refreshApplications();
    $scope.formState.status = $scope.criteria.status;
  };

  $scope.reject = function (application) {
    dialogService.showDialog('practiceApplication/practice.application.reject.dialog.html', function () {
    }, function (submittedDialogScope) {
      FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
        var ApplicationEndpoint = QueryUtils.endpoint('/practiceApplication/reject/' + application.id);
        var applicationEndpoint = new ApplicationEndpoint(submittedDialogScope.application);
        applicationEndpoint.$save().then(function () {
          message.info('practiceApplication.messages.applicationRejected');
          refreshApplications();
        }).catch(angular.noop);
      });
    });
  };

  $scope.accept = function (application) {
    dialogService.confirmDialog({prompt: 'practiceApplication.prompt.acceptConfirm'}, function() {
      var ApplicationEndpoint = QueryUtils.endpoint('/practiceApplication/accept/' + application.id);
      var applicationEndpoint = new ApplicationEndpoint();
      applicationEndpoint.$save().then(function () {
        message.info('practiceApplication.messages.applicationAccepted');
        refreshApplications();
      }).catch(angular.noop);
    });
  };
  
  $q.all(clMapper.promises).then($scope.loadData);
}
]).controller('PracticeApplicationPeriodSearchController', ['$scope', 'dialogService', 'message', 'QueryUtils', 'FormUtils', '$route', 'AuthService',
  function ($scope, dialogService, message, QueryUtils, FormUtils, $route, AuthService) {
    $scope.auth = $route.current.locals.auth;
    var baseUrl = '/practiceApplication';

    $scope.canEdit = $scope.auth.isAdmin() && AuthService.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAAVALDUS');
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;
    QueryUtils.createQueryForm($scope, baseUrl + '/applicationPeriods');
    angular.extend($scope.criteria, {order: 'e.\"name\"'});
    if (!$scope.criteria.validFrom && !$scope.criteria.validThru) {
      angular.extend($scope.criteria, {opened: true});
    }

    $scope.view = function(row) {
      if (!row) {
        return;
      }
      dialogService.showDialog('practiceEnterprise/practice.enterprise.admission.view.dialog.html', function (dialogScope) {
        dialogScope.header = 'enterprise.viewPeriod';
        QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id).search().$promise.then(function(response) {
          dialogScope.enterprise = response;
          dialogScope.enterprise.validFrom = new Date(response.validFrom);
          dialogScope.enterprise.validThru = new Date(response.validThru);
          dialogScope.enterprise.name = row.enterpriseName;
          dialogScope.enterprise.regCode = row.enterpriseRegCode;
        });
      }, null);
    };

    $scope.edit = function (row) {
      if (!row) {
        return;
      }
      dialogService.showDialog('practiceEnterprise/practice.enterprise.admission.add.dialog.html', function (dialogScope) {
        dialogScope.header = 'enterprise.changePeriod';
  
        dialogScope.addAllStudentGroups = function () {
          QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true}, function (studentGroups) {
            if (!angular.isArray(dialogScope.enterprise.studentGroups)) {
              dialogScope.enterprise.studentGroups = [];
            }
            dialogScope.enterprise.studentGroups = studentGroups;
          });
        };
  
        dialogScope.$watch('enterprise.studentGroup', function () {
          if (angular.isDefined(dialogScope.enterprise) && angular.isDefined(dialogScope.enterprise.studentGroup) && dialogScope.enterprise.studentGroup !== null) {
            dialogScope.addStudentGroup();
          }
        });
    
        dialogScope.addStudentGroup = function () {
          if (!angular.isArray(dialogScope.enterprise.studentGroups)) {
            dialogScope.enterprise.studentGroups = [];
          }
          if (dialogScope.enterprise.studentGroups.some(function (studentGroup) {
              return studentGroup.id === dialogScope.enterprise.studentGroup.id;
            })) {
            message.error('timetable.timetableEvent.error.duplicateStudentGroup');
            return;
          }
          dialogScope.enterprise.studentGroups.push(dialogScope.enterprise.studentGroup);
          dialogScope.enterprise.studentGroup = undefined;
        };
    
        dialogScope.deleteStudentGroup = function (studentGroup) {
          var index = dialogScope.enterprise.studentGroups.indexOf(studentGroup);
          if (index !== -1) {
            dialogScope.enterprise.studentGroups.splice(index, 1);
            dialogScope.dialogForm.$setDirty();
          }
        };
  
        dialogScope.delete = function () {
          dialogService.confirmDialog({ prompt: 'enterprise.admissionTab.deleteConfirm' }, function () {
            var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id);
            var enterpriseLocation = new LocationEndpoint();
            enterpriseLocation.$delete().then(function (response) {
              if (response.nameEt === null) {
                message.info('main.messages.delete.success');
                _loadData();
              } else {
                message.error(response.nameEt);
              }
            }).catch(angular.noop);
          });
        };
        QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id).search().$promise.then(function(response) {
          dialogScope.enterprise = response;
          dialogScope.enterprise.validFrom = new Date(response.validFrom);
          dialogScope.enterprise.validThru = new Date(response.validThru);
          dialogScope.enterprise.name = row.enterpriseName;
          dialogScope.enterprise.regCode = row.enterpriseRegCode;
        });

        dialogScope.validate = function () {
          if (dialogScope.enterprise.studentGroups === undefined || dialogScope.enterprise.studentGroups.length === 0) {
            message.error('practiceApplication.messages.atLeastOneStudentGroup');
            return;
          }
          dialogScope.submit();
        };
      }, function (submittedDialogScope) {
        FormUtils.withValidForm(submittedDialogScope.dialogForm, function() {
          var LocationEndpoint = QueryUtils.endpoint('/practiceEnterprise/admission/' + row.id);
          var locationEndPoint = new LocationEndpoint(submittedDialogScope.enterprise);
          locationEndPoint.$save().then(function () {
            message.info('main.messages.create.success');
            _loadData();
          }).catch(angular.noop);
        });
      });
    };

    var _loadData = $scope.loadData;
    $scope.loadData = function() {
      if (angular.isDefined($scope.searchForm)) {
        $scope.searchForm.$setSubmitted();
      }
      if (typeof $scope.criteria.validFrom === "string") {
        $scope.criteria.validFrom = new Date($scope.criteria.validFrom);
      }
      if (typeof $scope.criteria.validThru === "string") {
        $scope.criteria.validThru = new Date($scope.criteria.validThru);
      }
      var isValidDate = (!$scope.criteria.validFrom || !$scope.criteria.validThru) || $scope.criteria.validFrom <= $scope.criteria.validThru;
      if ((!$scope.searchForm || $scope.searchForm.$valid) && isValidDate) {
        _loadData();
      } else {
        message.error('main.messages.form-has-errors');
        return;
      }
    };
    $scope.loadData();
  }
]);
