'use strict';

angular.module('hitsaOis').controller('BuildingEditController', ['dialogService', 'message', '$location', '$route', '$scope', 'QueryUtils',
  function (dialogService, message, $location, $route, $scope, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/buildings';
    var Endpoint = QueryUtils.endpoint(baseUrl);
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
      var afterSave = function() {
        message.info(id ? 'main.messages.update.success' : 'main.messages.create.success');
        if(!id) {
          $location.path(baseUrl + '/' + $scope.record.id + '/edit');
        }
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
