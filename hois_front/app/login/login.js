'use strict';

angular.module('hitsaOis')
  .controller('LoginController', function (message, $rootScope, $scope, AuthService, AUTH_EVENTS, $location, config, $mdDialog) {

    function setLoggedInVisuals(authenticatedUser) {
      if (angular.isObject(authenticatedUser) && angular.isObject(authenticatedUser.school)) {
        $rootScope.state.logo = config.apiUrl + '/school/' + authenticatedUser.school.id + '/logo';
      } else {
        $rootScope.state.logo = '';
      }
      if (!angular.isObject(authenticatedUser) || !angular.isNumber(authenticatedUser.user)) {
        $rootScope.state.userWorkplace = null;
      } else {
        $rootScope.state.userWorkplace = authenticatedUser.user;
      }
    }

    $rootScope.loggedOut = function() {
      $rootScope.setCurrentUser(null);
      setLoggedInVisuals(null);
    };

    function successfulAuthentication(authenticatedUser) {
      if (authenticatedUser) {
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        $rootScope.setCurrentUser(authenticatedUser);
        setLoggedInVisuals(authenticatedUser);
        $scope.error = false;
        $mdDialog.hide();
      } else {
        $rootScope.setCurrentUser(null);
        setLoggedInVisuals(null);
        $location.path("/");
      }
    }

    function failedAuthentication() {
      $scope.error = true;
      $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      $location.path("/");
    }

    var authenticate = function(credentials) {
      var headers = credentials ? {authorization : "Basic " +
        btoa(credentials.username + ":" + "undefined")
        } : {};
      AuthService.login(headers).then(successfulAuthentication, failedAuthentication);
    };

    var authenticateIdCard = function() {
      AuthService.loginIdCard().then(successfulAuthentication, failedAuthentication);
    };

    var showAlert = function () {
      authenticate();
      if (!AuthService.isAuthorized) {
        message.error('no-auth');
      }
    };

    authenticate();
    $scope.credentials = {};

    $rootScope.$on(AUTH_EVENTS.notAuthenticated, showAlert);
    $rootScope.$on(AUTH_EVENTS.notAuthorized, showAlert);
    $rootScope.$on(AUTH_EVENTS.reAuthenticate, function () { authenticate(); });

    $scope.login = function () {
      authenticate($scope.credentials);
    };

    $scope.idlogin = function () {
      authenticateIdCard();
    };

    $scope.logout = function() {
      AuthService.logout().finally(function() {
        $rootScope.loggedOut();
        $location.path("/");
      });
    };

    $scope.changeUser = function () {
      AuthService.changeUser($rootScope.state.userWorkplace).then(function (authenticatedUser) {
        if (angular.isObject(authenticatedUser)) {
          setLoggedInVisuals(authenticatedUser);
          $rootScope.setCurrentUser(authenticatedUser);
          $location.path("/");
          $rootScope.$broadcast(AUTH_EVENTS.userChanged);
        } else {
          $rootScope.loggedOut();
          $location.path("/");
        }
      });
    };

    $scope.showLogin = function () {
      $scope.error = false;
      $mdDialog.show({
        controller: function () { this.parent = $scope; },
        controllerAs: 'ctrl',
        templateUrl: 'login/login.dialog.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
    };
  });
