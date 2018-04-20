'use strict';

angular.module('hitsaOis').controller('StudentGroupYearTransferController', ['$scope', 'QueryUtils', '$translate', 'message', 'dialogService', 'Classifier', 'DataUtils',
  function ($scope, QueryUtils, $translate, message, dialogService, Classifier, DataUtils) {

    var baseUrl = '/studentGroupYearTransfer';
    var clMapper = Classifier.valuemapper({mismatchCode: 'OPPERYHM_EISOBI'});
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $scope.canTransfer = false;
    $scope.studyYears = QueryUtils.endpoint(baseUrl + '/studyYears').query();

    function getStudyYear(studyYearId) {
      return $scope.studyYears.filter(function(studyYear) {
        return studyYear.id === studyYearId;
      })[0];
    }
    
    function getBaseParams() {
      return {
        id: $scope.studyYear,
        lang: $translate.use().toUpperCase()
      };
    }

    function getCheckGroupValidThru(studentGroups) {
      if (!$scope.canTransfer) {
        return null;
      }
      var studyYearStart = getStudyYear($scope.studyYear).startDate;
      return studentGroups.filter(function(studentGroup) {
        return !studentGroup.transfered && studentGroup.validThru < studyYearStart;
      }).map(function(studentGroup) {
        return studentGroup.oldCode;
      }).join(', ');
    }

    $scope.studyYearChanged = function() {
      $scope.canTransfer = !DataUtils.isPastStudyYearOrPeriod(getStudyYear($scope.studyYear));
      $scope.studentGroups = Endpoint.query(getBaseParams(), function(result) {
        $scope.checkGroupValidThru = getCheckGroupValidThru(result);
      });
      $scope.multiSelect = false;
      $scope.calculated = false;
      $scope.mismatchingStudents = [];
    };

    $scope.multiSelectChanged = function() {
      $scope.studentGroups.filter(function(studentGroup) {
        return !studentGroup.transfered;
      }).forEach(function(studentGroup) {
        studentGroup.selected = $scope.multiSelect;
      });
      $scope.selectChanged();
    };

    $scope.selectChanged = function() {
      if ($scope.calculated) {
        $scope.calculated = false;
        dialogService.messageDialog({prompt: 'studentGroupYearTransfer.recalculate'});
      }
    };

    function getSelectedGroups() {
      return $scope.studentGroups.filter(function(studentGroup) {
        return studentGroup.selected;
      });
    }

    function getNewGroupCodes() {
      var result = {};
      getSelectedGroups().forEach(function(studentGroup) {
        result[studentGroup.id] = studentGroup.newCode;
      });
      return result;
    }

    function allFilled(objects, field) {
      var result = true;
      objects.forEach(function(o) {
        if (!o[field]) {
          result = false;
        }
      });
      return result;
    }

    function isFormValid(selectedGroups) {
      $scope.transferForm.$setSubmitted();
      if (selectedGroups.length === 0) {
        message.error('main.messages.error.atLeastOneMustBeSelected');
        return false;
      }
      if (!allFilled(selectedGroups, 'newCode')) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.calculate = function() {
      var selectedGroups = getSelectedGroups();
      if (!isFormValid(selectedGroups)) {
        return;
      }
      QueryUtils.endpoint(baseUrl + '/calculate').save(angular.extend(getBaseParams(), {
        academicLeave: $scope.academicLeave,
        academicLeaveDays: $scope.academicLeaveDays,
        abroadStudies: $scope.abroadStudies,
        abroadStudiesDays: $scope.abroadStudiesDays,
        studentGroupIds: selectedGroups.map(function(studentGroup) {
          return studentGroup.id;
        }),
        newGroupCodes: getNewGroupCodes()
      }), function(result) {
        $scope.mismatchingStudents = [];
        $scope.studentGroups.forEach(function(studentGroup) {
          var calculated = result[studentGroup.id];
          if (calculated) {
            studentGroup.logId = calculated.logId;
            studentGroup.suitableStudents = calculated.suitableStudents;
            studentGroup.unsuitableStudents = calculated.unsuitableStudents;
            calculated.mismatchingStudents.forEach(function(student) {
              $scope.mismatchingStudents.push(angular.extend(student, {group: studentGroup}));
            });
          }
        });
        clMapper.objectmapper($scope.mismatchingStudents);
        $scope.calculated = true;
      }).$promise.catch(angular.noop);
    };

    $scope.viewMatchingStudents = function(studentGroup) {
      dialogService.showDialog('studentGroupYearTransfer/students.match.dialog.html', function (dialogScope) {
        dialogScope.studentGroup = studentGroup;
        dialogScope.students = QueryUtils.endpoint(baseUrl + '/matching/' + studentGroup.logId).query();
      });
    };

    $scope.viewMismatchingStudents = function(studentGroup) {
      dialogService.showDialog('studentGroupYearTransfer/students.mismatch.dialog.html', function (dialogScope) {
        dialogScope.studentGroup = studentGroup;
        dialogScope.students = QueryUtils.endpoint(baseUrl + '/mismatching/' + studentGroup.logId).query(
          function(result) {
            clMapper.objectmapper(result);
          }
        );
      });
    };

    function getNewStudentGroups() {
      var result = {};
      $scope.mismatchingStudents.forEach(function(student) {
        result[student.id] = student.newGroup;
      });
      return result;
    }

    $scope.transfer = function() {
      var selectedGroups = getSelectedGroups();
      if (!isFormValid(selectedGroups)) {
        return;
      }
      if (!allFilled($scope.mismatchingStudents, 'newGroup')) {
        message.error('main.messages.form-has-errors');
        return;
      }
      dialogService.confirmDialog({prompt: 'studentGroupYearTransfer.confirm'}, function() {
        QueryUtils.endpoint(baseUrl + '/transfer').save({
          logIds: selectedGroups.map(function(studentGroup) {
            return studentGroup.logId;
          }),
          newGroupCodes: getNewGroupCodes(),
          newStudentGroups: getNewStudentGroups()
        }, function() {
          $scope.studyYearChanged();
        }).$promise.catch(angular.noop);
      });
    };
  }
]);
