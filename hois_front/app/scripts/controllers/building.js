'use strict';

angular.module('hitsaOis').config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
  $routeProvider
    .when('/buildings/new', {
      templateUrl: 'views/building.edit.html',
      controller: 'BuildingEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/buildings/:id', {
      templateUrl: 'views/building.edit.html',
      controller: 'BuildingEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/rooms/new', {
      templateUrl: 'views/room.edit.html',
      controller: 'RoomEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/rooms/search', {
      templateUrl: 'views/room.search.html',
      controller: 'RoomSearchController',
      controllerAs: 'controller',
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    })
    .when('/rooms/:id', {
      templateUrl: 'views/room.edit.html',
      controller: 'RoomEditController',
      controllerAs: 'controller',
      resolve: { translationLoaded: function($translate) { return $translate.onReady(); } },
      data: {
        authorizedRoles: [USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_A]
      }
    });
}]).controller('RoomSearchController', ['$scope', 'Classifier', 'QueryUtils',
  function ($scope, Classifier, QueryUtils) {
    $scope.equipmentDefs = Classifier.queryForDropdown({mainClassCode: 'SEADMED'});

    QueryUtils.createQueryForm($scope, '/rooms', {order: 'name'}, function(rows) {
      // set up equipment column
      rows.forEach(function(room) {
        room.equipment = (room.roomEquipment || []).map(function(e) { return {equipment: $scope.equipmentDefs[e.equipment], equipmentCount: e.equipmentCount}; });
      });
    });

    $scope.equipmentDefs.$promise.then(function() {
      $scope.equipmentDefs = Classifier.toMap($scope.equipmentDefs);
      $scope.loadData();
    });
  }
]).controller('BuildingEditController', ['dialogService', 'message', '$location', '$route', '$scope', 'QueryUtils',
  function (dialogService, message, $location, $route, $scope, QueryUtils) {
    var Endpoint = QueryUtils.endpoint('/buildings');
    var id = $route.current.params.id;
    if(id) {
      $scope.record = Endpoint.get({id: id});
    } else {
      // new building
      $scope.record = new Endpoint({});
    }

    $scope.update = function() {
      $scope.buildingForm.$setSubmitted();
      if(!$scope.buildingForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
      var afterSave = function() {
        message.info(msg);
      };
      if($scope.record.id) {
        $scope.record.$update().then(afterSave);
      } else {
        $scope.record.$save().then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'building.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/rooms/search');
        });
      });
    };
  }
]).controller('RoomEditController', ['dialogService', 'message', '$location', '$route', '$scope', 'Classifier', 'QueryUtils',
  function (dialogService, message, $location, $route, $scope, Classifier, QueryUtils) {
    var Endpoint = QueryUtils.endpoint('/rooms');
    var id = $route.current.params.id;

    $scope.equipmentDefs = Classifier.queryForDropdown({mainClassCode: 'SEADMED'});
    $scope.formState = {};

    function afterLoad(record) {
      $scope.formState.roomEquipment = (record.roomEquipment || []).map(function(e) { return {equipment: $scope.equipmentDefs[e.equipment], equipmentCount: e.equipmentCount}; });
    }

    $scope.equipmentDefs.$promise.then(function() {
      $scope.equipmentDefs = Classifier.toMap($scope.equipmentDefs);
      if(id) {
        $scope.record = Endpoint.get({id: id}, afterLoad);
      } else {
        // new room
        $scope.record = new Endpoint({roomEquipment: []});
        afterLoad($scope.record);
      }
    });

    $scope.addEquipment = function() {
      if($scope.formState.roomEquipment.some(function(e) { return e.equipment.code === $scope.formState.newEquipment.code; })) {
        message.error('room.duplicateequipment');
        return;
      }
      $scope.formState.roomEquipment.push({equipment: $scope.formState.newEquipment, equipmentCount: 1});
      $scope.formState.newEquipment = null;
    };

    $scope.deleteEquipment = function(code) {
      var rows = $scope.formState.roomEquipment;
      for(var row_no = 0, row_cnt = rows.length;row_no < row_cnt;row_no++) {
        if(rows[row_no].equipment.code === code) {
          rows.splice(row_no, 1);
          break;
        }
      }
    };

    $scope.update = function() {
      $scope.roomForm.$setSubmitted();
      if(!$scope.roomForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var msg = $scope.record.id ? 'main.messages.update.success' : 'main.messages.create.success';
      var afterSave = function() {
        message.info(msg);
      };
      $scope.record.roomEquipment = $scope.formState.roomEquipment.map(function(e) { return {equipment: e.equipment.code, equipmentCount: e.equipmentCount}; });
      if($scope.record.id) {
        $scope.record.$update().then(afterLoad).then(afterSave);
      } else {
        $scope.record.$save().then(afterLoad).then(afterSave);
      }
    };

    $scope.delete = function() {
      dialogService.confirmDialog({prompt: 'room.deleteconfirm'}, function() {
        $scope.record.$delete().then(function() {
          message.info('main.messages.delete.success');
          $location.path('/rooms/search');
        });
      });
    };
  }
]);
