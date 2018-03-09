'use strict';

angular.module('hitsaOis').controller('ExamSearchController', ['$q', '$route', '$scope', '$timeout', 'message', 'Classifier', 'DataUtils', 'QueryUtils',
  function ($q, $route, $scope, $timeout, message, Classifier, DataUtils, QueryUtils) {

    $scope.auth = $route.current.locals.auth;
    var clMapper = Classifier.valuemapper({type: 'SOORITUS'});
    QueryUtils.createQueryForm($scope, '/exams', {order: 'start'}, clMapper.objectmapper);
    $scope.formState = {studyPeriods: QueryUtils.endpoint('/autocomplete/studyPeriods').query()};
    var promises = clMapper.promises;
    promises.push($scope.formState.studyPeriods.$promise);

    $q.all(promises).then(function() {
      if($scope.formState.studyPeriods.length > 0 && !$scope.criteria.studyPeriod) {
        var currentStudyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.formState.studyPeriods);
        $scope.criteria.studyPeriod = currentStudyPeriod ? currentStudyPeriod.id : undefined;
      }
      if($scope.criteria.studyPeriod) {
        $timeout($scope.loadData);
      } else if($scope.formState.studyPeriods.length === 0) {
        message.error('studyYear.studyPeriod.missing');
      }
    });
  }
]).controller('ExamStudentController', ['$q', '$scope', 'dialogService', 'Classifier', 'QueryUtils',
  function($q, $scope, dialogService, Classifier, QueryUtils) {

   var clMapper = Classifier.valuemapper({assessment: 'HINDAMISVIIS', type: 'SOORITUS'});
   QueryUtils.createQueryForm($scope, '/exams/forregistration', {order: 'start'});
   $scope.afterLoadData = function(result) {
     $scope.tabledata.content = result.content;
     $scope.tabledata.totalElements = result.totalElements;
     clMapper.objectmapper(result.content);
   };

   $q.all(clMapper.promises).then($scope.loadData);

   $scope.register = function() {
   };

   $scope.unregister = function() {
   };
  }
]);
