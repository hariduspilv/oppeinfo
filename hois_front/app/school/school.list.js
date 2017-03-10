'use strict';

angular.module('hitsaOis').controller('SchoolListController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/school', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
  $scope.loadData();
}]);
