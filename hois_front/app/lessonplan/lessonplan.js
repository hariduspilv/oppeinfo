'use strict';

angular.module('hitsaOis').controller('LessonplanSearchController', ['$location', '$mdDialog', '$route', '$scope', 'DataUtils', 'QueryUtils', 'Session',
  function ($location, $mdDialog, $route, $scope, DataUtils, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

    var baseUrl, criteria;
    if($scope.currentNavItem === 'lessonplan.vocational') {
      baseUrl = '/lessonplans';
      criteria = {};
      $scope.newLessonplan = function() {
        var formState = $scope.formState;
        $mdDialog.show({
          controller: function($scope) {
            $scope.formState = formState;
            $scope.record = {};

            $scope.create = function() {
              QueryUtils.endpoint(baseUrl).save($scope.record).$promise.then(function(result) {
                $mdDialog.hide();
                $location.url(baseUrl+'/vocational/'+result.id+'/edit');
              });
            };
            $scope.cancel = $mdDialog.hide;
          },
          templateUrl: 'lessonplan/new.lessonplan.dialog.html',
          clickOutsideToClose: true
        });
      };
      QueryUtils.endpoint(baseUrl+'/searchFormData').search().$promise.then(function(result) {
        var studyYears = result.studyYears;
        $scope.formState.studyYears = studyYears;
        if(!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if(sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if($scope.criteria.studyYear) {
          $scope.loadData();
        }
        var allstudentgroups = result.studentGroups;
        var existingplans = result.studentGroupMapping;
        var studentgroups = {};
        for(var i = 0, cnt = studyYears.length; i < cnt; i++) {
          var syid = studyYears[i].id;
          var existing = existingplans[syid] || [];
          studentgroups[syid] = allstudentgroups.filter(function(it) { return existing.indexOf(it.id) === -1; });
        }
        $scope.formState.studentgroups = studentgroups;
      });
    } else if($scope.currentNavItem === 'lessonplan.vocational-byteacher') {
      baseUrl = '/lessonplans/byteacher';
      criteria = {};
    }

    QueryUtils.createQueryForm($scope, baseUrl, criteria);
  }
]).controller('LessonplanEditController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/lessonplans';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    $scope.Object = Object;

    function rowSum(row) {
      return row.reduce(function(acc, item) { if(item !== null && isFinite(item) && item > 0) { acc += item; } return acc; }, 0);
    }

    function createTotalsRow() {
      var row = [];
      for(var i = 0, cnt = $scope.formState.weekNrs.length; i < cnt; i++) {
        row[i] = 0;
      }
      return row;
    }

    function updateRowTotals(journal, capacityType) {
      var sum = rowSum(journal.hours[capacityType]);
      $scope.formState.journalTotals[journal.id][capacityType] = sum;
    }

    function updateJournalTotals(journal, index) {
      var sum = 0;
      $scope.formState.capacityTypes.forEach(function(c) {
        var hours = journal.hours[c.code];
        if(hours !== undefined) {
          var item = hours[index];
          if(item !== null && isFinite(item) && item > 0) {
            sum += item;
          }
        }
      });
      var totals = $scope.formState.journalTotals[journal.id];
      totals._[index] = sum;
      totals.__ = rowSum(totals._);
    }

    function updateModuleTotals(module, capacityType, index) {
      var moduleTotals = $scope.formState.moduleTotals[module.id];
      var sum = 0;
      module.journals.forEach(function(journal) {
        var hours = journal.hours[capacityType];
        if(hours !== undefined && hours[index] !== undefined) {
          sum += hours[index];
        }
      });
      moduleTotals[capacityType][index] = sum;
      moduleTotals._[capacityType] = rowSum(moduleTotals[capacityType]);
      sum = 0;
      $scope.formState.capacityTypes.forEach(function(c) {
        var capacityType = c.code;
        var hours = moduleTotals[capacityType];
        if(hours !== undefined) {
          sum += hours[index];
        }
      });
      moduleTotals._._[index] = sum;
      moduleTotals.__ = rowSum(moduleTotals._._);
    }

    function updateGrandTotals(capacityType, index) {
      var sum = 0;
      Object.keys($scope.formState.moduleMap).forEach(function(moduleId) {
        var moduleTotals = $scope.formState.moduleTotals[moduleId];
        var hours = moduleTotals[capacityType];
        if(hours !== undefined) {
          sum += hours[index];
        }
      });
      var grandTotals = $scope.formState.grandTotals;
      grandTotals[capacityType][index] = sum;
      grandTotals._[capacityType] = rowSum(grandTotals[capacityType]);
      sum = 0;
      $scope.formState.capacityTypes.forEach(function(c) {
        var capacityType = c.code;
        var hours = grandTotals[capacityType];
        if(hours !== undefined) {
          sum += hours[index];
        }
      });
      grandTotals._._[index] = sum;
      grandTotals.__ = rowSum(grandTotals._._);
    }

    QueryUtils.endpoint(baseUrl).get({id: id}).$promise.then(function(result) {
      $scope.formState.studyPeriods = result.studyPeriods;
      $scope.formState.weekNrs = result.weekNrs;
      delete result.studyPeriods;
      delete result.weekNrs;
      $scope.formState.moduleMap = {};
      $scope.formState.rowTotals = {};
      $scope.formState.journalTotals = {};
      $scope.formState.moduleTotals = {};
      $scope.formState.grandTotals = {_: {_: createTotalsRow()}};
      // initialize totals
      result.modules.forEach(function(module) {
        $scope.formState.moduleMap[module.id] = module;
        $scope.formState.moduleTotals[module.id] = {_: {_: createTotalsRow()}};
        module.journals.forEach(function(journal) {
          $scope.formState.journalTotals[journal.id] = {_: createTotalsRow()};
          $scope.formState.capacityTypes.forEach(function(c) {
            var capacityType = c.code;
            var hours = journal.hours[capacityType];
            if(hours !== undefined) {
              updateRowTotals(journal, capacityType);
              var moduleTotals = $scope.formState.moduleTotals[module.id];
              if(moduleTotals[capacityType] === undefined) {
                moduleTotals[capacityType] = createTotalsRow();
              }
              var grandTotals = $scope.formState.grandTotals;
              if(grandTotals[capacityType] === undefined) {
                grandTotals[capacityType] = createTotalsRow();
              }
            }
          });
          for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
            updateJournalTotals(journal, i);
          }
        });

        $scope.formState.capacityTypes.forEach(function(c) {
          var capacityType = c.code;
          var moduleTotals = $scope.formState.moduleTotals[module.id];
          if(moduleTotals[capacityType] !== undefined) {
            for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              updateModuleTotals(module, capacityType, i);
            }
          }
        });
      });
      $scope.formState.capacityTypes.forEach(function(c) {
        var capacityType = c.code;
        var grandTotals = $scope.formState.grandTotals;
        if(grandTotals[capacityType] !== undefined) {
          for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
            updateGrandTotals(capacityType, i);
          }
        }
      });
      $scope.record = result;
    });

    $scope.updateTotals = function(journal, capacityType, index) {
      var hours = journal.hours[capacityType];
      var value = parseInt(hours[index], 10);
      if(isNaN(value) || value < 0) {
        value = undefined;
      }
      if(value !== hours[index]) {
        hours[index] = value;
      }

      updateRowTotals(journal, capacityType);
      updateJournalTotals(journal, index);
      updateModuleTotals($scope.formState.moduleMap[journal.lessonPlanModule], capacityType, index);
      updateGrandTotals(capacityType, index);
    };

    $scope.newJournal = function(module) {
      $location.url(baseUrl + '/journals/new?_noback&lessonPlanModule=' + module.id);
    };

    $scope.editJournal = function(journal) {
      $location.url(baseUrl + '/journals/' + journal.id + '/edit?_noback&lessonPlanModule=' + journal.lessonPlanModule);
    };

    $scope.update = function() {
      $scope.record.$update().then(message.updateSuccess);
    };
  }
]).controller('LessonplanJournalEditController', ['$location', '$mdDialog', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($location, $mdDialog, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var lessonPlanModule = $route.current.params.lessonPlanModule;
    var baseUrl = '/lessonplans/journals';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    $scope.record = QueryUtils.endpoint(baseUrl).get({id: id || 'new', lessonPlanModule: lessonPlanModule});
    $scope.record.$promise.then(function(result) {
      $scope.formState.capacityTypes.$promise.then(function() {
        Classifier.setSelectedCodes($scope.formState.capacityTypes, $scope.record.journalCapacityTypes);
      });

      $scope.formState.themes = result.themes;
      delete result.themes;
      $scope.formState.themeMap = $scope.formState.themes.reduce(function(acc, item) { acc[item.id] = item; return acc; }, {});
    });

    function formIsValid() {
      $scope.journalForm.$setSubmitted();
      if(!$scope.journalForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }
      return true;
    }

    $scope.update = function() {
      if(!formIsValid()) {
        return;
      }

      $scope.record.journalCapacityTypes = Classifier.getSelectedCodes($scope.formState.capacityTypes);
      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback&lessonPlanModule='+lessonPlanModule);
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'journal.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/lessonplans/vocational/'+$scope.record.lessonPlan+'/edit');
        });
      });
    };

    $scope.addTheme = function() {
      var themeId = $scope.formState.theme;
      if($scope.record.journalOccupationModuleThemes.indexOf(themeId) !== -1) {
        message.error('lessonplan.journal.duplicatetheme');
        return;
      }
      $scope.record.journalOccupationModuleThemes.push(themeId);
      $scope.formState.theme = null;
    };

    $scope.deleteTheme = function(themeId) {
      var index = $scope.record.journalOccupationModuleThemes.indexOf(themeId);
      if(index !== -1) {
        $scope.record.journalOccupationModuleThemes.splice(index, 1);
      }
    };
  }
]);
