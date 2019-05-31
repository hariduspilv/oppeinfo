'use strict';

angular.module('hitsaOis').controller('PracticeEnterpriseEditController', ['$location', '$route', '$scope', 'message', 'dialogService', 'FormUtils', 'QueryUtils',  
function ($location, $route, $scope, message, dialogService, FormUtils, QueryUtils) {
  $scope.currentNavItem = 'enterprise.data';    
  $scope.auth = $route.current.locals.auth;
  var id = $route.current.params.id;
  var baseUrl = '/practiceEnterprise';
  var Endpoint = QueryUtils.endpoint(baseUrl);

  $scope.clearAddressAndRegCode = function() {
    $scope.enterprise.addressRegister = undefined;
    $scope.enterprise.regCode = undefined;
  };

  $scope.goBack = function() {
    var url = '/practice/enterprise';
    if($scope.enterpriseForm.$dirty) {
      dialogService.confirmDialog({prompt: 'main.messages.confirmFormDataNotSaved'}, function() {
        $location.path(url);
      });
    } else {
      $location.path(url);
    }
  };

  $scope.sameCountryAndCode = function() {
    if ($scope.enterprise.country !== null && $scope.enterprise.regCode !== null) {
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl + '/sameCountryAndCode').get(
        {
          country: $scope.enterprise.country,
          regCode: $scope.enterprise.regCode
        }
      ).$promise.then(function (response) {
        var ctrl;
        if (response.status) {
          message.error(response.status);
          ctrl = $scope.enterpriseForm.enterpriseCode;
          ctrl.$setValidity('enterpriseCodeError', false);
        } else {
          ctrl = $scope.enterpriseForm.enterpriseCode;
          ctrl.$setValidity('enterpriseCodeError', true);
        }
        QueryUtils.loadingWheel($scope, false);
      }).catch(angular.noop);
    }
  };


  $scope.lookupRegNr = function() {
    if ($scope.enterprise.country === 'RIIK_EST' && $scope.enterpriseForm.enterpriseCode.$error.pattern === undefined && $scope.enterpriseForm.enterpriseCode.$error.minlength === undefined && $scope.enterpriseForm.enterpriseCode.$error.maxlength === undefined && $scope.enterprise.regCode) {
      QueryUtils.loadingWheel($scope, true);
      QueryUtils.endpoint(baseUrl + '/regCodeCheck').get(
        {
          country: $scope.enterprise.country,
          regCode: $scope.enterprise.regCode,
          language: $scope.enterprise.language,
          active: $scope.enterprise.active
        }
      ).$promise.then(function (response) {
        var ctrl;
        if (response.status) {
          message.error(response.status);
          ctrl = $scope.enterpriseForm.enterpriseCode;
          ctrl.$setValidity('enterpriseCodeError', false);
        } else {
          ctrl = $scope.enterpriseForm.enterpriseCode;
          ctrl.$setValidity('enterpriseCodeError', true);
          if ($scope.enterprise.address === undefined || $scope.enterprise.address === null) {
            $scope.enterprise = new Endpoint({
              regCode: $scope.enterprise.regCode,
              active: $scope.enterprise.active,
              language: $scope.enterprise.language,
              name: response.name,
              address: response.address,
              addressAds: response.addressAds,
              addressOid: response.addressOid,
              addressRegister: response.address,
              registerAddress: response.registerAddress,
              registerAddressAds: response.registerAddressAds,
              registerAddressOid: response.registerAddressOid,
              country: $scope.enterprise.country,
              postcode: $scope.enterprise.postcode,
              email: $scope.enterprise.email,
              phone: $scope.enterprise.phone,
              places: $scope.enterprise.places,
              placesDescr: $scope.enterprise.placesDescr,
              addInfo: $scope.enterprise.addInfo,
              person: $scope.enterprise.person,
              id: $scope.enterprise.id,
              });
          } else {
            $scope.enterprise = new Endpoint({
              regCode: $scope.enterprise.regCode,
              active: $scope.enterprise.active,
              language: $scope.enterprise.language,
              name: response.name,
              address: $scope.enterprise.address,
              addressAds: $scope.enterprise.addressAds,
              addressOid: $scope.enterprise.addressOid,
              registerAddress: response.registerAddress,
              registerAddressAds: response.registerAddressAds,
              registerAddressOid: response.registerAddressOid,
              country: $scope.enterprise.country,
              postcode: $scope.enterprise.postcode,
              email: $scope.enterprise.email,
              phone: $scope.enterprise.phone,
              places: $scope.enterprise.places,
              placesDescr: $scope.enterprise.placesDescr,
              addInfo: $scope.enterprise.addInfo,
              person: $scope.enterprise.person,
              id: $scope.enterprise.id,
              });
          }
          $scope.cameFromRegister = true;
        }
        QueryUtils.loadingWheel($scope, false);
      }).catch(angular.noop);
    }
  };

  $scope.lookupRegNrForAddressOnly = function(enterpriseResponse) {
    if (enterpriseResponse.country === 'RIIK_EST' && enterpriseResponse.regCode && !enterpriseResponse.person) {
      QueryUtils.endpoint(baseUrl + '/regCodeWithoutCheck').get(
      {
          country: enterpriseResponse.country,
          regCode: enterpriseResponse.regCode,
          language: enterpriseResponse.language,
          active: enterpriseResponse.active
      }
      ).$promise.then(function (response) {
      if (response.status === null) {
        $scope.enterprise = new Endpoint({
        regCode: $scope.enterprise.regCode,
        active: $scope.enterprise.active,
        application: $scope.enterprise.application,
        language: $scope.enterprise.language,
        name: $scope.enterprise.name,
        address: $scope.enterprise.address,
        addressAds: $scope.enterprise.addressAds,
        addressOid: $scope.enterprise.addressOid,
        registerAddress: response.registerAddress,
        registerAddressAds: response.registerAddressAds,
        registerAddressOid: response.registerAddressOid,
        country: $scope.enterprise.country,
        postcode: $scope.enterprise.postcode,
        email: $scope.enterprise.email,
        phone: $scope.enterprise.phone,
        places: $scope.enterprise.places,
        placesDescr: $scope.enterprise.placesDescr,
        addInfo: $scope.enterprise.addInfo,
        person: $scope.enterprise.person,
        ebusinessUpdated: $scope.enterprise.ebusinessUpdated,
        enterpriseSchoolId: $scope.enterprise.enterpriseSchoolId,
        id: $scope.enterprise.id
        });
      }
      }).catch(angular.noop);
    }
  };

  function loadEnterprise (id) {
    if (angular.isDefined(id) && id !== null) {
      $scope.enterprise = Endpoint.get({id: id});
      $scope.enterprise.$promise.then(function (data) {
        window.localStorage.setItem('enterpriseName', data.name);
        if (data.regCode !== null) {
          window.localStorage.setItem('enterpriseRegCode', data.regCode);
        } else {
          window.localStorage.removeItem('enterpriseRegCode');
        }
        if (data.application) {
          window.localStorage.setItem('enterpriseApplication', data.application);
        } else {
            window.localStorage.removeItem('enterpriseApplication');
        }
        $scope.enterpriseForm.$setPristine();
      });
    } else {
      $scope.enterprise = new Endpoint({active: true, country: 'RIIK_EST',language: 'OPPEKEEL_E'});
    }
  }

  $scope.delete = function () {
    dialogService.confirmDialog({ prompt: 'enterprise.delete' }, function () {
      var EnterpriseSchoolEndpoint = QueryUtils.endpoint('/practiceEnterprise/' + $scope.enterprise.enterpriseSchoolId);
      var enterpriseSchoolEndpoint = new EnterpriseSchoolEndpoint();
      enterpriseSchoolEndpoint.$delete().then(function () {
        message.info('main.messages.delete.success');
        $location.url('/practice/enterprise/');
      }).catch(angular.noop);
    });
  };
  
  loadEnterprise(id);

  $scope.update = function () {
    FormUtils.withValidForm($scope.enterpriseForm, function() {
        $scope.enterprise.$save().then(function (response) {
          message.info('main.messages.create.success');
          $location.url('/practice/enterprise/' + response.id + '/edit?_noback');
        }).catch(angular.noop);
    });
  };
}])
.directive('stringToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        return '' + value;
      });
      ngModel.$formatters.push(function(value) {
        return parseFloat(value);
      });
    }
  };
});