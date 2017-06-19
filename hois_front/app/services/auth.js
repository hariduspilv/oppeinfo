'use strict';

angular.module('hitsaOis')
  .factory('AuthService', function ($http, $q, Session, Menu, config, Classifier) {
    var authService = {};
    var roleMapper = Classifier.valuemapper({role: 'ROLL'});

    var newUser = function (response) {
      Menu.setMenu(response.data);
      if (response.data && response.data.user) {
        roleMapper.objectmapper(response.data.users);
        $q.all(roleMapper.promises).then(function () {
          for (var i = 0; i < response.data.users.length; i++) {
            response.data.users[i].nameEn = response.data.users[i].role.nameEn;
            response.data.users[i].nameEt = response.data.users[i].role.nameEt;
            response.data.users[i].nameRu = response.data.users[i].role.nameRu;
            if (response.data.users[i].schoolCode) {
              response.data.users[i].nameEn = response.data.users[i].nameEn + ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameEt = response.data.users[i].nameEt + ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameRu = response.data.users[i].nameRu + ' ' + response.data.users[i].schoolCode;
            }
          }
        });
        Session.create(response.data.user, response.data.authorizedRoles, response.data.school, response.data.roleCode);
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

    authService.matchesRole = function (roles) {
      if (!angular.isArray(roles)) {
        roles = [roles];
      }
      if (authService.isAuthenticated()) {
        for (var index = (roles.length - 1); index > -1; index--) {
          if (Session.roleCode === roles[index]) {
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
    function isUserInRole(currentUser, roleIn) {
      return function() {
        return angular.isString(currentUser.roleCode) && angular.isString(roleIn) && currentUser.roleCode === roleIn;
      };
    }
    return {
      resolve: function () {
        var deferred = $q.defer();
        var unwatch = $rootScope.$watch('currentUser', function (currentUser) {
          if (angular.isDefined(currentUser)) {
            if (currentUser) {
              var authObject = angular.extend({}, currentUser);
              authObject.isMainAdmin = isUserInRole(currentUser, 'ROLL_P');
              authObject.isAdmin = isUserInRole(currentUser, 'ROLL_A');
              authObject.isTeacher = isUserInRole(currentUser, 'ROLL_O');
              authObject.isStudent = isUserInRole(currentUser, 'ROLL_T');
              authObject.isParent = isUserInRole(currentUser, 'ROLL_L');
              authObject.isExternalExpert = isUserInRole(currentUser, 'ROLL_V');
              deferred.resolve(authObject);
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
    // TODO refresh and authresolver
    //$rootScope.$on('$routeChangeStart', function (event, next) {
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
    this.create = function (userId, authorizedRoles, school, roleCode) {
      this.userId = userId;
      this.authorizedRoles = authorizedRoles;
      this.school = school;
      this.roleCode = roleCode;
    };
    this.destroy = function () {
      this.userId = null;
      this.authorizedRoles = [];
      this.school = {};
      this.roleCode = null;
    };
  })
  .constant('USER_ROLES', {
    ROLE_OIGUS_V_TEEMAOIGUS_A: 'ROLE_OIGUS_V_TEEMAOIGUS_A',
    ROLE_OIGUS_V_TEEMAOIGUS_P: 'ROLE_OIGUS_V_TEEMAOIGUS_P'
  })
;