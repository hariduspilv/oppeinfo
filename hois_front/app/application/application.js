'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route, DataUtils) {
  var ApplicationsEndpoint = QueryUtils.endpoint('/applications');

  function entityToForm(savedApplication) {
    DataUtils.convertStringToDates(savedApplication, ["startDate", "endDate"]);
    angular.extend($scope.application, savedApplication);
  }
  $scope.applicationEditView = false;
  $scope.application = {
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  };

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.$watch('application.student', function(student) {
    if (angular.isObject(student)) {
      var StudentEndpoint = QueryUtils.endpoint('/students');
      if (['AVALDUS_LIIK_FINM', 'AVALDUS_LIIK_OVORM'].indexOf($scope.application.type) !== -1) {
        StudentEndpoint.get({id: student.id}, function(result) {
          if ($scope.application.type === 'AVALDUS_LIIK_FINM') {
            $scope.application.oldFinancialSource = result.fin;
            $scope.application.oldFinancialSourceSpecification = result.finSpecific;
            $scope.application.newFinancialSource = result.fin === "FINALLIKAS_RE" ? "FINALLIKAS_RE" : "FINALLIKAS_REV";
          } else if ($scope.application.type === 'AVALDUS_LIIK_OVORM') {
            $scope.application.oldStudyForm = result.studyForm;
          }
          $scope.applicationEditView = true;
        });
      } else if($scope.application.type === 'AVALDUS_LIIK_AKAD') {
        $scope.application.period = true;
        QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
          $scope.studyPeriods = result;
          $scope.applicationEditView = true;
        });
      } else if($scope.application.type === 'AVALDUS_LIIK_AKADK') {
        ApplicationsEndpoint.get({type: 'AVALDUS_LIIK_AKAD', student: $scope.application.student.id, order: "id"}, function(result) {
          if (result.numberOfElements === 0) {
            $scope.applicationEditView = false;
            message.error('application.messages.academicLeaveApplicationNotfound');
          } else {
            $scope.academicLeaveApplication = result.content[0];
            QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
              $scope.studyPeriods = result;
              $scope.applicationEditView = true;
            });
          }
          //TODO: vaja on leida viimane akadeemilise puhkuse avaldus, millel ei ole tühistamise avaldust
        });
      }
    }
  });

  $scope.openAddFileDialog = function() {
    dialogService.showDialog('application/file.add.dialog.html', null, function(submittedDialogScope) {
        var data = submittedDialogScope.data;
        oisFileService.getFromLfFile(data.file[0], function(file) {
          data.oisFile = file;
          $scope.application.files.push(data);
        });
      });
  };

  $scope.getUrl = function(oisFile) {
    return oisFileService.getFileUrl(oisFile);
  };

  $scope.saveAndSubmit = function() {
    $scope.application.status = 'AVALDUS_STAATUS_ESIT';
    $scope.save();
  };

  $scope.save = function() {
    $scope.applicationForm.$setSubmitted();
    if($scope.applicationForm.$valid) {
      var application = new ApplicationsEndpoint($scope.application);
      if (angular.isDefined($scope.application.id)) {
        application.$update().then(function() {
          message.info('main.messages.create.success');
          entityToForm(application);
        });
      } else {
          application.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path('/applications/'+application.id+'/edit');
          entityToForm(application);
        });
      }
    } else {
        console.log($scope.applicationForm.$error);
    }
  };
});
