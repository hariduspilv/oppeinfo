'use strict';

angular.module('hitsaOis').controller('StudentGroupSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils',
  function ($q, $scope, Classifier, QueryUtils) {
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM'});
    QueryUtils.createQueryForm($scope, '/studentgroups', {order: 'code'}, clMapper.objectmapper);

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('StudentGroupEditController', ['$location', '$q', '$route', '$scope', 'dialogService', 'message', 'Classifier', 'QueryUtils',
  function ($location, $q, $route, $scope, dialogService, message, Classifier, QueryUtils) {
    var id = $route.current.params.id;

    var baseUrl = '/studentgroups';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      // new student group
      $scope.record = new Endpoint();
    }

    $scope.update = function() {
      $scope.studentGroupForm.$setSubmitted();
      if(!$scope.studentGroupForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
      }
      if($scope.record.id) {
        $scope.record.$update().then(afterSave);
      }else{
        $scope.record.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'studentGroup.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
  }
]);
