'use strict';

angular.module('hitsaOis').controller('HigherProtocolEditViewController', ['$route', '$filter', '$location', '$scope', '$q', 'Classifier', 'HigherGradeUtil', 'QueryUtils', 'MidtermTaskUtil', 'ProtocolUtils', 'config', 'dialogService', 'message', 'oisFileService',
function ($route, $filter, $location, $scope, $q, Classifier, HigherGradeUtil, QueryUtils, MidtermTaskUtil, ProtocolUtils, config, dialogService, message, oisFileService) {
  $scope.auth = $route.current.locals.auth;
  $scope.gradeUtil = HigherGradeUtil;
  $scope.letterGrades = $scope.auth.school.letterGrades;
  var baseUrl = "/higherProtocols";
  var Endpoint = QueryUtils.endpoint(baseUrl);
  var midtermTaskUtil = new MidtermTaskUtil();
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS', grade: 'KORGHINDAMINE' });
  var deferredEntityToDto;

  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' });
  $scope.grades.$promise.then(function () {
    $scope.grades = HigherGradeUtil.orderedGrades($scope.grades);
  });

  $scope.gradeSelectShownValue = function (grade) {
    return HigherGradeUtil.gradeSelectShownValue(grade, $scope.auth.school.letterGrades);
  };

  function getEmptyStudentResult(student, midtermTask) {
    return {
            midtermTask: midtermTask.id,
            declarationSubject: student.declarationSubject,
            maxPoints: midtermTask.maxPoints,
            canBeChanged: student.studentResultCanBeChanged,
            isText: midtermTask.studentResultIsText,
            studentId: student.student.id
          };
  }

  $scope.calculateGrades = {
    protocolStudents: []
  };

  function resolveDeferredIfExists() {
    if (angular.isDefined(deferredEntityToDto) && deferredEntityToDto.promise.$$state.status === 0) {
      deferredEntityToDto.resolve();
    }
  }

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.higherProtocolForm.$setPristine();
      $scope.record = clMapper.objectmapper(entity);
      $scope.record.protocolStudents.forEach(function (student) {
        if ($route.current.locals.isView) {
          student = clMapper.objectmapper(student);
        }
        student.practiceJournalResults.forEach(function (result) {
          result = clMapper.objectmapper(result);
        });
      });

      $scope.savedStudents = angular.copy($scope.record.protocolStudents);

      $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks = midtermTaskUtil.getSortedMidtermTasks($scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

      addEmptyStudentResults();// TODO add to MidtermTaskUtil

      midtermTaskUtil.sortStudentResults($scope.record.subjectStudyPeriodMidtermTaskDto.studentResults, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

      $scope.getUrl = oisFileService.getUrl;
      $scope.formState = {};
      $scope.formState.protocolPdfUrl = config.apiUrl + baseUrl + '/print/' + $scope.record.id + '/protocol.pdf';
      $scope.formState.canEditProtocol = ProtocolUtils.canEditProtocol($scope.auth, $scope.record);
      $scope.formState.canChangeConfirmedProtocolGrade = ProtocolUtils.canChangeConfirmedProtocolGrade($scope.auth, $scope.record);
      $scope.formState.canDeleteStudents = ProtocolUtils.canAddDeleteStudents($scope.auth, $scope.record);
      $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.record);
      $scope.formState.canCalculate = $scope.record.protocolType === 'PROTOKOLLI_LIIK_P' && $scope.formState.canEditProtocol &&
        !$scope.record.subjectStudyPeriodMidtermTaskDto.subjectStudyPeriod.isPracticeSubject &&
        $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks.length > 0;
      $scope.formState.isConfirmed = $scope.record.status === 'PROTOKOLL_STAATUS_K';
      resolveDeferredIfExists();
    }).catch(function () {
      resolveDeferredIfExists();
    });
  }

  $scope.gradeChanged = function(row) {
    if (row) {
      var savedResult = $filter('filter')($scope.savedStudents, {id: row.id}, true)[0];
      if (savedResult.grade !== row.grade) {
        $scope.higherProtocolForm.$setSubmitted();
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
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.record);
  };

  $scope.addInfoChanged = function () {
    $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.record);
  };

  if ($route.current.locals.entity) {
    entityToDto($route.current.locals.entity);
  }

  $scope.deleteProtocolStudent = function (protocolStudent) {
    dialogService.confirmDialog({prompt: 'higherProtocol.prompt.deleteStudent'}, function() {
      var ProtocolStudentEndpoint = QueryUtils.endpoint(baseUrl + '/' + $scope.record.id + '/removeStudent');
      var removedStudent = new ProtocolStudentEndpoint(protocolStudent);
      removedStudent.$delete().then(function (protocol) {
        message.info('main.messages.delete.success');
        entityToDto(protocol);
      }).catch(angular.noop);
    });
  };

  function studentHasResultForTask(student, midtermTask) {
    var result = $scope.record.subjectStudyPeriodMidtermTaskDto.studentResults.find(function(studentResult){
      return studentResult.midtermTask === midtermTask.id && studentResult.studentId === student.student.id;
    });
    return angular.isDefined(result);
  }


  function addEmptyStudentResults() {
    $scope.record.protocolStudents.forEach(function(student){
      var newResults = [];
      for(var i = 0; i < $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks.length; i++) {
        if(!studentHasResultForTask(student, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks[i])) {
          newResults.push(getEmptyStudentResult(student, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks[i]));
        }
      }
      $scope.record.subjectStudyPeriodMidtermTaskDto.studentResults = $scope.record.subjectStudyPeriodMidtermTaskDto.studentResults.concat(newResults);
    });
  }

  $scope.filterStudentResults = function(student) {
    return function(studentResult) {
      return student.student.id === studentResult.studentId;
    };
  };

  function validationPassed() {
    $scope.higherProtocolForm.$setSubmitted();
    if(!$scope.higherProtocolForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }
    return true;
  }

  $scope.save = function() {
    if ($scope.higherProtocolForm.finalDate) {
      $scope.higherProtocolForm.finalDate.$setValidity('required', true);
    }
    if(!validationPassed()) {
      return;
    }
    save();
  };

  function save() {
    new Endpoint($scope.record).$update().then(function(response){
      message.updateSuccess();
      entityToDto(response);
    });
  }

  $scope.delete = function() {
    dialogService.confirmDialog({prompt: 'higherProtocol.prompt.delete'}, function() {
      new Endpoint($scope.record).$delete().then(function(){
        message.info('higherProtocol.message.deleted');
        $location.path(baseUrl);
      });
    });
  };

  function setCalculatedGrade(calculatedGrade) {
    var student = $scope.record.protocolStudents.find(function(s){
      return s.id === calculatedGrade.protocolStudent;
    });
    if (angular.isDefined(student) && student.canChangeGrade) {
      student.grade = calculatedGrade.grade;
      $scope.gradeChanged(student);
    }
  }

  function setCalculatedGrades(listOfResults) {
    listOfResults.forEach(setCalculatedGrade);
  }

  $scope.hideInvalid = function (cl) {
    return !Classifier.isValid(cl);
  };

  $scope.calculate = function() {
    QueryUtils.endpoint(baseUrl + "/" + $scope.record.id + "/calculate").query($scope.calculateGrades).$promise.then(function(response){
      $scope.calculateGrades.protocolStudents = [];
      setCalculatedGrades(response);
      message.info('higherProtocol.message.calculated');
    });
  };

  $scope.$watch('record.finalDate', function() {
    if ($scope.record !== undefined && $scope.record.finalDate && $scope.higherProtocolForm.finalDate) {
      $scope.higherProtocolForm.finalDate.$setValidity('required', true);
    }
  });

  $scope.confirm = function () {
    deferredEntityToDto = $q.defer();
    var validationFailed = !validationPassed();
    if(validationFailed || (!$scope.record.finalDate && $scope.record.protocolType === 'PROTOKOLLI_LIIK_P')) {
      if (!validationFailed && !$scope.record.finalDate && $scope.record.protocolType === 'PROTOKOLLI_LIIK_P') {
        message.error('main.messages.form-has-errors');
        $scope.higherProtocolForm.finalDate.$setValidity('required', false);
      }
      resolveDeferredIfExists();
      return deferredEntityToDto.promise;
    }
    ProtocolUtils.signBeforeConfirm($scope.auth, baseUrl + '/' + $scope.record.id, $scope.record, 'higherProtocol.message.confirmed',
      entityToDto, resolveDeferredIfExists);
    return deferredEntityToDto.promise;
  };

  $scope.getMidtermTaskHeader = midtermTaskUtil.getMidtermTaskHeader;

}]);
