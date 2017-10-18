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
  Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (result) {
    $scope.journalEntryTypes = Classifier.toMap(result);
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
    Classifier.queryForDropdown({ mainClassCode: 'PUUDUMINE' }, function (result) {
      dialogScope.absenceOptions = Classifier.toMap(result);
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


  function setForbiddenTypes(dialogScope, journalEntry) {
    QueryUtils.endpoint('/journals/' + entity.id + '/hasFinalEntry').search().$promise.then(function(response){
      if(response.hasFinalEntry) {
        if(journalEntry) {
          if(journalEntry.entryType !== 'SISSEKANNE_L') {
            dialogScope.forbiddenEntryTypes.push('SISSEKANNE_L');
          }
        } else {
          dialogScope.forbiddenEntryTypes.push('SISSEKANNE_L');
        }
      }
    });
  }

  function showEntryDialog(editEntity) {
    dialogService.showDialog('journal/journal.addEntry.dialog.html', function (dialogScope) {

      dialogScope.$watch('journalEntry.entryDate', function(){
        if(!dialogScope.journalEntry.entryDate) {
          setHasAcceptedAbsence(null);
          return;
        }
        QueryUtils.endpoint('/journals/' + entity.id + '/studentsWithAcceptedAbsence')
        .query({entryDate: dialogScope.journalEntry.entryDate}).$promise.then(function(response){
          setHasAcceptedAbsence(response);
        });
      });

      function setHasAcceptedAbsence(absences) {

        dialogScope.journalStudents.forEach(function(js){
          js.hasAcceptedAbsence = absences !== null && ArrayUtils.contains(absences, js.id);
        });
      }

      dialogScope.hasAcceptedAbsence = function(row) {
        var js = dialogScope.journalEntryStudents[row.id];
        return js && js.absence === 'PUUDUMINE_V' || row.hasAcceptedAbsence;
      };

      dialogScope.journalEntry = {};
      dialogScope.selectedCapacityTypes = {};
      dialogScope.journalEntryStudents = {};
      dialogScope.forbiddenEntryTypes = [];

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

      setForbiddenTypes(dialogScope, editEntity);

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

  var ConfirmEndpoint = QueryUtils.endpoint('/journals/confirm/');
  var UnconfirmEndpoint = QueryUtils.endpoint('/journals/unconfirm/');

    $scope.confirm = function() {
      dialogService.confirmDialog({ prompt: 'journal.prompt.confirm' }, function () {
        new ConfirmEndpoint($scope.journal).$update().then(function (response) {
          message.info('journal.messages.confirmed');
          $scope.journal.canBeConfirmed = response.canBeConfirmed;
          $scope.journal.canBeUnconfirmed = response.canBeUnconfirmed;
          $scope.$parent.journal.status = response.status;
        });
      });
    };

    $scope.unconfirm = function() {
      dialogService.confirmDialog({ prompt: 'journal.prompt.unconfirm' }, function () {
        new UnconfirmEndpoint($scope.journal).$update().then(function (response) {
          message.info('journal.messages.unconfirmed');
          $scope.journal.canBeConfirmed = response.canBeConfirmed;
          $scope.journal.canBeUnconfirmed = response.canBeUnconfirmed;
          $scope.$parent.journal.status = response.status;
        });
      });
    };
});
