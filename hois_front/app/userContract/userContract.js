(function() {
  'use strict';

  angular
    .module('hitsaOis')
    .controller('UserContractSettingsController', UserContractSettingsController)
    .controller('UserContractStudentsController', UserContractStudentsController);

  UserContractSettingsController.$inject = ['$scope', 'QueryUtils', 'message'];
  function UserContractSettingsController($scope, QueryUtils, message) {
    $scope.currentNavItem = 'settings';
    $scope.canEdit = $scope.isAuthorized('ROLE_OIGUS_M_TEEMAOIGUS_TINGIMUS');
    var baseUrl = "/userContract/admin";

    $scope.save = save;
    $scope.data = QueryUtils.endpoint(baseUrl).get();

    function save() {
      $scope.data.$update().then(message.updateSuccess);
    }
  }

  UserContractStudentsController.$inject = ['$scope', 'QueryUtils', 'dialogService'];
  function UserContractStudentsController($scope, QueryUtils, dialogService) {
    $scope.currentNavItem = 'users';
    var baseUrl = "/userContract/users";
    
    $scope.openContract = openContract;

    QueryUtils.createQueryForm($scope, baseUrl);
    $scope.loadData();

    function openContract(row) {
      dialogService.showDialog('userContract/userContract.users.dialog.html', function (dialogScope) {
        dialogScope.user = row;
        dialogScope.contract = QueryUtils.endpoint(baseUrl + "/" + row.id).get();
      });
    }
  }
})();