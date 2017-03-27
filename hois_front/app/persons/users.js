'use strict';

angular.module('hitsaOis')
  .controller('UsersEditController', ['$scope', '$location', '$route', 'AuthService', 'DataUtils', 'message', 'QueryUtils', function ($scope, $location,  $route, AuthService, DataUtils, message, QueryUtils) {
    var id = $route.current.params.person;
    var code = $route.current.params.user;

    var Endpoint = QueryUtils.endpoint('/persons/'+id+'/users');

    function afterLoad() {
      DataUtils.convertStringToDates($scope.user, ['validFrom', 'validThru']);
    }

    function afterNewLoad() {
      $scope.user.validFrom = new Date();
    }

    $scope.showSchool = AuthService.matchesRole('ROLL_P');

    $scope.userRoleDefaults = QueryUtils.endpoint('/users/rolesDefaults').query();

    $scope.filterValues = [];

    if (!$scope.showSchool) {
      $scope.filterValues = ['ROLL_P'];
    }

    if (code) {
      $scope.user = Endpoint.get({id: code}, afterLoad);
    } else {
      $scope.user = new Endpoint.get(afterNewLoad);
    }

    $scope.roleChange = function () {
      if ($scope.user.role === 'ROLL_P') {
        $scope.user.school = null;
      }
      if (!$scope.user.id) {
        var rights = $scope.userRoleDefaults.filter(function (it) {
          return it.roleCode === $scope.user.role;
        });
        if (rights && rights.length) {
          $scope.user.rights = JSON.parse(rights[0].data);
        }
      }
    };

    $scope.update = function () {
      $scope.userForm.$setSubmitted();
      if ($scope.userForm.$valid) {
        if ($scope.user.id) {
          $scope.user.$update().then(message.updateSuccess);
        } else {
          $scope.user.$save().then(function (response) {
            $location.path('/persons/' + response.person.id + '/users/' + response.id);
            message.info('main.messages.create.success');
          });
        }
      }
    };
  }]);
