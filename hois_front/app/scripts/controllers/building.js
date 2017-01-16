'use strict';

angular.module('hitsaOis').controller('BuildingController',

  function (message, Classifier, QueryUtils, $mdDialog, $q, $route, $scope) {
    $scope.equipmentDefs = Classifier.query({mainClassCode: 'SEADMED'});
    $scope.formState = {state: 'browse'};
    $scope.buildings = [];
    $scope.currentBuilding = {};

    var Building = QueryUtils.endpoint('/buildings');
    var Room = QueryUtils.endpoint('/buildings/:buildingId/rooms');

    var selectCurrentBuilding = function(id) {
      if($scope.buildings.length > 0) {
        var index = 0;
        if(id !== undefined) {
          var rows = $scope.buildings;
          for(var row_no = 0, row_cnt = rows.length;row_no < row_cnt;row_no++) {
            if(rows[row_no].id === id) {
              index = row_no;
              break;
            }
          }
        }
        $scope.currentBuilding = $scope.buildings[index];
        // refresh room list
        $scope.loadData();
      }else{
        $scope.currentBuilding = {};
      }
    };

    $scope.openBuildingSelection = function() {
      $mdDialog.show({
        controller: function($scope) {
          QueryUtils.createQueryForm($scope, '/buildings', {order: 'name'});

          $scope.cancel = $mdDialog.hide;

          $scope.select = function(id) {
            $mdDialog.hide();
            selectCurrentBuilding(id);
          };

          $scope.loadData();
        },
        templateUrl: 'views/templates/building.select.dialog.html',
        clickOutsideToClose: true
      });
    };

    // building crud
    $scope.addBuilding = function() {
      $scope.building = new Building();
      $scope.formState = {state: 'editBuilding'};
    };

    $scope.editBuilding = function() {
      $scope.building = new Building(angular.copy($scope.currentBuilding));
      $scope.formState = {state: 'editBuilding'};
    };

    $scope.updateBuilding = function() {
      if(!$scope.buildingForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var successMsg = $scope.building.id ? 'main.messages.update.success' : 'main.messages.create.success';
      var postsave = function() {
        message.info(successMsg);
        // add/update values in buildings list
        var building = $scope.building.toJSON();
        var found = false;
        $scope.buildings.forEach(function(element, index) {
          if(element.id === building.id) {
            $scope.buildings[index] = building;
            found = true;
          }
        });
        if(!found) {
          $scope.buildings.push(building);
        }
        if(!$scope.currentBuilding || !$scope.currentBuilding.id || $scope.currentBuilding.id === building.id) {
          $scope.currentBuilding = building;
        }
        $scope.formState = {state: 'browse'};
      };
      if($scope.building.id) {
        $scope.building.$update().then(postsave);
      } else {
        $scope.building.$save().then(postsave);
      }
    };

    $scope.deleteBuilding = function() {
      $scope.building.$delete().then(function() {
        // remove current building from list and select first one from remaining
        $scope.buildings = $scope.buildings.filter(function(element) { return element.id !== $scope.building.id; });
        selectCurrentBuilding();
        $scope.formState = {state: 'browse'};
      });
    };

    $scope.cancelBuildingEdit = function() {
      $scope.formState = {state: 'browse'};
    };

    // room crud
    $scope.addRoom = function() {
      $scope.room = new Room();
      $scope.formState = {state: 'editRoom', roomEquipment: []};
    };

    $scope.editRoom = function(id) {
      var room = angular.copy($scope.tabledata.content.find(function(element) { return element.id === id; }) || {});
      $scope.room = new Room(room);
      $scope.formState = {state: 'editRoom', roomEquipment: room.roomEquipment.map(function(e) { return {equipmentCode: $scope.equipmentDefs[e.equipmentCode], equipmentCount: e.equipmentCount}; } )};
    };

    $scope.updateRoom = function() {
      if(!$scope.roomForm.$valid) {
        message.error('main.messages.form-has-errors');
        return;
      }
      var successMsg = $scope.room.id ? 'main.messages.update.success' : 'main.messages.create.success';
      var postsave = function() {
        message.info(successMsg);
        $scope.formState = {state: 'browse'};
        $scope.loadData();
      };
      var params = {buildingId: $scope.currentBuilding.id};
      $scope.room.roomEquipment = $scope.formState.roomEquipment.map(function(e) { return {equipmentCode: e.equipmentCode.code, equipmentCount: e.equipmentCount}; });
      if($scope.room.id) {
        $scope.room.$update(params).then(postsave);
      } else {
        $scope.room.$save(params).then(postsave);
      }
    };

    $scope.deleteRoom = function() {
      $scope.room.$delete({buildingId: $scope.currentBuilding.id}).then(function() {
        $scope.loadData();
        $scope.formState = {state: 'browse'};
      });
    };

    $scope.cancelRoomEdit = function() {
      $scope.formState = {state: 'browse'};
    };

    $scope.addEquipment = function() {
      if($scope.formState.roomEquipment.some(function(e) { return e.equipmentCode.code === $scope.formState.newEquipmentCode.code; })) {
        message.error('room.duplicateequipment');
        return;
      }
      $scope.formState.roomEquipment.push({equipmentCode: $scope.formState.newEquipmentCode, equipmentCount: 1});
      $scope.formState.newEquipmentCode = null;
    };

    $scope.deleteEquipment = function(code) {
      var rows = $scope.formState.roomEquipment;
      for(var row_no = 0, row_cnt = rows.length;row_no < row_cnt;row_no++) {
        if(rows[row_no].equipmentCode.code === code) {
          rows.splice(row_no, 1);
          break;
        }
      }
    };

    // room pager
    QueryUtils.createQueryForm($scope, '/buildings/:buildingId/rooms', {order: 'name'}, function() {
      // TODO set up equipment column
      $scope.tabledata.content.forEach(function(e) {
      });
    });
    $scope.getCriteria = function() {
      return QueryUtils.getQueryParams(angular.extend({}, $scope.criteria, {buildingId: $scope.currentBuilding.id}));
    };

    // load buildings
    var buildings = QueryUtils.endpoint('/buildings').search({order: 'nameEt'});
    $q.all([$scope.equipmentDefs.$promise, buildings.$promise]).then(function(result) {
      $scope.equipmentDefs = Classifier.toMap(result[0].content);
      $scope.buildings = buildings.content;
      selectCurrentBuilding();
    });
  }
);
