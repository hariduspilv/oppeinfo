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
    }

    $scope.getStateCurriculums = function () {
      $scope.promise = StateCurriculum.query($scope.query, success).$promise;
    };

    $scope.getStateCurriculums();

    function getListOfEkrLevels() {
      Classifier.get('EKR').$promise.then(function(response) {
        $scope.listOfEkrLevels = response.children;
      });
    }
    getListOfEkrLevels();

    $scope.filteredOutStatuses = [{code: 'OPPEKAVA_STAATUS_M'}];
  });
