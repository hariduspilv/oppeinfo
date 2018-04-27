'use strict';

angular.module('hitsaOis').controller('HigherProtocolEditViewController', ['$scope', '$sessionStorage', '$filter', 'QueryUtils', 'DataUtils', '$route', '$rootScope', 'message', 'orderByFilter', '$location', 'config', 'MidtermTaskUtil', 'dialogService', 'ArrayUtils', 'Classifier', 
function ($scope, $sessionStorage, $filter, QueryUtils, DataUtils, $route, $rootScope, message, orderBy, $location, config, MidtermTaskUtil, dialogService, ArrayUtils, Classifier) {

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
    $scope.savedStudents = angular.copy($scope.record.protocolStudents);

    $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks = midtermTaskUtil.getSortedMidtermTasks($scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

    addEmptyStudentResults();// TODO add to MidtermTaskUtil

    midtermTaskUtil.sortStudentResults($scope.record.subjectStudyPeriodMidtermTaskDto.studentResults, $scope.record.subjectStudyPeriodMidtermTaskDto.midtermTasks);

    $scope.formState = {};
    $scope.formState.protocolPdfUrl = config.apiUrl + baseUrl + '/print/' + $scope.record.id + '/protocol.pdf';
    $scope.formState.canCalculate = $scope.record.protocolType === 'PROTOKOLLI_LIIK_P' && $scope.record.canChange && !$scope.record.subjectStudyPeriodMidtermTaskDto.subjectStudyPeriod.isPracticeSubject;
    $scope.formState.isConfirmed = $scope.record.status === 'PROTOKOLL_STAATUS_K';
    $scope.formState.canEditConfirmedProtocol = canEditConfirmedProtocol();
    $scope.formState.canChangeConfirmedProtocolGrade = canChangeConfirmedProtocolGrade();
    $scope.formState.canConfirm = $scope.record.status !== 'PROTOKOLL_STAATUS_K' && canConfirm();
  }

  function canChangeConfirmedProtocolGrade() {
    return canEditConfirmedProtocol() && $scope.record.status === 'PROTOKOLL_STAATUS_K';
  }

  function canEditConfirmedProtocol() {
    return ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M') &&
      ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_PROTOKOLL");
  }

  function canConfirm() {
    return allProtocolStudentsGraded() && allChangedGradesHaveAddInfo() && 
      ($scope.auth.loginMethod === 'LOGIN_TYPE_I' || $scope.auth.loginMethod === 'LOGIN_TYPE_M') &&
      $scope.auth.authorizedRoles.indexOf("ROLE_OIGUS_K_TEEMAOIGUS_PROTOKOLL") !== -1;
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

    if ($scope.record.status === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

  $scope.addInfoChanged = function (row) {
    if (row.addInfo && row.addInfo.charAt(0) === ' ') {
      row.addInfo = null;
    }
    
    if ($scope.record.status === 'PROTOKOLL_STAATUS_K' && $scope.auth.isAdmin()) {
      $scope.formState.canConfirm = canConfirm();
    }
  };

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

  function allChangedGradesHaveAddInfo() {
    if (!angular.isDefined($scope.record) || !angular.isArray($scope.record.protocolStudents)) {
      return false;
    }

    var allHaveAddInfo = true;
    if ($scope.formState.canEditConfirmedProtocol) {
      for (var i = 0; i < $scope.record.protocolStudents.length; i++) {
        if ($scope.record.protocolStudents[i].gradeHasChanged) {
          var addInfo = $scope.record.protocolStudents[i].addInfo;
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

  $scope.getMidtermTaskHeader = midtermTaskUtil.getMidtermTaskHeader;

}]);
