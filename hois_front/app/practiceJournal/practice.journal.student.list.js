'use strict';

angular.module('hitsaOis').controller('PracticeJournalStudentListController', function ($scope, $route, QueryUtils) {
  $scope.auth = $route.current.locals.auth;
  QueryUtils.createQueryForm($scope, '/practiceJournals');
  $scope.loadData();
});
