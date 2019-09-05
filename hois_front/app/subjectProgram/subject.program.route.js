'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
  var $route = $routeProvider.$get[$routeProvider.$get.length-1]({$on:function(){}}); // For Regex
    
  function checkRightsToEdit(Session) {
    return ['ROLL_O', 'ROLL_A'].indexOf(Session.roleCode) !== -1 && Session.higher;
  }

  function hasCurriculums(authUser) {
    return angular.isDefined(authUser.isCurriculumTeacher) && authUser.isCurriculumTeacher;
  }

  // function canSearchOtherTeacherPrograms($location, AuthResolver) {
  //   AuthResolver.resolve().then(function(auth) {
  //     if (!hasCurriculums(auth)) {
  //       $location.path('/subjectProgram/myPrograms');
  //     }
  //   });
  // }

  $routeProvider
    .when('/subjectProgram', {
      templateUrl: 'subjectProgram/subject.program.list.html',
      controller: 'SubjectProgramSearch',
      controllerAs: "searchController",
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function () {
          return { myData: false };
        }
        //checkAccess: canSearchOtherTeacherPrograms
      },
      data: {
        authorizedRoles: hasCurriculums
      }
    // }).when('/subjectProgram/myPrograms', {
    //   templateUrl: 'subjectProgram/subject.program.list.html',
    //   controller: 'SubjectProgramSearch',
    //   resolve: {
    //     translationLoaded: function($translate) { return $translate.onReady(); },
    //     auth: function (AuthResolver) { return AuthResolver.resolve(); },
    //     params: function () {
    //       return { myData: true };
    //     }
    //   },
    //   data: {
    //     authorizedRoles: function (Session, _) {
    //       return Session.roleCode === 'ROLL_O' && Session.higher;
    //     }
    //   }
    }).when('/subjectProgram/:subjectId/:subjectStudyPeriodId/:form/new', {
      templateUrl: 'subjectProgram/subject.program.edit.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: checkRightsToEdit
      }
    }).when('/subjectProgram/public/:subjectProgramId/view', {
      templateUrl: 'subjectProgram/subject.program.view.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        params: function () { return { form: "public" }; }
      }
    }).when('/subjectProgram/:form/:subjectProgramId/view', {
      templateUrl: 'subjectProgram/subject.program.view.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: function(Session) {
          return Session.roleCode === 'ROLL_A' || Session.roleCode === 'ROLL_O';
        }
      }
    }).when('/subjectProgram/:form/:subjectProgramId/edit', {
      templateUrl: 'subjectProgram/subject.program.edit.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
      },
      data: {
        authorizedRoles: checkRightsToEdit
      }
    });
    // Regex for paths
    $route.routes['/subjectProgram/:form/:subjectProgramId/view'].regexp = /^\/(?:subjectProgram\/(periods|programs|teachers)\/(\d+))\/view$/;
    $route.routes['/subjectProgram/:form/:subjectProgramId/view/'].regexp = /^\/(?:subjectProgram\/(periods|programs|teachers)\/(\d+))\/view\/$/;
    $route.routes['/subjectProgram/:form/:subjectProgramId/edit'].regexp = /^\/(?:subjectProgram\/(periods|programs|teachers)\/(\d+))\/edit$/;
    $route.routes['/subjectProgram/:form/:subjectProgramId/edit/'].regexp = /^\/(?:subjectProgram\/(periods|programs|teachers)\/(\d+))\/edit\/$/;
}]);
