'use strict';

angular.module('hitsaOis').controller('PracticeEnterpriseListController', function ($scope, $route, QueryUtils, dialogService, oisFileService, config, $q, Classifier) {
  $scope.auth = $route.current.locals.auth;
  $scope.csvSampleFileUrl = config.apiUrl + '/practiceEnterprise/sample.csv';
  var clMapper = Classifier.valuemapper({
    ratingCode: 'PR_HINNANG',
  });
  QueryUtils.createQueryForm($scope, '/practiceEnterprise', {enterpriseActive: true}, clMapper.objectmapper);
  $q.all(clMapper.promises).then($scope.loadData);

  $scope.getCurrentColor = function(code, ratingDate) {
    if (code === 'PR_HINNANG_A' && new Date(ratingDate) < new Date()) {
      return '#dadd88';
    } else if (code === 'PR_HINNANG_T' || code === 'PR_HINNANG_A') {
      return '#a4dba7';
    } else if (code === 'PR_HINNANG_E') {
      return '#dd9d9d';
    } else if (code === 'PR_HINNANG_X') {
      return '#dadd88';
    } else if (code === undefined || code === 'PR_HINNANG_P') {
      return '#c9c9c9';
    } else {
      return 'white';
    }
  };

  function doImport(file) {
    QueryUtils.endpoint('/practiceEnterprise/importCsv').save({ file: file }, function (result) {
      dialogService.showDialog('practiceEnterprise/practice.enterprise.csv.result.dialog.html', function (dialogScope) {
        dialogScope.enterprise = result;
      }, null);
    });
  }

  $scope.importEnterprise = function () {
    dialogService.showDialog('practiceEnterprise/practice.enterprise.import.csv.file.dialog.html', null, function (submittedDialogScope) {
      var data = submittedDialogScope.data;
      oisFileService.getFromLfFile(data.file[0], function (file) {
        dialogService.hide();
        doImport(file);
      });
    });
  };

});