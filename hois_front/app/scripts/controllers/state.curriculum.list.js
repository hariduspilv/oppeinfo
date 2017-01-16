'use strict';

angular.module('hitsaOis')
  .controller('StateCurriculumListController', function (QueryUtils, $scope, StateCurriculum, $translate, Classifier) {

    $scope.query = {
      order: 'id',
      size: 10,
      page: 1,
      name: ''
    };

    $scope.clearInputs = function() {
      QueryUtils.clearQueryParams($scope.query);
    };

    function success(response) {
      $scope.stateCurriculums = response.content;
      $scope.stateCurriculums.count = response.totalElements;
      //console.log("stateCurriculums:");
      //console.log($scope.stateCurriculums);
    }

    $scope.getStateCurriculums = function () {
      // passing them as date objects causes error (described in back end StateCurriculumSearchCommand class)
      $scope.query.validFromMillis = $scope.query.validFrom ? $scope.query.validFrom.getTime() : null;
      $scope.query.validThruMillis = $scope.query.validThru ? $scope.query.validThru.getTime() : null;
      //console.log("query");
      //console.log($scope.query);
      $scope.promise = StateCurriculum.query($scope.query, success).$promise;
    };

    $scope.getStateCurriculums();

    function getStatuses() {
      return Classifier.get('OPPEKAVA_STAATUS').$promise.then(function(response) {
        $scope.statuses = response.children;
        });
    }
    getStatuses();


    $scope.getClassifierValds = function() {
      var query = {
        mainClassCode: 'ISCED_VALD'
      };
      Classifier.queryForDropdown(query).$promise.then(function(response) {
        $scope.listOfClassifierValds = response;
      });
    };
    $scope.getClassifierValds();

    $scope.resetAndGetClassifierSuuns = function() {
      $scope.query.iscedClassCode = undefined;
      $scope.query.iscedClass = undefined;
      $scope.query.iscedSuun = undefined;
      $scope.getClassifierSuuns();
    };

    $scope.getClassifierSuuns = function() {
      if($scope.query && $scope.query.iscedVald) {
        Classifier.getChildren($scope.query.iscedVald).$promise.then(function(response) {
          $scope.listOfClassifierSuuns = response;
        });
      }
    };

    $scope.getClassifierGroups = function() {
      if($scope.query && $scope.query.iscedSuun) {
        Classifier.getChildren($scope.query.iscedSuun).$promise.then(function(response) {
          $scope.listOfClassifierGroups = response;
        });
      }
    };

    function getListOfEkrLevels() {
      Classifier.get('EKR').$promise.then(function(response) {
        $scope.listOfEkrLevels = response.children;
      });
    }
    getListOfEkrLevels();
  });
