(function () {
'use strict';

  function getDataForFormalLearningTables(scope) {
    for (var i = 0; i < scope.application.records.length; i++) {
      if (scope.application.records[i].isFormalLearning) {
        scope.application.records[i].data = [];
        var rows = 1;
        if (scope.application.isVocational) {
          rows = Math.max(scope.application.records[i].formalSubjectsOrModules.length, scope.application.records[i].formalReplacedSubjectsOrModules.length);
        }
        for (var j = 0; j < rows; j++) {
          var formalSubjectsOrModules = scope.application.records[i].formalSubjectsOrModules[j] ? scope.application.records[i].formalSubjectsOrModules[j] : {};
          var formalReplacedSubjectsOrModules = {};
          if (scope.application.isVocational) {
            formalReplacedSubjectsOrModules = scope.application.records[i].formalReplacedSubjectsOrModules[j] ? scope.application.records[i].formalReplacedSubjectsOrModules[j] : {};
          } else {
            formalReplacedSubjectsOrModules = scope.application.records[i].formalReplacedSubjectsOrModules;
          }
          getFormalLearningTableRow(scope, i, formalSubjectsOrModules, formalReplacedSubjectsOrModules);
        }
      }
    }
  }

  function getFormalLearningTableRow(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules) {
    var data = [];
    if (scope.application.isVocational) {
      if (formalSubjectsOrModules.curriculumVersionOmodule) {
        getFormalLearningModuleTableRow(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, formalSubjectsOrModules.curriculumVersionOmodule, null);
      } else {
        getFormalLearningModuleTableRow(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, null, null);
      }
    } else {
      data = getFormalSubjectRowData(scope, formalSubjectsOrModules, formalReplacedSubjectsOrModules,  scope.application.records[recordIndex].id);
      scope.application.records[recordIndex].data.push(data);
    }
  }

  function getFormalLearningModuleTableRow(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule, replacedOmodule) {
    if (!angular.equals(formalReplacedSubjectsOrModules, {})) {
      getFormalLearningTableRowWithReplacedModules(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule);
    } else {
      var data = getFormalModuleRowData(scope, formalSubjectsOrModules, scope.application.records[recordIndex].id, oModule, replacedOmodule);
      scope.application.records[recordIndex].data.push(data);
    }
  }

  function getFormalLearningTableRowWithReplacedModules(scope, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule) {
    var data = null;
    var replacedTheme = formalReplacedSubjectsOrModules.curriculumVersionOmoduleTheme;
    if (replacedTheme) {
      data = getFormalModuleRowData(scope, formalSubjectsOrModules, scope.application.records[recordIndex].id, 
        oModule, formalReplacedSubjectsOrModules.curriculumVersionOmodule, replacedTheme);
    } else {
      data = getFormalModuleRowData(scope, formalSubjectsOrModules, scope.application.records[recordIndex].id, oModule, 
        formalReplacedSubjectsOrModules.curriculumVersionOmodule, null);
    }
    scope.application.records[recordIndex].data.push(data);
  }

  function getFormalSubjectRowData(scope, formalSubjectsOrModules, formalReplacedSubjectsOrModules, recordId) {
    var data = [];
    data.recordId = recordId;
    data.subjectOrModuleId = formalSubjectsOrModules.id;
    data.school = formalSubjectsOrModules.apelSchool ? formalSubjectsOrModules.apelSchool : scope.school;
    data.subject = formalSubjectsOrModules.subject ? formalSubjectsOrModules.subject : {nameEn: formalSubjectsOrModules.nameEn, nameEt: formalSubjectsOrModules.nameEt};
    data.code = formalSubjectsOrModules.subjectCode;
    data.assessment = scope.assessmentsMap[formalSubjectsOrModules.assessment];
    data.grade = scope.gradesMap[formalSubjectsOrModules.grade];
    data.gradeDate = formalSubjectsOrModules.gradeDate;
    data.teachers = formalSubjectsOrModules.teachers;
    data.module = formalSubjectsOrModules.curriculumVersionHmodule;
    data.isOptional = formalSubjectsOrModules.isOptional;
    data.credits = formalSubjectsOrModules.credits;
    data.formalReplacedSubjectsOrModules = formalReplacedSubjectsOrModules;
    data.transfer = formalSubjectsOrModules.transfer;
    return data;
  }

  function getFormalModuleRowData(scope, formalSubjectsOrModules, recordId, oModule, replacedOmodule, replacedTheme) {
    var data = [];
    data.recordId = recordId;
    data.subjectOrModuleId = formalSubjectsOrModules.id;
    if (formalSubjectsOrModules && !angular.equals(formalSubjectsOrModules, {})) {
      data.school = formalSubjectsOrModules.apelSchool ? formalSubjectsOrModules.apelSchool : scope.school;
    }
    data.moduleId = formalSubjectsOrModules.id;
    data.module = oModule ? oModule : {nameEt: formalSubjectsOrModules.nameEt, nameEn: formalSubjectsOrModules.nameEn};
    data.credits = formalSubjectsOrModules.credits;
    data.grade = scope.gradesMap[formalSubjectsOrModules.grade];
    data.gradeDate = formalSubjectsOrModules.gradeDate;
    data.teachers = formalSubjectsOrModules.teachers;
    data.replacedModule = replacedOmodule;
    data.replacedTheme = replacedTheme;
    if (replacedOmodule && !replacedTheme) {
      data.replacedCredits = replacedOmodule.credits;
    } else if (replacedOmodule && replacedTheme) {
      data.replacedCredits = replacedTheme.credits;
    } else {
      data.replacedCredits = null;
    }
    data.transfer = formalSubjectsOrModules.transfer;
    return data;
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
    data.isModule = informalSubjectsOrModules.curriculumVersionOmoduleTheme ? false : true;
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
    data.isOptional = getSubjectIsOptional(data.subject.id, data.module);
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

  function recordsToIdentifiers(DataUtils, records) {
    for (var i = 0; i < records.length; i++) {
      formalSubjectOrModulesObjectsToIdentifiers(DataUtils, records[i]);
    }
  }

  function formalSubjectOrModulesObjectsToIdentifiers(DataUtils, record) {
    for (var i = 0; i < record.formalSubjectsOrModules.length; i++) {
      DataUtils.convertObjectToIdentifier(record.formalSubjectsOrModules[i], ['apelSchool', 'subject', 'curriculumVersionHmodule','curriculumVersionOmodule']);
    }
    for (var j = 0; j < record.formalReplacedSubjectsOrModules.length; j++) {
      DataUtils.convertObjectToIdentifier(record.formalReplacedSubjectsOrModules[j], ['subject', 'curriculumVersionOmodule', 'curriculumVersionOmoduleTheme']);
    }
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

  angular.module('hitsaOis').controller('ApelApplicationEditController', function ($filter, $rootScope, $scope, $route, ArrayUtils, HigherGradeUtil, VocationalGradeUtil, QueryUtils, 
    oisFileService, dialogService, message, $location, Classifier, DataUtils, config, $q) {

    var ApelApplicationEndpoint = QueryUtils.endpoint('/apelApplications');
    $scope.auth = $route.current.locals.auth;
    $scope.application = {};
    $scope.formState = {};
    $scope.formState.viewForm = false;

    function entityToForm(entity) {
      $scope.application = entity;
      $scope.school = entity.school;

      if (entity.status === 'VOTA_STAATUS_E' && $scope.auth.isAdmin()) {
        $scope.committees = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/committees').query();
      }
      $scope.application.committeeId = $scope.application.committee ? $scope.application.committee.id : null;
      $scope.canChangeTransferStatus = entity.canChangeTransferStatus;

      setStudentInfo();
      setGradesAndAssessments($q, $scope, Classifier, entity).then(function () {
        getDataForInformalLearningTables($scope);
        getDataForFormalLearningTables($scope);
      });
      setColspans($scope);
    }

    function setStudentInfo() {
      return QueryUtils.endpoint('/students/' + $scope.application.student.id).search(function (result) {
        $scope.application.person = result.person;
        $scope.application.curriculumVersion = result.curriculumVersion;
        $scope.application.isVocational = result.curriculumVersion.isVocational;
      });
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
      setStudentInfo().$promise.then(function () {
        var application = new ApelApplicationEndpoint($scope.application);
        application.$save().then(function () {
          message.info('main.messages.create.success');
          $location.path('/apelApplication/' + application.id + '/edit');
        }).catch(angular.noop);
      });
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
        dialogScope.student = $scope.application.student;
        dialogScope.curriculumVersionId = $scope.application.curriculumVersion.id;
        dialogScope.isVocational = $scope.application.curriculumVersion.isVocational;
        dialogScope.gradesMap = $scope.gradesMap;
        dialogScope.assessmentsMap = $scope.assessmentsMap;

        function addMissingValuesToInformalModules(informalSubjectsOrModules) {
          for (var i = 0; i < informalSubjectsOrModules.length; i++) {
            $q(function () {
              var entry = informalSubjectsOrModules[i];
              entry.isModule = entry.curriculumVersionOmoduleTheme ? false : true;
              entry.grade = dialogScope.gradesMap[entry.grade];

              var acquiredOutcomeIds = getOutcomeIds(entry.outcomes);
              entry.module = entry.curriculumVersionOmodule;
              entry.hours = entry.isModule ? getHours(entry.curriculumVersionOmodule.themes) : entry.curriculumVersionOmoduleTheme.hours;
              if (entry.isModule) {
                entry.EKAP = entry.curriculumVersionOmodule.credits;
                getModuleOutcomeIds(entry.curriculumVersionOmodule.curriculumModule).then(function (outcomeIds) {
                  getOutcomes(outcomeIds, acquiredOutcomeIds).then(function (allOutcomes) {
                    entry.outcomes = allOutcomes;
                  });
                });
              } else {
                entry.EKAP = entry.curriculumVersionOmoduleTheme.credits;
                var outcomeIds = entry.curriculumVersionOmoduleTheme.outcomes;
                getOutcomes(outcomeIds, acquiredOutcomeIds).then(function (allOutcomes) {
                  entry.outcomes = allOutcomes;
                });
              }
            });
          }
        }

        function addMissingValuesToInformalSubjects(informalSubjectsOrModules) {
          for (var i = 0; i < informalSubjectsOrModules.length; i++) {
            var entry = informalSubjectsOrModules[i];
            entry.credits = informalSubjectsOrModules[i].subject.credits;
          }
        }

        function addNewInformalExperienceRow() {
          dialogScope.record.informalExperiences.push({
            nameEt: null,
            placeTime: null,
            hours: null,
            documents: null,
            type: null,
          });
        }

        if (recordId) {
          dialogScope.record = angular.copy(getRecord(recordId));
          if (dialogScope.isVocational) {
            addMissingValuesToInformalModules(dialogScope.record.informalSubjectsOrModules);
          } else {
            addMissingValuesToInformalSubjects(dialogScope.record.informalSubjectsOrModules);
          }
        } else {
          dialogScope.record = {
            informalExperiences: [],
            informalSubjectsOrModules: []
          };
          addNewInformalExperienceRow();
        }

        function getSubjects() {
          QueryUtils.endpoint('/autocomplete/subjectsList').query(
            {student: dialogScope.student.id, curriculumSubjects: true, curriculumVersion: dialogScope.curriculumVersionId, withCredits: true}).$promise.then(function (subjects) {
            dialogScope.subjects = subjects;
          });
        }

        if (dialogScope.isVocational) {
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({
            student: dialogScope.student.id,
            curriculumVersion: dialogScope.curriculumVersionId
          }).$promise.then(function (result) {
            dialogScope.modulesAndThemes = getModulesAndThemes(result);
          });
        } else {
          getSubjects();
        }

        dialogScope.selectedModuleOrThemeChanged = function () {
          var selectedItem = dialogScope.selectedModuleOrTheme;
          if (selectedItem) {
            if (selectedItem.isModule) {
              if (moduleCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.informalSubjectsOrModules, selectedItem.moduleId)) {
                QueryUtils.endpoint('/occupationModule').get({id: selectedItem.id}).$promise.then(function (oModule) {
                  getModuleOutcomeIds(oModule.curriculumModule).then(function (outcomeIds) {
                    getOutcomes(outcomeIds, null).then(function(outcomes) {
                      addNewSubtitutableModuleTheme(selectedItem, getHours(oModule.themes), oModule.credits, outcomes);
                    });
                  });
                });
              } else {
                message.error('apel.error.moduleOrItsThemeHasAlreadyBeenAdded');
                dialogScope.selectedModuleOrTheme = null;
              }
            } else {
              if (moduleThemeCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.informalSubjectsOrModules, selectedItem.themeId, selectedItem.moduleId)) {
                QueryUtils.endpoint('/occupationModule/theme').get({id: selectedItem.id}).$promise.then(function (theme) {
                  getOutcomes(theme.outcomes, null).then(function (outcomes) {
                    addNewSubtitutableModuleTheme(selectedItem, theme.hours, theme.credits, outcomes);
                  });
                });
              } else {
                message.error('apel.error.themeOrParentModuleHasAlreadyBeenAdded');
                dialogScope.selectedModuleOrTheme = null;
              }
            }
          }
        };

        function getOutcomes(outcomeIds, acquiredOutcomeIds) {
          var promises = [];
          for (var i = 0; i < outcomeIds.length; i++) {
            promises.push(QueryUtils.endpoint('/occupationModule/outcome').get({id: outcomeIds[i]}).$promise);
          }
          return $q.all(promises).then(function (result) {
            var outcomes = [];
            for (var i = 0; i < result.length; i++) {
              if (acquiredOutcomeIds && ArrayUtils.contains(acquiredOutcomeIds, result[i].id)) {
                outcomes.push({curriculumModuleOutcomes: result[i], acquired: true});
              } else {
                outcomes.push({curriculumModuleOutcomes: result[i], acquired: false});
              }
            }
            return outcomes;
          });
        }

        function getModuleOutcomeIds(curriculumModuleId) {
          return QueryUtils.endpoint('/curriculumModule').get({id: curriculumModuleId}).$promise.then(function (result) {
            var outcomeIds = [];
            for (var i = 0; i < result.outcomes.length; i++) {
              outcomeIds.push(result.outcomes[i].id);
            }
            return outcomeIds;
          });
        }

        function getOutcomeIds(outcomes) {
          var outcomeIds = [];
          for (var i = 0; i < outcomes.length; i++) {
            outcomeIds.push(outcomes[i].curriculumModuleOutcomes.id);
          }
          return outcomeIds;
        }

        function addNewSubtitutableModuleTheme(selectedModuleOrTheme, hours, EKAP, outcomes) {
          if (!selectedModuleOrTheme.isModule) {
            QueryUtils.endpoint('/occupationModule/theme').get({id: dialogScope.selectedModuleOrTheme.id}, function (theme) {
              var oModuleTheme = theme;
              QueryUtils.endpoint('/occupationModule').get({id: oModuleTheme.module}, function (oModule) {
                addNewSubtitutableModuleThemeRow(oModuleTheme, oModule, hours, EKAP, outcomes);
              });
            });
          } else {
            QueryUtils.endpoint('/occupationModule').get({id: dialogScope.selectedModuleOrTheme.id}, function (oModule) {
              addNewSubtitutableModuleThemeRow(null, oModule, hours, EKAP, outcomes);
            });
          }
        }

        function addNewSubtitutableModuleThemeRow(oModuleTheme, oModule, hours, EKAP, outcomes) {
          var grade = dialogScope.gradesMap.KUTSEHINDAMINE_A;
          var newModuleTheme = {
            isModule: dialogScope.selectedModuleOrTheme.isModule,
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

        dialogScope.addSelectedSubject = function () {
          if (!ArrayUtils.contains(getReplacedSubjectsIds(dialogScope.record.informalSubjectsOrModules), dialogScope.selectedSubject.id)) {
            var subjectId = dialogScope.selectedSubject.id;
            QueryUtils.endpoint('/subject').get({id: subjectId}).$promise.then(function (subject) {
              var credits = subject.credits;
              QueryUtils.endpoint('/apelApplications/subjectModule/' + subject.id).search().$promise.then(function (hModule) {
                var isOptional = getSubjectIsOptional(subjectId, hModule);
                addNewSubtitutableSubjectRow(subject, hModule, isOptional, credits);
              });
            });
          }
        };

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

        if (dialogScope.isVocational) {
          dialogScope.curriculumModules = QueryUtils.endpoint('/autocomplete/curriculumversionomodules').query(
            {student: dialogScope.student.id, curriculumVersion: dialogScope.curriculumVersionId});
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({
            student: dialogScope.student.id,
            curriculumVersion: dialogScope.curriculumVersionId
          }).$promise.then(function (result) {
            dialogScope.modulesAndThemes = getModulesAndThemes(result);
          });
        } else {
          dialogScope.subjects = QueryUtils.endpoint('/autocomplete/subjectsList').query(
            {student: dialogScope.student.id, curriculumSubjects: true, curriculumVersion: dialogScope.curriculumVersionId, withCredits: true});
          dialogScope.curriculumModules = QueryUtils.endpoint('/autocomplete/curriculumversionhmodules').query({curriculumVersion: dialogScope.curriculumVersionId});
        }
        
        function addNewEmptyFormalSubjectOrModule() {
          dialogScope.formState.isMySchool = false;
          dialogScope.formState.isNewSchool = false;
          dialogScope.grades = null;
          dialogScope.record.assessment = null;
          dialogScope.record.curriculumVersionOmodule = null;
          dialogScope.record.newTransferableSubjectOrModule = {
            isNew: true,
            newApelSchool: null,
            type: 'VOTA_AINE_LIIK_M',
            assessment: null
          };
          getTransferableModulesOrSubjects(dialogScope.record.newTransferableSubjectOrModule.type);
        }

        function addMissingValuesToFormalModules(formalModules) {
          for (var i = 0; i < formalModules.length; i++) {
            $q(function () {
              var entry = formalModules[i];
              if (entry.curriculumVersionOmodule) {
                entry.module = entry.curriculumVersionOmodule;
              }
              if (!entry.grade.code) {
                entry.grade = dialogScope.gradesMap[formalModules[i].grade];
              }
            });
          }
        }

        function addMissingValuesToFormalReplacedModules(replacedFormalModules) {
          for (var i = 0; i < replacedFormalModules.length; i++) {
            var entry = replacedFormalModules[i];
            entry.isModule = entry.curriculumVersionOmoduleTheme ? false : true;
          }
        }

        if (recordId) {
          dialogScope.record = angular.copy(getRecord(recordId));
          if (dialogScope.isVocational) {
            addMissingValuesToFormalModules(dialogScope.record.formalSubjectsOrModules);
            addMissingValuesToFormalReplacedModules(dialogScope.record.formalReplacedSubjectsOrModules);
          } else {
            dialogScope.record.newTransferableSubjectOrModule = dialogScope.record.formalSubjectsOrModules[0];
            dialogScope.record.subject = dialogScope.record.newTransferableSubjectOrModule.subject;
            dialogScope.record.nameEt = dialogScope.record.newTransferableSubjectOrModule.nameEt;
            dialogScope.record.nameEn = dialogScope.record.newTransferableSubjectOrModule.nameEn;
            dialogScope.record.apelSchool = dialogScope.record.newTransferableSubjectOrModule.apelSchool;
            dialogScope.formState.isMySchool = dialogScope.record.newTransferableSubjectOrModule.isMySchool;
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
                transferableModuleIndex: copy.transferableModuleIndex
              };
              getGrades(dialogScope.record.newTransferableSubjectOrModule.assessment);
              if (copy.isMySchool === true) {
                if (copy.subject) {
                  getTransferableSubjects(copy.type);
                  getSubjectData(copy.subject.id);
                } else if (dialogScope.record.subject) {
                  getTransferableSubjects(copy.type);
                  getSubjectData(dialogScope.record.subject.id);
                } else if (copy.curriculumVersionOmodule) {
                  getTransferableModules(copy.type);
                  getModuleData(copy.curriculumVersionOmodule);
                } else if (dialogScope.record.curriculumVersionOmodule) {
                  getTransferableModules(copy.type);
                  getModuleData(dialogScope.record.curriculumVersionOmodule);
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
            getTransferableModulesOrSubjects(dialogScope.record.newTransferableSubjectOrModule.type);
          }
        });

        dialogScope.typeChanged = function (typeCode) {
          getTransferableModulesOrSubjects(typeCode);
        };

        function getTransferableModules(typeCode) {
          if (typeCode === 'VOTA_AINE_LIIK_O') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, curriculumModules: true, curriculumVersion: dialogScope.curriculumVersionId}).$promise.then(function (modules) {
              setTransferableModules(typeCode, modules);
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_M') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, curriculumModules: false, curriculumVersion: dialogScope.curriculumVersionId}).$promise.then(function (modules) {
              setTransferableModules(typeCode, modules);
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_V') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, otherStudents: true, ignoreStatuses: true}).$promise.then(function (modules) {
              setTransferableModules(typeCode, modules);
            });
          }
        }

        function setTransferableModules(typeCode, modules) {
          // type could be changed before query has finished therefore it needs to check if type code is still the same
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.type === typeCode) {
            dialogScope.transferableModules = modules;
          }
          setCurriculumVersionOmodule();
        }

        function setCurriculumVersionOmodule() {
          var moduleInTypeModules = false;
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule &&
            dialogScope.transferableModules !== null ) {
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
            QueryUtils.endpoint('/autocomplete/subjectsList').query(
              {student: dialogScope.student.id, curriculumSubjects: true, curriculumVersion: dialogScope.curriculumVersionId, withCredits: false}).$promise.then(function (subjects) {
              dialogScope.transferableSubjects = subjects;
              setSubject();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_M') {
            QueryUtils.endpoint('/autocomplete/subjectsList').query(
              {student: dialogScope.student.id, curriculumSubjects: false, curriculumVersion: dialogScope.curriculumVersionId, withCredits: false}).$promise.then(function (subjects) {
              dialogScope.transferableSubjects = subjects;
              setSubject();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_V') {
            QueryUtils.endpoint('/autocomplete/subjectsList').query(
              {student: dialogScope.student.id, otherStudents: true, withCredits: false}).$promise.then(function (subjects) {
              dialogScope.transferableSubjects = subjects;
              setSubject();
            });
          }
        }

        function setSubject() {
          var subjectInTypeSubjects = false;
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.subject && dialogScope.transferableSubjects !== null ) {
            for (var i = 0; i < dialogScope.transferableSubjects.length; i++) {
              if (dialogScope.transferableSubjects[i].id === dialogScope.record.subject.id) {
                subjectInTypeSubjects = true;
              }
            }
          }
          if (!subjectInTypeSubjects) {
            dialogScope.record.subject = null;
          }
        }

        dialogScope.gradeValue = function (code) {
          var grade = clMapper.objectmapper({ grade: code }).grade;
          return grade ? ($scope.auth.school.letterGrades && !dialogScope.isVocational ? grade.value2 : grade.value) : undefined;
        };

        dialogScope.newSchoolCountryChanged = function (countryCode) {
          if (countryCode === 'RIIK_EST') {
            dialogScope.newSchoolEst = true;
          } else {
            dialogScope.newSchoolEst = false;
          } 
        };

        dialogScope.ehisSchoolChanged = function (ehisCode) {
          Classifier.get(ehisCode).$promise.then(function (ehisSchool) {
            dialogScope.record.newTransferableSubjectOrModule.newApelSchool.nameEt = ehisSchool.nameEt;
            dialogScope.record.newTransferableSubjectOrModule.newApelSchool.nameEn = ehisSchool.nameEn;
          });
        };

        dialogScope.apelSchoolChanged = function (apelSchool) {
          if (!angular.isDefined(apelSchool.id)) {
            // apel school is created in this formal learning block
            dialogScope.record.newTransferableSubjectOrModule.newApelSchool = apelSchool;
          }
          Classifier.get(apelSchool.country).$promise.then(function (country) {
            dialogScope.record.newTransferableSubjectOrModule.country = country;
          });
        };

        dialogScope.subjectChanged = function (subject) {
          if (subject) {
            getSubjectData(subject.id);
          }
          setFormerSubjectResult(subject);
        };

        function getSubjectData(subjectId) {
          QueryUtils.endpoint('/subject').get({id: subjectId}, function (subject) {
            dialogScope.record.newTransferableSubjectOrModule.subject = subject;
            dialogScope.record.newTransferableSubjectOrModule.subjectCode = subject.code;
            dialogScope.record.newTransferableSubjectOrModule.credits = subject.credits;

            var assessment = dialogScope.assessmentsMap[subject.assessment];
            if (dialogScope.record.newTransferableSubjectOrModule.assessment !== assessment.code) {
              dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
              dialogScope.record.newTransferableSubjectOrModule.grade = null;
              getGrades(assessment.code);
            }
            dialogScope.record.assessment = assessment;
          });
        }

        function setFormerSubjectResult(subject) {
          if (subject && subject.gradeCode) {
            dialogScope.record.newTransferableSubjectOrModule.grade = subject.gradeCode;
            dialogScope.record.newTransferableSubjectOrModule.gradeDate = subject.gradeDate;
            dialogScope.record.newTransferableSubjectOrModule.teachers = subject.teachers;
          }
        }

        dialogScope.moduleChanged = function (oModule) {
          if (oModule && dialogScope.record.curriculumVersionOmodule) {
            if (oModule.id !== dialogScope.record.curriculumVersionOmodule.id) {
              getModuleData(oModule);
            }
          } else if (oModule) {
            getModuleData(oModule);
          }
          setFormerModuleResult(oModule);
        };

        function getModuleData(oModule) {
          dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = oModule;
          dialogScope.record.curriculumVersionOmodule = oModule;
          dialogScope.record.newTransferableSubjectOrModule.credits = oModule.credits;

          var assessment = dialogScope.assessmentsMap[oModule.assessment];
          if (dialogScope.record.newTransferableSubjectOrModule) {
            if (dialogScope.record.newTransferableSubjectOrModule.assessment !== assessment.code) {
              dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
              dialogScope.record.newTransferableSubjectOrModule.grade = null;
              getGrades(assessment.code);
            }
          }
          dialogScope.record.assessment = assessment;
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
          dialogScope.newSchoolEst = false;
          dialogScope.formState.isNewSchool = true;
          if (dialogScope.record.newTransferableSubjectOrModule && dialogScope.record.newTransferableSubjectOrModule.apelSchool) {
            dialogScope.record.newTransferableSubjectOrModule.apelSchool = null;
          }
        };

        dialogScope.addNewTransferableModule = function () {
          addNewEmptyFormalSubjectOrModule();
        };

        function isModuleAlreadyTransfered(transferableModule) {
          var alreadyTransferedModules = (dialogScope.record.formalSubjectsOrModules || []).map(function (transfer) {
            if (transfer.curriculumVersionOmodule) {
              return transfer.curriculumVersionOmodule.id;
            }
          });
          var addedModule = angular.isDefined(transferableModule.curriculumVersionOmodule) && 
            transferableModule.curriculumVersionOmodule !== null ? transferableModule.curriculumVersionOmodule.id : null;
          return alreadyTransferedModules.indexOf(addedModule) !== -1;
        }

        dialogScope.addTransferableModule = function (transferableModule) {
          if (isModuleAlreadyTransfered(transferableModule)) {
            message.error('apel.error.moduleHasAlreadyBeenAdded');
            return;
          }
          dialogScope.dialogForm.$setSubmitted();
          if (dialogScope.dialogForm.$valid) {
            dialogScope.record.newTransferableSubjectOrModule = null;
            transferableModule.isMySchool = dialogScope.formState.isMySchool;
            transferableModule.isNewSchool = dialogScope.formState.isNewSchool;
            transferableModule.isNew = false;

            if (!transferableModule.isMySchool) {
              transferableModule.type = 'VOTA_AINE_LIIK_M';
            } else {
              transferableModule.module = transferableModule.curriculumVersionOmodule;
            }            
            transferableModule.grade = dialogScope.gradesMap[transferableModule.grade];
            dialogScope.record.formalSubjectsOrModules.push(transferableModule);

            if (dialogScope.formState.isNewSchool) {
              addNewSchoolToSelection(transferableModule);
            }
          }
        };

        function addNewSchoolToSelection(transferableModule) {
          if (isNewSchoolAlreadyAdded(transferableModule.newApelSchool)) {
            return;
          }
          dialogScope.apelSchools.push(transferableModule.newApelSchool);
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
          }
        };

        dialogScope.removeSubtitutableSubject = function (subject) {
          ArrayUtils.remove(dialogScope.record.formalReplacedSubjectsOrModules, subject);
        };

        dialogScope.editTransferableModule = function (transferableModuleIndex) {
          dialogScope.record.newTransferableSubjectOrModule = angular.copy(dialogScope.record.formalSubjectsOrModules[transferableModuleIndex]);
          dialogScope.record.newTransferableSubjectOrModule.transferableModuleIndex = transferableModuleIndex;
          
          dialogScope.formState.isMySchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].isMySchool;
          dialogScope.formState.isNewSchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].isNewSchool;
          dialogScope.record.apelSchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].apelSchool;
          
          dialogScope.record.assessment = dialogScope.assessmentsMap[dialogScope.record.newTransferableSubjectOrModule.assessment];
          dialogScope.record.curriculumVersionOmodule = dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule;
          dialogScope.record.nameEt = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].nameEt;
          dialogScope.record.nameEn = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].nameEn;

          dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].curriculumVersionOmodule;

          dialogScope.record.newTransferableSubjectOrModule.grade = dialogScope.record.newTransferableSubjectOrModule.grade.code;
          getGrades(dialogScope.record.newTransferableSubjectOrModule.assessment);
          getTransferableModulesOrSubjects(dialogScope.record.newTransferableSubjectOrModule.type);
        };

        function getTransferableModulesOrSubjects(typeCode) {
          if (dialogScope.isVocational) {
            getTransferableModules(typeCode);
          } else {
            getTransferableSubjects(typeCode);
          }
        }

        dialogScope.changeTransferableModule = function (changedTransferableModule) {
          dialogScope.dialogForm.$setSubmitted();
          if (dialogScope.dialogForm.$valid) {
            dialogScope.record.formalSubjectsOrModules[changedTransferableModule.transferableModuleIndex] = changedTransferableModule;
            dialogScope.record.newTransferableSubjectOrModule = null;
            addMissingValuesToFormalModules(dialogScope.record.formalSubjectsOrModules);
            
            if (dialogScope.formState.isNewSchool) {
              addNewSchoolToSelection(changedTransferableModule);
            }
          }
        };

        dialogScope.deleteTransferableModule = function (transferableModule) {
          if (transferableModule) {
            if (transferableModule.transferableModuleIndex >= 0) {
              dialogScope.record.formalSubjectsOrModules.splice(transferableModule.transferableModuleIndex, 1);
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
                dialogScope.selectedModuleOrTheme = null;
              }
            } else {
              if (moduleThemeCanBeAddedAsReplaced(ArrayUtils, dialogScope.record.formalReplacedSubjectsOrModules, selectedItem.themeId, selectedItem.moduleId)) {
                addNewFormalSubtitutableModuleTheme(dialogScope.selectedModuleOrTheme);
              } else {
                message.error('apel.error.themeOrParentModuleHasAlreadyBeenAdded');
                dialogScope.selectedModuleOrTheme = null;
              }
            }
          }
        };

        function addNewFormalSubtitutableModuleTheme(selectedModuleOrTheme) {
          if (!selectedModuleOrTheme.isModule) {
            QueryUtils.endpoint('/occupationModule/theme').get({id: dialogScope.selectedModuleOrTheme.id}, function (theme) {
              var oModuleTheme = theme;
              QueryUtils.endpoint('/occupationModule').get({id: oModuleTheme.module}, function (oModule) {
                addNewFormalSubtitutableModuleThemeRow(oModuleTheme, oModule);
              });
            });
          } else {
            QueryUtils.endpoint('/occupationModule').get({id: dialogScope.selectedModuleOrTheme.id}, function (oModule) {
              addNewFormalSubtitutableModuleThemeRow(null, oModule);
            });
          }
        }

        function addNewFormalSubtitutableModuleThemeRow(oModuleTheme, oModule) {
          var newModuleTheme = {
            isModule: dialogScope.selectedModuleOrTheme.isModule,
            curriculumVersionOmoduleTheme: oModuleTheme,
            curriculumVersionOmodule: oModule
          };
          dialogScope.record.formalReplacedSubjectsOrModules.push(newModuleTheme);
        }

        dialogScope.removeSubtitutableModuleTheme = function (row) {
          ArrayUtils.remove(dialogScope.record.formalReplacedSubjectsOrModules, row);
        };
        
        dialogScope.submitVocationalFormalLearning = function () {
          if (dialogScope.record.formalSubjectsOrModules.length <= 0) {
            message.error('apel.error.atLeastOneTransferableModule');
          } else if (dialogScope.record.formalReplacedSubjectsOrModules.length <= 0) {
            message.error('apel.error.atLeastOneSubstitutableModule');
          } else if (!areThereMoreTransferableCreditsThanReplacedCredits(true)) {
            message.error('apel.error.thereMustBeMoreTransferableCreditsThanSubstitutableCreditsVocational');
          } else {
            formalSubjectsOrModulesToArray();
            formalSubjectOrModulesObjectsToIdentifiers(DataUtils, dialogScope.record);
            dialogScope.submit();
          }
        };

        function areThereMoreTransferableCreditsThanReplacedCredits(isVocational) {
          var transferableCredits = 0;
          var replacedCredits = 0;

          if (isVocational) {
            transferableCredits = getTransferableModulesCredits(dialogScope.record.formalSubjectsOrModules);
            replacedCredits = getReplacedModulesOrThemesCredits(dialogScope.record.formalReplacedSubjectsOrModules);
          } else {
            transferableCredits = dialogScope.record.newTransferableSubjectOrModule.credits;
            replacedCredits = getReplacedSubjectsCredits(dialogScope.record.formalReplacedSubjectsOrModules);
          }

          if (transferableCredits >= replacedCredits) {
            return true;
          }
          return false;
        }

        function getTransferableModulesCredits(transferableModules) {
          var credits = 0;
          for (var i = 0; i < transferableModules.length; i++) {
            credits += transferableModules[i].credits;
          }
          return credits;
        }
        
        function getReplacedModulesOrThemesCredits(replacedModulesOrThemes) {
          var credits = 0;
          for (var i = 0; i < replacedModulesOrThemes.length; i++) {
            if (replacedModulesOrThemes[i].curriculumVersionOmoduleTheme) {
              credits += replacedModulesOrThemes[i].curriculumVersionOmoduleTheme.credits;
            } else {
              credits += replacedModulesOrThemes[i].curriculumVersionOmodule.credits;
            }
          }
          return credits;
        }

        function getReplacedSubjectsCredits(replacedSubjects) {
          var credits = 0;
          for (var i = 0; i < replacedSubjects.length; i++) {
            credits += replacedSubjects[i].subject.credits;
          }
          return credits;
        }
        
        dialogScope.submitHigherFormalLearning = function () {
          if (dialogScope.record.formalReplacedSubjectsOrModules.length <= 0) {
            message.error('apel.error.atLeastOneSubstitutableSubject');
          } else if (!areThereMoreTransferableCreditsThanReplacedCredits(false)) {
            message.error('apel.error.thereMustBeMoreTransferableCreditsThanSubstitutableCreditsHigher');
          } else {
            dialogScope.record.formalSubjectsOrModules[0] = dialogScope.record.newTransferableSubjectOrModule;
            dialogScope.record.formalSubjectsOrModules[0].isMySchool = dialogScope.formState.isMySchool;
            if (!dialogScope.record.formalSubjectsOrModules[0].isMySchool) {
              dialogScope.record.formalSubjectsOrModules[0].type = 'VOTA_AINE_LIIK_M';
            }
            formalSubjectsOrModulesToArray();
            formalSubjectOrModulesObjectsToIdentifiers(DataUtils, dialogScope.record);
            dialogScope.submit();
          }
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
        for (var j = 0; j < themes.length; j++) {
          modulesAndThemes.push({
            id: themes[j].id,
            moduleId: result[i].id,
            themeId: themes[j].id,
            nameEt: result[i].nameEt + "/" + themes[j].nameEt,
            nameEn: result[i].nameEn + "/" + themes[j].nameEn,
            isModule: false,
          });
        }
      }
      return modulesAndThemes;
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
      recordsToIdentifiers(DataUtils, $scope.application.records);
      
      var application = new ApelApplicationEndpoint($scope.application);
      application.$update().then(function () {
        message.info('main.messages.create.success');
        entityToForm(application);
        $scope.apelApplicationForm.$setPristine();
      }).catch(angular.noop);
    };

    $scope.submit = function () {
      dialogService.confirmDialog({
        prompt: 'apel.submitConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/submit').put({}, function (response) {
          message.info('apel.messages.submitted');
          entityToForm(response);
        });
      });
    };

    $scope.sendBackToCreation = function () {
      if ($scope.application.status === 'VOTA_STAATUS_V') {
        dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
          dialogScope.sendBackToCreation = true;
          dialogScope.committee = true;
        }, function (submittedDialogScope) {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBackToCreation/').put({
            addInfo: submittedDialogScope.reason
          }, function (response) {
            message.info('apel.messages.sentBackToCreation');
            entityToForm(response);
          });
        });
      } else {
        dialogService.confirmDialog({
          prompt: 'apel.sendBackToCreationConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBackToCreation/').put({}, function (response) {
            message.info('apel.messages.sentBackToCreation');
            entityToForm(response);
          });
        });
      }
    };

    $scope.sendToConfirm = function () {
      recordsToIdentifiers(DataUtils, $scope.application.records);

      if ($scope.application.status === 'VOTA_STAATUS_V') {
        dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
          dialogScope.sendToConfirm = true;
          dialogScope.committee = true;
        }, function (submittedDialogScope) {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToConfirm/').put({
            student: $scope.application.student,
            isVocational: $scope.application.isVocational,
            records:  $scope.application.records,
            committeeId: $scope.application.committeeId,
            addInfo: submittedDialogScope.reason
          }, function (response) {
            message.info('apel.messages.sentToConfirm');
            entityToForm(response);
          });
        });
      } else {
        dialogService.confirmDialog({
          prompt: 'apel.sendToConfirmConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToConfirm/').put({
            student: $scope.application.student,
            isVocational: $scope.application.isVocational,
            committeeId: $scope.application.committeeId,
            records:  $scope.application.records
          }, function (response) {
            message.info('apel.messages.sentToConfirm');
            entityToForm(response);
          });
        });
      }
    };

    $scope.sendToCommittee = function () {
      recordsToIdentifiers(DataUtils, $scope.application.records);
      dialogService.confirmDialog({
        prompt: 'apel.sendToCommitteeConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToCommittee/').put({
          student: $scope.application.student,
          isVocational: $scope.application.isVocational,
          records:  $scope.application.records,
          committeeId: $scope.application.committeeId
        }, function (response) {
          message.info('apel.messages.sentToCommittee');
          entityToForm(response);
        });
      });
    };

    $scope.confirm = function () {
      if (atLeastOneLearningTransferred()) {
        dialogService.confirmDialog({
          prompt: 'apel.confirmConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/confirm/').put({}, function (response) {
            message.info('apel.messages.confirmed');
            entityToForm(response);
          });
        });
      } else {
        message.error('apel.error.atLeastOneLearningMustBeTransferred');
      }
    };

    function atLeastOneLearningTransferred() {
      for (var i = 0; i < $scope.application.records.length; i++) {
        for (var j = 0; j < $scope.application.records[i].data.length; j++) {
          if ($scope.application.records[i].data[j].transfer) {
            return true;
          }
        }
      }
      return false;
    }    

    $scope.sendBack = function () {
      dialogService.confirmDialog({
        prompt: 'apel.sendBackConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBack/').put({}, function (response) {
          message.info('apel.messages.sentBack');
          entityToForm(response);
        });
      });
    };

    $scope.reject = function () {
      dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
        dialogScope.rejection = true;
        dialogScope.committee = $scope.application.status === 'VOTA_STAATUS_V';
      }, function (submittedDialogScope) {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/reject/').put({
          addInfo: submittedDialogScope.reason
        }, function (response) {
          message.info('apel.messages.rejected');
          entityToForm(response);
        });
      });
    };

    $scope.removeConfirmation = function () {
      dialogService.confirmDialog({
        prompt: 'apel.removeConfirmationConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/removeConfirmation/').put({}, function (response) {
          message.info('apel.messages.removedConfirmation');
          entityToForm(response);
        });
      });
    };

    $scope.delete = function () {
      dialogService.confirmDialog({
        prompt: 'apel.deleteConfirm'
      }, function () {
        var application = new ApelApplicationEndpoint($scope.application);
        application.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.path('/apelApplication');
        }).catch(angular.noop);
      });
    };
  }).controller('ApelApplicationViewController', function ($scope, $route, $q, QueryUtils, oisFileService, Classifier, DataUtils, dialogService, message, config) {
    $scope.applicationId = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;
    $scope.application = {};
    $scope.formState = {};
    $scope.formState.viewForm = true;

    function entityToForm(entity) {
      $scope.application = entity;
      $scope.school = entity.school;

      QueryUtils.endpoint('/students/' + $scope.application.student.id).search(function (result) {
        $scope.application.person = result.person;
        $scope.application.curriculumVersion = result.curriculumVersion;
        $scope.application.isVocational = result.curriculumVersion.isVocational;
        $scope.application.committeeId = $scope.application.committee ? $scope.application.committee.id : null;
      });
      setGradesAndAssessments($q, $scope, Classifier, entity).then(function () {
        getDataForInformalLearningTables($scope);
        getDataForFormalLearningTables($scope);
      });
      setColspans($scope);

      if (entity.status === 'VOTA_STAATUS_E' && $scope.auth.isAdmin()) {
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

    $scope.removeConfirmation = function () {
      dialogService.confirmDialog({
        prompt: 'apel.removeConfirmationConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/removeConfirmation/').put({}, function (response) {
          message.info('apel.messages.removedConfirmation');
          entityToForm(response);
        });
      });
    };

    $scope.sendBackToCreation = function () {
      if ($scope.application.status === 'VOTA_STAATUS_V') {
        dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
          dialogScope.sendBackToCreation = true;
          dialogScope.committee = true;
        }, function (submittedDialogScope) {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBackToCreation/').put({
            addInfo: submittedDialogScope.reason
          }, function (response) {
            message.info('apel.messages.sentBackToCreation');
            entityToForm(response);
          });
        });
      } else {
        dialogService.confirmDialog({
          prompt: 'apel.sendBackToCreationConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBackToCreation/').put({}, function (response) {
            message.info('apel.messages.sentBackToCreation');
            entityToForm(response);
          });
        });
      }
    };

    $scope.sendToConfirm = function () {
      recordsToIdentifiers(DataUtils, $scope.application.records);

      if ($scope.application.status === 'VOTA_STAATUS_V') {
        dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
          dialogScope.sendToConfirm = true;
          dialogScope.committee = true;
        }, function (submittedDialogScope) {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToConfirm/').put({
            student: $scope.application.student,
            isVocational: $scope.application.isVocational,
            records:  $scope.application.records,
            committeeId: $scope.application.committeeId,
            addInfo: submittedDialogScope.reason
          }, function (response) {
            message.info('apel.messages.sentToConfirm');
            entityToForm(response);
          });
        });
      } else {
        dialogService.confirmDialog({
          prompt: 'apel.sendToConfirmConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToConfirm/').put({
            student: $scope.application.student, 
            isVocational: $scope.application.isVocational, 
            records:  $scope.application.records,
            committeeId: $scope.application.committeeId
          }, function (response) {
            message.info('apel.messages.sentToConfirm');
            entityToForm(response);
          });
        });
      }
    };

    $scope.sendToCommittee = function () {
      recordsToIdentifiers(DataUtils, $scope.application.records);
      dialogService.confirmDialog({
        prompt: 'apel.sendToCommitteeConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToCommittee/').put({
          student: $scope.application.student,
          isVocational: $scope.application.isVocational,
          records:  $scope.application.records,
          committeeId: $scope.application.committeeId
        }, function (response) {
          message.info('apel.messages.sentToCommittee');
          entityToForm(response);
        });
      });
    };

    $scope.confirm = function () {
      if (atLeastOneLearningTransferred()) {
        dialogService.confirmDialog({
          prompt: 'apel.confirmConfirm'
        }, function () {
          QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/confirm/').put({}, function (response) {
            message.info('apel.messages.confirmed');
            entityToForm(response);
          });
        });
      } else {
        message.error('apel.error.atLeastOneLearningMustBeTransferred');
      }
    };

    function atLeastOneLearningTransferred() {
      for (var i = 0; i < $scope.application.records.length; i++) {
        for (var j = 0; j < $scope.application.records[i].data.length; j++) {
          if ($scope.application.records[i].data[j].transfer) {
            return true;
          }
        }
      }
      return false;
    }    

    $scope.sendBack = function () {
      dialogService.confirmDialog({
        prompt: 'apel.sendBackConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBack/').put({}, function (response) {
          message.info('apel.messages.sentBack');
          entityToForm(response);
        });
      });
    };

    $scope.reject = function () {
      dialogService.showDialog('apelApplication/templates/reason.dialog.html', function (dialogScope) {
        dialogScope.rejection = true;
        dialogScope.committee = $scope.application.status === 'VOTA_STAATUS_V';
      }, function (submittedDialogScope) {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/reject/').put({
          addInfo: submittedDialogScope.reason
        }, function (response) {
          entityToForm(response);
        });
      });
    };

    $scope.removeConfirmation = function () {
      dialogService.confirmDialog({
        prompt: 'apel.removeConfirmationConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/removeConfirmation/').put({}, function (response) {
          message.info('apel.messages.removedConfirmation');
          entityToForm(response);
        });
      });
    };
  });
}());
