'use strict';

angular.module('hitsaOis').controller('HigherProtocolEditViewController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', '$route', '$rootScope', 'message', 'orderByFilter', '$location', 'config', 'MidtermTaskUtil', 'dialogService', 'ArrayUtils', 'Classifier', function ($scope, $sessionStorage, QueryUtils, DataUtils, $route, $rootScope, message, orderBy, $location, config, MidtermTaskUtil, dialogService, ArrayUtils, Classifier) {

  $scope.auth = $route.current.locals.auth;

  $scope.record = $route.current.locals.entity;
  var baseUrl = "/higherProtocols";

  var Endpoint = QueryUtils.endpoint(baseUrl);
  var ConfirmEndpoint = QueryUtils.endpoint(baseUrl + "/confirm");
  var SaveAndConfirmEndpoint = QueryUtils.endpoint(baseUrl + "/saveAndConfirm");

  var midtermTaskUtil = new MidtermTaskUtil();

  var forbiddenGrades = ['KORGHINDAMINE_MI'];

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

  function afterLoad() {

    $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks = midtermTaskUtil.getSortedMidtermTasks($scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

    addEmptyStudentResults();// TODO add to MidtermTaskUtil

    midtermTaskUtil.sortStudentResults($scope.record.subjectStudyPeriodMidtermTaskDto.studentResults, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

    $scope.formState = {
      protocolPdfUrl: config.apiUrl + baseUrl + '/print/' + $scope.record.id + '/protocol.pdf',
      canCalculate: $scope.record.protocolType === 'PROTOKOLLI_LIIK_P' && $scope.record.canChange && !$scope.record.subjectStudyPeriodMidtermTaskDto.subjectStudyPeriod.isPracticeSubject,
      isConfirmed: $scope.record.status === 'PROTOKOLL_STAATUS_K',
      canConfirm: $scope.record.canConfirm && allProtocolStudentsGraded()
    };
  }

  afterLoad();

  function allProtocolStudentsGraded() {
    if (!angular.isDefined($scope.record) || !angular.isArray($scope.record.protocolStudents)) {
      return false;
    }

    var allGraded = true;
    for (var i = 0; i < $scope.record.protocolStudents.length; i++) {
      if (!angular.isString($scope.record.protocolStudents[i].grade) || $scope.record.protocolStudents[i].grade.trim().length === 0) {
        allGraded = false;
        break;
      }
    }
    return allGraded;
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
    if($scope.formState.isConfirmed) {
      dialogService.confirmDialog({prompt: 'higherProtocol.prompt.saveConfirmedProtocol'}, save);
    } else {
      save();
    }
  };

  function save() {
    new Endpoint($scope.record).$update().then(function(response){
      message.updateSuccess();
      $scope.record = response;
      afterLoad();
      $scope.higherProtocolForm.$setPristine();
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

  function filterGrades() {
    $scope.grades = $scope.grades.filter(function(it) {
      if (!$route.current.locals.isView) {
        return forbiddenGrades.indexOf(it.code) === -1;
      }
    });
  }

  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KORGHINDAMINE' }, filterGrades);

  $scope.calculate = function() {
    QueryUtils.endpoint(baseUrl + "/" + $scope.record.id + "/calculate").query($scope.calculateGrades).$promise.then(function(response){
      $scope.calculateGrades.protocolStudents = [];
      setCalculatedGrades(response);
      message.info('higherProtocol.message.calculated');
    });
  };

  $scope.confirm = function() {
    new ConfirmEndpoint($scope.record).$update().then(function(response){
      message.info('higherProtocol.message.confirmed');
      $scope.record = response;
      afterLoad();
    });
  };

  $scope.saveAndConfirm = function() {
    new SaveAndConfirmEndpoint($scope.record).$update().then(function(response){
      message.info('higherProtocol.message.savedAndConfirmed');
      $scope.record = response;
      afterLoad();
      $scope.higherProtocolForm.$setPristine();
    });
  };

  $scope.gradeChanged = function(student) {
    student.changed = true;
  };

  $scope.getMidtermTaskHeader = midtermTaskUtil.getMidtermTaskHeader;

  /**
   * form.$dirty is required for $rootScope.back() function called on Back button click
   */
  $scope.setFormDirty = function() {
    $scope.higherProtocolStudentForm.$setDirty();
  };

}]);
