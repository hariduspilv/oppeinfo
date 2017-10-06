'use strict';

angular.module('hitsaOis').controller('VocationalTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService', 'ArrayUtils',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService, ArrayUtils) {
    $scope.journalColors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB', '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#D5D8DC'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;
    QueryUtils.endpoint(baseUrl + '/:id/createVocationalPlan').search({id: $scope.timetableId}).$promise.then(function (result) {
      initializeData(result, $route.current.params.groupId);
    });

    function initializeData(result, selectedGroupId) {
      $scope.plan = result;
      $scope.plan.selectAll = true;
      $scope.plan.journals.sort(function (a, b) {
        return a.id - b.id;
      });
      Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function (obj) {
        return obj.code;
      }));
      $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
      $scope.plan.datesForTimetable = [];
      var beginning = new Date($scope.plan.startDate);
      var end = new Date($scope.plan.endDate);
      var currentDate = beginning;
      while (currentDate <= end) {
        $scope.plan.datesForTimetable.push(currentDate);
        currentDate = new Date(currentDate.getTime() + 86400000);
      }
      $scope.plan.lessonTimes.sort(function (a, b) {
        return a.lessonNr - b.lessonNr;
      });
      $scope.lessonsInDay = [];
      for (var j = 0; $scope.weekday.length > j; j++) {
        var currentDay = $scope.weekday[j];
        $scope.lessonsInDay[currentDay] = $scope.plan.lessonTimes.filter(function (it) {
          return it[currentDay] === true;
        });
        var previousLessonNrs = [],
          index = $scope.lessonsInDay[currentDay].length - 1,
          currentLessons = $scope.lessonsInDay[currentDay];
        while (index >= 0) {
          if (!previousLessonNrs.includes(currentLessons[index].lessonNr)) {
            previousLessonNrs.push(currentLessons[index].lessonNr);
          } else {
            currentLessons.splice(index, 1);
          }
          index -= 1;
        }
      }
      $scope.lessonsInDay.sort(function (a, b) {
        return $scope.weekday.indexOf(a) - $scope.weekday.indexOf(b);
      });

      $scope.firstDay = $scope.weekday[new Date($scope.plan.startDate).getDay()];
      $scope.dayOrder = $scope.weekday.slice();
      while ($scope.dayOrder[0] !== $scope.firstDay) {
        $scope.dayOrder[$scope.dayOrder.length - 1] = $scope.dayOrder.shift();
      }
      $scope.currentLessonTimes = [];
      for (var k = 0; $scope.dayOrder.length > k; k++) {
        var currDay = $scope.lessonsInDay[$scope.dayOrder[k]].slice();
        while (angular.isDefined(currDay) && currDay.length > 0) {
          $scope.currentLessonTimes.push(currDay.shift());
        }
      }

      //creating object for each group to hold all planed lessons
      var plannedLessonsByGroup = {};
      for (var s = 0; $scope.plan.plannedLessons.length > s; s++) {
        var groupNr = $scope.plan.plannedLessons[s].studentGroup;
        if (!angular.isDefined(plannedLessonsByGroup[groupNr])) {
          plannedLessonsByGroup[groupNr] = {};
          plannedLessonsByGroup[groupNr].id = groupNr;
          plannedLessonsByGroup[groupNr].lessons = [];
        }
        plannedLessonsByGroup[groupNr].lessons.push($scope.plan.plannedLessons[s]);
      }
      $scope.plan.plannedLessonsByGroup = plannedLessonsByGroup;
      $scope.plan.selectedGroup = selectedGroupId;
      setCapacities();
    }

    $scope.$watch('plan.selectedGroup', function () {
      setCapacities();
    });

    function setCapacities() {
      if (angular.isDefined($scope.plan.selectedGroup)) {
        $scope.plan.currentCapacities = $scope.plan.studentGroupCapacities.filter(function (t) {
          return t.studentGroup === $scope.plan.selectedGroup;
        });
        $scope.plan.currentCapacities = $scope.plan.currentCapacities.sort(function (a, b) {
          return a.journal - b.journal;
        });
        var currentCapacities = $scope.plan.currentCapacities.map(function (obj) {
          return obj.journal;
        });
        $scope.plan.currentCapacitiesGrouped = groupBy($scope.plan.currentCapacities, "journal");
        if (angular.isDefined($scope.plan.journals)) {
          $scope.plan.journals.forEach(function (item, index) {
            item.color = $scope.journalColors[index];
          });
          $scope.plan.currentJournals = $scope.plan.journals.filter(function (t) {
            return currentCapacities.indexOf(t.id) !== -1;
          });
        }
      }
    }

    $scope.getByJournal = function (journal, param) {
      var capacity = $scope.plan.currentCapacities.find(function (it) {
        return it.journal === journal.id;
      });
      return capacity[param];
    };

    $scope.getByCapacity = function (capacity, param) {
      var journal = $scope.plan.currentJournals.find(function (it) {
        return it.id === capacity.journal;
      });
      return journal[param];
    };

    $scope.getCapacityTypeValue = function (code) {
      var result = $scope.capacityTypes.find(function (it) {
        return it.code === code;
      });
      return result !== null ? result.value.toUpperCase() : "";
    };

    $scope.getCapacityType = function (code) {
      return $scope.capacityTypes.find(function (it) {
        return it.code === code;
      });
    };

    $scope.getCountForCapacity = function (capacity) {
      capacity.thisAllocatedLessons = 0;
      return capacity.thisPlannedLessons - capacity.thisAllocatedLessons;
    };

    $scope.getRoomCodes = function (rooms) {
      return rooms.map(function (a) {
        return a.nameEt;
      }).join("-");
    };

    $scope.getEventForLesson = function (index, lesson, group) {
      var selectedDay, daysSoFar = 0;
      for (var k = 0; $scope.dayOrder.length > k; k++) {
        daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
        if (index < daysSoFar) {
          selectedDay = $scope.dayOrder[k];
          break;
        }
      }

      if (angular.isDefined($scope.plan.plannedLessonsByGroup[group.id])) {
        var groupLesson = $scope.plan.plannedLessonsByGroup[group.id].lessons.filter(function (t) {
          return $scope.weekday[new Date(t.start).getDay()] === selectedDay && lesson.lessonNr === t.lessonNr;
        });
        if (groupLesson.length !== 0) {
          groupLesson[0].journalObject = $scope.plan.journals.filter(function (item) {
            return item.id === groupLesson[0].journal;
          })[0];
          var buildingObjects = $scope.plan.lessonTimes.filter(function (item) {
            return item[selectedDay] === true && item.lessonNr === lesson.lessonNr;
          });
          if (buildingObjects.length > 1) {
            var buildingIds = buildingObjects.reduce(function (ids, currArr) {
              if (ids.constructor === Array) {
                return ids.concat(currArr.buildingIds);
              }
              return ids.buildingIds.concat(currArr.buildingIds);
            });
            if (buildingIds) {
              groupLesson[0].buildingIds = buildingIds.filter(function (item, i, ar) {
                return ar.indexOf(item) === i;
              });
            }
          } else {
            groupLesson[0].buildingIds = buildingObjects[0].buildingIds;
          }
          return groupLesson[0];
        }
      }
      return null;
    };

    $scope.removeFromArray = ArrayUtils.remove;

    $scope.changeEvent = function (currentEvent) {
      var currGroupId = $scope.plan.selectedGroup;
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.lessonCapacityName = $scope.getCapacityType(currentEvent.capacityType);
        dialogScope.lesson.startTime = new Date(currentEvent.start);
        dialogScope.lesson.endTime = new Date(currentEvent.end);
        if(currentEvent.rooms) {
          dialogScope.lesson.eventRooms = currentEvent.rooms;
        } else {
          dialogScope.lesson.eventRooms = [];
        }
        dialogScope.$watch('lesson.eventRoom', function () {
          if (angular.isDefined(dialogScope.lesson.eventRoom) && dialogScope.lesson.eventRoom !== null) {
            dialogScope.lesson.eventRooms.push(dialogScope.lesson.eventRoom);
            dialogScope.lesson.eventRoom = null;
          }
        });
        dialogScope.deleteEvent = function (toDelete) {
          QueryUtils.endpoint(baseUrl + '/deleteVocationalEvent').save({timetableEventId: toDelete}).$promise.then(function (result) {
            initializeData(result, currGroupId);
            dialogScope.cancel();
          });
        };
      }, function (submittedDialogScope) {
        var query = {
          startTime: submittedDialogScope.lesson.startTime,
          endTime: submittedDialogScope.lesson.endTime,
          rooms: submittedDialogScope.lesson.eventRooms,
          timetableEventId: submittedDialogScope.lesson.id
        };
        QueryUtils.endpoint(baseUrl + '/saveVocationalEventRoomsAndTimes').save(query).$promise.then(function (result) {
          initializeData(result, currGroupId);
        });
      });
    };


    $scope.saveEvent = function (params) {
      var currGroupId = $scope.plan.selectedGroup;
      var selectedDay, daysSoFar = 0;
      for (var k = 0; $scope.dayOrder.length > k; k++) {
        daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
        if (params.index < daysSoFar) {
          selectedDay = $scope.dayOrder[k];
          break;
        }
      }
      var journalId = params.journalId;
      var query = {
        journal: journalId,
        timetable: $scope.timetableId,
        lessonTime: this.lessonTime.id,
        selectedDay: selectedDay,
        oldEventId: params.oldEventId,
        capacityType: params.capacityType
      };
      QueryUtils.endpoint(baseUrl + '/saveVocationalEvent').save(query).$promise.then(function (result) {
        initializeData(result, currGroupId);
      });
    };

    $scope.range = function (count) {
      var array = [];
      for (var i = 0; i < count; i++) {
        array.push(i);
      }
      return array;
    };

    $scope.updateGroups = function () {
      var selectedCodes = Classifier.getSelectedCodes($scope.plan.studentGroups);
      $scope.plan.currentStudentGroups = $scope.plan.studentGroups.filter(function (t) {
        return selectedCodes.indexOf(t.code) !== -1;
      });
    };

    function groupBy(collection, property) {
      var value, index, values = [], result = [];
      for (var i = 0; i < collection.length; i++) {
        value = collection[i][property];
        index = values.indexOf(value);
        if (index > -1) {
          result[index].push(collection[i]);
        } else {
          values.push(value);
          result.push([collection[i]]);
        }
      }
      return result;
    }
  }
]);
