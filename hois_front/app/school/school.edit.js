'use strict';

angular.module('hitsaOis').controller('SchoolEditController', ['$scope', '$route', 'School', '$location', 'dialogService', 'message', 'QueryUtils', 'oisFileService', 'Classifier', 'classifierAutocomplete',

  function ($scope, $route, School, $location, dialogService, message, QueryUtils, oisFileService, Classifier, classifierAutocomplete) {
    var id = $route.current.params.id;

    function afterLoad() {
      if($scope.school.logo) {
        $scope.school.imageUrl = 'data:' + $scope.school.logo.ftype + ';base64,' + $scope.school.logo.fdata;
      } else {
        $scope.school.imageUrl = '?' + new Date().getTime();
      }
      if($scope.school.ehisSchool) {
        Classifier.get($scope.school.ehisSchool).$promise.then(function(result) {
          $scope.school._ehisSchool = result;
        });
      }
    }

    var baseUrl = '/school';
    var Endpoint = QueryUtils.endpoint(baseUrl);
    if(id) {
      $scope.school = Endpoint.get({id: id});
      $scope.school.$promise.then(afterLoad);
    }else{
      $scope.school = new Endpoint();
      afterLoad();
    }

    function withLogo(afterLogoLoad) {
      if($scope.school.deleteCurrentLogo) {
        $scope.school.logo = null;
        afterLogoLoad();
      } else if($scope.logoFiles[0]) {
        $scope.school.logo = oisFileService.getFromLfFile($scope.logoFiles[0], afterLogoLoad);
      } else {
        afterLogoLoad();
      }
    }

    $scope.update = function() {
      $scope.schoolForm.$setSubmitted();
      if(!$scope.schoolForm.$valid) {
        message.error("main.messages.form-has-errors");
        return;
      }

      var msg = $scope.school.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
        $scope.logoFileApi.removeAll();
        afterLoad();
      }

      withLogo(function() {
        if($scope.school.id) {
          $scope.school.$update().then(afterSave);
        } else {
          $scope.school.$save().then(afterSave);
        }
      });
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'school.deleteconfirm'}, function() {
        $scope.school.$delete().then(function() {
          $location.path(baseUrl);
        });
      });
    };

    // ehis school autocomplete
    $scope.querySearch = function(queryText) {
      return classifierAutocomplete.searchByName(queryText, 'EHIS_KOOL');
    };

    $scope.setEhisSchool = function() {
      var ehisSchool = $scope.school._ehisSchool;
      if(ehisSchool) {
        $scope.school.ehisSchool = ehisSchool.code;
        $scope.school.nameEt = ehisSchool.nameEt;
        $scope.school.ehisId = ehisSchool.value;
        $scope.school.regNr = ehisSchool.value2;
      } else {
        $scope.school.ehisSchool = undefined;
        $scope.school.nameEt = undefined;
        $scope.school.ehisId = undefined;
        $scope.school.regNr = undefined;
      }
    };
  }
]);
