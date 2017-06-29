'use strict';

angular.module('hitsaOis').controller('MidtermTaskStudentResultsController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', '$route', '$rootScope', 'message', 'orderByFilter', function ($scope, $sessionStorage, QueryUtils, DataUtils, $route, $rootScope, message, orderBy) {

  $scope.subjectStudyPeriodId = $route.current.params.id;
  var Endpoint = QueryUtils.endpoint('/midtermTasks/studentResults');

  function studentHasResultForTask(student, midtermTask) {
    var result = $scope.record.studentResults.find(function(studentResult){
      return studentResult.midtermTask === midtermTask.id && studentResult.declarationSubject === student.declarationSubject;
    });
    return angular.isDefined(result);
  }

  function indexOfMidtermTask(midtermTaskId) {
    var midtermTask = $scope.record.midtermTasks.find(function(el){
      return el.id === midtermTaskId;
    });
    return $scope.record.midtermTasks.indexOf(midtermTask);
  }

  function getEmptyStudentResult(student, midtermTask) {
    return {
            midtermTask: midtermTask.id,
            declarationSubject: student.declarationSubject,
            maxPoints: midtermTask.maxPoints,
            canBeChanged: student.studentResultCanBeChanged,
            isText: midtermTask.studentResultIsText
          };
  }

  function addEmptyStudentResults() {
    $scope.record.students.forEach(function(student){
      var newResults = [];
      for(var i = 0; i < $scope.record.midtermTasks.length; i++) {
        if(!studentHasResultForTask(student, $scope.record.midtermTasks[i])) {
          newResults.push(getEmptyStudentResult(student, $scope.record.midtermTasks[i]));
        }
      }
      $scope.record.studentResults = $scope.record.studentResults.concat(newResults);
    });
  }

  function afterload() {
    $scope.record.midtermTasks.forEach(function(el){
      DataUtils.convertStringToDates(el, ["taskDate"]);
    });
    $scope.record.midtermTasks = orderBy($scope.record.midtermTasks, ['taskDate', $rootScope.currentLanguageNameField()]);
    addEmptyStudentResults();
    $scope.record.studentResults.sort(function(val1, val2){
      return indexOfMidtermTask(val1.midtermTask) - indexOfMidtermTask(val2.midtermTask);
    });
  }

  Endpoint.get({id: $scope.subjectStudyPeriodId}).$promise.then(function(response){
    $scope.record = response;
    afterload();
  });

  function getNumberWithZero(number) {
    return number < 10 ? "0" + number : number;
  }

  $scope.getMidtermTaskHeader = function(midtermTask) {
    var date = midtermTask.taskDate ?
      getNumberWithZero(midtermTask.taskDate.getDate()) + "." +
      getNumberWithZero(midtermTask.taskDate.getMonth() + 1) : "";
    return $rootScope.currentLanguageNameField(midtermTask) + " " + date + " (" + midtermTask.percentage + "%), max " + midtermTask.maxPoints;
  };

  $scope.filterStudentResults = function(student) {
    return function(studentResult) {
      return student.declarationSubject === studentResult.declarationSubject;
    };
  };

  $scope.save = function() {
    $scope.midtermTaskResultForm.$setSubmitted();
    if(!$scope.midtermTaskResultForm.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }
    $scope.record.id = $scope.subjectStudyPeriodId;
    new Endpoint($scope.record).$update().then(function(response){
      message.updateSuccess();
      $scope.record = response;
      afterload();
    });
  };

}]).controller('MidtermTaskController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', '$route', 'ArrayUtils', 'message', 'dialogService', 'orderByFilter', '$rootScope', function ($scope, $sessionStorage, QueryUtils, DataUtils, $route, ArrayUtils, message, dialogService, orderBy, $rootScope) {

  var Endpoint = QueryUtils.endpoint('/midtermTasks');

  $scope.subjectStudyPeriodId = $route.current.params.id;
  $scope.auth = $route.current.locals.auth;

  function afterload() {
    if(!$scope.record.midtermTasks) {
      $scope.record.midtermTasks = [];
    }
    $scope.record.midtermTasks.forEach(function(el){
      DataUtils.convertStringToDates(el, ["taskDate"]);
    });

    if($scope.record.canBeEdited) {
      $scope.record.midtermTasks = orderBy($scope.record.midtermTasks, ['taskDate', $rootScope.currentLanguageNameField()]);
    }
  }

  Endpoint.get({id: $scope.subjectStudyPeriodId}).$promise.then(function(response){
    $scope.record = response;
    afterload();
  });

  $scope.addRow = function() {
    $scope.record.midtermTasks.push({maxPoints: 0, canBeDeleted: true});
  };

  $scope.removeRow = function(row) {
    if(!row.canBeDeleted) {
      message.error('midtermTask.error.midtermTaskCantBeDeleted');
    } else {
      ArrayUtils.remove($scope.record.midtermTasks, row);
    }
  };

  $scope.save = function() {
    $scope.midtermTaskForm.$setSubmitted();

    if(!$scope.midtermTaskForm.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }
    $scope.record.id = $scope.subjectStudyPeriodId;
    new Endpoint($scope.record).$update().then(function(response){
      message.updateSuccess();
      $scope.record = response;
      afterload();
    });
  };

  $scope.getPercentageSum = function() {
    if(!$scope.record || !$scope.record.midtermTasks) {
      return 0;
    }
    return $scope.record.midtermTasks.reduce(function(sum, val){
      if(val && val.percentage) {
        return sum + val.percentage;
      }
      return sum;
    }, 0);
  };

  $scope.clearThresholdPercentage = function(row) {
    if(!row.threshold) {
      row.thresholdPercentage = null;
    }
  };

  $scope.propertyName = ['taskDate', $rootScope.currentLanguageNameField()];
  $scope.reverse = false;

  $scope.sortBy = function(propertyName) {
    $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
    $scope.propertyName = propertyName;
  };

  $scope.openCopyDialog = function () {

    var DialogController = function (scope) {

      scope.subjectStudyPeriodSelected = [];

      scope.formState = {
        auth: $scope.auth
      };

      QueryUtils.createQueryForm(scope, '/midtermTasks/subjectStudyPeriods/' + $scope.subjectStudyPeriodId, {order: 'subject.' + $scope.currentLanguageNameField()});
      scope.clearCriteria();
      QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
        scope.studyPeriods = response;
        setCurrentStudyPeriod();
        scope.criteria.size = 10;
        scope.loadData();
      });

      function setCurrentStudyPeriod() {
          if(scope.criteria && !scope.criteria.studyPeriod) {
              scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod(scope.studyPeriods).id;
          }
      }

      scope.$watch('criteria.studyPeriod', function() {
              if(scope.studyPeriods && !scope.criteria.studyPeriod) {
                  setCurrentStudyPeriod();
              }
          }
      );

      scope.subjectObject = null;

      scope.$watch('criteria.subjectObject', function() {
        if(scope.criteria) {
          scope.criteria.subject = scope.criteria.subjectObject ? scope.criteria.subjectObject.id : null;
        }
      });

      scope.$watch('criteria.teacherObject', function() {
        if(scope.criteria) {
          scope.criteria.teacher = scope.criteria.teacherObject ? scope.criteria.teacherObject.id : null;
        }
      });
    };
    dialogService.showDialog('midtermTask/midtermTask.copy.dialog.html', DialogController,
      function (submitScope) {
        QueryUtils.endpoint('/midtermTasks/' + $scope.subjectStudyPeriodId + '/subjectStudyPeriodCopy/' + submitScope.subjectStudyPeriodSelected[0].id).put().$promise.then(function(response){
          message.info('midtermTask.message.copied');
          if(response.midtermTasks) {
            $scope.record.midtermTasks = $scope.record.midtermTasks.concat(response.midtermTasks);
          }
          afterload();
        });
    });
  };


}]);

