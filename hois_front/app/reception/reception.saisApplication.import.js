'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationImportController', function ($scope, Classifier, QueryUtils) {
  $scope.statusList = Classifier.queryForDropdown({mainClassCode: 'SAIS_AVALDUSESTAATUS', order: 'code'});
  
  $scope.importApplications = function() {
    QueryUtils.endpoint('/saisApplications/importSais').save($scope.criteria);
    //console.log(Classifier.getSelectedCodes($scope.statusList));
  };
  
});
