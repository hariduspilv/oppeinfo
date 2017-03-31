'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationListController', function ($scope, QueryUtils, Classifier, dialogService, oisFileService) {
  var clMapper = Classifier.valuemapper({status: 'SAIS_AVALDUSESTAATUS'});
  QueryUtils.createQueryForm($scope, '/saisApplications', {order: 'applicationNr'}, clMapper.objectmapper);
  $scope.loadData();

  var SaisAdmissionsEndpoint = QueryUtils.endpoint('/autocomplete/saisAdmissionCodes');
  SaisAdmissionsEndpoint.query({}, function(result) {
    $scope.saisAdmissionCodes = result;
  });

  function showImportResultDialog(file) {
    dialogService.showDialog('reception/reception.saisApplication.import.result.dialog.html', function(dialogScope) {
      QueryUtils.endpoint('/saisApplications/importCsv').save({file: file}, function(result) {
        dialogScope.result = result;
      });
    }, null);
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
});
