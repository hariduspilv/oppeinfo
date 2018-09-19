'use strict';

angular.module('hitsaOis').controller('StudentViewMainController', ['$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'oisFileService',

  function ($mdDialog, $q, $route, $scope, dialogService, message, Classifier, QueryUtils, oisFileService) {
    var auth = $route.current.locals.auth;
    var studentId = (auth.isStudent() || auth.isParent() ? auth.student : $route.current.params.id);
    var baseUrl = '/students';

    $scope.auth = auth;
    $scope.studentId = studentId;
    $scope.currentNavItem = 'student.main';

    QueryUtils.endpoint(baseUrl).get({ id: studentId }, function (result) {
      $scope.student = result;
      if ($scope.student.photo) {
        $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
      } else {
        $scope.student.imageUrl = '?' + new Date().getTime();
      }
    });

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
  }
]).controller('StudentViewResultsController', ['$route', '$scope', '$localStorage', 'QueryUtils', function ($route, $scope, $localStorage, QueryUtils) {
  $scope.auth = $route.current.locals.auth;

  $scope.studentId = ($scope.auth.isStudent() || $scope.auth.isParent() ? $scope.auth.student : $route.current.params.id);
  QueryUtils.endpoint('/students').get({ id: $scope.studentId }).$promise.then(function (student) {
    $scope.student = student;
    canWatchStudentConnectedEntities();
  });
  $scope.currentNavItem = 'student.results';

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
    if ($scope.auth.isAdmin()) {
      $scope.canWatchStudentConnectedEntities = true;
    } else if ($scope.auth.isTeacher()) {
      QueryUtils.endpoint('/studentgroups').get({ id: $scope.student.studentGroup.id }).$promise.then(function (studentGroup) {
        if (studentGroup.teacher.id === $scope.auth.teacher) {
          $scope.canWatchStudentConnectedEntities = true;
        }
      });
    }
  }

  $scope.isStudentCurriculumFulfilled = function (student) {
    return student && angular.isNumber(student.credits) && student.credits >= student.curriculumCredits;
  };

}]).controller('StudentViewResultsVocationalController', ['$filter', '$q', '$route', '$scope', 'Classifier', 'QueryUtils', '$rootScope', 'VocationalGradeUtil', 'StudentUtil',
function ($filter, $q, $route, $scope, Classifier, QueryUtils, $rootScope, VocationalGradeUtil, StudentUtil) {
  $scope.gradeUtil = VocationalGradeUtil;
  var entryMapper = Classifier.valuemapper({ grade: 'KUTSEHINDAMINE', studyYear: 'OPPEAASTA', entryType: 'SISSEKANNE' });
  var moduleMapper = Classifier.valuemapper({ module: 'KUTSEMOODUL' });

  $scope.canChangeStudentModules = $scope.auth.isAdmin() && StudentUtil.isActive($scope.student.status) && $scope.auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_OPPUR') !== -1;

  $scope.$watch('resultsCurrentNavItem', function () {
    loadCurrentNavItemData();
  });

  $scope.loadCurriculumFulfillment = function() {
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
          if (VocationalGradeUtil.isPositive(it.grade.code)) {
            moduleResultById[it.module.id].totalSubmitted += it.credits;
          }
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
        return angular.isObject(themeResult) && angular.isObject(themeResult.grade) && angular.isString(themeResult.grade.code) && VocationalGradeUtil.isPositive(themeResult.grade.code);
      };
      $scope.vocationalResultsCurriculum = {};
      $scope.vocationalResultsCurriculum.vocationalResultsModules = $scope.vocationalResults.curriculumModules.sort(function (m1, m2) {
        return $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });
      $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules = $scope.vocationalResults.extraCurriculaModules.sort(function (m1, m2) {
        return $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });
      var otherSchoolExtraCurriculaModuleResults = $filter('filter')($scope.vocationalResults.results, {'isOtherSchool': true});
      $scope.vocationalResultsCurriculum.otherSchoolExtraCurriculaModuleResults = otherSchoolExtraCurriculaModuleResults.sort(function (r1, r2) {
        return $rootScope.currentLanguageNameField(r1.module).localeCompare($rootScope.currentLanguageNameField(r2.module));
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

  function loadVocationalResults() {
    if (!angular.isObject($scope.vocationalResults)) {
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint('/students/' + $scope.student.id + '/vocationalResults').get(function (vocationalResults) {
        vocationalResults.results = entryMapper.objectmapper(vocationalResults.results);
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
  }


  $scope.loadStudyYearResults = function () {
    if (!angular.isObject($scope.vocationalResultsStudyYear)) {
      loadVocationalResultsByTime().$promise.then(function (vocationalResults) {
        var yearToModule = {};
        vocationalResults.forEach(function (it) {
          if (!angular.isObject(yearToModule[it.studyYear.code])) {
            yearToModule[it.studyYear.code] = { studyYear: it.studyYear, studyYearStartDate: it.studyYearStartDate, /*kkh: 0, credits: 0, */ results: [] };
          }
          yearToModule[it.studyYear.code].results.push(it);
        });

        var vocationalResultsStudyYear = Object.values(yearToModule);
        /* TODO delete this code if credits aren't needed
        vocationalResultsStudyYear.forEach(function (studyYearModulesThemes) {

          var creditsSum = 0;
          var gradeCreditsSum = 0;
          var creditsSumForAverage = 0;
          studyYearModulesThemes.results.forEach(function (moduleTheme) {
            if (moduleTheme.grade && VocationalGradeUtil.isPositive(moduleTheme.grade.code)) {
              if(VocationalGradeUtil.isDistinctive(moduleTheme.grade.code)) {
                var grade = parseInt(moduleTheme.grade.value, 10);
                creditsSumForAverage += moduleTheme.credits;
                gradeCreditsSum += moduleTheme.credits * parseInt(grade, 10);
              }
              creditsSum += moduleTheme.credits;
            }
          });

          if (creditsSumForAverage !== 0) {
            studyYearModulesThemes.kkh = gradeCreditsSum / creditsSumForAverage;
          }
          studyYearModulesThemes.credits = creditsSum;
        });
        */

        $scope.vocationalResultsStudyYear = vocationalResultsStudyYear.sort(function (a, b) { return a.studyYearStartDate < b.studyYearStartDate; });
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
      return entryMapper.objectmapper(vocationalResults);
    });
  }

  $scope.loadJournalsAndProtocols = function () {
    QueryUtils.endpoint('/students/' + $scope.studentId + '/vocationalConnectedEntities/').query(function (result) {
      $scope.studentConnectedEntities = result;
    });
  };

  $scope.loadChangeableModules = function () {
    if (!angular.isArray($scope.vocationalResults)) {
      QueryUtils.endpoint('/students/' + $scope.student.id + '/vocationalCurriculumModules/').query(function (curriculumModules) {
        $scope.vocationalCurriculumModules = curriculumModules;
        QueryUtils.endpoint('/students/' + $scope.student.id + '/vocationalChangeableModules').query(function (changeableModules) {
          setChangeableModules(changeableModules);
        });
      });
    }
  };

  $scope.saveChangedModules = function () {
    QueryUtils.endpoint('/students/' + $scope.student.id + '/changeVocationalCurriculumModules/').post(
      {modules: $scope.changeableModules}).$promise.then(function(changeableModules) {
      setChangeableModules(changeableModules);
    });
  };

  function setChangeableModules(changeableModules) {
    changeableModules.forEach(function (changeableModule) {
      changeableModule.oldCurriculumVersionModuleId = changeableModule.curriculumVersionModuleId;
      $scope.vocationalCurriculumModules.forEach(function (curriculumModule) {
        if (curriculumModule.id === changeableModule.curriculumVersionModuleId) {
          changeableModule.curriculumVersionModule = curriculumModule;
        }
      });
    });
    $scope.changeableModules = changeableModules;
  }

  $scope.displayTeachers = function (teachers) {
    if (angular.isArray(teachers)) {
      return teachers.map(function (teacher) { return teacher.nameEt; }).sort().join(', ');
    }
  };

  $scope.positiveGrade = function (grade) {
    return VocationalGradeUtil.isPositive(grade);//(angular.isNumber(grade) && grade > 2) || (angular.isString(grade) && parseInt(grade, 10) > 2);
  };

  function loadCurrentNavItemData() {
    if ($scope.resultsCurrentNavItem === 'student.curriculumFulfillment') {
      loadVocationalResults();
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfStudyYear') {
      $scope.loadStudyYearResults();
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfPassing') {
      $scope.loadModuleThemeResults();
    } else if ($scope.resultsCurrentNavItem === 'student.journalsAndProtocols') {
      $scope.loadJournalsAndProtocols();
    } else if ($scope.resultsCurrentNavItem === 'student.changeModules') {
      if (StudentUtil.isActive($scope.student.status)) {
        $scope.loadChangeableModules();
      } else {
        $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
      }
    }
  }

}]).controller('StudentViewResultsHigherController', ['$q', '$route', '$scope', 'Classifier', 'HigherGradeUtil', 'QueryUtils', 'StudentUtil', '$rootScope',
  function ($q, $route, $scope, Classifier, HigherGradeUtil, QueryUtils, StudentUtil, $rootScope) {
    $scope.gradeUtil = HigherGradeUtil;
    var auth = $route.current.locals.auth;

    $scope.canChangeStudentModules = $scope.auth.isAdmin() && StudentUtil.isActive($scope.student.status) && $scope.auth.authorizedRoles.indexOf('ROLE_OIGUS_M_TEEMAOIGUS_OPPUR') !== -1;

    $scope.$watch('resultsCurrentNavItem', function () {
      if ($scope.resultsCurrentNavItem === 'student.changeModules') {
        if (StudentUtil.isActive($scope.student.status)) {
          $scope.loadChangeableModules();
        } else {
          $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
        }
      }
    });

    function loadHigherResults() {
      var id = (auth.isStudent() ? auth.student : $route.current.params.id);
      QueryUtils.endpoint('/students/' + id + '/higherResults').get().$promise.then(function (response) {
        $scope.higherResults = response;
        $scope.student.higherResults = $scope.higherResults;
        $scope.student.higherResults.modules.sort(moduleComparator);
      });
    }

    if (!angular.isDefined($scope.higherResults)) {
      loadHigherResults();
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
        changeableModule.oldCurriculumVersionModuleId = changeableModule.curriculumVersionModuleId;
        $scope.higherCurriculumModules.forEach(function (curriculumModule) {
          if (curriculumModule.id === changeableModule.curriculumVersionModuleId) {
            changeableModule.curriculumVersionModule = curriculumModule;
          }
        });
      });
      $scope.changeableModules = changeableModules;
    }

    $scope.saveChangedModules = function () {
      QueryUtils.endpoint('/students/' + $scope.student.id + '/changeHigherCurriculumModules/').post(
        {modules: $scope.changeableModules}).$promise.then(function(changeableModules) {
        setChangeableModules(changeableModules);
        loadHigherResults();
      });
    };

  }]).controller('StudentViewDocumentsController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', 'CertificateUtil', 'CertificateType', function ($q, $route, $scope, Classifier, QueryUtils, CertificateUtil, CertificateType) {
   // var studentId = (auth.isStudent() ? auth.student : $route.current.params.id);
    $scope.studentId = ($route.current.locals.auth.isStudent() ? $route.current.locals.auth.student : $route.current.params.id);
    $scope.currentNavItem = 'student.documents';
    $scope.auth = $route.current.locals.auth;

    $scope.applicationsCriteria = { size: 5, page: 1, order: 'submitted', studentId: $scope.studentId };
    $scope.applications = {};
    var applicationsMapper = Classifier.valuemapper({ type: 'AVALDUS_LIIK', status: 'AVALDUS_STAATUS' });
    $scope.afterApplicationsLoad = function (result) {
      $scope.applications.content = applicationsMapper.objectmapper(result.content);
      $scope.applications.totalElements = result.totalElements;
    };

    $scope.loadApplications = function () {
      var query = QueryUtils.getQueryParams($scope.applicationsCriteria);
      $scope.applications.$promise = QueryUtils.endpoint('/students/:studentId/applications').search(query, $scope.afterApplicationsLoad);
    };

    if($scope.auth.isStudent()) {
      $scope.applicationTypes = Classifier.queryForDropdown({ mainClassCode: 'AVALDUS_LIIK' });
      $scope.certificateTypes = Classifier.queryForDropdown({mainClassCode: 'TOEND_LIIK', filterValues: [CertificateType.TOEND_LIIK_MUU]});
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

    $scope.certificatesCriteria = { size: 5, page: 1, order: 'type.' + $scope.currentLanguageNameField(), student: $scope.studentId};
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
      $scope.student = result.student;
      $q.all(applicationsMapper.promises).then(function () {
        $scope.afterApplicationsLoad(result.applications);
      });
      $q.all(directivesMapper.promises).then(function () {
        $scope.afterDirectivesLoad(result.directives);
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
  }]).controller('StudentViewTimetableController', ['$scope', '$route', 'QueryUtils', function ($scope, $route, QueryUtils) {
    $scope.studentId = ($route.current.locals.auth.isStudent() ? $route.current.locals.auth.student : $route.current.params.id);
    $scope.student = QueryUtils.endpoint('/students').get({ id: $scope.studentId });
    $scope.typeId = $scope.studentId;
    $scope.currentNavItem = 'student.timetable';
    $scope.auth = $route.current.locals.auth;

  }]).controller('StudentEditController', ['$location', '$route', '$scope', 'message', 'oisFileService', 'QueryUtils', function ($location, $route, $scope, message, oisFileService, QueryUtils) {
    var id = $route.current.params.id;

    function afterLoad() {
      if($scope.student.photo) {
        $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
      } else {
        $scope.student.imageUrl = '?' + new Date().getTime();
      }
    }

    function isAddressFilled(person) {
      if (person.residenceCountry === 'RIIK_EST') {
        return person.address && person.addressAds && person.addressAdsOid;
      }
      return person.address;
    }

    $scope.student = QueryUtils.endpoint('/students').get({ id: id });
    $scope.student.$promise.then(afterLoad);

    
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

    $scope.update = function () {
      $scope.studentEditForm.$setSubmitted();
      if (!isAddressFilled($scope.student.person)) {
        message.error('student.error.addressRequired');
        return;
      }
      if (!$scope.studentEditForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      withPhoto(function() {
        $scope.student.$update().then(function () {
          message.updateSuccess();
          $location.path('/students/' + id + '/main');
        });
      });
    };
  }]).controller('StudentAbsencesController', ['$mdDialog', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',
    function ($mdDialog, $route, $scope, dialogService, message, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
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
          clickOutsideToClose: false
        });
      };

      $scope.loadData();
    }]);
