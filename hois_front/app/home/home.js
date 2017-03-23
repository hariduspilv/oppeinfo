'use strict';
// todo this to main controller? and use this data on auth?
angular.module('hitsaOis').controller('HomeController', ['$scope', 'School',
  function ($scope, School) {
    $scope.schools = School.getAll();

  }
]).controller('AuthenticatedHomeController', ['$scope', 'AUTH_EVENTS', 'AuthService', 'QueryUtils', '$resource', 'config', 
  function ($scope, AUTH_EVENTS, AuthService, QueryUtils, $resource, config) {

    $scope.criteria = {size: 5, page: 1, order: 'inserted, title, id'};
    $scope.generalmessages = {};
    $scope.loadGeneralMessages = function() {
      var query = QueryUtils.getQueryParams($scope.criteria);
      $scope.generalmessages.$promise = QueryUtils.endpoint('/generalmessages/show').search(query, function(result) {
        $scope.generalmessages.content = result.content;
        $scope.generalmessages.totalElements = result.totalElements;
      });
    };
    $scope.unreadMessageCriteria = {size: 5, page: 1, order: 'inserted'};
    $scope.unreadMessages = {};
    $scope.loadUnreadMessages = function() {
      var query = QueryUtils.getQueryParams($scope.unreadMessageCriteria);
      $scope.unreadMessages.$promise = QueryUtils.endpoint('/message/received/mainPage').search(query, function(result) {
        $scope.unreadMessages.content = result.content;
        $scope.unreadMessages.totalElements = result.totalElements;
      });
    };

    $scope.readMessage = function(message) {
        message.clicked = !message.clicked;
        if(!message.isRead) {
            $resource(config.apiUrl + '/message/' + message.id).update(null).$promise.then(function(){
                message.isRead = true;
            });
        }
    };

    if (AuthService.isAuthenticated()) {
      $scope.loadGeneralMessages();
      $scope.loadUnreadMessages();
    }

    $scope.$on(AUTH_EVENTS.loginSuccess, $scope.loadGeneralMessages, $scope.loadUnreadMessages);
    $scope.$on(AUTH_EVENTS.userChanged, $scope.loadGeneralMessages, $scope.loadUnreadMessages);
  }
]);
