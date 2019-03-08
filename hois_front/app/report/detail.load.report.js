(function () {
  'use strict';

  var NO_CAPACITY_TYPE = '?';

  function setReportData(scope, MONTH, teachers, reportData) {
    scope.reportData = reportData;
    scope.reportData.teacherCapacities = [];
    scope.months = {};
    scope.entriesWithoutCapacity = false;
    
    if (reportData.criteria.byCapacities) {
      setTeacherCapacities(scope, teachers);
    }
    scope.plannedLessonsColspan = reportData.criteria.showPlannedLessons ? (scope.reportData.teacherCapacities.length || 1) : 0;
    // last addition is because of substitutable lessons
    scope.occurredLessonsColspan = (scope.reportData.teacherCapacities.length || 1) + (scope.entriesWithoutCapacity ? 1 : 0) +
      (reportData.criteria.showSingleEvents ? 1 : 0) + 1;
    scope.periodColspan = scope.plannedLessonsColspan + scope.occurredLessonsColspan;

    scope.periodTypesRow = [];
    scope.periodCapacitiesRow = [];
    if (reportData.criteria.byStudyPeriods || reportData.criteria.isHigher) {
      if (reportData.criteria.studyPeriod) {
        reportData.studyPeriods = reportData.studyPeriods.filter(function (sp) {
          return sp.id === reportData.criteria.studyPeriod;
        });
      }
      setTableByStudyPeriods(scope, reportData);
    } else if (reportData.criteria.byWeeks) {
      if (reportData.criteria.studyPeriod) {
        var studyPeriod = reportData.studyPeriods = reportData.studyPeriods.filter(function (sp) {
          return sp.id === reportData.criteria.studyPeriod;
        })[0];
        scope.weekNrs = studyPeriod.weekNrs;
      } else {
        scope.weekNrs = reportData.weekNrs.filter(function (value, index, self) {
          return self.indexOf(value) === index;
        });
      }
      setTableByWeeks(scope, reportData);
    } else if (reportData.criteria.byMonths) {
      setMonths(scope, MONTH, reportData.months);
      setTableByMonths(scope, reportData);
    }
  }

  function setTeacherCapacities(scope, teachers) {
    var capacityCodes = [];
    teachers.forEach(function (teacher) {
      if (teacher.teacherCapacities) {
        teacher.teacherCapacities.forEach(function (capacity) {
          if (capacity !== NO_CAPACITY_TYPE && capacityCodes.indexOf(capacity) === -1) {
            capacityCodes.push(capacity);
          }
          if (capacity === NO_CAPACITY_TYPE) {
            scope.entriesWithoutCapacity = true;
          }
        });
      }
    });

    var capacities = [];
    capacityCodes.forEach(function (code) {
      capacities.push(scope.capacityTypes[code]);
    });
    capacities.sort(function(a, b) {
      a = a.value.toUpperCase();
      b = b.value.toUpperCase();
      return a > b ? 1 : a < b ? -1 : 0;
    });
    scope.reportData.teacherCapacities = capacities;
  }

  function setMonths(scope, MONTH, months) {
    var monthObjects = [];
    months.forEach(function (month) {
      monthObjects.push({index: month, name: MONTH[getMonthIndex(month)]});
    });
    scope.months = monthObjects;
  }

  function setTableByStudyPeriods(scope, reportData) {
    reportData.studyPeriods.forEach(function (sp) {
      addPeriodTypesRowPeriod(scope, sp.id, reportData.criteria);
    });

    reportData.studyPeriods.forEach(function (sp) {
      addPeriodCapacitiesRowPeriod(scope, sp.id, reportData.criteria, reportData.teacherCapacities);
    });
  }

  function setTableByWeeks(scope, reportData) {
    scope.weekNrs.forEach(function (weekNr) {
      addPeriodTypesRowPeriod(scope, weekNr, reportData.criteria);
    });

    scope.weekNrs.forEach(function (weekNr) {
      addPeriodCapacitiesRowPeriod(scope, weekNr, reportData.criteria, reportData.teacherCapacities);
    });
  }

  function setTableByMonths(scope, reportData) {
    scope.months.forEach(function (month) {
      addPeriodTypesRowPeriod(scope, month.index, reportData.criteria);
    });

    scope.months.forEach(function (month) {
      addPeriodCapacitiesRowPeriod(scope, month.index, reportData.criteria, reportData.teacherCapacities);
    });
  }

  function getMonthIndex(index) {
    return index !== 0 ? index - 1 : 12;
  }

  function addPeriodTypesRowPeriod(scope, periodIndex, criteria) {
    if (criteria.showPlannedLessons) {
      scope.periodTypesRow.push({ index: periodIndex, plannedLessons: true, colspan: scope.plannedLessonsColspan });
    }
    scope.periodTypesRow.push({ index: periodIndex, occurredLessons: true, colspan: scope.occurredLessonsColspan });
  }

  function addPeriodCapacitiesRowPeriod(scope, periodIndex, criteria, teacherCapacities) {
    if (criteria.byCapacities) {
      if (criteria.showPlannedLessons) {
        scope.periodCapacitiesRow = scope.periodCapacitiesRow.concat(teacherCapacities.map(function (c) {
          return { index: periodIndex, plannedLessons: true, value: c.value, code: c.code };
        }));
        if (teacherCapacities.length === 0) {
          scope.periodCapacitiesRow.push({});
        }
      }
      
      scope.periodCapacitiesRow = scope.periodCapacitiesRow.concat(teacherCapacities.map(function (c) {
        return { index: periodIndex, occurredLessons: true, value: c.value, code: c.code };
      }));
      if (teacherCapacities.length === 0) {
        scope.periodCapacitiesRow.push({});
      }
    } else {
      if (criteria.showPlannedLessons) {
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'study', plannedLessons: true });
      }
      scope.periodCapacitiesRow.push({ index: periodIndex, name: 'study', occurredLessons: true });
    }

    if (scope.entriesWithoutCapacity) {
      scope.periodCapacitiesRow.push({ index: periodIndex, value: NO_CAPACITY_TYPE, code: NO_CAPACITY_TYPE, occurredLessons: true });
    }

    scope.periodCapacitiesRow.push({ index: periodIndex, name: 'substitute', substitutableEvents: true });
    if (criteria.showSingleEvents) {
      scope.periodCapacitiesRow.push({ index: periodIndex, name: 'singleEvent', singleEvent: true});
    }
  }

  angular.module('hitsaOis').controller('ReportTeacherDetailLoadVocationalController', ['$scope', '$route', 'Classifier', 'DataUtils', 'FormUtils', 'QueryUtils', 'dialogService', 'MONTH',
    function ($scope, $route, Classifier, DataUtils, FormUtils, QueryUtils, dialogService, MONTH) {
      $scope.auth = $route.current.locals.auth;
      var baseUrl = '/reports/teachers/detailload/vocational';

      Classifier.queryForDropdown({ mainClassCode: 'MAHT' }, function (result) {
        $scope.capacityTypes = Classifier.toMap(result);
      });

      $scope.formState = {
        studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
        allStudyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query(),
        studyPeriods: {},
        xlsUrl: 'reports/teachers/detailload/teachersdetailload.xlsx'
      };

      function loadReportData() {
        QueryUtils.loadingWheel($scope, true);
        $scope.content = $scope.tabledata.content;
        QueryUtils.endpoint('/reports/teachers/detailload/data').get($scope.criteria).$promise.then(function (reportData) {
          DataUtils.convertStringToDates(reportData.criteria, ['from', 'thru']);
          var teachers = $scope.tabledata ? $scope.tabledata.content : null;
          setReportData($scope, MONTH, teachers, reportData);
          $scope.reportData.byTeachers = true;
          $scope.$broadcast('refreshFixedColumns');
          QueryUtils.loadingWheel($scope, false);
        });
      }

      QueryUtils.createQueryForm($scope, baseUrl, {}, loadReportData);

      $scope.$watch('criteria.studyYear', function () {
        var studyYear = $scope.formState.studyYears.filter(function (sy) {
          return sy.id === $scope.criteria.studyYear;
        })[0];

        if ($scope.criteria.studyPeriod) {
          var studyYearPeriods = $scope.formState.studyPeriods[$scope.criteria.studyYear] || [];
          var chosenPeriodInStudyYear = studyYearPeriods.filter(function (sp) {
            return sp.id === $scope.criteria.studyPeriod;
          }).length > 0;
          if (!chosenPeriodInStudyYear) {
            $scope.criteria.studyPeriod = null;
          }
        }

        $scope.criteria.studyYearStart = studyYear ? studyYear.startDate : null;
        $scope.criteria.studyYearEnd = studyYear ? studyYear.endDate : null;
      });

      $scope.$watch('criteria.studyPeriod', function () {
        var studyPeriod = $scope.formState.allStudyPeriods.filter(function (sp) {
          return sp.id === $scope.criteria.studyPeriod;
        })[0];

        $scope.criteria.studyPeriodStart = studyPeriod ? studyPeriod.startDate : null;
        $scope.criteria.studyPeriodEnd = studyPeriod ? studyPeriod.endDate : null;
      });

      $scope.formState.studyYears.$promise.then(function () {
        $scope.formState.allStudyPeriods.$promise.then(function (studyPeriods) {
          for (var i = 0; i < studyPeriods.length; i++) {
            var sp = studyPeriods[i];
            var sy = $scope.formState.studyPeriods[sp.studyYear];
            if (!sy) {
              $scope.formState.studyPeriods[sp.studyYear] = sy = [];
            }
            sy.push(sp);
          }
        });

        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
      });

      $scope.periodChanged = function (period) {
        switch (period) {
          case 'byStudyPeriods':
            $scope.criteria.byWeeks = false;
            $scope.criteria.byMonths = false;
            break;
          case 'byWeeks':
            $scope.criteria.byStudyPeriods = false;
            $scope.criteria.byMonths = false;
            break;
          case 'byMonths':
            $scope.criteria.byStudyPeriods = false;
            $scope.criteria.byWeeks = false;
            break;
        }
      };

      $scope.dateFromThruChange = function () {
        if ($scope.criteria.from || $scope.criteria.thru) {
          $scope.criteria.byStudyPeriods = false;
          $scope.criteria.byWeeks = false;
        }
      };

      $scope.studyPeriodChanged = function () {
        if ($scope.criteria.studyPeriod) {
          $scope.criteria.byMonths = false;
        }
      };

      var loadData = $scope.loadData;
      $scope.loadData = function() {
        $scope.criteria.isHigher = false;
        FormUtils.withValidForm($scope.searchForm, loadData);
      };

      $scope.teacherJournalsDialog = function (teacher) {
        dialogService.showDialog('report/teachers.detail.load.teacher.dialog.tmpl.html', function (dialogScope) {
          dialogScope.teacher = teacher;
          dialogScope.criteria = angular.copy($scope.reportData.criteria);
          dialogScope.criteria.showSingleEvents = false;
          dialogScope.capacityTypes = $scope.capacityTypes;
          dialogScope.xlsUrl = 'reports/teachers/detailload/teacher/' + teacher.id + '/teachersdetailload.xlsx';

          QueryUtils.endpoint(baseUrl + '/' + dialogScope.teacher.id).get(dialogScope.criteria).$promise.then(function (result) {
            dialogScope.content = result.journalSubjects;
            QueryUtils.endpoint('/reports/teachers/detailload/data').get(dialogScope.criteria).$promise.then(function (reportData) {
              DataUtils.convertStringToDates(reportData.criteria, ['from', 'thru']);
              var teachers = [];
              teachers.push(result);
              setReportData(dialogScope, MONTH, teachers, reportData);
              dialogScope.reportData.byJournals = true;
              dialogScope.$broadcast('refreshFixedColumns');
            });
          });
        });
      };
    }
  ]).controller('ReportTeacherDetailLoadHigherController', ['$scope', '$route', 'Classifier', 'DataUtils', 'FormUtils', 'QueryUtils', 'dialogService',
    function ($scope, $route, Classifier, DataUtils, FormUtils, QueryUtils, dialogService) {
      $scope.auth = $route.current.locals.auth;
      var baseUrl = '/reports/teachers/detailload/higher';

      Classifier.queryForDropdown({ mainClassCode: 'MAHT' }, function (result) {
        $scope.capacityTypes = Classifier.toMap(result);
      });

      $scope.formState = {
        studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
        allStudyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query(),
        studyPeriods: {},
        xlsUrl: 'reports/teachers/detailload/teachersdetailload.xlsx'
      };

      function loadReportData() {
        QueryUtils.loadingWheel($scope, true);
        $scope.content = $scope.tabledata.content;
        QueryUtils.endpoint('/reports/teachers/detailload/data').get($scope.criteria).$promise.then(function (reportData) {
          var teachers = $scope.tabledata ? $scope.tabledata.content : null;
          //TODO: get rid of month?
          setReportData($scope, null, teachers, reportData);
          $scope.reportData.byTeachers = true;
          $scope.$broadcast('refreshFixedColumns');
          QueryUtils.loadingWheel($scope, false);
        });
      }

      QueryUtils.createQueryForm($scope, baseUrl, {}, loadReportData);

      $scope.$watch('criteria.studyYear', function () {
        var studyYear = $scope.formState.studyYears.filter(function (sy) {
          return sy.id === $scope.criteria.studyYear;
        })[0];

        if ($scope.criteria.studyPeriod) {
          var studyYearPeriods = $scope.formState.studyPeriods[$scope.criteria.studyYear] || [];
          var chosenPeriodInStudyYear = studyYearPeriods.filter(function (sp) {
            return sp.id === $scope.criteria.studyPeriod;
          }).length > 0;
          if (!chosenPeriodInStudyYear) {
            $scope.criteria.studyPeriod = null;
          }
        }

        $scope.criteria.studyYearStart = studyYear ? studyYear.startDate : null;
        $scope.criteria.studyYearEnd = studyYear ? studyYear.endDate : null;
      });

      $scope.$watch('criteria.studyPeriod', function () {
        var studyPeriod = $scope.formState.allStudyPeriods.filter(function (sp) {
          return sp.id === $scope.criteria.studyPeriod;
        })[0];

        $scope.criteria.studyPeriodStart = studyPeriod ? studyPeriod.startDate : null;
        $scope.criteria.studyPeriodEnd = studyPeriod ? studyPeriod.endDate : null;
      });

      $scope.formState.studyYears.$promise.then(function () {
        $scope.formState.allStudyPeriods.$promise.then(function (studyPeriods) {
          for (var i = 0; i < studyPeriods.length; i++) {
            var sp = studyPeriods[i];
            var sy = $scope.formState.studyPeriods[sp.studyYear];
            if (!sy) {
              $scope.formState.studyPeriods[sp.studyYear] = sy = [];
            }
            sy.push(sp);
          }
        });

        if (!$scope.criteria.studyYear) {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if (sy) {
            $scope.criteria.studyYear = sy.id;
          }
        }
      });

      $scope.periodChanged = function (period) {
        switch (period) {
          case 'byStudyPeriods':
            $scope.criteria.byWeeks = false;
            $scope.criteria.byMonths = false;
            break;
          case 'byWeeks':
            $scope.criteria.byStudyPeriods = false;
            $scope.criteria.byMonths = false;
            break;
          case 'byMonths':
            $scope.criteria.byStudyPeriods = false;
            $scope.criteria.byWeeks = false;
            break;
        }
      };

      var loadData = $scope.loadData;
      $scope.loadData = function() {
        $scope.criteria.isHigher = true;
        FormUtils.withValidForm($scope.searchForm, loadData);
      };

      $scope.teacherSubjectsDialog = function (teacher) {
        dialogService.showDialog('report/teachers.detail.load.teacher.dialog.tmpl.html', function (dialogScope) {
          dialogScope.teacher = teacher;
          dialogScope.criteria = angular.copy($scope.reportData.criteria);
          dialogScope.criteria.showSingleEvents = false;
          dialogScope.capacityTypes = $scope.capacityTypes;
          dialogScope.xlsUrl = 'reports/teachers/detailload/teacher/' + teacher.id + '/teachersdetailload.xlsx';

          QueryUtils.endpoint(baseUrl + '/' + dialogScope.teacher.id).get(dialogScope.criteria).$promise.then(function (result) {
            dialogScope.content = result.journalSubjects;
            QueryUtils.endpoint('/reports/teachers/detailload/data').get(dialogScope.criteria).$promise.then(function (reportData) {
              var teachers = [];
              teachers.push(result);
              setReportData(dialogScope, null, teachers, reportData);
              dialogScope.reportData.byStudyPeriods = true;
              dialogScope.$broadcast('refreshFixedColumns');
            });
          });
        });
      };
    }
  ]);
}());
