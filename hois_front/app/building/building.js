'use strict';

angular.module('hitsaOis').controller('BuildingEditController', ['$route', '$scope', 'FormUtils', 'QueryUtils',
  function ($route, $scope, FormUtils, QueryUtils) {
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
      FormUtils.saveRecord($scope.buildingForm, $scope.record, baseUrl);
      $scope.buildingForm.$setPristine();
    };

    $scope.delete = function() {
      FormUtils.deleteRecord($scope.record, '/rooms/search?_noback', {prompt: 'building.deleteconfirm'});
    };
  }
]);
