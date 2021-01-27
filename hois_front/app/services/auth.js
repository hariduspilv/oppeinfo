'use strict';

angular.module('hitsaOis')
  .factory('AuthService', function ($http, $q, Session, Menu, config, Classifier, $rootScope, USER_CONFIRM_RIGHTS, ArrayUtils, USER_MESSAGE_RIGHTS) {
    var JWT_TOKEN_HEADER = 'Authorization';
    var authService = {};
    var roleMapper = Classifier.valuemapper({role: 'ROLL'});

    var authenticatedUser = function (response) {
      Menu.setMenu(response.data);
      if (response.data && response.data.user) {
        $q.all(roleMapper.promises).then(function () {
          roleMapper.objectmapper(response.data.users);
          for (var i = 0; i < response.data.users.length; i++) {
            response.data.users[i].nameEt = response.data.users[i].nameEt;
            response.data.users[i].nameEn = response.data.users[i].nameEn ? response.data.users[i].nameEn : response.data.users[i].role.nameEt;
            response.data.users[i].nameRu = response.data.users[i].role.nameRu;
            switch (response.data.users[i].role.code) {
              case "ROLL_T":
                if (response.data.users[i].studentGroup) {
                  var group = response.data.users[i].studentGroup.length > 12 ? response.data.users[i].studentGroup.slice(0, 13) + "..." : response.data.users[i].studentGroup;
                  response.data.users[i].nameEn += ' (' + group + ')';
                  response.data.users[i].nameEt += ' (' + group + ')';
                  response.data.users[i].nameRu += ' (' + group + ')';
                }
                break;
              case "ROLL_L":
                if (response.data.users[i].studentName) {
                  var name = response.data.users[i].studentName.length > 12 ? response.data.users[i].studentName.slice(0, 13) + "..." : response.data.users[i].studentName;
                  response.data.users[i].nameEn += ' (' + name + ')';
                  response.data.users[i].nameEt += ' (' + name + ')';
                  response.data.users[i].nameRu += ' (' + name + ')';
                }
                break;
            }
            if (response.data.users[i].schoolCode) {
              response.data.users[i].nameEn += ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameEt += ' ' + response.data.users[i].schoolCode;
              response.data.users[i].nameRu += ' ' + response.data.users[i].schoolCode;
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

    authService.loginMobileId = function (idcode, mobileNumber) {
      return $http.post(config.apiUrl + '/mIdLogin', {idcode: idcode, mobileNumber:mobileNumber})
        .then(function (mIdLoginResult) {
          return {data: mIdLoginResult.data, jwt: mIdLoginResult.headers(JWT_TOKEN_HEADER)};
        });
    };

    authService.mobileIdAuthenticate = function (jwt) {
      var headers = {};
      headers[JWT_TOKEN_HEADER] = jwt;
      var url = config.apiUrl + '/mIdAuthentication?lang=' + $rootScope.currentLanguage().toUpperCase();
      return $http.get(url, {headers : headers})
        .then(function (mIdStatusResult) {
          return mIdStatusResult.data;
        });
    };

    authService.loginLdap = function (credentials) {
      return $http.post(config.apiUrl + '/ldap', credentials)
        .then(function () {
          return authService.login();
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
      if (authService.isAuthenticated()) {
        if(angular.isFunction(authorizedRoles)) {
          return authorizedRoles(Session, Session.authorizedRoles, ArrayUtils);
        }
        if (!angular.isArray(authorizedRoles)) {
          authorizedRoles = [authorizedRoles];
        }
        for (var index = (authorizedRoles.length - 1); index > -1; index--) {
          if (Session.authorizedRoles.indexOf(authorizedRoles[index]) !== -1) {
            return true;
          }
        }
      }
      return false;
    };

    authService.matchesRole = function (roles) {
      if (authService.isAuthenticated()) {
        if (!angular.isArray(roles)) {
          roles = [roles];
        }
        for (var index = (roles.length - 1); index > -1; index--) {
          if (Session.roleCode === roles[index]) {
            return true;
          }
        }
      }
      return false;
    };

    authService.isValidRolePermission = function (roleCode, objectCode, permCode) {
      return ['OIGUS_K', 'OIGUS_T'].indexOf(permCode) === -1 ||
        (permCode === 'OIGUS_K' && USER_CONFIRM_RIGHTS.indexOf(objectCode) !== -1) ||
        (permCode === 'OIGUS_T' && USER_MESSAGE_RIGHTS.indexOf(objectCode) !== -1);
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

  .factory('AuthResolver', function ($q, $rootScope, $route, ArrayUtils, PUBLIC_ROUTES, AUTH_EVENTS, AuthService) {
    function isUserInRole(currentUser, roleIn) {
      return function() {
        return angular.isString(currentUser.roleCode) && angular.isString(roleIn) && currentUser.roleCode === roleIn;
      };
    }

    function isStudentInType(currentUser, type) {
      return function() {
        return isUserInRole(currentUser, 'ROLL_T')() && angular.isString(currentUser.type) && angular.isString(type) && currentUser.type === type;
      };
    }

    function hasAccess(currentUser) {
      if (!angular.isDefined($route.current.data)) {
        return true;
      }
      if (currentUser.mustAgreeWithToS) {
        return false;
      }
      if (isStudentInType(currentUser, 'OPPUR_K')() && $route.current.data.guestStudentForbidden) {
        return false;
      }
      if (isStudentInType(currentUser, 'OPPUR_E')() && $route.current.data.externalStudentForbidden) {
        return false;
      }
      if (isStudentInType(currentUser, 'OPPUR_E')() && currentUser.curriculumVersion === null && $route.current.data.studentCurriculumRequired) {
        return false;
      }
      var authorizedRoles = $route.current.data.authorizedRoles;
      return AuthService.isAuthorized(authorizedRoles);
    }
    return {
      resolve: function () {
        var deferred = $q.defer();
        var unwatch = $rootScope.$watch('state.currentUser', function (currentUser) {
          if (angular.isDefined(currentUser)) {
            if (currentUser) {
              if (hasAccess(currentUser)) {
                var authObject = angular.extend({}, currentUser);
                authObject.isMainAdmin = isUserInRole(currentUser, 'ROLL_P');
                authObject.isAdmin = isUserInRole(currentUser, 'ROLL_A');
                authObject.isLeadingTeacher = isUserInRole(currentUser, 'ROLL_J');
                authObject.isTeacher = isUserInRole(currentUser, 'ROLL_O');
                authObject.isStudent = isUserInRole(currentUser, 'ROLL_T');
                authObject.isGuestStudent = isStudentInType(currentUser, 'OPPUR_K');
                authObject.isParent = isUserInRole(currentUser, 'ROLL_L');
                authObject.isExternalExpert = isUserInRole(currentUser, 'ROLL_V');
                deferred.resolve(authObject);
              } else {
                deferred.reject();
                $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
              }
            } else {
              //If not public route, do not grant access to route
              if (!ArrayUtils.contains(PUBLIC_ROUTES, $route.current.originalPath)) {
                deferred.reject();
              } else {
                deferred.resolve();
              }
            }
            unwatch();
          }
        });
        return deferred.promise;
      }
    };
  })
  .run(function ($rootScope, AUTH_EVENTS, message) {
    $rootScope.$on(AUTH_EVENTS.notAuthorized, function() {
      message.error('main.messages.error.nopermission');
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
  .constant('PUBLIC_ROUTES', [
    '/academicCalendars',
    '/academicCalendar/:schoolId?',
    '/curriculums/:schoolId?',
    '/curriculum/:id',
    '/curriculum/:curriculumId/version/:id',
    '/stateCurriculum/public',
    '/stateCurriculum/public/:id/view',
    '/schoolBoard/:schoolId',
    '/schoolBoard/:schoolId/:type',
    '/schoolBoard/:schoolId/:type/:typeId',
    '/schoolBoard/:schoolId/currentEvents',
    '/schoolBoard/:schoolId/currentEvents/:roomId',
    '/schoolBoard/:schoolId/freeRooms/:type',
    '/subject/public',
    '/subject/public/:id',
    '/subjectProgram/public/:subjectProgramId/view',
    '/timetables',
    '/timetable/:schoolId?/generalTimetable/:type',
    '/timetable/:schoolId?/searchGeneralTimetable',
    '/timetable/:schoolId/:type/:typeId/:studyYearId/:weekIndex?',
    '/timetable/personalGeneralTimetable/:encodedPerson',
    '/timetable/person/:encodedPerson/:studyYearId/:weekIndex?',
    '/:backType?/studyMaterial/:schoolId?/vocational/:journalId/view',
    '/:backType?/studyMaterial/:schoolId?/higher/:subjectStudyPeriodId/view',
    '/practiceJournals/supervisor/:uuid',
    '/poll/supervisor/:uuid',
    '/poll/expert/:uuid',
    '/studyYearSchedule/public/:schoolId'
  ])
  .service('Session', function () {
    this.school = {};

    this.create = function (user) {
      this.userId = user.user;
      this.studentId = user.student;
      this.teacherId = user.teacher;
      this.isCurriculumTeacher = user.isCurriculumTeacher;
      this.authorizedRoles = user.authorizedRoles;
      this.school = angular.extend(this.school || {}, user.school);
      this.roleCode = user.roleCode;
      this.vocational = user.vocational;
      this.higher = user.higher;
      this.timeoutInSeconds = user.sessionTimeoutInSeconds;
      this.teacherGroupIds = user.teacherGroupIds;
      this.committees = user.committees || [];
      this.inApplicationCommittee = user.inApplicationCommittee;
      this.curriculums = user.curriculums || [];
      this.mustAgreeWithToS = user.mustAgreeWithToS;
      this.hasSchoolRole = user.hasSchoolRole;
    };
    this.destroy = function () {
      this.userId = null;
      this.studentId = null;
      this.teacherId = null;
      this.isCurriculumTeacher = null;
      this.authorizedRoles = [];
      this.school = {};
      this.roleCode = null;
      this.vocational = undefined;
      this.higher = undefined;
      this.timeoutInSeconds = null;
      this.teacherGroupIds = null;
      this.committees = [];
      this.curriculums = [];
      this.mustAgreeWithToS = undefined;
      this.hasSchoolRole = undefined;
      this.inApplicationCommittee = undefined;
    };
  })
  .constant('USER_ROLES', {
    ROLE_OIGUS_V_TEEMAOIGUS_A: 'ROLE_OIGUS_V_TEEMAOIGUS_A',
    ROLE_OIGUS_V_TEEMAOIGUS_P: 'ROLE_OIGUS_V_TEEMAOIGUS_P',

    ROLE_OIGUS_V_TEEMAOIGUS_AINE: 'ROLE_OIGUS_V_TEEMAOIGUS_AINE',	//Õppeained
    ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_AINEOPPETAJA',	//Aine-õpetaja paarid
    ROLE_OIGUS_V_TEEMAOIGUS_AKADKALENDER: 'ROLE_OIGUS_V_TEEMAOIGUS_AKADKALENDER',	//Akadeemiline kalender
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EHIS: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EHIS',	//EHIS andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EKIS: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_EKIS',	//EKIS andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER',	//RTIP andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_RTIP: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_RTIP',	//RTIP andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS: 'ROLE_OIGUS_V_TEEMAOIGUS_ANDMEVAHETUS_SAIS',	//SAIS andmevahetus
    ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE: 'ROLE_OIGUS_V_TEEMAOIGUS_AUTOTEADE',	//Automaatsete teadete mallid
    ROLE_OIGUS_V_TEEMAOIGUS_AVALDUS: 'ROLE_OIGUS_V_TEEMAOIGUS_AVALDUS',	//Avaldused
    ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL: 'ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL', //Baasmoodulid
    ROLE_OIGUS_V_TEEMAOIGUS_DIPLOM: 'ROLE_OIGUS_V_TEEMAOIGUS_DIPLOM',	//Diplomid/lõputunnistused
    ROLE_OIGUS_V_TEEMAOIGUS_DOKALLKIRI: 'ROLE_OIGUS_V_TEEMAOIGUS_DOKALLKIRI',	//Dokumentide kooskõlastajad
    ROLE_OIGUS_V_TEEMAOIGUS_EKSAM: 'ROLE_OIGUS_V_TEEMAOIGUS_EKSAM',	//Eksamid
    ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS: 'ROLE_OIGUS_V_TEEMAOIGUS_ESINDAVALDUS',	//Esindajate avaldused
    ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE: 'ROLE_OIGUS_V_TEEMAOIGUS_ETTEVOTE', //Ettevõtted
    ROLE_OIGUS_V_TEEMAOIGUS_HINDAMISSYSTEEM: 'ROLE_OIGUS_V_TEEMAOIGUS_HINDAMISSYSTEEM',	//Hindamissüsteemid
    ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT: 'ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT',	//Akad. õiendid/hinnetelehed
    ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE: 'ROLE_OIGUS_V_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE',
    ROLE_OIGUS_V_TEEMAOIGUS_HOONERUUM: 'ROLE_OIGUS_V_TEEMAOIGUS_HOONERUUM',	//Hooned/ruumid
    ROLE_OIGUS_V_TEEMAOIGUS_INDIVID: 'ROLE_OIGUS_V_TEEMAOIGUS_INDIVID', //IÕK statisitka
    ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI: 'ROLE_OIGUS_V_TEEMAOIGUS_KASKKIRI',	//Käskkirjad
    ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_KASUTAJA',	//Kasutajad
    ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR: 'ROLE_OIGUS_V_TEEMAOIGUS_KLASSIFIKAATOR',	//Klassifikaatorid
    ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON: 'ROLE_OIGUS_V_TEEMAOIGUS_KOMISJON',	//Komisjonid
    ROLE_OIGUS_V_TEEMAOIGUS_KOORM: 'ROLE_OIGUS_V_TEEMAOIGUS_KOORM',	//Koormuste haldamine
    ROLE_OIGUS_V_TEEMAOIGUS_KYSITLUS: 'ROLE_OIGUS_V_TEEMAOIGUS_KYSITLUS',	//Küsitlused
    ROLE_OIGUS_V_TEEMAOIGUS_LEPING: 'ROLE_OIGUS_V_TEEMAOIGUS_LEPING',	//Lepingud
    ROLE_OIGUS_V_TEEMAOIGUS_LOPBLANKETT: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPBLANKETT',	//Blanketid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPDOKALLKIRI: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPDOKALLKIRI',
    ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPMOODULPROTOKOLL',	//Lõputöö moodulite protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPPROTOKOLL',	//Lõputöö protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA',	//Lõputöö teemad
    ROLE_OIGUS_V_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE: 'ROLE_OIGUS_V_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE',
    ROLE_OIGUS_V_TEEMAOIGUS_MARKUS: 'ROLE_OIGUS_V_TEEMAOIGUS_MARKUS',	//Märkus
    ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_MOODULPROTOKOLL',	//Moodulite protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPETAJA',	//Õpetaja
    ROLE_OIGUS_V_TEEMAOIGUS_OPETAJAAMET: 'ROLE_OIGUS_V_TEEMAOIGUS_OPETAJAAMET',	//Õpetaja ametikohad
    ROLE_OIGUS_V_TEEMAOIGUS_OPILASKODU: 'ROLE_OIGUS_V_TEEMAOIGUS_OPILASKODU', //Õpilaskodud
    ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPINGUKAVA',	//Õpingukavad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEASUTUS',	//Õppeasutused
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA',	//Õppekavad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEMATERJAL: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEMATERJAL',
    ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPEPERIOOD',	//Õppeperioodid
    ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPERYHM',	//Õpperühmad
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETASE: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETASE',	//Õppetasemed
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOGRAAFIK',	//Õppetöögraafik
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOLIIK: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETOOLIIK',
    ROLE_OIGUS_V_TEEMAOIGUS_OPPETULEMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPETULEMUS',	//Õppetulemused
    ROLE_OIGUS_V_TEEMAOIGUS_OPPUR: 'ROLE_OIGUS_V_TEEMAOIGUS_OPPUR',	//Õppurid
    ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK: 'ROLE_OIGUS_V_TEEMAOIGUS_PAEVIK',	//Päevikud
    ROLE_OIGUS_V_TEEMAOIGUS_PARING: 'ROLE_OIGUS_V_TEEMAOIGUS_PARING',	//Päringud
    ROLE_OIGUS_V_TEEMAOIGUS_LOUNATOETUS: 'ROLE_OIGUS_V_TEEMAOIGUS_LOUNATOETUS',	//Lõunatoetus
    ROLE_OIGUS_V_TEEMAOIGUS_PERSYNDMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_PERSYNDMUS', //Personaalsed sündmused
    ROLE_OIGUS_V_TEEMAOIGUS_PILET: 'ROLE_OIGUS_V_TEEMAOIGUS_PILET', //Õpilaspiletid
    ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAAVALDUS: 'ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAAVALDUS',
    ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAPAEVIK: 'ROLE_OIGUS_V_TEEMAOIGUS_PRAKTIKAPAEVIK',	//Praktika päevikud
    ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL: 'ROLE_OIGUS_V_TEEMAOIGUS_PROTOKOLL',	//Protokollid
    ROLE_OIGUS_V_TEEMAOIGUS_PRHINDAMISVORM: 'ROLE_OIGUS_V_TEEMAOIGUS_PRHINDAMISVORM',
    ROLE_OIGUS_V_TEEMAOIGUS_PRSTATISTIKA: 'ROLE_OIGUS_V_TEEMAOIGUS_PRSTATISTIKA',	//Praktika statistika
    ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE: 'ROLE_OIGUS_V_TEEMAOIGUS_PUUDUMINE',	//Puudumistõendid
    ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA: 'ROLE_OIGUS_V_TEEMAOIGUS_RIIKLIKOPPEKAVA',	//Riiklikud õppekavad
    ROLE_OIGUS_V_TEEMAOIGUS_RR: 'ROLE_OIGUS_V_TEEMAOIGUS_RR',	//RR (rahvastikuregister) päring
    ROLE_OIGUS_V_TEEMAOIGUS_RYHMAJUHATAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_RYHMAJUHATAJA', // Rühmajuhataja aruanne
    ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR: 'ROLE_OIGUS_V_TEEMAOIGUS_STRUKTUUR',	//Struktuuriüksused
    ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS',	//Sündmused
    ROLE_OIGUS_V_TEEMAOIGUS_T: 'ROLE_OIGUS_V_TEEMAOIGUS_T',	//Õppuri üldine õigus
    ROLE_OIGUS_V_TEEMAOIGUS_TINGIMUS: 'ROLE_OIGUS_V_TEEMAOIGUS_TINGIMUS',	//Kasutustingimused
    ROLE_OIGUS_V_TEEMAOIGUS_TOEND: 'ROLE_OIGUS_V_TEEMAOIGUS_TOEND',	//Tõendid
    ROLE_OIGUS_V_TEEMAOIGUS_TUGITEENUS: 'ROLE_OIGUS_V_TEEMAOIGUS_TUGITEENUS', // Tugiteenus
    ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNDAEG',	//Tundide ajad
    ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNNIJAOTUSPLAAN',	//Tunnijaotusplaan
    ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN: 'ROLE_OIGUS_V_TEEMAOIGUS_TUNNIPLAAN',	//Tunniplaan
    ROLE_OIGUS_V_TEEMAOIGUS_UUDE_AASTASSE: 'ROLE_OIGUS_V_TEEMAOIGUS_UUDE_AASTASSE',
    ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT: 'ROLE_OIGUS_V_TEEMAOIGUS_VASTUVOTT',	//Vastuvõtt
    ROLE_OIGUS_V_TEEMAOIGUS_VOTA: 'ROLE_OIGUS_V_TEEMAOIGUS_VOTA',	//VÕTA
    ROLE_OIGUS_V_TEEMAOIGUS_VOTAKOM: 'ROLE_OIGUS_V_TEEMAOIGUS_VOTAKOM',	//VÕTA komisjonid
    ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE: 'ROLE_OIGUS_V_TEEMAOIGUS_YLDTEADE',	//Üldteated
    ROLE_OIGUS_V_TEEMAOIGUS_TEADE: 'ROLE_OIGUS_V_TEEMAOIGUS_TEADE', //Teated
    ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS: 'ROLE_OIGUS_V_TEEMAOIGUS_STIPTOETUS', //Stipendiumid ja toetused

    ROLE_OIGUS_M_TEEMAOIGUS_AINE: 'ROLE_OIGUS_M_TEEMAOIGUS_AINE',	//Õppeained
    ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA: 'ROLE_OIGUS_M_TEEMAOIGUS_AINEOPPETAJA',	//Aine-õpetaja paarid
    ROLE_OIGUS_M_TEEMAOIGUS_AKADKALENDER: 'ROLE_OIGUS_M_TEEMAOIGUS_AKADKALENDER',	//Akadeemiline kalender
    ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EHIS: 'ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EHIS',	//EHIS andmevahetus
    ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EKIS: 'ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_EKIS',	//EKIS andmevahetus
    ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER: 'ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER',
    ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_RTIP: 'ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_RTIP',	//RTIP andmevahetus
    ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_SAIS: 'ROLE_OIGUS_M_TEEMAOIGUS_ANDMEVAHETUS_SAIS',	//SAIS andmevahetus
    ROLE_OIGUS_M_TEEMAOIGUS_AUTOTEADE: 'ROLE_OIGUS_M_TEEMAOIGUS_AUTOTEADE',	//Automaatsete teadete mallid
    ROLE_OIGUS_M_TEEMAOIGUS_AVALDUS: 'ROLE_OIGUS_M_TEEMAOIGUS_AVALDUS',	//Avaldused
    ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL: 'ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL', //Baasmoodulid
    ROLE_OIGUS_M_TEEMAOIGUS_DIPLOM: 'ROLE_OIGUS_M_TEEMAOIGUS_DIPLOM',	//Diplomid/lõputunnistused
    ROLE_OIGUS_M_TEEMAOIGUS_DOKALLKIRI: 'ROLE_OIGUS_M_TEEMAOIGUS_DOKALLKIRI',	//Dokumentide kooskõlastajad
    ROLE_OIGUS_M_TEEMAOIGUS_EKSAM: 'ROLE_OIGUS_M_TEEMAOIGUS_EKSAM',	//Eksamid
    ROLE_OIGUS_M_TEEMAOIGUS_ESINDAVALDUS: 'ROLE_OIGUS_M_TEEMAOIGUS_ESINDAVALDUS',	//Esindajate avaldused
    ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE: 'ROLE_OIGUS_M_TEEMAOIGUS_ETTEVOTE', //Ettevõtted
    ROLE_OIGUS_M_TEEMAOIGUS_HINDAMISSYSTEEM: 'ROLE_OIGUS_M_TEEMAOIGUS_HINDAMISSYSTEEM',	//Hindamissüsteemid
    ROLE_OIGUS_M_TEEMAOIGUS_HINNETELEHT: 'ROLE_OIGUS_M_TEEMAOIGUS_HINNETELEHT',	//Akad. õiendid/hinnetelehed
    ROLE_OIGUS_M_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE: 'ROLE_OIGUS_M_TEEMAOIGUS_HINNETELEHT_TRUKKIMINE',
    ROLE_OIGUS_M_TEEMAOIGUS_HOONERUUM: 'ROLE_OIGUS_M_TEEMAOIGUS_HOONERUUM',	//Hooned/ruumid
    ROLE_OIGUS_M_TEEMAOIGUS_INDIVID: 'ROLE_OIGUS_M_TEEMAOIGUS_INDIVID', //IÕK statisitka
    ROLE_OIGUS_M_TEEMAOIGUS_KASKKIRI: 'ROLE_OIGUS_M_TEEMAOIGUS_KASKKIRI',	//Käskkirjad
    ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA: 'ROLE_OIGUS_M_TEEMAOIGUS_KASUTAJA',	//Kasutajad
    ROLE_OIGUS_M_TEEMAOIGUS_KLASSIFIKAATOR: 'ROLE_OIGUS_M_TEEMAOIGUS_KLASSIFIKAATOR',	//Klassifikaatorid
    ROLE_OIGUS_M_TEEMAOIGUS_KOMISJON: 'ROLE_OIGUS_M_TEEMAOIGUS_KOMISJON',	//Komisjonid
    ROLE_OIGUS_M_TEEMAOIGUS_KOORM: 'ROLE_OIGUS_M_TEEMAOIGUS_KOORM',	//Koormuste haldamine
    ROLE_OIGUS_M_TEEMAOIGUS_KYSITLUS: 'ROLE_OIGUS_M_TEEMAOIGUS_KYSITLUS',	//Küsitlused
    ROLE_OIGUS_M_TEEMAOIGUS_LEPING: 'ROLE_OIGUS_M_TEEMAOIGUS_LEPING',	//Lepingud
    ROLE_OIGUS_M_TEEMAOIGUS_LOPBLANKETT: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPBLANKETT',	//Blanketid
    ROLE_OIGUS_M_TEEMAOIGUS_LOPDOKALLKIRI: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPDOKALLKIRI',
    ROLE_OIGUS_M_TEEMAOIGUS_LOPMOODULPROTOKOLL: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPMOODULPROTOKOLL',	//Lõputöö moodulite protokollid
    ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPPROTOKOLL',	//Lõputöö protokollid
    ROLE_OIGUS_M_TEEMAOIGUS_LOPTEEMA: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPTEEMA',	//Lõputöö teemad
    ROLE_OIGUS_M_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE: 'ROLE_OIGUS_M_TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE',
    ROLE_OIGUS_M_TEEMAOIGUS_MARKUS: 'ROLE_OIGUS_M_TEEMAOIGUS_MARKUS',	//Märkus
    ROLE_OIGUS_M_TEEMAOIGUS_MOODULPROTOKOLL: 'ROLE_OIGUS_M_TEEMAOIGUS_MOODULPROTOKOLL',	//Moodulite protokollid
    ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA: 'ROLE_OIGUS_M_TEEMAOIGUS_OPETAJA',	//Õpetaja
    ROLE_OIGUS_M_TEEMAOIGUS_OPETAJAAMET: 'ROLE_OIGUS_M_TEEMAOIGUS_OPETAJAAMET',	//Õpetaja ametikohad
    ROLE_OIGUS_M_TEEMAOIGUS_OPILASKODU: 'ROLE_OIGUS_M_TEEMAOIGUS_OPILASKODU', //Õpilaskodud
    ROLE_OIGUS_M_TEEMAOIGUS_OPINGUKAVA: 'ROLE_OIGUS_M_TEEMAOIGUS_OPINGUKAVA',	//Õpingukavad
    ROLE_OIGUS_M_TEEMAOIGUS_OPPEASUTUS: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPEASUTUS',	//Õppeasutused
    ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPEKAVA',	//Õppekavad
    ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPEMATERJAL',
    ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPEPERIOOD',	//Õppeperioodid
    ROLE_OIGUS_M_TEEMAOIGUS_OPPERYHM: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPERYHM',	//Õpperühmad
    ROLE_OIGUS_M_TEEMAOIGUS_OPPETASE: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPETASE',	//Õppetasemed
    ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOGRAAFIK: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOGRAAFIK',	//Õppetöögraafik
    ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOLIIK: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPETOOLIIK',
    ROLE_OIGUS_M_TEEMAOIGUS_OPPETULEMUS: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPETULEMUS',	//Õppetulemused
    ROLE_OIGUS_M_TEEMAOIGUS_OPPUR: 'ROLE_OIGUS_M_TEEMAOIGUS_OPPUR',	//Õppurid
    ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK: 'ROLE_OIGUS_M_TEEMAOIGUS_PAEVIK',	//Päevikud
    ROLE_OIGUS_M_TEEMAOIGUS_PARING: 'ROLE_OIGUS_M_TEEMAOIGUS_PARING',	//Päringud
    ROLE_OIGUS_M_TEEMAOIGUS_LOUNATOETUS: 'ROLE_OIGUS_M_TEEMAOIGUS_LOUNATOETUS',	//Lõunatoetus
    ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS: 'ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS', //Personaalsed sündmused
    ROLE_OIGUS_M_TEEMAOIGUS_PILET: 'ROLE_OIGUS_M_TEEMAOIGUS_PILET', //Õpilaspiletid
    ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAAVALDUS: 'ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAAVALDUS',
    ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK: 'ROLE_OIGUS_M_TEEMAOIGUS_PRAKTIKAPAEVIK',	//Praktika päevikud
    ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL: 'ROLE_OIGUS_M_TEEMAOIGUS_PROTOKOLL',	//Protokollid
    ROLE_OIGUS_M_TEEMAOIGUS_PRHINDAMISVORM: 'ROLE_OIGUS_M_TEEMAOIGUS_PRHINDAMISVORM',
    ROLE_OIGUS_M_TEEMAOIGUS_PRSTATISTIKA: 'ROLE_OIGUS_M_TEEMAOIGUS_PRSTATISTIKA',	//Praktika statistika
    ROLE_OIGUS_M_TEEMAOIGUS_PUUDUMINE: 'ROLE_OIGUS_M_TEEMAOIGUS_PUUDUMINE',	//Puudumistõendid
    ROLE_OIGUS_M_TEEMAOIGUS_RIIKLIKOPPEKAVA: 'ROLE_OIGUS_M_TEEMAOIGUS_RIIKLIKOPPEKAVA',	//Riiklikud õppekavad
    ROLE_OIGUS_M_TEEMAOIGUS_RR: 'ROLE_OIGUS_M_TEEMAOIGUS_RR',	//RR (rahvastikuregister) päring
    ROLE_OIGUS_M_TEEMAOIGUS_RYHMAJUHATAJA: 'ROLE_OIGUS_V_TEEMAOIGUS_RYHMAJUHATAJA', // Rühmajuhataja aruanne
    ROLE_OIGUS_M_TEEMAOIGUS_STRUKTUUR: 'ROLE_OIGUS_M_TEEMAOIGUS_STRUKTUUR',	//Struktuuriüksused
    ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS: 'ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS',	//Sündmused
    ROLE_OIGUS_M_TEEMAOIGUS_T: 'ROLE_OIGUS_M_TEEMAOIGUS_T',	//Õppuri üldine õigus
    ROLE_OIGUS_M_TEEMAOIGUS_TINGIMUS: 'ROLE_OIGUS_M_TEEMAOIGUS_TINGIMUS',	//Kasutustingimused
    ROLE_OIGUS_M_TEEMAOIGUS_TOEND: 'ROLE_OIGUS_M_TEEMAOIGUS_TOEND',	//Tõendid
    ROLE_OIGUS_M_TEEMAOIGUS_TUGITEENUS: 'ROLE_OIGUS_V_TEEMAOIGUS_TUGITEENUS', // Tugiteenus
    ROLE_OIGUS_M_TEEMAOIGUS_TUNDAEG: 'ROLE_OIGUS_M_TEEMAOIGUS_TUNDAEG',	//Tundide ajad
    ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN: 'ROLE_OIGUS_M_TEEMAOIGUS_TUNNIJAOTUSPLAAN',	//Tunnijaotusplaan
    ROLE_OIGUS_M_TEEMAOIGUS_TUNNIPLAAN: 'ROLE_OIGUS_M_TEEMAOIGUS_TUNNIPLAAN',	//Tunniplaan
    ROLE_OIGUS_M_TEEMAOIGUS_UUDE_AASTASSE: 'ROLE_OIGUS_M_TEEMAOIGUS_UUDE_AASTASSE',
    ROLE_OIGUS_M_TEEMAOIGUS_VASTUVOTT: 'ROLE_OIGUS_M_TEEMAOIGUS_VASTUVOTT',	//Vastuvõtt
    ROLE_OIGUS_M_TEEMAOIGUS_VOTA: 'ROLE_OIGUS_M_TEEMAOIGUS_VOTA',	//VÕTA
    ROLE_OIGUS_M_TEEMAOIGUS_VOTAKOM: 'ROLE_OIGUS_M_TEEMAOIGUS_VOTAKOM',	//VÕTA komisjonid
    ROLE_OIGUS_M_TEEMAOIGUS_YLDTEADE: 'ROLE_OIGUS_M_TEEMAOIGUS_YLDTEADE',	//Üldteated
    ROLE_OIGUS_M_TEEMAOIGUS_TEADE: 'ROLE_OIGUS_M_TEEMAOIGUS_TEADE', //Teated
    ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS: 'ROLE_OIGUS_M_TEEMAOIGUS_STIPTOETUS', //Stipendiumid ja toetused

    ROLE_OIGUS_K_TEEMAOIGUS_AVALDUS: 'ROLE_OIGUS_K_TEEMAOIGUS_AVALDUS',
    ROLE_OIGUS_K_TEEMAOIGUS_OPINGUKAVA: 'ROLE_OIGUS_K_TEEMAOIGUS_OPINGUKAVA',
    ROLE_OIGUS_K_TEEMAOIGUS_TUNNIPLAAN: 'ROLE_OIGUS_K_TEEMAOIGUS_TUNNIPLAAN'

  })
  .constant('USER_CONFIRM_RIGHTS', [
    'TEEMAOIGUS_AINE',
    'TEEMAOIGUS_AVALDUS',
    'TEEMAOIGUS_EKSAM',
    'TEEMAOIGUS_ESINDAVALDUS',
    'TEEMAOIGUS_HINNETELEHT',
    'TEEMAOIGUS_HINNETELEHT_TRUKKIMINE',
    'TEEMAOIGUS_KASKKIRI',
    'TEEMAOIGUS_KASKKIRI_EKISETA',
    'TEEMAOIGUS_KOMISJON',
    'TEEMAOIGUS_LOPMOODULPROTOKOLL',
    'TEEMAOIGUS_LOPPROTOKOLL',
    'TEEMAOIGUS_LOPTEEMA',
    'TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE',
    'TEEMAOIGUS_MOODULPROTOKOLL',
    'TEEMAOIGUS_OPINGUKAVA',
    'TEEMAOIGUS_OPPEKAVA',
    'TEEMAOIGUS_PAEVIK',
    'TEEMAOIGUS_PRAKTIKAPAEVIK',
    'TEEMAOIGUS_PROTOKOLL',
    'TEEMAOIGUS_RIIKLIKOPPEKAVA',
    'TEEMAOIGUS_TUNNIPLAAN',
    'TEEMAOIGUS_VOTA'
  ]).constant('USER_MESSAGE_RIGHTS', [
    'TEEMAOIGUS_AVALDUS',
    'TEEMAOIGUS_ESINDAVALDUS',
    'TEEMAOIGUS_MARKUS',
    'TEEMAOIGUS_PUUDUMINE',
    'TEEMAOIGUS_TUGITEENUS',
    'TEEMAOIGUS_VOTA'
])
;
