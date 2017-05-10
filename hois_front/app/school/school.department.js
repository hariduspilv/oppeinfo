'use strict';

angular.module('hitsaOis').controller('SchoolDepartmentEditController', ['$location', '$route', '$scope', 'dialogService', 'message', 'DataUtils', 'QueryUtils',

  function ($location, $route, $scope, dialogService, message, DataUtils, QueryUtils) {
    var id = $route.current.params.id;
    var baseUrl = '/school/departments';
    function afterLoad() {
      DataUtils.convertStringToDates($scope.schoolDepartment, ['validFrom', 'validThru']);
    }
    var Endpoint = QueryUtils.endpoint(baseUrl);
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

      if($scope.schoolDepartment.id) {
        $scope.schoolDepartment.$update().then(afterLoad).then(message.updateSuccess);
      } else {
        $scope.schoolDepartment.$save().then(function() {
          message.info('main.messages.create.success');
          $location.url(baseUrl + '/' + $scope.schoolDepartment.id + '/edit');
        });
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'school.department.deleteconfirm'}, function() {
        $scope.schoolDepartment.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.url('/school/departments');
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
