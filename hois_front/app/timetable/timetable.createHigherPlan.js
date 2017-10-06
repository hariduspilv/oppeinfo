'use strict';

angular.module('hitsaOis').controller('HigherTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService) {
    $scope.colors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB', '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#D5D8DC'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;
    QueryUtils.endpoint(baseUrl + '/:id/createHigherPlan').search({id: $scope.timetableId}).$promise.then(function (result) {
      initializeData(result);
    });

    function initializeData(result) {
      $scope.plan = result;
      $scope.plan.byLessons = false;
      $scope.plan.selectAll = true;
      Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function (obj) {
        return obj.code;
      }));
      $scope.plan.capacitiesGrouped = groupBy($scope.plan.studentGroupCapacities, "subjectCode");
      $scope.plan.colorsBySubjectCodes = [];
      $scope.plan.capacitiesGrouped.forEach(function (item, index) {
        $scope.plan.colorsBySubjectCodes.push({subjectCode: item[0].subjectCode, color: $scope.colors[index]});
      });
      $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
      $scope.plan.selectedWeek = getCurrentWeek();
      setCapacities();
      setCurrentDatesForTimetable();
      setLessonTimesForTimetable();
    }

    $scope.$watch('plan.selectedGroup', function () {
      setCapacities();
    });

    $scope.$watch('plan.selectAll', function () {
      if (angular.isDefined($scope.plan.selectAll)) {
        if ($scope.plan.selectAll) {
          Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function (obj) {
            return obj.code;
          }));
          updateSelectedTimetables();
        } else {
          Classifier.setSelectedCodes($scope.plan.studentGroups, []);
          updateSelectedTimetables();
        }
      }
    });

    $scope.$watch('plan.byLessons', function () {
      setCurrentDatesForTimetable();
      setLessonTimesForTimetable();
    });

    $scope.$watch('plan.selectedWeek', function () {
      setCurrentDatesForTimetable();
      setLessonTimesForTimetable();
    });

    function setCapacities() {
      if (angular.isDefined($scope.plan.selectedGroup)) {
        $scope.plan.currentCapacities = $scope.plan.studentGroupCapacities.filter(function (t) {
          return t.studentGroup === $scope.plan.selectedGroup;
        });
        $scope.plan.currentCapacities = $scope.plan.currentCapacities.sort(function (a, b) {
          return a.capacityType - b.capacityType;
        });
        $scope.plan.currentCapacitiesGrouped = groupBy($scope.plan.currentCapacities, "subjectCode");
      }
    }

    $scope.getColorByCode = function (code) {
      var result = $scope.plan.colorsBySubjectCodes.find(function (it) {
        return it.subjectCode === code;
      });
      return result !== null ? result.color : "";
    };

    $scope.getCapacityTypeValue = function (code) {
      var result = $scope.capacityTypes.find(function (it) {
        return it.code === code;
      });
      return result !== null ? result.value.toUpperCase() : "";
    };

    $scope.getCapacityTypeName = function (code) {
      var result = $scope.capacityTypes.find(function (it) {
        return it.code === code;
      });
      return result !== null ? result.nameEt.toUpperCase() : "";
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


    $scope.getEventForLessonTime = function (lessonTime, groupEvents) {
      var event;
      if (groupEvents !== null) {
        event = groupEvents.find(function (it) {
          return new Date(it.start).getTime() === lessonTime.getTime();
        });
      }
      return event;
    };

    $scope.changeEvent = function (currentEvent) {
      var currGroupId = currentEvent.studentGroup;
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.lessonCapacityName = $scope.getCapacityTypeName(currentEvent.capacityType);
        dialogScope.lesson.startTime = new Date(currentEvent.start);
        dialogScope.lesson.endTime = new Date(currentEvent.end);
        dialogScope.lesson.eventRooms = currentEvent.rooms;
        dialogScope.deleteEvent = function (toDelete) {
          QueryUtils.endpoint(baseUrl + '/deleteHigherEvent').save({timetableEventId: toDelete}).$promise.then(function (result) {
            updateAfterSave(result, currGroupId);
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
        QueryUtils.endpoint(baseUrl + '/saveHigherEventRoomsAndTimes').save(query).$promise.then(function (result) {
          updateAfterSave(result, currGroupId);
        });
      });
    };


    $scope.saveEvent = function (params) {
      if ($scope.plan.byLessons) {
        var selectedDay, daysSoFar = 0;
        for (var k = 0; $scope.dayOrder.length > k; k++) {
          daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
          if (params.index < daysSoFar) {
            selectedDay = $scope.dayOrder[k];
            break;
          }
        }
      }
      var query = {
        timetable: $scope.timetableId,
        startTime: this.lessonTime,
        oldEventId: params.oldEventId,
        capacityType: params.capacityType,
        subjectStudyPeriod: params.journalId,
        subjectStudyPeriodStudentGroup: $scope.plan.selectedGroup,
        repeatCode: $scope.plan.repeatCode,
        lessonAmount: $scope.plan.lessonAmount
      };

      QueryUtils.endpoint(baseUrl + '/saveHigherEvent').save(query).$promise.then(function (result) {
        updateAfterSave(result, $scope.plan.selectedGroup);
      });
    };

    function updateAfterSave(result, selectedGroupId) {
      var changedGroup = result.studentGroups.find(function (it) {
        return it.id === selectedGroupId;
      });

      var initialLessons = changedGroup.lessons;
      var finalLessons = [];
      if (initialLessons !== null) {
        for (var initLessons = 0; initLessons < initialLessons.length; initLessons++) {
          initialLessons[initLessons].start = new Date(initialLessons[initLessons].start).getTime();
          initialLessons[initLessons].end = new Date(initialLessons[initLessons].end).getTime();
        }
      } else {
        initialLessons = [];
      }
      for (var dateCounter = 0; dateCounter < $scope.plan.datesForTimetable.length; dateCounter++) {
        var startTime = new Date(new Date($scope.plan.datesForTimetable[dateCounter].getTime()).setHours(7));
        var endTime = new Date(startTime.getTime());
        endTime = new Date(endTime.setHours(23));

        while (startTime.getTime() < endTime.getTime()) {
          var firstLesson = initialLessons.find(function (lesson) {
            return lesson.start === startTime.getTime();
          });
          var noLesson = initialLessons.find(function (lesson) {
            return lesson.start < startTime.getTime() && lesson.end >= startTime.getTime();
          });
          if (firstLesson) {
            firstLesson.colspan = (firstLesson.end - firstLesson.start) / (30 * 30000);
            finalLessons.push(firstLesson);
          } else if (!angular.isDefined(noLesson)) {
            finalLessons.push(startTime);
          }
          //30 000 = 30s
          startTime = new Date(startTime.getTime() + 30 * 30000);
        }
      }
      $scope.plan.studentGroups.find(function(it) {
        return it.id === selectedGroupId;
      }).currentLessons = finalLessons;
    }


    $scope.range = function (count) {
      var array = [];
      for (var i = 0; i < count; i++) {
        array.push(i);
      }
      return array;
    };

    $scope.updateGroups = function () {
      updateSelectedTimetables();
    };

    $scope.updatePairs = function () {
      updateSelectedTimetables();
    };

    function updateSelectedTimetables() {
      var selectedGroupCodes = Classifier.getSelectedCodes($scope.plan.studentGroups);
      var selectedPairCodes = Classifier.getSelectedCodes($scope.plan.subjectTeacherPairs);
      var currentStudentGroups = $scope.plan.studentGroups.filter(function (t) {
        return selectedGroupCodes.indexOf(t.code) !== -1;
      });
      var currentTeacherPairs = $scope.plan.subjectTeacherPairs.filter(function (t) {
        return selectedPairCodes.indexOf(t.code) !== -1;
      });
      $scope.plan.currentStudentGroups = currentStudentGroups.concat(currentTeacherPairs);
    }

    function getCurrentWeek() {
      var currentDate = new Date();
      for (var i = 0; i < $scope.plan.weeks.length; i++) {
        if (currentDate.getTime() < new Date($scope.plan.weeks[i].end).getTime()) {
          return $scope.plan.weeks[i].start;
        }
      }
    }

    function setCurrentDatesForTimetable() {
      $scope.plan.datesForTimetable = [];
      var beginning = new Date($scope.plan.selectedWeek);
      var end = new Date(beginning);
      end.setDate(beginning.getDate() + 6);
      var currentDate = beginning;
      while (currentDate <= end) {
        $scope.plan.datesForTimetable.push(currentDate);
        currentDate = new Date(currentDate.getTime() + 86400000);
      }
    }

    function setLessonTimesForTimetable() {
      $scope.lessonsInDay = [];
      $scope.currentLessonTimes = [];
      if ($scope.plan.byLessons) {
        $scope.plan.lessonTimes.sort(function (a, b) {
          return a.lessonNr - b.lessonNr;
        });
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
        for (var k = 0; $scope.dayOrder.length > k; k++) {
          var currDay = $scope.lessonsInDay[$scope.dayOrder[k]].slice();
          while (angular.isDefined(currDay) && currDay.length > 0) {
            $scope.currentLessonTimes.push(currDay.shift());
          }
        }
      } else if(angular.isDefined($scope.plan.studentGroups)){
        for (var sgCount = 0; sgCount < $scope.plan.studentGroups.length; sgCount++) {
          var initialLessons = $scope.plan.studentGroups[sgCount].lessons;
          var finalLessons = [];
          if (initialLessons !== null) {
            for (var initLessons = 0; initLessons < initialLessons.length; initLessons++) {
              initialLessons[initLessons].start = new Date(initialLessons[initLessons].start).getTime();
              initialLessons[initLessons].end = new Date(initialLessons[initLessons].end).getTime();
            }
          } else {
            initialLessons = [];
          }
          for (var dateCounter = 0; dateCounter < $scope.plan.datesForTimetable.length; dateCounter++) {
            var startTime = new Date(new Date($scope.plan.datesForTimetable[dateCounter].getTime()).setHours(7));
            var endTime = new Date(startTime.getTime());
            endTime = new Date(endTime.setHours(23));

            while (startTime.getTime() < endTime.getTime()) {
              var firstLesson = initialLessons.find(function (lesson) {
                return lesson.start === startTime.getTime();
              });
              var noLesson = initialLessons.find(function (lesson) {
                return lesson.start < startTime.getTime() && lesson.end >= startTime.getTime();
              });
              if (firstLesson) {
                firstLesson.colspan = (firstLesson.end - firstLesson.start) / (30 * 30000);
                finalLessons.push(firstLesson);
              } else if (!angular.isDefined(noLesson)) {
                finalLessons.push(startTime);
              }
              //30 000 = 30s
              startTime = new Date(startTime.getTime() + 30 * 30000);
            }
          }
          $scope.plan.studentGroups[sgCount].currentLessons = finalLessons;
        }
        for (var overallDateCounter = 0; overallDateCounter < $scope.plan.datesForTimetable.length; overallDateCounter++) {
          var lessonsInDay = 0;
          var overallStartTime = new Date(new Date($scope.plan.datesForTimetable[overallDateCounter].getTime()).setHours(7));
          var overallEndTime = new Date(overallStartTime.getTime());
          overallEndTime = new Date(overallEndTime.setHours(23));
          while (overallStartTime.getTime() < overallEndTime.getTime()) {
            $scope.currentLessonTimes.push(overallStartTime);
            lessonsInDay += 1;
            overallStartTime = new Date(overallStartTime.getTime() + 30 * 30000);
          }
          $scope.lessonsInDay = lessonsInDay;
        }
      }
    }

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
