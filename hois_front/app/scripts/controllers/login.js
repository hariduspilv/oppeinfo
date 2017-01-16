'use strict';

angular.module('hitsaOis')
  .controller('LoginController', function ($rootScope, $scope, $http, Menu, $location, config, $mdDialog) {

    var authenticate = function(credentials, callback) {

      var headers = credentials ? {authorization : "Basic " +
        btoa(credentials.username + ":" + "undefined"/*"benspassword"credentials.password*/)
      } : {};

      $http.get(config.apiUrl + '/user', {headers : headers}).then(function(response) {
        if (response.data.name) {
          loggedIn(response.data);
        } else {
          loggedOut();
        }
        if (callback) {
          callback();
        }
      }, function() {
        loggedOut();
        if (callback) {
          callback();
        }
      });

    };

    authenticate();
    $scope.credentials = {};

    $scope.login = function() {
      authenticate($scope.credentials, function() {
        if ($rootScope.authenticated) {
          $location.path("/");
          $scope.error = false;
          $mdDialog.hide();
        } else {
          $location.path("/login");
          $scope.error = true;
        }
      });
    };

    $scope.logout = function() {
      $http.post(config.apiUrl + '/logout', {}).finally(function() {
        loggedOut();
        $location.path("/");
      });
    };

    $scope.showLogin = function () {
      $mdDialog.show({
        //controller: DialogController,
        templateUrl: 'views/templates/login.dialog.html',
        parent: angular.element(document.body),
        //targetEvent: ev,
        clickOutsideToClose:true
        //fullscreen: $scope.customFullscreen
      });
    };

    $scope.changeUser = function () {
      $http.post(config.apiUrl + '/changeUser', {id:$scope.userWork}).then(
        function (response) {
          //console.log('login:changeUser:success');
          loggedIn(response.data); // selected value messes up
          $location.path("/");
        }, function () {
          //console.log('login:changeUser:fail');
          loggedOut();
          $location.path("/");
        }
      );
    };

    var loggedIn = function (auth) {
      $rootScope.authenticated = true;
      $rootScope.auth = auth;
      if ($rootScope.auth.school) {
        $rootScope.auth.school.img = config.apiUrl + '/school/' + $rootScope.auth.school.id + '/logo';
      }
      $rootScope.userWork = auth.user;
      Menu.setMenu(auth);
    };

    var loggedOut = function () {
      $rootScope.authenticated = false;
      $rootScope.auth = null;
      Menu.setMenu({});
    };
  });
