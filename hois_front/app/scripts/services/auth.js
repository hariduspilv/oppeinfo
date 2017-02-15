'use strict';

angular.module('hitsaOis')
  .factory('AuthService', function ($http, Session, Menu, config) {
    var authService = {};

    var newUser = function (response) {
      Menu.setMenu(response.data);
      if (response.data && response.data.user) {
        Session.create(response.data.user, response.data.authorizedRoles, response.data.school);
        return response.data;
      } else {
        Session.destroy();
      }
      return null;
    };

    authService.login = function (headers) {
      return $http.get(config.apiUrl + '/user', {headers : headers})
        .then(function (res) {
          return newUser(res);
        });
    };

    authService.logout = function () {
      return $http.post(config.apiUrl + '/logout', {}).finally(function() {
        Session.destroy();
        Menu.setMenu({});
      });
    };

    authService.changeUser = function (userId) {
      return $http.post(config.apiUrl + '/changeUser', {id:userId})
        .then(function (res) {
          return newUser(res);
      });
    };

    authService.isAuthenticated = function () {
      return !!Session.userId;
    };

    authService.isAuthorized = function (authorizedRoles) {
      if (!angular.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }
      if (authService.isAuthenticated()) {
        for (var index = (authorizedRoles.length - 1); index > -1; index--) {
          if (Session.authorizedRoles.indexOf(authorizedRoles[index]) !== -1) {
            return true;
          }
        }
      }
      return false;
    };

    return authService;
  })
  .factory('AuthInterceptor', function ($rootScope, $q, AUTH_EVENTS) {
    return {
      responseError: function (response) {
        $rootScope.$broadcast({
          401: AUTH_EVENTS.notAuthenticated,
          403: AUTH_EVENTS.notAuthorized,
          419: AUTH_EVENTS.sessionTimeout,
          440: AUTH_EVENTS.sessionTimeout
        }[response.status], response);
        return $q.reject(response);
      }
    };
  })

  .factory('AuthResolver', function ($q, $rootScope) {
    return {
      resolve: function () {
        var deferred = $q.defer();
        var unwatch = $rootScope.$watch('currentUser', function (currentUser) {
          if (angular.isDefined(currentUser)) {
            if (currentUser) {
              deferred.resolve(currentUser);
            } else {
              deferred.reject();
            }
            unwatch();
          }
        });
        return deferred.promise;
      }
    };
  })
  .run(function ($rootScope, AUTH_EVENTS, AuthService) {
    // TODO: change the event to block site
    // $rootScope.$on('$routeChangeStart', function (event, next) {
    $rootScope.$on('$locationChangeStart', function (event, next) {
      if (angular.isDefined(next.data)) {
        var authorizedRoles = next.data.authorizedRoles;
        if (!AuthService.isAuthorized(authorizedRoles)) {
          event.preventDefault();
          if (AuthService.isAuthenticated()) {
            // user is not allowed
            $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
          } else {
            // user is not logged in
            $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
          }
        }
      }
    });
  })
  .constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized',
    userChanged: 'auth-user-changed',
    reAuthenticate: 'auth-re'
  })
  .service('Session', function () {
    this.create = function (userId, authorizedRoles, school) {
      this.userId = userId;
      this.authorizedRoles = authorizedRoles;
      this.school = school;
    };
    this.destroy = function () {
      this.userId = null;
      this.authorizedRoles = [];
      this.school = {};
    };
  })
  .constant('USER_ROLES', {
    ROLE_OIGUS_V_TEEMAOIGUS_A: 'ROLE_OIGUS_V_TEEMAOIGUS_A',
    ROLE_OIGUS_V_TEEMAOIGUS_P: 'ROLE_OIGUS_V_TEEMAOIGUS_P'
  })
;
