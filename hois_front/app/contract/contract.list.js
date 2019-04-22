'use strict';

angular.module('hitsaOis').controller('ContractListController', function ($scope, $route, QueryUtils, Classifier, $q, dialogService, $location) {
  $scope.auth = $route.current.locals.auth;
  var clMapper = Classifier.valuemapper({ status: 'LEPING_STAATUS' });
  QueryUtils.createQueryForm($scope, '/contracts', {order: 'student_person.lastname,student_person.firstname'}, clMapper.objectmapper);

  // Do not show 'notFound' message
  if ($scope.auth.isStudent()) {
    $scope.afterLoadData = function (resultData) {
      $scope.tabledata.content = resultData.content;
      $scope.tabledata.totalElements = resultData.totalElements;
      clMapper.objectmapper(resultData.content);
    };
  }
  
  $scope.sendToEkis = function() {
    $location.path("/practice/studentgroup/ekis").search({
      "":"_menu",
      studentGroup: $scope.criteria.studentGroup,
      studentName: $scope.criteria.studentName
    });
  };

  $scope.directiveControllers = [];
  var clearCriteria = $scope.clearCriteria;
  $scope.clearCriteria = function () {
    clearCriteria();
    $scope.directiveControllers.forEach(function (c) {
      c.clear();
    });
  };

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
