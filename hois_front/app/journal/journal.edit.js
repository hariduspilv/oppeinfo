'use strict';

angular.module('hitsaOis').controller('JournalEditController', function ($scope, $route, $filter, QueryUtils, ArrayUtils, DataUtils, Classifier, message, dialogService) {
  var classifierMapper = Classifier.valuemapper({ entryType: 'SISSEKANNE', grade: 'KUTSEHINDAMINE', absence: 'PUUDUMINE' });

  var LESSON_NRS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  var LESSONS = [1, 2, 3, 4, 5];

  $scope.formState = {};

  function loadJournalStudents(showNonStudying) {
    var journalStudentsQueryPromise = QueryUtils.endpoint('/journals/' + entity.id + '/journalStudents').query({ allStudents: !!showNonStudying }).$promise;

    QueryUtils.endpoint('/journals/' + entity.id + '/journalEntriesByDate').query({ allStudents: !!showNonStudying }, function (result) {
      var journalEntriesByDate = result;
      classifierMapper.objectmapper(journalEntriesByDate);
      journalEntriesByDate.forEach(function (it) {
        for (var p in it.journalStudentResults) {
          if (it.journalStudentResults.hasOwnProperty(p)) {
            classifierMapper.objectmapper(it.journalStudentResults[p]);
          }
        }
      });
      $scope.journal.journalEntriesByDate = journalEntriesByDate;

      journalStudentsQueryPromise.then(function (result) {
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
    $scope.formState.lessonInfo = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry/lessonInfo').get();
    $scope.formState.xlsUrl = 'journals/'+ entity.id + '/journal.xls';
    entityToForm(entity);
  }

    function loadJournalEntries() {
    $scope.journalEntriesCriteria = { size: 20, page: 1 };
    if (!angular.isDefined($scope.journalEntries)) {
      $scope.journalEntries = {};
    }

    $scope.loadJournalEntries = function () {
      var query = QueryUtils.getQueryParams($scope.journalEntriesCriteria);
      $scope.journalEntries.$promise = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry').search(query, $scope.afterLoadJournalEntries);
    };

    $scope.afterLoadJournalEntries = function (result) {
      $scope.journalEntriesCriteria.size = result.size;
      $scope.journalEntriesCriteria.page = result.number + 1;
      $scope.journalEntries.content = result.content;
      $scope.journalEntries.totalElements = result.totalElements;
    };
    $scope.loadJournalEntries();
  }

  $scope.searchStudent = function () {
    dialogService.showDialog('journal/journal.searchStudent.dialog.html', function (dialogScope) {
      dialogScope.selectedStudents = [];
      QueryUtils.createQueryForm(dialogScope, '/journals/' + entity.id + '/otherStudents', {});
      dialogScope.loadData();
    }, function (submittedDialogScope) {
      QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({ students: submittedDialogScope.selectedStudents }, function () {
        message.info('journal.messages.studentSuccesfullyAdded');
        loadJournalStudents();
      });
    });
  };

  $scope.removeStudentFromJournal = function (studentId) {
    dialogService.confirmDialog({ prompt: 'journal.studentDeleteconfirm' }, function () {
      QueryUtils.endpoint('/journals/' + entity.id + '/removeStudentsFromJournal').save({ students: [studentId] }, function () {
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
  Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (response) {
    response.forEach(function (it) {
      $scope.journalEntryTypes[it.code] = it;
    });
  });
  $scope.getEntryColor = function (type) {
    return $scope.journalEntryTypeColors[type];
  };


  var JournalEntryEndpoint = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry');

  function loadJournalEntryDialogInitialData(dialogScope) {
    dialogScope.journalStudents = entity.journalStudents;
    dialogScope.absenceOptions = {};
    dialogScope.capacityTypes = Classifier.queryForDropdown({ mainClassCode: 'MAHT' });
    dialogScope.lessonPlanDates = $scope.formState.lessonInfo.lessonPlanDates.map(function (it) {
      var value = $filter('hoisDate')(it);
      return { nameEt: value, nameEn: value, id: it };
    });
    dialogScope.startLessonNrs = LESSON_NRS.map(function (it) { return { nameEt: it, nameEn: it, id: it }; });
    dialogScope.journalEntry.startLessonNr = $scope.formState.lessonInfo.startLessonNr;
    dialogScope.lessons = LESSONS.map(function (it) { return { nameEt: it, nameEn: it, id: it }; });
    dialogScope.journalEntry.lessons = $scope.formState.lessonInfo.lessons;
    Classifier.queryForDropdown({ mainClassCode: 'PUUDUMINE' }, function (response) {
      response.forEach(function (it) {
        dialogScope.absenceOptions[it.code] = it;
      });
    });
  }

  function capacityTypesToArray(selectedCapacityTypes, newEntity) {
    var capacityTypesArray = [];
    if (angular.isObject(selectedCapacityTypes)) {
      for (var p in selectedCapacityTypes) {
        if (selectedCapacityTypes.hasOwnProperty(p) && selectedCapacityTypes[p] === true) {
          capacityTypesArray.push(p);
        }
      }
      newEntity.journalEntryCapacityTypes = capacityTypesArray;
    }
  }

  function showEntryDialog(editEntity) {
    dialogService.showDialog('journal/journal.addEntry.dialog.html', function (dialogScope) {
      dialogScope.journalEntry = {};
      dialogScope.selectedCapacityTypes = {};
      dialogScope.journalEntryStudents = {};

      loadJournalEntryDialogInitialData(dialogScope);
      if (angular.isDefined(editEntity)) {
        angular.extend(dialogScope.journalEntry, editEntity);
        dialogScope.entryDateCalendar = dialogScope.journalEntry.entryDate;
        editEntity.journalEntryStudents.forEach(function (it) {
          dialogScope.journalEntryStudents[it.journalStudent] = it;
          DataUtils.convertStringToDates(it.journalEntryStudentHistories, ["gradeInserted"]);
          classifierMapper.objectmapper(it.journalEntryStudentHistories);
        });
        editEntity.journalEntryCapacityTypes.forEach(function (it) {
          dialogScope.selectedCapacityTypes[it] = true;
        });
      }

      dialogScope.changedJournalEntryStudents = [];
      dialogScope.journalEntryStudentChanged = function (journalEntryStudents, row) {
        if (!angular.isObject(journalEntryStudents[row.id])) {
          journalEntryStudents[row.id] = {};
        }
        journalEntryStudents[row.id].journalStudent = row.id;
        if (dialogScope.changedJournalEntryStudents.indexOf(journalEntryStudents[row.id]) === -1) {
          dialogScope.changedJournalEntryStudents.push(journalEntryStudents[row.id]);
        }
      };
      dialogScope.setJournalEntryDefaultName = function (name) {
        var holder = { entryType: name };
        classifierMapper.objectmapper(holder);
        dialogScope.journalEntry.nameEt = holder.entryType.nameEt;
      };
      dialogScope.grades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
    }, function (submittedDialogScope) {
      var newEntity = angular.extend({}, submittedDialogScope.journalEntry);
      newEntity.journalEntryStudents = submittedDialogScope.changedJournalEntryStudents;
      capacityTypesToArray(submittedDialogScope.selectedCapacityTypes, newEntity);

      // this prevents an exception in back end - '' is sent if empty date is selected otherwise
      if(!newEntity.entryDate) {
        delete newEntity.entryDate;
      }

      var journalEntry = new JournalEntryEndpoint(newEntity);
      if (angular.isDefined(newEntity.id)) {
        journalEntry.$update().then(function () {
          message.info('main.messages.create.success');
          loadJournalEntries();
          loadJournalStudents();
        });
      } else {
        journalEntry.$save().then(function () {
          message.info('main.messages.create.success');
          loadJournalEntries();
          loadJournalStudents();
        });
      }
    });
  }

  $scope.addNewEntry = function () {
    showEntryDialog();
  };

  $scope.editJournalEntry = function (journalEntryId) {
    JournalEntryEndpoint.get({ id: journalEntryId }, function (response) {
      showEntryDialog(response);
    });
  };

  $scope.moduleDescriptionDialog = function (moduleDescription) {
    dialogService.showDialog('journal/journal.moduleDescription.dialog.html', function (dialogScope) {
      dialogScope.moduleDescription = moduleDescription;
    });
  };

  $scope.showAllStudents = function (show) {
    loadJournalStudents(show);
  };
});
