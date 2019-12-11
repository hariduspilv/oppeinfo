'use strict';

angular.module('hitsaOis').controller('JournalEditController', function ($scope, $route, $filter, $window, QueryUtils, ArrayUtils, DataUtils, Classifier, StudentUtil, message, dialogService, VocationalGradeUtil, $q, oisFileService, stateStorageService) {
  $scope.auth = $route.current.locals.auth;
  $scope.gradeUtil = VocationalGradeUtil;
  var classifierMapper = Classifier.valuemapper({ entryType: 'SISSEKANNE', grade: 'KUTSEHINDAMINE', absence: 'PUUDUMINE' });
  var stateKey = 'journal';
  var schoolId = $route.current.locals.auth.school.id;
  $scope.grades = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMINE' });
  $scope.gradeInputRegex = '^X$|^A$|^MA$|^1$|^2$|^3$|^4$|^5$';

  // same constant that is in absence.search.js
  var LESSONS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17];
  var STUDENT_ROW_HEIGHT = 33;
  var STUDENT_ROW_WITH_INPUT_HEIGHT = 49;
  var ACCEPTED_ABSENCES = ['PUUDUMINE_V', 'PUUDUMINE_PR'];

  $scope.formState = {
    gradeInputAsSelect: true,
    showAllStudentsModel: false
  };

  var state = stateStorageService.loadState(schoolId, stateKey);

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
            mapStudentResultClassifiers(it.journalStudentResults[p]);
          }
        }
      });
      $scope.journal.journalEntriesByDate = journalEntriesByDate;

      journalStudentsQueryPromise.then(function (students) {
        $scope.journal.journalStudents = students;
        $scope.journal.journalStudents.forEach(function (student) {
          for (var p in student.apelResults) {
            if (student.apelResults.hasOwnProperty(p)) {
              classifierMapper.objectmapper(student.apelResults[p]);
            }
          }
          setStudentApelTransferredModuleResult(student);
        });
        $scope.$broadcast('refreshFixedColumns');
        $scope.windowHeight = $window.innerHeight;
      });
    });
  }

  function setStudentApelTransferredModuleResult(student) {
    if (student.apelResults) {
      var moduleResults = student.apelResults.filter(function(apelResult){
        return apelResult.isModule;
      });
      if (moduleResults.length > 0) {
        // if there are more than one module that has results, set grade as 'KUTSEHINDAMINE_A'
        student.apelTransferredFinalResult = moduleResults.length === 1 ? moduleResults[0].grade.code : 'KUTSEHINDAMINE_A';
      }
    }
  }

  function mapStudentResultClassifiers(result) {
    classifierMapper.objectmapper(result);
    result[0].lessonAbsences.forEach(function (absence) {
      classifierMapper.objectmapper(absence);
    });
    result[0].journalEntryStudentHistories.forEach(function (history) {
      classifierMapper.objectmapper(history);
    });
  }

  $scope.hideInvalid = function (cl) {
    return !Classifier.isValid(cl);
  };

  $scope.finished = function (quickUpdateInProgress) {
    var rowHeight = quickUpdateInProgress ? STUDENT_ROW_WITH_INPUT_HEIGHT : STUDENT_ROW_HEIGHT;
    angular.element(document.getElementsByClassName('container'))
      .css('height', Math.min(10, $scope.journal.journalStudents.length) * rowHeight + 40 + 18 + 'px');
  };

  angular.element($window).bind('resize', function(){
    $scope.windowHeight = $window.innerHeight;
  });

  function entityToForm(entity) {
    $scope.journal = entity;
    $scope.$parent.$parent.moodleCourseId = entity.moodleCourseId;
    loadJournalEntries();
    loadJournalStudents($scope.formState.showAllStudentsModel);
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

      dialogScope.$watch('criteria.studentGroupObject', function() {
        dialogScope.criteria.studentGroupId = dialogScope.criteria.studentGroupObject ? dialogScope.criteria.studentGroupObject.id : null;
      });

      QueryUtils.createQueryForm(dialogScope, '/journals/' + entity.id + '/otherStudents', {});
      dialogScope.loadData();
    }, function (submittedDialogScope) {
      QueryUtils.endpoint('/journals/' + entity.id + '/addStudentsToJournal').save({ students: submittedDialogScope.selectedStudents }, function () {
        message.info('journal.messages.studentSuccesfullyAdded');
        loadJournalStudents($scope.formState.showAllStudentsModel);
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
        loadJournalStudents($scope.formState.showAllStudentsModel);
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
    if (!angular.equals({}, state)) {
      setSelectedEntryTypes(state.selectedEntryTypes);
    } else {
      setSelectedEntryTypes();
    }
  });

  function setSelectedEntryTypes(selectedEntryTypes) {
    var journalEntryTypeValues = Object.values($scope.journalEntryTypes);
    if (!selectedEntryTypes) {
      selectedEntryTypes = journalEntryTypeValues.map(function (type) { return type.code; });
    }

    journalEntryTypeValues.forEach(function (type) {
      type._selected = selectedEntryTypes.indexOf(type.code) !== -1;
    });
  }

  $scope.updateSelectedEntryTypes = function () {
    var selectedEntryTypes = Object.values($scope.journalEntryTypes).filter(function (type) {
      return type._selected;
    }).map(function (type) { return type.code; });

    stateStorageService.changeState(schoolId, stateKey, {
      selectedEntryTypes: selectedEntryTypes
    });
    $scope.$broadcast('refreshFixedColumns');
  };

  $scope.getEntryColor = function (type) {
    return $scope.journalEntryTypeColors[type];
  };

  var JournalEntryEndpoint = QueryUtils.endpoint('/journals/' + entity.id + '/journalEntry');

  function loadJournalEntryDialogInitialData(dialogScope) {
    dialogScope.journalStudents = entity.journalStudents;

    dialogScope.journalEntryStudents = [];
    dialogScope.journalStudents.forEach(function (student) {
      dialogScope.journalEntryStudents[student.id] = { isLessonAbsence: false, lessonAbsences: {} };
    });

    dialogScope.absenceOptions = {};
    dialogScope.capacityTypes = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({journalId: dialogScope.journal.id, entryTypes: true});
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
      dialogScope.gradeInputAsSelect = $scope.formState.gradeInputAsSelect;
      dialogScope.grades = $scope.grades;
      dialogScope.gradeInputRegex = $scope.gradeInputRegex;
      dialogScope.hideInvalid = $scope.hideInvalid;

      dialogScope.entryDateChanged = function () {
        getStudentsWithAcceptedAbsences(true);
      };

      function getStudentsWithAcceptedAbsences(changeAbsences) {
        if(!dialogScope.journalEntry.entryDate) {
          dialogScope.acceptedAbsences = null;
          setAcceptedAbsences(changeAbsences);
          return;
        }

        QueryUtils.endpoint('/journals/' + entity.id + '/studentsWithAcceptedAbsences')
        .query({entryDate: dialogScope.journalEntry.entryDate}).$promise.then(function (response) {
          dialogScope.acceptedAbsences = response;
          dialogScope.previousEntryDate = dialogScope.journalEntry.entryDate;
          setAcceptedAbsences(changeAbsences);
        });
      }

      dialogScope.lessonsChanged = function () {
        setAcceptedAbsences(true);
      };

      function setAcceptedAbsences(changeAbsences) {
        dialogScope.journalStudents.forEach(function(js) {
          if (StudentUtil.isActive(js.status)) {
            var entryStudent = dialogScope.journalEntryStudents[js.id];
            var studentAbsences = getStudentAcceptedAbsences(js.id);

            if (angular.isObject(studentAbsences)) {
              if (studentAbsences.wholeDay) {
                entryStudent.hasWholeDayAcceptedAbsence = true;
                entryStudent.wholeDayAbsenceCode = studentAbsences.practice ? 'PUUDUMINE_PR' : 'PUUDUMINE_V';
              }

              if (studentAbsences.lessons) {
                var overlappingLessonAbsence = false;
                var firstLessonNr = dialogScope.journalEntry.startLessonNr || 1;
                var lastLessonNr = firstLessonNr + ((dialogScope.journalEntry.lessons || 1) - 1);

                studentAbsences.lessons.forEach(function (lessonNr) {
                  if (!overlappingLessonAbsence) {
                    overlappingLessonAbsence = lessonNr >= firstLessonNr && lessonNr <= lastLessonNr;
                  }

                  var entryLessonNr = getEntryLessonNr(lessonNr);
                  if (entryLessonNr > 0) {
                    if (angular.isObject(entryStudent.lessonAbsences[entryLessonNr])) {
                      // from groupAbsences form you can set whatever absence type you want, this checks if correct accepted absence is set
                      var setAbsenceCode = entryStudent.lessonAbsences[entryLessonNr].absence;
                      var acceptedAbsenceCode = getAcceptedAbsenceCode(studentAbsences, entryLessonNr, false);

                      entryStudent.lessonAbsences[entryLessonNr].accepted = setAbsenceCode === acceptedAbsenceCode;
                    }
                  }
                });

                // whole day absence is replaced by lesson absences if lesson absences have overlap with
                // accepted absence lessons and start lesson nr is set
                var startLessonNrExists = dialogScope.journalEntry.startLessonNr && dialogScope.journalEntry.startLessonNr !== '';
                entryStudent.hasOverlappingLessonAbsence = startLessonNrExists && overlappingLessonAbsence;
              }
            } else {
              entryStudent.hasWholeDayAcceptedAbsence = false;
              entryStudent.wholeDayAbsenceCode = null;
              entryStudent.hasOverlappingLessonAbsence = false;
            }

            if (changeAbsences) {
              setWholeLessonAbsence(js, studentAbsences);
              setAcceptedLessonAbsences(js, studentAbsences);
            }
          }
        });
      }

      function setWholeLessonAbsence(journalStudent, studentAbsences) {
        var entryStudent = dialogScope.journalEntryStudents[journalStudent.id];

        if (entryStudent.hasWholeDayAcceptedAbsence || entryStudent.hasOverlappingLessonAbsence) {
          if (studentAbsences.practice) {
            entryStudent.practice = true;
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, true, 'PUUDUMINE_PR');
          } else {
            entryStudent.excused = true;
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, true, 'PUUDUMINE_V');
          }
        } else {
          entryStudent.practice = false;
          entryStudent.excused = false;
          if (isAcceptedAbsence(entryStudent.absence)) {
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, null, null);
          }
        }
      }

      function setAcceptedLessonAbsences(journalStudent, studentAbsences) {
        var entryStudent = dialogScope.journalEntryStudents[journalStudent.id];
        var lessonAbsences = angular.isObject(studentAbsences) ? studentAbsences.lessons : null;

        angular.forEach(entryStudent.lessonAbsences, function (it) {
          // remove previously set accepted lesson absences
          if (it.accepted) {
            it.accepted = false;
            it.absence = null;
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, null, null, it.lessonNr);
          }

          // remove not allowed set absences
          if (entryStudent.hasWholeDayAcceptedAbsence && !isAcceptedAbsence(it.absence)) {
            it.absence = null;
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, null, null, it.lessonNr);
          } else if (!entryStudent.hasWholeDayAcceptedAbsence && isAcceptedAbsence(it.absence)) {
            it.absence = null;
            dialogScope.journalEntryStudentAbsenceChanged(journalStudent, null, null, it.lessonNr);
          }
        });

        if (lessonAbsences) {
          lessonAbsences.forEach(function (lessonNr) {
            var entryLessonNr = getEntryLessonNr(lessonNr);

            if (entryLessonNr > 0) {
              if (!angular.isObject(entryStudent.lessonAbsences[entryLessonNr])) {
                entryStudent.lessonAbsences[entryLessonNr] = {};
              }
              entryStudent.lessonAbsences[entryLessonNr].accepted = true;
              entryStudent.lessonAbsences[entryLessonNr].lessonNr = entryLessonNr;
              entryStudent.lessonAbsences[entryLessonNr].excused = true;
              dialogScope.journalEntryStudentAbsenceChanged(journalStudent, true, 'PUUDUMINE_V', entryLessonNr);
            }
          });
        }
      }

      // calculate entry lesson nr equivalent to absence lesson nr
      function getEntryLessonNr(absenceLessonNr) {
        // if calculated lesson nr is not higher than 0 then accepted absence lesson is not included in entry lessons
        return dialogScope.journalEntry.startLessonNr && dialogScope.journalEntry.startLessonNr > 1 ?
          absenceLessonNr + (1 - dialogScope.journalEntry.startLessonNr) : absenceLessonNr;
      }

      function getAbsenceLessonNr(entryLessonNr) {
        return dialogScope.journalEntry.startLessonNr && dialogScope.journalEntry.startLessonNr > 1 ?
          entryLessonNr + (dialogScope.journalEntry.startLessonNr - 1) : entryLessonNr;
      }

      dialogScope.hasSavedWholeDayAcceptedAbsence = function (row) {
        var js = dialogScope.journalEntryStudents[row.id];
        return js && ((js.hasWholeDayAcceptedAbsence && isAcceptedAbsence(js.absence)) ||
          (js.hasOverlappingLessonAbsence && js.absence === 'PUUDUMINE_V'));
      };

      dialogScope.absenceCheckboxShown = function (row, absenceCode) {
        var js = dialogScope.journalEntryStudents[row.id];
        var studentAbsence = js.absence;

        if (studentAbsence === absenceCode) {
          // selected absence selectbox should always be shown and every type is selectable on groupAbsence form
          return true;
        }
        return !isAcceptedAbsence(studentAbsence) && !isAcceptedAbsence(absenceCode);
      };

      dialogScope.absenceCheckboxDisabled = function (row, absenceCode) {
        var js = dialogScope.journalEntryStudents[row.id];
        return !row.canEdit || (isAcceptedAbsence(absenceCode) && ((js.hasWholeDayAcceptedAbsence &&
          js.wholeDayAbsenceCode === absenceCode) || (js.hasOverlappingLessonAbsence && absenceCode === 'PUUDUMINE_V')));
      };

      dialogScope.lessonAbsenceCheckboxShown = function (row, absenceCode, lessonNr) {
        var js = dialogScope.journalEntryStudents[row.id];
        var lessonAbsence = js.lessonAbsences[lessonNr];
        var lessonAbsenceCode = angular.isDefined(lessonAbsence) ? lessonAbsence.absence : null;

        if (lessonAbsenceCode === absenceCode) {
          // selected absence selectbox should always be shown and every type is selectable on groupAbsence form
          return true;
        } else if (js.hasWholeDayAcceptedAbsence && js.wholeDayAbsenceCode === js.absence && isAcceptedAbsence(js.absence)) {
          // if whole day has accepted absence then only that absence type can be selected
          return js.wholeDayAbsenceCode === absenceCode;
        }
        return !isAcceptedAbsence(lessonAbsenceCode) && !isAcceptedAbsence(absenceCode);
      };

      dialogScope.lessonAbsenceCheckboxDisabled = function (row, absenceCode, lessonNr) {
        return !row.canEdit || hasAcceptedLessonAbsence(row, absenceCode, lessonNr);
      };

      function hasAcceptedLessonAbsence(row, absenceCode, lessonNr) {
        var js = dialogScope.journalEntryStudents[row.id];
        return isAcceptedAbsence(absenceCode) && js && js.lessonAbsences[lessonNr] &&
          angular.isDefined(js.lessonAbsences[lessonNr].accepted) && js.lessonAbsences[lessonNr].accepted;
      }

      function isAcceptedAbsence(absenceCode) {
        return ACCEPTED_ABSENCES.indexOf(absenceCode) !== -1;
      }

      function setStudentAbsenceCheckboxValues(it) {
        if (it.isLessonAbsence) {
          angular.forEach(it.lessonAbsences, function (absence) {
            setAbsenceCheckboxValue(absence);
          });
        } else {
          setAbsenceCheckboxValue(it);
        }
      }

      function setAbsenceCheckboxValue(it) {
        switch (it.absence) {
          case 'PUUDUMINE_PR':
            it.practice = true;
            break;
          case 'PUUDUMINE_V':
            it.excused = true;
            break;
          case 'PUUDUMINE_H':
            it.late = true;
            break;
          case 'PUUDUMINE_P':
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
          it.gradeValue = it.grade ? VocationalGradeUtil.removePrefix(it.grade) : null;
          dialogScope.journalEntryStudents[it.journalStudent] = it;
          DataUtils.convertStringToDates(it.journalEntryStudentHistories, ['gradeInserted']);
          classifierMapper.objectmapper(it.journalEntryStudentHistories);
          setStudentAbsenceCheckboxValues(it);
        });
        getStudentsWithAcceptedAbsences(false);
        dialogScope.savedJournalEntryStudents = angular.copy(dialogScope.journalEntryStudents);
        editEntity.journalEntryCapacityTypes.forEach(function (it) {
          dialogScope.selectedCapacityTypes[it] = true;
        });
        dialogScope.canDeleteEntries = canDeleteEntries();
      }

      setForbiddenTypes(dialogScope, editEntity);

      function changeAbsenceCheckboxValues(journalEntryStudentsRow, absence, checkboxValue) {
        switch (absence) {
          case 'PUUDUMINE_PR':
            journalEntryStudentsRow.practice = checkboxValue;
            journalEntryStudentsRow.withoutReason = false;
            journalEntryStudentsRow.late = false;
            journalEntryStudentsRow.excused = false;
            break;
          case 'PUUDUMINE_V':
            journalEntryStudentsRow.excused = checkboxValue;
            journalEntryStudentsRow.withoutReason = false;
            journalEntryStudentsRow.late = false;
            journalEntryStudentsRow.practice = false;
            break;
          case 'PUUDUMINE_H':
            journalEntryStudentsRow.late = checkboxValue;
            journalEntryStudentsRow.excused = false;
            journalEntryStudentsRow.withoutReason = false;
            journalEntryStudentsRow.practice = false;
            break;
          case 'PUUDUMINE_P':
            journalEntryStudentsRow.withoutReason = checkboxValue;
            journalEntryStudentsRow.excused = false;
            journalEntryStudentsRow.late = false;
            journalEntryStudentsRow.practice = false;
            break;
          default:
            journalEntryStudentsRow.practice = false;
            journalEntryStudentsRow.withoutReason = false;
            journalEntryStudentsRow.late = false;
            journalEntryStudentsRow.excused = false;
            break;
        }
      }

      dialogScope.changedJournalEntryStudents = [];
      dialogScope.journalEntryStudentChanged = function (row) {
        var entryStudent = dialogScope.journalEntryStudents[row.id];

        if (!dialogScope.gradeInputAsSelect) {
          if (entryStudent.gradeValue) {
            entryStudent.grade = VocationalGradeUtil.addPrefix(entryStudent.gradeValue);
          } else {
            entryStudent.grade = null;
          }
        }

        entryStudent.journalStudent = row.id;
        if (dialogScope.changedJournalEntryStudents.indexOf(entryStudent) === -1) {
          dialogScope.changedJournalEntryStudents.push(entryStudent);
        }
      };

      dialogScope.journalEntryStudentAbsenceChanged = function (row, checkboxValue, absence, lessonNr) {
        var entryStudent = dialogScope.journalEntryStudents[row.id];
        var studentAbsences = getStudentAcceptedAbsences(row.id);
        entryStudent.journalStudent = row.id;

        // if manually set absence is absence without reason then check if there is not already accepted absence
        if (absence === 'PUUDUMINE_P' && angular.isObject(studentAbsences)) {
          absence = getAcceptedAbsenceCode(studentAbsences, lessonNr, entryStudent.hasOverlappingLessonAbsence);
        }

        if (lessonNr) {
          if (!angular.isObject(entryStudent.lessonAbsences[lessonNr])) {
            entryStudent.lessonAbsences[lessonNr] = {};
          }
          entryStudent.lessonAbsences[lessonNr].lessonNr = lessonNr;
          entryStudent.lessonAbsences[lessonNr].absence = checkboxValue ? absence : null;
          entryStudent.lessonAbsences[lessonNr].accepted = checkboxValue ? isAcceptedLessonAbsence(studentAbsences, lessonNr) : false;
        } else {
          entryStudent.absence = checkboxValue ? absence : null;
        }

        if (dialogScope.changedJournalEntryStudents.indexOf(entryStudent) === -1) {
          dialogScope.changedJournalEntryStudents.push(entryStudent);
        }

        var journalEntryStudentsRow = lessonNr ? entryStudent.lessonAbsences[lessonNr] : entryStudent;
        changeAbsenceCheckboxValues(journalEntryStudentsRow, absence, checkboxValue);
      };

      function getStudentAcceptedAbsences(studentId) {
        var studentAbsences = dialogScope.acceptedAbsences ?
          dialogScope.acceptedAbsences.filter(function (student) { return student.journalStudent === studentId; }) : null;
        studentAbsences = studentAbsences !== null ? studentAbsences[0] : null;
        return studentAbsences;
      }

      function isAcceptedLessonAbsence(studentAbsences, lessonNr) {
        var absenceLessonNr = getAbsenceLessonNr(lessonNr);
        return studentAbsences && studentAbsences.lessons && studentAbsences.lessons.indexOf(absenceLessonNr) !== -1;
      }

      function getAcceptedAbsenceCode(studentAbsences, lessonNr, hasOverlappingLessonAbsence) {
        if (lessonNr) {
          var absenceLessonNr = getAbsenceLessonNr(lessonNr);
          if (studentAbsences.lessons && studentAbsences.lessons.indexOf(absenceLessonNr) !== -1) {
            return studentAbsences.practice ? 'PUUDUMINE_PR' : 'PUUDUMINE_V';
          }
          // no equivalent lesson and therefore absence stays as without reason
          return 'PUUDUMINE_P';
        } else {
          if (studentAbsences.wholeDay) {
            return studentAbsences.practice ? 'PUUDUMINE_PR' : 'PUUDUMINE_V';
          } else if (hasOverlappingLessonAbsence) {
            return 'PUUDUMINE_V';
          }
          return 'PUUDUMINE_P';
        }
      }

      dialogScope.setJournalEntryTypeChanged = function (entryType) {
        setJournalEntryDefaultName(entryType);
        if (entryType === 'SISSEKANNE_L') {
          setApelTransferredResultAsFinalResult();
        }
      };

      function setJournalEntryDefaultName(entryType) {
        var holder = { entryType: entryType };
        classifierMapper.objectmapper(holder);
        dialogScope.journalEntry.nameEt = holder.entryType.nameEt;
      }

      function setApelTransferredResultAsFinalResult() {
        dialogScope.journalStudents.forEach(function(student) {
          if (student.apelTransferredFinalResult) {
            dialogScope.journalEntryStudents[student.id].grade = student.apelTransferredFinalResult;
            dialogScope.journalEntryStudentChanged(student);
          }
        });
      }

      dialogScope.removeStudentHistory = function (row) {
        var entryStudent = dialogScope.journalEntryStudents[row.id];
        entryStudent.journalStudent = row.id;
        entryStudent.removeStudentHistory = true;
        entryStudent.grade = null;
        entryStudent.gradeValue = null;
        if (dialogScope.changedJournalEntryStudents.indexOf(entryStudent) === -1) {
          dialogScope.changedJournalEntryStudents.push(entryStudent);
        }
      };

      dialogScope.saveEntry = function () {
        var lessons = dialogScope.journalEntry.lessons || 1;

        // create lesson absences when there is an overlap
        dialogScope.journalStudents.forEach(function(js) {
          if (StudentUtil.isActive(js.status)) {
            var entryStudent = dialogScope.journalEntryStudents[js.id];

            if (!entryStudent.isLessonAbsence && entryStudent.hasOverlappingLessonAbsence && entryStudent.absence === 'PUUDUMINE_V') {
              for (var i = 1; i <= lessons; i++) {
                if (!angular.isObject(entryStudent.lessonAbsences[i])) {
                  dialogScope.journalEntryStudentAbsenceChanged(js, true, 'PUUDUMINE_P', i);
                }
              }
              entryStudent.isLessonAbsence = true;
            }
          }
        });
        dialogScope.submit();
      };

      dialogScope.delete = function () {
        dialogService.confirmDialog({prompt: 'journal.entryDeleteConfirm'}, function() {
          new JournalEntryEndpoint(dialogScope.journalEntry).$delete().then(function () {
            message.info('main.messages.delete.success');
            loadUsedHours();
            loadJournalEntries();
            loadJournalStudents($scope.formState.showAllStudentsModel);
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
          loadJournalStudents($scope.formState.showAllStudentsModel);
        });
      } else {
        journalEntry.$save().then(function () {
          message.info('main.messages.create.success');
          loadUsedHours();
          loadJournalEntries();
          loadJournalStudents($scope.formState.showAllStudentsModel);
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

  $scope.quickUpdate = function (journalEntry) {
    $scope.finished(true);
    journalEntry.quickUpdate = true;
    journalEntry.quickUpdateStudents = [];

    for (var p in journalEntry.journalStudentResults) {
      if (journalEntry.journalStudentResults.hasOwnProperty(p)) {
        var result = journalEntry.journalStudentResults[p][0];

        if (!angular.isObject(journalEntry.quickUpdateStudents[p])) {
          journalEntry.quickUpdateStudents[p] = {};
        }
        journalEntry.quickUpdateStudents[p] = {
          id: result.journalEntryStudentId,
          journalStudent: result.journalStudentId,
          grade: result.grade ? result.grade.code : null,
          gradeValue: result.grade ? VocationalGradeUtil.removePrefix(result.grade.code) : null,
          isRemark: result.isRemark,
          addInfo: result.addInfo
        };
      }
    }
  };

  $scope.saveQuickUpdate = function (journalEntry) {
    var journalEntryStudents = Object.values(journalEntry.quickUpdateStudents);
    var formValid = gradeInputsValid(journalEntryStudents);

    if (!formValid) {
      message.error('main.messages.form-has-errors');
      return;
    }

    QueryUtils.endpoint('/journals/' + $scope.journal.id + '/journalEntry/quickUpdate').save({
      journalEntryId: journalEntry.id,
      journalEntryStudents: journalEntryStudents,
      allStudents: $scope.formState.showAllStudentsModel
    }).$promise.then(function (result) {
      var updatedEntryStudents = [];
      for (var p in result) {
        if (result.hasOwnProperty(p) && !isNaN(p)) {
          updatedEntryStudents[p] = result[p];
        }
      }

      for (var i = 0; i < $scope.journal.journalEntriesByDate.length; i++) {
        var entriesByDate = $scope.journal.journalEntriesByDate[i];
        if (journalEntry.id === entriesByDate.id) {
          entriesByDate.journalStudentResults = updatedEntryStudents;
          for (var key in entriesByDate.journalStudentResults) {
            if (entriesByDate.journalStudentResults.hasOwnProperty(key)) {
              mapStudentResultClassifiers(entriesByDate.journalStudentResults[key]);
            }
          }
          entriesByDate.quickUpdate = false;
          updateEntryStudentsTableHeight();
          break;
        }
      }
    });
  };

  function gradeInputsValid(journalEntryStudents) {
    var valid = true;
    if (!$scope.formState.gradeInputAsSelect) {
      journalEntryStudents.forEach(function (student) {
        if (student.gradeValue && !student.grade) {
          valid = false;
        }
      });
    }
    return valid;
  }

  $scope.cancelQuickUpdate = function (journalEntry) {
    journalEntry.quickUpdate = false;
    updateEntryStudentsTableHeight();
  };

  function updateEntryStudentsTableHeight() {
    var entryQuickUpdateOpen = false;
    $scope.journal.journalEntriesByDate.forEach(function (entry) {
      if (entry.quickUpdate) {
        entryQuickUpdateOpen = true;
      }
    });
    if (!entryQuickUpdateOpen) {
      $scope.finished(false);
    }
  }

  $scope.quickUpdateGradeChanged = function (journalEntry, journalStudentId) {
    var studentQuickUpdate = journalEntry.quickUpdateStudents[journalStudentId];
    if (!angular.isDefined(studentQuickUpdate.journalStudent)) {
      studentQuickUpdate.journalStudent = journalStudentId;
    }

    if (!$scope.formState.gradeInputAsSelect) {
      if (studentQuickUpdate.gradeValue && new RegExp($scope.gradeInputRegex).test(studentQuickUpdate.gradeValue)) {
        studentQuickUpdate.grade = VocationalGradeUtil.addPrefix(studentQuickUpdate.gradeValue);
      } else {
        studentQuickUpdate.grade = null;
      }
    }
  };

  $scope.quickUpdateAddInfoChanged = function (journalEntry, journalStudentId) {
    var studentQuickUpdate = journalEntry.quickUpdateStudents[journalStudentId];
    if (!angular.isDefined(studentQuickUpdate.journalStudent)) {
      studentQuickUpdate.journalStudent = journalStudentId;
    }
  };

  $scope.moduleDescriptionDialog = function (moduleDescription) {
    dialogService.showDialog('journal/journal.moduleDescription.dialog.html', function (dialogScope) {
      dialogScope.moduleDescription = moduleDescription;
      dialogScope.isDistinctiveAssessment = moduleDescription.assessment === 'KUTSEHINDAMISVIIS_E' ? true : false;
    });
  };

  $scope.individualCurriculumDialog = function (individualCurriculum) {
    dialogService.showDialog('journal/journal.individualCurriculum.dialog.html', function (dialogScope) {
      dialogScope.individualCurriculum = individualCurriculum;
    });
  };

  $scope.showAllStudents = function () {
    loadJournalStudents($scope.formState.showAllStudentsModel);
  };

  var ConfirmEndpoint = QueryUtils.endpoint('/journals/confirm');
  var UnconfirmEndpoint = QueryUtils.endpoint('/journals/unconfirm');

  function confirm() {
    new ConfirmEndpoint().$update({id: $scope.journal.id}).then(function (response) {
      message.info('journal.messages.confirmed');
      if ($scope.auth.roleCode !== 'ROLL_A') {
        $scope.back('#/journal/' + response.id + '/view');
      } else {
        $scope.journal.canBeConfirmed = response.canBeConfirmed;
        $scope.journal.canBeUnconfirmed = response.canBeUnconfirmed;
        $scope.$parent.journal.status = response.status;
      }
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
  var ConnectEndpoint = QueryUtils.endpoint('/studyMaterial/journal/' + $scope.journal.id + '/connect');
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
    connection.$save({studyMaterial: $scope.existingMaterial.id}).then(function () {
      $scope.existingMaterial = undefined;
      loadMaterials();
    });
  };

  $scope.openApelResultsDialog = function (journalStudent) {
    dialogService.showDialog('journal/journal.apel.results.dialog.html', function (dialogScope) {
      dialogScope.journalStudent = journalStudent;
    });
  };

  $scope.openRemarkDialog = function (journalStudent) {
    dialogService.showDialog('journal/journal.remarks.dialog.html', function (dialogScope) {
      dialogScope.journalStudent = journalStudent;
    });
  };

});
