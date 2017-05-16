'use strict';

angular.module('hitsaOis').controller('JournalEditController', function ($scope, $route, QueryUtils, ArrayUtils, Classifier, message, dialogService) {
  function loadJournalEntries() {
    $scope.journalEntriesCriteria = {size: 10, page: 1};
    if (!angular.isDefined($scope.journalEntries)) {
      $scope.journalEntries = {};
    }
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

  function loadJournalStudents() {
    var mainQueryPromise = QueryUtils.endpoint('/journals/' + entity.id + '/journalStudents').query().$promise;

    QueryUtils.endpoint('/journals/' + entity.id + '/journalEntriesByDate').get(function(result) {
      var journalEntriesByDate = [];
      for(var property in result) {
        if (result.hasOwnProperty(property)) {
          var date = new Date(property);
          if (date.getTime() > 0) {
            journalEntriesByDate.push({date: date, journalEntryStudents: result[property]});
          }
        }
      }
      $scope.journal.journalEntriesByDate = journalEntriesByDate;

      mainQueryPromise.then(function(result) {
        $scope.journal.journalStudents = result;
      });
    });
  }

  function entityToForm(entity) {
    $scope.journal = entity;
    loadJournalEntries();
    loadJournalStudents();
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
      QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({students: submittedDialogScope.selectedStudents}, function() {
        message.info('journal.messages.studentSuccesfullyAdded');
        loadJournalStudents();
      });
    });
  };

  $scope.removeStudentFromJournal = function(studentId) {
    dialogService.confirmDialog({prompt: 'journal.studentDeleteconfirm'}, function() {
      QueryUtils.endpoint('/journals/' + entity.id + '/removeStudentsFromJournal').save({students: [studentId]}, function() {
        message.info('journal.messages.studentSuccesfullyRemoved');
        loadJournalStudents();
      });
    });
  };

  $scope.journalEntryTypeColors = {
    'SISSEKANNE_T': 'grey-50',
    'SISSEKANNE_E': 'amber-100',
    'SISSEKANNE_I': 'lime-100',
    'SISSEKANNE_H': 'pink-50',
    'SISSEKANNE_L': 'pink-300'
  };
  $scope.journalEntryTypes = {};
  Classifier.queryForDropdown({mainClassCode: 'SISSEKANNE'}, function(response) {
    response.forEach(function(it) {
      $scope.journalEntryTypes[it.code] = it;
    });
  });
  $scope.getEntryColor = function(type) {
    return $scope.journalEntryTypeColors[type];
  };


  var JournalEntryEndpoint = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry');

  function loadJournalEntryDialogInitialData(dialogScope) {
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
  }

  function showEntryDialog(editEntity) {
    dialogService.showDialog('journal/journal.addEntry.dialog.html', function(dialogScope) {
      dialogScope.journalEntry = {
        capacityTypes: {},
        journalEntryStudents: []
      };
      loadJournalEntryDialogInitialData(dialogScope);
      if (angular.isDefined(editEntity)) {
        angular.extend(dialogScope.journalEntry, editEntity);
      }
      dialogScope.journalEntryStudentChanged = function(journalEntry, index, row) {
        journalEntry.journalEntryStudents[index].journalStudent = row.id;
      };
    }, function(submittedDialogScope) {
      var journalEntry = new JournalEntryEndpoint(submittedDialogScope.journalEntry);
      if (angular.isDefined(submittedDialogScope.journalEntry.id)) {
        journalEntry.$update().then(function() {
          message.info('main.messages.create.success');
          loadJournalEntries();
        });
      } else {
        journalEntry.$save().then(function() {
          message.info('main.messages.create.success');
          loadJournalEntries();
        });
      }
    });
  }

  $scope.addNewEntry = function() {
    showEntryDialog();
  };

  $scope.editJournalEntry = function(journalEntryId) {
    JournalEntryEndpoint.get({id: journalEntryId}, function(response) {
      showEntryDialog(response);
    });
  };

});
