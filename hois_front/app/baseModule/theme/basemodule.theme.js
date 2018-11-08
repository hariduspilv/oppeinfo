'use strict';

angular.module('hitsaOis')
.controller('baseModuleThemeEditController',
['$scope', 'QueryUtils', '$route', 'message', 'Classifier', '$location', '$rootScope', 'dialogService',
function ($scope, QueryUtils, $route, message, Classifier, $location, $rootScope, dialogService) {
    $scope.auth = $route.current.locals.auth;
    $scope.baseModuleId = $route.current.params.baseModuleId;
    $scope.themeId = $route.current.params.baseModuleThemeId;

    var baseUrl = "/basemodule/theme"
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var NewThemeEndpoint = QueryUtils.endpoint(baseUrl + "/basemodule");
    var HOURS_PER_EKAP = 26;

    $scope.capacities = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: false });
    $scope.capacities.$promise.then(function (result) {
        result = result.map(function (row) {
            row.capacityType = row.code;
        });
    })
    
    $rootScope.removeLastUrlFromHistory(function(url){
        return url && url.indexOf("new") !== -1;
    });
    
    function uniqueCapacityArray(arr) {
        var a = arr.concat();
        for (var i = 0; i < a.length; ++i) {
            for (var j = i+1; j < a.length; ++j) {
                if (a[i].capacityType === a[j].capacityType) {
                    a.splice(j--, 1);
                }
            }
        }
        return a;
    };

    function validateTheme() {
        $scope.themeForm.$setSubmitted();
        if (!$scope.themeForm.$valid) {
            message.error('main.messages.form-has-errors');
            return false;
        }
        if ($scope.theme.hours !== Math.round(HOURS_PER_EKAP * $scope.theme.credits) &&
            $scope.theme.credits !== Math.round(($scope.theme.hours / HOURS_PER_EKAP) * 10) / 10) {
            message.error('curriculum.error.themeCreditsAndHoursMismatch');
            return false;
        }
        if (!proportionsMatch()) {
            message.error('curriculum.error.themeCapacitiesAndHoursMismatch');
            return false;
        }
        return true;
    }

    function proportionsMatch() {
        return $scope.theme.hours === $scope.theme.capacities.reduce(function (sum, value) {
            if (angular.isDefined(value.hours)) {
                return sum += value.hours;
            } else {
                return sum;
            }
        }, 0);
    }

    if ($scope.themeId) {
        $scope.theme = Endpoint.get({id: $scope.themeId});
        $scope.theme.$promise.then(function (response) {
            if (response.capacities) {
                response.capacities = uniqueCapacityArray(response.capacities.concat($scope.capacities));
            } else {
                response.capacities = $scope.capacities;
            }
        });
    } else {
        $scope.theme = NewThemeEndpoint.get({id: $scope.baseModuleId});
        $scope.theme.$promise.then(function (response) {
            response.capacities = $scope.capacities;
            response.assessment = 'KUTSEHINDAMISVIIS_E';
            response.outcomes = [];
            $scope.theme = new Endpoint($scope.theme);
        });
    }

    $scope.setDefaultHours = function () {
        if (angular.isNumber($scope.theme.credits)) {
            $scope.theme.hours = Math.round(HOURS_PER_EKAP * $scope.theme.credits);
            $scope.themeForm.hours.$setDirty();
        }
    };

    $scope.assessments = Classifier.queryForDropdown({ mainClassCode: 'KUTSEHINDAMISVIIS'});

    $scope.setDefaultCredits = function () {
        $scope.theme.credits = Math.round(($scope.theme.hours / HOURS_PER_EKAP) * 10) / 10;
        $scope.themeForm.credits.$setDirty();
    };
    
    $scope.getDefaultUrl = function () {
        return '#/basemodule/' + $scope.baseModuleId;
    }

    $scope.save = function () {
        if (!validateTheme()) {
            return;
        }
        if(angular.isDefined($scope.themeId)) {
            $scope.theme.$update().then(function(response){
                response.capacities = uniqueCapacityArray(response.capacities.concat($scope.capacities));
                message.info('main.messages.update.success');
                $scope.themeForm.$setPristine();
            });
        } else {
            $scope.theme.$save().then(function(response){
                message.info('main.messages.create.success');
                $location.url('/basemodule/' + $scope.baseModuleId + "/" + response.id + "/edit");
                $scope.themeForm.$setPristine();
            });
        }
    }

    $scope.edit = function () {
        $location.url("/basemodule/" + $scope.baseModuleId + "/" + $scope.themeId + "/edit")
    }

    $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteTheme'}, function() {
            $scope.theme.$delete().then(function() {
                message.info('main.messages.delete.success');
                $location.url("/basemodule/" + $scope.baseModuleId + "/edit");
            });
        });
    };
}]);