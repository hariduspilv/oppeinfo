'use strict';

angular.module('hitsaOis').controller('TimetablePlanController', ['$scope', 'message', 'QueryUtils', 'DataUtils', '$route', '$location', '$rootScope', 'Classifier',
  function ($scope, message, QueryUtils, DataUtils, $route, $location, $rootScope, Classifier) {
  $scope.plan = {};
  $scope.timetableId = $route.current.params.id;
  $scope.weekday = ["daySun", "dayMon", "dayTue", "dayWed", "dayThu", "dayFri", "daySat"];
  var baseUrl = '/timetables';
  $scope.currentLanguageNameField = $rootScope.currentLanguageNameField;
  QueryUtils.endpoint(baseUrl + '/:id/createPlan').search({id: $scope.timetableId}).$promise.then(function(result) {
    $scope.plan = result;
    $scope.plan.selectAll = true;
    $scope.plan.journals.sort(function(a, b) {
      return a.id - b.id;
    });
    $scope.plan.selectedGroup = $route.current.params.groupId;
    Classifier.setSelectedCodes($scope.plan.studentGroups, $scope.plan.studentGroups.map(function(obj) {
      return obj.code;
    }));
    $scope.plan.currentStudentGroups = $scope.plan.studentGroups;
    $scope.plan.datesForTimetable = [];
    var beginning = new Date($scope.plan.startDate);
    var end = new Date($scope.plan.endDate);
    var currentDate = beginning;
    while(currentDate <= end) {
      $scope.plan.datesForTimetable.push(currentDate);
      currentDate = new Date(currentDate.getTime() + 86400000);
    }
    $scope.plan.lessonTimes.sort(function(a, b) {
      return a.lessonNr - b.lessonNr;
    });
    $scope.lessonsInDay = [];
    for(var j = 0; $scope.weekday.length > j; j++) {
      var currentDay = $scope.weekday[j];
      $scope.lessonsInDay[currentDay] = $scope.plan.lessonTimes.filter(function(it) {
        return it[currentDay] === true;
      })
    }
    $scope.lessonsInDay.sort(function(a, b) {
      return weekday.indexOf(a) - weekday.indexOf(b);
    });
    
    $scope.firstDay = $scope.weekday[new Date($scope.plan.startDate).getDay()];
    $scope.dayOrder = $scope.weekday.slice();
    while($scope.dayOrder[0] != $scope.firstDay) {
      $scope.dayOrder[$scope.dayOrder.length-1] = $scope.dayOrder.shift();
    }
    $scope.currentLessonTimes = [];
    for(var k = 0; $scope.dayOrder.length > k; k++) {
      var currentDay = $scope.lessonsInDay[$scope.dayOrder[k]].slice();
      while(angular.isDefined(currentDay) && currentDay.length > 0) {
        $scope.currentLessonTimes.push(currentDay.shift());
      }
    }
  });
  
  $scope.$watch('plan.selectedGroup', function () {
    if(angular.isDefined($scope.plan.selectedGroup)) {
      $scope.plan.currentCapacities = $scope.plan.studentGroupCapacities.filter(function (t) { return t.studentGroup === $scope.plan.selectedGroup });
      $scope.plan.currentCapacities = $scope.plan.currentCapacities.sort(function (a, b) {
        return a.journal - b.journal;
      });
      var currentCapacities = $scope.plan.currentCapacities.map(function (obj) {
        return obj.journal;
      });
      if(angular.isDefined($scope.plan.journals)) {
        $scope.plan.currentJournals = $scope.plan.journals.filter(function (t) {
          return currentCapacities.indexOf(t.id) !== -1;
        });
      }
    }
  });
  
  $scope.getByJournal = function(journal, param) {
    var capacity = $scope.plan.currentCapacities.find(function (it) {
      return it.journal === journal.id;
    })
    return capacity[param];
  }
  
  $scope.getByCapacity = function(capacity, param) {
    var journal = $scope.plan.currentJournals.find(function (it) {
      return it.id === capacity.journal;
    })
    return journal[param];
  }
  
  $scope.getCountForCapacity = function(capacity) {
    capacity.thisAllocatedLessons = 0;
    return capacity.thisPlannedLessons - capacity.thisAllocatedLessons;
  }
  
  $scope.saveNewEvent = function(params) {
    var selectedDay, daysSoFar = 0;
    for(var k = 0; $scope.dayOrder.length > k; k++) {
      daysSoFar += $scope.lessonsInDay[$scope.dayOrder[k]].length;
      if(params.index < daysSoFar) {
        selectedDay = $scope.dayOrder[k];
        break;
      }
    }
    var journalId = params.param2.substr(0, params.param2.indexOf('-'));
    var query = { 
        journal: journalId,
        timetable: $scope.timetableId,
        lessonTime: this.lessonTime.id,
        selectedDay: selectedDay,
    };
    QueryUtils.endpoint(baseUrl + '/saveEvent').post(query).$promise.then(function(result) {
        console.log(result);
    });
  }
  
  $scope.range = function(count){
    var array = []; 
    for (var i = 0; i < count; i++) { 
      array.push(i);
    } 
    return array;
  }

  $scope.updateGroups = function() {
    var selectedCodes = Classifier.getSelectedCodes($scope.plan.studentGroups);
    $scope.plan.currentStudentGroups = $scope.plan.studentGroups.filter(function(t) {
      return selectedCodes.indexOf(t.code) !== -1;
    });
  }
  
}
]);