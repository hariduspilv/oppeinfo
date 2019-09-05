'use strict';

angular.module('hitsaOis').controller('BoardingSchoolSearchController', ['$scope', 'QueryUtils',
  function ($scope, QueryUtils) {
    $scope.currentNavItem = 'boarding.school.search';
    var baseUrl = '/boardingSchools';

    $scope.formState = {
      xlsUrl: 'boardingSchools/search.xlsx'
    };

    QueryUtils.createQueryForm($scope, baseUrl, {order: 'p.lastname, p.firstname', showValid: true}, function () {
      $scope.showNeighbours = $scope.criteria.showNeighbours;
    });

    $scope.loadData();
  }
]).controller('BoardingSchoolManagementController', ['$scope', '$timeout', '$q', 'ArrayUtils', 'DataUtils', 'FormUtils', 'QueryUtils', 'dialogService', 'message',
  function ($scope, $timeout, $q, ArrayUtils, DataUtils, FormUtils, QueryUtils, dialogService, message) {
    $scope.currentNavItem = 'boarding.school.management';
    var baseUrl = '/boardingSchools/management';

    $scope.rooms = QueryUtils.endpoint('/autocomplete/roomsAsList').query({isDormitory: true});
    QueryUtils.createQueryForm($scope, baseUrl, {order: 'p.lastname, p.firstname', size: 100}, function (resultData) {
      if (angular.isDefined($scope.boardingSchools) && $scope.boardingSchools.$dirty === true) {
        $scope.boardingSchools.$setPristine();
      }

      resultData.forEach(function (student) {
        if (student.latestDorm) {
          DataUtils.convertStringToDates(student.latestDorm, ['validFrom', 'validThru']);
          student.latestDormCopy = angular.copy(student.latestDorm);
        }
      });
    }, true);

    $scope.copiedCriteria = {};
    var _loadData = $scope.loadData;
    $scope.loadData = function () {
      FormUtils.withValidForm($scope.searchForm, function () {
        angular.copy($scope.criteria, $scope.copiedCriteria);
        _loadData();
      });
    };

    $q.all().then($scope.loadData);

    // uses the same solution as student.card.js
    var ignoreWindow = false;
    $scope.changePage = function () {
      if (ignoreWindow) {
        return;
      }
      if (angular.isDefined($scope.boardingSchools) && $scope.boardingSchools.$dirty === true ) {
        dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
          $scope.loadData();
        }, function () {
          ignoreWindow = true;
          angular.copy($scope.copiedCriteria, $scope.criteria);
          $timeout(function () {
            ignoreWindow = false;
          }, 50);
        });
      } else {
        $scope.loadData();
      }
    };

    $scope.roomChanged = function (row) {
      if (row.latestDorm.room === '') {
        row.latestDorm = {};
      }
    };

    $scope.addNew = function (row) {
      row.previousDorms.splice(0, 0, row.latestDormCopy);
      row.latestDorm = {};
      row.addedToPrevious = true;
      $scope.boardingSchools.$setDirty();
    };

    $scope.restorePreviousDorm = function (row) {
      row.latestDorm = angular.copy(row.latestDormCopy);
      row.previousDorms.splice(0, 1);
      row.addedToPrevious = false;

      // HACK: when restoring previous dorm after already choosing a value from room select,
      // valid from and through are shown as invalid (required) even though values exist and save succeeds
      $timeout(function () {
        row.latestDorm.validFrom = angular.copy(row.latestDormCopy.validFrom);
        row.latestDorm.validThru = angular.copy(row.latestDormCopy.validThru);
      });
    };

    $scope.previousDormsDialog = function (student) {
      dialogService.showDialog('boardingSchool/boarding.school.previous.places.dialog.html', function (dialogScope) {
        dialogScope.student = student;

        dialogScope.removePreviousDorm = function (row) {
          ArrayUtils.remove(dialogScope.student.previousDorms, row);
          $scope.boardingSchools.$setDirty();
        };
      });
    };

    $scope.save = function () {
      FormUtils.withValidForm($scope.boardingSchools, function () {
        QueryUtils.endpoint(baseUrl + '/check').post($scope.tabledata.content).$promise.then(function (result) {
          var invalidRows = result.filter(function (r) {
            return r.occupied || r.duplicateStudents;
          });

          if (invalidRows.length === 0) {
            save();
          } else {
            dialogService.showDialog('boardingSchool/boarding.school.check.dialog.html', function (dialogScope) {
              dialogScope.content = invalidRows;

              dialogScope.save = function () {
                save();
              };
            });
          }
        });
      });
    };

    function save() {
      QueryUtils.endpoint(baseUrl).save($scope.tabledata.content).$promise.then(function () {
        message.info("main.messages.update.success");
        $scope.loadData();
      });
    }
  }
]).controller('BoardingSchoolRoomsController', ['$scope', 'QueryUtils',
  function ($scope, QueryUtils) {
    $scope.currentNavItem = 'boarding.school.rooms';
    var baseUrl = '/boardingSchools/rooms';

    QueryUtils.createQueryForm($scope, baseUrl, {order: 'b_code, r_code', showValid: true}, function() {
      $scope.showResidents = $scope.criteria.showResidents;
    });
    $scope.loadData();
  }
]);