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
    'lfNgMdFileInput',
    'ngStorage',
    'angular-cache'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .otherwise({
        redirectTo: '/'
      });
  })
  .config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix ('');
  }])
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
    //$translateProvider.useSanitizeValueStrategy('sce');
  })
  .config(function($compileProvider){
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob|data):/);
    $compileProvider.debugInfoEnabled(false);
  })
  .config(function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
  })
  .config(['$resourceProvider', function ($resourceProvider) {
    $resourceProvider.defaults.actions.update = {method: 'PUT', params: {id: '@id'}};
    $resourceProvider.defaults.actions.delete = {method: 'DELETE', params: {id: '@id', version: '@version'}};
  }])
  .config(function($mdThemingProvider) {
    $mdThemingProvider.theme('default')
      .primaryPalette('blue', {'default': '900'})
	  .accentPalette('pink')
	  .backgroundPalette('grey')
	  ;
  }).config(function ($httpProvider) {
  $httpProvider.interceptors.push([
    '$injector',
    function ($injector) {
      return $injector.get('AuthInterceptor');
    }
  ]);
  $httpProvider.interceptors.push(function(Session, $rootScope) {
    return {
     'response': function(response) {
         if (response.config.url.indexOf('session.timing.out.dialog.html') === -1 &&
          response.config.url.indexOf('session.timed.out.dialog.html') === -1 && response.config.url.indexOf('/logout') === -1) {
          $rootScope.restartTimeoutDialogCounter();
         }
         return response;
      },
    };
  });

}).config(function (CacheFactoryProvider) {
  angular.extend(CacheFactoryProvider.defaults, { maxAge: 60 * 60 * 1000, deleteOnExpire: 'passive'});
});
