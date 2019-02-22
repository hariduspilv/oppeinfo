'use strict';

angular.module('hitsaOis').controller('PracticeApplicationStudentController', ['$route', '$scope', '$q', 'Classifier', 'QueryUtils', 'dialogService', 'FormUtils', 'message',
  function ($route, $scope, $q, Classifier, QueryUtils, dialogService, FormUtils, message) {
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
]).controller('PracticeApplicationSearchController', ['$q', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'FormUtils',
function ($q, $scope, dialogService, message, Classifier, QueryUtils, FormUtils) {
  var baseUrl = '/practiceApplication';

  $scope.formState = {status: 'PR_TAOTLUS_E'};
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
]);
