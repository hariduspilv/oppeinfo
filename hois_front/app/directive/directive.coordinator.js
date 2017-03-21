'use strict';

angular.module('hitsaOis').controller('DirectiveCoordinatorSearchController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/directives/coordinators', {order: 'name'});
  $scope.loadData();
}]).controller('DirectiveCoordinatorEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',

  function ($location, $route, $scope, dialogService, message, QueryUtils) {
    var id = $route.current.params.id;

    var baseUrl = '/directives/coordinators';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      // new coordinator
      $scope.record = new Endpoint();
    }

    $scope.update = function() {
      $scope.directiveCoordinatorForm.$setSubmitted();
      if(!$scope.directiveCoordinatorForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess);
      }else{
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'directive.coordinator.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
  }
]);
