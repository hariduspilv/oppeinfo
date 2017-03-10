'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/certificate', {
        templateUrl: 'certificate/certificate.search.html',
        controller: 'CertificateSearchController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/certificate/new', {
        templateUrl: 'certificate/certificate.edit.html',
        controller: 'CertificateEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      })
      .when('/certificate/:id/edit', {
        templateUrl: 'certificate/certificate.edit.html',
        controller: 'CertificateEditController',
        controllerAs: 'controller',
        resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
        data: {
          authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
        }
      });
}]).controller('CertificateSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$q', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $q) {
    var clMapper = Classifier.valuemapper({type: 'TOEND_LIIK'});
    QueryUtils.createQueryForm($scope, '/certificate', {order: 'type.' + $scope.currentLanguageNameField()}, clMapper.objectmapper);
    $q.all(clMapper.promises).then($scope.loadData);
}]).controller('CertificateEditController', ['$scope', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$resource', 'config', function ($scope, QueryUtils, $route, $location, dialogService, message, $resource, config) {

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    function afterLoad() {
        if($scope.record.student) {
            getStudent().then(function(response){
                var name = getFullname(response.person);
                $scope.student = {
                    id: response.id,
                    nameEt: name,
                    nameEn: name
                };
            });
        }
        $scope.otherFound = true;
    }

    function getFullname(person) {
        return person.firstname + ' ' + person.lastname;
    }

    if(id) {
        $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
        $scope.record = new Endpoint();
        $scope.record.status = "TOEND_STAATUS_T";
        $scope.record.content = "sisu...";
        afterLoad();
    }

    function getStudent() {
        return QueryUtils.endpoint("/students/" + $scope.record.student).get().$promise.then(function(response){
            var name = getFullname(response.person);
            $scope.record.otherName = name;
            $scope.record.otherIdcode = response.person.idcode;
            $scope.otherFound = true;
            return response;
        });
    }

    $scope.signatories = [];

    QueryUtils.endpoint("/directives/coordinators").get().$promise.then(function(response){
        $scope.signatories = response.content;
    });

    $scope.studentChanged = function() {
        if($scope.student) {
            $scope.record.student = $scope.student.id;
            getStudent();
        } else {
            $scope.record.student = null;
            $scope.record.otherName = null;
            $scope.record.otherIdcode = null;
            $scope.otherFound = false;
        }
    };

    $scope.save = function() {
        $scope.certificateEditForm.$setSubmitted();
        if(!$scope.certificateEditForm.$valid) {
            message.error('main.messages.form-has-errors');
            return;
        }
        var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
        function afterSave() {
            message.info(msg);
        }
        $scope.record.signatoryName = $scope.signatories.filter(function(e){return e.idcode === $scope.record.signatoryIdcode;})[0].name;
        if($scope.record.student) {
            $scope.record.otherName = null;
            $scope.record.otherIdcode = null;
        }
        if($scope.record.id) {
            $scope.record.$update(afterLoad).then(afterSave);
        }else{
            $scope.record.$save(afterSave).then(function(){
                $location.path(baseUrl + "/" + $scope.record.id + "/edit");
            });
        }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'certificate.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path(baseUrl);
        });
      });
    };

    // student's manual input

    $scope.otherFound = false;
    
    $scope.clearOther = function() {
        $scope.student = null;
        $scope.otherFound = false;
        $scope.record.otherName = null;
        $scope.record.otherIdcode = null;
    };

    $scope.$watch('record.otherIdcode', function() {
            if($scope.record && !$scope.record.otherName && $scope.record.otherIdcode && 
            $scope.certificateEditForm.idcode && $scope.certificateEditForm.idcode.$valid) {
                $scope.getNameByIdcode();
            }
        }
    );
    $scope.idcodePattern = "^[1-6][0-9]{2}[0-1][0-9][0-3][0-9][0-9]{4}";

    $scope.getNameByIdcode = function() {
        $resource(config.apiUrl + baseUrl + '/otherStudent').get({idcode: $scope.record.otherIdcode})
        .$promise.then(function(result){
            if(result.id) {
                $scope.record.student = result.id;
                $scope.record.otherName = result.fullname;
                $scope.otherFound = true;
                $scope.student = {
                    id: result.id,
                    nameEt: result.fullname,
                    nameEn: result.fullname
                };
            } else if(result.fullname) {
                $scope.student = null;
                $scope.record.otherName = result.fullname;
                $scope.otherFound = true;
            } else {
                $scope.student = null;
            }
        });
    };
}]);
