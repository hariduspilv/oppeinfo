'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/generalmessages', {
      templateUrl: 'views/general.message.list.html',
      controller: 'GeneralMessageSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/generalmessages/new', {
      templateUrl: 'views/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/generalmessages/:id', {
      templateUrl: 'views/general.message.edit.html',
      controller: 'GeneralMessageEditController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('GeneralMessageSearchController', ['$scope', '$sessionStorage', 'Classifier', 'DataUtils', 'QueryUtils', function ($scope, $sessionStorage, Classifier, DataUtils, QueryUtils) {
  $scope.roleDefs = Classifier.queryForDropdown({mainClassCode: 'ROLL'});
  $scope.fromStorage = function(key) {
    var criteria = JSON.parse($sessionStorage[key] || '{}');
    DataUtils.convertStringToDates(criteria, ['validFrom', 'validThru']);
    return criteria;
  };

  var now = new Date();
  now.setHours(0, 0, 0, 0);
  QueryUtils.createQueryForm($scope, '/generalmessages', {validFrom: now, order: 'validFrom'}, function(rows) {
    var roleMapper = function(i) { return $scope.roleDefs[i]; };
    for(var rowNo = 0; rowNo < rows.length; rowNo++) {
      var row = rows[rowNo];
      DataUtils.convertStringToDates(row, ['validFrom', 'validThru']);
      row.targets = (row.targets || []).map(roleMapper);
    }
  });

  $scope.roleDefs.$promise.then(function() {
    $scope.roleDefs = Classifier.toMap($scope.roleDefs);
    $scope.loadData();
  });
}]).controller('GeneralMessageEditController', ['$location', '$route', '$scope', 'message', 'Classifier', 'DataUtils', 'QueryUtils', function ($location, $route, $scope, message, Classifier, DataUtils, QueryUtils) {
  var baseUrl = '/generalmessages';

  var afterLoad = function() {
    DataUtils.convertStringToDates($scope.record, ['validFrom', 'validThru']);
    Classifier.setSelectedCodes($scope.roleDefs, $scope.record.targets || []);
  };

  $scope.roleDefs = Classifier.queryForDropdown({mainClassCode: 'ROLL'}, function() {
    $scope.roleDefs = $scope.roleDefs.filter(function(i) { return i.code !== 'ROLL_H'; });

    var Endpoint = QueryUtils.endpoint(baseUrl);
    var id = $route.current.params.id;
    if(id) {
      $scope.record = Endpoint.get({id: id}, afterLoad);
    } else {
      // new general message
      var now = new Date();
      now.setHours(0, 0, 0, 0);
      $scope.record = new Endpoint({validFrom: now, validThru: null});
      afterLoad();
    }
  });

  $scope.isFormValid = function() {
    return $scope.generalMessageForm.$valid;
  };

  $scope.beforeSave = function() {
    $scope.record.targets = Classifier.getSelectedCodes($scope.roleDefs);
  };

  $scope.update = function() {
    $scope.generalMessageForm.$setSubmitted();
    if(!$scope.isFormValid()) {
      message.error('main.messages.form-has-errors');
      return;
    }

    $scope.beforeSave();

    var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
    var aftersave = function() {
      message.info(msg);
      $location.path(baseUrl);
    };
    if($scope.record.id) {
      $scope.record.$update(afterLoad).then(aftersave);
    }else{
      $scope.record.$save(afterLoad).then(aftersave);
    }
  };

  $scope.delete = function() {
    $scope.record.$delete().then(function() {
      message.info('main.messages.delete.success');
      $location.path(baseUrl);
    });
  };
}]);
