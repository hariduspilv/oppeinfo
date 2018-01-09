'use strict';

angular.module('hitsaOis').controller('SchoolViewController', ['$route', '$scope', 'oisFileService', 'Classifier', 'QueryUtils',
  function ($route, $scope, oisFileService, Classifier, QueryUtils) {
    var id = $route.current.params.id;

    $scope.school = QueryUtils.endpoint('/school').get({id: id}, function() {
      if($scope.school.logo) {
        $scope.school.imageUrl = oisFileService.getUrl($scope.school.logo, 'school');
      } else {
        $scope.school.imageUrl = '?' + new Date().getTime();
      }
      if($scope.school.ehisSchool) {
        Classifier.get($scope.school.ehisSchool).$promise.then(function(result) {
          $scope.school.ehisSchool = result.code;
          $scope.school.nameEt = result.nameEt;
          $scope.school.ehisId = result.value;
          $scope.school.regNr = result.value2;
        });
      }
    });
  }
]);
