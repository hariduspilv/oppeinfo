'use strict';

angular.module('hitsaOis').controller('StudentViewMainController', ['$mdDialog', '$q', '$route', '$scope', 'dialogService',
  'message', 'Classifier', 'QueryUtils', 'oisFileService', 'ArrayUtils',
  function ($mdDialog, $q, $route, $scope, dialogService, message, Classifier, QueryUtils, oisFileService, ArrayUtils) {
    var auth = $route.current.locals.auth;
    var studentId = (auth.isStudent() || auth.isParent() ? auth.student : $route.current.params.id);
    var baseUrl = '/students';

    $scope.auth = auth;
    $scope.studentId = studentId;
    $scope.currentNavItem = 'student.main';

    var dormitoryMapper = Classifier.valuemapper({ dormitory: 'YHISELAMU' });
    var loadStudent = function () {
      QueryUtils.endpoint(baseUrl).get({ id: studentId, withHTM: true }, function (result) {
        $scope.student = result;
        if ($scope.student.person.address && $scope.student.person.postcode && $scope.student.person.address.endsWith($scope.student.person.postcode)) {
          $scope.student.person.address = $scope.student.person.address.substring(0, $scope.student.person.address.lastIndexOf($scope.student.person.postcode));
          if ($scope.student.person.address.endsWith(", ")) {
            $scope.student.person.address = $scope.student.person.address.slice(0, -2);
          }
        }
        mapForeignLanguages();
        $scope.student.dormitoryHistory.forEach(function (history) {
          dormitoryMapper.objectmapper(history);
        });
        if ($scope.student.photo) {
          $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
        } else {
          $scope.student.imageUrl = '?' + new Date().getTime();
        }
      });
    };
    loadStudent();

    function mapForeignLanguages() {
      $scope.student.studentLanguages.forEach(function (studentLanguage) {
        studentLanguage.languageFilter = [];
      });
      $scope.student.foreignLanguageTypes = ArrayUtils.partSplit($scope.student.studentLanguages, 2);
    }

    // representatives

    $scope.representativesCriteria = { order: 'person.lastname', size: 5, page: 1 };
    $scope.representatives = {};
    var representativesMapper = Classifier.valuemapper({ relation: 'OPPURESINDAJA' });

    function afterRepresentativesLoad(result) {
      $scope.representatives.content = representativesMapper.objectmapper(result.content);
      $scope.representatives.totalElements = result.totalElements;
    }

    function loadRepresentatives() {
      var query = angular.extend({}, QueryUtils.getQueryParams($scope.representativesCriteria), { id: studentId });
      $scope.representatives.$promise = QueryUtils.endpoint('/studentrepresentatives/:id').search(query, afterRepresentativesLoad);
    }
    $scope.loadRepresentatives = loadRepresentatives;

    $q.all(representativesMapper.promises).then(loadRepresentatives);

    $scope.editRepresentative = function (representativeId) {
      var student = $scope.student;
      var RepresentativeEndpoint = QueryUtils.endpoint('/studentrepresentatives/' + studentId);
      $mdDialog.show({
        controller: function ($scope) {
          $scope.isParent = $route.current.locals.auth.isParent();
          $scope.formState = {student: student};

          if (representativeId) {
            $scope.record = RepresentativeEndpoint.get({ id: representativeId });
          } else {
            $scope.record = new RepresentativeEndpoint({ person: {} });
          }

          function idcodevaliditycheck() {
            var ctrl = $scope.studentRepresentativeEditForm.idcode;
            ctrl.$setValidity('notUnique', $scope.record.person.idcode !== $scope.formState.student.person.idcode);
          }

          function setidcodevalidity(isValid) {
            var ctrl = $scope.studentRepresentativeEditForm.idcode;
            ctrl.$setValidity('estonianIdcode', isValid);
          }

          $scope.cancel = $mdDialog.hide;
          $scope.update = function () {
            idcodevaliditycheck();
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
              $scope.record.$update().then(afterSave).catch(angular.noop);
            } else {
              $scope.record.$save().then(afterSave).catch(angular.noop);
            }
          };
          $scope.delete = function () {
            dialogService.confirmDialog({ prompt: 'student.representative.deleteconfirm' }, function () {
              $scope.record.$delete().then(function () {
                message.info('main.messages.delete.success');
                $mdDialog.hide();
                loadRepresentatives();
              }).catch(angular.noop);
            });
          };
          $scope.lookupPerson = function () {
            function setresult(response) {
              $scope.record.person.firstname = response.firstname;
              $scope.record.person.lastname = response.lastname;
            }
            var idcode = $scope.record.person.idcode;
            if(idcode && idcode.length === 11 && idcode !== $scope.formState.idcode) {
              QueryUtils.endpoint('/autocomplete/persons', {search: {method: 'GET'}}).search({idcode: idcode, role: 'forrepresentative'}).$promise.then(function(response) {
                setidcodevalidity(true);
                // fill first/lastname and make them readonly
                setresult(response);
                // fill other empty fields
                if (!$scope.record.person.phone && response.phone) {
                  $scope.record.person.phone = response.phone;
                }
                if (!$scope.record.person.email && response.email) {
                  $scope.record.person.email = response.email;
                }
                $scope.formState.idcode = response.idcode;
              }).catch(function(error) {
                setresult({});
                $scope.formState.idcode = undefined;
                setidcodevalidity(error.status !== 412);
              });
            } else if(idcode !== $scope.formState.idcode) {
              setresult({});
              $scope.formState.idcode = undefined;
              setidcodevalidity(idcode.length === 11);
            }
            idcodevaliditycheck();
          };
        },
        templateUrl: 'studentRepresentative/representative.edit.dialog.html',
        clickOutsideToClose: false
      });
    };

    // foreign studies

    $scope.foreignstudiesCriteria = { order: '-3', size: 5, page: 1 };
    $scope.foreignstudies = {};

    function afterForeignstudiesLoad(result) {
      $scope.foreignstudies.content = result.content;
      $scope.foreignstudies.totalElements = result.totalElements;
    }

    $scope.loadForeignstudies = function() {
      var query = angular.extend({}, QueryUtils.getQueryParams($scope.foreignstudiesCriteria), { id: studentId });
      $scope.foreignstudies.$promise = QueryUtils.endpoint(baseUrl + '/:id/foreignstudies').search(query, afterForeignstudiesLoad);
    };

    $scope.loadForeignstudies();

    // akad directives

    $scope.akadDirectivesCriteria = { order: '-8', size: 5, page: 1 };
    $scope.akadDirectives = {};
    var akadDirectivesMapper = Classifier.valuemapper({ reason: 'AKADPUHKUS_POHJUS' });

    function afterAkadDirectivesLoad(result) {
      $scope.akadDirectives.content = akadDirectivesMapper.objectmapper(result.content);
      $scope.akadDirectives.totalElements = result.totalElements;
    }

    $scope.loadAkadDirectives = function() {
      var query = angular.extend({}, QueryUtils.getQueryParams($scope.akadDirectivesCriteria), { id: studentId });
      $scope.akadDirectives.$promise = QueryUtils.endpoint(baseUrl + '/:id/akadDirectives').search(query, afterAkadDirectivesLoad);
    };

    $scope.loadAkadDirectives();

    $scope.updatePersonData = function () {
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl + "/" + studentId + "/populationRegister").get({}, function() {
        message.info("rr.operations.updateStudentData.success");
        QueryUtils.loadingWheel($scope, false);
        loadStudent();
      }, function() {
        QueryUtils.loadingWheel($scope, false);
      });
    };

    $scope.requestPhotoBoxPhoto = function () {
      dialogService.confirmDialog({ prompt: 'student.photoBox.updateConfirm' }, function () {
        QueryUtils.loadingWheel($scope, true);
        QueryUtils.endpoint(baseUrl + '/:id/requestStudentPhoto').search({id: studentId}, function (result) {
          if (angular.isDefined(result.fdata)) {
            $scope.student.photo = result;
            $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
            message.info('student.photoBox.success');
          } else {
            message.error('student.photoBox.notFound');
          }
          QueryUtils.loadingWheel($scope, false);
        }, function() {
          QueryUtils.loadingWheel($scope, false);
        });
      });
    };
  }
]).controller('StudentViewResultsController', ['$route', '$scope', '$localStorage', 'QueryUtils', 'config', 'StudentUtil',
function ($route, $scope, $localStorage, QueryUtils, config, StudentUtil) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {};
  $scope.formState.openAllCollapsables = false;

  $scope.studentId = ($scope.auth.isStudent() || $scope.auth.isParent() ? $scope.auth.student : $route.current.params.id);
  QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
    $scope.student = student;
    if ($scope.student.isGuestStudent || ($scope.student.type === 'OPPUR_E' && $scope.student.curriculumVersion === null)) {
      $scope.changeResultsCurrentNavItem('student.inOrderOfPassing');
    }
    canWatchStudentConnectedEntities();
    if (StudentUtil.isActive($scope.student.status) && !$scope.student.isGuestStudent && $scope.auth.authorizedRoles.indexOf('ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE') !== -1) {
      $scope.supplementPreviewPdfUrl = config.apiUrl + '/documents/supplement/' + $scope.studentId + '/preview.pdf';
    }
  });
  $scope.currentNavItem = 'student.results';

  $scope.externalMissingCurriculum = function(student) {
    return student.type === 'OPPUR_E' && (student.curriculumVersion === null || student.curriculumVersion === undefined);
  };

  $scope.changeResultsCurrentNavItem = function (navItem) {
    $scope.resultsCurrentNavItem = navItem;

    var navItemIndex = currentSchoolUsersResultsNavItemIndex();
    var localStorageNavItem = {school: $scope.auth.school.id, user: $scope.auth.user, navItemName: navItem};
    if (navItemIndex) {
      $localStorage.resultsCurrentNavItems[navItemIndex-1] = localStorageNavItem;
    } else {
      if (!$localStorage.resultsCurrentNavItems) {
        $localStorage.resultsCurrentNavItems = [];
      }
      $localStorage.resultsCurrentNavItems.push(localStorageNavItem);
    }
  };

  function currentSchoolUsersResultsNavItemIndex() {
    if ($localStorage.resultsCurrentNavItems) {
      for (var i = 0; i < $localStorage.resultsCurrentNavItems.length; i++) {
        var navItem = $localStorage.resultsCurrentNavItems[i];

        if ($scope.auth.school.id === navItem.school && $scope.auth.user === navItem.user) {
          return i + 1;
        }
      }
    }
    return null;
  }

  if ($localStorage.resultsCurrentNavItems) {
    var navItemIndex = currentSchoolUsersResultsNavItemIndex();
    if (navItemIndex) {
      $scope.resultsCurrentNavItem = $localStorage.resultsCurrentNavItems[navItemIndex-1].navItemName;
    } else {
      $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
    }
  } else {
    $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
  }

  function canWatchStudentConnectedEntities() {
    if ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) {
      $scope.canWatchStudentConnectedEntities = true;
    } else if ($scope.auth.isTeacher() && $scope.student.studentGroup && $scope.auth.teacherGroupIds.length > 0) {
      $scope.canWatchStudentConnectedEntities = $scope.auth.teacherGroupIds.indexOf($scope.student.studentGroup.id) !== -1;
    }
  }

  $scope.isStudentCurriculumFulfilled = function (student) {
    return student && angular.isNumber(student.credits) && student.credits >= student.curriculumCredits;
  };

}]).controller('StudentViewResultsVocationalController', ['$filter', '$q', '$route', '$scope', 'GRADING_SCHEMA_TYPE', 'Classifier', 'GradingSchema', 'VocationalGradeUtil', 'QueryUtils', '$rootScope',
function ($filter, $q, $route, $scope, GRADING_SCHEMA_TYPE, Classifier, GradingSchema, VocationalGradeUtil, QueryUtils, $rootScope) {
  $scope.auth = $route.current.locals.auth;
  $scope.gradeUtil = VocationalGradeUtil;
  var entryMapper = Classifier.valuemapper({ studyYear: 'OPPEAASTA', entryType: 'SISSEKANNE' });
  var moduleMapper = Classifier.valuemapper({ module: 'KUTSEMOODUL' });
  var gradeMapper;

  var gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.VOCATIONAL);
  $q.all(gradingSchema.promises).then(function () {
    gradeMapper = gradingSchema.gradeMapper(gradingSchema.gradeSelection(), ['grade']);
  });

  $scope.$watch('resultsCurrentNavItem', function () {
    loadCurrentNavItemData();
  });

  $scope.loadCurriculumFulfillment = function() {
    var outcomeIds = themeOutcomeIds();

    if (!angular.isObject($scope.vocationalResultsCurriculum)) {
      var formalLearningResult = [];
      var otherOutcomeResults = [];
      var moduleResultById = {};
      $scope.vocationalResults.results.forEach(function (it) {
        if (!angular.isObject(moduleResultById[it.module.id])) {
          moduleResultById[it.module.id] = { themeResultById: {}, outcomeResultById: {}, totalSubmitted: 0 };
        }

        if (angular.isObject(it.theme)) {
          moduleResultById[it.module.id].themeResultById[it.theme.id] = it;
        } else if (angular.isObject(it.outcome)) {
          // outcomes that do not belong to any module themes that are shown in curriculum fullfillment
          // are shown under 'other outcomes' hois-collapsable
          if (outcomeIds.indexOf(it.outcome.id) !== -1) {
            moduleResultById[it.module.id].outcomeResultById[it.outcome.id] = it;
          } else {
            otherOutcomeResults.push(it);
          }
        } else {
          angular.extend(moduleResultById[it.module.id], it);

          // collect formal learning results
          if (it.isApelTransfer && it.isFormalLearning) {
            formalLearningResult.push(it);
          }
        }
      });

      var curriculumModulesById = {};
      $scope.vocationalResults.curriculumModules.forEach(function (it) {
        curriculumModulesById[it.curriculumModule.id] = it;

        // filter replaced themes that do not have a grade
        it.themes = it.themes.filter(function (theme) {
          var moduleResult = moduleResultById[it.curriculumModule.id];
          var themeResult = moduleResult ? moduleResult.themeResultById[theme.id] : undefined;
          return it.replacedThemes.indexOf(theme.id) === -1 || angular.isDefined(themeResult);
        });
      });

      $scope.moduleResultById = moduleResultById;

      // when setting module submitted credits theme.isOk is set
      $scope.vocationalResults.curriculumModules.forEach(function (it) {
        setModuleSubmittedCreditsAndBacklogs(it);
      });
      $scope.vocationalResults.extraCurriculaModules.forEach(function (it) {
        setModuleSubmittedCreditsAndBacklogs(it);
      });

      $scope.vocationalResultsCurriculum = {};
      $scope.vocationalResultsCurriculum.vocationalResultsModules =
        $scope.vocationalResults.curriculumModules.sort(moduleComparator);
      $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules =
        $scope.vocationalResults.extraCurriculaModules.sort(moduleComparator);

      // remove modules that are replaced by apel formal learning
      formalLearningResult.forEach(function (formalLearning) {
        formalLearning.replacedModules.forEach(function (replacedModuleId) {
          var replacedModule = $scope.vocationalResultsCurriculum.vocationalResultsModules.find(function (module) {
            return module.curriculumModule.id === replacedModuleId;
          });
          if (angular.isObject(replacedModule)) {
            var curriculumModuleIndex = $scope.vocationalResultsCurriculum.vocationalResultsModules.indexOf(replacedModule);
            if (curriculumModuleIndex > -1) {
              $scope.vocationalResultsCurriculum.vocationalResultsModules.splice(curriculumModuleIndex, 1);
            }
          }

          var replacedExtraCurriculaModule = $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules.find(function (module) {
            return module.curriculumModule.id === replacedModuleId;
          });
          if (angular.isObject(replacedExtraCurriculaModule)) {
            var extraCurriculuaModuleIndex = $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules.indexOf(replacedExtraCurriculaModule);
            if (extraCurriculuaModuleIndex > -1) {
              $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules.splice(extraCurriculuaModuleIndex, 1);
            }
          }
        });
      });

      var replacedModuleResults = $filter('filter')($scope.vocationalResults.results, {'isFormalLearning': true, replacedModules: ''});
      $scope.vocationalResultsCurriculum.replacedModuleResults = replacedModuleResults.sort(function (r1, r2) {
        if (r1.module.nameEt && r2.module.nameEt) {
          return $rootScope.currentLanguageNameField(r1.module).localeCompare($rootScope.currentLanguageNameField(r2.module));
        } else {
          return 0;
        }
      });

      // apel formal learnings that only replace extra curricular modules should be shown separately
      var onlyReplacedExtraCurriculaIndexes = [];
      $scope.vocationalResultsCurriculum.replacedModuleResults.forEach(function (result, index) {
        var hasReplacedCurriculumModule = false;
        for (var i = 0; i < result.replacedModules.length; i++) {
          if (curriculumModulesById.hasOwnProperty(result.replacedModules[i])) {
            hasReplacedCurriculumModule = true;
            break;
          }
        }
        if (!hasReplacedCurriculumModule) {
          onlyReplacedExtraCurriculaIndexes.push(index);
        }
      });

      $scope.vocationalResultsCurriculum.replacedExtraCurriculaModuleResults = $scope.vocationalResultsCurriculum.replacedModuleResults.filter(function (module, index) {
        return onlyReplacedExtraCurriculaIndexes.indexOf(index) !== -1;
      });
      $scope.vocationalResultsCurriculum.replacedModuleResults = $scope.vocationalResultsCurriculum.replacedModuleResults.filter(function (module, index) {
        return onlyReplacedExtraCurriculaIndexes.indexOf(index) === -1;
      });
      $scope.vocationalResultsCurriculum.otherOutcomeResults = otherOutcomeResults;
      changeModulesExpandedStatus();
    }
  };

  function moduleComparator(m1, m2) {
    return moduleOrderComparator(m1, m2) || m1.curriculumModule.specOrderNr - m2.curriculumModule.specOrderNr ||
      $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
  }

  function moduleOrderComparator(m1, m2) {
    return (m1.curriculumModule.orderNr === null) - (m2.curriculumModule.orderNr === null) ||
      + (m1.curriculumModule.orderNr > m2.curriculumModule.orderNr) || - (m1.curriculumModule.orderNr < m2.curriculumModule.orderNr);
  }

  function themeOutcomeIds() {
    var outcomeIds = [];
    $scope.vocationalResults.curriculumModules.forEach(function (module) {
      outcomeIds = outcomeIds.concat(getModuleOutcomes(module));
    });
    $scope.vocationalResults.extraCurriculaModules.forEach(function (module) {
      outcomeIds = outcomeIds.concat(getModuleOutcomes(module));
    });
    return outcomeIds;
  }

  function getModuleOutcomes(module) {
    var moduleOutcomes = [];
    module.themes.forEach(function (theme) {
      theme.curriculumModuleOutcomes.forEach(function (outcome) {
        moduleOutcomes.push(outcome.id);
      });
    });
    module.otherCurriculumVersionModuleThemes.forEach(function (theme) {
      theme.curriculumModuleOutcomes.forEach(function (outcome) {
        moduleOutcomes.push(outcome.id);
      });
    });
    return moduleOutcomes;
  }

  function loadVocationalResults() {
    QueryUtils.loadingWheel($scope, true);
    QueryUtils.endpoint('/students/' + $scope.student.id + '/vocationalResults').get(function (vocationalResults) {
      vocationalResults.results = gradeMapper.objectmapper(vocationalResults.results);
      vocationalResults.curriculumModules.forEach(function (it) {
        it.curriculumModule = moduleMapper.objectmapper(it.curriculumModule);
      });
      vocationalResults.extraCurriculaModules.forEach(function (it) {
        it.curriculumModule = moduleMapper.objectmapper(it.curriculumModule);
      });
      $scope.vocationalResults = vocationalResults;
      $scope.loadCurriculumFulfillment();
      QueryUtils.loadingWheel($scope, false);
    });
  }

  $scope.changeModulesExpandedStatus = function () {
    changeModulesExpandedStatus();
  };

  function changeModulesExpandedStatus() {
    $scope.vocationalResultsCurriculum.vocationalResultsModules.forEach(function (module) {
      module.collapsableOpen = $scope.formState.openAllCollapsables;
    });
    $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules.forEach(function (module) {
      module.collapsableOpen = $scope.formState.openAllCollapsables;
    });
    $scope.vocationalResultsCurriculum.otherOutcomesCollapsableOpen = $scope.formState.openAllCollapsables;
  }

  $scope.loadStudyYearResults = function () {
    if (!angular.isObject($scope.vocationalResultsStudyYear)) {
      loadVocationalResultsByTime().$promise.then(function (vocationalResults) {
        var yearToModule = {};
        vocationalResults.forEach(function (it) {
          if (!angular.isObject(yearToModule[it.studyYear.code])) {
            yearToModule[it.studyYear.code] = { studyYear: it.studyYear, studyYearStartDate: it.studyYearStartDate, results: [] };
          }
          yearToModule[it.studyYear.code].results.push(it);
        });

        var vocationalResultsStudyYear = Object.values(yearToModule);
        vocationalResultsStudyYear.sort(function (a, b) {
          return new Date(b.studyYearStartDate).getTime() - new Date(a.studyYearStartDate).getTime();
        });
        $scope.vocationalResultsStudyYear = vocationalResultsStudyYear;
      });
    }
  };


  $scope.loadModuleThemeResults = function () {
    if (!angular.isObject($scope.vocationalResultsByTime)) {
      $scope.vocationalResultsPassing = loadVocationalResultsByTime();
    }
  };

  function loadVocationalResultsByTime() {
    return QueryUtils.endpoint('/students/' + $scope.studentId + '/vocationalResultsByTime/').query(function (vocationalResults) {
      entryMapper.objectmapper(vocationalResults);
      gradeMapper.objectmapper(vocationalResults);
      return vocationalResults;
    });
  }

  $scope.loadJournalsAndProtocols = function () {
    QueryUtils.endpoint('/students/' + $scope.studentId + '/vocationalConnectedEntities/').query(function (result) {
      $scope.studentConnectedEntities = result;
    });
  };

  $scope.displayTeachers = function (teachers) {
    if (angular.isArray(teachers)) {
      return teachers.map(function (teacher) { return teacher.nameEt; }).sort().join(', ');
    }
  };

  $scope.positiveGrade = function (grade) {
    return VocationalGradeUtil.isPositive(grade);//(angular.isNumber(grade) && grade > 2) || (angular.isString(grade) && parseInt(grade, 10) > 2);
  };

  function setModuleSubmittedCreditsAndBacklogs(module) {
    var moduleResult = $scope.moduleResultById[module.curriculumModule.id];
    module.themes.forEach(function (theme) {
      setThemeIsOk(moduleResult, theme);
    });
    module.otherCurriculumVersionModuleThemes.forEach(function (theme) {
      setThemeIsOk(moduleResult, theme);
    });
    module.moduleBacklog = (moduleResult ? moduleResult.totalSubmitted : 0) - module.curriculumModule.credits;
  }

  function setThemeIsOk(moduleResult, theme) {
    var themeResult = angular.isDefined(moduleResult) ? moduleResult.themeResultById[theme.id] : undefined;
    if (isThemeGraded(theme, themeResult)) {
      var outcomeResults = angular.isDefined(moduleResult) ? moduleResult.outcomeResultById : undefined;
      theme.isOk = isThemeOk(theme, themeResult, outcomeResults);
    }

    if (theme.isOk) {
      moduleResult.totalSubmitted += theme.credits;
    }
  }

  // 'isOk' column value should not be shown if theme has no assessment, is not graded by outcomes
  // and doesn't already have a grade
  function isThemeGraded(theme, themeResult) {
    return angular.isDefined(theme) && (angular.isDefined(themeResult) || theme.assessment !== null || theme.moduleOutcomes);
  }

  function isThemeOk(theme, themeResult, outcomeResults) {
    if (!theme.moduleOutcomes) {
      return themeHasPositiveGrade(themeResult);
    }
    return themeHasPositiveGrade(themeResult) || (angular.isDefined(outcomeResults) &&
      allOutcomesWithPositiveGrades(theme, outcomeResults));
  }

  function themeHasPositiveGrade(themeResult) {
    return angular.isDefined(themeResult) && angular.isObject(themeResult.grade) &&
      VocationalGradeUtil.isPositive(themeResult.grade.code);
  }

  function allOutcomesWithPositiveGrades(theme, outcomeResults) {
    var allResultsPositive = true;
    for (var i = 0; i < theme.curriculumModuleOutcomes.length; i++) {
      var themeOutcomeResult = outcomeResults[theme.curriculumModuleOutcomes[i].id];
      if (!themeOutcomeResult || !VocationalGradeUtil.isPositive(themeOutcomeResult.grade.code)) {
        return false;
      }
    }
    return allResultsPositive;
  }

  function loadCurrentNavItemData() {
    if ($scope.resultsCurrentNavItem === 'student.curriculumFulfillment') {
      $q.all(moduleMapper.promises.concat(gradingSchema.promises)).then(function () {
        loadVocationalResults();
      });
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfStudyYear') {
      $q.all(entryMapper.promises.concat(gradingSchema.promises)).then(function () {
        $scope.loadStudyYearResults();
      });
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfPassing') {
      $q.all(entryMapper.promises.concat(gradingSchema.promises)).then(function () {
        $scope.loadModuleThemeResults();
      });
    } else if ($scope.resultsCurrentNavItem === 'student.journalsAndProtocols') {
      if (canWatchStudentConnectedEntities()) {
        $scope.loadJournalsAndProtocols();
      } else {
        $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
      }
    }
  }

  function canWatchStudentConnectedEntities() {
    if ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) {
      return true;
    } else if ($scope.auth.isTeacher() && $scope.student.studentGroup && $scope.auth.teacherGroupIds.length > 0) {
      return $scope.auth.teacherGroupIds.indexOf($scope.student.studentGroup.id) !== -1;
    }
  }

}]).controller('StudentViewResultsHigherController', ['$q', '$rootScope', '$route', '$scope', 'GRADING_SCHEMA_TYPE', 'USER_ROLES', 'AuthService', 'Classifier', 'GradingSchema', 'HigherGradeUtil', 'StudentUtil', 'QueryUtils', 'dialogService', 'message',
  function ($q, $rootScope, $route, $scope, GRADING_SCHEMA_TYPE, USER_ROLES, AuthService, Classifier, GradingSchema, HigherGradeUtil, StudentUtil, QueryUtils, dialogService, message) {
    $scope.studentId = ($scope.auth.isStudent() || $scope.auth.isParent()) ? $scope.auth.student : $route.current.params.id;
    $scope.auth = $route.current.locals.auth;
    $scope.gradeUtil = HigherGradeUtil;
    var gradeMapper;

    var gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.HIGHER);
    $q.all(gradingSchema.promises).then(function () {
      gradeMapper = gradingSchema.gradeMapper(gradingSchema.gradeSelection(), ['grade']);
    });

    var studentIsActive = StudentUtil.isActive($scope.student.status);
    $scope.canChangeStudentModules = ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) && studentIsActive &&
      AuthService.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_OPPUR');
    $scope.canViewModulesAsDone = $scope.auth.isAdmin() && AuthService.isAuthorized('ROLE_OIGUS_V_TEEMAOIGUS_OKTAITMINE');
    $scope.canChangeModulesAsDone = $scope.auth.isAdmin() && studentIsActive &&
      AuthService.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_OKTAITMINE');

    $scope.$watch('resultsCurrentNavItem', function () {
      if ($scope.resultsCurrentNavItem === 'student.progress') {
        $q.all(gradingSchema.promises).then(function () {
          $scope.loadStudentProgress();
        });
      } else if ($scope.resultsCurrentNavItem === 'student.changeModules') {
        if (StudentUtil.isActive($scope.student.status)) {
          $q.all(gradingSchema.promises).then(function () {
            $scope.loadChangeableModules();
          });
        } else {
          $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
        }
      }
    });

    function loadHigherResults(expandedModules) {
      QueryUtils.endpoint('/students/' + $scope.studentId + '/higherResults').get().$promise.then(function (response) {
        var allResults = [];
        var replacedSubjectsByModule = {};

        response.modules.forEach(function (module) {
          module.markedComplete = module.studentCurriculumCompletionHigherModule !== null;
          module.studyCredits = module.compulsoryStudyCredits + module.optionalStudyCredits;
          module.creditsSubmitted = module.mandatoryCreditsSubmitted + module.optionalCreditsSubmitted;
          module.difference = (module.mandatoryDifference < 0 ? module.mandatoryDifference : 0) +
            (module.optionalDifference < 0 ? module.optionalDifference : 0);
          module.grades.forEach(function (grade) {
            gradeMapper.objectmapper(grade);
          });
          if (module.grades.length > 0) {
            gradeMapper.objectmapper(module.lastGrade);
            var moduleResult = {
              module: {id: module.id, nameEt: module.nameEt, nameEn: module.nameEn, credits: module.totalCredits},
              isModule: true,
              grades: module.grades,
              lastGrade: module.lastGrade
            };
            allResults.push(moduleResult);
          }
        });
        response.subjectResults.forEach(function (result) {
          result.grades.forEach(function (grade) {
            gradeMapper.objectmapper(grade);
          });
          allResults.push(result);

          if (result.replacedSubjects.length > 0) {
            if (!angular.isObject(replacedSubjectsByModule[result.higherModule.id])) {
              replacedSubjectsByModule[result.higherModule.id] = [];
            }
            result.replacedSubjects.forEach(function (replacedSubject) {
              replacedSubjectsByModule[result.higherModule.id].push(replacedSubject.id);
            });
          }
        });
        response.extraCurriculumModuleResults.forEach(function (module) {
          module.grades.forEach(function (grade) {
            gradeMapper.objectmapper(grade);
          });
          if (module.grades.length > 0) {
            gradeMapper.objectmapper(module.lastGrade);
            var moduleResult = {
              module: {
                id: module.id,
                nameEt: module.nameEt,
                nameEn: module.nameEn,
                curriculumVersion: module.curriculumVersionObject,
                credits: module.totalCredits
              },
              isModule: true,
              grades: module.grades,
              lastGrade: module.lastGrade
            };
            allResults.push(moduleResult);
          }
        });
        $scope.higherResults = response;
        $scope.higherResults.allResults = allResults;
        $scope.student.higherResults = $scope.higherResults;
        $scope.student.higherResults.modules.sort(moduleComparator);
        $scope.replacedSubjectsByModule = replacedSubjectsByModule;
        changeModulesExpandedStatus(expandedModules);
      });
    }

    if (!angular.isDefined($scope.higherResults)) {
      loadHigherResults();
    }

    $scope.changeModulesExpandedStatus = function () {
      changeModulesExpandedStatus();
    };

    $scope.changeModuleCompletion = function (module) {
      var expandedModules = getExpandedModules();
      if (module.studentCurriculumCompletionHigherModule === null) {
        dialogService.confirmDialog({ prompt: 'student.result.higher.markModuleCompleteConfirm' }, function () {
          QueryUtils.endpoint('/students/' + $scope.studentId + '/markModuleComplete/' + module.id).put({}, function () {
            loadHigherResults(expandedModules);
          });
        }, function () {
          module.markedComplete = false;
        });
      } else {
        dialogService.confirmDialog({ prompt: 'student.result.higher.removeModuleCompletionConfirm' }, function () {
          QueryUtils.endpoint('/students/' + $scope.studentId + '/removeModuleCompletion/' +
            module.studentCurriculumCompletionHigherModule).delete({}, function () {
              loadHigherResults(expandedModules);
          });
        }, function () {
          module.markedComplete = true;
        });
      }
    };

    function changeModulesExpandedStatus(expandedModules) {
      if (angular.isArray(expandedModules)) {
        $scope.higherResults.modules.forEach(function (module) {
          module.collapsableOpen = expandedModules.indexOf(module.id) !== -1;
        });
      } else {
        $scope.higherResults.modules.forEach(function (module) {
          module.collapsableOpen = $scope.formState.openAllCollapsables;
        });
      }
      $scope.formState.extraCurriculumModuleResultsCollapsableOpen = $scope.formState.openAllCollapsables;
    }

    function getExpandedModules() {
      var expandedModules = [];
      $scope.higherResults.modules.forEach(function (module) {
        if (module.collapsableOpen) {
          expandedModules.push(module.id);
        }
      });
      return expandedModules;
    }

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
        return higherModule.id === subjectResult.higherModule.id && !nongradedReplacedSubject(higherModule, subjectResult);
      };
    };

    function nongradedReplacedSubject(higherModule, subjectResult) {
      return subjectResult.grades.length === 0 && angular.isObject($scope.replacedSubjectsByModule[higherModule.id]) &&
        $scope.replacedSubjectsByModule[higherModule.id].indexOf(subjectResult.subject.id) !== -1;
    }

    $scope.filterResultsByStudyPeriod = function (studyPeriod) {
      return function (result) {
        return result.lastGrade && studyPeriod.id === result.lastGrade.studyPeriod;
      };
    };

    // every other type comes before those
    var moduleOrder = ['KORGMOODUL_V',   // Free choice
                       'KORGMOODUL_F',   // Final exam
                       'KORGMOODUL_L'];    // Final thesis

    function moduleComparator(v1, v2) {
      return moduleOrderComparator(v1, v2) || moduleOrder.indexOf(v1.type) - moduleOrder.indexOf(v2.type) ||
        $rootScope.currentLanguageNameField(v1).localeCompare($rootScope.currentLanguageNameField(v2));
    }

    function moduleOrderComparator(m1, m2) {
      return (m1.orderNr === null) - (m2.orderNr === null) || + (m1.orderNr > m2.orderNr) || - (m1.orderNr < m2.orderNr);
    }

    $scope.loadChangeableModules = function () {
      QueryUtils.endpoint('/students/' + $scope.student.id + '/higherCurriculumModules/').query(function (curriculumModules) {
        $scope.higherCurriculumModules = curriculumModules;
        QueryUtils.endpoint('/students/' + $scope.student.id + '/higherChangeableModules').query(function (changeableModules) {
          setChangeableModules(changeableModules);
        });
      });
    };

    function setChangeableModules(changeableModules) {
      changeableModules.forEach(function (changeableModule) {
        gradeMapper.objectmapper(changeableModule);
        changeableModule.oldCurriculumVersionModuleId = changeableModule.curriculumVersionModuleId;
        changeableModule.oldIsOptional = changeableModule.isOptional;
        $scope.higherCurriculumModules.forEach(function (curriculumModule) {
          if (curriculumModule.id === changeableModule.curriculumVersionModuleId) {
            changeableModule.curriculumVersionModule = curriculumModule;
          }
        });
      });
      $scope.changeableModules = changeableModules;
    }

    $scope.saveChangedModules = function () {
      var expandedModules = getExpandedModules();
      QueryUtils.endpoint('/students/' + $scope.student.id + '/changeHigherCurriculumModules/').post(
        {modules: $scope.changeableModules}).$promise.then(function(changeableModules) {
        message.updateSuccess();
        setChangeableModules(changeableModules);
        loadHigherResults(expandedModules);
      });
    };

    $scope.progressStatus = {
      positive: 'green-600',
      negative: 'red-500',
      declared: 'light-blue-100',
      prerequisitesCompleted: 'yellow-400',
      notTaughtThisSemester: 'grey-600',
      taughtThisSemester: 'grey-50'
    };

    $scope.loadStudentProgress = function () {
      QueryUtils.endpoint('/students/' + $scope.student.id + '/progress').get(function (result) {
        result.years.forEach(function (year) {
          year.periods.forEach(function (period) {
            period.subjects.forEach(function (subject) {
              subjectMapper(subject);
            });
          });
        });
        if (result.extraCurriculumResults) {
          result.extraCurriculumResults.subjects.forEach(function (subject) {
            subjectMapper(subject);
          });
        }
        $scope.years = result.years;
        $scope.extraCurriculumResults = result.extraCurriculumResults;
      });
    };

    function subjectMapper(subject) {
      gradeMapper.objectmapper(subject);

      if (subject.protocolId !== null) {
        if ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) {
          if (subject.isFinalProtocol && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL)) {
            subject.resultLink = '/#/finalHigherProtocols/' + subject.protocolId + '/view';
          } else if (!subject.isFinalProtocol && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL)) {
            subject.resultLink = '/#/higherProtocols/' + subject.protocolId + '/view';
          }
        }
      } else if (subject.apelApplicationId !== null || subject.replacedApelApplicationId !== null) {
        if (($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher() || $scope.auth.isStudent()) &&
            AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_VOTA)) {
          if (subject.apelApplicationId !== null) {
            subject.resultLink = '/#/apelApplication/' + subject.apelApplicationId + '/view';
          } else {
            subject.replacedApplicationLink = '/#/apelApplication/' + subject.replacedApelApplicationId + '/view';
          }
        }
      }

      if (angular.isDefined(subject.replacedModuleOptional)) {
        subject.isOptionalChanged = subject.replacedModuleOptional !== subject.optional;
      }

      subject.color = progressColor(subject);
    }

    $scope.containsMandatorySubjects = function (subjects) {
      return subjects.some(function (s) {
        return !s.optional;
      });
    };

    $scope.containsOptionalSubjects = function (subjects) {
      return subjects.some(function (s) {
        return s.optional;
      });
    };

    function progressColor (subject) {
      if (angular.isDefined(subject.grade) && subject.grade !== null) {
        return HigherGradeUtil.isPositive(subject.grade.code) ? $scope.progressStatus.positive : $scope.progressStatus.negative;
      } else if (subject.replacedApelApplicationId !== null) {
        return  $scope.progressStatus.positive;
      } else if (subject.declared) {
        return $scope.progressStatus.declared;
      } else if (!subject.prerequisitesCompleted) {
        return $scope.progressStatus.prerequisitesCompleted;
      } else if (!subject.taughtThisSemester) {
        return $scope.progressStatus.notTaughtThisSemester;
      }
      return $scope.progressStatus.taughtThisSemester;
    }

  }]).controller('StudentViewDocumentsController', ['$route', '$scope', '$q', 'USER_ROLES', 'AuthService', 'Classifier', 'CertificateType', 'CertificateUtil', 'QueryUtils',
    function ($route, $scope, $q, USER_ROLES, AuthService, Classifier, CertificateType, CertificateUtil, QueryUtils) {
    $scope.studentId = ($route.current.locals.auth.isStudent() || $route.current.locals.auth.isParent() ? $route.current.locals.auth.student : $route.current.params.id);
    $scope.currentNavItem = 'student.documents';
    $scope.auth = $route.current.locals.auth;

    $scope.applicationsCriteria = { size: 5, page: 1, order: '-inserted', studentId: $scope.studentId };
    $scope.applications = {};
    var applicationsMapper = Classifier.valuemapper({ type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS' });
    $scope.afterApplicationsLoad = function (result) {
      $scope.applications.content = applicationsMapper.objectmapper(result.content);
      $scope.applications.totalElements = result.totalElements;
    };

    QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
      $scope.student = student;
    });

    $scope.loadApplications = function () {
      var query = QueryUtils.getQueryParams($scope.applicationsCriteria);
      $scope.applications.$promise = QueryUtils.endpoint('/students/:studentId/applications').search(query, $scope.afterApplicationsLoad);
    };

    if($scope.auth.isStudent()) {
      Classifier.queryForDropdown({ mainClassCode: 'AVALDUS_LIIK' }).$promise.then(function (responses) {
        if ($scope.auth.isGuestStudent()) {
          $scope.applicationTypes = responses.filter(function (classifier) {
            return classifier.code === 'AVALDUS_LIIK_MUU';
          });
        } else {
          $scope.applicationTypes = responses;
        }
      });
      $scope.certificateTypes = Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK',
      filterValues: ($scope.auth.isGuestStudent() ? CertificateUtil.getGuestStudentForbiddenTypes() : [CertificateType.TOEND_LIIK_MUU])});
    }

    $scope.directivesCriteria = { size: 5, page: 1, order: 'confirm_date, headline', studentId: $scope.studentId };
    $scope.directives = {};
    var directivesMapper = Classifier.valuemapper({ type: 'KASKKIRI', status: 'KASKKIRI_STAATUS' });
    $scope.afterDirectivesLoad = function (result) {
      $scope.directives.content = directivesMapper.objectmapper(result.content);
      $scope.directives.totalElements = result.totalElements;
    };

    $scope.loadDirectives = function () {
      var query = QueryUtils.getQueryParams($scope.directivesCriteria);
      $scope.directives.$promise = QueryUtils.endpoint('/students/:studentId/directives').search(query, $scope.afterDirectivesLoad);
    };

    // apel applications

    $scope.apelApplicationsCriteria = { size: 5, page: 1, studentId: $scope.studentId };
    $scope.apelApplications = {};
    var apelApplicationsMapper = Classifier.valuemapper({ status: 'VOTA_STAATUS' });
    $scope.afterApelApplicationsLoad = function (result) {
      $scope.apelApplications.content = apelApplicationsMapper.objectmapper(result.content);
      $scope.apelApplications.totalElements = result.totalElements;
    };

    $scope.loadApelApplications = function () {
      var query = QueryUtils.getQueryParams($scope.apelApplicationsCriteria);
      $scope.apelApplications.$promise = QueryUtils.endpoint('/students/:studentId/apelApplications').search(query, $scope.afterApelApplicationsLoad);
    };

    // practice contracts

    $scope.practiceContractsCriteria = { size: 5, page: 1, order: 'contract_nr', studentId: $scope.studentId };
    $scope.practiceContracts = {};
    var practiceContractsMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
    $scope.afterPracticeContractsLoad = function (result) {
      $scope.practiceContracts.content = practiceContractsMapper.objectmapper(result.content);
      $scope.practiceContracts.totalElements = result.totalElements;
    };

    $scope.loadPracticeContracts = function () {
      var query = QueryUtils.getQueryParams($scope.practiceContractsCriteria);
      $scope.practiceContracts.$promise = QueryUtils.endpoint('/students/:studentId/practicecontracts').search(query, $scope.afterPracticeContractsLoad);
    };

    $scope.certificatesCriteria = { size: 5, page: 1, order: 'type.' + $scope.currentLanguageNameField() + ', inserted desc', student: $scope.studentId};
    $scope.certificates = {};
    var certificatesMapper = Classifier.valuemapper({type: 'TOEND_LIIK', status: 'TOEND_STAATUS'});
    function afterCertificatesLoad(result) {
      $scope.certificates.totalElements = result.totalElements;
      $scope.certificates.content = certificatesMapper.objectmapper(result.content);
    }

    if(!$scope.auth.isTeacher()) {
      $scope.loadCertificates = function() {
        var query = QueryUtils.getQueryParams($scope.certificatesCriteria);
        $scope.directives.$promise = QueryUtils.endpoint('/certificate').search(query, afterCertificatesLoad);
      };
      $scope.loadCertificates();
    }

    // initially load whole form
    QueryUtils.endpoint('/students/:studentId/documents').search({ studentId: $scope.studentId }, function (result) {

      $q.all(applicationsMapper.promises).then(function () {
        $scope.afterApplicationsLoad(result.applications);
      });
      $q.all(directivesMapper.promises).then(function () {
        $scope.afterDirectivesLoad(result.directives);
      });
      $q.all(apelApplicationsMapper.promises).then(function () {
        $scope.afterApelApplicationsLoad(result.apelApplications);
      });
      $q.all(practiceContractsMapper.promises).then(function () {
        $scope.afterPracticeContractsLoad(result.practiceContracts);
      });
      if($scope.auth.isStudent()) {
        $scope.applicationTypesApplicable = result.applicationTypesApplicable;

        $scope.certificateTypes.$promise.then(function() {
          var forbiddenTypes = CertificateUtil.getForbiddenTypes(result.student.status);
          $scope.certificateTypes = $scope.certificateTypes.filter(function(it) {
            return forbiddenTypes.indexOf(it.code) === -1;
          });
        });
      }
    });

    $scope.canChangeApplication = function (application) {
      return AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_AVALDUS) &&
        $scope.student && $scope.student.userCanEditStudent && (application.status.code === 'AVALDUS_STAATUS_KOOST' ||
        (application.status.code === 'AVALDUS_STAATUS_YLEVAAT' && ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()))) &&
        !($scope.auth.isAdmin() && application.type.code === 'AVALDUS_LIIK_TUGI' && !AuthService.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_TUGITEENUS') && !application.isConnectedByCommittee) &&
        ($scope.auth.isAdmin() || application.type.code === 'AVALDUS_LIIK_TUGI');
    };

    $scope.canViewContract = function () {
      return AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_LEPING) &&
        (!($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) || (($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher())));
    };

    $scope.canEditContract = function (contract) {
      return AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_LEPING) &&
        $scope.student !== undefined && $scope.student.userCanEditStudent &&
        (($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher()) && contract.status.code === 'LEPING_STAATUS_S');
    };

  }]).controller('StudentViewTimetableController', ['$route', '$scope', 'GeneralTimetableUtils', 'QueryUtils', function ($route, $scope, GeneralTimetableUtils, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    $scope.generalTimetableUtils = new GeneralTimetableUtils();
    $scope.studentId = ($route.current.locals.auth.isStudent() ? $route.current.locals.auth.student : $route.current.params.id);
    QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
      $scope.student = student;
    });
    $scope.typeId = $scope.studentId;
    $scope.currentNavItem = 'student.timetable';
    $scope.schoolId = $scope.auth.school.id;
    $scope.criteria = {studentId: $scope.studentId};

    QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
      $scope.studyYears = result;
      $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
    });

    $scope.schoolId = $scope.auth.school.id;
    QueryUtils.endpoint('/timetables/timetableStudyYears/:schoolId').query({schoolId: $scope.schoolId}).$promise.then(function (result) {
      $scope.studyYears = result;
      $scope.criteria.studyYearId = $scope.generalTimetableUtils.getStudyYearId($scope.schoolId, result);
    });

    $scope.$watch('criteria.studyYearId', function () {
      if (angular.isDefined($scope.criteria.studyYearId) && $scope.criteria.studyYearId !== null) {
        $scope.showStudentWeek();
      }
    });

    $scope.changeStudyYear = function (studyYear) {
      $scope.generalTimetableUtils.changeState({ studyYearId: studyYear.id }, $scope.schoolId);
      $scope.criteria.studyYearId = studyYear.id;
    };

    $scope.showStudentWeek = function () {
      var newState = {};
      newState.studentId = $scope.criteria.studentId;
      $scope.generalTimetableUtils.changeState(newState, $scope.schoolId);
      $scope.$broadcast("updateWeekTimetable", { criteria: $scope.criteria });
    };

  }]).controller('StudentEditController', ['$location', '$route', '$scope', 'message', 'oisFileService', 'QueryUtils', 'DataUtils', 'dialogService', 'hoisDateFilter', '$rootScope', 'Classifier', 'ArrayUtils',
    function ($location, $route, $scope, message, oisFileService, QueryUtils, DataUtils, dialogService, hoisDateFilter, $rootScope, Classifier, ArrayUtils) {
    var id = $route.current.params.id;
    var cannotEditStatuses = Object.freeze({
      OPPURSTAATUS_K: "OPPURSTAATUS_K",
      OPPURSTAATUS_L: "OPPURSTAATUS_L"
    });

    function afterLoad() {
      if ($scope.student.status in cannotEditStatuses) {
        message.error("main.messages.error.nopermission");
        $rootScope.back("#/students/" + $scope.student.id + "/main");
      }
      if($scope.student.photo) {
        $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
      } else {
        $scope.student.imageUrl = '?' + new Date().getTime();
      }
      DataUtils.convertStringToDates($scope.student, ['studyStart']);
      $scope.errorParams = {
        studyStart: hoisDateFilter($scope.student.studyStart)
      };
      $scope.student.foreignLanguageTypes = {};
      Classifier.queryForDropdown({ mainClassCode: 'VOORKEEL_TYYP', order: 'code' }, function (result) {
        var languagesList = [];
        result.forEach(function (language) {
          var languageSaved = false;
          var savedObject = {};
          $scope.student.studentLanguages.forEach(function (studentLanguage) {
            if (studentLanguage.foreignLangTypeCode === language.code) {
              languageSaved = true;
              savedObject = studentLanguage;
            }
          });
          if (languageSaved) {
            savedObject.languageFilter = [];
            languagesList.push(angular.copy(savedObject));
          } else {
            languagesList.push({foreignLangTypeCode: language.code, languageFilter: []});
          }
        });
        $scope.student.foreignLanguageTypes = ArrayUtils.partSplit(languagesList, 2);
      });
    }


    $scope.addValueToOtherFilters = function(self, oldValue) {
      $scope.student.foreignLanguageTypes.forEach(function(languagePairs) {
        languagePairs.forEach(function (language) {
          if (language.foreignLangTypeCode !== self.foreignLangTypeCode) {
            if (oldValue !== null && oldValue !== undefined) {
              ArrayUtils.remove(language.languageFilter, oldValue);
            }
            language.languageFilter.push(self.foreignLangCode);
          }
        });
      });
    };

    function isAddressFilled(person) {
      // TEMPORARY REMOVED
      return true;
      if (person.residenceCountry === 'RIIK_EST') {
        return person.address && person.addressAds && person.addressAdsOid;
      }
      return person.address;
    }

    QueryUtils.endpoint('/students').get({ id: id }).$promise.then(function (student) {
      $scope.student = student;
      afterLoad();
    });

    $scope.specialities = QueryUtils.endpoint("/students/" + id + "/specialities").query();

    function withPhoto(afterPhotoLoad) {
      if ($scope.studentEditForm.photoFiles && $scope.studentEditForm.photoFiles.$modelValue[0]) {
        $scope.student.deleteCurrentPhoto = null;
        $scope.student.photo = oisFileService.getFromLfFile($scope.studentEditForm.photoFiles.$modelValue[0], afterPhotoLoad);
      } else if ($scope.studentEditForm.deleteCurrentPhoto && $scope.studentEditForm.deleteCurrentPhoto.$modelValue) {
        $scope.student.photo = null;
        afterPhotoLoad();
      } else {
        afterPhotoLoad();
      }
    }

    $scope.setAcadStudyDefault = function() {
      if ($scope.student.isAcadStudyAllowed === null || $scope.student.isAcadStudyAllowed === undefined) {
        $scope.student.isAcadStudyAllowed = true;
      }
    };

    function flatMapLanguages() {
      $scope.student.studentLanguages = [].concat.apply([], $scope.student.foreignLanguageTypes);
      $scope.student.studentLanguages = $scope.student.studentLanguages.filter(function (language) {
        return language.foreignLangCode !== undefined && language.foreignLangCode !== null && language.foreignLangCode !== "";
      });
    }

    $scope.update = function () {
      flatMapLanguages();
      $scope.studentEditForm.$setSubmitted();
      if (!isAddressFilled($scope.student.person)) {
        message.error('student.error.addressRequired');
        return;
      }
      if (!$scope.studentEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      dialogService.confirmDialog({prompt: 'main.messages.confirmSave'}, function() {
        withPhoto(function() {
          $scope.student.$update().then(function () {
            message.updateSuccess();
            $location.path('/students/' + id + '/main');
          });
        });
      });
    };
  }]).controller('StudentAddInfoController', ['$location', '$route', '$scope', 'message', 'oisFileService', 'QueryUtils', 'DataUtils', 'dialogService', 'hoisDateFilter', '$rootScope',
  function ($location, $route, $scope, message, oisFileService, QueryUtils, DataUtils, dialogService, hoisDateFilter, $rootScope) {
    var id = $route.current.params.id;
    $scope.currentNavItem = 'student.addInfo';
    $scope.auth = $route.current.locals.auth;
    $scope.studentId = ($route.current.locals.auth.isStudent() ? $route.current.locals.auth.student : $route.current.params.id);
    QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
      $scope.student = student;
      if (!$scope.student.userCanViewStudentAddInfo) {
        message.error("main.messages.error.nopermission");
        $rootScope.back("#/students/" + $scope.student.id + "/main");
      }
    });

    $scope.update = function() {
      $scope.studentEditAddInfoForm.$setSubmitted();
      if (!$scope.studentEditAddInfoForm.$valid) {
          message.error('main.messages.form-has-errors');
          return false;
      }
      QueryUtils.endpoint('/students/'+id+'/addinfo').save($scope.student, function () {
        message.updateSuccess();
      });
    };
}]).controller('StudentAbsencesController', ['$location', '$mdDialog', '$route', '$scope', 'DataUtils', 'QueryUtils', 'dialogService', 'message',
    function ($location, $mdDialog, $route, $scope, DataUtils, QueryUtils, dialogService, message) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      $scope.student = QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true });
      if ($scope.auth.school.notAbsence) {
        message.error('main.messages.error.nopermission');
        return $location.path('');
      }
      $scope.currentNavItem = 'student.absences';

      $scope.absencesCriteria = { size: 20, page: 1, order: 'validFrom, validThru' };

      $scope.loadAbsences = function () {
        var query = QueryUtils.getQueryParams($scope.absencesCriteria);
        QueryUtils.endpoint('/students/' + $scope.studentId + '/absences').search(query, $scope.afterLoadData);
      };

      $scope.afterLoadData = function(resultData) {
        $scope.tabledata = resultData;
      };

      $scope.editAbsence = function (absence) {
        var AbsenceEndpoint = QueryUtils.endpoint('/students/' + $scope.studentId + '/absences');
        var loadAbsences = $scope.loadAbsences;
        absence = angular.copy(absence, {});
        absence.studentName = $scope.tabledata.studentName;
        absence.studentGroup = $scope.tabledata.studentGroup;

        $mdDialog.show({
          controller: function ($scope) {
            $scope.record = new AbsenceEndpoint(absence || {});
            DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);

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
          clickOutsideToClose: false
        });
      };

      $scope.loadAbsences();
    }]).controller('StudentViewRemarksController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils',
      function ($q, $route, $scope, Classifier, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (response) {
        $scope.student = response;
      });
      $scope.currentNavItem = 'student.remarks';

      var clMapper = Classifier.valuemapper({ reason: 'MARKUS' });
      $scope.remarksCriteria = { size: 20, page: 1, order: '-remark_time' };

      $scope.loadRemarks = function () {
        var query = QueryUtils.getQueryParams($scope.remarksCriteria);
        QueryUtils.endpoint('/remarks/student/' + $scope.studentId).search(query, $scope.afterLoadData);
      };

      $scope.afterLoadData = function(resultData) {
        $scope.tabledata = {};
        $scope.tabledata.content = clMapper.objectmapper(resultData.content);
        $scope.tabledata.totalElements = resultData.totalElements;
      };

      $q.all(clMapper.promises).then($scope.loadRemarks);

    }]).controller('StudentViewRRController', ['$route', '$scope', 'QueryUtils',
      function ($route, $scope, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (response) {
        $scope.student = response;
      });
      $scope.currentNavItem = 'student.rr';

      QueryUtils.createQueryForm($scope, '/logs/rr/changelogs', {order: '-wrcl.inserted', student: $scope.studentId}, undefined, undefined, true);

      $scope.loadData();
    }]).controller('StudentViewSupportServiceController', ['$route', '$scope', 'QueryUtils', '$timeout', 'dialogService',
      'oisFileService', 'message', 'config', '$httpParamSerializer',
      function ($route, $scope, QueryUtils, $timeout, dialogService, oisFileService, message, config, $httpParamSerializer) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      QueryUtils.endpoint('/students').get({ id: $scope.studentId, withHTM: true }).$promise.then(function (student) {
        $scope.student = student;
      });
      $scope.currentNavItem = 'student.supportService';

      $scope.isArray = angular.isArray;
      $scope.getUrl = oisFileService.getUrl;

      $scope.innoveHistory = innoveHistory;

      $scope.editService = function(service) {
        dialogService.showDialog("student/templates/edit.service.dialog.html", function (dialogScope) {
          if (!service) {
            dialogScope.service = {entryDate: new Date(), entrySubmitter: $scope.auth.fullname, validity: 'TUGIKEHTIV_K'};
          } else {
            dialogScope.service = angular.copy(service, {});
          }

          dialogScope.getUrl = $scope.getUrl;

          dialogScope.update = function () {
            QueryUtils.endpoint("/students/" + $scope.studentId + "/supportservice")
            .update(dialogScope.service).$promise.then(function () {
              message.info("main.messages.update.success");
              $scope.loadSupportServices();
            });
          };

          dialogScope.save = function () {
            QueryUtils.endpoint("/students/" + $scope.studentId + "/supportservice").save(dialogScope.service, function () {
              message.info("main.messages.create.success");
              $scope.loadSupportServices();
            });
          };

          dialogScope.delete = function () {
            dialogService.confirmDialog({ prompt: 'student.supportService.operation.deleteService.message' }, function () {
              QueryUtils.endpoint("/students/" + $scope.studentId + "/supportservice").delete(dialogScope.service, function () {
                message.info("main.messages.delete.success");
                dialogScope.cancel();
                $scope.loadSupportServices();
              });
            });
          };

          dialogScope.close = function () {
            dialogScope.dialogForm.$setSubmitted();
            if (dialogScope.dialogForm.$dirty) {
              dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
                dialogScope.cancel();
              });
            } else {
              dialogScope.cancel();
            }
          };
        }, function (submittedDialogScope) {
          var data = submittedDialogScope.data;
          if (data && data.file[0]) {
            oisFileService.getFromLfFile(data.file[0], function (file) {
              submittedDialogScope.service.file = file;
              if (submittedDialogScope.service.id) {
                submittedDialogScope.update();
              } else {
                submittedDialogScope.save();
              }
            });
          } else {
            if (submittedDialogScope.service.id) {
              submittedDialogScope.update();
            } else {
              submittedDialogScope.save();
            }
          }
        });
      };

      $scope.printServices = function () {
        dialogService.showDialog("student/templates/print.services.dialog.html", function (dialogScope) {
          dialogScope.pdfUrl = function() {
            return config.apiUrl + "/students/" + $scope.studentId + "/supportservices/print.pdf?" + $httpParamSerializer({from: dialogScope.from, thru: dialogScope.thru});
          };
        });
      };

      $scope.supportServicesCriteria = { size: 10, page: 1, order: "-entry_date"};
      $scope.supportServices = {};

      function afterSupportServicesLoad(result) {
        $scope.supportServices.content = result.content;
        $scope.supportServices.totalElements = result.totalElements;
        $scope.supportServices.content.forEach(function (service) {
          if (service.isArtificial) {
            service.content = service.content.split(";");
          }
        });
      }

      $scope.loadSupportServices = function() {
        var query = angular.extend({}, QueryUtils.getQueryParams($scope.supportServicesCriteria), { id: $scope.studentId });
        $scope.supportServices.$promise = QueryUtils.endpoint('/students/:id/supportservices').search(query, afterSupportServicesLoad);
      };

      $scope.loadSupportServices();

      function innoveHistory() {
        QueryUtils.loadingWheel($scope, true);
        QueryUtils.endpoint('/students/innoveHistory').get({id: $scope.studentId}, function (result) {
          if (result.error) {
            message.error(result.message);
          } else {
            message.info('main.messages.dataUpdated');
          }
          QueryUtils.loadingWheel($scope, false);
          $scope.loadSupportServices();
        }, function () {
          QueryUtils.loadingWheel($scope, false);
        });
      }
    }]).controller('StudentSearchController', ['$q', '$route', '$scope', '$translate', 'Classifier', 'PollingService', 'QueryUtils', 'busyHandler', 'dialogService', 'message',
    function ($q, $route, $scope, $translate, Classifier, PollingService, QueryUtils, busyHandler, dialogService, message) {
      $scope.auth = $route.current.locals.auth;

      var clMapping = $route.current.locals.clMapping;
      var clMapper = clMapping ? Classifier.valuemapper(clMapping) : undefined;
      QueryUtils.createQueryForm($scope, $route.current.locals.url, {
        order: 'person.lastname,person.firstname',
        showMyStudentGroups: $scope.auth.isTeacher() && $scope.auth.teacherGroupIds.length > 0
      }, clMapper ? clMapper.objectmapper : undefined);

      if(clMapper) {
        $q.all(clMapper.promises).then($scope.loadData);
      } else {
        $scope.loadData();
      }

      $scope.directiveControllers = [];
      var clearCriteria = $scope.clearCriteria;
      $scope.clearCriteria = function () {
        clearCriteria();
        $scope.directiveControllers.forEach(function (c) {
          c.clear();
        });
      };

      $scope.requestPhotoBoxPhotos = function () {
        QueryUtils.endpoint('/students/studentsWithoutPhoto').get(function (result) {
          if (result.count > 0) {
            dialogService.confirmDialog({ prompt: 'student.photoBox.requestConfirm', studentCount: result.count }, function () {
              QueryUtils.loadingWheel($scope, true, false, $translate.instant('student.photoBox.requestInProgress'), true);
              sendRequest();
            });
          } else {
            message.error('student.photoBox.noStudentsWithoutPhotos');
          }
        });
      };

      function sendRequest() {
        PollingService.sendRequest({
          url: '/students/studentsWithoutPhotoRequest',
          pollUrl: '/students/studentsWithoutPhotoRequestStatus',
          successCallback: function (pollResult) {
            busyHandler.setProgress(100);
            QueryUtils.loadingWheel($scope, false);
            dialogService.showDialog('student/templates/photobox.result.dialog.html', function (dialogScope) {
              dialogScope.successfulRequests = [];
              dialogScope.failedRequests = [];
              pollResult.result.forEach(function (student) {
                if (student.success) {
                  dialogScope.successfulRequests.push(student);
                } else {
                  dialogScope.failedRequests.push(student);
                }
              });
            });
          },
          failCallback: function (pollResult) {
            if (pollResult) {
              message.error('student.photoBox.requestResult.failure');
            }
            QueryUtils.loadingWheel($scope, false);
          },
          updateProgress: function (pollResult) {
            if (pollResult) {
              busyHandler.setProgress(Math.round(pollResult.progress * 100));
            }
          }
        });
      }

    }]);
