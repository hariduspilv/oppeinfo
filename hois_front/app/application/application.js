'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route, DataUtils, ArrayUtils) {
  var ApplicationsEndpoint = QueryUtils.endpoint('/applications');

  $scope.removeFromArray = ArrayUtils.remove;
  $scope.auth = $route.current.locals.auth;

  function entityToForm(savedApplication) {
    DataUtils.convertStringToDates(savedApplication, ["startDate", "endDate"]);
    if (angular.isArray(savedApplication.plannedSubjects)) {
      savedApplication.plannedSubjects.forEach(function(plannedSubject) {
        if (angular.isArray(plannedSubject.equivalents)) {
          plannedSubject.subjectsSelected = plannedSubject.equivalents.map(function(it){return it.subject;});
        }
      });
    }
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
    //TODO: valid to true
    CurriculumVersionsEndpoint.get({valid: false}, function(result) {
      $scope.curriculumVersions = result.content;
    });
    $scope.application.oldStudyForm = student.studyForm;
    $scope.application.oldCurriculumVersion = student.curriculumVersion;
  }

  $scope.studyPeriodView = function(studyPeriodId) {
    if (angular.isArray($scope.studyPeriods) && angular.isNumber(studyPeriodId)) {
      for(var i = 0; i < $scope.studyPeriods.length; i++) {
        if ($scope.studyPeriods[i].id === studyPeriodId) {
          return $scope.currentLanguageNameField($scope.studyPeriods[i]);
        }
      }
    }
  };

  $scope.subjectsListView = function(plannedSubject) {
    if (angular.isDefined(plannedSubject) && angular.isArray(plannedSubject.subjectsSelected)) {
      var subjects = [];
      $scope.studentSubjects.forEach(function(it) {
        if (plannedSubject.subjectsSelected.indexOf(it.id) !== -1) {
          subjects.push($scope.currentLanguageNameField(it));
        }
      });
      return subjects.join(", ");
    } else {
      return "";
    }
  };

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
      var AcademicLeaveApplicationRevocationsEndpoint = QueryUtils.endpoint('/applications/' + academicLeaveApplication.id + '/validAcademicLeaveRevocation');
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

  function abroadStudentApplication() {
    $scope.isAbroadChanged = function() {
      if ($scope.application.isAbroad) {
        $scope.application.ehisSchool = undefined;
      } else {
        $scope.application.abroadSchool = undefined;
        $scope.application.country = undefined;
      }
    };

    $scope.addPlannedSubjectRow = function() {
      if (!angular.isArray($scope.application.plannedSubjects)) {
        $scope.application.plannedSubjects = [];
      }
      $scope.application.plannedSubjects.push({equivalents: []});
    };

    $scope.subjectsSelected = function(plannedSubject) {
      if (angular.isArray(plannedSubject.subjectsSelected)) {
        var newEquivalents = [];
        plannedSubject.subjectsSelected.forEach(function(subjectId) {
          var found = false;
          for(var i = 0; i < plannedSubject.equivalents.length && !found; i++) {
            var equivalent = plannedSubject.equivalents[i];
            if (equivalent.subject === subjectId) {
              newEquivalents.push(equivalent);
              found = true;
            }
          }
          if (!found) {
            newEquivalents.push({subject: subjectId});
          }
        });
        plannedSubject.equivalents = newEquivalents;
      } else {
        plannedSubject.equivalents = [];
      }
    };

    $scope.studentEhisSchool = $route.current.locals.auth.school.ehisSchool;
    if (!isEdit()) {
      $scope.application.isPeriod = true;
      $scope.application.isAbroad = false;
    }
    QueryUtils.endpoint('/students/' + $scope.application.student.id + '/subjects').query({}, function(result) {
      $scope.studentSubjects = result;
    });
    QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
      $scope.studyPeriods = result;
    });

    $scope.applicationEditView = true;
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
        var AcademicLeaveApplicationsEndpoint = QueryUtils.endpoint('/applications/student/'+$scope.application.student.id+'/validAcademicLeave');
        AcademicLeaveApplicationsEndpoint.get({}, function(academicLeaveApplication) {
          var hasAcademicLeaveApplication = angular.isNumber(academicLeaveApplication.id) && academicLeaveApplication.id > 0;
          if($scope.application.type === 'AVALDUS_LIIK_AKAD') {
            academicLeave(hasAcademicLeaveApplication);
          } else if ($scope.application.type === 'AVALDUS_LIIK_AKADK') {
            academicLeaveRevocation(hasAcademicLeaveApplication, academicLeaveApplication);
          }
        });
      } else if ($scope.application.type === 'AVALDUS_LIIK_VALIS') {
        abroadStudentApplication();
        $scope.applicationEditView = true;
      }
      else {
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

  $scope.isStudentRepresentative = function() {
    return angular.isDefined($scope.auth.student) && angular.isDefined($scope.application.student) &&
      $scope.auth.student === $scope.application.student.id && $scope.auth.isParent();
  };

  $scope.submit = function() {
    QueryUtils.endpoint('/applications/'+$scope.application.id+'/submit/').put({}, function(response) {
      message.info('application.messages.submitted');
      entityToForm(response);
    });
  };

  $scope.reject = function() {
    dialogService.showDialog('application/reject.dialog.html', null, function(submittedDialogScope) {
      QueryUtils.endpoint('/applications/'+$scope.application.id+'/reject/').put({reason: submittedDialogScope.rejectReason}, function(response) {
        message.info('application.messages.rejected');
        entityToForm(response);
      });
    });
  };

  $scope.save = function() {
    $scope.applicationForm.$setSubmitted();
    if($scope.applicationForm.$valid) {
      var application = new ApplicationsEndpoint($scope.application);
      if (angular.isDefined($scope.application.id)) {
        application.$update().then(function() {
          message.info('main.messages.create.success');
          entityToForm(application);
          $scope.applicationForm.$setPristine();
        });
      } else {
          application.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path('/applications/'+application.id+'/edit');
        });
      }
    } else {
        console.log($scope.applicationForm.$error);
    }
  };

  $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'application.deleteconfirm'}, function() {
        var application = new ApplicationsEndpoint($scope.application);
        application.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/applications/search');
        });
      });
    };
});
