'use strict';

angular.module('hitsaOis').controller('SchoolListController', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/school', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
  $scope.loadData();
});
