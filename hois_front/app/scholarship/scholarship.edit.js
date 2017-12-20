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
    $scope.formState = {};
    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $scope.stipendTypeChanged = function () {
      $scope.templateName = templateMap[$scope.stipend.type];
    };

    function afterLoad(result) {
      $scope.formState.allowedStipendTypes = [result.type];
      $scope.stipend = result;
      $scope.stipendTypeChanged();
      $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function () {
        $scope.formState.readonlyStudyPeriod = $scope.formState.studyPeriods.find(function (it) {
          return it.id === $scope.stipend.studyPeriod;
        });
      });
    }

    if (id) {
      $scope.stipend = Endpoint.get({
        id: id
      });
      $scope.stipend.$promise.then(afterLoad);
    } else {
      $scope.stipend = new Endpoint({});
      $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;
      if ($scope.formState.allowedStipendTypes.length === 1) {
        $scope.stipend.type = $scope.formState.allowedStipendTypes[0];
      }
      $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function () {
        if (!$scope.stipend.studyPeriod) {
          $scope.stipend.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods).id;
        }
      });
      $scope.stipend.courses = ['KURSUS_1', 'KURSUS_2', 'KURSUS_3', 'KURSUS_4'];
    }

    $scope.update = function () {
      if (!formIsValid()) {
        return;
      }
      if ($scope.stipend.id) {
        $scope.stipend.$update().then(afterLoad).then(message.updateSuccess);
      } else {
        $scope.stipend.$save().then(function () {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.stipend.id + '/edit?_noback');
        });
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
        $scope.formState.curriculum = undefined;
      }
    });

    $scope.deleteCurriculum = function (curriculum) {
      var index = $scope.stipend.curriculums.indexOf(curriculum);
      if (index !== -1) {
        $scope.stipend.curriculums.splice(index, 1);
      }
    };

    $scope.publish = function () {
      dialogService.confirmDialog({
          prompt: 'stipend.confirmations.publish',
          accept: 'main.yes',
          cancel: 'main.no'
        },
        function () {
          QueryUtils.endpoint(baseUrl + '/' + $scope.stipend.id + '/publish').put({}, function (result) {
            afterLoad(result);
          });
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
  }
]).controller('ScholarshipViewController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route) {
    var templateMap = {
      STIPTOETUS_POHI: 'scholarship/term/scholarship.pohi.view.html',
      STIPTOETUS_ERI: 'scholarship/term/scholarship.eri.view.html',
      STIPTOETUS_SOIDU: 'scholarship/term/scholarship.soidu.view.html',
      STIPTOETUS_DOKTOR: 'scholarship/term/scholarship.doktor.view.html',
      STIPTOETUS_ERIALA: 'scholarship/term/scholarship.scho.view.html',
      STIPTOETUS_MUU: 'scholarship/term/scholarship.scho.view.html',
      STIPTOETUS_TULEMUS: 'scholarship/term/scholarship.scho.view.html'
    };
    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    function afterLoad(result) {
      $scope.stipend = result;
      $scope.templateName = templateMap[result.type];
      $scope.curriculumsDisplay = result.curriculums.map(function (it) {
        return $scope.currentLanguageNameField(it);
      });
    }

    if (id) {
      $scope.stipend = Endpoint.get({
        id: id
      });
      $scope.stipend.$promise.then(afterLoad);
    }


  }
]);