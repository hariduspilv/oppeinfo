'use strict';

angular.module('hitsaOis').controller('SubjectListController', function (config, $resource, $scope, QueryUtils) {
  QueryUtils.createQueryForm($scope, '/subject', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'});
  // initialize selections on form
  $resource(config.apiUrl+'/subject/initSearchFormData').get().$promise.then(function(result) {
    angular.extend($scope, result.toJSON());
  });
  $scope.loadData();

  $scope.languages = function(lang, row){
    return row.map(function (it) {
      return lang==='en' ? (it.nameEn || it.nameEt) : it.nameEt;
    }).join(', ');
  };
});
