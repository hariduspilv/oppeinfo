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

      if($scope.record.id) {
        $scope.record.$update().then(message.updateSuccess).catch(angular.noop);
      } else {
        $scope.record.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.record.id + '/edit');
        }).catch(angular.noop);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'building.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/rooms/search');
        }).catch(angular.noop);
      });
    };
  }
]);
