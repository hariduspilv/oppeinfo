'use strict';

angular.module('hitsaOis').controller('MidtermTaskStudentResultsController',
['$q', '$route', '$scope', '$timeout', 'ArrayUtils', 'Classifier', 'MidtermTaskUtil', 'QueryUtils', 'dialogService', 'message',
function ($q, $route, $scope, $timeout, ArrayUtils, Classifier, MidtermTaskUtil, QueryUtils, dialogService, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.updateSubgroupPlaces = updateSubgroupPlaces;

  $scope.subjectStudyPeriodId = $route.current.params.id;
  var Endpoint = QueryUtils.endpoint('/midtermTasks/studentResults');
  var midtermTaskUtil = new MidtermTaskUtil();
  var clMapper = Classifier.valuemapper({ grade: 'KORGHINDAMINE' });

  function studentHasResultForTask(student, midtermTask) {
    var result = $scope.record.studentResults.find(function(studentResult){
      return studentResult.midtermTask === midtermTask.id && studentResult.declarationSubject === student.declarationSubject;
    });
    return angular.isDefined(result);
  }

  function getEmptyStudentResult(student, midtermTask) {
    return {
            midtermTask: midtermTask.id,
            declarationSubject: student.declarationSubject,
            maxPoints: midtermTask.maxPoints,
            isText: midtermTask.studentResultIsText
          };
  }

  function addEmptyStudentResults(student) {
    var newResults = [];
    for (var i = 0; i < $scope.record.midtermTasks.length; i++) {
      if (!studentHasResultForTask(student, $scope.record.midtermTasks[i])) {
        newResults.push(getEmptyStudentResult(student, $scope.record.midtermTasks[i]));
      }
    }
    $scope.record.studentResults = $scope.record.studentResults.concat(newResults);
  }

  function loadTasks() {
    Endpoint.get({id: $scope.subjectStudyPeriodId}).$promise.then(function(response){
      $scope.record = response;
      $scope.moodleCourseId = response.subjectStudyPeriod.moodleCourseId;
      afterload();
    });
  }

  function afterload() {
    $scope.record.midtermTasks = midtermTaskUtil.getSortedMidtermTasks($scope.record.midtermTasks);
    $scope.moodleTasks = midtermTaskUtil.getMoodleTasks($scope.record.midtermTasks);
    $q.all(clMapper.promises).then(function () {
      $scope.record.protocols.forEach(function (protocol) {
        protocol.protocolStudents.forEach(function (student) {
          clMapper.objectmapper(student);
        });
      });
    });
    $scope.record.students.forEach(function (student) {
      student.previousSubgroup = student.subgroup !== null ? student.subgroup.id : null;
      // TODO add to MidtermTaskUtil
      addEmptyStudentResults(student);
    });
    midtermTaskUtil.sortStudentResults($scope.record.studentResults, $scope.record.midtermTasks);
    // set pristine after timeout as ngModel in mdSelect will set dirty
    $timeout(function () {
      $scope.midtermTaskResultForm.$setPristine();
    });
  }

  loadTasks();

  $scope.getMidtermTaskHeader = midtermTaskUtil.getMidtermTaskHeader;

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

  $scope.enrollStudents = function () {
    QueryUtils.endpoint('/midtermTasks/' + $scope.subjectStudyPeriodId + '/moodle/enrollStudents').save(null, function (moodleResponse) {
      dialogService.showDialog('components/moodle.enroll.dialog.html', function (dialogScope) {
        dialogScope.moodleResponse = moodleResponse;
      }, loadTasks, loadTasks);
    });
  };
  $scope.importGradeItems = function () {
    QueryUtils.endpoint('/midtermTasks/' + $scope.subjectStudyPeriodId + '/moodle/importGradeItems').save(null, function () {
      message.info('moodle.messages.gradeItemsImported');
      loadTasks();
    });
  };
  $scope.importAllGrades = function () {
    QueryUtils.endpoint('/midtermTasks/' + $scope.subjectStudyPeriodId + '/moodle/importAllGrades').save(null, function () {
      message.info('moodle.messages.allGradesImported');
      loadTasks();
    });
  };
  $scope.importMissingGrades = function () {
    QueryUtils.endpoint('/midtermTasks/' + $scope.subjectStudyPeriodId + '/moodle/importMissingGrades').save(null, function () {
      message.info('moodle.messages.missingGradesImported');
      loadTasks();
    });
  };

  $scope.filterProtocols = function(student) {
    return function(protocol){
      var protocolStudents = protocol.protocolStudents.map(function(ps){
        return ps.student.id;
      });
      return ArrayUtils.contains(protocolStudents, student.studentId);
    };
  };

  $scope.filterStudentProtocols = function(student) {
    return function(protocolStudent) {
      return student.studentId === protocolStudent.student.id;
    };
  };

  function updateSubgroupPlaces(student, newSubgroupId) {
    var oldSubgroup = student.previousSubgroup !== null ? $scope.record.subgroups.find(function (el) {
      return el.id === student.previousSubgroup;
    }) : undefined;
    var newSubgroup = $scope.record.subgroups.find(function (el) {
      return el.id === newSubgroupId;
    });
    if (oldSubgroup) {
      oldSubgroup.declared--;
    }
    if (newSubgroup) {
      newSubgroup.declared++;
      student.previousSubgroup = newSubgroup.id;
    }
  }

}]).controller('MidtermTaskController', ['$scope', '$sessionStorage', 'QueryUtils', 'DataUtils', '$route', 'ArrayUtils', 'message', 'dialogService', 'orderByFilter', '$rootScope', function ($scope, $sessionStorage, QueryUtils, DataUtils, $route, ArrayUtils, message, dialogService, orderBy, $rootScope) {

  var Endpoint = QueryUtils.endpoint('/midtermTasks');

  $scope.subjectStudyPeriodId = $route.current.params.id;
  $scope.auth = $route.current.locals.auth;

  function afterload() {
    if(!$scope.record.midtermTasks) {
      $scope.record.midtermTasks = [];
    }
    DataUtils.convertStringToDates($scope.record.midtermTasks, ['taskDate']);

    if($scope.record.canBeEdited) {
      $scope.record.midtermTasks = orderBy($scope.record.midtermTasks, ['taskDate', $rootScope.currentLanguageNameField()]);
    }
  }

  $scope.record = Endpoint.get({id: $scope.subjectStudyPeriodId}, afterload);

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
      $scope.midtermTaskForm.$setPristine();
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
              if(!ArrayUtils.isEmpty(scope.studyPeriods) && !scope.criteria.studyPeriod) {
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

