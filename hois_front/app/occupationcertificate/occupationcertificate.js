'use strict';

angular.module('hitsaOis').controller('OccupationCertificateImportController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    function curriculumVersionChanged(newValues) {
      // calculate allowed student groups
      $scope.formState.studentGroups = $scope.formState.allStudentGroups.filter(function(it) {
        return !newValues || (it.curriculumVersion && newValues.indexOf(it.curriculumVersion) !== -1);
      });
    }

    $scope.criteria = {};
    $scope.formState = {allStudentGroups: QueryUtils.endpoint('/autocomplete/studentgroups').query({valid: true})};
    $scope.formState.allStudentGroups.$promise.then(function() { curriculumVersionChanged($scope.criteria.curriculumVersion); });
    $scope.$watchCollection('criteria.curriculumVersion', curriculumVersionChanged);

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
