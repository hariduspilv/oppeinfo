'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route) {
  var ApplicationEndpoint = QueryUtils.endpoint('/application');

  $scope.application = {
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    $scope.application = entity;
  }

  $scope.$watch('application.student', function(student) {
    if (angular.isObject(student)) {
      if (['AVALDUS_LIIK_FINM'].indexOf($scope.application.type) !== -1) {
        QueryUtils.endpoint('/students').get({id: student.id}).$promise
          .then(function(student) {
              $scope.application.oldFinancialSource = student.fin;
              $scope.application.oldFinancialSourceSpecification = student.finSpecific;
              $scope.application.newFinancialSource = student.fin === "FINALLIKAS_RE" ? "FINALLIKAS_RE" : "FINALLIKAS_REV";
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

  function afterSave(savedApplication) {
    angular.extend($scope.application, savedApplication);
  }

  $scope.save = function() {
    var application = new ApplicationEndpoint($scope.application);
    if (angular.isDefined($scope.application.id)) {
      application.$update().then(function() {
        message.info('main.messages.create.success');
        afterSave(application);
      });
    } else {
        application.$save().then(function() {
        message.info('main.messages.create.success');
        $location.path('/application/'+application.id+'/edit');
        afterSave(application);
      });
    }
  };
});
