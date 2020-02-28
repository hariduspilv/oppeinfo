'use strict';

angular.module('hitsaOis').controller('HigherTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService', '$filter', '$translate',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService, $filter, $translate) {
    $scope.auth = $route.current.locals.auth;
    var MS_PER_MINUTE = 60000;
    var MS_PER_FITEENMINUTES = MS_PER_MINUTE * 15;
    var CONCURRENT_TOP_MARGIN = 17;
    $scope.colors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB',
    '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#64B5F6','#B0BEC5', '#80CBC4',
    '#BCAAA4', '#7986CB', '#FFF59D', '#66BB6A', '#E6EE9C', '#9FA8DA', '#EF9A9A'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;

    $translate.onReady(function() {
      $scope.loaded = true;
    });

    function initializeData(result) {
      $scope.capacityTypes = result.timetableCapacities;

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
      setTimetableTimeRange();
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
      setTeachersForStudentGroups($scope.plan.studentGroupCapacities, $scope.plan.studentGroups);

      updateSelectedTimetables();
      if ($route.current.params.groupId) {
        $scope.plan.selectedGroup = 'GROU-' + $route.current.params.groupId;
      } else if ($route.current.params.pairId) {
        $scope.plan.selectedGroup = 'PAIR-' + $route.current.params.pairId;
      }
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
      updatesResultingFromTimeRangeChange();
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
        $scope.plan.currentCapacitiesGrouped.forEach(function (group) {
          group.forEach(function (capacity) {
            capacity.teachersString = (capacity.teachers || []).map(function (teacher) {
              return teacher.nameEt;
            }).join(', ');
            var subgroups = $scope.plan.subjectStudyPeriodSubgroups[capacity.subjectStudyPeriod];
            capacity.subgroupsString = (subgroups || []).map(function (subgroup) {
              return subgroup.code;
            }).join(', ');
          });
        });
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
      return result !== null && angular.isDefined(result) ? result.value.toUpperCase() : "";
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
      var days = Object.keys($scope.lessonsInDay);
      var lessonTimeDay = days.find(function (it) {
        it = new Date(it);
        return DataUtils.isSameDay(it, lessonTime.start);
      });
      var lessonsInDay = $scope.lessonsInDay[lessonTimeDay];

      var result = {};
      result['background-color'] = lessonTime.id ? $scope.getColorBySubjectStudyPeriod(lessonTime.subjectStudyPeriod) : 'transparent';
      result.width = ((100 / lessonsInDay) * lessonTime.colspan).toString() + '%';
      if (lessonTime.marginleft) {
        result['margin-left'] = ((100 / lessonsInDay) * lessonTime.marginleft).toString() + '%';
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
        return it.subjectStudyPeriod === currentEvent.subjectStudyPeriod && it.capacityType === currentEvent.capacityType;
      });
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.subgroups = $scope.plan.subjectStudyPeriodSubgroups[currentEvent.subjectStudyPeriod];
        (dialogScope.subgroups || []).forEach(function (it) {
          if (angular.isArray(currentEvent.subgroups)) {
            it.selected = currentEvent.subgroups.includes(it.id);
          } else {
            it.selected = true;
          }
        });
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

        dialogScope.$watchGroup(['lesson.eventRooms.length', 'lesson.startTime', 'lesson.endTime'], function () {
          var occupiedQuery = {
            startTime: dialogScope.lesson.startTime,
            endTime: dialogScope.lesson.endTime,
            timetableEventId: dialogScope.lesson.id,
          };

          occupiedQuery.rooms = dialogScope.lesson.eventRooms.reduce(function (filtered, room) {
            filtered.push(room.id);
            return filtered;
          }, []);

          occupiedQuery.teachers = dialogScope.teachers.reduce(function (filtered, teacher) {
            if (teacher.isTeaching) {
              filtered.push(teacher.id);
            }
            return filtered;
          }, []);

          QueryUtils.endpoint('/timetableevents/timetableTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
            dialogScope.occupiedTime = result;
          });
        });

        dialogScope.isUnderAllocatedLessons = function (lessonCapacity) {
          return $scope.isUnderAllocatedLessons(lessonCapacity);
        };

        dialogScope.subgroupSelected = function (subgroup) {
          dialogScope.teachers.forEach(function (teacher) {
            if (teacher.id === subgroup.teacher.id) {
              teacher.isTeaching = subgroup.selected;
            }
          });
        };

        dialogScope.isRoomOccupied = function (roomId) {
          if (dialogScope.occupiedTime && dialogScope.occupiedTime.rooms) {
            return dialogScope.occupiedTime.rooms.filter(function (it) {
              return it.id === roomId;
            }).length > 0;
          }
          return false;
        };

        dialogScope.isTeacherOccupied = function (teacherId) {
          if (dialogScope.occupiedTime && dialogScope.occupiedTime.teachers) {
            return dialogScope.occupiedTime.teachers.filter(function (it) {
              return it.id === teacherId;
            }).length > 0;
          }
          return false;
        };

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
            updatesResultingFromTimeRangeChange();
            checkAndUpdateSelectedTimetable();
            dialogScope.cancel();
          });
        };

        function atLeastOneSubgroupMustBeSelected(subgroups) {
          var atleastOneSelected = false;
          for (var i = 0; i < subgroups.length; i++) {
            if (subgroups[i].selected) {
              atleastOneSelected = true;
              break;
            }
          }
          return atleastOneSelected;
        }

        dialogScope.saveEvent = function () {
          if (dialogScope.lesson.startTime > dialogScope.lesson.endTime) {
            message.error('timetable.timetableEvent.error.endIsEarlierThanStart');
            return;
          }
          if (dialogScope.subgroups && dialogScope.subgroups.length > 0) {
            if (!atLeastOneSubgroupMustBeSelected(dialogScope.subgroups)) {
              message.error('timetable.timetableEvent.error.atLeastOneSubgroupMustBeSelected');
              return;
            }
          }
          dialogScope.submit();
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
          }, []),
          subgroups: (submittedDialogScope.subgroups || []).reduce(function (filtered, subgroup) {
            if (subgroup.selected) {
              filtered.push(subgroup.id);
            }
            return filtered;
          }, [])
        };
        saveEventRoomsAndTimes(query, currentEvent);
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
        updatesResultingFromTimeRangeChange();
        checkAndUpdateSelectedTimetable();
      });
    }

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
          dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts($scope, $scope.auth.higher, result), function () {
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
      updatesResultingFromTimeRangeChange();
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
      $scope.$broadcast('refreshFixedColumns');
    };

    $scope.updatePairs = function () {
      updateSelectedTimetables();
      $scope.$broadcast('refreshFixedColumns');
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

    function updatesResultingFromTimeRangeChange() {
      setTimetableTimeRange();
      lessonTimeBuildingChanged($scope.plan.lessonTimeBuilding);
      setTimetableForGroups($scope.plan.studentGroups, false);
      setTimetableForGroups($scope.plan.subjectTeacherPairs, false);
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
      $scope.totalLessons = 0;
      $scope.lessonsInDay = {};
      $scope.dayTimeRanges = {};
      $scope.currentLessonTimes = [];

      for (var overallDateCounter = 0; overallDateCounter < $scope.plan.datesForTimetable.length; overallDateCounter++) {
        var day = $scope.plan.datesForTimetable[overallDateCounter];
        var dayTimeRange = getWeekdayTimeRange(day);
        var lessonsInDay = 0;

        if (dayTimeRange !== null) {
          var overallStartTime = new Date(new Date(day.getTime()));
          overallStartTime.setHours(dayTimeRange.start.getHours());
          overallStartTime.setMinutes(dayTimeRange.start.getMinutes());
          var overallEndTime = new Date(overallStartTime.getTime());
          overallEndTime.setHours(dayTimeRange.end.getHours());
          overallEndTime.setMinutes(dayTimeRange.end.getMinutes());
          $scope.dayTimeRanges[day] = {start: overallStartTime, end: overallEndTime};

          while (overallStartTime.getTime() < overallEndTime.getTime()) {
            var nextStartTime = new Date(overallStartTime.getTime() + MS_PER_FITEENMINUTES);
            $scope.currentLessonTimes.push({time: overallStartTime, endOfDay: nextStartTime.getTime() >= overallEndTime.getTime()});
            lessonsInDay += 1;
            overallStartTime = nextStartTime;
          }
        }
        $scope.totalLessons += lessonsInDay;
        $scope.lessonsInDay[day] = lessonsInDay;
      }
    }

    function getWeekdayTimeRange(day) {
      var weekday = $scope.weekday[day.getDay()];

      var startTimes = [];
      var endTimes = [];

      $scope.plan.lessonTimes.filter(function (it) {
        return it[weekday];
      }).forEach(function (it) {
        startTimes.push(moment(it.startTime, "HH:mm"));
        endTimes.push(moment(it.endTime, "HH:mm"));
      });

      var lessons = [];
      var studentGroupLessons = getPlannedLessons($scope.plan.studentGroups, day);
      var subjectTeacherPairLessons = getPlannedLessons($scope.plan.subjectTeacherPairs, day);
      lessons = lessons.concat(studentGroupLessons);
      lessons = lessons.concat(subjectTeacherPairLessons);
      lessons.forEach(function (it) {
        var start = new Date(it.start);
        var end = new Date(it.end);
        startTimes.push(moment({ hour: start.getHours(), minute: start.getMinutes() }));
        endTimes.push(moment({ hour: end.getHours(), minute: end.getMinutes() }));
      });

      if (startTimes.length === 0 || endTimes.length === 0) {
        return null;
      }

      var minStartTime = moment.min(startTimes).toDate();
      minStartTime.setHours(Math.max(7, minStartTime.getHours()));
      minStartTime.setMinutes(0);
      minStartTime.setSeconds(0, 0);
      var maxEndTime = moment.max(endTimes).toDate();
      maxEndTime.setHours(Math.min(23, maxEndTime.getMinutes() > 0 ? maxEndTime.getHours() + 1 : maxEndTime.getHours()));
      maxEndTime.setMinutes(0);
      maxEndTime.setSeconds(0, 0);
      return { start: minStartTime, end: maxEndTime };
    }

    function getPlannedLessons(type, weekday) {
      var plannedLessons = [];
      type.forEach(function (it) {
        if (it.lessons) {
          var weekDayLessons = it.lessons.filter(function (lesson) {
            return DataUtils.isSameDay(weekday, new Date(lesson.start));
          });
          plannedLessons = plannedLessons.concat(weekDayLessons);
        }
      });
      return plannedLessons;
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

          var finalLessons = {};
          for (var datePointer = 0; datePointer < $scope.plan.datesForTimetable.length; datePointer++) {
            var currDayRange = $scope.dayTimeRanges[$scope.plan.datesForTimetable[datePointer]];
            if (!currDayRange) {
              continue;
            }

            var startTime = new Date(currDayRange.start);
            var endTime = new Date(currDayRange.end);

            var currDayInitialLessons = initialLessons.filter(function (it) {
              return DataUtils.isSameDay(it.start, startTime);
            });
            currDayInitialLessons = currDayInitialLessons.sort(function (a, b) {
              return a.start.getTime() - b.start.getTime();
            });

            var startTimeDateString = startTime.getDate() + startTime.getMonth() + startTime.getFullYear();
            var concurrentLessonsCount = 0;
            if (!angular.isArray(finalLessons[startTimeDateString])) {
              finalLessons[startTimeDateString] = [];
            }

            var currDayDrawnLessons = 0;
            if (currDayInitialLessons.length > 0) {
              for (var ilCount = 0; ilCount < currDayInitialLessons.length; ilCount++) {
                currDayDrawnLessons = 0;
                var currEvent = currDayInitialLessons[ilCount];
                var colspan = 0;
                //dont draw the lesson if the current even is out of bounds
                if(currEvent.start.getHours() < 7 || currEvent.end.getHours() < 7 || currEvent.start.getHours() === 23) {
                  continue;
                }

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
                //if current lesson is longer than day range, calculate colspan from event start to end of range
                if (currEvent.end.getTime() >= endTime.getTime()) {
                  currEvent.colspan -= (currEvent.end.getTime() - endTime.getTime()) / MS_PER_FITEENMINUTES;
                }

                finalLessons[startTimeDateString].push(currEvent);
                if (startTime.getTime() < currEvent.end.getTime()) {
                  startTime = new Date(currEvent.end.getTime());
                }

                //if current lesson is last make sure to add empty blocks until the end
                if (currDayInitialLessons.length - 1 === ilCount) {
                  if (draggable) {
                    while (endTime.getTime() > startTime.getTime()) {
                      finalLessons[startTimeDateString].push({
                        start: new Date(startTime.getTime()),
                        colspan: 1,
                        isSubjectTeacherPair: isSubjectTeacherPair,
                        subjectStudyPeriod: currEvent.subjectStudyPeriod
                      });
                      startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                    }
                  } else {
                    colspan = (endTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                    finalLessons[startTimeDateString].push({
                      start: new Date(startTime.getTime()),
                      colspan: colspan
                    });
                  }
                }
                currDayDrawnLessons++;
              }
            }

            if (currDayDrawnLessons === 0) {
              if (draggable) {
                while (startTime.getTime() < endTime.getTime()) {
                  finalLessons[startTimeDateString].push({
                    start: new Date(startTime.getTime()),
                    colspan: 1,
                    isSubjectTeacherPair: isSubjectTeacherPair
                  });
                  startTime = new Date(startTime.getTime() + MS_PER_FITEENMINUTES);
                }
              } else {
                var emptyColspan = (endTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES;
                finalLessons[startTimeDateString].push({
                  start: new Date(startTime.getTime()),
                  colspan: emptyColspan
                });
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
        var startDayTimeRange = $scope.dayTimeRanges[$scope.plan.datesForTimetable[0]];
        if (!startDayTimeRange) {
          return;
        }
        var startTime = new Date(startDayTimeRange.start);
        $scope.lessonTimeLegend = [];

        //datesForTimetable is an array of date objects currently shown in the timetable
        for (var datePointer = 0; datePointer < $scope.plan.datesForTimetable.length; datePointer++) {
          var colspan = 0;
          var currDate = $scope.plan.datesForTimetable[datePointer];
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
              colspan = Math.round((currentLesson.lessonDateStart.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES);
              $scope.lessonTimeLegend.push({
                colspan: colspan
              });
            }
            if (lessonPointer > 0 && currentLesson.lessonDateStart.getTime() < currentLessons[lessonPointer - 1].lessonDateEnd.getTime()) {
              colspan = Math.round((currentLessons[lessonPointer - 1].lessonDateEnd.getTime() - currentLesson.lessonDateEnd.getTime()) / MS_PER_FITEENMINUTES);
            } else {
              colspan = Math.round((currentLesson.lessonDateEnd.getTime() - currentLesson.lessonDateStart.getTime()) / MS_PER_FITEENMINUTES);
            }
            $scope.lessonTimeLegend.push({
              lessonNr: currentLesson.lessonNr,
              colspan: colspan
            });
            if (currentLesson.lessonDateEnd.getTime() > startTime.getTime()) {
              startTime = new Date(currentLesson.lessonDateEnd.getTime());
            }
          }

          var currEndTime = new Date($scope.dayTimeRanges[$scope.plan.datesForTimetable[datePointer]].end);
          colspan = Math.round((currEndTime.getTime() - startTime.getTime()) / MS_PER_FITEENMINUTES);

          if (colspan > 0) {
            $scope.lessonTimeLegend.push({
              colspan: colspan,
              endOfDay: true
            });
          } else {
            if ($scope.lessonTimeLegend[$scope.lessonTimeLegend.length - 1]) {
              $scope.lessonTimeLegend[$scope.lessonTimeLegend.length - 1].endOfDay = true;
            }
          }

          while (datePointer < $scope.plan.datesForTimetable.length - 1) {
            if ($scope.dayTimeRanges[$scope.plan.datesForTimetable[datePointer + 1]]) {
              startTime = new Date($scope.dayTimeRanges[$scope.plan.datesForTimetable[datePointer + 1]].start);
              break;
            }
            datePointer++;
          }
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
