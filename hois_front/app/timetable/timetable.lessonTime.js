'use strict';

angular.module('hitsaOis').controller('TimetableLessonTimeController', function ($scope, $rootScope, $q, QueryUtils, Classifier, DataUtils, ArrayUtils, $route, message, $timeout, $location) {
  var INITIAL_BLOCK_COUNT = 10;
  $rootScope.replaceLastUrl("#/timetable/lessonTime/search");
  $scope.buildings = QueryUtils.endpoint('/autocomplete/buildings').query();
  $scope.blocks = [];
  $scope.usedBuildings = {};


  function setMinValidFrom(val) {
    var buildings = [];
    for (var p in val) {
      if (val.hasOwnProperty(p) && angular.isDefined(val[p])) {
        buildings.push(p);
      }
    }
    if (buildings.length > 0) {
      QueryUtils.endpoint('/lessontimes/minValidFrom').get({buildings: buildings.join(), lessonTimeId: $route.current.params.id}, function(result) {
        if (result.minValidFrom === null) {
          $scope.minValidFrom = undefined;
        } else {
          DataUtils.convertStringToDates(result, ["minValidFrom"]);
          $scope.minValidFrom = result.minValidFrom;
          if ($scope.validFrom < $scope.minValidFrom + 1) {
            $scope.validFrom = undefined;
          }
        }
      });
    } else {
      $scope.minValidFrom = undefined;
    }
  }

  var usedBuildingsTimeout;
  $scope.$watchCollection('usedBuildings', function(val) {
    if (angular.isDefined(usedBuildingsTimeout) && !usedBuildingsTimeout.resolved) {
      $timeout.cancel(usedBuildingsTimeout);
    }
    usedBuildingsTimeout = $timeout(function() {
      setMinValidFrom(val);
    }, 500);
  });


  function selectBuilding(building, block, selected) {
    if (selected === false && angular.isDefined($scope.usedBuildings[building.id])) {
      $scope.usedBuildings[building.id] = undefined;
      block.selectedBuildings[building.id] = false;
    } else {
      $scope.usedBuildings[building.id] = block;
      block.selectedBuildings[building.id] = true;
    }
  }
  function updateUsedBuildingsForDeletedBlock(block) {
    for (var p in block.selectedBuildings) {
      if (block.selectedBuildings.hasOwnProperty(p) && block.selectedBuildings[p] === true) {
        $scope.usedBuildings[p] = undefined;
      }
    }
  }
  function initialBlock() {
    var block = {lessonTimes: [], selectedBuildings: {}};
    var initialLessonTime = {
      dayMon: true,
      dayTue: true,
      dayWed: true,
      dayThu: true,
      dayFri: true,
      daySat: true,
      daySun: true
    };
    for(var i = 0; i < INITIAL_BLOCK_COUNT; i++) {
      block.lessonTimes.push(angular.extend({}, initialLessonTime));
    }
    $scope.buildings.forEach(function(it) {
      if (!angular.isDefined($scope.usedBuildings[it.id])) {
        selectBuilding(it, block, true);
      }
    });
    return block;
  }

  function entityToForm(entity) {
    $scope.blocks = [];
    $scope.usedBuildings = {};
    DataUtils.convertStringToDates(entity, ["validFrom"]);
    $scope.validFrom = entity.validFrom;

    if (angular.isArray(entity.lessonTimeBuildingGroups)) {
      entity.lessonTimeBuildingGroups.forEach(function(lessonTimeBuildingGroup) {
        var block = {id: lessonTimeBuildingGroup.id, lessonTimes: [], selectedBuildings: {}};
        $scope.blocks.push(block);

        if (angular.isArray(lessonTimeBuildingGroup.lessonTimes)) {
          lessonTimeBuildingGroup.lessonTimes.forEach(function(it) {
            DataUtils.convertStringToTime(it, ["startTime", "endTime"]);
            block.lessonTimes.push(it);
          });
        }
        if (angular.isArray(lessonTimeBuildingGroup.buildings)) {
          lessonTimeBuildingGroup.buildings.forEach(function(it) {
            selectBuilding(it, block, true);
          });
        }
      });
    }
  }

  function removeEmptyLessonTimeRows(lessonTimes) {
    var i = lessonTimes.length;
    while (i--) {
      if (!$scope.isLessonTimeRowFilled(lessonTimes[i])) {
        lessonTimes.splice(i, 1);
      }
    }
  }
  
  function eachBlockHasValidRows() {
    var valid = true;
    blocks:
    for(var i = 0; $scope.blocks.length > i; i++) {
      var currentValid = false;
      for(var j = 0; $scope.blocks[i].lessonTimes.length > j; j++) {
        // each block has atleas one valid row
        if($scope.isLessonTimeRowFilled($scope.blocks[i].lessonTimes[j])) {
          currentValid = true;
        }

        var lessonStart = $scope.blocks[i].lessonTimes[j].startTime;
        var lessonEnd = $scope.blocks[i].lessonTimes[j].endTime;
        if (typeof lessonStart !== 'undefined' && typeof lessonEnd !== 'undefined') {
          if (lessonStart > lessonEnd) {
            message.error('timetable.lessonTime.endIsEarlierThanStart');
            valid = false;
            break blocks;
          }
          if (lessonStart.getHours() < 7 || (lessonEnd.getHours() === 23 && lessonEnd.getMinutes() > 0)) {
            message.error('timetable.lessonTime.outOfTimeRange');
            valid = false;
            break blocks;
          }
        }
      }

      if(!currentValid) {
        message.error('timetable.lessonTime.oneRowRequired');
        valid = false;
      }
    }
    return valid;
  }

  function dtoToEntity(scope) {
    var entity = {validFrom: scope.validFrom, lessonTimeBuildingGroups: []};
    if (angular.isArray(scope.blocks)) {
      scope.blocks.forEach(function(it) {
        removeEmptyLessonTimeRows(it.lessonTimes);
        var lessonTimeBuildingGroup = {id: it.id, lessonTimes: it.lessonTimes, buildings: []};
        if (angular.isObject(it.selectedBuildings)) {
          for (var p in it.selectedBuildings) {
            if (it.selectedBuildings.hasOwnProperty(p) && it.selectedBuildings[p] === true) {
              lessonTimeBuildingGroup.buildings.push({id: parseInt(p, 10)});
            }
          }
        }
        entity.lessonTimeBuildingGroups.push(lessonTimeBuildingGroup);
      });
    }
    return entity;
  }


  $scope.addNewRow = function(block) {
    block.lessonTimes.push({});
  };
  $scope.addNewBlock = function() {
    $scope.lessonTimeForm.$setPristine(true);
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
    var value = Math.round(((endTime - startTime) / 2700000.0) * 100) / 100;
    return value >= 0 ? value : 0;
  };

  $scope.buildingChanged = function(building, block) {
    selectBuilding(building, block, block.selectedBuildings[building.id]);
    $scope.blocks.$setDirty();
  };
  $scope.isBuildingDisabled = function(building, block) {
    if (angular.isDefined($scope.usedBuildings[building.id])) {
      return !angular.equals(block, $scope.usedBuildings[building.id]);
    } else {
      return false;
    }
  };

  $scope.selectedBuildingsLength = function(block) {
    var count = 0;
    for (var p in block.selectedBuildings) {
      if (block.selectedBuildings.hasOwnProperty(p) && block.selectedBuildings[p] === true) {
        count++;
      }
    }
    var getterSetter = function() {
      return count;
    };
    return getterSetter;
  };

/**
 * at least one field is filled and one day is selected
 */
  $scope.isLessonTimeRowFilled = function(lessonTime) {
    if (angular.isObject(lessonTime)) {
      return !!lessonTime.lessonNr ||
        !!lessonTime.startTime ||
        !!lessonTime.endTime && (
        lessonTime.dayMon === true ||
        lessonTime.dayTue === true ||
        lessonTime.dayWed === true ||
        lessonTime.dayThu === true ||
        lessonTime.dayFri === true ||
        lessonTime.daySat === true ||
        lessonTime.daySun === true);
    }
    return false;
  };


  var entity = $route.current.locals.entity;
  if (angular.isDefined(entity)) {
    entityToForm(entity);
  } else {
      $scope.buildings.$promise.then(function() {
        $scope.blocks.push(initialBlock());
      });
  }
  function isEdit() {
    return angular.isDefined(entity);
  }

  $scope.save = function() {
    $scope.lessonTimeForm.$setSubmitted();
    if($scope.lessonTimeForm.$valid && eachBlockHasValidRows()) {
      var LessonTimeEndpoint;
      if(isEdit()) {
        LessonTimeEndpoint = QueryUtils.endpoint('/lessontimes/');
        var updatedEntity = dtoToEntity($scope);
        new LessonTimeEndpoint(updatedEntity).$update().then(function(result) {
          message.info('main.messages.create.success');
          $scope.lessonTimeForm.$setPristine();
          entityToForm(result);
        });
      } else {
        LessonTimeEndpoint = QueryUtils.endpoint('/lessontimes');
        var newEntity = dtoToEntity($scope);
        new LessonTimeEndpoint(newEntity).$save().then(function(result) {
          message.info('main.messages.create.success');
          $scope.lessonTimeForm.$setPristine();
          if (angular.isArray(result.lessonTimeBuildingGroups)) {
            result.lessonTimeBuildingGroups.forEach(function(lessonTimeBuildingGroup) {
              if (angular.isArray(lessonTimeBuildingGroup.lessonTimes) && lessonTimeBuildingGroup.lessonTimes.length > 0) {
                $location.path('/timetable/lessonTime/'+lessonTimeBuildingGroup.lessonTimes[0].id+'/edit');
              }
            });
          }
        });
      }
    }
  };
});
