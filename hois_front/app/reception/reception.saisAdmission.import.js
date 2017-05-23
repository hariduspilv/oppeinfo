'use strict';

angular.module('hitsaOis').controller('ReceptionSaisAdmissionImportController', function ($scope, message, QueryUtils, $mdDialog, Classifier) {
  $scope.successEmpty = true;
  $scope.failEmpty = true;
  $scope.importAdmissions = function() {
    $scope.successEmpty = true;
    $scope.failEmpty = true;
    var clMapper = Classifier.valuemapper({studyForm: 'OPPEVORM', language: 'OPPEKEEL'});
    if($scope.admissionImportForm.$valid) {
      // impordi vahemikus
      QueryUtils.endpoint('/saisAdmissions/saisImport').save($scope.criteria).$promise.then(function(result) {
        clMapper.objectmapper(result.content);
        $scope.success = [];
        $scope.fail = [];
        for (var i = 0; i < result.content.length; i++) { 
          if(result.content[i].failed === true) {
            $scope.fail.push(result.content[i]);
            $scope.failEmpty = false;
          } else {
            $scope.success.push(result.content[i]);
            $scope.successEmpty = false;
          }
        }
        message.info('reception.admission.importFinished');
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
      message.error('main.messages.form-has-errors');
    }
  };
  
});
