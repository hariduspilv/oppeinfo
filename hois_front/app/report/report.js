'use strict';

angular.module('hitsaOis').controller('ReportStudentController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
  function ($q, $scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var certificateMapper = Classifier.valuemapper({occupationCode: 'KUTSE', partOccupationCode: 'OSAKUTSE', specialityCode: 'SPETSKUTSE'});
    var clMapper = Classifier.valuemapper({fin: 'FINALLIKAS', finSpecific: 'FINTAPSUSTUS', language: 'OPPEKEEL',
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
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.isHigher = $scope.auth.higher;
    };

    $scope.formState = {
      studentGroups: QueryUtils.endpoint('/autocomplete/studentgroups').query(),
      filterStudentGroups: [],
      xlsUrl: 'reports/students/students.xls'
    };

    $scope.curriculumVersionChanged = function() {
      $scope.formState.filterStudentGroups = [];
      if ($scope.criteria.curriculumVersion) {
        $scope.formState.studentGroups.forEach(function(studentGroup) {
          if (studentGroup.curriculumVersion !== $scope.criteria.curriculumVersion.id) {
            $scope.formState.filterStudentGroups.push(studentGroup.id);
          }
        });
      }
    };

    $q.all(certificateMapper.promises).then(function() {
      $q.all(clMapper.promises).then($scope.loadData);
    });
  }
]).controller('ReportStudentStatisticsController', ['$scope', 'Classifier', 'QueryUtils',
  function ($scope, Classifier, QueryUtils) {
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatistics.xls',
                        filterValues: {OPPURSTAATUS: ['OPPURSTAATUS_K', 'OPPURSTAATUS_L']}};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics', {result: 'OPPEVORM'}, function() {
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

    $scope.loadData();
  }
]).controller('ReportStudentStatisticsByperiodController', ['$scope', '$route', 'Classifier', 'QueryUtils', 'Session',
  function ($scope, $route, Classifier, QueryUtils, Session) {
    $scope.auth = $route.current.locals.auth;
    var classifierMapping = {OPPURSTAATUS_A: 'AKADPUHKUS_POHJUS', OPPURSTAATUS_K: 'EKSMAT_POHJUS'};
    var school = Session.school || {};
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatisticsbyperiod.xls'};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics/byperiod', {result: 'OPPURSTAATUS_A'}, function() {
      var resultType = $scope.criteria.result;
      if($scope.formState.resultType !== resultType) {
        $scope.formState.resultType = resultType;
        var mainClassCode = classifierMapping[resultType];
        $scope.formState.resultDef = mainClassCode ? Classifier.queryForDropdown({mainClassCode: mainClassCode, higher: school.higher, vocational: school.vocational}) : undefined;
      }
    });
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

    QueryUtils.createQueryForm($scope, '/reports/curriculums/completion', {order: 'p.lastname,p.firstname', isHigher: $scope.auth.higher}, clMapper.objectmapper);
    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function() {
      _clearCriteria();
      $scope.criteria.isHigher = $scope.auth.higher;
    };

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('ReportTeacherLoadHigherController', ['$scope', '$timeout', 'DataUtils', 'FormUtils', 'QueryUtils',
  function ($scope, $timeout, DataUtils, FormUtils, QueryUtils) {
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
]).controller('ReportTeacherLoadVocationalController', ['$scope', '$timeout', 'DataUtils', 'FormUtils', 'QueryUtils',
  function ($scope, $timeout, DataUtils, FormUtils, QueryUtils) {
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
]).controller('StudentGroupTeacherController', ['$httpParamSerializer', '$route', '$scope', '$sessionStorage', '$timeout', 'Classifier', 'DataUtils', 'VocationalGradeUtil', 'QueryUtils', 'config', 'dialogService', 'message',
function ($httpParamSerializer, $route, $scope, $sessionStorage, $timeout, Classifier, DataUtils, VocationalGradeUtil, QueryUtils, config, dialogService, message) {
  $scope.gradeUtil = VocationalGradeUtil;
  $scope.auth = $route.current.locals.auth;
  
  var baseUrl = '/reports/studentgroupteacher';
  var resultsMapper = Classifier.valuemapper({grade: 'KUTSEHINDAMINE', entryType: 'SISSEKANNE', absence: 'PUUDUMINE'});
  var entryTypesOrder = ['SISSEKANNE_H', 'SISSEKANNE_R', 'SISSEKANNE_O', 'SISSEKANNE_L', 'SISSEKANNE_P', 'SISSEKANNE_T', 'SISSEKANNE_E', 'SISSEKANNE_I'];
  $scope.entryTypeColors = {
    'SISSEKANNE_H': 'green-300',
    'SISSEKANNE_R': 'indigo-300',
    'SISSEKANNE_O': 'teal-300',
    'SISSEKANNE_L': 'pink-300'
  };

  if ($scope.auth.isTeacher()) {
    $scope.teacherId = $scope.auth.teacher;
  }

  $scope.formState = {
    studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), 
    studyPeriods: {},
    studentGroups: QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true, higher: false, studentGroupTeacherId: $scope.teacherId}),
    xlsUrl: 'reports/studentgroupteacher/studentgroupteacher.xls',
    pdfUrl: 'reports/studentgroupteacher/studentgroupteacher.pdf'
  };

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
      $timeout(searchUsingStoredCriteria);
    }
  }

  if(!$scope.storedCriteria || angular.equals({}, $scope.storedCriteria)) {
    $scope.criteria = {entryType: {'SISSEKANNE_H': true, 'SISSEKANNE_R': true, 'SISSEKANNE_O': true, 'SISSEKANNE_L': true}};
  } else {
    $scope.criteria = $scope.storedCriteria;
    $scope.formState.studyPeriod = $scope.storedCriteria.formState.studyPeriod;
    $scope.formState.studentGroup = $scope.storedCriteria.formState.studentGroup;
    if ($scope.formState.studentGroup && ($scope.criteria.studyYear || $scope.criteria.from)) {
      $timeout(searchUsingStoredCriteria);
    }
  }

  Classifier.queryForDropdown({ mainClassCode: 'SISSEKANNE' }, function (result) {
    var entryTypes = Classifier.toMap(result);
    $scope.entryTypes = Object.keys(entryTypes).map(function(it) { 
      return entryTypes[it];
    });
    $scope.entryTypes.sort(function(a,b) {
      if (entryTypesOrder.indexOf(a.code) === -1) {
        return 1;
      } else if (entryTypesOrder.indexOf(b.code) === -1) {
        return -1;
      } else {
        return entryTypesOrder.indexOf(a.code) > entryTypesOrder.indexOf(b.code);
      }
    });
  });

  $scope.getEntryColor = function (type) {
    return $scope.entryTypeColors[type];
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

    $scope.criteria.entryTypes = [];
    angular.forEach($scope.criteria.entryType, function (boolean, type) {
      if (boolean) {
        $scope.criteria.entryTypes.push(type);
      }
    });

    QueryUtils.loadingWheel($scope, true);
    QueryUtils.endpoint(baseUrl).get($scope.criteria).$promise.then(function (result) {
      $scope.record = result;
      $scope.record.students.forEach(function (student) {
        student.resultColumns.forEach(function (column) {
          if (column.journalResult && column.journalResult.entries) {
            resultsMapper.objectmapper(column.journalResult.entries);
            column.journalResult.entries.forEach(function (entry) {
              resultsMapper.objectmapper(entry.lessonAbsences);
            });
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
      QueryUtils.loadingWheel($scope, false);
      $scope.criteria.formState = {studyPeriod: $scope.formState.studyPeriod, studentGroup: $scope.formState.studentGroup};
      $scope.toStorage(baseUrl, $scope.criteria);
      $scope.$broadcast('refreshFixedColumns');
    });
  };

  $scope.clearCriteria = function() {
    $scope.formState.studyPeriod = null;
    $scope.formState.studentGroup = null;
    $scope.criteria = {};
  };

  $scope.openAddInfoDialog = function (student) {
    dialogService.showDialog('report/studentgroup.teacher.addinfo.html', function (dialogScope) {
      dialogScope.student = student;
      dialogScope.entriesWithAddInfo = [];

      var addedJournals = [];
      dialogScope.student.resultColumns.forEach(function (resultColumn) {
        if (resultColumn.journalResult) {
          if (addedJournals.indexOf(resultColumn.journalResult.id) === -1) {
            resultColumn.journalResult.entries.forEach(function (entry) {
              if (entry.addInfo) {
                dialogScope.entriesWithAddInfo.push(entry);
              }
            });
            addedJournals.push(resultColumn.journalResult.id);
          }
        }
      });
    });
  };
}
]);
