'use strict';

angular.module('hitsaOis').controller('SubjectListController', ['$q', '$scope', 'Classifier', 'QueryUtils', function ($q, $scope, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({status: 'AINESTAATUS', assessment: 'HINDAMISVIIS', languages: 'OPPEKEEL'});
  QueryUtils.createQueryForm($scope, '/subject', {order: $scope.currentLanguage() === 'en' ? 'nameEn' : 'nameEt'}, clMapper.objectmapper);

  // initialize selections on form
  QueryUtils.endpoint('/subject/initSearchFormData').get(function(result) {
    angular.extend($scope, result.toJSON());
  });

  $q.all(clMapper.promises).then($scope.loadData);

  $scope.subjectLanguages = function(row) {
    return row.map(function (it) {
      return $scope.currentLanguageNameField(it);
    }).join(', ');
  };
}]);
