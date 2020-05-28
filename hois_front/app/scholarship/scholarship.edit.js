'use strict';

angular.module('hitsaOis').controller('ScholarshipEditController', ['$route', '$scope', '$location', 'Classifier',
 'DataUtils', 'QueryUtils', 'dialogService', 'message', 'ArrayUtils',
  function ($route, $scope, $location, Classifier, DataUtils, QueryUtils, dialogService, message, ArrayUtils) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/term/scholarship.pohi.edit.html',
      STIPTOETUS_ERI: 'scholarship/term/scholarship.eri.edit.html',
      STIPTOETUS_SOIDU: 'scholarship/term/scholarship.soidu.edit.html',
      STIPTOETUS_DOKTOR: 'scholarship/term/scholarship.doktor.edit.html',
      STIPTOETUS_ERIALA: 'scholarship/term/scholarship.scho.edit.html',
      STIPTOETUS_MUU: 'scholarship/term/scholarship.scho.edit.html',
      STIPTOETUS_TULEMUS: 'scholarship/term/scholarship.scho.edit.html'
    };

    $scope.filteredStudyForm = ['OPPEVORM_P', 'OPPEVORM_Q', 'OPPEVORM_S', 'OPPEVORM_MS', 'OPPEVORM_K', 'OPPEVORM_M'];
    var enabledEhisTypeFor = ['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS', 'STIPTOETUS_DOKTOR'];

    $scope.formState = {priorities: Classifier.queryForDropdown({mainClassCode: 'PRIORITEET'})};
    var typePromise = Classifier.queryForDropdown({mainClassCode: 'STIPTOETUS'}).$promise;
    typePromise.then(function(result) {
      $scope.formState.typeMap = Classifier.toMap(result);
    });

    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.auth = $route.current.locals.auth;

    $scope.stipendTypeChanged = function () {
      $scope.templateName = templateMap[$scope.stipend.type];
      typePromise.then(function() {
        if (!$scope.stipend.nameEt) {
          $scope.stipend.nameEt = ($scope.formState.typeMap[$scope.stipend.type] || {}).nameEt;
        }
        if (angular.isArray($scope.formState.allowedStipendTypes) && $scope.formState.allowedStipendTypes.length === 1) {
          $scope.formState.typeIsScholarship = (['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'].indexOf($scope.formState.allowedStipendTypes[0]) !== -1);
        }
        setEhisStipendiumValues($scope.stipend.type);
      });
    };

    function reloadCommittees() {
      $scope.formState.committees = QueryUtils.endpoint(baseUrl + "/committees").query({
        validDate: $scope.stipend.applicationEnd,
        curriclumIds: ($scope.stipend.curriculums || []).map(function (curriculum) {
          return curriculum.id;
        })
      });
    }

    $scope.applicationEndChanged = function () {
      reloadCommittees();
    };

    function afterLoad(result) {
      $scope.formState.allowedStipendTypes = [result.type];
      if ($scope.formState.allowedStipendTypes.indexOf('STIPTOETUS_DOKTOR') !== -1) {
        $scope.currentNavItem = 'scholarship.drStipend';
      } else if (ArrayUtils.intersect($scope.formState.allowedStipendTypes, ['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'])) {
        $scope.currentNavItem = 'scholarship.stipend';
      }
      $scope.formState.enableEhisType = enabledEhisTypeFor.indexOf(result.type) !== -1;
      $scope.stipend = result;
      $scope.stipendTypeChanged();
      $scope.applicationEndChanged();
      $scope.stipendForm.$setPristine();
      DataUtils.convertStringToDates($scope.stipend, ['applicationStart', 'applicationEnd', 'paymentStart', 'paymentEnd',
        'lastPeriodGradeFrom', 'lastPeriodGradeThru', 'studyStartPeriodStart', 'studyStartPeriodEnd']);
    }

    if (id) {
      $scope.stipend = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.stipend = new Endpoint({});
      $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
      $scope.formState.typeIsScholarship = $route.current.locals.params.typeIsScholarship;
      $scope.formState.enableEhisType = $route.current.locals.params.enableEhisType;
      if ($scope.formState.allowedStipendTypes.length === 1) {
        $scope.stipend.type = $scope.formState.allowedStipendTypes[0];
        $scope.stipendTypeChanged();
      }
      $scope.stipend.courses = ['KURSUS_1', 'KURSUS_2', 'KURSUS_3', 'KURSUS_4'];
      $scope.missingCurriculums = true;

      if ($scope.formState.allowedStipendTypes.indexOf('STIPTOETUS_DOKTOR') !== -1) {
        $scope.currentNavItem = 'scholarship.drStipend';
      } else if (ArrayUtils.intersect($scope.formState.allowedStipendTypes, ['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'])) {
        $scope.currentNavItem = 'scholarship.stipend';
      }
    }

    $scope.update = function () {
      if (!formIsValid()) {
        return;
      }
      if ($scope.stipend.id) {
        $scope.stipend.$update().then(afterLoad).then(message.updateSuccess).catch(angular.noop);
      } else {
        $scope.stipend.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.stipend.id + '/edit?_noback');
        }).catch(angular.noop);
      }
    };

    $scope.$watch('formState.curriculum', function () {
      if (angular.isDefined($scope.formState.curriculum) && $scope.formState.curriculum !== null) {
        if (!angular.isArray($scope.stipend.curriculums)) {
          $scope.stipend.curriculums = [];
        }
        if ($scope.stipend.curriculums.some(function (e) {
            return e.id === $scope.formState.curriculum.id;
          })) {
          message.error('stipend.messages.error.duplicateCurriculum');
          $scope.formState.curriculum = undefined;
          return;
        }
        $scope.stipend.curriculums.push($scope.formState.curriculum);
        if($scope.stipend.curriculums.length > 0) {
          $scope.missingCurriculums = false;
        }
        $scope.formState.curriculum = undefined;
        reloadCommittees();
      }
    });

    $scope.addAllCurriculums = function (criteria) {
      QueryUtils.endpoint('/autocomplete/curriculumsauto').query(criteria, function (curriculums) {
        if (!angular.isArray($scope.stipend.curriculums)) {
          $scope.stipend.curriculums = [];
        }
        var stipendCurriculumIds = $scope.stipend.curriculums.map(function (it) {
          return it.id;
        });

        for (var i = 0; i < curriculums.length; i++) {
          var curriculum = curriculums[i];
          if (stipendCurriculumIds.indexOf(curriculum.id) === -1) {
            $scope.stipend.curriculums.push(curriculum);
          }
        }
        if($scope.stipend.curriculums.length > 0) {
          $scope.missingCurriculums = false;
        }
        reloadCommittees();
      });
    };

    $scope.deleteCurriculum = function (curriculum) {
      var index = $scope.stipend.curriculums.indexOf(curriculum);
      if (index !== -1) {
        $scope.stipend.curriculums.splice(index, 1);
      }
      if($scope.stipend.curriculums.length === 0) {
        $scope.missingCurriculums = true;
      }
      reloadCommittees();
    };

    $scope.publish = function () {
      dialogService.confirmDialog({prompt: 'stipend.confirmations.saveAndPublish'}, function () {
        if (!formIsValid()) {
          return;
        }
        $scope.stipend.$update({}, function () {
          QueryUtils.endpoint(baseUrl + '/' + $scope.stipend.id + '/publish').put({}, function () {
            $route.reload();
          });
        }).catch(angular.noop);
      });
    };

    $scope.copy = function () {
      if (!formIsValid()) {
        return;
      }
      $scope.stipend.$update({}, function () {
        QueryUtils.endpoint(baseUrl + '/copy/' + $scope.stipend.id).save({}, function (result) {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + result.id + '/edit');
        });
      }).catch(angular.noop);
    };

    $scope.delete = function () {
      var deleteType, url;
      if (['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'].indexOf($scope.stipend.type) !== -1) {
        deleteType = 'stipend.confirmations.deleteGrant';
        url = '/scholarships/grants';
      } else {
        deleteType = 'stipend.confirmations.deleteStipend';
        url = '/scholarships/scholarships';
      }
      dialogService.confirmDialog({prompt: deleteType}, function () {
        QueryUtils.endpoint(baseUrl + '/' + $scope.stipend.id + '/deleteTerm').delete({}, function () {
          $location.url(url + '?_noback');
        }).$promise.catch(angular.noop);
      });
    };

    function formIsValid() {
      $scope.stipendForm.$setSubmitted();
      if (!$scope.stipendForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      } else if (!angular.isArray($scope.stipend.curriculums) || $scope.stipend.curriculums.length < 1) {
        message.error('stipend.messages.error.curriculumsEmtpy');
        return false;
      }
      return true;
    }

    $scope.averageMarkPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority, $scope.stipend.maxAbsencesPriority,
        $scope.stipend.wagMarkPriority, $scope.stipend.lastPeriodWagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.lastPeriodMarkPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.curriculumCompletionPriority, $scope.stipend.maxAbsencesPriority,
        $scope.stipend.wagMarkPriority, $scope.stipend.lastPeriodWagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.curriculumCompletionPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.maxAbsencesPriority,
        $scope.stipend.wagMarkPriority, $scope.stipend.lastPeriodWagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.maxAbsencesPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority,
        $scope.stipend.wagMarkPriority, $scope.stipend.lastPeriodWagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.wagMarkPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority,
        $scope.stipend.maxAbsencesPriority, $scope.stipend.lastPeriodWagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.lastPeriodWagMarkPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority,
        $scope.stipend.maxAbsencesPriority, $scope.stipend.wagMarkPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    function setEhisStipendiumValues(type) {
      if (!type) {
        $scope.formState.allowedEhisTypes = [];
        return;
      }
      switch (type) {
        case 'STIPTOETUS_TULEMUS':
          $scope.formState.allowedEhisTypes = ['EHIS_STIPENDIUM_2'];
          setDefaultEhisStupendium('EHIS_STIPENDIUM_2');
          break;
        case 'STIPTOETUS_ERIALA':
          $scope.formState.allowedEhisTypes = ['EHIS_STIPENDIUM_8', 'EHIS_STIPENDIUM_10'];
          setDefaultEhisStupendium('EHIS_STIPENDIUM_10');
          break;
        case 'STIPTOETUS_DOKTOR':
          $scope.formState.allowedEhisTypes = ['EHIS_STIPENDIUM_7', 'EHIS_STIPENDIUM_14'];
            setDefaultEhisStupendium('EHIS_STIPENDIUM_7');
          break;
        case 'STIPTOETUS_MUU':
          $scope.formState.allowedEhisTypes = ['EHIS_STIPENDIUM_11', 'EHIS_STIPENDIUM_12', 'EHIS_STIPENDIUM_13',
            'EHIS_STIPENDIUM_15', 'EHIS_STIPENDIUM_16', 'EHIS_STIPENDIUM_6', 'EHIS_STIPENDIUM_9'];
          break;
        default:
          $scope.formState.allowedEhisTypes = [];
      }
    }

    function setDefaultEhisStupendium(value) {
      if (!$scope.stipend.scholarshipEhis) {
        $scope.stipend.scholarshipEhis = value;
      }
    }
  }
]).controller('ScholarshipViewController', ['$route', '$scope', 'AuthService', 'ScholarshipUtils', 'USER_ROLES', 'QueryUtils', 'dialogService', '$location', 'message',
  function ($route, $scope, AuthService, ScholarshipUtils, USER_ROLES, QueryUtils, dialogService, $location, message) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/term/scholarship.pohi.view.html',
      STIPTOETUS_ERI: 'scholarship/term/scholarship.eri.view.html',
      STIPTOETUS_SOIDU: 'scholarship/term/scholarship.soidu.view.html',
      STIPTOETUS_DOKTOR: 'scholarship/term/scholarship.doktor.view.html',
      STIPTOETUS_ERIALA: 'scholarship/term/scholarship.scho.view.html',
      STIPTOETUS_MUU: 'scholarship/term/scholarship.scho.view.html',
      STIPTOETUS_TULEMUS: 'scholarship/term/scholarship.scho.view.html'
    };
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS);
    $scope.formState = {};
    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    $scope.auth = $route.current.locals.auth;
    var enabledEhisTypeFor = ['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS', 'STIPTOETUS_DOKTOR'];

    function afterLoad(result) {
      $scope.stipend = result;
      $scope.templateName = templateMap[result.type];
      $scope.formState.typeIsScholarship = (['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'].indexOf(result.type) !== -1);
      $scope.formState.enableEhisType = enabledEhisTypeFor.indexOf(result.type) !== -1;

      if (result.type === 'STIPTOETUS_DOKTOR') {
        $scope.currentNavItem = 'scholarship.drStipend';
      } else if (['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'].indexOf(result.type) !== -1) {
        $scope.currentNavItem = 'scholarship.stipend';
      }

      QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query(function (studyPeriods) {
        $scope.formState.readonlyStudyPeriod = studyPeriods.find(function (it) {
          return it.id === $scope.stipend.studyPeriod;
        });
      });

      QueryUtils.endpoint(baseUrl + "/committees").query(function (committees) {
        $scope.formState.committee = committees.find(function (it) {
          return it.id === $scope.stipend.committee;
        });
      });
    }

    $scope.publish = function () {
      dialogService.confirmDialog({prompt: 'stipend.confirmations.publish'}, function () {
        QueryUtils.endpoint(baseUrl + '/' + $scope.stipend.id + '/publish').put({}, function (result) {
          afterLoad(result);
        }).catch(angular.noop);
      });
    };

    $scope.copy = function () {
      QueryUtils.endpoint(baseUrl + '/copy/' + $scope.stipend.id).save({}, function (result) {
        message.info('main.messages.create.success');
        $location.url(baseUrl + '/' + result.id + '/edit');
      });
    };

    if (id) {
      $scope.stipend = Endpoint.get({id: id}, afterLoad);
    }

    $scope.changeStipend = function(stipend) {
      ScholarshipUtils.changeStipend(stipend.id, stipend.type, stipend.isOpen);
    };
  }
]);
