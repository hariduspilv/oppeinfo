'use strict';

angular.module('hitsaOis')
.controller('SchoolCapacityTypeController', ['$route', '$q', '$scope', 'Classifier', 'QueryUtils', 'message', 'USER_ROLES', 'AuthService', 'dialogService',
  function ($route, $q, $scope, Classifier, QueryUtils, message, USER_ROLES, AuthService, dialogService) {
    var baseUrl = '/schoolCapacityType';
    $scope.auth = $route.current.locals.auth;
    $scope.isHigher = $scope.auth.higher && !$scope.auth.vocational;
    $scope.currentNavItem = 'school.capacityType' + ($scope.isHigher ? '.higher' : '.vocational');
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOLIIK);
    $scope.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query(function (result) {
      $scope.studyYearMap = result.reduce(function(mapped, studyYear) {
        mapped[studyYear.id] = studyYear;
        return mapped;
      }, {});
    });
    Classifier.queryForDropdown({mainClassCode: 'MAHT', order: 'code'}).$promise.then(function (result) {
      $scope.capacityMap = result.reduce(function(mapped, capacity) {
        mapped[capacity.code] = capacity;
        return mapped;
      }, {});
    });
    $scope.coefficients = Classifier.queryForDropdown({mainClassCode: 'KOEFITSIENT', order: 'code'});

    function loadCapacityTypes() {
      $scope.capacityTypes = QueryUtils.endpoint(baseUrl).query({ isHigher: $scope.isHigher });
    }
    loadCapacityTypes();

    $scope.switchHigher = function (value) {
      $scope.isHigher = value;
      loadCapacityTypes();
    };

    $scope.updateAllCheckBoxes = function (value) {
      $scope.capacityTypes.forEach(function (capacityType) {
        capacityType.isUsable = value;
      });
    };

    $scope.isUsableChanged = function (capacityType) {
      if (!capacityType.isUsable && capacityType.id) {
        capacityType.isTimetable = false;
        dialogService.confirmDialog(
          { prompt: $scope.auth.higher ? 'schoolCapacityType.removeUsableConfirmHigher' : 'schoolCapacityType.removeUsableConfirmVocational' }, function () {
          capacityType.loads.forEach(function (load) {
            load.loadPercentage = null;
          });
          QueryUtils.endpoint(baseUrl + '/loads').save({ id: capacityType.id, loads: capacityType.loads}, function () {
            $scope.update();
          });
        });
      }
    };

    $scope.update = function() {
      QueryUtils.endpoint(baseUrl).save({ isHigher: $scope.isHigher, capacities: $scope.capacityTypes }, function () {
        message.updateSuccess();
        loadCapacityTypes();
      });
    };

    $scope.changeLoads = function (capacityType) {
      dialogService.showDialog('school/school.capacity.type.load.dialog.html', function (dialogScope) {
        dialogScope.capacityType = $scope.capacityMap[capacityType.typeCode];
        dialogScope.coefficients = $scope.coefficients;

        var coefSource = dialogScope.coefficients.reduce(function (acc, obj) {
          acc[obj.code] = null;
          return acc;
        }, {});

        var loadsPerYear = capacityType.loads.reduce(function(mapped, load) {
          if (!mapped[load.studyYearId]) {
            mapped[load.studyYearId] = angular.copy(coefSource, {});
          }
          mapped[load.studyYearId][load.coefficient] = load.loadPercentage;
          return mapped;
        }, {});

        dialogScope.studyYears = [];
        $scope.studyYears.forEach(function (studyYear) {
          dialogScope.studyYears.push({
            studyYearId: studyYear.id,
            startDate: studyYear.startDate,
            loads: loadsPerYear[studyYear.id]
          });
        });
        dialogScope.studyYearMap = $scope.studyYearMap;
      }, function (dialogScope) {
        QueryUtils.endpoint(baseUrl + '/loads').save({
            id: capacityType.id,
            loads: (dialogScope.studyYears||[]).reduce(function (acc, it) {
              for (var loadCoef in it.loads) {
                if (!it.loads.hasOwnProperty(loadCoef)) {
                  continue;
                }
                acc.push({
                  studyYearId: it.studyYearId,
                  loadPercentage: it.loads[loadCoef],
                  coefficient: loadCoef
                })
              }
              return acc;
            }, [])
          }, function () {
          message.updateSuccess();
          loadCapacityTypes();
        });
      });
    };
  }]);
