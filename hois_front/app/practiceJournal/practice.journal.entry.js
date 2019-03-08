'use strict';

angular.module('hitsaOis').controller('PracticeJournalEntryController', function ($scope, $route, $location, dialogService, oisFileService, QueryUtils, message, DataUtils, ArrayUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.removeFromArray = ArrayUtils.remove;
  $scope.practiceJournal = {
    practiceJournalEntries: [],
    practiceJournalFiles: [],
    practiceJournalStudentFiles: []
  };
  $scope.formState = {};

  function assertPermissionToEdit(entity) {
    if ($route.current.locals.isEntryEdit === true && !entity.canAddEntries) {
      message.error('main.messages.error.nopermission');
      $location.path('');
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);
    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);
    $scope.gradesClassCode = entity.isHigher ? 'KORGHINDAMINE' : 'KUTSEHINDAMINE';
    $scope.grades = Classifier.queryForDropdown({ mainClassCode: $scope.gradesClassCode });
    $scope.practiceJournal = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.getAstronomicalHours = DataUtils.getAstronomicalHours;

  $scope.addNewEntryRow = function () {
    $scope.practiceJournal.practiceJournalEntries.push({ isStudentEntry: $scope.auth.isStudent() });
  };

  $scope.getEntryColor = function (entry) {
    if ($scope.auth.isStudent() && !entry.isStudentEntry) {
      return 'pink-50';
    }
  };

  $scope.getGrade = function (gradeCode) {
    if ($scope.grades) {
      for (var i = 0; i < $scope.grades.length; i++) {
        if ($scope.grades[i].code === gradeCode) {
          return $scope.grades[i];
        }
      } 
    }
  };

  $scope.openAddFileDialog = function (student) {
    dialogService.showDialog('practiceJournal/practice.journal.entry.add.file.dialog.html', function (dialogScope) {
      if (student) {
        dialogScope.addedFiles = $scope.practiceJournal.practiceJournalStudentFiles;
      } else {
        dialogScope.addedFiles = $scope.practiceJournal.practiceJournalFiles;
      }
    }, function (submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function (file) {
        data.oisFile = file;
        if (student) {
          data.isStudent = true;
          $scope.practiceJournal.practiceJournalStudentFiles.push(data);
        } else {
          $scope.practiceJournal.practiceJournalFiles.push(data);
        }
      });
    });
  };

  $scope.deleteEntry = function(entry) {
    ArrayUtils.remove($scope.practiceJournal.practiceJournalEntries, entry);
  }

  $scope.getUrl = oisFileService.getUrl;

  var baseEndpointUrl = '/practiceJournals/' + $scope.practiceJournal.id + '/saveEntries/';
  $scope.save = function () {

    $scope.practiceJournalEntryForm.$setSubmitted();
    if(!$scope.practiceJournalEntryForm.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }

    var practiceJournalEntries;
    var files = [];
    files = files.concat($scope.practiceJournal.practiceJournalFiles, $scope.practiceJournal.practiceJournalStudentFiles);
    var evals = [];
    evals = evals.concat($scope.practiceJournal.supervisorPracticeEvalCriteria, $scope.practiceJournal.studentPracticeEvalCriteria);
    if ($scope.auth.isStudent()) {
      var EndpointStudent = QueryUtils.endpoint(baseEndpointUrl + 'student');
      practiceJournalEntries = new EndpointStudent(
        {
          practiceJournalEntries: $scope.practiceJournal.practiceJournalEntries,
          studentPracticeEvalCriteria: evals,
          practiceReport: $scope.practiceJournal.practiceReport,
          practiceJournalStudentFiles: files
        });
    } else {
      var EndpointTeacher = QueryUtils.endpoint(baseEndpointUrl + 'teacher');
      practiceJournalEntries = new EndpointTeacher(
        {
          teacherComment: $scope.practiceJournal.teacherComment,
          teacherOpinion: $scope.practiceJournal.teacherOpinion,
          grade: $scope.practiceJournal.grade,
          practiceJournalFiles: files,
          practiceJournalEntries: $scope.practiceJournal.practiceJournalEntries
        });
    }
    practiceJournalEntries.$update().then(function (result) {
      message.info('main.messages.create.success');
      entityToForm(result);
      $scope.practiceJournalEntryForm.$setPristine();
    });
  };

  var PracticeJournalEndpoint = QueryUtils.endpoint('/practiceJournals');
  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'practiceJournal.deleteconfirm' }, function () {
      var practiceJournal = new PracticeJournalEndpoint($scope.practiceJournal);
      practiceJournal.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.path('/practiceJournals');
      });
    });
  };

  $scope.getNames = function(names) {
    return names.map(function(elem){return elem.supervisorName;}).join(', ');
  };

  $scope.deleteFile = function(file){
    dialogService.confirmDialog({ prompt: 'practiceJournal.prompt.fileDeleteConfirm' }, function () {
      ArrayUtils.remove($scope.practiceJournal.practiceJournalFiles, file);
    });
  };

  $scope.deleteStudentFile = function(file){
    dialogService.confirmDialog({ prompt: 'practiceJournal.prompt.fileDeleteConfirm' }, function () {
      ArrayUtils.remove($scope.practiceJournal.practiceJournalStudentFiles, file);
    });
  };

});
