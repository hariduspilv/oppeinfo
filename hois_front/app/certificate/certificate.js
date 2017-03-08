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
}]).controller('CertificateEditController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', '$route', '$location', 'dialogService', 'message', '$resource', 'config', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils, $route, $location, dialogService, message, $resource, config) {


    $scope.getStudent = function() {
        if($scope.record.student) {
            QueryUtils.endpoint("/students/" + $scope.record.student).get().$promise.then(function(response){
                var name = response.person.firstname + ' ' + response.person.lastname;
                $scope.student = {
                    id: response.id,
                    nameEt: name,
                    nameEn: name
                };
            });
        }
    };

    function afterLoad() {
         $scope.getStudent();
    }

    var baseUrl = '/certificate';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;

    if(id) {
        $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
        $scope.record = new Endpoint();
        $scope.record.status = "TOEND_STAATUS_T";
        $scope.record.content = "sisu...";
        afterLoad();
    }

    $scope.signatories = [];

    QueryUtils.endpoint("/directives/coordinators").get().$promise.then(function(response){
        $scope.signatories = response.content;
    });

    $scope.studentChanged = function() {
        $scope.record.student = $scope.student ? $scope.student.id : null;
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
        if($scope.record.type !== 'TOEND_LIIK_MUU') {
            $scope.record.signatoryName = $scope.signatories.filter(function(e){return e.idcode === $scope.record.signatoryIdcode;})[0].name;
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

    // signatories manual input

    $scope.signatoryFound = false;
    
    $scope.clearSignatory = function() {
        $scope.signatoryFound = false;
        $scope.record.signatoryName = null;
        $scope.record.signatoryIdcode = null;
    };

    $scope.$watch('record.signatoryIdcode', function() {
            if($scope.record && !$scope.record.signatoryName && $scope.record.signatoryIdcode && 
            $scope.certificateEditForm.idcode && $scope.certificateEditForm.idcode.$valid) {
                $scope.getNameByIdcode();
            }
        }
    );
    $scope.idcodePattern = "^[1-6][0-9]{2}[0-1][0-9][0-3][0-9][0-9]{4}";

    $scope.getNameByIdcode = function() {
        $resource(config.apiUrl + baseUrl + '/signatoryName/').query({idcode: $scope.record.signatoryIdcode})
        .$promise.then(function(result){
            if(result[0] !== undefined) {
                $scope.record.signatoryName = result[0];
                $scope.signatoryFound = true;
            }
        });
    };
}]);
