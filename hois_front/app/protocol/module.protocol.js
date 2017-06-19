'use strict';

angular.module('hitsaOis').controller('ModuleProtocolController', function ($scope, $route, Classifier, $q, ArrayUtils, QueryUtils, message, $location, dialogService) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', status: 'PROTOKOLL_STAATUS' });
  var studentClMapper = Classifier.valuemapper({ journalResults: 'KUTSEHINDAMINE', status: 'OPPURSTAATUS' });
  var nonNumberGrades = ['KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_MA', 'KUTSEHINDAMINE_X'];
  var allGrades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });

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

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.protocol = clMapper.objectmapper(entity);
      loadJournals();
      loadGradesSelect();
    });
  }

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
      var query = QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/otherStudents').query();
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
      QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/addStudents').save({
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

  $scope.confirm = function () {
    QueryUtils.endpoint('/moduleProtocols/' + $scope.protocol.id + '/confirm').save({
      version: $scope.protocol.version,
      protocolStudents: $scope.protocol.protocolStudents
    }, function (result) {
      message.info('moduleProtocol.messages.confirmed');
      entityToDto(result);
    });
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint('/moduleProtocols');
  $scope.save = function () {
    new ModuleProtocolEndpoint({ id: $scope.protocol.id, version: $scope.protocol.version, protocolStudents: $scope.protocol.protocolStudents })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      });
  };
});
