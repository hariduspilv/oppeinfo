(function () {
  'use strict';

  function rowSum(row) {
    return row.reduce(function (sum, item) {
      if (item !== null && isFinite(item) && item > 0) {
        sum += item;
      }
      return sum;
    }, 0);
  }

  function createTotalsRow(scope) {
    var row = [];
    for (var i = 0, cnt = scope.formState.weekNrs.length; i < cnt; i++) {
      row[i] = 0;
    }
    return row;
  }

  function updateRowTotals(scope, journal, capacityType) {
    var sum = rowSum(journal.hours[capacityType]);
    scope.formState.journalTotals[journal.id][capacityType] = sum;
  }

  function updateSpRows(scope, journal, capacityType) {
    var hours = journal.hours[capacityType];
    scope.formState.studyPeriods.forEach(function (sp) {
      journal.spHours[capacityType][sp.arrayIndex] = rowSum(hours.slice(sp.weekIndex[0], sp.weekIndex[1]));
    });
  }

  function updateSpJournalTotals(scope, journal) {
    if (!angular.isObject(scope.formState.journalTotals[journal.id]._spTotals)) {
      scope.formState.journalTotals[journal.id]._spTotals = {};
    }
    scope.formState.studyPeriods.forEach(function (sp, spIndex) {
      scope.formState.journalTotals[journal.id]._spTotals[spIndex] = Object.values(journal.spHours).reduce(function (sum, item) {
        if (angular.isObject(item) && !isNaN(item[spIndex])) {
          sum += item[spIndex];
        }
        return sum;
      }, 0);
    });
  }

  function initializeSpGrandTotals(scope, capacityType) {
    if (!angular.isObject(scope.formState.capGrandTotalsSp)) {
      scope.formState.capGrandTotalsSp = {};
      scope.formState.grandTotalsSp = {};
    }
    if (!angular.isObject(scope.formState.capGrandTotalsSp[capacityType])) {
      scope.formState.capGrandTotalsSp[capacityType] = {};
    }
    scope.formState.studyPeriods.forEach(function (sp) {
      if (angular.isArray(scope.formState.grandTotals[capacityType])) {
        scope.formState.capGrandTotalsSp[capacityType][sp.arrayIndex] = rowSum(scope.formState.grandTotals[capacityType].slice(sp.weekIndex[0], sp.weekIndex[1]));
        scope.formState.grandTotalsSp[sp.arrayIndex] = rowSum(scope.formState.grandTotals._._.slice(sp.weekIndex[0], sp.weekIndex[1]));
      }
    });
  }

  function updateJournalTotals(scope, journal, index) {
    var sum = scope.formState.capacityTypes.reduce(function (sum, c) {
      var hours = journal.hours[c.code];
      if (hours !== undefined) {
        var item = hours[index];
        if (item !== null && isFinite(item) && item > 0) {
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
    var sum = scope.formState.capacityTypes.reduce(function (sum, c) {
      var hours = totals[c.code];
      if (hours !== undefined) {
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
      $scope.formState = {
        higher: school.higher,
        vocational: school.vocational
      };
      $scope.currentNavItem = $route.current.$$route.data.currentNavItem;
      var baseUrl = '/lessonplans';

      $scope.newLessonplan = function () {
        var formState = $scope.formState;
        var studyYear = $scope.criteria.studyYear;

        $mdDialog.show({
          controller: function ($scope) {
            $scope.formState = formState;
            $scope.record = {
              studyYear: studyYear
            };

            $scope.create = function () {
              QueryUtils.endpoint(baseUrl).save($scope.record).$promise.then(function (result) {
                $mdDialog.hide();
                $location.url(baseUrl + '/vocational/' + result.id + '/edit');
              });
            };
            $scope.cancel = $mdDialog.hide;
          },
          templateUrl: 'lessonplan/new.lessonplan.dialog.html',
          clickOutsideToClose: true
        });
      };

      function filterStudentGroups(existing) {
        return function (it) {
          return existing.indexOf(it.id) === -1;
        };
      }

      QueryUtils.endpoint(baseUrl + '/searchFormData').search().$promise.then(function (result) {
        var studyYears = result.studyYears;
        $scope.formState.studyYears = studyYears;
        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if ($scope.criteria.studyYear) {
          $scope.loadData();
        }
        var allstudentgroups = result.studentGroups;
        var existingplans = result.studentGroupMapping;
        var studentgroups = {};
        for (var i = 0, cnt = studyYears.length; i < cnt; i++) {
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
      $scope.formState = {
        higher: school.higher,
        vocational: school.vocational
      };
      $scope.currentNavItem = $route.current.$$route.data.currentNavItem;

      var baseUrl = '/lessonplans/byteacher';

      $scope.formState.studyYears = QueryUtils.endpoint('/autocomplete/studyYears').query();
      $scope.formState.studyYears.$promise.then(function () {
        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
        if ($scope.criteria.studyYear) {
          $scope.loadData();
        }
      });

      QueryUtils.createQueryForm($scope, baseUrl, {});
    }
  ]).controller('LessonplanEditController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',

    function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
      var id = $route.current.params.id;
      var baseUrl = '/lessonplans';
      $scope.formState = {
        capacityTypes: Classifier.queryForDropdown({
          mainClassCode: 'MAHT'
        }),
        showWeeks: true
      };
      $scope.keys = Object.keys;

      $scope.getCapacityTypes = function (hours) {
        return $scope.formState.capacityTypes.filter(function (ct) {
          return hours !== undefined && hours[ct.code] !== undefined;
        });
      };

      function updateModuleTotals(module, capacityType, index) {
        var moduleTotals = $scope.formState.moduleTotals[module.id];
        var sum = module.journals.reduce(function (sum, journal) {
          var hours = journal.hours[capacityType];
          if (hours !== undefined && hours[index] !== undefined) {
            sum += hours[index];
          }
          return sum;
        }, 0);
        moduleTotals[capacityType][index] = sum;
        updateTotals($scope, moduleTotals, capacityType, index);
      }

      function updateGrandTotals(capacityType, index) {
        var sum = Object.keys($scope.formState.moduleMap).reduce(function (sum, moduleId) {
          var moduleTotals = $scope.formState.moduleTotals[moduleId];
          var hours = moduleTotals[capacityType];
          if (hours !== undefined) {
            sum += hours[index];
          }
          return sum;
        }, 0);
        var grandTotals = $scope.formState.grandTotals;
        grandTotals[capacityType][index] = sum;
        updateTotals($scope, grandTotals, capacityType, index);
      }

      QueryUtils.endpoint(baseUrl).get({
        id: id
      }).$promise.then(function (result) {
        $scope.formState.studyPeriods = result.studyPeriods;
        var spLocationPointer = 0;
        $scope.formState.studyPeriods.forEach(function (sp, spIndex) {
          sp.weekIndex = [spLocationPointer, spLocationPointer + sp.weekNrs.length];
          sp.arrayIndex = spIndex;
          spLocationPointer += sp.weekNrs.length;
        });
        //console.log($scope.formState.studyPeriods);
        $scope.formState.weekNrs = result.weekNrs;
        $scope.formState.weekBeginningDates = result.weekBeginningDates;
        $scope.formState.legends = result.legends.reduce(function (acc, item) {
          acc[item.weekNr] = item.color;
          return acc;
        }, {});
        delete result.studyPeriods;
        delete result.weekNrs;
        delete result.weekBeginningDates;
        delete result.legends;
        $scope.formState.moduleMap = {};
        $scope.formState.rowTotals = {};
        $scope.formState.journalTotals = {};
        $scope.formState.moduleTotals = {};
        $scope.formState.grandTotals = {
          _: {
            _: createTotalsRow($scope)
          }
        };
        // initialize totals
        $scope.formState.capacityTypes.$promise.then(function () {
          result.modules.forEach(function (module) {
            $scope.formState.moduleMap[module.id] = module;
            $scope.formState.moduleTotals[module.id] = {
              _: {
                _: createTotalsRow($scope)
              }
            };
            module.journals.forEach(function (journal) {
              journal.spHours = {};
              journal.spTotals = {};
              journal.lessonPlanModule = module.id;
              $scope.formState.journalTotals[journal.id] = {
                _: createTotalsRow($scope)
              };
              $scope.formState.capacityTypes.forEach(function (c) {
                var capacityType = c.code;
                journal.spHours[capacityType] = {};
                var hours = journal.hours[capacityType];
                if (hours !== undefined) {
                  updateRowTotals($scope, journal, capacityType);
                  updateSpRows($scope, journal, capacityType);
                  var moduleTotals = $scope.formState.moduleTotals[module.id];
                  if (moduleTotals[capacityType] === undefined) {
                    moduleTotals[capacityType] = createTotalsRow($scope);
                  }
                  var grandTotals = $scope.formState.grandTotals;
                  if (grandTotals[capacityType] === undefined) {
                    grandTotals[capacityType] = createTotalsRow($scope);
                  }
                }
              });
              for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
                updateJournalTotals($scope, journal, i);
              }
              updateSpJournalTotals($scope, journal);
              journal.requiredLessons = {};
              journal.themes.forEach(function (theme) {
                for (var key in theme.hours) {
                  if (angular.isUndefined(journal.requiredLessons[key])) {
                    journal.requiredLessons[key] = 0;
                  }
                  console.log(theme.hours[key]);
                  journal.requiredLessons[key] += theme.hours[key];
                }
              });
            });

            $scope.formState.capacityTypes.forEach(function (c) {
              var capacityType = c.code;
              var moduleTotals = $scope.formState.moduleTotals[module.id];
              if (moduleTotals[capacityType] !== undefined) {
                for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
                  updateModuleTotals(module, capacityType, i);
                }
              }
            });
          });
          $scope.formState.capacityTypes.forEach(function (c) {
            var capacityType = c.code;
            var grandTotals = $scope.formState.grandTotals;
            if (grandTotals[capacityType] !== undefined) {
              for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
                updateGrandTotals(capacityType, i);
              }
            }
            initializeSpGrandTotals($scope, capacityType);
          });
          $scope.record = result;
        });
      });

      $scope.updateTotals = function (journal, capacityType, index) {
        var hours = journal.hours[capacityType];
        var value = parseInt(hours[index], 10);
        if (isNaN(value) || value < 0) {
          value = undefined;
        }
        if (value !== hours[index]) {
          hours[index] = value;
        }

        updateRowTotals($scope, journal, capacityType);
        updateJournalTotals($scope, journal, index);
        updateModuleTotals($scope.formState.moduleMap[journal.lessonPlanModule], capacityType, index);
        updateGrandTotals(capacityType, index);
        //study period updates - we have to keep 2 different forms up to date at the same time
        $scope.updateSpRowByWeek(journal, capacityType, index);
        updateSpJournalTotals($scope, journal);
        $scope.updateSpGrandTotals(capacityType, index);
      };

      $scope.getCapacityByCode = function (capacityCode) {
        return $scope.formState.capacityTypes.find(function (it) {
          return it.code === capacityCode;
        });
      };

      $scope.updateLessonCountByStudyPeriod = function (journal, capacityType, spIndex) {
        var studyPeriod = $scope.formState.studyPeriods[spIndex];
        var newTotalHours = journal.spHours[capacityType][spIndex];
        var lessonsByWeek = newTotalHours / studyPeriod.weekNrs.length;
        if (lessonsByWeek % 1 !== 0) {
          lessonsByWeek = Math.ceil(lessonsByWeek);
        }
        var lessonsAdded = 0;
        var weekIndex = studyPeriod.weekIndex[0];
        for (var weekNr = 0; weekNr < studyPeriod.weekNrs.length; weekNr++) {
          journal.hours[capacityType][weekIndex] = lessonsByWeek;
          lessonsAdded += lessonsByWeek;
          $scope.updateTotals(journal, capacityType, weekIndex);
          if (lessonsAdded === Number(newTotalHours)) {
            lessonsByWeek = 0;
          }
          if (((newTotalHours - lessonsAdded) / (studyPeriod.weekNrs.length - weekNr - 1)) % 1 === 0) {
            lessonsByWeek = (newTotalHours - lessonsAdded) / (studyPeriod.weekNrs.length - weekNr - 1);
          }
          weekIndex += 1;
        }
      };

      $scope.updateSpGrandTotals = function (capacityType, index) {
        if (!angular.isObject($scope.formState.capGrandTotalsSp)) {
          $scope.formState.capGrandTotalsSp = {};
          $scope.formState.grandTotalsSp = {};
        }
        if (!angular.isObject($scope.formState.capGrandTotalsSp[capacityType])) {
          $scope.formState.capGrandTotalsSp[capacityType] = {};
        }
        var sp = $scope.formState.studyPeriods.find(function (it) {
          return it.weekIndex[0] <= index && it.weekIndex[1] >= index;
        });
        $scope.formState.capGrandTotalsSp[capacityType][sp.arrayIndex] = rowSum($scope.formState.grandTotals[capacityType].slice(sp.weekIndex[0], sp.weekIndex[1]));
        $scope.formState.grandTotalsSp[sp.arrayIndex] = rowSum($scope.formState.grandTotals._._.slice(sp.weekIndex[0], sp.weekIndex[1]));
      };

      $scope.updateSpRowByWeek = function (journal, capacityType, index) {
        var hours = journal.hours[capacityType];
        var sp = $scope.formState.studyPeriods.find(function (it) {
          return it.weekIndex[0] <= index && it.weekIndex[1] >= index;
        });
        journal.spHours[capacityType][sp.arrayIndex] = rowSum(hours.slice(sp.weekIndex[0], sp.weekIndex[1]));
      };

      $scope.newJournal = function (module) {
        $location.url(baseUrl + '/journals/new?_noback&lessonPlanModule=' + module.id);
      };

      $scope.editJournal = function (journal) {
        $location.url(baseUrl + '/journals/' + journal.id + '/edit?_noback&lessonPlanModule=' + journal.lessonPlanModule);
      };

      $scope.update = function () {
        $scope.record.$update().then(message.updateSuccess);
      };

      $scope.getLegendByWeek = function (weekNr) {
        return $scope.formState.legends[weekNr];
      };
    }
  ]).controller('LessonplanTeacherViewController', ['$location', '$mdDialog', '$route', '$scope', 'message', 'Classifier', 'QueryUtils',

    function ($location, $mdDialog, $route, $scope, message, Classifier, QueryUtils) {
      var id = $route.current.params.id;
      var studyYearId = $route.current.params.studyYear;
      var baseUrl = '/lessonplans/byteacher';
      $scope.formState = {
        capacityTypes: Classifier.queryForDropdown({
          mainClassCode: 'MAHT'
        }),
        showWeeks: true,
        showCapacityTypes: true
      };
      $scope.keys = Object.keys;

      $scope.getCapacityTypes = function (hours) {
        return $scope.formState.capacityTypes.filter(function (ct) {
          return hours !== undefined && hours[ct.code] !== undefined;
        });
      };

      $scope.getCapacityByCode = function (capacityCode) {
        return $scope.formState.capacityTypes.find(function (it) {
          return it.code === capacityCode;
        });
      };

      QueryUtils.endpoint(baseUrl + '/:id/:studyYear').search({
        id: id,
        studyYear: studyYearId
      }).$promise.then(function (result) {
        $scope.formState.studyPeriods = result.studyPeriods;
        var spLocationPointer = 0;
        $scope.formState.studyPeriods.forEach(function (sp, spIndex) {
          sp.weekIndex = [spLocationPointer, spLocationPointer + sp.weekNrs.length];
          sp.arrayIndex = spIndex;
          spLocationPointer += sp.weekNrs.length;
        });
        $scope.formState.weekNrs = result.weekNrs;
        $scope.formState.weekBeginningDates = result.weekBeginningDates;
        $scope.formState.rowTotals = {};
        $scope.formState.journalTotals = {};
        $scope.formState.grandTotals = {
          _: {
            _: createTotalsRow($scope)
          }
        };
        // initialize totals

        $scope.formState.capacityTypes.$promise.then(function () {
          result.journals.forEach(function (journal) {
            journal.spHours = {};
            journal.spTotals = {};
            $scope.formState.journalTotals[journal.id] = {
              _: createTotalsRow($scope)
            };
            $scope.formState.capacityTypes.forEach(function (c) {
              var capacityType = c.code;
              journal.spHours[capacityType] = {};
              var hours = journal.hours[capacityType];
              if (hours !== undefined) {
                updateRowTotals($scope, journal, capacityType);
                updateSpRows($scope, journal, capacityType);
                var grandTotals = $scope.formState.grandTotals;
                if (grandTotals[capacityType] === undefined) {
                  grandTotals[capacityType] = createTotalsRow($scope);
                }
              }
            });
            for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
              updateJournalTotals($scope, journal, i);
            }
            updateSpJournalTotals($scope, journal);
          });

          function calculateHours(i, capacityType) {
            return result.journals.reduce(function (sum, journal) {
              var hours = journal.hours[capacityType];
              if (hours !== undefined) {
                sum += hours[i];
              }
              return sum;
            }, 0);
          }

          $scope.formState.capacityTypes.forEach(function (c) {
            var capacityType = c.code;
            var grandTotals = $scope.formState.grandTotals;
            if (grandTotals[capacityType] !== undefined) {
              for (var i = 0, wnCnt = $scope.formState.weekNrs.length; i < wnCnt; i++) {
                grandTotals[capacityType][i] = calculateHours(i, capacityType);
                updateTotals($scope, grandTotals, capacityType, i);
              }
            }
            initializeSpGrandTotals($scope, capacityType);
          });
          // subjects
          $scope.formState.subjectCapacityTypes = [];
          $scope.formState.studyPeriods.forEach(function (sp) {
            $scope.formState.capacityTypes.forEach(function (c) {
              $scope.formState.subjectCapacityTypes.push({
                studyPeriod: sp.id,
                code: c.code,
                value: c.value
              });
            });
          });
          $scope.record = result;
        });
      });
    }
  ]);
}());
