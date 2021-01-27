'use strict';

angular.module('hitsaOis').controller('ReportStudentController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
  function ($q, $scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var certificateMapper = Classifier.valuemapper({occupationCode: 'KUTSE', partOccupationCode: 'OSAKUTSE', specialityCode: 'SPETSKUTSE'});
    var clMapper = Classifier.valuemapper({dormitory: 'YHISELAMU', fin: 'FINALLIKAS', finSpecific: 'FINTAPSUSTUS', language: 'OPPEKEEL',
      studyLevel: 'OPPEASTE', studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', status: 'OPPURSTAATUS', previousStudyLevel: 'OPPEASTE'});

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

    var _loadData = $scope.loadData;
    $scope.loadData = function() {
      $scope.$broadcast('refreshFixedColumns');
      _loadData();
    };

    $scope.toggleShowAllParameters = function () {
      $scope.formState.showAllParameters = !$scope.formState.showAllParameters;
    };

    $scope.formState = {
      xlsUrl: 'reports/students/students.xls',
      showAllParameters: false
    };

    $scope.curriculumVersionChanged = function() {
      $scope.studentGroupName = null;
      $scope.criteria.studentGroup = null;
    };

    $q.all(certificateMapper.promises).then(function() {
      $q.all(clMapper.promises).then($scope.loadData);
    });
  }
]).controller('ReportStudentCountController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils',
function ($q, $scope, $route, Classifier, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    xlsUrl: 'reports/students/count.xls',
  };
  var clMapper = Classifier.valuemapper({occupationCode: 'KUTSE', partOccupationCode: 'OSAKUTSE', specialityCode: 'SPETSKUTSE'});
  QueryUtils.createQueryForm($scope, '/reports/students/count');

  $scope.clearCriteria = function() {
    $scope.formState = {
      xlsUrl: 'reports/students/count.xls',
    };
  };

  $scope.getObjectOrderNumber = function() {
    switch ($scope.criteria.resultType) {
      case 'COUNT_STAT_STUDY_LEVEL':
        return "c.orig_study_level_code, oslc.name_et, oslc.name_en";
      case 'COUNT_STAT_STUDENT_GROUP':
        return "sg.code";
      case 'COUNT_STAT_STRUCTURAL_UNIT':
        return "sd1.name_et, sd1.name_en";
      case 'COUNT_STAT_AGE':
        return "cast(v.agefrom as int)";
      case 'COUNT_STAT_CURRICULA':
        return "c.name_et || ' (' || c.code || ')', c.name_en || ' (' || c.code || ')'";
      case 'COUNT_STAT_COURSE':
        return "sg.course";
      case 'COUNT_STAT_ISCED_VALD':
        return "c_connected.name_et, c_connected.name_en";
      case 'COUNT_STAT_MUNICIPALITY':
        return "c_municipality.name_et, c_municipality.name_en";
      case 'COUNT_STAT_COUNTY':
        return "c_county.name_et, c_county.name_en";
      default:
        break;
      }
  };

  $scope.clearSort = function() {
    $scope.criteria.order = undefined;
  };

  $scope.resultTypeChanged = function(resultType) {
    if (resultType === 'COUNT_STAT_AGE') {
      $scope.formState.ageFrom = 15;
      $scope.formState.ageStep = 5;
    } else {
      $scope.formState.ageFrom = undefined;
      $scope.formState.ageStep = undefined;
    }
    $scope.clearSort();
  };

  $scope.resultTypes = ["COUNT_STAT_OVERALL",
                        "COUNT_STAT_STUDY_LEVEL",
                        "COUNT_STAT_STUDENT_GROUP",
                        "COUNT_STAT_STRUCTURAL_UNIT",
                        "COUNT_STAT_AGE",
                        "COUNT_STAT_CURRICULA",
                        "COUNT_STAT_COURSE",
                        "COUNT_STAT_ISCED_VALD",
                        "COUNT_STAT_COUNTY",
                        "COUNT_STAT_MUNICIPALITY"];

  function initCriteria() {
    $scope.formState.studentTypes = ['OPPUR_T', 'OPPUR_K'];
    $scope.formState.perStatus = true;
    $scope.formState.from = new Date();
    $scope.formState.resultType = 'COUNT_STAT_OVERALL';
  }

  initCriteria();

  $scope.loadResults = function() {
    // reset page when changing statistics type
    $scope.clearSort();
    $scope.criteria.page = 1;
    $scope.criteria = angular.merge({}, $scope.criteria, $scope.formState);
    $scope.criteria.studentTypes = angular.copy($scope.formState.studentTypes);
    $scope.loadData();
  };

  $q.all(clMapper.promises).then(function() {
    $scope.loadResults();
  });
}
]).controller('ReportStudentMovementController', ['$q', '$scope', '$route', 'QueryUtils', 'DataUtils',
function ($q, $scope, $route, QueryUtils, DataUtils) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    xlsUrl: 'reports/students/movement.xls'
  };

  $scope.staticState = {
    studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query()
  };

  QueryUtils.createQueryForm($scope, '/reports/students/movement');

  $scope.staticState.studyYears.$promise.then(function () {
    if (!$scope.formState.from && !$scope.formState.thru) {
      var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.staticState.studyYears);
      if (sy) {
        DataUtils.convertStringToDates(sy, ['from', 'thru']);
        $scope.formState.from = sy ? sy.startDate : null;
        $scope.formState.thru = sy ? sy.endDate : null;
      }
    }
  });

  $scope.clearCriteria = function() {
    $scope.formState = {
      xlsUrl: 'reports/students/movement.xls'
    };
  };

  $scope.clearSort = function() {
    $scope.criteria.order = undefined;
  };

  $scope.staticState.queryTypes = ["MOVEMENT_STAT_SUM",
                                  "MOVEMENT_STAT_CURRICULUM_GROUP",
                                  "MOVEMENT_STAT_STRUCTURAL_UNIT",
                                  "MOVEMENT_STAT_CURRICULA",
                                  "MOVEMENT_STAT_STUDENT_GROUP",
                                  "MOVEMENT_STAT_COURSE",
                                  "MOVEMENT_STAT_STUDY_LEVEL"];

  function initCriteria() {
    $scope.formState.queryType = 'MOVEMENT_STAT_SUM';
  }

  initCriteria();

  $scope.loadResults = function() {
    // reset page when changing statistics type
    $scope.criteria.page = 1;
    $scope.criteria = angular.merge({}, $scope.criteria, $scope.formState);
    $scope.loadData();
  };

  $q.all([$scope.staticState.studyYears.$promise]).then(function() {
    if (!!$scope.formState.from && !!$scope.formState.thru) {
      $scope.loadResults();
    }
  });
}
]).controller('ReportStudentDataController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils', 'classifierAutocomplete', 'message', 'FormUtils', '$translate', '$rootScope', 'dialogService',
function ($q, $scope, $route, Classifier, QueryUtils, classifierAutocomplete, message, FormUtils, $translate, $rootScope, dialogService) {
  $scope.auth = $route.current.locals.auth;
  $scope.currentNavItem = 'student.data.search';
  $scope.savedHeader = {};
  var clMapper = Classifier.valuemapper({sex: 'SUGU', residenceCountry: 'RIIK', citizenship: 'RIIK', 
    directiveTypes: 'KASKKIRI', stiptoetlReason: 'KASKKIRI_STIPTOETL_POHJUS', fin: 'FINTAPSUSTUS', 
    exmatReason: 'EKSMAT_POHJUS', akadReason: 'AKADPUHKUS_POHJUS',
    studentStatuses: 'OPPURSTAATUS', studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', studyLevel: 'OPPEASTE', 
    language: 'OPPEKEEL', activeResult: 'KORGHINDAMINE', foreignLanguage: 'EHIS_VOORKEEL', previousStudyLevel: 'OPPEASTE', 
    dormitory: 'YHISELAMU'});
    
  QueryUtils.createQueryForm($scope, '/reports/students/data', {sort: 'firstname, lastname, foreign_language_et'}, clMapper.objectmapper);

  function getSavedQueries() {
    $scope.formState.savedQueries = QueryUtils.endpoint('/reports/students/data/query/savedStudentQueries').query({isStudentQuery: true});
  }

  function initCollapsable() {
    $scope.collapsable = {
      personDataExpanded: true,
      studyDataExpanded: false,
      contactDataExpanded: false,
      statisticsExpanded: false,
      addInfoExpanded: false,
      isSortingExpanded: false,
      queryExpanded: false,
    };
  }

  initCollapsable();

  function initState() {
    $scope.searched = false;
    $scope.formState = {
      xlsUrl: 'reports/students/data.xls',
      biggerThan: '>',
      smallerThan: '<',
      equal: '=',
      orderFields1: [],
      orderFields2: [],
      orderFields3: [],
      criteria: {
        resultType: 'STUDENT_DATA_ALL',
        fullname: false
      }
    };

    $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });

    getSavedQueries();

    // clear everything from criteria except size, page and sort
    // this should keep link to queryutil
    var notChangeableCriteria = ["size", "page", "sort"];
    var fieldNames = Object.keys($scope.criteria);
    fieldNames.forEach(function(fieldName) {
      if (notChangeableCriteria.indexOf(fieldName) === -1) {
        $scope.criteria[fieldName] = undefined;
      }
    });
  }

  $scope.deleteStudentQuery = function(studentQueryId) {
    dialogService.confirmDialog({prompt: 'report.studentData.query.message.delete'}, function() {
      var StudentQueryEndPoint = QueryUtils.endpoint('/reports/students/data/query');
      new StudentQueryEndPoint().$delete({id: studentQueryId}).then(function () {
        message.info('main.messages.delete.success');
        getSavedQueries();
      }).catch(angular.noop);
    });
  };

  $scope.loadQueryToForm = function(studentQueryId) {
    $scope.formState.criteria = QueryUtils.endpoint('/reports/students/data/query/' + studentQueryId).get({}, function () {
      message.info('report.studentData.query.message.queryLoaded');
      $scope.updateOrderFields();
      initCollapsable();
    });
  };

  $scope.hasValue = function(field) {
    return field !== undefined && field !== null && field !== "";
  };

  $scope.isEmpty = function(field) {
    return field === undefined || field === null || field === "";
  };

  $scope.filterOrderField = function (field1Val, field2Val) {
    return function (field) {
      return field.val !== field1Val && field.val !== field2Val;
    };
  };

  $scope.clearFields = function(attributename1, attributename2) {
    $scope.formState.criteria[attributename1] = null;
    if (attributename2 !== undefined) {
      $scope.formState.criteria[attributename2] = null;
    }
  };

  $scope.isCheckboxed = function() {
    var groupNames = Object.keys($scope.formState.criteria);
    var checkBoxed = false;
    groupNames.forEach(function(groupName) {
      if (groupName.endsWith("Show") && $scope.formState.criteria[groupName] === true && groupName !== 'nrShow') {
        checkBoxed = true;
      }
    });
    return checkBoxed;
  };

  function pushToList(variableName, list) {
    var variable = {};
    variable.translation = "report.studentData." + variableName;
    variable.val = mapVariableName(variableName);
    list.push(variable);
  }

  $scope.updateOrderFields = function() {
    var returnableList = [];
    var groupNames = Object.keys($scope.formState.criteria);
    groupNames.forEach(function(groupName) {
      if (groupName.endsWith("Show") && $scope.formState.criteria[groupName] === true && groupName !== 'nrShow') {
        if (groupName === 'activeResultShow') {
          pushToList('activeResultSubject', returnableList);
        }
        pushToList(groupName.replace("Show", ""), returnableList);
      }
    });
    $scope.formState.orderFields1 = angular.copy(returnableList);
    $scope.formState.orderFields2 = angular.copy(returnableList);
    $scope.formState.orderFields3 = angular.copy(returnableList);
  };

  function mapVariableName(variableName) {
    if (variableName === 'fullname') {
      if ($scope.formState.criteria[variableName] === true) {
        return "p.lastname || ' ' || p.firstname";
      }
      return "p.firstname || ' ' || p.lastname";
    } else if (['curriculum', 'activeResultSubject', 'declaredSubject', 'foreignLanguage'].indexOf(variableName) >= 0) {
      return variableName + ($translate.use() === 'en' ? 'En' : 'Et');
    } else if (variableName === 'studyYearNumber') {
      return "study_year_nr";
    } else if (variableName === 'activeResult') {
      return 'gradeCode';
    }
    return variableName;
  }
  
  initState();

  $scope.getDirectiveReason = function(row) {
    if (row.stiptoetlReason !== null && row.stiptoetlReason !== undefined) {return row.stiptoetlReason;}
    if (row.exmatReason !== null && row.exmatReason !== undefined) {return row.exmatReason;}
    if (row.akadReason !== null && row.akadReason !== undefined) {return row.akadReason;}
    return null;
  };

  $scope.saveQuery = function() {
    FormUtils.withValidForm($scope.queryForm, function() {
      //Post
      if ($scope.savedHeader.queryNameEt === undefined || $scope.savedHeader.queryNameEt === null || $scope.savedHeader.queryNameEt === "") {
        message.error('report.studentData.query.message.error.name');
        return;
      }
      var CriteriaEndPoint = QueryUtils.endpoint('/reports/students/data/query/save');
      var parameters = new CriteriaEndPoint($scope.savedHeader);
      parameters.$save().then(function() {
        message.info('main.messages.create.success');
        getSavedQueries();
      }).catch(angular.noop);
    });
  };

  $scope.excelDL = function(url, criteria) {
    var extendedCriteria = angular.extend({}, criteria, $scope.savedHeader.criteria);
    return $rootScope.excel(url, extendedCriteria);
  };

  $scope.getDirectiveReasons = function() {
    if ($scope.formState.criteria.directiveTypes !== null) {
      var reasons = [];
      if ($scope.formState.criteria.directiveTypes.indexOf('KASKKIRI_EKSMAT') !== -1) {
        reasons.push('EKSMAT_POHJUS');
      }
      if ($scope.formState.criteria.directiveTypes.indexOf('KASKKIRI_AKAD') !== -1) {
        reasons.push('AKADPUHKUS_POHJUS');
      }
      if ($scope.formState.criteria.directiveTypes.indexOf('KASKKIRI_STIPTOETL') !== -1) {
        reasons.push('KASKKIRI_STIPTOETL_POHJUS');
      }
      return reasons.join(', ');
    }
  };

  $scope.countryAutocompleteSearch = function(queryText) {
    return classifierAutocomplete.searchByName(queryText, 'RIIK');
  };

  $scope.clearCriteria = function() {
    initState();
  };

  $scope.clearSort = function() {
    $scope.criteria.order = undefined;
  };

  $scope.resultTypeChanged = function(resultType) {
    if (resultType === 'COUNT_STAT_AGE') {
      $scope.formState.ageFrom = 15;
      $scope.formState.ageStep = 5;
    } else {
      $scope.formState.ageFrom = undefined;
      $scope.formState.ageStep = undefined;
    }
    $scope.clearSort();
  };

  $scope.resultTypes = ["STUDENT_DATA_ALL",
                        "STUDENT_DATA_ACTIVE",
                        "STUDENT_DATA_STUDYING",
                        "STUDENT_DATA_STUDENTGROUP",
                        "STUDENT_DATA_GRADUATES"];

  $scope.loadResults = function() {
    FormUtils.withValidForm($scope.searchForm, function() {
      // reset page when changing statistics type
      var checkBoxed = $scope.isCheckboxed();
      if (!checkBoxed) {
        message.error('report.studentData.error.showInResults');
        $scope.searched = false;
        return;
      }
      $scope.criteria.page = 1;
      $scope.criteria = angular.extend({}, $scope.criteria, $scope.formState.criteria);
      $scope.savedHeader = angular.copy($scope.formState.criteria);
      $q.all(clMapper.promises).then($scope.loadData);
      $scope.searched = true;
    });
  };
}
]).controller('ReportSubjectStudyPeriodController', ['$q', '$scope', '$route', 'Classifier', 'QueryUtils', 'message', 'FormUtils', '$rootScope', 'dialogService',
function ($q, $scope, $route, Classifier, QueryUtils, message, FormUtils, $rootScope, dialogService) {
  $scope.auth = $route.current.locals.auth;
  $scope.currentNavItem = 'subject.study.period.search';
  $scope.savedHeader = {};
  var clMapper = Classifier.valuemapper({studyYear: 'OPPEAASTA'});
    
  QueryUtils.createQueryForm($scope, '/reports/subjectStudyPeriod/data', {sort: 's.name_et, s.name_en'}, clMapper.objectmapper, true);

  function getSavedQueries() {
    $scope.formState.savedQueries = QueryUtils.endpoint('/reports/students/data/query/savedStudentQueries').query({isStudentQuery: false});
  }

  function initCollapsable() {
    $scope.collapsable = {
      subjectExpanded: true,
      subjectStudyPeriodExpanded: false,
      queryExpanded: false
    };
  }

  initCollapsable();

  function initState() {
    $scope.searched = false;
    $scope.formState = {
      xlsUrl: 'reports/subjectStudyPeriod/data.xls',
      biggerThan: '>',
      smallerThan: '<',
      equal: '=',
      criteria: {}
    };

    $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query({}, function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod.display = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });

    getSavedQueries();

    // clear everything from criteria except size, page and sort
    // this should keep link to queryutil
    var notChangeableCriteria = ["size", "page", "sort"];
    var fieldNames = Object.keys($scope.criteria);
    fieldNames.forEach(function(fieldName) {
      if (notChangeableCriteria.indexOf(fieldName) === -1) {
        $scope.criteria[fieldName] = undefined;
      }
    });
  }

  $scope.deleteStudentQuery = function(studentQueryId) {
    dialogService.confirmDialog({prompt: 'report.studentData.query.message.delete'}, function() {
      var StudentQueryEndPoint = QueryUtils.endpoint('/reports/students/data/query');
      new StudentQueryEndPoint().$delete({id: studentQueryId}).then(function () {
        message.info('main.messages.delete.success');
        getSavedQueries();
      }).catch(angular.noop);
    });
  };

  $scope.loadQueryToForm = function(studentQueryId) {
    $scope.formState.criteria = QueryUtils.endpoint('/reports/subjectStudyPeriod/data/query/' + studentQueryId).get({}, function () {
      message.info('report.studentData.query.message.queryLoaded');
      initCollapsable();
    });
  };

  $scope.hasValue = function(field) {
    return field !== undefined && field !== null && field !== "";
  };

  $scope.isEmpty = function(field) {
    return field === undefined || field === null || field === "";
  };

  $scope.filterOrderField = function (field1Val, field2Val) {
    return function (field) {
      return field.val !== field1Val && field.val !== field2Val;
    };
  };

  $scope.clearFields = function(attributename1, attributename2) {
    $scope.formState.criteria[attributename1] = null;
    if (attributename2 !== undefined) {
      $scope.formState.criteria[attributename2] = null;
    }
  };

  function setFalseToNulls() {
    var groupNames = Object.keys($scope.savedHeader);
    groupNames.forEach(function(groupName) {
      if (groupName.endsWith("Show") && $scope.savedHeader[groupName] === false) {
        $scope.savedHeader[groupName] = undefined;
      }
    });
  }

  $scope.isCheckboxed = function() {
    var groupNames = Object.keys($scope.formState.criteria);
    var checkBoxed = false;
    groupNames.forEach(function(groupName) {
      if (groupName.endsWith("Show") && $scope.formState.criteria[groupName] === true) {
        checkBoxed = true;
      }
    });
    return checkBoxed;
  };
  
  initState();

  $scope.saveQuery = function() {
    FormUtils.withValidForm($scope.queryForm, function() {
      //Post
      if ($scope.savedHeader.queryNameEt === undefined || $scope.savedHeader.queryNameEt === null || $scope.savedHeader.queryNameEt === "") {
        message.error('report.studentData.query.message.error.name');
        return;
      }
      setFalseToNulls();
      var CriteriaEndPoint = QueryUtils.endpoint('/reports/subjectStudyPeriod/data/query/save');
      var parameters = new CriteriaEndPoint($scope.savedHeader);
      parameters.$save().then(function() {
        message.info('main.messages.create.success');
        getSavedQueries();
      }).catch(angular.noop);
    });
  };

  $scope.excelDL = function(url, criteria) {
    var extendedCriteria = angular.extend({}, criteria, $scope.savedHeader.criteria);
    return $rootScope.excel(url, extendedCriteria);
  };

  $scope.clearCriteria = function() {
    initState();
  };

  $scope.clearSort = function() {
    $scope.criteria.order = undefined;
  };

  $scope.loadResults = function() {
    FormUtils.withValidForm($scope.searchForm, function() {
      // reset page when changing statistics type
      var checkBoxed = $scope.isCheckboxed();
      if (!checkBoxed) {
        message.error('report.studentData.error.showInResults');
        $scope.searched = false;
        return;
      }
      $scope.criteria.page = 1;
      $scope.criteria = angular.extend({}, $scope.criteria, $scope.formState.criteria);
      $scope.savedHeader = angular.copy($scope.formState.criteria);
      $q.all(clMapper.promises).then($scope.loadData);
      $scope.searched = true;
    });
  };
}
])
.controller('ReportStudentEducationalSuccessController', ['$q', '$scope', '$route', 'QueryUtils', 'DataUtils', 'Classifier', 'message', 'FormUtils', 'ExcelUtils', '$rootScope',
function ($q, $scope, $route, QueryUtils, DataUtils, Classifier, message, FormUtils, ExcelUtils, $rootScope) {
  $scope.auth = $route.current.locals.auth;
  $scope.initEntryTypes = function() {
    $scope.entryTypes = [
      {label:"SISSEKANNE_H", selected: true},
      {label:"SISSEKANNE_R", selected: true},
      {label:"SISSEKANNE_L", selected: true},
      {label:"SISSEKANNE_O", selected: true},
      {label:"SISSEKANNE_M", selected: true}
    ];
    return true;
  };

  function initFormstate() {
    $scope.formState = {
      xlsUrl: 'reports/students/educationalSuccess.xls',
      biggerThan: '>',
      smallerThan: '<',
      equal: '=',
      studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(),
      statisticsTypes: ["EDUCATIONAL_SUCCESS_HAS_DEBT",
                        "EDUCATIONAL_SUCCESS_NO_DEBT",
                        "EDUCATIONAL_SUCCESS_RESULTS",
                        "EDUCATIONAL_SUCCESS_STUDY_DIRECTOR_REPORT",
                        "EDUCATIONAL_SUCCESS_BEST_RESULTS"]
    };
    $scope.initEntryTypes();
  }

  initFormstate();

  $scope.entryTypeColors = {
    'SISSEKANNE_H': 'green-300',
    'SISSEKANNE_R': 'indigo-300',
    'SISSEKANNE_O': 'teal-300',
    'SISSEKANNE_L': 'pink-300'
  };

  $scope.grades = Classifier.queryForDropdown({mainClassCode: 'KUTSEHINDAMINE', order: 'value'}, defaultSelect);

  function defaultSelect(response) {
    var selectedGrades = ['KUTSEHINDAMINE_3', 'KUTSEHINDAMINE_4', 'KUTSEHINDAMINE_5', 'KUTSEHINDAMINE_A'];
    response.forEach(function(grade) {
      if (selectedGrades.indexOf(grade.code) !== -1) {
        grade.selected = true;
      }
    });
  }

  $scope.getEntryColor = function (type) {
    return $scope.entryTypeColors[type];
  };

  $scope.hasValue = function(field) {
    return field !== undefined && field !== null && field !== "";
  };

  $scope.isEmpty = function(field) {
    return field === undefined || field === null || field === "";
  };

  QueryUtils.createQueryForm($scope, '/reports/students/educationalSuccess', {}, null, true);

  $scope.setToday = function() {
    $scope.criteria.thru = new Date();
  };

  $scope.preselectFrom = function() {
    $scope.setToday();
    if ($scope.criteria.queryType === 'EDUCATIONAL_SUCCESS_STUDY_DIRECTOR_REPORT') {
      $scope.formState.studyYears.$promise.then(function () {
          var sy = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyYears);
          DataUtils.convertStringToDates(sy, ['from', 'thru']);
          if (sy) {
            $scope.criteria.from = sy ? sy.startDate : null;
          }
      });
    } else {
      $scope.criteria.from = null;
    }
  };

  $scope.clearSortAndTable = function() {
    var savedType = $scope.criteria.queryType;
    $scope.initEntryTypes();
    $scope.clearCriteria();
    entryTypeToCriteria();
    gradeToCriteria();
    gradeToFormState();
    $scope.criteria.order = undefined;
    $scope.tabledata.content = [];
    $scope.criteria.queryType = savedType;
    $scope.criteria.perGroup = true;
    return true;
  };

  $scope.clearOrder = function() {
    $scope.criteria.order = undefined;
  };

  function entryTypeToCriteria() {
    $scope.criteria.gradeType = [];
    $scope.entryTypes.forEach(function(entryType) {
      if (entryType.selected) {
        $scope.criteria.gradeType.push(entryType.label);
      }
    });
  }

  function gradeToCriteria() {
    $scope.criteria.countableGrades = [];
    $scope.criteria.countableGradeValues = [];
    $scope.grades.forEach(function(grade) {
      if (grade.selected) {
        $scope.criteria.countableGrades.push(grade.code);
        $scope.criteria.countableGradeValues.push(grade.value);
      }
    });
  }

  function gradeToFormState() {
    $scope.formState.countableGrades = [];
    $scope.grades.forEach(function(grade) {
      if (grade.selected) {
        $scope.formState.countableGrades.push(grade.value);
      }
    });
  }
  
  $scope.toExcel = function () {
    FormUtils.withValidForm($scope.searchForm, function () {
      $scope.criteria.perGroup = $scope.formState.perGroup;
      entryTypeToCriteria();
      gradeToCriteria();
      if ($scope.criteria.countableGrades.length === 0 && $scope.criteria.queryType === 'EDUCATIONAL_SUCCESS_RESULTS') {
        message.error('report.studentSuccess.error.countableGrades');
        return;
      }
      if ($scope.criteria.gradeType.length === 0) {
        message.error('report.studentSuccess.error.gradeType');
        return;
      }
      gradeToFormState();
      ExcelUtils.get($rootScope.excel('reports/students/educationalSuccess.xls', $scope.criteria), 'educationalSuccess', $scope);
    });
  };

  $scope.loadResults = function() {
    $scope.criteria.perGroup = $scope.formState.perGroup;
    entryTypeToCriteria();
    gradeToCriteria();
    // reset page when searching
    $scope.criteria.page = 1;
    $scope.tabledata.content = [];
    if ($scope.criteria.countableGrades.length === 0 && $scope.criteria.queryType === 'EDUCATIONAL_SUCCESS_RESULTS') {
      message.error('report.studentSuccess.error.countableGrades');
      return;
    }
    if ($scope.criteria.gradeType.length === 0) {
      message.error('report.studentSuccess.error.gradeType');
      return;
    }
    gradeToFormState();
    $scope.loadData();
  };
}
]).controller('ReportStudentStatisticsController', ['$route', '$scope', 'Classifier', 'QueryUtils', 'message',
  function ($route, $scope, Classifier, QueryUtils, message) {
    $scope.auth = $route.current.locals.auth;
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatistics.xls',
                        filterValues: {OPPURSTAATUS: ['OPPURSTAATUS_K', 'OPPURSTAATUS_L']}};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics', {
      order: $scope.currentLanguage() === 'en' ? 'c.nameEn' : 'c.nameEt',
      result: 'OPPEVORM',
      date: new Date().withoutTime()
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
]).controller('ReportGuestStudentStatisticsController', ['$route', '$scope', 'Classifier', 'QueryUtils', '$q',
function ($route, $scope, Classifier, QueryUtils, $q) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    departments: QueryUtils.endpoint('/autocomplete/curriculumdepartments').query(),
    xlsUrl: 'reports/gueststudents/statistics/gueststudentstatistics.xls'
  };
  var clMapper = Classifier.valuemapper({homeCountry: 'RIIK', programme: 'VALISKOOL_PROGRAMM'});
  QueryUtils.createQueryForm($scope, '/reports/gueststudents/statistics', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

  function loadEducationLevelOptions() {
    QueryUtils.endpoint('/reports/educationalLevels').query().$promise.then(function(response){
      $scope.educationLevelOptions = response;
    });
  }

  loadEducationLevelOptions();

  function filterEducationOptions() {
    $scope.educationLevelOptions = $scope.educationLevelOptions.filter(function(educationLevel) {
      if ($scope.hiddenCriteria.curriculumObj.origStudyLevel !== undefined && 
        $scope.hiddenCriteria.curriculumObj.origStudyLevel !== null &&
        $scope.hiddenCriteria.curriculumObj.origStudyLevel.endsWith(educationLevel.value2)) {
        return true;
      }
      return false;
    });
  }

  /** Load departments and educationLevelOptions when curriculum changes */
  $scope.$watch('hiddenCriteria.curriculumObj', function() {
    // Check if I can remove curriculum version from field
    if ($scope.hiddenCriteria.curriculumVersionObj &&
       ($scope.hiddenCriteria.curriculumObj || {}).id !== $scope.hiddenCriteria.curriculumVersionObj.curriculum) {
      $scope.hiddenCriteria.curriculumVersionObj = undefined;
    }
    if (angular.isObject($scope.hiddenCriteria.curriculumObj) && $scope.hiddenCriteria.curriculumObj.id !== undefined) {
      $scope.criteria.curriculum = $scope.hiddenCriteria.curriculumObj.id;
      $scope.formState.departments = QueryUtils.endpoint('/autocomplete/curriculumdepartments').query({id: $scope.criteria.curriculum});
      filterEducationOptions();
    } else {
      $scope.criteria.curriculum = undefined;
      $scope.formState.departments = QueryUtils.endpoint('/autocomplete/curriculumdepartments').query();
      loadEducationLevelOptions();
    }
  });

  $scope.$watch('hiddenCriteria.curriculumVersionObj', function (newV, oldV) {
    if (newV === oldV) {
      return;
    }
    $scope.criteria.curriculumVersion = ($scope.hiddenCriteria.curriculumVersionObj || {}).id;
  });

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });

}
]).controller('ReportForeignStudentStatisticsController', ['$route', '$scope', 'Classifier', 'QueryUtils', '$q',
function ($route, $scope, Classifier, QueryUtils, $q) {
  $scope.auth = $route.current.locals.auth;
  $scope.formState = {
    departments: QueryUtils.endpoint('/autocomplete/curriculumdepartments').query(),
    xlsUrl: 'reports/foreignstudents/statistics/foreignstudentstatistics.xls',
    educationLevelCriteria: getEducationLevelCriteria()
  };
  var clMapper = Classifier.valuemapper({
    educationLevel: 'HARIDUSTASE',
    foreignCountry: 'RIIK',
    programme: 'VALISKOOL_PROGRAMM',
    extention: 'NOM_PIKEND'
  });
  QueryUtils.createQueryForm($scope, '/reports/foreignstudents/statistics', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

  function loadEducationLevelOptions() {
    QueryUtils.endpoint('/reports/educationalLevels').query().$promise.then(function(response){
      $scope.educationLevelOptions = response;
    });
  }

  loadEducationLevelOptions();

  function filterEducationOptions() {
    $scope.educationLevelOptions = $scope.educationLevelOptions.filter(function(educationLevel) {
      if ($scope.hiddenCriteria.curriculum.origStudyLevel !== undefined && 
        $scope.hiddenCriteria.curriculum.origStudyLevel !== null &&
        $scope.hiddenCriteria.curriculum.origStudyLevel.endsWith(educationLevel.value2)) {
        return true;
      }
      return false;
    });
  }

  function getEducationLevelCriteria() {
    var params = {};
    if ($scope.auth.higher && $scope.auth.vocational) {
      return params;
    }
    if ($scope.auth.higher) {
      params.higher = true;
    }
    if ($scope.auth.vocational) {
      params.higher = false;
    }
    return params;
  }

  /** Load departments when curriculum changes */
  $scope.$watch('hiddenCriteria.curriculum', function() {
    // Check if I can remove curriculum version from field
    if ($scope.hiddenCriteria.curriculumVersion &&
       ($scope.hiddenCriteria.curriculum || {}).id !== $scope.hiddenCriteria.curriculumVersion.curriculum) {
      $scope.hiddenCriteria.curriculumVersion = undefined;
    }
    if (angular.isObject($scope.hiddenCriteria.curriculum) && $scope.hiddenCriteria.curriculum.id !== undefined) {
      $scope.criteria.curriculum = $scope.hiddenCriteria.curriculum.id;
      QueryUtils.endpoint('/autocomplete/curriculumdepartments').query({id: $scope.criteria.curriculum})
        .$promise.then(function (result) {
          $scope.formState.departments = result;
          if ($scope.criteria.department !== null && !isCurriculumDepartment($scope.criteria.department, result)) {
            $scope.criteria.department = null;
          }
      });
      filterEducationOptions();
    } else {
      $scope.criteria.curriculum = undefined;
      $scope.formState.departments = QueryUtils.endpoint('/autocomplete/curriculumdepartments').query();
      loadEducationLevelOptions();
    }
  });

  function isCurriculumDepartment(selectedDepartment, curriculumDepartments) {
    var departmentIds = (curriculumDepartments || []).map(function (department) { return department.id; });
    return departmentIds.indexOf(selectedDepartment) !== -1;
  }

  $scope.$watch('hiddenCriteria.curriculumVersion', function (newV, oldV) {
    if (newV === oldV) {
      return;
    }
    $scope.criteria.curriculumVersion = ($scope.hiddenCriteria.curriculumVersion || {}).id;
  });

  var unbindStudyYearWatch = $scope.$watch('criteria.studyYear', function(value) {
    if (angular.isNumber(value)) {
      unbindStudyYearWatch();
      $q.all(clMapper.promises).then($scope.loadData);
    }
  });

}
]).controller('ReportStudentStatisticsByperiodController', ['$scope', '$route', 'Classifier', 'QueryUtils',
  function ($scope, $route, Classifier, QueryUtils) {
    $scope.auth = $route.current.locals.auth;
    var classifierMapping = {OPPURSTAATUS_A: 'AKADPUHKUS_POHJUS', OPPURSTAATUS_K: 'EKSMAT_POHJUS'};
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatisticsbyperiod.xls'};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics/byperiod', {
      order: $scope.currentLanguage() === 'en' ? 'c.nameEn' : 'c.nameEt',
      result: 'OPPURSTAATUS_A',
      from: new Date().withoutTime()
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

    $scope.studyYearChanged = function () {
      $scope.criteria.studyPeriod = null;
    };

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
]).controller('StudentGroupTeacherController', ['$httpParamSerializer', '$q', '$route', '$scope', '$sessionStorage', '$timeout', '$window', 'GRADING_SCHEMA_TYPE', 'Classifier', 'DataUtils', 'GradingSchema', 'VocationalGradeUtil', 'QueryUtils', 'config', 'dialogService', 'message',
function ($httpParamSerializer, $q, $route, $scope, $sessionStorage, $timeout, $window, GRADING_SCHEMA_TYPE, Classifier, DataUtils, GradingSchema, VocationalGradeUtil, QueryUtils, config, dialogService, message) {
  $scope.gradeUtil = VocationalGradeUtil;
  $scope.auth = $route.current.locals.auth;

  var baseUrl = '/reports/studentgroupteacher';
  var resultsMapper = Classifier.valuemapper({entryType: 'SISSEKANNE'});
  var absencesMapper = Classifier.valuemapper({absence: 'PUUDUMINE', entryType: 'SISSEKANNE'});
  var gradeMapper;

  var gradingSchema = new GradingSchema(GRADING_SCHEMA_TYPE.VOCATIONAL);
  $q.all(gradingSchema.promises).then(function () {
    $scope.existsSchoolGradingSchema = gradingSchema.existsSchoolGradingSchema();
    gradeMapper = gradingSchema.gradeMapper(gradingSchema.gradeSelection(), ['grade']);
  });

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

  $scope.allModulesChanged = function () {
    if ($scope.criteria.allModules) {
      $scope.criteria.studyYear = null;
      $scope.criteria.onlyModuleGrades = true;
      $scope.onlyModuleGradesChanged();
    }
  };

  $scope.allModulesAndOutcomesChanged = function () {
    if ($scope.criteria.allModulesAndOutcomes) {
      $scope.criteria.allModules = true;
      $scope.allModulesChanged();
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

    if (!$scope.criteria.allModules) {
      if (!$scope.criteria.studyYear && !$scope.criteria.from) {
        message.error('report.studentGroupTeacher.error.studyYearOrEntriesFromRequired');
        return;
      }
    }

    $q.all(resultsMapper.promises.concat(gradingSchema.promises)).then(function () {
      search();
    });
  };

  function search() {
    QueryUtils.loadingWheel($scope, true);
    QueryUtils.endpoint(baseUrl).get($scope.criteria).$promise.then(function (result) {
      $scope.record = result;
      $scope.record.students.forEach(function (student) {
        student.resultColumns.forEach(function (column) {
          if (column.journalResult) {
            if (column.journalResult.results) {
              resultsMapper.objectmapper(column.journalResult.results);
              gradeMapper.objectmapper(column.journalResult.results);
            }
            if (column.journalResult.absences) {
              absencesMapper.objectmapper(column.journalResult.absences);
            }
          }
          if (column.practiceModuleThemeResult) {
            gradeMapper.objectmapper(column.practiceModuleThemeResult);
          }
          if (column.practiceModuleResult) {
            gradeMapper.objectmapper(column.practiceModuleResult);
          }
          if (column.outcomeResult) {
            gradeMapper.objectmapper(column.outcomeResult);
          }
          if (column.moduleResult) {
            gradeMapper.objectmapper(column.moduleResult);
          }
        });
      });
      $scope.tableTotalColumnsColspan = 7 + ($scope.record.showAverageGrade ? 1 : 0) +
        ($scope.record.showWeightedAverageGrade ? 1 : 0);

      QueryUtils.loadingWheel($scope, false);
      $scope.criteria.formState = {studyPeriod: $scope.formState.studyPeriod, studentGroup: $scope.formState.studentGroup};
      $scope.formState.showAllParameters = false;
      $scope.toStorage(baseUrl, $scope.criteria);
      $scope.$broadcast('refreshFixedColumns');
    });
  }

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
    dialogService.showDialog('report/templates/studentgroup.teacher.remark.dialog.html', function (dialogScope) {
      dialogScope.auth = $scope.auth;
      dialogScope.student = student;
    });
  };
}
]).controller("ScholarshipStatisticsController", ["$scope", 'ScholarshipUtils', function ($scope, ScholarshipUtils) {
  $scope.criteria = {};
  $scope.url = "reports/scholarships/statistics.xlsx";
  $scope.types = ScholarshipUtils.getScholarshipSchoolTypes();
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

}]).controller("ReportStudentLunchSupportController", ['$route', '$scope', 'DataUtils', 'FormUtils', 'QueryUtils', 'Classifier', '$q', 'message',
  function ($route, $scope, DataUtils, FormUtils, QueryUtils, Classifier, $q, message) {
  $scope.auth = $route.current.locals.auth;
  $scope.criteria = {};
  var clMapper = Classifier.valuemapper({status: 'OPPURSTAATUS'});
  var baseUrl = '/reports/students/lunchSupport';
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'lastname, firstname'});

  $scope.afterLoadData = function(result) {
    $scope.tabledata.content = result.content;
    $scope.tabledata.totalElements = result.totalElements;
    clMapper.objectmapper(result.content);
  };
  
  var loadData = $scope.loadData;
  $scope.loadData = function() {
    FormUtils.withValidForm($scope.searchForm, loadData);
  };

  $scope.updateSchoolMeal = function(row) {
    QueryUtils.loadingWheel($scope, true);
    var SchoolMealEndpoint = QueryUtils.endpoint('/reports/students/lunchSupport/' + row.studentId);
    var rowCopy = angular.copy(row);
    rowCopy.status = row.status.code;
    var record = new SchoolMealEndpoint(rowCopy);
    record.$update().then(function () {
      QueryUtils.loadingWheel($scope, false);
      message.info('main.messages.create.success');
      $scope.loadData();
    }).catch(angular.noop);
  };

  $q.all(clMapper.promises).then(function() {
    $scope.loadData();
  });

  $scope.directiveControllers = [];
  var clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function () {
    clearCriteria();
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };
}]);
