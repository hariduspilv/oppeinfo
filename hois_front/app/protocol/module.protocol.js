'use strict';

angular.module('hitsaOis').controller('ModuleProtocolController', function ($scope, $route, Classifier, $q, ArrayUtils, QueryUtils, message, $location, dialogService, $mdDialog, $window, oisFileService, config, $timeout, $filter) {
  var endpoint = '/moduleProtocols/';
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var editForbiddenGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_X'];
  var viewForbiddenGrades = ['KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  
  $scope.formState = {};

  $scope.calculateGrades = {
    protocolStudents: []
  };

  allGrades.$promise.then(function (result) {
    $scope.grades = result.filter(function (it) {
      if ($route.current.locals.isView) {
        return viewForbiddenGrades.indexOf(it.code) === -1;
      } else {
        return editForbiddenGrades.indexOf(it.code) === -1;
      }
    });
  });

  var protocolStudentJournalResults = {};
  function loadJournals() {
    protocolStudentJournalResults = {};
    var journalResults = {};
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      protocolStudent.journalResults.forEach(function (journalResult) {
        if (!journalResults[journalResult.journalId]) {
          journalResults[journalResult.journalId] = ({
            id: journalResult.journalId,
            nameEt: journalResult.nameEt,
            capacity: journalResult.capacity,
            hasOutcomes: journalResult.journalHasOutcomes
          });
        }

        if (!protocolStudentJournalResults[protocolStudent.id]) {
          protocolStudentJournalResults[protocolStudent.id] = {};
        }
        if (!protocolStudentJournalResults[protocolStudent.id][journalResult.journalId]) {
          protocolStudentJournalResults[protocolStudent.id][journalResult.journalId] = [];
        }
        protocolStudentJournalResults[protocolStudent.id][journalResult.journalId].push(clMapper.objectmapper(journalResult).grade);
      });
    });
    var journals = [];
    $scope.formState.outcomesAsEntries = false;
    for (var p in journalResults) {
      if (journalResults.hasOwnProperty(p)) {
        journals.push(journalResults[p]);
        
        if (!$scope.formState.outcomesAsEntries && journalResults[p].hasOutcomes) {
          $scope.formState.outcomesAsEntries = true;
        }
      }
    }
    $scope.journals = journals;
  }

  var protocolStudentOutcomeResults = {};
  function loadOutcomes() {
    protocolStudentOutcomeResults = {};
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      var outcomeResults = [];
      protocolStudent.outcomeResults.forEach(function (outcomeResult) {
        outcomeResults.push({id: outcomeResult.curriculumModuleOutcomeId, grade: outcomeResult.grade, gradeInserted: outcomeResult.gradeInserted });
      });

      outcomeResults = sortResultsByGradeInserted(outcomeResults);
      outcomeResults.forEach(function (outcomeResult) {
        if (!protocolStudentOutcomeResults[protocolStudent.id]) {
          protocolStudentOutcomeResults[protocolStudent.id] = {};
        }
        if (!protocolStudentOutcomeResults[protocolStudent.id][outcomeResult.id]) {
          protocolStudentOutcomeResults[protocolStudent.id][outcomeResult.id] = clMapper.objectmapper(outcomeResult).grade;
        }
      });
    });

    setOutcomes($scope.protocol.protocolVdata.outcomes);
  }

  function setOutcomes(outcomes) {
    var orderNr = 0;
    outcomes.forEach(function (outcome) {
      if (outcome.orderNr === null) {
        outcome.orderNr = orderNr++;
      }
    });
    $scope.outcomes =outcomes;
  }

  function sortResultsByGradeInserted(results) {
    results.sort(function(a, b) {
      a = new Date(a.gradeInserted);
      b = new Date(b.gradeInserted);
      return a>b ? -1 : a<b ? 1 : 0;
    });
    return results;
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

  function allChangedGradesHaveAddInfo() {
    if (!angular.isDefined($scope.protocol) || !angular.isArray($scope.protocol.protocolStudents)) {
      return false;
    }

    var allHaveAddInfo = true;
    if ($scope.formState.canEditConfirmedProtocol) {
      for (var i = 0; i < $scope.protocol.protocolStudents.length; i++) {
        if ($scope.protocol.protocolStudents[i].gradeHasChanged) {
          var addInfo = $scope.protocol.protocolStudents[i].addInfo;
          addInfo = addInfo !== undefined && addInfo !== null ? addInfo.split(' ').join('') : null;
          
          if (!addInfo) {
            allHaveAddInfo = false;
            break;
          }
        }
      }
    }
    return allHaveAddInfo;
  }

  function canChangeConfirmedProtocolGrade() {
    return canEditConfirmedProtocol() && $scope.protocol.status.code === 'PROTOKOLL_STAATUS_K';
  }

  function canEditConfirmedProtocol() {
    return ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M') &&
      ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_MOODULPROTOKOLL");
  }

  function canConfirm() {
    return allProtocolStudentsGraded() && allChangedGradesHaveAddInfo() && 
      ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M') &&
      ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_MOODULPROTOKOLL");
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.savedStudents = angular.copy($scope.protocol.protocolStudents);
      $scope.getUrl = oisFileService.getUrl;
      loadJournals();
      loadOutcomes();
      $scope.formState.canEditConfirmedProtocol = canEditConfirmedProtocol();
      $scope.formState.canChangeConfirmedProtocolGrade = canChangeConfirmedProtocolGrade();
      $scope.formState.canAddDeleteStudents = $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && $scope.protocol.canBeEdited;
      $scope.formState.canConfirm = $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && canConfirm();
      $scope.formState.protocolPdfUrl = config.apiUrl + endpoint + entity.id + '/print/protocol.pdf';
    });
  }

  $scope.gradeChanged = function(row) {
    if (row) {
      var savedResult = $filter('filter')($scope.savedStudents, {id: row.id}, true)[0];
      if (savedResult.grade !== row.grade) {
        $scope.moduleProtocolForm.$setSubmitted();
        row.gradeHasChanged = true;
      } else {
        row.gradeHasChanged = false;

        if (!savedResult.addInfo) {
          row.addInfo = null;
        } else {
          row.addInfo = savedResult.addInfo;
        }
      }
    }

    if ($scope.protocol.status.code === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  $scope.addInfoChanged = function (row) {
    if (row.addInfo && row.addInfo.charAt(0) === ' ') {
      row.addInfo = null;
    }
    
    if ($scope.protocol.status.code === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  }

  $scope.deleteProtocolStudent = function (protocolStudent) {
    dialogService.confirmDialog({prompt: 'moduleProtocol.prompt.deleteStudent'}, function() {
      var ProtocolStudentEndpoint = QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/removeStudent');
      var removedStudent = new ProtocolStudentEndpoint(protocolStudent);
      removedStudent.$delete().then(function (protocol) {
          message.info('main.messages.delete.success');
          entityToDto(protocol);
      }).catch(angular.noop);
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
        message.info('moduleProtocol.messages.studentSuccesfullyAdded');
        entityToDto(result);
      });
    });
  };

  $scope.journalResultsView = function (journalId, protocolStudent) {
    if (protocolStudentJournalResults[protocolStudent.id] && protocolStudentJournalResults[protocolStudent.id][journalId]) {
      return protocolStudentJournalResults[protocolStudent.id][journalId].map(function (it) { return it.value; }).join(' / ');
    }
  };

  $scope.outcomeResultsView = function (curriculumModuleOutcomeId, protocolStudent) {
    if (protocolStudentOutcomeResults[protocolStudent.id] && protocolStudentOutcomeResults[protocolStudent.id][curriculumModuleOutcomeId]) {
      return protocolStudentOutcomeResults[protocolStudent.id][curriculumModuleOutcomeId].value;
    }
  };

  $scope.gradeValue = function (code) {
    var grade = clMapper.objectmapper({ grade: code }).grade;
    return grade ? grade.value : undefined;
  };

  function signBeforeConfirm() {
    $window.hwcrypto.getCertificate({ lang: 'en' }).then(function (certificate) {
      QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/signToConfirm').save({
        version: $scope.protocol.version,
        protocolStudents: $scope.protocol.protocolStudents,
        certificate: certificate.hex
      }, function (result) {
        $window.hwcrypto.sign(certificate, { type: 'SHA-256', hex: result.digestToSign }, { lang: 'en' }).then(function (signature) {
          QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/signToConfirmFinalize').save({
            signature: signature.hex,
            version: result.version
          }, function (result) {
            message.info('moduleProtocol.messages.confirmed');
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
    QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/mobileSignToConfirm').save({
      version: $scope.protocol.version,
      protocolStudents: $scope.protocol.protocolStudents
    }, function (result) {
      if (result.challengeID) {
        $scope.signVersion = result.version;
        $mdDialog.show({
          controller: function ($scope) {
            $scope.challengeID = result.challengeID;
          },
          templateUrl: 'protocol/module.protocol.mobileSign.dialog.html',
          parent: angular.element(document.body),
          clickOutsideToClose: false
        });
        $scope.mobileIdPolls = 0;
        $timeout(pollMobileSignStatus, config.mobileIdInitialDelay);
      } else {
        message.error('main.messages.error.mobileIdSignFailed');
      }
    });
  }

  function pollMobileSignStatus() {
    QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/mobileSignStatus').get(
      function (response) {
        if (response.status === 'SIGNATURE') {
          $mdDialog.hide();
          QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/mobileSignFinalize').save({
            version: $scope.signVersion
          }, function (result) {
            message.info('moduleProtocol.messages.confirmed');
            entityToDto(result);
          });
        } else if (response.status === 'OUTSTANDING_TRANSACTION') {
          $scope.mobileIdPolls++;
          if ($scope.mobileIdPolls < config.mobileIdMaxPolls) {
            $timeout(pollMobileSignStatus, config.mobileIdPollInterval);
          }
        } else {
          console.log('mobileSignStatus: '+response.status);
          $mdDialog.hide();
          message.error('main.messages.error.mobileIdSignFailed');
        }
      });
  }
  
  $scope.confirm = function () {
    if ($scope.auth.loginMethod === 'LOGIN_TYPE_I') {
      signBeforeConfirm();
    } else if ($scope.auth.loginMethod === 'LOGIN_TYPE_M') {
      mobileSignBeforeConfirm();
    }
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint('/moduleProtocols');
  $scope.save = function () {
    new ModuleProtocolEndpoint({ id: $scope.protocol.id, version: $scope.protocol.version, protocolStudents: $scope.protocol.protocolStudents })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      });
  };

  function setCalculatedGrade(calculatedGrade) {
    var student = $scope.protocol.protocolStudents.find(function(s){
      return s.id === calculatedGrade.protocolStudent;
    });
    student.grade = calculatedGrade.grade;
  }

  function setCalculatedGrades(listOfResults) {
    listOfResults.forEach(setCalculatedGrade);
    $scope.gradeChanged();
  }

  $scope.calculate = function() {
    QueryUtils.endpoint("/moduleProtocols/" + $scope.protocol.id + "/calculate").query($scope.calculateGrades).$promise.then(function(response){
      $scope.calculateGrades.protocolStudents = [];
      setCalculatedGrades(response);
      message.info('moduleProtocol.messages.calculated');
    });
  };

  var Endpoint = QueryUtils.endpoint(endpoint);

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'moduleProtocol.prompt.delete'}, function() {
      new Endpoint($scope.protocol).$delete().then(function(){
        message.info('moduleProtocol.messages.deleted');
        $location.path(endpoint);
      });
    });
  };
});
