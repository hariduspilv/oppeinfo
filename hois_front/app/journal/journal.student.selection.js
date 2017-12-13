'use strict';

angular.module('hitsaOis').controller('JournalStudentSelectionController', function ($scope, $route, QueryUtils, ArrayUtils, message, dialogService) {
  var entity = $route.current.locals.entity;

  $scope.selectedStudents = [];
  $scope.addSelectedStudents = function() {
    QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({students: $scope.selectedStudents}, $route.reload);
  };

  QueryUtils.endpoint('/journals/' + entity.id + '/suitedStudents').query({}, function(result) {
    $scope.tabledata = {
      content: result
    };
    result.forEach(function(it) {
      $scope.selectedStudents.push(it.studentId);
    });
  });

  $scope.searchStudent = function() {
    dialogService.showDialog('journal/journal.searchStudent.dialog.html', function(dialogScope) {
      dialogScope.selectedStudents = [];

      // code below required for not querying for students already present on the form,
      // thus avoiding double adding of the same student to the journal
      var students = [];
      if($scope.tabledata.content) {
        students = $scope.tabledata.content.map(function(s){
          return s.studentId;
        });
      }
      $route.current.params._menu = true;

      QueryUtils.createQueryForm(dialogScope, '/journals/' + entity.id + '/otherStudents', {
        studentId: students
      });
      dialogScope.loadData();
    }, function(submittedDialogScope) {
      submittedDialogScope.tabledata.content.forEach(function(it) {
        if (submittedDialogScope.selectedStudents.indexOf(it.studentId) !== -1) {
          $scope.tabledata.content.push(it);
          $scope.selectedStudents.push(it.studentId);
        }
      });
    });
  };

  $scope.removeStudent = function(row) {
    ArrayUtils.remove($scope.selectedStudents, row.studentId);
    ArrayUtils.remove($scope.tabledata.content, row);
  };
});
