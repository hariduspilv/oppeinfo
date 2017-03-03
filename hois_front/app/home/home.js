'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis')
  .controller('HomeController', function ($scope, School) {
    $scope.schools = School.getAll();

  }).controller('AuthenticatedHomeController', function ($scope, AUTH_EVENTS, AuthService, QueryUtils) {

    $scope.criteria = {size: 5, page: 1};
    $scope.generalmessages = {};
    $scope.loadGeneralMessages = function() {
      var query = QueryUtils.getQueryParams($scope.criteria);
      $scope.generalmessages.$promise = QueryUtils.endpoint('/generalmessages/show').search(query, function(result) {
        $scope.generalmessages.content = result.content;
        $scope.generalmessages.totalElements = result.totalElements;
      });
    };

    if (AuthService.isAuthenticated()) {
      $scope.loadGeneralMessages();
    }

    $scope.$on(AUTH_EVENTS.loginSuccess, $scope.loadGeneralMessages);
    $scope.$on(AUTH_EVENTS.userChanged, $scope.loadGeneralMessages);
  });
