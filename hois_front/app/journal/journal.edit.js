'use strict';

angular.module('hitsaOis').controller('JournalEditController', function ($scope, $route, $filter, $window, QueryUtils, ArrayUtils, DataUtils, Classifier, message, dialogService, VocationalGradeUtil, $q, oisFileService) {
  $scope.gradeUtil = VocationalGradeUtil;
  var classifierMapper = Classifier.valuemapper({ entryType: 'SISSEKANNE', grade: 'KUTSEHINDAMINE', absence: 'PUUDUMINE' });

  var LESSONS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

  $scope.formState = {};
  $scope.showAllStudentsModel = true;

  function loadUsedHours() {
    QueryUtils.endpoint('/journals/' + entity.id + '/usedHours').get().$promise.then(function (result) {
      $scope.journal.lessonHours = result;
    });
  }

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
          it.journalStudentResults[p][0].journalEntryStudentHistories.forEach(function (history) {
            classifierMapper.objectmapper(history);
          });
        }
      });
      $scope.journal.journalEntriesByDate = journalEntriesByDate;

      journalStudentsQueryPromise.then(function (result) {
        $scope.journal.journalStudents = result;
        $scope.$broadcast('refreshFixedColumns');
        $scope.windowHeight = $window.innerHeight;

      });
    });
  }

    $scope.finished=function ()
    {
      var hh, tb;


      hh = angular.element(document.getElementsByClassName("journalTable"))[0].clientHeight;
      tb = angular.element(document.getElementsByClassName("journalTable")).find("tbody")[0].clientHeight;
      hh=39+tb;

      if($scope.journal.journalStudents && $scope.journal.journalStudents.length > 10)
      {
        angular.element(document.getElementsByClassName("container")).css('height', $scope.journal.journalStudents.length*32+39+18+ 'px');
      }
      else
      {
        angular.element(document.getElementsByClassName("container")).css('height', hh +18+ 'px');
      }

    };

  angular.element($window).bind('resize', function(){
    $scope.windowHeight = $window.innerHeight;
 });

  function entityToForm(entity) {
    $scope.journal = entity;
    $scope.$parent.$parent.moodleCourseId = entity.moodleCourseId;
    loadJournalEntries();
    loadJournalStudents($scope.showAllStudentsModel);
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

  function reloadEntriesAndStudents() {
    entityToForm(entity);
  }

  $scope.searchStudent = function () {
    dialogService.showDialog('journal/journal.searchStudent.dialog.html', function (dialogScope) {
      dialogScope.selectedStudents = [];
      QueryUtils.createQueryForm(dialogScope, '/journals/' + entity.id + '/otherStudents', {});
      dialogScope.loadData();
    }, function (submittedDialogScope) {
      QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({ students: submittedDialogScope.selectedStudents }, function () {
        message.info('journal.messages.studentSuccesfullyAdded');
        loadJournalStudents($scope.showAllStudentsModel);
      });
    });
  };

  $scope.enrollStudents = function () {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/enrollStudents').save(null, function (moodleResponse) {
      dialogService.showDialog('components/moodle.enroll.dialog.html', function (dialogScope) {
        dialogScope.moodleResponse = moodleResponse;
      }, reloadEntriesAndStudents, reloadEntriesAndStudents);
    });
  };
  $scope.importGradeItems = function () {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/importGradeItems').save(null, function () {
      message.info('moodle.messages.gradeItemsImported');
      reloadEntriesAndStudents();
    });
  };
  $scope.importAllGrades = function () {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/importAllGrades').save(null, function () {
      message.info('moodle.messages.allGradesImported');
      reloadEntriesAndStudents();
    });
  };
  $scope.importMissingGrades = function () {
    QueryUtils.endpoint('/journals/' + entity.id + '/moodle/importMissingGrades').save(null, function () {
      message.info('moodle.messages.missingGradesImported');
      reloadEntriesAndStudents();
    });
  };

  $scope.removeStudentFromJournal = function (studentId) {
    dialogService.confirmDialog({ prompt: 'journal.studentDeleteconfirm' }, function () {
      QueryUtils.endpoint('/journals/' + entity.id + '/removeStudentsFromJournal').save({ students: [studentId] }, function () {
        message.info('journal.messages.studentSuccesfullyRemoved');
        loadJournalStudents($scope.showAllStudentsModel);
      });
    });
  };

  $scope.journalEntryTypeColors = {
    'SISSEKANNE_T': 'grey-50',
    'SISSEKANNE_E': 'amber-100',
    'SISSEKANNE_I': 'lime-100',
    'SISSEKANNE_H': 'pink-50',
    'SISSEKANNE_L': 'pink-300',
    'SISSEKANNE_O': 'light-blue-50',
    'SISSEKANNE_P': 'teal-100',
    'SISSEKANNE_R': 'indigo-100'
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
    dialogScope.capacityTypes =  Classifier.queryForDropdown({ mainClassCode: 'MAHT', vocational: true });
    dialogScope.lessonPlanDates = $scope.formState.lessonInfo.lessonPlanDates.map(function (it) {
      var value = $filter('hoisDate')(it);
      return { nameEt: value, nameEn: value, id: it };
    });
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
    dialogScope.forbiddenEntryTypes.push('SISSEKANNE_O');
  }

  function showEntryDialog(editEntity) {
    dialogService.showDialog('journal/journal.addEntry.dialog.html', function (dialogScope) {
      dialogScope.journal = $scope.journal;

      dialogScope.$watch('journalEntry.entryType', function() {
        if (dialogScope.journalEntry.entryType === 'SISSEKANNE_L') {
          if ($scope.journal.isDistinctiveAssessment) {
            dialogScope.filterGrades = ['KUTSEHINDAMINE_X', 'KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_MA'];
          } else {
            dialogScope.filterGrades = ['KUTSEHINDAMINE_1', 'KUTSEHINDAMINE_2', 'KUTSEHINDAMINE_3', 'KUTSEHINDAMINE_4', 'KUTSEHINDAMINE_5'];
          }
        } else {
          dialogScope.filterGrades = [];
        }
      });

      dialogScope.entryDateChanged = function () {
        if(!dialogScope.journalEntry.entryDate) {
          setHasAcceptedAbsence(null);
          return;
        }
        QueryUtils.endpoint('/journals/' + entity.id + '/studentsWithAcceptedAbsence')
        .query({entryDate: dialogScope.journalEntry.entryDate}).$promise.then(function(response){
          setHasAcceptedAbsence(response);
          dialogScope.previousEntryDate = dialogScope.journalEntry.entryDate;
        });
      };

      function setHasAcceptedAbsence(absences) {
        dialogScope.journalStudents.forEach(function(js) {
          js.hasAcceptedAbsence = absences !== null && ArrayUtils.contains(absences, js.id);

          if (!dialogScope.journalEntryStudents[js.id]) {
            dialogScope.journalEntryStudents[js.id] = {};
          }

          if (js.hasAcceptedAbsence) {
            dialogScope.journalEntryStudents[js.id].excused = true;
            dialogScope.journalEntryStudentAbsenceChanged(dialogScope.journalEntryStudents, js, dialogScope.journalEntryStudents[js.id].excused, 'PUUDUMINE_V');
          } else {
            dialogScope.journalEntryStudents[js.id].excused = false;
            if (dialogScope.journalEntryStudents[js.id].absence === 'PUUDUMINE_V') {
              dialogScope.journalEntryStudentAbsenceChanged(dialogScope.journalEntryStudents, js, null, null);
            }
          }
        });
      }

      dialogScope.hasAcceptedAbsence = function(row) {
        var js = dialogScope.journalEntryStudents[row.id];
        return js && js.absence === 'PUUDUMINE_V' || row.hasAcceptedAbsence;
      };

      function setAbsenceCheckboxValue(it) {
        switch (it.absence) {
          case "PUUDUMINE_V":
            it.excused = true;
            break;
          case "PUUDUMINE_H":
            it.late = true;
            break;
          case "PUUDUMINE_P":
            it.withoutReason = true;
            break;
        }
      }

      dialogScope.journalEntry = {};
      dialogScope.selectedCapacityTypes = {};
      dialogScope.journalEntryStudents = {};
      dialogScope.forbiddenEntryTypes = [];

      function canDeleteEntries() {
        return dialogScope.journalEntry.entryType !== 'SISSEKANNE_O';
      }

      loadJournalEntryDialogInitialData(dialogScope);
      if (angular.isDefined(editEntity)) {
        angular.extend(dialogScope.journalEntry, editEntity);
        dialogScope.entryDateCalendar = dialogScope.journalEntry.entryDate;
        editEntity.journalEntryStudents.forEach(function (it) {
          dialogScope.journalEntryStudents[it.journalStudent] = it;
          DataUtils.convertStringToDates(it.journalEntryStudentHistories, ["gradeInserted"]);
          classifierMapper.objectmapper(it.journalEntryStudentHistories);
          setAbsenceCheckboxValue(it);
          setEditEntityhasAcceptedAbsence(it.journalStudent, it.absence);
        });
        dialogScope.savedJournalEntryStudents = angular.copy(dialogScope.journalEntryStudents);
        editEntity.journalEntryCapacityTypes.forEach(function (it) {
          dialogScope.selectedCapacityTypes[it] = true;
        });
        dialogScope.canDeleteEntries = canDeleteEntries();
      } else {
        if (dialogScope.journalStudents) {
          dialogScope.journalStudents.forEach(function (js) {
            js.hasAcceptedAbsence = false;
          });
        }
      }

      function setEditEntityhasAcceptedAbsence(studentId, absenceCode) {
        for (var i = 0; i < dialogScope.journalStudents.length; i++) {
          if (dialogScope.journalStudents[i].id === studentId) {
            dialogScope.journalStudents[i].hasAcceptedAbsence = absenceCode === 'PUUDUMINE_V' ? true : false;
          }
        }
      }

      setForbiddenTypes(dialogScope, editEntity);

      function changeAbsenceCheckboxValues(journalEntryStudents, row, absence) {
        switch (absence) {
          case "PUUDUMINE_V":
            journalEntryStudents[row.id].withoutReason = false;
            journalEntryStudents[row.id].late = false;
          break;
          case "PUUDUMINE_H":
            journalEntryStudents[row.id].excused = false;
            journalEntryStudents[row.id].withoutReason = false;
            break;
          case "PUUDUMINE_P":
            journalEntryStudents[row.id].excused = false;
            journalEntryStudents[row.id].late = false;
            break;
        }
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
      dialogScope.journalEntryStudentAbsenceChanged = function (journalEntryStudents, row, checkboxValue, absence) {
        if (!angular.isObject(journalEntryStudents[row.id])) {
          journalEntryStudents[row.id] = {};
        }
        journalEntryStudents[row.id].journalStudent = row.id;
        journalEntryStudents[row.id].absence = checkboxValue ? absence : null;
        if (dialogScope.changedJournalEntryStudents.indexOf(journalEntryStudents[row.id]) === -1) {
          dialogScope.changedJournalEntryStudents.push(journalEntryStudents[row.id]);
        }
        changeAbsenceCheckboxValues(journalEntryStudents, row, absence);
      };
      dialogScope.setJournalEntryDefaultName = function (name) {
        var holder = { entryType: name };
        classifierMapper.objectmapper(holder);
        dialogScope.journalEntry.nameEt = holder.entryType.nameEt;
      };
      dialogScope.grades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });

      dialogScope.delete = function () {
        dialogService.confirmDialog({prompt: 'journal.entryDeleteConfirm'}, function() {
          new JournalEntryEndpoint(dialogScope.journalEntry).$delete().then(function () {
            message.info('main.messages.delete.success');
            loadUsedHours();
            loadJournalEntries();
            loadJournalStudents($scope.showAllStudentsModel);
          }).catch(angular.noop);
        });
      };
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
          loadUsedHours();
          loadJournalEntries();
          loadJournalStudents($scope.showAllStudentsModel);
        });
      } else {
        journalEntry.$save().then(function () {
          message.info('main.messages.create.success');
          loadUsedHours();
          loadJournalEntries();
          loadJournalStudents($scope.showAllStudentsModel);
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
      dialogScope.isDistinctiveAssessment = moduleDescription.assessment === 'KUTSEHINDAMISVIIS_E' ? true : false;
    });
  };

  $scope.showAllStudents = function (show) {
    loadJournalStudents(show);
  };

  var ConfirmEndpoint = QueryUtils.endpoint('/journals/confirm');
  var UnconfirmEndpoint = QueryUtils.endpoint('/journals/unconfirm');

  function confirm() {
    new ConfirmEndpoint().$update({id: $scope.journal.id}).then(function (response) {
      message.info('journal.messages.confirmed');
      $scope.journal.canBeConfirmed = response.canBeConfirmed;
      $scope.journal.canBeUnconfirmed = response.canBeUnconfirmed;
      $scope.$parent.journal.status = response.status;
    });
  }

  $scope.confirm = function() {
    dialogService.confirmDialog({ prompt: 'journal.prompt.confirm' }, function () {
      QueryUtils.endpoint('/journals/' + $scope.journal.id + '/withoutFinalResult/').query().$promise.then(function(response){
        if(!ArrayUtils.isEmpty(response)) {
          var withoutFinalResult = response.map(function(student){
            return student.fullname;
          }).join(', ');
          dialogService.confirmDialog({ prompt: 'journal.prompt.someStudentsDoNotHaveFinalResult', students: withoutFinalResult  }, confirm);
        } else {
          confirm();
        }
      });
    });
  };

  $scope.unconfirm = function() {
    dialogService.confirmDialog({ prompt: 'journal.prompt.unconfirm' }, function () {
      new UnconfirmEndpoint().$update({id: $scope.journal.id}).then(function (response) {
        message.info('journal.messages.unconfirmed');
        $scope.journal.canBeConfirmed = response.canBeConfirmed;
        $scope.journal.canBeUnconfirmed = response.canBeUnconfirmed;
        $scope.$parent.journal.status = response.status;
      });
    });
  };

  $scope.getFileUrl = oisFileService.getUrl;
  var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/connect');
  var clMapper = Classifier.valuemapper({
    typeCode: 'OPPEMATERJAL'
  });
  function loadMaterials() {
    $scope.materials = QueryUtils.endpoint('/studyMaterial/journal/' + $scope.journal.id + '/materials').query();
    $scope.materials.$promise.then(function (materials) {
      $q.all(clMapper.promises).then(function () {
        clMapper.objectmapper(materials);
      });
    });
  }
  loadMaterials();
  $scope.deleteConnection = function (materialConnect, connections) {
    dialogService.confirmDialog({
      prompt: (connections > 1) ? 'studyMaterial.deleteconfirm' : 'studyMaterial.deletelastconfirm'
    }, function () {
      var connection = new ConnectEndpoint(materialConnect);
      connection.$delete().then(function () {
        message.info('main.messages.delete.success');
        loadMaterials();
      }).catch(angular.noop);
    });
  };
  $scope.addExisting = function () {
    if (!$scope.existingMaterial) {
      message.error('main.messages.form-has-errors');
      return;
    }
    var connection = new ConnectEndpoint();
    connection.studyMaterial = $scope.existingMaterial.id;
    connection.journal = $scope.journal.id;
    connection.$save().then(function () {
      $scope.existingMaterial = undefined;
      loadMaterials();
    });
  };


});
