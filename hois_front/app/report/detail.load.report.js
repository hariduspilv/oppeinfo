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
    
    scope.plannedLessonsColspan = reportData.criteria.showPlannedLessons ? 
      (scope.reportData.teacherCapacities.length != 0 ? scope.reportData.teacherCapacities.length + 2 : (2 + (scope.reportData.criteria.byCapacities ? 0 : 1))) : 0;
    
    scope.journalOccurredLessonsColspan = 0;
    if (!reportData.criteria.isHigher) {
      scope.journalOccurredLessonsColspan = (scope.reportData.teacherCapacities.length != 0 ? scope.reportData.teacherCapacities.length + 2 : 2 + (scope.reportData.criteria.byCapacities ? 0 : 1)) 
      + (scope.entriesWithoutCapacity ? 1 : 0);
    }
    // last addition is because of substitutable lessons
    scope.timetableOccurredLessonsColspan = 0;
    if (reportData.criteria.isHigher || reportData.criteria.showTimetableLoad) {
      scope.timetableOccurredLessonsColspan = (scope.reportData.teacherCapacities.length) + (scope.entriesWithoutCapacity ? 1 : 0) +
        (reportData.criteria.showSingleEvents ? 1 : 0) + 3 + (scope.reportData.criteria.byCapacities ? 0 : 1);
    }
    
    scope.periodColspan = scope.plannedLessonsColspan + scope.journalOccurredLessonsColspan + scope.timetableOccurredLessonsColspan;
    // last addition is because of timetable occured lessons totals
    scope.totalsColspan = scope.periodColspan + (scope.reportData.criteria.byCapacities ? 1 : 0) + 1;

    scope.periodTypesRow = [];
    scope.periodCapacitiesRow = [];
    if (reportData.criteria.byStudyPeriods) {
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

    if (!criteria.isHigher) {
      scope.periodTypesRow.push({
        index: periodIndex,
        journalOccurredLessons: true,
        colspan: scope.journalOccurredLessonsColspan,
        divider: !criteria.showTimetableLoad
      });
    } 
    
    if (criteria.isHigher || criteria.showTimetableLoad) {
      scope.periodTypesRow.push({
        index: periodIndex,
        timetableOccurredLessons: true,
        colspan: scope.timetableOccurredLessonsColspan,
        divider: true,
      });
    }
  }

  function addPeriodCapacitiesRowPeriod(scope, periodIndex, criteria, teacherCapacities) {
    if (criteria.byCapacities) {
      if (criteria.showPlannedLessons) {
        scope.periodCapacitiesRow = scope.periodCapacitiesRow.concat(teacherCapacities.map(function (c) {
          return { index: periodIndex, plannedLessons: true, value: c.value, code: c.code };
        }));
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', plannedContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', plannedKoefLessons: true });
      }
      
      if (!criteria.isHigher) {
        scope.periodCapacitiesRow = scope.periodCapacitiesRow.concat(teacherCapacities.map(function (c) {
          return { index: periodIndex, journalOccurredLessons: true, value: c.value, code: c.code };
        }));
        if (scope.entriesWithoutCapacity) {
          scope.periodCapacitiesRow.push({ index: periodIndex, value: NO_CAPACITY_TYPE, code: NO_CAPACITY_TYPE, 
            journalOccurredLessons: true});
        }
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', journalOccurredContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', journalOccurredKoefLessons: true, divider: !criteria.showSingleEvents && !criteria.showSingleEvents && !(criteria.isHigher || criteria.showTimetableLoad)});
      }
  
      if (criteria.isHigher || criteria.showTimetableLoad) {
        
        scope.periodCapacitiesRow = scope.periodCapacitiesRow.concat(teacherCapacities.map(function (c) {
          return { index: periodIndex, timetableOccurredLessons: true, value: c.value, code: c.code };
        }));
        if (scope.entriesWithoutCapacity) {
          scope.periodCapacitiesRow.push({ index: periodIndex, value: NO_CAPACITY_TYPE, code: NO_CAPACITY_TYPE, 
            timetableOccurredLessons: true });
        }
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', timetableOccurredContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', timetableOccurredKoefLessons: true, divider: !criteria.showSingleEvents && !criteria.showSingleEvents && !(criteria.isHigher || criteria.showTimetableLoad)});
      }
    } else {
      if (criteria.showPlannedLessons) {
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'study', plannedLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', plannedContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', plannedKoefLessons: true });
      }

      if (!criteria.isHigher) {
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'study', journalOccurredLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', journalOccurredContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', journalOccurredKoefLessons: true, divider: !criteria.isHigher && !criteria.showTimetableLoad});
      }
  
      if (criteria.isHigher || criteria.showTimetableLoad) {
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'study', timetableOccurredLessons: true});
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'contactLesson', timetableOccurredContactLessons: true });
        scope.periodCapacitiesRow.push({ index: periodIndex, name: 'koefLesson', timetableOccurredKoefLessons: true });
      }
    }

    if (criteria.isHigher || criteria.showTimetableLoad) {
      scope.periodCapacitiesRow.push({ index: periodIndex, name: 'substitute', substitutableEvents: true, divider: !criteria.showSingleEvents});
    }

    if (criteria.showSingleEvents) {
      scope.periodCapacitiesRow.push({ index: periodIndex, name: 'singleEvent', singleEvent: true, divider: true});
    }
  }

  angular.module('hitsaOis').controller('ReportTeacherDetailLoadVocationalController', ['$scope', '$route', 'Classifier', 'DataUtils', 'FormUtils', 'QueryUtils', 'dialogService', 'MONTH', 'ExcelUtils',
    function ($scope, $route, Classifier, DataUtils, FormUtils, QueryUtils, dialogService, MONTH, ExcelUtils) {
      $scope.auth = $route.current.locals.auth;
      var baseUrl = '/reports/teachers/detailload/vocational';

      Classifier.queryForDropdown({ mainClassCode: 'MAHT' }, function (result) {
        $scope.capacityTypes = Classifier.toMap(result);
      });

      $scope.formState = {
        studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
        allStudyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query(),
        studyPeriods: {},
        xlsUrl: 'reports/teachers/detailload/teachersdetailload.xlsx',
        xlsUrlDetail: 'reports/teachers/detailload/teachersdetailloadsubjectjournal.xlsx'
      };

      function loadReportData() {
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

      $scope.showTimetableLoadChanged = function () {
        if (!$scope.criteria.showTimetableLoad) {
          $scope.criteria.showSingleEvents = false;
        }
      };

      $scope.excelUrl = function () {
        $scope.criteria.isHigher = false;
        return $scope.excel($scope.formState.xlsUrl, $scope.criteria);
      };

      $scope.toExcel = function () {
        FormUtils.withValidForm($scope.searchForm, function () {
          if ($scope.reportData === undefined) {
            $scope.reportData = {};
          }
          if ($scope.reportData.criteria === undefined) {
            $scope.reportData.criteria = {};
          }
          $scope.criteria.isHigher = false;
          var criteria = angular.copy($scope.reportData.criteria);
          criteria.showSingleEvents = false;
          ExcelUtils.get($scope.excel($scope.formState.xlsUrlDetail, criteria), 'teachersdetailloadsubjectjournal.xlsx', $scope);
        });
      };

      var loadData = $scope.loadData;
      $scope.loadData = function() {
        $scope.criteria.isHigher = false;
        FormUtils.withValidForm($scope.searchForm, function () {
          QueryUtils.loadingWheel($scope, true);
          loadData();
        });
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
  ]).controller('ReportTeacherDetailLoadHigherController', ['$scope', '$route', 'Classifier', 'DataUtils', 'FormUtils', 'QueryUtils', 'dialogService', 'MONTH', 'ExcelUtils',
    function ($scope, $route, Classifier, DataUtils, FormUtils, QueryUtils, dialogService, MONTH, ExcelUtils) {
      $scope.auth = $route.current.locals.auth;
      var baseUrl = '/reports/teachers/detailload/higher';

      Classifier.queryForDropdown({ mainClassCode: 'MAHT' }, function (result) {
        $scope.capacityTypes = Classifier.toMap(result);
      });

      $scope.dateFromThruChange = function () {
        if ($scope.criteria.from || $scope.criteria.thru) {
          $scope.criteria.byStudyPeriods = false;
          $scope.criteria.byWeeks = false;
        }
      };

      $scope.formState = {
        studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
        allStudyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query(),
        studyPeriods: {},
        xlsUrl: 'reports/teachers/detailload/teachersdetailload.xlsx',
        xlsUrlDetail: 'reports/teachers/detailload/teachersdetailloadsubjectjournal.xlsx'
      };

      function loadReportData() {
        $scope.content = $scope.tabledata.content;
        QueryUtils.endpoint('/reports/teachers/detailload/data').get($scope.criteria).$promise.then(function (reportData) {
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

      $scope.excelUrl = function () {
        $scope.criteria.isHigher = true;
        return $scope.excel($scope.formState.xlsUrl, $scope.criteria);
      };

      $scope.toExcel = function () {
        FormUtils.withValidForm($scope.searchForm, function () {
          if ($scope.reportData === undefined) {
            $scope.reportData = {};
          }
          if ($scope.reportData.criteria === undefined) {
            $scope.reportData.criteria = {};
          }
          $scope.criteria.isHigher = true;
          var criteria = angular.copy($scope.reportData.criteria);
          criteria.showSingleEvents = false;
          ExcelUtils.get($scope.excel($scope.formState.xlsUrlDetail, criteria), 'teachersdetailloadsubjectjournal.xlsx', $scope);
        });
      };

      var loadData = $scope.loadData;
      $scope.loadData = function() {
        $scope.criteria.isHigher = true;
        FormUtils.withValidForm($scope.searchForm, function () {
          QueryUtils.loadingWheel($scope, true);
          loadData();
        });
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
              setReportData(dialogScope, MONTH, teachers, reportData);
              dialogScope.reportData.byStudyPeriods = true;
              dialogScope.$broadcast('refreshFixedColumns');
            });
          });
        });
      };
    }
  ]);
}());
