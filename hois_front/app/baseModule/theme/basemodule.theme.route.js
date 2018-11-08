'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider) {

    function checkRightsToEdit(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
        AuthResolver.resolve().then(function(auth){
            if(!(ArrayUtils.includes(auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL) && auth.vocational)) {
                message.error('main.messages.error.nopermission');
                $location.path('/');
            }
        });
    }
    
    function checkRightsToView(message, $location, ArrayUtils, AuthResolver, USER_ROLES) {
        AuthResolver.resolve().then(function(auth) {
            if (!(ArrayUtils.includes(auth.authorizedRoles, USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_BAASMOODUL) && auth.vocational)) {
                message.error('main.messages.error.nopermission');
                $location.path('/');
            }
        });
    }

    $routeProvider
        .when('/basemodule/:baseModuleId/new', {
            templateUrl: 'baseModule/theme/basemodule.theme.edit.html',
            controller: 'baseModuleThemeEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToEdit
            }
        })
        .when('/basemodule/:baseModuleId/:baseModuleThemeId/edit', {
            templateUrl: 'baseModule/theme/basemodule.theme.edit.html',
            controller: 'baseModuleThemeEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToEdit
            }
        })
        .when('/basemodule/:baseModuleId/:baseModuleThemeId', {
            templateUrl: 'baseModule/theme/basemodule.theme.view.html',
            controller: 'baseModuleThemeEditController',
            resolve: {
                translationLoaded: function($translate) { return $translate.onReady(); },
                auth: function (AuthResolver) { return AuthResolver.resolve(); },
                checkAccess: checkRightsToView
            }
        })
}]);