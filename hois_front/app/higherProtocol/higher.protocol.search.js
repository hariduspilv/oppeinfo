'use strict';

angular.module('hitsaOis').controller('HigherProtocolSearchController', function ($scope, $route, $location, QueryUtils, DataUtils, Classifier, message, $q, dialogService, ArrayUtils) {
  $scope.auth = $route.current.locals.auth;
  var baseUrl = "/higherProtocols";

  var clMapper = Classifier.valuemapper({status: 'PROTOKOLL_STAATUS', protocolType: 'PROTOKOLLI_LIIK'});
  QueryUtils.createQueryForm($scope, baseUrl, {order: 'id'}, clMapper.objectmapper);
  $q.all(clMapper.promises);
  DataUtils.convertStringToDates($scope.criteria, ['inserted', 'confirmDate']);

  var Endpoint = QueryUtils.endpoint(baseUrl);

  function setCurrentStudyPeriod() {
    if($scope.criteria && $scope.studyPeriods && !$scope.criteria.studyPeriod) {
        $scope.criteria.studyPeriod = DataUtils.getCurrentStudyYearOrPeriod($scope.studyPeriods).id;
    }
  }

  QueryUtils.endpoint('/autocomplete/studyPeriods').query().$promise.then(function(response){
      $scope.studyPeriods = response;
      setCurrentStudyPeriod();
      $scope.loadData();
  });

  $scope.$watch('criteria.studyPeriod', function() {
        setCurrentStudyPeriod();
      }
  );

  $scope.openCreateProtocolDialog = function() {
    var DialogController = function (scope) {
      scope.record = {
        protocolType: 'PROTOKOLLI_LIIK_P',
        students: []
      };

      QueryUtils.endpoint(baseUrl + "/subjectStudyPeriods").query().$promise.then(function(response){
        scope.subjectStudyPeriods = response;
      });

      function getStudents() {
        if(scope.record.protocolType && scope.record.subjectStudyPeriod) {
          QueryUtils.endpoint(baseUrl + "/students").query(scope.record).$promise.then(function(response){
            scope.record.students = [];
            scope.students = response;
          });
        }
      }

      scope.$watch('record.subjectStudyPeriod', getStudents);
      scope.$watch('record.protocolType', getStudents);

      scope.save = function() {
        scope.dialogForm.$setSubmitted();
        if(ArrayUtils.isEmpty(scope.record.students)) {
          message.error("higherProtocol.error.noStudents");
          return;
        }
        scope.submit(scope);
      };
    };

    dialogService.showDialog('higherProtocol/higher.protocol.create.dialog.html', DialogController,
      function (submitScope) {
        new Endpoint(submitScope.record).$save().then(function(response){
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/'+ response.id +'/edit');
        });
      });
  };


});
