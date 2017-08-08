'use strict';

angular.module('hitsaOis').controller('ContractListController', function ($scope, $route, QueryUtils, DataUtils, Classifier, $q, dialogService, $location) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
  QueryUtils.createQueryForm($scope, '/contracts', {order: 'student_person.lastname,student_person.firstname'}, clMapper.objectmapper);
  $q.all(clMapper.promises).then($scope.loadData);

  $scope.newContract = function () {
    if ($scope.auth.school.higher && $scope.auth.school.vocational) {
      dialogService.showDialog('contract/vocational.higher.select.dialog.html', function () {
      }, function (submittedDialogScope) {
        if (submittedDialogScope.higher === true) {
          $location.path("/contracts/new").search({ higher: true });
        } else {
          $location.path("/contracts/new");
        }
      });
    } else {
      $location.path("/contracts/new");
    }
  };
});
