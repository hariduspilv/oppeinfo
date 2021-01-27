'use strict';

angular.module('hitsaOis').controller('FinalThesisEditController', function ($location, $route, $scope, QueryUtils, config, dialogService, message, DataUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {};
  var endpoint = '/finalThesis';
  var FinalThesisEndpoint = QueryUtils.endpoint(endpoint);
  var isEditForm = $route.current.locals.params.isEdit;

  $scope.cercsValueChanged = cercsValueChanged;
  $scope.cercsTypeChanged = cercsTypeChanged;

  function entityToForm(entity) {
    $scope.thesis = entity.finalThesis;
    if ($scope.thesis !== null) {
      $scope.thesis.supervisors.forEach(function (supervisor) {
        supervisor.canEdit = !$scope.auth.isTeacher() || supervisor.isExternal || supervisor.teacher.id !== $scope.auth.teacher;
      });
      if (isEditForm && $scope.thesis.curriculumGrade) {
        $scope.thesis.curriculumGrade = $scope.thesis.curriculumGrade.id;
      }
      if (isEditForm && $scope.thesis.cercses.length < 100) {
        $scope.thesis.cercses.push({});
      }
    } else {
      newThesis();
    }
    setStudentInfo(entity);
    $scope.isStudentActive = entity.isStudentActive;
    $scope.finalThesisExpected = entity.finalThesisExpected;
    $scope.noFinalExamDeclaration = entity.noFinalExamDeclaration;
    $scope.specialitySet = entity.specialitySet;
    $scope.allRequirementsMet = entity.isStudentActive && entity.finalThesisExpected &&
      entity.noFinalExamDeclaration && entity.specialitySet;
  }

  function newThesis() {
    $scope.thesis = {
      hasDraft: false,
      supervisors: [],
      cercses: []
    };

    if ($scope.auth.isStudent()) {
      $scope.thesis.student = {id: $scope.auth.student};
    }
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entity.$promise.then(function (result) {
      if (result !== null) {
        entityToForm(result);
        if (angular.isDefined($scope.thesis.id) && $scope.thesis.id !== null) {
          if (!$scope.thesis.canBeEdited && isEditForm) {
            $location.url(endpoint + '/' + $scope.thesis.id + '/view?_noback');
          }
          $scope.finalThesisPdfUrl = config.apiUrl + endpoint + '/print/' + $scope.thesis.id + '/finalThesis.pdf';
        }
      }
    });
  }

  if (angular.isUndefined(entity) && $scope.auth.isStudent()) {
    $scope.formState.student = { id:  $scope.auth.student };
  }

  $scope.$watch('formState.student', function () {
    if ($scope.formState.student !== null && angular.isDefined($scope.formState.student)) {
      var studentId = $scope.formState.student.id;
      QueryUtils.endpoint(endpoint + '/studentFinalThesis').get({ studentId: studentId }, function (result) {
        entityToForm(result);
        $scope.thesis.student = { id: studentId };
      });
    }
  });

  function setStudentInfo(entity) {
    var result = entity.student;
    if (angular.isDefined(result)) {
        $scope.thesis.person = result.person;
        $scope.thesis.curriculumVersion = result.curriculumVersion;
        $scope.thesis.studentGroup = result.studentGroup;
        $scope.thesis.isVocational = result.isVocational;
        $scope.thesis.curriculumGrades = result.curriculumGrades;
        $scope.thesis.isMagisterStudy = result.isMagisterStudy;
        $scope.thesis.isDoctoralStudy = result.isDoctoralStudy;
        $scope.thesis.isIntegratedStudy = result.isIntegratedStudy;
        $scope.thesis.isMagisterOrDoctoralOrIntegratedStudy = result.isMagisterStudy || result.isDoctoralStudy || result.isIntegratedStudy;
        if ($scope.thesis.isMagisterOrDoctoralOrIntegratedStudy) {
          if (!$scope.thesis.language) {
            if ($scope.thesis.isMagisterStudy || $scope.thesis.isIntegratedStudy) {
              $scope.thesis.language = 'LOPUTOO_KEEL_et';
            } else {
              $scope.thesis.language = 'LOPUTOO_KEEL_en';
            }
          }
          if (isEditForm && $scope.thesis.cercses.length === 0) {
            $scope.thesis.cercses.push({});
          }
        }
    }
  }

  $scope.openAddSupervisorDialog = function (supervisorIndex) {
    var supervisorsCount = $scope.thesis.supervisors ? $scope.thesis.supervisors.length : 0;
    if (supervisorsCount >= 3 && !angular.isDefined(supervisorIndex)) {
      message.error('finalThesis.error.maxSupervisors');
    } else {
      dialogService.showDialog('finalThesis/supervisor.add.dialog.html', function (dialogScope) {
        dialogScope.isMagisterOrDoctoralOrIntegratedStudy = $scope.thesis.isMagisterOrDoctoralOrIntegratedStudy;
        dialogScope.newSupervisor = angular.isDefined(supervisorIndex) ? false : true;
        if (!dialogScope.newSupervisor) {
          dialogScope.supervisor = angular.copy($scope.thesis.supervisors[supervisorIndex]);
        } else {
          dialogScope.supervisor = {isExternal: false};
        }

        dialogScope.$watch("supervisor.teacher", function () {
          if (dialogScope.supervisor && dialogScope.supervisor.teacher) {
            QueryUtils.endpoint(endpoint + '/teacher/').get({teacherId: dialogScope.supervisor.teacher.id}, function (result) {
              dialogScope.supervisor.firstname = result.person.firstname;
              dialogScope.supervisor.lastname = result.person.lastname;
              dialogScope.supervisor.email = result.email;
              dialogScope.supervisor.occupation = result.teacherOccupation.nameEt;
            });
          }
        });

        dialogScope.typeChanged = function (isExternal) {
          dialogScope.supervisor = {isExternal: isExternal};
        };

        dialogScope.removeSupervisor = function () {
          dialogService.confirmDialog({prompt: 'finalThesis.supervisorDeleteConfirm'}, function() {
            $scope.thesis.supervisors.splice(supervisorIndex, 1);
            dialogScope.cancel();
          });
        };

        dialogScope.submitSupervisor = function () {
          if (teacherAlreadyAdded(dialogScope.supervisor, supervisorIndex)) {
            message.error('finalThesis.error.teacherAlreadyIsSupervisor');
          } else {
            dialogScope.submit();
          }
        };

        dialogScope.preventBtn = false;

        dialogScope.changedIdcode = function () {
          dialogScope.preventBtn = false;
          if (!!dialogScope.supervisor.idcode) {
            dialogScope.supervisor.sex = DataUtils.sexFromIdcode(dialogScope.supervisor.idcode);
            dialogScope.supervisor.birthdate = DataUtils.birthdayFromIdcode(dialogScope.supervisor.idcode);
          }
        };

        dialogScope.preventSave = function () {
          dialogScope.preventBtn = true;
        };

        dialogScope.wrongIdcode = function (response) {
          if (response.status === 404) {
            dialogScope.changedIdcode();
          }
          dialogScope.preventBtn = false;
        };

      }, function (submittedDialogScope) {
        var modifiedSupervisor = submittedDialogScope.supervisor;
        removePreviousPrimarySupervisor(modifiedSupervisor);

        if (angular.isDefined(supervisorIndex)) {
          $scope.thesis.supervisors[supervisorIndex] = modifiedSupervisor;
        } else {
          $scope.thesis.supervisors.push(modifiedSupervisor);
        }
      });
    }
  };

  function teacherAlreadyAdded(modifiedSupervisor, modifiedSupervisorIndex) {
    var teacherId = modifiedSupervisor.teacher ? modifiedSupervisor.teacher.id : null;

    if (teacherId) {
      for (var i = 0; i < $scope.thesis.supervisors.length; i++) {
        if ($scope.thesis.supervisors[i].teacher && $scope.thesis.supervisors[i].teacher.id === teacherId && modifiedSupervisorIndex !== i) {
          return true;
        }
      }
    }
    return false;
  }

  function removePreviousPrimarySupervisor(modifiedSupervisor) {
    if (modifiedSupervisor.isPrimary) {
      for (var i = 0; i < $scope.thesis.supervisors.length; i++) {
        $scope.thesis.supervisors[i].isPrimary = false;
      }
    }
  }

  function isValid() {
    $scope.formState.finalThesisForm.$setSubmitted();
    if($scope.thesis.supervisors.length === 0) {
      message.error('finalThesis.error.supervisorRequired');
      return false;
    }

    // Check supervisor data if outer.
    if ($scope.thesis.isMagisterOrDoctoralOrIntegratedStudy) {
      for (var i = 0; i < $scope.thesis.supervisors.length; i++) {
        if ($scope.thesis.supervisors[i].isExternal) {
          if (!$scope.thesis.supervisors[i].idcode && (!$scope.thesis.supervisors[i].birthdate || !$scope.thesis.supervisors[i].sex)) {
            message.error('finalThesis.error.supervisorOutdated');
            return false;
          }
        }
      }
    }

    // For view form
    if ($scope.thesis.isMagisterOrDoctoralOrIntegratedStudy && $scope.thesis.cercses.length === 0) {
      message.error('finalThesis.error.cercsRequired');
      return false;
    }

    if(!$scope.formState.finalThesisForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }

    return true;
  }

  $scope.save = function () {
    if (isValid()) {
      if ($scope.thesis.cercses.length > 0) {
        cercsTypeChanged($scope.thesis.cercses.length - 1, $scope.thesis.cercses[$scope.thesis.cercses.length - 1].cercsType, false, true);
      }

      if (angular.isDefined($scope.thesis.id)) {
        QueryUtils.endpoint(endpoint + '/' + $scope.thesis.id + '/isStudentActive').get().$promise.then(function (result) {
          if (!result.isActive) {
            dialogService.confirmDialog({ prompt: 'finalThesis.saveNotActiveStudentConfirm' }, function () {
              updateFinalThesis();
            });
          } else {
            updateFinalThesis();
          }
        });
      } else {
        var finalThesis = new FinalThesisEndpoint($scope.thesis);
        finalThesis.$save().then(function (result) {
          message.info('main.messages.update.success');
          $location.url(endpoint + '/' + result.finalThesis.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    }
  };

  function updateFinalThesis() {
    var finalThesis = new FinalThesisEndpoint($scope.thesis);
    finalThesis.$update().then(function (result) {
      message.info('main.messages.update.success');
      entityToForm(result);
    }).catch(angular.noop);
  }

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'finalThesis.deleteConfirm' }, function () {
      var finalThesis = new FinalThesisEndpoint($scope.thesis);
      finalThesis.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.url(endpoint);
      });
    });
  };

  $scope.confirm = function () {
    if (isValid()) {
      dialogService.confirmDialog({
        prompt: 'finalThesis.confirmConfirm'
      }, function () {
        if ($scope.thesis.cercses.length > 0) {
          cercsTypeChanged($scope.thesis.cercses.length - 1, $scope.thesis.cercses[$scope.thesis.cercses.length - 1].cercsType, false, true);
        }
        QueryUtils.endpoint(endpoint + '/' + $scope.thesis.id + '/confirm').put($scope.thesis, function (response) {
          message.info('finalThesis.isConfirmed');
          entityToForm(response);
        });
      });
    }
  };

  function cercsValueChanged(index, value) {
    for (var i = 0; i < $scope.thesis.cercses.length; i++) {
      if (i !== index && $scope.thesis.cercses[i].cercs === value) {
        $scope.thesis.cercses[index].cercs = undefined;
        message.error('finalThesis.error.duplicateCercs');
        return;
      }
    }
    if ($scope.thesis.cercses.length === index + 1 && $scope.thesis.cercses.length < 100 && !!value) {
      addEmptyCercsToThesis();
    }
  }

  function cercsTypeChanged(index, type, ignoreLast, canByEmpty) {
    if ($scope.thesis.cercses.length > (!!canByEmpty ? 0 : 1) && !type && (!ignoreLast || $scope.thesis.cercses.length !== index + 1)) {
      removeCercs(index);
    }
  }

  function addEmptyCercsToThesis() {
    if (!angular.isArray($scope.thesis.cercses)) {
      $scope.thesis.cercses = [];
    }
    $scope.thesis.cercses.push({});
  }

  function removeCercs(index) {
    $scope.thesis.cercses.splice(index, 1);
  }

});
