'use strict';

angular.module('hitsaOis').config(function ($routeProvider) {
  $routeProvider
    .when('/students', {
      templateUrl: 'views/student.list.html',
      controller: 'StudentSearchController',
      controllerAs: 'controller'
    });
}).controller('StudentSearchController', function ($q, $scope, Classifier, QueryUtils) {
  $scope.studyFormDefs = Classifier.query({mainClassCode: 'OPPEVORM'});
  $scope.statusDefs = Classifier.query({mainClassCode: 'STATUS'});

  QueryUtils.createQueryForm($scope, '/students', {}, function() {
    var rows = $scope.tabledata.content;
    for(var rowNo = 0; rowNo < rows.length; rowNo++) {
      var row = rows[rowNo];
      row.studyForm = $scope.studyFormDefs[row.studyForm];
      row.status = $scope.statusDefs[row.status];
    }
  });

  $q.all([$scope.studyFormDefs.$promise, $scope.statusDef.$promise]).then(function() {
    $scope.studyFormDefs = Classifier.toMap($scope.studyFormDefs.content);
    $scope.statusDefs = Classifier.toMap($scope.statusDefs.content);
    $scope.loadData();
  });
});
