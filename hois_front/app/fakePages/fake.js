'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/subjectTeacher', {
        templateUrl: 'fakePages/fake.html',
        controller: 'fakeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/declaration', {
        templateUrl: 'fakePages/fake.html',
        controller: 'fakeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/notifications', {
        templateUrl: 'fakePages/fake.html',
        controller: 'fakeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/receptionImport', {
        templateUrl: 'fakePages/fake.html',
        controller: 'fakeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
     .when('/users', {
        templateUrl: 'fakePages/fake.html',
        controller: 'fakeController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_P]
        }
      })
}]).controller('fakeController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q) {
    // var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK'});
    // QueryUtils.createQueryForm($scope, '/certificate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    // $q.all(clMapper.promises).then($scope.loadData);
    // var clMapper = Classifier.valuemapper({status: 'OPPEKAVA_STAATUS', ekrLevel: 'EKR'});
    // QueryUtils.createQueryForm($scope, '/stateCurriculum', {order: 'fakeOrder'}, clMapper.objectmapper);
    // $q.all(clMapper.promises).then($scope.loadData);
    // $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];

    QueryUtils.endpoint("/fake/get505Error").get().$promise.then(function(response){
        // console.log("got error!");

            });
    // console.log("F!");
}]);