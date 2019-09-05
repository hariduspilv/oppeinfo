'use strict';


angular.module('hitsaOis').controller('ApplicationController', function ($http, $location, $q, $route, $scope, $timeout,
  ArrayUtils, AuthService, Classifier, Curriculum, DataUtils, FormUtils, QueryUtils, USER_ROLES, config, dialogService,
  message, oisFileService) {
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

    $scope.canChange = canChange($scope.application);
    $scope.canSave = canSave($scope.application);
  }

  $scope.applicationEditView = false;
  $scope.application = {files: [], status: 'AVALDUS_STAATUS_KOOST'};
  $scope.canChange = canChange($scope.application);
  $scope.canSave = canSave($scope.application);
  
  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entity.$promise.then(function (response) {
      if (!$scope.isView) {
        if (!canChange(response)) {
          message.error('main.messages.error.nopermission');
          $scope.back("#/");
        }
      }
    });
  }
  
  $scope.showOnlyTypes = !angular.isDefined(entity) && $scope.auth.isTeacher() ? ['AVALDUS_LIIK_TUGI'] : true;

  if (angular.isDefined(entity)) {
    if (entity.hasBeenSeenByAdmin === true) { // Hack to remove OptimisticLock
      entity.version++;
    }
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

  function canChange(application) {
    var createdApplicationPerm = false;
    var reviewApplicationPerm = false;
    var confirmedApplicationPerm = false;

    if (application.status === 'AVALDUS_STAATUS_KOOST') {
      createdApplicationPerm = ($scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AVALDUS)) ||
        ($scope.auth.isStudent() || (($scope.auth.isParent() || $scope.auth.isTeacher()) && application.type === 'AVALDUS_LIIK_TUGI'));
    } else if (application.status === 'AVALDUS_STAATUS_YLEVAAT') {
      reviewApplicationPerm = $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AVALDUS);
    } else if (application.status === 'AVALDUS_STAATUS_KINNITATUD') {
      confirmedApplicationPerm = application.type === 'AVALDUS_LIIK_RAKKAVA' && application.canChangeThemeReplacements;
    }

    return application.canEditStudent && (createdApplicationPerm || reviewApplicationPerm || confirmedApplicationPerm);
  }

  function canSave(application) {
    var createdApplicationPerm = application.status === 'AVALDUS_STAATUS_KOOST';
    var reviewApplicationPerm = false;
    var confirmedApplicationPerm = false;

    if (application.status === 'AVALDUS_STAATUS_YLEVAAT') {
      reviewApplicationPerm = application.status === 'AVALDUS_STAATUS_YLEVAAT' && $scope.auth.isAdmin();
    } else if (application.status === 'AVALDUS_STAATUS_KINNITATUD') {
      confirmedApplicationPerm = application.type === 'AVALDUS_LIIK_RAKKAVA' && application.canChangeThemeReplacements;
    }
    
    return (!$scope.application.id || $scope.application.canEditStudent) &&
      (createdApplicationPerm || reviewApplicationPerm || confirmedApplicationPerm);
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
    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });
    $q.all([$scope.studentSubjects.$promise, $scope.studyPeriods.$promise]).then(loadFormDeferred.resolve);
  }

  function applicationOverskava(student, loadFormDeferred) {
    if (!$scope.application.id) {
      $scope.application.oldCurriculumVersion = student.curriculumVersion;
    }
    $scope.formState = {
      curriculum: student.curriculum,
      curriculumVersions: QueryUtils.endpoint("/autocomplete/curriculumversions").query({
        valid: true,
        higher: true,
        curriculumId: student.curriculum,
        hasGroup: true,
        studyForm: student.studyForm,
        checkStudentGroupStudyForm: true
      }, function(result) {
        $scope.formState.curriculumVersions = result.filter(function (r) {
          return r.id !== $scope.application.oldCurriculumVersion.id;
        });
        if ($scope.formState.curriculumVersions.length === 0) {
          $scope.formState.curriculumsEmpty = true;
        }
      }),
      studentGroups: QueryUtils.endpoint("/autocomplete/studentgroups").query({valid: true, higher: true, curriculumId: student.curriculum, studyForm: student.studyForm})
    };
    $q.all($scope.formState.curriculumVersions, $scope.formState.studentGroups).then(loadFormDeferred.resolve);
  }
  
  function applicationRakkava(student, loadFormDeferred) {
    if (!$scope.application.id) {
      $scope.application.oldCurriculumVersion = student.curriculumVersion;
    }
    $scope.formState = {
      curriculum: student.curriculum,
      curriculumVersions: QueryUtils.endpoint("/autocomplete/curriculumversions").query({
        valid: true,
        higher: false,
        curriculumId: student.curriculum,
        hasGroup: true,
        studyForm: student.studyForm
      }, function(result) {
        $scope.formState.curriculumVersions = result.filter(function (r) {
          return r.id !== $scope.application.oldCurriculumVersion.id;
        });
        if ($scope.formState.curriculumVersions.length === 0) {
          $scope.formState.curriculumsEmpty = true;
        }
      }),
      studentGroups: QueryUtils.endpoint("/autocomplete/studentgroups").query({valid: true, higher: false, curriculumId: student.curriculum, studyForm: student.studyForm})
    };
    $q.all($scope.formState.curriculumVersions, $scope.formState.studentGroups).then(loadFormDeferred.resolve);

    if ($scope.isView) {
      getThemeReplacementData();
    }

    $scope.newCurriculumVersionSelected = function () {
      getThemeReplacementData();
    };

    function getThemeReplacementData() {
      QueryUtils.endpoint("/applications/curriculumVersionThemeReplacements/:id").search({
        id: $scope.application.student.id,
        applicationId: $scope.application.id,
        curriculumVersionId: $scope.application.newCurriculumVersion.id
      }).$promise.then(function (result) {
        $scope.application.themeReplacement = result;
    });
    }

  }

  function applicationEksmat(loadFormDeferred) {
    loadFormDeferred.resolve();
  }

  function applicationMuu(loadFormDeferred) {
    loadFormDeferred.resolve();
  }

  function applicationTugi(loadFormDeferred) {
    $scope.application.selectedModules = []; // Should be declared before because of possible error with table where used multiselect.
    $scope.onTugiClChange = function () {
      if ($scope.formState.supportService) {
        var exists = $scope.application.supportServices.find(function (r) {
          return r.code === $scope.formState.supportService.code;
        }) === undefined;
        if (exists) {
          $scope.application.supportServices.push($scope.formState.supportService);
        }
        $scope.formState.supportService = undefined;
      }
    };

    $scope.deleteService = function (service) {
      for(var i = 0; i < $scope.application.supportServices.length; i++){ 
        if ($scope.application.supportServices[i].code === service.code) {
          $scope.application.supportServices.splice(i, 1); 
        }
      }
    };

    $scope.tugiIndivCurriculum = ($scope.application.supportServices || []).find(function (cl) {
      return cl.code === 'TUGITEENUS_1';
    }) !== undefined;
    if (!$scope.isView && $scope.auth.isAdmin()) {
      $scope.$watchCollection('application.supportServices', function (newValues, oldValues) {
        if (newValues !== oldValues) {
          $scope.tugiIndivCurriculum = (newValues || []).find(function (cl) {
            return cl.code === 'TUGITEENUS_1';
          }) !== undefined;
        }
      });

      $scope.formState = {
        committees: QueryUtils.endpoint("/autocomplete/committeesList").query({type: 'KOMISJON_A', valid: true}, function(results) {
          if ($scope.application.id && $scope.application.committee) {
            for (var i = 0; i < results.length; i++) {
              if (results[i].id === $scope.application.committee.id) {
                angular.extend(results[i], $scope.application.committee);
                return;
              }
            }
            results.push($scope.application.committee);
          }
        }),
        modules: QueryUtils.endpoint('/applications/studentIndividualCurriculumModules/:id').query({id: $scope.application.student.id}).$promise.then(function (result) {
          $scope.curriculumVersionModules = result;

          if ($scope.application.supportModules) {
            $scope.application.supportModules.forEach(function (individualModule) {
              
              var curriculumModule = ($scope.curriculumVersionModules || []).find(function (cvModule) {
                return cvModule.module.id === individualModule.module.id;
              });
              if (curriculumModule) {
                $scope.application.selectedModules.push(curriculumModule);
                curriculumModule.id = individualModule.id;
                curriculumModule.addInfo = individualModule.addInfo;
              }
            });
          }
        }),
        supportServices: Classifier.queryForDropdown({
          mainClassCode: 'TUGITEENUS',
          higher: $scope.auth.school.higher ? true : undefined,
          vocational: $scope.auth.school.vocational ? true : undefined})
      };
      $q.all($scope.formState.committees, $scope.formState.modules, $scope.formState.supportServices).then(loadFormDeferred.resolve);
    } else {
      loadFormDeferred.resolve();
    }
  }

  function loadFormData(type, studentId) {
    var loadFormDeferred = $q.defer();
    if (['AVALDUS_LIIK_AKAD', 'AVALDUS_LIIK_FINM', 'AVALDUS_LIIK_OVORM', 'AVALDUS_LIIK_OKAVA', 'AVALDUS_LIIK_OVERSKAVA', 'AVALDUS_LIIK_RAKKAVA'].indexOf(type) !== -1) {
      QueryUtils.endpoint('/students').get({ id: studentId }, function (student) {
        switch (type) {
          case 'AVALDUS_LIIK_AKAD':
            applicationAkad(student, loadFormDeferred);
            break;
          case 'AVALDUS_LIIK_FINM':
            applicationFinm(student, loadFormDeferred);
            break;
          case 'AVALDUS_LIIK_OVORM':
            applicationOvorm(student, loadFormDeferred);
            break;
          case 'AVALDUS_LIIK_OKAVA':
            applicationOkava(student, loadFormDeferred);
            break;
          case 'AVALDUS_LIIK_OVERSKAVA':
            applicationOverskava(student, loadFormDeferred);
            break;
          case 'AVALDUS_LIIK_RAKKAVA':
            applicationRakkava(student, loadFormDeferred);
            break;
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
    } else if (type === 'AVALDUS_LIIK_MUU') {
      applicationMuu(loadFormDeferred);
    } else if (type === 'AVALDUS_LIIK_TUGI') {
      applicationTugi(loadFormDeferred);
    }

    loadFormDeferred.promise.then(function () {
      $scope.applicationEditView = true;
      var type = $scope.application.type.substring($scope.application.type.lastIndexOf('_') + 1).toLowerCase();
      $scope.templateUrlByType = 'application/templates/application.type.' + type + "." + ($scope.isView ? 'view' : 'edit') + ".html";
      $timeout(setPristineWhenFinished);
    }, function (rejectMessage) {
      message.error(rejectMessage);
      $scope.applicationEditView = false;
    });
  }

  function setPristineWhenFinished() {
    if ($http.pendingRequests.length > 0) {
      $timeout(setPristineWhenFinished);
    } else {
      if ($scope.applicationForm) {
        $scope.applicationForm.$setPristine();
      }
    }
  }

  $scope.applicationTypeChange = function () {
    $scope.application.student = undefined;
    switch ($scope.application.type) {
      case 'AVALDUS_LIIK_AKAD':
        $scope.studentSearchCriteria = { active: true, nominalStudy: true };
        break;
      case 'AVALDUS_LIIK_AKADK':
        $scope.studentSearchCriteria = { active: true, academicLeave: true };
        break;
      case 'AVALDUS_LIIK_VALIS':
      case 'AVALDUS_LIIK_OVORM':
      case 'AVALDUS_LIIK_OVERSKAVA':
        $scope.studentSearchCriteria = { studying: true, higher: true };
        break;
      case 'AVALDUS_LIIK_RAKKAVA':
      case 'AVALDUS_LIIK_TUGI':
        $scope.studentSearchCriteria = { studying: true, higher: false };
        break;
      default:
        $scope.studentSearchCriteria = { studying: true };
        break;
    }
    if ($scope.auth.isTeacher()) {
      angular.extend($scope.studentSearchCriteria, {studentGroup: $scope.auth.teacherGroupIds});
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
        }, function () {
          $scope.back("#/");
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
  
  $scope.onOisFileChange = function () {
    if ($scope.applicationForm.fileData && $scope.applicationForm.fileData.$modelValue && $scope.applicationForm.fileData.$modelValue[0]) {
      oisFileService.getFromLfFile($scope.applicationForm.fileData.$modelValue[0], function (file) {
        $scope.application.oisFile = file;
      });
    } else {
      $scope.application.oisFile = undefined;
    }
  };

  $scope.getUrl = oisFileService.getUrl;

  $scope.isStudentRepresentative = function () {
    return angular.isObject($scope.auth) && angular.isObject($scope.application.student) &&
      $scope.auth.student === $scope.application.student.id && $scope.auth.isParent();
  };

  function submit() {
    QueryUtils.endpoint('/applications/' + $scope.application.id + '/submit/').put().$promise.then(function (response) {
      message.info('application.messages.submitted');
      if ($scope.auth.isAdmin() || $scope.isView) {
        entityToForm(response);
        $scope.applicationForm.$setPristine();
      } else {
        $location.url('/applications/' + $scope.application.id + '/view?_noback');
      }
    }).catch(angular.noop);
  }

  $scope.submit = function () {
    $scope.strictRequired = false;
    $timeout(function () {
      FormUtils.withValidForm($scope.applicationForm, function () {
        if (angular.isDefined($scope.applicationForm) && $scope.applicationForm.$dirty === true ) {
          dialogService.confirmDialog({prompt: 'application.messages.confirmSaveAndSubmit'}, function() {  
            var application = new ApplicationsEndpoint($scope.application);
            application.$update().then(function (response) {
              entityToForm(response);
              submit();
            }).catch(angular.noop);
          });
        } else {
          dialogService.confirmDialog({prompt: 'application.messages.confirmSubmit'}, function() {
            submit();
          });
        }
      });
    });
  };
  
  $scope.getStar = function(additionalCheck) {
    return $scope.strictRequired || (angular.isDefined(additionalCheck) && !additionalCheck) ? '' : ' *';
  };

  function confirm() {
    QueryUtils.endpoint('/applications/' + $scope.application.id + '/confirm/').put().$promise.then(function (response) {
      message.info('application.messages.confirmed');
      if ($scope.isView) {
        entityToForm(response);
        $scope.applicationForm.$setPristine();
      } else {
        $location.url('/applications/' + $scope.application.id + '/view?_noback');
      }
    }).catch(angular.noop);
  }

  $scope.confirm = function () {
    $scope.strictRequired = true;
    $timeout(function () {
      FormUtils.withValidForm($scope.applicationForm, function () {
        if (angular.isDefined($scope.applicationForm) && $scope.applicationForm.$dirty === true ) {
          dialogService.confirmDialog({prompt: 'application.messages.confirmSaveAndConfirm'}, function() {  
            var application = new ApplicationsEndpoint($scope.application);
            application.$update().then(function (response) {
              $scope.strictRequired = false;
              entityToForm(response);
              confirm();
            }).catch(angular.noop);
          });
        } else {
          dialogService.confirmDialog({prompt: 'application.messages.confirmConfirm'}, function() {  
            confirm();
          });
        }
      });
    });
  };

  $scope.confirmConfirmation = function () {
    FormUtils.withValidForm($scope.applicationForm, function () {
      QueryUtils.endpoint('/applications/' + $scope.application.id + '/confirmConfirmation').put({
        confirm: $scope.application.agreeWithDecision,
        oisFile: $scope.application.oisFile,
        representativeDecisionAddInfo: $scope.application.representativeDecisionAddInfo
      }).$promise.then(function (response) {
        message.info('application.messages.confirmed');
        if ($scope.isView) {
          entityToForm(response);
          $scope.applicationForm.$setPristine();
        } else {
          $location.url('/applications/' + $scope.application.id + '/view?_noback');
        }
      }).catch(angular.noop);
    });
  };

  $scope.removeConfirmation = function () {
    dialogService.confirmDialog({prompt: $scope.application.representativeConfirmed ?
      'application.messages.confirmRemovingFinalConfirmation' : 'application.messages.confirmRemovingConfirmation'}, function() {
      QueryUtils.endpoint('/applications/' + $scope.application.id + '/removeConfirmation').put().$promise.then(function (response) {
        message.info('application.messages.removeConfirmationSuccess');
        if ($scope.isView) {
          entityToForm(response);
          $scope.applicationForm.$setPristine();
        } else {
          $location.url('/applications/' + $scope.application.id + '/view?_noback');
        }
      }).catch(angular.noop);
    });
  };
  
  $scope.pdfUrl = function () {
    return config.apiUrl + "/applications/print/" + $scope.application.id + "/application.pdf";
  };

  $scope.reject = function () {
    $scope.strictRequired = false;
    $timeout(function () {
      dialogService.showDialog('application/reject.dialog.html', null, function (submittedDialogScope) {
        QueryUtils.endpoint('/applications/' + $scope.application.id + '/reject/').put({ reason: submittedDialogScope.rejectReason }).$promise.then(function (response) {
          message.info('application.messages.rejected');
          if ($scope.isView) {
            entityToForm(response);
            $scope.applicationForm.$setPristine();
          } else {
            $location.url('/applications/' + $scope.application.id + '/view?_noback');
          }
        }).catch(angular.noop);
      });
    });
  };

  $scope.save = function () {
    $scope.strictRequired = false;
    $timeout(function () {
      FormUtils.withValidForm($scope.applicationForm, function () {
        beforeSave();
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
      });
    });
  };

  function beforeSave() {
    if ($scope.application.type === 'AVALDUS_LIIK_RAKKAVA') {
      var applicationOmoduleThemes = [];

      $scope.application.themeReplacement.modules.forEach(function (omodule) {
        omodule.newThemes.forEach(function (theme) {
          if (theme.covering) {
            applicationOmoduleThemes.push(theme);
          }
        });
        omodule.oldThemes.forEach(function (theme) {
          applicationOmoduleThemes.push(theme);
        });
      });
      $scope.application.themeReplacements = applicationOmoduleThemes;
    }
  }

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'application.deleteconfirm' }, function () {
      var application = new ApplicationsEndpoint($scope.application);
      application.$delete().then(function () {
        message.info('main.messages.delete.success');
        if ($scope.auth.isStudent()) {
          $scope.back('#/applications/student');
        } else {
          $scope.back('#/applications');
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

  $scope.isValid = function () {
    return function (cl) {
      return Classifier.isValid(cl);
    };
  };
});
