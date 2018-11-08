'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
    
    function checkRightsToEdit(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
        AuthResolver.resolve().then(function(auth){
            if(!auth.vocational) {
                message.error('main.messages.error.nopermission');
                $location.path('/');
            }
        });
    }

    function checkRightsToView(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
        AuthResolver.resolve().then(function(auth) {
            if (!auth.vocational) {
                message.error('main.messages.error.nopermission');
                $location.path('/');
            }
        });
    }

    $routeProvider
        .when('/basemodule', {
            templateUrl: 'baseModule/basemodule.list.html',
            controller: 'baseModuleListController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToView
            },
            data: {
              authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL]
            }
        })
        .when('/basemodule/new', {
            templateUrl: 'baseModule/basemodule.edit.html',
            controller: 'baseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToEdit
            },
            data: {
              authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL]
            }
        })
        .when('/basemodule/:baseModuleId/edit', {
            templateUrl: 'baseModule/basemodule.edit.html',
            controller: 'baseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToEdit
            },
            data: {
              authorizedRoles: [USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL]
            }
        })
        .when('/basemodule/:baseModuleId', {
            templateUrl: 'baseModule/basemodule.view.html',
            controller: 'baseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToView
            },
            data: {
              authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL]
            }
        });
}]);
