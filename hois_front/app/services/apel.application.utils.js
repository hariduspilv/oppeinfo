'use strict';

angular.module('hitsaOis').factory('ApelApplicationUtils', function (DataUtils, QueryUtils, dialogService, message) {
  var apelApplicationUtil = {};

  apelApplicationUtil.sendBackToCreation = function (application, callback) {
    if (application.status === 'VOTA_STAATUS_V') {
      dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
        dialogScope.sendBackToCreation = true;
        dialogScope.committee = true;
      }, function (submittedDialogScope) {
        QueryUtils.endpoint('/apelApplications/' + application.id + '/sendBackToCreation/').put({
          addInfo: submittedDialogScope.reason
        }, function (response) {
          message.info('apel.messages.sentBackToCreation');
          callback(response);
        });
      });
    } else {
      dialogService.confirmDialog({
        prompt: 'apel.sendBackToCreationConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + application.id + '/sendBackToCreation/').put({}, function (response) {
          message.info('apel.messages.sentBackToCreation');
          callback(response);
        });
      });
    }
  };

  apelApplicationUtil.sendToConfirm = function (application, callback) {
    if (!atLeastOneLearningTransferred(application)) {
      message.error('apel.error.atLeastOneLearningMustBeTransferred');
      return;
    }

    var extraPrompts = [];
    if (!application.isVocational && formalTansferredSubjectsSmallerThanSubstitutableSubjects(application)) {
      extraPrompts.push('apel.error.transferredSubjectLargerThanSubstitutableSubject');
    }
    var studyEndExtendingNotAllowed = !application.isEhisSent && application.nominalType !== null &&
      application.nominalType !== 'NOM_PIKEND_0' && apelApplicationUtil.abroadStudiesCredits(application) === 0;
    if (studyEndExtendingNotAllowed) {
      extraPrompts.push('apel.error.nominalStudyEndWontBeChanged');
    }

    if (application.status === 'VOTA_STAATUS_V') {
      sendToConfirmWithComment(application, callback, studyEndExtendingNotAllowed, extraPrompts);
    } else {
      dialogService.confirmDialog({
        prompt: 'apel.sendToConfirmConfirm',
        extraPrompts: extraPrompts
      }, function () {
        sendToConfirmWithoutComment(application, callback, studyEndExtendingNotAllowed);
      });
    }
  };

  function atLeastOneLearningTransferred(application) {
    for (var i = 0; i < application.records.length; i++) {
      for (var j = 0; j < application.records[i].data.length; j++) {
        if (application.records[i].data[j].transfer) {
          return true;
        }
      }
    }
    return false;
  }

  function formalTansferredSubjectsSmallerThanSubstitutableSubjects(application) {
    var error = false;
    application.records.forEach(function (record) {
      if (record.isFormalLearning) {
        var transferredCredits = 0;
        var replacedCredits = 0;

        record.formalSubjectsOrModules.forEach(function (subject) {
          if (subject.transfer) {
            transferredCredits += subject.credits;
          }
        });

        record.formalReplacedSubjectsOrModules.forEach(function (subject) {
          replacedCredits += subject.subject.credits;
        });

        record.error = transferredCredits > replacedCredits;
        if (record.error) {
          error = true;
        }
      }
    });
    return error;
  }

  function sendToConfirmWithComment(application, callback, studyEndExtendingNotAllowed, extraPrompts) {
     var modifiedApplication = apelApplicationUtil.recordsToIdentifiers(application);
    dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
      dialogScope.sendToConfirm = true;
      dialogScope.committee = true;
      dialogScope.extraPrompts = extraPrompts;
    }, function (submittedDialogScope) {
      QueryUtils.endpoint('/apelApplications/' + modifiedApplication.id + '/sendToConfirm/').put({
        student: modifiedApplication.student,
        isVocational: modifiedApplication.isVocational,
        nominalType: studyEndExtendingNotAllowed ? null : modifiedApplication.nominalType,
        newNominalStudyEnd: studyEndExtendingNotAllowed ? null : modifiedApplication.newNominalStudyEnd,
        records: modifiedApplication.records,
        committeeId: modifiedApplication.committeeId,
        addInfo: submittedDialogScope.reason
      }, function (response) {
        message.info('apel.messages.sentToConfirm');
        callback(response);
      }, function (response) {
        setRecordsWithErrors(application, response);
      });
    });
  }

  function sendToConfirmWithoutComment(application, callback, studyEndExtendingNotAllowed) {
    var modifiedApplication = apelApplicationUtil.recordsToIdentifiers(application);
    QueryUtils.endpoint('/apelApplications/' + modifiedApplication.id + '/sendToConfirm/').put({
      student: modifiedApplication.student,
      isVocational: modifiedApplication.isVocational,
      nominalType: studyEndExtendingNotAllowed ? null : modifiedApplication.nominalType,
      newNominalStudyEnd: studyEndExtendingNotAllowed ? null : modifiedApplication.newNominalStudyEnd,
      records:  modifiedApplication.records,
      committeeId: modifiedApplication.committeeId
    }, function (response) {
      message.info('apel.messages.sentToConfirm');
      callback(response);
    }, function (response) {
      setRecordsWithErrors(application, response);
    });
  }

  apelApplicationUtil.sendToCommittee = function (application, callback) {
    var modifiedApplication = apelApplicationUtil.recordsToIdentifiers(application);
    dialogService.confirmDialog({
      prompt: 'apel.sendToCommitteeConfirm'
    }, function () {
      QueryUtils.endpoint('/apelApplications/' + modifiedApplication.id + '/sendToCommittee/').put({
        student: modifiedApplication.student,
        isVocational: modifiedApplication.isVocational,
        nominalType: modifiedApplication.nominalType,
        newNominalStudyEnd: modifiedApplication.newNominalStudyEnd,
        committeeId: modifiedApplication.committeeId,
        records:  modifiedApplication.records
      }, function (response) {
        message.info('apel.messages.sentToCommittee');
        callback(response);
      }, function (response) {
        setRecordsWithErrors(application, response);
      });
    });
  };

  apelApplicationUtil.confirm = function (application, callback) {
    if (!atLeastOneLearningTransferred(application)) {
      message.error('apel.error.atLeastOneLearningMustBeTransferred');
      return;
    }
    if (!application.isVocational && formalTansferredSubjectsSmallerThanSubstitutableSubjects(application)) {
      dialogService.confirmDialog({
        prompt: 'apel.error.transferredSubjectLargerThanSubstitutableSubject',
        extraPrompts: ['apel.confirmConfirm']
      }, function () {
        confirm(application, callback, false);
      });
    } else {
      confirm(application, callback, true);
    }
  };

  function confirm(application, callback, askForConfirmation) {
    if (askForConfirmation) {
      dialogService.confirmDialog({
        prompt: 'apel.confirmConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + application.id + '/confirm/').put({}, function (response) {
          message.info('apel.messages.confirmed');
          callback(response);
        });
      });
    } else {
      QueryUtils.endpoint('/apelApplications/' + application.id + '/confirm/').put({}, function (response) {
        message.info('apel.messages.confirmed');
        callback(response);
      });
    }
  }

  apelApplicationUtil.sendBack = function (application, callback) {
    dialogService.confirmDialog({
      prompt: 'apel.sendBackConfirm'
    }, function () {
      QueryUtils.endpoint('/apelApplications/' + application.id + '/sendBack/').put({}, function (response) {
        message.info('apel.messages.sentBack');
        callback(response);
      });
    });
  };

  apelApplicationUtil.reject = function (application, callback) {
    dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
      dialogScope.rejection = true;
      dialogScope.committee = application.status === 'VOTA_STAATUS_V';
    }, function (submittedDialogScope) {
      QueryUtils.endpoint('/apelApplications/' + application.id + '/reject/').put({
        addInfo: submittedDialogScope.reason
      }, function (response) {
        message.info('apel.messages.rejected');
        callback(response);
      });
    });
  };

  apelApplicationUtil.removeConfirmation = function (application, callback) {
    var extraPrompts = [];
    if (application.isEhisSent) {
      extraPrompts.push('apel.error.alreadySentToEhis');
    }

    dialogService.confirmDialog({
      prompt: 'apel.removeConfirmationConfirm',
      extraPrompts: extraPrompts
    }, function () {
      QueryUtils.endpoint('/apelApplications/' + application.id + '/removeConfirmation/').put({}, function (response) {
        message.info('apel.messages.removedConfirmation');
        callback(response);
      });
    });
  };

  apelApplicationUtil.recordsToIdentifiers = function (application) {
    var modifiedApplication = angular.copy(application);
    modifiedApplication.records.forEach(function (record) {
      apelApplicationUtil.formalSubjectOrModulesObjectsToIdentifiers(record);
    });
    return modifiedApplication;
  };

  apelApplicationUtil.formalSubjectOrModulesObjectsToIdentifiers = function (record) {
    for (var i = 0; i < record.formalSubjectsOrModules.length; i++) {
      DataUtils.convertObjectToIdentifier(record.formalSubjectsOrModules[i], ['apelSchool', 'subject', 'curriculumVersionHmodule','curriculumVersionOmodule']);
    }
    for (var j = 0; j < record.formalReplacedSubjectsOrModules.length; j++) {
      DataUtils.convertObjectToIdentifier(record.formalReplacedSubjectsOrModules[j], ['subject', 'curriculumVersionOmodule', 'curriculumVersionOmoduleTheme']);
    }
  };

  apelApplicationUtil.setRecordsWithErrors = function (application, response) {
    setRecordsWithErrors(application, response);
  };

  function setRecordsWithErrors(application, response) {
    angular.forEach(response.data._errors, function (err) {
      if (err.code === 'apel.error.formalRecordsHaveErrors') {
        application.records.forEach(function (record) {
          record.error = err.params.recordsWithErrors.indexOf(record.id) !== -1;
        });
      }
    });
  }

  apelApplicationUtil.abroadStudiesCredits = function (application) {
    var credits = 0;
    application.records.forEach(function (record) {
      record.formalSubjectsOrModules.forEach(function (subject) {
        if (subject.transfer && subject.apelSchool !== null && subject.apelSchool.country !== 'RIIK_EST') {
          credits += subject.credits;
        }
      });
    });
    return credits;
  };

  return apelApplicationUtil;
});
