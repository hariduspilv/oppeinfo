'use strict';

angular.module('hitsaOis').controller('FinalExamVocationalProtocolEditController', 
function ($scope, $route, QueryUtils, Classifier, $q, oisFileService, config, message, dialogService, ArrayUtils, $location) {
  var endpoint = '/finalExamVocationalProtocols/';
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS', studyLevel: 'OPPEASTE' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var nonNumberGrades = ['KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_MA', 'KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.formState = {};

  function loadGradesSelect() {
    allGrades.$promise.then(function (result) {
      $scope.grades = result.filter(function (it) {
        if ($scope.protocol.protocolVdata.assessment === 'KUTSEHINDAMISVIIS_M') {
          return nonNumberGrades.indexOf(it.code) > -1;
        } else {
          return nonNumberGrades.indexOf(it.code) === -1;
        }
      });
    });
  }

  function allProtocolStudentsGraded() {
    if (!angular.isDefined($scope.protocol) || !angular.isArray($scope.protocol.protocolStudents)) {
      return false;
    }

    var allGraded = true;
    for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
      if (!angular.isString($scope.protocol.protocolStudents[i].grade) || $scope.protocol.protocolStudents[i].grade.trim().length === 0) {
        allGraded = false;
        break;
      }
    }
    return allGraded;
  }

  function canConfirm() {
    return allProtocolStudentsGraded() && ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M');
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.getUrl = oisFileService.getUrl;
      loadGradesSelect();
      $scope.formState.canConfirm = $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && canConfirm();
      $scope.formState.protocolPdfUrl = config.apiUrl + endpoint + entity.id + '/print/protocol.pdf';
    });
  }

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  } else if ($scope.protocol) {
    loadGradesSelect();
  }

  $scope.selectedFinalExamDateChanged = function () {
    $scope.committees = QueryUtils.endpoint(endpoint + 'committees').query({ finalExamDate: $scope.protocol.finalExamDate });
  };

  $scope.selectedCommitteeChanged = function () {
    QueryUtils.endpoint("/committees").get({ id: $scope.protocol.committee.id }).$promise.then(function (committee) {
      $scope.protocol.committee = committee;
      $scope.committeeMembers = committee.members;
    });
  };

  $scope.deleteProtocolStudent = function (protocolStudent) {
    dialogService.confirmDialog({prompt: 'finalExamProtocol.prompt.deleteStudent'}, function() {
      ArrayUtils.remove($scope.protocol.protocolStudents, protocolStudent);
    });
  };

  $scope.addProtocolStudents = function () {
    dialogService.showDialog('protocol/module.protocol.addStudent.dialog.html', function (dialogScope) {
      dialogScope.selectedStudents = [];
      var query = QueryUtils.endpoint(endpoint + $scope.protocol.id + '/otherStudents').query();
      dialogScope.tabledata = {
        $promise: query.$promise,
      };
      query.$promise.then(function (result) {
        dialogScope.tabledata.content = studentClMapper.objectmapper(result);
      });
      dialogScope.journalResultsView = function (results) {
        return results.map(function (it) { return it.value; }).join(' / ');
      };
    }, function (submittedDialogScope) {
      QueryUtils.endpoint(endpoint + $scope.protocol.id + '/addStudents').save({
        version: $scope.protocol.version,
        protocolStudents: submittedDialogScope.selectedStudents.map(function (it) { return { studentId: it }; })
      }, function (result) {
        message.info('finalExamProtocol.messages.studentSuccesfullyAdded');
        entityToDto(result);
      }).catch(angular.noop);
    });
  };

  $scope.gradeValue = function (code) {
    var grade = clMapper.objectmapper({ grade: code }).grade;
    return grade ? grade.value : undefined;
  };

  function signBeforeConfirm() {
    $window.hwcrypto.getCertificate({ lang: 'en' }).then(function (certificate) {
      QueryUtils.endpoint(endpoint + $scope.protocol.id + '/signToConfirm').save({
        version: $scope.protocol.version,
        protocolStudents: $scope.protocol.protocolStudents,
        certificate: certificate.hex
      }, function (result) {
        $window.hwcrypto.sign(certificate, { type: 'SHA-256', hex: result.digestToSign }, { lang: 'en' }).then(function (signature) {
          QueryUtils.endpoint(endpoint + $scope.protocol.id + '/signToConfirmFinalize').save({
            signature: signature.hex,
            version: result.version
          }, function (result) {
            message.info('finalExamProtocol.messages.confirmed');
            entityToDto(result);
          });
        });
      });
    }).catch(function (reason) {
      //no_implementation, no_certificates, user_cancel, technical_error
      if (reason.message === 'user_cancel') {
        message.error('main.messages.error.idCardSigningCancelled');
      } else {
        message.error('main.messages.error.readingIdCardFailed');
      }
    });
  }

  function mobileSignBeforeConfirm() {
    QueryUtils.endpoint(endpoint + $scope.protocol.id + '/mobileSignToConfirm').save({
      version: $scope.protocol.version,
      protocolStudents: $scope.protocol.protocolStudents
    }, function (result) {
      if (result.challengeID) {
        $scope.signVersion = result.version;
        $mdDialog.show({
          controller: function ($scope) {
            $scope.challengeID = result.challengeID;
          },
          templateUrl: 'finalExamProtocol/final.exam.protocol.mobile.sign.dialog.html',
          parent: angular.element(document.body),
          clickOutsideToClose: false
        });
        $scope.mobileIdPolls = 0;
        $timeout(pollMobileSignStatus, config.mobileIdInitialDelay);
      } else {
        message.error('main.messages.error.mobileIdSignFailed');
      }
    }).catch(angular.noop);
  }

  function pollMobileSignStatus() {
    QueryUtils.endpoint(endpoint + $scope.protocol.id + '/mobileSignStatus').get(
      function (response) {
        if (response.status === 'SIGNATURE') {
          $mdDialog.hide();
          QueryUtils.endpoint(endpoint + $scope.protocol.id + '/mobileSignFinalize').save({
            version: $scope.signVersion
          }, function (result) {
            message.info('finalExamProtocol.messages.confirmed');
            entityToDto(result);
          });
        } else if (response.status === 'OUTSTANDING_TRANSACTION') {
          $scope.mobileIdPolls++;
          if ($scope.mobileIdPolls < config.mobileIdMaxPolls) {
            $timeout(pollMobileSignStatus, config.mobileIdPollInterval);
          }
        } else {
          $mdDialog.hide();
          message.error('main.messages.error.mobileIdSignFailed');
        }
      });
  }
  
  $scope.confirm = function () {
    if ($scope.auth.isAdmin()) {
      QueryUtils.endpoint(endpoint + $scope.protocol.id + '/confirm').save({
        version: $scope.protocol.version,
        protocolStudents: $scope.protocol.protocolStudents
      }, function (result) {
        message.info('finalExamProtocol.messages.confirmed');
        entityToDto(result);
      }).catch(angular.noop);;
    } else {
      if ($scope.auth.loginMethod === 'LOGIN_TYPE_I') {
        signBeforeConfirm();
      } else if ($scope.auth.loginMethod === 'LOGIN_TYPE_M') {
        mobileSignBeforeConfirm();
      }
    }
  };

  var FinalExamVocationlProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    new FinalExamVocationlProtocolEndpoint({ 
      id: $scope.protocol.id, 
      version: $scope.protocol.version,
      committeeId: $scope.protocol.committee.id,
      protocolCommitteeMembers: getPresentCommitteeMembers($scope.committeeMembers),
      protocolStudents: $scope.protocol.protocolStudents })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      }).catch(angular.noop);
  };

  function getPresentCommitteeMembers(committeeMembers) {
    var presentCommitteeMembers = [];
    if (committeeMembers) {
      for (var i = 0; i < committeeMembers.length; i++) {
        if (committeeMembers[i].isPresent) {
          presentCommitteeMembers.push({committeeMemberId: committeeMembers[i].id});
        }
      }
    }
    return presentCommitteeMembers;
  }

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'finalExamProtocol.prompt.delete'}, function() {
      new FinalExamVocationlProtocolEndpoint($scope.protocol).$delete().then(function(){
        message.info('finalExamProtocol.messages.deleted');
        $location.path(endpoint);
      }).catch(angular.noop);
    });
  };

});
