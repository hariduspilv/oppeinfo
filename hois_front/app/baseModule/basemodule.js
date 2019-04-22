'use strict';

angular.module('hitsaOis')
.controller('BaseModuleListController', ['$scope', 'QueryUtils', '$route', "USER_ROLES", "AuthService", 
function ($scope, QueryUtils, $route, USER_ROLES, AuthService) {
    QueryUtils.createQueryForm($scope, '/basemodule');

    $scope.auth = $route.current.locals.auth;
    
    var autocompleteCurriculum = QueryUtils.endpoint("/autocomplete/curriculumsauto");
    var autocompleteCurriculumVersion = QueryUtils.endpoint("/autocomplete/curriculumversions");

    $scope.formState = {
        canCreate: $scope.auth.isAdmin() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL)
    };

    $scope.searchCurriculums = function (name) {
        return autocompleteCurriculum.query({name: name}).$promise;
    };

    $scope.searchCurriculumVersions = function (name) {
        return autocompleteCurriculumVersion.query({name: name}).$promise;
    };
    
    $scope.criteria.order = 'b.' + $scope.currentLanguageNameField();
    $scope.loadData();
}])
.controller('BaseModuleEditController',
['$scope', 'QueryUtils', '$route', 'message', 'ArrayUtils', 'dialogService', '$location', '$rootScope', '$q', "USER_ROLES", "AuthService", 
function ($scope, QueryUtils, $route, message, ArrayUtils, dialogService, $location, $rootScope, $q, USER_ROLES, AuthService) {

    var baseUrl = "/basemodule";
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var autocompleteTeacher = QueryUtils.endpoint(baseUrl + "/teachers");
    var capacityPromise = $q.defer();

    $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_BAASMOODUL);
    $scope.auth = $route.current.locals.auth;
    $scope.baseModuleId = $route.current.params.baseModuleId;
    $scope.capacities = QueryUtils.endpoint('/autocomplete/schoolCapacityTypes').query({ isHigher: false });
    $scope.capacities.$promise.then(function (result) {
        result = result.map(function (row) {
            row.capacityType = row.code;
        });
        capacityPromise.resolve(result);
    }).catch(function (error) {
        capacityPromise.reject(error);
    });

    $rootScope.removeLastUrlFromHistory(function(url){
        return url && url.indexOf("new") !== -1;
    });

    var initial = {
        cvAssessment: "KUTSEHINDAMISVIIS_E",
        outcomes: [],
        capacities: $scope.capacities,
        themes: []
    };

    function get() {
        if ($scope.baseModuleId) {
            $scope.baseModule = Endpoint.get({id: $scope.baseModuleId});
            $scope.baseModule.$promise.then(function () {
                dtoToModule();
            });
        } else {
            $scope.baseModule = new Endpoint(initial);
        }
    }

    function create() {
        $scope.baseModule.$save().then(function (response) {
            message.info('main.messages.create.success');
            $location.url("/basemodule/" + response.id + "/edit");
        });
    }

    function update() {
        $scope.baseModule.$update().then(function () {
            dtoToModule();
            message.info('main.messages.update.success');
            $scope.baseModuleForm.$setPristine();
        }).catch(function (error) {
            if (error.data._errors[0].code === "basemodule.errors.outcomehasconnections") {
                get();
            }
        });
    }

    function editTheme(theme) {
        if (angular.isDefined(theme)) {
            $location.url("/basemodule/" + $scope.baseModuleId + "/" + theme.id + "/edit");
        } else {
            $location.url("/basemodule/" + $scope.baseModuleId + "/new");
        }
    }

    function validationPassed() {
        $scope.baseModuleForm.$setSubmitted();
        if (!$scope.baseModuleForm.$valid) {
            message.error('main.messages.form-has-errors');
            return false;
        }
        return true;
    }
    
    function moduleToDto() {
        $scope.baseModule.outcomes.forEach(function(outcome){
            outcome.orderNr = $scope.baseModule.outcomes.indexOf(outcome);
        });
    }

    function dtoToModule() {
        if ($scope.baseModule.outcomes) {
            $scope.baseModule.outcomes.sort(function (o1, o2) {
                return o1.orderNr - o2.orderNr;
            });
        } else {
            $scope.baseModule.outcomes = [];
        }
        $scope.baseModule.validFrom = new Date($scope.baseModule.validFrom);
        if ($scope.baseModule.validThru) {
            $scope.baseModule.validThru = new Date($scope.baseModule.validThru);
        }
        capacityPromise.promise.then(function () {
            if ($scope.baseModule.capacities) {
                $scope.baseModule.capacities = uniqueCapacityArray($scope.baseModule.capacities.concat($scope.capacities));
            } else {
                $scope.baseModule.capacities = $scope.capacities;
            }
        });
    }
    
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
    }

    /****************
     *   Outcomes   *
     ****************/

    $scope.isEditingOutcome = false;
    $scope.editedOutcome = null;

    $scope.addOutcome = function () {
        if (angular.isString($scope.outcomeEt) && $scope.outcomeEt !== '') {
            $scope.baseModule.outcomes.push({outcomeEt: $scope.outcomeEt, outcomeEn: $scope.outcomeEn, orderNr: $scope.baseModule.outcomes.length});
            $scope.outcomeEt = undefined;
            $scope.outcomeEn = undefined;
        }
    };

    $scope.editOutcome = function (outcome) {
        $scope.isEditingOutcome = true;
        $scope.editedOutcome = outcome;
        $scope.outcomeEt = outcome.outcomeEt;
        $scope.outcomeEn = outcome.outcomeEn;
    };

    $scope.swap = function (index1, index2) {
        var temp = $scope.baseModule.outcomes[index1];
        $scope.baseModule.outcomes[index1] = $scope.baseModule.outcomes[index2];
        $scope.baseModule.outcomes[index2] = temp;
        $scope.baseModuleForm.$setDirty();
    };

    $scope.saveOutcome = function () {
        if (angular.isString($scope.outcomeEt) && $scope.outcomeEt !== '') {
            $scope.isEditingOutcome = false;

            $scope.editedOutcome.outcomeEt = $scope.outcomeEt;
            $scope.editedOutcome.outcomeEn = $scope.outcomeEn;

            $scope.outcomeEt = undefined;
            $scope.outcomeEn = undefined;
        }
    };

    $scope.removeFromArray = function (array, item) {
        dialogService.confirmDialog({prompt: 'curriculum.itemDeleteConfirm'}, function() {
            ArrayUtils.remove(array, item);
            $scope.baseModuleForm.$setDirty();
        });
    };

    $scope.hasThemes = function () {
        return $scope.baseModule && $scope.baseModule.themes && $scope.baseModule.themes.length > 0;
    };

    $scope.editTheme = function (theme) {
        if (angular.isDefined($scope.baseModuleForm) && $scope.baseModuleForm.$dirty === true ) {
            dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
                editTheme(theme);
            });
        } else {
            editTheme(theme);
        }
    };

    $scope.viewTheme = function (theme) {
        if (angular.isDefined(theme) && $scope.baseModuleId) {
            $location.url("/basemodule/" + $scope.baseModuleId + "/" + theme.id + "/view");
        }
    };
    
    $scope.searchTeacher = function (text) {
        return autocompleteTeacher.query({
            name: text
        }).$promise;
    };


    $scope.edit = function () {
        $location.url("/basemodule/" + $scope.baseModule.id + "/edit");
    };
    
    $scope.delete = function () {
        dialogService.confirmDialog({prompt: 'curriculum.prompt.deleteModule'}, function() {
            $scope.baseModule.$delete().then(function () {
                message.info('main.messages.delete.success');
                $location.url("/basemodule");
            });
        });
    };

    $scope.save = function () {
        if(!validationPassed()) {
          return;
        }
        moduleToDto();
        if($scope.baseModule.id) {
          update();
        } else {
          create();
        }
    };

    get();
}]);