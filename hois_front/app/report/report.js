'use strict';

angular.module('hitsaOis').controller('ReportStudentController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
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
    QueryUtils.createQueryForm($scope, '/reports/students', {order: 'p.lastname,p.firstname'}, afterLoad);


    $scope.formState = {xlsUrl: 'reports/students/students.xls'};

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

    $scope.loadData();
  }
]).controller('ReportStudentStatisticsByperiodController', ['$scope', 'Classifier', 'QueryUtils', 'Session',
  function ($scope, Classifier, QueryUtils, Session) {
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

    $scope.loadData();
  }
]).controller('ReportCurriculumsCompletionController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', status: 'OPPURSTAATUS'});
    QueryUtils.createQueryForm($scope, '/reports/curriculums/completion', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('ReportTeacherLoadHigherController', ['$scope', 'DataUtils', 'QueryUtils',
  function ($scope, DataUtils, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/reports/teachers/load/higher', {order: 'p.lastname,p.firstname'});

    $scope.formState = {studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), studyPeriods: {}};
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
        $scope.loadData();
      }
    });
  }
]).controller('ReportTeacherLoadVocationalController', ['$scope', 'DataUtils', 'QueryUtils',
  function ($scope, DataUtils, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/reports/teachers/load/vocational', {order: 'p.lastname,p.firstname'});

    $scope.formState = {studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query(), studyPeriods: {}};
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
        $scope.loadData();
      }
    });
  }
]);
