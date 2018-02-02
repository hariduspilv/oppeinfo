'use strict';

angular.module('hitsaOis').controller('TimetableLessonTimeListController', function ($scope, $q, QueryUtils, Classifier, DataUtils, $filter, USER_ROLES, AuthService) {
  $scope.canEdit = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_TUNDAEG);

  var clMapper = Classifier.valuemapper({day: 'NADALAPAEV'});
  var promises = clMapper.promises;
  $scope.days = [];

  var currentPeriodPromise = QueryUtils.endpoint('/lessontimes/currentPeriod').get(function(period) {
    DataUtils.convertStringToDates(period, ["periodStart"]);
    $scope.criteria.from = period.periodStart;
  }).$promise;
  promises.push(currentPeriodPromise);

  QueryUtils.createQueryForm($scope, '/lessontimes', {order: 'lessonNr'}, clMapper.objectmapper);
  $q.all(promises).then(function() {
    var days = [{day:'NADALAPAEV_E'}, {day:'NADALAPAEV_T'}, {day:'NADALAPAEV_K'}, {day:'NADALAPAEV_N'}, {day:'NADALAPAEV_R'}, {day:'NADALAPAEV_L'}, {day:'NADALAPAEV_P'}];
    clMapper.objectmapper(days).forEach(function(it) {
      $scope.days.push(it.day);
    });
    $scope.loadData();
  });

  function getSelectedDays(lessonTime) {
    var days = [];
    if (lessonTime.dayMon === true) {
      days.push({day: 'NADALAPAEV_E'});
    }
    if (lessonTime.dayTue === true) {
      days.push({day: 'NADALAPAEV_T'});
    }
    if (lessonTime.dayWed === true) {
      days.push({day: 'NADALAPAEV_K'});
    }
    if (lessonTime.dayThu === true) {
      days.push({day: 'NADALAPAEV_N'});
    }
    if (lessonTime.dayFri === true) {
      days.push({day: 'NADALAPAEV_R'});
    }
    if (lessonTime.daySat === true) {
      days.push({day: 'NADALAPAEV_L'});
    }
    if (lessonTime.daySun === true) {
      days.push({day: 'NADALAPAEV_P'});
    }
    return days;
  }

  $scope.displayWeekdays = function(lessonTime) {
    var days = getSelectedDays(lessonTime);
    return clMapper.objectmapper(days).map(function(it) {
      return it.day[$scope.currentLanguageNameField()].charAt(0);
    }).join(", ");
  };

  $scope.displayBuildings = function(buildings) {
    var result = buildings.map(function(it) {
      return it[$scope.currentLanguageNameField()];
    });
    return $filter('orderBy')(result).join(", ");
  };

  $scope.hasPassed = function(lessonTime) {
    DataUtils.convertStringToDates(lessonTime, ["validThru"]);
    return lessonTime.validThru !== null && lessonTime.validThru < new Date();
  };

  $scope.canEditLessonTime = function(lessonTime) {
    return !$scope.hasPassed(lessonTime) && $scope.canEdit;
  };
});
