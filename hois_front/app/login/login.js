'use strict';

angular.module('hitsaOis')
  .controller('LoginController', function (message, $rootScope, $scope, $http, AuthService, AUTH_EVENTS, $location, config, $mdDialog) {

    var authenticate = function(credentials) {
      var headers = credentials ? {authorization : "Basic " +
        btoa(credentials.username + ":" + "undefined")
        } : {};
      AuthService.login(headers).then(function (auth) {
        if (auth) {
          $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
          $rootScope.setCurrentUser(auth);
          setLoggedInVisuals(auth);
          $scope.error = false;
          $mdDialog.hide();
        } else {
          $rootScope.setCurrentUser(null);
          setLoggedInVisuals({});
        }
      }, function () {
        console.log('udonth happen right?');
        $scope.error = true;
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      });
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
    $rootScope.$on(AUTH_EVENTS.reAuthenticate, authenticate);

    $scope.login = function () {
      authenticate($scope.credentials);
      if (AuthService.isAuthenticated()) {
        $location.path("/");
      }
    };

    $scope.logout = function() {
      AuthService.logout().finally(function() {
        loggedOut();
        $location.path("/");
      });
    };

    $scope.changeUser = function () {
      AuthService.changeUser($scope.userWork).then(function (auth) {
        if (auth) {
          setLoggedInVisuals(auth);
          $rootScope.setCurrentUser(auth);
          $location.path("/");
          $rootScope.$broadcast(AUTH_EVENTS.userChanged);
        } else {
          //console.log('login:changeUser:fail');
          loggedOut();
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

    var setLoggedInVisuals = function (auth) {
      if (auth.school) {
        $rootScope.logo = config.apiUrl + '/school/' + auth.school.id + '/logo';
      } else {
        $rootScope.logo = '';
      }
      $rootScope.userWork = auth.user;
    };

    var loggedOut = function () {
      $rootScope.currentUser = null;
    };
  });
