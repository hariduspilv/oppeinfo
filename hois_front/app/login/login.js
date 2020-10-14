'use strict';

angular.module('hitsaOis')
  .controller('LoginController', function ($location, $mdDialog, $rootScope, $route, $scope, message, AUTH_EVENTS, PUBLIC_ROUTES, ArrayUtils, AuthService, QueryUtils, config, resourceErrorHandler) {

    function setLoggedInVisuals(authenticatedUser) {
      if (angular.isObject(authenticatedUser) && ['ROLL_V', 'ROLL_X'].indexOf(authenticatedUser.roleCode) === -1) {
        QueryUtils.endpoint('/message/received/new').get().$promise.then(function (result) {
          $rootScope.unreadMessages = result.unread;
        });
      }

      if (angular.isObject(authenticatedUser) && angular.isObject(authenticatedUser.school) &&
          authenticatedUser.school.logo) {
        $rootScope.state.logo = 'data:image/JPEG;base64,' + authenticatedUser.school.logo;
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

    function checkToS(authenticatedUser) {
      if (authenticatedUser && authenticatedUser.mustAgreeWithToS) {
        $location.path("/");
        $mdDialog.show({
          controller: ['$scope', '$window', function (dialogScope, $window) {
            dialogScope.contract = QueryUtils.endpoint('/userContract').get();

            dialogScope.changeUser = $scope.changeUser;
            dialogScope.logout = $scope.logout;

            dialogScope.confirm = confirm;
            dialogScope.cancel = cancel;
            function confirm() {
              if (!dialogScope.agree) {
                message.error('userContract.haveToAgree');
                return;
              }
              QueryUtils.endpoint('/userContract').post2().$promise.then(function () {
                $mdDialog.hide();
                $location.path("/");
                $window.location.reload();
              });
            }

            function cancel() {
              $mdDialog.hide();
              dialogScope.logout();
            }
          }],
          templateUrl: 'userContract/userContract.dialog.html',
          parent: angular.element(document.body),
          clickOutsideToClose: false,
          escapeToClose : false
        });
      }
    }

    function successfulAuthentication(authenticatedUser) {
      if ($scope.hideDialog) {
        $mdDialog.hide();
      }
      if (authenticatedUser) {
        checkToS(authenticatedUser);
        $rootScope.setCurrentUser(authenticatedUser);
        setLoggedInVisuals(authenticatedUser);
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
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
      if (response.data.challengeID) {
        $scope.showMobileId(response.data.challengeID, response.jwt);
      } else {
        failedAuthentication();
      }
    }

    function successfulMobileIdAuthentication() {
      $scope.hideDialog = false;
      $mdDialog.hide();
      AuthService.login().then(successfulAuthentication, failedAuthentication);
    }

    function failedMobileIdAuthentication(response) {
      $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      $mdDialog.hide();
      resourceErrorHandler.responseError(response).catch(angular.noop);
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

    var authenticateMobileId = function(idcode, mobilenumber) {
      AuthService.loginMobileId(idcode, mobilenumber).then(successfulMobileId, failedAuthentication);
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
          checkToS(authenticatedUser);
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
          $scope.visibleIndex = 1;

          $scope.showIndex = function(index) {
            $scope.visibleIndex = index;
          };

          $scope.credentials = {};
          if ($localStorage.ldapschool) {
            $scope.credentials.school = $localStorage.ldapschool;
          }
          $scope.mobilenumber = '+372';
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
            if ($scope.idcode && $scope.mobilenumber) {
              authenticateMobileId($scope.idcode, $scope.mobilenumber);
            }
          };
          $scope.taraLoginUrl = config.apiUrl + '/taraLogin' + ($rootScope.currentLanguage() === 'en' ? '?lang=EN': '');
          $scope.haridLoginUrl = config.apiUrl + '/haridLogin';
        },
        templateUrl: 'login/login.dialog.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
    };

    $scope.showMobileId = function (challengeID, jwt) {
      $mdDialog.show({
        controller: function ($scope) {
          $scope.challengeID = challengeID;
          AuthService.mobileIdAuthenticate(jwt).then(successfulMobileIdAuthentication, failedMobileIdAuthentication);
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
