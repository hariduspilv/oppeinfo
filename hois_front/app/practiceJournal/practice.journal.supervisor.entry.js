'use strict';

angular.module('hitsaOis').controller('PracticeJournalSupervisorEntryController', function ($filter, $q, $route, $location, $scope, GRADING_SCHEMA_TYPE, ArrayUtils, Classifier, DataUtils, GradingSchema, HigherGradeUtil, QueryUtils, dialogService, message, oisFileService) {
  $scope.removeFromArray = ArrayUtils.remove;
  $scope.practiceJournal = {
    practiceJournalEntries: [],
    practiceJournalFiles: []
  };
  $scope.formState = {};
  var gradingSchema, gradeMapper;

  function assertPermissionToEdit(entity) {
    if (!entity.canAddEntries) {
      message.error('main.messages.error.nopermission');
      $location.path('');
    }
  }

  function setGradingSchema(entity) {
    var gradingSchemaType = entity.isHigher ? GRADING_SCHEMA_TYPE.HIGHER : GRADING_SCHEMA_TYPE.VOCATIONAL;
    gradingSchema = new GradingSchema(gradingSchemaType, entity.school.id);
    $q.all(gradingSchema.promises).then(function () {
      $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
      $scope.grades = gradingSchema.gradeSelection(entity.studyYear.id);
      gradeMapper = gradingSchema.gradeMapper($scope.grades, ['grade']);
    });
  }

  function entityToForm(entity) {
    assertPermissionToEdit(entity);
    DataUtils.convertStringToDates(entity, ['startDate', 'endDate']);
    entity.practiceJournalEntries.forEach(function (entry) {
      entry.astroHours = DataUtils.getHoursFromDoubleMinutes(entry.hours); // entry has minutes instead of hours
    });

    $q.all(gradingSchema.promises).then(function () {
      entity.studentPracticeEvalCriteria.forEach(function (evaluation) {
        gradeMapper.objectmapper(evaluation);
      });
      entity.supervisorPracticeEvalCriteria.forEach(function (evaluation) {
        gradeMapper.objectmapper(evaluation);
      });
    });

    $scope.practiceJournal = entity;
    $scope.practiceJournal.endDateDisplay = new Date($scope.practiceJournal.endDate);
    $scope.practiceJournal.endDateDisplay.setDate($scope.practiceJournal.endDateDisplay.getDate() + 30);
    updateTotal();
  }

  function updateTotal() {
    var totalAstro = 0.00;
    $scope.practiceJournal.practiceJournalEntries.forEach(function (entry) {
      if (entry.hours !== undefined) {
        totalAstro += entry.hours;
      }
    });

    $scope.totalAstro = DataUtils.getHoursFromDoubleMinutes(totalAstro);
  }

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    setGradingSchema(entity);
    entityToForm(entity);
  }

  $scope.hideInvalid = function (cl) {
    return !Classifier.isValid(cl);
  };

  $scope.getNames = function(names) {
    return names.map(function(elem){return elem.supervisorName;}).join(', ');
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
