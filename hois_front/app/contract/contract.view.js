'use strict';

angular.module('hitsaOis').controller('ContractViewController', ['$scope', '$location', '$route', 'QueryUtils', 'dialogService', 'ekisService', 'message','FormUtils','DataUtils','Classifier','$q','$window', 'config',
  function ($scope, $location, $route, QueryUtils, dialogService, ekisService, message, FormUtils, DataUtils, Classifier, $q, $window, config) {
    $scope.auth = $route.current.locals.auth;
    $scope.getEkisContractUrl = ekisService.getContractUrl;
    $scope.formState = {};

    function entityToForm(entity) {
      DataUtils.convertObjectToIdentifier(entity, ['module', 'theme', 'subject']);
      $scope.contract = entity;
      $scope.contract.canceled = new Date();
      $scope.formState.isHigher = entity.isHigher;
    }

    var entity = $route.current.locals.entity;
    if (angular.isDefined(entity)) {
      entityToForm(entity);
    }

    var clMapper = Classifier.valuemapper({
      status: 'LEPING_STAATUS'
    });

    $q.all(clMapper.promises).then(function () {
      if ($scope.contract) {
        clMapper.objectmapper($scope.contract);
      }
    });

    $scope.getAstronomicalHours = DataUtils.getAstronomicalHours;

    $scope.showCancel = function () {
      if ($scope.showCancelBoolean === undefined || $scope.showCancelBoolean === false) {
        $scope.showCancelBoolean = true;
      } else if ($scope.showCancelBoolean === true) {
        $scope.showCancelBoolean = false;
      }
    }; 

    $scope.openInNewTab = function(supervisorUrl) {
      QueryUtils.endpoint('/practiceJournals/supervisor').get({id: supervisorUrl}).$promise.then(function() {
        $window.open('#/practiceJournals/supervisor/' + supervisorUrl, '_blank');
      }).catch(angular.noop);
    };

    $scope.sendEmailAgain = function (supervisor, supervisorUrl) {
      QueryUtils.endpoint('/practiceJournals/supervisor').get({id: supervisorUrl}).$promise.then(function() {
        var ContractEndpoint = QueryUtils.endpoint('/contracts/sendEmail');
        var contract = new ContractEndpoint({id: supervisor});
        contract.$update().then(function () {
          message.info('contract.emailSent');
        }).catch(angular.noop);
      }).catch(angular.noop);
    };

    $scope.delete = function () {
      dialogService.confirmDialog({ prompt: 'contract.deleteconfirm' }, function () {
        var ContractEndpoint = QueryUtils.endpoint('/contracts');
        var contract = new ContractEndpoint($scope.contract);
        contract.$delete().then(function () {
          message.info('main.messages.delete.success');
          $scope.back('/#/contracts');
        });
      });
    };

    var ContractEndpoint = QueryUtils.endpoint('/contracts/cancel');
    $scope.save = function (success) {
      FormUtils.withValidForm($scope.contractForm, function() {
        var contract = new ContractEndpoint($scope.contract);
        if (angular.isDefined($scope.contract.id)) {
          contract.$update().then(function (response) {
            message.updateSuccess();
            entityToForm(response);
            if (angular.isFunction(success)) {
              success();
            }
            $scope.contractForm.$setPristine();
            $route.reload();
          }).catch(angular.noop);
        }
      });
    };

    $scope.changeContractNr = function() {
      FormUtils.withValidForm($scope.contractForm, function () {
        var ContractEndpoint = QueryUtils.endpoint('/contracts/changeContractNr/' + $scope.contract.id);
        var contract = new ContractEndpoint({contractNr: $scope.contract.contractNr});
        contract.$putWithoutLoad();
      });
    };
    
    $scope.printUrl = function () {
      return config.apiUrl + '/contracts/print/' + $scope.contract.id + "/contract.rtf?lang=" + $scope.currentLanguage().toUpperCase();
    };

    $scope.confirm = function () {
      dialogService.confirmDialog({ prompt: 'contract.withoutEkisConfirm'}, function () {
        var ConfirmEndpoint = QueryUtils.endpoint('/contracts/confirm/' + $scope.contract.id);
        new ConfirmEndpoint().$save().then(function (contract) {
          entityToForm(contract);
          clMapper.objectmapper($scope.contract);
          message.info('contract.messages.confirmed');
        }).catch(angular.noop);
      });
    };
  }
]);
