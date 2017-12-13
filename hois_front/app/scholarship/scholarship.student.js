'use strict';

angular.module('hitsaOis').controller('StudentScholarshipController', ['dialogService', 'Classifier', '$scope', '$location', 'message', 'QueryUtils', '$route', 'DataUtils', 'ArrayUtils', '$q',
  function (dialogService, Classifier, $scope, $location, message, QueryUtils, $route, DataUtils, ArrayUtils, $q) {
    var baseUrl = '/scholarships';
    var clMapper = Classifier.valuemapper({
      type: 'STIPTOETUS', status: 'STIPTOETUS_STAATUS'
    });
    $q.all(clMapper.promises).then(function () {
      var available = QueryUtils.endpoint(baseUrl + '/availableStipends').query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.possibleStipends = result;
      }).$promise;
      var student = QueryUtils.endpoint(baseUrl + '/studentStipends').query({}, function (result) {
        clMapper.objectmapper(result);
        $scope.studentStipends = result;
      }).$promise;
      $q.all([available, student]).then(function () {
        $scope.possibleStipends.forEach(function (possible) {
          var match = $scope.studentStipends.find(function (application) {
            return application.id === possible.id;
          });
          if (match) {
            possible.canApply = false;
          } else {
            possible.canApply = true;
          }
        });
      });
    });
  }
]);
