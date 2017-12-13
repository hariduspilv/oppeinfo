'use strict';

angular.module('hitsaOis').controller('OccupationCertificateImportController', ['$scope', 'message', 'QueryUtils',
  function ($scope, message, QueryUtils) {

    $scope.criteria = {};
    $scope.formState = {studentGroups: QueryUtils.endpoint('/autocomplete/studentgroups').query({higher: false, valid: true})};
    $scope.formState.studentGroups.$promise.then(function(groups) {
      $scope.formState.studentGroupMap = {};
      for(var i = 0; i < groups.length; i++) {
        var sg = groups[i];
        var cv = sg.curriculumVersion;
        if(cv) {
          var cvgroups = $scope.formState.studentGroupMap[cv];
          if(!cvgroups) {
            cvgroups = [];
            $scope.formState.studentGroupMap[cv] = cvgroups;
          }
          cvgroups.push(sg);
        }
      }
    });

    $scope.$watchCollection('criteria.curriculumVersion', function(newValues, oldValues) {
      // TODO calculate allowed student groups
    });

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
