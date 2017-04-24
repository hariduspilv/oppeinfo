'use strict';

angular.module('hitsaOis').controller('TimetableLessonTimeController', function ($scope, $q, QueryUtils, Classifier, DataUtils, ArrayUtils, $route) {

  $scope.buildings = QueryUtils.endpoint('/autocomplete/buildings').query();
  $scope.blocks = [];
  var usedBuildings = {};



  function selectBuilding(building, block, selected) {
    if (selected === false && angular.isDefined(usedBuildings[building.id])) {
      usedBuildings[building.id] = undefined;
      block.selectedBuildings[building.id] = false;
    } else {
      usedBuildings[building.id] = block;
      block.selectedBuildings[building.id] = true;
    }
  }
  function updateUsedBuildingsForDeletedBlock(block) {
    for (var p in block.selectedBuildings) {
      if (block.selectedBuildings.hasOwnProperty(p) && block.selectedBuildings[p] === true) {
        usedBuildings[p] = undefined;
      }
    }
  }
  function initialBlock() {
    var block = {lessonTimes: [], selectedBuildings: {}};
    for(var i = 0; i < 10; i++) {
      block.lessonTimes.push({});
    }
    $scope.buildings.forEach(function(it) {
      if (!angular.isDefined(usedBuildings[it.id])) {
        selectBuilding(it, block, true);
      }
    });
    return block;
  }

  function entityToForm(entity) {
    DataUtils.convertStringToDates(entity, ["validFrom"]);
    $scope.validFrom = entity.validFrom;
    var block = {lessonTimes: [], selectedBuildings: {}};
    $scope.blocks.push(block);

    if (angular.isArray(entity.lessonTimeBuildingGroup.lessonTimes)) {
      entity.lessonTimeBuildingGroup.lessonTimes.forEach(function(it) {
        DataUtils.convertStringToTime(it, ["startTime", "endTime"]);
        block.lessonTimes.push(it);
      });
    }

    if (angular.isArray(entity.lessonTimeBuildingGroup.buildings)) {
      entity.lessonTimeBuildingGroup.buildings.forEach(function(it) {
        selectBuilding(it, block, true);
      });
    }
  }


  $scope.addNewRow = function(block) {
    block.lessonTimes.push({});
  };
  $scope.addNewBlock = function() {
    $scope.blocks.push(initialBlock());
  };
  $scope.deleteBlock = function(block) {
    updateUsedBuildingsForDeletedBlock(block);
    ArrayUtils.remove($scope.blocks, block);
  };
  $scope.deleteRow = function(lessonTimes, lessonTime) {
    ArrayUtils.remove(lessonTimes, lessonTime);
  };
  $scope.calculateAcademicHours = function(startTime, endTime) {
    var value = window.Math.floor((endTime-startTime)/2700000.0);
    return value >= 0 ? value : 0;
  };
  $scope.buildingChanged = function(building, block) {
    selectBuilding(building, block, block.selectedBuildings[building.id]);
  };
  $scope.isBuildingDisabled = function(building, block) {
    if (angular.isDefined(usedBuildings[building.id])) {
      return !angular.equals(block, usedBuildings[building.id]);
    } else {
      return false;
    }
  };



  $scope.save = function() {
    console.log($scope.blocks);
  };

  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  } else {
      $scope.buildings.$promise.then(function() {
        $scope.blocks.push(initialBlock());
      });
  }
});
