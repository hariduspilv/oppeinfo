'use strict';

angular.module('hitsaOis').controller('ScholarshipEditController', ['$scope', '$rootScope', 'Classifier', '$location', 'message', 'QueryUtils', 'dialogService', '$route', 'DataUtils',
  function ($scope, $rootScope, Classifier, $location, message, QueryUtils, dialogService, $route, DataUtils) {
    $scope.formState = {};
    $scope.display = {};
    var id = $route.current.params.id;
    var baseUrl = '/scholarships';
    var Endpoint = QueryUtils.endpoint(baseUrl);

    $scope.formState.scholarShipTypes = ['STIPTOETUS_ERIALA', 'STIPTOETUS_TULEMUS', 'STIPTOETUS_MUU'];
    if ($route.current.params && $route.current.params.grants) {
      $scope.formState.allowedStipendTypes = ['STIPTOETUS_POHI', 'STIPTOETUS_ERI', 'STIPTOETUS_SOIDU'];
      $scope.backString = '?grants';
    } else if ($route.current.params && $route.current.params.scholarships) {
      $scope.formState.allowedStipendTypes = $scope.formState.scholarShipTypes;
      $scope.formState.typeIsScholarship = true;
      $scope.backString = '?scholarships';
    } else {
      $scope.formState.allowedStipendTypes = ['STIPTOETUS_DOKTOR'];
    }

    function afterLoad(result) {
      $scope.stipend = result;
      $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function () {
        $scope.formState.readonlyStudyPeriod = $scope.formState.studyPeriods.find(function (it) {
          return it.id === $scope.stipend.studyPeriod;
        });
      });
      initializeFormDisplay();
      $scope.formState.curriculumsDisplay = $scope.stipend.curriculums.map(function(it){
        return $scope.currentLanguageNameField(it);
      });
    }

    if (id) {
      $scope.stipend = Endpoint.get({
        id: id
      });
      $scope.stipend.$promise.then(afterLoad);
    } else {
      $scope.stipend = new Endpoint({});
      $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query(function () {
        if (!$scope.stipend.studyPeriod) {
          $scope.stipend.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods).id;
        }
      });
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
        if(!angular.isArray($scope.stipend.curriculums)) {
          $scope.stipend.curriculums = [];
        }
        if ($scope.stipend.curriculums.some(function (e) {
            return e.id === $scope.formState.curriculum.id;
          })) {
          message.error('stipend.duplicatecurriculum');
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
      }
      return true;
    }

    $scope.stipendTypeChanged = function () {
      if (!$scope.stipend.name) {
        $scope.stipend.name = $scope.stipend.type;
      }
      initializeFormDisplay();
    };

    function initializeFormDisplay() {
      if ($scope.stipend.type) {
        var POHI = 'STIPTOETUS_POHI';
        var ERI = 'STIPTOETUS_ERI';
        var DOKTOR = 'STIPTOETUS_DOKTOR';
        var SOIDU = 'STIPTOETUS_SOIDU';
        $scope.required = {};
        $scope.formState.typeIsScholarship = $scope.formState.scholarShipTypes.indexOf($scope.stipend.type) !== -1 ? true : false;
        for (var key in $scope.display) {
          $scope.display[key] = false;
        }
        if ($scope.formState.scholarShipTypes.concat([POHI, ERI]).indexOf($scope.stipend.type) !== -1) {
          $scope.display.avgPreviousCompletion = true;
          if ($scope.stipend.type === POHI) {
            $scope.display.maxAbsences = true;
          }
        }
        if ([DOKTOR, POHI].concat($scope.formState.scholarShipTypes).indexOf($scope.stipend.type) !== -1) {
          $scope.required.applicationAndPaymentPeriod = true;
        }
        if ([DOKTOR].concat($scope.formState.scholarShipTypes).indexOf($scope.stipend.type) !== -1) {
          $scope.display.studyLoad = true;
          $scope.required.matriculationStart = true;
        } else {
          $scope.display.studyForm = true;
          $scope.display.isTeacherConfirm = true;
          $scope.display.isStudyBacklog = true;
          if ([POHI, SOIDU, ERI].indexOf($scope.stipend.type) !== -1) {
            $scope.required.studyCourses = true;
            if ($scope.stipend.type !== ERI) {
              $scope.required.studyForms = true;
            }
          }
          if ($scope.stipend.type === ERI) {
            $scope.display.isFamilyIncomes = true;
          } else if ($scope.stipend.type === SOIDU) {
            $scope.display.isTeacherConfirmRead = true;
            $scope.display.isStudyBacklogRead = true;
            $scope.stipend.isStudyBacklog = false;
            $scope.stipend.isTeacherConfirm = true;
          } else {
            $scope.required.matriculationStart = true;
          }
        }
        if ([DOKTOR, SOIDU].indexOf($scope.stipend.type) !== -1) {
          $scope.stipend.isAcademicLeave = false;
          $scope.display.isAcademicLeaveRead = true;
        }
      }
    }
  }
]);
