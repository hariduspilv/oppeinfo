'use strict';

angular.module('hitsaOis').controller('HigherTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService', '$filter',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService, $filter) {
    var MS_PER_MINUTE = 60000;
    var MS_PER_FITEENMINUTES = MS_PER_MINUTE * 15;
    var CONCURRENT_TOP_MARGIN = 17;
    $scope.colors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB',
    '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#64B5F6','#B0BEC5', '#80CBC4',
    '#BCAAA4', '#7986CB', '#FFF59D', '#66BB6A', '#E6EE9C', '#9FA8DA', '#EF9A9A'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    $scope.capacityTypes = Classifier.queryForDropdown({
      mainClassCode: 'MAHT'
    });
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;

    function initializeData(result) {
      $scope.plan = result;
      $scope.plan.selectAll = true;
      $scope.plan.capacitiesGrouped = groupBy($scope.plan.studentGroupCapacities, "subjectStudyPeriod");
      $scope.plan.colorsBySubjectStudyPeriods = [];
      $scope.plan.capacitiesGrouped.forEach(function (item, index) {
        $scope.plan.colorsBySubjectStudyPeriods.push({
          subjectStudyPeriod: item[0].subjectStudyPeriod,
          color: $scope.colors[index % 19]
        });
      });
      $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
      var currWeek = getCurrentWeek();
      $scope.plan.selectedWeek = getCurrentWeek() ? currWeek : $scope.plan.weeks[0].start;
      $scope.plan.weeks.forEach(function (it) {
        it.startDisplay = $filter('hoisDate')(it.start);
      });
      $scope.plan.subjectTeacherPairs.forEach(function (it) {
        it.isSubjectTeacherPair = true;
        it.dropdownValue = "PAIR-" + it.id;
        it.originalCode = it.code;
        it.code = it.code + " - " + it.teacherNamesShort;
      });
      $scope.plan.studentGroups.forEach(function (it) {
        it.dropdownValue = "GROU-" + it.id;
      });
      setCapacities();
      setAllGroups(true);
      setCurrentDatesForTimetable();
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
      setTeachersForStudentGroups($scope.plan.studentGroupCapacities, $scope.plan.studentGroups);
      setTimetableTimeRange();
    }

    QueryUtils.endpoint(baseUrl + '/:id/createHigherPlan').search({
      id: $scope.timetableId
    }, initializeData);

    $scope.$watch('plan.selectedGroup', function () {
      checkAndUpdateSelectedTimetable();
    });

    $scope.$watch('plan.selectAll', function () {
      if (angular.isDefined($scope.plan.selectAll)) {
        setAllGroups($scope.plan.selectAll);
        updateSelectedTimetables();
      }
    });

    $scope.orderBySelectedGroup = function (group) {
      return group.dropdownValue !== $scope.plan.selectedGroup;
    };

    $scope.$watch('plan.selectedWeek', function () {
      setCurrentDatesForTimetable();
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
      setTimetableTimeRange();
      checkAndUpdateSelectedTimetable();
    });

    $scope.$watch('plan.lessonTimeBuilding', function () {
      lessonTimeBuildingChanged($scope.plan.lessonTimeBuilding);
    });

    function setCapacities(selectedGroup) {
      if (angular.isDefined(selectedGroup)) {
        //small hack - only pairs have teacherNamesShort param, so if this one has it, we search from pairs
        if (angular.isDefined(selectedGroup.teacherNamesShort)) {
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

    $scope.boldAndRed = function (boolean) {
      if (angular.isArray(boolean)) {
        boolean = boolean.length === 0 ? true : false;
      }
      if (!angular.isDefined(boolean) || boolean === null || boolean) {
        return {
          'font-weight': 'bold',
          'color': 'red'
        };
      }
      return '';
    };

    $scope.isUnderAllocatedLessons = function (lessonCapacity) {
      var totalAllocatedLessons = lessonCapacity.totalAllocatedLessons ? lessonCapacity.totalAllocatedLessons : 0;
      if (lessonCapacity.totalPlannedLessons >= totalAllocatedLessons) {
        return true;
      }
      return false;
    };

    $scope.lessonTimeStyle = function (lessonTime) {
      var result = {};
      result['background-color'] = lessonTime.id ? $scope.getColorBySubjectStudyPeriod(lessonTime.subjectStudyPeriod) : 'transparent';
      result.width = ((100 / $scope.lessonsInDay) * lessonTime.colspan).toString() + '%';
      if (lessonTime.marginleft) {
        result['margin-left'] = ((100 / $scope.lessonsInDay) * lessonTime.marginleft).toString() + '%';
      }
      if (lessonTime.topmargin) {
        result['margin-top'] = lessonTime.topmargin.toString() + 'px';
      }
      if (!lessonTime.id) {
        result.height = '100%';
      }
      return result;
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
      if (!currentEvent.id) {
        return;
      }
      var capacity = $scope.plan.studentGroupCapacities.find(function (it) {
        return it.subjectStudyPeriod === currentEvent.subjectStudyPeriod;
      });
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.teachers = capacity.teachers;
        dialogScope.teachers.forEach(function (it) {
          if (angular.isArray(currentEvent.teachers)) {
            it.isTeaching = currentEvent.teachers.includes(it.id);
          } else {
            it.isTeaching = true;
          }
        });
        dialogScope.lessonHeader = capacity.subjectName + ", " + capacity.teachers.map(function (teacher) {
          return teacher.nameEt;
        }).join(", ");
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
          QueryUtils.endpoint(baseUrl + '/deleteHigherEvent').save({
            timetableEventId: toDelete
          }).$promise.then(function (result) {
            var changedGroup;
            if (currentEvent.isSubjectTeacherPair) {
              changedGroup = result.subjectTeacherPairs.find(function (it) {
                return it.id === currentEvent.subjectStudyPeriod;
              });
            } else {
              changedGroup = result.studentGroups.find(function (it) {
                return it.id === currentEvent.studentGroup;
              });
            }
            setTimetableForGroups([changedGroup], true);
            $scope.plan.studentGroupCapacities = result.studentGroupCapacities;
            checkAndUpdateSelectedTimetable();
            dialogScope.cancel();
          });
        };
      }, function (submittedDialogScope) {
        var query = {
          startTime: submittedDialogScope.lesson.startTime,
          endTime: submittedDialogScope.lesson.endTime,
          rooms: submittedDialogScope.lesson.eventRooms,
          timetableEventId: submittedDialogScope.lesson.id,
          teachers: submittedDialogScope.teachers.reduce(function (filtered, teacher) {
            if (teacher.isTeaching) {
              filtered.push(teacher.id);
            }
            return filtered;
          }, [])
        };

        var occupiedQuery = angular.copy(query);
        occupiedQuery.timetable = $scope.timetableId;
        occupiedQuery.rooms = occupiedQuery.rooms.reduce(function (filtered, room) {
          filtered.push(room.id);
          return filtered;
        }, []);

        QueryUtils.endpoint('/timetableevents/timetableTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
          if(result.occupied) {
            dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
              saveEventRoomsAndTimes(query, currentEvent);
            });
          } else {
            saveEventRoomsAndTimes(query, currentEvent);
          }
        });
      });
    };

    function saveEventRoomsAndTimes(query, currentEvent) {
      QueryUtils.endpoint(baseUrl + '/saveHigherEventRoomsAndTimes').save(query).$promise.then(function (result) {
        var changedGroup;
        if (currentEvent.isSubjectTeacherPair) {
          changedGroup = result.subjectTeacherPairs.find(function (it) {
            return it.id === currentEvent.subjectStudyPeriod;
          });
        } else {
          changedGroup = result.studentGroups.find(function (it) {
            return it.id === currentEvent.studentGroup;
          });
        }
        setTimetableForGroups([changedGroup], true);
        $scope.plan.studentGroupCapacities = result.studentGroupCapacities;
        checkAndUpdateSelectedTimetable();
      });
    }

    /*$scope.saveEvent = function (params) {

      var clashingRoom = findRoomClashByIds();
      var clashingTeacher = findTeachersByIds();
      var clashingTimes = findEventByTimeAndGroup(startTime, $scope.plan.selectedGroup.substr(5), isSubjectTeacherPair);
      if(clashingTimes) {
        dialogService.confirmDialog({ prompt: 'timetable.timetablePlan.addForOtherGroups', accept: 'main.yes', cancel: 'main.no' }, function() {
          $scope.saveEventAfterClashCheck(params, isSubjectTeacherPair, startTime);
        });
      } else {
        $scope.saveEventAfterClashCheck(params, isSubjectTeacherPair, startTime);
      }
    };*/

    /*function findEventByTimeAndGroup(startTime, group, isSubjectTeacherPair) {
      var endTime = new Date(startTime.getTime() + (MS_PER_FITEENMINUTES * $scope.plan.lessonAmount));
      var groupLessons;
      if(isSubjectTeacherPair) {
        groupLessons = $scope.plan.subjectTeacherPairs.find(function (it) {
          return it.id === group;
        }).lessons;
      } else {
        groupLessons = $scope.plan.studentGroups.find(function (it) {
          return it.id === group;
        }).lessons;
      }
      if(groupLessons) {
        var lessonsFound = groupLessons.filter(function(lesson) {
          return (startTime.getTime() > lesson.start.getTime() && startTime.getTime() < lesson.end.getTime()) || (endTime.getTime() < lesson.start.getTime() && 
            endTime.getTime() > lesson.end.getTime());
        })
        if(lessonsFound) {
          return lessonsFound;
        }
      }
      return [];
    }*/

    $scope.saveEventAfterClashCheck = function (params) {
      var isSubjectTeacherPair = this.lessonTime.isSubjectTeacherPair ? true : false;
      var startTime = this.lessonTime.start;
      var matchingCapacities = $scope.plan.studentGroupCapacities.filter(function (it) {
        return it.subjectStudyPeriod === Number(params.journalId) && it.capacityType === params.capacityType;
      });

      if (!isFinite(parseInt(params.oldEventId)) && matchingCapacities.length > 1) {
        dialogService.confirmDialog({
            prompt: 'timetable.timetablePlan.addForOtherGroups',
            accept: 'main.yes',
            cancel: 'main.no'
          },
          function () {
            $scope.saveEventAfterCheck(params, true, isSubjectTeacherPair, startTime);
          },
          function () {
            $scope.saveEventAfterCheck(params, false, isSubjectTeacherPair, startTime);
          });
      } else {
        $scope.saveEventAfterCheck(params, false, isSubjectTeacherPair, startTime);
      }
    };


    $scope.saveEventAfterCheck = function (params, allGroups, isSubjectTeacherPair, startTime) {
      var occupiedQuery = {
        timetable: $scope.timetableId,
        oldEventId: params.oldEventId,
        startTime: startTime,
        subjectStudyPeriod: params.journalId,
        repeatCode: $scope.plan.repeatCode,
        lessonAmount: $scope.plan.lessonAmount,
        room: angular.isDefined($scope.plan.eventRoom) ? $scope.plan.eventRoom.id : null
      };

      QueryUtils.endpoint('/timetableevents/timetableNewHigherTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
        if(result.occupied) {
          dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
            saveEvent(params, allGroups, isSubjectTeacherPair, startTime);
          }, function () {
            // needs TimetablePlanDto to get rid of wrong dragable lesson
            QueryUtils.endpoint(baseUrl + '/:id/createHigherPlan').search({id: $scope.timetableId}).$promise.then(function (result) {
              updateChangedGroup(result, allGroups, isSubjectTeacherPair);
            });
          });
        } else {
          saveEvent(params, allGroups, isSubjectTeacherPair, startTime);
        }
      });
    };

    function saveEvent(params, allGroups, isSubjectTeacherPair, startTime) {
      var query = {
        timetable: $scope.timetableId,
        oldEventId: params.oldEventId,
        startTime: startTime,
        capacityType: params.capacityType,
        subjectStudyPeriod: params.journalId,
        studentGroupId: $scope.plan.selectedGroup.substr(5),
        repeatCode: $scope.plan.repeatCode,
        lessonAmount: $scope.plan.lessonAmount,
        room: $scope.plan.eventRoom,
        isSubjectTeacherPair: isSubjectTeacherPair,
        isForAllGroups: allGroups
      };

      QueryUtils.endpoint(baseUrl + '/saveHigherEvent').save(query).$promise.then(function (result) {
        updateChangedGroup(result, allGroups, isSubjectTeacherPair);
      });
    }

    function updateChangedGroup(result, allGroups, isSubjectTeacherPair) {
      var changedGroup;
      if (isSubjectTeacherPair) {
        changedGroup = result.subjectTeacherPairs.find(function (it) {
          return it.id === Number($scope.plan.selectedGroup.substr(5));
        });
      } else {
        changedGroup = result.studentGroups.find(function (it) {
          return it.id === Number($scope.plan.selectedGroup.substr(5));
        });
      }
      if (allGroups) {
        setTimetableForGroups(result.studentGroups, false);
      }
      setTimetableForGroups([changedGroup], true);
      $scope.plan.studentGroupCapacities = result.studentGroupCapacities;
      checkAndUpdateSelectedTimetable();
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
      $scope.plan.studentGroups.concat($scope.plan.subjectTeacherPairs).forEach(function (it) {
        if (it._selected) {
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
        $scope.plan.selectedStudentGroupObject = selectedGroup;
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
          overallStartTime = new Date(overallStartTime.getTime() + MS_PER_FITEENMINUTES);
        }
        $scope.lessonsInDay = lessonsInDay;
      }
      //setLessonTimeLegend();
    }

    function setTeachersForStudentGroups(capacities, groups) {
      if (angular.isArray(capacities) && angular.isArray(groups)) {
        groups.forEach(function (currGroup) {
          currGroup.teachers = [];
          currGroup.teacherIds = [];
          var currCapacities = capacities.filter(function (cap) {
            return cap.studentGroup === currGroup.id;
          });
          currCapacities.forEach(function (currCap) {
            currCap.teachers.forEach(function (currTeacher) {
              if (currGroup.teacherIds.indexOf(currTeacher.id) === -1) {
                currGroup.teacherIds.push(currTeacher.id);
                currGroup.teachers.push(currTeacher);
              }
            });
          });
        });
      }
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
          var finalLessons = {};
          var startTimeDateString = startTime.getDate() + startTime.getMonth() + startTime.getFullYear();
          var concurrentLessonsCount = 0;
          if (!angular.isArray(finalLessons[startTimeDateString])) {
            finalLessons[startTimeDateString] = [];
          }
          if (initialLessons.length > 0) {
            for (var ilCount = 0; ilCount < initialLessons.length; ilCount++) {
              var currEvent = initialLessons[ilCount];
              var currEndTime;
              var colspan = 0;
              //dont draw the lesson if the current even is out of bounds
              if(currEvent.start.getHours() < 7 || currEvent.end.getHours() < 7 || currEvent.start.getHours() === 23) {
                continue;
              }
              //while the dates of current event starting and the block to draw are different
              //draw blocks until the start time is the same as 
              while (startTime.toDateString() !== currEvent.start.toDateString()) {
                currEndTime = new Date(startTime.getTime());
                currEndTime.setHours(23);
                currEndTime.setMinutes(0);
                if (draggable) {
                  while (currEndTime.getTime() > startTime.getTime()) {
                    finalLessons[startTimeDateString].push({
                      start: new Date(startTime.getTime()),
                      colspan: 1,
                      isSubjectTeacherPair: isSubjectTeacherPair,
                      subjectStudyPeriod: currEvent.subjectStudyPeriod
                    });
                    startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                  }
                } else if (currEndTime.getTime() > startTime.getTime()) {
                  colspan = (currEndTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                  finalLessons[startTimeDateString].push({
                    start: new Date(startTime.getTime()),
                    colspan: colspan
                  });
                }
                startTime.setDate(startTime.getDate() + 1);
                startTime.setHours(7);
                startTime.setMinutes(0);
                startTimeDateString = startTime.getDate() + startTime.getMonth() + startTime.getFullYear();
                if (!angular.isArray(finalLessons[startTimeDateString])) {
                  finalLessons[startTimeDateString] = [];
                }
              }
              currEndTime = new Date(startTime.getTime());
              currEndTime.setHours(23);
              currEndTime.setMinutes(0);
              //if we are editing the current row we make it draggable
              if (draggable) {
                while (startTime.getTime() < currEvent.start.getTime()) {
                  finalLessons[startTimeDateString].push({
                    start: new Date(startTime.getTime()),
                    colspan: 1,
                    isSubjectTeacherPair: isSubjectTeacherPair,
                    subjectStudyPeriod: currEvent.subjectStudyPeriod
                  });
                  startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                }
                //
              } else if (currEvent.start.getTime() >= startTime.getTime()) {
                colspan = (currEvent.start.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                finalLessons[startTimeDateString].push({
                  start: new Date(startTime.getTime()),
                  colspan: colspan
                });
              }
              //if event clashes with another one , give it a margin and add a custom parameter
              if (currEvent.start.getTime() <= startTime.getTime()) {
                currEvent.colspan = (currEvent.end.getTime() - currEvent.start.getTime()) / MS_PER_FITEENMINUTES;
                currEvent.marginleft = (currEvent.start.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                var concurrentLessons = finalLessons[startTimeDateString].filter(function (lesson) {
                  return currEvent.start.getTime() >= lesson.start.getTime() && currEvent.start.getTime() < (lesson.start.getTime() + (lesson.colspan * MS_PER_FITEENMINUTES));
                });
                if (concurrentLessons.length > concurrentLessonsCount) {
                  var prevLesson = finalLessons[startTimeDateString][finalLessons[startTimeDateString].length - 1];
                  if (finalLessons[startTimeDateString].length > 0 && prevLesson.topmargin) {
                    currEvent.topmargin = prevLesson.topmargin + CONCURRENT_TOP_MARGIN;
                  } else {
                    currEvent.topmargin = CONCURRENT_TOP_MARGIN;
                  }
                  concurrentLessonsCount += 1;
                } else {
                  concurrentLessonsCount = 0;
                }
              } else {
                currEvent.colspan = (currEvent.end.getTime() - currEvent.start.getTime()) / MS_PER_FITEENMINUTES;
              }
              if (currEvent.end.getTime() >= currEndTime.getTime()) {
                currEvent.colspan -= (currEvent.end.getTime() - currEndTime.getTime()) / MS_PER_FITEENMINUTES;
              }
              finalLessons[startTimeDateString].push(currEvent);
              if (startTime.getTime() < currEvent.end.getTime()) {
                startTime = new Date(currEvent.end.getTime());
              }
              //if current lesson is last make sure to add empty blocks until the end
              if (initialLessons.length - 1 === ilCount) {
                while (startTime.getTime() < endTime.getTime()) {
                  var lastCurrEndTime = new Date(startTime.getTime());
                  lastCurrEndTime.setHours(23);
                  lastCurrEndTime.setMinutes(0);
                  if (draggable) {
                    while (lastCurrEndTime.getTime() > startTime.getTime()) {
                      finalLessons[startTimeDateString].push({
                        start: new Date(startTime.getTime()),
                        colspan: 1,
                        isSubjectTeacherPair: isSubjectTeacherPair,
                        subjectStudyPeriod: currEvent.subjectStudyPeriod
                      });
                      startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                    }
                  } else {
                    colspan = (lastCurrEndTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                    finalLessons[startTimeDateString].push({
                      start: new Date(startTime.getTime()),
                      colspan: colspan
                    });
                  }
                  startTime.setDate(startTime.getDate() + 1);
                  startTime.setHours(7);
                  startTime.setMinutes(0);
                  var startTimeDateString = startTime.getDate() + startTime.getMonth() + startTime.getFullYear();
                  if (!angular.isArray(finalLessons[startTimeDateString])) {
                    finalLessons[startTimeDateString] = [];
                  }
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
                  finalLessons[startTimeDateString].push({
                    start: new Date(startTime.getTime()),
                    colspan: 1,
                    isSubjectTeacherPair: isSubjectTeacherPair
                  });
                  startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                }
              } else {
                var emptyColspan = (emptyCurrEndTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                finalLessons[startTimeDateString].push({
                  start: new Date(startTime.getTime()),
                  colspan: emptyColspan
                });
              }
              startTime.setDate(startTime.getDate() + 1);
              startTime.setHours(7);
              startTime.setMinutes(0);
              startTimeDateString = startTime.getDate() + startTime.getMonth() + startTime.getFullYear();
              if (!angular.isArray(finalLessons[startTimeDateString])) {
                finalLessons[startTimeDateString] = [];
              }
            }
          }
          //small hack - only pairs have teacherNamesShort param, so if this one has it, we search from pairs
          var group;
          if (angular.isDefined(studentGroups[sgCount].teacherNamesShort)) {
            for (var key in finalLessons) {
              finalLessons[key].forEach(function (it) {
                it.isSubjectTeacherPair = true;
              });
            }
            group = $scope.plan.subjectTeacherPairs.find(function (it) {
              return it.id === studentGroups[sgCount].id;
            });
          } else {
            group = $scope.plan.studentGroups.find(function (it) {
              return it.id === studentGroups[sgCount].id;
            });
          }
          group.currentLessons = finalLessons;
          group.lessons = studentGroups[sgCount].lessons;
        }
      }
    }

    function setAllGroups(selected) {
      $scope.plan.studentGroups.concat($scope.plan.subjectTeacherPairs).forEach(function (it) {
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
              colspan = (currentLesson.lessonDateStart.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
              $scope.lessonTimeLegend.push({
                colspan: colspan
              });
            }
            if (lessonPointer > 0 && currentLesson.lessonDateStart.getTime() < currentLessons[lessonPointer - 1].lessonDateEnd.getTime()) {
              colspan = (currentLessons[lessonPointer - 1].lessonDateEnd.getTime() - currentLesson.lessonDateEnd.getTime()) / MS_PER_FITEENMINUTES;
            } else {
              colspan = (currentLesson.lessonDateEnd.getTime() - currentLesson.lessonDateStart.getTime()) / MS_PER_FITEENMINUTES;
            }
            $scope.lessonTimeLegend.push({
              lessonNr: currentLesson.lessonNr,
              colspan: colspan
            });
            if (currentLesson.lessonDateEnd.getTime() > startTime.getTime()) {
              startTime = new Date(currentLesson.lessonDateEnd.getTime());
            }
          }
          var currEndTime = new Date(startTime.getTime());
          currEndTime.setHours(23);
          currEndTime.setMinutes(0);
          colspan = (currEndTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
          $scope.lessonTimeLegend.push({
            colspan: colspan
          });
          startTime.setDate(startTime.getDate() + 1);
          startTime.setHours(7);
          startTime.setMinutes(0);
        }
      }
    }

    function groupBy(collection, property) {
      var value, index, values = [],
        result = [];
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
