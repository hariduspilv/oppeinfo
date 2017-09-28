'use strict';

angular.module('hitsaOis').controller('PracticeJournalSupervisorEntryController', function ($scope, $route, dialogService, oisFileService, QueryUtils, message, DataUtils, ArrayUtils) {

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

  var Endpoint = QueryUtils.endpoint('/practiceJournals/supervisor/' + $route.current.params.uuid + '/saveEntries/');
  $scope.save = function () {
    var practiceJournalEntries = new Endpoint(
      {
        supervisorOpinion: $scope.practiceJournal.supervisorOpinion,
        supervisorComment: $scope.practiceJournal.supervisorComment,
        practiceJournalFiles: $scope.practiceJournal.practiceJournalFiles,
        practiceJournalEntries: $scope.practiceJournal.practiceJournalEntries
      });

    practiceJournalEntries.$update().then(function (result) {
      message.info('main.messages.create.success');
      entityToForm(result);
    });
  };

});
