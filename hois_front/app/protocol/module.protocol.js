'use strict';

angular.module('hitsaOis').controller('ModuleProtocolController', function ($scope, $route, Classifier, $q, ArrayUtils, QueryUtils, message, $location, dialogService, $window, oisFileService, config) {
  var endpoint = '/moduleProtocols/';
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var nonNumberGrades = ['KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_MA', 'KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.formState = {};

  $scope.calculateGrades = {
    protocolStudents: []
  };

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

  var protocolStudentJournalResults = {};
  function loadJournals() {
    protocolStudentJournalResults = {};
    var journalResults = {};
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      protocolStudent.journalResults.forEach(function (journalResult) {
        if (!journalResults[journalResult.journalId]) {
          journalResults[journalResult.journalId] = ({ id: journalResult.journalId, nameEt: journalResult.nameEt, capacity: journalResult.capacity });
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
    for (var p in journalResults) {
      if (journalResults.hasOwnProperty(p)) {
        journals.push(journalResults[p]);
      }
    }
    $scope.journals = journals;
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
    return allProtocolStudentsGraded() && ($scope.auth.loginMethod === 'ID_CARD' || $scope.auth.loginMethod === 'MOBILE_ID');
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.getUrl = oisFileService.getUrl;
      loadJournals();
      loadGradesSelect();
      $scope.formState.canConfirm = $scope.protocol.status.code !== 'PROTOKOLL_STAATUS_K' && canConfirm();
      $scope.formState.protocolPdfUrl = config.apiUrl + endpoint + entity.id + '/print/protocol.pdf';
    });
  }

  $scope.gradeChanged = function() {
    if ($scope.protocol.status.code === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  } else if ($scope.protocol) {
    loadGradesSelect();
  }


  $scope.deleteProtocolStudent = function (protocolStudent) {
    ArrayUtils.remove($scope.protocol.protocolStudents, protocolStudent);
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

  $scope.confirm = function () {
    if ($scope.auth.isAdmin()) {
      QueryUtils.endpoint(endpoint + $scope.protocol.id + '/confirm').save({
        version: $scope.protocol.version,
        protocolStudents: $scope.protocol.protocolStudents
      }, function (result) {
        message.info('moduleProtocol.messages.confirmed');
        entityToDto(result);
      });
    } else {
      signBeforeConfirm();
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
});
