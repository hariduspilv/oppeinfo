'use strict';

angular.module('hitsaOis').controller('SchoolDepartmentEditController',

  function ($location, message, $route, $scope, $translate, QueryUtils) {
    var id = $route.current.params.id;
    var postload = function() {
      // TODO utility function: convertDates($scope.schoolDepartment, ['validFrom', 'validThru'])
      if($scope.schoolDepartment.validFrom) {
        $scope.schoolDepartment.validFrom = new Date(Date.parse($scope.schoolDepartment.validFrom));
      }
      if($scope.schoolDepartment.validThru) {
        $scope.schoolDepartment.validThru = new Date(Date.parse($scope.schoolDepartment.validThru));
      }
      if($scope.schoolDepartment.parentSchoolDepartment) {
        $scope.schoolDepartment.parentSchoolDepartmentId = $scope.schoolDepartment.parentSchoolDepartment.id || '';
      }
    };
    $scope.departments = QueryUtils.endpoint('/autocomplete/schooldepartments').get({excludedId: id, lang: $translate.use().toUpperCase()});
    var Endpoint = QueryUtils.endpoint('/school/departments');
    if(id) {
      $scope.schoolDepartment = Endpoint.get({id: id});
      $scope.schoolDepartment.$promise.then(postload);
    } else {
      // new department
      var now = new Date();
      now.setUTCHours(0, 0, 0, 0);
      $scope.schoolDepartment = new Endpoint({validFrom: now, validThru: null});
    }

    $scope.update = function() {
      if(!$scope.schoolDepartmentForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      if($scope.schoolDepartment.id) {
        $scope.schoolDepartment.$update().then(postload).then(function() {
          message.info('main.messages.update.success');
          $location.path('/school/departments');
        });
      } else {
        $scope.schoolDepartment.$save().then(postload).then(function() {
          message.info('main.messages.create.success');
          $location.path('/school/departments');
        });
      }
    };

    $scope.delete = function() {
      $scope.schoolDepartment.$delete().then(function() {
        message.info('main.messages.delete.success');
        $location.path('/school/departments');
      });
    };
  }
);

angular.module('hitsaOis').controller('SchoolDepartmentListController', function ($scope, QueryUtils) {
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
      row.validFrom = new Date(Date.parse(row.validFrom));
      row.validThru = row.validThru ? new Date(Date.parse(row.validThru)) : null;
      row.isValid = row.validFrom <= now && (!row.validThru || row.validThru >= now);
    }
  };

  QueryUtils.createQueryForm($scope, '/school/departments', {order: $scope.currentLanguage()==='en' ? 'nameEn' : 'nameEt'}, postLoad);
  $scope.loadData();
});
