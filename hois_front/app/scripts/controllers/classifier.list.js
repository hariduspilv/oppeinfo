'use strict';


angular.module('hitsaOis').controller('ClassifierListController', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/classifier/heads', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
  $scope.loadData();
});
