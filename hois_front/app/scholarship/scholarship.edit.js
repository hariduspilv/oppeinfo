'use strict';

angular.module('hitsaOis').controller('ScholarshipEditController', ['$scope', '$rootScope', 'Classifier', '$location', 'message', 'QueryUtils', 'dialogService', '$route', 'DataUtils',
  function ($scope, $rootScope, Classifier, $location, message, QueryUtils, dialogService, $route, DataUtils) {
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

    $scope.formState = {priorities: Classifier.queryForDropdown({mainClassCode: 'PRIORITEET'})};
    var typePromise = Classifier.queryForDropdown({mainClassCode: 'STIPTOETUS'}).$promise;
    typePromise.then(function(result) {
      $scope.formState.typeMap = Classifier.toMap(result);
    });
    $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();

    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $scope.stipendTypeChanged = function () {
      $scope.templateName = templateMap[$scope.stipend.type];
      typePromise.then(function() {
        if (!$scope.stipend.nameEt) {
          $scope.stipend.nameEt = ($scope.formState.typeMap[$scope.stipend.type] || {}).nameEt;
        }
        if (angular.isArray($scope.formState.allowedStipendTypes) && $scope.formState.allowedStipendTypes.length === 1) {
          $scope.formState.typeIsScholarship = (['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'].indexOf($scope.formState.allowedStipendTypes[0]) !== -1);
        }
      });
    };

    function afterLoad(result) {
      $scope.formState.allowedStipendTypes = [result.type];
      $scope.stipend = result;
      $scope.stipendTypeChanged();
      $scope.formState.studyPeriods.$promise.then(function () {
        $scope.formState.readonlyStudyPeriod = $scope.formState.studyPeriods.find(function (it) {
          return it.id === $scope.stipend.studyPeriod;
        });
      });
    }

    if (id) {
      $scope.stipend = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.stipend = new Endpoint({});
      $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
      $scope.formState.typeIsScholarship = $route.current.locals.params.typeIsScholarship;
      if ($scope.formState.allowedStipendTypes.length === 1) {
        $scope.stipend.type = $scope.formState.allowedStipendTypes[0];
        $scope.stipendTypeChanged();
      }
      $scope.formState.studyPeriods.$promise.then(function () {
        if (!$scope.stipend.studyPeriod) {
          var sp = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods);
          if(sp) {
            $scope.stipend.studyPeriod = sp.id;
          }
        }
      });
      $scope.stipend.courses = ['KURSUS_1', 'KURSUS_2', 'KURSUS_3', 'KURSUS_4'];
      $scope.missingCurriculums = true;
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
      }
    });

    $scope.deleteCurriculum = function (curriculum) {
      var index = $scope.stipend.curriculums.indexOf(curriculum);
      if (index !== -1) {
        $scope.stipend.curriculums.splice(index, 1);
      }
      if($scope.stipend.curriculums.length === 0) {
        $scope.missingCurriculums = true;
      }
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
      var otherValues = [$scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority, $scope.stipend.maxAbsencesPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.lastPeriodMarkPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.curriculumCompletionPriority, $scope.stipend.maxAbsencesPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.curriculumCompletionPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.maxAbsencesPriority];
      return otherValues.indexOf(value.code) === -1;
    };

    $scope.maxAbsencesPriorityFilter = function (value) {
      var otherValues = [$scope.stipend.averageMarkPriority, $scope.stipend.lastPeriodMarkPriority, $scope.stipend.curriculumCompletionPriority];
      return otherValues.indexOf(value.code) === -1;
    };
  }
]).controller('ScholarshipViewController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'AuthService', 'USER_ROLES', 
    'ScholarshipUtils',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, AuthService, USER_ROLES, ScholarshipUtils) {
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

    function afterLoad(result) {
      $scope.stipend = result;
      $scope.templateName = templateMap[result.type];
      $scope.formState.typeIsScholarship = (['STIPTOETUS_ERIALA', 'STIPTOETUS_MUU', 'STIPTOETUS_TULEMUS'].indexOf(result.type) !== -1);
      $scope.curriculumsDisplay = result.curriculums.map($scope.currentLanguageNameField);

      $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function () {
        $scope.formState.readonlyStudyPeriod = $scope.formState.studyPeriods.find(function (it) {
          return it.id === $scope.stipend.studyPeriod;
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

    if (id) {
      $scope.stipend = Endpoint.get({id: id}, afterLoad);
    }

    $scope.changeStipend = function(stipend) {
      ScholarshipUtils.changeStipend(stipend.id, stipend.type, stipend.isOpen);
    };
  }
]);
