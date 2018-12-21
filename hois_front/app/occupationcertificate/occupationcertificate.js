'use strict';

angular.module('hitsaOis').controller('OccupationCertificateImportController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    function curriculumVersionChanged() {
      $scope.criteria.studentGroups = null;
    }

    $scope.criteria = {};
    $scope.$watchCollection('criteria.curriculumVersion', curriculumVersionChanged);

    $scope.directiveControllers = [];
    var clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      clearCriteria();
      $scope.directiveControllers.forEach(function (c) {
        c.clear();
      });
    };

    $scope.importFromKutseregister = function() {
      $scope.occupationCertificateForm.$setSubmitted();
      if(!$scope.occupationCertificateForm.$valid) {
        message.error('main.messages.form-has-errors');
        return false;
      }

      QueryUtils.endpoint('/occupationcertificates/import').post($scope.criteria).$promise.then(function(response) {
        $scope.results = response;
      });
    };
  }
]);
