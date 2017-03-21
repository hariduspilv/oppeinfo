'use strict';

angular.module('hitsaOis').controller('UsersSearchController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/users'/*, {order: 'person.lastname,person.firstname'}*/);

  $scope.loadData();
}])
  .controller('UsersEditController', ['$route', '$scope', 'QueryUtils', function ($route, $scope, QueryUtils) {
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/users');

    function afterLoad() {

    }

    if (id) {
      $scope.person = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.person = new Endpoint();
    }
  }]);
