'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/curriculum', {
      templateUrl: 'curriculum/curriculum.list.html',
      controller: 'CurriculumListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        auth: function (AuthResolver) { return AuthResolver.resolve(); }
      },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_OPPEKAVA]
      }
    })
    .when('/curriculums/:schoolId?', {
      templateUrl: 'curriculum/curriculum.public.list.html',
      controller: 'CurriculumPublicListController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); }
      }
    })
    .when('/curriculum/:id', {
      templateUrl: 'curriculum/curriculum.public.view.html',
      controller: 'CurriculumPublicController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        entity: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/public/curriculum').get({id: $route.current.params.id}).$promise;
        }
      }
    })
    .when('/curriculum/:curriculumId/version/:id', {
      templateUrl: 'curriculum/curriculum.public.version.html',
      controller: 'CurriculumPublicVersionController',
      controllerAs: 'controller',
      resolve: {
        translationLoaded: function($translate) { return $translate.onReady(); },
        curriculum: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/public/curriculum').get({id: $route.current.params.curriculumId}).$promise;
        },
        curriculumVersion: function(QueryUtils, $route) {
          return QueryUtils.endpoint('/public/curriculum/' + $route.current.params.curriculumId).get({id: $route.current.params.id}).$promise;
        }
      }
    });
}]);
