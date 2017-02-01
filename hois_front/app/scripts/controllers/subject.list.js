'use strict';

angular.module('hitsaOis').controller('SubjectListController', ['$scope', 'QueryUtils', function ($scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/subject', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});

  // initialize selections on form
  QueryUtils.endpoint('/subject/initSearchFormData').get(function(result) {
    angular.extend($scope, result.toJSON());
  });

  $scope.loadData();

  $scope.languages = function(lang, row) {
    return row.map(function (it) {
      return lang==='en' ? (it.nameEn || it.nameEt) : it.nameEt;
    }).join(', ');
  };
}]);
