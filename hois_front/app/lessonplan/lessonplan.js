(function () {
  'use strict';

  function rowSum(row) {
    return row.reduce(function(sum, item) {
      if(item !== null && isFinite(item) && item > 0) {
        sum += item;
      }
      return sum;
    }, 0);
  }

  function createTotalsRow(scope) {
    var row = [];
    for(var i = 0, cnt = scope.formState.weekNrs.length; i < cnt; i++) {
      row[i] = 0;
    }
    return row;
  }

  function updateRowTotals(scope, journal, capacityType) {
    var sum = rowSum(journal.hours[capacityType]);
    scope.formState.journalTotals[journal.id][capacityType] = sum;
  }

  function updateJournalTotals(scope, journal, index) {
    var sum = scope.formState.capacityTypes.reduce(function(sum, c) {
      var hours = journal.hours[c.code];
      if(hours !== undefined) {
        var item = hours[index];
        if(item !== null && isFinite(item) && item > 0) {
          sum += item;
        }
      }
      return sum;
    }, 0);
    var totals = scope.formState.journalTotals[journal.id];
    totals._[index] = sum;
    totals.__ = rowSum(totals._);
  }

  function updateTotals(scope, totals, capacityType, index) {
    totals._[capacityType] = rowSum(totals[capacityType]);
    var sum = scope.formState.capacityTypes.reduce(function(sum, c) {
      var hours = totals[c.code];
      if(hours !== undefined) {
        sum += hours[index];
      }
      return sum;
    }, 0);
    totals._._[index] = sum;
    totals.__ = rowSum(totals._._);
  }

angular.module('hitsaOis').controller('LessonplanSearchController', ['$location', '$mdDialog', '$route', '$scope', 'DataUtils', 'QueryUtils', 'Session',

  function ($location, $mdDialog, $route, $scope, DataUtils, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;
    var baseUrl = '/lessonplans';

    $scope.newLessonplan = function() {
      var formState = $scope.formState;
      var studyYear = $scope.criteria.studyYear;

      $mdDialog.show({
        controller: function($scope) {
          $scope.formState = formState;
          $scope.record = {studyYear: studyYear};

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

    function filterStudentGroups(existing) {
      return function(it) {
        return existing.indexOf(it.id) === -1;
      };
    }

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
        studentgroups[syid] = allstudentgroups.filter(filterStudentGroups(existing));
      }
      $scope.formState.studentGroups = allstudentgroups;
      $scope.formState.studentGroupMap = studentgroups;
      $scope.formState.curriculumVersions = result.curriculumVersions;
    });

    QueryUtils.createQueryForm($scope, baseUrl, {});
  }
]).controller('LessonplanTeacherSearchController', ['$route', '$scope', 'DataUtils', 'QueryUtils', 'Session',

  function ($route, $scope, DataUtils, QueryUtils, Session) {
    var school = Session.school || {};
    $scope.formState = {higher: school.higher, vocational: school.vocational};
    $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

    var baseUrl = '/lessonplans/byteacher';

    $scope.formState.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
    $scope.formState.studyYears.$promise.then(function() {
      if(!$scope.criteria.studyYear) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if(sy) {
          $scope.criteria.studyYear = sy.id;
        }
      }
      if($scope.criteria.studyYear) {
        $scope.loadData();
      }
    });

    QueryUtils.createQueryForm($scope, baseUrl, {});
  }
]).controller('LessonplanEditController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',

  function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/lessonplans';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'})};
    $scope.keys = Object.keys;

    $scope.getCapacityTypes = function(hours) {
      return $scope.formState.capacityTypes.filter(function(ct) { return hours !== undefined && hours[ct.code] !== undefined; });
    };

    function updateModuleTotals(module, capacityType, index) {
      var moduleTotals = $scope.formState.moduleTotals[module.id];
      var sum = module.journals.reduce(function(sum, journal) {
        var hours = journal.hours[capacityType];
        if(hours !== undefined && hours[index] !== undefined) {
          sum += hours[index];
        }
        return sum;
      }, 0);
      moduleTotals[capacityType][index] = sum;
      updateTotals($scope, moduleTotals, capacityType, index);
    }

    function updateGrandTotals(capacityType, index) {
      var sum = Object.keys($scope.formState.moduleMap).reduce(function(sum, moduleId) {
        var moduleTotals = $scope.formState.moduleTotals[moduleId];
        var hours = moduleTotals[capacityType];
        if(hours !== undefined) {
          sum += hours[index];
        }
        return sum;
      }, 0);
      var grandTotals = $scope.formState.grandTotals;
      grandTotals[capacityType][index] = sum;
      updateTotals($scope, grandTotals, capacityType, index);
    }

    QueryUtils.endpoint(baseUrl).get({id: id}).$promise.then(function(result) {
      $scope.formState.studyPeriods = result.studyPeriods;
      //console.log($scope.formState.studyPeriods);
      $scope.formState.weekNrs = result.weekNrs;
      $scope.formState.legends = result.legends.reduce(function(acc, item) { acc[item.weekNr] = item.color; return acc; }, {});
      delete result.studyPeriods;
      delete result.weekNrs;
      delete result.legends;
      $scope.formState.moduleMap = {};
      $scope.formState.rowTotals = {};
      $scope.formState.journalTotals = {};
      $scope.formState.moduleTotals = {};
      $scope.formState.grandTotals = {_: {_: createTotalsRow($scope)}};
      // initialize totals
      $scope.formState.capacityTypes.$promise.then(function() {
        result.modules.forEach(function(module) {
          $scope.formState.moduleMap[module.id] = module;
          $scope.formState.moduleTotals[module.id] = {_: {_: createTotalsRow($scope)}};
          module.journals.forEach(function(journal) {
            journal.lessonPlanModule = module.id;
            $scope.formState.journalTotals[journal.id] = {_: createTotalsRow($scope)};
            $scope.formState.capacityTypes.forEach(function(c) {
              var capacityType = c.code;
              var hours = journal.hours[capacityType];
              if(hours !== undefined) {
                updateRowTotals($scope, journal, capacityType);
                var moduleTotals = $scope.formState.moduleTotals[module.id];
                if(moduleTotals[capacityType] === undefined) {
                  moduleTotals[capacityType] = createTotalsRow($scope);
                }
                var grandTotals = $scope.formState.grandTotals;
                if(grandTotals[capacityType] === undefined) {
                  grandTotals[capacityType] = createTotalsRow($scope);
                }
              }
            });
            for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              updateJournalTotals($scope, journal, i);
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

      updateRowTotals($scope, journal, capacityType);
      updateJournalTotals($scope, journal, index);
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

    $scope.getLegendByWeek = function(weekNr) {
      return $scope.formState.legends[weekNr];
    };
  }
]).controller('LessonplanTeacherViewController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',

  function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;
    var studyYearId = $route.current.params.studyYear;
    var baseUrl = '/lessonplans/byteacher';
    $scope.formState = {capacityTypes: Classifier.queryForDropdown({mainClassCode: 'MAHT'}), showWeeks: true, showCapacityTypes: true};
    $scope.keys = Object.keys;

    $scope.getCapacityTypes = function(hours) {
      return $scope.formState.capacityTypes.filter(function(ct) { return hours !== undefined && hours[ct.code] !== undefined; });
    };

    QueryUtils.endpoint(baseUrl + '/:id/:studyYear').search({id: id, studyYear: studyYearId}).$promise.then(function(result) {
      $scope.formState.studyPeriods = result.studyPeriods;
      $scope.formState.weekNrs = result.weekNrs;
      $scope.formState.rowTotals = {};
      $scope.formState.journalTotals = {};
      $scope.formState.grandTotals = {_: {_: createTotalsRow($scope)}};
      // initialize totals

      $scope.formState.capacityTypes.$promise.then(function() {
        result.journals.forEach(function(journal) {
          $scope.formState.journalTotals[journal.id] = {_: createTotalsRow($scope)};
          $scope.formState.capacityTypes.forEach(function(c) {
            var capacityType = c.code;
            var hours = journal.hours[capacityType];
            if(hours !== undefined) {
              updateRowTotals($scope, journal, capacityType);
              var grandTotals = $scope.formState.grandTotals;
              if(grandTotals[capacityType] === undefined) {
                grandTotals[capacityType] = createTotalsRow($scope);
              }
            }
          });
          for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
            updateJournalTotals($scope, journal, i);
          }
        });

        function calculateHours(i, capacityType) {
          return result.journals.reduce(function(sum, journal){
            var hours = journal.hours[capacityType];
            if(hours !== undefined) {
              sum += hours[i];
            }
            return sum;
          }, 0);
        }

        $scope.formState.capacityTypes.forEach(function(c) {
          var capacityType = c.code;
          var grandTotals = $scope.formState.grandTotals;
          if(grandTotals[capacityType] !== undefined) {
            for(var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              grandTotals[capacityType][i] = calculateHours(i, capacityType);
              updateTotals($scope, grandTotals, capacityType, i);
            }
          }
        });
        // subjects
        $scope.formState.subjectCapacityTypes = [];
        $scope.formState.studyPeriods.forEach(function(sp) {
          $scope.formState.capacityTypes.forEach(function(c) {
            $scope.formState.subjectCapacityTypes.push({studyPeriod: sp.id, code: c.code, value: c.value});
          });
        });
        $scope.record = result;
      });
    });
  }
]);
}());
