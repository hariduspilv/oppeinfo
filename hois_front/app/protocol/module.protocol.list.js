'use strict';

angular.module('hitsaOis').controller('ModuleProtocolListController',
  function ($scope, $route, QueryUtils, Classifier, $q, message, dialogService, sharedProperties, $location) {
  $scope.auth = $route.current.locals.auth;
  $scope.search = {};
  $scope.criteria = {};
  $scope.directiveControllers = [];
  $scope.myModules = myModulesDialog;

  function canCreateProtocol() {
    return ($scope.auth.isAdmin() || $scope.auth.isLeadingTeacher() || $scope.auth.isTeacher()) &&
      $scope.auth.authorizedRoles.indexOf("ROLE_OIGUS_M_TEEMAOIGUS_MOODULPROTOKOLL") !== -1;
  }

  $scope.load = function() {
    if (!$scope.searchForm.$valid) {
      message.error('main.messages.form-has-errors');
      return false;
    } else {
      $scope.loadData();
    }
  };

  $scope.formState = {
    canCreateProtocol: canCreateProtocol()
  };

  var clMapper = Classifier.valuemapper({ status: 'PROTOKOLL_STAATUS' });
  QueryUtils.createQueryForm($scope, '/moduleProtocols', {}, clMapper.objectmapper);

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });

  $scope.$watch('criteria.moduleObject', function() {
      $scope.criteria.module = $scope.criteria.moduleObject ? $scope.criteria.moduleObject.id : null;
    }
  );

  $scope.clearSearch = function () {
    $scope.clearCriteria();
    $scope.search = {};
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

  $scope.curriculumVersionModule = function(row) {
    var result = '';
    result += row.curriculumVersions.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    result += ', ';
    result += row.curriculumVersionOccupationModules.map(function(it) {return $scope.currentLanguageNameField(it);}).join('; ');
    return result;
  };

  function myModulesDialog() {
    dialogService.showDialog("protocol/module.protocol.myModules.dialog.html", dialogController, undefined, undefined, true);

    function dialogController(dialogScope) {
      var studyYearId = angular.copy($scope.criteria.studyYear);
      dialogScope.loadModuleHistory = moduleHistoryDialog;
      dialogScope.addModuleProtocol = addModuleProtocol;

      QueryUtils.endpoint('/autocomplete/studyYears').query({}).$promise.then(function (results) {
        if (results && results.length) {
          dialogScope.studyYear = results.find(function (element) {
            return element.id === studyYearId;
          });
        }
      });

      QueryUtils.createQueryForm(dialogScope, "/moduleProtocols/myModules?studyYear=" + studyYearId);
      dialogScope.loadData();

      function addModuleProtocol(row) {
        var defaultFields = {
          studyYear: studyYearId,
          curriculumVersion: row.curriculumVersion,
          module: row.module
        };
        sharedProperties.getProperties()['module.protocol.default.fields'] = defaultFields;
        dialogScope.cancel();
        $location.url('/moduleProtocols/new');
      }
    }
  }

  function moduleHistoryDialog(moduleId) {
    dialogService.showDialog("protocol/module.protocol.module.history.dialog.html", dialogController, undefined, undefined, true);

    function dialogController(dialogScope) {
      var HOURS_PER_EKAP = 26;
      var clStudyYearMapper = Classifier.valuemapper({
        year: 'OPPEAASTA'
      });
      QueryUtils.endpoint('/moduleProtocols/moduleHistory/' + moduleId).get().$promise.then(function (result) {
        dialogScope.maxPeriods = 0;
        dialogScope.result = result;
        dialogScope.result.lessonPlanModules.forEach(function (row) {
          clStudyYearMapper.objectmapper(row.studyYear);
          row.mappedHoursByPeriod = {};
          row.mappedHoursByJournal = {};
          row.totalHours = 0.0;
          for (var jId in row.mappedHours) {
            var periodCounter = 0;
            for (var pId in row.mappedHours[jId]) {
              periodCounter++;
              if (row.mappedHours[jId][pId]) {
                row.mappedHours[jId][pId] = Math.round((row.mappedHours[jId][pId] / HOURS_PER_EKAP) * 10) / 10;
                row.mappedHoursByPeriod[pId] = row.mappedHoursByPeriod[pId] ?
                  row.mappedHoursByPeriod[pId] + row.mappedHours[jId][pId] :
                  row.mappedHours[jId][pId];
                row.mappedHoursByJournal[jId] = row.mappedHoursByJournal[jId] ?
                  row.mappedHoursByJournal[jId] + row.mappedHours[jId][pId] :
                  row.mappedHours[jId][pId];
                row.totalHours += row.mappedHours[jId][pId];
              }
            }
            if (dialogScope.maxPeriods < periodCounter) {
              dialogScope.maxPeriods = periodCounter;
            }
          }
        });
      });
    }
  }
});
