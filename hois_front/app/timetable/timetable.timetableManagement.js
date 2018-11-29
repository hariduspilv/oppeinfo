'use strict';

angular.module('hitsaOis').controller('TimetableManagementController', 
  function ($rootScope, $window, $scope, $location, $timeout, message, QueryUtils, DataUtils, Classifier, dialogService, USER_ROLES, AuthService, oisFileService) {
    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNNIPLAAN);
    var baseUrl = '/timetables';

    var clMapper = Classifier.valuemapper({status: 'TUNNIPLAAN_STAATUS'});
    QueryUtils.createQueryForm($scope, baseUrl + "/searchTimetableForManagement", {order: '3, 4'}, clMapper.objectmapper);

    $scope.formState = {xlsDiffUrl: 'timetables/timetableDifference.xls', xlsPlanUrl: 'timetables/timetablePlan.xlsx'};

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.timetableSearchForm.$setSubmitted();
      if(!$scope.timetableSearchForm.$valid) {
        message.error('main.messages.form-has-errors');
      } else {
        loadData();
      }
    };

    $scope.openAddFileDialog = function (studyPeriod, startDate) {
      dialogService.showDialog('timetable/dialog/file.import.dialog.html', function(scope){
      }, function (submitScope) {
        var data = submitScope.data;
        oisFileService.getFromLfFile(data.file[0], function(file) {
            data.oisFile = file;
            var ImportTimetableEndpoint = QueryUtils.endpoint('/timetables/importXml/' + studyPeriod);
            var newFile = new ImportTimetableEndpoint(data.oisFile);
            newFile.$save(startDate).then(function(response) {
                if (response.status != null) {
                  message.error(response.status);
                }
                message.info('main.messages.create.success');
            }).catch(angular.noop);
        });
      });
    };

    $scope.exportTimetable = function(url, params) {
      QueryUtils.endpoint("/timetables/exportTimetableCheck").search(params).$promise.then(function (response) {
        if (!response._error) {
          $window.location.href = $rootScope.excel(url, params);
        }
      });
    };

    function filterStudyPeriods() {
      $scope.formState.studyPeriods = $scope.allStudyPeriods.filter(function (t) {
        return t.studyYear === $scope.criteria.studyYear;
      });
    }

    QueryUtils.endpoint(baseUrl + '/managementSearchFormData').search().$promise.then(function (result) {
      $scope.formState.studyYears = result.studyYears;
      $scope.allStudyPeriods = result.studyPeriods;
      var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
      if (sy) {
        $scope.criteria.studyYear = sy.id;
        filterStudyPeriods();
        $scope.criteria.studyPeriod = result.currentStudyPeriod;
      }
      $scope.higher = result.higher;
      $scope.vocational = result.vocational;
      if (!$scope.higher || !$scope.vocational) {
        if ($scope.higher) {
          $scope.criteria.type = 'TUNNIPLAAN_LIIK_H';
        }
      }
      if($scope.criteria.studyYear && $scope.criteria.studyPeriod) {
        $timeout($scope.loadData);
      } else if($scope.formState.studyYears.length === 0) {
        message.error('studyYear.missing');
      } else if($scope.allStudyPeriods.length === 0) {
        message.error('studyYear.studyPeriod.missing');
      }
    });

    $scope.$watch('criteria.studyYear', function () {
      if ($scope.criteria.studyYear !== undefined && $scope.allStudyPeriods) {
        filterStudyPeriods();
        if (!studyPeriodBelongsToStudyYear($scope.criteria.studyPeriod)) {
          $scope.criteria.studyPeriod = null;
        }
      }
    });

    function studyPeriodBelongsToStudyYear(studyPeriodId) {
      for (var i = 0; i < $scope.formState.studyPeriods.length; i++) {
        var studyPeriod = $scope.formState.studyPeriods[i];
        if (studyPeriod.id === studyPeriodId) {
          return true;
        }
      }
      return false;
    } 

    $scope.copyTimetable = function (rowId) {
      QueryUtils.endpoint(baseUrl + '/getPossibleTargetsForCopy').query({id: rowId}).$promise.then(function (possibleTargets) {
        possibleTargets.forEach(function (element) {
          element.formattedStart = new Date(element.start);
        });
        dialogService.showDialog('timetable/timetable.timetableManagement.copyTimetable.html', function (dialogScope) {
          dialogScope.copyTargets = possibleTargets;
        }, function (submittedDialogScope) {
          var query = {
            start: submittedDialogScope.copyTarget.start,
            higher: submittedDialogScope.copyTarget.isHigher,
            id: submittedDialogScope.copyTarget.id,
            originalTimetable: rowId,
            studyPeriod: submittedDialogScope.copyTarget.studyPeriod
          };
          QueryUtils.endpoint(baseUrl + '/copyTimetable').search(query).$promise.then(function () {
            message.info('main.messages.create.success');
            $scope.loadData();
          });
        });
      });
    };

    $scope.createEvent = function (row) {
      var Endpoint = QueryUtils.endpoint(baseUrl);
      var timetable = new Endpoint();
      timetable.code = row.isHigher ? 'TUNNIPLAAN_LIIK_H' : 'TUNNIPLAAN_LIIK_V';
      timetable.studyPeriod = row.studyPeriod;
      timetable.startDate = row.start;
      timetable.endDate = row.end;
      timetable.$save().then(function (result) {
        $location.url('/timetable/' + result.id + '/view');
      });
    };
  });
