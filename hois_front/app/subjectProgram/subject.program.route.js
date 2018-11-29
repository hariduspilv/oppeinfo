'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
    
  function checkRightsToEdit(message, $location, AuthResolver) {
    AuthResolver.resolve().then(function(auth){
      if(!auth.isTeacher() || !auth.higher) {
        message.error('main.messages.error.nopermission');
        $location.path('/');
      }
    });
  }

  function checkRightsToView(message, $location, AuthResolver) {
    AuthResolver.resolve().then(function(auth) {
      if (!auth.isTeacher() || !auth.higher) {
        message.error('main.messages.error.nopermission');
        $location.path('/');
      }
    });
  }

  function hasCurriculums(authUser) {
    return angular.isDefined(authUser.hasCurriculums) && authUser.hasCurriculums;
  }

  $routeProvider
    .when('/subjectProgram', {
      templateUrl: 'subjectProgram/subject.program.list.html',
      controller: 'SubjectProgramSearch',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: hasCurriculums
      }
    }).when('/subjectProgram/:subjectId/:subjectStudyPeriodId/:form/new', {
      templateUrl: 'subjectProgram/subject.program.edit.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        checkAccess: checkRightsToEdit
      }
    }).when('/subjectProgram/:subjectId/:subjectStudyPeriodId/:form/:subjectProgramId/view', {
      templateUrl: 'subjectProgram/subject.program.view.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        checkAccess: checkRightsToView
      }
    }).when('/subjectProgram/:subjectId/:subjectStudyPeriodId/:form/:subjectProgramId/edit', {
      templateUrl: 'subjectProgram/subject.program.edit.html',
      controller: 'SubjectProgramController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); },
        checkAccess: checkRightsToEdit
      }
    });
}]);
