'use strict';

angular.module('hitsaOis').controller('VocationalTimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier', 'dialogService', 'ArrayUtils',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier, dialogService, ArrayUtils) {
    var bsave=false;
    $scope.Math = window.Math;
    $scope.isArray = angular.isArray;
    $scope.journalColors = ['#ffff00', '#9fff80', '#ff99cc', '#E8DAEF', '#85C1E9', '#D1F2EB',
     '#ABEBC6', '#F9E79F', '#FAD7A0', '#EDBB99', '#D5DBDB', '#64B5F6','#B0BEC5', '#80CBC4',
     '#FCD4F5', '#AFBAF7', '#FFF59D', '#66BB6A', '#E6EE9C', '#9FA8DA', '#EF9A9A'];
    $scope.plan = {};
    $scope.timetableId = $route.current.params.id;
    $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
    $scope.capacityTypes = Classifier.queryForDropdown({
      mainClassCode: 'MAHT'
    });
    var baseUrl = '/timetables';
    $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;

    QueryUtils.loadingWheel($scope, true);
    QueryUtils.endpoint(baseUrl + '/:id/createVocationalPlan').search({
      id: $scope.timetableId
    }).$promise.then(function (result) {
      initializeData(result, $route.current.params.groupId, null);
      QueryUtils.loadingWheel($scope, false);
    });

    function initializeData(result, selectedGroupId, selectedGroups) {
      var displayPeriodLessons = $scope.plan.displayPeriodLessons ? $scope.plan.displayPeriodLessons : false;
      $scope.plan = result;
      $scope.plan.selectAll = false;
      $scope.plan.displayPeriodLessons = displayPeriodLessons;
      $scope.plan.journals.sort(function (a, b) {
        return a.id - b.id;
      });
      Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function (obj) {
        return obj.code;
      }));
      if (selectedGroups) {
        $scope.plan.currentStudentGroups = selectedGroups;

        var selectedGroupCodes = selectedGroups.map(function(it) { return it.code;});
        $scope.plan.studentGroups.forEach(function (studentGroup) {
          if (selectedGroupCodes.indexOf(studentGroup.code) === -1) {
            studentGroup._selected = false;
            $scope.plan.selectAll = false;
          }
        });
      } else {
        $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
        $scope.plan.currentStudentGroups.forEach(function (studentGroup) {
          if (studentGroup.id !== Number(selectedGroupId)) {
            studentGroup._selected = false;
            $scope.plan.selectAll = false;
          }
        });
      }
      setTeachersForStudentGroups($scope.plan.studentGroupCapacities, $scope.plan.studentGroups, $scope.plan.journals);
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

          var hasLessons = false;
          while (angular.isDefined(currDay) && currDay.length > 0) {
            hasLessons = true;
            $scope.currentLessonTimes.push(currDay.shift());
          }
          if (!hasLessons) {
            $scope.currentLessonTimes.push({noLessons: true});
          }
      }

      //creating object for each group to hold all planed lessons
      var plannedLessonsByGroup = {};
      var plannedLessonsByTeachers = {};
      for (var s = 0; $scope.plan.plannedLessons.length > s; s++) {
        var groupNr = $scope.plan.plannedLessons[s].studentGroup;
        if (!angular.isDefined(plannedLessonsByGroup[groupNr])) {
          plannedLessonsByGroup[groupNr] = {};
          plannedLessonsByGroup[groupNr].id = groupNr;
          plannedLessonsByGroup[groupNr].lessons = [];
        }
        plannedLessonsByGroup[groupNr].lessons.push($scope.plan.plannedLessons[s]);
        if (angular.isArray($scope.plan.plannedLessons[s].teachers)) {
          $scope.plan.plannedLessons[s].teachers.forEach(function (teacher) {
            if (!angular.isArray(plannedLessonsByTeachers[teacher])) {
              plannedLessonsByTeachers[teacher] = [];
            }
            var currentLesson = plannedLessonsByTeachers[teacher].find(function (lesson) {
              return lesson.id === $scope.plan.plannedLessons[s].id;
            });
            if (!currentLesson) {
              plannedLessonsByTeachers[teacher].push($scope.plan.plannedLessons[s]);
            }
          });
        }
      }
      $scope.plan.plannedLessonsByTeachers = plannedLessonsByTeachers;
      $scope.plan.plannedLessonsByGroup = plannedLessonsByGroup;
      $scope.plan.selectedGroup = selectedGroupId;
      setCapacities();
      if(!bsave)
      {
        $scope.updateGroups();
      }
      else
      {
        bsave=false;
      }

    }

    $scope.$watch('plan.selectedGroup', function () {
      setCapacities();
    });

    $scope.selectAllChanged = function () {
      if (angular.isDefined($scope.plan.selectAll)) {
        if ($scope.plan.selectAll) {
          Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function (obj) {
            return obj.code;
          }));
        } else {
          Classifier.setSelectedCodes($scope.plan.studentGroups, []);
        }
        $scope.updateGroups();
      }
    };

    $scope.orderBySelectedGroup = function (group) {
      return group.id !== $scope.plan.selectedGroup;
    };

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
            item.color = $scope.journalColors[index % 19];
          });
          $scope.plan.currentJournals = $scope.plan.journals.filter(function (t) {
            return currentCapacities.indexOf(t.id) !== -1;
          });
        }
        $scope.plan.selectedStudentGroupObject = $scope.plan.studentGroups.find(function(sg) {
          return sg.id === $scope.plan.selectedGroup;
        });
      }
    }

    $scope.getByJournal = function (journal, param) {
      var capacity = $scope.plan.currentCapacities.find(function (it) {
        return it.journal === journal.id;
      });
      return capacity[param];
    };

    $scope.getTotalsByJournal = function (journal, param) {
      var allCapacities = $scope.plan.currentCapacities.filter(function (it) {
        return it.journal === journal.id;
      });
      var result = 0;
      allCapacities.forEach(function (it) {
        result += it[param];
      });
      return result;
    };

    $scope.isUnderAllocatedWeekLessons = function (journal) {
      if ($scope.getTotalsByJournal(journal, 'thisPlannedLessons') >= $scope.getTotalsByJournal(journal, 'thisPlannedLessons') - $scope.getTotalsByJournal(journal, 'lessonsLeft')) {
        return true;
      }
      return false;
    };

    $scope.isUnderAllocatedTotalLessons = function (journal) {
      if ($scope.getTotalsByJournal(journal, 'totalPlannedLessons') >= $scope.getTotalsByJournal(journal, 'totalAllocatedLessons')) {
        return true;
      }
      return false;
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

    $scope.getEventsForLesson = function (index, lesson, group) {
      var selectedDay, daysSoFar = 0;
      for (var k = 0; $scope.dayOrder.length > k; k++) {
        daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
        if (index < daysSoFar) {
          selectedDay = $scope.dayOrder[k];
          break;
        }
      }

      if (angular.isDefined($scope.plan.plannedLessonsByGroup) && angular.isDefined($scope.plan.plannedLessonsByGroup[group.id])) {
        var groupLessons = $scope.plan.plannedLessonsByGroup[group.id].lessons.filter(function (t) {
          return $scope.weekday[new Date(t.start).getDay()] === selectedDay && lesson.lessonNr === t.lessonNr;
        });
        if (groupLessons.length !== 0) {
          groupLessons.forEach(function (currLesson) {
            currLesson.journalObject = $scope.plan.journals.filter(function (item) {
              return item.id === currLesson.journal;
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
                currLesson.buildingIds = buildingIds.filter(function (item, i, ar) {
                  return ar.indexOf(item) === i;
                });
              }
            } else {
              currLesson.buildingIds = buildingObjects[0].buildingIds;
            }
            if (!angular.isArray(currLesson.teachers)) {
              currLesson.teachers = [];
            }
            if (currLesson.journalObject && currLesson.journalObject.teachers)
            {
              currLesson.currentTeacherIds = currLesson.journalObject.teachers.filter(function (teacher) {
                return currLesson.teachers.indexOf(teacher.id) !== -1;
              });

              currLesson.currentTeachers = currLesson.currentTeacherIds.map(function (teacher) {
                return teacher.nameEt;
              });
            }
          });
          return groupLessons;
        }
      }
      return null;
    };


    $scope.getTeacherLesson = function (index, lesson, group, fullName) {
      var selectedDay, daysSoFar = 0;
      for (var k = 0; $scope.dayOrder.length > k; k++) {
        daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
        if (index < daysSoFar) {
          selectedDay = $scope.dayOrder[k];
          break;
        }
      }
      var teacherPlan = [];
      group.teachers.forEach(function (teacher) {
        if (angular.isDefined($scope.plan.plannedLessonsByTeachers) && $scope.plan.plannedLessonsByTeachers[teacher.id]) {
          var teacherLesson = $scope.plan.plannedLessonsByTeachers[teacher.id].find(function (t) {
            return $scope.weekday[new Date(t.start).getDay()] === selectedDay && lesson.lessonNr === t.lessonNr;
          });
          if (teacherLesson) {
            if(fullName) {
              teacherPlan.push(teacher.nameEt);
            } else {
              teacherPlan.push(teacher.nameEt.replace(/\b(\S{1,2})\S*/g, '$1').replace(/ /g, ''));
            }
          }
        }
      });
      return teacherPlan ? teacherPlan : null;
    };

    $scope.removeFromArray = ArrayUtils.remove;

    $scope.changeEvent = function (currentEvent) {
      var currGroupId = $scope.plan.selectedGroup;
      dialogService.showDialog('timetable/timetable.event.change.dialog.html', function (dialogScope) {
        dialogScope.lesson = currentEvent;
        dialogScope.teachers = currentEvent.journalObject.teachers;
        dialogScope.teachers.forEach(function (it) {
          if (angular.isArray(currentEvent.teachers)) {
            it.isTeaching = currentEvent.teachers.includes(it.id);
          } else {
            it.isTeaching = true;
          }
        });
        dialogScope.lessonHeader = currentEvent.journalObject.name;
        dialogScope.lessonCapacityName = $scope.getCapacityType(currentEvent.capacityType);
        dialogScope.lesson.startTime = new Date(currentEvent.start);
        dialogScope.lesson.endTime = new Date(currentEvent.end);
        if (currentEvent.rooms) {
          dialogScope.lesson.eventRooms = angular.copy(currentEvent.rooms);
        } else {
          dialogScope.lesson.eventRooms = [];
        }
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
          bsave=true;
          QueryUtils.endpoint(baseUrl + '/deleteVocationalEvent').save({
            timetableEventId: toDelete
          }).$promise.then(function (result) {
            initializeData(result, currGroupId, $scope.plan.currentStudentGroups);
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
        occupiedQuery.rooms = occupiedQuery.rooms.reduce(function (filtered, room) {
          filtered.push(room.id);
          return filtered;
        }, []);

        QueryUtils.endpoint('/timetableevents/timetableTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
          if(result.occupied) {
            dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
              saveEventRoomsAndTimes(query, currGroupId);
            });
          } else {
            saveEventRoomsAndTimes(query, currGroupId);
          }
        });
      });
    };

    function saveEventRoomsAndTimes(query, currGroupId) {
      bsave=true;
      QueryUtils.endpoint(baseUrl + '/saveVocationalEventRoomsAndTimes').save(query).$promise.then(function (result) {
        initializeData(result, currGroupId, $scope.plan.currentStudentGroups);
      });
    }

    $scope.saveEvent = function (params) {
      bsave=true;
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

      var occupiedQuery = {
        journal: journalId,
        timetable: $scope.timetableId,
        lessonTime: this.lessonTime.id,
        selectedDay: selectedDay,
        oldEventId: params.oldEventId
      };

      QueryUtils.endpoint('/timetableevents/timetableNewVocationalTimeOccupied').get(occupiedQuery).$promise.then(function (result) {
        if (result.occupied) {
          dialogService.confirmDialog(DataUtils.occupiedEventTimePrompts(result), function () {
            saveVocationalEvent(query, currGroupId);
          }, function () {
            bsave = false;
            QueryUtils.loadingWheel($scope, true);
            QueryUtils.endpoint(baseUrl + '/:id/createVocationalPlan').search({
              id: $scope.timetableId
            }).$promise.then(function (result) {
              initializeData(result, currGroupId, $scope.plan.currentStudentGroups);
              QueryUtils.loadingWheel($scope, false);
            });
          });
        } else {
          saveVocationalEvent(query, currGroupId);
        }
      });
    };

    function saveVocationalEvent(query, currGroupId) {
      QueryUtils.endpoint(baseUrl + '/saveVocationalEvent').save(query).$promise.then(function (result) {
        initializeData(result, currGroupId, $scope.plan.currentStudentGroups);
      });
    }

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
      updateSelectAll();
    };

    function updateSelectAll() {
      $scope.plan.selectAll = true;
      $scope.plan.studentGroups.forEach(function (studentGroup) {
        if (!studentGroup._selected) {
          $scope.plan.selectAll = false;
        }
      });
    }

    function setTeachersForStudentGroups(capacities, groups, journals) {
      if (angular.isArray(capacities) && angular.isArray(groups)) {
        groups.forEach(function (currGroup) {
          currGroup.teachers = [];
          currGroup.teacherIds = [];
          var currCapacities = capacities.filter(function (cap) {
            return cap.studentGroup === currGroup.id;
          });
          currCapacities.forEach(function (currCap) {
            var journal = journals.find(function (currJournal) {
              return currJournal.id === currCap.journal;
            });
            journal.teachers.forEach(function (currTeacher) {
              if (currGroup.teacherIds.indexOf(currTeacher.id) === -1) {
                currGroup.teacherIds.push(currTeacher.id);
                currGroup.teachers.push(currTeacher);
              }
            });
          });
        });
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
