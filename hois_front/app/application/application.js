'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route, DataUtils, ArrayUtils, $q, Curriculum) {
  var ApplicationsEndpoint = QueryUtils.endpoint('/applications');

  $scope.removeFromArray = ArrayUtils.remove;
  $scope.auth = $route.current.locals.auth;
  $scope.isCreate = $route.current.locals.isCreate;
  $scope.now = new Date();

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


  $scope.applicationEditView = false;
  $scope.application = {
    files: [],
    status: 'AVALDUS_STAATUS_KOOST'
  };

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
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
    if (angular.isDefined(plannedSubject) && angular.isArray(plannedSubject.subjectsSelected) && angular.isArray($scope.studentSubjects)) {
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


  function applicationFinm(student, loadFormDeferred) {
    $scope.application.oldFin = student.fin;
    $scope.application.oldFinSpecific = student.finSpecific;
    $scope.application.newFin = student.fin === "FINALLIKAS_RE" ? "FINALLIKAS_REV" : "FINALLIKAS_RE";
    loadFormDeferred.resolve();
  }

  function applicationOvorm(student, loadFormDeferred) {
    $scope.application.oldStudyForm = student.studyForm;
    loadFormDeferred.resolve();
  }

  function applicationOkava(student, loadFormDeferred) {
    //TODO: valid to true
    Curriculum.queryVersions({valid: false}).$promise.then(function(result) {
      $scope.curriculumVersions = result.filter(function(it) { return it.id !== student.curriculumVersion.id;});
      loadFormDeferred.resolve();
    });
    $scope.application.oldStudyForm = student.studyForm;
    $scope.application.oldCurriculumVersion = student.curriculumVersion;
  }

  function applicationAkad(loadFormDeferred) {
        if ($scope.isCreate === true) {
          $scope.application.isPeriod = true;
        }
        QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
          $scope.studyPeriods = result;
          loadFormDeferred.resolve();
        });
  }

  function applicationAkadk(loadFormDeferred, academicLeaveApplication) {
    if ($scope.isCreate === true) {
      if (!angular.isObject(academicLeaveApplication) || !angular.isNumber(academicLeaveApplication.id)) {
        loadFormDeferred.reject('application.messages.academicLeaveApplicationNotfound');
      } else {
        DataUtils.convertStringToDates(academicLeaveApplication, ["startDate", "endDate"]);
        $scope.application.academicApplication = academicLeaveApplication;
      }
    }

    QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
      $scope.studyPeriods = result;
      loadFormDeferred.resolve();
    });
  }

  function applicationValis(loadFormDeferred) {
    $scope.isAbroadChanged = function() {
      if ($scope.application.isAbroad) {
        $scope.application.ehisSchool = undefined;
      } else {
        $scope.application.abroadSchool = undefined;
        $scope.application.country = undefined;
      }
    };

    $scope.plannedSubjectLenght = 0;
    $scope.$watchCollection('application.plannedSubjects', function(val) {
      if (angular.isArray(val)) {
        $scope.plannedSubjectLenght = val.length;
      } else {
        $scope.plannedSubjectLenght = 0;
      }
    });

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
    if ($scope.isCreate === true) {
      $scope.application.isPeriod = true;
      $scope.application.isAbroad = false;
    }

    var promises = [];
    var subjectsPromise = QueryUtils.endpoint('/students/' + $scope.application.student.id + '/subjects').query({}, function(result) {
      $scope.studentSubjects = result;
    }).$promise;
    promises.push(subjectsPromise);

    var periodsPromise = QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, function(result) {
      $scope.studyPeriods = result;
    }).$promise;
    promises.push(periodsPromise);

    $q.all(promises).then(function() {
      loadFormDeferred.resolve();
    });
  }

  function applicationEksmat(loadFormDeferred) {
      loadFormDeferred.resolve();
  }

  function loadFormData(type, studentId) {
    var loadFormDeferred = $q.defer();
    if (type === 'AVALDUS_LIIK_FINM' || type === 'AVALDUS_LIIK_OVORM' || type === 'AVALDUS_LIIK_OKAVA') {
      QueryUtils.endpoint('/students').get({id: studentId}, function(student) {
        if ($scope.application.type === 'AVALDUS_LIIK_FINM') {
          applicationFinm(student, loadFormDeferred);
        } else if ($scope.application.type === 'AVALDUS_LIIK_OVORM') {
          applicationOvorm(student, loadFormDeferred);
        } else if ($scope.application.type === 'AVALDUS_LIIK_OKAVA') {
          applicationOkava(student, loadFormDeferred);
        }
      });
    } else if (type === 'AVALDUS_LIIK_AKADK') {
      if ($scope.isCreate === true) {
        QueryUtils.endpoint('/applications/student/'+studentId+'/validAcademicLeave').search(function(academicLeaveApplication) {
          applicationAkadk(loadFormDeferred, academicLeaveApplication);
        });
      } else {
        applicationAkadk(loadFormDeferred);
      }
    } else {
      if($scope.application.type === 'AVALDUS_LIIK_AKAD') {
        applicationAkad(loadFormDeferred);
      }  else if ($scope.application.type === 'AVALDUS_LIIK_VALIS') {
        applicationValis(loadFormDeferred);
      } else if ($scope.application.type === 'AVALDUS_LIIK_EKSMAT') {
        applicationEksmat(loadFormDeferred);
      }
    }

    loadFormDeferred.promise.then(function() {
      $scope.applicationEditView = true;
    }, function(rejectMessage) {
      message.error(rejectMessage);
      $scope.applicationEditView = false;
    });
  }



  $scope.$watch('application.student', function(student) {
    if (angular.isObject(student)) {
      if ($scope.isCreate === true) {
        QueryUtils.endpoint('/applications/student/'+student.id+'/applicable').search(function(result) {
          if(angular.isObject(result[$scope.application.type]) && result[$scope.application.type].isAllowed === true) {
            loadFormData($scope.application.type, student.id);
          } else {
            message.error('application.messages.applicationAlreadyExists');
          }
        });
      } else {
        loadFormData($scope.application.type, student.id);
      }
    }
  });

  $scope.openAddFileDialog = function() {
    dialogService.showDialog('application/file.add.dialog.html', function(dialogScope) {
      dialogScope.addedFiles = $scope.application.files;
    }, function(submittedDialogScope) {
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
    return angular.isObject($scope.auth) && angular.isObject($scope.application.student) &&
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
          if ($scope.auth.isStudent()) {
            $location.path('/applications/student');
          } else {
            $location.path('/applications');
          }
        });
      });
    };
});
