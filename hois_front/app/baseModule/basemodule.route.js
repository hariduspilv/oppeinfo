'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
    
    function checkRightsToEdit(Session, roles) {
        return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL) !== -1;
    }

    function checkRightsToView(Session, roles) {
        return Session.vocational && roles.indexOf(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL) !== -1;
    }

    $routeProvider
        .when('/basemodule', {
            templateUrl: 'baseModule/basemodule.list.html',
            controller: 'BaseModuleListController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); }
            },
            data: {
              authorizedRoles: checkRightsToView
            }
        })
        .when('/basemodule/new', {
            templateUrl: 'baseModule/basemodule.edit.html',
            controller: 'BaseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); }
            },
            data: {
              authorizedRoles: checkRightsToEdit
            }
        })
        .when('/basemodule/:baseModuleId/edit', {
            templateUrl: 'baseModule/basemodule.edit.html',
            controller: 'BaseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); }
            },
            data: {
              authorizedRoles: checkRightsToEdit
            }
        })
        .when('/basemodule/:baseModuleId/view', {
            templateUrl: 'baseModule/basemodule.view.html',
            controller: 'BaseModuleEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); }
            },
            data: {
              authorizedRoles: checkRightsToView
            }
        });
}]);
