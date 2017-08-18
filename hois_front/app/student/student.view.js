'use strict';

angular.module('hitsaOis').controller('StudentViewMainController', ['$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'oisFileService',

  function ($mdDialog, $q, $route, $scope, dialogService, message, Classifier, QueryUtils, oisFileService) {
    var studentId = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/students');

    $scope.studentId = studentId;
    $scope.currentNavItem = 'student.main';

    Endpoint.get({ id: studentId }, function (result) {
      $scope.student = result;
      if ($scope.student.photo) {
        $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo);
      } else {
        $scope.student.imageUrl = '?' + new Date().getTime();
      }
    });

    $scope.representativesCriteria = { order: 'person.lastname', size: 5, page: 1 };
    $scope.representatives = {};
    var representativesMapper = Classifier.valuemapper({ relation: 'OPPURESINDAJA' });

    $scope.afterRepresentativesLoad = function (result) {
      $scope.representatives.content = representativesMapper.objectmapper(result.content);
      $scope.representatives.totalElements = result.totalElements;
    };

    function loadRepresentatives() {
      var query = angular.extend({}, QueryUtils.getQueryParams($scope.representativesCriteria), { id: studentId });
      $scope.representatives.$promise = QueryUtils.endpoint('/studentrepresentatives/:id').search(query, $scope.afterRepresentativesLoad);
    }
    $scope.loadRepresentatives = loadRepresentatives;

    $q.all(representativesMapper.promises).then(loadRepresentatives);

    $scope.editRepresentative = function (representativeId) {
      var RepresentativeEndpoint = QueryUtils.endpoint('/studentrepresentatives/' + studentId);
      $mdDialog.show({
        controller: function ($scope) {
          $scope.formState = {};

          if (representativeId) {
            $scope.record = RepresentativeEndpoint.get({ id: representativeId });
          } else {
            $scope.record = new RepresentativeEndpoint({ person: {} });
          }
          $scope.cancel = $mdDialog.hide;
          $scope.update = function () {
            $scope.studentRepresentativeEditForm.$setSubmitted();
            if (!$scope.studentRepresentativeEditForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }
            function afterSave() {
              message.info(representativeId ? 'main.messages.update.success' : 'main.messages.create.success');
              $mdDialog.hide();
              loadRepresentatives();
            }
            if ($scope.record.id) {
              $scope.record.$update().then(afterSave);
            } else {
              $scope.record.$save().then(afterSave);
            }
          };
          $scope.delete = function () {
            dialogService.confirmDialog({ prompt: 'student.representative.deleteconfirm' }, function () {
              $scope.record.$delete().then(function () {
                message.info('main.messages.delete.success');
                $mdDialog.hide();
                loadRepresentatives();
              });
            });
          };
          $scope.lookupPerson = function (response) {
            // fill first/lastname and make them readonly
            $scope.record.person.firstname = response.firstname;
            $scope.record.person.lastname = response.lastname;
            // fill other empty fields
            if (!$scope.record.person.phone && response.phone) {
              $scope.record.person.phone = response.phone;
            }
            if (!$scope.record.person.email && response.email) {
              $scope.record.person.email = response.email;
            }
            $scope.formState.idcode = response.idcode;
          };
          $scope.lookupFailure = function () {
            $scope.formState.idcode = undefined;
          };
        },
        templateUrl: 'studentRepresentative/representative.edit.dialog.html',
        clickOutsideToClose: true
      });
    };
  }
]).controller('StudentViewResultsController', ['$q', '$route', '$scope', function ($q, $route, $scope) {
  $scope.currentNavItem = 'student.results';
  $scope.resultsCurrentNavItem = 'student.curriculumFulfillment';
  $scope.auth = $route.current.locals.auth;

  $scope.student = $route.current.locals.student;
  $scope.studentId = $route.current.params.id;  //used in links in html files

  $scope.isStudentCurriculumFulfilled = function (student) {
    return student && angular.isNumber(student.credits) && student.credits >= student.curriculumCredits;
  };
}]).controller('StudentViewResultsVocationalController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', '$rootScope', function ($q, $route, $scope, Classifier, QueryUtils, $rootScope) {
  var gradeMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', studyYear: 'OPPEAASTA' });
  var moduleMapper = Classifier.valuemapper({ module: 'KUTSEMOODUL' });
  $scope.loadCurriculumFulfillment = function () {
    if (!angular.isObject($scope.vocationalResultsCurriculum)) {
      var curriculumModulesById = {};
      $scope.vocationalResults.curriculumModules.forEach(function (it) {
        curriculumModulesById[it.id] = it;
      });
      $scope.vocationalResults.extraCurriculaModules.forEach(function (it) {
        curriculumModulesById[it.id] = it;
      });

      var extracurricularVocationalModuleResults = [];
      var moduleResultById = {};
      $scope.vocationalResults.results.forEach(function (it) {
        if (!angular.isObject(moduleResultById[it.module.id])) {
          moduleResultById[it.module.id] = { themeResultById: {}, totalSubmitted: 0 };
        }

        if (angular.isObject(it.theme)) {
          moduleResultById[it.module.id].themeResultById[it.theme.id] = it;
          moduleResultById[it.module.id].totalSubmitted += it.credits;
        } else {
          angular.extend(moduleResultById[it.module.id], it);
          //extra curricula module results
          if (!angular.isObject(curriculumModulesById[it.module.id])) {
            extracurricularVocationalModuleResults.push(it);
          }
        }
      });

      $scope.moduleResultById = moduleResultById;
      $scope.positiveThemeResult = function (themeResult) {
        return angular.isObject(themeResult) && angular.isObject(themeResult.grade) && angular.isString(themeResult.grade.value) && parseInt(themeResult.grade.value) > 2;
      };
      $scope.vocationalResultsCurriculum = {};
      $scope.vocationalResultsCurriculum.vocationalResultsModules = $scope.vocationalResults.curriculumModules.sort(function (m1, m2) {
        return $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });
      $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules = $scope.vocationalResults.extraCurriculaModules.sort(function (m1, m2) {
        return $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });

      $scope.allThemesHavePositiveGrade = function (module) {
        var themes = curriculumModulesById[module.id].themes;
        if (themes.length === 0) {
          return false;
        }

        var allThemesGraded = true;
        for (var i = 0; i < themes.length; i++) {
          var theme = themes[i];
          if (!angular.isObject(moduleResultById[module.id]) || !angular.isObject(moduleResultById[module.id].themeResultById[theme.id]) || !$scope.positiveThemeResult(moduleResultById[module.id].themeResultById[theme.id])) {
            allThemesGraded = false;
            break;
          }
        }
        return allThemesGraded;
      };
    }
  };


  $scope.loadStudyYearResults = function () {
    if (!angular.isObject($scope.vocationalResultsStudyYear)) {
      var yearToModule = {};
      $scope.vocationalResults.results.forEach(function (it) {
        if (!angular.isObject(yearToModule[it.studyYear.code])) {
          yearToModule[it.studyYear.code] = { studyYear: it.studyYear, studyYearStartDate: it.studyYearStartDate, kkh: 0, credits: 0, results: [] };
        }
        yearToModule[it.studyYear.code].results.push(it);
      });

      var vocationalResultsStudyYear = Object.values(yearToModule);
      vocationalResultsStudyYear.forEach(function (studyYearModulesThemes) {
        var creditsSum = 0;
        var gradeCreditsSum = 0;
        studyYearModulesThemes.results.forEach(function (moduleTheme) {
          if (moduleTheme.theme === null) {
            var grade = parseInt(moduleTheme.grade.value);
            if ($scope.positiveGrade(grade)) {
              creditsSum += moduleTheme.credits;
              gradeCreditsSum += moduleTheme.credits * parseInt(grade);
            }
          }
        });

        if (creditsSum !== 0) {
          studyYearModulesThemes.kkh = gradeCreditsSum / creditsSum;
        }
        studyYearModulesThemes.credits = creditsSum;
      });

      $scope.vocationalResultsStudyYear = vocationalResultsStudyYear.sort(function (a, b) { return a.studyYearStartDate < b.studyYearStartDate; });
    }
  };


  $scope.loadModuleThemeResults = function () {
    if (!angular.isObject($scope.vocationalResultsPassing)) {
      $scope.vocationalResultsPassing = $scope.vocationalResults;
    }
  };

  $scope.displayTeachers = function (teachers) {
    if (angular.isArray(teachers)) {
      return teachers.map(function (teacher) { return teacher.nameEt; }).sort().join(', ');
    }
  };

  $scope.positiveGrade = function (grade) {
    return (angular.isNumber(grade) && grade > 2) || (angular.isString(grade) && parseInt(grade) > 2);
  };

  function loadVocationalResults() {
    if (!angular.isObject($scope.vocationalResults)) {
      QueryUtils.endpoint('/students/' + $scope.student.id + '/vocationalResults').get(function (vocationalResults) {
        vocationalResults.results = gradeMapper.objectmapper(vocationalResults.results);
        vocationalResults.curriculumModules.forEach(function (it) {
          it.curriculumModule = moduleMapper.objectmapper(it.curriculumModule);
        });
        vocationalResults.extraCurriculaModules.forEach(function (it) {
          it.curriculumModule = moduleMapper.objectmapper(it.curriculumModule);
        });
        $scope.vocationalResults = vocationalResults;
        //load first tab
        $scope.loadCurriculumFulfillment();
      });
    }
  }

  loadVocationalResults();

}]).controller('StudentViewResultsHigherController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', '$rootScope',
  function ($q, $route, $scope, Classifier, QueryUtils, $rootScope) {

    function getHigherResults() {
      if (!angular.isDefined($scope.higherResults)) {
        var id = $route.current.params.id;
        QueryUtils.endpoint('/students/' + id + '/higherResults').get().$promise.then(function (response) {
          $scope.higherResults = response;
          $scope.student.higherResults = $scope.higherResults;
          $scope.student.higherResults.modules.sort(moduleComparator);
        });
      }
    }

    getHigherResults();

    $scope.hasMandatorySubjects = function (module) {
      return $scope.higherResults.subjectResults.some(function (s) {
        return s.higherModule.id === module.id && !s.isOptional;
      });
    };

    $scope.hasOptionalSubjects = function (module) {
      return $scope.higherResults.subjectResults.some(function (s) {
        return s.higherModule.id === module.id && s.isOptional;
      });
    };

    $scope.filterSubjectResultsByModule = function (higherModule) {
      return function (subjectResult) {
        return higherModule.id === subjectResult.higherModule.id;
      };
    };

    $scope.filterSubjectResultsByStudyPeriod = function (studyPeriod) {
      return function (subjectResult) {
        return subjectResult.lastGrade && subjectResult.lastGrade.gradeValue && studyPeriod.id === subjectResult.lastGrade.studyPeriod;
      };
    };

    function isFreeModule(module) {
      return module.type === 'KORGMOODUL_V';
    }

    function moduleComparator(v1, v2) {
      if (isFreeModule(v1) && !isFreeModule(v2)) {
        return 1;
      } else if (!isFreeModule(v1) && isFreeModule(v2)) {
        return -1;
      } else {
        return $rootScope.currentLanguageNameField(v1).localeCompare($rootScope.currentLanguageNameField(v2));
      }
    }

  }]).controller('StudentViewDocumentsController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', function ($q, $route, $scope, Classifier, QueryUtils) {
    $scope.studentId = $route.current.params.id;
    $scope.currentNavItem = 'student.documents';
    $scope.applicationsCriteria = { order: 'submitted', studentId: $scope.studentId };
    $scope.applications = {};
    $scope.auth = $route.current.locals.auth;

    var applicationsMapper = Classifier.valuemapper({ type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS' });
    $scope.afterApplicationsLoad = function (result) {
      $scope.applicationsCriteria.size = result.size;
      $scope.applicationsCriteria.page = result.number + 1;
      $scope.applications.content = applicationsMapper.objectmapper(result.content);
      $scope.applications.totalElements = result.totalElements;
    };

    $scope.loadApplications = function () {
      var query = QueryUtils.getQueryParams($scope.applicationsCriteria);
      $scope.applications.$promise = QueryUtils.endpoint('/students/:studentId/applications').search(query, $scope.afterApplicationsLoad);
    };

    var StudentApplicableApplicationsEndpoint = QueryUtils.endpoint('/applications/student/' + $scope.studentId + '/applicable');
    StudentApplicableApplicationsEndpoint.search(function (result) {
      $scope.applicationTypesApplicable = result;
      $scope.applicationTypes = Classifier.queryForDropdown({ mainClassCode: 'AVALDUS_LIIK' });
    });


    $scope.directivesCriteria = { order: 'headline', studentId: $scope.studentId };
    $scope.directives = {};

    var directivesMapper = Classifier.valuemapper({ type: 'KASKKIRI', status: 'KASKKIRI_STAATUS' });
    $scope.afterDirectivesLoad = function (result) {
      $scope.directivesCriteria.size = result.size;
      $scope.directivesCriteria.page = result.number + 1;
      $scope.directives.content = directivesMapper.objectmapper(result.content);
      $scope.directives.totalElements = result.totalElements;
    };

    $scope.loadDirectives = function () {
      var query = QueryUtils.getQueryParams($scope.directivesCriteria);
      $scope.directives.$promise = QueryUtils.endpoint('/students/:studentId/directives').search(query, $scope.afterDirectivesLoad);
    };

    // initially load whole form
    QueryUtils.endpoint('/students/:studentId/documents').search({ studentId: $scope.studentId }, function (result) {
      $scope.student = result.student;
      $q.all(applicationsMapper.promises).then(function () {
        $scope.afterApplicationsLoad(result.applications);
      });
      $q.all(directivesMapper.promises).then(function () {
        $scope.afterDirectivesLoad(result.directives);
      });
    });
  }]).controller('StudentEditController', ['$location', '$route', '$scope', 'message', 'QueryUtils', function ($location, $route, $scope, message, QueryUtils) {
    var id = $route.current.params.id;

    $scope.student = QueryUtils.endpoint('/students').get({ id: id });
    $scope.update = function () {
      $scope.studentEditForm.$setSubmitted();
      if (!$scope.studentEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      $scope.student.$update().then(function () {
        message.updateSuccess();
        $location.path('/students/' + id + '/main');
      });
    };
  }]).controller('StudentAbsencesController', ['$mdDialog', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($mdDialog, $route, $scope, dialogService, message, QueryUtils) {
      $scope.studentId = $route.current.params.id;
      $scope.currentNavItem = 'student.absences';
      QueryUtils.createQueryForm($scope, '/students/' + $scope.studentId + '/absences', { order: 'validFrom'});

      var oldAfterLoadData = $scope.afterLoadData;
      $scope.afterLoadData = function(resultData) {
        $scope.tabledata.studentName = resultData.studentName;
        $scope.tabledata.studentGroup = resultData.studentGroup;
        $scope.tabledata.canAddAbsence = resultData.canAddAbsence;
        oldAfterLoadData(resultData);
      };

      $scope.editAbsence = function (absence) {
        var AbsenceEndpoint = QueryUtils.endpoint('/students/' + $scope.studentId + '/absences');
        var loadAbsences = $scope.loadData;

        absence = angular.copy(absence, {});
        absence.studentName = $scope.tabledata.studentName;
        absence.studentGroup = $scope.tabledata.studentGroup;

        $mdDialog.show({
          controller: function ($scope) {
            $scope.record = new AbsenceEndpoint(absence || {});
            $scope.cancel = $mdDialog.hide;
            $scope.update = function () {
              $scope.studentAbsenceForm.$setSubmitted();
              if (!$scope.studentAbsenceForm.$valid) {
                message.error('main.messages.form-has-errors');
                return;
              }
              var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
              function afterSave() {
                message.info(msg);
                $mdDialog.hide();
                loadAbsences();
              }
              if ($scope.record.id) {
                $scope.record.$update().then(afterSave);
              } else {
                $scope.record.$save().then(afterSave);
              }
            };
            $scope.delete = function () {
              dialogService.confirmDialog({ prompt: 'student.absence.deleteconfirm' }, function () {
                $scope.record.$delete().then(function () {
                  message.info('main.messages.delete.success');
                  $mdDialog.hide();
                  loadAbsences();
                });
              });
            };
          },
          templateUrl: 'student/absence.edit.dialog.html',
          clickOutsideToClose: true
        });
      };

      $scope.loadData();
    }]);
