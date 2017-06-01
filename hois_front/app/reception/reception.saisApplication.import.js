'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationImportController', function ($scope, Classifier, QueryUtils, $mdDialog, $q, message) {
  $scope.statusList = Classifier.queryForDropdown({mainClassCode: 'SAIS_AVALDUSESTAATUS', order: 'code'});
  
  $q.all([$scope.statusList.$promise, $scope.statusList.$promise]).then(function() {
    Classifier.setSelectedCodes($scope.statusList, ["SAIS_AVALDUSESTAATUS_T"]);
  });
  
  QueryUtils.endpoint('/autocomplete/saisAdmissionCodes').query(function(result) {
    $scope.saisAdmissionCodes = result;
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
        $mdDialog.hide();
      }).catch(function() {
        $mdDialog.hide();
      });
      var parentEl = angular.element(document.body);
      $mdDialog.show({
        parent: parentEl,
        template: 
            '<md-dialog style="background-color:transparent;box-shadow:none">' +
            '<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait" style="height:120px;">'+
            '<md-progress-circular class="md-hue-2" md-diameter="80px" class="loader"></md-progress-circular>'+
            '</div>' +
            '</md-dialog>'
        });
    } else {
      message.error('reception.application.form-has-errors');
    }
  };
  
});
