'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/studentrepresentatives/applications', {
      templateUrl: 'views/student/representative.application.list.html',
      controller: 'StudentRepresentativeApplicationSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    }).when('/studentrepresentatives/applications/new', {
      templateUrl: 'views/student/representative.application.create.html',
      controller: 'StudentRepresentativeApplicationCreateController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('StudentRepresentativeApplicationSearchController', ['$mdDialog', '$q', '$scope', 'dialogService', 'message', 'Classifier', 'DataUtils', 'QueryUtils',
  function ($mdDialog, $q, $scope, dialogService, message, Classifier, DataUtils, QueryUtils) {
    var clMapper = Classifier.valuemapper({relation: 'OPPURESINDAJA', status: 'AVALDUS_ESINDAJA_STAATUS'});
    QueryUtils.createQueryForm($scope, '/studentrepresentatives/applications', {order: 'student.person.lastname,student.person.firstname', status: 'AVALDUS_ESINDAJA_STAATUS_E'}, function(rows) {
      for(var row_no = 0, row_cnt = rows.length;row_no < row_cnt;row_no++) {
        var row = rows[row_no];
        clMapper.objectmapper(row);
        DataUtils.convertStringToDates(row, ['inserted']);
      }
    });

    var refreshApplications = $scope.loadData;

    $scope.accept = function(row) {
      dialogService.confirmDialog({prompt: 'student.representative.application.confirm'}, function() {
        QueryUtils.endpoint('/studentrepresentatives/applications/accept').update({id: row.id, version: row.version}).$promise.then(function() {
          refreshApplications();
        });
      });
    };

    $scope.decline = function(row) {
      $mdDialog.show({
        controller: function($scope) {
          $scope.record = {id: row.id, version: row.version};
          $scope.decline = function() {
            $scope.studentRepresentativeApplicationDeclineForm.$setSubmitted();
            if(!$scope.studentRepresentativeApplicationDeclineForm.$valid) {
              message.error('main.messages.form-has-errors');
              return;
            }

            var data = $scope.record;
            QueryUtils.endpoint('/studentrepresentatives/applications/decline').update(data).$promise.then(function() {
              $mdDialog.hide();
              refreshApplications();
            });
          };
          $scope.cancel = $mdDialog.hide;
        },
        templateUrl: 'views/student/representative.application.decline.dialog.html',
        clickOutsideToClose: true
      });
    };

    $q.all(clMapper.promises).then($scope.loadData);
  }
]).controller('StudentRepresentativeApplicationCreateController', ['$location', '$scope', 'message', 'QueryUtils',
  function ($location, $scope, message, QueryUtils) {
    $scope.record = {};

    $scope.lookupPerson = function(response) {
      $scope.record.firstname = response.firstname;
      $scope.record.lastname = response.lastname;
    };

    $scope.apply = function() {
      $scope.studentRepresentativeApplicationCreateForm.$setSubmitted();
      if(!$scope.studentRepresentativeApplicationCreateForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }

      QueryUtils.endpoint('/studentrepresentatives/applications').save($scope.record).$promise.then(function() {
        message.info('student.representative.application.applied');
        $location.path('#/');
      });
    };
  }
]);
