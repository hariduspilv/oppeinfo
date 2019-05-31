'use strict';

angular.module('hitsaOis').controller('StudentViewMainController', ['$mdDialog', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils', 'oisFileService',

  function ($mdDialog, $q, $route, $scope, dialogService, message, Classifier, QueryUtils, oisFileService) {
    var auth = $route.current.locals.auth;
    var studentId = (auth.isStudent() || auth.isParent() ? auth.student : $route.current.params.id);
    var baseUrl = '/students';

    $scope.auth = auth;
    $scope.studentId = studentId;
    $scope.currentNavItem = 'student.main';

    var loadStudent = function () {
      QueryUtils.endpoint(baseUrl).get({ id: studentId }, function (result) {
        $scope.student = result;
        if ($scope.student.photo) {
          $scope.student.imageUrl = oisFileService.getUrl($scope.student.photo, 'student');
        } else {
          $scope.student.imageUrl = '?' + new Date().getTime();
        }
      });
    };
    loadStudent();

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
  }
]).controller('StudentViewResultsController', ['$route', '$scope', '$localStorage', 'QueryUtils', 'config', 'StudentUtil', 
function ($route, $scope, $localStorage, QueryUtils, config, StudentUtil) {
  $scope.auth = $route.current.locals.auth;

  $scope.studentId = ($scope.auth.isStudent() || $scope.auth.isParent() ? $scope.auth.student : $route.current.params.id);
  QueryUtils.endpoint('/students').get({ id: $scope.studentId }).$promise.then(function (student) {
    $scope.student = student;
    canWatchStudentConnectedEntities();
    if (StudentUtil.isActive($scope.student.status) && $scope.auth.authorizedRoles.indexOf('ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE') !== -1) {
      $scope.supplementPreviewPdfUrl = config.apiUrl + '/documents/supplement/' + $scope.studentId + '/preview.pdf';
    }
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
    } else if ($scope.auth.isTeacher() && $scope.student.studentGroup && $scope.auth.teacherGroupIds.length > 0) {
      $scope.canWatchStudentConnectedEntities = $scope.auth.teacherGroupIds.indexOf($scope.student.studentGroup.id) !== -1;
    }
  }

  $scope.isStudentCurriculumFulfilled = function (student) {
    return student && angular.isNumber(student.credits) && student.credits >= student.curriculumCredits;
  };

}]).controller('StudentViewResultsVocationalController', ['$filter', '$route', '$scope', 'Classifier', 'QueryUtils', '$rootScope', 'VocationalGradeUtil', 'StudentUtil',
function ($filter, $route, $scope, Classifier, QueryUtils, $rootScope, VocationalGradeUtil, StudentUtil) {
  $scope.auth = $route.current.locals.auth;
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
        curriculumModulesById[it.curriculumModule.id] = it;
      });

      var formalLearningResult = [];
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

          // collect formal learning results
          if (it.isApelTransfer && it.isFormalLearning) {
            formalLearningResult.push(it);
          }
        }
      });

      $scope.moduleResultById = moduleResultById;
      $scope.positiveThemeResult = function (themeResult) {
        return angular.isObject(themeResult) && angular.isObject(themeResult.grade) && angular.isString(themeResult.grade.code) && VocationalGradeUtil.isPositive(themeResult.grade.code);
      };

      $scope.vocationalResultsCurriculum = {};
      $scope.vocationalResultsCurriculum.vocationalResultsModules = $scope.vocationalResults.curriculumModules.sort(function (m1, m2) {
        return m1.curriculumModule.orderNr - m2.curriculumModule.orderNr || $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });
      $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules = $scope.vocationalResults.extraCurriculaModules.sort(function (m1, m2) {
        return m1.curriculumModule.orderNr - m2.curriculumModule.orderNr || $rootScope.currentLanguageNameField(m1.curriculumModule).localeCompare($rootScope.currentLanguageNameField(m2.curriculumModule));
      });

      // remove modules that are replaced by apel formal learning
      formalLearningResult.forEach(function (formalLearning) {
        formalLearning.replacedModules.forEach(function (replacedModuleId) {
          var replacedModule = $scope.vocationalResultsCurriculum.vocationalResultsModules.filter(function (module) {
            return module.curriculumModule.id === replacedModuleId;
          })[0];
          if (angular.isObject(replacedModule)) {
            var curriculumModuleIndex = $scope.vocationalResultsCurriculum.vocationalResultsModules.indexOf(replacedModule);
            if (curriculumModuleIndex > -1) {
              $scope.vocationalResultsCurriculum.vocationalResultsModules.splice(curriculumModuleIndex, 1);
            }
          }

          var replacedExtraCurriculaModule = $scope.vocationalResultsCurriculum.extraCurriculaVocationalResultsModules.filter(function (module) {
            return module.curriculumModule.id === replacedModuleId;
          })[0];
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

      $scope.moduleBacklog = function (module) {
        return (($scope.moduleResultById[module.curriculumModule.id] ? moduleResultById[module.curriculumModule.id].totalSubmitted : 0) - module.curriculumModule.credits);
      };
    }
  };

  function loadVocationalResults() {
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
      return entryMapper.objectmapper(vocationalResults);
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

  function loadCurrentNavItemData() {
    if ($scope.resultsCurrentNavItem === 'student.curriculumFulfillment') {
      loadVocationalResults();
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfStudyYear') {
      $scope.loadStudyYearResults();
    } else if ($scope.resultsCurrentNavItem === 'student.inOrderOfPassing') {
      $scope.loadModuleThemeResults();
    } else if ($scope.resultsCurrentNavItem === 'student.journalsAndProtocols') {
      if (canWatchStudentConnectedEntities()) {
        $scope.loadJournalsAndProtocols();
      } else {
        $scope.changeResultsCurrentNavItem('student.curriculumFulfillment');
      }
    }
  }

  function canWatchStudentConnectedEntities() {
    if ($scope.auth.isAdmin()) {
      return true;
    } else if ($scope.auth.isTeacher() && $scope.student.studentGroup && $scope.auth.teacherGroupIds.length > 0) {
      return $scope.auth.teacherGroupIds.indexOf($scope.student.studentGroup.id) !== -1;
    }
  }

}]).controller('StudentViewResultsHigherController', ['$route', '$scope', 'HigherGradeUtil', 'QueryUtils', 'StudentUtil', '$rootScope', 'message',
  function ($route, $scope, HigherGradeUtil, QueryUtils, StudentUtil, $rootScope, message) {
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
      var id = (auth.isStudent() || auth.isParent()) ? auth.student : $route.current.params.id;
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
        message.updateSuccess();
        setChangeableModules(changeableModules);
        loadHigherResults();
      });
    };

  }]).controller('StudentViewDocumentsController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', 'CertificateUtil', 'CertificateType', function ($q, $route, $scope, Classifier, QueryUtils, CertificateUtil, CertificateType) {
   // var studentId = (auth.isStudent() ? auth.student : $route.current.params.id);
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

    QueryUtils.endpoint('/students').get({ id: $scope.studentId }).$promise.then(function (student) {
      $scope.student = student;
    });

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

  }]).controller('StudentEditController', ['$location', '$route', '$scope', 'message', 'oisFileService', 'QueryUtils', 'DataUtils', 'dialogService', 'hoisDateFilter', '$rootScope',
    function ($location, $route, $scope, message, oisFileService, QueryUtils, DataUtils, dialogService, hoisDateFilter, $rootScope) {
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
    }

    function isAddressFilled(person) {
      if (person.residenceCountry === 'RIIK_EST') {
        return person.address && person.addressAds && person.addressAdsOid;
      }
      return person.address;
    }

    $scope.student = QueryUtils.endpoint('/students').get({ id: id });
    $scope.student.$promise.then(afterLoad);
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
      dialogService.confirmDialog({prompt: 'main.messages.confirmSave'}, function() {
        withPhoto(function() {
          $scope.student.$update().then(function () {
            message.updateSuccess();
            $location.path('/students/' + id + '/main');
          });
        });
      });
    };
  }]).controller('StudentAbsencesController', ['$mdDialog', '$route', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',
    function ($mdDialog, $route, $scope, dialogService, message, DataUtils, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      $scope.student = QueryUtils.endpoint('/students').get({ id: $scope.studentId });
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
    }]).controller('StudentViewRemarksController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', function ($q, $route, $scope, Classifier, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      $scope.student = QueryUtils.endpoint('/students').get({ id: $scope.studentId });
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

    }]).controller('StudentViewRRController', ['$q', '$route', '$scope', 'Classifier', 'QueryUtils', function ($q, $route, $scope, Classifier, QueryUtils) {
      $scope.auth = $route.current.locals.auth;
      $scope.studentId = $route.current.params.id;
      $scope.student = QueryUtils.endpoint('/students').get({ id: $scope.studentId });
      $scope.currentNavItem = 'student.rr';

      QueryUtils.createQueryForm($scope, '/logs/rr/changelogs', {order: '-wrcl.inserted', student: $scope.studentId}, undefined, undefined, true);

      $scope.loadData();
    }]);