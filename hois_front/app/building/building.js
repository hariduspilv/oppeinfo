'use strict';

angular.module('hitsaOis').controller('BuildingEditController', ['dialogService', 'message', '$location', '$route', '$scope', 'QueryUtils',
  function (dialogService, message, $location, $route, $scope, QueryUtils) {
    var Endpoint = QueryUtils.endpoint('/buildings');
    var id = $route.current.params.id;
    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      // new building
      $scope.record = new Endpoint({});
    }

    $scope.update = function() {
      $scope.buildingForm.$setSubmitted();
      if(!$scope.buildingForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
      var afterSave = function() {
        message.info(msg);
      };
      if($scope.record.id) {
        $scope.record.$update().then(afterSave);
      } else {
        $scope.record.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'building.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/rooms/search');
        });
      });
    };
  }
]);
