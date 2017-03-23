'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationListController', function ($scope, QueryUtils, Classifier) {
  var clMapper = Classifier.valuemapper({status: 'SAIS_AVALDUSESTAATUS'});
  QueryUtils.createQueryForm($scope, '/saisApplications', {order: 'applicationNr'}, clMapper.objectmapper);
  $scope.loadData();
});
