'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route, DataUtils, ArrayUtils) {
  var ApplicationsEndpoint = QueryUtils.endpoint('/applications');
  $scope.removeFromArray = ArrayUtils.remove;

  function entityToForm(savedApplication) {
    DataUtils.convertStringToDates(savedApplication, ["startDate", "endDate"]);
    angular.extend($scope.application, savedApplication);
  }

  function isEdit() {
    return angular.isNumber($scope.application.id);
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

  function financialSourceChange(student) {
    $scope.application.oldFin = student.fin;
    $scope.application.oldFinSpecific = student.finSpecific;
    $scope.application.newFin = student.fin === "FINALLIKAS_RE" ? "FINALLIKAS_REV" : "FINALLIKAS_RE";
  }

  function curriculumVersionChange(student) {
    var CurriculumVersionsEndpoint = QueryUtils.endpoint('/autocomplete/curriculumversions');
    CurriculumVersionsEndpoint.get({valid: true}, function(result) {
      $scope.curriculumVersions = result.content;
    });
    $scope.application.oldStudyForm = student.studyForm;
    $scope.application.oldCurriculumVersion = student.curriculumVersion;
  }

  function academicLeave(hasAcademicLeaveApplication) {
    if (!isEdit() && hasAcademicLeaveApplication) {
        $scope.applicationEditView = false;
        message.error('application.messages.validAcademicLeaveApplicationExists');
      } else {
        if (!isEdit()) {
          $scope.application.isPeriod = true;
        }
        QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
          $scope.studyPeriods = result;
          $scope.applicationEditView = true;
        });
      }
  }

  function academicLeaveRevocation(hasAcademicLeaveApplication, academicLeaveApplication) {
    if (!hasAcademicLeaveApplication) {
      $scope.applicationEditView = false;
      message.error('application.messages.academicLeaveApplicationNotfound');
    } else {
      var AcademicLeaveApplicationRevocationsEndpoint = QueryUtils.endpoint('/applications/' + academicLeaveApplication.id + '/academicLeaveRevocation');
      AcademicLeaveApplicationRevocationsEndpoint.get({}, function(revocation) {
        var hasAcademicLeaveRevocationApplication = angular.isNumber(revocation.id) && revocation.id > 0;
        if (!isEdit() && hasAcademicLeaveRevocationApplication) {
          $scope.applicationEditView = false;
          message.error('application.messages.validAcademicLeaveRevocationApplicationExists');
        } else {
          DataUtils.convertStringToDates(academicLeaveApplication, ["startDate", "endDate"]);
          $scope.academicLeaveApplication = academicLeaveApplication;
          $scope.application.academicApplication = academicLeaveApplication.id;
          QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
            $scope.studyPeriods = result;
            $scope.applicationEditView = true;
          });
        }
      });
    }
  }


  $scope.$watch('application.student', function(student) {
    if (angular.isObject(student)) {
      if (['AVALDUS_LIIK_FINM', 'AVALDUS_LIIK_OVORM', 'AVALDUS_LIIK_OKAVA'].indexOf($scope.application.type) !== -1) {
        var StudentEndpoint = QueryUtils.endpoint('/students');
        StudentEndpoint.get({id: student.id}, function(student) {
          if ($scope.application.type === 'AVALDUS_LIIK_FINM') {
            financialSourceChange(student);
          } else if ($scope.application.type === 'AVALDUS_LIIK_OVORM') {
            $scope.application.oldStudyForm = student.studyForm;
          }
          else if ($scope.application.type === 'AVALDUS_LIIK_OKAVA') {
            curriculumVersionChange(student);
          }
          $scope.applicationEditView = true;
        });
      } else if(['AVALDUS_LIIK_AKAD', 'AVALDUS_LIIK_AKADK'].indexOf($scope.application.type) !== -1) {
        var AcademicLeaveApplicationsEndpoint = QueryUtils.endpoint('/applications/academicLeave');
        AcademicLeaveApplicationsEndpoint.get({student: $scope.application.student.id}, function(academicLeaveApplication) {
          var hasAcademicLeaveApplication = angular.isNumber(academicLeaveApplication.id) && academicLeaveApplication.id > 0;
          if($scope.application.type === 'AVALDUS_LIIK_AKAD') {
            academicLeave(hasAcademicLeaveApplication);
          } else if ($scope.application.type === 'AVALDUS_LIIK_AKADK') {
            academicLeaveRevocation(hasAcademicLeaveApplication, academicLeaveApplication);
          }
        });
      } else {
        $scope.applicationEditView = true;
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
