'use strict';

angular.module('hitsaOis').controller('JournalEditController', function ($scope, $route, QueryUtils, ArrayUtils, Classifier, message, dialogService) {


  function entityToForm(entity) {
    $scope.journal = entity;

    $scope.journalEntriesCriteria = {size: 10, page: 1};
    $scope.journalEntries = {};
    $scope.loadJournalEntries = function() {
      var query = QueryUtils.getQueryParams($scope.journalEntriesCriteria);
      $scope.journalEntries.$promise = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry').search(query, $scope.afterLoadJournalEntries);
    };
    $scope.afterLoadJournalEntries = function(result) {
      $scope.journalEntriesCriteria.size = result.size;
      $scope.journalEntriesCriteria.page = result.number + 1;
      $scope.journalEntries.content = result.content;
      $scope.journalEntries.totalElements = result.totalElements;
    };
    $scope.loadJournalEntries();
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

    $scope.searchStudent = function() {
    dialogService.showDialog('journal/journal.searchStudent.dialog.html', function(dialogScope) {
      dialogScope.selectedStudents = [];
      QueryUtils.createQueryForm(dialogScope, '/journals/' + entity.id + '/otherStudents', {});
      dialogScope.loadData();
    }, function(submittedDialogScope) {
      $scope.saveSelectedStudents(submittedDialogScope);
    });
  };

  var JournalEntryEndpoint = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry');
  $scope.addNewEntry = function() {
    dialogService.showDialog('journal/journal.addEntry.dialog.html', function(dialogScope) {
      dialogScope.journalEntry = {
        capacityTypes: {},
        journalEntryStudents: []
      };

      dialogScope.journalStudents = entity.journalStudents;
      dialogScope.absenceOptions = {};
      dialogScope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
      var lessonInfo = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry/lessonInfo').get();
      dialogScope.lessonPlanDates = lessonInfo.lessonPlanDates;
      dialogScope.journalEntry.startLessonNr = lessonInfo.startLessonNr;
      dialogScope.journalEntry.lessons = lessonInfo.lessons;
      Classifier.queryForDropdown({mainClassCode: 'PUUDUMINE'}, function(response) {
        response.forEach(function(it) {
          dialogScope.absenceOptions[it.code] = it;
        });
      });



    }, function(submittedDialogScope) {
      var journalEntry = new JournalEntryEndpoint(submittedDialogScope.journalEntry);
      journalEntry.$save().then(function() {
        message.info('main.messages.create.success');
        $route.reload();
      });
    });
  };

});
