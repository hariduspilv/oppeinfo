'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($scope, dialogService, oisFileService, QueryUtils, message, $location, $route, DataUtils, ArrayUtils, $q, Curriculum, Classifier) {
  var ApplicationsEndpoint = QueryUtils.endpoint('/applications');

  $scope.removeFromArray = ArrayUtils.remove;
  $scope.auth = $route.current.locals.auth;
  $scope.isCreate = $route.current.locals.isCreate;
  $scope.isView = $route.current.locals.isView;
  $scope.now = new Date();

  function entityToForm(savedApplication) {
    DataUtils.convertStringToDates(savedApplication, ['startDate', 'endDate']);
    if (angular.isArray(savedApplication.plannedSubjects)) {
      savedApplication.plannedSubjects.forEach(function (plannedSubject) {
        if (angular.isArray(plannedSubject.equivalents)) {
          plannedSubject.subjectsSelected = plannedSubject.equivalents.map(function (it) { return it.subject; });
        }
      });
    }
    angular.extend($scope.application, savedApplication);
  }

  $scope.applicationEditView = false;
  $scope.application = {files: [], status: 'AVALDUS_STAATUS_KOOST'};

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  if (angular.isDefined($route.current.params.student) && angular.isDefined($route.current.params.type)) {
    $scope.application.type = $route.current.params.type;
    QueryUtils.endpoint('/autocomplete/students?id=' + $route.current.params.student).get({}, function (result) {
      if (result && result.totalElements === 1) {
        $scope.application.student = result.content[0];
      }
    });
  }

  function studyPeriodDisplay(studyPeriod) {
    return $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
  }

  $scope.studyPeriodView = function (studyPeriodId) {
    if (angular.isArray($scope.studyPeriods) && angular.isNumber(studyPeriodId)) {
      for (var i = 0; i < $scope.studyPeriods.length; i++) {
        if ($scope.studyPeriods[i].id === studyPeriodId) {
          return studyPeriodDisplay($scope.studyPeriods[i]);
        }
      }
    }
  };

  $scope.subjectsListView = function (plannedSubject) {
    if (angular.isDefined(plannedSubject) && angular.isArray(plannedSubject.subjectsSelected) && angular.isArray($scope.studentSubjects)) {
      var subjects = [];
      $scope.studentSubjects.forEach(function (it) {
        if (plannedSubject.subjectsSelected.indexOf(it.id) !== -1) {
          subjects.push($scope.currentLanguageNameField(it));
        }
      });
      return subjects.join(", ");
    }
    return "";
  };

  function applicationFinm(student, loadFormDeferred) {
    if ($scope.isCreate) {
      $scope.application.oldFin = student.fin;
      $scope.application.oldFinSpecific = student.finSpecific;
    }
    $scope.application.newFin = student.fin === "FINALLIKAS_RE" ? "FINALLIKAS_REV" : "FINALLIKAS_RE";
    loadFormDeferred.resolve();
  }

  function applicationOvorm(student, loadFormDeferred) {
    if ($scope.isCreate) {
      $scope.application.oldStudyForm = student.studyForm;
    }
    loadFormDeferred.resolve();
  }

  function applicationOkava(student, loadFormDeferred) {
    var allCurriculumVersions = Curriculum.queryVersions({ valid: true });
    if ($scope.isCreate) {
      $scope.application.oldStudyForm = student.studyForm;
      $scope.application.oldCurriculumVersion = student.curriculumVersion;
    }

    var allStudyForms = Classifier.queryForDropdown({ mainClassCode: 'OPPEVORM' });
    function filterStudyForms() {
      var newCurriculumVersion = $scope.application.newCurriculumVersion;
      var selectedCurriculumVersion = newCurriculumVersion ? allCurriculumVersions.find(function (it) { return it.id === newCurriculumVersion.id; }) : undefined;
      var higher = angular.isObject(selectedCurriculumVersion) ? !selectedCurriculumVersion.isVocational : $scope.auth.school.higher;
      var vocational = angular.isObject(selectedCurriculumVersion) ? selectedCurriculumVersion.isVocational : $scope.auth.school.vocational;

      var studyForms;
      if(selectedCurriculumVersion && angular.isString(selectedCurriculumVersion.studyForm)) {
        studyForms = [selectedCurriculumVersion.studyForm];
      } else {
        studyForms = allStudyForms.filter(function(it) {
          return (higher && it.higher) || (vocational && it.vocational);
        }).map(function(it) { return it.code;});
      }
      $scope.allowedStudyForms = studyForms;
    }

    $scope.newCurriculumVersionSelected = function () {
      var selectedCurriculumVersion = allCurriculumVersions.find(function (it) { return it.id === $scope.application.newCurriculumVersion.id; });
      if (angular.isObject(selectedCurriculumVersion)) {
        allStudyForms.$promise.then(function() {
          $scope.application.newStudyForm = selectedCurriculumVersion.studyForm;
          filterStudyForms();
        });
      }
    };

    allCurriculumVersions.$promise.then(function (result) {
      $scope.curriculumVersions = result.filter(function (it) { return it.id !== student.curriculumVersion.id; });
      allStudyForms.$promise.then(function() {
        filterStudyForms();
        loadFormDeferred.resolve();
      });
    });
  }

  function applicationAkad(student, loadFormDeferred) {
    if ($scope.isCreate) {
      $scope.application.isPeriod = true;
    }
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({},
    function (studyPeriods) {
      studyPeriods.forEach(function (studyPeriod) {
        studyPeriod.display = studyPeriodDisplay(studyPeriod);
      });
      loadFormDeferred.resolve();
    });
    $scope.curriculumVersion = student.curriculumVersion;
  }

  function applicationAkadk(loadFormDeferred) {
    DataUtils.convertStringToDates($scope.application.validAcademicLeave, ['startDate', 'endDate']);
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query({}, loadFormDeferred.resolve);
  }

  function applicationValis(loadFormDeferred) {
    $scope.isAbroadChanged = function () {
      if ($scope.application.isAbroad) {
        $scope.application.ehisSchool = undefined;
      } else {
        $scope.application.abroadSchool = undefined;
        $scope.application.country = undefined;
      }
    };

    $scope.plannedSubjectLength = function () {
      return angular.isArray($scope.application.plannedSubjects) ? $scope.application.plannedSubjects.length : 0;
    };

    $scope.addPlannedSubjectRow = function () {
      if (!angular.isArray($scope.application.plannedSubjects)) {
        $scope.application.plannedSubjects = [];
      }
      $scope.application.plannedSubjects.push({ equivalents: [] });
    };

    $scope.subjectsSelected = function (plannedSubject) {
      if (angular.isArray(plannedSubject.subjectsSelected)) {
        var newEquivalents = [];
        plannedSubject.subjectsSelected.forEach(function (subjectId) {
          var found = false;
          for (var i = 0; i < plannedSubject.equivalents.length && !found; i++) {
            var equivalent = plannedSubject.equivalents[i];
            if (equivalent.subject === subjectId) {
              newEquivalents.push(equivalent);
              found = true;
            }
          }
          if (!found) {
            newEquivalents.push({ subject: subjectId });
          }
        });
        plannedSubject.equivalents = newEquivalents;
      } else {
        plannedSubject.equivalents = [];
      }
    };

    $scope.studentEhisSchool = $route.current.locals.auth.school.ehisSchool;
    if ($scope.isCreate) {
      $scope.application.isPeriod = true;
      $scope.application.isAbroad = false;
    }

    $scope.studentSubjects = QueryUtils.endpoint('/students/' + $scope.application.student.id + '/subjects').query();
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $q.all([$scope.studentSubjects.$promise, $scope.studyPeriods.$promise]).then(loadFormDeferred.resolve);
  }

  function applicationEksmat(loadFormDeferred) {
    loadFormDeferred.resolve();
  }

  function loadFormData(type, studentId) {
    var loadFormDeferred = $q.defer();
    if (type === 'AVALDUS_LIIK_AKAD' || type === 'AVALDUS_LIIK_FINM' || type === 'AVALDUS_LIIK_OVORM' || type === 'AVALDUS_LIIK_OKAVA') {
      QueryUtils.endpoint('/students').get({ id: studentId }, function (student) {
        if (type === 'AVALDUS_LIIK_AKAD') {
          applicationAkad(student, loadFormDeferred);
        } else if (type === 'AVALDUS_LIIK_FINM') {
          applicationFinm(student, loadFormDeferred);
        } else if (type === 'AVALDUS_LIIK_OVORM') {
          applicationOvorm(student, loadFormDeferred);
        } else if (type === 'AVALDUS_LIIK_OKAVA') {
          applicationOkava(student, loadFormDeferred);
        }
      });
    } else if (type === 'AVALDUS_LIIK_AKADK') {
      if ($scope.isCreate) {
        QueryUtils.endpoint('/applications/student/' + studentId + '/validAcademicLeave').search(function (validAcademicLeave) {
          if (!angular.isObject(validAcademicLeave) || !angular.isNumber(validAcademicLeave.id)) {
            loadFormDeferred.reject('application.messages.academicLeaveApplicationNotfound');
          } else {
            $scope.application.validAcademicLeave = validAcademicLeave;
            applicationAkadk(loadFormDeferred);
          }
        });
      } else {
        applicationAkadk(loadFormDeferred);
      }
    } else if (type === 'AVALDUS_LIIK_VALIS') {
      applicationValis(loadFormDeferred);
    } else if (type === 'AVALDUS_LIIK_EKSMAT') {
      applicationEksmat(loadFormDeferred);
    }

    loadFormDeferred.promise.then(function () {
      $scope.applicationEditView = true;
      var type = $scope.application.type.substring($scope.application.type.lastIndexOf('_') + 1).toLowerCase();
      $scope.templateUrlByType = 'application/templates/application.type.' + type + "." + ($scope.isView ? 'view' : 'edit') + ".html";
    }, function (rejectMessage) {
      message.error(rejectMessage);
      $scope.applicationEditView = false;
    });
  }

  $scope.applicationTypeChange = function () {
    $scope.application.student = undefined;
    if ($scope.application.type === 'AVALDUS_LIIK_AKAD') {
      $scope.studentSearchCriteria = { active: true, nominalStudy: true };
    } else if ($scope.application.type === 'AVALDUS_LIIK_AKADK') {
      $scope.studentSearchCriteria = { active: true, academicLeave: true };
    } else if ($scope.application.type === 'AVALDUS_LIIK_VALIS') {
      $scope.studentSearchCriteria = { studying: true, higher: true };
    } else if ($scope.application.type === 'AVALDUS_LIIK_OVORM') {
      $scope.studentSearchCriteria = { studying: true, higher: true };
    } else {
      $scope.studentSearchCriteria = { studying: true };
    }
  };

  $scope.$watch('application.student', function (student) {
    if (angular.isObject(student)) {
      if ($scope.isCreate) {
        QueryUtils.endpoint('/applications/student/' + student.id + '/applicable').search(function (result) {
          if (angular.isObject(result[$scope.application.type]) && result[$scope.application.type].isAllowed === true) {
            loadFormData($scope.application.type, student.id);
          } else {
            message.error(result[$scope.application.type].reason);
          }
        });
      } else {
        loadFormData($scope.application.type, student.id);
      }
    }
  });

  $scope.openAddFileDialog = function () {
    dialogService.showDialog('application/file.add.dialog.html', function (dialogScope) {
      dialogScope.addedFiles = $scope.application.files;
    }, function (submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function (file) {
        data.oisFile = file;
        $scope.application.files.push(data);
      });
    });
  };

  $scope.getUrl = oisFileService.getUrl;

  $scope.isStudentRepresentative = function () {
    return angular.isObject($scope.auth) && angular.isObject($scope.application.student) &&
      $scope.auth.student === $scope.application.student.id && $scope.auth.isParent();
  };

  $scope.submit = function () {
    QueryUtils.endpoint('/applications/' + $scope.application.id + '/submit/').put().$promise.then(function (response) {
      message.info('application.messages.submitted');
      if ($scope.auth.isAdmin() || $scope.isView) {
        entityToForm(response);
      } else {
        $location.url('/applications/' + $scope.application.id + '/view?_noback');
      }
    }).catch(angular.noop);
  };

  $scope.reject = function () {
    dialogService.showDialog('application/reject.dialog.html', null, function (submittedDialogScope) {
      QueryUtils.endpoint('/applications/' + $scope.application.id + '/reject/').put({ reason: submittedDialogScope.rejectReason }).$promise.then(function (response) {
        message.info('application.messages.rejected');
        entityToForm(response);
      }).catch(angular.noop);
    });
  };

  $scope.save = function () {
    $scope.applicationForm.$setSubmitted();
    if ($scope.applicationForm.$valid) {
      var application = new ApplicationsEndpoint($scope.application);
      if (angular.isDefined($scope.application.id)) {
        application.$update().then(function () {
          message.info('main.messages.update.success');
          entityToForm(application);
          $scope.applicationForm.$setPristine();
        }).catch(angular.noop);
      } else {
        application.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url('/applications/' + application.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    }
  };

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'application.deleteconfirm' }, function () {
      var application = new ApplicationsEndpoint($scope.application);
      application.$delete().then(function () {
        message.info('main.messages.delete.success');
        if ($scope.auth.isStudent()) {
          $location.path('/applications/student');
        } else {
          $location.path('/applications');
        }
      }).catch(angular.noop);
    });
  };

  $scope.periodTypeChanged = function() {
    $scope.application.startDate = undefined;
    $scope.application.endDate = undefined;
    $scope.application.studyPeriodStart = undefined;
    $scope.application.studyPeriodEnd = undefined;
  };
});
