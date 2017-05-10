'use strict';

angular.module('hitsaOis').controller('JournalController', function ($scope, $route, QueryUtils, ArrayUtils, message, dialogService) {
  $scope.auth = $route.current.locals.auth;

  $scope.showModuleNames = function(modules) {
    return modules.map(function(it) {return $scope.currentLanguageNameField(it);}).join(", ");
  };

  function entityToForm(entity) {
    $scope.journal = entity;
    if (!angular.isString(entity.endDate)) {
      $scope.journal.endDate = entity.studyYearEndDate;
    }

    $scope.hasStudents = angular.isArray(entity.journalStudents) && entity.journalStudents.length > 0;
    $scope.hasEntries = angular.isArray(entity.journalEntries) && entity.journalEntries.length > 0;

    if ($scope.hasStudents) {
      $scope.isDeletionAllowed = $scope.auth.isAdmin() || ($scope.auth.isTeacher() && !$scope.hasEntries);
      $scope.tabledata = {
        content: entity.journalStudents
      };
    } else {
      loadStudentsTable($scope, true);
      $scope.addSelectedStudents = function() {
        afterStudentsSelected($scope, function() {
          $route.reload();
        });
      };
    }
  }

  $scope.selectedStudents = [];
  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.saveEndDate = function() {
    QueryUtils.endpoint('/journals/' + entity.id + '/saveEndDate').save({endDate: $scope.journal.endDate}, function() {
      message.info('main.messages.create.success');
    });
  };

  $scope.removeStudentFormJournal = function(row) {
    QueryUtils.endpoint('/journals/' + entity.id + '/removeStudentsFromJournal').save({students: [row.studentId]}, function() {
      ArrayUtils.remove($scope.tabledata.content, row);
      if ($scope.tabledata.content.length === 0) {
        $route.reload();
      }
    });
  };

  function loadStudentsTable(scope, restrictStudentGroup) {
    scope.selectedStudents = [];
      QueryUtils.createQueryForm(scope, '/journals/' + entity.id + '/students' + (restrictStudentGroup ? '?restrictStudentGroup=true' : ''), {});
      scope.loadData();
  }

  function afterStudentsSelected(scope, done) {
    QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({students: scope.selectedStudents}, function() {
        scope.tabledata.content.forEach(function(it) {
          if (scope.selectedStudents.indexOf(it.studentId) !== -1) {
            $scope.tabledata.content.push(it);
          }
        });
        if (angular.isFunction(done)) {
          done();
        }
      });
  }

  $scope.searchStudent = function() {
    dialogService.showDialog('journal/journal.searchStudent.dialog.html', function(dialogScope) {
      loadStudentsTable(dialogScope);
    }, function(submittedDialogScope) {
      afterStudentsSelected(submittedDialogScope);
    });
  };

});
