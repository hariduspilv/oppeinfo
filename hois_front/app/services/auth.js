'use strict';

angular.module('hitsaOis')
  .factory('AuthService', function ($http, $q, Session, Menu, config, Classifier, $sce, $rootScope) {
    var JWT_TOKEN_HEADER = 'Authorization';
    var authService = {};
    var roleMapper = Classifier.valuemapper({role: 'ROLL'});

    var authenticatedUser = function (response) {
      Menu.setMenu(response.data);
      if (response.data && response.data.user) {
        $q.all(roleMapper.promises).then(function () {
          roleMapper.objectmapper(response.data.users);
          for (var i = 0; i < response.data.users.length; i++) {
            if (response.data.users[i].schoolCode) {
              response.data.users[i].nameEn = response.data.users[i].role.nameEn + ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameEt = response.data.users[i].role.nameEt + ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameRu = response.data.users[i].role.nameRu + ' ' + response.data.users[i].schoolCode;
            } else {
              response.data.users[i].nameEn = response.data.users[i].role.nameEn;
              response.data.users[i].nameEt = response.data.users[i].role.nameEt;
              response.data.users[i].nameRu = response.data.users[i].role.nameRu;
            }
          }
        });
        Session.create(response.data);
        return response.data;
      } else {
        Session.destroy();
      }
      return null;
    };

    authService.login = function (headers) {
      return $http.get(config.apiUrl + '/user', {headers : headers})
        .then(function (res) {
          return authenticatedUser(res);
        });
    };

    authService.loginIdCard = function () {
      return $http.get($sce.trustAsUrl(config.idCardLoginUrl))
        .then(function (idLoginResult) {
          var headers = {headers: {}};
          headers[JWT_TOKEN_HEADER] = idLoginResult.headers(JWT_TOKEN_HEADER);
          return authService.login(headers);
        });
    };

    authService.postLogout = function() {
      Session.destroy();
      Menu.setMenu({});
      $rootScope.restartTimeoutDialogCounter();
    };

    authService.logout = function () {
      return $http.post(config.apiUrl + '/logout', {}).finally(function() {
        authService.postLogout();
      });
    };

    authService.changeUser = function (userId) {
      return $http.post(config.apiUrl + '/changeUser', {id:userId})
        .then(function (res) {
          return authenticatedUser(res);
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
        var unwatch = $rootScope.$watch('state.currentUser', function (currentUser) {
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
    this.create = function (user) {
      this.userId = user.user;
      this.studentId = user.student;
      this.teacherId = user.teacher;
      this.authorizedRoles = user.authorizedRoles;
      this.school = user.school;
      this.roleCode = user.roleCode;
      this.vocational = user.vocational;
      this.higher = user.higher;
      this.timeoutInSeconds = user.sessionTimeoutInSeconds;
    };
    this.destroy = function () {
      this.userId = null;
      this.studentId = null;
      this.teacherId = null;
      this.authorizedRoles = [];
      this.school = {};
      this.roleCode = null;
      this.vocational = undefined;
      this.higher = undefined;
      this.timeoutInSeconds = null;
    };
  })
  .constant('USER_ROLES', {
    ROLE_OIGUS_V_TEEMAOIGUS_A: 'ROLE_OIGUS_V_TEEMAOIGUS_A',
    ROLE_OIGUS_V_TEEMAOIGUS_P: 'ROLE_OIGUS_V_TEEMAOIGUS_P',

    ROLE_OIGUS_V_TEEMAOIGUS_AINE: 'ROLE_OIGUS_V_TEEMAOIGUS_AINE',	//Õppeained
    ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA',	//Aine-õpetaja paarid
    ROLE_OIGUS_V_TEEMAOIGUS_AKADKALENDER: 'ROLE_OIGUS_V_TEEMAOIGUS_AKADKALENDER',	//Akadeemiline kalender
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EHIS: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EHIS',	//EHIS andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS',	//SAIS andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE: 'ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE',	//Automaatsete teadete mallid
    ROLE_OIGUS_V_TEEMAOIGUS_AVALDUS: 'ROLE_OIGUS_V_TEEMAOIGUS_AVALDUS',	//Avaldused
    ROLE_OIGUS_V_TEEMAOIGUS_DIPLOM: 'ROLE_OIGUS_V_TEEMAOIGUS_DIPLOM',	//Diplomid/lõputunnistused
    ROLE_OIGUS_V_TEEMAOIGUS_DOKALLKIRI: 'ROLE_OIGUS_V_TEEMAOIGUS_DOKALLKIRI',	//Dokumentide kooskõlastajad
    ROLE_OIGUS_V_TEEMAOIGUS_EKSAM: 'ROLE_OIGUS_V_TEEMAOIGUS_EKSAM',	//Eksamid
    ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS: 'ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS',	//Esindajate avaldused
    ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT: 'ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT',	//Akad. õiendid/hinnetelehed
    ROLE_OIGUS_V_TEEMAOIGUS_HOONERUUM: 'ROLE_OIGUS_V_TEEMAOIGUS_HOONERUUM',	//Hooned/ruumid
    ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI: 'ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI',	//Käskkirjad
    ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA',	//Kasutajad
    ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR: 'ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR',	//Klassifikaatorid
    ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON: 'ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON',	//Komisjonid
    ROLE_OIGUS_V_TEEMAOIGUS_LEPING: 'ROLE_OIGUS_V_TEEMAOIGUS_LEPING',	//Lepingud
    ROLE_OIGUS_V_TEEMAOIGUS_LOPBLANKETT: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPBLANKETT',	//Blanketid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL',	//Lõputöö moodulite protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL',	//Lõputöö protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA',	//Lõputöö teemad
    ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL',	//Moodulite protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA',	//Õpetaja
    ROLE_OIGUS_V_TEEMAOIGUS_OPETAJAAMET: 'ROLE_OIGUS_V_TEEMAOIGUS_OPETAJAAMET',	//Õpetaja ametikohad
    ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA',	//Õpingukavad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS',	//Õppeasutused
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA',	//Õppekavad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD',	//Õppeperioodid
    ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM',	//Õpperühmad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETASE: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETASE',	//Õppetasemed
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK',	//Õppetöögraafik
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETULEMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETULEMUS',	//Õppetulemused
    ROLE_OIGUS_V_TEEMAOIGUS_OPPUR: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPUR',	//Õppurid
    ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK: 'ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK',	//Päevikud
    ROLE_OIGUS_V_TEEMAOIGUS_PARING: 'ROLE_OIGUS_V_TEEMAOIGUS_PARING',	//Päringud
    ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAPAEVIK: 'ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAPAEVIK',	//Praktika päevikud
    ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL',	//Protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE: 'ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE',	//Puudumistõendid
    ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA',	//Riiklikud õppekavad
    ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR: 'ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR',	//Struktuuriüksused
    ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS',	//Sündmused
    ROLE_OIGUS_V_TEEMAOIGUS_T: 'ROLE_OIGUS_V_TEEMAOIGUS_T',	//Õppuri üldine õigus
    ROLE_OIGUS_V_TEEMAOIGUS_TOEND: 'ROLE_OIGUS_V_TEEMAOIGUS_TOEND',	//Tõendid
    ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG',	//Tundide ajad
    ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN',	//Tunnijaotusplaan
    ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN',	//Tunniplaan
    ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT: 'ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT',	//Vastuvõtt
    ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE: 'ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE'	//Üldteated
  })
;
