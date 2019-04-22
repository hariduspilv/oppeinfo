'use strict';

angular.module('hitsaOis').controller('PracticeJournalEntryController', function ($scope, $route, $location, dialogService, oisFileService, QueryUtils, message, DataUtils, ArrayUtils, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.removeFromArray = ArrayUtils.remove;
  var DAYS_AFTER_CAN_EDIT = 30;
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
    entity.practiceJournalEntries.forEach(function (entry) {
      entry.astroHours = DataUtils.getAcademicHoursFromDouble(entry.hours);
      entry.acadHours = DataUtils.getAcademicHours(entry.hours);
    });
    $scope.practiceJournal = entity;
    $scope.practiceJournal.endDateDisplay = new Date($scope.practiceJournal.endDate);
    $scope.practiceJournal.endDateDisplay.setDate($scope.practiceJournal.endDateDisplay.getDate() + 30);
    updateTotal();
  }

  function updateTotal() {
    var totalAstro = 0.00;
    var totalAcad = 0.00;
    $scope.practiceJournal.practiceJournalEntries.forEach(function (entry) {
      if (entry.hours !== undefined) {
        totalAstro += entry.hours;
      }
      if (entry.acadHours !== undefined) {
        totalAcad += entry.acadHours;
      }
    });
    $scope.totalAstro = DataUtils.getAcademicHoursFromDouble(totalAstro);
    $scope.totalAcad = Math.round(totalAcad * 100) / 100;
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  }

  $scope.getAstronomicalHours = DataUtils.getAstronomicalHours;

  $scope.setAstronomicalHours = function(row, akadHours) {
    row.hours = DataUtils.getAstronomicalHoursAsDouble(akadHours);
    row.astroHours = DataUtils.getAstronomicalHoursAsString(akadHours);
    updateTotal();
  };

  $scope.setAcademicalHours = function(row) {
    if (row.astroHours === undefined) {
      return;
    }
    row.astroHours = row.astroHours.replace(".", ":");
    row.hours = DataUtils.getAcademicHoursFromString(row.astroHours);
    row.acadHours = DataUtils.getAcademicHours(row.hours);
    updateTotal();
  };

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
    updateTotal();
  };

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
    evals = evals.filter(function (criteria) {
      return criteria.id || (!criteria.id && (criteria.valueNr || criteria.valueTxt || criteria.valueClf));
    });
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

  function validationPassed() {
    $scope.practiceJournalEntryForm.$setSubmitted();
    if(!$scope.practiceJournalEntryForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    }

    if($scope.practiceJournal.moduleSubjects.length === 0) {
      message.error($scope.formState.isHigher ? 'practiceJournal.messages.atleastOneSubjectRequired' : 'practiceJournal.messages.atleastOneModuleRequired');
      return false;
    }

    return true;
  }

  function isBeforeDaysAfterCanEdit(practiceJournal) {
    var now = moment();
    var endDate = moment(practiceJournal.endDate);

    return moment.duration(now.diff(endDate)).asDays() <= DAYS_AFTER_CAN_EDIT;
  }

  $scope.confirm = function () {
    if(!validationPassed()) {
      return false;
    }
    $scope.practiceJournal.isHigher = $scope.formState.isHigher;
    if (!isBeforeDaysAfterCanEdit($scope.practiceJournal)) {        
      dialogService.confirmDialog({prompt: 'practiceJournal.prompt.isAfterDaysAfterCanEditConfirm'}, function () {
        confirmPracticeJournal(true);
      });
    } else {
      dialogService.confirmDialog({prompt: 'practiceJournal.prompt.confirmConfirm'}, function () {
        confirmPracticeJournal(false);
      });
    }
  };

  function confirmPracticeJournal(sendBackToList) {
    var practiceJournalCopy = angular.copy($scope.practiceJournal);
    practiceJournalCopy.moduleSubjects.forEach(function (it) {
      DataUtils.convertObjectToIdentifier(it, ['module', 'theme', 'subject']);
    });
    DataUtils.convertObjectToIdentifier(practiceJournalCopy, ['practiceEvaluation']);
    QueryUtils.endpoint('/practiceJournals/' + practiceJournalCopy.id + '/confirm').put(practiceJournalCopy, function (practiceJournal) {
      message.info('practiceJournal.messages.confirmed');
      if (sendBackToList) {
        $location.path('/practiceJournals');
      } else {
        entityToForm(practiceJournal);
        $scope.practiceJournalEntryForm.$setPristine();
      }
    });
  }

  $scope.open = function () {
    if(!validationPassed()) {
      return false;
    }
    $scope.practiceJournal.isHigher = $scope.formState.isHigher;
    dialogService.confirmDialog({prompt: 'practiceJournal.prompt.confirmOpen'}, function () {
      openPracticeJournal();
    });
  };

  function openPracticeJournal() {
    var practiceJournalCopy = angular.copy($scope.practiceJournal);
    practiceJournalCopy.moduleSubjects.forEach(function (it) {
      DataUtils.convertObjectToIdentifier(it, ['module', 'theme', 'subject']);
    });
    DataUtils.convertObjectToIdentifier(practiceJournalCopy, ['practiceEvaluation']);
    QueryUtils.endpoint('/practiceJournals/' + practiceJournalCopy.id + '/open').put(practiceJournalCopy, function (practiceJournal) {
      message.info('practiceJournal.messages.opened');
      entityToForm(practiceJournal);
      $scope.practiceJournalEntryForm.$setPristine();
    });
  }

});
