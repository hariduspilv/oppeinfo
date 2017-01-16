'use strict';


angular.module('hitsaOis').controller('SchoolEditController', function ($scope, $route, School, $location, message, QueryUtils, oisFileService, Classifier, classifierAutocomplete) {

  var id = $route.current.params.id;

  function postLoad() {
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
    $scope.school.$promise.then(postLoad);
  }else{
    $scope.school = new Endpoint();
    postLoad();
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

  function createSchool() {
    $scope.school.$save().then(function() {
      message.info('main.messages.create.success');
      $scope.logoFileApi.removeAll();
      postLoad();
    });
  }

  function updateSchool() {
    $scope.school.$update().then(function() {
      message.info('main.messages.update.success');
      $scope.logoFileApi.removeAll();
      postLoad();
    });
  }

  $scope.update = function() {
    $scope.schoolForm.$setSubmitted();
    if(!$scope.schoolForm.$valid) {
      message.error("main.messages.form-has-errors");
      return;
    }
    withLogo($scope.school.id ? updateSchool : createSchool);
  };

  $scope.delete = function() {
    $scope.school.$delete().then(function() {
      $location.path(baseUrl);
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
});
