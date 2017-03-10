'use strict';

angular.module('hitsaOis').config(['$routeProvider', function ($routeProvider) {
  $routeProvider
  .when('/', {
      templateUrl: 'home/home.html',
      controller: 'HomeController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } }
    });
}]);
