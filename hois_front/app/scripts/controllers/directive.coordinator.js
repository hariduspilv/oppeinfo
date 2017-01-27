'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/directives/coordinators', {
      templateUrl: 'views/directive.coordinator.search.html',
      controller: 'DirectiveCoordinatorSearchController',
      controllerAs: 'controller'
    })
    .when('/directives/coordinators/new', {
        templateUrl: 'views/directive.coordinator.edit.html',
        controller: 'DirectiveCoordinatorEditController',
        controllerAs: 'controller'
    })
    .when('/directives/coordinators/:id', {
        templateUrl: 'views/directive.coordinator.edit.html',
        controller: 'DirectiveCoordinatorEditController',
        controllerAs: 'controller'
    });
}).controller('DirectiveCoordinatorSearchController', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/directives/coordinators');
  $scope.loadData();
}).controller('DirectiveCoordinatorEditController', function ($location, $route, $scope, message, QueryUtils) {
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
    if($scope.directiveCoordinator.id) {
      $scope.directiveCoordinator.$update().then(function() {
        message.info('main.messages.update.success');
        $location.path(baseUrl);
      });
    }else{
      $scope.directiveCoordinator.$save().then(function() {
        message.info('main.messages.create.success');
        $location.path(baseUrl);
      });
    }
  };

  $scope.delete = function() {
    $scope.directiveCoordinator.$delete().then(function() {
      message.info('main.messages.delete.success');
      $location.path(baseUrl);
    });
  };
});
