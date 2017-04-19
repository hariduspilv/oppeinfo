'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationListController', function ($scope, QueryUtils, Classifier, dialogService, oisFileService, config) {
  var clMapper = Classifier.valuemapper({status: 'SAIS_AVALDUSESTAATUS'});
  QueryUtils.createQueryForm($scope, '/saisApplications', {order: 'applicationNr'}, clMapper.objectmapper);
  $scope.loadData();

  QueryUtils.endpoint('/autocomplete/saisAdmissionCodes').query(function(result) {
    $scope.saisAdmissionCodes = result;
  });

  function showImportResultDialog(file) {
    dialogService.showDialog('reception/reception.saisApplication.import.result.dialog.html', function(dialogScope) {
      QueryUtils.endpoint('/saisApplications/importCsv').save({file: file}, function(result) {
        dialogScope.result = result;
      });
    }, null, null, {clickOutsideToClose: false});
  }

  $scope.importFromCsvFile = function() {
    dialogService.showDialog('reception/reception.saisApplication.import.csv.file.dialog.html', null, function(submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function(file) {
        dialogService.hide();
        showImportResultDialog(file);
      });
    });
  };

  $scope.csvSampleFileUrl = config.apiUrl + '/saisApplications/sample.csv';
  $scope.classifiersFileUrl = config.apiUrl + '/saisApplications/classifiers.csv';
});
