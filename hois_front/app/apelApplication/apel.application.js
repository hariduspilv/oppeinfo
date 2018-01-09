(function () {
'use strict';

  function getDataForFormalLearningTables(scope, queryUtils, classifier) {
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
          getFormalLearningTableRow(scope, queryUtils, classifier, i, formalSubjectsOrModules, formalReplacedSubjectsOrModules);
        }
      }
    }
  }

  function getFormalLearningTableRow(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules) {
    var data = [];
    if (scope.application.isVocational) {
      if (formalSubjectsOrModules.curriculumVersionOmodule) {
        getFormalLearningTableRowWithModules(scope, classifier, queryUtils, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules);
      } else {
        getFormalLearningModuleTableRow(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, null, null);
      }
    } else {
      data = getFormalSubjectRowData(scope, classifier, formalSubjectsOrModules, formalReplacedSubjectsOrModules,  scope.application.records[recordIndex].id);
      scope.application.records[recordIndex].data.push(data);
    }
  }

  function getFormalLearningTableRowWithReplacedModules(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule) {
    var replacedCurriculumVersionId = formalReplacedSubjectsOrModules.curriculumVersionOmodule.curriculumVersion;
    var replacedOccupationModuleId = formalReplacedSubjectsOrModules.curriculumVersionOmodule.id;
    getCurriculumModule(replacedCurriculumVersionId, queryUtils, replacedOccupationModuleId).then(function (replacedOmodule) {
      var data = getFormalModuleRowData(scope, classifier, formalSubjectsOrModules, scope.application.records[recordIndex].id, oModule, replacedOmodule);
      scope.application.records[recordIndex].data.push(data);
    });
  }

  function getFormalLearningTableRowWithModules(scope, classifier, queryUtils, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules) {
    var curriculumVersionId = formalSubjectsOrModules.curriculumVersionOmodule.curriculumVersion;
    var occupationModuleId = formalSubjectsOrModules.curriculumVersionOmodule.id;
    getCurriculumModule(curriculumVersionId, queryUtils, occupationModuleId).then(function (oModule) {
      getFormalLearningModuleTableRow(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule, null);
    });
  }

  function getFormalLearningModuleTableRow(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule, replacedOmodule) {
    if (!angular.equals(formalReplacedSubjectsOrModules, {})) {
      getFormalLearningTableRowWithReplacedModules(scope, queryUtils, classifier, recordIndex, formalSubjectsOrModules, formalReplacedSubjectsOrModules, oModule);
    } else {
      var data = getFormalModuleRowData(scope, classifier, formalSubjectsOrModules, scope.application.records[recordIndex].id, oModule, replacedOmodule);
      scope.application.records[recordIndex].data.push(data);
    }
  }

  function getFormalSubjectRowData(scope, classifier, formalSubjectsOrModules, formalReplacedSubjectsOrModules, recordId) {
    var data = [];
    data.recordId = recordId;
    data.subjectOrModuleId = formalSubjectsOrModules.id;
    data.school = formalSubjectsOrModules.apelSchool ? formalSubjectsOrModules.apelSchool : scope.school;
    data.subject = formalSubjectsOrModules.subject ? formalSubjectsOrModules.subject : {nameEn: formalSubjectsOrModules.nameEn, nameEt: formalSubjectsOrModules.nameEt};
    data.code = formalSubjectsOrModules.subjectCode;
    data.assessment = classifier.get(formalSubjectsOrModules.assessment);
    data.grade = classifier.get(formalSubjectsOrModules.grade);
    data.gradeDate = formalSubjectsOrModules.gradeDate;
    data.teachers = formalSubjectsOrModules.teachers;
    data.module = formalSubjectsOrModules.curriculumVersionHmodule;
    data.isOptional = formalSubjectsOrModules.isOptional;
    data.credits = formalSubjectsOrModules.credits;
    data.formalReplacedSubjectsOrModules = formalReplacedSubjectsOrModules;
    data.transfer = formalSubjectsOrModules.transfer;
    return data;
  }

  function getFormalModuleRowData(scope, classifier, formalSubjectsOrModules, recordId, oModule, replacedOmodule) {
    var data = [];
    data.recordId = recordId;
    data.subjectOrModuleId = formalSubjectsOrModules.id;
    if (formalSubjectsOrModules) {
      data.school = formalSubjectsOrModules.apelSchool ? formalSubjectsOrModules.apelSchool : scope.school;
    }
    data.moduleId = formalSubjectsOrModules.id;
    data.module = oModule ? oModule : {nameEt: formalSubjectsOrModules.nameEt, nameEn: formalSubjectsOrModules.nameEn};
    data.credits = formalSubjectsOrModules.credits;
    data.grade = classifier.get(formalSubjectsOrModules.grade);
    data.gradeDate = formalSubjectsOrModules.gradeDate;
    data.teachers = formalSubjectsOrModules.teachers;
    data.replacedModule = replacedOmodule;
    data.replacedCredits = replacedOmodule ? getEKAP(replacedOmodule.themes) : null;
    data.transfer = formalSubjectsOrModules.transfer;
    return data;
  }

  function getDataForInformalLearningTables(scope, queryUtils, classifier) {
    for (var i = 0; i < scope.application.records.length; i++) {
      if (!scope.application.records[i].isFormalLearning) {
        scope.application.records[i].data = [];
        var rows = Math.max(scope.application.records[i].informalExperiences.length, scope.application.records[i].informalSubjectsOrModules.length);
        for (var j = 0; j < rows; j++) {
          var informalExperiences = scope.application.records[i].informalExperiences[j] ? scope.application.records[i].informalExperiences[j] : {};
          var informalSubjectsOrModules = scope.application.records[i].informalSubjectsOrModules[j] ? scope.application.records[i].informalSubjectsOrModules[j] : {};

          getInformalLearningTableRow(scope, queryUtils, classifier, i, informalExperiences, informalSubjectsOrModules);
        }
      }
    }
  }

  function getInformalLearningTableRow(scope, queryUtils, classifier, recordIndex, informalExperiences, informalSubjectsOrModules) {
    var data = [];
    var curriculumVersionId = scope.application.curriculumVersion.id;

    if (informalSubjectsOrModules.curriculumVersionOmodule) {
      getCurriculumModule(curriculumVersionId, queryUtils, informalSubjectsOrModules.curriculumVersionOmodule.id).then(function (oModule) {
        data = getInformalModuleRowData(classifier, informalSubjectsOrModules, oModule, scope.application.records[recordIndex].id);
        scope.application.records[recordIndex].data.push(angular.extend({}, informalExperiences, data));
      });
    } else if (informalSubjectsOrModules.curriculumVersionOmoduleTheme) {
      queryUtils.endpoint('/occupationModule').get({id: informalSubjectsOrModules.curriculumVersionOmoduleTheme.module}, function (occupationModule) {
        getCurriculumModule(curriculumVersionId, queryUtils, occupationModule.id).then(function (oModule) {
          data = getInformalModuleRowData(classifier, informalSubjectsOrModules, oModule , scope.application.records[recordIndex].id);
          scope.application.records[recordIndex].data.push(angular.extend({}, informalExperiences, data));
        });
      });
    } else if (informalSubjectsOrModules.subject) {
      data = getInformalSubjectRowData(classifier, informalSubjectsOrModules, scope.application.records[recordIndex].id);
      scope.application.records[recordIndex].data.push(angular.extend({}, informalExperiences, data));
    } else {
      scope.application.records[recordIndex].data.push(informalExperiences);
    }
  }

  function getCurriculumModule(curriculumVersionId, QueryUtils, moduleId) {
    return QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({curriculumVersion: curriculumVersionId}).$promise.then(function (modules) {
      for (var i = 0; i < modules.length; i++) {
        if (modules[i].id === moduleId) {
          return modules[i];
        }
      }
    });
  }

  function getInformalModuleRowData(classifier, informalSubjectsOrModules, oModule, recordId) {
    var data = [];
    data.recordId = recordId;
    data.moduleId = informalSubjectsOrModules.id;
    data.module = oModule;
    data.theme = informalSubjectsOrModules.curriculumVersionOmoduleTheme ? informalSubjectsOrModules.curriculumVersionOmoduleTheme : null;
    data.isModule = informalSubjectsOrModules.curriculumVersionOmoduleTheme ? false : true;
    data.schoolResultHours = data.isModule ? getHours(informalSubjectsOrModules.curriculumVersionOmodule.themes) : informalSubjectsOrModules.curriculumVersionOmoduleTheme.hours;
    data.ekap = data.isModule ? getEKAP(informalSubjectsOrModules.curriculumVersionOmodule.themes) : null;
    data.grade = classifier.get(informalSubjectsOrModules.grade);
    data.outcomes = informalSubjectsOrModules.outcomes;
    data.skills = informalSubjectsOrModules.skills;
    data.transfer = informalSubjectsOrModules.transfer;
    return data;
  }

  function getInformalSubjectRowData(classifier, informalSubjectsOrModules, recordId) {
    var data = [];
    data.recordId = recordId;
    data.subjectId = informalSubjectsOrModules.id;
    data.subject = informalSubjectsOrModules.subject;
    data.code = informalSubjectsOrModules.subject.code;
    data.module = informalSubjectsOrModules.curriculumVersionHmodule;
    data.isOptional = getSubjectIsOptional(data.subject.id, data.module);
    data.credits = informalSubjectsOrModules.subject.credits;
    data.grade = classifier.get(informalSubjectsOrModules.grade);
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

  function getEKAP(themes) {
    var ekap = 0;
    for (var i = 0; i < themes.length; i++) {
      ekap += themes[i].credits;
    }
    return ekap;
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
      DataUtils.convertObjectToIdentifier(record.formalReplacedSubjectsOrModules[j], ['subject', 'curriculumVersionOmodule']);
    }
  }

  function getReplacedSubjectsIds(replacedSubjects) {
    var replacedSubjectsIds = [];
    for (var i = 0; i < replacedSubjects.length; i++) {
      replacedSubjectsIds.push(replacedSubjects[i].subject.id);
    }
    return replacedSubjectsIds;
  }

  angular.module('hitsaOis').controller('ApelApplicationEditController', function ($scope, $route, QueryUtils, ArrayUtils, oisFileService, 
    dialogService, message, $location, Classifier, DataUtils, config, $q) {

    var ApelApplicationEndpoint = QueryUtils.endpoint('/apelApplications');
    $scope.auth = $route.current.locals.auth;
    $scope.canConfirmAndRemoveConfirmation = ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_VOTA");
    $scope.application = {};
    QueryUtils.endpoint('/autocomplete/schools').query({id: $scope.auth.school.id}).$promise.then(function (schools) {
      $scope.school = schools[0];  
    });

    $scope.$watch('application.status', function () {
      $scope.editing = false;
      if ($scope.auth.isStudent() && $scope.application.status === 'VOTA_STAATUS_K') {
        $scope.editing = true;
      } else if ($scope.auth.isAdmin() && ($scope.application.status === 'VOTA_STAATUS_K' || $scope.application.status === 'VOTA_STAATUS_E')) {
        $scope.editing = true;
      }
      
      $scope.canChangeTransferStatus = false;
      if ($scope.application.status === 'VOTA_STAATUS_E' && $scope.auth.isAdmin()) {
        $scope.canChangeTransferStatus = true;
      }
    });

    function entityToForm(entity) {
      $scope.application = entity;
    }

    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      entityToForm(entity);
    } else {
      $scope.application.status = "VOTA_STAATUS_K";
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
      if ($scope.application.student) {
        QueryUtils.endpoint('/students/' + $scope.application.student.id).search(function (result) {
          $scope.application.person = result.person;
          $scope.application.curriculumVersion = result.curriculumVersion;
          $scope.application.isVocational = result.curriculumVersion.isVocational;

          if (!angular.isDefined($scope.application.id)) {
            createNewApplication();
          } else {
            getDataForInformalLearningTables($scope, QueryUtils, Classifier);
            getDataForFormalLearningTables($scope, QueryUtils, Classifier);
          }
        });
      }
    });

    function createNewApplication() {
      var application = new ApelApplicationEndpoint($scope.application);
      application.$save().then(function () {
        message.info('main.messages.create.success');
        $location.path('/apelApplication/' + application.id + '/edit');
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
        if (record.informalSubjectsOrModules[i].id === subjectOrModuleId) {
          return i;
        }
      }
    }

    function getFormalSubjectOrModuleIndex(record, subjectOrModuleId) {
      for (var i = 0; i < record.formalSubjectsOrModules.length; i++) {
        if (record.formalSubjectsOrModules[i].id === subjectOrModuleId) {
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

        function addMissingValuesToInformalModules(informalSubjectsOrModules) {
          for (var i = 0; i < informalSubjectsOrModules.length; i++) {
            $q(function () {
              var entry = informalSubjectsOrModules[i];
              entry.isModule = entry.curriculumVersionOmoduleTheme ? false : true;
              entry.grade = Classifier.get(entry.grade);
              var outcomeIds = entry.isModule ? getThemesOutcomeIds(entry.curriculumVersionOmodule.themes) : entry.curriculumVersionOmoduleTheme.outcomes;
              var acquiredOutcomeIds = getOutcomeIds(entry.outcomes);
              getOutcomes(outcomeIds, acquiredOutcomeIds).then(function (allOutcomes) {
                getCurriculumModule(dialogScope.curriculumVersionId, QueryUtils, entry.curriculumVersionOmodule.id).then(function (oModule) {
                  entry.module = oModule;
                  entry.hours = entry.isModule ? getHours(entry.curriculumVersionOmodule.themes) : entry.curriculumVersionOmoduleTheme.hours;
                  entry.EKAP = entry.isModule ? getEKAP(entry.curriculumVersionOmodule.themes) : entry.curriculumVersionOmoduleTheme.credits;
                  entry.outcomes = allOutcomes;
                });
              });
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

        function getModulesAndThemes() {
          dialogScope.modulesAndThemes = [];
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({
            student: dialogScope.student.id,            
            curriculumVersion: dialogScope.curriculumVersionId,
            curriculumVersionStatusCode: "OPPEKAVA_VERSIOON_STAATUS_K"
          }).$promise.then(function (result) {
            result = sortByName(result);
            for (var i = 0; i < result.length; i++) {
              dialogScope.modulesAndThemes.push({id: result[i].id, nameEt: result[i].nameEt, nameEn: result[i].nameEn, isModule: true});
              var themes = sortByName(result[i].themes);
              for (var j = 0; j < themes.length; j++) {
                dialogScope.modulesAndThemes.push({
                  id: themes[j].id,
                  nameEt: result[i].nameEt + "/" + themes[j].nameEt,
                  nameEn: result[i].nameEn + "/" + themes[j].nameEn,
                  isModule: false,
                  moduleId: result[i].id
                });
              }
            }
          });
        }

        function getSubjects() {
          QueryUtils.endpoint('/autocomplete/subjectsList').query(
            {student: dialogScope.student.id, curriculumSubjects: true, curriculumVersion: dialogScope.curriculumVersionId, withCredits: true}).$promise.then(function (subjects) {
            dialogScope.subjects = subjects;
          });
        }

        if (dialogScope.isVocational) {
          getModulesAndThemes();
        } else {
          getSubjects();
        }

        function sortByName(array) {
          if ($scope.currentLanguage() === 'et') {
            array.sort(function (a, b) {
              return a.nameEt.localeCompare(b.nameEt);
            });
          } else {
            array.sort(function (a, b) {
              return a.nameEn.localeCompare(b.nameEn);
            });
          }
          return array;
        }

        function getReplacedModulesIds(replacedModules) {
          var replacedModulesIds = [];
          for (var i = 0; i < replacedModules.length; i++) {
            if (replacedModules[i].isModule) {
              replacedModulesIds.push(replacedModules[i].curriculumVersionOmodule.id);
            }
          }
          return replacedModulesIds;
        }

        function getReplacedModuleThemesIds(replacedModules) {
          var replacedModuleThemesIds = [];
          for (var i = 0; i < replacedModules.length; i++) {
            if (!replacedModules[i].isModule) {
              replacedModuleThemesIds.push(replacedModules[i].curriculumVersionOmoduleTheme.id);
            }
          }
          return replacedModuleThemesIds;
        }

        dialogScope.selectedModuleOrThemeChanged = function () {
          if (dialogScope.selectedModuleOrTheme.isModule) {
            if (!ArrayUtils.contains(getReplacedModulesIds(dialogScope.record.informalSubjectsOrModules), dialogScope.selectedModuleOrTheme.id)) {
              QueryUtils.endpoint('/occupationModule').get({id: dialogScope.selectedModuleOrTheme.id}).$promise.then(function (oModule) {
                var outcomeIds = getThemesOutcomeIds(oModule.themes);
                getOutcomes(outcomeIds, null).then(function(outcomes) {
                  var hours = getHours(oModule.themes);
                  var ekap = getEKAP(oModule.themes);
                  addNewSubtitutableModuleTheme(dialogScope.selectedModuleOrTheme, hours, ekap, outcomes);
                });
              });
            }
          } else {
            if (!ArrayUtils.contains(getReplacedModulesIds(dialogScope.record.informalSubjectsOrModules), dialogScope.selectedModuleOrTheme.moduleId) &&
              !ArrayUtils.contains(getReplacedModuleThemesIds(dialogScope.record.informalSubjectsOrModules), dialogScope.selectedModuleOrTheme.id)) {
              QueryUtils.endpoint('/occupationModule/theme').get({id: dialogScope.selectedModuleOrTheme.id}).$promise.then(function (theme) {
                getOutcomes(theme.outcomes, null).then(function (outcomes) {
                  addNewSubtitutableModuleTheme(dialogScope.selectedModuleOrTheme, theme.hours, null, outcomes);
                });
              });
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

        function getThemesOutcomeIds(themes) {
          var outcomeIds = [];
          for (var i = 0; i < themes.length; i++) {
            for (var j = 0; j < themes[i].outcomes.length; j++) {
              outcomeIds.push(themes[i].outcomes[j]);
            }
          }
          var uniqueIds = outcomeIds.filter(function(value, index, self) {return index === self.indexOf(value);});
          return uniqueIds.sort();
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
                addNewSubtitutableModuleThemeRow(oModuleTheme, oModule, hours, null, outcomes);
              });
            });
          } else {
            QueryUtils.endpoint('/occupationModule').get({id: dialogScope.selectedModuleOrTheme.id}, function (oModule) {
              addNewSubtitutableModuleThemeRow(null, oModule, hours, EKAP, outcomes);
            });
          }
        }

        function addNewSubtitutableModuleThemeRow(oModuleTheme, oModule, hours, EKAP, outcomes) {
          getCurriculumModule(dialogScope.curriculumVersionId, QueryUtils, oModule.id).then(function (curriculumModule) {
            var grade = Classifier.get('KUTSEHINDAMINE_A');
            var newModuleTheme = {
              module: curriculumModule,
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
          });
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
          var grade = Classifier.get('KORGHINDAMINE_A');
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
          });

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
          });
        } else {
          record.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          });
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
      var dialogTemplate = "";
      if ($scope.application.curriculumVersion.isVocational) {
        dialogTemplate = 'apelApplication/templates/formal.learning.vocational.edit.dialog.html';
      } else {
        dialogTemplate = 'apelApplication/templates/formal.learning.higher.edit.dialog.html';
      }

      dialogService.showDialog(dialogTemplate, function (dialogScope) {
        dialogScope.auth = $scope.auth;
        dialogScope.student = $scope.application.student;
        dialogScope.currentDate = new Date();
        dialogScope.curriculumVersionId = $scope.application.curriculumVersion.id;
        dialogScope.isVocational = $scope.application.curriculumVersion.isVocational;
        dialogScope.apelSchools = QueryUtils.endpoint('/autocomplete/apelschools').query();

        if (dialogScope.isVocational) {
          dialogScope.curriculumModules = QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
            {student: dialogScope.student.id, curriculumVersion: dialogScope.curriculumVersionId});
          dialogScope.curriculumModules.$promise.then(function (modules) {
            for (var i = 0; i < modules.length; i++) {
              dialogScope.curriculumModules[i].credits = getEKAP(modules[i].themes);
            }
          });
        } else {
          dialogScope.subjects = QueryUtils.endpoint('/autocomplete/subjectsList').query(
            {student: dialogScope.student.id, curriculumSubjects: true, curriculumVersion: dialogScope.curriculumVersionId, withCredits: true});
          dialogScope.curriculumModules = QueryUtils.endpoint('/autocomplete/curriculumversionhmodules').query({curriculumVersion: dialogScope.curriculumVersionId});
        }
        
        function addNewEmptyFormalSubjectOrModule() {
          dialogScope.isMySchool = true;
          dialogScope.record.assessment = null;
          dialogScope.grades = null;
          dialogScope.record.newTransferableSubjectOrModule = {
            isNew: true,
            newApelSchool: null,
            type: 'VOTA_AINE_LIIK_V',
            assessment: null
          };
          getTransferableModulesOrSubjects(dialogScope.record.newTransferableSubjectOrModule.type);
        }

        function addMissingValuesToFormalModules(formalModules) {
          for (var i = 0; i < formalModules.length; i++) {
            $q(function () {
              var entry = formalModules[i];
              if (entry.curriculumVersionOmodule) {
                getCurriculumModule(dialogScope.curriculumVersionId, QueryUtils, entry.curriculumVersionOmodule.id).then(function (curriculumModule) {
                  entry.module = curriculumModule;
                });
              }
              if (!entry.grade.code) {
                entry.grade = Classifier.get(formalModules[i].grade);
              }
            });
          }
        }

        function addMissingValuesToFormalReplacedModules(replacedFormalModules) {
          dialogScope.curriculumModules.$promise.then(function () {
            for (var i = 0; i < replacedFormalModules.length; i++) {
              for (var j = 0; j < dialogScope.curriculumModules.length; j++) {
                if (dialogScope.curriculumModules[j].id === replacedFormalModules[i].curriculumVersionOmodule.id) {
                  dialogScope.curriculumModules[j].substitutable = true;
                }
              }
            }
          });
        }

        if (recordId) {
          dialogScope.record = angular.copy(getRecord(recordId));
          if (dialogScope.isVocational) {
            addMissingValuesToFormalModules(dialogScope.record.formalSubjectsOrModules);
            addMissingValuesToFormalReplacedModules(dialogScope.record.formalReplacedSubjectsOrModules);
          } else {
            dialogScope.record.newTransferableSubjectOrModule = dialogScope.record.formalSubjectsOrModules[0];
            dialogScope.isMySchool = dialogScope.record.newTransferableSubjectOrModule.isMySchool;
            dialogScope.record.subject = dialogScope.record.newTransferableSubjectOrModule.subject;
            dialogScope.record.nameEt = dialogScope.record.newTransferableSubjectOrModule.nameEt;
            dialogScope.record.nameEn = dialogScope.record.newTransferableSubjectOrModule.nameEn;
            dialogScope.record.apelSchool = dialogScope.record.newTransferableSubjectOrModule.apelSchool;
          }
        } else {
          dialogScope.record = {
            formalSubjectsOrModules: [],
            formalReplacedSubjectsOrModules: []
          };
          addNewEmptyFormalSubjectOrModule();
        }


        dialogScope.$watch('isMySchool', function () {
          dialogScope.record.school = $scope.school;

          if (dialogScope.record.newTransferableSubjectOrModule) {
            dialogScope.record.newTransferableSubjectOrModule.isMySchool = dialogScope.isMySchool;
          }

          if (recordId) {
            if (dialogScope.record.newTransferableSubjectOrModule) {
              var copy = angular.copy(dialogScope.record.newTransferableSubjectOrModule);
              dialogScope.isNewSchool = copy.isNewSchool;
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
                isOptional: copy.isOptional
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
            if (dialogScope.isMySchool) {
              dialogScope.record.newTransferableSubjectOrModule.type = 'VOTA_AINE_LIIK_V';
            } else {
              dialogScope.record.newTransferableSubjectOrModule.type = 'VOTA_AINE_LIIK_M';
            }
          }
        });

        dialogScope.typeChanged = function (typeCode) {
          if (dialogScope.isVocational) {
            getTransferableModules(typeCode);
          } else {
            getTransferableSubjects(typeCode);
          }
        };

        function getTransferableModules(typeCode) {
          if (typeCode === 'VOTA_AINE_LIIK_O') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, curriculumModules: true, curriculumVersion: dialogScope.curriculumVersionId}).$promise.then(function (modules) {
              dialogScope.transferableModules = modules;
              setCurriculumVersionOmodule();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_M') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, curriculumModules: false, curriculumVersion: dialogScope.curriculumVersionId, school: dialogScope.auth.school.id}).$promise.then(function (modules) {
              dialogScope.transferableModules = modules;
              setCurriculumVersionOmodule();
            });
          } else if (typeCode === 'VOTA_AINE_LIIK_V') {
            QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query(
              {student: dialogScope.student.id, otherStudents: true, school: dialogScope.auth.school.id}).$promise.then(function (modules) {
              dialogScope.transferableModules = modules;
              setCurriculumVersionOmodule();
            });
          }
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
          if (!moduleInTypeModules) {
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
          QueryUtils.endpoint('/apelSchool').get({id: apelSchool.id}, function (apelSchool) {
            Classifier.get(apelSchool.country).$promise.then(function (country) {
              dialogScope.record.newTransferableSubjectOrModule.country = country;
            });
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
            Classifier.get(subject.assessment).$promise.then(function (assessment) {
              dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
              dialogScope.record.assessment = assessment;
              getGrades(assessment.code);
            });
          });
        }

        function setFormerSubjectResult(subject) {
          if (subject) {
            dialogScope.record.newTransferableSubjectOrModule.grade = subject.gradeCode;
            dialogScope.record.newTransferableSubjectOrModule.gradeDate = subject.gradeDate;
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
          QueryUtils.endpoint('/autocomplete/curriculumversionomodulesandthemes').query({id: oModule.id, curriculumVersion: dialogScope.curriculumVersionId}, function (occupationModules) {
            dialogScope.record.newTransferableSubjectOrModule.curriculumVersionOmodule = occupationModules[0];
            dialogScope.record.curriculumVersionOmodule = occupationModules[0];
            Classifier.get(occupationModules[0].assessment).$promise.then(function (assessment) {
              if (dialogScope.record.newTransferableSubjectOrModule) {
                dialogScope.record.newTransferableSubjectOrModule.assessment = assessment.code;
                getGrades(assessment.code);
              }
              dialogScope.record.assessment = assessment;
            });

            getCurriculumModule(dialogScope.curriculumVersionId, QueryUtils, occupationModules[0].id).then(function (curriculumModule) {
              if (dialogScope.record.newTransferableSubjectOrModule) {
                dialogScope.record.newTransferableSubjectOrModule.module = curriculumModule;
                dialogScope.record.newTransferableSubjectOrModule.credits = getEKAP(curriculumModule.themes);
              }
            });
          });
        }

        function setFormerModuleResult(oModule) {
          if (oModule && oModule.gradeCode && oModule.gradeDate) {
            dialogScope.record.newTransferableSubjectOrModule.grade = oModule.gradeCode;
            dialogScope.record.newTransferableSubjectOrModule.gradeDate = oModule.gradeDate;
          }
        }
        
        dialogScope.assessmentTypeChanged = function (assessment) {
          dialogScope.record.newTransferableSubjectOrModule.grade = null;
          getGrades(assessment);
        };

        function getGrades(assessment) {
          if (dialogScope.isVocational) {
            if (assessment === 'KUTSEHINDAMISVIIS_M') {
              dialogScope.grades = Classifier.queryForDropdown({
                mainClassCode: 'KUTSEHINDAMINE',
                filterValues: ['KUTSEHINDAMINE_5', 'KUTSEHINDAMINE_4', 'KUTSEHINDAMINE_3', 'KUTSEHINDAMINE_2', 'KUTSEHINDAMINE_MA', 'KUTSEHINDAMINE_X'] 
              });
            } else if (assessment === 'KUTSEHINDAMISVIIS_E') {
              dialogScope.grades = Classifier.queryForDropdown({
                mainClassCode: 'KUTSEHINDAMINE',
                filterValues: ['KUTSEHINDAMINE_2', 'KUTSEHINDAMINE_MA', 'KUTSEHINDAMINE_A', 'KUTSEHINDAMINE_X'] 
              });
            }
          } else {
            if (assessment === 'HINDAMISVIIS_H' || assessment === 'HINDAMISVIIS_E') {
              dialogScope.grades = Classifier.queryForDropdown({
                mainClassCode: 'KORGHINDAMINE',
                filterValues: ['KORGHINDAMINE_0', 'KORGHINDAMINE_A', 'KORGHINDAMINE_M', 'KORGHINDAMINE_MI'] 
            });
            } else if (assessment === 'HINDAMISVIIS_A') {
              dialogScope.grades = Classifier.queryForDropdown({
                mainClassCode: 'KORGHINDAMINE',
                filterValues: ['KORGHINDAMINE_5', 'KORGHINDAMINE_4', 'KORGHINDAMINE_3', 'KORGHINDAMINE_2', 'KORGHINDAMINE_1', 'KORGHINDAMINE_0', 'KORGHINDAMINE_M', 'KORGHINDAMINE_MI'] 
              });
            }
          }
        }

        dialogScope.addNewSchool = function () {
          dialogScope.isNewSchool = true;
        };

        dialogScope.addNewTransferableModule = function () {
          addNewEmptyFormalSubjectOrModule();
        };

        dialogScope.addTransferableModule = function (transferableModule) {
          dialogScope.dialogForm.$setSubmitted();
          if (dialogScope.dialogForm.$valid) {
            dialogScope.record.newTransferableSubjectOrModule = null;
            transferableModule.isMySchool = dialogScope.isMySchool;
            transferableModule.isNewSchool = dialogScope.isNewSchool;
            transferableModule.isNew = false;

            if (!transferableModule.isMySchool) {
              transferableModule.type = 'VOTA_AINE_LIIK_M';
            }
            
            Classifier.get(transferableModule.grade).$promise.then(function (grade) {
              transferableModule.grade = grade;
              dialogScope.record.formalSubjectsOrModules.push(transferableModule);
            });
          }
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
          
          dialogScope.isMySchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].isMySchool;
          dialogScope.isNewSchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].isNewSchool;
          dialogScope.record.apelSchool = dialogScope.record.formalSubjectsOrModules[transferableModuleIndex].apelSchool;
          
          dialogScope.record.assessment = Classifier.get(dialogScope.record.newTransferableSubjectOrModule.assessment);
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
            var transferableModuleIndex = getFormalSubjectOrModuleIndex(dialogScope.record, changedTransferableModule.id);
            dialogScope.record.formalSubjectsOrModules[transferableModuleIndex] = changedTransferableModule;
            dialogScope.record.newTransferableSubjectOrModule = null;
            addMissingValuesToFormalModules(dialogScope.record.formalSubjectsOrModules);
          }
        };

        dialogScope.deleteTransferableModule = function (transferableModule) {
          if (transferableModule) {
            var transferableModuleIndex = getFormalSubjectOrModuleIndex(dialogScope.record, transferableModule.id);
            if (transferableModuleIndex >= 0) {
              dialogScope.record.formalSubjectsOrModules.splice(transferableModuleIndex, 1);
            } else {
              dialogScope.record.newTransferableSubjectOrModule = null;    
            }
          }
          dialogScope.record.newTransferableSubjectOrModule = null;
        };

        dialogScope.substitutableModuleChanged = function (substitutableModule) {
          var substitutableModuleIndex = null;
          if (substitutableModule.substitutable) {
            substitutableModuleIndex = getSubstitutableModuleIndex(substitutableModule.id);
            if (substitutableModuleIndex === null) {
              dialogScope.record.formalReplacedSubjectsOrModules.push({curriculumVersionOmodule: substitutableModule});
            }
          } else {
            substitutableModuleIndex = getSubstitutableModuleIndex(substitutableModule.id);
            dialogScope.record.formalReplacedSubjectsOrModules.splice(substitutableModuleIndex, 1);
          }
        };

        function getSubstitutableModuleIndex(substitutableModuleId) {
          for (var i = 0; i < dialogScope.record.formalReplacedSubjectsOrModules.length; i++) {
            if (dialogScope.record.formalReplacedSubjectsOrModules[i].curriculumVersionOmodule.id === substitutableModuleId) {
              return i;
            }
          }
          return null;
        }

        
        dialogScope.submitVocationalFormalLearning = function () {
          if (dialogScope.record.formalReplacedSubjectsOrModules.length <= 0) {
            message.error('apel.error.atLeastOneSubstitutableModule');
          } else if (!areThereMoreTransferableCreditsThanReplacedCredits()) {
            message.error('apel.error.thereMustBeMoreTransferableCreditsThanSubstitutableCredits');
          } else {
            formalSubjectsOrModulesToArray();
            formalSubjectOrModulesObjectsToIdentifiers(DataUtils, dialogScope.record);
            dialogScope.submit();
          }
        };

        function areThereMoreTransferableCreditsThanReplacedCredits() {
          var transferableCredits = getTransferableModulesCredits(dialogScope.record.formalSubjectsOrModules);
          var replacedCredits = getReplacedModulesCredits(dialogScope.record.formalReplacedSubjectsOrModules);

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
        

        function getReplacedModulesCredits(replacedModules) {
          var credits = 0;
          for (var i = 0; i < replacedModules.length; i++) {
            credits += getEKAP(replacedModules[i].curriculumVersionOmodule.themes);
          }
          return credits;
        }
        
        dialogScope.submitHigherFormalLearning = function () {
          dialogScope.record.formalSubjectsOrModules[0] = dialogScope.record.newTransferableSubjectOrModule;
          dialogScope.record.formalSubjectsOrModules[0].isMySchool = dialogScope.isMySchool;
          if (!dialogScope.record.formalSubjectsOrModules[0].isMySchool) {
            dialogScope.record.formalSubjectsOrModules[0].type = 'VOTA_AINE_LIIK_M';
          }
          formalSubjectsOrModulesToArray();
          formalSubjectOrModulesObjectsToIdentifiers(DataUtils, dialogScope.record);
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
          });

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
          });
        } else {
          record.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          });
        }
      });
    };

    var ApelApplicationFileEndpoint = QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/file');

    $scope.openAddFileDialog = function () {
      dialogService.showDialog('components/file.add.dialog.html', function (dialogScope) {
        dialogScope.addedFiles = $scope.application.files;
      }, function (submittedDialogScope) {
        var data = submittedDialogScope.data;
        oisFileService.getFromLfFile(data.file[0], function (file) {
          data.oisFile = file;
          var newFile = new ApelApplicationFileEndpoint(data);
          newFile.$save().then(function(response){
            message.info('main.messages.create.success');
            $scope.application.files.push(response);
          });
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
          });
        } else {
          comment.$save().then(function (application) {
            message.info('main.messages.create.success');
            entityToForm(application);
          });
        }
      });
    };

    $scope.deleteFile = function(file) {
      dialogService.confirmDialog({prompt: 'apel.deleteFileConfirm'}, function() {
        var deletedFile = new ApelApplicationFileEndpoint(file);
          deletedFile.$delete().then(function () {
            message.info('main.messages.delete.success');
            ArrayUtils.remove($scope.application.files, file);
          });
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
      });
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
      dialogService.confirmDialog({
        prompt: 'apel.sendBackToCreation'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendBackToCreation/').put({}, function (response) {
          message.info('apel.messages.sentBackToCreation');
          entityToForm(response);
        });
      });
    };

    $scope.sendToConfirm = function () {
      dialogService.confirmDialog({
        prompt: 'apel.sendToConfirmConfirm'
      }, function () {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/sendToConfirm/').put({}, function (response) {
          message.info('apel.messages.sentToConfirm');
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
      dialogService.showDialog('apelApplication/templates/reject.dialog.html', function () {}, function (submittedDialogScope) {
        QueryUtils.endpoint('/apelApplications/' + $scope.application.id + '/reject/').put({
          addInfo: submittedDialogScope.rejectReason
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

    $scope.delete = function () {
      dialogService.confirmDialog({
        prompt: 'apel.deleteConfirm'
      }, function () {
        var application = new ApelApplicationEndpoint($scope.application);
        application.$delete().then(function () {
          message.info('main.messages.delete.success');
          $location.path('/apelApplication');
        });
      });
    };
  }).controller('ApelApplicationViewController', function ($scope, $route, QueryUtils, oisFileService, Classifier, ArrayUtils, dialogService, message, config) {
    $scope.applicationId = $route.current.params.id;
    $scope.auth = $route.current.locals.auth;
    $scope.canConfirmAndRemoveConfirmation = ArrayUtils.contains($scope.auth.authorizedRoles, "ROLE_OIGUS_K_TEEMAOIGUS_VOTA");
    $scope.application = {};
    $scope.formState = {};
    QueryUtils.endpoint('/autocomplete/schools').query({id: $scope.auth.school.id}).$promise.then(function (schools) {
      $scope.school = schools[0];  
    });

    function entityToForm(entity) {
      $scope.application = entity;
      QueryUtils.endpoint('/students/' + $scope.application.student.id).search(function (result) {
        $scope.application.person = result.person;
        $scope.application.curriculumVersion = result.curriculumVersion;
        $scope.application.isVocational = result.curriculumVersion.isVocational;

        getDataForInformalLearningTables($scope, QueryUtils, Classifier);
        getDataForFormalLearningTables($scope, QueryUtils, Classifier);
      });
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
  });
}());
