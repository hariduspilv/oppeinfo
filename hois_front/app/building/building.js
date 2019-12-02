'use strict';

angular.module('hitsaOis').controller('BuildingEditController', ['$location', '$route', '$scope', 'FormUtils', 'QueryUtils', 'message',
  function ($location, $route, $scope, FormUtils, QueryUtils, message) {
    var id = $route.current.params.id;
    var baseUrl = '/buildings';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      // new building
      $scope.record = new Endpoint({});
    }

    $scope.buildingUniqueQuery = {url: baseUrl + '/unique',  id: id};

    $scope.update = function() {
      FormUtils.withValidForm($scope.buildingForm, function () {
        if ($scope.record.id) {
          $scope.record.$update().then(message.updateSuccess).catch(angular.noop);
          $scope.buildingForm.$setPristine();
        } else {
          $scope.record.$save().then(function () {
            message.info('main.messages.create.success');
            $location.url(baseUrl + '/' + $scope.record.id + '/edit?_noback');
          }).catch(angular.noop);
        }
      });
    };

    $scope.delete = function() {
      FormUtils.deleteRecord($scope.record, '/rooms/search?_noback', {prompt: 'building.deleteconfirm'});
    };
  }
]);
