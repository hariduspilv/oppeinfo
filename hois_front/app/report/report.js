'use strict';

angular.module('hitsaOis').controller('ReportStudentController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
  function ($q, $scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var certificateMapper = Classifier.valuemapper({occupationCode: 'KUTSE', partOccupationCode: 'OSAKUTSE', specialityCode: 'SPETSKUTSE'});
    var clMapper = Classifier.valuemapper({dormitory: 'YHISELAMU', fin: 'FINALLIKAS', finSpecific: 'FINTAPSUSTUS', language: 'OPPEKEEL',
      studyLevel: 'OPPEASTE', studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', status: 'OPPURSTAATUS'});

    function certMapper(it) {
      certificateMapper.objectmapper(it);
      return {certificateNr: it.certificateNr, occupation: it.specialityCode || it.partOccupationCode || it.occupationCode};
    }

    function afterLoad(result) {
      clMapper.objectmapper(result);
      for(var i = 0, cnt = result.length; i < cnt; i++) {
        result[i].occupationCertificates = (result[i].occupationCertificates || []).map(certMapper);
      }
    }
    QueryUtils.createQueryForm($scope, '/reports/students', {order: 'p.lastname,p.firstname', isHigher: $scope.auth.higher}, afterLoad);
    
    $scope.directiveControllers = [];
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.isHigher = $scope.auth.higher;
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };

    $scope.formState = {
      xlsUrl: 'reports/students/students.xls'
    };

    $scope.curriculumVersionChanged = function() {
      $scope.studentGroupName = null;
      $scope.criteria.studentGroup = null;
    };

    $q.all(certificateMapper.promises).then(function() {
      $q.all(clMapper.promises).then($scope.loadData);
    });
  }
]).controller('ReportStudentStatisticsController', ['$scope', 'Classifier', 'QueryUtils', 'message',
  function ($scope, Classifier, QueryUtils, message) {
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatistics.xls',
                        filterValues: {OPPURSTAATUS: ['OPPURSTAATUS_K', 'OPPURSTAATUS_L']}};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics', {
      order: $scope.currentLanguage() === 'en' ? 'c.nameEn' : 'c.nameEt',
      result: 'OPPEVORM'
    }, function() {
      var resultType = $scope.criteria.result;
      $scope.savedCriteria = angular.copy($scope.criteria);
      if($scope.formState.resultType !== resultType) {
        $scope.formState.resultType = resultType;
        if(resultType) {
          var filterValues = $scope.formState.filterValues[resultType];
          $scope.formState.resultDef = Classifier.queryForDropdown({mainClassCode: resultType, filterValues: filterValues});
          if(resultType === 'FINALLIKAS') {
            Classifier.queryForDropdown({mainClassCode: 'FINTAPSUSTUS'}, function(result) {
              $scope.formState.resultDef.$promise.then(function() {
                Array.prototype.push.apply($scope.formState.resultDef, result);
              });
            });
          }
        } else {
          $scope.formState.resultDef = [];
        }
      }
    });
    $scope.criteria.date = new Date().withoutTime();
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.curriculum = [];
    };

    $scope.load = function() {
      if (!$scope.searchForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      } else {
        $scope.loadData();
      }
    };

    $scope.loadData();
  }
]).controller('ReportStudentStatisticsByperiodController', ['$scope', '$route', 'Classifier', 'QueryUtils',
  function ($scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var classifierMapping = {OPPURSTAATUS_A: 'AKADPUHKUS_POHJUS', OPPURSTAATUS_K: 'EKSMAT_POHJUS'};
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatisticsbyperiod.xls'};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics/byperiod', {
      order: $scope.currentLanguage() === 'en' ? 'c.nameEn' : 'c.nameEt', 
      result: 'OPPURSTAATUS_A'
    }, function() {
      setResultClassifiers($scope.criteria.result);
    });

    function setResultClassifiers(resultType) {
      $scope.formState.resultDef = [];
      // get all classifiers by main class code
      if ($scope.formState.resultType !== resultType) {
        $scope.formState.resultType = resultType;
        var mainClassCode = classifierMapping[resultType];
        $scope.resultDef = mainClassCode ? Classifier.queryForDropdown({mainClassCode: mainClassCode}) : undefined;
      }
      // get classifiers from search result curriculums
      if (angular.isDefined($scope.resultDef)) {
        var queryClassifiers = queryResultClassifiers();
  
        $scope.resultDef.$promise.then(function () {
          for (var i = 0; i < $scope.resultDef.length; i++) {
            var classifier = $scope.resultDef[i];
            if (queryClassifiers.indexOf(classifier.code) !== -1) {
              $scope.formState.resultDef.push(classifier);
            }
          }
        });
      }
    }

    function queryResultClassifiers() {
      var classifiers = [];
      for (var i = 0; i < $scope.tabledata.content.length; i++) {
        var curriculum = $scope.tabledata.content[i];

        var curriculumResultClassifiers = Object.keys(curriculum.result);
        for (var j = 0; j < curriculumResultClassifiers.length; j++) {
          var result = curriculumResultClassifiers[j];
          if (classifiers.indexOf(result) === -1) {
            classifiers.push(result);
          }
        }
      }
      return classifiers;
    }

    $scope.criteria.from = new Date().withoutTime();
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.curriculum = [];
    };

    $scope.loadData();
  }
]).controller('ReportCurriculumsCompletionController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
  function ($q, $scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', status: 'OPPURSTAATUS'});
    $scope.formState = {xlsUrl: 'reports/curriculums/completion/curriculumscompletion.xls'};
    $scope.directiveControllers = [];

    QueryUtils.createQueryForm($scope, '/reports/curriculums/completion', {order: 'p.lastname,p.firstname', isHigher: $scope.auth.higher}, clMapper.objectmapper);
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.isHigher = $scope.auth.higher;
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('ReportTeacherLoadHigherController', ['$scope', '$route', '$timeout', 'DataUtils', 'FormUtils', 'QueryUtils',
  function ($scope, $route, $timeout, DataUtils, FormUtils, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    QueryUtils.createQueryForm($scope, '/reports/teachers/load/higher', {order: 'p.lastname,p.firstname'});

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      FormUtils.withValidForm($scope.teacherLoadReportForm, loadData);
    };

    $scope.formState = {
      studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), 
      studyPeriods: {},
      xlsUrl: 'reports/teachers/load/higher/teachersloadhigher.xls'
    };
    $scope.formState.allStudyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $scope.formState.studyYears.$promise.then(function() {
      $scope.formState.allStudyPeriods.$promise.then(function(studyPeriods) {
        for(var i = 0;i < studyPeriods.length;i++) {
          var sp = studyPeriods[i];
          var sy = $scope.formState.studyPeriods[sp.studyYear];
          if(!sy) {
            $scope.formState.studyPeriods[sp.studyYear] = sy = [];
          }
          sy.push(sp);
        }
      });

      if(!$scope.criteria.studyYear) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if(sy) {
          $scope.criteria.studyYear = sy.id;
        }
      }
      if($scope.criteria.studyYear) {
        $timeout($scope.loadData);
      }
    });
  }
]).controller('ReportTeacherLoadVocationalController', ['$scope', '$route', '$timeout', 'DataUtils', 'FormUtils', 'QueryUtils',
  function ($scope, $route, $timeout, DataUtils, FormUtils, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    QueryUtils.createQueryForm($scope, '/reports/teachers/load/vocational', {order: 'p.lastname,p.firstname'});

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      FormUtils.withValidForm($scope.teacherLoadReportForm, loadData);
    };

    $scope.formState = {
      studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), 
      studyPeriods: {},
      xlsUrl: 'reports/teachers/load/vocational/teachersloadvocational.xls'
    };
    $scope.formState.allStudyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $scope.formState.studyYears.$promise.then(function() {
      $scope.formState.allStudyPeriods.$promise.then(function(studyPeriods) {
        for(var i = 0;i < studyPeriods.length;i++) {
          var sp = studyPeriods[i];
          var sy = $scope.formState.studyPeriods[sp.studyYear];
          if(!sy) {
            $scope.formState.studyPeriods[sp.studyYear] = sy = [];
          }
          sy.push(sp);
        }
      });

      if(!$scope.criteria.studyYear) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if(sy) {
          $scope.criteria.studyYear = sy.id;
        }
      }
      if($scope.criteria.studyYear) {
        $timeout($scope.loadData);
      }
    });
  }
]).controller('ReportVotaController', ['$scope', '$timeout', 'DataUtils', 'FormUtils', 'QueryUtils',
  function ($scope, $timeout, DataUtils, FormUtils, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/reports/vota', {order: 'sy.start_date'});

    var loadData = $scope.loadData;
    $scope.loadData = function() {
      FormUtils.withValidForm($scope.votaReportForm, loadData);
    };

    $scope.formState = {studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), studyPeriods: {}};
    $scope.formState.allStudyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
    $scope.formState.studyYears.$promise.then(function() {
      $scope.formState.allStudyPeriods.$promise.then(function(studyPeriods) {
        var sp, sy;
        for(var i = 0;i < studyPeriods.length;i++) {
          sp = studyPeriods[i];
          sy = $scope.formState.studyPeriods[sp.studyYear];
          if(!sy) {
            $scope.formState.studyPeriods[sp.studyYear] = sy = [];
          }
          sy.push(sp);
        }
        if(!$scope.criteria.studyYear) {
          sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          if(sy) {
            $scope.criteria.studyYear = sy.id;
            if(!$scope.criteria.studyPeriod) {
              sp = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods[sy.id] || []);
              if(sp) {
                $scope.criteria.studyPeriod = sp.id;
              }
            }
          }
        }
        if($scope.criteria.studyYear) {
          $timeout($scope.loadData);
        }
      });
    });

    $scope.studyPeriodRows = function(index) {
      var rowcount = 1;
      if(index !== -1) {
        var table = $scope.tabledata.content;
        var sp = table[index].studyPeriod.id;
        for(var i = index + 1; i < table.length;i++) {
          if(table[i].studyPeriod.id !== sp) {
            break;
          }
          rowcount++;
        }
      }
      return rowcount;
    };

    $scope.studyPeriodVisible = function(index) {
      var table = $scope.tabledata.content;
      return index === 0 || table[index - 1].studyPeriod.id !== table[index].studyPeriod.id;
    };
  }
]).controller('StudentGroupTeacherController', ['$httpParamSerializer', '$route', '$scope', '$sessionStorage', '$timeout', '$window', 'Classifier', 'DataUtils', 'VocationalGradeUtil', 'QueryUtils', 'config', 'dialogService', 'message',
function ($httpParamSerializer, $route, $scope, $sessionStorage, $timeout, $window, Classifier, DataUtils, VocationalGradeUtil, QueryUtils, config, dialogService, message) {
  $scope.gradeUtil = VocationalGradeUtil;
  $scope.auth = $route.current.locals.auth;
  
  var baseUrl = '/reports/studentgroupteacher';
  var resultsMapper = Classifier.valuemapper({grade: 'KUTSEHINDAMINE', entryType: 'SISSEKANNE'});
  var absencesMapper = Classifier.valuemapper({absence: 'PUUDUMINE', entryType: 'SISSEKANNE'});
  $scope.entryTypesOrder = [
    'SISSEKANNE_H',
    'SISSEKANNE_R',
    'SISSEKANNE_O',
    'SISSEKANNE_L',
    'SISSEKANNE_P',
    'SISSEKANNE_T',
    'SISSEKANNE_E',
    'SISSEKANNE_I'
  ];
  $scope.entryTypeColors = {
    'SISSEKANNE_H': 'green-300',
    'SISSEKANNE_R': 'indigo-300',
    'SISSEKANNE_O': 'teal-300',
    'SISSEKANNE_L': 'pink-300'
  };

  if ($scope.auth.isTeacher()) {
    $scope.teacherId = $scope.auth.teacher;
  }
  
  $scope.directiveControllers = [];

  $scope.formState = {
    showAllParameters: false,
    studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), 
    studyPeriods: {},
    xlsUrl: 'reports/studentgroupteacher/studentgroupteacher.xls',
    pdfUrl: 'reports/studentgroupteacher/studentgroupteacher.pdf',
    negativeResultsPdfUrl: 'reports/studentgroupteacher/negativeresults.pdf',
    negativeResultsXlsUrl: 'reports/studentgroupteacher/negativeresults.xls',
  };

  if ($scope.auth.isTeacher()) {
    QueryUtils.endpoint('/autocomplete/studentgroups').query({
      valid: true,
      higher: false,
      studentGroupTeacherId: $scope.auth.teacher
    }).$promise.then(function (studentGroups) {
      $scope.formState.studentGroups = studentGroups;
      if (!$scope.criteria.studentGroup) {
        $scope.criteria.studentGroup = studentGroups.length === 1 ? studentGroups[0].id : null;
      }
    });
  }

  $scope.pdf = function (url, params) {
    return config.apiUrl + '/'+ url + '?' + $httpParamSerializer(params);
  };

  $scope.fromStorage = function (key) {
    return JSON.parse($sessionStorage[key] || '{}');
  };

  $scope.toStorage = function(key, criteria) {
    $sessionStorage[key] = JSON.stringify(criteria);
  };

  if(!('_menu' in $route.current.params)) {
    $scope.storedCriteria = $scope.fromStorage(baseUrl);
  }

  function searchUsingStoredCriteria() {
    if ($scope.studentGroupTeacherReportForm && $scope.studentGroupTeacherReportForm.$valid) {
      $scope.search();
    } else {
      $timeout(searchUsingStoredCriteria, 200);
    }
  }
  
  function setEntryTypeCriteria() {
    $scope.criteria.entryTypes = [];
    angular.forEach($scope.criteria.entryType, function (boolean, type) {
      if (boolean) {
        $scope.criteria.entryTypes.push(type);
      }
    });
  }

  if(!$scope.storedCriteria || angular.equals({}, $scope.storedCriteria)) {
    $scope.criteria = {entryType: {'SISSEKANNE_H': true, 'SISSEKANNE_R': true, 'SISSEKANNE_O': true, 'SISSEKANNE_L': true}};
    setEntryTypeCriteria();
  } else {
    $scope.criteria = $scope.storedCriteria;
    $scope.formState.studyPeriod = $scope.storedCriteria.formState.studyPeriod;
    $scope.formState.studentGroup = $scope.storedCriteria.formState.studentGroup;
    $scope.formState.student = $scope.storedCriteria.formState.student;
    setEntryTypeCriteria();
    if ($scope.formState.studentGroup && ($scope.criteria.studyYear || $scope.criteria.from)) {
      $timeout(searchUsingStoredCriteria);
    }
  }

  Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (result) {
    var entryTypes = Classifier.toMap(result);
    $scope.entryTypes = Object.keys(entryTypes).map(function(it) { 
      return entryTypes[it];
    });
  });

  $scope.getEntryColor = function (type) {
    return $scope.entryTypeColors[type];
  };

  $scope.getEntryTypeOrderNr = function (type) {
    return $scope.entryTypesOrder.indexOf(type.code);
  };

  $scope.formState.allStudyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriods').query();
  $scope.formState.studyYears.$promise.then(function() {
    $scope.formState.allStudyPeriods.$promise.then(function(studyPeriods) {
      var sp, sy;
      for(var i = 0;i < studyPeriods.length;i++) {
        sp = studyPeriods[i];
        sy = $scope.formState.studyPeriods[sp.studyYear];
        if(!sy) {
          $scope.formState.studyPeriods[sp.studyYear] = sy = [];
        }
        sy.push(sp);
      }
      if(!$scope.criteria.studyYear) {
        sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        if(sy) {
          $scope.criteria.studyYear = sy.id;
        }
      }
    });
  });

  $scope.studyPeriodChanged = function () {
    $scope.criteria.studyPeriod = $scope.formState.studyPeriod ? $scope.formState.studyPeriod.id : null;
    $scope.criteria.studyPeriodStart = $scope.formState.studyPeriod ? $scope.formState.studyPeriod.startDate : null;
    $scope.criteria.studyPeriodEnd = $scope.formState.studyPeriod ? $scope.formState.studyPeriod.endDate : null;
  };

  $scope.studentGroupChanged = function () {
    $scope.criteria.studentGroup = $scope.formState.studentGroup ? $scope.formState.studentGroup.id : null;
    $scope.criteria.curriculumVersion = $scope.formState.studentGroup ? $scope.formState.studentGroup.curriculumVersion : null;
    $scope.criteria.student = null;
    $scope.formState.student = null;
  };

  $scope.studentChanged = function () {
    $scope.criteria.student = $scope.formState.student ? $scope.formState.student.id : null;
  };

  $scope.entryTypeChanged = function () {
    setEntryTypeCriteria();
  };

  $scope.negativeResultsChanged = function () {
    if ($scope.criteria.negativeResults) {
      $scope.criteria.journalsWithEntries = true;
    }
  };

  $scope.onlyModuleGradesChanged = function () {
    if ($scope.criteria.onlyModuleGrades) {
      $scope.criteria.moduleGrade = true;
    }
  };

  $scope.toggleShowAllParameters = function () {
    $scope.formState.showAllParameters = !$scope.formState.showAllParameters;
    $scope.$broadcast('refreshFixedTableHeight');
  };

  $scope.search = function() {
    var form = $scope.studentGroupTeacherReportForm;
    form.$setSubmitted();
    if(!form.$valid) {
      message.error('main.messages.form-has-errors');
      return;
    }
    
    if (!$scope.criteria.studyYear && !$scope.criteria.from) {
      message.error('report.studentGroupTeacher.error.studyYearOrEntriesFromRequired');
      return;
    }

    QueryUtils.loadingWheel($scope, true);
    QueryUtils.endpoint(baseUrl).get($scope.criteria).$promise.then(function (result) {
      $scope.record = result;
      $scope.record.students.forEach(function (student) {
        student.resultColumns.forEach(function (column) {
          if (column.journalResult) {
            if (column.journalResult.results) {
              resultsMapper.objectmapper(column.journalResult.results);
            }
            if (column.journalResult.absences) {
              absencesMapper.objectmapper(column.journalResult.absences);
            }
          }
          if (column.practiceModuleThemeResult) {
            resultsMapper.objectmapper(column.practiceModuleThemeResult);
          }
          if (column.practiceModuleResult) {
            resultsMapper.objectmapper(column.practiceModuleResult);
          }
          if (column.moduleResult) {
            resultsMapper.objectmapper(column.moduleResult);
          }
        });
      });
      $scope.tableTotalColumnsColspan = 6 + ($scope.record.showAverageGrade ? 1 : 0) +
        ($scope.record.showWeightedAverageGrade ? 1 : 0);
        
      QueryUtils.loadingWheel($scope, false);
      $scope.criteria.formState = {studyPeriod: $scope.formState.studyPeriod, studentGroup: $scope.formState.studentGroup};
      $scope.formState.showAllParameters = false;
      $scope.toStorage(baseUrl, $scope.criteria);
      $scope.$broadcast('refreshFixedColumns');
    });
  };
  
  $scope.clearCriteria = function() {
    $scope.formState.showAllParameters = true;
    $scope.formState.studyPeriod = null;
    $scope.formState.studentGroup = null;
    $scope.criteria = {};
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

  $scope.windowHeight = function () {
    return $window.innerHeight;
  };

  $scope.filterOnlyModuleGrades = function (column) {
    if ($scope.record.showOnlyModuleGrades) {
      return column.module || column.moduleResult;
    }
    return true;
  };

  $scope.filterAbsences = function (absenceCode) {
    return function (absence) {
      return absence.absence.code === absenceCode;
    };
  };

  $scope.openRemarkDialog = function (student) {
    dialogService.showDialog('report/studentgroup.teacher.remark.dialog.html', function (dialogScope) {
      dialogScope.auth = $scope.auth;
      dialogScope.student = student;
    });
  };
}
]).controller("ScholarshipStatisticsController", ["$scope", "Classifier", "$route", function ($scope, Classifier, $route) {
  var auth = $route.current.locals.auth;
  $scope.criteria = {};
  $scope.url = "reports/scholarships/statistics.xlsx";
  $scope.types = Classifier.queryForDropdown({mainClassCode: "STIPTOETUS"});
  $scope.types.$promise.then(function (response) {
    $scope.types = response.filter(function (r) {
      return (auth.vocational && r.vocational) || (auth.higher && r.higher);
    });
  });

  // function isFormValid() {
  //   if ($scope.scholarshipStatisticExportForm) {
  //     $scope.scholarshipStatisticExportForm.$setSubmitted();
  //     return $scope.scholarshipStatisticExportForm.$valid;
  //   }
  //   return false;
  // }
}
]).controller("IndividualCurriculumStatisticsController", ['$route', '$scope', 'DataUtils', 'FormUtils', 'QueryUtils', function ($route, $scope, DataUtils, FormUtils, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.teacherId = $scope.auth.teacher;
  $scope.criteria = {};

  var baseUrl = '/reports/individualcurriculumstatistics';
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'lastname, firstname'});

  $scope.formState = {
    studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
    xlsUrl: 'reports/individualcurriculumstatistics.xls'
  };
  $scope.formState.studyYears.$promise.then(function () {
    if('_menu' in $route.current.params) {
      if (!$scope.criteria.from && !$scope.criteria.thru) {
        var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
        DataUtils.convertStringToDates(sy, ['from', 'thru']);
        if (sy) {
          $scope.criteria.from = sy ? sy.startDate : null;
          $scope.criteria.thru = sy ? sy.endDate : null;
        }
      }
    }
    $scope.loadData();
  });

  if ($scope.auth.isTeacher()) {
    QueryUtils.endpoint('/autocomplete/studentgroups').query({
      valid: true,
      higher: false,
      studentGroupTeacherId: $scope.auth.teacher
    }).$promise.then(function (studentGroups) {
      $scope.formState.studentGroups = studentGroups;
      if (!$scope.criteria.studentGroup) {
        $scope.criteria.studentGroup = studentGroups.length === 1 ? studentGroups[0].id : null;
      }
    });
  }

  $scope.$watch('criteria.studentObject', function () {
    $scope.criteria.student = $scope.criteria.studentObject ? $scope.criteria.studentObject.id : null;
  });

  var loadData = $scope.loadData;
  $scope.loadData = function() {
    FormUtils.withValidForm($scope.searchForm, loadData);
  };

  $scope.directiveControllers = [];
  var clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function () {
    clearCriteria();
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };
  
}]);
