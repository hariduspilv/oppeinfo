'use strict';

angular.module('hitsaOis').controller('HigherProtocolSearchController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, $q, dialogService, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  var currentStudyPeriod = null;
  var baseUrl = "/higherProtocols";

  var clMapper = Classifier.valuemapper({status: 'PROTOKOLL_STAATUS', protocolType: 'PROTOKOLLI_LIIK'});
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'}, clMapper.objectmapper);
  $q.all(clMapper.promises);
  DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

  var Endpoint = QueryUtils.endpoint(baseUrl);

  function selectCurrentStudyPeriod() {
    if(currentStudyPeriod) {
        $scope.criteria.studyPeriod = currentStudyPeriod;
    } else {
      QueryUtils.endpoint('/school/studyPeriod/current').get().$promise.then(function(response){
        currentStudyPeriod = response.currentStudyPeriod;
        $scope.criteria.studyPeriod = currentStudyPeriod;
        $scope.loadData();
      });
    }
  }

  $scope.$watch("criteria.studyPeriod", function(value) {
    if(!$scope.criteria.studyPeriod) {
      selectCurrentStudyPeriod();
    }
  });

  $scope.openCreateProtocolDialog = function() {
    var DialogController = function (scope) {
      scope.record = {
        protocolType: 'PROTOKOLLI_LIIK_P',
        students: []
      }

      QueryUtils.endpoint(baseUrl + "/subjectStudyPeriods").query().$promise.then(function(response){
        scope.subjectStudyPeriods = response;
      });

      scope.$watch('record.subjectStudyPeriod', getStudents);
      scope.$watch('record.protocolType', getStudents);

      function getStudents() {
        if(scope.record.protocolType && scope.record.subjectStudyPeriod) {
          QueryUtils.endpoint(baseUrl + "/students").query(scope.record).$promise.then(function(response){
            console.log(response);
            scope.students = response;
          });
        }
      }
    };

    dialogService.showDialog('higherProtocol/higher.protocol.create.dialog.html', DialogController,
      function (submitScope) {
        if(ArrayUtils.isEmpty(submitScope.record.students)) {
          return;
        }
        new Endpoint(submitScope.record).$save().then(function(response){
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/'+ response.id +'/edit');
        });
      });
  };


});
