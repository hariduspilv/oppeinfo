'use strict';

angular.module('hitsaOis').controller('HigherProtocolEditViewController', ['$scope', '$sessionStorage', '$filter', '$q', 'QueryUtils', 'DataUtils', '$route', '$rootScope', 'message', 'orderByFilter', '$location', 'config', 'MidtermTaskUtil', 'dialogService', 'ArrayUtils', 'Classifier', 'ProtocolUtils', 'oisFileService', 'HigherGradeUtil',
function ($scope, $sessionStorage, $filter, $q, QueryUtils, DataUtils, $route, $rootScope, message, orderBy, $location, config, MidtermTaskUtil, dialogService, ArrayUtils, Classifier, ProtocolUtils, oisFileService, HigherGradeUtil) {
  $scope.auth = $route.current.locals.auth;
  $scope.gradeUtil = HigherGradeUtil;
  var baseUrl = "/higherProtocols";
  var Endpoint = QueryUtils.endpoint(baseUrl);
  var midtermTaskUtil = new MidtermTaskUtil();
  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });

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

  function entityToDto(entity) {
    $q.all(clMapper.promises).then(function () {
      $scope.higherProtocolForm.$setPristine();
      $scope.record = clMapper.objectmapper(entity);
      $scope.savedStudents = angular.copy($scope.record.protocolStudents);
  
      $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks = midtermTaskUtil.getSortedMidtermTasks($scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);
  
      addEmptyStudentResults();// TODO add to MidtermTaskUtil
  
      midtermTaskUtil.sortStudentResults($scope.record.subjectStudyPeriodMidtermTaskDto.studentResults, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);
  
      $scope.getUrl = oisFileService.getUrl;
      $scope.formState = {};
      $scope.formState.protocolPdfUrl = config.apiUrl + baseUrl + '/print/' + $scope.record.id + '/protocol.pdf';
      $scope.formState.canEditProtocol = ProtocolUtils.canEditProtocol($scope.auth, $scope.record);
      $scope.formState.canChangeConfirmedProtocolGrade = ProtocolUtils.canChangeConfirmedProtocolGrade($scope.auth, $scope.record);
      $scope.formState.canConfirm = ProtocolUtils.canConfirm($scope.auth, $scope.record);
      $scope.formState.canCalculate = $scope.record.protocolType === 'PROTOKOLLI_LIIK_P' && $scope.formState.canEditProtocol &&
        !$scope.record.subjectStudyPeriodMidtermTaskDto.subjectStudyPeriod.isPracticeSubject;
      $scope.formState.isConfirmed = $scope.record.status === 'PROTOKOLL_STAATUS_K';
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

  function setCalculatedGrade(calculatedGrade) {
    var student = $scope.record.protocolStudents.find(function(s){
      return s.id === calculatedGrade.protocolStudent;
    });
    student.grade = calculatedGrade.grade;
    $scope.gradeChanged(student);
  }

  function setCalculatedGrades(listOfResults) {
    listOfResults.forEach(setCalculatedGrade);
  }

  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' });

  $scope.calculate = function() {
    QueryUtils.endpoint(baseUrl + "/" + $scope.record.id + "/calculate").query($scope.calculateGrades).$promise.then(function(response){
      $scope.calculateGrades.protocolStudents = [];
      setCalculatedGrades(response);
      message.info('higherProtocol.message.calculated');
    });
  };

  $scope.confirm = function () {
    if(!validationPassed()) {
      return;
    }

    if ($scope.auth.loginMethod === 'LOGIN_TYPE_I') {
      ProtocolUtils.signBeforeConfirm(baseUrl + '/' + $scope.record.id, $scope.record, entityToDto);
    } else if ($scope.auth.loginMethod === 'LOGIN_TYPE_M') {
      ProtocolUtils.mobileSignBeforeConfirm(baseUrl + '/' + $scope.record.id, $scope.record, entityToDto);
    }
  };

  $scope.getMidtermTaskHeader = midtermTaskUtil.getMidtermTaskHeader;

}]);
