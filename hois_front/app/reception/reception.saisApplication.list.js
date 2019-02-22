'use strict';

angular.module('hitsaOis').controller('ReceptionSaisApplicationListController', 
function ($scope, QueryUtils, Classifier, dialogService, oisFileService, config, USER_ROLES, AuthService) {
  $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_VASTUVOTT);
  var clMapper = Classifier.valuemapper({ status: 'SAIS_AVALDUSESTAATUS' });
  QueryUtils.createQueryForm($scope, '/saisApplications', { order: 'applicationNr' }, clMapper.objectmapper);
  $scope.loadData();

  function doImport(file) {
    QueryUtils.endpoint('/saisApplications/importCsv').save({ file: file }, function (result) {
      dialogService.showDialog('reception/reception.saisApplication.import.result.dialog.html', function (dialogScope) {
        dialogScope.result = result;
      }, null, function() {
        $scope.loadData();
      }, { clickOutsideToClose: false });
    });
  }

  $scope.importFromCsvFile = function () {
    dialogService.showDialog('reception/reception.saisApplication.import.csv.file.dialog.html', null, function (submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function (file) {
        dialogService.hide();
        doImport(file);
      });
    }); 
  };

  $scope.csvSampleFileUrl = config.apiUrl + '/saisApplications/sample.csv';
  $scope.classifiersFileUrl = config.apiUrl + '/saisApplications/classifiers.csv';
});
