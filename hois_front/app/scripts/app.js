'use strict';

/**
 * @ngdoc overview
 * @name hitsaOis
 * @description
 * # hitsaOis
 *
 * Main module of the application.
 */
angular
  .module('hitsaOis', [
    'ngAnimate',
    'ngAria',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngMaterial',
    'pascalprecht.translate',
    'md.data.table',
    'ui.router',
    'lfNgMdFileInput',
    'ngStorage'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/buildings/:buildingId?/:roomId?', {
        templateUrl: 'views/building.html',
        controller: 'BuildingController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/subject', {
        templateUrl: 'views/subject.list.html',
        controller: 'SubjectListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/subject/new', {
        templateUrl: 'views/subject.edit.html',
        controller: 'SubjectEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/subject/:id/edit', {
        templateUrl: 'views/subject.edit.html',
        controller: 'SubjectEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school', {
        templateUrl: 'views/school.list.html',
        controller: 'SchoolListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/new', {
        templateUrl: 'views/school.edit.html',
        controller: 'SchoolEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/:id/edit', {
        templateUrl: 'views/school.edit.html',
        controller: 'SchoolEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/departments', {
        templateUrl: 'views/school.department.list.html',
        controller: 'SchoolDepartmentListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/departments/new', {
        templateUrl: 'views/school.department.edit.html',
        controller: 'SchoolDepartmentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/departments/:id/edit', {
        templateUrl: 'views/school.department.edit.html',
        controller: 'SchoolDepartmentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/teacheroccupations', {
        templateUrl: 'views/teacher.occupation.list.html',
        controller: 'TeacherOccupationListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/teacheroccupations/new', {
        templateUrl: 'views/teacher.occupation.edit.html',
        controller: 'TeacherOccupationEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/teacheroccupations/:id/edit', {
        templateUrl: 'views/teacher.occupation.edit.html',
        controller: 'TeacherOccupationEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/school/studyLevels', {
        templateUrl: 'views/school.study.levels.html',
        controller: 'SchoolStudyLevelsController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/classifier', {
        templateUrl: 'views/classifier.list.html',
        controller: 'ClassifierListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/classifier/:code', {
        templateUrl: 'views/classifier.content.list.html',
        controller: 'ClassifierContentListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/classifier/:mainClassCode/new', {
        templateUrl: 'views/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/classifier/:mainClassCode/:codeThis/edit', {
        templateUrl: 'views/classifier.content.edit.html',
        controller: 'ClassifierContentEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })

      .when('/stateCurriculum/new', {
        templateUrl: 'views/state.curriculum.html',
        controller: 'StateCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
       .when('/stateCurriculum/module', {
        templateUrl: 'views/state.curriculum.module.html',
        controller: 'StateCurriculumModuleController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/stateCurriculum/:id/edit', {
        templateUrl: 'views/state.curriculum.html',
        controller: 'StateCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/stateCurriculum', {
        templateUrl: 'views/state.curriculum.list.html',
        controller: 'StateCurriculumListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/vocationalCurriculum/new', {
        templateUrl: 'views/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/vocationalCurriculum/:id/edit', {
        templateUrl: 'views/vocational.curriculum.html',
        controller: 'VocationalCurriculumController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        }
      })
      .when('/vocationalCurriculum/:id/moduleImplementationPlan/new', {
        templateUrl: 'views/vocational.curriculum.module.implementation.plan.html',
        controller: 'VocationalCurriculumModuleImplementationPlanController',
        controllerAs: 'controller',
        resolve: {
          translationLoaded: function($translate) { return $translate.onReady(); },
          entity: function(QueryUtils, $route) {
            return QueryUtils.endpoint('/curriculum').get({id: $route.current.params.id}).$promise;
          }
        }
      })
      .when('/curriculum', {
        templateUrl: 'views/curriculum.list.html',
        controller: 'CurriculumListController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/higherEducationCurriculum/new', {
        templateUrl: 'views/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/higherEducationCurriculum/:id/edit', {
        templateUrl: 'views/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .when('/higherEducationCurriculum/:id/view', {
        templateUrl: 'views/higher.education.curriculum.html',
        controller: 'HigherEducationCurriculumController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .config(function ($translateProvider) {

    //later use $translatePartialLoaderProvider
    // $translateProvider.useLoader('$translatePartialLoader', {
    //  urlTemplate: 'i18n/{part}/{lang}.json'
    // });

    $translateProvider.useStaticFilesLoader({
      prefix: 'i18n/',
      suffix: '.json'
    });
    $translateProvider.preferredLanguage('et');
    $translateProvider.fallbackLanguage('et');
    $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
  })
  .config(function($compileProvider){
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob|data):/);
    $compileProvider.debugInfoEnabled(false);
  })
  .config(function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    /*$httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';*/
  })
  .config(['$resourceProvider', function ($resourceProvider) {
    $resourceProvider.defaults.actions.update = {method: 'PUT', params: {id: '@id'}};
    $resourceProvider.defaults.actions.delete = {method: 'DELETE', params: {id: '@id', version: '@version'}};
  }])
  .config(function($mdThemingProvider) {
  $mdThemingProvider.theme('default')
    .primaryPalette('blue', {
      'default': '900'
    });
});
