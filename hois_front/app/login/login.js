'use strict';

angular.module('hitsaOis')
  .controller('LoginController', function (message, $rootScope, $scope, AuthService, AUTH_EVENTS, $location, config, $mdDialog, $timeout, ArrayUtils, PUBLIC_ROUTES, $route, QueryUtils) {

    var NOT_MOBILE_ID_USER_ERROR = 301;

    function setLoggedInVisuals(authenticatedUser) {
      if (angular.isObject(authenticatedUser) && authenticatedUser.roleCode !== 'ROLL_V') {
        QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
          $rootScope.unreadMessages = result.unread;
        });
      }

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
      if ($scope.hideDialog) {
        $mdDialog.hide();
      }
      if (authenticatedUser) {
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        $rootScope.setCurrentUser(authenticatedUser);
        setLoggedInVisuals(authenticatedUser);
      } else {
        $rootScope.setCurrentUser(null);
        setLoggedInVisuals(null);
        //Do not redirect if public route
        if (!$route.current || !ArrayUtils.contains(PUBLIC_ROUTES, $route.current.originalPath)) {
          $location.path("/");
        }
      }
    }

    function failedAuthentication() {
      $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      $location.path("/");
      message.error('main.login.error');
    }

    function successfulMobileId(response) {
      $scope.jwt = response.jwt;
      if (response.data.errorCode === NOT_MOBILE_ID_USER_ERROR) {
        failedAuthentication();
        $scope.showMessage('main.login.mobileid.notMobileIdUser');
      } else if (response.data.errorCode) {
        failedAuthentication();
      } else if (response.data.challengeID) {
        $scope.showMobileId(response.data.challengeID);
        $scope.mobileIdPolls = 0;
        $timeout(pollMobileIdStatus, config.mobileIdInitialDelay);
      } else {
        failedAuthentication();
      }
    }

    function successfulMobileIdStatus(response) {
      if (response.status === 'USER_AUTHENTICATED') {
        $scope.hideDialog = false;
        $mdDialog.hide();
        AuthService.login().then(successfulAuthentication, failedAuthentication);
      } else if (response.status === 'OUTSTANDING_TRANSACTION') {
        $scope.mobileIdPolls++;
        if ($scope.mobileIdPolls < config.mobileIdMaxPolls) {
          $timeout(pollMobileIdStatus, config.mobileIdPollInterval);
        }
      } else {
        console.log('mobileIdStatus: '+response.status);
        $mdDialog.hide();
        failedAuthentication();
      }
    }

    function pollMobileIdStatus() {
      AuthService.pollMobileIdStatus($scope.jwt).then(successfulMobileIdStatus, failedAuthentication);
    }
    
    var authenticate = function() {
      $scope.hideDialog = false;
      var token = $location.search()._code;
      if (token) {
        AuthService.login({Authorization: 'Bearer ' + token}).then(successfulAuthentication, failedAuthentication);
      } else {
        AuthService.login().then(successfulAuthentication, failedAuthentication);
      }
    };

    var authenticateUser = function(credentials) {
      $scope.hideDialog = false;
      AuthService.loginLdap(credentials).then(successfulAuthentication, failedAuthentication);
    };

    var authenticateMobileId = function(mobilenumber) {
      AuthService.loginMobileId(mobilenumber).then(successfulMobileId, failedAuthentication);
    };

    authenticate();

    $rootScope.$on(AUTH_EVENTS.reAuthenticate, function () { authenticate(); });

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
      $mdDialog.show({
        controller: function ($scope, School, $localStorage, config) {
          School.getLdap().$promise.then(function (schools) {
            $scope.schools = schools;
            $scope.schools.sort(function (a, b) {
              return $rootScope.currentLanguageNameField(a).localeCompare($rootScope.currentLanguageNameField(b));
            });
          });
          $scope.credentials = {};
          if ($localStorage.ldapschool) {
            $scope.credentials.school = $localStorage.ldapschool;
          }
          $scope.mobilenumber = '372';
          $scope.cancel = function() {
            $mdDialog.hide();
          };
          $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;
          $scope.login = function () {
            $scope.userLoginForm.$setSubmitted();
            if ($scope.userLoginForm.$valid && $scope.credentials.school && 
              $scope.credentials.username && $scope.credentials.password) {
              $localStorage.ldapschool = $scope.credentials.school;
              authenticateUser($scope.credentials);
            }
          };
          $scope.idCardLoginUrl = config.idCardLoginUrl;
          $scope.mIdLogin = function () {
            if ($scope.mobilenumber) {
              authenticateMobileId($scope.mobilenumber);
            }
          };
        },
        templateUrl: 'login/login.dialog.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
    };

    $scope.showMobileId = function (challengeID) {
      $mdDialog.show({
        controller: function ($scope) {
          $scope.challengeID = challengeID;
        },
        templateUrl: 'login/m-id.login.dialog.html',
        parent: angular.element(document.body),
        clickOutsideToClose: false
      });
    };

    $scope.showMessage = function (message) {
      $mdDialog.show({
        controller: function ($scope) {
          $scope.message = message;
        },
        templateUrl: 'login/message.login.dialog.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
    };
  });
