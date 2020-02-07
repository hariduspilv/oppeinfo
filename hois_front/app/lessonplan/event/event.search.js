'use strict';

angular.module('hitsaOis').controller('LessonplanEventSearchController',
  function ($scope, $route, QueryUtils, USER_ROLES, AuthService) {
    $scope.currentNavItem = 'events';
    $scope.auth = $route.current.locals.auth;
    $scope.eventsPerm = AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_SYNDMUS);
    $scope.canEdit = AuthService.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS]);
    $scope.canViewPersonalEvents = $scope.auth.isAdmin() || ($scope.auth.isLeadingTeacher() && AuthService.isAuthorized(USER_ROLES.ROLE_OIGUS_V_TEEMAOIGUS_PERSYNDMUS));
    $scope.formState = {};
    var baseUrl = '/timetableevents';

    $scope.formState.studyPeriods = QueryUtils.endpoint('/autocomplete/studyPeriodsWithYear').query();
    $scope.formState.studyPeriods.$promise.then(function (response) {
      response.forEach(function (studyPeriod) {
        studyPeriod[$scope.currentLanguageNameField()] = $scope.currentLanguageNameField(studyPeriod.studyYear) + ' ' + $scope.currentLanguageNameField(studyPeriod);
      });
    });
    
    QueryUtils.createQueryForm($scope, baseUrl, {
      order:'4 desc'
    });

    if ($scope.eventsPerm) {
      if (!angular.isDefined($scope.criteria.singleEvent)) {
        $scope.criteria.singleEvent = true;
      }
      if (!angular.isDefined($scope.criteria.personalEvent)) {
        $scope.criteria.personalEvent = false;
      }
    }

    if (!angular.isDefined($scope.criteria.from)) {
      $scope.criteria.from = new Date();
    }
    $scope.loadData();

    $scope.$watch('criteria.roomObject', function () {
      $scope.criteria.room = $scope.criteria.roomObject ? $scope.criteria.roomObject.id : null;
    });

    $scope.isPersonalEventChanged = function () {
      if (!$scope.criteria.singleEvent) {
        $scope.criteria.singleEvent = true;
      }
    };

    var _clearCriteria = $scope.clearCriteria;
    $scope.directiveControllers = []; 
    $scope.clearEventCriteria = function () {
      _clearCriteria();
      $scope.criteria.singleEvent = false;
      $scope.directiveControllers.forEach(function (c) { 
        c.clear(); 
      }); 
      $scope.criteria.order = '4 desc';
    };

    $scope.afterNow = function (date, time) {
      date = new Date(date);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var dateString = '' + year + '-' + month + '-' + day;
      return new Date(dateString + ' ' + time) > new Date();
    };

    $scope.allowedToEdit = function (event) {
      if (!event.canEdit) {
        return false;
      }
      if ($scope.auth.isAdmin()) {
        return !event.isPersonal || event.person.id === $scope.auth.person;
      }

      // leading teacher can only see his/her studentgroup events or his/her personal events
      // therefore there is no need for stricter check here
      if ($scope.auth.isLeadingTeacher()) {
        return true;
      }

      if ($scope.auth.isTeacher()) {
        if (!event.teachers) {
          return false;
        }

        for (var i = 0; i < event.teachers.length; i++) {
          if (event.teachers[i].id === $scope.auth.teacher) {
            return true;
          }
        }
      }
      return false;
    };
  })
  .controller('LessonplanEventRoomSearchController', ['$scope', 'QueryUtils', 'FormUtils', '$timeout', 'message', 'USER_ROLES',
  function ($scope, QueryUtils, FormUtils, $timeout, message, USER_ROLES) {
    $scope.currentNavItem = 'rooms';
    var startTimeCopy, endTimeCopy;
    $scope.canEdit = $scope.isAuthorized([USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_SYNDMUS, USER_ROLES.ROLE_OIGUS_M_TEEMAOIGUS_PERSYNDMUS]);
    $scope.canAddEvent = canAddEvent;

    $scope.setPartlyBox = checkCheckboxPartlyBusy;
    $scope.event = getEventLink;
    
    QueryUtils.createQueryForm($scope, '/timetableevents/rooms', {isFreeRoom: true, from: new Date(), thru: new Date()});

    var _loadData = $scope.loadData;
    $scope.loadData = function() {
      startTimeCopy = angular.copy($scope.criteria.startTime);
      endTimeCopy = angular.copy($scope.criteria.endTime);
      if ($scope.searchForm) {
        $scope.timeError = $scope.criteria.startTime && $scope.criteria.endTime && $scope.criteria.startTime >= $scope.criteria.endTime;

        if ($scope.timeError) {
          message.error('main.messages.form-has-errors');
          return;
        }
        FormUtils.withValidForm($scope.searchForm, _loadData);
      }
    };

    var _clearCriteria = $scope.clearCriteria;
    $scope.clearCriteria = function () {
      _clearCriteria();
      $scope.criteria.isFreeRoom = true;
    }

    // Initialize form before.
    $timeout($scope.loadData);

    function checkCheckboxPartlyBusy() {
      if (!$scope.criteria.isFreeRoom) {
        $scope.criteria.isPartlyBusyRoom = false;
      }
    }

    function getEventLink(row) {
      return '#/timetable/timetableEvent/new?' +
             'room=' + row.id + '&' +
             ((row.times || []).length === 0 && startTimeCopy ? 'startTime=' + (startTimeCopy instanceof Date ? startTimeCopy.toISOString() : startTimeCopy) + '&' : '') +
             ((row.times || []).length === 0 && endTimeCopy ? 'endTime=' + (endTimeCopy instanceof Date ? endTimeCopy.toISOString() : endTimeCopy) + '&' : '') +
             'date=' + row.startDate;
    }

    function canAddEvent(row) {
      return $scope.canEdit && $scope.criteria.isFreeRoom && row.isUsedInStudy;
    }
  }]);
