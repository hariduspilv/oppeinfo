(function () {
'use strict';

  function getDataForFormalLearningTables(scope) {
    for (var i = 0; i < scope.application.records.length; i++) {
      if (scope.application.records[i].isFormalLearning) {
        scope.application.records[i].data = [];
        var rows = Math.max(scope.application.records[i].formalSubjectsOrModules.length, scope.application.records[i].formalReplacedSubjectsOrModules.length);
        for (var j = 0; j < rows; j++) {
          var transferableSubjectOrModule = scope.application.records[i].formalSubjectsOrModules[j] ?
            scope.application.records[i].formalSubjectsOrModules[j] : {};
          var replacedSubjectOrModule = scope.application.records[i].formalReplacedSubjectsOrModules[j] ?
            scope.application.records[i].formalReplacedSubjectsOrModules[j] : {};
          getFormalLearningTableRow(scope, i, transferableSubjectOrModule, replacedSubjectOrModule);
        }
      }
    }
  }

  function getFormalLearningTableRow(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule) {
    if (scope.application.isVocational) {
      if (transferableSubjectOrModule.curriculumVersionOmodule) {
        getFormalLearningModuleTableRow(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule,
          transferableSubjectOrModule.curriculumVersionOmodule);
      } else {
        getFormalLearningModuleTableRow(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule, null);
      }
    } else {
      getFormalLearningSubjectTableRow(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule);
    }
  }

  function getFormalLearningModuleTableRow(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule, oModule) {
    if (!angular.equals(replacedSubjectOrModule, {})) {
      getFormalLearningTableRowWithReplacedModules(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule, oModule);
    } else {
      var data = getFormalModuleRowData(scope, transferableSubjectOrModule, scope.application.records[recordIndex].id, oModule, null);
      scope.application.records[recordIndex].data.push(data);
    }
  }

  function getFormalLearningTableRowWithReplacedModules(scope, recordIndex, transferableSubjectOrModule, replacedSubjectOrModule, oModule) {
    var data = null;
    var replacedTheme = replacedSubjectOrModule.curriculumVersionOmoduleTheme;
    if (replacedTheme) {
      data = getFormalModuleRowData(scope, transferableSubjectOrModule, scope.application.records[recordIndex].id,
        oModule, replacedSubjectOrModule.curriculumVersionOmodule, replacedTheme);
    } else {
      data = getFormalModuleRowData(scope, transferableSubjectOrModule, scope.application.records[recordIndex].id, oModule,
        replacedSubjectOrModule.curriculumVersionOmodule, null);
    }
    scope.application.records[recordIndex].data.push(data);
  }

  function getFormalModuleRowData(scope, formalSubjectsOrModules, recordId, oModule, replacedOmodule, replacedTheme) {
    var data = {};
    data.recordId = recordId;

    if (formalSubjectsOrModules && !angular.equals(formalSubjectsOrModules, {})) {
      data.subjectOrModuleId = formalSubjectsOrModules.id;
      data.school = formalSubjectsOrModules.apelSchool ? formalSubjectsOrModules.apelSchool : scope.school;
      data.moduleId = formalSubjectsOrModules.id;
      data.module = oModule ? oModule : {nameEt: formalSubjectsOrModules.nameEt, nameEn: formalSubjectsOrModules.nameEn};
      data.credits = formalSubjectsOrModules.credits;
      data.grade = scope.gradesMap[formalSubjectsOrModules.grade];
      data.gradeDate = formalSubjectsOrModules.gradeDate;
      data.teachers = formalSubjectsOrModules.teachers;
      data.transfer = formalSubjectsOrModules.transfer;
    }
    data.replacedModule = replacedOmodule;
    data.replacedTheme = replacedTheme;
    if (replacedOmodule && !replacedTheme) {
      data.replacedCredits = replacedOmodule.credits;
    } else if (replacedOmodule && replacedTheme) {
      data.replacedCredits = replacedTheme.credits;
    } else {
      data.replacedCredits = null;
    }
    return data;
  }


  function getFormalLearningSubjectTableRow(scope, recordIndex, transferableSubject, replacedSubject) {
    var data = {};
    data.recordId = scope.application.records[recordIndex].id;
    data.subjectOrModuleId = transferableSubject.id;

    if (transferableSubject && !angular.equals(transferableSubject, {})) {
      data.school = transferableSubject.apelSchool ? transferableSubject.apelSchool : scope.school;
      data.subject = transferableSubject.subject ? transferableSubject.subject : {nameEn: transferableSubject.nameEn, nameEt: transferableSubject.nameEt};
      data.code = transferableSubject.subjectCode;
      data.credits = transferableSubject.credits;
      data.assessment = scope.assessmentsMap[transferableSubject.assessment];
      data.grade = scope.gradesMap[transferableSubject.grade];
      data.gradeDate = transferableSubject.gradeDate;
      data.teachers = transferableSubject.teachers;
      data.module = transferableSubject.curriculumVersionHmodule;
      data.isOptional = transferableSubject.isOptional;
      data.transfer = transferableSubject.transfer;
    }

    if (replacedSubject && !angular.equals(replacedSubject, {})) {
      data.replacedSubject = replacedSubject.subject;
      data.replacedCredits = replacedSubject.subject.credits;
    }

    scope.application.records[recordIndex].data.push(data);
  }


  function getDataForInformalLearningTables(scope) {
    for (var i = 0; i < scope.application.records.length; i++) {
      if (!scope.application.records[i].isFormalLearning) {
        scope.application.records[i].data = [];
        var rows = Math.max(scope.application.records[i].informalExperiences.length, scope.application.records[i].informalSubjectsOrModules.length);
        for (var j = 0; j < rows; j++) {
          var informalExperiences = scope.application.records[i].informalExperiences[j] ? scope.application.records[i].informalExperiences[j] : {};
          var informalSubjectsOrModules = scope.application.records[i].informalSubjectsOrModules[j] ? scope.application.records[i].informalSubjectsOrModules[j] : {};

          getInformalLearningTableRow(scope, i, informalExperiences, informalSubjectsOrModules);
        }
      }
    }
  }

  function getInformalLearningTableRow(scope, recordIndex, informalExperiences, informalSubjectsOrModules) {
    var data = [];
    var recordId = scope.application.records[recordIndex].id;

    if (informalSubjectsOrModules.curriculumVersionOmodule) {
      data = getInformalModuleRowData(scope, informalSubjectsOrModules, informalSubjectsOrModules.curriculumVersionOmodule, recordId);
      scope.application.records[recordIndex].data.push(angular.extend({}, informalExperiences, data));
    } else if (informalSubjectsOrModules.subject) {
      data = getInformalSubjectRowData(scope, informalSubjectsOrModules, recordId);
      scope.application.records[recordIndex].data.push(angular.extend({}, informalExperiences, data));
    } else {
      scope.application.records[recordIndex].data.push(informalExperiences);
    }
  }

  function getInformalModuleRowData(scope, informalSubjectsOrModules, oModule, recordId) {
    var data = [];
    data.recordId = recordId;
    data.moduleId = informalSubjectsOrModules.id;
    data.module = oModule;
    data.theme = informalSubjectsOrModules.curriculumVersionOmoduleTheme ? informalSubjectsOrModules.curriculumVersionOmoduleTheme : null;
    data.isModule = !informalSubjectsOrModules.curriculumVersionOmoduleTheme;
    data.schoolResultHours = data.isModule ? getHours(informalSubjectsOrModules.curriculumVersionOmodule.themes) : informalSubjectsOrModules.curriculumVersionOmoduleTheme.hours;
    data.grade = scope.gradesMap[informalSubjectsOrModules.grade];
    data.outcomes = informalSubjectsOrModules.outcomes;
    data.skills = informalSubjectsOrModules.skills;
    data.transfer = informalSubjectsOrModules.transfer;
    data.ekap = data.isModule ? oModule.credits : informalSubjectsOrModules.curriculumVersionOmoduleTheme.credits;
    return data;
  }

  function getInformalSubjectRowData(scope, informalSubjectsOrModules, recordId) {
    var data = [];
    data.recordId = recordId;
    data.subjectId = informalSubjectsOrModules.id;
    data.subject = informalSubjectsOrModules.subject;
    data.code = informalSubjectsOrModules.subject.code;
    data.module = informalSubjectsOrModules.curriculumVersionHmodule;
    data.isOptional = informalSubjectsOrModules.isOptional;
    data.credits = informalSubjectsOrModules.subject.credits;
    data.grade = scope.gradesMap[informalSubjectsOrModules.grade];
    data.skills = informalSubjectsOrModules.skills;
    data.transfer = informalSubjectsOrModules.transfer;
    return data;
  }

  function getSubjectIsOptional(subjectId, hModule) {
    for (var i = 0; i < hModule.subjects.length; i++) {
      if (hModule.subjects[i].subjectId === subjectId) {
        return hModule.subjects[i].optional;
      }
    }
  }

  function getHours(themes) {
    var hours = 0;
    for (var i = 0; i < themes.length; i++) {
      hours += themes[i].hours;
    }
    return hours;
  }

  function getReplacedSubjectsIds(replacedSubjects) {
    var replacedSubjectsIds = [];
    for (var i = 0; i < replacedSubjects.length; i++) {
      replacedSubjectsIds.push(replacedSubjects[i].subject.id);
    }
    return replacedSubjectsIds;
  }

  function moduleCanBeAddedAsReplaced(arrayUtils, replacedModulesThemes, selectedModuleId) {
    var replacedModulesIds = [];
    for (var i = 0; i < replacedModulesThemes.length; i++) {
      if (replacedModulesThemes[i].isModule) {
        replacedModulesIds.push(replacedModulesThemes[i].curriculumVersionOmodule.id);
      } else {
        replacedModulesIds.push(replacedModulesThemes[i].curriculumVersionOmoduleTheme.module);
      }
    }
    return !arrayUtils.contains(replacedModulesIds, selectedModuleId);
  }

  function moduleThemeCanBeAddedAsReplaced(arrayUtils, replacedModulesThemes, selectedThemeId, selectedThemeModuleId) {
    var replacedModulesIds = [];
    var replacedModuleThemesIds = [];
    for (var i = 0; i < replacedModulesThemes.length; i++) {
      if (!replacedModulesThemes[i].isModule) {
        replacedModuleThemesIds.push(replacedModulesThemes[i].curriculumVersionOmoduleTheme.id);
      } else {
        replacedModulesIds.push(replacedModulesThemes[i].curriculumVersionOmodule.id);
      }
    }
    return !arrayUtils.contains(replacedModuleThemesIds, selectedThemeId) && !arrayUtils.contains(replacedModulesIds, selectedThemeModuleId);
  }

  function setGradesAndAssessments($q, scope, classifier, entity) {
    if (entity.isVocational) {
      scope.grades = classifier.queryForDropdown({mainClassCode: 'KUTSEHINDAMINE'});
      scope.assessments = classifier.queryForDropdown({mainClassCode: 'KUTSEHINDAMISVIIS'});
    } else {
      scope.grades = classifier.queryForDropdown({mainClassCode: 'KORGHINDAMINE'});
      scope.assessments = classifier.queryForDropdown({mainClassCode: 'HINDAMISVIIS'});
    }
    return $q.all([scope.grades.$promise, scope.assessments.$promise]).then(function (result) {
      var grades = result[0];
      var assessments = result[1];
      scope.gradesMap = classifier.toMap(grades);
      scope.assessmentsMap = classifier.toMap(assessments);
    });
  }

  function getFormalLearningColspan(application, isViewForm) {
    var colspan = 0;
    if (application.isVocational) {
      if (isViewForm) {
        colspan = application.status === 'VOTA_STAATUS_K' ? 6 : 7;
      } else {
        if (application.status === 'VOTA_STAATUS_K') {
          colspan = application.canEdit ? 5 : 6;
        } else {
          colspan = application.canEdit ? 6 : 7;
        }
      }
    } else {
      if (isViewForm) {
        colspan = application.status === 'VOTA_STAATUS_K' ? 8 : 9;
      } else {
        if (application.status === 'VOTA_STAATUS_K') {
          colspan = application.canEdit ? 7 : 8;
        } else {
          colspan = application.canEdit ? 8 : 9;
        }
      }
    }
    return colspan;
  }

  function getInformalLearningColspan(application, isViewForm) {
    var colspan = 0;
    if (application.isVocational) {
      if (isViewForm) {
        colspan = application.status === 'VOTA_STAATUS_K' ? 6 : 7;
      } else {
        if (application.status === 'VOTA_STAATUS_K') {
          colspan = application.canEdit ? 5 : 6;
        } else {
          colspan = application.canEdit ? 6 : 7;
        }
      }
    } else {
      colspan = application.status === 'VOTA_STAATUS_K' ? 4 : 5;
    }
    return colspan;
  }

  function setColspans(scope) {
    scope.formalLearningColspan = getFormalLearningColspan(scope.application, scope.formState.viewForm);
    scope.informalLearningColspan = getInformalLearningColspan(scope.application, scope.formState.viewForm);
  }

  function canSeeNominalStudyExtension(auth, application) {
    if (application.status !== 'VOTA_STAATUS_K' && application.hasAbroadStudies) {
      return !auth.isStudent() || application.nominalType !== null;
    }
    return false;
  }

angular.module('hitsaOis').controller('ApelApplicationEditController', function ($filter, $location, $rootScope, $route, $scope, $q,
    ApelApplicationUtils, ArrayUtils, Classifier, DataUtils, HigherGradeUtil, VocationalGradeUtil, QueryUtils,
    config, dialogService, message, oisFileService) {

    var ApelApplicationEndpoint = QueryUtils.endpoint('/apelApplications');
    $scope.auth = $route.current.locals.auth;
    $scope.application = {};
    $scope.formState = {};
    $scope.formState.viewForm = false;

    Classifier.queryForDropdown({ mainClassCode: 'NOM_PIKEND' }, function (response) {
      $scope.nominalStudyExtensions = Classifier.toMap(response);
    });

    function entityToForm(entity) {
      $scope.application = entity;
      $scope.school = entity.school;
      DataUtils.convertStringToDates($scope.application, ['inserted', 'confirmed', 'newNominalStudyEnd', 'oldNominalStudyEnd']);
      ($scope.application.abroadStudyPeriods || []).forEach(function (period) {
        DataUtils.convertStringToDates(period, ['start', 'end']);
      });

      if (entity.hasAbroadStudies) {
        $scope.abroadStudiesCredits = ApelApplicationUtils.abroadStudiesCredits(entity);
      }
      if (entity.canExtendNominalDuration && !entity.nominalType) {
        $scope.application.nominalType = 'NOM_PIKEND_0';
        $scope.application.newNominalStudyEnd = entity.oldNominalStudyEnd;
      }
      if (entity.status === 'VOTA_STAATUS_E' && ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher())) {
        $scope.committees = QueryUtils.endpoint('/apelApplications/' + entity.id + '/committees').query();
      }
      $scope.application.committeeId = entity.committee ? entity.committee.id : null;
      $scope.canChangeTransferStatus = entity.canChangeTransferStatus;
      $scope.nominalDurationDisabled = entity.isEhisSent || ['VOTA_STAATUS_E', 'VOTA_STAATUS_V'].indexOf(entity.status) === -1;
      $scope.canSeeNominalStudyExtension = canSeeNominalStudyExtension($scope.auth, entity);

      setGradesAndAssessments($q, $scope, Classifier, entity).then(function () {
        getDataForInformalLearningTables($scope);
        getDataForFormalLearningTables($scope);

        if (!entity.isVocational) {
          $scope.grades = HigherGradeUtil.orderedGrades($scope.grades);
        }
      });

      setColspans($scope);
    }

    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      entityToForm(entity);
    } else {
      $scope.application.status = 'VOTA_STAATUS_K';
    }
    $scope.applicationPdfUrl = config.apiUrl + '/apelApplications/print/' + $scope.application.id + '/application.pdf';

    if ($scope.auth.isStudent() && !$scope.application.student) {
      QueryUtils.endpoint('/autocomplete/students?id=' + $scope.auth.student).get({}, function (result) {
        if (result && result.totalElements === 1) {
          $scope.application.student = result.content[0];
        }
      });
    }

    $scope.$watch('application.student', function () {
      if ($scope.application && $scope.application.student && !$scope.application.id) {
        createNewApplication();
      }
    });

    function createNewApplication() {
      var application = new ApelApplicationEndpoint($scope.application);
      application.$save().then(function () {
        message.info('main.messages.create.success');
        $location.path('/apelApplication/' + application.id + '/edit?_noback');
      }).catch(angular.noop);
    }

    function getRecord(recordId) {
      return $scope.application.records[getRecordIndex(recordId)];
    }

    function getRecordIndex(recordId) {
      for (var i = 0; i < $scope.application.records.length; i++) {
        if ($scope.application.records[i].id === recordId) {
          return i;
        }
      }
    }

    function getInformalSubjectOrModuleIndex(record, subjectOrModuleId) {
      for (var i = 0; i < record.informalSubjectsOrModules.length; i++) {
        if (record.informalSubjectsOrModules[i].id && subjectOrModuleId && record.informalSubjectsOrModules[i].id === subjectOrModuleId) {
          return i;
        }
      }
    }

    function getFormalSubjectOrModuleIndex(record, subjectOrModuleId) {
      for (var i = 0; i < record.formalSubjectsOrModules.length; i++) {
        if (record.formalSubjectsOrModules[i].id && subjectOrModuleId && record.formalSubjectsOrModules[i].id === subjectOrModuleId) {
          return i;
        }
      }
    }

    function removeNotAcquiredOutcomes(informalSubjectsOrModules) {
      for (var i = 0; i < informalSubjectsOrModules.length; i++) {
        var acquiredOutcomes = [];
        for (var j = 0; j < informalSubjectsOrModules[i].outcomes.length; j++) {
          var outcome = informalSubjectsOrModules[i].outcomes[j];
          if (outcome.acquired) {
            acquiredOutcomes.push(outcome);
          }
        }
        informalSubjectsOrModules[i].outcomes = acquiredOutcomes;
      }
    }

    function changeGradeObjectToCode(subjectsOrModules) {
      for (var i = 0; i < subjectsOrModules.length; i++) {
        if (subjectsOrModules[i].grade && subjectsOrModules[i].grade.code) {
          subjectsOrModules[i].grade = subjectsOrModules[i].grade.code;
        }
      }
    }

    $scope.nominalTypeChange = function () {
      var newNominalStudyEnd = new Date($scope.application.oldNominalStudyEnd);
      switch ($scope.application.nominalType) {
        case "NOM_PIKEND_0":
          $scope.application.newNominalStudyEnd = $scope.application.oldNominalStudyEnd;
          break;
        case "NOM_PIKEND_1":
          newNominalStudyEnd.setDate(newNominalStudyEnd.getDate() + 180);
          $scope.application.newNominalStudyEnd = newNominalStudyEnd;
        break;
        case "NOM_PIKEND_2":
          newNominalStudyEnd.setDate(newNominalStudyEnd.getDate() + 360);
          $scope.application.newNominalStudyEnd = newNominalStudyEnd;
          break;
      }
    };

    $scope.hideInvalid = function (cl) {
      return !Classifier.isValid(cl);
    };

    $scope.changeInformalTransferStatus = function (informalSubjectOrModule) {
      var subjectOrModuleId = informalSubjectOrModule.subjectId || informalSubjectOrModule.moduleId;
      var recordIndex = getRecordIndex(informalSubjectOrModule.recordId);
      var subjectOrModuleIndex = getInformalSubjectOrModuleIndex($scope.application.records[recordIndex], subjectOrModuleId);
      var transferStatus = $scope.application.records[recordIndex].informalSubjectsOrModules[subjectOrModuleIndex].transfer;

      $scope.application.records[recordIndex].informalSubjectsOrModules[subjectOrModuleIndex].transfer = !transferStatus;
    };

    $scope.editInformalLearning = function (recordId) {
      var dialogTemplate = "";
      if ($scope.application.curriculumVersion.isVocational) {
        dialogTemplate = 'apelApplication/templates/informal.learning.vocational.edit.dialog.html';
      } else {
        dialogTemplate = 'apelApplication/templates/informal.learning.higher.edit.dialog.html';
      }

      dialogService.showDialog(dialogTemplate, function (dialogScope) {
        dialogScope.formState = {};
        dialogScope.student = $scope.application.student;
        dialogScope.curriculumVersionId = $scope.application.curriculumVersion.id;
        dialogScope.isVocational = $scope.application.curriculumVersion.isVocational;
        dialogScope.gradesMap = $scope.gradesMap;
        dialogScope.assessmentsMap = $scope.assessmentsMap;

        function addMissingValuesToInformalModules(informalSubjectsOrModules) {
          for (var i = 0; i < informalSubjectsOrModules.length; i++) {
            $q(function () {
              var entry = informalSubjectsOrModules[i];
              entry.isModule = !entry.curriculumVersionOmoduleTheme;
              entry.grade = dialogScope.gradesMap[entry.grade];

              entry.module = entry.curriculumVersionOmodule;
              entry.hours = entry.isModule ? getHours(entry.curriculumVersionOmodule.themes) : entry.curriculumVersionOmoduleTheme.hours;
              if (entry.isModule) {
                entry.EKAP = entry.curriculumVersionOmodule.credits;
                getModuleOutcomes(entry.curriculumVersionOmodule.curriculumModule, entry.outcomes).then(function (allOutcomes) {
                  entry.outcomes = allOutcomes;
                });
              } else {
                entry.EKAP = entry.curriculumVersionOmoduleTheme.credits;
                getThemeOutcomes(entry.curriculumVersionOmoduleTheme.id, entry.outcomes).then(function (allOutcomes) {
                  entry.outcomes = allOutcomes;
                });
              }
            });
          }
        }

        function addNewInformalExperienceRow() {
          dialogScope.record.informalExperiences.push({
            nameEt: null,
            placeTime: null,
            hours: null,
            documents: null,
            type: null
          });
        }

        if (recordId) {
          dialogScope.record = angular.copy(getRecord(recordId));
          if (dialogScope.isVocational) {
            addMissingValuesToInformalModules(dialogScope.record.informalSubjectsOrModules);
          }
        } else {
          dialogScope.record = {
            informalExperiences: [],
            informalSubjectsOrModules: []
          };
          addNewInformalExperienceRow();
        }

        if (dialogScope.isVocational) {
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({
            student: dialogScope.student.id,
            curriculumVersion: dialogScope.curriculumVersionId
          }).$promise.then(function (result) {
            dialogScope.modulesAndThemes = getModulesAndThemes(result);
          });
        } else {
          QueryUtils.endpoint('/autocomplete/subjectsList').query({
            student: dialogScope.student.id,
            curriculumSubjects: true,
            curriculumVersion: dialogScope.curriculumVersionId,
            withCredits: true,
            noFinalSubjects: true
          }).$promise.then(function (subjects) {
            dialogScope.subjects = subjects;
          });

          QueryUtils.endpoint('/apelApplications/freeChoiceModule/:student').search({
            student: dialogScope.student.id
          }).$promise.then(function (result) {
            dialogScope.freeChoiceModule = result.id ? result : null;
          });
        }

        dialogScope.selectedModuleOrThemeChanged = function () {
          var selectedItem = dialogScope.selectedModuleOrTheme;
          if (selectedItem) {
            if (selectedItem.isModule) {
              if (moduleCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.informalSubjectsOrModules, selectedItem.moduleId)) {
                QueryUtils.endpoint('/occupationModule').get({id: selectedItem.id}).$promise.then(function (oModule) {
                  getModuleOutcomes(oModule.curriculumModule, null).then(function (outcomes) {
                    addNewSubtitutableModuleTheme(selectedItem, getHours(oModule.themes), oModule.credits, outcomes);
                  });
                });
              } else {
                message.error('apel.error.moduleOrItsThemeHasAlreadyBeenAdded');
              }
            } else {
              if (moduleThemeCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.informalSubjectsOrModules, selectedItem.themeId, selectedItem.moduleId)) {
                QueryUtils.endpoint('/occupationModule/theme').get({id: selectedItem.id}).$promise.then(function (theme) {
                  getThemeOutcomes(selectedItem.id, null).then(function (outcomes) {
                    addNewSubtitutableModuleTheme(selectedItem, theme.hours, theme.credits, outcomes);
                  });
                });
              } else {
                message.error('apel.error.themeOrParentModuleHasAlreadyBeenAdded');
              }
            }
            dialogScope.selectedModuleOrTheme = null;
          }
        };

        function getModuleOutcomes(curriculumModuleId, acquiredOutcomes) {
          return QueryUtils.endpoint('/curriculumModule/' + curriculumModuleId + '/outcomes').query().$promise.then(function (result) {
            return setOutcomeSelection(result, acquiredOutcomes);
          });
        }

        function getThemeOutcomes(themeId, acquiredOutcomes) {
          return QueryUtils.endpoint('/occupationModule/theme/' + themeId + '/outcomes').query().$promise.then(function (result) {
            return setOutcomeSelection(result, acquiredOutcomes);
          });
        }

        function setOutcomeSelection(outcomes, acquiredOutcomes) {
          var selection = [];
          for (var i = 0; i < outcomes.length; i++) {
            var curriculumModuleOutcome = outcomes[i];
            var acquiredOutcome = acquiredOutcomes ? acquiredOutcomes.filter(function (outcome) {
              return curriculumModuleOutcome.id === outcome.curriculumModuleOutcomes.id;
            })[0] : undefined;

            selection.push({
              id: acquiredOutcome ? acquiredOutcome.id : null,
              curriculumModuleOutcomes: curriculumModuleOutcome,
              acquired: angular.isDefined(acquiredOutcome)
            });
          }
          return selection;
        }

        function addNewSubtitutableModuleTheme(selectedModuleOrTheme, hours, EKAP, outcomes) {
          if (!selectedModuleOrTheme.isModule) {
            QueryUtils.endpoint('/occupationModule/theme').get({id: selectedModuleOrTheme.id}, function (theme) {
              QueryUtils.endpoint('/occupationModule').get({id: theme.module}, function (oModule) {
                addNewSubtitutableModuleThemeRow(selectedModuleOrTheme, theme, oModule, hours, EKAP, outcomes);
              });
            });
          } else {
            QueryUtils.endpoint('/occupationModule').get({id: selectedModuleOrTheme.id}, function (oModule) {
              addNewSubtitutableModuleThemeRow(selectedModuleOrTheme, null, oModule, hours, EKAP, outcomes);
            });
          }
        }

        function addNewSubtitutableModuleThemeRow(selectedModuleOrTheme, oModuleTheme, oModule, hours, EKAP, outcomes) {
          var grade = dialogScope.gradesMap.KUTSEHINDAMINE_A;
          var newModuleTheme = {
            isModule: selectedModuleOrTheme.isModule,
            curriculumVersionOmoduleTheme: oModuleTheme,
            curriculumVersionOmodule: oModule,
            hours: hours,
            EKAP: EKAP,
            grade: grade,
            outcomes: outcomes,
            skills: null
          };
          dialogScope.record.informalSubjectsOrModules.push(newModuleTheme);
        }

        dialogScope.$watch('formState.selectedSubject', function () {
          if (!dialogScope.formState.selectedSubject) {
            return;
          }

          if (!ArrayUtils.contains(getReplacedSubjectsIds(dialogScope.record.informalSubjectsOrModules), dialogScope.formState.selectedSubject.id)) {
            var subject = dialogScope.formState.selectedSubject;

            if (dialogScope.subjects.indexOf(dialogScope.formState.selectedSubject) !== -1) {
              QueryUtils.endpoint('/apelApplications/subjectModule').search({
                curriculumVersionId: dialogScope.curriculumVersionId,
                subjectId: subject.id
              }).$promise.then(function (hModule) {
                var isOptional = getSubjectIsOptional(subject.id, hModule);
                addNewSubtitutableSubjectRow(subject, hModule, isOptional, subject.credits);
              });
            } else {
              addNewSubtitutableSubjectRow(subject, dialogScope.freeChoiceModule, true, subject.credits);
            }
          } else {
            message.error('apel.error.subjectHasAlreadyBeenAdded');
          }
          dialogScope.formState.selectedSubject = null;
        });

        function addNewSubtitutableSubjectRow(subject, hModule, isOptional, credits) {
          var grade = dialogScope.gradesMap.KORGHINDAMINE_A;
          var newSubject = {
            subject: subject,
            curriculumVersionHmodule: hModule,
            isOptional: isOptional,
            credits: credits,
            skills: null,
            grade: grade
          };
          dialogScope.record.informalSubjectsOrModules.push(newSubject);
        }

        dialogScope.submitInformalLearning = function () {
          if (dialogScope.record.informalExperiences.length <= 0) {
            message.error('apel.error.atLeastOneOtherInformalLearning');
          } else if (dialogScope.record.informalSubjectsOrModules.length <= 0) {
            if (dialogScope.isVocational) {
              message.error('apel.error.atLeastOneSubstitutableModule');
            } else {
              message.error('apel.error.atLeastOneSubstitutableSubject');
            }
          } else {
            dialogScope.submit();
          }
        };

        dialogScope.removeSubtitutableModuleTheme = function (row) {
          ArrayUtils.remove(dialogScope.record.informalSubjectsOrModules, row);
        };

        dialogScope.addNewInformalExperienceRow = function () {
          addNewInformalExperienceRow();
        };

        dialogScope.removeInformalExperienceRow = function (row) {
          ArrayUtils.remove(dialogScope.record.informalExperiences, row);
        };

        dialogScope.delete = function () {
          var ApelApplicationRecordEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/record');
          new ApelApplicationRecordEndpoint(dialogScope.record).$delete().then(function (application) {
            message.info('apel.messages.informalLearningRemoved');
            entityToForm(application);
          }).catch(angular.noop);

          dialogScope.cancel();
        };

      }, function (submittedDialogScope) {
        if ($scope.application.curriculumVersion.isVocational) {
          removeNotAcquiredOutcomes(submittedDialogScope.record.informalSubjectsOrModules);
        }
        changeGradeObjectToCode(submittedDialogScope.record.informalSubjectsOrModules);
        submittedDialogScope.record.isFormalLearning = false;

        var ApelApplicationRecordEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/record');
        var record = new ApelApplicationRecordEndpoint(submittedDialogScope.record);

        if (angular.isDefined(submittedDialogScope.record.id)) {
          record.$update().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        } else {
          record.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        }
      });
    };

    $scope.changeFormalTransferStatus = function (formalSubjectOrModule) {
      var subjectOrModuleId = formalSubjectOrModule.subjectOrModuleId;
      var recordIndex = getRecordIndex(formalSubjectOrModule.recordId);
      var subjectOrModuleIndex = getFormalSubjectOrModuleIndex($scope.application.records[recordIndex], subjectOrModuleId);

      var transferStatus = $scope.application.records[recordIndex].formalSubjectsOrModules[subjectOrModuleIndex].transfer;
      $scope.application.records[recordIndex].formalSubjectsOrModules[subjectOrModuleIndex].transfer = !transferStatus;

      if ($scope.application.hasAbroadStudies) {
        $scope.abroadStudiesCredits = ApelApplicationUtils.abroadStudiesCredits($scope.application);
      }
    };

    $scope.transferFromApplication = function () {
      QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/transferFromApplication').get().$promise
        .then(function (result) {
          message.info('apel.formalLearnings.transferSuccess');
          entityToForm(result);
        });
    };

    $scope.editFormalLearning = function (recordId) {
      var clMapper;
      var dialogTemplate = "";
      if ($scope.application.curriculumVersion.isVocational) {
        dialogTemplate = 'apelApplication/templates/formal.learning.vocational.edit.dialog.html';
        clMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE'});
      } else {
        dialogTemplate = 'apelApplication/templates/formal.learning.higher.edit.dialog.html';
        clMapper = Classifier.valuemapper({ grade: 'KORGHINDAMINE'});
      }

      dialogService.showDialog(dialogTemplate, function (dialogScope) {
        dialogScope.auth = $scope.auth;
        dialogScope.formState = {};
        dialogScope.student = $scope.application.student;
        dialogScope.currentDate = new Date();
        dialogScope.curriculumVersionId = $scope.application.curriculumVersion.id;
        dialogScope.isVocational = $scope.application.curriculumVersion.isVocational;
        dialogScope.apelSchools = QueryUtils.endpoint('/autocomplete/apelschools').query();
        dialogScope.gradesMap = $scope.gradesMap;
        dialogScope.assessmentsMap = $scope.assessmentsMap;
        dialogScope.hideInvalid = $scope.hideInvalid;

        Classifier.queryForDropdown({mainClassCode: 'RIIK'}, function(result) {
          dialogScope.countryMap = Classifier.toMap(result);
        });
        Classifier.queryForDropdown({mainClassCode: 'EHIS_KOOL'}, function(result) {
          dialogScope.ehisSchoolMap = Classifier.toMap(result);
        });

        if (dialogScope.isVocational) {
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({
            student: dialogScope.student.id,
            curriculumVersion: dialogScope.curriculumVersionId
          }).$promise.then(function (result) {
            dialogScope.modulesAndThemes = getModulesAndThemes(result);
          });
          dialogScope.curriculumModules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query(
            {student: dialogScope.student.id, curriculumVersion: dialogScope.curriculumVersionId});
        } else {
          QueryUtils.endpoint('/autocomplete/subjectsList').query({
            student: dialogScope.student.id,
            curriculumSubjects: true,
            curriculumVersion: dialogScope.curriculumVersionId,
            withCredits: true,
            noFinalSubjects: true
          }).$promise.then(function (subjects) {
            dialogScope.subjects = subjects;
          });
          QueryUtils.endpoint('/apelApplications/studentModules/:student').query({
            student: dialogScope.student.id
          }).$promise.then(function(modules) {
            dialogScope.curriculumModules = modules;
          });
        }

        function addNewEmptyFormalSubjectOrModule() {
          dialogScope.formState.isMySchool = false;
          dialogScope.formState.isNewSchool = false;
          dialogScope.grades = null;
          dialogScope.record.assessment = null;
          dialogScope.record.curriculumVersionOmodule = null;
          dialogScope.record.subject = null;
          dialogScope.record.newTransferableSubjectOrModule = {
            isNew: true,
            newApelSchool: null,
            type: 'VOTA_AINE_LIIK_M',
            assessment: null
          };
          getTransferableSubjectsOrModules(dialogScope.record.newTransferableSubjectOrModule.type);
        }

        function addMissingValuesToFormalSubjectsOrModules(formalSubjectsOrModules) {
          for (var i = 0; i < formalSubjectsOrModules.length; i++) {
            $q(function () {
              var entry = formalSubjectsOrModules[i];
              if (entry.curriculumVersionOmodule) {
                entry.module = entry.curriculumVersionOmodule;
              }
              if (entry.curriculumVersionHmodule) {
                entry.module = entry.curriculumVersionHmodule;
              }
              if (entry.grade && !entry.grade.code) {
                entry.grade = dialogScope.gradesMap[formalSubjectsOrModules[i].grade];
              }
            });
          }
        }

        function addMissingValuesToFormalReplacedModules(replacedFormalModules) {
          for (var i = 0; i < replacedFormalModules.length; i++) {
            var entry = replacedFormalModules[i];
            entry.isModule = !entry.curriculumVersionOmoduleTheme;
          }
        }

        if (recordId) {
          dialogScope.record = angular.copy(getRecord(recordId));
          addMissingValuesToFormalSubjectsOrModules(dialogScope.record.formalSubjectsOrModules);
          if (dialogScope.isVocational) {
            addMissingValuesToFormalReplacedModules(dialogScope.record.formalReplacedSubjectsOrModules);
          }
        } else {
          dialogScope.record = {
            formalSubjectsOrModules: [],
            formalReplacedSubjectsOrModules: []
          };
          addNewEmptyFormalSubjectOrModule();
        }

        dialogScope.$watch('formState.isMySchool', function () {
          dialogScope.record.school = $scope.school;

          if (dialogScope.record.newTransferableSubjectOrModule) {
            dialogScope.record.newTransferableSubjectOrModule.isMySchool = dialogScope.formState.isMySchool;
          }

          if (recordId) {
            if (dialogScope.record.newTransferableSubjectOrModule) {
              var copy = angular.copy(dialogScope.record.newTransferableSubjectOrModule);
              dialogScope.formState.isNewSchool = copy.isNewSchool;
              dialogScope.record.newTransferableSubjectOrModule = {
                id: copy.id,
                isNew: copy.isNew,
                isNewSchool: copy.isNewSchool,
                isMySchool: copy.isMySchool,
                type: copy.type,
                grade: copy.grade,
                gradeDate: copy.gradeDate,
                teachers: copy.teachers,
                assessment: copy.assessment,
                credits: copy.credits,
                subjectCode: copy.subjectCode,
                curriculumVersionHmodule: copy.curriculumVersionHmodule,
                isOptional: copy.isOptional,
                transferableSubjectOrModuleIndex: copy.transferableSubjectOrModuleIndex,
                transfer: copy.transfer
              };
              getGrades(dialogScope.record.newTransferableSubjectOrModule.assessment);
              if (copy.isMySchool === true) {
                if (copy.subject) {
                  getTransferableSubjects(copy.type);
                  setSubjectData(copy.subject);
                } else if (dialogScope.record.subject) {
                  getTransferableSubjects(copy.type);
                  setSubjectData(dialogScope.record.subject);
                } else if (copy.curriculumVersionOmodule) {
                  getTransferableModules(copy.type);
                  setModuleData(copy.curriculumVersionOmodule);
                } else if (dialogScope.record.curriculumVersionOmodule) {
                  getTransferableModules(copy.type);
                  setModuleData(dialogScope.record.curriculumVersionOmodule);
                }
              } else {
                if (copy.isNewSchool) {
                  dialogScope.record.newTransferableSubjectOrModule.newApelSchool = copy.newApelSchool;
                } else {
                  dialogScope.record.newTransferableSubjectOrModule.apelSchool = dialogScope.record.apelSchool;
                }
                dialogScope.record.newTransferableSubjectOrModule.nameEt = dialogScope.record.nameEt;
                dialogScope.record.newTransferableSubjectOrModule.nameEn = dialogScope.record.nameEn;
                dialogScope.record.newTransferableSubjectOrModule.type = 'VOTA_AINE_LIIK_M';
              }
            }
          } else {
            dialogScope.record.newTransferableSubjectOrModule = {};
            dialogScope.record.newTransferableSubjectOrModule.isNew = true;
            dialogScope.record.newTransferableSubjectOrModule.apelSchool = null;
            if (dialogScope.formState.isMySchool) {
              dialogScope.record.newTransferableSubjectOrModule.type = 'VOTA_AINE_LIIK_V';
            } else {
              dialogScope.record.newTransferableSubjectOrModule.type = 'VOTA_AINE_LIIK_M';
            }
            getTransferableSubjectsOrModules(dialogScope.record.newTransferableSubjectOrModule.type);
          }
        });

        dialogScope.typeChanged = function (typeCode) {
          getTransferableSubjectsOrModules(typeCode);
        };

        function getTransferableModules(typeCode) {
          if (typeCode === 'VOTA_AINE_LIIK_O') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query(
              {student: dialogScope.student.id, curriculumModules: true, curriculumVersion: dialogScope.curriculumVersionId}).$promise.then(function (modules) {
              setTransferableModules(typeCode, modules);
              setCurriculumVersionOmodule();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_V') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query(
              {student: dialogScope.student.id, otherStudents: true, ignoreStatuses: true}).$promise.then(function (modules) {
              setTransferableModules(typeCode, modules);
              setCurriculumVersionOmodule();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_M') {
            if (dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule) {
              QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query(
                {id: dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule.id, student: dialogScope.student.id,
                  curriculumModules: false, curriculumVersion: dialogScope.curriculumVersionId}).$promise.then(function (modules) {
                setTransferableModules(typeCode, modules);
                setCurriculumVersionOmodule();
              });
            }
          }
        }

        function setTransferableModules(typeCode, modules) {
          // type could be changed before query has finished therefore it needs to check if type code is still the same
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.type === typeCode) {
            dialogScope.transferableModules = modules;
          }
        }

        function setCurriculumVersionOmodule() {
          var moduleInTypeModules = false;
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule &&
            dialogScope.transferableModules !== null) {
            for (var i = 0; i < dialogScope.transferableModules.length; i++) {
              if (dialogScope.transferableModules[i].id === dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule.id) {
                moduleInTypeModules = true;
              }
            }
          }
          if (!moduleInTypeModules && dialogScope.record.newTransferableSubjectOrModule) {
            dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = null;
          }
        }

        function getTransferableSubjects(typeCode) {
          if (typeCode === 'VOTA_AINE_LIIK_O') {
            QueryUtils.endpoint('/autocomplete/subjectsList').query({
              student: dialogScope.student.id,
              curriculumSubjects: true,
              curriculumVersion: dialogScope.curriculumVersionId,
              withCredits: false,
              noFinalSubjects: true
            })
            .$promise.then(function (subjects) {
              setTransferableSubjects(typeCode, subjects);
              setSubject();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_V') {
            QueryUtils.endpoint('/autocomplete/subjectsList').query({
              student: dialogScope.student.id,
              otherStudents: true,
              withCredits: false,
              noFinalSubjects: true
            }).$promise.then(function (subjects) {
              setTransferableSubjects(typeCode, subjects);
              setSubject();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_M') {
            if (dialogScope.record.newTransferableSubjectOrModule.subject) {
              QueryUtils.endpoint('/autocomplete/subjectsList').query({
                student: dialogScope.student.id,
                curriculumSubjects: false,
                curriculumVersion: dialogScope.curriculumVersionId,
                ignoreCurriculumVersionStatus: true,
                withCredits: false,
                noFinalSubjects: true
              }).$promise.then(function (subjects) {
                setTransferableSubjects(typeCode, subjects);
                setSubject();
              });
            }
          }
        }

        function setTransferableSubjects(typeCode, subjects) {
          // type could be changed before query has finished therefore it needs to check if type code is still the same
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.type === typeCode) {
            dialogScope.transferableSubjects = subjects;
          }
        }

        function setSubject() {
          var subjectInTypeSubjects = false;
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.subject &&
            dialogScope.transferableSubjects !== null) {
            for (var i = 0; i < dialogScope.transferableSubjects.length; i++) {
              if (dialogScope.transferableSubjects[i].id === dialogScope.record.newTransferableSubjectOrModule.subject.id) {
                subjectInTypeSubjects = true;
              }
            }
          }
          if (!subjectInTypeSubjects) {
            dialogScope.record.newTransferableSubjectOrModule.subject = null;
          }
        }

        dialogScope.gradeValue = function (code) {
          var grade = clMapper.objectmapper({ grade: code }).grade;
          return grade ? ($scope.auth.school.letterGrades && !dialogScope.isVocational ? grade.value2 : grade.value) : undefined;
        };

        dialogScope.newSchoolCountryChanged = function (countryCode) {
          dialogScope.newSchoolEst = countryCode === 'RIIK_EST';
          dialogScope.record.newTransferableSubjectOrModule.newApelSchool.ehisSchool = null;
        };

        dialogScope.ehisSchoolChanged = function (ehisCode) {
          var ehisSchool = dialogScope.ehisSchoolMap[ehisCode];
          dialogScope.record.newTransferableSubjectOrModule.newApelSchool.nameEt = ehisSchool.nameEt;
          dialogScope.record.newTransferableSubjectOrModule.newApelSchool.nameEn = ehisSchool.nameEn;
        };

        dialogScope.apelSchoolChanged = function (apelSchool) {
          if (!angular.isDefined(apelSchool.id)) {
            // apel school is created in this formal learning block
            dialogScope.record.newTransferableSubjectOrModule.newApelSchool = apelSchool;
          }
          dialogScope.record.newTransferableSubjectOrModule.country = dialogScope.countryMap[apelSchool.country];
        };

        dialogScope.subjectChanged = function (subject) {
          if (subject) {
            if (dialogScope.record.subject) {
              if (subject.id !== dialogScope.record.subject.id) {
                setSubjectData(subject);
                setFormerSubjectResult(subject);
              }
            } else {
              setSubjectData(subject);
              setFormerSubjectResult(subject);
            }
          }
        };

        function setSubjectData(subject) {
          dialogScope.record.newTransferableSubjectOrModule.subject = subject;
          dialogScope.record.subject = subject;
          dialogScope.record.newTransferableSubjectOrModule.subjectCode = subject.code;
          dialogScope.record.newTransferableSubjectOrModule.credits = subject.credits;

          var assessment = dialogScope.assessmentsMap[subject.assessment];
          if (dialogScope.record.newTransferableSubjectOrModule.assessment !== assessment.code) {
            dialogScope.record.assessment = assessment;
            dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
            dialogScope.record.newTransferableSubjectOrModule.grade = null;
            getGrades(assessment.code);
          }
        }

        function setFormerSubjectResult(subject) {
          if (subject && subject.gradeCode) {
            dialogScope.record.newTransferableSubjectOrModule.grade = subject.gradeCode;
            dialogScope.record.newTransferableSubjectOrModule.gradeDate = subject.gradeDate;
            dialogScope.record.newTransferableSubjectOrModule.teachers = subject.teachers;
          }
        }

        dialogScope.moduleChanged = function (oModule) {
          if (oModule) {
            if (dialogScope.record.curriculumVersionOmodule) {
              if (oModule.id !== dialogScope.record.curriculumVersionOmodule.id) {
                setModuleData(oModule);
                setFormerModuleResult(oModule);
              }
            } else {
              setModuleData(oModule);
              setFormerModuleResult(oModule);
            }
          }
        };

        function setModuleData(oModule) {
          dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = oModule;
          dialogScope.record.curriculumVersionOmodule = oModule;
          dialogScope.record.newTransferableSubjectOrModule.credits = oModule.credits;

          var assessment = dialogScope.assessmentsMap[oModule.assessment];
          if (dialogScope.record.newTransferableSubjectOrModule.assessment !== assessment.code) {
            dialogScope.record.assessment = assessment;
            dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
            dialogScope.record.newTransferableSubjectOrModule.grade = null;
            getGrades(assessment.code);
          }
        }

        function setFormerModuleResult(oModule) {
          if (oModule && oModule.gradeCode) {
            dialogScope.record.newTransferableSubjectOrModule.grade = oModule.gradeCode;
            dialogScope.record.newTransferableSubjectOrModule.gradeDate = oModule.gradeDate;
            dialogScope.record.newTransferableSubjectOrModule.teachers = oModule.teachers;
          }
        }

        dialogScope.assessmentTypeChanged = function (assessment) {
          dialogScope.record.newTransferableSubjectOrModule.grade = null;
          getGrades(assessment);
        };

        dialogScope.gradeSelectShownValue = function (grade) {
          return HigherGradeUtil.gradeSelectShownValue(grade, $scope.auth.school.letterGrades);
        };

        function getGrades(assessment) {
          if (dialogScope.isVocational) {
            if (assessment === 'KUTSEHINDAMISVIIS_M') {
              dialogScope.grades = $scope.grades.filter(function (grade) {
                return VocationalGradeUtil.isPositive(grade.code) && !VocationalGradeUtil.isDistinctive(grade.code);
              });
            } else if (assessment === 'KUTSEHINDAMISVIIS_E') {
              dialogScope.grades = $scope.grades.filter(function (grade) {
                return VocationalGradeUtil.isPositive(grade.code) && VocationalGradeUtil.isDistinctive(grade.code);
              });
            }
          } else {
            if (assessment === 'HINDAMISVIIS_H' || assessment === 'HINDAMISVIIS_E') {
              dialogScope.grades = $scope.grades.filter(function (grade) {
                return HigherGradeUtil.isPositive(grade.code) && HigherGradeUtil.isDistinctive(grade.code);
              });
            } else if (assessment === 'HINDAMISVIIS_A') {
              dialogScope.grades = $scope.grades.filter(function (grade) {
                return HigherGradeUtil.isPositive(grade.code) && !HigherGradeUtil.isDistinctive(grade.code);
              });
            }
          }
        }

        dialogScope.addNewSchool = function () {
          dialogScope.formState.isNewSchool = true;
          emptyNewSchoolForm();
        };

        dialogScope.cancelCreatingNewSchool = function () {
          dialogScope.formState.isNewSchool = false;
          emptyNewSchoolForm();
        };

        function emptyNewSchoolForm() {
          dialogScope.newSchoolEst = false;
          if (dialogScope.record.newTransferableSubjectOrModule) {
            dialogScope.record.newTransferableSubjectOrModule.apelSchool = null;
            dialogScope.record.newTransferableSubjectOrModule.newApelSchool = null;
          }
        }

        dialogScope.addNewTransferableSubjectOrModule = function () {
          dialogScope.dialogForm.$setPristine();
          addNewEmptyFormalSubjectOrModule();
        };

        function isModuleAlreadyTransferred(transferableModule) {
          var alreadyTransferedModules = (dialogScope.record.formalSubjectsOrModules || []).map(function (transfer) {
            if (transfer.curriculumVersionOmodule) {
              return transfer.curriculumVersionOmodule.id;
            }
          });
          var addedModule = transferableModule.curriculumVersionOmodule !== null && angular.isDefined(transferableModule.curriculumVersionOmodule) ?
            transferableModule.curriculumVersionOmodule.id : null;
          return alreadyTransferedModules.indexOf(addedModule) !== -1;
        }

        function isSubjectAlreadyTransferred(transferableSubject) {
          var alreadyTransferedSubjects = (dialogScope.record.formalSubjectsOrModules || []).map(function (transfer) {
            if (transfer.subject) {
              return transfer.subject.id;
            }
          });
          var addedSubject = transferableSubject.subject !== null && angular.isDefined(transferableSubject.subject) ?
            transferableSubject.subject.id : null;
          return alreadyTransferedSubjects.indexOf(addedSubject) !== -1;
        }

        dialogScope.addTransferableSubjectOrModule = function () {
          var transferableSubjectOrModule = dialogScope.record.newTransferableSubjectOrModule;

          if (dialogScope.isVocational) {
            if (isModuleAlreadyTransferred(transferableSubjectOrModule)) {
              message.error('apel.error.moduleHasAlreadyBeenAdded');
              return;
            }
          } else {
            if (isSubjectAlreadyTransferred(transferableSubjectOrModule)) {
              message.error('apel.error.subjectHasAlreadyBeenAdded');
              return;
            }
          }
          dialogScope.dialogForm.$setSubmitted();

          if (dialogScope.dialogForm.$valid) {
            dialogScope.record.newTransferableSubjectOrModule = null;
            transferableSubjectOrModule.isMySchool = dialogScope.formState.isMySchool;
            transferableSubjectOrModule.isNewSchool = dialogScope.formState.isNewSchool;
            transferableSubjectOrModule.isNew = false;

            if (!transferableSubjectOrModule.isMySchool) {
              transferableSubjectOrModule.type = 'VOTA_AINE_LIIK_M';
            }
            if (transferableSubjectOrModule.curriculumVersionOmodule) {
              transferableSubjectOrModule.module = transferableSubjectOrModule.curriculumVersionOmodule;
            }
            if (transferableSubjectOrModule.curriculumVersionHmodule) {
              transferableSubjectOrModule.module = transferableSubjectOrModule.curriculumVersionHmodule;
            }

            transferableSubjectOrModule.grade = dialogScope.gradesMap[transferableSubjectOrModule.grade];
            dialogScope.record.formalSubjectsOrModules.push(transferableSubjectOrModule);

            if (dialogScope.formState.isNewSchool) {
              addNewSchoolToSelection(transferableSubjectOrModule);
            }
          }
        };

        function addNewSchoolToSelection(transferableSubjectOrModule) {
          if (isNewSchoolAlreadyAdded(transferableSubjectOrModule.newApelSchool)) {
            return;
          }
          dialogScope.apelSchools.push(transferableSubjectOrModule.newApelSchool);
          dialogScope.apelSchools = dialogScope.apelSchools.sort(function (s1, s2) {
            return $rootScope.currentLanguageNameField(s1).localeCompare($rootScope.currentLanguageNameField(s2));
          });
        }

        function isNewSchoolAlreadyAdded(newSchool) {
          var schoolAlreadyAdded = false;
          for (var i = 0; i < dialogScope.apelSchools.length; i++) {
            if (newSchool.ehisSchool) {
              if (dialogScope.apelSchools[i].ehisSchool === newSchool.ehisSchool) {
                schoolAlreadyAdded = true;
              }
            } else {
              if (dialogScope.apelSchools[i].nameEt === newSchool.nameEt &&
                dialogScope.apelSchools[i].country === newSchool.country) {
                schoolAlreadyAdded = true;
              }
            }
          }
          return schoolAlreadyAdded;
        }

        dialogScope.apelSchoolsEhisCodes = function () {
          return dialogScope.apelSchools.filter(function (it) { return it.ehisSchool; }).map(function(it) {
            if (dialogScope.record.newTransferableSubjectOrModule.newApelSchool.ehisSchool !== it.ehisSchool) {
              return it.ehisSchool;
            }
          });
        };

        dialogScope.addSelectedSubject = function () {
          if (!ArrayUtils.contains(getReplacedSubjectsIds(dialogScope.record.formalReplacedSubjectsOrModules), dialogScope.selectedSubject.id)) {
            dialogScope.record.formalReplacedSubjectsOrModules.push({subject: dialogScope.selectedSubject});
          } else {
            message.error('apel.error.subjectHasAlreadyBeenAdded');
          }
          dialogScope.selectedSubject = null;
        };

        dialogScope.removeSubtitutableSubject = function (subject) {
          ArrayUtils.remove(dialogScope.record.formalReplacedSubjectsOrModules, subject);
        };

        dialogScope.editTransferableSubjectOrModule = function (transferableSubjectOrModuleIndex) {
          dialogScope.record.newTransferableSubjectOrModule = angular.copy(dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex]);
          dialogScope.record.newTransferableSubjectOrModule.transferableSubjectOrModuleIndex = transferableSubjectOrModuleIndex;

          dialogScope.formState.isMySchool = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].isMySchool;
          dialogScope.formState.isNewSchool = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].isNewSchool;
          dialogScope.record.apelSchool = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].apelSchool;
          if (dialogScope.record.apelSchool) {
            dialogScope.record.newTransferableSubjectOrModule.country = dialogScope.countryMap[dialogScope.record.apelSchool.country];
          }

          dialogScope.record.assessment = dialogScope.assessmentsMap[dialogScope.record.newTransferableSubjectOrModule.assessment];
          dialogScope.record.curriculumVersionOmodule = dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule;
          dialogScope.record.nameEt = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].nameEt;
          dialogScope.record.nameEn = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].nameEn;

          dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = dialogScope.record.formalSubjectsOrModules[transferableSubjectOrModuleIndex].curriculumVersionOmodule;

          if (dialogScope.record.newTransferableSubjectOrModule.grade) {
            dialogScope.record.newTransferableSubjectOrModule.grade = dialogScope.record.newTransferableSubjectOrModule.grade.code;
          }
          getGrades(dialogScope.record.newTransferableSubjectOrModule.assessment);
          getTransferableSubjectsOrModules(dialogScope.record.newTransferableSubjectOrModule.type);
        };

        function getTransferableSubjectsOrModules(typeCode) {
          if (dialogScope.isVocational) {
            getTransferableModules(typeCode);
          } else {
            getTransferableSubjects(typeCode);
          }
        }

        dialogScope.changeTransferableSubjectOrModule = function () {
          var changedTransferableSubjectOrModule = dialogScope.record.newTransferableSubjectOrModule;

          dialogScope.dialogForm.$setSubmitted();
          if (dialogScope.dialogForm.$valid) {
            dialogScope.record.formalSubjectsOrModules[changedTransferableSubjectOrModule.transferableSubjectOrModuleIndex] = changedTransferableSubjectOrModule;
            dialogScope.record.newTransferableSubjectOrModule = null;
            addMissingValuesToFormalSubjectsOrModules(dialogScope.record.formalSubjectsOrModules);

            if (dialogScope.formState.isNewSchool) {
              addNewSchoolToSelection(changedTransferableSubjectOrModule);
            }
          }
        };

        dialogScope.deleteTransferableSubjectOrModule = function () {
          var transferableSubjectOrModule = dialogScope.record.newTransferableSubjectOrModule;
          if (transferableSubjectOrModule) {
            if (transferableSubjectOrModule.transferableSubjectOrModuleIndex >= 0) {
              dialogScope.record.formalSubjectsOrModules.splice(transferableSubjectOrModule.transferableSubjectOrModuleIndex, 1);
            } else {
              dialogScope.record.newTransferableSubjectOrModule = null;
            }
          }
          dialogScope.record.newTransferableSubjectOrModule = null;
        };

        dialogScope.formalSelectedModuleOrThemeChanged = function () {
          var selectedItem = dialogScope.selectedModuleOrTheme;
          if (selectedItem) {
            if (selectedItem.isModule) {
              if (moduleCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.formalReplacedSubjectsOrModules, selectedItem.moduleId)) {
                addNewFormalSubtitutableModuleTheme(selectedItem);
              } else {
                message.error('apel.error.moduleOrItsThemeHasAlreadyBeenAdded');
              }
            } else {
              if (moduleThemeCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.formalReplacedSubjectsOrModules, selectedItem.themeId, selectedItem.moduleId)) {
                addNewFormalSubtitutableModuleTheme(selectedItem);
              } else {
                message.error('apel.error.themeOrParentModuleHasAlreadyBeenAdded');
              }
            }
            dialogScope.selectedModuleOrTheme = null;
          }
        };

        function addNewFormalSubtitutableModuleTheme(selectedModuleOrTheme) {
          if (!selectedModuleOrTheme.isModule) {
            QueryUtils.endpoint('/occupationModule/theme').get({id: dialogScope.selectedModuleOrTheme.id}, function (theme) {
              var oModuleTheme = theme;
              QueryUtils.endpoint('/occupationModule').get({id: oModuleTheme.module}, function (oModule) {
                addNewFormalSubtitutableModuleThemeRow(selectedModuleOrTheme, oModuleTheme, oModule);
              });
            });
          } else {
            QueryUtils.endpoint('/occupationModule').get({id: dialogScope.selectedModuleOrTheme.id}, function (oModule) {
              addNewFormalSubtitutableModuleThemeRow(selectedModuleOrTheme, null, oModule);
            });
          }
        }

        function addNewFormalSubtitutableModuleThemeRow(selectedModuleOrTheme, oModuleTheme, oModule) {
          var newModuleTheme = {
            isModule: selectedModuleOrTheme.isModule,
            curriculumVersionOmoduleTheme: oModuleTheme,
            curriculumVersionOmodule: oModule
          };
          dialogScope.record.formalReplacedSubjectsOrModules.push(newModuleTheme);
        }

        dialogScope.removeSubtitutableModuleTheme = function (row) {
          ArrayUtils.remove(dialogScope.record.formalReplacedSubjectsOrModules, row);
        };

        dialogScope.submitVocationalFormalLearning = function () {
          if (ArrayUtils.isEmpty(dialogScope.record.formalSubjectsOrModules)) {
            message.error('apel.error.atLeastOneTransferableModule');
          } else if (!areTransferredSubjectsOrModulesValid()) {
            message.error('apel.error.incompleteData');
          } else if (ArrayUtils.isEmpty(dialogScope.record.formalReplacedSubjectsOrModules)) {
            message.error('apel.error.atLeastOneSubstitutableModule');
          } else {
            formalSubjectsOrModulesToArray();
            ApelApplicationUtils.formalSubjectOrModulesObjectsToIdentifiers(dialogScope.record);
            dialogScope.submit();
          }
        };

        dialogScope.areTransferredSubjectsOrModulesValid = function () {
          return areTransferredSubjectsOrModulesValid();
        };

        function areTransferredSubjectsOrModulesValid() {
          if (!ArrayUtils.isEmpty(dialogScope.record.formalSubjectsOrModules)) {
            for (var i = 0; i < dialogScope.record.formalSubjectsOrModules.length; i++) {
              var som =  dialogScope.record.formalSubjectsOrModules[0];
              if (som.credits === null || som.assessment === null || som.grade === null) {
                return false;
              } else {
                if (dialogScope.isVocational) {
                  return som.nameEt !== null || som.curriculumVersionOmodule !== null;
                } else {
                  return ((som.nameEt !== null && som.nameEn !== null) || som.subject !== null) &&
                    som.curriculumVersionHmodule !== null;
                }
              }
            }
          }
          return false;
        }

        dialogScope.areReplacedModulesValid = function () {
          return !ArrayUtils.isEmpty(dialogScope.record.formalReplacedSubjectsOrModules);
        };

        dialogScope.areReplacedSubjectsValid = function () {
          if (!ApelApplicationUtils.allTransferredSubjectsInFreeChoiceModules(dialogScope.record)) {
            return !ArrayUtils.isEmpty(dialogScope.record.formalReplacedSubjectsOrModules);
          }
          return true;
        };

        dialogScope.submitHigherFormalLearning = function () {
          if (ArrayUtils.isEmpty(dialogScope.record.formalSubjectsOrModules)) {
            message.error('apel.error.atLeastOneTransferableSubject');
            return;
          } else {
            if (!areTransferredSubjectsOrModulesValid()) {
              message.error('apel.error.incompleteData');
              return;
            }
          }

          if (!ApelApplicationUtils.allTransferredSubjectsInFreeChoiceModules(dialogScope.record)) {
            if (ArrayUtils.isEmpty(dialogScope.record.formalReplacedSubjectsOrModules)) {
              message.error('apel.error.atLeastOneSubstitutableSubject');
              return;
            }
          }

          formalSubjectsOrModulesToArray();
          ApelApplicationUtils.formalSubjectOrModulesObjectsToIdentifiers(dialogScope.record);
          dialogScope.submit();
        };

        function formalSubjectsOrModulesToArray() {
          var array = [];
          angular.forEach(dialogScope.record.formalSubjectsOrModules, function(value) {
            array.push(value);
          });
          dialogScope.record.formalSubjectsOrModules = array;
        }

        dialogScope.delete = function () {
          var ApelApplicationRecordEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/record');
          new ApelApplicationRecordEndpoint(dialogScope.record).$delete().then(function (application) {
            message.info('apel.messages.formalLearningRemoved');
            entityToForm(application);
          }).catch(angular.noop);

          dialogScope.cancel();
        };

      }, function (submittedDialogScope) {
        changeGradeObjectToCode(submittedDialogScope.record.formalSubjectsOrModules);
        submittedDialogScope.record.isFormalLearning = true;
        var ApelApplicationRecordEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/record');
        var record = new ApelApplicationRecordEndpoint(submittedDialogScope.record);

        if (angular.isDefined(submittedDialogScope.record.id)) {
          record.$update().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        } else {
          record.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        }
      });
    };

    function getModulesAndThemes(result) {
      var modulesAndThemes = [];
      if (result) {
        result = sortByName(result);

        for (var i = 0; i < result.length; i++) {
          modulesAndThemes.push({
            id: result[i].id,
            moduleId: result[i].id,
            themeId: null,
            nameEt: result[i].nameEt,
            nameEn: result[i].nameEn,
            isModule: true
          });
          var themes = sortByName(result[i].themes);
          if (themes) {
            for (var j = 0; j < themes.length; j++) {
              modulesAndThemes.push({
                id: themes[j].id,
                moduleId: result[i].id,
                themeId: themes[j].id,
                nameEt: result[i].nameEt + "/" + themes[j].nameEt,
                nameEn: result[i].nameEn + "/" + themes[j].nameEn,
                isModule: false
              });
            }
          }
        }
        return modulesAndThemes;
      }
    }

    function sortByName(array) {
      return $filter('orderBy')(array, $scope.currentLanguageNameField());
    }

    var ApelApplicationFileEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/file');

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('components/file.add.dialog.html', function (dialogScope) {
        dialogScope.addedFiles = $scope.application.files;
      }, function (submittedDialogScope) {
        var data = submittedDialogScope.data;
        oisFileService.getFromLfFile(data.file[0], function (file) {
          data.oisFile = file;
          var newFile = new ApelApplicationFileEndpoint(data);
          newFile.$save().then(function(response) {
            message.info('main.messages.create.success');
            $scope.application.files.push(response);
          }).catch(angular.noop);
        });
      });
    };

    $scope.openEditCommentDialog = function (comment) {
      dialogService.showDialog('apelApplication/templates/comment.edit.dialog.html', function (dialogScope) {
        if (comment) {
          dialogScope.comment = angular.copy(comment);
        }
      }, function (submittedDialogScope) {
        var ApelApplicationCommentEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/comment');
        var comment = new ApelApplicationCommentEndpoint(submittedDialogScope.comment);

        if (angular.isDefined(submittedDialogScope.comment.id)) {
          comment.$update().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        } else {
          comment.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          }).catch(angular.noop);
        }
      });
    };

    $scope.deleteFile = function(file) {
      dialogService.confirmDialog({prompt: 'apel.deleteFileConfirm'}, function() {
        var deletedFile = new ApelApplicationFileEndpoint(file);
          deletedFile.$delete().then(function () {
            message.info('main.messages.delete.success');
            ArrayUtils.remove($scope.application.files, file);
          }).catch(angular.noop);
      });
    };

    $scope.getUrl = oisFileService.getUrl;

    $scope.save = function () {
      var application = new ApelApplicationEndpoint(ApelApplicationUtils.recordsToIdentifiers($scope.application));
      application.$update().then(function () {
        message.info('main.messages.create.success');
        entityToForm(application);
        $scope.apelApplicationForm.$setPristine();
      }).catch(angular.noop);
    };

    $scope.submit = function () {
      dialogService.confirmDialog({ prompt: 'apel.submitConfirm' }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/submit').put({}, function (response) {
          message.info('apel.messages.submitted');
          entityToForm(response);
        }, function (response) {
          ApelApplicationUtils.setRecordsWithErrors($scope.application, response);
        });
      });
    };

    $scope.sendBackToCreation = function () {
      ApelApplicationUtils.sendBackToCreation($scope.application, entityToForm);
    };

    $scope.sendToConfirm = function () {
      ApelApplicationUtils.sendToConfirm($scope.application, entityToForm);
    };

    $scope.sendToCommittee = function () {
      ApelApplicationUtils.sendToCommittee($scope.application, entityToForm);
    };

    $scope.confirm = function () {
      ApelApplicationUtils.confirm($scope.application, entityToForm);
    };

    $scope.sendBack = function () {
      ApelApplicationUtils.sendBack($scope.application, entityToForm);
    };

    $scope.reject = function () {
      ApelApplicationUtils.reject($scope.application, entityToForm);
    };

    $scope.removeConfirmation = function () {
      ApelApplicationUtils.removeConfirmation($scope.application, entityToForm);
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'apel.deleteConfirm' }, function () {
        var application = new ApelApplicationEndpoint($scope.application);
        application.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.path('/apelApplication');
        }).catch(angular.noop);
      });
    };
  }).controller('ApelApplicationViewController', function ($route, $scope, $q, ApelApplicationUtils, Classifier, DataUtils, QueryUtils,
      config, dialogService, oisFileService) {
    $scope.applicationId = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;
    $scope.application = {};
    $scope.formState = {};
    $scope.formState.viewForm = true;

    Classifier.queryForDropdown({ mainClassCode: 'NOM_PIKEND' }, function (response) {
      $scope.nominalStudyExtensions = Classifier.toMap(response);
    });

    function entityToForm(entity) {
      $scope.application = entity;
      $scope.school = entity.school;
      DataUtils.convertStringToDates($scope.application, ['inserted', 'confirmed', 'newNominalStudyEnd', 'oldNominalStudyEnd']);
      ($scope.application.abroadStudyPeriods || []).forEach(function (period) {
        DataUtils.convertStringToDates(period, ['start', 'end']);
      });

      $scope.application.committeeId = entity.committee ? entity.committee.id : null;
      $scope.canSeeNominalStudyExtension = canSeeNominalStudyExtension($scope.auth, entity);

      setGradesAndAssessments($q, $scope, Classifier, entity).then(function () {
        getDataForInformalLearningTables($scope);
        getDataForFormalLearningTables($scope);
      });
      setColspans($scope);

      if (entity.hasAbroadStudies) {
        $scope.abroadStudiesCredits = ApelApplicationUtils.abroadStudiesCredits(entity);
      }
      if (entity.canExtendNominalDuration && !entity.nominalType) {
        $scope.application.nominalType = 'NOM_PIKEND_0';
        $scope.application.newNominalStudyEnd = $scope.application.oldNominalStudyEnd;
      }
      if (entity.status === 'VOTA_STAATUS_E' && ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher())) {
        $scope.committees = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/committees').query();
      }
      $scope.application.committeeId = $scope.application.committee ? $scope.application.committee.id : null;
    }

    $scope.getUrl = oisFileService.getUrl;

    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      entityToForm(entity);
    } else {
      $scope.application.insertedBy = $scope.auth.fullname;
      $scope.application.inserted = new Date();
      $scope.application.status = "VOTA_STAATUS_K";
    }
    $scope.applicationPdfUrl = config.apiUrl + '/apelApplications/print/' + $scope.application.id + '/application.pdf';

    $scope.hideInvalid = function (cl) {
      return !Classifier.isValid(cl);
    };

    $scope.sendBackToCreation = function () {
      ApelApplicationUtils.sendBackToCreation($scope.application, entityToForm);
    };

    $scope.sendToConfirm = function () {
      ApelApplicationUtils.sendToConfirm($scope.application, entityToForm);
    };

    $scope.sendToCommittee = function () {
      ApelApplicationUtils.sendToCommittee($scope.application, entityToForm);
    };

    $scope.confirm = function () {
      ApelApplicationUtils.confirm($scope.application, entityToForm);
    };

    $scope.sendBack = function () {
      ApelApplicationUtils.sendBack($scope.application, entityToForm);
    };

    $scope.reject = function () {
      ApelApplicationUtils.reject($scope.application, entityToForm);
    };

    $scope.removeConfirmation = function () {
      ApelApplicationUtils.removeConfirmation($scope.application, entityToForm);
    };

  });
}());
