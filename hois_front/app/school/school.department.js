'use strict';

angular.module('hitsaOis').controller('SchoolDepartmentEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',

  function ($location, $route, $scope, dialogService, message, DataUtils, QueryUtils) {
    var id = $route.current.params.id;
    function afterLoad() {
      DataUtils.convertStringToDates($scope.schoolDepartment, ['validFrom', 'validThru']);
    }
    var Endpoint = QueryUtils.endpoint('/school/departments');
    if(id) {
      $scope.schoolDepartment = Endpoint.get({id: id});
      $scope.schoolDepartment.$promise.then(afterLoad);
    } else {
      // new department
      var now = new Date();
      now.setHours(0, 0, 0, 0);
      $scope.schoolDepartment = new Endpoint({validFrom: now, validThru: null});
    }

    $scope.update = function() {
      $scope.schoolDepartmentForm.$setSubmitted();
      if(!$scope.schoolDepartmentForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.schoolDepartment.id ? 'main.messages.update.success' : 'main.messages.create.success';
      function afterSave() {
        message.info(msg);
        afterLoad();
      }
      if($scope.schoolDepartment.id) {
        $scope.schoolDepartment.$update().then(afterSave);
      } else {
        $scope.schoolDepartment.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'school.department.deleteconfirm'}, function() {
        $scope.schoolDepartment.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/school/departments');
        });
      });
    };
}]).controller('SchoolDepartmentListController', ['$scope', 'DataUtils', 'QueryUtils', function ($scope, DataUtils, QueryUtils) {
  var postLoad = function() {
    var addChildren = function(rows, indent, children) {
      for(var row_no = 0, row_cnt = children.length;row_no < row_cnt;row_no++) {
        var row = children[row_no];
        row.indent = indent;
        rows.push(row);
        addChildren(rows, indent+1, row.children || []);
      }
    };

    var rows = [];
    // make hierarchical table flat
    addChildren(rows, 0, $scope.tabledata.content);
    $scope.tabledata.content = rows;

    var now = new Date();
    now.setUTCHours(0, 0, 0, 0);
    for(var row_no = 0, row_cnt = rows.length;row_no < row_cnt;row_no++) {
      var row = rows[row_no];
      DataUtils.convertStringToDates(row, ['validFrom', 'validThru']);
      row.isValid = row.validFrom <= now && (!row.validThru || row.validThru >= now);
    }
  };

  QueryUtils.createQueryForm($scope, '/school/departments', {order: $scope.currentLanguage()==='en' ? 'nameEn' : 'nameEt'}, postLoad);
  $scope.loadData();
}]);
