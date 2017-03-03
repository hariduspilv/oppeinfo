'use strict';

angular.module('hitsaOis').controller('DirectiveSearchController', ['$q', '$scope', 'Classifier', 'QueryUtils', function ($q, $scope, Classifier, QueryUtils) {
  var clMapper = Classifier.valuemapper({type: 'KASKKIRI', status: 'KASKKIRI_STAATUS'});
  QueryUtils.createQueryForm($scope, '/directives', {order: 'headline'}, clMapper.objectmapper);

  $q.all(clMapper.promises).then($scope.loadData);
}]);
