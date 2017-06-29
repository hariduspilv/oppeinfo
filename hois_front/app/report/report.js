'use strict';

angular.module('hitsaOis').controller('ReportStudentController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({fin: 'FINALLIKAS', finSpecific: 'FINTAPSUSTUS', language: 'OPPEKEEL',
      studyLevel: 'OPPEASTE', studyForm: 'OPPEVORM', studyLoad: 'OPPEKOORMUS', status: 'OPPURSTAATUS'});
    QueryUtils.createQueryForm($scope, '/reports/students', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

    $scope.formState = {xlsUrl: 'reports/students/students.xls'};

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('ReportStudentStatisticsController', ['$scope', 'Classifier', 'QueryUtils',
  function ($scope, Classifier, QueryUtils) {
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatistics.xls'};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics', {result: 'OPPEVORM'}, function() {
      var resultType = $scope.criteria.result;
      if($scope.formState.resultType !== resultType) {
        $scope.formState.resultType = resultType;
        $scope.formState.resultDef = Classifier.queryForDropdown({mainClassCode: resultType});
        if(resultType === 'FINALLIKAS') {
          Classifier.queryForDropdown({mainClassCode: 'FINTAPSUSTUS'}, function(result) {
            $scope.formState.resultDef.$promise.then(function() {
              Array.prototype.push.apply($scope.formState.resultDef, result);
            });
          });
        }
      }
    });

    $scope.loadData();
  }
]).controller('ReportStudentStatisticsByperiodController', ['$scope', 'Classifier', 'QueryUtils',
  function ($scope, Classifier, QueryUtils) {
    var classifierMapping = {OPPURSTAATUS_A: 'AKADPUHKUS_POHJUS', OPPURSTAATUS_K: 'EKSMAT_POHJUS'};
    $scope.formState = {xlsUrl: 'reports/students/statistics/studentstatisticsbyperiod.xls'};

    QueryUtils.createQueryForm($scope, '/reports/students/statistics/byperiod', {result: 'OPPURSTAATUS_A'}, function() {
      var resultType = $scope.criteria.result;
      if($scope.formState.resultType !== resultType) {
        $scope.formState.resultType = resultType;
        var mainClassCode = classifierMapping[resultType];
        $scope.formState.resultDef = mainClassCode ? Classifier.queryForDropdown({mainClassCode: mainClassCode}) : undefined;
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
]).controller('ReportTeacherLoadController', ['$scope', 'DataUtils', 'QueryUtils',
  function ($scope, DataUtils, QueryUtils) {
    QueryUtils.createQueryForm($scope, '/reports/teachers/load', {order: 'p.lastname,p.firstname'});

    $scope.formState = {studyYears: QueryUtils.endpoint('/autocomplete/studyYears').query()};
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
  }
]);
