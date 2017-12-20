'use strict';

angular.module('hitsaOis').controller('ScholarshipApplicationController', ['Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', '$q', '$sessionStorage',
  function (Classifier, $scope, $location, message, QueryUtils, $route, DataUtils, $q, $sessionStorage) {
    var baseUrl = '/scholarships';
    $scope.criteria = {};
    $scope.formState = {};
    $scope.formState.allowedStipendTypes = $route.current.locals.params.allowedStipendTypes;

    $scope.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    var clMapper = Classifier.valuemapper({
      status: 'STIPTOETUS_STAATUS',
      type: 'STIPTOETUS',
      compensationFrequency: 'STIPTOETUS_HYVITAMINE',
      compensationReason: 'STIPTOETUS_HYVITAMINE_POHJUS'
    });

    var promises = clMapper.promises;
    promises.push($scope.studyPeriods.$promise);

    $q.all(promises).then(function () {
      $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
      if(!('_menu' in $route.current.params)) {
        $scope.fromStorage = function(key) {
          return JSON.parse($sessionStorage[key] || '{}');
        };
        var storedCriteria = $scope.fromStorage(baseUrl + '/applications');
        if(angular.isNumber(storedCriteria.page)) {
          storedCriteria.page = storedCriteria.page + 1;
        }
        angular.extend($scope.criteria, storedCriteria);
      }
    });

    $scope.reloadTable = function () {
      $scope.applicationSearchForm.$setSubmitted();
      if (!$scope.applicationSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      QueryUtils.endpoint(baseUrl + '/applications').query($scope.criteria, function (result) {
        if (angular.isArray(result)) {
          if (result.length < 1) {
            message.error('main.messages.error.notFound');
          }
        }
        $scope.submittedType = $scope.criteria.type;
        $scope.applications = clMapper.objectmapper(result);
      });
    };

    $scope.updateAllApplicationCheckBoxes = function (value) {
      $scope.applications.forEach(function (it) {
        it._selected = value;
      });
    };

    $scope.clearCriteria = function () {
      $scope.criteria = {};
    };

    function chosenApplications() {
      return $scope.applications.filter(function (it) {
        return it._selected;
      }).map(function (it) {
        return it.id;
      });
    }

    function checkSelected() {
      return chosenApplications().length > 0;
    }

    $scope.accept = function () {
      if (checkSelected()) {
        QueryUtils.endpoint(baseUrl + '/acceptApplications').put({
          applications: chosenApplications()
        }, function () {
          $scope.reloadTable();
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    $scope.annul = function () {
      if (checkSelected()) {
        QueryUtils.endpoint(baseUrl + '/annulApplications').put({
          applications: chosenApplications()
        }, function () {
          $scope.reloadTable();
        });
      } else {
        message.error('stipend.messages.error.noStudentsSelected');
      }
    };

    var previousType;
    $scope.resetCurriculum = function () {
      if (previousType === 'STIPTOETUS_POHI') {
        $scope.criteria.curriculum = [];
      }
      previousType = $scope.criteria.type;
    };
  }
]);
