'use strict';

angular.module('hitsaOis').controller('PracticeJournalEntryController', function ($scope, $route, $location, dialogService, oisFileService, QueryUtils, message, DataUtils, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.removeFromArray = ArrayUtils.remove;
  $scope.practiceJournal = {
    practiceJournalEntries: [],
    practiceJournalFiles: []
  };
  $scope.formState = {};

  function entityToForm(entity) {
    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);
    $scope.practiceJournal = entity;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.addNewEntryRow = function () {
    $scope.practiceJournal.practiceJournalEntries.push({ isStudentEntry: $scope.auth.isStudent() });
  };

  $scope.getEntryColor = function (entry) {
    if ($scope.auth.isStudent() && !entry.isStudentEntry) {
      return 'pink-50';
    }
  };

  $scope.openAddFileDialog = function () {
    dialogService.showDialog('practiceJournal/practice.journal.entry.add.file.dialog.html', function (dialogScope) {
      dialogScope.addedFiles = $scope.practiceJournal.practiceJournalFiles;
    }, function (submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function (file) {
        data.oisFile = file;
        $scope.practiceJournal.practiceJournalFiles.push(data);
      });
    });
  };

  $scope.getUrl = oisFileService.getUrl;

  var baseEndpointUrl = '/practiceJournals/' + $scope.practiceJournal.id + '/saveEntries/';
  $scope.save = function () {
    var practiceJournalEntries;
    if ($scope.auth.isStudent()) {
      var EndpointStudent = QueryUtils.endpoint(baseEndpointUrl + 'student');
      practiceJournalEntries = new EndpointStudent(
        {
          practiceJournalEntries: $scope.practiceJournal.practiceJournalEntries,
          practiceReport: $scope.practiceJournal.practiceReport
        });
    } else {
      var EndpointTeacher = QueryUtils.endpoint(baseEndpointUrl + 'teacher');
      practiceJournalEntries = new EndpointTeacher(
        {
          teacherComment: $scope.practiceJournal.teacherComment,
          teacherOpinion: $scope.practiceJournal.teacherOpinion,
          grade: $scope.practiceJournal.grade,
          practiceJournalFiles: $scope.practiceJournal.practiceJournalFiles,
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

});
