'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationImportController', function ($scope, Classifier, QueryUtils, message) {
  $scope.statusList = Classifier.queryForDropdown({mainClassCode: 'SAIS_AVALDUSESTAATUS', order: 'code'});
  $scope.statusList.$promise.then(function() {
    Classifier.setSelectedCodes($scope.statusList, ['SAIS_AVALDUSESTAATUS_T']);
  });

  var clMapper = Classifier.valuemapper({applicationStatus: 'SAIS_AVALDUSESTAATUS'});
  $scope.importApplications = function() {
    if($scope.applicationImportForm.$valid) {
      var selectedCodes = Classifier.getSelectedCodes($scope.statusList);
      if(selectedCodes.length > 1) {
        $scope.criteria.status = selectedCodes;
      }
      QueryUtils.endpoint('/saisApplications/importSais').save($scope.criteria).$promise.then(function(result) {
        clMapper.objectmapper(result.successful);
        $scope.failed = result.failed;
        $scope.successful = result.successful;
        $scope.result = true;
        message.info('reception.application.importFinished');
      });
    } else {
      message.error('reception.application.form-has-errors');
    }
  };
  
});
