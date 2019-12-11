'use strict';

angular.module('hitsaOis').controller('PracticeJournalSupervisorEntryController', function ($filter, $route, $location, $scope, ArrayUtils, Classifier, DataUtils, HigherGradeUtil, QueryUtils, dialogService, message, oisFileService) {
  $scope.removeFromArray = ArrayUtils.remove;
  $scope.practiceJournal = {
    practiceJournalEntries: [],
    practiceJournalFiles: []
  };
  $scope.formState = {};

  function assertPermissionToEdit(entity) {
    if (!entity.canAddEntries) {
      message.error('main.messages.error.nopermission');
      $location.path('');
    }
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);
    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);

    var gradesClassCode = entity.isHigher ? 'KORGHINDAMINE' : 'KUTSEHINDAMINE';
    $scope.grades = Classifier.queryForDropdown({ mainClassCode: gradesClassCode });
    $scope.grades.$promise.then(function () {
      if (entity.isHigher) {
        $scope.grades = HigherGradeUtil.orderedGrades($scope.grades);
        $scope.gradeSelectShownValue = function (grade) {
          return HigherGradeUtil.gradeSelectShownValue(grade, $scope.auth.school.letterGrades);
        };
      } 
      $scope.gradesMap = Classifier.toMap($scope.grades);
    });

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

  $scope.hideInvalid = function (cl) {
    return !Classifier.isValid(cl);
  };
  
  $scope.getNames = function(names) {
    return names.map(function(elem){return elem.supervisorName;}).join(', ');
  };

  $scope.getAstronomicalHours = DataUtils.getAstronomicalHours;

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
    var files = [];
    files = files.concat($scope.practiceJournal.practiceJournalFiles, $scope.practiceJournal.practiceJournalStudentFiles);
    var evals = [];
    evals = evals.concat($scope.practiceJournal.supervisorPracticeEvalCriteria, $scope.practiceJournal.studentPracticeEvalCriteria);
    var practiceJournalEntries = new Endpoint(
      {
        supervisorOpinion: $scope.practiceJournal.supervisorOpinion,
        supervisorComment: $scope.practiceJournal.supervisorComment,
        supervisorPracticeEvalCriteria: evals,
        practiceJournalFiles: files,
        practiceJournalEntries: $scope.practiceJournal.practiceJournalEntries
      });

    practiceJournalEntries.$update().then(function (result) {
      message.info('main.messages.create.success');
      entityToForm(result);
    });
  };

});
