'use strict';

angular.module('hitsaOis').controller('UsersSearchController', ['$scope', 'Classifier', 'QueryUtils', function ($scope, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({role: 'ROLL'});
  QueryUtils.createQueryForm($scope, '/users', {order: 'p.lastname,p.firstname'}, clMapper.objectmapper);

  $scope.loadData();
}])
  .controller('PersonsEditController', ['$location', '$route', '$scope', 'Classifier', 'DataUtils', 'message', 'QueryUtils', function ($location, $route, $scope, Classifier, DataUtils, message, QueryUtils) {
    var id = $route.current.params.id;
    var Endpoint = QueryUtils.endpoint('/persons');
    var clMapper = Classifier.valuemapper({role: "ROLL"});

    function afterLoad() {

      $scope.users = $scope.person.users;
      $scope.person.users = null;
      clMapper.objectmapper($scope.users);
      $scope.users.forEach(function (it) {
        DataUtils.convertStringToDates(it, ['validThru', 'validFrom']);
      });
      var now = new moment;
      for (var i = 0; i < $scope.users.length; i++) {
        var valid = true;
        var item = $scope.users[i];
        if (item.validThru !== null && moment(item.validThru).isBefore(now)) {
          valid = false;
        }
        if (item.validFrom !== null && moment(item.validFrom).isAfter(now)) {
          valid = false;
        }
        item.valid = valid;
      }
      console.log($scope.users);
    }

    if (id) {
      $scope.person = Endpoint.get({id: id}, afterLoad);
    } else {
      $scope.person = new Endpoint();
    }

    $scope.update = function () {
      $scope.personForm.$setSubmitted();
      if ($scope.personForm.$valid) {
        if ($scope.person.id) {
          $scope.person.$update().then(message.updateSuccess)
        } else {
          $scope.person.$save().then(function (response) {
            $location.path('/persons/' + response.id + '/edit');
            message.info('main.messages.create.success');
          })
        }
      }
    }
  }]);
