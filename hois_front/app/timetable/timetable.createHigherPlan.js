'use strict';

angular.module('hitsaOis').controller('HigherTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService', '$filter',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService, $filter) {
    $scope.colors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB', '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#D5D8DC'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    $scope.capacityTypes = Classifier.queryForDropdown({mainClassCode: 'MAHT'});
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;

    function initializeData(result) {
      $scope.plan = result;
      $scope.plan.selectAll = true;
      $scope.plan.capacitiesGrouped = groupBy($scope.plan.studentGroupCapacities, "subjectStudyPeriod");
      $scope.plan.colorsBySubjectStudyPeriods = [];
      $scope.plan.capacitiesGrouped.forEach(function (item, index) {
        $scope.plan.colorsBySubjectStudyPeriods.push({subjectStudyPeriod: item[0].subjectStudyPeriod, color: $scope.colors[index]});
      });
      $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
      var currWeek = getCurrentWeek();
      $scope.plan.selectedWeek = getCurrentWeek() ? currWeek : $scope.plan.weeks[0].start;
      $scope.plan.weeks.forEach(function(it) {
        it.startDisplay = $filter('hoisDate')(it.start);
      });
      $scope.plan.subjectTeacherPairs.forEach(function(it) {
        it.isSubjectTeacherPair = true;
        it.dropdownValue = "PAIR-" + it.id;
        it.originalCode = it.code;
        it.code = it.code + " - " +it.teacherNamesShort;
      });
      $scope.plan.studentGroups.forEach(function(it) {
        it.dropdownValue = "GROU-" + it.id;
      });
      setCapacities();
      setAllGroups(true);
      setCurrentDatesForTimetable();
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
      setTimetableTimeRange();
    }

    QueryUtils.endpoint(baseUrl + '/:id/createHigherPlan').search({id: $scope.timetableId}, initializeData);

    $scope.$watch('plan.selectedGroup', function () {
      checkAndUpdateSelectedTimetable();
    });

    $scope.$watch('plan.selectAll', function () {
      if (angular.isDefined($scope.plan.selectAll)) {
          setAllGroups($scope.plan.selectAll);
          updateSelectedTimetables();
      }
    });

    $scope.$watch('plan.selectedWeek', function () {
      setCurrentDatesForTimetable();
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
      setTimetableTimeRange();
      checkAndUpdateSelectedTimetable();
    });

    $scope.$watch('plan.lessonTimeBuilding', function() {
      lessonTimeBuildingChanged($scope.plan.lessonTimeBuilding);
    });

    function setCapacities(selectedGroup) {
      if(angular.isDefined(selectedGroup)) {
        //small hack - only pairs have teacherNamesShort param, so if this one has it, we search from pairs
        if(angular.isDefined(selectedGroup.teacherNamesShort)) {
          $scope.plan.currentCapacities = $scope.plan.studentGroupCapacities.filter(function (t) {
            return t.subjectStudyPeriod === selectedGroup.id;
          });
        } else {
          $scope.plan.currentCapacities = $scope.plan.studentGroupCapacities.filter(function (t) {
            return t.studentGroup === selectedGroup.id;
          });
        }
        $scope.plan.currentCapacities = $scope.plan.currentCapacities.sort(function (a, b) {
          return a.capacityType - b.capacityType;
        });
        $scope.plan.currentCapacitiesGrouped = groupBy($scope.plan.currentCapacities, "subjectStudyPeriod");
      }
    }

    $scope.getColorBySubjectStudyPeriod = function (ssp) {
      var result = $scope.plan.colorsBySubjectStudyPeriods.find(function (it) {
        return it.subjectStudyPeriod === ssp;
      });
      return result ? result.color : "";
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
      var capacity = $scope.plan.studentGroupCapacities.find(function(it) {
        return it.subjectStudyPeriod === currentEvent.subjectStudyPeriod;
      });
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.lessonHeader = capacity.subjectName + ", "  + capacity.teacherNames.join(", ");
        dialogScope.lessonCapacityName = $scope.getCapacityType(currentEvent.capacityType);
        dialogScope.lesson.startTime = new Date(currentEvent.start);
        dialogScope.lesson.endTime = new Date(currentEvent.end);
        dialogScope.lesson.eventRooms = currentEvent.rooms ? currentEvent.rooms : [];
        dialogScope.$watch('lesson.eventRoom', function () {
          if (angular.isDefined(dialogScope.lesson.eventRoom) && dialogScope.lesson.eventRoom !== null) {
            if (dialogScope.lesson.eventRooms.some(function (e) {
                return e.id === dialogScope.lesson.eventRoom.id;
              })) {
              message.error('timetable.timetablePlan.duplicateroom');
              dialogScope.lesson.eventRoom = undefined;
              return;
            }
            dialogScope.lesson.eventRooms.push(dialogScope.lesson.eventRoom);
            dialogScope.lesson.eventRoom = null;
          }
        });
        dialogScope.deleteEvent = function (toDelete) {
          QueryUtils.endpoint(baseUrl + '/deleteHigherEvent').save({timetableEventId: toDelete}).$promise.then(function (result) {
            var changedGroup;
            if(currentEvent.isSubjectTeacherPair) {
              changedGroup = result.subjectTeacherPairs.find(function (it) {
                return it.id === currentEvent.subjectStudyPeriod;
              });
            } else {
              changedGroup = result.studentGroups.find(function (it) {
                return it.id === currentEvent.studentGroup;
              });
            }
            setTimetableForGroups([changedGroup], true);
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
          var changedGroup;
          if(currentEvent.isSubjectTeacherPair) {
            changedGroup = result.subjectTeacherPairs.find(function (it) {
              return it.id === currentEvent.subjectStudyPeriod;
            });
          } else {
            changedGroup = result.studentGroups.find(function (it) {
              return it.id === currentEvent.studentGroup;
            });
          }
          setTimetableForGroups([changedGroup], true);
        });
      });
    };


    $scope.saveEvent = function (params) {
      var isSubjectTeacherPair = this.lessonTime.isSubjectTeacherPair ? true : false;
      var query = {
        timetable: $scope.timetableId,
        startTime: this.lessonTime.start,
        oldEventId: params.oldEventId,
        capacityType: params.capacityType,
        subjectStudyPeriod: params.journalId,
        studentGroupId: $scope.plan.selectedGroup.substr(5),
        repeatCode: $scope.plan.repeatCode,
        lessonAmount: $scope.plan.lessonAmount,
        room: $scope.plan.eventRoom,
        isSubjectTeacherPair: isSubjectTeacherPair
      };

      QueryUtils.endpoint(baseUrl + '/saveHigherEvent').save(query).$promise.then(function (result) {
        var changedGroup;
        if(isSubjectTeacherPair) {
          changedGroup = result.subjectTeacherPairs.find(function (it) {
            return it.id === Number($scope.plan.selectedGroup.substr(5));
          });
        } else {
          changedGroup = result.studentGroups.find(function (it) {
            return it.id === Number($scope.plan.selectedGroup.substr(5));
          });
        }
        setTimetableForGroups([changedGroup], true);
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
      updateSelectedTimetables();
    };

    $scope.updatePairs = function () {
      updateSelectedTimetables();
    };

    function updateSelectedTimetables() {
      var selected = getSelectedGroupsAndPairs();
      selected.sort(function (a, b) {
        if (a.code < b.code) {
          return -1;
        }
        if (a.code > b.code) {
          return 1;
        }
        return 0;
      });
      $scope.plan.currentStudentGroups = selected;
    }

    function getSelectedGroupsAndPairs() {
      var selected = [];
      $scope.plan.studentGroups.concat($scope.plan.subjectTeacherPairs).forEach(function(it) {
        if(it._selected) {
          selected.push(it);
        }
      });
      return selected;
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

    function checkAndUpdateSelectedTimetable() {
      if (angular.isDefined($scope.plan.selectedGroup)) {
        var selectedGroup = $scope.plan.currentStudentGroups.find(function (it) {
          return it.dropdownValue === $scope.plan.selectedGroup;
        });
        setCapacities(selectedGroup);
        setTimetableForGroups([selectedGroup], true);
      }
    }

    function setTimetableTimeRange() {
      $scope.lessonsInDay = [];
      $scope.currentLessonTimes = [];
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
      //setLessonTimeLegend();
    }

    function setTimetableForGroups(studentGroups, draggable) {
      if (studentGroups) {
        for (var sgCount = 0; sgCount < studentGroups.length; sgCount++) {
          var isSubjectTeacherPair = studentGroups[sgCount].isSubjectTeacherPair;
          var initialLessons = studentGroups[sgCount].lessons;
          if (initialLessons !== null) {
            for (var initLessons = 0; initLessons < initialLessons.length; initLessons++) {
              initialLessons[initLessons].start = new Date(initialLessons[initLessons].start);
              initialLessons[initLessons].end = new Date(initialLessons[initLessons].end);
            }
          } else {
            initialLessons = [];
          }
          var startTime = new Date(new Date($scope.plan.datesForTimetable[0].getTime()).setHours(7));
          var endTime = new Date(new Date($scope.plan.datesForTimetable[$scope.plan.datesForTimetable.length - 1].getTime()).setHours(23));
          initialLessons = initialLessons.filter(function (it) {
            return it.start.getTime() >= startTime.getTime() && it.end.getTime() <= endTime.getTime();
          });
          initialLessons = initialLessons.sort(function (a, b) {
            return a.start.getTime() - b.start.getTime();
          });
          var finalLessons = [];
          if (initialLessons.length > 0) {
            for (var ilCount = 0; ilCount < initialLessons.length; ilCount++) {
              var currEvent = initialLessons[ilCount];
              var currEndTime;
              var colspan = 0;
              //while the dates of current event starting and the block to draw are different
              while (startTime.toDateString() !== currEvent.start.toDateString()) {
                currEndTime = new Date(startTime.getTime());
                currEndTime.setHours(23);
                currEndTime.setMinutes(0);
                if (draggable) {
                  while (currEndTime.getTime() > startTime.getTime()) {
                    finalLessons.push({start: new Date(startTime.getTime()), colspan: 1, isSubjectTeacherPair: isSubjectTeacherPair, subjectStudyPeriod: currEvent.subjectStudyPeriod});
                    startTime = new Date(startTime.getTime() + 30 * 30000);
                  }
                } else if(currEndTime.getTime() > startTime.getTime()){
                  colspan = (currEndTime.getTime() - startTime.getTime()) / (30 * 30000);
                  finalLessons.push({start: new Date(startTime.getTime()), colspan: colspan});
                }
                startTime.setDate(startTime.getDate() + 1);
                startTime.setHours(7);
                startTime.setMinutes(0);
              }
              currEndTime = new Date(startTime.getTime());
              currEndTime.setHours(23);
              currEndTime.setMinutes(0);
              if(draggable) {
                while(startTime.getTime() < initialLessons[ilCount].start.getTime()) {
                  finalLessons.push({start: new Date(startTime.getTime()), colspan: 1, isSubjectTeacherPair: isSubjectTeacherPair, subjectStudyPeriod: currEvent.subjectStudyPeriod});
                  startTime = new Date(startTime.getTime() + 30 * 30000);
                }
              } else if(initialLessons[ilCount].start.getTime() > startTime.getTime()) {
                colspan = (initialLessons[ilCount].start.getTime() - startTime.getTime()) / (30 * 30000);
                finalLessons.push({start: new Date(startTime.getTime()), colspan: colspan});
              }
              //if event clases with another one , make it smaller and add a custom parameter
              if(currEvent.start.getTime() < startTime.getTime()) {
                currEvent.colspan = (currEvent.end.getTime() - startTime.getTime()) / (30 * 30000);
                currEvent.clash = true;
              } else {
                currEvent.colspan = (currEvent.end.getTime() - currEvent.start.getTime()) / (30 * 30000);
                currEvent.clash = false;
              }
              if(currEvent.end.getTime() > currEndTime.getTime()) {
                currEvent.colspan -= (currEvent.end.getTime() - currEndTime.getTime()) / (30 * 30000);
                currEvent.clash = true;
              }
              finalLessons.push(currEvent);
              startTime = new Date(currEvent.end.getTime());
              //if current lesson is last make sure to add empty blocks until the end
              if(initialLessons.length - 1 === ilCount) {
                while (startTime.getTime() < endTime.getTime()) {
                  var lastCurrEndTime = new Date(startTime.getTime());
                  lastCurrEndTime.setHours(23);
                  lastCurrEndTime.setMinutes(0);
                  if (draggable) {
                    while (lastCurrEndTime.getTime() > startTime.getTime()) {
                      finalLessons.push({start: new Date(startTime.getTime()), colspan: 1, isSubjectTeacherPair: isSubjectTeacherPair, subjectStudyPeriod: currEvent.subjectStudyPeriod});
                      startTime = new Date(startTime.getTime() + 30 * 30000);
                    }
                  } else {
                    colspan = (lastCurrEndTime.getTime() - startTime.getTime()) / (30 * 30000);
                    finalLessons.push({start: new Date(startTime.getTime()), colspan: colspan});
                  }
                  startTime.setDate(startTime.getDate() + 1);
                  startTime.setHours(7);
                  startTime.setMinutes(0);
                }
              }
            }
          } else {
            while (startTime.getTime() <= endTime.getTime()) {
              var emptyCurrEndTime = new Date(startTime.getTime());
              emptyCurrEndTime.setHours(23);
              emptyCurrEndTime.setMinutes(0);
              if (draggable) {
                while (startTime.getTime() <= emptyCurrEndTime.getTime()) {
                  finalLessons.push({start: new Date(startTime.getTime()), colspan: 1, isSubjectTeacherPair: isSubjectTeacherPair});
                  startTime = new Date(startTime.getTime() + 30 * 30000);
                }
              } else {
                var emptyColspan = (emptyCurrEndTime.getTime() - startTime.getTime()) / (30 * 30000);
                finalLessons.push({start: new Date(startTime.getTime()), colspan: emptyColspan});
              }
              startTime.setDate(startTime.getDate() + 1);
              startTime.setHours(7);
              startTime.setMinutes(0);
            }
          }
          //small hack - only pairs have teacherNamesShort param, so if this one has it, we search from pairs
          var group;
          if(angular.isDefined(studentGroups[sgCount].teacherNamesShort)) {
            finalLessons.forEach(function(it) {
              it.isSubjectTeacherPair = true;
            });
            group = $scope.plan.subjectTeacherPairs.find(function(it) {
              return it.id === studentGroups[sgCount].id;
            });
          } else {
            group = $scope.plan.studentGroups.find(function(it) {
              return it.id === studentGroups[sgCount].id;
            });
          }
          group.currentLessons = finalLessons;
          group.lessons = studentGroups[sgCount].lessons;
        }
      }
    }

    function setAllGroups(selected) {
      $scope.plan.studentGroups.concat($scope.plan.subjectTeacherPairs).forEach(function(it) {
        it._selected = selected;
      });
    }

    function lessonTimeBuildingChanged(buildingId) {
      if (angular.isDefined($scope.plan.datesForTimetable) && $scope.plan.datesForTimetable.length > 0) {
        var startTime = new Date(new Date($scope.plan.datesForTimetable[0].getTime()).setHours(7));
        $scope.lessonTimeLegend = [];

        //datesForTimetable is an array of date objects currently shown in the timetable
        for (var datePointer = 0; datePointer < $scope.plan.datesForTimetable.length; datePointer++) {
          var colspan = 0;
          var currDate = $scope.plan.datesForTimetable[datePointer];
          //    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
          var currDay = $scope.weekday[currDate.getDay()];
          var currentLessons = $scope.plan.lessonTimes.filter(function (it) {
            return it[currDay] === true &&
              it.buildingIds.includes(Number(buildingId)) &&
              (Number(it.startTime.substr(0, 2)) < 23 || (Number(it.startTime.substr(0, 2)) === 7 && Number(it.startTime.substr(3, 2)) === 0)) &&
              ((Number(it.endTime.substr(0, 2)) && Number(it.endTime.substr(3, 2)) === 0) || Number(it.endTime.substr(0, 2)) < 23);
          });
          currentLessons.forEach(function (it) {
            it.lessonDateStart = new Date(currDate.getTime());
            it.lessonDateStart.setHours(it.startTime.substr(0, 2), it.startTime.substr(3, 2));
            it.lessonDateEnd = new Date(currDate.getTime());
            it.lessonDateEnd.setHours(it.endTime.substr(0, 2), it.endTime.substr(3, 2));
          });
          currentLessons = currentLessons.sort(function (a, b) {
            return a.lessonDateStart.getTime() - b.lessonDateStart.getTime();
          });
          for (var lessonPointer = 0; lessonPointer < currentLessons.length; lessonPointer++) {
            colspan = 0;
            var currentLesson = currentLessons[lessonPointer];
            if (lessonPointer > 0 && currentLesson.lessonDateStart.getTime() < currentLessons[lessonPointer - 1].lessonDateEnd.getTime() &&
              currentLesson.lessonDateEnd.getTime() <= currentLessons[lessonPointer - 1].lessonDateEnd.getTime()) {
              continue;
            }
            if (currentLesson.lessonDateStart.getTime() > startTime.getTime()) {
              colspan = (currentLesson.lessonDateStart.getTime() - startTime.getTime()) / (30 * 30000);
              $scope.lessonTimeLegend.push({colspan: colspan});
            }
            if (lessonPointer > 0 && currentLesson.lessonDateStart.getTime() < currentLessons[lessonPointer - 1].lessonDateEnd.getTime()) {
              colspan = (currentLessons[lessonPointer - 1].lessonDateEnd.getTime() - currentLesson.lessonDateEnd.getTime()) / (30 * 30000);
            } else {
              colspan = (currentLesson.lessonDateEnd.getTime() - currentLesson.lessonDateStart.getTime()) / (30 * 30000);
            }
            $scope.lessonTimeLegend.push({lessonNr: currentLesson.lessonNr, colspan: colspan});
            if(currentLesson.lessonDateEnd.getTime() > startTime.getTime()) {
              startTime = new Date(currentLesson.lessonDateEnd.getTime());
            }
          }
          var currEndTime = new Date(startTime.getTime());
          currEndTime.setHours(23);
          currEndTime.setMinutes(0);
          colspan = (currEndTime.getTime() - startTime.getTime()) / (30 * 30000);
          $scope.lessonTimeLegend.push({colspan: colspan});
          startTime.setDate(startTime.getDate() + 1);
          startTime.setHours(7);
          startTime.setMinutes(0);
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
