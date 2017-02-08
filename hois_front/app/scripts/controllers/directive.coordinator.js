'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/directives/coordinators', {
      templateUrl: 'views/directive.coordinator.search.html',
      controller: 'DirectiveCoordinatorSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/coordinators/new', {
      templateUrl: 'views/directive.coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/directives/coordinators/:id', {
      templateUrl: 'views/directive.coordinator.edit.html',
      controller: 'DirectiveCoordinatorEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('DirectiveCoordinatorSearchController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/directives/coordinators', {order: 'name'});
  $scope.loadData();
}]).controller('DirectiveCoordinatorEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'QueryUtils',

  function ($location, $route, $scope, dialogService, message, QueryUtils) {
    var id = $route.current.params.id;

    var baseUrl = '/directives/coordinators';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.directiveCoordinator = Endpoint.get({id: id});
    } else {
      // new coordinator
      $scope.directiveCoordinator = new Endpoint();
    }

    $scope.update = function() {
      $scope.directiveCoordinatorForm.$setSubmitted();
      if(!$scope.directiveCoordinatorForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.directiveCoordinator.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
      }
      if($scope.directiveCoordinator.id) {
        $scope.directiveCoordinator.$update().then(afterSave);
      }else{
        $scope.directiveCoordinator.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'directive.coordinator.deleteconfirm'}, function() {
        $scope.directiveCoordinator.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };
  }
]);
