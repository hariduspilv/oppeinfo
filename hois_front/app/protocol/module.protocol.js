'use strict';

angular.module('hitsaOis').controller('ModuleProtocolController', function ($q, $location, $route, $scope, GRADING_SCHEMA_TYPE, Classifier, GradingSchema, ProtocolUtils, QueryUtils, config, dialogService, message, oisFileService, stateStorageService) {
  var endpoint = '/moduleProtocols';
  $scope.auth = $route.current.locals.auth;
  var stateKey = 'moduleProtocol';
  var schoolId = $scope.auth.school.id;
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  var gradingSchema, gradeMapper, analogGetter;
  var hiddenGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_X'];
  var deferredEntityToDto;

  $scope.formState = {};
  $scope.formState.selectedStudentsExist = false;
  var state = stateStorageService.loadState(schoolId, stateKey);
  if (!angular.equals({}, state)) {
    $scope.formState.showJournals = state.showJournals;
    $scope.formState.showOutcomes = state.showOutcomes;
  } else {
    $scope.formState.showJournals = true;
    $scope.formState.showOutcomes = true;
  }

  $scope.$watch('formState.showJournals', function() {
    stateStorageService.changeState(schoolId, stateKey, {showJournals: $scope.formState.showJournals});
  });

  $scope.$watch('formState.showOutcomes', function() {
    stateStorageService.changeState(schoolId, stateKey, {showOutcomes: $scope.formState.showOutcomes});
  });

  $scope.calculateGrades = {
    protocolStudents: []
  };

  function setGradingSchema(entity) {
    gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.VOCATIONAL);
    $q.all(gradingSchema.promises).then(function () {
      $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
      $scope.grades = gradingSchema.gradeSelection(entity.protocolVdata.studyYear.id);
      $scope.grades.forEach(function (grade) {
        // hide classifier grades that were previously filtered
        if (!grade.gradingSchemaRowId && hiddenGrades.indexOf(grade.code) !== -1) {
          grade.valid = false;
        }
      });
      gradeMapper = gradingSchema.gradeMapper($scope.grades, ['grade']);
      analogGetter = gradingSchema.analogGetter($scope.grades);
    });
  }

  $scope.protocolStudentJournalResults = {};
  function loadJournals() {
    $scope.protocolStudentJournalResults = {};
    var journalResults = {};
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      protocolStudent.journalResults.forEach(function (journalResult) {
        if (!journalResults[journalResult.journalId]) {
          journalResults[journalResult.journalId] = ({
            id: journalResult.journalId,
            nameEt: journalResult.nameEt,
            capacity: journalResult.capacity,
          });
        }

        if (!$scope.protocolStudentJournalResults[protocolStudent.id]) {
          $scope.protocolStudentJournalResults[protocolStudent.id] = {};
        }
        if (!$scope.protocolStudentJournalResults[protocolStudent.id][journalResult.journalId]) {
          $scope.protocolStudentJournalResults[protocolStudent.id][journalResult.journalId] = [];
        }
        var grade = gradeMapper.objectmapper(journalResult).grade;
        $scope.protocolStudentJournalResults[protocolStudent.id][journalResult.journalId].push(grade);
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

  $scope.protocolStudentOutcomeResults = {};
  function loadOutcomes() {
    $scope.protocolStudentOutcomeResults = {};
    $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
      var outcomeResults = [];
      protocolStudent.outcomeResults.forEach(function (outcomeResult) {
        outcomeResults.push({
          id: outcomeResult.curriculumModuleOutcomeId,
          grade: outcomeResult.grade,
          gradeDate: outcomeResult.gradeDate,
          gradeInserted: outcomeResult.gradeInserted
        });
      });

      outcomeResults = sortResultsByGradeInserted(outcomeResults);
      outcomeResults.forEach(function (outcomeResult) {
        if (!$scope.protocolStudentOutcomeResults[protocolStudent.id]) {
          $scope.protocolStudentOutcomeResults[protocolStudent.id] = {};
        }
        if (!$scope.protocolStudentOutcomeResults[protocolStudent.id][outcomeResult.id]) {
          var grade = gradeMapper.objectmapper(outcomeResult).grade;
          $scope.protocolStudentOutcomeResults[protocolStudent.id][outcomeResult.id] = grade;
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
    $scope.outcomes = outcomes;
  }

  function sortResultsByGradeInserted(results) {
    results.sort(function(a, b) {
      a = new Date(a.gradeInserted);
      b = new Date(b.gradeInserted);
      return a>b ? -1 : a<b ? 1 : 0;
    });
    return results;
  }

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises.concat(gradingSchema.promises)).then(function () {
      $scope.moduleProtocolForm.$setPristine();
      $scope.protocol = clMapper.objectmapper(entity);
      $scope.protocol.protocolStudents.forEach(function (protocolStudent) {
        gradeMapper.objectmapper(protocolStudent);
        protocolStudent.practiceJournalResults.forEach(function (practiceJournalResult) {
          gradeMapper.objectmapper(practiceJournalResult);
        });
      });
      $scope.savedStudents = angular.copy($scope.protocol.protocolStudents);
      $scope.getUrl = oisFileService.getUrl;
      loadJournals();
      loadOutcomes();
      $scope.formState.hasOutcomes = $scope.protocol.protocolVdata.outcomes.length > 0;
      $scope.formState.canEditProtocol = ProtocolUtils.canEditProtocol($scope.auth, $scope.protocol);
      $scope.formState.canChangeConfirmedProtocolGrade = ProtocolUtils.canChangeConfirmedProtocolGrade($scope.auth, $scope.protocol);
      $scope.formState.canAddDeleteStudents = ProtocolUtils.canAddDeleteStudents($scope.auth, $scope.protocol);
      $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.protocol);
      $scope.formState.protocolPdfUrl = config.apiUrl + endpoint + '/' + entity.id + '/print/protocol.pdf';
      $scope.$broadcast('refreshFixedColumns');
      $scope.$broadcast('refreshFixedTableHeight');
      resolveDeferredIfExists();
    }).catch(function () {
      resolveDeferredIfExists();
    });
  }

  $scope.gradeChanged = function(row) {
    ProtocolUtils.gradeChanged($scope.moduleProtocolForm, $scope.savedStudents, row);
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.protocol);
  };

  $scope.addInfoChanged = function () {
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.protocol);
  };

  if ($route.current.locals.entity) {
    setGradingSchema($route.current.locals.entity);
    entityToDto($route.current.locals.entity);
  }

  $scope.deleteProtocolStudent = function (protocolStudent) {
    dialogService.confirmDialog({prompt: 'moduleProtocol.prompt.deleteStudent'}, function() {
      var ProtocolStudentEndpoint = QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/removeStudent');
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
      QueryUtils.createQueryForm(dialogScope, endpoint + '/' + $scope.protocol.id + '/otherStudents', {});
      dialogScope.loadData();
    }, function (submittedDialogScope) {
      QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + '/addStudents').save({
        version: $scope.protocol.version,
        protocolStudents: submittedDialogScope.selectedStudents.map(function (it) { return { studentId: it }; })
      }, function (result) {
        message.info('moduleProtocol.messages.studentSuccesfullyAdded');
        entityToDto(result);
      });
    });
  };

  function validationPassed() {
    if(!$scope.moduleProtocolForm.$valid) {
      message.error('main.messages.form-has-errors');
      $scope.moduleProtocolForm.$setSubmitted();
      return false;
    }
    return true;
  }

  $scope.confirm = function () {
    deferredEntityToDto = $q.defer();
    if(!validationPassed()) {
      resolveDeferredIfExists();
      return deferredEntityToDto.promise;
    }

    var data = {
      version: $scope.protocol.version,
      protocolStudents: $scope.protocol.protocolStudents,
    };

    ProtocolUtils.signBeforeConfirm($scope.auth, endpoint + '/' + $scope.protocol.id, data, 'moduleProtocol.messages.confirmed',
      entityToDto, resolveDeferredIfExists);
    return deferredEntityToDto.promise;
  };

  var ModuleProtocolEndpoint = QueryUtils.endpoint(endpoint);
  $scope.save = function () {
    if(!validationPassed()) {
      return;
    }

    new ModuleProtocolEndpoint({ id: $scope.protocol.id, version: $scope.protocol.version, protocolStudents: $scope.protocol.protocolStudents })
      .$update().then(function (result) {
        message.info('main.messages.create.success');
        entityToDto(result);
      });
  };

  function setCalculatedGrade(calculatedGrade) {
    var student = $scope.protocol.protocolStudents.find(function(s) {
      return s.id === calculatedGrade.protocolStudent;
    });
    if (angular.isDefined(student) && student.canChangeGrade) {
      student.grade = analogGetter.get(calculatedGrade.grade);
      $scope.gradeChanged(student);
    }
  }

  function setCalculatedGrades(listOfResults) {
    listOfResults.forEach(setCalculatedGrade);
  }

  function getSelectedStudents() {
    if ($scope.protocol && $scope.protocol.protocolStudents) {
      return $scope.protocol.protocolStudents.filter(function (student) {
        return $scope.calculateGrades.protocolStudents[student.id];
      }).map(function (student) {
        return student.id;
      });
    }
    return 0;
  }

  $scope.$watchCollection('calculateGrades.protocolStudents', function () {
    var selectedStudents = getSelectedStudents();
    $scope.formState.selectedStudentsExist = selectedStudents.length > 0;
  });

  $scope.updateAllStudentsCheckBoxes = function (value) {
    $scope.protocol.protocolStudents.forEach(function (student) {
      if (student.canChangeGrade) {
        $scope.calculateGrades.protocolStudents[student.id] = value;
      }
    });
  };

  $scope.calculate = function() {
    var selectedStudents = getSelectedStudents();
    QueryUtils.endpoint(endpoint + '/' + $scope.protocol.id + "/calculate").query({protocolStudents: selectedStudents}).$promise.then(function(response) {
      $scope.calculateGrades.protocolStudents = [];
      $scope.formState.selectAllStudents = false;
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
